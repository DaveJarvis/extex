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

package org.extex.sitebuilder.mojo;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.extex.sitebuilder.core.SiteBuilder;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilderMojo extends AbstractMojo {

    /**
     * The field <tt>basedir</tt> contains the ...
     * 
     * @parameter expression="${project.outputDirectory}/../src/site/html"
     */
    private File basedir;

    /**
     * The field <tt>omit</tt> contains the list of omitted files and
     * directories.
     * 
     * @parameter
     */
    private String[] omit;

    /**
     * The field <tt>resources</tt> contains the ...
     * 
     * @parameter expression="${project.outputDirectory}/../src/site/resources"
     */
    private File resources;

    /**
     * The field <tt>output</tt> contains the ...
     * 
     * @parameter expression="${project.outputDirectory}/site"
     */
    private File output;

    /**
     * The field <tt>template</tt> contains the ...
     * 
     * @parameter
     */
    private String template;

    /**
     * The field <tt>handler</tt> contains the ...
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
            Log l = getLog();
            if (level == Level.SEVERE) {
                l.error(record.getMessage());
            } else if (level == Level.WARNING) {
                l.warn(record.getMessage());
            } else if (level == Level.INFO) {
                l.info(record.getMessage());
            } else {
                l.debug(record.getMessage());
            }
        }

    };

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        SiteBuilder builder = new SiteBuilder();
        Logger logger = builder.getLogger();
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);

        try {
            builder.createSiteBase(basedir);
            builder.addResourceDir(resources);
            builder.setTemplate(template);
            builder.setTarget(output);
            builder.omit(omit);

            builder.run();
        } catch (Exception e) {
            throw new MojoExecutionException("", e);
        }
    }

}
