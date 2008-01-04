/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.UnknownAttributeException;
import org.extex.exindex.core.parser.RawIndexParser;
import org.extex.exindex.core.parser.makeindex.MakeindexLoader;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.type.value.LList;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.main.exception.MainException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.InteractionIndicator;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This is the main program for the indexer.
 * 
 * <doc section="Command Line Use">
 * 
 * <h2>Command Line Use</h2>
 * <p>
 * This program is normally used through a wrapper which performs all necessary
 * initializations and hides the implementation language from the casual user.
 * Since this is the default case it is described here first. Details about the
 * direct usage without the wrapper can be found in section <a
 * href="#invocation">Direct Java Invocation</a>.
 * </p>
 * <p>
 * This program &ndash; called <tt>exindex</tt> here &ndash; has in its
 * simplest form of invocation two parameters. The first parameter is the name
 * of the style to use and the second parameter the file to process:
 * </p>
 * 
 * <pre class="CLISample">
 *   exindex -style abc.ist file.idx </pre>
 * 
 * <p>
 * The input file is sought in the current directory and other locations.
 * Details about searching can be found in <a href="#fileSearch">Searching Files</a>.
 * </p>
 * <p>
 * In general the syntax of invocation is as follows:
 * </p>
 * 
 * <pre class="CLIsyntax">
 *   exindex &lang;options&rang; &lang;raw-file&rang; </pre>
 * 
 * <p>
 * The command line options are contained in the table below.
 * </p>
 * 
 * <dl>
 * <dt><a name="-style"><tt>--style &lang;style-file&rang;</tt><br />
 * <dd> This parameter contains the name of the style to use. The style contains
 * instructions which control the behavior of <logo>ExIndex</logo>. Several
 * styles are supported. The program can read makeindex styles (*.ist) as well
 * as xindy styles (*.xdy). The file name extension is used to select the
 * appropriate parser.</dd>
 * 
 * <dt><tt>&lang;raw-file&rang;</tt></dt>
 * <dd> This parameter contains the file to read raw index data from. It may not
 * start with a hyphen. It has no default. If no file is given the standard
 * input is used to read from.</dd>
 * 
 * <dt><tt>-- &lang;raw-file&rang;</tt></dt>
 * <dd> This parameter contains the raw index file to read from. A file name may
 * start with any character since it is protected by the prefix <tt>--</tt>.
 * </dd>
 * 
 * <dt><a name="-input"><tt>--input &lang;raw-file&rang;</tt><br />
 * <dd> This parameter contains the raw index file to read from. A file name may
 * start with any character since it is protected by the prefix <tt>--input</tt>.
 * The file name has no default. several input file can be given in one of the
 * three forms. If no input file is given the program reads from standard input.
 * The empty raw file is also interpreted as standard input.</dd>
 * 
 * <dt><a name="-output"><tt>--output &lang;index-file&rang;</tt><br />
 * <dd> This parameter names the output file to write the structured index to.
 * If not given ... </dd>
 * 
 * <dt><a name="-transcript"><tt>--transcript &lang;transcript file&rang;</tt><br />
 * <dd> This parameter requests that the log is sent to a file as well as to the
 * console. </dd>
 * 
 * <dt><a name="-quiet"><tt>--quiet</tt><br />
 * <dd> This parameter disables the informative output to the console. It a
 * transcript is requested it will be used otherwise the program is deadly
 * silent. </dd>
 * 
 * <dt><a name="-Charset"><tt>--Charset &lang;charset-name&rang;</tt><br />
 * <dd> This parameter can be used to set the character set to be used when
 * reading raw index files. If the charset-name is empty the platform default
 * will be used. All raw index files are read with the same charset. The default
 * value is utf-8. </dd>
 * 
 * <dt><a name="-collate-spaces"><tt>--collate-spaces</tt><br />
 * <dd> This parameter instructs the program to delete spaces from the sort key.
 * </dd>
 * 
 * <dt><a name="-Encoding"><tt>-Encoding &lang;encoding-name&rang;</tt><br />
 * <dd> This parameter can be used to set the character set to be used when
 * reading style files. All style files share the same character set. If the
 * encoding-name is empty the platform default will be used. The default is
 * utf-8. </dd>
 * 
 * <dt><a name="-Module"><tt>--Module &lang;module-name&rang;</tt><br />
 * <dd> This instruction can be used to load the named module. </dd>
 * 
 * <dt><a name="-filter"><tt>--filter &lang;filter-name&rang;</tt><br />
 * <dd> ... </dd>
 * 
 * <dt><a name="-german"><tt>--german</tt><br />
 * <dd> ... </dd>
 * 
 * <dt><a name="-r"><tt>--r</tt><br />
 * <dd> ... </dd>
 * 
 * <dt><a name="-letter-ordering"><tt>--letter-ordering</tt><br />
 * <dd> ... </dd>
 * 
 * <dt><a name="-page"><tt>--page &lang;page&rang;</tt><br />
 * <dd> ... </dd>
 * 
 * <dt><a name="-trace"><tt>--trace</tt><br />
 * <dd> This parameter instructs the program to emit tracing output. </dd>
 * 
 * <dt><a name="-Log-level"><tt>--Log-level &lang;level&rang;</tt><br />
 * <dd> This parameter can be used to set the log level to a given value.
 * Possible levels are 0, 1, and 2. </dd>
 * 
 * <dt><a name="-help"><tt>--help</tt></a></dt>
 * <dd> This command line option produces a short usage description on the
 * standard output stream and terminates the program afterwards. </dd>
 * 
 * <!--
 * 
 * <dt><a name="-progname"/><tt>--progname &lang;name&rang;</tt><br />
 * <tt>-progname=&lang;name&rang;</tt> </a></dt>
 * <dd> This parameter can be used to overrule the name of the program shown in
 * the banner and the version information. </dd>
 * 
 * <dt><a name="-copyright"><tt>--copyright</tt></a></dt>
 * <dd> This command line option produces a copyright notice on the standard
 * output stream and terminates the program afterwards. </dd>
 * 
 * 
 * <dt><a name="-texinputs"/><tt>--texinputs &lang;path&rang;</tt><br />
 * <tt>-texinputs=&lang;path&rang;</tt> </a></dt>
 * <dd> This parameter contains the additional directories for searching
 * <logo>exindex</logo> input files. </dd>
 * </dd>
 * 
 * -->
 * 
 * <dt><a name="-Version"/><tt>--Version</tt></dt>
 * <dd> This command line parameter forces that the version information is
 * written to standard output and the program is terminated. </dd>
 * </dl>
 * 
 * <p>
 * Command line parameters can be abbreviated up to a unique prefix &ndash; and
 * sometimes even more. Thus the following invocations are equivalent:
 * </p>
 * 
 * <pre class="CLIsyntax">
 *   exindex --V
 *   exindex --Ve
 *   exindex --Ver
 *   exindex --Vers
 *   exindex --Versi
 *   exindex --Versio
 *   exindex --Version  </pre>
 * 
 * <p>
 * Command line parameters as described above start with a double hyphen (--).
 * They can be written with a single hyphen (-) as well. Thus <tt>-help</tt>
 * and <tt>--help</tt> are equivalent.
 * </p>
 * 
 * <a name="invocation"/>
 * <h3>Direct Java Invocation</h3>
 * 
 * <p>
 * The direct invocation of the Java needs some settings to be preset. These
 * settings are needed for <logo>ExIndex</logo> to run properly. The following
 * premises are needed:
 * </p>
 * <ul>
 * <li>Java needs to be installed (see section <a
 * href="#installation">Installation</a>. The program <tt>java</tt> is
 * assumed to be on the path of executables. </li>
 * <li>Java must be configured to find the jar files from the ExIndex
 * distribution. This can be accomplished by setting the environment variable
 * <tt>CLASSPATH</tt> or <tt>JAVA_HOME</tt>. See the documentation of your
 * Java system for details. </li>
 * </ul>
 * <p>
 * Now <logo>ExIndex</logo> can be invoked with the same parameters as
 * described above:
 * </p>
 * 
 * <pre class="CLIsyntax">
 *   java org.extex.exindex.main.ExIndex &lang;options&rang; &lang;raw-file&rang; </pre>
 * 
 * <p>
 * The result should be the same as the invocation of the wrapper.
 * </p>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExIndex extends Indexer {

    /**
     * The field <tt>LOCALIZER</tt> contains the the localizer.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(ExIndex.class);

    /**
     * Log a {@link java.lang.Throwable Throwable} including its stack trace to
     * the logger.
     * 
     * @param logger the target logger
     * @param text the prefix text to log
     * @param e the Throwable to log
     */
    protected static void logException(Logger logger, String text, Throwable e) {

        logger.log(Level.SEVERE, text == null ? "" : text);
        logger.log(Level.FINE, "", e);
    }

    /**
     * This is the command line interface to the indexer.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            System.exit(new ExIndex().run(args));
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * The field <tt>banner</tt> contains the indicator that the banner needs
     * to be written.
     */
    private boolean banner;

    /**
     * The field <tt>collateGerman</tt> contains the indicator to recognize
     * german.sty.
     */
    private boolean collateGerman = false;

    /**
     * The field <tt>consoleHandler</tt> contains the handler writing to the
     * console.
     */
    private Handler consoleHandler;

    /**
     * The field <tt>fileHandler</tt> contains the file handler for logging.
     */
    private FileHandler fileHandler = null;

    /**
     * The field <tt>filter</tt> contains the name of the filter or
     * <code>null</code> for none.
     */
    private String filter = null;

    /**
     * The field <tt>logger</tt> contains the logger for messages.
     */
    private Logger logger;

    /**
     * The field <tt>output</tt> contains the name of the output file or
     * <code>null</code> for stdout.
     */
    private String output = null;

    /**
     * The field <tt>pageCompression</tt> contains the indicator for page
     * range compression.
     */
    private boolean pageCompression = true;

    /**
     * The field <tt>log</tt> contains the name of the transcript file.
     */
    private String transcript;

    /**
     * The field <tt>charset</tt> contains the character set to be used for
     * writing.
     */
    private Charset charset;

    /**
     * The field <tt>inCharset</tt> contains the character set to be used for
     * reading style files.
     */
    private Charset inCharset;

    /**
     * The field <tt>config</tt> contains the configuration.
     */
    private Configuration config;

    /**
     * The field <tt>letterOrdering</tt> contains the indicator for letter
     * ordering.
     */
    private boolean letterOrdering = false;

    /**
     * Creates a new object.
     * 
     * @throws IOException in case of an I/O error when reading the default
     *         settings
     * @throws NoSuchMethodException in case of an undefined method in a
     *         function definition
     * @throws SecurityException in case of an security problem
     * @throws LSettingConstantException should not happen
     * @throws InvocationTargetException in case of an error
     * @throws IllegalAccessException in case of an error
     * @throws InstantiationException in case of an error
     * @throws IllegalArgumentException in case of an error
     */
    public ExIndex()
            throws IOException,
                SecurityException,
                NoSuchMethodException,
                LSettingConstantException,
                IllegalArgumentException,
                InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        super();
        config = ConfigurationFactory.newInstance("path/indexer");
        setResourceFinder(new ResourceFinderFactory().createResourceFinder(
            config, //
            logger, System.getProperties(), new InteractionIndicator() {

                public boolean isInteractive() {

                    return false;
                }
            }));
        charset = Charset.defaultCharset();
        banner = true;

        logger = Logger.getLogger(ExIndex.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);
    }

    /**
     * Get a numbered argument from a list or produce an appropriate error
     * message.
     * 
     * @param a the flag currently processed
     * @param args the arguments
     * @param i the index
     * @return the argument: arg[i]
     * 
     * @throws MainException if the argument is out of bounds
     */
    private String getArg(String a, String[] args, int i) throws MainException {

        if (i >= args.length) {
            throw new MainException(LOCALIZER.format("MissingArgument", a));
        }
        return args[i];
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
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.Indexer#makeRawIndexParser(java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected RawIndexParser makeRawIndexParser(String resource, String charset)
            throws IOException,
                RawIndexException {

        String parser = "xindy";
        InputStream stream;
        if (resource == null) {
            stream = System.in;
        } else {
            stream = getResourceFinder().findResource(resource, "raw");
            if (stream == null) {
                stream = getResourceFinder().findResource(resource, "idx");
                parser = "makeindex";
                if (stream == null) {
                    return null;
                }
            }
        }
        Reader reader = new InputStreamReader(stream, charset);
        if (filter != null) {
            stream = getClass().getClassLoader().getResourceAsStream(//
                "org/extex/exindex/filter/" + filter + ".filter");
            if (stream != null) {
                Properties p = new Properties();
                p.load(stream);
                String clazz = (String) p.get("class");
                if (clazz == null) {
                    throw new MainException(LOCALIZER.format(
                        "FilterMissingClass", filter));
                }
                try {
                    Class<?> theClass = Class.forName(clazz);
                    if (!Reader.class.isAssignableFrom(theClass)) {
                        throw new MainException(LOCALIZER.format(
                            "FilterClassCast", filter, clazz));
                    }
                    reader =
                            (Reader) theClass.getConstructor(
                                new Class[]{Reader.class}).newInstance(
                                new Object[]{reader});
                } catch (ClassNotFoundException e) {
                    logException(logger, "", e);
                    throw new MainException(LOCALIZER.format(
                        "FilterClassNotFound", filter, clazz), e);
                } catch (NoSuchMethodException e) {
                    logException(logger, "", e);
                    throw new MainException(LOCALIZER.format(
                        "FilterNoSuchMethod", filter, clazz), e);
                } catch (SecurityException e) {
                    logException(logger, "", e);
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, e.toString()), e);
                } catch (IllegalArgumentException e) {
                    logException(logger, "", e);
                    throw new MainException(LOCALIZER.format(
                        "FilterIllegalArgument", filter, e.toString()), e);
                } catch (InstantiationException e) {
                    logException(logger, "", e);
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, e.toString()), e);
                } catch (IllegalAccessException e) {
                    logException(logger, "", e);
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, e.toString()), e);
                } catch (InvocationTargetException e) {
                    logException(logger, "", e);
                    Throwable ex = (e.getCause() != null ? e.getCause() : e);
                    throw new MainException(LOCALIZER.format("FilterError",
                        filter, ex.toString()), e);
                } finally {
                    stream.close();
                }

            }
            // TODO gene: filter with external program
            throw new MainException(LOCALIZER.format("UnknownFilter", filter));
        }

        stream = getClass().getClassLoader().getResourceAsStream(//
            "org/extex/exindex/main/config/" + parser + ".parser");
        if (stream == null) {
            throw new MainException(LOCALIZER.format("ParserMissing", parser));
        }
        Properties p = new Properties();
        p.load(stream);
        String clazz = (String) p.get("class");
        if (clazz == null) {
            throw new MainException(LOCALIZER.format("ParserMissingClass",
                parser));
        }

        try {
            Constructor<?> cons =
                    Class.forName(clazz).getConstructor(Reader.class,
                        String.class, LInterpreter.class);
            return (RawIndexParser) cons.newInstance(reader, resource, this);
        } catch (SecurityException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        } catch (NoSuchMethodException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        } catch (ClassNotFoundException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        } catch (IllegalArgumentException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        } catch (InstantiationException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        } catch (IllegalAccessException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        } catch (InvocationTargetException e) {
            throw new MainException(LOCALIZER.format("ParserError", parser), e);
        }
    }

    /**
     * Make an writer for output.
     * 
     * @param logger the logger
     * 
     * @return the writer
     * 
     * @throws IOException in case of an I/O error
     */
    protected Writer makeWriter(Logger logger) throws IOException {

        return new OutputStreamWriter((output == null
                ? System.out
                : new FileOutputStream(output)), charset);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.Indexer#markup(java.io.Writer,
     *      java.util.logging.Logger)
     */
    @Override
    protected void markup(Writer writer, Logger logger)
            throws IOException,
                LException {

        logger.log(Level.INFO, LOCALIZER.format((output == null
                ? "GeneratingOutput"
                : "GeneratingOutputFile"), output));

        super.markup(writer, logger);

        logger.log(Level.INFO, LOCALIZER.format((output == null
                ? "StandardOutput"
                : "Output"), output));
    }

    /**
     * Run the indexer with some parameters from an array.
     * 
     * @param args the command line arguments
     * 
     * @return the exit code
     */
    public int run(String[] args) {

        if (args == null) {
            throw new NullPointerException();
        }
        List<String> files = new ArrayList<String>();
        List<String> styles = new ArrayList<String>();

        try {
            for (int i = 0; i < args.length; i++) {
                String a = args[i];
                if (a == null || "".equals(a)) {
                    logger.log(Level.FINE, LOCALIZER
                        .format("EmptyArgumentIgnored"));
                    continue;
                }

                if (a.startsWith("--")) {
                    a = a.substring(1);
                }
                if (!a.startsWith("-")) {
                    files.add(a);
                } else if ("-".equals(a)) {
                    files.add(getArg(a, args, ++i));
                } else if ("-collate-spaces".startsWith(a)) {
                    eval(new LList(LSymbol.get("sort-rule"), //
                        new LString(" "), //
                        new LString("")));

                } else if ("-filter".startsWith(a)) {
                    filter = getArg(a, args, ++i);

                } else if ("-german".startsWith(a)) {
                    collateGerman = true;

                } else if ("-input".startsWith(a)) {
                    files.add(null);

                } else if ("-letter-ordering".startsWith(a)) {
                    letterOrdering = true;

                } else if ("-output".startsWith(a)) {
                    output = getArg(a, args, ++i);
                    if (output.equals("") || output.equals("-")) {
                        output = null;
                    }

                } else if ("-page".startsWith(a)) {
                    setq("markup:start-page", new LString(getArg(a, args, ++i)));

                } else if ("-quiet".startsWith(a)) {
                    logger.removeHandler(consoleHandler);

                } else if ("-r".startsWith(a)) {
                    pageCompression = false;

                } else if ("-style".startsWith(a)) {
                    styles.add(getArg(a, args, ++i));

                } else if ("-transcript".startsWith(a) && a.length() > 4) {
                    transcript = getArg(a, args, ++i);
                    if (transcript.equals("") || transcript.equals("-")) {
                        transcript = null;
                    }

                } else if ("-trace".startsWith(a) && a.length() > 4) {
                    eval(new LList(LSymbol.get("markup-trace"), //
                        LSymbol.get(":on")));

                } else if ("-Charset".startsWith(a)) {
                    try {
                        charset = Charset.forName(getArg(a, args, ++i));
                    } catch (UnsupportedCharsetException e) {
                        throw new MainException(LOCALIZER.format(
                            "UnsupportedCharset", args[i]));
                    }

                } else if ("-Encoding".startsWith(a)) {
                    try {
                        inCharset = Charset.forName(getArg(a, args, ++i));
                    } catch (UnsupportedCharsetException e) {
                        throw new MainException(LOCALIZER.format(
                            "UnsupportedCharset", args[i]));
                    }

                } else if ("-Module".startsWith(a)) {
                    load(getArg(a, args, ++i));

                } else if ("-Log-level".startsWith(a)) {
                    setLogLevel(getArg(a, args, ++i));

                } else if ("-Version".startsWith(a)) {
                    showBanner();
                    return 1;

                } else if ("-help".startsWith(a) || "-?".equals(a)) {
                    logger.log(Level.SEVERE, LOCALIZER.format("Usage",
                        "ExIndex"));
                    return 1;

                } else {
                    throw new MainException(LOCALIZER.format("UnknownArgument",
                        a));
                }
            }

            if (transcript == null && files.size() != 0 && files.get(0) != null) {
                transcript = files.get(0).replaceAll("\\.[^.]*$", "") + ".ilg";
            }

            if (transcript != null) {
                fileHandler = new FileHandler(transcript);
                fileHandler.setFormatter(new LogFormatter());
                fileHandler.setLevel(Level.INFO);
                logger.addHandler(fileHandler);
            }

            if (files.isEmpty()) {
                // last resort: stdin
                files.add(null);
            }

            Writer writer = makeWriter(logger);
            try {
                run(styles, files, writer, logger);
            } finally {
                writer.close();
            }

            if (transcript != null) {
                logger.log(Level.INFO, LOCALIZER.format("Transcript",
                    transcript));
            }
            if (fileHandler != null) {
                fileHandler.flush();
            }
            return 0;

        } catch (FileNotFoundException e) {
            showBanner();
            logger.log(Level.SEVERE, LOCALIZER.format("FileNotFound", e
                .getMessage()));
            logger.log(Level.FINE, "", e);
        } catch (MainException e) {
            showBanner();
            logger.log(Level.SEVERE, e.getLocalizedMessage());
            logger.log(Level.FINE, "", e);
        } catch (Exception e) {
            showBanner();
            logger.log(Level.SEVERE, LOCALIZER.format("SevereError", e
                .toString()));
            logger.log(Level.FINE, "", e);
            e.printStackTrace();
        }
        if (fileHandler != null) {
            fileHandler.flush();
        }
        return -1;
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
     * Set the log level.
     * 
     * @param level the level which is is one of the following values: "1",
     *        "fine", "2", "fine", "3", "finest"
     * 
     * @throws MainException in case of an undefined log level name
     */
    private void setLogLevel(String level) throws MainException {

        if ("1".equals(level) || "fine".equals(level)) {
            logger.setLevel(Level.FINE);
        } else if ("2".equals(level) || "finer".equals(level)) {
            logger.setLevel(Level.FINER);
        } else if ("3".equals(level) || "finest".equals(level)) {
            logger.setLevel(Level.FINEST);
        } else {
            throw new MainException(LOCALIZER
                .format("UndefinedLogLevel", level));
        }

        ResourceFinder finder = getResourceFinder();
        if (finder != null) {
            finder.enableTracing(true);
        }
    }

    /**
     * Show the program banner.
     */
    private void showBanner() {

        if (banner) {
            String build = "$Revision$".replaceAll("[^0-9]", "");
            logger.log(Level.INFO, LOCALIZER.format("Banner", build, //
                System.getProperty("java.version")));
            banner = false;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.Indexer#startup(java.util.List,
     *      java.util.logging.Logger)
     */
    @Override
    protected void startup(List<String> styles, Logger logger)
            throws IOException,
                LException,
                UnknownAttributeException {

        showBanner();

        for (String style : styles) {
            logger.log(Level.INFO, LOCALIZER.format("ScanningStyle", style));

            InputStream stream = getResourceFinder().findResource(style, "ist");
            if (stream != null) {

                Reader reader = new InputStreamReader(stream, inCharset);
                try {
                    MakeindexLoader.load(reader, style, this);
                } finally {
                    reader.close();
                }

            } else {
                stream = getResourceFinder().findResource(style, "xdy");
                if (stream == null) {
                    throw new FileNotFoundException(style);
                }
                Reader reader = new InputStreamReader(stream, inCharset);
                try {
                    load(reader, style);
                } finally {
                    reader.close();
                }
            }
            logger.log(Level.INFO, LOCALIZER.format("ScanningStyleDone", //
                "?", "?"));
        }
    }

}
