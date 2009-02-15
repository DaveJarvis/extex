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
import java.io.IOException;

import org.extex.maven.latex.builder.DependencyNet;

/**
 * This abstract base class provides argument parsing. one optional argument and
 * one real argument are recognized. The real argument may be a block or or
 * consists of a single letter.
 */
abstract class MacroWithArgs extends Macro {

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.maven.latex.builder.artifact.latex.Macro#expand(LatexReader,
     *      org.extex.maven.latex.builder.DependencyNet, java.io.File)
     */
    @Override
    public final void expand(LatexReader reader, DependencyNet net, File base)
            throws IOException {

        int c = reader.scanNext();
        String opt = null;
        if (c == '[') {
            reader.unread(c);
            opt = reader.scanOption();
        } else {
            reader.unread(c);
        }
        String arg = reader.scanBlock();
        if (arg == null) {
            return;
        }
        expand(reader, net, base, opt, arg);
    }

    /**
     * Process the macro.
     * 
     * @param reader the reader to consume further input
     * @param net the net
     * @param base the base file
     * @param opt the optional argument or <code>null</code>
     * @param arg the argument
     * 
     * @throws IOException in case of an I/O error
     */
    protected abstract void expand(LatexReader reader, DependencyNet net,
            File base, String opt, String arg) throws IOException;

}
