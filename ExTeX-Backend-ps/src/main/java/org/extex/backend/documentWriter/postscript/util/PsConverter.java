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

package org.extex.backend.documentWriter.postscript.util;

import java.io.IOException;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.typesetter.type.page.Page;

/**
 * This interface describes the ability to translate some nodes into
 * PostScript code.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface PsConverter {

    /**
     * Perform some initializations for each document.
     *
     * @param header the header manager
     *
     * @throws IOException in case of an IO exception
     */
    void init(HeaderManager header) throws IOException;

    /**
     * Translate nodes into PostScript code.
     *
     * @param page the nodes to translate into PostScript code
     * @param fontManager the font manager to inform about characters
     * @param headerManager the container fro the headers
     *
     * @return the bytes representing the current page
     *
     * @throws DocumentWriterException in case of an error
     */
    byte[] toPostScript(Page page, FontManager fontManager,
            HeaderManager headerManager) throws DocumentWriterException;

}