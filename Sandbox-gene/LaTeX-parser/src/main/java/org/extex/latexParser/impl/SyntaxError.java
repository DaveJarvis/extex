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

package org.extex.latexParser.impl;

import org.extex.scanner.api.exception.ScannerException;

/**
 * This class represents a syntax error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SyntaxError extends ScannerException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>lineNo</tt> contains the line number.
     */
    private int lineNumber = -1;

    /**
     * Creates a new object.
     * 
     */
    public SyntaxError() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param message the message
     */
    public SyntaxError(String message) {

        super(message);
    }

    /**
     * Creates a new object.
     * 
     * @param message the message
     * @param cause the cause exception
     */
    public SyntaxError(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Getter for the line number.
     * 
     * @return the line number
     */
    public int getLineNumber() {

        return lineNumber;
    }

    /**
     * Setter for the line number.
     * 
     * @param lineNumber the line number
     */
    public void setLineNumber(int lineNumber) {

        this.lineNumber = lineNumber;
    }

}
