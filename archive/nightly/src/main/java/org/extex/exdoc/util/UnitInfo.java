/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exdoc.util;

import java.util.ArrayList;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class UnitInfo extends ArrayList<PrimitiveInfo> {

    /**
     * The field {@code serialVersionUID} contains the ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field {@code name} contains the name of the unit.
     */
    private String name;

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

        return name;
    }

}
