/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
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
import org.extex.framework.i18n.Localizer;

/**
 * This Exception is thrown when a syntax error during the parsing of the input
 * or some String has been detected.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibUnexpectedEofException extends ExBibSyntaxException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2008L;

    /**
     * The field <tt>expected</tt> contains the expected characters or
     * <code>null</code> for none.
     */
    private String expected;

    /**
     * Create a new object.
     * 
     * @param found the found entity; it can be <code>null</code> for EOF
     * @param expected the expected characters
     * @param locator the locator
     */
    public ExBibUnexpectedEofException(String found, String expected,
            Locator locator) {

        super(found, locator);
        this.expected = expected;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.exceptions.ExBibException#getLocalizedMessage()
     */
    @Override
    public String getLocalizedMessage() {

        String found = super.getMessage();
        String fmt =
                "Message"
                        + (found == null ? "Eof" : "")
                        + (expected == null ? "None" : expected.length() == 1
                                ? "One"
                                : "");
        Locator locator = getLocator();
        Localizer localizer = getLocalizer();
        return localizer.format(fmt, found, expected) + formatLocator(locator);
    }

}
