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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.extex.exindex.makeindex.normalizer.Collator;
import org.extex.exindex.makeindex.normalizer.CollatorPipe;
import org.extex.exindex.makeindex.normalizer.EmptyCollator;
import org.extex.exindex.makeindex.normalizer.LettersOnlyCollator;
import org.extex.exindex.makeindex.normalizer.MakeindexGermanCollator;
import org.extex.exindex.makeindex.normalizer.SpaceCollator;
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

/**
 * This is the main program for an indexer a la <i>MakeIndex</i>.
 * <p>
 * Usage: <tt>java org.extex.exindex.makeindex.main.makeindex </tt>
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
 * <dt><tt>-c[ollateSpaces]</tt></dt>
 * <dd>Treat sequences of spaces as one space for comparison.</dd>
 * <dt><tt>-D&lt;key&gt;=&lt;value&gt;</tt></dt>
 * <dd>Store a value for a key in the parameters.</dd>
 * <dt><tt>-g[erman]</tt></dt>
 * <dd>Use the German sort order.</dd>
 * <dt><tt>- &lt;file&gt;</tt></dt>
 * <dt><tt>-i[nput] &lt;file&gt;</tt></dt>
 * <dd>Take the argument as input file regardless of it starting with a minus.</dd>
 * <dt><tt>-l[etterOrdering]</tt></dt>
 * <dd>Consider letters only when comparing.</dd>
 * <dt><tt>-o[utput] &lt;output file&gt;</tt></dt>
 * <dt><tt>-output=&lt;output file&gt;</tt></dt>
 * <dd>Sent the output to an output file. The default is to use the extension
 * <tt>.ind</tt> together with the first index file name.</dd>
 * <dt><tt>-p[age] &lt;page&gt;</tt></dt>
 * <dt><tt>-page=&lt;page&gt;</tt></dt>
 * <dd>The start page.</dd>
 * <dt><tt>-q[uiet]</tt></dt>
 * <dd>Act quietly and suppress the logging to the console.</dd>
 * <dt><tt>-r</tt></dt>
 * <dd>...</dd>
 * <dt><tt>-s[tyle] &lt;style&gt;</tt></dt>
 * <dt><tt>-style=&lt;style&gt;</tt></dt>
 * <dd>Add the style to the styles to be applied.</dd>
 * <dt><tt>-t[ranscript] &lt;transcript&gt;</tt></dt>
 * <dt><tt>-transcript=&lt;transcript&gt;</tt></dt>
 * <dd>Sent the transcript to the given file. This setting overwrites previous
 * transcript file names. The default is to use the extension <tt>.ilg</tt>
 * together with the first index file name.</dd>
 * <dt><tt>-V[ersion]</tt></dt>
 * <dd>Print the version and exit.</dd>
 * <dt><tt>-h[elp]</tt></dt>
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
 * @version $Revision:7779 $
 */
public class Makeindex {

    /**
     * The field <tt>localizer</tt> contains the localizer.
     */
    private static final Localizer LOCALIZER =
            LocalizerFactory.getLocalizer(Makeindex.class);

    /**
     * The field <tt>REVISION</tt> contains the revision number.
     */
    public static final String REVISION =
            "$Revision:7779 $".replaceAll("[^0-9]", "");

    /**
     * The field <tt>VERSION</tt> contains the version number.
     */
    public static final String VERSION = "0.1";

    /**
     * The field <tt>PROP_COLLATE_GERMAN</tt> contains the key for the German
     * property.
     */
    protected static final String PROP_COLLATE_GERMAN =
            "makeindex.collate.german";

    /**
     * The field <tt>PROP_COLLATE_SPACES</tt> contains the key for the collate
     * property.
     */
    protected static final String PROP_COLLATE_SPACES =
            "makeindex.collate.spaces";

    /**
     * The field <tt>PROP_CONFIG</tt> contains the key for the configuration
     * property.
     */
    protected static final String PROP_CONFIG = "makeindex.config";

    /**
     * The field <tt>PROP_INPUT_ENCODING</tt> contains the key for the input
     * encoding property.
     */
    protected static final String PROP_INPUT_ENCODING =
            "makeindex.input.encoding";

