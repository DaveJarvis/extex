/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.filters.StringInputStream;
import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.ExBib.ExBibDebug;
import org.extex.logging.LogFormatter;

/**
 * This class provides an interface from Ant to <logo>&epsilon;&chi;Bib</logo>.
 * 
 * <h2>The <logo>&epsilon;&chi;Bib</logo> Ant Task</h2>
 * 
 * <h3>Invocation</h3>
 * 
 * <p>
 * <logo>&epsilon;&chi;Bib</logo> provides an integration into Apache Ant. This
 * allows the invocation of <logo>&epsilon;&chi;Bib</logo> from within Ant. For
 * this purpose an Ant task can be defined as follows.
 * </p>
 * 
 * <pre>
 *  &lt;<b>taskdef</b> name="ExBib"
 *           classname="org.extex.exbib.ant.ExBibTask" /&gt;
 * </pre>
 * 
 * <p>
 * This assumes that the jars from the <logo>&epsilon;&chi;Bib</logo>
 * distribution are on the class path. You can extend the class path for this
 * definition with the classpath attribute as shown below.
 * </p>
 * 
 * <pre>
 *  &lt;<b>taskdef</b> name="ExBib"
 *           classname="org.extex.exbib.ant.ExBibTask"
 *           classpath="classes" /&gt;
 * </pre>
 * 
 * <p>
 * As a result you have defined the task named <tt>ExBib</tt>. This task can be
 * used in arbitrary targets.
 * </p>
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"/&gt;
 * </pre>
 * 
 * <p>
 * Note that a version of Ant is required. Ant is not not included in the
 * distribution. Thus it is possible to use the <logo>&epsilon;&chi;Bib</logo>
 * Ant task together with the existing Ant installation.
 * </p>
 * 
 * 
 * <h3>Options</h3>
 * 
 * <p>
 * The body of the task invocation can contain options. They are treated as if
 * they where read from a properties file. The names and values are those which
 * could also be taken from a dot file.
 * </p>
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"&gt;
 *      exbib.encoding=UTF-8
 *     &lt;/ExBib&gt;
 * </pre>
 * 
 * <p>
 * Note that typos in option names are silently ignored.
 * </p>
 * 
 * 
 * <h3>Parameters</h3>
 * 
 * <p>
 * The invocation of the task can be controlled by several parameters. Those
 * parameters are given as attributes to the task.
 * </p>
 * 
 * <dl>
 * <dt>file="&lang;file&rang;"</dt>
 * <dd>The parameter is the name of the <tt>aux</tt> file to read further
 * parameters from. This attribute is mandatory.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"/&gt;
 * </pre>
 * </dd>
 * 
 * <dt>sort="&lang;sorter&rang;"</dt>
 * <dd>This option can be used to specify the sorter.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            sort="encoding:de" /&gt;
 * </pre>
 * 
 * <dt>encoding="&lang;enc&rang;"</dt>
 * <dd>This option can be used to specify the encoding for reading files. The
 * default is to use the platform default encoding.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            encoding="UTF-8" /&gt;
 * </pre>
 * 
 * <dt>bibEncoding="&lang;enc&rang;"</dt>
 * <dd>This option can be used to specify the encoding for reading database
 * files. The default is to use the same value as the parameter
 * <tt>encoding</tt>.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            bibEncoding="UTF-8" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>csf="&lang;csf&rang;"</dt>
 * <dd>The option can be used to specify the CSF which cintains character
 * definitions and sorting order specification.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            csf="german.csf" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>csfEncoding="&lang;enc&rang;"</dt>
 * <dd>This option contains the encoding for reading cs files. The encoding
 * needs to be a valid character set. The fallback is the platform default
 * encoding.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            csf="german.csf" 
 *            csfEncoding="UTF-8" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>minCrossrefs="&lang;number&rang;"</dt>
 * <dd>The parameter is a number. It gives the number of cross references before
 * the entries are left alone and not collapsed. The default value is 2.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            minCrossrefs="3" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>output="&lang;file&rang;"</dt>
 * <dd>This parameter redirects the output for the default type of references to
 * the given file. The default is derived from the name of the aux file by using
 * the extension <tt>.bbl</tt>.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            output="file.bbl" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>logfile="&lang;file&rang;"]</dt>
 * <dd>This option can be used to redirect the output to a file. The default is
 * to print the informative messages to the console only.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            logfile="file.blg" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>config="&lang;config&rang;"</dt>
 * <dd>This parameter can be used to specify the configuration for assembling
 * <logo>&epsilon;&chi;Bib</logo>. The default value is <tt>exbib</tt>. The
 * value <tt>bibtex099</tt> can be used to switch to the compatibility mode for
 * B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X 0.99c.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            config="bibtex099" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>load="&lang;file&rang;"</dt>
 * <dd>In contrast to the command line interface no dot files are read by the
 * Ant task. This attribute can be used to load dot files.
 * <p>
 * The value is the name of the parameter file to load. It can be relative to
 * the current directory or absolute. If the first letter is a <tt>~</tt> then
 * it is replaced with the user's home directory.
 * </p>
 * <p>
 * This attribute can be given several times to load different dot files.
 * </p>
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            load="~/.exbib"
 *            load="./.exbib" /&gt;
 * </pre>
 * </dd>
 * 
 * <dt>debug="&lang;flags&rang;"</dt>
 * <dd>This option can be used to specify debug options.
 * 
 * <pre>
 *   &lt;<b>target</b> name="simple"
 *           description="This is a simple invocation of ExBib." &gt;
 *     &lt;ExBib file="file.aux"
 *            logfile="file.blg"
 *            debug="trace,search" /&gt;
 * </pre>
 * </dd>
 * 
 * </dl>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibTask extends Task {

    /**
     * The field <tt>logfile</tt> contains the name of the log file.
     */
    private String logfile = null;

    /**
     * The field <tt>properties</tt> contains the properties.
     */
    private Properties properties = new Properties();

    /**
     * The field <tt>debug</tt> contains the debug flags.
     */
    private Set<ExBibDebug> debug = null;

    /**
     * The field <tt>text</tt> contains the body text.
     */
    private StringBuilder text = new StringBuilder();

    /**
     * Creates a new object.
     */
    public ExBibTask() {

    }

    /**
     * Adder for body text.
     * 
     * @param t the text
     */
    public void addText(String t) {

        this.text.append(t);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        if (text.length() > 0) {
            StringInputStream r = new StringInputStream(text.toString());
            try {
                try {
                    properties.load(r);
                } finally {
                    r.close();
                }
            } catch (IOException e) {
                throw new BuildException(e);
            }
        }
        Logger logger = makeLogger();
        ExBib exbib;
        try {
            exbib = new ExBib(properties);
            exbib.setLogger(logger);
            if (debug != null) {
                for (ExBibDebug d : debug) {
                    exbib.setDebug(d);
                }
            }
            exbib.run();
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    /**
     * Create the logger.
     * 
     * @return the logger
     */
    private Logger makeLogger() {

        Logger logger = Logger.getLogger(ExBibTask.class.getName());
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

                ExBibTask.this.log(record.getMessage(), Project.MSG_WARN);
            }
        };
        logAdaptor.setFormatter(new LogFormatter());
        logAdaptor.setLevel(Level.WARNING);
        logger.addHandler(logAdaptor);
        if (logfile != null && !logfile.equals("")) {
            Handler fileHandler;
            try {
                fileHandler = new FileHandler(logfile);
            } catch (Exception e) {
                throw new BuildException(e);
            }
            fileHandler.setFormatter(new LogFormatter());
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        }

        return logger;
    }

    /**
     * Setter for bibEncoding.
     * 
     * @param bibEncoding the bibEncoding to set
     */
    public void setBibEncoding(String bibEncoding) {

        properties.setProperty(ExBib.PROP_BIB_ENCODING, bibEncoding);
    }

    /**
     * Setter for config.
     * 
     * @param config the config to set
     */
    public void setConfig(String config) {

        properties.setProperty(ExBib.PROP_CONFIG, config);
    }

    /**
     * Setter for csf.
     * 
     * @param csf the csf to set
     */
    public void setCsf(String csf) {

        properties.setProperty(ExBib.PROP_SORT, "csf:" + csf);
    }

    /**
     * Setter for CSF encoding.
     * 
     * @param encoding the encoding to set
     */
    public void setCsfEncoding(String encoding) {

        properties.setProperty(ExBib.PROP_CSF_ENCODING, encoding);
    }

    /**
     * Setter for debug flags.
     * 
     * @param d the aspect to debug
     */
    public void setDebug(String d) {

        if ("all".equals(d)) {
            for (ExBibDebug x : ExBibDebug.values()) {
                debug.add(x);
            }
        } else if ("none".equals(d)) {
            debug.clear();
        } else {
            try {
                for (String s : d.split("[,;:]")) {
                    debug.add(ExBibDebug.valueOf(s));
                }
            } catch (Exception e) {
                throw new BuildException(e);
            }
        }
    }

    /**
     * Setter for encoding.
     * 
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {

        properties.setProperty(ExBib.PROP_ENCODING, encoding);
    }

    /**
     * Setter for file.
     * 
     * @param file the file to set
     */
    public void setFile(String file) {

        properties.setProperty(ExBib.PROP_FILE, file);
    }

    /**
     * Setter for load.
     * 
     * @param file the file to load
     */
    public void setLoad(String file) {

        File f;
        if (file.startsWith("~")) {
            f = new File(System.getProperty("user.home"), file.substring(1));
        } else {
            f = new File(file);
        }
        try {
            FileInputStream r = new FileInputStream(f);
            try {
                properties.load(r);
            } finally {
                r.close();
            }
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    /**
     * Setter for log file.
     * 
     * @param logfile the log file to set
     */
    public void setLogfile(String logfile) {

        this.logfile = logfile;
    }

    /**
     * Setter for minCrossrefs.
     * 
     * @param minCrossrefs the minCrossrefs to set
     */
    public void setMinCrossrefs(String minCrossrefs) {

        properties.setProperty(ExBib.PROP_MIN_CROSSREF, minCrossrefs);
    }

    /**
     * Setter for output file.
     * 
     * @param outfile the output file to set
     */
    public void setOutput(String outfile) {

        properties.setProperty(ExBib.PROP_OUTFILE, outfile);
    }

    /**
     * Setter for the sorter.
     * 
     * @param sort the sorter
     */
    public void setSort(String sort) {

        properties.setProperty(ExBib.PROP_SORT, sort);
    }

}
