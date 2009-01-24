/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.data.var;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class VarManager {

    /**
     * The field <tt>no</tt> contains the counter for next items.
     */
    private int no = 1;

    /**
     * The field <tt>localPrefix</tt> contains the prefix for the name of new
     * instances of Var.
     */
    private String localPrefix = "v";

    /**
     * Creates a new object.
     */
    public VarManager(String prefix) {

        localPrefix = prefix;
    }

    /**
     * Create a new local variable.
     * 
     * @return a new local variable
     */
    public Var makeVar() {

        String name = Integer.toString(no);
        return new Var(localPrefix + name, no++);
    }

    /**
     * Reset the internal numbering for generated locals.
     */
    public void reset() {

        no = 1;
    }

    /**
     * Setter for the localPrefix.
     * 
     * @param prefix the localPrefix to set
     */
    public void setLocalPrefix(String prefix) {

        localPrefix = prefix;
    }

}
