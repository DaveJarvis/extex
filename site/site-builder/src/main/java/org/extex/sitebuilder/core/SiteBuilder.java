/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.log.LogChute;
import org.xml.sax.SAXException;

/**
 * This is the site builder of &epsilon;&chi;TeX which utilizes Apache Velocity
 * as templating engine.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilder {

    /**
     * The field <tt>levelMap</tt> contains the mapping from Velocity error
     * levels to those of the Java logger.
     */
    private static Map<Integer, Level> levelMap = new HashMap<Integer, Level>();
    static {
        levelMap.put(Integer.valueOf(LogChute.ERROR_ID), Level.SEVERE);
        levelMap.put(Integer.valueOf(LogChute.WARN_ID), Level.WARNING);
        levelMap.put(Integer.valueOf(LogChute.INFO_ID), Level.INFO);
        levelMap.put(Integer.valueOf(LogChute.DEBUG_ID), Level.FINE);
        levelMap.put(Integer.valueOf(LogChute.TRACE_ID), Level.FINEST);
    }

    /**
     * The field <tt>FILTER</tt> contains the file filter for $directory.
     */
    private final FilenameFilter FILTER = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {

            return !name.startsWith(".") && !omit.contains(name);
        }
    };

    /**
     * The field <tt>bases</tt> contains the list of the base directories for
     * the files to be transformed.
     */
    private List<SiteBase> bases = new ArrayList<SiteBase>();

    /**
     * The field <tt>resources</tt> contains the list of the resource
     * directories for the files to be copied.
     */
    private List<File> resources = new ArrayList<File>();

    /**
     * The field <tt>target</tt> contains the target directory.
     */
    private File target = new File("target/test-site");

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * The field <tt>omit</tt> contains the list of files and directories to be
     * omitted.
     */
    private List<String> omit = new ArrayList<String>();

    /**
     * The field <tt>libraries</tt> contains the libraries to be loaded.
     */
    private List<String> libraries = new ArrayList<String>();

    /**
     * The field <tt>sitemapTempate</tt> contains the template for the site map.
     */
    private String sitemapTempate = "org/extex/sitebuilder/sitemap.vm";

    /**
     * The field <tt>siteMapList</tt> contains the ...
     */
    private List<SiteMap> siteMapList = new ArrayList<SiteMap>();

    /**
     * Creates a new object.
     */
    public SiteBuilder() {

        logger = Logger.getLogger(SiteBuilder.class.getName());
    }

    /**
     * Setter for the resource directory.
     * 
     * @param resourceDir the resource directory to set
     */
    public void addResourceDir(File resourceDir) {

        if (resourceDir == null) {
            return;
        }
        this.resources.add(resourceDir);
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
            InputStream in = new BufferedInputStream(new FileInputStream(f));
            try {
                File outfile = new File(outdir, f.getName());
                OutputStream out =
                        new BufferedOutputStream(new FileOutputStream(outfile));
                try {
                    if (logger != null) {
                        logger.info(f + " --> " + outfile);
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
    }

    /**
     * TODO gne: missing JavaDoc
     * 
     * @param basedir
     * @return
     * @throws FileNotFoundException
     */
    public SiteBase createSiteBase(File basedir) throws FileNotFoundException {

        SiteBase siteBase = new SiteBase();
        siteBase.setBase(basedir);
        siteBase.setTarget(target);
        for (String lib : libraries) {
            siteBase.addLibrary(lib);
        }
        bases.add(siteBase);
        return siteBase;
    }

    /**
     * TODO gne: missing JavaDoc
     * 
     * @param basedir
     * @return
     * @throws FileNotFoundException
     */
    public SiteBase createSiteBase(String basedir) throws FileNotFoundException {

        return createSiteBase(new File(basedir));
    }

    /**
     * TODO gne: missing JavaDoc
     * 
     * @return
     */
    public SiteMap createSiteMap() {

        SiteMap map = new SiteMap();
        map.setTarget(target);
        siteMapList.add(map);
        return map;
    }

    /**
     * Getter for the logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Adder for libs.
     * 
     * @param list the libs to add or <code>null</code>
     * 
     * @throws ParseErrorException in case of an error
     * @throws ResourceNotFoundException in case of an error
     */
    public void lib(String... list)
            throws ResourceNotFoundException,
                ParseErrorException {

        if (list == null) {
            return;
        }
        for (String lib : list) {
            libraries.add(lib);
            for (SiteBase base : bases) {
                base.addLibrary(lib);
            }
        }
    }

    /**
     * Add some strings to be omitted.
     * 
     * @param list the list of omit; a <code>null</code> list is silently
     *        ignored
     */
    public void omit(String... list) {

        if (list == null) {
            return;
        }
        for (String s : list) {
            omit.add(s);
        }
    }

    /**
     * Generate the target directory structure and populate it.
     * 
     * @throws Exception in case of an error
     */
    public void run() throws Exception {

        // for (File dir : resources) {
        // if (!dir.isDirectory()) {
        // throw new FileNotFoundException(dir.toString());
        // }
        // copy(dir, targetDirectory);
        // }

        for (SiteBase base : bases) {
            base.generate();
        }

        for (SiteMap map : siteMapList) {
            map.generate();
        }
    }

    /**
     * Setter for the logger.
     * 
     * @param logger the logger to set
     */
    protected void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for the log level.
     * 
     * @param level the new level
     */
    public void setLogLevel(Level level) {

        logger.setLevel(level);
    }

    /**
     * Setter for the target dir.
     * 
     * @param targetDir the target dir to set
     */
    public void setTarget(File targetDir) {

        if (targetDir == null) {
            throw new NullPointerException("Missing target directory");
        }
        target = targetDir;
        for (SiteBase base : bases) {
            base.setTarget(targetDir);
        }
        for (SiteMap map : siteMapList) {
            map.setTarget(targetDir);
        }
    }

    /**
     * Setter for the template.
     * 
     * @param template the template to set
     */
    public void setTemplate(String template) {

        if (template == null) {
            throw new NullPointerException("Missing template");
        }
        // TODO
    }
}
