/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EntryRefernce {

    /**
     * The field <tt>name</tt> contains the the name of the entry.
     */
    private String name;

    /**
     * The field <tt>used</tt> contains the the indicator that the entry is
     * used.
     */
    private boolean used = false;

    /**
     * Creates a new object.
     * 
     * @param name the name of the reference
     */
    public EntryRefernce(String name) {

        super();
        this.name = name;
    }

    /**
     * Getter for the name.
     * 
     * @return the name
     */
    public String getName() {

        used = true;
        return name;
    }

    /**
     * Getter for the used.
     * 
     * @return the used
     */
    public boolean isUsed() {

        return used;
    }

}
