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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.ProcessorContainer;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.auxio.AuxReader;
import org.extex.exbib.core.io.auxio.AuxReaderFactory;
import org.extex.exbib.core.io.bblio.BblWriterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.bstio.BstReaderFactory;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.io.csf.CsfReader;
import org.extex.exbib.core.io.csf.CsfSorter;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.main.cli.BooleanOption;
import org.extex.exbib.main.cli.NoArgOption;
import org.extex.exbib.main.cli.NumberOption;
import org.extex.exbib.main.cli.StringOption;
import org.extex.exbib.main.util.AbstractMain;
import org.extex.exbib.main.util.DBObserver;
import org.extex.exbib.main.util.FuncallObserver;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.exbib.main.util.MainResourceObserver;
import org.extex.exbib.main.util.TracingObserver;
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

        declareOptions();
    }

    /**
     * Declare the list of command line options.
     */
    protected void declareOptions() {

        declareCommonOptions();
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
        option("-c", "--configuration", new StringOption("opt.config") {

            @Override
            protected int run(String name, String arg) {

                setConfigSource("exbib/" + arg);
                return EXIT_CONTINUE;
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

                return setDebug(value.split("[,;: ]"));
            }
        });
        option("-e", "--encoding", new StringOption("opt.encoding") {

            @Override
            protected int run(String name, String arg) {

                return setEncoding(arg);
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
        option("-t", "--trace", new BooleanOption("opt.trace") {

            @Override
            protected int run(String arg, boolean value) {

                setTrace(value);
                return EXIT_CONTINUE;
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
     * Report an error and increment the error counter.
     * 
     * @param tag the tag of the resource bundle
     * @param args the arguments to be inserted for braced numbers
     * 
     * @return EXIT_FAILURE
     */
    protected int error(String tag, Object... args) {

        errors++;
        return log(tag, args);
    }

    /**
     * Report an exception and increment the error counter.
     * 
     * @param e the cause
     * @param tag the tag of the resource bundle
     * 
     * @return EXIT_FAILURE
     */
    protected int error(Throwable e, String tag) {

        errors++;
        return logException(e, tag, debug.contains(Debug.MISC));
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
        Configuration config = ConfigurationFactory.newInstance(configSource);
        ResourceFinder finder =
                new ResourceFinderFactory().createResourceFinder(config
                    .getConfiguration("Resource"), getLogger(), System
                    .getProperties(), null);
        if (debug.contains(Debug.SEARCH)) {
            finder.enableTracing(true);
        }
        if (file == null) {
            return logBanner("missing.file");
        }
        file = stripExtension(file, AUX_FILE_EXTENSION);

        attachFileLogger(file, BLG_FILE_EXTENSION);
        logBanner();

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

        long warnings = 0;

        try {
            FuncallObserver funcall = null;

            BibReaderFactory bibReaderFactory = new BibReaderFactory(//
                config.getConfiguration("BibReader"), finder);
            if (bibEncoding != null) {
                bibReaderFactory.setEncoding(bibEncoding);
            }
            ProcessorContainer container = new ProcessorContainer(config);
            container.setMinCrossrefs(minCrossrefs);
            container.setSorter(sorter);
            container.setBibReaderFactory(bibReaderFactory);
            container.registerObserver("startRead", new DBObserver(getLogger(),
                getBundle().getString("observer.db.pattern")));
            if (trace) {
                funcall = runRegisterTracers(container);
            }

            AuxReader auxReader = new AuxReaderFactory(//
                config.getConfiguration("AuxReader")).newInstance(finder);
            auxReader.register(new MainResourceObserver(getLogger()));

            try {
                auxReader.load(container, file, encoding);
            } catch (FileNotFoundException e) {
                return error("aux.not.found", e.getMessage());
            }

            if (!validate(container)) {
                return EXIT_FAIL;
            }

            if (bst != null) {
                Processor processor = container.findBibliography("bbl");
                processor.addBibliographyStyle(stripExtension(bst,
                    BST_FILE_EXTENSION));
            }
            BblWriterFactory bblWriterFactory =
                    new BblWriterFactory(config.getConfiguration("BblWriter"),
                        encoding) {

                        /**
                         * {@inheritDoc}
                         * 
                         * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoDiscarted()
                         */
                        @Override
                        protected void infoDiscarted() {

                            info("output.discarted");
                        }

                        /**
                         * {@inheritDoc}
                         * 
                         * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoOutput(java.lang.String)
                         */
                        @Override
                        protected void infoOutput(String file) {

                            info("output.file", outfile);
                        }

                        /**
                         * {@inheritDoc}
                         * 
                         * @see org.extex.exbib.core.io.bblio.BblWriterFactory#infoStdout()
                         */
                        @Override
                        protected void infoStdout() {

                            info("output.to.stdout");
                        }

                    };
            BstReaderFactory bstReaderFactory =
                    new BstReaderFactory(config.getConfiguration("BstReader"),
                        finder);

            for (String key : container) {
                Processor processor = container.getProcessor(key);

                for (String style : processor.getBibliographyStyles()) {
                    info("bst.file", stripExtension(style, BST_FILE_EXTENSION));
                }
                bstReaderFactory.newInstance().parse(processor);

                Writer writer;
                try {
                    if (outfile == null || !"bbl".equals(key)) {
                        outfile = file + "." + key;
                    }
                    writer = bblWriterFactory.newInstance(outfile);
                } catch (FileNotFoundException e) {
                    return error("output.could.not.be.opened", outfile);
                }
                try {
                    warnings += processor.process(writer, getLogger());
                } finally {
                    writer.close();
                }
            }

            info("runtime", Long.toString(System.currentTimeMillis() - time));

            if (funcall != null) {
                funcall.print();
            }

        } catch (ExBibImpossibleException e) {
            return error(e, "internal.error");
        } catch (ExBibException e) {
            return error("verbatim", e.getLocalizedMessage());
        } catch (ConfigurationWrapperException e) {
            return error(e.getCause(), "installation.error");
        } catch (ConfigurationException e) {
            return error(e, "installation.error");
        } catch (NoClassDefFoundError e) {
            return error(e, "installation.error");
        } catch (Exception e) {
            return error(e, "internal.error");
        } finally {
            if (errors > 0) {
                log(errors == 1 ? "error" : "errors", Long.toString(errors));
            }
            if (warnings > 0) {
                info(warnings == 1 ? "warning" : "warnings", //
                    Long.toString(warnings));
            }
        }

        return errors > 0 ? EXIT_FAIL : EXIT_OK;
    }

    /**
     * Register observers to get tracing output.
     * 
     * @param container the processor container
     * 
     * @return the function call observer
     * 
     * @throws NotObservableException in case of an unknown observer name
     * @throws ExBibIllegalValueException in case of an illegal value
     */
    private FuncallObserver runRegisterTracers(ProcessorContainer container)
            throws NotObservableException,
                ExBibIllegalValueException {

        Logger logger = getLogger();
        FuncallObserver funcall = (trace ? new FuncallObserver(logger) : null);

        // db.registerObserver("makeEntry",
        // new EntryObserver(logger, bibliography));
        // TODO reactivate

        container.registerObserver("step", new TracingObserver(logger,
            getBundle().getString("step_msg")));

        container.registerObserver("run", new TracingObserver(logger,
            getBundle().getString("do_msg")));
        container.registerObserver("step", funcall);
        container.registerObserver("push", new TracingObserver(logger,
            getBundle().getString("push_msg")));
        container.registerObserver("startParse", new TracingObserver(logger,
            getBundle().getString("start_parse_msg")));
        container.registerObserver("endParse", new TracingObserver(logger,
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
    }

    /**
     * Setter for the debugging indicator.
     * 
     * @param d indicator for debugging
     */
    public void setDebug(Debug d) {

        this.debug.add(d);
    };

    /**
     * Setter for the debugging indicator.
     * 
     * @param value indicator for debugging
     * 
     * @return EXIT_CONTINUE if everything went through
     */
    public int setDebug(String... value) {

        for (String s : value) {
            try {
                setDebug(Debug.valueOf(s.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return logBanner("debug.mode.unknown", s);
            }
        }
        return EXIT_CONTINUE;
    }

    /**
     * Setter for encoding.
     * 
     * @param encoding the encoding to set
     * 
     * @return EXIT_CONTINUE
     */
    public int setEncoding(String encoding) {

        this.encoding = encoding;
        return EXIT_CONTINUE;
    }

    /**
     * Setter for the file name
     * 
     * @param arg the file name
     * 
     * @return EXIT_FAILURE at failure and EXIT_CONTINUE at success
     */
    @Override
    protected int setFile(String arg) {

        if (file != null) {
            return logBanner("one.file", file);
        } else if ("".equals(arg)) {
            return logBanner("empty.file", file);
        }
        file = arg;
        return EXIT_CONTINUE;
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
    @Override
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

    /**
     * Validate the container after a read.
     * 
     * @param container the container
     * 
     * @return <code>true</code> iff everything is fine
     * 
     * @throws ExBibException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     */
    private boolean validate(ProcessorContainer container)
            throws ConfigurationException,
                ExBibException {

        if (container.isEmpty()) {
            error("bst.missing", file);
            error("data.missing", file);
            error("citation.missing", file);
            return false;
        }

        for (String key : container) {
            Processor p = container.findBibliography(key);
            if (p.countBibliographyStyles() == 0) {
                error("bst.missing.in", key, file);
            }
            if (p.countDatabases() == 0) {
                error("data.missing.in", key, file);
            }
            if (p.countCitations() == 0) {
                error("citation.missing.in", key, file);
            }
        }
        return errors <= 0;
    }

}
