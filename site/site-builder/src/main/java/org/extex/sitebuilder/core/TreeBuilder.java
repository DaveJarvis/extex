/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.sitebuilder.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.xml.sax.SAXException;

/**
 * This class is a builder for directory trees.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TreeBuilder extends TemplatingEngine {

    /**
     * The field <tt>base</tt> contains the base directory.
     */
    private File base = null;

    /**
     * The field <tt>target</tt> contains the target directory.
     */
    private File target = null;

    /**
     * The field <tt>template</tt> contains the template file.
     */
    private String template = "org/extex/sitebuilder/sitemap.vm";

    /**
     * The field <tt>omitList</tt> contains the omit patterns.
     */
    private List<String> omitList = new ArrayList<String>();

    /**
     * The field <tt>DIR_FILTER</tt> contains the file filter for $directory.
     */
    private final FilenameFilter DIR_FILTER = new FilenameFilter() {

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
         */
        @Override
        public boolean accept(File dir, String name) {

            return FILTER.accept(dir, name) && !name.equals("index.html");
        }
    };

    /**
     * The field <tt>FILTER</tt> contains the file filter for $directory.
     */
    private final FilenameFilter FILTER = new FilenameFilter() {

        /**
         * {@inheritDoc}
         * 
         * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
         */
        @Override
        public boolean accept(File dir, String name) {

            return !name.startsWith(".") && !omitList.contains(name);
        }
    };

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * The field <tt>translateHtml</tt> contains the indicator whether or not
     * the HTML files should be translated.
     */
    private boolean translateHtml = true;

    /**
     * Adder for an omit pattern.
     * 
     * @param omit the new omit pattern
     */
    public void addOmit(String omit) {

        omitList.add(omit);
    }

    /**
     * Apply a single template to a file.
     * 
     * @param infile the input file
     * @param t the template
     * @param outdir the output directory
     * @param context the context
     * @param logger the logger
     * @param engine the engine
     * 
     * @throws IOException in case of an I/O error
     * @throws ParserConfigurationException in case of an error
     * @throws SAXException in case of an error
     */
    public void apply(File infile, Template t, File outdir,
            VelocityContext context, Logger logger, VelocityEngine engine)
            throws IOException,
                ParserConfigurationException,
                SAXException {

        if (!outdir.exists() && !outdir.mkdirs()) {
            throw new IOException("Creation of directory failed: " + outdir);
        }

        File outfile = new File(outdir, infile.getName());
        if (logger != null) {
            logger.info(infile + " ==> " + outfile);
        }

        InputStream stream =
                new BufferedInputStream(new FileInputStream(infile));
        StringBuilder buffer = new StringBuilder();
        try {
            for (int c = stream.read(); c >= 0; c = stream.read()) {
                buffer.append((char) c);
            }
        } finally {
            stream.close();
        }

        context.put("relatvePath", ".");
        context.put("headContent", "");
        context.put("bodyContent", "");
        String head = applyFindTag("head", engine, buffer, context);
        String body = applyFindTag("body", engine, buffer, context);

        context.put("headContent", head);
        context.put("bodyContent", body);
        Writer writer = new FileWriter(outfile);
        try {
            t.merge(context, writer);
        } finally {
            writer.close();
        }
    }

    /**
     * Extract the contents of an XML tag from a file. The method does not use
     * an XML parser but uses a rough approximation which allows invalid XML to
     * be processed.
     * 
     * @param tag the tag
     * @param engine the engine
     * @param buffer the buffer
     * @param context the context
     * 
     * @return the value of the tagged node
     * 
     * @throws IOException in case of an I/O error
     * @throws ResourceNotFoundException in case of an error
     * @throws MethodInvocationException in case of an error
     * @throws ParseErrorException in case of an error
     */
    private String applyFindTag(String tag, VelocityEngine engine,
            StringBuilder buffer, Context context)
            throws ParseErrorException,
                MethodInvocationException,
                ResourceNotFoundException,
                IOException {

        int start = buffer.indexOf("<" + tag);
        if (start < 0) {
            throw new RuntimeException("missing <" + tag);
        }
        start = buffer.indexOf(">", start);
        if (start < 0) {
            throw new RuntimeException("missing > for " + tag);
        }
        start++;

        int end = buffer.indexOf("</" + tag + ">", start);
        if (end < 0) {
            throw new RuntimeException("missing </" + tag + ">");
        }

        Writer writer = new StringWriter();
        engine.evaluate(context, writer, tag, //
            buffer.substring(start, end).trim());
        return writer.toString();
    }

    /**
     * Traverse a directory tree and process the files.
     * 
     * @param dir the input directory
     * @param outdir the output directory
     * @param relativePath the path to the top directory
     * @param t the template
     * @param engine the engine
     * @param context the context
     * 
     * @throws IOException in case of an I/O error
     * @throws SAXException in case of an error
     * @throws ParserConfigurationException in case of an error
     */
    private void applyTemplate(File dir, File outdir, String relativePath,
            Template t, VelocityEngine engine, VelocityContext context)
            throws IOException,
                ParserConfigurationException,
                SAXException {

        if (omitList.contains(dir.getName())) {
            return;
        }
        File[] dirlist = dir.listFiles(DIR_FILTER);
        Arrays.sort(dirlist);
        context.put("relativePath", relativePath);
        String info =
                inheritSetting(dir, relativePath, engine, context, ".info");
        String nav =
                inheritSetting(dir, relativePath, engine, context,
                    ".navigation");
        String tabs =
                inheritSetting(dir, relativePath, engine, context, ".tabs");

        for (File f : dir.listFiles(FILTER)) {

            if (f.isDirectory()) {
                applyTemplate(f,
                    new File(outdir, f.getName()), //
                    (relativePath.equals(".") ? ".." : relativePath + "/.."),
                    t, engine, context);
            } else if (translateHtml) {
                context.put("relativePath", relativePath);
                context.put("directory", dirlist);
                context.put("navigation", nav);
                context.put("info", info);
                context.put("tabs", tabs);
                apply(f, t, outdir, context, logger, engine);
            } else {
                copyFile(outdir, f);
            }
        }
    }

    /**
     * Traverse a directory tree and copy the files and directories.
     * 
     * @param dir the input directory
     * @param outdir the output directory
     * 
     * @throws IOException in case of an I/O error
     */
    private void copy(File dir, File outdir) throws IOException {

        if (!FILTER.accept(dir.getParentFile(), dir.getName())) {
            if (logger != null) {
                logger.info(dir + " omitted");
            }
            return;
        }

        for (File f : dir.listFiles(FILTER)) {

            if (f.isDirectory()) {
                copy(f, new File(outdir, f.getName()));
                continue;
            } else if (!outdir.exists() && !outdir.mkdirs()) {
                throw new FileNotFoundException(outdir.toString());
            } else if (!outdir.isDirectory()) {
                throw new FileNotFoundException(outdir.toString());
            }
            copyFile(outdir, f);
        }
    }

    private void copyFile(File outdir, File file)
            throws FileNotFoundException,
                IOException {

        InputStream in = new BufferedInputStream(new FileInputStream(file));
        try {
            File outfile = new File(outdir, file.getName());
            OutputStream out =
                    new BufferedOutputStream(new FileOutputStream(outfile));
            try {
                if (logger != null) {
                    logger.info(file + " --> " + outfile);
                }

                for (int c = in.read(); c >= 0; c = in.read()) {
                    out.write(c);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * Evaluate a file as Velocity template.
     * 
     * @param infile the input file
     * @param tag the tag
     * @param engine the engine
     * @param context the context
     * 
     * @return the evaluated file contents
     * 
     * @throws IOException in case of an I/O error
     * @throws ResourceNotFoundException in case of an error
     * @throws MethodInvocationException in case of an error
     * @throws ParseErrorException in case of an error
     */
    private String evaluate(File infile, String tag, VelocityEngine engine,
            Context context)
            throws ParseErrorException,
                MethodInvocationException,
                ResourceNotFoundException,
                IOException {

        if (infile == null) {
            return "";
        }

        InputStream stream =
                new BufferedInputStream(new FileInputStream(infile));
        StringBuilder buffer = new StringBuilder();
        try {
            for (int c = stream.read(); c >= 0; c = stream.read()) {
                buffer.append((char) c);
            }
        } finally {
            stream.close();
        }

        Writer writer = new StringWriter();
        engine.evaluate(context, writer, tag, buffer.toString());
        return writer.toString();
    }

    /**
     * TODO gne: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void generate() throws Exception {

        if (base == null) {
            throw new IllegalArgumentException("Missing base");
        }
        if (target == null) {
            throw new IllegalArgumentException("Missing target");
        }
        if (template == null) {
            throw new IllegalArgumentException("Missing template");
        }

        VelocityEngine engine = makeEngine();
        VelocityContext context = makeContext(engine);

        applyTemplate(base, //
            target, //
            ".", //
            engine.getTemplate(template), //
            engine, //
            context);
    }

    /**
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Search for a file from the current directory up to the root and evaluate
     * the first one found.
     * 
     * @param cwd the working directory
     * @param relativePath the path for counting to top
     * @param engine the engine
     * @param context the context
     * @param type the name of the file
     * 
     * @return the content of the file sought or the empty string if none was
     *         found
     * 
     * @throws IOException in case of an I/O error
     */
    private String inheritSetting(File cwd, String relativePath,
            VelocityEngine engine, VelocityContext context, String type)
            throws IOException {

        int pathLength = relativePath.length();
        File dir = cwd;

        for (int i = pathLength == 1 ? 1 : pathLength + 2; i > 0; i -= 3) {
            File f = new File(dir, type);
            if (f.exists()) {
                return evaluate(f, type, engine, context);
            }
            dir = dir.getParentFile();
        }
        return "";
    }

    /**
     * Setter for base.
     * 
     * @param base the base to set
     * 
     * @throws FileNotFoundException in case of an error
     */
    public void setBase(File base) throws FileNotFoundException {

        if (!base.isDirectory()) {
            throw new FileNotFoundException(base.toString());
        }

        this.base = base;
    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for target.
     * 
     * @param target the target to set
     */
    public void setTarget(File target) {

        this.target = target;
    }

    /**
     * Setter for template.
     * 
     * @param template the template to set
     */
    public void setTemplate(String template) {

        this.template = template;
    }

    /**
     * Setter for translateHtml.
     * 
     * @param translateHtml the translateHtml to set
     */
    public void setTranslateHtml(boolean translateHtml) {

        this.translateHtml = translateHtml;
    }

}
