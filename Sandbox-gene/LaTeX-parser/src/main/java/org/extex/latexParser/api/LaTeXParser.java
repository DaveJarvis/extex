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

package org.extex.latexParser.api;

import java.io.IOException;
import java.io.InputStream;

import org.extex.scanner.api.exception.ScannerException;

/**
 * This interface describes a parser for LaTeX.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface LaTeXParser {

    /**
     * Parse the source and return the syntax tree as list of nodes.
     * 
     * @param stream the input stream
     * @param source the source to read from
     * 
     * @return the syntax tree for the entities read
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    NodeList parse(InputStream stream, String source)
            throws IOException,
                ScannerException;

    /**
     * Parse the source and return the syntax tree as list of nodes.
     * 
     * @param source the source to read from
     * 
     * @return the syntax tree for the entities read
     * 
     * @throws IOException in case of an I/O error
     * @throws ScannerException in case of an error
     */
    NodeList parse(String source) throws IOException, ScannerException;

}
