/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core;

/**
 * This class provides a modifiable boolean object. In contrast to the class
 * {@link java.lang.Boolean Boolean} this class has also a setter for the
 * encapsulated boolean value.
 * <p>
 * This class cures the deficiency of Java to provide booleans as first-class
 * objects.
 * </p>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5417 $
 */
public class Switch {

    /**
     * The field <tt>value</tt> contains the encapsulated boolean value.
     */
    private boolean value;

    /**
     * Creates a new object with a given boolean value.
     * 
     * @param on the initial value
     */
    public Switch(boolean on) {

        super();
        this.value = on;
    }

    /**
     * Getter for value.
     * 
     * @return the value.
     */
    public boolean isOn() {

        return value;
    }

    /**
     * Setter for value.
     * 
     * @param on the value to set.
     */
    public void set(boolean on) {

        this.value = on;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return this.value ? "on" : "off";
    }

}
