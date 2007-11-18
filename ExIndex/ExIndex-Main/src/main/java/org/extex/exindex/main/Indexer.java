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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exindex.core.Index;
import org.extex.exindex.core.parser.MakeindexParser;
import org.extex.exindex.core.parser.Parser;
import org.extex.exindex.main.exception.MissingArgumentException;
import org.extex.exindex.main.exception.UnknownArgumentException;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.logging.LogFormatter;

/**
 * This is the main program for an indexer a la makeindex or xindy.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Indexer {

    /**
     * The field <tt>localizer</tt> contains the localozer.
     */
    private static final Localizer localizer =
            LocalizerFactory.getLocalizer(Indexer.class);

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
            System.exit(new Indexer().run(args));
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * The field <tt>index</tt> contains the enclosed index.
     */
    private Index index;

    /**
     * The field <tt>output</tt> contains the name of the output file or
     * <code>null</code> for stdout.
     */
    private String output = null;

    /**
     * The field <tt>page</tt> contains the ...
     */
    private String page;

    /**
     * The field <tt>log</tt> contains the name of the log file.
     */
    private String log;

    /**
     * The field <tt>logger</tt> contains the logger for messages.
     */
    private Logger logger;

    /**
     * The field <tt>consoleHandler</tt> contains the handler writing to the
     * console.
     */
    private Handler consoleHandler;

    /**
     * The field <tt>banner</tt> contains the indicator that the banner needs
     * to be written.
     */
    private boolean banner;

    /**
     * The field <tt>collateSpaces</tt> contains the ...
     */
    private boolean collateSpaces = false;

    /**
     * The field <tt>collateGerman</tt> contains the ...
     */
    private boolean collateGerman = false;

    /**
     * Creates a new object.
     * 
     * @throws IOException in case of an I/O error when reading the default
     *         settings
     */
    public Indexer() throws IOException {

        super();

        banner = true;
        index = new Index();

        logger = Logger.getLogger(Indexer.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.INFO);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);
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
                    // strange argument is ignored
                } else if (!a.startsWith("-")) {
                    files.add(a);
                } else if ("-".equals(a)) {
                    if (++i >= args.length) {
                        throw new MissingArgumentException(a);
                    }
                    files.add(args[i]);
                } else if ("-output".startsWith(a)) {
                    if (++i >= args.length) {
                        throw new MissingArgumentException(a);
                    }
                    output = args[i];
                    if (output.equals("") || output.equals("-")) {
                        output = null;
                    }

                } else if ("-style".startsWith(a)) {
                    if (++i >= args.length) {
                        throw new MissingArgumentException(a);
                    }
                    styles.add(args[i]);

                } else if ("-page".startsWith(a)) {
                    if (++i >= args.length) {
                        throw new MissingArgumentException(a);
                    }
                    page = args[i];

                } else if ("-t".startsWith(a)) {
                    if (++i >= args.length) {
                        throw new MissingArgumentException(a);
                    }
                    log = args[i];

                } else if ("-i".startsWith(a)) {
                    files.add(null);
                } else if ("-c".startsWith(a)) {
                    collateSpaces = true;
                } else if ("-g".startsWith(a)) {
                    collateGerman = true;
                } else if ("-l".startsWith(a)) {
                    // TODO letter ordering
                } else if ("-q".startsWith(a)) {
                    logger.removeHandler(consoleHandler);
                } else if ("-r".startsWith(a)) {
                    // TODO range compression
                } else if ("-version".startsWith(a)) {
                    showBanner();
                    return 1;
                } else if ("-help".startsWith(a)) {
                    logger.log(Level.SEVERE, localizer.format("Usage"),
                        "Indexer");
                    return 1;
                } else {
                    throw new UnknownArgumentException(a);
                }

            }

            String transcript = null;

            if (files.size() != 0 && files.get(0) != null) {
                transcript = files.get(0).replaceAll("\\.idx", "") + ".ilg";
                Handler handler = new FileHandler(transcript);
                handler.setFormatter(new LogFormatter());
                handler.setLevel(Level.INFO);
                logger.addHandler(handler);
            }

            for (String s : styles) {
                scanStyle(s);
            }

            if (files.size() == 0) {
                scanInput(null);
            } else {
                for (String s : files) {
                    scanInput(s);
                }
            }
            writeOutput(logger);

            if (transcript != null) {
                logger.log(Level.INFO, localizer.format("Transcript",
                    transcript));
            }

        } catch (FileNotFoundException e) {
            showBanner();
            logger.log(Level.SEVERE, localizer.format("FileNotFound", e
                .getMessage()));
            logger.log(Level.FINE, "", e);
            return -1;
        } catch (Exception e) {
            showBanner();
            logger.log(Level.SEVERE, localizer.format("SevereError", e
                .toString()));
            logger.log(Level.FINE, "", e);
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    /**
     * Load data into the index.
     * 
     * @param file the name of the input file
     * 
     * @throws IOException in case of an I/O error
     */
    protected void scanInput(String file) throws IOException {

        showBanner();

        InputStream stream;

        if (file == null) {
            logger.log(Level.INFO, localizer.format("ScanningStandardInput"));
            stream = System.in;
        } else {
            logger.log(Level.INFO, localizer.format("ScanningInput", file));
            File f = new File(file);
            if (!f.exists()) {
                f = new File(file + ".idx");
                if (!f.exists()) {
                    throw new FileNotFoundException(file);
                }
                if (output == null) {
                    output = file + ".ind";
                }
            } else if (output == null) {
                output = file + ".ind";
            }
            stream = new FileInputStream(f);
        }
        int[] count;
        Reader reader = new InputStreamReader(stream);
        try {
            Parser parser = new MakeindexParser();
            count = parser.load(reader, file, index);
        } finally {
            reader.close();
        }
        logger.log(Level.INFO, localizer.format("ScanningInputDone", //
            Integer.toString(count[0]), Integer.toString(count[1])));
    }

    /**
     * Merge a style into the one already loaded.
     * 
     * @param file the name of the resource
     * 
     * @throws IOException in case of an I/O error
     */
    protected void scanStyle(String file) throws IOException {

        showBanner();
        logger.log(Level.INFO, localizer.format("ScanningStyle", file));
        Reader reader = new InputStreamReader(new FileInputStream(file));
        int[] count;
        try {
            count = index.loadStyle(reader);
        } finally {
            reader.close();
        }
        logger.log(Level.INFO, localizer.format("ScanningStyleDone", //
            Integer.toString(count[0]), Integer.toString(count[1])));
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
     * Show the program banner.
     */
    private void showBanner() {

        if (banner) {
            String build = "$Revision$".replaceAll("[^0-9]", "");
            logger.log(Level.INFO, localizer.format("Banner", build, //
                System.getProperty("java.version")));
            banner = false;
        }
    }

    /**
     * Generate the output.
     * 
     * @param logger the logger
     * 
     * @throws IOException in case of an I/O error
     */
    protected void writeOutput(Logger logger) throws IOException {

        int[] count;
        Writer w;
        String fmt;
        if (output == null) {
            fmt = "GeneratingOutput";
            w = new PrintWriter(System.out);
        } else {
            fmt = "GeneratingOutputFile";
            w = new FileWriter(output);
        }
        try {
            logger.log(Level.INFO, localizer.format("Sorting"));
            int i = 0; // TODO soring info
            logger.log(Level.INFO, localizer.format("SortingDone", //
                Integer.toString(i)));
            logger.log(Level.INFO, localizer.format(fmt, output));
            count = index.print(w, logger);
        } finally {
            w.close();
        }
        logger.log(Level.INFO, localizer.format("GeneratingOutputDone", //
            Integer.toString(count[0]), Integer.toString(count[1])));
        if (output == null) {
            logger.log(Level.INFO, localizer.format("StandardOutput", output));
        } else {
            logger.log(Level.INFO, localizer.format("Output", output));
        }
    }

}
