/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.type.file;

import java.io.Serializable;

import org.extex.core.exception.helping.HelpingException;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class holds an input file from which tokens can be read.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface InFile extends Serializable {

    /**
     * Close the current stream. No reading operation is possible afterwards.
     */
    void close();

    /**
     * Checks whether this InFile is at end of file.
     * 
     * @return {@code true} iff no further token can be read.
     * 
     * @throws HelpingException in case of an error
     */
    boolean isEof() throws HelpingException;

    /**
     * Check if this stream is attached to a file.
     * 
     * @return {@code true} if this stream reads from a file
     */
    boolean isFileStream();

    /**
     * Check whether this InFile is currently opened for reading.
     * 
     * @return {@code true} iff the input stream has still a stream assigned to
     *         it.
     */
    boolean isOpen();

    /**
     * Getter for standardStream.
     * 
     * @return the standardStream
     */
    boolean isStandardStream();

    /**
     * Read a line of characters into a tokens list.
     * 
     * @param factory the factory to request new tokens from
     * @param tokenizer the tokenizer to use
     * 
     * @return the tokens read or {@code null} in case of eof
     * 
     * @throws HelpingException in case of an error
     */
    Tokens read(TokenFactory factory, Tokenizer tokenizer)
            throws HelpingException;

}
