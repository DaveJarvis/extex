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

package org.extex.doc.util;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ConstantNode implements DocToken {

    /**
     * The field <tt>s</tt> contains the ...
     */
    private String s;

    /**
     * Creates a new object.
     * 
     * @param s the content
     */
    public ConstantNode(String s) {

        super();
        this.s = s;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return s;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.doc.util.DocToken#visit(org.extex.doc.util.TokenVisitor)
     */
    public void visit(TokenVisitor visitor) {

        visitor.visitString(s);
    }

}
