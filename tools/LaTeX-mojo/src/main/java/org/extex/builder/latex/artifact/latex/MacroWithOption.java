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

import java.io.IOException;

import org.extex.builder.latex.DependencyNet;
import org.extex.builder.latex.Parameters;
import org.extex.builder.latex.artifact.Artifact;

/**
 * This abstract base class provides argument parsing. One optional argument is
 * recognized. The optional argument is enclosed in brackets and must contain
 * balanced braces.
 */
public abstract class MacroWithOption extends Macro {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.builder.latex.artifact.latex.Macro#expand(LatexReader,
     *      org.extex.builder.latex.DependencyNet, Artifact)
     */
    @Override
    public final void expand(LatexReader reader, DependencyNet net,
            Artifact artifact) throws IOException {

        int c = reader.scanNext();
        reader.unread(c);
        String opt = null;
        if (c == '[') {
            opt = reader.scanOption();
        }
        if (opt == null) {
            return;
        }
        expand(reader, net.getParameters(), artifact, opt);
    }

    /**
     * Process the macro.
     * 
     * @param reader the reader to consume further input
     * @param net the net
     * @param artifact the artifact currently processed
     * @param opt the optional argument or <code>null</code>
     * 
     * @throws IOException in case of an I/O error
     */
    protected abstract void expand(LatexReader reader, Parameters net,
            Artifact artifact, String opt) throws IOException;

}
