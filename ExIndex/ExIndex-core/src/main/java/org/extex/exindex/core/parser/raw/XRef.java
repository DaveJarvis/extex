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

package org.extex.exindex.core.parser.raw;

import java.util.List;
import java.util.logging.Logger;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6617 $
 */
public class XRef implements RefSpec {

    /**
     * The field <tt>refs</tt> contains the reference list.
     */
    private String[] refs;

    /**
     * Creates a new object.
     * 
     * @param refs the references
     */
    public XRef(String[] refs) {

        this.refs = refs;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.parser.raw.RefSpec#check(java.util.List,
     *      java.util.logging.Logger)
     */
    public boolean check(List<OpenLocRef> openPages, Logger logger) {

        // TODO gene: check unimplemented
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        boolean first = true;
        for (String s : refs) {
            if (first) {
                first = false;
                sb.append("\"");
            } else {
                sb.append(" \"");
            }
            sb.append(s.replaceAll("\\\\", "\\\\").replaceAll("\"", "\\\""));
            sb.append("\"");
        }
        sb.append(")");
        return sb.toString();
    }

}
