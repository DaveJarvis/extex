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
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
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
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.WriterFactory;
import org.extex.exbib.core.io.auxio.AuxReader;
import org.extex.exbib.core.io.auxio.AuxReaderFactory;
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
import org.extex.exbib.main.cli.BooleanOption;
import org.extex.exbib.main.cli.NoArgOption;
import org.extex.exbib.main.cli.NumberOption;
import org.extex.exbib.main.cli.StringOption;
import org.extex.exbib.main.cli.exception.UnknownOptionCliException;
import org.extex.exbib.main.cli.exception.UnusedArgumentCliException;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.exbib.main.util.MainResourceObserver;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
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
 * <dt>-[-] &lang;file&rang;</dt>
 * <dd>Use this argument as file name -- even when it looks like an option.</dd>
 * <dt>--a[vailableCharsets] | -a</dt>
 * <dd>List the available encoding names and exit.</dd>
 * <dt>--bib-[encoding] | --bib.[encoding] | -E &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the bib files.</dd>
 * <dt>--b[st] | -b &lang;style&rang;</dt>
 * <dd>Overwrite the bst file given in the aux file.</dd>
 * <dt>--c[onfig] | -c &lang;configuration&rang;</dt>
 * <dd>Use the configuration given. This is not a file!</dd>
 * <dt>--cop[ying]</dt>
 * <dd>Display the copyright conditions.</dd>
 * <dt>--cs[file] &lang;csfile&rang;</dt>
 * <dd>Name the csf for defining characters and the sort order</dd>
 * <dt>--d[ebug] | -d</dt>
 * <dd>Run in debug mode.</dd>
 * <dt>--e[ncoding] | -e &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the output file.</dd>
 * <dt>--h[elp] | -? | -h</dt>
 * <dd>Show a short list of command line arguments.</dd>
 * <dt>--la[nguage] | -L &lang;language></dt>
 * <dd>Use the named language for message. The argument is a two-letter ISO
 * code.</dd>
 * <dt>--l[ogfile] | -l &lang;file&rang;</dt>
 * <dd>Send the output to the log file named instead of the default one.</dd>
 * <dt>--m[in-crossrefs] | --min.[crossrefs] | --min_[crossrefs] | -m
 * &lang;n&rang;</dt>
 * <dd>Set the value for min.crossrefs. The default is 2.</dd>
 * <dt>--o[utfile] | --outp[ut] | -o &lang;file&rang;</dt>
 * <dd>Redirect the output to the file given. <br />
 * The file name - can be used to redirect to stdout <br />
 * The empty file name can be used to discard the output completely</dd>
 * <dt>--p[rogname] | --progr[am-name] | --program.[name] | -p
 * &lang;program&rang;</dt>
 * <dd>Set the program name for messages.</dd>
 * <dt>--q[uiet] | --t[erse] | -q</dt>
 * <dd>Act quietly; some informative messages are suppressed.</dd>
 * <dt>--r[elease] | -r</dt>
 * <dd>Print the release number and exit.</dd>
 * <dt>--bi[btex] | --s[trict]</dt>
 * <dd>Use the configuration for BibTeX 0.99c.</dd>
 * <dt>--tr[ace] | -t</dt>
 * <dd>Show a detailed trace of many operations.</dd>
 * <dt>--v[erbose] | -v</dt>
 * <dd>Act verbosely; some additional informational messages are displayed.</dd>
 * <dt>--vers[ion]</dt>
 * <dd>Print the version information and exit.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class ExBib extends AbstractMain {

    /**
     * This enumeration names the point for debugging.
     */
    public enum Debug {
        /**
         * The field <tt>CSF</tt> contains the csf processing.
         */
        CSF,
        /**
         * The field <tt>IO</tt> contains the I/O.
         */
        IO,
        /**
         * The field <tt>MEM</tt> contains the memory allocation.
         */
        MEM,
        /**
         * The field <tt>MISC</tt> contains miscellaneous.
         */
        MISC,
        /**
         * The field <tt>SEARCH</tt> contains the search.
         */
        SEARCH
    }

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
     * The field <tt>INCEPTION_YEAR</tt> contains the year the development has
     * been started. This is fixed to be 2002 and should not be altered.
     */
    static final int INCEPTION_YEAR = 2002;

    /**
     * The main program. The command line parameters are evaluated and the
     * appropriate actions are performed. Exceptions are caught and reported.
     * 
     * @param argv list of command line parameters
     * 
     * @return the exit code
     */
    protected static int commandLine(String[] argv) {

        AbstractMain exBib = null;
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
    private Set<Debug> debug = new HashSet<Debug>();

    /**
     * The field <tt>errors</tt> contains the number of errors reported.
     */
    private int errors = 0;

    /**
     * The field <tt>outfile</tt> contains the name of the output file.
     */
    private String outfile = null;

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
     * The field <tt>logfile</tt> contains the name of the log file.
     */
    private String logfile = null;

    /**
     * The field <tt>file</tt> contains the file to be processed.
     */
    private String file = null;

    /**
     * The field <tt>encoding</tt> contains the encoding for the bbl writer.
     */
    private String encoding = null;

    /**
     * The field <tt>bibEncoding</tt> contains the encoding for bib files.
     */
    private String bibEncoding = null;

    /**
     * Creates a new object.
     */
    public ExBib() {

        super("exbib", VERSION, INCEPTION_YEAR);

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.WARNING);
        getLogger().addHandler(consoleHandler);

        declareOptions();
    }

    /**
     * Close the instance and release the logger.
     */
    @Override
    public void close() {

        super.close();
        consoleHandler = null;
    }

    /**
     * Declare the list of command line options.
     * 
     */
    protected void declareOptions() {

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

                return setFile(arg);
            }

        });
        option("-", "--", new StringOption("opt.1.file") {

            @Override
            protected int run(String name, String arg) {

                return setFile(arg);
            }

        });
        option("-a", "--availableCharsets", new NoArgOption(
            "opt.available.charsets") {

            @Override
            protected int run(String arg) {

                Logger logger = getLogger();
                for (String s : Charset.availableCharsets().keySet()) {
                    logger.severe(s + "\n");
                }

                return EXIT_FAIL;
            }

        });
        option("-b", "--bst", new StringOption("opt.bst") {

            @Override
            protected int run(String name, String arg) {

                setBst(arg);
                return EXIT_CONTINUE;
            }

        });
        option(null, "--strict", new NoArgOption("opt.strict") {

            @Override
            protected int run(String arg) {

                setConfigSource(CONFIGURATION_0_99);
                return EXIT_CONTINUE;
            }

        }, "--bibtex");
        option("-E", "--bib.encoding", new StringOption("opt.bib.encoding") {

            @Override
            protected int run(String name, String arg) {

                bibEncoding = arg;
                return EXIT_CONTINUE;
            }

        }, "--bib-encoding");
        option("-c", "--config", new StringOption("opt.config") {

            @Override
            protected int run(String name, String arg) {

                setConfigSource("exbib/" + arg);
                return EXIT_CONTINUE;
            }

        });
        option(null, "--copying", new NoArgOption("opt.copying") {

            @Override
            protected int run(String name) {

                return logCopying(getLogger());
            }

        });
        option(null, "--csfile", new StringOption("opt.csfile") {

            @Override
            protected int run(String name, String arg) {

                setCsfile(arg);
                return EXIT_CONTINUE;
            }

        });
        option("-d", "--debug", new StringOption("opt.debug") {

            @Override
            protected int run(String name, String value) {

                for (String s : value.split("[,;: ]")) {
                    try {
                        setDebug(Debug.valueOf(s));
                    } catch (IllegalArgumentException e) {
                        // TODO
                        return EXIT_CONTINUE;
                    }
                }
                return EXIT_CONTINUE;
            }

        });
        option("-e", "--encoding", new StringOption("opt.encoding") {

            @Override
            protected int run(String name, String arg) {

                setEncoding(arg);
                return EXIT_CONTINUE;
            }

        });
        option("-h", "--help", new NoArgOption("opt.help") {

            @Override
            protected int run(String arg) {

                logBanner(false);
                getLogger().severe(
                    describeOptions(getBundle(), "usage.start", "usage.end",
                        getProgramName()));
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
        option("-M", "--min.crossrefs", new NumberOption("opt.min.crossref") {

            @Override
            protected int run(String name, int arg) {

                setMinCrossrefs(arg);
                return EXIT_CONTINUE;
            }

        }, "--min-crossrefs", "--min_crossrefs");
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
        option("-r", "--release", new NoArgOption("opt.release") {

            @Override
            protected int run(String arg) {

                getLogger().severe(VERSION + "\n");
                return EXIT_FAIL;
            }

        }, "--release");
        option("-t", "--trace", new BooleanOption("opt.trace") {

            @Override
            protected int run(String arg, boolean value) {

                setTrace(value);
                return EXIT_CONTINUE;
            }

        });
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

                return logBanner(true);
            }

        });
        option("-7", "--traditional", new NoArgOption("opt.7.bit") {

            @Override
            protected int run(String arg) {

                csf = "";
                return EXIT_CONTINUE;
            }

        });
        option("-8", "--8bit", new NoArgOption("opt.8.bit") {

            @Override
            protected int run(String arg) {

                csf = "88591lat.csf";
                return EXIT_CONTINUE;
            }

        });
        option("-B", "--big", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                info("ignore.option", arg);
                return EXIT_CONTINUE;
            }

        }, "-H", "--huge", "-W", "--wolfgang");
        option(null, "--mcites", new NumberOption(null) {

            @Override
            protected int run(String arg, int value) {

                info("ignore.option", arg);
                return EXIT_CONTINUE;
            }

        }, "--mentints", "-mentstrs", "--mfields", "--mpool", "--mstrings",
            "--mwizfuns");
    }

    /**
     * Getter for debug.
     * 
     * @return the debug
     */
    public Set<Debug> getDebug() {

        return debug;
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
     * Getter for trace.
     * 
     * @return the trace
     */
    public boolean isTrace() {

        return trace;
    }

    /**
     * Create a new {@link java.io.Writer} for a bbl file.
     * 
     * @param file the name of the file
     * @param cfg the configuration
     * @return the new writer or <code>null</code> when the file could not be
     *         opened for writing
     * 
     * @throws UnsupportedEncodingException if an error with the encoding is
     *         encountered
     * @throws ConfigurationException in case of a configuration error
     */
    private Writer makeBblWriter(String file, Configuration cfg)
            throws UnsupportedEncodingException,
                ConfigurationException {

        Configuration configuration = cfg.getConfiguration("BblWriter");
        WriterFactory writerFactory = new WriterFactory(configuration);
        if (encoding != null) {
            writerFactory.setEncoding(encoding);
        }
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

        return new BblWriterFactory(configuration).newInstance(writer);
    }

    /**
     * This is the top level of the BibT<sub>E</sub>X engine. When all
     * parameters are present then this method can be invoked.
     * 
     * @return <code>true</code> iff an error has occurred
     * 
     * @throws IOException is case of an uncaught IOException
     * @throws ConfigurationException in case that the top-level configuration
     *         could not be found
     */
    @Override
    public int run() throws IOException, ConfigurationException {

        long time = System.currentTimeMillis();
        Configuration topConfiguration =
                ConfigurationFactory.newInstance(configSource);
        ResourceFinder finder =
                new ResourceFinderFactory().createResourceFinder(
                    topConfiguration.getConfiguration("Resource"), getLogger(),
                    System.getProperties(), null);
        if (file == null) {
            return logBanner("missing.file");
        }
        file = stripExtension(file, AUX_FILE_EXTENSION);

        runAttachLogFile(file);
        logBanner(false);

        CsfSorter sorter = null;
        if ("".equals(csf)) {
            sorter = new CsfSorter();
        } else if (csf != null) {
            InputStream is = finder.findResource(csf, "csf");
            if (is == null) {
                return logBanner("csf.missing");
            }
            try {
                sorter = new CsfReader().read(new InputStreamReader(is));
            } catch (CsfException e) {
                return logBanner(e.getLocalizedMessage());
            } finally {
                is.close();
            }
        }

        Writer writer = makeBblWriter(file, topConfiguration);
        if (writer == null) {
            return EXIT_FAIL;
        }

        try {
            FuncallObserver funcall = null;

            BibReaderFactory bibReaderFactory = new BibReaderFactory(//
                topConfiguration.getConfiguration("BibReader"), finder);
            if (bibEncoding != null) {
                bibReaderFactory.setEncoding(bibEncoding);
            }
            DB db =
                    new DBFactory(//
                        topConfiguration.getConfiguration("DB")).newInstance(
                        bibReaderFactory, minCrossrefs);
            if (sorter != null) {
                db.setSorter(sorter);
            }

            Processor processor = new ProcessorFactory(//
                topConfiguration.getConfiguration("Processor")).newInstance(db);

            if (trace) {
                funcall = runRegisterTracers(db, processor);
            }
            processor.registerObserver("startRead", new DBObserver(getLogger(),
                getBundle().getString("observer.db.pattern")));

            AuxReader engine =
                    new AuxReaderFactory(//
                        topConfiguration.getConfiguration("AuxReader"))
                        .newInstance(finder);

            engine.register(new MainResourceObserver(getLogger()));

            try {
                int[] no = engine.process(processor, file, encoding);

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
                return log("aux.not.found", e.getMessage());
            }

            if (bst != null) {
                processor.addBibliographyStyle(stripExtension(bst,
                    BST_FILE_EXTENSION));
            }

            List<String> bibliographyStyles = processor.getBibliographyStyles();

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
                bstReader.parse(processor);
            } catch (FileNotFoundException e) {
                return log("bst.not.found", e.getMessage());
            }

            processor.process(writer, getLogger());

            if (errors > 0) {
                log(errors == 1 ? "error" : "errors", Long.toString(errors));
            }
            long warnings = processor.getNumberOfWarnings();
            if (warnings > 0) {
                info(warnings == 1 ? "warning" : "warnings", //
                    Long.toString(warnings));
            }

            info("runtime", Long.toString(System.currentTimeMillis() - time));

            if (funcall != null) {
                funcall.print();
            }

        } catch (ExBibImpossibleException e) {
            return logException(e, "internal.error", false);
        } catch (ExBibFileNotFoundException e) {
            getLogger().severe(e.getLocalizedMessage() + "\n");
            return EXIT_FAIL;
        } catch (ExBibException e) {
            getLogger().severe(e.getLocalizedMessage() + "\n");
            return EXIT_FAIL;
        } catch (ConfigurationWrapperException e) {
            return logException(e.getCause(), "installation.error", false);
        } catch (ConfigurationException e) {
            return logException(e, "installation.error", false);
        } catch (NoClassDefFoundError e) {
            return logException(e, "installation.error", false);
        } catch (Exception e) {
            return logException(e, "internal.error", false);
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
            fileHandler.setLevel(debug.contains(Debug.MISC)
                    ? Level.ALL
                    : Level.FINE);
            getLogger().addHandler(fileHandler);
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

        Logger logger = getLogger();
        FuncallObserver funcall = (trace ? new FuncallObserver(logger) : null);

        db.registerObserver("makeEntry",
            new EntryObserver(logger, bibliography));

        bibliography.registerObserver("step", new TracingObserver(logger,
            getBundle().getString("step_msg")));

        bibliography.registerObserver("run", new TracingObserver(logger,
            getBundle().getString("do_msg")));
        bibliography.registerObserver("step", funcall);
        bibliography.registerObserver("push", new TracingObserver(logger,
            getBundle().getString("push_msg")));
        bibliography.registerObserver("startParse", new TracingObserver(logger,
            getBundle().getString("start_parse_msg")));
        bibliography.registerObserver("endParse", new TracingObserver(logger,
            getBundle().getString("end_parse_msg")));
        return funcall;
    }

    /**
     * Setter for the Bib style
     * 
     * @param bst the bib style
     */
    public void setBst(String bst) {

        this.bst = bst;
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
    };

    /**
     * Setter for the debugging indicator.
     * 
     * @param d indicator for debugging
     */
    public void setDebug(Debug d) {

        this.debug.add(d);
    }

    /**
     * Setter for encoding.
     * 
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {

        this.encoding = encoding;
    }

    /**
     * Setter for the file name
     * 
     * @param arg the file name
     * 
     * @return EXIT_FAILURE at failure and EXIT_CONTINUE at success
     */
    private int setFile(String arg) {

        if (file != null) {
            return logBanner("one.file", file);
        } else if ("".equals(arg)) {
            return logBanner("empty.file", file);
        }
        file = arg;
        return EXIT_CONTINUE;
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
