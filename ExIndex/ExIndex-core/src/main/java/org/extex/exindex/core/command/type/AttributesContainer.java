/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
 * This interface provides reading access to a container of defined attributes.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface AttributesContainer {

    /**
     * Define an attribute
     * 
     * @param attribute the name of the attribute
     * @param attributeData the information
     */
    void defineAttribute(String attribute, Attribute attributeData);

    /**
     * Check whether an attribute is defined.
     * 
     * @param attribute the name of the attribute
     * 
     * @return <code>true</code> iff the attribute is defined
     */
    boolean isAttributeDefined(String attribute);

    /**
     * Getter for a named attribute
     * 
     * @param attribute the name of the attribute
     * 
     * @return the named attribute or <code>null</code> if not defined
     */
    Attribute lookupAttribute(String attribute);

}
