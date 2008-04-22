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

package org.extex.exbib.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.main.cli.CLI;
import org.extex.exbib.main.cli.Option;
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
            logger.info(MessageFormat.format(bundle.getString(tag), args));
        } catch (MissingResourceException e) {
            logger.severe(MessageFormat.format(bundle.getString("missing.tag"),
                tag));
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
            logger.severe(MessageFormat.format(bundle.getString(tag), args));
        } catch (MissingResourceException e) {
            logger.severe(MessageFormat.format(bundle.getString("missing.tag"),
                tag));
        }
        return EXIT_FAIL;
    }

    /**
     * Write the banner to the logger. The used log level is warning.
     * 
     * @param copyright the indicator to show the copyright
     * 
     * @return the exit code <code>1</code>
     */
    protected int logBanner(boolean copyright) {

        if (banner) {
            return EXIT_FAIL;
        }
        banner = true;

        logger.warning(MessageFormat.format(bundle.getString("version"),
            getProgramName(), version));
        if (copyright) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String copyrightYear =
                    (year <= inceptionYear
                            ? Integer.toString(inceptionYear)
                            : Integer.toString(inceptionYear) + "-"
                                    + Integer.toString(year));
            logger.severe(MessageFormat.format(bundle.getString("copyright"),
                getProgramName(), copyrightYear));
        }
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

        logBanner(false);
        return log(tag, getProgramName());
    }

    /**
     * Write a message to the logger. It is preceded by the banner if the banner
     * has not been shown before.
     * 
     * @param tag the resource tag of the message pattern
     * @param arg the arguments
     * 
     * @return the exit code <code>1</code>
     */
    protected int logBanner(String tag, String arg) {

        logBanner(false);
        return log(tag, getProgramName(), arg);
    }

    /**
     * Show the copying information (license) which is sought on the classpath.
     * 
     * @param log the target logger
     * 
     * @return the exit code EXIT_FAILURE
     */
    protected int logCopying(Logger log) {

        InputStream is =
                getClass().getClassLoader().getResourceAsStream(
                    COPYING_RESOURCE);

        if (is == null) {
            log.severe("--copying " + COPYING_RESOURCE);
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
        log.severe(sb.toString());

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
     * @param opt the option
     * @param nameList the list of names
     */
    protected void option(Option opt, String... nameList) {

        for (String name : nameList) {
            declare(name, opt);
            declare("-" + name, opt);
        }
    }

    /**
     * Process the list of string as command line parameters.
     * 
     * @param argv the command line parameters
     * 
     * @return the exit code; i.e. 0 iff everything went fine
     */
    protected int processCommandLine(String[] argv) {

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
     * Setter for logger.
     * 
     * @param logger the logger to set
     */
    public void setLogger(Logger logger) {

        this.logger = logger;
    }

    /**
     * Setter for the program name.
     * 
     * @param programName the program name to set
     */
    public void setProgramName(String programName) {

        this.programName = programName;
    }

}
