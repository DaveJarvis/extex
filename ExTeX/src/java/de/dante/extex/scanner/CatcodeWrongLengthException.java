/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.scanner;



/**
 * This exception is thrown when a value is encountered which has the wrong
 * number of characters in it.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CatcodeWrongLengthException extends CatcodeException {

    /**
     * Creates a new object.
     *
     * @param message the message of the exception
     */
    public CatcodeWrongLengthException(final String message) {
        super(message);
    }

    /**
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {

        return getLocalizer().format("CatcodeWrongLengthException.Text", //
                               super.getMessage());
    }

}
