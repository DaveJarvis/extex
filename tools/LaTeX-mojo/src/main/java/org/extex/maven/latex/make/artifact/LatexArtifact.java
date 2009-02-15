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
import java.io.IOException;

import org.extex.maven.latex.make.DependencyNet;
import org.extex.maven.latex.make.artifact.latex.LaTeXAnalyzer;
import org.extex.maven.latex.make.artifact.latex.LatexTokenizerAnalyzer;

/**
 * This class represents a LaTeX artifact.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexArtifact extends Artifact {

    /**
     * The field <tt>ANALYZER</tt> contains the analyzer.
     */
    private static final LaTeXAnalyzer ANALYZER =
            new LatexTokenizerAnalyzer();

    /**
     * The field <tt>BIBTEX_EXTENSIONS</tt> contains the context name for the
     * BibTeX extensions.
     */
    public static final String BIBTEX_EXTENSIONS = "bibtex.extensions";

    /**
     * The field <tt>GRAPHICS_EXTENSIONS</tt> contains the context name for the
     * graphics extensions.
     */
    public static final String GRAPHICS_EXTENSIONS = "graphics.extensions";

    /**
     * The field <tt>LATEX_COMMAND</tt> contains the context name for the LaTeX
     * command.
     */
    public static final String LATEX_COMMAND = "latex.command";

    /**
     * The field <tt>LATEX_EXTENSIONS</tt> contains the context name for the
     * LaTeX extensions.
     */
    public static final String LATEX_EXTENSIONS = "latex.extensions";

    /**
     * Callback method to initialize a net.
     * 
     * @param net the net to initialize
     */
    public static void setup(DependencyNet net) {

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
     * 
     * @throws IOException in case of an I/O error
     */
    public LatexArtifact(File file) throws IOException {

        super(file);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.artifact.Artifact#analyze(DependencyNet)
     */
    @Override
    public void analyze(DependencyNet net) throws IOException {

        ANALYZER.analyze(this, net);
    }

}
