/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.parameters;

/**
 * This class represents a parameter for bst2groovy.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Parameter {

    /**
     * The field {@code TRUE} contains the boolean value true.
     */
    public static final Parameter TRUE = new Parameter(true);

    /**
     * The field {@code FALSE} contains the boolean value false.
     */
    public static final Parameter FALSE = new Parameter(false);

    /**
     * The field {@code ZERO} contains the numeric value zero.
     */
    public static final Parameter ZERO = new Parameter(0);

    /**
     * The field {@code EMPTY} contains the string value for the empty string.
     */
    public static final Parameter EMPTY = new Parameter("");

    /**
     * The field {@code intValue} contains the value.
     */
    private int intValue;

    /**
     * The field {@code value} contains the value.
     */
    private String stringValue;

    /**
     * The field {@code booleanValue} contains the value.
     */
    private boolean booleanValue;

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Parameter(boolean value) {

        this.booleanValue = value;
        this.intValue = value ? 1 : 0;
        this.stringValue = Boolean.toString(value);
    }

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Parameter(int value) {

        this.intValue = value;
        this.stringValue = Integer.toString(value);
        this.booleanValue = value != 0;
    }

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Parameter(String value) {

        this.stringValue = value;
        this.intValue =
                stringValue.matches("-?[0-9]+")
                        ? Integer.parseInt(stringValue)
                        : 0;
        this.booleanValue = Boolean.parseBoolean(stringValue);
    }

    /**
     * Getter for the boolean value.
     * 
     * @return the boolean value
     */
    public boolean toBoolean() {

        return booleanValue;
    }

    /**
     * Getter for the integer value.
     * 
     * @return the integer value
     */
    public int toInteger() {

        return intValue;
    }

@Override
    public String toString() {

        return stringValue;
    }

}
