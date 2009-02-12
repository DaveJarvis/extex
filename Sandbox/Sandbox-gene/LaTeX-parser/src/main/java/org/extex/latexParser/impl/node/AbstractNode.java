/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.latexParser.impl.node;

import org.extex.latexParser.api.Node;

/**
 * This abstract base class for nodes takes care of source references.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractNode implements Node {

    /**
     * The field <tt>lineNumber</tt> contains the line number.
     */
    private int lineNumber;

    /**
     * The field <tt>source</tt> contains the source, i.e. the file.
     */
    private String source;

    /**
     * Creates a new object.
     * 
     * @param source the source
     * @param lineNumber the line number
     */
    public AbstractNode(String source, int lineNumber) {

        super();
        this.source = source;
        this.lineNumber = lineNumber;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#getLineNumber()
     */
    public int getLineNumber() {

        return lineNumber;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.latexParser.api.Node#getSource()
     */
    public String getSource() {

        return source;
    }

}
