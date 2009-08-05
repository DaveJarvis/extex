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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexAutoTask extends Task {

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

    private int bibtexLimit = 3;

    private int latexLimit = 8;

    /**
     * The field <tt>simulate</tt> contains the ...
     */
    private boolean simulate = true;

    /**
     * The field <tt>output</tt> contains the ...
     */
    private String output = ".";

    /**
     * The field <tt>latexCommand</tt> contains the ...
     */
    private String latexCommand = "pdflatex";

    /**
     * The field <tt>workingDirectory</tt> contains the ...
     */
    private String workingDirectory = ".";

    private Map<String, String> checksum = new HashMap<String, String>();

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param aux
     * @param base
     * 
     */
    private void determineDependencies(File base, File aux) {

        try {
            LineNumberReader r = new LineNumberReader(new FileReader(aux));
            try {
                for (String line = r.readLine(); line != null; line =
                        r.readLine()) {
                    switch (line.equals("") ? -1 : line.charAt(0)) {
                        case 'L':
                            if (line.matches("^LaTeX Warning: "
                                    + "There were undefined references\\.")) {
                                // LaTeX Warning: There were undefined
                                // references.
                            } else if (line
                                .matches("^LaTeX Warning: "
                                        + "Citation `.*' on page .* undefined on input line 3\\.")) {
                                // LaTeX Warning: Citation `abc' on page 1
                                // undefined on input line 3.
                            } else if (line
                                .matches("^LaTeX Warning: "
                                        + "Label(s) may have changed. Rerun to get cross-references right\\.")) {
                                // LaTeX Warning: Label(s) may have changed.
                                // Rerun to get cross-references right.
                            }
                            break;
                        case 'N':
                            if (line.matches("^No file .*\\.ind\\.")) {
                                // No file document2.ind.
                            }
                            break;
                        case 'O':
                            if (line
                                .matches("^Output written on target/document2.dvi (1 page, 280 bytes)\\.")) {
                                // Output written on target/document2.dvi (1
                                // page,
                                // 280 bytes).
                            }
                            break;
                    }
                }
            } finally {
                r.close();
            }
        } catch (FileNotFoundException e) {
            throw new BuildException(e);
        } catch (IOException e) {
            throw new BuildException(e);
        }
        // TODO gene: determineDependencies unimplemented
    }

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
        determineDependencies(base, aux);
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
        latex.directory(new File(workingDirectory));
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
