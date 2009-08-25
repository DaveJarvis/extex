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
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;
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

    /**
     * The field <tt>bibtexCommand</tt> contains the command for BibTeX.
     */
    private String bibtexCommand = "bibtex";

    /**
     * The field <tt>bibtexLimit</tt> contains the maximum number of BibTeX
     * runs.
     */
    private int bibtexLimit = 3;

    private Map<String, Command> commandMap = new HashMap<String, Command>();

    /**
     * The field <tt>latexCommand</tt> contains the command for LaTeX.
     */
    private String latexCommand = "pdflatex";

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
    private boolean simulate = true;

    /**
     * The field <tt>target</tt> contains the ...
     */
    private File target = new File("target");

    /**
     * The field <tt>workingDirectory</tt> contains the working directory.
     */
    private File workingDirectory = new File(".");

    /**
     * Creates a new object.
     * 
     */
    public LatexTask() {

        commandMap.put("latex", new LaTeX(this));
        commandMap.put("bibtex", new BibTeX(this));
        commandMap.put("makeindex", new Makeindex());
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
        String base = master.getName().replaceFirst("\\.[a-zA-Z]+$", "");
        // File aux = new File(target, base + ".aux");

        target.mkdirs();

        LaTeX latex = new LaTeX(this);
        BibTeX bibtex = new BibTeX(this);
        Makeindex makeindex = new Makeindex();

        run(latex, master);
        run(bibtex, master);
        run(makeindex, master);
        run(latex, master);
        run(latex, master);

        // File goal = new File(base + "." + outputFormat);
        //
        // if (!goal.exists()) {
        // throw new RuntimeException("goal unimplemented");
        // }
        //
        // if (!aux.exists() || aux.lastModified() < master.lastModified()) {
        // latex(master);
        // if (!aux.exists()) {
        // // TODO gene: execute unimplemented
        // throw new RuntimeException("unimplemented");
        // }
        // }
        // determineDependencies(base, aux);
    }

    /**
     * Getter for bibtexCommand.
     * 
     * @return the bibtexCommand
     */
    public String getBibtexCommand() {

        return bibtexCommand;
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
     * TODO gene: missing JavaDoc
     * 
     * @param type
     * @param artifact
     */
    private void run(Command type, File artifact) {

        log(type + " " + artifact.getName() + "\n");

        if (simulate) {
            return;
        }

        throw new RuntimeException("unimplemented");
        // TODO gene: run unimplemented

    }

    /**
     * Setter for bibtexCommand.
     * 
     * @param bibtexCommand the bibtexCommand to set
     */
    public void setBibtexCommand(String bibtexCommand) {

        this.bibtexCommand = bibtexCommand;
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
