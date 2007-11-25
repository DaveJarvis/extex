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

package org.extex.exindex.lisp.type.value;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is a node containing a list of arbitrary values.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LList implements LValue, Iterable<LValue> {

    /**
     * The field <tt>NIL</tt> contains the unmodifiable empty list.
     */
    public static final LList NIL = new LList() {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exindex.lisp.type.value.LList#add(
         *      org.extex.exindex.lisp.type.value.LValue)
         */
        @Override
        public boolean add(LValue o) {

            throw new UnsupportedOperationException();
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {

            return "nil";
        }

    };

    /**
     * The field <tt>content</tt> contains the content.
     */
    List<LValue> content = new ArrayList<LValue>();

    /**
     * Creates a new object.
     */
    public LList() {

        super();
    }

    /**
     * Append a node to the end of the list.
     * 
     * @param o the node
     * 
     * @return <code>true</code>
     */
    public boolean add(LValue o) {

        return content.add(o);
    }

    /**
     * Remove all nodes from the list.
     */
    public void clear() {

        content.clear();
    }

    /**
     * Check if the list contains a certain element.
     * 
     * @param o the node to find
     * 
     * @return <code>true</code> iff the element is found
     */
    public boolean contains(Object o) {

        return content.contains(o);
    }

    /**
     * Get a node at a position.
     * 
     * @param index the index
     * 
     * @return the node at the position
     */
    public LValue get(int index) {

        return content.get(index);
    }

    /**
     * Check whether the list is empty.
     * 
     * @return <code>true</code> iff the list does not contain any element
     */
    public boolean isEmpty() {

        return content.isEmpty();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<LValue> iterator() {

        return content.iterator();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.println(toString());
    }

    /**
     * Remove a node from the list.
     * 
     * @param index the index of the node to remove
     * 
     * @return the node removed
     */
    public LValue remove(int index) {

        return content.remove(index);
    }

    /**
     * Getter for the size of the list.
     * 
     * @return the number of elements contained
     */
    public int size() {

        return content.size();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        if (content.size() == 0) {
            return "nil";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('(');

        for (LValue val : content) {
            sb.append(val.toString());
            sb.append(' ');
        }
        sb.append(')');
        return sb.toString();
    }

}
