/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.unit;

import java.io.Serializable;

import org.extex.typesetter.Typesetter;

/**
 * This is the base class for unit infos. Unit infos can be used to specify
 * code to be executing in the course of loading a unit.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class UnitInfo implements Serializable {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2006L;

    /**
     * The field {@code name} contains the the name of the unit.
     */
    private String name;


    public UnitInfo() {

    }

    /**
     * Creates a new object.
     *
     * @param name the name of the unit
     */
    public UnitInfo(String name) {

        this.name = name;
    }

    /**
     * Getter for name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Setter for name.
     *
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Setter for the typesetter.
     *
     * @param typesetter the typesetter
     */
    public void setTypesetter(Typesetter typesetter) {

        // noop
    }

    /**
     * Returns a string representation of the object.
     *
     * @return  a string representation of the object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

         return name;
    }

}
