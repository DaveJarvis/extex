/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

import java.io.PrintStream;

import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.page.SomePage;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class contains the finite set of elements as a list.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ListAlphabet implements LValue, Alphabet, LocationClass {

    /**
     * The field <tt>list</tt> contains the list of words.
     */
    private String[] list;

    /**
     * Creates a new object.
     * 
     * @param list the list of words
     */
    public ListAlphabet(String[] list) {

        super();
        this.list = list;
    }

    /**
     * Getter for list.
     * 
     * @return the list
     */
    public String[] getList() {

        return list;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.LocationClass#match(java.lang.String,
     *      java.lang.String)
     */
    public PageReference match(String encap, String s) {

        for (String x : list) {
            if (!x.equals(s)) {
                return null;
            }
        }
        return new SomePage(encap, s);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.core.type.alphabet.LocationClass#match(java.lang.StringBuilder)
     */
    public boolean match(StringBuilder s) {

        for (String x : list) {
            if (match(s, x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Match a string at all positions in a string builder and delete all
     * characters if a match is found.
     * 
     * @param s the string builder
     * @param x the pattern
     * 
     * @return <code>true</code> if a match has been found
     */
    private boolean match(StringBuilder s, String x) {

        int j = 0;
        for (int i = 0; i < x.length(); i++) {
            if (s.length() == 0 || s.charAt(j) != x.charAt(i)) {
                return false;
            }
            j++;
        }
        s.delete(0, j);
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print("#alphabet(");
        for (String a : list) {
            stream.print("\"");
            stream.print(a);
            stream.print("\" ");
        }
        stream.print(")");
    }

}
