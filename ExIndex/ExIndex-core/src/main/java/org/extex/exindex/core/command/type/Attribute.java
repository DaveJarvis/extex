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

package org.extex.exindex.core.command.type;

/**
 * This class represents an attribute description.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Attribute {

    /**
     * The field <tt>name</tt> contains the name.
     */
    private String name;

    /**
     * The field <tt>ord</tt> contains the ordering.
     */
    private int ord;

    /**
     * The field <tt>group</tt> contains the ...
     */
    private int group;

    /**
     * Creates a new object.
     * 
     * @param name the name
     * @param ord the order
     * @param group the attribute group
     */
    public Attribute(String name, int ord, int group) {

        super();
        this.name = name;
        this.ord = ord;
        this.group = group;
    }

    /**
     * Getter for group.
     * 
     * @return the group
     */
    public int getGroup() {

        return group;
    }

    /**
     * Getter for name.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Getter for ord.
     * 
     * @return the ord
     */
    public int getOrd() {

        return ord;
    }

}
