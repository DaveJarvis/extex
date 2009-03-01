/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.normalizer;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6622 $
 */
public class MakeindexGermanCollator implements Collator {

    /**
     * The field <tt>collateSpaces</tt> contains the indicator for collating
     * spaces.
     */
    private boolean collateSpaces;

    /**
     * Creates a new object.
     * 
     * @param collateSpaces the indicator to collate spaces
     */
    public MakeindexGermanCollator(boolean collateSpaces) {

        this.collateSpaces = collateSpaces;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.makeindex.normalizer.Collator#collate(java.lang.String)
     */
    public String collate(String in) {

        String s = in;
        if (collateSpaces) {
            s = s.replaceAll("[ \t\n\r\f\b]", "");
        }
        return s.replaceAll("[\\\\]?\"[aA]", "ae").replaceAll("[\\\\]?\"[oO]",
            "oe").replaceAll("[\\\\]?\"[uU]", "ue").replaceAll("\"s", "ss")
            .replaceAll("\\\\ss ", "ss ");
    }
}
