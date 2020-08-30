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

import java.util.HashMap;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class StartNode implements DocToken {

    /**
     * The field {@code name} contains the ...
     */
    private String name;

    /**
     * The field {@code attributes} contains the ...
     */
    private Map<String, String> attributes = new HashMap<String, String>();

    /**
     * Creates a new object.
     * 
     * @param name
     */
    public StartNode(String name) {

        this.name = name;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param att
     * @param value
     */
    public void setAttribute(String att, String value) {

        attributes.put(att, value);
    }

@Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(name);

        for (String k : attributes.keySet()) {
            sb.append(" ");
            sb.append(k);
            sb.append("=\"");
            sb.append(attributes.get(k));
            sb.append("\"");
        }
        sb.append(">");
        return sb.toString();
    }

public void visit(TokenVisitor visitor) {

        visitor.visitStart(this);
    }

}
