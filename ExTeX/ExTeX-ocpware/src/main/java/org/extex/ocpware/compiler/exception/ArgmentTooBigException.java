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

package org.extex.ocpware.compiler.exception;

/**
 * This exception indicates that an argument has been encountered which does not
 * fit into the 24 bits of an &Omega;CP register.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ArgmentTooBigException extends RuntimeException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * The field <tt>n</tt> contains the number encountered
     */
    private int n;

    /**
     * Creates a new object.
     * 
     * @param n the number encountered
     */
    public ArgmentTooBigException(int n) {

        super();
        this.n = n;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {

        return "Argument (" + Integer.toString(n)
                + ") of instruction is too big";
    }

}