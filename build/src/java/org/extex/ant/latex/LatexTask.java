/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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
package org.extex.ant.latex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * This class provides an interface from Ant to LaTeX.
 * 
 * <pre>
 *            &lt;taskdef name=&quot;LaTeX&quot; .../&gt;
 *            
 *            &lt;LaTeX file=&quot;abc.ltx&quot;
 *                   executable=&quot;latex&quot;
 *            &gt;
 *            &lt;/LaTeX&gt;
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexTask extends Task {

    private String basename;

    private String bibtex = "bibtex";

    private String executable = "pdflatex";

    private String file = null;

    private List filesets = new ArrayList();

    private boolean keepAux = true;

    private String makeindex = "makeindex";

    private String target = null;

    private AuxVisitor visitor = new AuxVisitor() {

        public void visitBibTeX(final File file) throws IOException {

            run(bibtex);
        }

        public void visitLaTeX(final File file) throws IOException {

            run(executable);
        }

        public void visitMakeindex(final File file) throws IOException {

            run(makeindex);
        }

    };

    /**
     * ...
     * 
     * @param fileset
     */
    public void addFileset(final FileSet fileset) {

        filesets.add(fileset);
    }

    /**
     * @see org.apache.tools.ant.Task#execute()
     */
    public void execute() {

        if (file == null) {
            throw new BuildException("No file given.");
        }

        this.basename = file.replaceAll("\\.[a-zA-Z0-9_]$", "");

        AuxFile aux = new AuxFile(basename);
        try {
            execute(aux);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * ...
     * 
     * @param aux
     * 
     * @throws IOException in case of an error
     */
    private void execute(final AuxFile aux) throws IOException {

        File t = new File(target != null ? target : basename + ".pdf");
        if (upToDate(t)) {
            return;
        }

        if (!aux.canRead()) {
            run(executable);
        }

        while (aux.redo(visitor)) {
        }
    }

    /**
     * ...
     * 
     * @throws IOException in case of an error
     * 
     */
    private void run(final String program) throws IOException {

        String[] cmd = {program, basename};
        Process process = Runtime.getRuntime().exec(cmd);
        process.exitValue();
    }

    /**
     * Setter for executable.
     * 
     * @param executable the executable to set.
     */
    public void setExecutable(String executable) {

        this.executable = executable;
    }

    /**
     * Setter for file name.
     * 
     * @param name the name to set.
     */
    public void setFile(String name) {

        this.file = name;
    }

    /**
     * Setter for keepAux.
     * 
     * @param keep the keepAux to set.
     */
    public void setKeepAux(final String keep) {

        this.keepAux = Boolean.getBoolean(keep);
    }

    /**
     * Setter for target.
     * 
     * @param target the target to set.
     */
    public void setTarget(final String target) {

        this.target = target;
    }

    /**
     * ...
     * 
     * @param file
     * 
     * @return
     */
    private boolean upToDate(final File f) {

        if (!f.exists()) {
            return false;
        }
        File source = new File(file);
        if (source.lastModified() > f.lastModified()) {
            return false;
        }
        for (int i = 0; i < filesets.size(); i++) {
            System.err.println(((FileSet)filesets.get(i)).toString());
        }
        return true;
    }
}
