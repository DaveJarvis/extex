/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.type.file;

import java.io.File;
import java.io.Serializable;

import de.dante.extex.interpreter.Tokenizer;
import de.dante.extex.interpreter.type.tokens.Tokens;
import de.dante.extex.scanner.Token;
import de.dante.extex.scanner.TokenFactory;
import de.dante.extex.scanner.stream.TokenStream;
import de.dante.util.GeneralException;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InFile implements Serializable {

    /**
     * The constant <tt>MAX_FILE_NO</tt> contains the maximum number of input
     * files.
     */
    public static final int MAX_FILE_NO = 15;

    /**
     * The field <tt>file</tt> contains name of the underlying file.
     */
    private File file;

    /**
     * The field <tt>stream</tt> contains the stream.
     */
    private TokenStream stream = null;

    /**
     * The field <tt>lookahead</tt> contains the lokk-ahead token.
     */
    private Token lookahead = null;

    /**
     * Creates a new object.
     *
     * @param theFile the name of the file to read
     */
    public InFile(final File theFile) {

        super();
        this.file = theFile;
    }

    /**
     * Creates a new object.
     *
     * @param inStream the token stream to read from
     */
    public InFile(final TokenStream inStream) {

        super();
        this.file = null;
        this.stream = inStream;
    }

    /**
     * Checks whether this InFile is at end of file.
     *
     * @return <code>true</code> iff no further token can be read.
     */
    public boolean ifEof() {

        if (stream == null) {
            return true;
        }
        //TODO incorrect and incomplete
        return (lookahead == null);
    }

    /**
     * Check whether this InFile is curretly opened for reading.
     *
     * @return <tt>true</tt> iff the input stream has still a stream assigned
     * to it.
     */
    public boolean isOpen() {

        return (stream != null);
    }

    /**
     * Close the current stream. No reading operation is possible afterwards.
     */
    public void close() {

        stream = null;
        file = null;
    }

    /**
     * Read a line of characters into a tokens list.
     *
     * @param factory the factory to request new tokens from
     * @param tokenizer the tokenizer to use
     *
     * @return the tokens read
     *
     * @throws GeneralException in case of an error
     */
    public Tokens read(final TokenFactory factory, final Tokenizer tokenizer)
            throws GeneralException {

        if (stream == null) {
            if (file == null) {
                // TODO unimplemented
                throw new RuntimeException("unimplemented");
            } else if (!isOpen()) {
                // TODO unimplemented
                throw new RuntimeException("unimplemented");
            }
        }
        Token t = lookahead;
        lookahead = stream.get(factory, tokenizer);
        //TODO incorrect and incomplete
        return new Tokens(t);
    }

}