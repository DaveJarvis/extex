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
import java.io.IOException;
import java.io.InputStream;

import org.apache.tools.ant.BuildException;
import org.extex.ant.latex.LatexTask;

public class LaTeX implements Command {

    /**
     * The field <tt>task</tt> contains the ...
     */
    private LatexTask task;

    /**
     * Creates a new object.
     * 
     * @param task
     */
    public LaTeX(LatexTask task) {

        this.task = task;
    }

    public void execute(File artifact) {

        String base = artifact.getName().replace('\\', '/');

        ProcessBuilder latex = new ProcessBuilder(task.getLatexCommand(), //
            "-output-directory=" + task.getOutput(), //
            base);
        latex.directory(task.getWorkingDirectory());
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
                task.log(buffer.toString());
            }
        } catch (IOException e) {
            throw new BuildException(e);
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    }

    public void simulate(File base) {

        // TODO gene: simulate unimplemented

    }

}
