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

package org.extex.ant.latex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexTask extends Task {

    /**
     * The field <tt>bibtexLimit</tt> contains the maximum number of BibTeX
     * runs.
     */
    private int bibtexLimit = 3;

    /**
     * The field <tt>latexLimit</tt> contains the maximum number of LaTeX runs.
     */
    private int latexLimit = 8;

    /**
     * The field <tt>simulate</tt> contains the simulation indicator.
     */
    private boolean simulate = true;

    /**
     * The field <tt>output</tt> contains the output directory.
     */
    private File output = new File("target");

    /**
     * The field <tt>latexCommand</tt> contains the command for LaTeX.
     */
    private String latexCommand = "pdflatex";

    /**
     * The field <tt>workingDirectory</tt> contains the working directory.
     */
    private File workingDirectory = new File(".");

    /**
     * The field <tt>master</tt> contains the name of the master file.
     */
    private File master = null;

    /**
     * The field <tt>target</tt> contains the ...
     */
    private File target = new File("target");

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        if (master == null) {
            throw new BuildException("master file parameter missing");
        }
        File aux = new File(target, //
            master.getName().replaceFirst("\\.[a-zA-Z]+$", ".aux"));

        target.mkdirs();

        if (!master.exists()) {
            // TODO gene: execute unimplemented
            throw new RuntimeException("unimplemented");
        }
        if (!aux.exists() || aux.lastModified() < master.lastModified()) {
            latex(master);
            if (!aux.exists()) {
                // TODO gene: execute unimplemented
                throw new RuntimeException("unimplemented");
            }
        }
        // determineDependencies(base, aux);
    }

    /**
     * Getter for bibtexLimit.
     * 
     * @return the bibtexLimit
     */
    public int getBibtexLimit() {

        return bibtexLimit;
    }

    /**
     * Getter for latexCommand.
     * 
     * @return the latexCommand
     */
    public String getLatexCommand() {

        return latexCommand;
    }

    /**
     * Getter for latexLimit.
     * 
     * @return the latexLimit
     */
    public int getLatexLimit() {

        return latexLimit;
    }

    /**
     * Getter for output.
     * 
     * @return the output
     */
    public File getOutput() {

        return output;
    }

    /**
     * Getter for workingDirectory.
     * 
     * @return the workingDirectory
     */
    public File getWorkingDirectory() {

        return workingDirectory;
    }

    /**
     * Getter for simulate.
     * 
     * @return the simulate
     */
    public boolean isSimulate() {

        return simulate;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param artifact
     * 
     * @throws BuildException in case of an error
     */
    private void latex(File artifact) throws BuildException {

        log(latexCommand + " " + artifact.getName());

        if (simulate) {
            return;
        }

        String base = artifact.getName().replace('\\', '/');

        ProcessBuilder latex = new ProcessBuilder(latexCommand, //
            "-output-directory=" + output, //
            base);
        latex.directory(workingDirectory);
        latex.redirectErrorStream(true);
        Process p = null;
        try {
            p = latex.start();
            p.getOutputStream().close();
            StringBuilder buffer = new StringBuilder();
            InputStream in = p.getInputStream();
            for (int c = in.read(); c >= 0; c = in.read()) {
                buffer.append((char) c);
            }
            if (p.exitValue() != 0) {
                log(buffer.toString());
            }
        } catch (IOException e) {
            throw new BuildException(e);
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    }

    /**
     * Setter for bibtexLimit.
     * 
     * @param bibtexLimit the bibtexLimit to set
     */
    public void setBibtexLimit(int bibtexLimit) {

        this.bibtexLimit = bibtexLimit;
    }

    /**
     * Setter for latexCommand.
     * 
     * @param latexCommand the latexCommand to set
     */
    public void setLatexCommand(String latexCommand) {

        this.latexCommand = latexCommand;
    }

    /**
     * Setter for latexLimit.
     * 
     * @param latexLimit the latexLimit to set
     */
    public void setLatexLimit(int latexLimit) {

        this.latexLimit = latexLimit;
    }

    /**
     * Setter for output.
     * 
     * @param output the output to set
     */
    public void setOutput(File output) {

        this.output = output;
    }

    /**
     * Setter for simulate.
     * 
     * @param simulate the simulate to set
     */
    public void setSimulate(boolean simulate) {

        this.simulate = simulate;
    }

    /**
     * Setter for workingDirectory.
     * 
     * @param workingDirectory the workingDirectory to set
     */
    public void setWorkingDirectory(File workingDirectory) {

        this.workingDirectory = workingDirectory;
    }

}
