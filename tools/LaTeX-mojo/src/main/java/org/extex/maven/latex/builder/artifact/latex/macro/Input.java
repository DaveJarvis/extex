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
import org.extex.maven.latex.builder.artifact.Artifact;
import org.extex.maven.latex.builder.artifact.latex.LatexReader;
import org.extex.maven.latex.builder.artifact.latex.Macro;

/**
 * This class implements a handler for <code>\input</code>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Input extends Macro {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.artifact.latex.Macro#expand(org.extex.maven.latex.builder.artifact.latex.LatexReader,
     *      org.extex.maven.latex.builder.DependencyNet, java.io.File)
     */
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
        File file = net.findFile(arg, ContextKey.LATEX_EXTENSIONS, base);
        Artifact a = net.getArtifact(file);
        Artifact target = net.getTarget();
        target.dependsOn(a);
        net.analyzeLaTeX(a);
    }
}
