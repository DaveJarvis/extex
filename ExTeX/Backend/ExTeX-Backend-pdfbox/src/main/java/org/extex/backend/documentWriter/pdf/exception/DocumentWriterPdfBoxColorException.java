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

package org.extex.backend.documentWriter.pdf.exception;

/**
 * Exception for the ColorAdapter.
 * It is use, if a error occurs, when the color is set in the pdf content.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

public class DocumentWriterPdfBoxColorException
        extends
            DocumentWriterPdfException {

    /**
     * The field {@code serialVersionUID} ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new object.
     * @param message The message.
     */
    public DocumentWriterPdfBoxColorException(String message) {

        super(message);
    }

    /**
     * Create a new object.
     * @param message   The message.
     * @param cause     The cuase.
     */
    public DocumentWriterPdfBoxColorException(String message,
            Throwable cause) {

        super(message, cause);
    }

    /**
     * Create a new object.
     * @param cause The cause.
     */
    public DocumentWriterPdfBoxColorException(Throwable cause) {

        super(cause);
    }

}
