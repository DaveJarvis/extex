/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.ant.latex.command;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.extex.ant.latex.MakeException;
import org.extex.ant.latex.Settings;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class LaTeX implements Command {

    private static final String DIR_TARGET = "build";

    /**
     * The field <tt>settings</tt> contains the task.
     */
    private Settings settings;

    /**
     * Creates a new object.
     * 
     * @param settings the task for reference to logging and parameters
     */
    public LaTeX(Settings settings) {

        this.settings = settings;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param fls
     * 
     * @throws IOException in case of an I/O error
     */
    private void analyzeFls(File fls) throws IOException {

        if (!fls.exists()) {
            settings.log(fls.toString() + " not found\n");
            return;
        }
        settings.log(fls.toString() + " found\n");

        LineNumberReader r = new LineNumberReader(new FileReader(fls));

        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                // System.err.println(line);
                if (line.startsWith("INPUT ")) {
                    line = line.substring(6);
                }
            }
            // TODO gene: analyzeFls unimplemented
        } finally {
            r.close();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ant.latex.command.Command#execute(java.io.File)
     */
    public boolean execute(File artifact) throws MakeException {

        settings.log(toString() + " " + artifact.getName() + "\n");

        String base = artifact.getAbsolutePath();

        String outputFormat = settings.get(Settings.OUTPUT_FORMAT, "pdf");
        if (!"pdf".equals(outputFormat) && !"dvi".equals(outputFormat)) {
            throw new MakeException("unknown output format: " + outputFormat);
        }
        String latexCommand = settings.get(Settings.LATEX_COMMAND, "latex");
        List<String> commandLine = new ArrayList<String>();
        commandLine.add(latexCommand);
        commandLine.add("-output-format=" + outputFormat);
        commandLine.add("-output-directory="
                + settings.get(Settings.OUTPUT_DIRECTORY, DIR_TARGET));
        String rec = settings.get("latex.use.recorder", "true");
        if (rec != null && Boolean.parseBoolean(rec)) {
            commandLine.add("-recorder");
        } else {
            rec = null;
        }
        commandLine.add(base.replaceAll("[\\\\]", "\\\\"));
        ProcessBuilder builder = new ProcessBuilder(commandLine);
        File workingDirectory = settings.getWorkingDirectory();
        builder.directory(workingDirectory);
        builder.redirectErrorStream(true);
        Process p;
        try {
            p = builder.start();
        } catch (IOException e) {
            throw new BuildException(e.toString(), e);
        }
        try {
            p.getOutputStream().close();
            StringBuilder buffer = new StringBuilder();
            InputStream in = p.getInputStream();
            for (int c = in.read(); c >= 0; c = in.read()) {
                buffer.append((char) c);
            }
            if (p.exitValue() != 0) {
                String msg = buffer.toString();
                if (msg.contains("! Emergency stop.")) {
                    throw new BuildException(msg);
                } else {
                    settings.log(msg);
                }
            }
        } catch (IOException e) {
            throw new BuildException(e.toString(), e);
        } finally {
            p.destroy();
        }
        if (rec != null) {
            File fls =
                    new File(workingDirectory, latexCommand.replaceAll(
                        "^.*[/\\\\]", "").replaceAll("\\.[a-zA-Z]*$", "")
                            + ".fls");
            try {
                analyzeFls(fls);
            } catch (IOException e) {
                throw new BuildException(e.toString(), e);
            } finally {
                settings.log("removing " + fls.toString() + "\n");
                fls.delete();
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.ant.latex.command.Command#simulate(java.io.File)
     */
    public boolean simulate(File artifact) {

        settings.log(toString() + " " + artifact.getName() + "\n");
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return settings.get(Settings.LATEX_COMMAND, "latex");
    }

}
