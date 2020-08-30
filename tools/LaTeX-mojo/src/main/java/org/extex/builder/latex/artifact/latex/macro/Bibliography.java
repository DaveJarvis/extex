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

package org.extex.builder.latex.artifact.latex.macro;

import java.io.File;
import java.io.IOException;

import org.extex.builder.latex.DependencyNet;
import org.extex.builder.latex.action.BibTeXAction;
import org.extex.builder.latex.action.LaTeXAction;
import org.extex.builder.latex.artifact.Artifact;
import org.extex.builder.latex.artifact.latex.LatexReader;
import org.extex.builder.latex.artifact.latex.MacroWithArgs;

/**
 * This class implements a handler for {@code \bibliography}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class Bibliography extends MacroWithArgs {

    /**
*      org.extex.builder.latex.DependencyNet,
     *      org.extex.builder.latex.artifact.Artifact, java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected void expand(LatexReader reader, DependencyNet net,
            Artifact artifact, String opt, String block) throws IOException {

        String[] args = block.split(",");

        Artifact target = net.getTarget();
        File bblFile = target.derivedFile("bbl");
        Artifact bbl = net.findArtifact(bblFile);
        if (bbl == null) {
            bbl = new Artifact(bblFile);
            net.addArtifact(bbl);
            bbl.provideActions(new BibTeXAction(net.getMaster()));
        }
        target.dependsOn(bbl);

        String[] bibtexExtensions = net.getParameters().getBibtexExtensions();
        for (String arg : args) {
            File file = net.findFile(arg, bibtexExtensions, artifact);
            bbl.dependsOn(net.getArtifact(file));
        }
        Artifact aux = net.getDerivedTargetArtifact("aux");
        aux.provideActions(new LaTeXAction(net.getMaster()));
        bbl.dependsOn(aux);
    }

}
