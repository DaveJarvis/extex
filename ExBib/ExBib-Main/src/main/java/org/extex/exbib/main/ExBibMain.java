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

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.ExBib.Debug;
import org.extex.exbib.main.cli.BooleanOption;
import org.extex.exbib.main.cli.CLI;
import org.extex.exbib.main.cli.NoArgOption;
import org.extex.exbib.main.cli.NoArgPropertyOption;
import org.extex.exbib.main.cli.NumberOption;
import org.extex.exbib.main.cli.StringOption;
import org.extex.exbib.main.cli.StringPropertyOption;
import org.extex.exbib.main.util.AbstractMain;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.framework.configuration.exception.ConfigurationException;

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
 * <dt>-D&lang;property&rang;=&lang;value&rang;</dt>
 * <dd>Set the property to a given value.</dd>
 * <dt>-[-] &lang;file&rang;</dt>
 * <dd>Use this argument as file name -- even when it looks like an option.</dd>
 * <dt>--a[vailableCharsets] | -a</dt>
 * <dd>List the available encoding names and exit.</dd>
 * <dt>--bib-[encoding] | --bib.[encoding] | -E &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the bib files.</dd>
 * <dt>--c[onfig] | -c &lang;configuration&rang;</dt>
 * <dd>Use the configuration given. This is not a file!</dd>
 * <dt>--cop[ying]</dt>
 * <dd>Display the copyright conditions.</dd>
 * <dt>--cs[file] &lang;csfile&rang;</dt>
 * <dd>Name the csf for defining characters and the sort order</dd>
 * <dt>--csf-[encoding] | --csf.[encoding] &lang;enc&rang;</dt>
 * <dd>Use the given encoding for the csf files.</dd>
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
 * <dt>--b[ibtex] | --s[trict]</dt>
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
public class ExBibMain extends AbstractMain {

    /**
     * The field <tt>CONFIGURATION_0_99</tt> contains the name of the
     * configuration to be used in strict mode.
     */
    private static final String CONFIGURATION_0_99 = "bibtex099";

