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

import org.extex.exindex.lisp.exception.LNonMatchingTypeException;

/**
 * This class is a node containing a string.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LString implements LValue {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param value the lvalue
     * 
     * @return the string contained
     * 
     * @throws LNonMatchingTypeException in case of an error
     */
    public static String getString(LValue value)
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append('"');
        sb.append(value.replaceAll("([\\\"])", "\\\\\\1"));
        // TODO handle \n,...
        sb.append('"');
        return sb.toString();
    }

}
