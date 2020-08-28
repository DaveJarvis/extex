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

package org.extex.exindex.core.type.alphabet;

import org.extex.exindex.core.type.page.PageReference;

/**
 * This class contains a cross-reference location class.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CrossreferenceLocationClass implements LocationClass {

    /**
     * The field <tt>verified</tt> contains the indicator for unverified cross
     * references.
     */
    private boolean unverified;

    /**
     * Creates a new object.
     * 
     * @param unverified the indicator for unverified cross-references
     */
    public CrossreferenceLocationClass(boolean unverified) {

        this.unverified = unverified;
    }

    /**
     * Getter for verified.
     * 
     * @return the verified
     */
    public boolean isUnverified() {

        return unverified;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.LocationClass#match(java.lang.String,
     *      String)
     */
    public PageReference match(String encap, String text) {

        // return new CrossReference(new String[] {text}, layer);
        // TODO gene: match unimplemented
        throw new RuntimeException("unimplemented");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.LocationClass#match(java.lang.StringBuilder)
     */
    public boolean match(StringBuilder s) {

        // TODO gene: match unimplemented
        throw new RuntimeException("unimplemented");
    }

}
