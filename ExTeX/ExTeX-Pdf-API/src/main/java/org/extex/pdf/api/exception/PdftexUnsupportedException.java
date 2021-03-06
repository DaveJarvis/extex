/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.pdf.api.exception;

import org.extex.interpreter.exception.InterpreterException;

/**
 * This exception is raised when a  pdfTeX primitive is used
 * without being in PDF mode.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PdftexUnsupportedException extends InterpreterException {

    /**
     * The field {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     */
    public PdftexUnsupportedException() {

    }

    /**
     * Creates a new object.
     *
     * @param primitive the name of the primitive in action
     */
    public PdftexUnsupportedException(String primitive) {

        super(primitive);
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return  the detail message string of this {@code Throwable} instance
     *          (which may be {@code null}).
     *
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {

        return getLocalizer().format("Text", super.getMessage());
    }

}
