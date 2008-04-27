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

package org.extex.exbib.main.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.main.ExBib;
import org.extex.exbib.main.cli.CLI;
import org.extex.exbib.main.cli.NoArgOption;
import org.extex.exbib.main.cli.Option;
import org.extex.exbib.main.cli.StringOption;
import org.extex.exbib.main.cli.exception.MissingArgumentCliException;
import org.extex.exbib.main.cli.exception.NonNumericArgumentCliException;
import org.extex.exbib.main.cli.exception.UnknownOptionCliException;
import org.extex.exbib.main.cli.exception.UnusedArgumentCliException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This is an abstract base class for main programs.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractMain extends CLI {

    /**
     * The field <tt>COPYING_RESOURCE</tt> contains the name of the resource
     * for the copyright file (in the jar).
     */
    private static final String COPYING_RESOURCE =
            "org/extex/exbib/main/COPYING";

    /**
     * The field <tt>programName</tt> contains the name of the program.
     */
    private String programName = getClass().getName();

    /**
     * The field <tt>banner</tt> contains the indicator that the banner has
     * already been printed.
     */
    private boolean banner = false;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * The field <tt>version</tt> contains the version number.
     */
    private String version;

    /**
     * The field <tt>inceptionYear</tt> contains the first year of
     * development.
     */
    private int inceptionYear;

    /**
     * The field <tt>consoleHandler</tt> contains the console handler for log
     * messages. It can be used to modify the log level for the console.
     */
    private Handler consoleHandler;

    /**
     * The field <tt>logfile</tt> contains the name of the log file.
     */
    private String logfile = null;

    /**
     * Creates a new object.
     * 
     * @param programName the name of the program
     * @param version the version
     * @param year the inception year
     */
    public AbstractMain(String programName, String version, int year) {

        super();
        this.programName = programName;
        this.version = version;
        this.inceptionYear = year;

        bundle = ResourceBundle.getBundle(getClass().getName());

        logger = Logger.getLogger(getClass().getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        getLogger().addHandler(consoleHandler);
    }

    /**
     * Attach a handler to the logger to direct messages to a log file.
     * 
     * @param log the base name of the file
     * @param extension the extension
     * 
     * @throws IOException in case of an I/O error
     */
    protected void attachFileLogger(String log, String extension)
            throws IOException {

        if (logfile == null && log != null && !log.equals("")
                && !log.equals("-")) {
            logfile = log + extension;
        }
        if (logfile != null && !logfile.equals("")) {
            Handler fileHandler = new FileHandler(logfile);
            fileHandler.setFormatter(new LogFormatter());
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        }
    }

    /**
     * Close the instance and release the logger.
     */
    public void close() {

        if (logger == null) {
            return;
        }
        for (Handler h : logger.getHandlers()) {
            h.close();
            logger.removeHandler(h);
        }
        logger = null;
        consoleHandler = null;
    }

    /**
     * Declare the list of command line options.
     */
    protected void declareCommonOptions() {

        declareOption(null, new NoArgOption(null) {

            @Override
            protected int run(String arg) throws UnknownOptionCliException {

                if (arg.startsWith("-")) {
                    throw new UnknownOptionCliException(arg);
                }
                return setFile(arg);
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
        declareOption("", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                return setFile("");
            }
        });
        option("-", "--", new StringOption("opt.1.file") {

            @Override
            protected int run(String name, String arg) {

                return setFile(arg);
            }
        });
        option(null, "--availableCharsets", new NoArgOption(
            "opt.available.charsets") {

            @Override
            protected int run(String arg) {

                for (String s : Charset.availableCharsets().keySet()) {
                    logger.severe(s + "\n");
                }

                return EXIT_FAIL;
            }
        });
        option(null, "--copying", new NoArgOption("opt.copying") {

            @Override
            protected int run(String name) {

                return logCopying();
            }
        });
        option("-h", "--help", new NoArgOption("opt.help") {

            @Override
            protected int run(String arg) {

                logBanner();
                getLogger().severe(describeOptions(getBundle(), //
                    "usage.start", "usage.end", getProgramName()));
                return EXIT_FAIL;
            }
        }, "-?");
        option("-l", "--logfile", new StringOption("opt.logfile") {

            @Override
            protected int run(String name, String arg) {

                setLogfile(arg);
                return EXIT_CONTINUE;
            }
        });
        option("-L", "--language", new StringOption("opt.language") {

            @Override
            protected int run(String name, String arg) {

                Locale.setDefault(new Locale(arg));
                setBundle(ResourceBundle.getBundle(ExBib.class.getName()));
                return EXIT_CONTINUE;
            }
        });
        option("-o", "--output", new StringOption("opt.output") {

            @Override
            protected int run(String name, String arg) {

                setOutfile(arg);
                return EXIT_CONTINUE;
            }
        }, "--outfile");
        option("-p", "--progname", new StringOption("opt.progname") {

            @Override
            protected int run(String name, String arg) {

                setProgramName(arg);
                return EXIT_CONTINUE;
            }
        }, "--program.name", "--program-name");
        option("-q", "--quiet", new NoArgOption("opt.quiet") {

            @Override
            protected int run(String arg) {

                consoleHandler.setLevel(Level.SEVERE);
                return EXIT_CONTINUE;
            }
        }, "--terse");
        option(null, "--release", new NoArgOption("opt.release") {

            @Override
            protected int run(String arg) {

                getLogger().severe(version + "\n");
                return EXIT_FAIL;
            }
        }, "--release");
        option("-v", "--verbose", new NoArgOption("opt.verbose") {

            @Override
            protected int run(String arg) {

                consoleHandler.setLevel(Level.INFO);
                return EXIT_CONTINUE;
            }
        });
        option(null, "--version", new NoArgOption("opt.version") {

            @Override
            protected int run(String arg) {

                return logBannerCopyright();
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
     * Getter for the log file.
     * 
     * @return the log file
     */
    public String getLogfile() {

        return logfile;
    }

    /**
     * Getter for logger.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        return logger;
    }

    /**
     * Getter for the program name.
     * 
     * @return the program name
     */
    public String getProgramName() {

        return programName;
    }

    /**
     * Log an info message.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the exit code <code>1</code>
     */
    protected int info(String tag, Object... args) {

        try {
            logger.info(MessageFormat.format(getBundle().getString(tag), args));
        } catch (MissingResourceException e) {
            logger.severe(MessageFormat.format(getBundle().getString(
                "missing.tag"), tag));
        }
        return EXIT_FAIL;
    }

    /**
     * Log a severe message.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the exit code <code>1</code>
     */
    protected int log(String tag, Object... args) {

        try {
            logger.severe(MessageFormat
                .format(getBundle().getString(tag), args));
        } catch (MissingResourceException e) {
            logger.severe(MessageFormat.format(getBundle().getString(
                "missing.tag"), tag));
        }
        return EXIT_FAIL;
    }

    /**
     * Write the banner to the logger. The used log level is warning.
     * 
     * @return the exit code <code>EXIT_FAILURE</code>
     */
    protected int logBanner() {

        if (banner) {
            return EXIT_FAIL;
        }
        banner = true;

        logger.warning(MessageFormat.format(getBundle().getString("version"),
            getProgramName(), version));
        return EXIT_FAIL;
    }

    /**
     * Write a message to the logger. It is preceded by the banner if the banner
     * has not been shown before.
     * 
     * @param tag the resource tag of the message pattern
     * 
     * @return the exit code <code>1</code>
     */
    protected int logBanner(String tag) {

        logBanner();
        return log(tag, getProgramName());
    }

    /**
     * Write a message to the logger. It is preceded by the banner if the banner
     * has not been shown before.
     * 
     * @param tag the resource tag of the message pattern
     * @param arg the argument
     * 
     * @return the exit code <code>1</code>
     */
    protected int logBanner(String tag, String arg) {

        logBanner();
        return log(tag, getProgramName(), arg);
    }

    /**
     * Write a message to the logger. It is preceded by the banner if the banner
     * has not been shown before.
     * 
     * @param tag the resource tag of the message pattern
     * @param arg1 the argument
     * @param arg2 the second argument
     * 
     * @return the exit code <code>1</code>
     */
    protected int logBanner(String tag, String arg1, String arg2) {

        logBanner();
        return log(tag, getProgramName(), arg1, arg2);
    }

    /**
     * Write the banner and the copyright to the logger. The used log level is
     * warning.
     * 
     * @return the exit code <code>EXIT_FAIL</code>
     */
    protected int logBannerCopyright() {

        if (banner) {
            return EXIT_FAIL;
        }
        banner = true;

        logger.warning(MessageFormat.format(getBundle().getString("version"),
            getProgramName(), version));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String copyrightYear =
                (year <= inceptionYear
                        ? Integer.toString(inceptionYear)
                        : Integer.toString(inceptionYear) + "-"
                                + Integer.toString(year));
        logger.severe(MessageFormat.format(getBundle().getString("copyright"),
            getProgramName(), copyrightYear));
        return EXIT_FAIL;
    }

    /**
     * Show the copying information (license) which is sought on the classpath.
     * 
     * @return the exit code EXIT_FAILURE
     */
    protected int logCopying() {

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
            r = new LineNumberReader(new InputStreamReader(is));
            for (String s = r.readLine(); s != null; s = r.readLine()) {
                sb.append(s);
                sb.append('\n');
            }
        } catch (IOException e) {
            // shit happens
        } finally {
            try {
                if (r != null) {
                    r.close();
                } else {
                    is.close();
                }
            } catch (IOException e) {
                // finally ignore it
            }
        }
        logger.severe(sb.toString());

        return EXIT_FAIL;
    }

    /**
     * Log an exception.
     * 
     * @param e the exception which has lead to the error
     * @param tag the resource tag for the format pattern
     * @param debug indicator whether or not to produce a printed stack trace
     * 
     * @return the exit code <code>1</code>
     */
    protected int logException(Throwable e, String tag, boolean debug) {

        logBanner(tag, e.getLocalizedMessage());

        if (debug) {
            logger.throwing("", "", e);
        }

        return EXIT_FAIL;
    }

    /**
     * Declare an option for the argument given and one with a hyphen prefixed
     * for each name given.
     * 
     * @param shortcut the character to be used as shortcut or <code>null</code>
     *        for none
     * @param name the name of the option
     * @param opt the option
     * @param aliases the list of alias names
     */
    protected void option(String shortcut, String name, Option opt,
            String... aliases) {

        if (shortcut != null) {
            declareOption(shortcut, opt);
        }
        if (name != null) {
            declareOption(name, opt);
        }

        for (String a : aliases) {
            declareOption(a, opt);
        }
    }

    /**
     * Process the list of string as command line parameters.
     * 
     * @param argv the command line parameters
     * 
     * @return the exit code; i.e. 0 iff everything went fine
     */
    public int processCommandLine(String[] argv) {

        try {
            int ret = run(argv);
            if (ret != EXIT_CONTINUE) {
                return ret;
            }
        } catch (UnknownOptionCliException e) {
            logBanner("unknown.option", e.getMessage());
        } catch (MissingArgumentCliException e) {
            return logBanner("missing.option", e.getMessage());
        } catch (NonNumericArgumentCliException e) {
            return logBanner("non-numeric.option", e.getMessage());
        } catch (UnusedArgumentCliException e) {
            return logBanner("unused.option.argument", e.getMessage());
        }

        try {

            return run();

        } catch (ConfigurationException e) {
            return logBanner("configuration.error", e.getLocalizedMessage());
        } catch (IOException e) {
            return logBanner("io.error", e.toString());
        }
    }

    /**
     * Invoke the processing core.
     * 
     * @return the exit code
     * 
     * @throws IOException in case of an I/O error
     * @throws ConfigurationException in case of a configuration error
     */
    protected abstract int run() throws IOException, ConfigurationException;

    /**
     * Setter for bundle.
     * 
     * @param bundle the bundle to set
     */
    public void setBundle(ResourceBundle bundle) {

        this.bundle = bundle;
    }

    /**
     * Setter for the file name.
     * 
     * @param arg the file name
     * 
     * @return EXIT_CONTINUE
     */
    protected abstract int setFile(String arg);

    /**
     * Setter for the log file.
     * 
     * @param logfile the log file
     */
    public void setLogfile(String logfile) {

        this.logfile = logfile;
    }

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Sett for the output file.
     * 
     * @param arg the output file
     */
    protected abstract void setOutfile(String arg);

    /**
     * Setter for the program name.
     * 
     * @param programName the program name to set
     */
    public void setProgramName(String programName) {

        this.programName = programName;
    }

}
