/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.main.tex.exception;

/**
 * This exception is thrown when the main program tries to open an output file
 * and is not able to perform this operation.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MainOutputFileNotFoundException extends MainException {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     *
     * @param filename the name of the file to open
     */
    public MainOutputFileNotFoundException(String filename) {

        super(filename);
    }

    /**
     * Creates a localized description of this throwable.
     *
     * @return  The localized description of this throwable.
     *
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        return getLocalizer().format("MainOutputFileNotFoundException.Message",
            super.getMessage());
    }

}
