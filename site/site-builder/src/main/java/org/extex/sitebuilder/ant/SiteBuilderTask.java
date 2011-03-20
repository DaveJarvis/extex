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
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.extex.sitebuilder.core.SiteBuilder;

/**
 * This is the Ant task for the site builder.
 * 
 * 
 * TODO: The following does not work yet
 * 
 * <pre style="background:#eeeeee;">
 *   &lt;taskdef name="SiteBuilder"
 *            classname="org.extex.sitebuilder.ant.SiteBuilderTask" /&gt;
 * 
 *   &lt;SiteBuilder
 *      target="<i>output/directory</i>" &gt;
 *     &lt;SiteHTML
 *        sources="<i>html/sources</i>"
 *        template="<i>html/template/resource</i>" /&gt;
 *     &lt;SiteResources
 *        dir="<i>resources/directory</i>" /&gt;
 *     &lt;SiteMap
 *        output="<i>site/map/file</i>" /&gt;
 *     &lt;SiteNews
 *        output="<i>news/file</i>" /&gt;
 *   &lt;/SiteBuilder&gt; </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilderTask extends Task {

    /**
     * The field <tt>builder</tt> contains the instance of the site builder.
     * This is needed due to the limitation of Java not supporting multiple
     * inheritance.
     */
    private final SiteBuilder builder = new SiteBuilder();

    /**
     * The field <tt>handler</tt> contains the log handler.
     */
    private final Handler handler = new Handler() {

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#close()
         */
        @Override
        public void close() throws SecurityException {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#flush()
         */
        @Override
        public void flush() {

            //
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
         */
        @Override
        public void publish(LogRecord record) {

            Level level = record.getLevel();

            if (level == Level.SEVERE) {
                log(record.getMessage(), Project.MSG_ERR);
            } else if (level == Level.WARNING) {
                log(record.getMessage(), Project.MSG_WARN);
            } else if (level == Level.INFO) {
                log(record.getMessage(), Project.MSG_INFO);
            } else {
                log(record.getMessage());
            }
        }

    };

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        Logger logger = builder.getLogger();
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);

        try {
            builder.run();
        } catch (Exception e) {
            throw new BuildException("", e);
        } finally {
            logger.removeHandler(handler);
        }
    }

    /**
     * Setter for basedir.
     * 
     * @param basedir the basedir to set
     */
    public void setBasedir(File basedir) {

        builder.setBaseDir(basedir);
    }

    /**
     * Setter for omit.
     * 
     * @param omit the omit to set
     */
    public void setOmit(String[] omit) {

        builder.omit(omit);
    }

    /**
     * Setter for output.
     * 
     * @param output the output to set
     */
    public void setOutput(File output) {

        builder.setTargetdir(output);
    }

    /**
     * Setter for resources.
     * 
     * @param resources the resources to set
     */
    public void setResources(File resources) {

        builder.setResourceDir(resources);
    }

    /**
     * Setter for template.
     * 
     * @param template the template to set
     */
    public void setTemplate(String template) {

        builder.setTemplate(template);
    }

}
