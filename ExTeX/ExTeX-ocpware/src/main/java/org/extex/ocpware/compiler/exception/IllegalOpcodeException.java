/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.ocpware.compiler.exception;

/**
 * This exception indicates that an op code of an &Omega;CP instruction has been
 * encountered which is not defined.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class IllegalOpcodeException extends RuntimeException {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field {@code n} contains the number encountered
     */
    private final int n;

    /**
     * Creates a new object.
     * 
     * @param n the number encountered
     */
    public IllegalOpcodeException(int n) {

        this.n = n;
    }

@Override
    public String getMessage() {

        return "Illegal opcode (" + n + ")";
    }

}
