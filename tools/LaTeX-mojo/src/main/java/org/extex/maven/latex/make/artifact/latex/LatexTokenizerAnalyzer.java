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

package org.extex.maven.latex.make.artifact.latex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.extex.maven.latex.make.DependencyNet;
import org.extex.maven.latex.make.action.BibTeXAction;
import org.extex.maven.latex.make.action.LaTeXAction;
import org.extex.maven.latex.make.action.MakeindexAction;
import org.extex.maven.latex.make.artifact.Artifact;
import org.extex.maven.latex.make.artifact.BblArtifact;
import org.extex.maven.latex.make.artifact.BibtexArtifact;
import org.extex.maven.latex.make.artifact.LatexArtifact;

/**
 * This class represents a LaTeX analyzer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexTokenizerAnalyzer implements LaTeXAnalyzer {

    /**
     * The field <tt>macros</tt> contains the macros.
     */
    private Map<String, Macro> macros = new HashMap<String, Macro>();

    private final Macro BEGIN = new Macro() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.maven.latex.make.artifact.latex.Macro#expand(LatexReader,
         *      org.extex.maven.latex.make.DependencyNet, java.io.File)
         */
        @Override
        public void expand(LatexReader reader, DependencyNet net, File base)
                throws IOException {

            String arg = reader.scanBlock();
            if (arg != null) {
                Macro macro = macros.get("\\begin{" + arg + "}");
                if (macro != null) {
                    macro.expand(reader, net, base);
                }
            }
        }
    };

    /**
     * Creates a new object.
     * 
     */
    public LatexTokenizerAnalyzer() {

        macros.put("\\begin", BEGIN);
        macros.put("\\begin{document}", new Macro() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.maven.latex.make.artifact.latex.Macro#expand(LatexReader,
             *      org.extex.maven.latex.make.DependencyNet, java.io.File)
             */
            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                net.getLogger().fine(base.getName() + ": \\begin{document}");

                Artifact aux = net.getDerivedTargetArtifact("aux");
                aux.provideActions(new LaTeXAction(net.getMaster()));
                net.getTarget().dependsOn(aux);
            }
        });
        macros.put("\\bibliography", new MacroWithArgs() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.maven.latex.make.artifact.latex.MacroWithArgs#expand(java.io.PushbackReader,
             *      org.extex.maven.latex.make.DependencyNet, java.io.File,
             *      java.lang.String, java.lang.String)
             */
            @Override
            protected void expand(LatexReader reader, DependencyNet net,
                    File base, String opt, String block) throws IOException {

                String[] args = block.split(",");
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
                            net
                                .findFile(arg, net
                                    .context(LatexArtifact.BIBTEX_EXTENSIONS),
                                    base);
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
        });
        macros.put("\\input", new Macro() {

            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                String arg;
                int c = reader.scanNext();
                if (c == '{') {
                    reader.unread(c);
                    arg = reader.scanBlock();
                } else if (c < 0) {
                    return;
                } else {
                    StringBuilder buffer = new StringBuilder();
                    do {
                        buffer.append((char) c);
                        c = reader.read();
                    } while (c >= 0 && !Character.isWhitespace(c));

                    arg = buffer.toString();
                }

                net.getLogger().fine(base.getName() + ": \\input " + arg);
                File file =
                        net.findFile(arg, net
                            .context(LatexArtifact.LATEX_EXTENSIONS), base);
                Artifact a = net.findArtifact(file);
                if (a == null) {
                    a = new LatexArtifact(file);
                    net.addArtifact(a);
                }
                Artifact target = net.getTarget();
                target.dependsOn(a);
                // TODO
            }
        });
        macros.put("\\InputIfFileExists", new MacroWithArgs() {

            @Override
            public void expand(LatexReader reader, DependencyNet net,
                    File base, String opt, String arg) throws IOException {

                net.getLogger().fine(
                    base.getName() + ": \\InputIfFileExists " + arg);
                File file =
                        net.searchFile(arg, net
                            .context(LatexArtifact.LATEX_EXTENSIONS), base);
                if (file == null) {
                    return;
                }
                Artifact a = net.findArtifact(file);
                if (a == null) {
                    a = new LatexArtifact(file);
                    net.addArtifact(a);
                }
                Artifact target = net.getTarget();
                target.dependsOn(a);
            }
        });
        macros.put("\\include", new MacroWithArgs() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.maven.latex.make.artifact.latex.MacroWithArgs#expand(java.io.PushbackReader,
             *      org.extex.maven.latex.make.DependencyNet, java.io.File,
             *      java.lang.String, java.lang.String)
             */
            @Override
            public void expand(LatexReader reader, DependencyNet net,
                    File base, String opt, String arg) throws IOException {

                net.getLogger().fine(base.getName() + ": \\include " + arg);

                File file =
                        net.findFile(arg, net
                            .context(LatexArtifact.LATEX_EXTENSIONS), base);
                Artifact a = net.findArtifact(file);
                if (a == null) {
                    a = new LatexArtifact(file);
                    net.addArtifact(a);
                }

                Artifact target = net.getTarget();
                target.dependsOn(a);
            }
        });
        macros.put("\\includegraphics", new MacroWithArgs() {

            @Override
            public void expand(LatexReader reader, DependencyNet net,
                    File base, String opt, String arg) throws IOException {

                net.getLogger().fine(
                    base.getName() + ": \\includegraphics " + arg);

                File file =
                        net.findFile(arg, net
                            .context(LatexArtifact.GRAPHICS_EXTENSIONS), base);
                net.getTarget().dependsOn(net.getArtifact(file));
            }
        });
        macros.put("\\makeatletter", new Macro() {

            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                reader.setAtLetter(true);
            }

        });
        macros.put("\\makeatother", new Macro() {

            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                reader.setAtLetter(false);
            }

        });
        macros.put("\\printglossary", new Macro() {

            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                net.getLogger().fine(base.getName() + ": \\printglossary");

                Artifact target = net.getTarget();
                Artifact glx = net.getDerivedTargetArtifact("glx");
                Artifact glo = net.getDerivedTargetArtifact("glo");
                target.dependsOn(glo);
                glo.dependsOn(glx);
            }
        });
        macros.put("\\printindex", new Macro() {

            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

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
        });
        macros.put("\\tableofcontents", new Macro() {

            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                net.getLogger().fine(base.getName() + ": \\tableofcontents");

                Artifact toc = net.getDerivedTargetArtifact("toc");
                toc.provideActions(new LaTeXAction(net.getMaster()));
                net.getTarget().dependsOn(toc);
            }
        });
        macros.put("\\usepackage", new MacroWithArgs() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.maven.latex.make.artifact.latex.MacroWithArgs#expand(java.io.PushbackReader,
             *      org.extex.maven.latex.make.DependencyNet, java.io.File,
             *      java.lang.String, java.lang.String)
             */
            @Override
            public void expand(LatexReader reader, DependencyNet net,
                    File base, String opt, String arg) throws IOException {

                net.getLogger().fine(base.getName() + ": \\usepackage " + arg);

                // TODO

                // File file =
                // net.findFile(arg, net
                // .context(LatexArtifact.LATEX_EXTENSIONS), base);

                // Artifact a = net.findArtifact(file);
                // if (a == null) {
                // a = new LatexArtifact(file);
                // net.addArtifact(a);
                // }
                //
                // Artifact target = net.getTarget();
                // target.dependsOn(a);
            }
        });
        macros.put("\\verb", new Macro() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.maven.latex.make.artifact.latex.Macro#expand(LatexReader,
             *      org.extex.maven.latex.make.DependencyNet, java.io.File)
             */
            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                int del = reader.scanNext();
                int c;
                do {
                    c = reader.scanNext();
                } while (c >= 0 && c != del);
            }
        });
        macros.put("\\begin{verbatim}", new Macro() {

            /**
             * {@inheritDoc}
             * 
             * @see org.extex.maven.latex.make.artifact.latex.Macro#expand(LatexReader,
             *      org.extex.maven.latex.make.DependencyNet, java.io.File)
             */
            @Override
            public void expand(LatexReader reader, DependencyNet net, File base)
                    throws IOException {

                reader.scanTo("\\end{verbatim}");
            }
        });
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.make.artifact.latex.LaTeXAnalyzer#analyze(org.extex.maven.latex.make.artifact.Artifact,
     *      org.extex.maven.latex.make.DependencyNet)
     */
    public void analyze(Artifact artifact, DependencyNet net)
            throws IOException {

        File file = artifact.getFile();
        LatexReader reader =
                new LatexReader(new BufferedReader(new FileReader(file)));

        try {
            for (String cs = reader.scanControlSequence(); cs != null; cs =
                    reader.scanControlSequence()) {
                Macro macro = macros.get(cs);
                if (macro != null) {
                    macro.expand(reader, net, file);
                }
            }
        } finally {
            reader.close();
        }
    }

}
