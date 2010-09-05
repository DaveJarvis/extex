/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.bst2groovy.linker;

import java.io.IOException;

import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This code is added to the linking container.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class LinkingCode implements Comparable<LinkingCode> {

    /**
     * The field <tt>key</tt> contains the key for sorting.
     */
    private String key;

    /**
     * Creates a new object.
     * 
     * @param key the key for sorting
     */
    public LinkingCode(String key) {

        this.key = key;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(LinkingCode o) {

        return key.compareTo(o.key);
    }

    /**
     * Print the linking code.
     * 
     * @param writer the writer
     * @throws IOException in case of an I/O error
     */
    public abstract void print(CodeWriter writer) throws IOException;

}
