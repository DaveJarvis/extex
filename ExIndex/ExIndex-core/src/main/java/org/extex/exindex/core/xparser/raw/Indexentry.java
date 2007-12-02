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
public class Indexentry {

    /**
     * The field <tt>key</tt> contains the ...
     */
    private KeySpec key;

    /**
     * The field <tt>attr</tt> contains the ...
     */
    private String attr;

    /**
     * The field <tt>ref</tt> contains the ...
     */
    private RefSpec ref;

    /**
     * Creates a new object.
     * 
     * @param key
     * @param attr
     * @param ref
     */
    public Indexentry(KeySpec key, String attr, RefSpec ref) {

        super();
        this.key = key;
        this.attr = attr;
        this.ref = ref;
    }

    /**
     * Getter for attr.
     * 
     * @return the attr
     */
    public String getAttr() {

        return attr;
    }

    /**
     * Getter for key.
     * 
     * @return the key
     */
    public KeySpec getKey() {

        return key;
    }

    /**
     * Getter for ref.
     * 
     * @return the ref
     */
    public RefSpec getRef() {

        return ref;
    }

}
