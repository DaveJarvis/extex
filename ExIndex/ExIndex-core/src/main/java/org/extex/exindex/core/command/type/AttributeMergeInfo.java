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
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class AttributeMergeInfo {

    /**
     * The field <tt>attribute</tt> contains the attribute.
     */
    private String attribute;

    /**
     * The field <tt>drop</tt> contains the drop indicator.
     */
    private boolean drop;

    /**
     * Creates a new object.
     * 
     * @param attribute
     * @param drop
     */
    public AttributeMergeInfo(String attribute, boolean drop) {

        super();
        this.attribute = attribute;
        this.drop = drop;
    }

    /**
     * Getter for attribute.
     * 
     * @return the attribute
     */
    public String getAttribute() {

        return attribute;
    }

    /**
     * Getter for drop.
     * 
     * @return the drop
     */
    public boolean isDrop() {

        return drop;
    }

}
