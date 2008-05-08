/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.ant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.ProcessorFactory;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.DBFactory;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibFileNotFoundException;
import org.extex.exbib.core.exceptions.ExBibImpossibleException;
import org.extex.exbib.core.io.Writer;
import org.extex.exbib.core.io.WriterFactory;
import org.extex.exbib.core.io.auxio.AuxReader;
import org.extex.exbib.core.io.auxio.AuxReaderFactory;
import org.extex.exbib.core.io.bblio.BblWriterFactory;
import org.extex.exbib.core.io.bibio.BibReaderFactory;
import org.extex.exbib.core.io.bstio.BstReader;
import org.extex.exbib.core.io.bstio.BstReaderFactory;
import org.extex.exbib.core.io.csf.CsfException;
import org.extex.exbib.core.io.csf.CsfReader;
import org.extex.exbib.core.io.csf.CsfSorter;
import org.extex.exbib.main.util.DBObserver;
import org.extex.exbib.main.util.LogFormatter;
import org.extex.exbib.main.util.MainResourceObserver;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.ConfigurationFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationWrapperException;
import org.extex.resource.ResourceFinder;
import org.extex.resource.ResourceFinderFactory;

/**
 * This class provides an interface from Ant to ExBib.
 * 
 * <pre>
 *   &lt;taskdef name=&quot;ExBib&quot; class=". . ."/&gt;
 *
 *   &lt;ExBib file=&quot;abc&quot;
 *          executable=&quot;pdflatex&quot;
 *          &gt;
 *   &lt;/ExBib&gt;
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibTask extends Task {

    /**
     * The field <tt>AUX_FILE_EXTENSION</tt> contains the extension of aux
     * files (in lower case).
     */
    private static final String AUX_FILE_EXTENSION = ".aux";

    /**
     * The field <tt>BBL_FILE_EXTENSION</tt> contains the extension of output
     * files (in lower case).
     */
    private static final String BBL_FILE_EXTENSION = ".bbl";

    /**
     * The field <tt>BLG_FILE_EXTENSION</tt> contains the extension of log
     * files (in lower case).
     */
    private static final String BLG_FILE_EXTENSION = ".blg";

    /**
     * The field <tt>BST_FILE_EXTENSION</tt> contains the extension of BibTeX
     * style files (in lower case).
     */
    private static final String BST_FILE_EXTENSION = ".bst";

    /**
     * The field <tt>file</tt> contains the aux file name.
     */
    private String file = null;

    /**
     * The field <tt>encoding</tt> contains the encoding.
     */
    private String encoding = null;

    /**
     * The field <tt>bibEncoding</tt> contains the encoding for bib files.
     */
    private String bibEncoding = null;

    /**
     * The field <tt>csf</tt> contains the name or the csf file or
     * <code>null</code>.
     */
    private String csf = null;

    /**
     * The field <tt>config</tt> contains the name of the configuration.
     */
    private String config = "exbib";

    /**
     * The field <tt>minCrossrefs</tt> contains the minimum crossref number.
     */
    private int minCrossrefs = 2;

    /**
     * The field <tt>bundle</tt> contains the resource bundle for i18n.
     */
    private ResourceBundle bundle;

    /**
     * The field <tt>outfile</tt> contains the name of the output file.
     */
    private String outfile = null;

    /**
     * The field <tt>logfile</tt> contains the name of the log file.
     */
    private String logfile = null;

    /**
     * The field <tt>logger</tt> contains the logger.
     */
    private Logger logger;

    /**
     * Creates a new object.
     */
    public ExBibTask() {

        super();

        bundle = ResourceBundle.getBundle(getClass().getName());

        logger = Logger.getLogger(ExBibTask.class.getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        Handler logAdaptor = new Handler() {

            @Override
            public void close() throws SecurityException {

                //
            }

            @Override
            public void flush() {

                //
            }

            @Override
            public void publish(LogRecord record) {

                log(record.getMessage());
            }
        };
        logAdaptor.setFormatter(new LogFormatter());
        logAdaptor.setLevel(Level.WARNING);
        logger.addHandler(logAdaptor);
    }

    /**
     * Attach a handler to the logger to direct messages to a log file.
     * 
     * @param log the base name of the file
     * @param extension the extension
     * 
     * @throws IOException in case of an I/O error
     */
    protected void attachFileLogger(String log, String extension)
            throws IOException {

        if (logfile == null && log != null && !log.equals("")
                && !log.equals("-")) {
            logfile = log + extension;
        }
        if (logfile != null && !logfile.equals("")) {
            Handler fileHandler = new FileHandler(logfile);
            fileHandler.setFormatter(new LogFormatter());
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        Writer writer = null;

        try {
            Configuration config =
                    ConfigurationFactory.newInstance(this.config);
            ResourceFinder finder =
                    new ResourceFinderFactory().createResourceFinder(config
                        .getConfiguration("Resource"), logger, System
                        .getProperties(), null);
            if (file == null) {
                throw new BuildException(msg("missing.file"));
            }
            file = stripExtension(file, AUX_FILE_EXTENSION);
            CsfSorter sorter = null;

            attachFileLogger(file, BLG_FILE_EXTENSION);

            if ("".equals(csf)) {
                sorter = new CsfSorter();
            } else if (csf != null) {
                InputStream is = finder.findResource(csf, "csf");
                if (is == null) {
                    throw new BuildException(msg("csf.missing"));
                }
                try {
                    sorter = new CsfReader().read(new InputStreamReader(is));
                } catch (CsfException e) {
                    throw new BuildException(e.getLocalizedMessage());
                } finally {
                    is.close();
                }
            }

            writer = makeBblWriter(file, config);

            if (writer == null) {
                throw new BuildException();
            }

            BibReaderFactory bibReaderFactory = new BibReaderFactory(//
                config.getConfiguration("BibReader"), finder);
            if (bibEncoding != null) {
                bibReaderFactory.setEncoding(bibEncoding);
            }
            DB db =
                    new DBFactory(//
                        config.getConfiguration("DB")).newInstance(
                        bibReaderFactory, minCrossrefs);
            if (sorter != null) {
                db.setSorter(sorter);
            }

            Processor processor = new ProcessorFactory(//
                config.getConfiguration("Processor")).newInstance(db);

            processor.registerObserver("startRead", new DBObserver(logger,
                bundle.getString("observer.db.pattern")));

            AuxReader auxReader = new AuxReaderFactory(//
                config.getConfiguration("AuxReader")).newInstance(finder);
            auxReader.register(new MainResourceObserver(logger));

            int errors = 0;
            // try {
            // int[] no = auxReader.load(processor, file, encoding);
            //
            // if (no[1] == 0) {
            // errors++;
            // log("bst.missing", file);
            // }
            // if (no[0] == 0) {
            // errors++;
            // log("data.missing", file);
            // }
            // if (no[2] == 0) {
            // errors++;
            // log("citation.missing", file);
            // }
            // } catch (FileNotFoundException e) {
            // throw new BuildException(msg("aux.not.found", e.getMessage()));
            // }
            // TODO rewrite

            String bst = null;
            if (bst != null) {
                processor.addBibliographyStyle(stripExtension(bst,
                    BST_FILE_EXTENSION));
            }

            List<String> bibliographyStyles = processor.getBibliographyStyles();

            if (bibliographyStyles == null || bibliographyStyles.isEmpty()) {
                throw new BuildException();
            }
            bst = bibliographyStyles.get(0);
            bst = stripExtension(bst, BST_FILE_EXTENSION);
            log("bst.file", bst);
            BstReader bstReader =
                    new BstReaderFactory(config.getConfiguration("BstReader"))
                        .newInstance();
            bstReader.setResourceFinder(finder);
            try {
                bstReader.parse(processor);
            } catch (FileNotFoundException e) {
                throw new BuildException(msg("bst.not.found", e.getMessage()));
            }

            processor.process(writer, logger);

            if (errors > 0) {
                throw new BuildException(msg(errors == 1 ? "error" : "errors",
                    Long.toString(errors)));
            }
            long warnings = processor.getNumberOfWarnings();
            if (warnings > 0) {
                log(warnings == 1 ? "warning" : "warnings", //
                    Long.toString(warnings));
            }

        } catch (BuildException e) {
            throw e;
        } catch (IOException e) {
            throw new BuildException(e);
        } catch (ExBibImpossibleException e) {
            throw new BuildException(e);
        } catch (ExBibFileNotFoundException e) {
            throw new BuildException(e);
        } catch (ExBibException e) {
            throw new BuildException(e);
        } catch (ConfigurationWrapperException e) {
            throw new BuildException(msg("installation.error", //
                e.getLocalizedMessage()), e);
        } catch (ConfigurationException e) {
            throw new BuildException(msg("installation.error", //
                e.getLocalizedMessage()), e);
        } catch (NoClassDefFoundError e) {
            throw new BuildException(msg("installation.error", //
                e.getLocalizedMessage()), e);
        } catch (Exception e) {
            throw new BuildException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new BuildException(e);
                }
            }
        }

    }

    /**
     * Getter for bibEncoding.
     * 
     * @return the bibEncoding
     */
    public String getBibEncoding() {

        return bibEncoding;
    }

    /**
     * Getter for config.
     * 
     * @return the config
     */
    public String getConfig() {

        return config;
    }

    /**
     * Getter for csf.
     * 
     * @return the csf
     */
    public String getCsf() {

        return csf;
    }

    /**
     * Getter for encoding.
     * 
     * @return the encoding
     */
    public String getEncoding() {

        return encoding;
    }

    /**
     * Getter for file.
     * 
     * @return the file
     */
    public String getFile() {

        return file;
    }

    /**
     * Getter for logfile.
     * 
     * @return the logfile
     */
    public String getLogfile() {

        return logfile;
    }

    /**
     * Getter for minCrossrefs.
     * 
     * @return the minCrossrefs
     */
    public int getMinCrossrefs() {

        return minCrossrefs;
    }

    /**
     * Getter for outfile.
     * 
     * @return the outfile
     */
    public String getOutfile() {

        return outfile;
    }

    /**
     * Write a formatted message to the log stream.
     * 
     * @param tag the tag name
     * @param args the arguments
     */
    private void log(String tag, Object... args) {

        log(msg(tag, args));
    }

    /**
     * Create a new {@link java.io.Writer} for a bbl file.
     * 
     * @param file the name of the file
     * @param cfg the configuration
     * @return the new writer or <code>null</code> when the file could not be
     *         opened for writing
     * 
     * @throws UnsupportedEncodingException if an error with the encoding is
     *         encountered
     * @throws ConfigurationException in case of a configuration error
     */
    private Writer makeBblWriter(String file, Configuration cfg)
            throws UnsupportedEncodingException,
                ConfigurationException {

        Configuration configuration = cfg.getConfiguration("BblWriter");
        WriterFactory writerFactory = new WriterFactory(configuration);
        if (encoding != null) {
            writerFactory.setEncoding(encoding);
        }
        Writer writer = null;

        if (outfile == null) {
            outfile = file + BBL_FILE_EXTENSION;
        }

        if (outfile.equals("")) {
            writer = writerFactory.newInstance();
            log("output.discarted");
        } else if (outfile.equals("-")) {
            writer = writerFactory.newInstance(System.out);
            log("output.to.stdout");
        } else {
            try {
                writer = writerFactory.newInstance(outfile);
            } catch (FileNotFoundException e) {
                log("output.could.not.be.opened", outfile);
                return null;
            }
            log("output.file", outfile);
        }

        return new BblWriterFactory(configuration).newInstance(writer);
    }

    /**
     * Prepare a message from a pattern in the resource bundle.
     * 
     * @param tag the make of the pattern
     * @param args the arguments
     * 
     * @return the message
     */
    private String msg(String tag, Object... args) {

        return MessageFormat.format(bundle.getString(tag), args);
    }

    /**
     * Setter for bibEncoding.
     * 
     * @param bibEncoding the bibEncoding to set
     */
    public void setBibEncoding(String bibEncoding) {

        this.bibEncoding = bibEncoding;
    }

    /**
     * Setter for config.
     * 
     * @param config the config to set
     */
    public void setConfig(String config) {

        this.config = config;
    }

    /**
     * Setter for csf.
     * 
     * @param csf the csf to set
     */
    public void setCsf(String csf) {

        this.csf = csf;
    }

    /**
     * Setter for encoding.
     * 
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {

        this.encoding = encoding;
    }

    /**
     * Setter for file.
     * 
     * @param file the file to set
     */
    public void setFile(String file) {

        this.file = file;
    }

    /**
     * Setter for logfile.
     * 
     * @param logfile the logfile to set
     */
    public void setLogfile(String logfile) {

        this.logfile = logfile;
    }

    /**
     * Setter for minCrossrefs.
     * 
     * @param minCrossrefs the minCrossrefs to set
     */
    public void setMinCrossrefs(int minCrossrefs) {

        this.minCrossrefs = minCrossrefs;
    }

    /**
     * Setter for outfile.
     * 
     * @param outfile the outfile to set
     */
    public void setOutfile(String outfile) {

        this.outfile = outfile;
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

        if (file.toLowerCase().endsWith(extension)) {
            return file.substring(0, file.length() - extension.length());
        }

        return file;
    }

}
