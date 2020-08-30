/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import java.io.File;
import java.io.FileInputStream;
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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.cli.CLI;
import org.extex.cli.NoArgOption;
import org.extex.cli.StringOption;
import org.extex.cli.StringPropertyOption;
import org.extex.cli.exception.MissingArgumentCliException;
import org.extex.cli.exception.NonNumericArgumentCliException;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.cli.exception.UnusedArgumentCliException;
import org.extex.exbib.core.ExBib;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;

/**
 * This is an abstract base class for main programs.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractMain extends CLI {

    /**
     * The field {@code COPYING_RESOURCE} contains the name of the resource for
     * the copyright file (in the jar).
     */
    private static final String COPYING_RESOURCE =
            "org/extex/exbib/main/COPYING";

    /**
     * The field {@code PROP_LANG} contains the name of the property for the
     * language to use.
     */
    private static final String PROP_LANG = "language";

    /**
     * The field {@code PROP_LOGFILE} contains the name of the property for the
     * log file.
     */
    public static final String PROP_LOGFILE = "exbib.logfile";

    /**
     * The field {@code PROP_PROGNAME} contains the name of the property from
     * which the name of the program is extracted.
     */
    public static final String PROP_PROGNAME = "program.name";

    /**
     * Create a console handler and attach it to a logger.
     * 
     * @param log the logger
     * 
     * @return the console handler
     */
    protected static ConsoleHandler makeConsoleHandler(Logger log) {

        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new LogFormatter());
        ch.setLevel(Level.WARNING);
        log.addHandler(ch);
        return ch;
    }

    /**
     * Create a logger.
     * 
     * @param className the name of the class
     * 
     * @return the logger
     */
    protected static Logger makeLogger(String className) {

        Logger logger = Logger.getLogger(className);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        return logger;
    }

    /**
     * The field {@code banner} contains the indicator that the banner has
     * already been printed.
     */
    private boolean banner = false;

    /**
     * The field {@code logger} contains the logger.
     */
    private Logger logger;

    /**
     * The field {@code bundle} contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * The field {@code version} contains the version number.
     */
    private final String version;

    /**
     * The field {@code inceptionYear} contains the first year of development.
     */
    private final int inceptionYear;

    /**
     * The field {@code consoleHandler} contains the console handler for log
     * messages. It can be used to modify the log level for the console.
     */
    private Handler consoleHandler;

    /**
     * The field {@code properties} contains the settings for the program.
     */
    private Properties properties = null;

    /**
     * Creates a new object.
     * 
     * @param programName the name of the program
     * @param version the version
     * @param year the inception year
     * @param dotFile the name of the user's dot file
     * @param properties the initial properties
     * 
     * @throws IOException in case of an I/O error while reading the dot file
     */
    public AbstractMain(String programName, String version, int year,
            String dotFile, Properties properties) throws IOException {

        this.version = version;
        this.inceptionYear = year;
        this.bundle = ResourceBundle.getBundle(getClass().getName());
        this.logger = makeLogger(getClass().getName());
        this.consoleHandler = makeConsoleHandler(logger);
        this.properties = (Properties) properties.clone();

        if (dotFile != null) {
            loadUserProperties(new File(System.getProperty("user.home"),
                dotFile));
            loadUserProperties(new File(dotFile));

            applyLanguage();
        }
        propertyDefault(PROP_PROGNAME, programName);
    }

    /**
     * Try to determine which language to use and configure the localizer
     * accordingly. If no locale has been given then the default locale is used.
     * If the given locale is not supported then the default locale is used and
     * a warning is logged.
     */
    protected void applyLanguage() {

        String lang = properties.getProperty(PROP_LANG);
        Locale locale;

        if (lang == null || "".equals(lang)) {
            return;
        } else if (lang.matches("[a-z][a-z]")) {
            locale = new Locale(lang);
        } else if (lang.matches("[a-z][a-z][-_][a-z][a-z]")) {
            locale = new Locale(lang.substring(0, 1),
                lang.substring(3, 4));
        } else if (lang.matches("[a-z][a-z][-_][a-z][a-z][-_][a-z][a-z]")) {
            locale = new Locale(lang.substring(0, 1),
                lang.substring(3, 4), lang.substring(6, 7));
        } else {
            log("undefined.language", lang);
            return;
        }

        Locale.setDefault(locale);
        useLanguage(locale);
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

        String logfile = getProperty(PROP_LOGFILE);
        if (logfile == null && log != null && !"".equals(log)
                && !"-".equals(log)) {
            logfile = log + extension;
        }
        if (logfile != null && !"".equals(logfile)) {
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

                if (a.startsWith("-D")) {
                    String prop = a.substring(2);
                    properties.setProperty(prop, arg);
                    if (PROP_LANG.equals(prop)) {
                        applyLanguage();
                    }
                    return CLI.EXIT_CONTINUE;
                } else if (a.startsWith("-")) {
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

                try {
                    return logCopying();
                } catch (IOException e) {
                    // shit happens
                    return EXIT_FAIL;
                }
            }
        });
        option("-h", "--help", new NoArgOption("opt.help") {

            @Override
            protected int run(String arg) {

                logBanner();
                getLogger().severe(describeOptions(getBundle(),
                    "usage.start", "usage.end", getProgramName()));
                return EXIT_FAIL;
            }
        }, "-?");
        option("-l", "--logfile",
            new StringPropertyOption("opt.logfile", PROP_LOGFILE, properties));
        option(null, "--load", new StringOption("opt.load") {

            @Override
            protected int run(String name, String arg) {

                try {
                    if (!loadUserProperties(new File(arg))) {
                        return logBanner("properties.not.found", arg);
                    }
                } catch (IOException e) {
                    return logBanner("properties.io.error", arg);
                }
                return EXIT_CONTINUE;
            }
        });
        option("-L", "--language", new StringOption("opt.language") {

            @Override
            protected int run(String name, String arg) {

                Locale locale = new Locale(arg);
                Locale.setDefault(locale);
                useLanguage(locale);
                return EXIT_CONTINUE;
            }
        });
        option("-o", "--output",
            new StringPropertyOption("opt.output", ExBib.PROP_OUTFILE,
                properties), "--outfile");
        option(
            "-p",
            "--progname",
            new StringPropertyOption("opt.progname", PROP_PROGNAME, properties),
            "--program.name", "--program-name");
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
     * Format a message from a resource bundle.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the formatted string
     */
    private String format(String tag, Object... args) {

        return MessageFormat.format(getBundle().getString(tag), args);
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

        return properties.getProperty(PROP_PROGNAME);
    }

    /**
     * Getter for properties.
     * 
     * @return the properties
     */
    protected Properties getProperties() {

        return properties;
    }

    /**
     * Getter for a named property.
     * 
     * @param key the property name
     * 
     * @return the value of the named property or {@code null}
     */
    public String getProperty(String key) {

        return properties.getProperty(key);
    }

    /**
     * Log an info message.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the exit code {@code -1}
     */
    protected int info(String tag, Object... args) {

        return log(Level.INFO, tag, args);
    }

    /**
     * Load properties from a given file if it exists.
     * 
     * @param file the file to consider
     * 
     * @return {@code true} iff the file could be read
     * 
     * @throws IOException in case of an IO Error during the reading of the
     *         properties file
     */
    protected boolean loadUserProperties(File file) throws IOException {

        if (file != null && file.canRead()) {
            FileInputStream stream = new FileInputStream(file);
            try {
                properties.load(stream);
            } finally {
                stream.close();
            }
            return true;
        }
        return false;
    }

    /**
     * Log a severe message.
     * 
     * @param level the log level
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the exit code {@code 1}
     */
    private int log(Level level, String tag, Object... args) {

        try {
            logger.log(level, format(tag, args));
        } catch (MissingResourceException e) {
            logger.severe(format("missing.tag", tag));
        }
        return EXIT_FAIL;
    }

    /**
     * Log a severe message.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return the exit code {@code 1}
     */
    protected int log(String tag, Object... args) {

        return log(Level.SEVERE, tag, args);
    }

    /**
     * Write the banner to the logger. The used log level is warning.
     * 
     * @return the exit code {@code EXIT_FAILURE}
     */
    protected int logBanner() {

        if (banner) {
            return EXIT_FAIL;
        }
        banner = true;
        return log(Level.WARNING, "version", getProgramName(), version);
    }

    /**
     * Write a message to the logger. It is preceded by the banner if the banner
     * has not been shown before.
     * 
     * @param tag the resource tag of the message pattern
     * @param args the arguments
     * 
     * @return the exit code {@code 1}
     */
    public int logBanner(String tag, Object... args) {

        logBanner();
        Object[] a = new Object[args.length + 1];
        a[0] = getProgramName();
        System.arraycopy(args, 0, a, 1, args.length);
        return log(tag, a);
    }

    /**
     * Write the banner and the copyright to the logger. The used log level is
     * warning.
     * 
     * @return the exit code {@code EXIT_FAIL}
     */
    protected int logBannerCopyright() {

        if (!banner) {
            banner = true;

            log(Level.WARNING, "version", getProgramName(), version);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            log(Level.SEVERE, "copyright", getProgramName(),
                year <= inceptionYear
                        ? Integer.toString(inceptionYear)
                        : inceptionYear + "-"
                                + year );
        }
        return EXIT_FAIL;
    }

    /**
     * Show the copying information (license) which is sought on the classpath.
     * 
     * @return the exit code EXIT_FAILURE
     * 
     * @throws IOException in case of an I/O error
     */
    protected int logCopying() throws IOException {

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
        } finally {
            if (r != null) {
                r.close();
            } else {
                is.close();
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
     * @return the exit code {@code 1}
     */
    protected int logException(Throwable e, String tag, boolean debug) {

        logBanner(tag, e.getLocalizedMessage());

        if (debug) {
            logger.throwing("", "", e);
        }
        return EXIT_FAIL;
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
     * Set a property to a given value if not set yet.
     * 
     * @param name the name of the property
     * @param value the default value
     */
    protected void propertyDefault(String name, String value) {

        if (!properties.containsKey(name) && value != null) {
            properties.setProperty(name, value);
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
     * Sets a file name to be processed.
     * 
     * @param arg the file
     * 
     * @return the value EXIT_CONTINUE on success and EXIT_FAILURE on an error
     */
    protected abstract int setFile(String arg);

    /**
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for a named property.
     * 
     * @param key the property name
     * @param value the new value of the named property
     */
    protected void setProperty(String key, String value) {

        properties.setProperty(key, value);
    }

    /**
     * Activate a language.
     * 
     * @param locale the locale
     */
    protected void useLanguage(Locale locale) {

        bundle = ResourceBundle.getBundle(getClass().getName());
    }

}
