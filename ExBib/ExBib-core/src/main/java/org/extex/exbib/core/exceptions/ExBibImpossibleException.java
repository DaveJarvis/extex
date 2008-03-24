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

import org.extex.exbib.core.io.Locator;

/**
 * This ExBibException is thrown in branches of the program which should not be
 * reachable. This means they normally indicate a programming error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.4 $
 */
public class ExBibImpossibleException extends ExBibException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * Create a new object.
     * 
     * @param message the message
     */
    public ExBibImpossibleException(String message) {

        super(message);
    }

    /**
     * Create a new object.
     * 
     * @param message the message
     * @param locator the locator
     */
    public ExBibImpossibleException(String message, Locator locator) {

        super(message, locator);
    }

    /**
     * Create a new object.
     * 
     * @param cause the chained throwable
     */
    public ExBibImpossibleException(Throwable cause) {

        super(cause);
    }

}
