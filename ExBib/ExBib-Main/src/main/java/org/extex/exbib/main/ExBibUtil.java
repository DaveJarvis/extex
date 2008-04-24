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
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.DBFactory;
import org.extex.exbib.core.io.NullWriter;
import org.extex.exbib.core.io.StreamWriter;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.bibio.BibPrinterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.main.cli.NoArgOption;
import org.extex.exbib.main.cli.StringOption;
import org.extex.exbib.main.cli.exception.UnknownOptionCliException;
import org.extex.exbib.main.cli.exception.UnusedArgumentCliException;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
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
 * <dd>Use this argument as file name -- even when it looks like an option.</dd>
 * <dt>--b[ib-encoding] | --bib.[encoding] | -E &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the bib files.</dd>
 * <dt>--c[onfig] | -c &lang;configuration&rang;</dt>
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
 * <dt>--l[ogfile] | -l &lang;file&rang;</dt>
 * <dd>Send the output to the log file named instead of the default one.</dd>
 * <dt>--o[utfile] | --outp[ut] | -o &lang;file&rang;</dt>
 * <dd>Redirect the output to the file given. <br />
 * The file name - can be used to redirect to stdout <br />
 * The empty file name can be used to discard the output completely</dd>
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
     * The field <tt>config</tt> contains the name of the configuration to
     * use.
     */
    private String config = "exbib";

    /**
     * The field <tt>type</tt> contains the type of the output driver.
     */
    private String type = "bib";

    /**
     * The field <tt>encoding</tt> contains the encoding or <code>null</code>
     * for the default encoding.
     */
    private String encoding = null;

    /**
     * The field <tt>bibEncoding</tt> contains the encoding for bib files.
     */
    private String bibEncoding = null;

    /**
     * Creates a new object.
     */
    public ExBibUtil() {

        super(PROGNAME, VERSION, INCEPTION_YEAR);

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
        declareOption("", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                return EXIT_CONTINUE;
            }

        });
        option("-", "--", new StringOption("opt.1.file") {

            @Override
            protected int run(String name, String arg) {

                files.add(arg);
                return EXIT_CONTINUE;
            }

        });
        option(null, "--availableCharsets", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                Logger logger = getLogger();
                for (String s : Charset.availableCharsets().keySet()) {
                    logger.severe(s + "\n");
                }

                return EXIT_FAIL;
            }

        });
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

                setConfig(arg);
                return EXIT_CONTINUE;
            }

        });
        option(null, "--copying", new NoArgOption("opt.copying") {

            @Override
            protected int run(String name) {

                return logCopying(getLogger());
            }

        });
        option("-e", "--encoding", new StringOption("opt.encoding") {

            @Override
            protected int run(String name, String arg) {

                encoding = arg;
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

                boolean debug = false;
                if (arg != null && !arg.equals("")) {
                    Handler fileHandler;
                    try {
                        fileHandler = new FileHandler(arg);
                    } catch (SecurityException e) {
                        throw new ConfigurationWrapperException(e);
                    } catch (IOException e) {
                        throw new ConfigurationWrapperException(e);
                    }
                    fileHandler.setFormatter(new LogFormatter());
                    fileHandler.setLevel(debug ? Level.ALL : Level.FINE);
                    getLogger().addHandler(fileHandler);
                }
                return EXIT_CONTINUE;
            }
        });
        option("-L", "--language", new StringOption("opt.language") {

            @Override
            protected int run(String name, String arg) {

                Locale.setDefault(new Locale(arg));
                setBundle(ResourceBundle.getBundle(ExBibUtil.class.getName()));
                return EXIT_CONTINUE;
            }

        });
        option("-o", "--output", new StringOption("opt.output") {

            @Override
            protected int run(String name, String arg) {

                outfile = arg;
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

                getLogger().severe(VERSION + "\n");
                return EXIT_FAIL;
            }

        });
        option("-t", "--type", new StringOption("opt.type") {

            @Override
            protected int run(String arg, String value) {

                type = value;
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
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.AbstractMain#run()
     */
    @Override
    protected int run() {

        long time = System.currentTimeMillis();
        logBanner(false);

        Writer writer = null;

        try {
            if (outfile == null) {
                writer = new StreamWriter(System.out, encoding);
                info("output.to.stdout");
            } else if (outfile.equals("")) {
                info("output.discarted");
                writer = new NullWriter(null);
            } else if (outfile.equals("-")) {
                info("output.to.stdout");
                writer = new StreamWriter(System.out, encoding);
            } else {
                info("output.file", outfile);
                writer = new StreamWriter(outfile, encoding);
            }
        } catch (FileNotFoundException e) {
            log("output.could.not.be.opened", outfile);
            return EXIT_FAIL;
        } catch (UnsupportedEncodingException e) {
            log("unknown.encoding", encoding);
            return EXIT_FAIL;
        }

        try {
            Configuration configuration =
                    ConfigurationFactory.newInstance("exbib/" + config);
            Configuration cfg =
                    configuration.getConfiguration("BibPrinter")
                        .findConfiguration(type);
            if (cfg == null) {
                return log("unknown.type", type);
            }
            ResourceFinder finder =
                    new ResourceFinderFactory().createResourceFinder(
                        configuration.getConfiguration("Resource"),
                        getLogger(), System.getProperties(), null);
            BibReaderFactory bibReaderFactory = new BibReaderFactory(//
                configuration.getConfiguration("BibReader"), finder);
            if (bibEncoding != null) {
                bibReaderFactory.setEncoding(bibEncoding);
            }
            DB db = new DBFactory(configuration.getConfiguration("DB"))//
                .newInstance(bibReaderFactory, Integer.MAX_VALUE);
            int i = 1;
            for (String file : files) {
                info("observer.db.pattern", Integer.valueOf(i++), file);
                db.load(file, null);
            }

            new BibPrinterFactory(cfg).newInstance(writer, encoding).print(db);

            writer.close();

        } catch (ConfigurationWrapperException e) {
            return logException(e.getCause(), "installation.error", false);
        } catch (ConfigurationException e) {
            return logException(e, "installation.error", false);
        } catch (FileNotFoundException e) {
            return log("bib.not.found", e.getMessage());
        } catch (Exception e) {
            log("error.format", getProgramName(), e.toString());
            return EXIT_FAIL;
        }

        info("runtime", Long.toString(System.currentTimeMillis() - time));

        return EXIT_OK;
    }

    /**
     * Setter for the configuration.
     * 
     * @param config the configuration to set
     */
    public void setConfig(String config) {

        this.config = config;
    }

}
