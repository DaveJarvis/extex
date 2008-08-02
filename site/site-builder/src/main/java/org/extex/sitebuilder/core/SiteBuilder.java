/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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
import java.io.BufferedWriter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
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
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.JdkLogChute;
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
     * The field <tt>DIR_FILTER</tt> contains the file filter for $directory.
     */
    private final FilenameFilter DIR_FILTER = new FilenameFilter() {

        public boolean accept(File dir, String name) {

            return !name.startsWith(".") && !name.equals("index.html")
                    && !omit.contains(name);
        }
    };

    /**
     * The field <tt>FILTER</tt> contains the file filter for $directory.
     */
    private final FilenameFilter FILTER = new FilenameFilter() {

        public boolean accept(File dir, String name) {

            return !name.startsWith(".") && !omit.contains(name);
        }
    };

    /**
     * The field <tt>ls</tt> contains the adapter for the logging.
     */
    private LogChute logChute = new LogChute() {

        public void init(RuntimeServices rs) throws Exception {

            //
        }

        public boolean isLevelEnabled(int level) {

            return false;
        }

        public void log(int id, String message) {

            Level l = levelMap.get(Integer.valueOf(id));
            logger.log(l != null ? l : Level.FINER, message);
        }

        public void log(int id, String message, Throwable cause) {

            Level l = levelMap.get(Integer.valueOf(id));
            logger.log(l != null ? l : Level.FINER, message, cause);
        }

    };

    /**
     * The field <tt>siteMap</tt> contains the name of the site map file.
     */
    private File siteMap = null;

    /**
     * The field <tt>template</tt> contains the name of the template.
     */
    private String template = "org/extex/sitebuilder/site.vm";

    /**
     * The field <tt>baseDirectory</tt> contains the name of the base directory
     * for the files to be transformed.
     */
    private File baseDirectory = new File("src/site/html");

    /**
     * The field <tt>resourceDirectory</tt> contains the name of the resource
     * directory for the files to be copied.
     */
    private File resourceDirectory = new File("src/site/resources");

    /**
     * The field <tt>targetDirectory</tt> contains the target directory.
     */
    private File targetDirectory = new File("target/site");

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
     * The field <tt>lib</tt> contains the libraries to be loaded.
     */
    private List<String> lib = new ArrayList<String>();

    /**
     * The field <tt>sitemapTempate</tt> contains the ...
     */
    private String sitemapTempate = "org/extex/sitebuilder/sitemap.vm";

    /**
     * Creates a new object.
     */
    public SiteBuilder() {

        logger = Logger.getLogger(SiteBuilder.class.getName());
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

        outdir.mkdirs();

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

        if (omit.contains(dir.getName())) {
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
            } else if (!omit.contains(f.getName())) {
                context.put("relativePath", relativePath);
                context.put("directory", dirlist);
                context.put("navigation", nav);
                context.put("info", info);
                context.put("tabs", tabs);
                apply(f, t, outdir, context, logger, engine);
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

        if (omit.contains(dir.getName())) {
            return;
        }

        for (File f : dir.listFiles()) {

            if (f.isDirectory()) {
                copy(f, new File(outdir, f.getName()));
                continue;
            } else if (omit.contains(f.getName())) {
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
     * TODO gene: missing JavaDoc
     * 
     * @param engine the engine
     * @param context the context
     * 
     * @throws Exception in case of an error
     */
    private void createSiteMap(VelocityEngine engine, VelocityContext context)
            throws Exception {

        context.put("headContent", "");
        context.put("bodyContent", "");
        context.put("relativePath", ".");
        context.put("targetDirectory", new FileWrapper(targetDirectory));
        siteMap.getParentFile().mkdirs();
        Writer writer = new BufferedWriter(new FileWriter(siteMap));
        try {
            Template t = engine.getTemplate(sitemapTempate);
            t.merge(context, writer);
        } finally {
            writer.close();
            context.put("targetDirectory", null);
        }
    }

    /**
     * Evaluate a file as >Velocity template.
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
     * Getter for the logger.
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
     * Adder for libs.
     * 
     * @param list the libs to add
     * 
     * @throws Exception in case of an error
     * @throws ParseErrorException in case of an error
     * @throws ResourceNotFoundException in case of an error
     */
    public void lib(String... list)
            throws ResourceNotFoundException,
                ParseErrorException,
                Exception {

        if (list != null) {
            for (String s : list) {
                lib.add(s);
            }
        }
    }

    /**
     * Create and initialize a new context.
     * 
     * @param engine the engine
     * 
     * @return the new context
     * 
     * @throws Exception in case of an error
     */
    private VelocityContext makeContext(VelocityEngine engine) throws Exception {

        VelocityContext context = new VelocityContext();
        context.setAllowRendering(false);
        for (String s : lib) {
            engine.getTemplate(s).merge(context, null);
        }
        context.setAllowRendering(true);
        context.put("currentDate", new Date());
        context.put("dateFormat", new SimpleDateFormat("d/m/yyyy",
            Locale.ENGLISH));
        return context;
    }

    /**
     * Create a new engine and initialize it.
     * 
     * @return the engine
     * 
     * @throws Exception in case of an error
     */
    private VelocityEngine makeEngine() throws Exception {

        VelocityEngine engine = new VelocityEngine();
        // engine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, logChute);
        engine.setProperty(JdkLogChute.RUNTIME_LOG_JDK_LOGGER, "xxx");

        Properties prop = new Properties();
        String name = getClass().getName().replace('.', '/') + ".properties";
        InputStream stream =
                getClass().getClassLoader().getResourceAsStream(name);
        if (stream == null) {
            throw new FileNotFoundException(name);
        }
        try {
            prop.load(stream);
        } finally {
            stream.close();
        }
        engine.init(prop);
        return engine;
    }

    /**
     * Add some strings to be omitted.
     * 
     * @param list the list of omit; <code>null</code> is ignored
     */
    public void omit(String... list) {

        if (list != null) {
            for (String s : list) {
                omit.add(s);
            }
        }
    }

    /**
     * Generate the target directory structure and populate it.
     * 
     * @throws Exception in case of an error
     */
    public void run() throws Exception {

        if (resourceDirectory.isDirectory()) {
            copy(resourceDirectory, targetDirectory);
        }

        VelocityEngine engine = makeEngine();
        VelocityContext context = makeContext(engine);

        if (baseDirectory.isDirectory()) {
            applyTemplate(baseDirectory, //
                targetDirectory, //
                ".", //
                engine.getTemplate(template), //
                engine, //
                context);
        }

        if (siteMap != null) {
            createSiteMap(engine, context);
        }
    }

    /**
     * Setter for the base directory.
     * 
     * @param basedir the base directory to set
     */
    public void setBaseDir(File basedir) {

        if (basedir != null) {
            this.baseDirectory = basedir;
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
     * Setter for the resource directory.
     * 
     * @param resourcedir the resource directory to set
     */
    public void setResourceDir(File resourcedir) {

        if (resourcedir != null) {
            this.resourceDirectory = resourcedir;
        }
    }

    /**
     * Setter for the siteMap.
     * 
     * @param siteMap the siteMap to set
     */
    protected void setSiteMap(File siteMap) {

        this.siteMap = siteMap;
    }

    /**
     * Setter for the target dir.
     * 
     * @param targetdir the target dir to set
     */
    public void setTargetdir(File targetdir) {

        if (targetdir != null) {
            this.targetDirectory = targetdir;
        }
    }

    /**
     * Setter for the template.
     * 
     * @param template the template to set
     */
    public void setTemplate(String template) {

        if (template != null) {
            this.template = template;
        }
    }

}
