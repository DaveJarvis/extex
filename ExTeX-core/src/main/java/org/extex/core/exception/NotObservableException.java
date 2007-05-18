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

package org.extex.core.exception;

/**
 * This Exception is thrown when an Observer for a non-observable action is
 * requested to be registered.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:5417 $
 */
public class NotObservableException extends GeneralException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    private static final long serialVersionUID = 2007L;

    /**
     * Create a new object.
     *
     * @param message the message
     */
    public NotObservableException(String message) {

        super(message);
    }

    /**
     * Create a new object.
     *
     * @param message the message
     * @param cause the chained throwable
     */
    public NotObservableException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * Create a new object.
     *
     * @param cause the cause for this exception
     */
    public NotObservableException(Throwable cause) {

        super(cause);
    }

    /**
     * Provides the message for this Exception.
     *
     * @return the message for this exception
     *
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage() {

        return getLocalizer().format("NotObservableException.Text",
            super.getMessage());
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return  the detail message string of this <tt>Throwable</tt> instance
     *          (which may be <tt>null</tt>).
     *
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {

        return getLocalizedMessage();
    }

}
