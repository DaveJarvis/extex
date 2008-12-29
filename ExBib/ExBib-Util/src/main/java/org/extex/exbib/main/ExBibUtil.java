/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.cli.NoArgOption;
import org.extex.cli.StringOption;
import org.extex.cli.StringPropertyOption;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.cli.exception.UnusedArgumentCliException;
import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.Processor;
import org.extex.exbib.core.ProcessorContainer;
import org.extex.exbib.core.db.sorter.SorterFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;
import org.extex.exbib.core.io.StreamWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.auxio.AuxReader;
import org.extex.exbib.core.io.auxio.AuxReaderFactory;
import org.extex.exbib.core.io.bibio.BibPrinter;
import org.extex.exbib.core.io.bibio.BibPrinterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.util.ResourceObserverImpl;
import org.extex.exbib.main.util.AbstractMain;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * ExBibUtil a bibliography.
 * 
 * 
 * <p>
 * The following options are supported:
 * </p>
 * <dl>
 * <dt>-[-] &lang;file&rang;</dt>
 * <dd>Use this argument as file name &ndash; even when it looks like an option.
 * </dd>
 * <dt>--au[xfile] | --ex[tract] | -x &lang;file&rang;</dt>
 * <dd>Use this argument as file name of an aux file to get databases and
 * citations from.</dd>
 * <dt>--b[ib-encoding] | --bib.[encoding] | -E &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the bib files.</dd>
 * <dt>--c[onfiguration] | -c &lang;configuration&rang;</dt>
 * <dd>Use the configuration given. This is not a file!</dd>
 * <dt>--cop[ying]</dt>
 * <dd>Display the copyright conditions.</dd>
 * <dt>--e[ncoding] | -e &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the output file.</dd>
 * <dt>--h[elp] | -? | -h</dt>
 * <dd>Show a short list of command line arguments.</dd>
 * <dt>--la[nguage] | -L &lang;language&rang;</dt>
 * <dd>Use the named language for message.</dd>
 * <dt>\tThe argument is a two-letter ISO code.</dd>
 * <dt>--loa[d] &lang;file&rang;</dt>
 * <dd>Additionally load settings from the file given.</dd>
 * <dt>--l[ogfile] | -l &lang;file&rang;</dt>
 * <dd>Send the output to the log file named instead of the default one.</dd>
 * <dt>--o[utfile] | --outp[ut] | -o &lang;file&rang;</dt>
 * <dd>Redirect the output to the file given. <br /> The file name - can be used
 * to redirect to stdout <br /> The empty file name can be used to discard the
 * output completely</dd>
 * <dt>--p[rogname] | --progr[am-name] | --program.[name] | -p
 * &lang;program&rang;</dt>
 * <dd>Set the program name for messages.</dd>
 * <dt>--q[uiet] | --t[erse] | -q</dt>
 * <dd>Act quietly; some informative messages are suppressed.</dd>
 * <dt>--r[elease]</dt>
 * <dd>Print the release number and exit.</dd>
 * <dt>--ty[pe] | -t &lang;type&rang;</dt>
 * <dd>Use the given type as output format (e.g. bib, xml).</dd>
 * <dt>--v[erbose] | -v</dt>
 * <dd>Act verbosely; some additional informational messages are displayed.</dd>
 * <dt>--vers[ion]</dt>
 * <dd>Print the version information and exit.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public final class ExBibUtil extends AbstractMain {

    /**
     * The field <tt>LOG_EXTENSION</tt> contains the extension for the log file.
     */
    private static final String LOG_EXTENSION = ".blg";

    /**
     * The field <tt>PROGNAME</tt> contains the name of the program.
     */
    private static final String PROGNAME = "exbibutil";

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
     * The field <tt>PROP_BIB_ENCODING</tt> contains the name of the encoding
     * for bib files.
     */
    private static final String PROP_BIB_ENCODING = "exbib.bib.encoding";

    /**
     * The field <tt>PROP_ENCODING</tt> contains the name of the property to
     * carry the encoding.
     */
    public static final String PROP_ENCODING = "exbib.encoding";

    /**
     * The field <tt>PROP_CONFIG</tt> contains the name of the property to carry
     * the configuration.
     */
    protected static final String PROP_CONFIG = "exbib.config";

    /**
     * The field <tt>PROP_TYPE</tt> contains the name for the property for the
     * printer type.
     */
    private static final String PROP_TYPE = "exbib.type";

    /**
     * The field <tt>PROP_FILE</tt> contains the name for the property for the
     * aux file.
     */
    private static final String PROP_FILE = "exbib.file";

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
        } catch (Exception e) {
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
     * Creates a new object.
     * 
     * @throws IOException in case of an I/O error while reading the dot file
     */
    public ExBibUtil() throws IOException {

        this(System.getProperties());
    }

    /**
     * Creates a new object.
     * 
     * @param properties the properties with the settings
     * 
     * @throws IOException in case of an I/O error
     */
    public ExBibUtil(Properties properties) throws IOException {

        super(PROGNAME, VERSION, INCEPTION_YEAR, ".exbib", properties);
        propertyDefault(PROP_CONFIG, "exbib");
        propertyDefault(PROP_TYPE, "bib");
        declareOptions();
    }

    /**
     * Declare the list of command line options.
     * 
     */
    protected void declareOptions() {

        declareCommonOptions();
        declareOption(null, new NoArgOption(null) {

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

                throw new UnknownOptionCliException(a);
            }

        });
        option("-E", "--bib.encoding", new StringPropertyOption(
            "opt.bib.encoding", PROP_BIB_ENCODING, getProperties()),
            "--bib-encoding");
        option("-c", "--configuration", new StringPropertyOption("opt.config",
            PROP_CONFIG, getProperties()));
        option("-e", "--encoding", new StringPropertyOption("opt.encoding",
            PROP_ENCODING, getProperties()));
        option("-t", "--type", new StringPropertyOption("opt.type", PROP_TYPE,
            getProperties()));
        option("-x", "--auxfile", new StringOption("opt.auxfile") {

            @Override
            protected int run(String name, String file) {

                if (getProperty(PROP_FILE) != null) {
                    return logBanner("one.file", file);
                } else if ("".equals(file)) {
                    return logBanner("empty.file", file);
                }
                setProperty(PROP_FILE, file);
                return EXIT_CONTINUE;
            }

        }, "--extract");
    }

    /**
     * Make a sorter or throw an error.
     * 
     * @param finder the resource finder
     * @param cfg the configuration
     * 
     * @return the sorter; it can be <code>null</code> if none is required
     */
    protected SorterFactory makeSorterFactory(ResourceFinder finder,
            Configuration cfg) {

        SorterFactory sorterFactory = new SorterFactory(cfg);
        sorterFactory.enableLogging(getLogger());
        sorterFactory.setResourceFinder(finder);
        sorterFactory.setProperties(getProperties());
        return sorterFactory;
    }

    /**
     * Initialize the database from an aux file.
     * 
     * @param configuration the configuration
     * @param finder the resource finder
     * @param container the bibliography
     * 
     * @return <code>true</code> iff everything went through
     * 
     * @throws IOException in case of an I/O error
     * @throws ConfigurationException in case of a configuration problem
     * @throws ExBibException in case of an error
     */
    protected boolean readAux(Configuration configuration,
            ResourceFinder finder, ProcessorContainer container)
            throws ConfigurationException,
                IOException,
                ExBibException {

        AuxReader auxReader = new AuxReaderFactory(//
            configuration.getConfiguration("AuxReader")).newInstance(finder);
        auxReader.register(new ResourceObserverImpl(getLogger()));

        try {
            auxReader.load(container, getProperty(PROP_FILE),
                getProperty(PROP_ENCODING));
        } catch (FileNotFoundException e) {
            log("aux.not.found", e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.util.AbstractMain#run()
     */
    @Override
    protected int run() throws IOException {

        long time = System.currentTimeMillis();

        attachFileLogger(null, LOG_EXTENSION);
        logBanner();

        Writer writer = null;

        String outfile = getProperty(ExBib.PROP_OUTFILE);
        // strange dependency
        try {
            if (outfile == null) {
                writer =
                        new StreamWriter(System.out, getProperty(PROP_ENCODING));
                info("output.to.stdout");
            } else if (outfile.equals("")) {
                info("output.discarted");
                writer = new NullWriter();
            } else if (outfile.equals("-")) {
                info("output.to.stdout");
                writer =
                        new StreamWriter(System.out, getProperty(PROP_ENCODING));
            } else {
                info("output.file", outfile);
                writer = new StreamWriter(outfile, getProperty(PROP_ENCODING));
            }
        } catch (FileNotFoundException e) {
            return log("output.could.not.be.opened", outfile);
        } catch (UnsupportedEncodingException e) {
            return log("unknown.encoding", getProperty(PROP_ENCODING));
        }

        try {
            Configuration configuration =
                    ConfigurationFactory.newInstance("exbib/"
                            + getProperty(PROP_CONFIG));
            ResourceFinder finder =
                    new ResourceFinderFactory().createResourceFinder(
                        configuration.getConfiguration("Resource"),
                        getLogger(), System.getProperties(), null);
            BibReaderFactory bibReaderFactory =
                    new BibReaderFactory(configuration
                        .getConfiguration("BibReader"), finder,
                        getProperty(PROP_BIB_ENCODING),
                        getProperty(PROP_ENCODING));
            ProcessorContainer container =
                    new ProcessorContainer(configuration, getLogger(),
                        getProperties());
            container.setSorterFactory(makeSorterFactory(finder, configuration
                .getConfiguration("Sorter")));
            container.setBibReaderFactory(bibReaderFactory);
            container.setMinCrossrefs(Integer.MAX_VALUE);

            Processor bibliography = container.findProcessor(null);
            if (getProperty(PROP_FILE) == null) {
                bibliography.addCitation("*");
            } else if (!readAux(configuration, finder, container)) {
                return EXIT_FAIL;
            }

            for (String file : files) {
                bibliography.addBibliographyDatabase(file);
            }
            bibliography.loadDatabases();

            for (String key : container) {

                // TODO make a new writer for each iteration

                BibPrinter printer;
                String type = getProperty(PROP_TYPE);
                try {
                    printer =
                            new BibPrinterFactory(configuration
                                .getConfiguration("BibPrinter")).newInstance(
                                type, writer);
                } catch (ConfigurationNotFoundException e) {
                    return log("unknown.type", type);
                }
                printer.print(container.getProcessor(key).getDB());
            }

        } catch (ConfigurationWrapperException e) {
            return logException(e.getCause(), "installation.error", false);
        } catch (ConfigurationException e) {
            throw e;
        } catch (FileNotFoundException e) {
            return log("bib.not.found", e.getMessage());
        } catch (Exception e) {
            return log("error.format", getProgramName(), e.toString());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                log("error.format", getProgramName(), e.toString());
            }
        }

        info("runtime", Long.toString(System.currentTimeMillis() - time));
        return EXIT_OK;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.util.AbstractMain#setFile(java.lang.String)
     */
    @Override
    protected int setFile(String arg) {

        if (!"".equals(arg)) {
            files.add(arg);
        }
        return EXIT_CONTINUE;
    }

}
