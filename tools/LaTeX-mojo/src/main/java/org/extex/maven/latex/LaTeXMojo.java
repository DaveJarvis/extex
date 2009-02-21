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
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.extex.maven.latex.builder.DependencyNet;
import org.extex.maven.latex.builder.Parameters;
import org.extex.maven.latex.builder.exception.MakeException;

/**
 * This plug-in is a L<span class="la">a</span><span class="t">T</span><span
 * class="e">e</span>X adapter for typesetting documentation. It uses an
 * installed T<span class="e">e</span>X system to compile the source files. The
 * mojo tries to analyze the input file to find the required programs and a
 * minimal sequence of commands to produce the desired output format.
 * 
 * @goal latex
 * @execute phase=compile
 * 
 * @since 1.0
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LaTeXMojo extends AbstractMojo {

    /**
     * The parameter <tt>bibtexCommand</tt> contains the command to be used for
     * <span class="sc">Bib</span><span class="t">T</span><span
     * class="e">e</span>X. This command has to be found on the system path for
     * executables.
     * 
     * @parameter
     * @since 1.0
     */
    private String bibtexCommand = "bibtex";

    /**
     * The parameter <tt>file</tt> contains the name of the L<span
     * class="la">a</span><span class="t">T</span><span class="e">e</span>X
     * master file. This file is analyzed to determine the dependencies.
     * 
     * @parameter
     * @required
     * @since 1.0
     */
    private File file = new File("texinputs");

    /**
     * The parameter <tt>format</tt> contains the target format. Currently the
     * values <tt>pdf</tt> and <tt>dvi</tt> are supported. The default is
     * <tt>pdf</tt>.
     * 
     * @parameter
     * @since 1.0
     */
    private String format = "pdf";

    /**
     * The parameter <tt>latexCommand</tt> contains the command to be used for
     * L<span class="la">a</span><span class="t">T</span><span
     * class="e">e</span>X. This command has to be found on the system path for
     * executables.
     * 
     * @parameter
     * @since 1.0
     */
    private String latexCommand = "latex";

    /**
     * The parameter <tt>noaction</tt> contains the indicator that no actions
     * should be performed.
     * 
     * @parameter
     * @since 1.0
     */
    private boolean noaction = false;

    /**
     * The parameter <tt>output</tt> contains the output directory. The default
     * is <tt>target/doc</tt>.
     * 
     * @parameter expression="${project.outputDirectory}"
     * @since 1.0
     */
    private File output = new File("target/doc");

    /**
     * The parameter <tt>texinputs</tt> contains the list of directories for the
     * <tt>TEXINPUTS</tt> environment variable.
     * 
     * @parameter
     * @since 1.0
     */
    private String[] texinputs = null;

    /**
     * The parameter <tt>workingDirectory</tt> contains the working directory.
     * This is usually the base directory of the project.
     * 
     * @parameter expression="${basedir}"
     * @since 1.0
     */
    private File workingDirectory = new File(".");

    /**
     * The field <tt>debug</tt> contains the indicator for the debugging.
     */
    private boolean debug = false;

    /**
     * Setter for the texinputs.
     * 
     * @param inputs the texinputs to set
     */
    protected void addTexinputs(String... inputs) {

        if (this.texinputs == null || this.texinputs.length == 0) {
            this.texinputs = inputs;
        } else {
            String[] a = new String[this.texinputs.length + inputs.length];
            System.arraycopy(this.texinputs, 0, a, 0, this.texinputs.length);
            System
                .arraycopy(inputs, 0, a, this.texinputs.length, inputs.length);
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
        logger.setLevel(debug ? Level.ALL : Level.INFO);
        Handler handler = new LogAdaptorHandler(this.getLog());
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        try {
            DependencyNet net = new DependencyNet();
            net.setLogger(logger);
            Parameters p = net.getParameters();

            p.setWorkingDirectory(workingDirectory);
            p.setOutputDirectory(output);
            p.setLatexCommand(latexCommand);
            p.setBibtexCommand(bibtexCommand);
            p.setTargetFormat(format);
            p.setTexinputs(texinputs);

            net.wire(file);

            logNet(net);

            net.build(noaction);

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
     * Print the net to the log.
     * 
     * @param net the dependency net
     */
    private void logNet(DependencyNet net) {

        if (debug) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PrintWriter w = new PrintWriter(os);
            w.print('\n');
            net.print(w, "\t");
            w.flush();
            getLog().debug(os.toString());
        }
    }

    /**
     * Setter for the bibtexCommand.
     * 
     * @param bibtexCommand the bibtexCommand to set
     */
    protected void setBibtexCommand(String bibtexCommand) {

        this.bibtexCommand = bibtexCommand;
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
     * Setter for the noaction.
     * 
     * @param noaction the noaction to set
     */
    protected void setNoaction(boolean noaction) {

        this.noaction = noaction;
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
