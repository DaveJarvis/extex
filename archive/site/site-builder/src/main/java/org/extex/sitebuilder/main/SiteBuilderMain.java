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
import java.io.FileNotFoundException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.extex.sitebuilder.core.SiteBuilder;
import org.extex.sitebuilder.core.SiteMapBuilder;

/**
 * This is the command line interface to the site builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class SiteBuilderMain {

    public static final String HELP_INFO = "Arguments:\n"
            + "\t-baseDirectory [dir]\n" + "\t-outputDirectory [dir]\n"
            + "\t-resourceDirectory [dir]\n" + "\t-template [template file]";

    /**
     * The field <tt>HANDLER</tt> contains the log handler.
     */
    private static final Handler HANDLER = new Handler() {

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#close()
         */
        @Override
        public void close() throws SecurityException {

            flush();
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#flush()
         */
        @Override
        public void flush() {

            System.err.flush();
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
         */
        @Override
        public void publish(LogRecord record) {

            Level level = record.getLevel();
            if (level.equals(Level.SEVERE)) {
                System.err.print("*** ");
            } else if (!level.equals(Level.INFO)) {
                System.err.print("--- ");
            }
            System.err.println(record.getMessage());
        }

    };

    /**
     * This is the command line interface to the site builder. This method calls
     * {@link System#exit(int)} to signal the result via the exit code.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(run(args));
    }

    /**
     * Parse and process the command line arguments.
     * 
     * @param args the command line arguments
     * @param siteBuilder the site builder
     * @param logger the logger for messages
     * 
     * @return the exit code
     */
    public static int processCommandLine(String[] args,
            SiteBuilder siteBuilder, Logger logger) {

        SiteMapBuilder siteMap = siteBuilder.createSiteMap();
        siteMap.setOutput(new File("target/test-site/sitemap.html"));
        siteBuilder.omit("CVS");
        siteBuilder.omit(".svn");
        // siteBuilder.lib("src/site/velocity/macros.vm");

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                arg = arg.substring(1);
            }
            if ("".equals(arg)) {

            } else if (!arg.startsWith("-")) {

            } else if ("-baseDirectory".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                try {
                    siteBuilder.createTreeBuilder(args[++i]);
                } catch (FileNotFoundException e) {
                    System.err.println(e.toString());
                    return (-1);
                }
            } else if ("-library".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                siteBuilder.lib(args[++i]);
            } else if ("-outputDirectory".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                File targetDir = new File(args[++i]);
                siteBuilder.setTarget(targetDir);
                siteMap.setOutput(new File(targetDir, "sitemap.html"));
            } else if ("-omit".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                siteBuilder.omit(args[++i]);
            } else if ("-sitemap".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                siteMap.setOutput(new File(args[++i]));
            } else if ("-help".startsWith(arg)) {
                logger.info(HELP_INFO);
                return -111;
            } else {
                logger.severe("Unknown option: " + arg);
                return -2;
            }
        }

        Logger.getLogger("org.apache.velocity").setLevel(Level.WARNING);
        try {
            siteBuilder.run();
        } catch (Exception e) {
            logger.throwing(SiteBuilder.class.getName(), "run", e);
            return -1;
        }
        return 0;
    }

    /**
     * This is the command line interface to the site builder.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code; i.e. 0 for success and something else for failure
     */
    public static int run(String[] args) {

        Logger.getLogger("org.apache.velocity").setLevel(Level.WARNING);

        SiteBuilder siteBuilder = new SiteBuilder();
        Logger logger = siteBuilder.getLogger();
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
        logger.addHandler(HANDLER);

        try {
            return processCommandLine(args, siteBuilder, logger);
        } finally {
            logger.removeHandler(HANDLER);
            HANDLER.flush();
        }
    }

}
