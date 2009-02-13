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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.extex.maven.latex.make.BuildAction;
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
     * TODO gene: missing JavaDoc
     * 
     * @param aux
     * @return
     * @throws IOException
     */
    private List<BuildAction> analyzeAux(File aux) throws IOException {

        StringBuilder buffer = new StringBuilder();
        LineNumberReader r = new LineNumberReader(new FileReader(aux));

        try {
            for (int c = r.read(); c >= 0; c = r.read()) {
                buffer.append((char) c);
            }
        } finally {
            r.close();
        }

        // TODO gene: analyzeAux unimplemented
        return null;
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
     * TODO gene: missing JavaDoc
     * 
     * @throws MojoExecutionException in case of an error
     * @throws IOException in case of an I/O error
     */
    private void generateFromTeX() throws IOException, MojoExecutionException {

        if (!file.exists()) {
            throw new MojoExecutionException(file.toString()
                    + ": file not found");
        }
        File aux =
                new File(output, file.getName().replaceAll("\\.[a-zA-Z0-9_]*$",
                    ".aux"));
        if (!aux.exists()) {
            latex();
            if (!aux.exists()) {
                throw new MojoExecutionException(latexCommand
                        + "  does not create the aux file");
            }
        } else if (file.lastModified() >= aux.lastModified()) {
            latex();
        }

        for (;;) {
            List<BuildAction> actions = analyzeAux(aux);

            if (actions == null) {
                return;
            }

            for (BuildAction a : actions) {
                // a.execute(artifact, context, logger, simulate);
            }
        }

        // TODO gene: generateFromTeX unimplemented
    }

    /**
     * Run the LaTeX command on some input file.
     * 
     * @throws IOException in case of an I/O error
     */
    private void latex() throws IOException {

        String base = file.toString().replace('\\', '/');
        getLog().info(latexCommand + " " + base);

        ProcessBuilder latex = new ProcessBuilder(latexCommand, //
            "-output-directory=" + output, //
            base);
        latex.directory(workingDirectory);
        latex.redirectErrorStream(true);
        Process p = latex.start();
        try {
            p.getOutputStream().close();
            StringBuilder buffer = new StringBuilder();
            InputStream in = p.getInputStream();
            for (int c = in.read(); c >= 0; c = in.read()) {
                buffer.append((char) c);
            }
            if (p.exitValue() != 0) {
                getLog().error(buffer);
            }
        } finally {
            p.destroy();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args the command and its arguments
     * 
     * @return <code>true</code> iff the command succeeded and returned an exit
     *         code of 0
     */
    private boolean probe(String... args) {

        StringBuilder buffer = new StringBuilder();
        for (String s : args) {
            buffer.append(s);
            buffer.append(' ');
        }

        getLog().info(buffer);

        ProcessBuilder latex = new ProcessBuilder(args);
        latex.redirectErrorStream(true);
        try {
            Process p = latex.start();
            try {
                InputStream s = p.getInputStream();
                for (int c = s.read(); c >= 0; c = s.read()) {
                    // System.err.print((char) c);
                }
                p.waitFor();
                return p.exitValue() == 0;
            } catch (InterruptedException e) {
                return false;
            } finally {
                p.destroy();
            }
        } catch (IOException e) {
            return false;
        }
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
