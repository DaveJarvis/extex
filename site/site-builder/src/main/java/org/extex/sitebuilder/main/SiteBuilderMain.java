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

package org.extex.sitebuilder.main;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.extex.sitebuilder.core.SiteBuilder;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SiteBuilderMain {

    /**
     * The field <tt>handler</tt> contains the log handler.
     */
    private static final Handler handler = new Handler() {

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

            System.err.print(record.getLevel().toString());
            System.err.print(" ");
            System.err.println(record.getMessage());
        }

    };

    /**
     * This is the command line interface to the site builder. This method calls
     * {@link System.exit()} to signal the result via the exit code.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(run(args));
    }

    /**
     * This is the command line interface to the site builder.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code; i.e. 0 for success and something else for failure
     */
    public static int run(String[] args) {

        SiteBuilder siteBuilder = new SiteBuilder();
        siteBuilder.setSiteMap(new File("target/site/sitemap.html"));
        siteBuilder.omit("CVS", ".svn");
        siteBuilder.lib("src/site/velocity/macros.vm");
        // siteBuilder.setBaseDir(new File("../www/src/site/html"));
        // siteBuilder.setResourceDir(new File("../www/src/site/resources"));

        for (int i = 0; i < args.length; i++) {
            if ("".equals(args[i])) {

            } else if (!args[i].startsWith("-")) {

            } else if ("-baseDirectory".startsWith(args[i])) {
                siteBuilder.setBaseDir(new File(args[++i]));
            } else if ("-library".startsWith(args[i])) {
                siteBuilder.lib(args[++i]);
            } else if ("-outputDirectory".startsWith(args[i])) {
                siteBuilder.setTargetdir(new File(args[++i]));
            } else if ("-omit".startsWith(args[i])) {
                siteBuilder.omit(args[++i]);
            } else if ("-resourceDirectory".startsWith(args[i])) {
                siteBuilder.setResourceDir(new File(args[++i]));
            } else if ("-template".startsWith(args[i])) {
                siteBuilder.setTemplate(args[++i]);
            } else {
                System.err.println("Arguments:");
                System.err.println("\t-baseDirectory [dir]");
                System.err.println("\t-outputDirectory [dir]");
                System.err.println("\t-resourceDirectory [dir]");
                System.err.println("\t-template [template file]");
                return 0;
            }
        }

        Logger.getLogger("org.apache.velocity").setLevel(Level.WARNING);
        Logger logger = siteBuilder.getLogger();
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
        for (Handler h : logger.getHandlers()) {
            h.setLevel(Level.OFF);
        }
        logger.addHandler(handler);
        try {
            siteBuilder.run();
        } catch (Exception e) {
            logger.throwing(SiteBuilder.class.getName(), "run", e);
            return -1;
        }
        return 0;
    }

}
