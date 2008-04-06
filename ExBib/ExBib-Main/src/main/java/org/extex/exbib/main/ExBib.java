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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.ProcessorFactory;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.DBFactory;
import org.extex.exbib.core.engine.Engine;
import org.extex.exbib.core.engine.EngineFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.WriterFactory;
import org.extex.exbib.core.io.bblio.BblWriterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.bstio.BstReader;
import org.extex.exbib.core.io.bstio.BstReaderFactory;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.io.csf.CsfReader;
import org.extex.exbib.core.io.csf.CsfSorter;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.main.bibtex.DBObserver;
import org.extex.exbib.main.bibtex.EntryObserver;
import org.extex.exbib.main.bibtex.FuncallObserver;
import org.extex.exbib.main.bibtex.TracingObserver;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This class contains the main program for ExBib.
 * <p>
 * </p>
 * <p>
 * Usage: <tt>java org.extex.exbib.main.ExBib </tt><i>&lt;options&gt; file</i>
 * </p>
 * <p>
 * The following options are supported:
 * </p>
 * <dl>
 * <dt>-help</dt>
 * <dd>Show a short list of command line arguments. </dd>
 * <dt>-copying</dt>
 * <dd>Display the copyright conditions. </dd>
 * <dt>-quiet</dt>
 * <dt>-terse</dt>
 * <dd>Act quietly; some informative messages are suppressed. </dd>
 * <dt>-verbose</dt>
 * <dd>Act verbosely; some additional informational messages are displayed.
 * </dd>
 * <dt>-dump</dt>
 * <dd>Write some internal information to the log file at the end (for
 * debugging) </dd>
 * <dt>-trace</dt>
 * <dd>Show a detailed trace of many operations. </dd>
 * <dt>-version</dt>
 * <dd>Print the version information and exit. </dd>
 * <dt>-release</dt>
 * <dd>Print the release number to stdout and exit. </dd>
 * <dt>-strict</dt>
 * <dd>use the settings for BibT<sub>E</sub>X 0.99c. </dd>
 * <dt>-config <i>file</i></dt>
 * <dd> </dd>
 * <dt>-minCrossrefs <i>value</i></dt>
 * <dd>Set the value for <tt>min.crossrefs</tt>. The default is 2. </dd>
 * <dt>-bst <i>style</i></dt>
 * <dd>use the named bibstyle </dd>
 * <dt>-logfile <i>file</i></dt>
 * <dd>use the given logfile instead of the one derived from the name of the
 * aux file </dd>
 * <dt>-outfile <i>file</i></dt>
 * <dd>use the given output file instead of the one derived from the name of
 * the aux file </dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class ExBib {

    /**
     * The field <tt>VERSION</tt> contains the official version number.
     */
    public static final String VERSION = "0.1";

    /**
     * The field <tt>CONFIGURATION_0_99</tt> contains the name of the
     * configuration to be used in strict mode.
     */
    private static final String CONFIGURATION_0_99 = "exbib/bibtex099";

    /**
     * The field <tt>CONFIGURATION_DEFAULT</tt> contains the name of the
     * configuration to be used in default mode.
     */
    private static final String CONFIGURATION_DEFAULT = "exbib/exbib";

    /**
     * The field <tt>AUX_FILE_EXTENSION</tt> contains the extension of aux
     * files (in lower case).
     */
    private static final String AUX_FILE_EXTENSION = ".aux";

    /**
     * The field <tt>BBL_FILE_EXTENSION</tt> contains the extension of output
     * files (in lower case).
     */
    private static final String BBL_FILE_EXTENSION = ".bbl";

    /**
     * The field <tt>BLG_FILE_EXTENSION</tt> contains the extension of log
     * files (in lower case).
     */
    private static final String BLG_FILE_EXTENSION = ".blg";

    /**
     * The field <tt>BST_FILE_EXTENSION</tt> contains the extension of BibTeX
     * style files (in lower case).
     */
    private static final String BST_FILE_EXTENSION = ".bst";

    /**
     * The field <tt>COPYING_RESOURCE</tt> contains the name of the resource
     * for the copyright file (in the jar).
     */
    private static final String COPYING_RESOURCE =
            "org/extex/exbib/main/COPYING";

    /**
     * The field <tt>INCEPTION_YEAR</tt> contains the year the development has
     * been started. This is fixed to be 2002 and should not be altered.
     */
    private static final int INCEPTION_YEAR = 2002;

    /**
     * The field <tt>EXIT_FAIL</tt> contains the exit code for failure.
     */
    private static final int EXIT_FAIL = 1;

    /**
     * The field <tt>EXIT_OK</tt> contains the exit code for success.
     */
    private static final int EXIT_OK = 0;

    /**
     * The main program. The command line parameters are evaluated and the
     * appropriate actions are performed. Exceptions are caught and reported.
     * 
     * @param argv list of command line parameters
     * 
     * @return the exit code
     */
    protected static int commandLine(String[] argv) {

        ExBib exBib = null;
        try {
            exBib = new ExBib();
            return exBib.processCommandLine(argv);
        } catch (RuntimeException e) {
            Logger logger = Logger.getLogger(ExBib.class.getName());
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LogFormatter());
            consoleHandler.setLevel(Level.WARNING);
            logger.addHandler(consoleHandler);
            logger.severe(e.toString());
            return EXIT_FAIL;
        } finally {
            if (exBib != null) {
                exBib.close();
            }
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
     * The field <tt>banner</tt> contains the indicator that the banner has
     * already been printed.
     */
    private boolean banner = false;

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * The field <tt>consoleHandler</tt> contains the console handler for log
     * messages. It can be used to modify the log level for the console.
     */
    private Handler consoleHandler;

    /**
     * The field <tt>bst</tt> contains the name of the Bib style.
     */
    private String bst = null;

    /**
     * The field <tt>configSource</tt> contains the name of the configuration
     * file.
     */
    private String configSource = CONFIGURATION_DEFAULT;

    /**
     * The field <tt>debug</tt> contains the indicator for debugging output.
     */
    private boolean debug = false;

    /**
     * The field <tt>errors</tt> contains the number of errors reported.
     */
    private int errors = 0;

    /**
     * The field <tt>logfile</tt> contains the name of the log file.
     */
    private String logfile = null;

    /**
     * The field <tt>outfile</tt> contains the name of the output file.
     */
    private String outfile = null;

    /**
     * The field <tt>programName</tt> contains the name of the program.
     */
    private String programName = "exbib";

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * The field <tt>minCrossrefs</tt> contains the <tt>min.crossrefs</tt>
     * parameter for the database.
     */
    private int minCrossrefs = 2;

    /**
     * The field <tt>trace</tt> contains the indicator for trace output.
     */
    private boolean trace = false;

    /**
     * The field <tt>csf</tt> contains the name of the csf file to read.
     */
    private String csf = null;

    /**
     * Creates a new object.
     */
    public ExBib() {

        super();
        bundle = ResourceBundle.getBundle(getClass().getName());

        logger = Logger.getLogger(getClass().getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);
    }

    /**
     * Close the instance and release the logger.
     */
    public void close() {

        for (Handler h : logger.getHandlers()) {
            h.close();
            logger.removeHandler(h);
        }
        consoleHandler = null;
        logger = null;
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
     * Getter for minCrossrefs.
     * 
     * @return the minCrossrefs
     */
    public int getMinCrossrefs() {

        return minCrossrefs;
    }

    /**
     * Getter for the out file.
     * 
     * @return the out file
     */
    public String getOutfile() {

        return outfile;
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
     * Getter for debug.
     * 
     * @return the debug
     */
    public boolean isDebug() {

        return debug;
    }

    /**
     * Getter for trace.
     * 
     * @return the trace
     */
    public boolean isTrace() {

        return trace;
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
    private int logBanner(boolean copyright) {

        if (banner) {
            return EXIT_FAIL;
        }
        banner = true;

        logger.warning(MessageFormat.format(bundle.getString("version"),
            programName, VERSION));
        if (copyright) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String copyrightYear =
                    (year <= INCEPTION_YEAR
                            ? Integer.toString(INCEPTION_YEAR)
                            : Integer.toString(INCEPTION_YEAR) + "-"
                                    + Integer.toString(year));
            logger.severe(MessageFormat.format(bundle.getString("copyright"),
                programName, copyrightYear));
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
    private int logBanner(String tag) {

        logBanner(false);
        return log(tag, programName);
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
    private int logBanner(String tag, String arg) {

        logBanner(false);
        return log(tag, programName, arg);
    }

    /**
     * Show the copying information (license) which is sought on the classpath.
     * 
     * @return the exit code <code>1</code>
     */
    private int logCopying() {

        InputStream is =
                getClass().getClassLoader().getResourceAsStream(
                    COPYING_RESOURCE);

        if (is == null) {
            logger.severe("--copying " + COPYING_RESOURCE);
            return EXIT_FAIL;
        }

        StringBuilder sb = new StringBuilder();
        try {
            LineNumberReader r =
                    new LineNumberReader(new InputStreamReader(is));
            for (String s = r.readLine(); s != null; s = r.readLine()) {
                sb.append(s);
                sb.append('\n');
            }
        } catch (IOException e) {
            // shit happens
        } finally {
            try {
                is.close();
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
    private int logException(Throwable e, String tag, boolean debug) {

        logBanner(tag, e.getLocalizedMessage());

        if (debug) {
            logger.throwing("", "", e);
        }

        return EXIT_FAIL;
    }

    /**
     * Create a new {@link java.io.Writer} for a bbl file.
     * 
     * @param file the name of the file
     * @param writerFactory the factory for new writers
     * @param cfg the configuration
     * 
     * @return the new writer or <code>null</code> when the file could not be
     *         opened for writing
     * 
     * @throws UnsupportedEncodingException if an error with the encoding is
     *         encountered
     * @throws ConfigurationException in case of a configuration error
     */
    private Writer makeBblWriter(String file, WriterFactory writerFactory,
            Configuration cfg)
            throws UnsupportedEncodingException,
                ConfigurationException {

        Writer writer = null;

        if (outfile == null) {
            outfile = file + BBL_FILE_EXTENSION;
        }

        if (outfile.equals("")) {
            writer = writerFactory.newInstance();
            info("output.discarted");
        } else if (outfile.equals("-")) {
            writer = writerFactory.newInstance(System.out);
            info("output.to.stdout");
        } else {
            try {
                writer = writerFactory.newInstance(outfile);
            } catch (FileNotFoundException e) {
                log("output.could.not.be.opened", outfile);
                return null;
            }
            info("output.file", outfile);
        }

        return new BblWriterFactory(cfg.getConfiguration("BblWriter"))
            .newInstance(writer);
    }

    /**
     * Process the list of string as command line parameters.
     * 
     * @param argv the command line parameters
     * 
     * @return the exit code; i.e. 0 iff everything went fine
     */
    protected int processCommandLine(String[] argv) {

        String file = null;

        for (int i = 0; i < argv.length; i++) {
            String a = argv[i];

            if (a.startsWith("-")) {
                a = a.substring(a.startsWith("--") ? 2 : 1);

                if ("".equals(a)) {
                    if (file != null) {
                        return logBanner("one.file", file);
                    } else if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    file = argv[i];

                } else if ("bst".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    setBst(argv[i]);

                } else if ("csfile".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    setCsfile(argv[i]);

                } else if ("config".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    setConfigSource("exbib/" + argv[i]);

                } else if ("copying".startsWith(a)) {
                    return logCopying();

                } else if ("debug".startsWith(a)) {
                    setDebug(true);

                } else if ("help".startsWith(a) || "?".equals(a)) {
                    return logBanner("usage", programName);

                } else if ("logfile".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    setLogfile(argv[i]);

                } else if ("min_crossrefs".startsWith(a)
                        || "min.crossrefs".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    try {
                        setMinCrossrefs(Integer.parseInt(argv[i]));
                    } catch (NumberFormatException e) {
                        return logBanner("non-numeric.option", argv[i - 1]);
                    }

                } else if ("outfile".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    setOutfile(argv[i]);

                } else if ("progname".startsWith(a)
                        || "program.name".startsWith(a)
                        || "program_name".startsWith(a)) {
                    if (++i >= argv.length) {
                        return logBanner("missing.option", argv[i - 1]);
                    }
                    setProgramName(argv[i]);

                } else if ("quiet".startsWith(a)) {
                    consoleHandler.setLevel(Level.SEVERE);

                } else if ("release".startsWith(a)) {
                    logger.severe(VERSION + "\n");
                    return 1;

                } else if ("strict".startsWith(a)) {
                    setConfigSource(CONFIGURATION_0_99);

                } else if ("trace".startsWith(a)) {
                    setTrace(true);

                } else if ("terse".startsWith(a)) {
                    consoleHandler.setLevel(Level.SEVERE);

                } else if ("verbose".startsWith(a)) {
                    consoleHandler.setLevel(Level.INFO);

                } else if ("version".startsWith(a)) {
                    return logBanner(true);

                } else {
                    logBanner("unknown.option", a);
                }
            } else if (file != null) {
                return logBanner("one.file", file);

            } else {
                file = argv[i];
            }
        }

        try {

            return run(file);

        } catch (ConfigurationNotFoundException e) {
            return logBanner("configuration.error", e.getLocalizedMessage());
        } catch (ConfigurationException e) {
            return logBanner("configuration.error", e.getLocalizedMessage());
        } catch (IOException e) {
            return logBanner("io.error", e.toString());
        }
    }

    /**
     * This is the top level of the BibT<sub>E</sub>X engine. When all
     * parameters are present then this method can be invoked.
     * 
     * @param filename the name of the input file (part of)
     * 
     * @return <code>true</code> iff an error has occurred
     * 
     * @throws IOException is case of an uncaught IOException
     * @throws ConfigurationException in case that the top-level configuration
     *         could not be found
     */
    public int run(String filename) throws IOException, ConfigurationException {

        long time = System.currentTimeMillis();
        Configuration topConfiguration =
                ConfigurationFactory.newInstance(configSource);
        WriterFactory writerFactory =
                new WriterFactory(topConfiguration.getConfiguration("Writers"));
        ResourceFinder finder =
                new ResourceFinderFactory().createResourceFinder(
                    topConfiguration.getConfiguration("Resource"), logger,
                    System.getProperties(), null);
        if (filename == null) {
            return logBanner("missing.file");
        }
        String file = stripExtension(filename, AUX_FILE_EXTENSION);

        runAttachLogFile(file);
        logBanner(false);

        CsfSorter csfSorter = null;
        if (csf != null) {
            InputStream is = finder.findResource(csf, "csf");
            if (is == null) {
                return logBanner("csf.missing");
            }
            try {
                csfSorter = new CsfReader().read(new InputStreamReader(is));
            } catch (CsfException e) {
                return logBanner(e.getLocalizedMessage());
            } finally {
                is.close();
            }
        }

        Writer writer = makeBblWriter(file, writerFactory, topConfiguration);
        if (writer == null) {
            return EXIT_FAIL;
        }

        try {
            FuncallObserver funcall = null;

            DB db = new DBFactory(//
                topConfiguration.getConfiguration("DB")).newInstance();
            BibReaderFactory bibReaderFactory = new BibReaderFactory(//
                topConfiguration.getConfiguration("BibReader"));
            bibReaderFactory.setResourceFinder(finder);
            db.setBibReaderFactory(bibReaderFactory);

            Processor bibliography = new ProcessorFactory(//
                topConfiguration.getConfiguration("Processor")).newInstance(db);
            bibliography.setMinCrossrefs(minCrossrefs);

            if (trace) {
                funcall = runRegisterTracers(db, bibliography);
            }
            bibliography.registerObserver("startRead", new DBObserver(logger,
                bundle.getString("observer.db.pattern")));

            Engine engine = new EngineFactory(//
                topConfiguration.getConfiguration("Engine")).newInstance();
            engine.setResourceFinder(finder);
            try {
                engine.setFilename(file);
            } catch (FileNotFoundException e) {
                return log("aux.not.found", file);
            }

            info("aux.file", engine.getFilename());

            try {
                int[] no = engine.process(bibliography);

                if (no[1] == 0) {
                    errors++;
                    log("bst.missing", file);
                }
                if (no[0] == 0) {
                    errors++;
                    log("data.missing", file);
                }
                if (no[2] == 0) {
                    errors++;
                    log("citation.missing", file);
                }
            } catch (FileNotFoundException e) {
                return log("aux.not.found", file);
            }

            if (bst != null) {
                bibliography.addBibliographyStyle(stripExtension(bst,
                    BST_FILE_EXTENSION));
            }

            List<String> bibliographyStyles =
                    bibliography.getBibliographyStyles();

            if (bibliographyStyles == null || bibliographyStyles.isEmpty()) {
                return EXIT_FAIL;
            }
            bst = bibliographyStyles.get(0);

            bst = stripExtension(bst, BST_FILE_EXTENSION);
            info("bst.file", bst);
            BstReader bstReader =
                    new BstReaderFactory(topConfiguration
                        .getConfiguration("BstReader")).newInstance();
            bstReader.setResourceFinder(finder);
            try {
                bstReader.parse(bibliography);
            } catch (FileNotFoundException e) {
                return log("bst.not.found", e.getMessage());
            }

            bibliography.process(writer, logger);

            if (errors > 0) {
                log(errors == 1 ? "error" : "errors", Long.toString(errors));
            }
            long warnings = bibliography.getNumberOfWarnings();
            if (warnings > 0) {
                info(warnings == 1 ? "warning" : "warnings", //
                    Long.toString(warnings));
            }

            info("runtime", Long.toString(System.currentTimeMillis() - time));

            if (funcall != null) {
                funcall.print();
            }

        } catch (ExBibImpossibleException e) {
            return logException(e, "internal.error", debug);
        } catch (ExBibFileNotFoundException e) {
            logger.severe(e.getLocalizedMessage() + "\n");
            return EXIT_FAIL;
        } catch (ExBibException e) {
            logger.severe(e.getLocalizedMessage() + "\n");
            return EXIT_FAIL;
        } catch (ConfigurationWrapperException e) {
            return logException(e, "installation.error", debug);
        } catch (ConfigurationException e) {
            return logException(e, "installation.error", debug);
        } catch (NoClassDefFoundError e) {
            return logException(e, "installation.error", debug);
        } catch (Throwable e) {
            return logException(e, "internal.error", debug);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return errors > 0 ? EXIT_FAIL : EXIT_OK;
    }

    /**
     * Attach a handler to the logger to direct messages to a log file.
     * 
     * @param file the base name of the file
     * 
     * @throws IOException in case of an I/O error
     */
    private void runAttachLogFile(String file) throws IOException {

        if (logfile == null && !file.equals("") && !file.equals("-")) {
            logfile = file + BLG_FILE_EXTENSION;
        }
        if (logfile != null && !logfile.equals("")) {
            Handler fileHandler = new FileHandler(logfile);
            fileHandler.setFormatter(new LogFormatter());
            fileHandler.setLevel(debug ? Level.ALL : Level.FINE);
            logger.addHandler(fileHandler);
        }
    }

    /**
     * Register observers to get tracing output.
     * 
     * @param db the database
     * @param bibliography the bibliography
     * 
     * @return the function call observer
     * 
     * @throws NotObservableException in case of an unknown observer name
     * @throws ExBibIllegalValueException in case of an illegal value
     */
    private FuncallObserver runRegisterTracers(DB db, Processor bibliography)
            throws NotObservableException,
                ExBibIllegalValueException {

        FuncallObserver funcall = (trace ? new FuncallObserver(logger) : null);

        db.registerObserver("makeEntry",
            new EntryObserver(logger, bibliography));

        bibliography.registerObserver("step", new TracingObserver(logger,
            bundle.getString("step_msg")));

        bibliography.registerObserver("run", new TracingObserver(logger, bundle
            .getString("do_msg")));
        bibliography.registerObserver("step", funcall);
        bibliography.registerObserver("push", new TracingObserver(logger,
            bundle.getString("push_msg")));
        bibliography.registerObserver("startParse", new TracingObserver(logger,
            bundle.getString("start_parse_msg")));
        bibliography.registerObserver("endParse", new TracingObserver(logger,
            bundle.getString("end_parse_msg")));
        return funcall;
    }

    /**
     * Setter for the Bib style
     * 
     * @param string the bib style
     */
    public void setBst(String string) {

        bst = string;
    }

    /**
     * Setter for the configuration source.
     * 
     * @param configSource the configuration source
     */
    public void setConfigSource(String configSource) {

        this.configSource = configSource;
    }

    /**
     * Setter for the cs file.
     * 
     * @param csf the name of the cs file
     */
    private void setCsfile(String csf) {

        this.csf = csf;
    }

    /**
     * Setter for the debugging indicator.
     * 
     * @param debug indicator for debugging
     */
    public void setDebug(boolean debug) {

        this.debug = debug;
    }

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
     * Setter for the min.crossrefs.
     * 
     * @param minCrossrefs the new value
     */
    public void setMinCrossrefs(int minCrossrefs) {

        this.minCrossrefs = minCrossrefs;
    }

    /**
     * Setter for the output file.
     * 
     * @param outfile the output file to set
     */
    public void setOutfile(String outfile) {

        this.outfile = outfile;
    }

    /**
     * Setter for the program name.
     * 
     * @param programName the program name to set
     */
    public void setProgramName(String programName) {

        this.programName = programName;
    }

    /**
     * Setter for trace.
     * 
     * @param trace the trace to set
     */
    public void setTrace(boolean trace) {

        this.trace = trace;
    }

    /**
     * Remove an extension if the given file name ends with it. The comparison
     * is performed case-insensitive.
     * 
     * @param file the name of the file
     * @param extension the postfix to remove
     * 
     * @return the normalized file name
     */
    private String stripExtension(String file, String extension) {

        if (file.toLowerCase().endsWith(extension)) {
            return file.substring(0, file.length() - extension.length());
        }

        return file;
    }

}
