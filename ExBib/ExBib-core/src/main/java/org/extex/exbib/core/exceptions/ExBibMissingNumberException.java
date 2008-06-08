/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.exceptions;

import org.extex.exbib.core.io.Locator;

/**
 * This exception is thrown when a string is missing.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class ExBibMissingNumberException extends ExBibSyntaxException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * Create a new object.
     * 
     * @param found the entity found; <code>null</code> means EOF
     * @param locator the locator
     */
    public ExBibMissingNumberException(String found, Locator locator) {

        super(found, locator);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.exceptions.ExBibException#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        String found = super.getMessage();
        return getLocalizer().format(found != null ? "Message" : "MessageEOF",
            found);
    }

}
