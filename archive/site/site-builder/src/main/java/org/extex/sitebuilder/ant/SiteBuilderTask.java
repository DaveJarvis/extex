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

package org.extex.sitebuilder.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.extex.sitebuilder.core.SiteBuilder;

/**
 * This is the Ant task for the site builder. The following functionality is
 * provided:
 * <ul>
 * <li>One common output directory is shared among all children.</li>
 * <li>One child is a tree traversal function. It processes the HTML files found
 * with the help of the templating engine &ndash; unless the templating is
 * disabled. All other files are copied into the target directory.</li>
 * <li>A sitemap file can be generated.</li>
 * <li>News from a directory can be translated into an RSS file..</li>
 * </ul>
 * 
 * <pre style="background:#eeeeee;">
 *   &lt;taskdef name="SiteBuilder"
 *            classname="org.extex.sitebuilder.ant.SiteBuilderTask" /&gt;
 * 
 *   &lt;SiteBuilder
 *     logLevel="<i>level</i>"
 *     target="<i>output/directory</i>" &gt;
 *     &lt;omit&gt;<i>file pattern</i>&lt;/omit&gt;
 *     &lt;lib&gt;<i>library</i>&lt;/lib&gt;
 *     &lt;Tree
 *        dir="<i>html/sources</i>"
 *        processHtml="<i>boolean value</i>"
 *        template="<i>html/template/resource</i>" /&gt;
 *     &lt;Sitemap
 *        output="<i>site/map/file</i>" 
 *        template="<i>template/file</i>" /&gt;
 *     &lt;News
 *        output="<i>news/file</i>" 
 *        max="<i>max value</i>"
 *        template="<i>template/file</i>" /&gt;
 *   &lt;/SiteBuilder&gt; </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SiteBuilderTask extends Task {

    /**
     * The field {@code builder} contains the instance of the site builder.
     * This is needed due to the limitation of Java not supporting multiple
     * inheritance.
     */
    private final SiteBuilder builder = new SiteBuilder();

    /**
     * The field {@code handler} contains the log handler.
     */
    private final Handler handler = new Handler() {

    @Override
        public void close() throws SecurityException {


        }

    @Override
        public void flush() {


        }

    @Override
        public void publish(LogRecord record) {

            Level level = record.getLevel();
            String message = record.getMessage();
            if (level == Level.SEVERE) {
                log(message, Project.MSG_ERR);
            } else if (level == Level.WARNING) {
                log(message, Project.MSG_WARN);
            } else if (level == Level.INFO) {
                log(message, Project.MSG_INFO);
            } else {
                log(message);
            }
        }

    };

    /**
     * The field {@code omitList} contains the omit container.
     */
    private List<OmitTag> omitList = new ArrayList<OmitTag>();

    /**
     * The field {@code libList} contains the lib container.
     */
    private List<LibTag> libList = new ArrayList<LibTag>();

    /**
     * Factory method for the omit element.
     * 
     * @return the omit element
     */
    public LibTag createLib() {

        LibTag tag = new LibTag();
        libList.add(tag);
        return tag;
    }

    /**
     * Factory method for the news element
     * 
     * @return the news element
     */
    public NewsTag createNews() {

        return new NewsTag(builder.createNewsBuilder());
    }

    /**
     * Factory method for the omit element.
     * 
     * @return the omit element
     */
    public OmitTag createOmit() {

        OmitTag omit = new OmitTag();
        omitList.add(omit);
        return omit;
    }

    /**
     * Factory method for the site map.
     * 
     * @return the site map
     */
    public SiteMapTag createSitemap() {

        return new SiteMapTag(builder.createSiteMap());
    }

    /**
     * Factory method for the tree builder.
     * 
     * @return the site base
     */
    public TreeTag createTree() {

        return new TreeTag(builder.createTreeBuilder());
    }

@Override
    public void execute() throws BuildException {

        Logger logger = builder.getLogger();
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);

        try {
            for (OmitTag ot : omitList) {
                ot.propagate(builder);
            }
            for (LibTag lt : libList) {
                lt.propagate(builder);
            }
            builder.run();
        } catch (Exception e) {
            throw new BuildException("", e);
        } finally {
            logger.removeHandler(handler);
        }
    }

    /**
     * Setter for the log level.
     * <p>
     * The argument string may consist of either a level name or an integer
     * value.
     * <p>
     * For example:
     * <ul>
     * <li>"SEVERE"
     * <li>"1000"
     * </ul>
     * 
     * @param level the log level
     */
    public void setLogLevel(String level) {

        builder.getLogger().setLevel(Level.parse(level.toUpperCase()));
    }

    /**
     * Setter for the output directory.
     * 
     * @param output the output directory to set
     */
    public void setOutput(File output) {

        builder.setTarget(output);
    }

}
