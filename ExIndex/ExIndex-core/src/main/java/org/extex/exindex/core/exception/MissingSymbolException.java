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

package org.extex.exindex.core.exception;


/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MissingSymbolException extends RawIndexException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource or <code>null</code>
     * @param line the line number
     */
    public MissingSymbolException(String resource, int line) {

        super(resource, Integer.toString(line));
    }

    /**
     * Creates a new object.
     * 
     * @param resource the name of the resource or <code>null</code>
     * @param line the line number
     * @param c the character found instead
     */
    public MissingSymbolException(String resource, int line, char c) {

        super(resource, Integer.toString(line), Character.toString(c));
    }

}
