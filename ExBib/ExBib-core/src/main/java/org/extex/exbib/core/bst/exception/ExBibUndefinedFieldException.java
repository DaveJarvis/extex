/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.exception;

import org.extex.exbib.core.io.Locator;

/**
 * This Exception is thrown when an undefined field has been used.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ExBibUndefinedFieldException extends ExBibIllegalValueException {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field {@code entry} contains the name of the entry.
     */
    private final String entry;

    /**
     * Create a new object.
     * 
     * @param message the message
     * @param entry the name of the entry
     * @param locator the locator
     */
    public ExBibUndefinedFieldException(String message, String entry,
            Locator locator) {

        super(message, locator);
        this.entry = entry;
    }

@Override
    public String getLocalizedMessage() {

        return getLocalizer().format("Message", super.getMessage(), entry);
    }

}
