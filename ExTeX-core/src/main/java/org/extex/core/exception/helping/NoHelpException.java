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

package org.extex.core.exception.helping;

import org.extex.framework.i18n.LocalizerFactory;

/**
 * This exception is thrown when a helping exception is required and no help is
 * available. It simply acts as a wrapper for other exceptions.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5586 $
 */
public class NoHelpException extends HelpingException {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param cause the cause
     */
    public NoHelpException(Throwable cause) {

        super(cause);
    }

    /**
     * Creates a new object.
     * 
     * @param message the parameter to be inserted into the test template of
     *        this exception
     */
    public NoHelpException(String message) {

        super(LocalizerFactory.getLocalizer(NoHelpException.class), "Text",
            message);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.core.exception.helping.HelpingException#getLocalizedMessage()
     */
    public String getLocalizedMessage() {

        Throwable e = getCause();
        if (e != null) {
            return e.getLocalizedMessage();
        }
        return super.getLocalizedMessage();
    }

}
