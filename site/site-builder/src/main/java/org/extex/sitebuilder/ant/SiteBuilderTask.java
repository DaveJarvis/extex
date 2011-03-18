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
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilderTask extends Task {

    /**
     * The field <tt>basedir</tt> contains the base directory.
     */
    private File basedir = new File("src/site/html");

    /**
     * The field <tt>omit</tt> contains the list of omitted files and
     * directories.
     */
    private String[] omit;

    /**
     * The field <tt>resources</tt> contains the resources directory.
     */
    private File resources = new File("src/site/resources");

    /**
     * The field <tt>output</tt> contains the output directory.
     */
    private File output = new File("target/site");

    /**
     * The field <tt>template</tt> contains the template file.
     */
    private String template = null;

    /**
     * The field <tt>handler</tt> contains the log handler.
     */
    private Handler handler = new Handler() {

        @Override
        public void close() throws SecurityException {

            //
        }

        @Override
        public void flush() {

            //
        }

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

        SiteBuilder builder = new SiteBuilder();
        builder.setBaseDir(basedir);
        builder.setResourceDir(resources);
        builder.setTemplate(template);
        builder.setTargetdir(output);
        builder.omit(omit);

        Logger logger = builder.getLogger();
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);

        try {
            builder.run();
        } catch (Exception e) {
            throw new BuildException("", e);
        }
    }

}
