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

package org.extex.maven.latex.builder.artifact.latex.macro;

import java.io.File;
import java.io.IOException;

import org.extex.maven.latex.builder.ContextKey;
import org.extex.maven.latex.builder.DependencyNet;
import org.extex.maven.latex.builder.action.BibTeXAction;
import org.extex.maven.latex.builder.action.LaTeXAction;
import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.artifact.latex.LatexReader;
import org.extex.maven.latex.builder.artifact.latex.MacroWithArgs;

/**
 * This class implements a handler for <code>\bibliography</code>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Bibliography extends MacroWithArgs {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.artifact.latex.MacroWithArgs#expand(org.extex.maven.latex.builder.artifact.latex.LatexReader,
     *      org.extex.maven.latex.builder.DependencyNet, java.io.File,
     *      java.lang.String, java.lang.String)
     */
    @Override
    protected void expand(LatexReader reader, DependencyNet net, File base,
            String opt, String block) throws IOException {

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
            bbl = new Artifact(bblFile);
            net.addArtifact(bbl);
            bbl.provideActions(new BibTeXAction(net.getMaster()));
        }
        target.dependsOn(bbl);

        for (String arg : args) {
            File file = net.findFile(arg, ContextKey.BIBTEX_EXTENSIONS, base);
            bbl.dependsOn(net.getArtifact(file));
        }
        Artifact aux = net.getDerivedTargetArtifact("aux");
        aux.provideActions(new LaTeXAction(net.getMaster()));
        bbl.dependsOn(aux);
    }

}
