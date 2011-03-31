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

import org.extex.sitebuilder.core.NewsBuilder;

/**
 * This is the command line interface to the news builder.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class NewsBuilderMain {

    /**
     * The constant <tt>HELP_INFO</tt> contains the help message.
     */
    protected static final String HELP_INFO = "Arguments:\n" //
            + "\t-baseDirectory [base directory]\n" //
            + "\t-max [maximum number of items]\n" //
            + "\t-outputFile [file]\n" //
            + "\t-template [template file]\n"//
            + "\t[base directory]\n";

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

        int exit = -1;
        try {
            exit = run(args);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        System.exit(exit);
    }

    /**
     * Parse and process the command line arguments.
     * 
     * @param args the command line arguments
     * @param builder the news builder
     * @param logger the logger for messages
     * 
     * @return the exit code
     */
    private static int processCommandLine(String[] args, NewsBuilder builder,
            Logger logger) {

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                arg = arg.substring(1);
            }
            if (!arg.startsWith("-")) {
                builder.setBaseDirectory(new File(arg));
            } else if ("-baseDirectory".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                builder.setBaseDirectory(new File(args[++i]));
            } else if ("-max".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                try {
                    builder.setMax(Integer.parseInt(args[++i]));
                } catch (NumberFormatException e) {
                    logger.severe("Invalid argument for " + arg + ": "
                            + args[i]);
                    return -1;
                }
            } else if ("-outputFile".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                builder.setOutput(new File(args[++i]));
            } else if ("-template".startsWith(arg)) {
                if (i >= args.length - 1) {
                    logger.severe("Missing argument for " + arg);
                    return -1;
                }
                builder.setTemplate(args[++i]);
            } else if ("-help".startsWith(arg)) {
                logger.info(HELP_INFO);
                return -111;
            } else {
                logger.severe("Unknown option: " + arg);
                return -2;
            }
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

        NewsBuilder builder = new NewsBuilder();
        Logger logger = builder.getLogger();
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
        logger.addHandler(HANDLER);

        try {
            int exit = processCommandLine(args, builder, logger);
            if (exit != 0) {
                return exit;
            }
            builder.generate();
        } catch (Exception e) {
            logger.throwing("NewsBuilderMain", "run", e);
            return -1;
        } finally {
            logger.removeHandler(HANDLER);
            HANDLER.flush();
        }
        return 0;
    }

}
