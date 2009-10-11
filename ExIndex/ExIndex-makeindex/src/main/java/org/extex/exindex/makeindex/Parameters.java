/*
 * Copyright (C) 2007-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex;

import java.util.HashMap;

import org.extex.exindex.lisp.type.value.LChar;
import org.extex.exindex.lisp.type.value.LNumber;
import org.extex.exindex.lisp.type.value.LString;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * This class encapsulates a set of parameters of type int, char, and String.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Parameters extends HashMap<String, LValue> {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2009L;

    /**
     * Getter for a named character parameter.
     * 
     * @param name the name of the parameter
     * 
     * @return the character or 0 if none is defined
     */
    public char getChar(String name) {

        LValue value = get(name);
        if (value instanceof LChar) {
            return ((LChar) value).getValue();
        }
        return 0;
    }

    /**
     * Getter for a named number parameter.
     * 
     * @param name the name of the parameter
     * 
     * @return the number or 0 if not defined or not a number
     */
    public long getNumber(String name) {

        LValue value = get(name);
        if (value instanceof LNumber) {
            return ((LNumber) value).getValue();
        }
        return 0;
    }

    /**
     * Getter for a named String parameter.
     * 
     * @param name the name of the parameter
     * 
     * @return the string or <code>null</code> if not defined
     */
    public String getString(String name) {

        LValue value = get(name);
        if (value instanceof LString) {
            return ((LString) value).getValue();
        }
        if (value != null) {
            return value.toString();
        }
        return null;
    }

    /**
     * Setter for the value.
     * 
     * @param name the name
     * @param value the value
     */
    public void set(String name, LValue value) {

        put(name, value);
    }

}
