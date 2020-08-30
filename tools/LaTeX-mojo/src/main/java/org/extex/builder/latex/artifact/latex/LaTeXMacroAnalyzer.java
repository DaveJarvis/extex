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

package org.extex.builder.latex.artifact.latex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.extex.builder.latex.DependencyNet;
import org.extex.builder.latex.artifact.Artifact;
import org.extex.builder.latex.artifact.latex.macro.BeginDocument;
import org.extex.builder.latex.artifact.latex.macro.BeginLstListing;
import org.extex.builder.latex.artifact.latex.macro.BeginVerbatim;
import org.extex.builder.latex.artifact.latex.macro.Bibliography;
import org.extex.builder.latex.artifact.latex.macro.DocumentClass;
import org.extex.builder.latex.artifact.latex.macro.Include;
import org.extex.builder.latex.artifact.latex.macro.IncludeGraphics;
import org.extex.builder.latex.artifact.latex.macro.Input;
import org.extex.builder.latex.artifact.latex.macro.InputIfFileExists;
import org.extex.builder.latex.artifact.latex.macro.MakeAtLetter;
import org.extex.builder.latex.artifact.latex.macro.MakeAtOther;
import org.extex.builder.latex.artifact.latex.macro.PrintGlossary;
import org.extex.builder.latex.artifact.latex.macro.PrintIndex;
import org.extex.builder.latex.artifact.latex.macro.TableOfContents;
import org.extex.builder.latex.artifact.latex.macro.UsePackage;
import org.extex.builder.latex.artifact.latex.macro.Verb;
import org.extex.builder.latex.artifact.latex.macro.VerbatimInput;

/**
 * This class represents a L<span class="la">a</span>T<span class="e">e</span>X
 * analyzer. It is based on a map of macros. The source files are scanned and
 * the handlers found in the map are invoked to react on the input. This means
 * the sate may be changed or the dependency net can be constructed.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LaTeXMacroAnalyzer implements LaTeXAnalyzer {

    /**
     * The field {@code macros} contains the macros.
     */
    private Map<String, Macro> macros = new HashMap<String, Macro>();


    public LaTeXMacroAnalyzer() {

        macros.put("\\begin", new Macro() {

            /**
        *      org.extex.builder.latex.DependencyNet, Artifact)
             */
            @Override
            public void expand(LatexReader reader, DependencyNet net,
                    Artifact artifact) throws IOException {

                String arg = reader.scanBlock();
                if (arg != null) {
                    Macro macro = macros.get("\\begin{" + arg + "}");
                    if (macro != null) {
                        macro.expand(reader, net, artifact);
                    }
                }
            }
        });
        macros.put("\\begin{document}", new BeginDocument());
        macros.put("\\bibliography", new Bibliography());
        macros.put("\\documentclass", new DocumentClass());
        macros.put("\\LoadClass", new DocumentClass());
        macros.put("\\input", new Input());
        macros.put("\\InputIfFileExists", new InputIfFileExists());
        macros.put("\\include", new Include());
        macros.put("\\includegraphics", new IncludeGraphics());
        macros.put("\\epsfig", new IncludeGraphics());
        macros.put("\\makeatletter", new MakeAtLetter());
        macros.put("\\makeatother", new MakeAtOther());
        macros.put("\\begin{lstlisting}", new BeginLstListing());
        macros.put("\\printglossary", new PrintGlossary());
        macros.put("\\printindex", new PrintIndex());
        macros.put("\\tableofcontents", new TableOfContents());
        macros.put("\\usepackage", new UsePackage());
        macros.put("\\verb", new Verb());
        macros.put("\\begin{verbatim}", new BeginVerbatim());
        macros.put("\\verbatiminput", new VerbatimInput());
        macros.put("\\lstinputlisting", new VerbatimInput());
    }

    /**
*      org.extex.builder.latex.DependencyNet)
     */
    public void analyze(Artifact artifact, DependencyNet net)
            throws IOException {

        File file = artifact.getFile();
        LatexReader reader =
                new LatexReader(new BufferedReader(new FileReader(file)), net);

        try {
            for (String cs = reader.scanControlSequence(); cs != null; cs =
                    reader.scanControlSequence()) {
                Macro macro = macros.get(cs);
                if (macro != null) {
                    net.getLogger().fine(artifact.getName() + ": " + cs);
                    macro.expand(reader, net, artifact);
                }
            }
        } finally {
            reader.close();
        }
    }

}
