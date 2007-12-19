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

package org.extex.exindex.core.type.alphabet;

import java.util.ArrayList;
import java.util.List;

import org.extex.exindex.core.type.page.PageReference;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VarLocationClass implements LocationClass {

    /**
     * This inner class represents a separator.
     */
    private class Seperator implements LocationClass {

        /**
         * The field <tt>sep</tt> contains the separator.
         */
        private String sep;

        /**
         * Creates a new object.
         * 
         * @param sep the separator
         */
        public Seperator(String sep) {

            super();
            this.sep = sep;
        }

        /**
         * Getter for sep.
         * 
         * @return the sep
         */
        public String getSep() {

            return sep;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exindex.core.type.alphabet.LocationClass#match(java.lang.String,
         *      java.lang.String)
         */
        public PageReference match(String encap, String s) {

            return null;
        }

    }

    /**
     * The field <tt>list</tt> contains the ...
     */
    private List<LocationClass> list = new ArrayList<LocationClass>();

    /**
     * Creates a new object.
     */
    public VarLocationClass() {

        super();
    }

    /**
     * Add some location class to the ones already stored.
     * 
     * @param loc the location class to add
     */
    public void add(LocationClass loc) {

        list.add(loc);
    }

    /**
     * Add a separator to the internal list.
     * 
     * @param sep the separator
     */
    public void add(String sep) {

        list.add(new Seperator(sep));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.LocationClass#match(java.lang.String,
     *      java.lang.String)
     */
    public PageReference match(String encap, String s) {

        // TODO gene: match unimplemented
        return null;
    }

}
