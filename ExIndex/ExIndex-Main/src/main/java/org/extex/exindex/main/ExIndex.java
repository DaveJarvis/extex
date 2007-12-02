/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

import org.extex.exindex.core.makeindex.MakeindexLoader;
import org.extex.exindex.core.xindy.Indexer;
import org.extex.exindex.core.xparser.MakeindexParser;
import org.extex.exindex.core.xparser.RawIndexParser;
import org.extex.exindex.core.xparser.XindyParser;
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
 * This is the main program for an indexer a la Xindy.
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
     * reading and writing.
     */
    private Charset charset;

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
     */
    public ExIndex()
            throws IOException,
                SecurityException,
                NoSuchMethodException,
                LSettingConstantException {

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
     * @see org.extex.exindex.core.xindy.Indexer#makeRawIndexParser(
     *      java.lang.String)
     */
    @Override
    protected RawIndexParser makeRawIndexParser(String resource)
            throws IOException {

        boolean makeindex = false;
        InputStream stream;
        if (resource == null) {
            stream = System.in;
        } else {
            stream = getResourceFinder().findResource(resource, "raw");
            if (stream == null) {
                stream = getResourceFinder().findResource(resource, "idx");
                makeindex = true;
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

        if (makeindex) {
            return new MakeindexParser(new InputStreamReader(stream), resource,
                this);
        }
        return new XindyParser(new InputStreamReader(stream), resource);
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
     * @see org.extex.exindex.core.xindy.Indexer#markup(java.io.Writer,
     *      java.util.logging.Logger)
     */
    @Override
    protected void markup(Writer writer, Logger logger) {

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
                } else if ("-collateSpaces".startsWith(a)) {
                    eval(new LList(LSymbol.get("sort-rule"), //
                        new LString(" "), //
                        new LString("")));

                } else if ("-filter".startsWith(a)) {
                    filter = getArg(a, args, ++i);

                } else if ("-german".startsWith(a)) {
                    collateGerman = true;

                } else if ("-input".startsWith(a)) {
                    files.add(null);

                } else if ("-letterOrdering".startsWith(a)) {
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

                } else if ("-transcript".startsWith(a)) {
                    transcript = getArg(a, args, ++i);
                    if (transcript.equals("") || transcript.equals("-")) {
                        transcript = null;
                    }

                } else if ("-Charset".startsWith(a)) {
                    try {
                        charset = Charset.forName(getArg(a, args, ++i));
                    } catch (UnsupportedCharsetException e) {
                        throw new MainException(LOCALIZER.format(
                            "UnsupportedCharset", args[i]));
                    }

                } else if ("-Module".startsWith(a)) {
                    load(getArg(a, args, ++i));

                } else if ("-LogLevel".startsWith(a)) {
                    setLogLevel(getArg(a, args, ++i));

                } else if ("-Version".startsWith(a)) {
                    showBanner();
                    return 1;

                } else if ("-help".startsWith(a)) {
                    logger.log(Level.SEVERE, LOCALIZER.format("Usage",
                        "Indexer"));
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
     * @see org.extex.exindex.core.xindy.Indexer#startup(java.util.List,
     *      java.util.logging.Logger)
     */
    @Override
    protected void startup(List<String> styles, Logger logger)
            throws IOException,
                LException {

        showBanner();

        for (String style : styles) {
            logger.log(Level.INFO, LOCALIZER.format("ScanningStyle", style));

            InputStream stream = getResourceFinder().findResource(style, "ist");
            if (stream != null) {

                Reader reader = new InputStreamReader(stream);
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
                Reader reader = new InputStreamReader(stream);
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
