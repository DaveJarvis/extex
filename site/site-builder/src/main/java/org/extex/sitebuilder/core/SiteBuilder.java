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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
     * The field <tt>bases</tt> contains the list of the base directories for
     * the files to be transformed.
     */
    private List<TreeBuilder> bases = new ArrayList<TreeBuilder>();

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
     * The field <tt>siteMapList</tt> contains the ...
     */
    private List<SiteMapBuilder> siteMapList = new ArrayList<SiteMapBuilder>();

    /**
     * The field <tt>newsBuilderList</tt> contains the ...
     */
    private List<NewsBuilder> newsBuilderList = new ArrayList<NewsBuilder>();

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
     * Factory method for the {@link SiteMapBuilder} class. The new instance is
     * integrated into the list of site maps to be produced.
     * 
     * @return the new site map
     */
    public NewsBuilder createNewsBuilder() {

        NewsBuilder news = new NewsBuilder();
        // map.setTarget(target);
        news.setLogger(logger);
        newsBuilderList.add(news);
        return news;
    }

    /**
     * Factory method for the {@link TreeBuilder} class. The new instance is
     * integrated into the list of site maps to be produced.
     * 
     * @param basedir the base directory
     * 
     * @return the new instance
     * 
     * @throws FileNotFoundException in case of an error
     */
    public TreeBuilder createSiteBase(File basedir) throws FileNotFoundException {

        TreeBuilder siteBase = new TreeBuilder();
        siteBase.setLogger(logger);
        siteBase.setBase(basedir);
        siteBase.setTarget(target);
        for (String lib : libraries) {
            siteBase.addLibrary(lib);
        }
        bases.add(siteBase);
        return siteBase;
    }

    /**
     * Factory method for the {@link TreeBuilder} class. The new instance is
     * integrated into the list of site maps to be produced.
     * 
     * @param basedir the base directory
     * 
     * @return the new instance
     * 
     * @throws FileNotFoundException in case of an error
     */
    public TreeBuilder createSiteBase(String basedir) throws FileNotFoundException {

        return createSiteBase(new File(basedir));
    }

    /**
     * Factory method for the {@link SiteMapBuilder} class. The new instance is
     * integrated into the list of site maps to be produced.
     * 
     * @return the new site map
     */
    public SiteMapBuilder createSiteMap() {

        SiteMapBuilder map = new SiteMapBuilder();
        map.setTarget(target);
        map.setLogger(logger);
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
            for (TreeBuilder base : bases) {
                base.addLibrary(lib);
            }
        }
    }

    /**
     * Add some directory or file names to be omitted.
     * 
     * @param omits the list of omit; a <code>null</code> list is silently
     *        ignored
     */
    public void omit(String... omits) {

        if (omits == null) {
            return;
        }
        for (String s : omits) {
            omit.add(s);
            for (TreeBuilder base : bases) {
                base.addOmit(s);
            }
        }
    }

    /**
     * Generate the target directory structure and populate it.
     * 
     * @throws Exception in case of an error
     */
    public void run() throws Exception {

        for (TreeBuilder base : bases) {
            base.generate();
        }

        for (SiteMapBuilder map : siteMapList) {
            map.generate();
        }

        for (NewsBuilder news : newsBuilderList) {
            news.generate();
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
        for (TreeBuilder base : bases) {
            base.setTarget(targetDir);
        }
        for (SiteMapBuilder map : siteMapList) {
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
