/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

/**
 * This class models a pair consisting of a {@link String String} named key and
 * a {@link Value Value} named value.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class KeyValue {

    /**
     * The field <tt>theKey</tt> contains the instance variable containing the
     * key.
     */
    private String theKey;

    /**
     * The field <tt>theValue</tt> contains the instance variable containing
     * the value.
     */
    private Value theValue;

    /**
     * Creates a new object.
     * 
     * @param key the key
     * @param val the value
     */
    public KeyValue(String key, Value val) {

        super();
        theKey = key;
        theValue = val;
    }

    /**
     * Getter for the key
     * 
     * @return the key
     */
    public String getKey() {

        return theKey;
    }

    /**
     * Getter for the value
     * 
     * @return the value
     */
    public Value getValue() {

        return theValue;
    }

}
