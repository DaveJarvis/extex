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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.log.LogChute;

/**
 * This is the site builder of <logo>&epsilon;&chi;T<span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
 * >e</span>X</logo> which utilizes Apache Velocity
 * as templating engine.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilder implements LibReceiver {

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


    public SiteBuilder() {

        logger = Logger.getLogger(SiteBuilder.class.getName());
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
     * Factory method for the {@link TreeBuilder} class. The new instance is
     * integrated into the list of site maps to be produced.
     * 
     * @return the new instance
     */
    public TreeBuilder createTreeBuilder() {

        TreeBuilder treeBuilder = new TreeBuilder();
        treeBuilder.setLogger(logger);
        treeBuilder.setTarget(target);
        for (String lib : libraries) {
            treeBuilder.lib(lib);
        }
        for (String om : omit) {
            treeBuilder.addOmit(om);
        }
        bases.add(treeBuilder);
        return treeBuilder;
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
    public TreeBuilder createTreeBuilder(String basedir)
            throws FileNotFoundException {

        TreeBuilder treeBuilder = createTreeBuilder();
        treeBuilder.setBase(new File(basedir));
        return treeBuilder;
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
     * @param lib the lib to add or <code>null</code>
     * 
     * @throws ParseErrorException in case of an error
     * @throws ResourceNotFoundException in case of an error
     */
    @Override
    public void lib(String lib)
            throws ResourceNotFoundException,
                ParseErrorException {

        if (lib == null) {
            return;
        }
        libraries.add(lib);
        for (TreeBuilder base : bases) {
            base.lib(lib);
        }
    }

    /**
     * Add some directory or file names to be omitted. This information is kept
     * for future tree builders and immediately propagated to already registered
     * tree builders.
     * 
     * @param omits the list of omit; a <code>null</code> list is silently
     *        ignored
     */
    public void omit(String omits) {

        if (omits == null) {
            return;
        }
        omit.add(omits);
        for (TreeBuilder base : bases) {
            base.addOmit(omits);
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
     * Setter for the target dir.
     * 
     * @param targetDir the target dir to set
     */
    public void setTarget(File targetDir) {

        if (targetDir == null) {
            throw new IllegalArgumentException("Missing target directory");
        }
        target = targetDir;
        for (TreeBuilder base : bases) {
            base.setTarget(targetDir);
        }
        for (SiteMapBuilder map : siteMapList) {
            map.setTarget(targetDir);
        }
    }

}
