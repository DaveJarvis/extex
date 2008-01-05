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

package org.extex.exindex.core.parser;

import java.io.IOException;

import org.extex.exindex.core.exception.ParserException;
import org.extex.exindex.core.exception.RawIndexException;
import org.extex.exindex.lisp.LInterpreter;
import org.extex.resource.ResourceFinder;

/**
 * This interface describes a factory for raw index parsers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface RawIndexParserFactory {

    /**
     * Create a parser for the raw index from a resource name.
     * 
     * @param resource the name of the resource
     * @param charset the name of the character set; a value of
     *        <code>null</code> or the empty string used the platform default
     * @param interpreter the interpreter
     * 
     * @return the parser
     * 
     * @throws RawIndexException in case of an error
     * @throws IOException in case of an I/O error
     * @throws ParserException in case of an error
     */
    RawIndexParser create(String resource, String charset,
            LInterpreter interpreter)
            throws RawIndexException,
                IOException,
                ParserException;

    /**
     * Setter for the resource finder.
     * 
     * @param finder the resource finder
     */
    void setResourceFinder(ResourceFinder finder);

}
