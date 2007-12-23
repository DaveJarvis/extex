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

import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

/**
 * This class is a node containing a string.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LString implements LValue {

    /**
     * Check that the argument is an LString and extract the String contained.
     * 
     * @param value the lvalue
     * 
     * @return the string contained
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    public static String stringValue(LValue value)
            throws LNonMatchingTypeException {

        if (!(value instanceof LString)) {
            throw new LNonMatchingTypeException("");
        }

        return ((LString) value).getValue();
    }

    /**
     * The field <tt>value</tt> contains the value.
     */
    private String value;

    /**
     * Creates a new object.
     * 
     * @param value
     */
    public LString(String value) {

        super();
        this.value = value;
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public String getValue() {

        return value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exindex.lisp.type.value.LValue#print(java.io.PrintStream)
     */
    public void print(PrintStream stream) {

        stream.print(toString());
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return toString('\\');
    }

    /**
     * Get the print representation.
     * 
     * @param esc the escape character
     * 
     * @return the print representation
     */
    public String toString(char esc) {

        StringBuilder sb = new StringBuilder();
        sb.append('"');
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"':
                    sb.append(esc);
                    break;
                case '\n':
                    sb.append(esc);
                    c = 'n';
                    break;
                case '\r':
                    sb.append(esc);
                    c = 'r';
                    break;
                case '\t':
                    sb.append(esc);
                    c = 't';
                    break;
            }
            if (c == esc) {
                sb.append(c);
            }
            sb.append(c);
        }
        sb.append('"');
        return sb.toString();
    }

}
