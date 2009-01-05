/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.cli.BooleanOption;
import org.extex.cli.CLI;
import org.extex.cli.NoArgOption;
import org.extex.cli.StringOption;
import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.NonNumericArgumentCliException;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.cli.exception.UnusedArgumentCliException;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This is the main program for the BST to Groovy compiler.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Main extends CLI {

    /**
     * The field <tt>PROGNAME</tt> contains the program name.
     */
    protected static final String PROGNAME = "bst2groovy";

    /**
     * The field <tt>VERSION</tt> contains the version number.
     */
    protected static final String VERSION = "1.0";

    /**
     * Process the command line options and return the exit code.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code
     */
    public static int commandLine(String... args) {

        try {
            return new Main().run(args);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(Main.class.getName());
            String message = e.getLocalizedMessage();
            logger.severe((message == null ? e.toString() : message) + "\n");
            return CLI.EXIT_FAIL;
        } finally {
            Logger logger = Logger.getLogger(Main.class.getName());
            for (Handler h : logger.getHandlers()) {
                h.close();
                logger.removeHandler(h);
            }
        }
    }

    /**
     * This is the main program entry point.
     * 
     * @param args the command line arguments
     */
    public static void main(String... args) {

        System.exit(commandLine(args));
    }

    /**
     * Create a logger.
     * 
     * @return the logger
     */
    protected static Logger makeLogger() {

        Logger log = Logger.getLogger(Main.class.getName());
        log.setUseParentHandlers(false);
        log.setLevel(Level.SEVERE);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new LogFormatter());
        log.addHandler(handler);
        return log;
    }

    /**
     * The field <tt>config</tt> contains the configuration for the finder.
     */
    private String config = "config/path/exbibFinder";

    /**
     * The field <tt>out</tt> contains the output file name or <code>null</code>
     * for stdout.
     */
    private String out = null;

    /**
     * The field <tt>in</tt> contains the input file name or null for stdin..
     */
    private String in = null;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger = makeLogger();

    /**
     * The field <tt>traceFinder</tt> contains the indicator to trace the
     * finder.
     */
    private boolean traceFinder = false;

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * The field <tt>banner</tt> contains the indicator that the banner has
     * already been shown.
     */
    private boolean banner = false;

    /**
     * The field <tt>optimizing</tt> contains the optimizing flag.
     */
    private boolean optimizing = true;

    /**
     * The field <tt>verbose</tt> contains the indicator for the verbosity.
     */
    private boolean verbose = false;

    /**
     * The field <tt>COPYING_RESOURCE</tt> contains the name of the resource for
     * the copyright file (in the jar).
     */
    private static final String COPYING_RESOURCE =
            "org/extex/exbib/bst2groovy/COPYING";

    /**
     * Creates a new object.
     * 
     */
    public Main() {

        this.bundle = ResourceBundle.getBundle(getClass().getName());

        declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String arg) throws UnknownOptionCliException {

                if (arg.startsWith("-")) {
                    throw new UnknownOptionCliException(arg);
                }
                in = arg;
                return CLI.EXIT_CONTINUE;
            }
        });
        declareOption("", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                return EXIT_CONTINUE;
            }
        });
        option("-", "--", new StringOption("opt.1.file") {

            @Override
            protected int run(String name, String arg) {

                in = arg;
                return EXIT_CONTINUE;
            }
        });
        option("-c", "--configuration", new StringOption("opt.config") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                config = arg;
                return CLI.EXIT_CONTINUE;
            }
        });
        option(null, "--copying", new NoArgOption("opt.copying") {

            @Override
            protected int run(String name) {

                InputStream is =
                        getClass().getClassLoader().getResourceAsStream(
                            COPYING_RESOURCE);

                if (is == null) {
                    logger.severe("--copying " + COPYING_RESOURCE);
                    return EXIT_FAIL;
                }

                LineNumberReader r = null;
                StringBuilder sb = new StringBuilder();
                try {
                    try {
                        r = new LineNumberReader(new InputStreamReader(is));
                        for (String s = r.readLine(); s != null; s =
                                r.readLine()) {
                            sb.append(s);
                            sb.append('\n');
                        }
                    } finally {
                        if (r != null) {
                            r.close();
                        }
                        is.close();
                    }
                } catch (IOException e) {
                    // shit happens
                }
                logger.severe(sb.toString());
                return EXIT_FAIL;
            }
        });
        option("-h", "--help", new NoArgOption("opt.help") {

            @Override
            protected int run(String arg) {

                logBanner(Level.WARNING);
                logger.severe(describeOptions(getBundle(), //
                    "usage.start", "usage.end", PROGNAME));
                return EXIT_FAIL;
            }
        }, "-?");
        option("-o", "--output-file", new StringOption("opt.output") {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                out = arg;
                return CLI.EXIT_CONTINUE;
            }
        });
        option("-O", "--optimizing", new BooleanOption("opt.optimizing") {

            @Override
            protected int run(String a, boolean arg) {

                optimizing = arg;
                return EXIT_CONTINUE;
            }
        });
        option("-v", "--verbose", new NoArgOption("opt.verbose") {

            @Override
            protected int run(String arg) {

                traceFinder = true;
                verbose = true;
                return EXIT_CONTINUE;
            }
        });
        option(null, "--version", new NoArgOption("opt.version") {

            @Override
            protected int run(String arg) {

                return logBanner(Level.SEVERE);
            }
        });
    }

    /**
     * Getter for bundle.
     * 
     * @return the bundle
     */
    public ResourceBundle getBundle() {

        return bundle;
    }

    /**
     * Log a severe message.
     * 
     * @param level the log level
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the exit code <code>1</code>
     */
    private int log(Level level, String tag, Object... args) {

        try {
            logger.log(level, //
                MessageFormat.format(bundle.getString(tag), args));
        } catch (MissingResourceException e) {
            logger.severe("???" + tag + "???");
        }
        return EXIT_FAIL;
    }

    /**
     * Write the banner to the logger.
     * 
     * @param level the log level
     * 
     * @return the exit code <code>EXIT_FAILURE</code>
     */
    protected int logBanner(Level level) {

        if (banner) {
            return EXIT_FAIL;
        }
        banner = true;
        return log(level, "version", PROGNAME, VERSION);
    }

    /**
     * Do whatever might be necessary.
     * 
     * @param properties the properties
     * 
     * @return the exit code
     */
    private int run(Properties properties) {

        if (verbose) {
            logBanner(Level.SEVERE);
        }
        try {
            Bst2Groovy bst2Groovy = new Bst2Groovy();
            bst2Groovy.setOptimizing(optimizing);
            ResourceFinder finder =
                    new ResourceFinderFactory().createResourceFinder(
                        ConfigurationFactory.newInstance(config), logger, //
                        properties, null);
            finder.enableTracing(traceFinder);
            bst2Groovy.setResourceFinder(finder);
            bst2Groovy.addBibliographyStyle(in == null ? "" : in);
            bst2Groovy.load();
            OutputStreamWriter w =
                    new OutputStreamWriter(out == null || "-".equals(out)
                            ? System.out
                            : new FileOutputStream(out));
            bst2Groovy.write(w);
            w.flush();
            w.close();
        } catch (Exception e) {
            logger.severe(e.getLocalizedMessage() + "\n");
            return CLI.EXIT_FAIL;
        }
        return CLI.EXIT_OK;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.cli.CLI#run(java.lang.String[])
     */
    @Override
    public int run(String[] argv)
            throws UnknownOptionCliException,
                MissingArgumentCliException,
                NonNumericArgumentCliException,
                UnusedArgumentCliException {

        int exitCode = super.run(argv);
        return exitCode != CLI.EXIT_FAIL
                ? run(System.getProperties())
                : exitCode;
    }
}
