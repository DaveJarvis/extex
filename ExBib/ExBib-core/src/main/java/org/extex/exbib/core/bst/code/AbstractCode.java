/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.bst.code;


/**
 * This is a superclass for classes implementing the interface {@link Code}. The
 * handling of the name attribute is located in this class.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractCode implements Code {

    /**
     * Each code has a name. This is the String under which it is registered in
     * the processor context.
     */
    private String name;

    /**
     * Create a new object with the empty string as name.
     */
    public AbstractCode() {

        this("");
    }

    /**
     * Creates a new object.
     * 
     * @param name the name of the code in the processor context
     */
    public AbstractCode(String name) {

        super();
        this.name = name;
    }

    /**
     * Getter for the name. Each code has a name. This is the String under which
     * it is registered in the processor context.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Setter for the name. Each code has a name. This is the String under which
     * it is registered in the processor context.
     * 
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }

}
