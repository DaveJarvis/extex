/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.maven.latex.make.artifact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.maven.latex.make.Artifact;
import org.extex.maven.latex.make.Net;
import org.extex.maven.latex.make.dependency.ArtifactDependency;

/**
 * This class represents a LaTeX artifact.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexArtifact extends Artifact {

    /**
     * The field <tt>BIBTEX_EXTENSIONS</tt> contains the ...
     */
    private static final String BIBTEX_EXTENSIONS = "bibtex.extensions";

    /**
     * The field <tt>GRAPHICS_EXTENSIONS</tt> contains the ...
     */
    private static final String GRAPHICS_EXTENSIONS = "graphics.extensions";

    /**
     * The field <tt>LATEX_COMMAND</tt> contains the ...
     */
    private static final String LATEX_COMMAND = "latex.command";

    /**
     * The field <tt>LATEX_EXTENSIONS</tt> contains the ...
     */
    private static final String LATEX_EXTENSIONS = "latex.extensions";

    /**
     * The field <tt>INPUT_PATTERN_1</tt> contains the pattern for \input with
     * argument in braces.
     */
    private static final Pattern INPUT_PATTERN_1 =
            Pattern.compile("\\\\input[ ]*\\{([^{}]*)\\}");

    /**
     * The field <tt>INPUT_PATTERN_2</tt> contains the pattern for \input
     * without braces.
     */
    private static final Pattern INPUT_PATTERN_2 =
            Pattern.compile("\\\\input[ ]*([^{} ]*)");

    /**
     * The field <tt>INCLUDE_PATTERN</tt> contains the pattern for \include.
     */
    private static final Pattern INCLUDE_PATTERN =
            Pattern.compile("\\\\include[ ]*\\{([^{}]*)\\}");

    /**
     * The field <tt>INCLUDEGRAPHICS_PATTERN_1</tt> contains the pattern for
     * \includegraphics.
     */
    private static final Pattern INCLUDEGRAPHICS_PATTERN_1 =
            Pattern.compile("\\\\includegraphics[ ]*\\{([^{}]*)\\}");

    /**
     * The field <tt>INCLUDEGRAPHICS_PATTERN_2</tt> contains the pattern for
     * \includegraphics with optional argument.
     */
    private static final Pattern INCLUDEGRAPHICS_PATTERN_2 =
            Pattern.compile("\\\\includegraphics[ ]*\\[[^]]*\\][ ]*"
                    + "\\{([^{}]*)\\}");

    /**
     * The field <tt>BIBLIOGRAPHY_PATTERN</tt> contains the pattern for
     * \bibliography.
     */
    private static final Pattern BIBLIOGRAPHY_PATTERN =
            Pattern.compile("\\\\bibliography[ ]*\\{([^{}]*)\\}");

    /**
     * Callback method to initialize a net.
     * 
     * @param net the net to initialize
     */
    public static void setup(Net net) {

        net.contextFallback(LATEX_COMMAND, "latex");
        net.contextFallback(LATEX_EXTENSIONS, ".tex:.TeX:.latex:.LaTeX:.ltx:");
        net.contextFallback(GRAPHICS_EXTENSIONS,
            ".png:.jpg:.jpeg:.gif:.tiff:.pdf:");
        net.contextFallback(BIBTEX_EXTENSIONS, ".bib:.bibtex:.BibTeX:");
    }

    /**
     * Creates a new object.
     * 
     * @param file the file
     */
    public LatexArtifact(File file) {

        super(file);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.Artifact#analyze(Net)
     */
    @Override
    public void analyze(Net net) throws IOException {

        LineNumberReader r = new LineNumberReader(new FileReader(getFile()));
        Matcher m;

        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                line.replaceAll("[^\\\\]%.*", "");
                line.replaceAll("\t", " ");

                if ((m = INPUT_PATTERN_1.matcher(line)).matches()
                        || (m = INPUT_PATTERN_2.matcher(line)).matches()) {
                    onInput(net, m.group(1));
                } else if ((m = INCLUDE_PATTERN.matcher(line)).matches()) {
                    onInclude(net, m.group(1));
                } else if ((m = BIBLIOGRAPHY_PATTERN.matcher(line)).matches()) {
                    onBibliography(net, m.group(1).split(","));
                } else if ((m = INCLUDEGRAPHICS_PATTERN_1.matcher(line))
                    .matches()
                        || (m = INCLUDEGRAPHICS_PATTERN_2.matcher(line))
                            .matches()) {
                    onIncludegraphics(net, m.group(1));
                } else if (line.matches("\\\\tableofcontent")) {
                    onToc(net);
                } else if (line.matches("\\\\printindex")) {
                    onPrintindex(net);
                } else if (line.matches("\\\\printglossary")) {
                    onPrintglossary(net);
                }
            }
        } finally {
            r.close();
        }
    }

    /**
     * Find a resource.
     * 
     * @param extensions the extensions
     * 
     * @return the full file
     * 
     * @throws FileNotFoundException in case no resource could be found
     */
    private File findFile(String extensions) throws FileNotFoundException {

        String name = getFile().getName().replaceAll("\\.[a-zA-Z0-9_]*", "");

        return findFile(name, extensions);
    }

    /**
     * Find a resource.
     * 
     * @param fileName
     * @param extensions the extensions
     * 
     * @return the full file
     * 
     * @throws FileNotFoundException in case no resource could be found
     */
    private File findFile(String fileName, String extensions)
            throws FileNotFoundException {

        String name = fileName.replaceAll("\\.[a-zA-Z0-9_]*", "");

        for (String ext : extensions.split(":")) {
            File f = new File(getFile().getParentFile(), name + ext);
            if (f.exists()) {
                return f;
            }
        }
        throw new FileNotFoundException(name);
    }

    /**
     * Process an \bibliography.
     * 
     * @param net the dependency net
     * @param args the arguments
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onBibliography(Net net, String[] args)
            throws IOException,
                FileNotFoundException {

        System.err.print("bibliography");
        for (String arg : args) {
            System.err.print(" " + arg);
        }
        System.err.println();

        for (String arg : args) {
            File file = findFile(arg, net.context(BIBTEX_EXTENSIONS));
            Artifact a = net.findArtifact(file);
            if (a == null) {
                a = new BibtexArtifact(file);
                net.putArtifact(a);
            }

            addDependencies(new ArtifactDependency(a));
        }
    }

    /**
     * Process an \include.
     * 
     * @param net the dependency net
     * @param arg the argument
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onInclude(Net net, String arg)
            throws IOException,
                FileNotFoundException {

        System.err.println("include " + arg);

        File file = findFile(arg, net.context(LATEX_EXTENSIONS));
        Artifact a = net.findArtifact(file);
        if (a == null) {
            a = new LatexArtifact(file);
            net.putArtifact(a);
        }

        addDependencies(new ArtifactDependency(a));
    }

    /**
     * Process an \includegraphics.
     * 
     * @param net the dependency net
     * @param arg the argument
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onIncludegraphics(Net net, String arg)
            throws FileNotFoundException,
                IOException {

        System.err.println("includegraphics " + arg);
        File file = findFile(arg, net.context(GRAPHICS_EXTENSIONS));
        Artifact a = net.findArtifact(file);
        if (a == null) {
            a = new Artifact(file);
            net.putArtifact(a);
        }
        addDependencies(new ArtifactDependency(a));
    }

    /**
     * Process an \input.
     * 
     * @param net the dependency net
     * @param arg the argument
     * 
     * @throws IOException in case of an I/O error
     * @throws FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onInput(Net net, String arg)
            throws IOException,
                FileNotFoundException {

        System.err.println("input " + arg);
        File file = findFile(arg, net.context(LATEX_EXTENSIONS));
        Artifact a = net.findArtifact(file);
        if (a == null) {
            a = new LatexArtifact(file);
            net.putArtifact(a);
        }
        addDependencies(new ArtifactDependency(a));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param net the dependency net
     */
    private void onPrintglossary(Net net) {

        System.err.println("printglossary");
        // TODO
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param net the dependency net
     */
    private void onPrintindex(Net net) {

        System.err.println("printindex");
        // TODO
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param net the dependency net
     */
    private void onToc(Net net) {

        System.err.println("tableofcontent");
        // TODO
    }

}
