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

package org.extex.exindex.lisp.type;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LList implements LValue {

    /**
     * The field <tt>NIL</tt> contains the ...
     */
    public static final LList NIL = new LList() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exindex.lisp.type.LList#add(
         *      org.extex.exindex.lisp.type.LValue)
         */
        @Override
        public boolean add(LValue o) {

            throw new UnsupportedOperationException();
        }

    };

    /**
     * The field <tt>content</tt> contains the ...
     */
    List<LValue> content = new ArrayList<LValue>();

    /**
     * Creates a new object.
     */
    public LList() {

        super();
    }

    /**
     * @param o
     * @return
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean add(LValue o) {

        return content.add(o);
    }

    /**
     * 
     * @see java.util.List#clear()
     */
    public void clear() {

        content.clear();
    }

    /**
     * @param o
     * @return
     * @see java.util.List#contains(java.lang.Object)
     */
    public boolean contains(Object o) {

        return content.contains(o);
    }

    /**
     * @param index
     * @return
     * @see java.util.List#get(int)
     */
    public LValue get(int index) {

        return content.get(index);
    }

    /**
     * @return
     * @see java.util.List#isEmpty()
     */
    public boolean isEmpty() {

        return content.isEmpty();
    }

    /**
     * @param index
     * @return
     * @see java.util.List#remove(int)
     */
    public LValue remove(int index) {

        return content.remove(index);
    }

    /**
     * @return
     * @see java.util.List#size()
     */
    public int size() {

        return content.size();
    }

}
