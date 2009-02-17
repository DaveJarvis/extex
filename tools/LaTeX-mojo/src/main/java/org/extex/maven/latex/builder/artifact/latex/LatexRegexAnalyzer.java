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

package org.extex.maven.latex.builder.artifact.latex;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.extex.maven.latex.builder.DependencyNet;
import org.extex.maven.latex.builder.action.BibTeXAction;
import org.extex.maven.latex.builder.action.LaTeXAction;
import org.extex.maven.latex.builder.action.MakeindexAction;
import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.artifact.BblArtifact;
import org.extex.maven.latex.builder.artifact.BibtexArtifact;
import org.extex.maven.latex.builder.artifact.LatexArtifact;

/**
 * This class represents a L<span class="la">a</span>T<span class="e">e</span>X
 * analyzer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexRegexAnalyzer implements LaTeXAnalyzer {

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
     * Analyze a LaTeX file and update the dependency net accordingly.
     * 
     * @param artifact the artifact to analyze
     * @param net the dependency net
     * 
     * @throws IOException in case of an I/O error
     * 
     * @see org.extex.maven.latex.builder.artifact.latex.LaTeXAnalyzer#analyze(org.extex.maven.latex.builder.artifact.Artifact,
     *      org.extex.maven.latex.builder.DependencyNet)
     */
    public void analyze(Artifact artifact, DependencyNet net)
            throws IOException {

        File file = artifact.getFile();
        LineNumberReader r = new LineNumberReader(new FileReader(file));
        Matcher m;

        try {
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                line.replaceAll("[^\\\\]%.*", "");
                line.replaceAll("\t", " ");

                // The inner assignments make this method rather short and
                // readable. Thus it is better to use them then to omit them.
                if ((m = INPUT_PATTERN_1.matcher(line)).matches()
                        || (m = INPUT_PATTERN_2.matcher(line)).matches()) {
                    onInput(net, file, m.group(1));
                } else if ((m = INCLUDE_PATTERN.matcher(line)).matches()) {
                    onInclude(net, file, m.group(1));
                } else if ((m = BIBLIOGRAPHY_PATTERN.matcher(line)).matches()) {
                    onBibliography(net, file, m.group(1).split(","));
                } else if ((m = INCLUDEGRAPHICS_PATTERN_1.matcher(line))
                    .matches()
                        || (m = INCLUDEGRAPHICS_PATTERN_2.matcher(line))
                            .matches()) {
                    onIncludegraphics(net, file, m.group(1));
                } else if (line.matches("\\\\tableofcontents")) {
                    onToc(net, file);
                } else if (line.matches("\\\\printindex")) {
                    onPrintindex(net, file);
                } else if (line.matches("\\\\printglossary")) {
                    onPrintglossary(net, file);
                } else if (line.matches("\\\\begin[ ]*\\{document\\}")) {
                    onBeginDocument(net, file);
                }
            }
        } finally {
            r.close();
        }
    }

    /**
     * Process an \begin{document}.
     * 
     * @param net the dependency net
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error
     */
    private void onBeginDocument(DependencyNet net, File base)
            throws IOException {

        net.getLogger().fine(base.getName() + ": \\begin{document}");

        Artifact aux = net.getDerivedTargetArtifact("aux");
        aux.provideActions(new LaTeXAction(net.getMaster()));
        net.getTarget().dependsOn(aux);
    }

    /**
     * Process an \bibliography.
     * 
     * @param net the dependency net
     * @param base the base file
     * @param args the arguments
     * 
     * @throws IOException in case of an I/O error <br/>
     *         FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onBibliography(DependencyNet net, File base, String[] args)
            throws IOException {

        StringBuilder buffer =
                new StringBuilder(base.getName() + ": \\bibliography");
        for (String arg : args) {
            buffer.append(' ');
            buffer.append(arg);
        }
        net.getLogger().fine(buffer.toString());

        Artifact target = net.getTarget();
        File bblFile = target.derivedFile("bbl");
        Artifact bbl = net.findArtifact(bblFile);
        if (bbl == null) {
            bbl = new BblArtifact(bblFile);
            net.addArtifact(bbl);
            bbl.provideActions(new BibTeXAction(net.getMaster()));
        }
        target.dependsOn(bbl);

        for (String arg : args) {
            File file =
                    net.findFile(arg, net
                        .context(LatexArtifact.BIBTEX_EXTENSIONS), base);
            Artifact a = net.findArtifact(file);
            if (a == null) {
                a = new BibtexArtifact(file);
                net.addArtifact(a);
            }
            bbl.dependsOn(a);
        }
        Artifact aux = net.getDerivedTargetArtifact("aux");
        aux.provideActions(new LaTeXAction(net.getMaster()));
        bbl.dependsOn(aux);
    }

    /**
     * Process an \include.
     * 
     * @param net the dependency net
     * @param arg the argument
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error <br/>
     *         FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onInclude(DependencyNet net, File base, String arg)
            throws IOException {

        net.getLogger().fine(base.getName() + ": \\include " + arg);

        File file =
                net.findFile(arg, net.context(LatexArtifact.LATEX_EXTENSIONS),
                    base);
        Artifact a = net.findArtifact(file);
        if (a == null) {
            a = new LatexArtifact(file);
            net.addArtifact(a);
        }

        Artifact target = net.getTarget();
        target.dependsOn(a);
    }

    /**
     * Process an \includegraphics.
     * 
     * @param net the dependency net
     * @param arg the argument
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error <br/>
     *         FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onIncludegraphics(DependencyNet net, File base, String arg)
            throws IOException {

        net.getLogger().fine(base.getName() + ": \\includegraphics " + arg);

        File file =
                net.findFile(arg, net
                    .context(LatexArtifact.GRAPHICS_EXTENSIONS), base);
        net.getTarget().dependsOn(net.getArtifact(file));
    }

    /**
     * Process an \input.
     * 
     * @param net the dependency net
     * @param arg the argument
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error <br/>
     *         FileNotFoundException in case that the resource could not be
     *         found
     */
    private void onInput(DependencyNet net, File base, String arg)
            throws IOException {

        net.getLogger().fine(base.getName() + ": \\input " + arg);
        File file =
                net.findFile(arg, net.context(LatexArtifact.LATEX_EXTENSIONS),
                    base);
        Artifact a = net.findArtifact(file);
        if (a == null) {
            a = new LatexArtifact(file);
            net.addArtifact(a);
        }
        Artifact target = net.getTarget();
        target.dependsOn(a);
    }

    /**
     * Process an \printglossary.
     * 
     * @param net the dependency net
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error
     */
    private void onPrintglossary(DependencyNet net, File base)
            throws IOException {

        net.getLogger().fine(base.getName() + ": \\printglossary");

        Artifact target = net.getTarget();
        Artifact glx = net.getDerivedTargetArtifact("glx");
        Artifact glo = net.getDerivedTargetArtifact("glo");
        target.dependsOn(glo);
        glo.dependsOn(glx);
    }

    /**
     * Process an \printindex.
     * 
     * @param net the dependency net
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error
     */
    private void onPrintindex(DependencyNet net, File base) throws IOException {

        net.getLogger().fine(base.getName() + ": \\printindex");

        Artifact target = net.getTarget();
        Artifact idx = net.getDerivedTargetArtifact("idx");
        idx.provideActions(new LaTeXAction(net.getMaster()));
        Artifact ind = net.getDerivedTargetArtifact("ind");
        idx.provideActions(new MakeindexAction(net.getMaster()));
        target.dependsOn(ind);
        ind.dependsOn(idx);
        // TODO idx.dependsOn(*tex);
    }

    /**
     * Process an \tableofcontents.
     * 
     * @param net the dependency net
     * @param base the base file
     * 
     * @throws IOException in case of an I/O error
     */
    private void onToc(DependencyNet net, File base) throws IOException {

        net.getLogger().fine(base.getName() + ": \\tableofcontents");

        Artifact toc = net.getDerivedTargetArtifact("toc");
        toc.provideActions(new LaTeXAction(net.getMaster()));
        net.getTarget().dependsOn(toc);
    }

}
