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

package org.extex.exindex.core.xparser.raw;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Key {

    /**
     * The field <tt>key</tt> contains the ...
     */
    private String[] key;

    /**
     * The field <tt>print</tt> contains the ...
     */
    private String[] print;

    /**
     * Creates a new object.
     * 
     * @param key the key
     * @param print the print representation
     */
    public Key(String[] key, String[] print) {

        super();
        this.key = key;
        this.print = print;
    }

    /**
     * Getter for key.
     * 
     * @return the key
     */
    public String[] getKey() {

        return key;
    }

    /**
     * Getter for print.
     * 
     * @return the print
     */
    public String[] getPrint() {

        return print;
    }

}
