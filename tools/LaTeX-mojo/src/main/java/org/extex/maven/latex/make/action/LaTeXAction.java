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

package org.extex.maven.latex.make.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.maven.latex.make.artifact.Artifact;
import org.extex.maven.latex.make.exception.MakeException;

/**
 * This action runs LaTeX in one of its variants on the artifact.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LaTeXAction implements BuildAction {

    /**
     * The field <tt>artifact</tt> contains the artifact to run LaTeX on.
     */
    private final Artifact artifact;

    /**
     * Creates a new object.
     * 
     * @param artifact the artifact to run LaTeX on
     */
    public LaTeXAction(Artifact artifact) {

        this.artifact = artifact;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.action.BuildAction#execute(org.extex.maven.latex.make.artifact.Artifact,
     *      java.util.Map, Logger, boolean)
     */
    public void execute(Artifact target, Map<String, String> context,
            Logger logger, boolean simulate) throws MakeException {

        String output = get(context, "latex.output.directory", ".");
        String latexCommand = get(context, "latex.command", "latex");
        String workingDirectory = get(context, "latex.working.directory", ".");
        String base = artifact.getFile().toString().replace('\\', '/');

        if (logger != null) {
            logger.log(simulate ? Level.INFO : Level.FINE, //
                "--> " + latexCommand + " " + base);
        }
        if (simulate) {
            return;
        }

        ProcessBuilder latex = new ProcessBuilder(latexCommand, //
            "-output-directory=" + output, //
            "-nonstopmode", //
            base);
        latex.directory(new File(workingDirectory));
        latex.redirectErrorStream(true);
        try {
            Process p = latex.start();
            try {
                p.getOutputStream().close();
                StringBuilder buffer = new StringBuilder();
                InputStream in = p.getInputStream();
                for (int c = in.read(); c >= 0; c = in.read()) {
                    buffer.append((char) c);
                }
                if (p.exitValue() != 0) {
                    logger.severe(buffer.toString());
                }
            } finally {
                p.destroy();
            }
        } catch (IOException e) {
            throw new MakeException(e);
        }
    }

    /**
     * Get some value from a context map with a fallback value in case that the
     * value is not found in the context.
     * 
     * @param context the context map
     * @param key the key
     * @param fallback the fallback value
     * 
     * @return the value for the key of the fallback value
     */
    private String get(Map<String, String> context, String key, String fallback) {

        String result = context.get(key);
        return result != null ? result : fallback;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.action.BuildAction#print(java.io.PrintWriter,
     *      java.lang.String)
     */
    public void print(PrintWriter w, String pre) {

        w.print(pre);
        w.println(toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "latex $*";
    }

}