    /**
     * The field <tt>PROP_LETTER_ORDERING</tt> contains the key for the letter
     * ordering property.
     */
    protected static final String PROP_LETTER_ORDERING =
            "makeindex.letter.ordering";

    /**
     * The field <tt>PROP_OUTPUT_ENCODING</tt> contains the key for the output
     * encoding property.
     */
    protected static final String PROP_OUTPUT_ENCODING =
            "makeindex.output.encoding";

    /**
     * The field <tt>PROP_OUTPUT</tt> contains the key for the output property.
     */
    protected static final String PROP_OUTPUT = "makeindex.output";

    /**
     * The field <tt>PROP_PAGE_COMPRESSION</tt> contains the key for the page
     * compression property.
     */
    protected static final String PROP_PAGE_COMPRESSION =
            "makeindex.page.compression";

    /**
     * The field <tt>PROP_START_PAGE</tt> contains the key for the start page
     * property.
     */
    protected static final String PROP_START_PAGE = "makeindex.start.page";

    /**
     * The field <tt>PROP_STYLE_ENCODING</tt> contains the key for the style
     * encoding property.
     */
    protected static final String PROP_STYLE_ENCODING =
            "makeindex.style.encoding";

    /**
     * The field <tt>PROP_TRANSCRIPT</tt> contains the key for the transcript
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
     * The field <tt>banner</tt> contains the indicator that the banner needs to
     * be written.
     */
    private boolean banner = true;

    /**
     * The field <tt>comparator</tt> contains the comparator.
     */
    private MakeindexComparator comparator = new MakeindexComparator();

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
     * The field <tt>files</tt> contains the input files.
     */
    private List<String> files = new ArrayList<String>();

    /**
     * The field <tt>logger</tt> contains the logger for messages.
     */
    private Logger logger = null;

    /**
     * The field <tt>styles</tt> contains the style files.
     */
    private ArrayList<String> styles = new ArrayList<String>();

    /**
     * The field <tt>resourceFinder</tt> contains the resource finder.
     */
    private ResourceFinder resourceFinder;

    /**
     * The field <tt>properties</tt> contains the properties controlling the
     * behavior.
     */
    private Properties properties;


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
     * @param file the index file. If the file is <code>null</code> then the
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
     * @return <code>true</code> iff all letters could be digested
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
        } catch (ConfigurationException e) {
            log(e.getMessage());
            return -1;
        } catch (UnknownOptionException e) {
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
        Reader reader = new InputStreamReader(nis,
            properties.getProperty(PROP_STYLE_ENCODING));
        info("ScanningStyle", file);
        try {
            int[] count =
                    MakeindexParameters.load(reader, file, params, logger);
            info("ScanningStyleDone",
                Integer.toString(count[0]), Integer.toString(count[1]));
        } finally {
            reader.close();
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

        Writer w;
        String fmt;
        String output = properties.getProperty(PROP_OUTPUT);

        if (output == null) {
            fmt = "GeneratingOutput";
            w = new OutputStreamWriter(System.out, // 
                properties.getProperty(PROP_OUTPUT_ENCODING));
        } else {
            fmt = "GeneratingOutputFile";
            w = new OutputStreamWriter(new FileOutputStream(output),
                properties.getProperty(PROP_OUTPUT_ENCODING));
        }
        try {
            Parameters params = index.getParams();
            IndexWriter indexWriter = new MakeindexWriter(w, params);
            long[] warn = {0, 0};
            PageProcessor pageProcessor =
                    new MakeindexPageProcessor(params, logger);

            info("Sorting");
            List<Entry> entries =
                    index.sort(comparator, pageProcessor, logger, warn);
            info("SortingDone", Long.toString(warn[1]));
            info(fmt, output);

            int[] count = indexWriter.write(entries, logger, getStartPage(),
                pageProcessor);

            info("GeneratingOutputDone",
                Integer.toString(count[0]),
                Long.toString(count[1] + warn[0]));
        } finally {
            w.close();
        }
        if (output == null) {
            info("StandardOutput", output);
        } else {
            info("Output", output);
        }
    }

}
