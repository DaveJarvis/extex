/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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
import java.util.logging.Logger;

import org.extex.cli.BooleanOption;
import org.extex.cli.CLI;
import org.extex.cli.NoArgOption;
import org.extex.cli.NoArgPropertyOption;
import org.extex.cli.NumberOption;
import org.extex.cli.NumberPropertyOption;
import org.extex.cli.StringOption;
import org.extex.cli.StringPropertyOption;
import org.extex.cli.exception.UnknownOptionCliException;
import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.ExBib.ExBibDebug;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * This class contains the main program for <logo>&epsilon;&chi;Bib</logo>.
 * <p>
 * </p>
 * <p>
 * Usage: <tt>java org.extex.exbib.main.ExBib </tt><i>&lt;options&gt; file</i>
 * </p>
 * <p>
 * The following options are supported:
 * </p>
 * <dl>
 * <dt><tt>-D&lt;property&gt;=&lt;value&gt;</tt></dt>
 * <dd>Set the property to a given value.</dd>
 * <dt><tt>-[-] &lt;file&gt;</tt></dt>
 * <dd>Use this argument as file name &ndash; even when it looks like an option.
 * </dd>
 * <dt><tt>--a[vailableCharsets] | -a</tt></dt>
 * <dd>List the available encoding names and exit.</dd>
 * <dt><tt>--bib-[encoding] | --bib.[encoding] | -E &lt;enc&gt;</tt></dt>
 * <dd>Use the given encoding for the bib files.</dd>
 * <dt><tt>--c[onfig] | -c &lt;configuration&gt;</tt></dt>
 * <dd>Use the configuration given. This is not a file!</dd>
 * <dt><tt>--cop[ying]</tt></dt>
 * <dd>Display the copyright conditions.</dd>
 * <dt><tt>--cs[file] &lt;csfile&gt;</tt></dt>
 * <dd>Name the csf for defining characters and the sort order</dd>
 * <dt><tt>--csf-[encoding] | --csf.[encoding] &lt;enc&gt;</tt></dt>
 * <dd>Use the given encoding for the csf files.</dd>
 * <dt><tt>--d[ebug] | -d</tt></dt>
 * <dd>Run in debug mode.</dd>
 * <dt><tt>--e[ncoding] | -e &lt;enc&gt;</tt></dt>
 * <dd>Use the given encoding for the output file.</dd>
 * <dt><tt>--h[elp] | -? | -h</tt></dt>
 * <dd>Show a short list of command line arguments.</dd>
 * <dt><tt>--la[nguage] | -L &lt;language></tt></dt>
 * <dd>Use the named language for message. The argument is a two-letter ISO
 * code.</dd>
 * <dt><tt>--loa[d] &lt;file&gt;</tt></dt>
 * <dd>Additionally load settings from the file given.</dd>
 * <dt><tt>--l[ogfile] | -l &lt;file&gt;</tt></dt>
 * <dd>Send the output to the log file named instead of the default one.</dd>
 * <dt><tt>--m[in-crossrefs] | --min.[crossrefs] | --min_[crossrefs] | -m
 * &lt;n&gt;</tt></dt>
 * <dd>Set the value for min.crossrefs. The default is 2.</dd>
 * <dt><tt>--o[utfile] | --outp[ut] | -o &lt;file&gt;</tt></dt>
 * <dd>Redirect the output to the file given. <br />
 * The file name - can be used to redirect to stdout <br />
 * The empty file name can be used to discard the output completely</dd>
 * <dt><tt>--p[rogname] | --progr[am-name] | --program.[name] | -p
 * &lt;program&gt;</tt></dt>
 * <dd>Set the program name for messages.</dd>
 * <dt><tt>--q[uiet] | --t[erse] | -q</tt></dt>
 * <dd>Act quietly; some informative messages are suppressed.</dd>
 * <dt><tt>--r[elease] | -r</tt></dt>
 * <dd>Print the release number and exit.</dd>
 * <dt><tt>--so[rter] | -s &lt;sort&gt;</tt></dt>
 * <dd>Use the specified sorter, e.g. <tt>locale:de</tt> or <tt>csf:ascii.</tt></dd>
 * <dt><tt>--b[ibtex] | --s[trict]</tt></dt>
 * <dd>Use the configuration for BibTeX 0.99c.</dd>
 * <dt><tt>--tr[ace] | -t</tt></dt>
 * <dd>Show a detailed trace of many operations.</dd>
 * <dt><tt>--v[erbose] | -v</tt></dt>
 * <dd>Act verbosely; some additional informational messages are displayed.</dd>
 * <dt><tt>--vers[ion]</tt></dt>
 * <dd>Print the version information and exit.</dd>
 * </dl>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibMain extends AbstractMain {

    /**
     * The field <tt>CONFIGURATION_0_99</tt> contains the name of the
     * configuration to be used in strict mode.
     */
    private static final String CONFIGURATION_0_99 = "bibtex099";

    /**
     * The main program. The command line parameters are evaluated and the
     * appropriate actions are performed. Exceptions are caught and reported to
     * the logger with the name of this class.
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
            Logger logger = makeLogger(ExBibMain.class.getName());
            makeConsoleHandler(logger);
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

                ExBibMain.this.logBanner();
                return super.logBanner(tag, args);
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
            ExBib.PROP_SORT, properties) {

            @Override
            protected int run(String a, String arg)
                    throws UnknownOptionCliException {

                set("csf:" + arg);
                return CLI.EXIT_CONTINUE;
            }
        });
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
        option("-M", "--min.crossrefs", new NumberPropertyOption(
            "opt.min.crossref", ExBib.PROP_MIN_CROSSREF, properties),
            "--min-crossrefs", "--min_crossrefs");
        option("-o", "--output", new StringPropertyOption("opt.output",
            ExBib.PROP_OUTFILE, properties), "--outfile");
        option(
            "-p",
            "--progname", //
            new StringPropertyOption("opt.progname", PROP_PROGNAME, properties),
            "--program.name", "--program-name");
        option("-s", "--sorter", new StringPropertyOption("opt.sorter",
            ExBib.PROP_SORT, properties));
        option("-t", "--trace", new BooleanOption("opt.trace") {

            @Override
            protected int run(String arg, boolean value) {

                exBib.setDebug("trace");
                return EXIT_CONTINUE;
            }
        });
        option("-7", "--traditional", //
            new NoArgPropertyOption("opt.7.bit", ExBib.PROP_SORT, "csf:",
                properties));
        option("-8", "--8bit", //
            new NoArgPropertyOption("opt.8.bit", ExBib.PROP_SORT,
                "csf:88591lat.csf", properties));
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
    public Set<ExBibDebug> getDebug() {

        return exBib.getDebug();
    }

    /**
     * Getter for the trace indicator.
     * 
     * @return the trace indicator
     */
    public boolean isTrace() {

        return exBib.getDebug().contains(ExBib.ExBibDebug.TRACE);
    }

    /**
     * This is the top level of the B<small>IB</small><span
     * style="margin-left: -0.15em;" >T</span><span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
     * >e</span>X engine. When all parameters are present then this method can
     * be invoked.
     * 
     * @return <code>true</code> iff an error has occurred
     * 
     * @throws IOException is case of an uncaught IOException
     * @throws ConfigurationException in case that the top-level configuration
     *         could not be found
     * 
     * @see org.extex.exbib.main.AbstractMain#run()
     */
    @Override
    public int run() throws IOException, ConfigurationException {

        logBanner();
        return exBib.run() ? CLI.EXIT_OK : CLI.EXIT_FAIL;
    }

    /**
     * Setter for the file name.
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
     * @see org.extex.exbib.main.AbstractMain#useLanguage(java.util.Locale)
     */
    @Override
    protected void useLanguage(Locale locale) {

        super.useLanguage(locale);
        exBib.useLanguage(locale);
    }

}
