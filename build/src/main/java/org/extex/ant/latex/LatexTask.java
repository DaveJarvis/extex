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
 *   &lt;taskdef name=&quot;LaTeX&quot; .../&gt;
 *
 *   &lt;LaTeX file=&quot;abc.ltx&quot;
 *          executable=&quot;latex&quot;
 *          &gt;
 *   &lt;/LaTeX&gt;
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexTask extends Task {

    /**
     * The field <tt>basename</tt> contains the ...
     */
    private String basename;

    /**
     * The field <tt>bibtex</tt> contains the ...
     */
    private String bibtex = "bibtex";

    /**
     * The field <tt>executable</tt> contains the ...
     */
    private String executable = "pdflatex";

    /**
     * The field <tt>file</tt> contains the ...
     */
    private String file = null;

    /**
     * The field <tt>filesets</tt> contains the ...
     */
    private List<FileSet> filesets = new ArrayList<FileSet>();

    /**
     * The field <tt>keepAux</tt> contains the ...
     */
    private boolean keepAux = true;

    /**
     * The field <tt>makeindex</tt> contains the ...
     */
    private String makeindex = "makeindex";

    /**
     * The field <tt>target</tt> contains the ...
     */
    private String target = null;

    /**
     * The field <tt>visitor</tt> contains the ...
     */
    private AuxVisitor visitor = new AuxVisitor() {

        /**
         * TODO gene: missing JavaDoc
         *
         * @param f ...
         * @throws IOException ...
         *
         * @see org.extex.ant.latex.AuxVisitor#visitBibTeX(java.io.File)
         */
        public void visitBibTeX(File f) throws IOException {

            run(bibtex);
        }

        /**
         * TODO gene: missing JavaDoc
         *
         * @param f ...
         *
         * @throws IOException ...
         *
         * @see org.extex.ant.latex.AuxVisitor#visitLaTeX(java.io.File)
         */
        public void visitLaTeX(File f) throws IOException {

            run(executable);
        }

        /**
         * TODO gene: missing JavaDoc
         *
         * @param f ...
         *
         * @throws IOException ...
         *
         * @see org.extex.ant.latex.AuxVisitor#visitMakeindex(java.io.File)
         */
        public void visitMakeindex(File f) throws IOException {

            run(makeindex);
        }

    };

    /**
     * ...
     *
     * @param fileset ...
     */
    public void addFileset(FileSet fileset) {

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
     * @param aux ...
     *
     * @throws IOException in case of an error
     */
    private void execute(AuxFile aux) throws IOException {

        File t = new File(target != null ? target : basename + ".pdf");
        if (upToDate(t)) {
            return;
        }

        if (!aux.canRead()) {
            run(executable);
        }

        while (aux.redo(visitor)) {
            // ...
        }
    }

    /**
     * ...
     *
     * @param program the command name
     *
     * @throws IOException in case of an error
     */
    private void run(String program) throws IOException {

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
    public void setKeepAux(String keep) {

        this.keepAux = Boolean.getBoolean(keep);
    }

    /**
     * Setter for target.
     *
     * @param target the target to set.
     */
    public void setTarget(String target) {

        this.target = target;
    }

    /**
     * ...
     *
     * @param f the file to check
     *
     * @return <code>true</code> iff ...
     */
    private boolean upToDate(File f) {

        if (!f.exists()) {
            return false;
        }
        File source = new File(file);
        if (source.lastModified() > f.lastModified()) {
            return false;
        }
        for (int i = 0; i < filesets.size(); i++) {
            System.err.println(filesets.get(i).toString());
        }
        return true;
    }
}
