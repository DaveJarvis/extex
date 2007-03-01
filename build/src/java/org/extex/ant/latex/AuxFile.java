/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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
package org.extex.ant.latex;

import java.io.File;

/**
 * ...
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class AuxFile {

    /**
     * The field <tt>name</tt> contains the ...
     */
    private String name;

    /**
     * Creates a new object.
     * 
     */
    public AuxFile(final String name) {
        super();
        
        this.name = name.replaceAll("\\.[a-zA-Z0-9_]$", "") + ".aux";
    }

    /**
     * ...
     * 
     * @return
     */
    public boolean canRead() {

        return new File(name).canRead();

    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return name;
    }

    /**
     * ...
     * 
     * @param visitor
     * @return
     */
    public boolean redo(final AuxVisitor visitor) {
        // TODO Auto-generated method stub
        return false;
    }


}
