/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.exception;

/**
 * DocumentWriter: clodes channel.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class DocumentWriterClosedChannelException
        extends
            DocumentWriterIOException {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new object.
     *
     * @param message the error message
     */
    public DocumentWriterClosedChannelException(String message) {

        super(message);
    }

    /**
     * Creates a new object.
     *
     * @param message the message
     * @param cause the cause
     */
    public DocumentWriterClosedChannelException(String message,
            Throwable cause) {

        super(message, cause);
    }

    /**
     * Creates a new object.
     *
     * @param cause the cause
     */
    public DocumentWriterClosedChannelException(Throwable cause) {

        super(cause);
    }
}
