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

package org.extex.exindex.core.type.page;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VarPage implements PageReference {

    /**
     * TODO gene: missing JavaDoc.
     */
    private class ConstPageRef implements PageReference {

        /**
         * The field <tt>sep</tt> contains the ...
         */
        private String sep;

        /**
         * Creates a new object.
         * 
         * @param sep
         */
        public ConstPageRef(String sep) {

            super();
            this.sep = sep;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exindex.core.type.page.PageReference#getEncap()
         */
        public String getEncap() {

            return null;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exindex.core.type.page.PageReference#getPage()
         */
        public String getPage() {

            return sep;
        }

    };

    /**
     * The field <tt>list</tt> contains the ...
     */
    private List<PageReference> list = new ArrayList<PageReference>();

    /**
     * Creates a new object.
     */
    public VarPage() {

        super();
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param pr the reference to add
     */
    public void add(PageReference pr) {

        list.add(pr);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param sep the constant separator to add
     */
    public void add(String sep) {

        list.add(new ConstPageRef(sep));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.page.PageReference#getEncap()
     */
    public String getEncap() {

        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.page.PageReference#getPage()
     */
    public String getPage() {

        return null;
    }

}
