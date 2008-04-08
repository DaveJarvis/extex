/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.extex.exbib.core.io.StreamWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.bibio.BibPrinterFactory;
import org.extex.exbib.core.io.bibio.BibReader;
import org.extex.exbib.core.io.bibio.BibReader099Impl;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;

/**
 * Reformat a bibliography.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public final class Reformat extends AbstractMain {

    /**
     * The field <tt>PROGNAME</tt> contains the name of the program.
     */
    private static final String PROGNAME = "reformat";

    /**
     * The field <tt>VERSION</tt> contains the official version number.
     */
    public static final String VERSION = "0.1";

    /**
     * The field <tt>INCEPTION_YEAR</tt> contains the year the development has
     * been started. This is fixed to be 2002 and should not be altered.
     */
    private static final int INCEPTION_YEAR = 2002;

    /**
     * Run the command line.
     * 
     * @param argv the command line arguments
     * 
     * @return the exit code
     */
    public static int commandLine(String[] argv) {

        try {
            return new Reformat().processCommandLine(argv);
        } catch (RuntimeException e) {
            Logger logger = Logger.getLogger(Reformat.class.getName());
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LogFormatter());
            consoleHandler.setLevel(Level.WARNING);
            logger.addHandler(consoleHandler);
            logger.severe(e.toString());
            return EXIT_FAIL;
        }
    }

    /**
     * The main program.
     * 
     * @param argv list of command line parameters
     */
    public static void main(String[] argv) {

        System.exit(commandLine(argv));
    }

    /**
     * Creates a new object.
     */
    private Reformat() {

        super("reformat", "0.1", INCEPTION_YEAR);
    }

    /**
     * Run the command line.
     * 
     * @param argv the command line arguments
     * 
     * @return the exit code
     */
    private int processCommandLine(String[] argv) {

        Logger logger = Logger.getLogger(Reformat.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);

        String outfile = null;
        String file = null;

        for (int i = 0; i < argv.length; i++) {
            String a = argv[i];
            if (a.startsWith("-")) {
                if ("-help".startsWith(a)) {
                    // usage(System.err, PROGNAME);
                    return EXIT_FAIL;
                } else if ("-copying".startsWith(argv[i])) {
                    return logCopying(logger);
                } else if ("-version".startsWith(a)) {
                    logger.severe(VERSION + "\n");
                    return EXIT_FAIL;
                } else if ("-release".startsWith(a)) {
                    logger.severe(VERSION + "\n");
                    return EXIT_FAIL;
                    // } else if ("-logfile".startsWith(a) && (i < (argv.length
                    // - 1))) {
                    // logfile = argv[++i];
                } else if ("-outfile".startsWith(a) && (i < (argv.length - 1))) {
                    outfile = argv[++i];
                } else {
                    logBanner("unknown.option", a);
                }
            } else if (file == null) {
                file = a;
            } else {
                logger.severe(PROGNAME + ": Need exactly one file argument.\n"
                        + "Try `reformat --help' for more information.");
                return EXIT_FAIL;
            }
        }

        if (file == null) {
            logger.severe(PROGNAME + ": Need exactly one file argument.\n"
                    + "Try `reformat --help' for more information.");
            return EXIT_FAIL;
        }

        Writer writer = null;

        try {
            if (outfile == null) {
                writer = new StreamWriter(System.out, null);
                logger.info("The output file: stdout \n");
            } else if (outfile.equals("")) {
                logger.info("The output is discarted.\n");
                writer = new NullWriter(null);
            } else if (outfile.equals("-")) {
                logger.info("The output is sent to stdout.\n");
                writer = new StreamWriter(System.out, null);
            } else {
                logger.info("The output file: " + outfile + "\n");
                writer = new StreamWriter(outfile, null);
            }
        } catch (FileNotFoundException e) {
            logger.severe("The output file could not be opened: " + outfile);
        } catch (UnsupportedEncodingException e) {
            logger.severe("Unsupported Encoding for " + outfile);
        }

        DB db = null;

        try {
            BibReader reader = new BibReader099Impl();
            db = new DBImpl();
            reader.open(file);
            db.load(file, null, null);

            Configuration cfg =
                    ConfigurationFactory.newInstance("config/exbib.xml")
                        .getConfiguration("BibPrinter", "xml");

            new BibPrinterFactory(cfg).newInstance(writer, null).print(db);
        } catch (Exception e) {
            logger.severe("*** " + e.toString());
            return EXIT_FAIL;
        }
        return EXIT_OK;
    }

}
