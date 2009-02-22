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
import java.util.logging.Logger;

import org.extex.maven.latex.builder.DependencyNet;
import org.extex.maven.latex.builder.Message;
import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.artifact.latex.LatexReader;
import org.extex.maven.latex.builder.artifact.latex.MacroWithArgs;

/**
 * This class implements a handler for <code>&#x5c;usepackage</code>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class UsePackage extends MacroWithArgs {

    /**
     * The field <tt>PACKAGES</tt> contains the known packages.
     */
    private static final String[] PACKAGES = new String[]{//
            "fontenc", //
                    "inputenc", //
                    "includegraphics", //
                    "includegraphicx", //
                    "listings", //
                    "makeindx", //
                    "verbatim"};

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.artifact.latex.MacroWithArgs#expand(org.extex.maven.latex.builder.artifact.latex.LatexReader,
     *      org.extex.maven.latex.builder.DependencyNet,
     *      org.extex.maven.latex.builder.artifact.Artifact, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void expand(LatexReader reader, DependencyNet net,
            Artifact artifact, String opt, String arg) throws IOException {

        for (String p : arg.split(",")) {
            p = p.trim();
            File f = net.searchFile(arg, //
                net.getParameters().getLatexExtensions(), artifact.getFile());
            if (f == null) {
                warn(p, net.getLogger());
            } else {
                Artifact a = new Artifact(f);
                net.addArtifact(a);
                net.getTarget().dependsOn(a);

                net.setAtLetter(true);
                net.analyzeLaTeX(a);
                net.setAtLetter(false);
            }
        }
    }

    /**
     * Possibly issue a warning that the package is knot known.
     * 
     * @param p the name of the package
     * @param logger the logger
     */
    private void warn(String p, Logger logger) {

        for (String s : PACKAGES) {
            if (p.equals(s)) {
                return;
            }
        }
        logger.info(Message.get("usepackage.ignored", p));
        return;
    }

}
