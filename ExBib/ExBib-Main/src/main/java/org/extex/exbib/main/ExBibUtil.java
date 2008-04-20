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
import java.util.ArrayList;
import java.util.List;
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
import org.extex.exbib.main.cli.NoArgOption;
import org.extex.exbib.main.cli.StringOption;
import org.extex.exbib.main.cli.exception.UnknownOptionCliException;
import org.extex.exbib.main.cli.exception.UnusedArgumentCliException;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;

/**
 * ExBibUtil a bibliography.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public final class ExBibUtil extends AbstractMain {

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
            return new ExBibUtil().processCommandLine(argv);
        } catch (RuntimeException e) {
            Logger logger = Logger.getLogger(ExBibUtil.class.getName());
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
     * The main program. The command line parameters are evaluated and the
     * appropriate actions are performed. Finally the program exits with an exit
     * code which signals success or failure.
     * 
     * @param argv list of command line parameters
     */
    public static void main(String[] argv) {

        System.exit(commandLine(argv));
    }

    /**
     * The field <tt>files</tt> contains the name of the files to start with.
     */
    private List<String> files = new ArrayList<String>();

    /**
     * The field <tt>consoleHandler</tt> contains the console handler.
     */
    private Handler consoleHandler;

    /**
     * The field <tt>outfile</tt> contains the output file or
     * <code>null</code> for stdout.
     */
    private String outfile = null;

    /**
     * Creates a new object.
     */
    private ExBibUtil() {

        super(PROGNAME, VERSION, INCEPTION_YEAR);

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        getLogger().addHandler(consoleHandler);

        declareOptions();
    }

    /**
     * Declare the list of command line options.
     * 
     */
    protected void declareOptions() {

        declare(null, new NoArgOption(null) {

            @Override
            protected int run(String arg) throws UnknownOptionCliException {

                if (arg.startsWith("-")) {
                    throw new UnknownOptionCliException(arg);
                }
                files.add(arg);
                return EXIT_CONTINUE;
            }

            @Override
            public int run(String a, String arg, List<String> args)
                    throws UnusedArgumentCliException,
                        UnknownOptionCliException {

                if (a.startsWith("-")) {
                    throw new UnknownOptionCliException(a);
                }
                throw new UnusedArgumentCliException(a);
            }

        });
        declare("", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                return EXIT_CONTINUE;
            }

        });
        option(new StringOption("opt.file") {

            @Override
            protected int run(String name, String arg) {

                files.add(arg);
                return EXIT_CONTINUE;
            }

        }, "-");
        option(new NoArgOption("opt.copying") {

            @Override
            protected int run(String name) {

                return logCopying(getLogger());
            }

        }, "-copying");
        // option(new NoArgOption("opt.debug") {
        //
        // @Override
        // protected int run(String name) {
        //
        // setDebug(true);
        // return EXIT_CONTINUE;
        // }
        //
        // }, "-debug");
        option(new NoArgOption("opt.help") {

            @Override
            protected int run(String arg) {

                return logBanner("usage", getProgramName());
            }

        }, "-help", "-?");
        // option(new NumberOption("opt.min.crossref") {
        //
        // @Override
        // protected int run(String name, int arg) {
        //
        // setMinCrossrefs(arg);
        // return EXIT_CONTINUE;
        // }
        //
        // }, "-min.crossrefs", "-min-crossrefs", "-min_crossrefs");
        option(new StringOption("opt.output") {

            @Override
            protected int run(String name, String arg) {

                // setOutfile(arg);
                return EXIT_CONTINUE;
            }

        }, "-outfile", "-output");
        option(new StringOption("opt.progname") {

            @Override
            protected int run(String name, String arg) {

                setProgramName(arg);
                return EXIT_CONTINUE;
            }

        }, "-progname", "-program.name", "-program-name");
        // option(new NoArgOption("opt.quiet") {
        //
        // @Override
        // protected int run(String arg) {
        //
        // consoleHandler.setLevel(Level.SEVERE);
        // return EXIT_CONTINUE;
        // }
        //
        // }, "-quiet");
        option(new NoArgOption("opt.release") {

            @Override
            protected int run(String arg) {

                getLogger().severe(VERSION + "\n");
                return EXIT_FAIL;
            }

        }, "-release");
        // option(new BooleanOption("opt.trace") {
        //
        // @Override
        // protected int run(String arg, boolean value) {
        //
        // setTrace(value);
        // return EXIT_CONTINUE;
        // }
        //
        // }, "-trace");
        // option(new NoArgOption("opt.terse") {
        //
        // @Override
        // protected int run(String arg) {
        //
        // consoleHandler.setLevel(Level.SEVERE);
        // return EXIT_CONTINUE;
        // }
        //
        // }, "-terse");
        // option(new NoArgOption("opt.verbose") {
        //
        // @Override
        // protected int run(String arg) {
        //
        // consoleHandler.setLevel(Level.INFO);
        // return EXIT_CONTINUE;
        // }
        //
        // }, "-verbose");
        option(new NoArgOption("opt.version") {

            @Override
            protected int run(String arg) {

                return logBanner(true);
            }

        }, "-version");
    }

    /**
     * Run the command line.
     * 
     * @param argv the command line arguments
     * 
     * @return the exit code
     */
    public int processCommandLine(String[] argv) {

        Writer writer = null;

        try {
            if (outfile == null) {
                writer = new StreamWriter(System.out, null);
                info("The output file: stdout \n");
            } else if (outfile.equals("")) {
                info("The output is discarted.\n");
                writer = new NullWriter(null);
            } else if (outfile.equals("-")) {
                info("The output is sent to stdout.\n");
                writer = new StreamWriter(System.out, null);
            } else {
                info("The output file: " + outfile + "\n");
                writer = new StreamWriter(outfile, null);
            }
        } catch (FileNotFoundException e) {
            log("The output file could not be opened: " + outfile);
        } catch (UnsupportedEncodingException e) {
            log("Unsupported Encoding for " + outfile);
        }

        DB db = null;

        try {
            BibReader reader = new BibReader099Impl();
            db = new DBImpl();
            for (String file : files) {
                reader.open(file);
                db.load(file, null, null);
            }

            Configuration cfg =
                    ConfigurationFactory.newInstance("config/exbib.xml")
                        .getConfiguration("BibPrinter", "xml");

            new BibPrinterFactory(cfg).newInstance(writer, null).print(db);
        } catch (Exception e) {
            log("*** " + e.toString());
            return EXIT_FAIL;
        }
        return EXIT_OK;
    }

}
