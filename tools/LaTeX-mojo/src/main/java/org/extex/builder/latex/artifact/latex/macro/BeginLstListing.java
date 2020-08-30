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

import java.io.IOException;

import org.extex.builder.latex.DependencyNet;
import org.extex.builder.latex.artifact.Artifact;
import org.extex.builder.latex.artifact.latex.LatexReader;
import org.extex.builder.latex.artifact.latex.Macro;

/**
 * This class implements a handler for {@code \begin{lstlisting}}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class BeginLstListing extends Macro {

    /**
*      org.extex.builder.latex.DependencyNet, Artifact)
     */
    @Override
    public void expand(LatexReader reader, DependencyNet net, Artifact artifact)
            throws IOException {

        reader.scanTo("\\end{lstlisting}");
    }

}
