/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex.exception;

/**
 * This exception is raised when a <logo>pdfTeX</logo> encounters an invalid
 * destination type.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InterpreterPdftexDestinationTypeException
        extends
            InterpreterPdftexException {

    /**
     * The field <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * Creates a new object.
     *
     */
    public InterpreterPdftexDestinationTypeException() {

        super();
    }

    /**
     * Creates a new object.
     *
     * @param primitive the name of the primitive in action
     */
    public InterpreterPdftexDestinationTypeException(String primitive) {

        super(primitive);
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return  the detail message string of this <tt>Throwable</tt> instance
     *          (which may be <tt>null</tt>).
     *
     * @see org.extex.unit.pdftex.exception.InterpreterPdftexException#getMessage()
     */
    public String getMessage() {

        return getLocalizer().format("Text", super.getMessage());
    }

}