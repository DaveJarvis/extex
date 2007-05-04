/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.exception;

import org.extex.interpreter.exception.helping.HelpingException;

/**
 * This exception s thrown when the error count exceeds the given limit.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ErrorLimitException extends HelpingException {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * The field <tt>limit</tt> contains the maximal allowed number of errors.
     */
    private long limit;

    /**
     * Creates a new object.
     *
     * @param limit the maximal allowed number of errors
     */
    public ErrorLimitException(long limit) {

        super();
        this.limit = limit;
    }

    /**
     * Creates a localized description of this throwable.
     *
     * @return the localized description of this throwable
     *
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage() {

        return getLocalizer().format("TTP.ErrorLimitReached",
            Long.toString(limit));
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return  the detail message string of this <tt>Throwable</tt> instance
     *          (which may be <tt>null</tt>).
     *
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {

        return getLocalizedMessage();
    }

}
