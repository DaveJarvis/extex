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

package org.extex.latexParser.impl.exception;

import java.text.MessageFormat;

import org.extex.latexParser.impl.Locator;
import org.extex.scanner.api.exception.ScannerException;

/**
 * This class represents a syntax error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SyntaxError extends ScannerException {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     * 
     * @param locator the locator to acquire the current position from
     * @param message the message format
     * @param args the arguments to be inserted into the format
     */
    public SyntaxError(Locator locator, String message, Object... args) {

        super(locator.getSource() + ":" + Integer.toString(locator.getLineno())
                + ": " + MessageFormat.format(message, args));
    }

}
