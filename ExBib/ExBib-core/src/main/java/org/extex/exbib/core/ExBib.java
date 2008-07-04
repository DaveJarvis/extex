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

package org.extex.exbib.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.sorter.SorterFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.auxio.AuxReader;
import org.extex.exbib.core.io.auxio.AuxReaderFactory;
import org.extex.exbib.core.io.bblio.BblWriterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.util.DBObserver;
import org.extex.exbib.core.util.EntryObserver;
import org.extex.exbib.core.util.FuncallObserver;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.exbib.core.util.ResourceObserverImpl;
import org.extex.exbib.core.util.TracingObserver;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This class contains the assembler for ExBib.
 * <p>
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class ExBib {

    /**
     * This enumeration names the point for debugging.
     */
    public enum ExBibDebug {
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
        SEARCH,
        /**
         * The field <tt>TRACE</tt> contains the indicator for tracing.
         */
        TRACE
    }

    /**
     * The field <tt>VERSION</tt> contains the official version number.
     */
    public static final String VERSION = "0.1";

    /**
     * The field <tt>INCEPTION_YEAR</tt> contains the year the development has
     * been started. This is fixed to be 2002 and should not be altered.
     */
    public static final int INCEPTION_YEAR = 2002;

    /**
     * The field <tt>CONFIGURATION_DEFAULT</tt> contains the name of the
     * configuration to be used in default mode.
     */
    private static final String CONFIGURATION_DEFAULT = "exbib";

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
     * The field <tt>PROP_CONFIG</tt> contains the name of the property to
     * carry the configuration.
     */
    public static final String PROP_CONFIG = "exbib.config";

    /**
     * The field <tt>PROP_BIB_ENCODING</tt> contains the name of the property
     * to carry the encoding for bib files.
     */
    public static final String PROP_BIB_ENCODING = "exbib.bib.encoding";

    /**
     * The field <tt>PROP_SORT</tt> contains the specification for the sorter.
     */
    public static final String PROP_SORT = "exbib.sort";

    /**
     * The field <tt>PROP_CSF_ENCODING</tt> contains the name of the property
     * to carry the encoding for csf files.
     */
    public static final String PROP_CSF_ENCODING = "exbib.csf.encoding";

    /**
     * The field <tt>PROP_ENCODING</tt> contains the name of the property to
     * carry the encoding.
     */
    public static final String PROP_ENCODING = "exbib.encoding";

    /**
     * The field <tt>PROP_FILE</tt> contains the name of the property to carry
     * the aux file.
     */
    public static final String PROP_FILE = "exbib.file";

    /**
     * The field <tt>PROP_MIN_CROSSREF</tt> contains the name of the property
     * containing the value for <tt>min.crossrefs</tt>.
     */
    public static final String PROP_MIN_CROSSREF = "exbib.min.crossref";

    /**
     * The field <tt>PROP_OUTFILE</tt> contains the name of the property for
     * the output.
     */
    public static final String PROP_OUTFILE = "exbib.output";

    /**
     * The field <tt>PROP_PROCESSOR</tt> contains the name of the property for
     * the processor type.
     */
    public static final String PROP_PROCESSOR = "exbib.processor";

    /**
     * The field <tt>debug</tt> contains the indicator for debugging output.
     */
    private Set<ExBibDebug> debug = new HashSet<ExBibDebug>();

    /**
     * The field <tt>errors</tt> contains the number of errors reported.
     */
    private int errors = 0;

    /**
     * The field <tt>warnings</tt> contains the number of warnings.
     */
    private long warnings = 0;

    /**
     * The field <tt>properties</tt> contains the settings for the program.
     */
    private Properties properties = null;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * Creates a new object. The properties containing the controlling
     * attributes are initialized from the System.properties.
     * 
     * @throws IOException in case of an I/O error while reading the dot file
     */
    public ExBib() throws IOException {

        this(System.getProperties());
    }

    /**
     * Creates a new object.
     * 
     * @param properties the properties with the settings
     * 
     * @throws IOException in case of an I/O error while reading the dot file
     */
    public ExBib(Properties properties) throws IOException {

        super();

        useLanguage(Locale.getDefault());
        setProperties(properties);
    }

    /**
     * Report an error and increment the error counter.
     * 
     * @param tag the tag of the resource bundle
     * @param args the arguments to be inserted for braced numbers
     * 
     * @return <code>false</code>
     */
    protected boolean error(String tag, Object... args) {

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
    protected boolean error(Throwable e, String tag) {

        errors++;
        log(tag, e.getLocalizedMessage());

        if (debug.contains(ExBibDebug.MISC)) {
            logger.throwing("", "", e);
        }

        return false;
    }

    /**
     * Getter for debug.
     * 
     * @return the debug
     */
    public Set<ExBibDebug> getDebug() {

        return debug;
    }

    /**
     * Getter for properties.
     * 
     * @return the properties
     */
    public Properties getProperties() {

        return properties;
    }

    /**
     * Getter for a property
     * 
     * @param key the key
     * 
     * @return the value or <code>null</code>
     * 
     * @see java.util.Properties#getProperty(java.lang.String)
     */
    public String getProperty(String key) {

        return properties.getProperty(key);
    }

    /**
     * Log an info message.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return <code>false</code>
     */
    protected boolean info(String tag, Object... args) {

        return log(Level.INFO, tag, args);
    }

    /**
     * Log a message.
     * 
     * @param level the log level
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return <code>false</code>
     */
    protected boolean log(Level level, String tag, Object... args) {

        try {
            logger.log(level, //
                MessageFormat.format(bundle.getString(tag), args));
        } catch (MissingResourceException e) {
            logger.severe(MessageFormat.format(bundle.getString("missing.tag"),
                tag));
        }
        return false;
    }

    /**
     * Log a severe message.
     * 
     * @param tag the resource tag
     * @param args the arguments to be inserted
     * 
     * @return <code>false</code>
     */
    protected boolean log(String tag, Object... args) {

        return log(Level.SEVERE, tag, args);
    }

    /**
     * Write a message to the logger. It is preceded by the banner if the banner
     * has not been shown before.
     * 
     * @param tag the resource tag of the message pattern
     * @param args the arguments
     * 
     * @return <code>false</code>
     */
    protected boolean logBanner(String tag, Object... args) {

        return log(Level.SEVERE, tag, args);
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
        sorterFactory.enableLogging(logger);
        sorterFactory.setResourceFinder(finder);
        sorterFactory.setProperties(properties);
        return sorterFactory;
    }

    /**
     * Set a property to a given value if not set yet.
     * 
     * @param name the name of the property
     * @param value the default value
     */
    protected void propertyDefault(String name, String value) {

        if (!properties.containsKey(name) && value != null) {
            properties.setProperty(name, value);
        }
    }

    /**
     * Attach a handler to the logger to direct messages to a log file.
     * 
     * @param log the base name of the file
     * @param extension the extension
     * 
     * @throws IOException in case of an I/O error
     */
    protected void recognizeFile(String log, String extension)
            throws IOException {

        //
    }

    /**
     * This is the top level of the BibT<sub>E</sub>X engine. When all
     * parameters are present then this method can be invoked.
     * 
     * @return <code>false</code> iff an error has occurred
     * 
     * @throws IOException is case of an uncaught IOException
     * @throws ConfigurationException in case that the top-level configuration
     *         could not be found
     */
    public boolean run() throws IOException, ConfigurationException {

        long time = System.currentTimeMillis();

        try {
            String file = properties.getProperty(PROP_FILE);
            if (file == null) {
                return error("missing.file");
            }
            file = stripExtension(file, AUX_FILE_EXTENSION);

            Configuration config = ConfigurationFactory.newInstance(//
                "exbib/" + properties.getProperty(PROP_CONFIG, ""));

            ResourceFinder finder =
                    new ResourceFinderFactory().createResourceFinder(//
                        config.getConfiguration("Resource"), //
                        logger, //
                        properties, //
                        null);

            if (debug.contains(ExBibDebug.SEARCH)) {
                finder.enableTracing(true);
            }

            recognizeFile(file, BLG_FILE_EXTENSION);

            FuncallObserver funcall = null;

            String encoding = properties.getProperty(PROP_ENCODING);
            BibReaderFactory bibReaderFactory =
                    new BibReaderFactory(config.getConfiguration("BibReader"),
                        finder, properties.getProperty(PROP_BIB_ENCODING),
                        encoding);
            ProcessorContainer container =
                    new ProcessorContainer(config, logger, properties) {

                        /**
                         * {@inheritDoc}
                         * 
                         * @see org.extex.exbib.core.ProcessorContainer#prepareProcessor(
                         *      org.extex.exbib.core.Processor,
                         *      org.extex.exbib.core.db.DB)
                         */
                        @Override
                        protected void prepareProcessor(Processor processor,
                                DB db)
                                throws NotObservableException,
                                    ExBibIllegalValueException {

                            db.registerObserver("makeEntry", new EntryObserver(
                                logger, processor));
                        }
                    };
            container.setSorterFactory(makeSorterFactory(finder, config
                .getConfiguration("Sorter")));
            container.setBibReaderFactory(bibReaderFactory);
            container.setResourceFinder(finder);
            container.registerObserver("startRead", new DBObserver(logger,
                bundle.getString("observer.db.pattern")));
            if (debug.contains(ExBibDebug.TRACE)) {
                funcall = runRegisterTracers(container);
            }

            AuxReader auxReader = new AuxReaderFactory(//
                config.getConfiguration("AuxReader")).newInstance(finder);
            auxReader.register(new ResourceObserverImpl(logger));

            try {
                auxReader.load(container, file, encoding);
            } catch (FileNotFoundException e) {
                return error("aux.not.found", e.getMessage());
            }

            if (!validate(container, file)) {
                return false;
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

                            info("output.file", file);
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

            for (String key : container) {
                Processor processor = container.getProcessor(key);

                for (String style : processor.getBibliographyStyles()) {
                    info("bst.file", style);
                }

                String outfile = properties.getProperty(PROP_OUTFILE);
                if (outfile == null || !"bbl".equals(key)) {
                    outfile = file + "." + key;
                }
                Writer writer = null;
                try {
                    writer = bblWriterFactory.newInstance(outfile);
                    warnings += processor.process(writer);
                } catch (FileNotFoundException e) {
                    return error("output.could.not.be.opened", outfile);
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
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
            return error("verbatim", e.getLocalizedMessage());
        } catch (NoClassDefFoundError e) {
            return error(e, "installation.error");
        } catch (NotObservableException e) {
            return error(e, "internal.error");
        } catch (RuntimeException e) {
            return error(e, "internal.error");
        } finally {
            if (warnings > 0) {
                info(warnings == 1 ? "warning" : "warnings", //
                    Long.toString(warnings));
            }
            if (errors > 0) {
                log(errors == 1 ? "error" : "errors", Long.toString(errors));
            }
        }

        return errors <= 0;
    };

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

        FuncallObserver funcall =
                (debug.contains(ExBibDebug.TRACE)
                        ? new FuncallObserver(logger)
                        : null);

        container.registerObserver("step", new TracingObserver(logger, bundle
            .getString("step_msg")));

        container.registerObserver("run", new TracingObserver(logger, bundle
            .getString("do_msg")));
        container.registerObserver("step", funcall);
        container.registerObserver("push", new TracingObserver(logger, bundle
            .getString("push_msg")));
        container.registerObserver("startParse", new TracingObserver(logger,
            bundle.getString("start_parse_msg")));
        container.registerObserver("endParse", new TracingObserver(logger,
            bundle.getString("end_parse_msg")));
        return funcall;
    }

    /**
     * Setter for the debugging indicator.
     * 
     * @param d indicator for debugging
     */
    public void setDebug(ExBibDebug d) {

        debug.add(d);
    }

    /**
     * Setter for the debugging indicator.
     * 
     * @param value indicator for debugging
     * 
     * @return <code>true</code> if everything went through
     */
    public boolean setDebug(String... value) {

        for (String s : value) {
            try {
                if ("all".equals(s)) {
                    for (ExBibDebug d : ExBibDebug.values()) {
                        debug.add(d);
                    }
                } else if ("none".equals(s)) {
                    debug.clear();
                } else {
                    setDebug(ExBibDebug.valueOf(s.toUpperCase(Locale.ENGLISH)));
                }
            } catch (IllegalArgumentException e) {
                return logBanner("debug.mode.unknown", s);
            }
        }
        return true;
    }

    /**
     * Setter for the file name
     * 
     * @param arg the file name
     * 
     * @return <code>true</code> if everything went through
     */
    public boolean setFile(String arg) {

        if (properties.getProperty(PROP_FILE) != null) {
            return logBanner("one.file", arg);
        } else if ("".equals(arg)) {
            return logBanner("empty.file", arg);
        }
        properties.setProperty(PROP_FILE, arg);
        return true;
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
     * Setter for properties.
     * 
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {

        this.properties = properties;
        propertyDefault(PROP_BIB_ENCODING, null);
        propertyDefault(PROP_CSF_ENCODING, null);
        propertyDefault(PROP_CONFIG, CONFIGURATION_DEFAULT);
        propertyDefault(PROP_SORT, null);
        propertyDefault(PROP_FILE, null);
        propertyDefault(PROP_ENCODING, null);
        propertyDefault(PROP_OUTFILE, null);
    }

    /**
     * Setter for a property.
     * 
     * @param key the key
     * @param value the value
     * 
     * @return the old value
     */
    public Object setProperty(String key, String value) {

        return properties.setProperty(key, value);
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

        if (file.toLowerCase(Locale.ENGLISH).endsWith(extension)) {
            return file.substring(0, file.length() - extension.length());
        }

        return file;
    }

    /**
     * Activate the language from
     * 
     * @param locale the locale
     */
    public void useLanguage(Locale locale) {

        this.bundle = ResourceBundle.getBundle(ExBib.class.getName(), locale);
    }

    /**
     * Validate the container after a read.
     * 
     * @param container the container
     * @param file the file currently read for error messages
     * 
     * @return <code>true</code> iff everything is fine
     * 
     * @throws ExBibException in case of an error
     * @throws ConfigurationException in case of an configuration problem
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of an error in the CSF processing
     * @throws UnsupportedEncodingException in case of an unsupported encoding
     */
    private boolean validate(ProcessorContainer container, Object file)
            throws ConfigurationException,
                ExBibException,
                UnsupportedEncodingException,
                CsfException,
                IOException {

        if (container.isEmpty()) {
            error("bst.missing", file);
            error("data.missing", file);
            return error("citation.missing", file);
        }

        for (String key : container) {
            Processor p = container.findProcessor(key);
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
