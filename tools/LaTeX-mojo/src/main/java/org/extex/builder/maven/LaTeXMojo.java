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

package org.extex.builder.maven;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.extex.builder.latex.DependencyNet;
import org.extex.builder.latex.FileFormat;
import org.extex.builder.latex.Parameters;
import org.extex.builder.latex.exception.MakeException;

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
*/
public class LaTeXMojo extends AbstractMojo {

    /**
     * The parameter {@code bibtexCommand} contains the command to be used for
     * <span class="sc">Bib</span><span class="t">T</span><span
     * class="e">e</span>X. This command has to be found on the system path for
     * executables.
     * 
     * @parameter
     * @since 1.0
     */
    private String bibtexCommand = "bibtex";

    /**
     * The parameter {@code bibtexExtensions} contains the list of extensions
     * for <span class="sc">Bib</span><span class="t">T</span><span
     * class="e">e</span>X files.
     * 
     * @parameter
     * @since 1.0
     */
    private String[] bibtexExtensions = null;

    /**
     * The field {@code debug} contains the indicator for the debugging.
     */
    private boolean debug = false;

    /**
     * The parameter {@code file} contains the name of the L<span
     * class="la">a</span><span class="t">T</span><span class="e">e</span>X
     * master file. This file is analyzed to determine the dependencies.
     * 
     * @parameter
     * @required
     * @since 1.0
     */
    private File file = null;

    /**
     * The parameter {@code format} contains the target format. Currently the
     * values {@code pdf} and {@code dvi} are supported. The default is
     * {@code pdf}.
     * 
     * @parameter default-value="pdf"
     * @since 1.0
     */
    private String format = "pdf";

    /**
     * The parameter {@code indexerCommand} contains the command to be used for
     * index creation. This command has to be found on the system path for
     * executables.
     * 
     * @parameter
     * @since 1.0
     */
    private String indexerCommand = "makeindex";

    /**
     * The parameter {@code latexCommand} contains the command to be used for
     * L<span class="la">a</span><span class="t">T</span><span
     * class="e">e</span>X. This command has to be found on the system path for
     * executables.
     * 
     * @parameter default-value="pdflatex"
     * @since 1.0
     */
    private String latexCommand = "pdflatex";

    /**
     * The parameter {@code latexExtensions} contains the list of extensions
     * for L<span class="la">a</span><span class="t">T</span><span
     * class="e">e</span>X files.
     * 
     * @parameter
     * @since 1.0
     */
    private String[] latexExtensions = null;

    /**
     * The parameter {@code limit} contains the maximal number of iterations in
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
     * The parameter {@code noaction} contains the indicator that no actions
     * should be performed.
     * 
     * @parameter default-value=false
     * @since 1.0
     */
    private boolean noaction = false;

    /**
     * The parameter {@code output} contains the output directory. The default
     * is {@code target/doc}.
     * 
     * @parameter expression="${project.outputDirectory}"
     * @since 1.0
     */
    private File output = new File(DIR_TARGET + "/doc");

    /**
     * The parameter {@code texinputs} contains the list of directories for the
     * {@code TEXINPUTS} environment variable.
     * 
     * @parameter
     * @since 1.0
     */
    private String[] texinputs = null;

    /**
     * The parameter {@code workingDirectory} contains the working directory.
     * This is usually the base directory of the project.
     * 
     * @parameter expression="${basedir}"
     * @since 1.0
     */
    private File workingDirectory = new File(".");

    /**
     * Creates a new object.
     * 
     */
    public LaTeXMojo() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param noaction the initial value for the noaction flag
     */
    public LaTeXMojo(boolean noaction) {

        this.noaction = noaction;
    }

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
            p.setLatexExtensions(latexExtensions);
            p.setBibtexCommand(bibtexCommand);
            p.setBibtexExtensions(bibtexExtensions);
            p.setMakeindexCommand(indexerCommand);
            p.setTexinputs(texinputs);
            p.setLimit(limit);
            try {
                p.setTargetFormat(FileFormat.valueOf(format
                    .toUpperCase(Locale.ENGLISH)));
            } catch (IllegalArgumentException e) {
                throw new MakeException(logger, "net.illegal.format", format);
            }

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
     * Setter for the file.
     * 
     * @param file the file to set
     * 
     * @deprecated use the configuration instead
     */
    @Deprecated
    protected void setFile(File file) {

        this.file = file;
    }

}
