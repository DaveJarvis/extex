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
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.extex.ant.latex.command.BibTeX;
import org.extex.ant.latex.command.Command;
import org.extex.ant.latex.command.LaTeX;
import org.extex.ant.latex.command.Makeindex;

/**
 * TODO gene: missing JavaDoc.
 * 
 * <pre>
 *   &lt;taskdef name="LaTeX"
 *            classname="org.extex.ant.latex.LatexTask" /&gt;
 * </pre>
 * 
 * <pre>
 *   &lt;LaTeX master="master_file" /&gt;
 * </pre>
 * 
 * <pre>
 *   &lt;LaTeX master="master_file"&gt;
 *     &lt;files&gt;
 *       &lt;file&gt;abc&lt;/file&gt;
 *       &lt;file&gt;def&lt;/file&gt;
 *     &lt;/files&gt;
 *   &lt;/LaTeX&gt;
 * </pre>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexTask extends Task {

    public class Dep {

    }

    /**
     * The field <tt>DEFAULT_BIBTEX_COMMAND</tt> contains the command for
     * BibTeX.
     */
    private static final String DEFAULT_BIBTEX_COMMAND = "bibtex";

    /**
     * The field <tt>bibtexLimit</tt> contains the maximum number of BibTeX
     * runs.
     */
    private int bibtexLimit = 3;

    /**
     * The field <tt>DEFAULT_LATEX_COMMAND</tt> contains the command for LaTeX.
     */
    private static final String DEFAULT_LATEX_COMMAND = "pdflatex";

    /**
     * The field <tt>latexLimit</tt> contains the maximum number of LaTeX runs.
     */
    private int latexLimit = 8;

    /**
     * The field <tt>master</tt> contains the name of the master file.
     */
    private File master = null;

    /**
     * The field <tt>output</tt> contains the output directory.
     */
    private File output = new File("target");

    /**
     * The field <tt>outputFormat</tt> contains the output format.
     */
    private String outputFormat = "pdf";

    /**
     * The field <tt>simulate</tt> contains the simulation indicator.
     */
    private boolean simulate = false;

    /**
     * The field <tt>target</tt> contains the target directory.
     */
    private File target = new File("target");

    /**
     * The field <tt>workingDirectory</tt> contains the working directory.
     */
    private File workingDirectory = new File(".");

    /**
     * The field <tt>files</tt> contains the files.
     */
    private List<File> files = new ArrayList<File>();

    /**
     * The field <tt>dependencies</tt> contains the dependencies.
     */
    private List<Dep> dependencies = new ArrayList<Dep>();

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    private boolean checkDependencies() {

        String base = master.getName().replaceFirst("\\.[a-zA-Z]+$", "");
        File goal = new File(base + "." + outputFormat);
        if (!goal.exists()) {
            log(goal.toString() + " needed\n", Project.MSG_VERBOSE);
            return false;
        }
        long goalLastModified = goal.lastModified();
        if (goalLastModified > master.lastModified()) {
            log(goal.toString() + " not up to date\n", Project.MSG_VERBOSE);
            return false;
        }
        for (File f : files) {
            if (!f.exists()) {
                throw new BuildException(f.toString() + " does not exist\n");
            }
            if (goalLastModified > f.lastModified()) {
                log(goal.toString() + " not up to date\n", Project.MSG_VERBOSE);
                return false;
            }
        }

        return true;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return the dependency
     */
    public Dep createDependency() {

        Dep dep = new Dep();
        dependencies.add(dep);
        return dep;
    }

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
        if (!master.exists()) {
            throw new BuildException("master file " + master.toString()
                    + " not found");
        }

        target.mkdirs();

        LaTeX latex = new LaTeX(this);
        BibTeX bibtex = new BibTeX(this);
        Makeindex makeindex = new Makeindex(this);

        if (checkDependencies()) {
            log("up to date");
            return;
        }
        if (run(latex, master)) {
            return;
        }
        if (run(bibtex, master)) {
            return;
        }
        if (run(makeindex, master)) {
            return;
        }
        if (run(latex, master)) {
            return;
        }
        if (run(latex, master)) {
            return;
        }
    }

    /**
     * Getter for bibtexCommand.
     * 
     * @return the bibtexCommand
     */
    public String getBibtexCommand() {

        String value = getProject().getProperty("bibtex.command");
        return value != null ? value : DEFAULT_BIBTEX_COMMAND;
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

        String value = getProject().getProperty("latex.command");
        return value != null ? value : DEFAULT_LATEX_COMMAND;
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
     * Getter for master.
     * 
     * @return the master
     */
    public File getMaster() {

        return master;
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
     * Getter for outputFormat.
     * 
     * @return the outputFormat
     */
    public String getOutputFormat() {

        return outputFormat;
    }

    /**
     * Getter for target.
     * 
     * @return the target
     */
    public File getTarget() {

        return target;
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
     * Execute a command or simulate the execution.
     * 
     * @param command the command to run
     * @param artifact the artifact
     */
    private boolean run(Command command, File artifact) {

        if (simulate) {
            command.simulate(artifact);
        } else {
            command.execute(artifact);
        }
        return false;
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
     * Setter for latexLimit.
     * 
     * @param latexLimit the latexLimit to set
     */
    public void setLatexLimit(int latexLimit) {

        this.latexLimit = latexLimit;
    }

    /**
     * Setter for master.
     * 
     * @param master the master to set
     */
    public void setMaster(File master) {

        this.master = master;
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
     * Setter for outputFormat.
     * 
     * @param outputFormat the outputFormat to set
     */
    public void setOutputFormat(String outputFormat) {

        this.outputFormat = outputFormat;
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
     * Setter for target.
     * 
     * @param target the target to set
     */
    public void setTarget(File target) {

        this.target = target;
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
