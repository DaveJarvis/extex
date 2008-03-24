/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
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

package org.extex.exbib.core.exceptions;

import org.extex.exbib.core.i18n.Messages;
import org.extex.exbib.core.io.Locator;


/**
 * This Exception is thrown when a syntax error during the parsing of the input
 * or some String has been detected.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class ExBibSyntaxException extends ExBibException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * Create a new object.
     */
    public ExBibSyntaxException() {

        super();
    }

    /**
     * Create a new object.
     * 
     * @param message the message
     */
    public ExBibSyntaxException(String message) {

        super(message);
    }

    /**
     * Create a new object.
     * 
     * @param message the message
     * @param locator the locator
     */
    public ExBibSyntaxException(String message, Locator locator) {

        super(message, locator);
    }

    /**
     * Create a new object.
     * 
     * @param message the message
     * @param cause the chained Throwable
     */
    public ExBibSyntaxException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Create a new object.
     * 
     * @param cause the chained Throwable
     */
    public ExBibSyntaxException(Throwable cause) {

        super(cause);
    }

    /**
     * Provides the message for this Exception.
     * 
     * @return the message for this exception
     */
    @Override
    public String getMessage() {

        return Messages
            .format("ExBibSyntaxException.Message", super.getMessage());
    }
}
