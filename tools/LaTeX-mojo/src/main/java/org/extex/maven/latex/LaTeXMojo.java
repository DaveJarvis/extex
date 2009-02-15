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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.extex.maven.latex.builder.DependencyNet;
import org.extex.maven.latex.builder.exception.MakeException;

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
     * The field <tt>file</tt> contains the name of the master file.
     * 
     * @parameter
     * @required
     */
    private File file = new File("texinputs");

    /**
     * The field <tt>format</tt> contains the target format. Currently the
     * values <tt>pdf</tt> and <tt>dvi</tt> are supported. The default is
     * <tt>pdf</tt>.
     * 
     * @parameter
     */
    private String format = "pdf";

    /**
     * The field <tt>handler</tt> contains the ...
     */
    private Handler handler = new Handler() {

        @Override
        public void close() throws SecurityException {

            // nothing to do
        }

        @Override
        public void flush() {

            // nothing to do
        }

        @Override
        public void publish(LogRecord record) {

            Level level = record.getLevel();
            int levelValue = level.intValue();

            if (levelValue >= Level.SEVERE.intValue()) {
                getLog().error(record.getMessage());
            } else if (levelValue >= Level.WARNING.intValue()) {
                getLog().warn(record.getMessage());
            } else if (levelValue >= Level.INFO.intValue()) {
                getLog().info(record.getMessage());
            } else {
                getLog().debug(record.getMessage());
            }
        }

    };

    /**
     * The field <tt>latexCommand</tt> contains the command to be used for
     * LaTeX.
     * 
     * @parameter
     */
    private String latexCommand = "latex";

    /**
     * The field <tt>output</tt> contains the output directory. The default is
     * <tt>target</tt>.
     * 
     * @parameter expression="${project.outputDirectory}"
     */
    private File output = new File("target");

    /**
     * The field <tt>texinputs</tt> contains the list of directories for the
     * TEXINPUTS environment variable.
     * 
     * @parameter
     */
    private String[] texinputs = null;

    /**
     * The field <tt>workingDirectory</tt> contains the working directory. This
     * is usually the base directory of the project.
     * 
     * @parameter expression="${basedir}"
     */
    private File workingDirectory = new File(".");

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

        Logger logger = Logger.getLogger(LaTeXMojo.class.getName(), null);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);
        handler.setLevel(Level.ALL);

        try {
            DependencyNet net = new DependencyNet();
            net.setLogger(logger);

            net.context("working.directory", //
                workingDirectory.getAbsolutePath());
            net.context("output.directory", output.getAbsolutePath());
            net.context("latex.command", latexCommand);
            net.context("target.format", format);

            net.wire(file);

            logNet(net);

            net.build(true);

        } catch (MakeException e) {
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        } catch (FileNotFoundException e) {
            throw new MojoFailureException(e.getMessage() + ": file not found");
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage() + ": I/O error");
        } finally {
            logger.removeHandler(handler);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param net
     */
    private void logNet(DependencyNet net) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintWriter w = new PrintWriter(os);
        w.print('\n');
        net.print(w, "\t");
        w.flush();
        getLog().debug(os.toString());
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