    /**
     * The main program. The command line parameters are evaluated and the
     * appropriate actions are performed. Exceptions are caught and reported.
     * 
     * @param argv list of command line parameters
     * 
     * @return the exit code
     */
    protected static int commandLine(String[] argv) {

        AbstractMain main = null;
        try {
            main = new ExBibMain();
            return main.processCommandLine(argv);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(ExBibMain.class.getName());
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.ALL);
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LogFormatter());
            consoleHandler.setLevel(Level.WARNING);
            logger.addHandler(consoleHandler);
            logger.severe(e.toString());
            return EXIT_FAIL;
        } finally {
            if (main != null) {
                main.close();
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
     * The field <tt>exBib</tt> contains the enclosed object.
     */
    private ExBib exBib;

    /**
     * Creates a new object.
     * 
     * @throws IOException in case of an I/O error while reading the dot file
     */
    public ExBibMain() throws IOException {

        this(System.getProperties());
    }

    /**
     * Creates a new object.
     * 
     * @param properties the properties with the settings
     * 
     * @throws IOException in case of an I/O error while reading the dot file
     */
    public ExBibMain(Properties properties) throws IOException {

        super("exbib", ExBib.VERSION, ExBib.INCEPTION_YEAR, ".exbib",
            properties);

        this.exBib = new ExBib(getProperties()) {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.ExBib#logBanner(java.lang.String,
             *      java.lang.Object[])
             */
            @Override
            protected boolean logBanner(String tag, Object... args) {

                return ExBibMain.this.logBanner(tag, args) != CLI.EXIT_FAIL;
            }

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.exbib.core.ExBib#recognizeFile(java.lang.String,
             *      java.lang.String)
             */
            @Override
            protected void recognizeFile(String log, String extension)
                    throws IOException {

                attachFileLogger(log, extension);
            }

        };
        exBib.setLogger(getLogger());

        declareOptions();
    }

    /**
     * Declare the list of command line options.
     */
    protected void declareOptions() {

        declareCommonOptions();
        Properties properties = getProperties();
        option(null, "--strict", new NoArgPropertyOption("opt.strict",
            ExBib.PROP_CONFIG, CONFIGURATION_0_99, properties), "--bibtex");
        option("-E", "--bib.encoding", new StringPropertyOption(
            "opt.bib.encoding", ExBib.PROP_BIB_ENCODING, properties),
            "--bib-encoding");
        option("-c", "--configuration", new StringPropertyOption("opt.config",
            ExBib.PROP_CONFIG, properties));
        option(null, "--csfile", new StringPropertyOption("opt.csfile",
            ExBib.PROP_CSF, properties));
        option(null, "--csf.encoding", new StringPropertyOption(
            "opt.csf.encoding", ExBib.PROP_CSF_ENCODING, properties),
            "--csf-encoding");
        option("-d", "--debug", new StringOption("opt.debug") {

            @Override
            protected int run(String name, String value) {

                return exBib.setDebug(value.split("[,;: ]"))
                        ? CLI.EXIT_CONTINUE
                        : CLI.EXIT_FAIL;
            }
        });
        option("-e", "--encoding", new StringPropertyOption("opt.encoding",
            ExBib.PROP_ENCODING, properties));
        option("-M", "--min.crossrefs", new NumberOption("opt.min.crossref") {

            @Override
            protected int run(String name, int arg) {

                exBib.setMinCrossrefs(arg);
                return EXIT_CONTINUE;
            }
        }, "--min-crossrefs", "--min_crossrefs");
        option("-o", "--output", new StringPropertyOption("opt.output",
            PROP_OUTFILE, properties), "--outfile");
        option(
            "-p",
            "--progname", //
            new StringPropertyOption("opt.progname", PROP_PROGNAME, properties),
            "--program.name", "--program-name");
        option("-t", "--trace", new BooleanOption("opt.trace") {

            @Override
            protected int run(String arg, boolean value) {

                exBib.setTrace(value);
                return EXIT_CONTINUE;
            }
        });
        option("-7",
            "--traditional", //
            new NoArgPropertyOption("opt.7.bit", ExBib.PROP_CSF, "", properties));
        option("-8", "--8bit", //
            new NoArgPropertyOption("opt.8.bit", ExBib.PROP_CSF,
                "88591lat.csf", properties));
        option("-B", "--big", new NoArgOption(null) {

            @Override
            protected int run(String arg) {

                info("ignore.option", arg);
                return EXIT_CONTINUE;
            }
        }, "-H", "--huge", "-W", "--wolfgang");
        option(null, "--mcites",
            new NumberOption(null) {

                @Override
                protected int run(String arg, int value) {

                    info("ignore.option", arg);
                    return EXIT_CONTINUE;
                }
            }, //
            "--mentints", "-mentstrs", "--mfields", "--mpool", "--mstrings",
            "--mwizfuns");
    }

    /**
     * Getter for the debug description.
     * 
     * @return the value
     * 
     * @see org.extex.exbib.core.ExBib#getDebug()
     */
    public Set<Debug> getDebug() {

        return exBib.getDebug();
    }

    /**
     * Getter for min.crossrefs.
     * 
     * @return the value
     * 
     * @see org.extex.exbib.core.ExBib#getMinCrossrefs()
     */
    public int getMinCrossrefs() {

        return exBib.getMinCrossrefs();
    }

    /**
     * Getter for the trace indicator.
     * 
     * @return the trace indicator
     * 
     * @see org.extex.exbib.core.ExBib#isTrace()
     */
    public boolean isTrace() {

        return exBib.isTrace();
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
     * 
     * @see org.extex.exbib.main.util.AbstractMain#run()
     */
    @Override
    public int run() throws IOException, ConfigurationException {

        logBanner();
        return exBib.run() ? CLI.EXIT_OK : CLI.EXIT_FAIL;
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

        return exBib.setFile(arg) ? CLI.EXIT_CONTINUE : CLI.EXIT_FAIL;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.util.AbstractMain#useLanguage(java.util.Locale)
     */
    @Override
    protected void useLanguage(Locale locale) {

        super.useLanguage(locale);
        exBib.useLanguage(locale);
    }

}
