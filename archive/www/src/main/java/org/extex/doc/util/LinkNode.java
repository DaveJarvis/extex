/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
*/
public class LinkNode implements DocToken {

    /**
     * The field {@code ref} contains the ...
     */
    private String ref;

    /**
     * The field {@code text} contains the ...
     */
    private String text;

    /**
     * Creates a new object.
     * 
     * @param ref ...
     * @param text ...
     */
    public LinkNode(String ref, String text) {

        this.ref = ref;
        this.text = text;
    }

@Override
    public String toString() {

        return "{@..." + ref + " " + text + "}";
    }

public void visit(TokenVisitor visitor) {

        visitor.visitLink(ref, text);
    }

}
