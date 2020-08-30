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

package org.extex.backend.documentWriter.postscript.converter;

import java.io.IOException;
import java.io.PrintStream;

import org.extex.backend.documentWriter.exception.DocumentWriterException;
import org.extex.backend.documentWriter.postscript.util.FontManager;
import org.extex.backend.documentWriter.postscript.util.HeaderManager;
import org.extex.typesetter.type.page.Page;

/**
 * This interface describes the ability to translate some nodes into PostScript
 * code.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface PsConverter {

    /**
     * Perform some initializations for each document.
     * 
     * @param header the header manager
     * @param fontManager the font manager
     * 
     * @throws IOException in case of an IO exception
     */
    void init(HeaderManager header, FontManager fontManager) throws IOException;

    /**
     * Translate nodes into PostScript code.
     * 
     * @param page the nodes to translate into PostScript code
     * @return the bytes representing the current page
     * 
     * @throws DocumentWriterException in case of an error
     */
    byte[] toPostScript(Page page)
            throws DocumentWriterException;

    /**
     * Write the header to the target stream.
     * 
     * @param out the target stream
     * 
     * @throws IOException in case of an I/O error
     */
    void writeHeaders(PrintStream out) throws IOException;

}
