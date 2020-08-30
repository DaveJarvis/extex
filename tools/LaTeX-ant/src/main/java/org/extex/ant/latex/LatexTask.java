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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.extex.ant.latex.command.Command;
import org.extex.ant.latex.command.CommandFactory;

/**
 * This class provides an Ant task for processing a L<span style="text-transform:uppercase;font-size:75%;vertical-align: 0.45ex;margin-left: -0.36em;margin-right: -0.15em;"
 * >a</span>T<span style="text-transform:uppercase;font-size:90%;vertical-align: -0.4ex;margin-left: -0.2em;margin-right: -0.1em;line-height: 0;"
 * >e</span>X document.
 * 
 * <p>Usage</p>
 * 
 * <p>
 * To use this class in an Ant file you need to define it as a task. In the
 * following example the name <em>LaTeX</em> is used as tag name. This tag name
 * can been seen in the following examples.
 * </p>
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
 * <p>Configuration Properties</p>
 * 
 * <pre>
 *   &lt;property name="..." value="..." /&gt;
 * </pre>
 * 
 * <dl>
 * <dt>bibtex.command</dt>
 * <dd>...</dd>
 * <dt>latex.command</dt>
 * <dd>...</dd>
 * <dt>latex.output.format</dt>
 * <dd>...</dd>
 * <dt>latex.output.directory</dt>
 * <dd>...</dd>
 * <dt>latex.use.recorder</dt>
 * <dd>...</dd>
 * <dt>latex.working.directory</dt>
 * <dd>...</dd>
 * <dt>makeindex.command</dt>
 * <dd>...</dd>
 * </dl>
 * 
 * TODO gene: missing JavaDoc.
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LatexTask extends Task implements Settings {

    private static final String DIR_TARGET = "build";
    
    /**
     * TODO gene: missing JavaDoc.
     * 
     */
    public class Dep {

        /**
         * The field {@code name} contains the ...
         */
        private String name;

        /**
         * Setter for name.
         * 
         * @param name the name to set
         */
        public void setName(String name) {

            this.name = name;
        }

    }

    /**
     * TODO gene: missing JavaDoc.
     * 
     */
    public class Prop {

        /**
         * The field {@code name} contains the name.
         */
        private String name;

        /**
         * The field {@code value} contains the value.
         */
        private String value;

        /**
         * Creates a new object.
         * 
         * @param name
         * @param value
         */
        public Prop(String name, String value) {

            this.name = name;
            this.value = value;
        }

        /**
         * Setter for name.
         * 
         * @param name the name to set
         */
        public void setName(String name) {

            this.name = name;
        }

        /**
         * Setter for value.
         * 
         * @param value the value to set
         */
        public void setValue(String value) {

            this.value = value;
        }

    }

    /**
     * Compute the MD5 checksum for a file.
     * 
     * @param file the file
     * 
     * @return the MD5 checksum
     * 
     * @throws IOException in case of an error
     */
    public static String md5(File file) throws IOException {

        byte[] buffer;
        FileInputStream in = new FileInputStream(file);
        try {
            int bytes = in.available();
            buffer = new byte[bytes];
            in.read(buffer);
        } finally {
            in.close();
        }
        StringBuilder md5Hash = new StringBuilder(32);
        try {
            for (byte b : MessageDigest.getInstance("MD5").digest(buffer)) {
                md5Hash.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return md5Hash.toString();
    }

    /**
     * The field {@code master} contains the name of the master file.
     */
    private File master = null;

    /**
     * The field {@code simulate} contains the simulation indicator.
     */
    private boolean simulate = false;

    /**
     * The field {@code target} contains the target directory.
     */
    private File target = new File(DIR_TARGET);

    /**
     * The field {@code workingDirectory} contains the working directory.
     */
    private File workingDirectory = new File(".");

    /**
     * The field {@code dependencies} contains the dependencies.
     */
    private List<Dep> dependencies = new ArrayList<Dep>();

    /**
     * The field {@code fallback} contains the fall-back values for parameters.
     */
    private Map<String, String> fallback = new HashMap<String, String>();

    /**
     * The field {@code properties} contains the local properties.
     */
    private Map<String, String> properties = new HashMap<String, String>();

    /**
     * The field {@code propList} contains the intermediary list pf properties.
     */
    private List<Prop> propList = new ArrayList<Prop>();

    /**
     * Creates a new object.
     * 
     */
    public LatexTask() {

        fallback.put(Settings.BIBTEX_COMMAND, "bibtex");
        fallback.put(Settings.LATEX_COMMAND, "pdflatex");
        fallback.put(Settings.MAKEINDEX_COMMAND, "makeindex");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     * 
     * @throws IOException in case of an I/O error
     */
    private boolean[] analyzeLog() throws BuildException {

        File logFile =
                new File(target, master.getName().replaceAll("\\.[a-zA-Z]*$",
                    "")
                        + ".log");
        LineNumberReader r;
        try {
            r = new LineNumberReader(new FileReader(logFile));
        } catch (FileNotFoundException e) {
            throw new BuildException(e.getLocalizedMessage(), e);
        }

        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                // System.err.println(line);
                if (line.startsWith("INPUT ")) {
                    line = line.substring(6);
                }
            }
            // TODO gene: analyzeFls unimplemented
        } catch (IOException e) {
            throw new BuildException(e.getLocalizedMessage(), e);
        } finally {
            try {
                r.close();
            } catch (IOException e) {
                throw new BuildException(e.getLocalizedMessage(), e);
            }
        }

        // TODO gene: analyzeLog unimplemented
        return new boolean[]{true, true};
    }

    /**
     * Determine whether something needs to be done. This is relevant for the
     * first run primarily.
     * 
     * @return {@code true} if something is not up to date
     */
    private boolean checkDependencies() {

        String base = master.getName().replaceFirst("\\.[a-zA-Z]+$", "");
        File goal = new File(base + "." + get(Settings.OUTPUT_FORMAT, "pdf"));
        if (!goal.exists()) {
            log(goal.toString() + " needed\n", Project.MSG_VERBOSE);
            return false;
        }
        long goalLastModified = goal.lastModified();
        if (goalLastModified > master.lastModified()) {
            log(goal.toString() + " not up to date\n", Project.MSG_VERBOSE);
            return false;
        }
        for (Dep d : dependencies) {
            File f = new File(d.name);
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
     * Factory method for dependencies.
     * 
     * @return the dependency
     */
    public Dep createFile() {

        Dep dep = new Dep();
        dependencies.add(dep);
        return dep;
    }

    /**
     * factory method for local properties.
     * 
     * @return a new Prop instance
     */
    public Prop createProperty() {

        Prop prop = new Prop(null, null);
        propList.add(prop);
        return prop;
    }

@Override
    public void execute() throws BuildException {

        if (master == null) {
            throw new BuildException("master file argument missing");
        }
        if (!master.exists()) {
            throw new BuildException("master file " + master.toString()
                    + " not found");
        }

        for (Prop p : propList) {
            if (p.name == null) {
                throw new BuildException("property without name encountered");
            }
            if (p.value == null) {
                throw new BuildException("property `" + p.name
                        + "' has no value");
            }
            properties.put(p.name, p.value);
        }
        propList.clear();

        target.mkdirs();

        Command latex = CommandFactory.create(CommandFactory.Type.LATEX, this);
        Command bibtex =
                CommandFactory.create(CommandFactory.Type.BIBTEX, this);
        Command makeindex =
                CommandFactory.create(CommandFactory.Type.MAKEINDEX, this);

        if (checkDependencies()) {
            log("up to date");
            return;
        }
        for (;;) {
            if (run(latex, master)) {
                return;
            }
            boolean[] xx = analyzeLog();

            if (xx[0] && run(bibtex, master)) {
                return;
            }
            if (xx[1] && run(makeindex, master)) {
                return;
            }
            // if (run(latex, master)) {
            // return;
            // }
            // if (run(latex, master)) {
            // return;
            // }
        }
    }

public String get(String key, String def) {

        String value = properties.get(key);
        if (value != null) {
            return value;
        }
        value = getProject().getProperty(key);
        if (value != null) {
            return value;
        }
        value = fallback.get(key);
        return value != null ? value : def;
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
     * Execute a command or simulate the execution.
     * 
     * @param command the command to run
     * @param artifact the artifact
     * 
     * @throws MakeException in case of an error
     */
    private boolean run(Command command, File artifact) throws BuildException {

        try {
            if (simulate) {
                return command.simulate(artifact);
            } else {
                return command.execute(artifact);
            }
        } catch (MakeException e) {
            throw new BuildException(e.getMessage(), e);
        }
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

        propList.add(new Prop(Settings.OUTPUT_DIRECTORY, output.toString()));
    }

    /**
     * Setter for outputFormat.
     * 
     * @param outputFormat the outputFormat to set
     */
    public void setOutputFormat(String outputFormat) {

        propList.add(new Prop(Settings.OUTPUT_FORMAT, outputFormat));
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
