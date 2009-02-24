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
     * The parameter <tt>limit</tt> contains the maximal number of iterations in
     * the attempt to come to a fixed point. The value should be greater than 2.
     * <p>
     * Note that you can construct files which converge not to a fixed point but
     * oscillate between two states. This parameter forces an artificial break
     * out of such an infinite loop.
     * </p>
     * 
     * @parameter default-value=3
     * @since 1.0
     */
    private int limit = 3;

    /**
     * The parameter <tt>file</tt> contains the name of the L<span
     * class="la">a</span><span class="t">T</span><span class="e">e</span>X
     * master file. This file is analyzed to determine the dependencies.
     * 
     * @parameter
     * @required
     * @since 1.0
     */
    private File file;

    /**
     * The parameter <tt>format</tt> contains the target format. Currently the
     * values <tt>pdf</tt> and <tt>dvi</tt> are supported. The default is
     * <tt>pdf</tt>.
     * 
     * @parameter default-value="pdf"
     * @since 1.0
     */
    private String format = "pdf";

    /**
     * The parameter <tt>latexCommand</tt> contains the command to be used for
     * L<span class="la">a</span><span class="t">T</span><span
     * class="e">e</span>X. This command has to be found on the system path for
     * executables.
     * 
     * @parameter default-value="pdflatex"
     * @since 1.0
     */
    private String latexCommand = "pdflatex";

    /**
     * The parameter <tt>indexerCommand</tt> contains the command to be used for
     * index creation. This command has to be found on the system path for
     * executables.
     * 
     * @parameter
     * @since 1.0
     */
    private String indexerCommand = "makeindex";

    /**
     * The parameter <tt>noaction</tt> contains the indicator that no actions
     * should be performed.
     * 
     * @parameter default-value=false
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
     * The parameter <tt>latexExtensions</tt> contains the list of extensions
     * for L<span class="la">a</span><span class="t">T</span><span
     * class="e">e</span>X files.
     * 
     * @parameter
     * @since 1.0
     */
    private String[] latexExtensions = null;

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
     * Setter for the LatexExtensions.
     * 
     * @param extensions the LatexExtensions to set
     */
    protected void addLatexExtensions(String... extensions) {

        if (this.latexExtensions == null || this.latexExtensions.length == 0) {
            this.latexExtensions = extensions;
        } else {
            String[] a =
                    new String[this.latexExtensions.length + extensions.length];
            System.arraycopy(this.latexExtensions, 0, a, 0,
                this.latexExtensions.length);
            System.arraycopy(extensions, 0, a, this.latexExtensions.length,
                extensions.length);
            this.latexExtensions = a;
        }
    }

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
            p.setMakeindexCommand(indexerCommand);
            p.setTargetFormat(format);
            p.setTexinputs(texinputs);
            p.setLimit(limit);

            net.wire(file);

            logNet(net);

            net.build(noaction);

        } catch (MakeException e) {
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new MojoExecutionException(e.getLocalizedMessage(), e);
        } finally {
            logger.removeHandler(handler);
        }
    }

    /**
     * Getter for the indexerCommand.
     * 
     * @return the indexerCommand
     */
    protected String getIndexerCommand() {

        return indexerCommand;
    }

    /**
     * Getter for the limit.
     * 
     * @return the limit
     */
    protected int getLimit() {

        return limit;
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
     * Setter for the indexerCommand.
     * 
     * @param indexerCommand the indexerCommand to set
     */
    protected void setIndexerCommand(String indexerCommand) {

        this.indexerCommand = indexerCommand;
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
     * Setter for the limit.
     * 
     * @param limit the limit to set
     */
    protected void setLimit(int limit) {

        this.limit = limit;
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
