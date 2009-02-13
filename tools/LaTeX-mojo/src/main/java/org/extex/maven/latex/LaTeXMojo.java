/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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

package org.extex.maven.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.extex.maven.latex.make.Net;

/**
 * This mojo can act as a compiler for LaTeX documents.
 * 
 * @goal latex
 * @execute phase=compile
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LaTeXMojo extends AbstractMojo {

    /**
     * This class is an adaptor of a Logger to a Log.
     */
    private class LogAdaptor extends Logger {

        /**
         * The field <tt>log</tt> contains the log.
         */
        private final Log log;

        /**
         * Creates a new object.
         * 
         * @param log the log
         */
        protected LogAdaptor(Log log) {

            super(LaTeXMojo.class.getName(), LaTeXMojo.class.getName());
            this.log = log;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#entering(java.lang.String,
         *      java.lang.String)
         */
        @Override
        public void entering(String sourceClass, String sourceMethod) {

            // TODO gene: entering unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#entering(java.lang.String,
         *      java.lang.String, java.lang.Object)
         */
        @Override
        public void entering(String sourceClass, String sourceMethod,
                Object param1) {

            // TODO gene: entering unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#entering(java.lang.String,
         *      java.lang.String, java.lang.Object[])
         */
        @Override
        public void entering(String sourceClass, String sourceMethod,
                Object[] params) {

            // TODO gene: entering unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#exiting(java.lang.String,
         *      java.lang.String)
         */
        @Override
        public void exiting(String sourceClass, String sourceMethod) {

            // TODO gene: exiting unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#exiting(java.lang.String,
         *      java.lang.String, java.lang.Object)
         */
        @Override
        public void exiting(String sourceClass, String sourceMethod,
                Object result) {

            // TODO gene: exiting unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#fine(java.lang.String)
         */
        @Override
        public void fine(String msg) {

            log.debug(msg);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#finer(java.lang.String)
         */
        @Override
        public void finer(String msg) {

            log.debug(msg);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#finest(java.lang.String)
         */
        @Override
        public void finest(String msg) {

            log.debug(msg);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#info(java.lang.String)
         */
        @Override
        public void info(String msg) {

            log.info(msg);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#log(java.util.logging.Level,
         *      java.lang.String)
         */
        @Override
        public void log(Level level, String msg) {

            // TODO gene: log unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#log(java.util.logging.Level,
         *      java.lang.String, java.lang.Object)
         */
        @Override
        public void log(Level level, String msg, Object param1) {

            // TODO gene: log unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#log(java.util.logging.Level,
         *      java.lang.String, java.lang.Object[])
         */
        @Override
        public void log(Level level, String msg, Object[] params) {

            // TODO gene: log unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#log(java.util.logging.Level,
         *      java.lang.String, java.lang.Throwable)
         */
        @Override
        public void log(Level level, String msg, Throwable thrown) {

            // TODO gene: log unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#log(java.util.logging.LogRecord)
         */
        @Override
        public void log(LogRecord record) {

            // TODO gene: log unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logp(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String)
         */
        @Override
        public void logp(Level level, String sourceClass, String sourceMethod,
                String msg) {

            // TODO gene: logp unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logp(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.Object)
         */
        @Override
        public void logp(Level level, String sourceClass, String sourceMethod,
                String msg, Object param1) {

            // TODO gene: logp unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logp(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.Object[])
         */
        @Override
        public void logp(Level level, String sourceClass, String sourceMethod,
                String msg, Object[] params) {

            // TODO gene: logp unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logp(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.Throwable)
         */
        @Override
        public void logp(Level level, String sourceClass, String sourceMethod,
                String msg, Throwable thrown) {

            // TODO gene: logp unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logrb(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String)
         */
        @Override
        public void logrb(Level level, String sourceClass, String sourceMethod,
                String bundleName, String msg) {

            // TODO gene: logrb unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logrb(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.Object)
         */
        @Override
        public void logrb(Level level, String sourceClass, String sourceMethod,
                String bundleName, String msg, Object param1) {

            // TODO gene: logrb unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logrb(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.Object[])
         */
        @Override
        public void logrb(Level level, String sourceClass, String sourceMethod,
                String bundleName, String msg, Object[] params) {

            // TODO gene: logrb unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#logrb(java.util.logging.Level,
         *      java.lang.String, java.lang.String, java.lang.String,
         *      java.lang.String, java.lang.Throwable)
         */
        @Override
        public void logrb(Level level, String sourceClass, String sourceMethod,
                String bundleName, String msg, Throwable thrown) {

            // TODO gene: logrb unimplemented
            throw new RuntimeException("unimplemented");
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#severe(java.lang.String)
         */
        @Override
        public void severe(String msg) {

            log.error(msg);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#throwing(java.lang.String,
         *      java.lang.String, java.lang.Throwable)
         */
        @Override
        public void throwing(String sourceClass, String sourceMethod,
                Throwable error) {

            log.info(sourceClass + "#" + sourceMethod + "()", error);
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.util.logging.Logger#warning(java.lang.String)
         */
        @Override
        public void warning(String msg) {

            log.warn(msg);
        }
    }

    /**
     * The field <tt>workingDirectory</tt> contains the working directory. This
     * is usually the base directory of the project.
     * 
     * @parameter expression="${basedir}"
     */
    private File workingDirectory = new File(".");

    /**
     * The field <tt>latexCommand</tt> contains the command to be used for
     * LaTeX.
     * 
     * @parameter
     */
    private String latexCommand = "latex";

    /**
     * The field <tt>format</tt> contains the target format. Currently the
     * values <tt>pdf</tt> and <tt>dvi</tt> are supported. The default is
     * <tt>pdf</tt>.
     * 
     * @parameter
     */
    private String format = "pdf";

    /**
     * The field <tt>file</tt> contains the name of the master file.
     * 
     * @parameter
     * @required
     */
    private File file = new File("texinputs");

    /**
     * The field <tt>texinputs</tt> contains the list of directories for the
     * TEXINPUTS environment variable.
     * 
     * @parameter
     */
    private String[] texinputs = null;

    /**
     * The field <tt>output</tt> contains the output directory. The default is
     * <tt>target</tt>.
     * 
     * @parameter expression="${project.outputDirectory}"
     */
    private File output = new File("target");

    /**
     * Setter for the texinputs.
     * 
     * @param texinputs the texinputs to set
     */
    protected void addTexinputs(String... texinputs) {

        if (this.texinputs == null || this.texinputs.length == 0) {
            this.texinputs = texinputs;
        } else {
            String[] a = new String[this.texinputs.length + texinputs.length];
            System.arraycopy(this.texinputs, 0, a, 0, this.texinputs.length);
            System.arraycopy(texinputs, 0, a, this.texinputs.length,
                texinputs.length);
            this.texinputs = a;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute() throws MojoExecutionException, MojoFailureException {

        Net net;
        try {
            net = new Net(file);
        } catch (FileNotFoundException e) {
            throw new MojoFailureException(e.getMessage() + ": file not found");
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage() + ": I/O error");
        }

        net.setLogger(new LogAdaptor(getLog()));

        net.context("working.directory", workingDirectory.getAbsolutePath());
        net.context("output.directory", output.getAbsolutePath());
        net.context("latex.command", latexCommand);
        net.context("latex.format", format);

        PrintWriter w = new PrintWriter(System.out);
        net.print(w, "");
        w.flush();

        // LaTeX Warning: There were undefined references.
        // LaTeX Warning: Citation `abc' on page 1 undefined on input line 3.
        // LaTeX Warning: Label(s) may have changed. Rerun to get
        // cross-references right.
        // No file document2.ind.
        // Output written on target/document2.dvi (1 page, 280 bytes).
        /*
         * try { generateFromTeX(); // LaTeX // BibTeX // makeindex // LaTeX //
         * LaTeX } catch (IOException e) { throw new MojoExecutionException("",
         * e); }
         */
    }

    /**
     * Setter for the file.
     * 
     * @param file the file to set
     */
    protected void setFile(File file) {

        this.file = file;
    }

    /**
     * Setter for the format.
     * 
     * @param format the format to set
     */
    protected void setFormat(String format) {

        this.format = format;
    }

    /**
     * Setter for the latexCommand.
     * 
     * @param latexCommand the latexCommand to set
     */
    protected void setLatexCommand(String latexCommand) {

        this.latexCommand = latexCommand;
    }

    /**
     * Setter for the output.
     * 
     * @param output the output to set
     */
    protected void setOutput(File output) {

        this.output = output;
    }

    /**
     * Setter for the workingDirectory.
     * 
     * @param workingDirectory the workingDirectory to set
     */
    protected void setWorkingDirectory(File workingDirectory) {

        this.workingDirectory = workingDirectory;
    }

}
