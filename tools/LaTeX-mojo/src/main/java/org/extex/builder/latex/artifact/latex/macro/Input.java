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
import org.extex.builder.latex.artifact.Artifact;
import org.extex.builder.latex.artifact.latex.LatexReader;
import org.extex.builder.latex.artifact.latex.Macro;

/**
 * This class implements a handler for {@code \input}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class Input extends Macro {

    /**
*      org.extex.builder.latex.DependencyNet, Artifact)
     */
    @Override
    public void expand(LatexReader reader, DependencyNet net, Artifact artifact)
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

        File file =
                net.findFile(arg, net.getParameters().getLatexExtensions(),
                    artifact);
        Artifact a = net.getArtifact(file);
        Artifact target = net.getTarget();
        target.dependsOn(a);
        net.analyzeLaTeX(a);
    }
}
