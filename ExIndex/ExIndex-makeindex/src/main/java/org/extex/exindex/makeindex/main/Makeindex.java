/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import org.extex.exindex.core.exception.RawIndexEofException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.core.exception.RawIndexMissingCharException;
import org.extex.exindex.makeindex.Entry;
import org.extex.exindex.makeindex.Index;
import org.extex.exindex.makeindex.MakeindexParameters;
import org.extex.exindex.makeindex.Parameters;
import org.extex.exindex.makeindex.exceptions.MissingSymbolException;
import org.extex.exindex.makeindex.exceptions.StyleNotFoundException;
import org.extex.exindex.makeindex.exceptions.UnknownOptionException;
import org.extex.exindex.makeindex.normalizer.*;
import org.extex.exindex.makeindex.pages.MakeindexPageProcessor;
import org.extex.exindex.makeindex.pages.PageProcessor;
import org.extex.exindex.makeindex.parser.MakeindexParser;
import org.extex.exindex.makeindex.parser.Parser;
import org.extex.exindex.makeindex.writer.IndexWriter;
import org.extex.exindex.makeindex.writer.MakeindexWriter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;
import org.extex.resource.io.NamedInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.*;

/**
 * This is the main program for an indexer a la <i>MakeIndex</i>.
 * <p>
 * Usage: {@code java org.extex.exindex.makeindex.main.makeindex }
 * <i>&lt;options&gt; files</i>
 * </p>
 * <p>
 * Read the files which contain the raw index and produce a sorted and formatted
 * index. If no file is specified read from standard input.
 * </p>
 * <p>
 * The following options are supported:
 * </p>
 * <dl>
 * <dt>{@code -c[ollateSpaces]}</dt>
 * <dd>Treat sequences of spaces as one space for comparison.</dd>
 * <dt>{@code -D&lt;key&gt;=&lt;value&gt;}</dt>
 * <dd>Store a value for a key in the parameters.</dd>
 * <dt>{@code -g[erman]}</dt>
 * <dd>Use the German sort order.</dd>
 * <dt>{@code - &lt;file&gt;}</dt>
 * <dt>{@code -i[nput] &lt;file&gt;}</dt>
 * <dd>Take the argument as input file regardless of it starting with a minus.</dd>
 * <dt>{@code -l[etterOrdering]}</dt>
 * <dd>Consider letters only when comparing.</dd>
 * <dt>{@code -o[utput] &lt;output file&gt;}</dt>
 * <dt>{@code -output=&lt;output file&gt;}</dt>
 * <dd>Sent the output to an output file. The default is to use the extension
 * {@code .ind} together with the first index file name.</dd>
 * <dt>{@code -p[age] &lt;page&gt;}</dt>
 * <dt>{@code -page=&lt;page&gt;}</dt>
 * <dd>The start page.</dd>
 * <dt>{@code -q[uiet]}</dt>
 * <dd>Act quietly and suppress the logging to the console.</dd>
 * <dt>{@code -r}</dt>
 * <dd>...</dd>
 * <dt>{@code -s[tyle] &lt;style&gt;}</dt>
 * <dt>{@code -style=&lt;style&gt;}</dt>
 * <dd>Add the style to the styles to be applied.</dd>
 * <dt>{@code -t[ranscript] &lt;transcript&gt;}</dt>
 * <dt>{@code -transcript=&lt;transcript&gt;}</dt>
 * <dd>Sent the transcript to the given file. This setting overwrites previous
 * transcript file names. The default is to use the extension {@code .ilg}
 * together with the first index file name.</dd>
 * <dt>{@code -V[ersion]}</dt>
 * <dd>Print the version and exit.</dd>
 * <dt>{@code -h[elp]}</dt>
 * <dd>Print a short usage and exit.</dd>
 * </dl>
 * <p>
 * The options can be abbreviated up to a single letter.
 * </p>
 * <p>
 * The default encoding for reading index files and styles and writing is utf-8.
 * </p>
 * 
 * <h2>Parameters</h2>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
@SuppressWarnings("unused")
public class Makeindex {

    /**
     * The field {@code localizer} contains the localizer.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(Makeindex.class);

    /**
     * The field {@code REVISION} contains the revision number.
     */
    public static final String REVISION =
            "$Revision:7779 $".replaceAll("[^0-9]", "");

    /**
     * The field {@code VERSION} contains the version number.
     */
    public static final String VERSION = "0.1";

    /**
     * The field {@code PROP_COLLATE_GERMAN} contains the key for the German
     * property.
     */
    protected static final String PROP_COLLATE_GERMAN =
            "makeindex.collate.german";

    /**
     * The field {@code PROP_COLLATE_SPACES} contains the key for the collate
     * property.
     */
    protected static final String PROP_COLLATE_SPACES =
            "makeindex.collate.spaces";

    /**
     * The field {@code PROP_CONFIG} contains the key for the configuration
     * property.
     */
    protected static final String PROP_CONFIG = "makeindex.config";

    /**
     * The field {@code PROP_INPUT_ENCODING} contains the key for the input
     * encoding property.
     */
    protected static final String PROP_INPUT_ENCODING =
            "makeindex.input.encoding";

    /**
     * The field {@code PROP_LETTER_ORDERING} contains the key for the letter
     * ordering property.
     */
    protected static final String PROP_LETTER_ORDERING =
            "makeindex.letter.ordering";

    /**
     * The field {@code PROP_OUTPUT_ENCODING} contains the key for the output
     * encoding property.
     */
    protected static final String PROP_OUTPUT_ENCODING =
            "makeindex.output.encoding";

    /**
     * The field {@code PROP_OUTPUT} contains the key for the output property.
     */
    protected static final String PROP_OUTPUT = "makeindex.output";

    /**
     * The field {@code PROP_PAGE_COMPRESSION} contains the key for the page
     * compression property.
     */
    protected static final String PROP_PAGE_COMPRESSION =
            "makeindex.page.compression";

    /**
     * The field {@code PROP_START_PAGE} contains the key for the start page
     * property.
     */
    protected static final String PROP_START_PAGE = "makeindex.start.page";

    /**
     * The field {@code PROP_STYLE_ENCODING} contains the key for the style
     * encoding property.
     */
    protected static final String PROP_STYLE_ENCODING =
            "makeindex.style.encoding";

    /**
     * The field {@code PROP_TRANSCRIPT} contains the key for the transcript
     * property.
     */
    protected static final String PROP_TRANSCRIPT = "makeindex.transcript";

    /**
     * This is the command line interface to the indexer.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(new Makeindex().run(args));
    }

    /**
     * The field {@code banner} contains the indicator that the banner needs to
     * be written.
     */
    private boolean banner = true;

    /**
     * The field {@code comparator} contains the comparator.
     */
    private final MakeindexComparator comparator = new MakeindexComparator();

    /**
     * The field {@code consoleHandler} contains the handler writing to the
     * console.
     */
    private Handler consoleHandler;

    /**
     * The field {@code fileHandler} contains the file handler for logging.
     */
    private FileHandler fileHandler = null;

    /**
     * The field {@code files} contains the input files.
     */
    private final List<String> files = new ArrayList<>();

    /**
     * The field {@code logger} contains the logger for messages.
     */
    private Logger logger = null;

    /**
     * The field {@code styles} contains the style files.
     */
    private final ArrayList<String> styles = new ArrayList<>();

    /**
     * The field {@code resourceFinder} contains the resource finder.
     */
    private ResourceFinder resourceFinder;

    /**
     * The field {@code properties} contains the properties controlling the
     * behavior.
     */
    private final Properties properties;


    public Makeindex() {

        this(null);
    }

    /**
     * Creates a new object.
     * 
     * @param p the parameters
     */
    public Makeindex(Properties p) {

        if (p == null) {
            properties = new Properties();
        } else {
            properties = (Properties) p.clone();
        }
        provideDefault(PROP_INPUT_ENCODING, "utf-8");
        provideDefault(PROP_OUTPUT_ENCODING, "utf-8");
        provideDefault(PROP_STYLE_ENCODING, "utf-8");
    }

    /**
     * Add an index file.
     * 
     * @param file the index file. If the file is {@code null} then the
     *        content is read from stdin instead.
     */
    public void addIndexFile(String file) {

        files.add(file);
    }

    /**
     * Add a style.
     * 
     * @param style the style to add
     */
    public void addStyle(String style) {

        if (style == null || style.equals("")) {
            throw new IllegalArgumentException("style");
        }
        styles.add(style);
    }

    /**
     * Get a numbered argument from a list or produce an appropriate error
     * message.
     * 
     * @param args the arguments
     * @param i the index
     * @param option the flag currently processed
     * 
     * @return the argument: arg[i]
     * 
     * @throws UnknownOptionException if the argument is out of bounds
     */
    protected String argument(String[] args, int i, String option)
            throws UnknownOptionException {

        if (i >= args.length) {
            throw new UnknownOptionException(LocalizerFactory.getLocalizer(
                getClass()).format("MissingArgument", option));
        }
        return args[i];
    }

    /**
     * Getter for the files.
     * 
     * @return the files
     */
    public List<String> getFiles() {

        return files;
    }

    /**
     * Getter for the inputEncoding.
     * 
     * @return the inputEncoding
     */
    public String getInputEncoding() {

        return properties.getProperty(PROP_INPUT_ENCODING);
    }

    /**
     * Getter for logger. If none is set then a new one is created.
     * 
     * @return the logger
     */
    public Logger getLogger() {

        if (logger == null) {
            logger = Logger.getLogger(Makeindex.class.getName());
            logger.setUseParentHandlers(false);
            logger.setLevel(Level.INFO);
            consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new LogFormatter());
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);
        }
        return logger;
    }

    /**
     * Getter for the output.
     * 
     * @return the output
     */
    public String getOutput() {

        return properties.getProperty(PROP_OUTPUT);
    }

    /**
     * Getter for the outputEncoding.
     * 
     * @return the outputEncoding
     */
    public String getOutputEncoding() {

        return properties.getProperty(PROP_OUTPUT_ENCODING);
    }

    /**
     * Getter for the startPage.
     * 
     * @return the startPage
     */
    public String getStartPage() {

        return properties.getProperty(PROP_START_PAGE);
    }

    /**
     * Getter for the styleEncoding.
     * 
     * @return the styleEncoding
     */
    public String getStyleEncoding() {

        return properties.getProperty(PROP_STYLE_ENCODING);
    }

    /**
     * Getter for the styles.
     * 
     * @return the styles
     */
    public List<String> getStyles() {

        return styles;
    }

    /**
     * Getter for the transcript.
     * 
     * @return the transcript
     */
    public String getTranscript() {

        return properties.getProperty(PROP_TRANSCRIPT);
    }

    /**
     * Write an info message to the log.
     * 
     * @param key the message key
     * @param args the arguments
     */
    protected void info(String key, Object... args) {

        getLogger().log(Level.INFO, LOCALIZER.format(key, args));
    }

    /**
     * Getter for the collateGerman.
     * 
     * @return the collateGerman
     */
    public boolean isCollateGerman() {

        return Boolean
            .parseBoolean(properties.getProperty(PROP_COLLATE_GERMAN));
    }

    /**
     * Getter for the collateSpaces.
     * 
     * @return the collateSpaces
     */
    public boolean isCollateSpaces() {

        return Boolean
            .parseBoolean(properties.getProperty(PROP_COLLATE_SPACES));
    }

    /**
     * Getter for the letterOrdering.
     * 
     * @return the letterOrdering
     */
    public boolean isLetterOrdering() {

        return Boolean.parseBoolean(properties
            .getProperty(PROP_LETTER_ORDERING));
    }

    /**
     * Getter for the pageCompression.
     * 
     * @return the pageCompression
     */
    public boolean isPageCompression() {

        return Boolean.parseBoolean(properties
            .getProperty(PROP_PAGE_COMPRESSION));
    }

    /**
     * Write an error message to the log. This method assures that the banner is
     * printed if required.
     * 
     * @param message the message
     */
    protected void log(String message) {

        Logger log = getLogger();
        if (banner) {
            log.log(Level.INFO, LOCALIZER.format("Banner", VERSION, REVISION,
                System.getProperty("java.version")));
            banner = false;
        }
        if (message != null) {
            log.log(Level.SEVERE, message);
        }
    }

    /**
     * Log a {@link java.lang.Throwable Throwable} including its stack trace to
     * the logger.
     * 
     * @param text the prefix text to log
     * @param e the Throwable to log
     */
    protected void logException(String text, Throwable e) {

        log(text == null ? "" : text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream w = new PrintStream(out);
        e.printStackTrace(w);
        w.close();
        logger.log(Level.FINE, out.toString(), e);
    }

    /**
     * Create a collator from the settings.
     * 
     * @return the collator
     */
    private Collator makeCollator() {

        Collator collator = null;
        if (isCollateGerman()) {
            collator = new MakeindexGermanCollator();
        }
        if (isCollateSpaces()) {
            collator = (collator != null
                    ? new CollatorPipe(new SpaceCollator(), collator)
                    : new SpaceCollator());
        }
        if (isLetterOrdering()) {
            collator = (collator != null
                    ? new CollatorPipe(new LettersOnlyCollator(), collator)
                    : new LettersOnlyCollator());
        }

        return collator != null ? collator : new EmptyCollator();
    }

    /**
     * Process a combined command line argument consisting of several single
     * letter options.
     * 
     * @param a the argument to process including a leading -
     * 
     * @return {@code true} iff all letters could be digested
     */
    protected boolean processShortArguments(String a) {

        for (char c : a.toCharArray()) {
            switch (c) {
                case '-':
                    break;
                case 'g':
                    setCollateGerman(true);
                    break;
                case 'i':
                    addIndexFile(null);
                    break;
                case 'l':
                    setLetterOrdering(true);
                    break;
                case 'q':
                    getLogger().removeHandler(consoleHandler);
                    break;
                case 'r':
                    setPageCompression(false);
                    break;
                case 'c':
                    setCollateSpaces(true);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * Provide a default pro a property. If a value is defined already then
     * nothing is done. Otherwise the value provided is stored for the key.
     * 
     * @param key the key
     * @param value the value
     */
    private void provideDefault(String key, String value) {

        if (properties.getProperty(key) == null) {
            properties.setProperty(key, value);
        }
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
            throw new IllegalArgumentException();
        }

        try {
            for (int i = 0; i < args.length; i++) {
                String a = args[i];
                if (a == null || "".equals(a)) {
                    // a strange argument is ignored
                    continue;
                }

                if (a.startsWith("--")) {
                    a = a.substring(1);
                }
                if (!a.startsWith("-")) {
                    addIndexFile(a);
                } else if ("-".equals(a)) {
                    addIndexFile(argument(args, ++i, a));

                } else if ("-collateSpaces".startsWith(a)) {
                    setCollateSpaces(true);

                } else if ("-encoding".startsWith(a)) {
                    String enc = argument(args, ++i, a);
                    setInputEncoding(enc);
                    setOutputEncoding(enc);
                    setStyleEncoding(enc);

                } else if (a.startsWith("-encoding=")) {
                    if (a.length() <= 10) {
                        throw new UnknownOptionException(LocalizerFactory
                            .getLocalizer(getClass()).format("MissingArgument",
                                a));
                    }
                    a = a.substring(10);
                    setInputEncoding(a);
                    setOutputEncoding(a);
                    setStyleEncoding(a);

                } else if ("-german".startsWith(a)) {
                    setCollateGerman(true);

                } else if ("-input".startsWith(a)) {
                    addIndexFile(null);

                } else if ("-letterOrdering".startsWith(a)) {
                    setLetterOrdering(true);

                } else if ("-output".startsWith(a)) {
                    a = argument(args, ++i, a);
                    if (a.equals("") || a.equals("-")) {
                        a = null;
                    }
                    setOutput(a);

                } else if (a.startsWith("-output=")) {
                    a = a.substring(8);
                    if (a.equals("") || a.equals("-")) {
                        a = null;
                    }
                    setOutput(a);

                } else if ("-page".startsWith(a)) {
                    setStartPage(argument(args, ++i, a));

                } else if (a.startsWith("-page=")) {
                    setStartPage(a.substring(6));

                } else if ("-quiet".startsWith(a)) {
                    getLogger().removeHandler(consoleHandler);

                } else if ("-r".startsWith(a)) {
                    setPageCompression(false);

                } else if ("-style".startsWith(a)) {
                    String style = argument(args, ++i, a);
                    if (style == null || style.equals("")) {
                        log(LOCALIZER.format("EmptyStyle"));
                        return -1;
                    }
                    addStyle(style);

                } else if (a.startsWith("-style=")) {
                    String style = a.substring(7);
                    if (style.equals("")) {
                        log(LOCALIZER.format("EmptyStyle"));
                        return -1;
                    }
                    addStyle(style);

                } else if ("-transcript".startsWith(a)) {
                    setTranscript(argument(args, ++i, a));

                } else if (a.startsWith("-transcript=")) {
                    setTranscript(a.substring(12));

                } else if ("-Version".startsWith(a)) {
                    log(null);
                    return 1;

                } else if ("-help".startsWith(a)) {
                    log(LOCALIZER.format("Usage", "Indexer"));
                    return 1;

                } else if (a.startsWith("-D")) {
                    int eq = a.indexOf('=');
                    if (eq < 0) {
                        log(LOCALIZER.format("UnknownArgument", a));
                        return -1;
                    }
                    properties.setProperty(a.substring(2, eq),
                        a.substring(eq + 1));

                } else if (!processShortArguments(a)) {
                    log(LOCALIZER.format("UnknownArgument", a));
                    return -1;
                }
            }

            Configuration config =
                    ConfigurationFactory.newInstance(
                        "makeindex/"
                                + properties.getProperty(PROP_CONFIG,
                                    "makeindex.xml"));

            ResourceFinder finder =
                    (resourceFinder != null
                            ? resourceFinder
                            : new ResourceFinderFactory().createResourceFinder(
                                config.getConfiguration("Resource"),
                                logger,
                                properties,
                                null));

            String transcript = properties.getProperty(PROP_TRANSCRIPT);

            if (transcript == null && files.size() != 0 && files.get(0) != null) {
                transcript = files.get(0).replaceAll("\\.idx$", "") + ".ilg";
            }

            if (transcript != null) {
                fileHandler = new FileHandler(transcript);
                fileHandler.setFormatter(new LogFormatter());
                fileHandler.setLevel(Level.INFO);
                getLogger().addHandler(fileHandler);
            }

            Index index = new Index();

            for (String s : styles) {
                scanStyle(s, finder, index.getParams());
            }

            if (files.size() == 0) {
                scanInput(null, finder, index);
            } else {
                for (String s : files) {
                    scanInput(s, finder, index);
                }
            }
            writeOutput(index);

            if (transcript != null) {
                info("Transcript", transcript);
            }

        } catch (StyleNotFoundException e) {
            log(LOCALIZER.format("StyleNotFound", e.getMessage()));
            return -1;
        } catch (FileNotFoundException e) {
            log(LOCALIZER.format("FileNotFound", e.getMessage()));
            return -1;
        } catch (UnsupportedEncodingException e) {
            log(LOCALIZER.format("UnsupportedEncoding", e.getMessage()));
            return -1;
        } catch ( ConfigurationException | UnknownOptionException e) {
            log(e.getMessage());
            return -1;
        } catch (Exception e) {
            logException(e.getLocalizedMessage(), e);
            e.printStackTrace();
            return -1;
        } finally {
            if (fileHandler != null) {
                logger.removeHandler(fileHandler);
                fileHandler.close();
            }
            if (consoleHandler != null) {
                logger.removeHandler(consoleHandler);
            }
        }
        return 0;
    }

    /**
     * Load data into the index.
     * 
     * @param file the name of the input file
     * @param finder the resource finder
     * @param index the index
     * 
     * @throws IOException in case of an I/O error
     * @throws RawIndexMissingCharException in case of an error
     * @throws RawIndexEofException in case of an error
     */
    protected void scanInput(String file, ResourceFinder finder, Index index)
            throws IOException,
                RawIndexEofException,
                RawIndexMissingCharException {

        log(null);

        Reader reader;
        String fmt;

        if (file == null) {
            fmt = "ScanningStandardInput";
            reader = new InputStreamReader(System.in,
                properties.getProperty(PROP_INPUT_ENCODING));
        } else {
            NamedInputStream nis = finder.findResource(file, "idx");
            if (nis == null) {
                throw new FileNotFoundException(file);
            }
            file = nis.getName();
            reader = new InputStreamReader(nis,
                properties.getProperty(PROP_INPUT_ENCODING));
            fmt = "ScanningInput";

            if (properties.getProperty(PROP_OUTPUT) == null) {
                properties.setProperty(PROP_OUTPUT,
                    file.replaceAll(".idx$", "") + ".ind");
            }
        }
        try {
            info(fmt, file);
            Parser parser = new MakeindexParser();
            Collator collator = makeCollator();
            int[] count = parser.load(reader, file, index, collator);
            info("ScanningInputDone",
                Integer.toString(count[0]), Integer.toString(count[1]));
        } finally {
            reader.close();
        }
    }

    /**
     * Merge a style into the one already loaded.
     * 
     * @param file the name of the resource
     * @param finder the resource finder
     * @param params the parameters
     * 
     * @throws IOException in case of an I/O error
     * @throws RawIndexException in case of an error
     * @throws RawIndexEofException in case of an error
     * @throws MissingSymbolException in case of an error
     */
    protected void scanStyle(String file, ResourceFinder finder,
            Parameters params)
            throws IOException,
                MissingSymbolException,
                RawIndexEofException,
                RawIndexException {

        log(null);

        NamedInputStream nis = finder.findResource(file, "ist");
        if (nis == null) {
            throw new StyleNotFoundException(file);
        }
        try( Reader reader = new InputStreamReader(
            nis, properties.getProperty( PROP_STYLE_ENCODING ) ) ) {
            info( "ScanningStyle", file );
            int[] count =
                MakeindexParameters.load( reader, file, params, logger );
            info( "ScanningStyleDone",
                  Integer.toString( count[ 0 ] ),
                  Integer.toString( count[ 1 ] ) );
        }
    }

    /**
     * Setter for the collateGerman.
     * 
     * @param collateGerman the collateGerman to set
     */
    public void setCollateGerman(boolean collateGerman) {

        properties.setProperty(PROP_COLLATE_GERMAN,
            Boolean.toString(collateGerman));
    }

    /**
     * Setter for the collateSpaces.
     * 
     * @param collateSpaces the collateSpaces to set
     */
    public void setCollateSpaces(boolean collateSpaces) {

        properties.setProperty(PROP_COLLATE_SPACES,
            Boolean.toString(collateSpaces));
    }

    /**
     * Setter for the inputEncoding.
     * 
     * @param inputEncoding the inputEncoding to set
     */
    public void setInputEncoding(String inputEncoding) {

        properties.setProperty(PROP_INPUT_ENCODING, inputEncoding);
    }

    /**
     * Setter for the letterOrdering.
     * 
     * @param letterOrdering the letterOrdering to set
     */
    public void setLetterOrdering(boolean letterOrdering) {

        properties.setProperty(PROP_LETTER_ORDERING,
            Boolean.toString(letterOrdering));
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
     * Setter for the output.
     * 
     * @param output the output to set
     */
    public void setOutput(String output) {

        properties.setProperty(PROP_OUTPUT, output);
    }

    /**
     * Setter for the outputEncoding.
     * 
     * @param outputEncoding the outputEncoding to set
     */
    public void setOutputEncoding(String outputEncoding) {

        properties.setProperty(PROP_OUTPUT_ENCODING, outputEncoding);
    }

    /**
     * Setter for the pageCompression.
     * 
     * @param pageCompression the pageCompression to set
     */
    public void setPageCompression(boolean pageCompression) {

        properties.setProperty(PROP_PAGE_COMPRESSION,
            Boolean.toString(pageCompression));
    }

    /**
     * Setter for the startPage.
     * 
     * @param startPage the startPage to set
     */
    public void setStartPage(String startPage) {

        properties.setProperty(PROP_START_PAGE, startPage);
    }

    /**
     * Setter for the styleEncoding.
     * 
     * @param styleEncoding the styleEncoding to set
     */
    public void setStyleEncoding(String styleEncoding) {

        properties.setProperty(PROP_STYLE_ENCODING, styleEncoding);
    }

    /**
     * Setter for the transcript.
     * 
     * @param transcript the transcript to set
     */
    public void setTranscript(String transcript) {

        properties.setProperty(PROP_TRANSCRIPT, transcript);
    }

    /**
     * Generate the output.
     * 
     * @param index the index
     * 
     * @throws IOException in case of an I/O error
     */
    protected void writeOutput(Index index) throws IOException {

        String fmt;
        String output = properties.getProperty(PROP_OUTPUT);
        OutputStream out;

        if (output == null) {
            fmt = "GeneratingOutput";
            out = System.out;
        } else {
            fmt = "GeneratingOutputFile";
            out = new FileOutputStream( output );
        }

        try( Writer w = new OutputStreamWriter(
            out, properties.getProperty( PROP_OUTPUT_ENCODING ) ) ) {
            Parameters params = index.getParams();
            IndexWriter indexWriter = new MakeindexWriter( w, params );
            long[] warn = {0, 0};
            PageProcessor pageProcessor =
                new MakeindexPageProcessor( params, logger );

            info( "Sorting" );
            List<Entry> entries =
                index.sort( comparator, pageProcessor, logger, warn );
            info( "SortingDone", Long.toString( warn[ 1 ] ) );
            info( fmt, output );

            int[] count = indexWriter.write( entries, logger, getStartPage(),
                                             pageProcessor );

            info( "GeneratingOutputDone",
                  Integer.toString( count[ 0 ] ),
                  Long.toString( count[ 1 ] + warn[ 0 ] ) );
        }
        if (output == null) {
            info("StandardOutput", output);
        } else {
            info("Output", output);
        }
    }
}
