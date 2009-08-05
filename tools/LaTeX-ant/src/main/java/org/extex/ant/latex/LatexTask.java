/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

    private int bibtexLimit = 3;

    private int latexLimit = 8;

    /**
     * The field <tt>simulate</tt> contains the ...
     */
    private boolean simulate = true;

    /**
     * The field <tt>output</tt> contains the ...
     */
    private File output = new File("target");

    /**
     * The field <tt>latexCommand</tt> contains the ...
     */
    private String latexCommand = "pdflatex";

    /**
     * The field <tt>workingDirectory</tt> contains the ...
     */
    private File workingDirectory = new File(".");

    /**
     * {@inheritDoc}
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        File base = new File("base.tex");
        File target = new File("target");
        File aux = new File(target, //
            base.getName().replaceFirst("\\.[a-zA-Z]+$", ".aux"));

        target.mkdirs();

        if (!base.exists()) {
            // TODO gene: execute unimplemented
            throw new RuntimeException("unimplemented");
        }
        if (!aux.exists() || aux.lastModified() < base.lastModified()) {
            latex(base);
            if (!aux.exists()) {
                // TODO gene: execute unimplemented
                throw new RuntimeException("unimplemented");
            }
        }
        // determineDependencies(base, aux);
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

}
