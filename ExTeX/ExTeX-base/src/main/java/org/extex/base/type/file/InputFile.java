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

package org.extex.base.type.file;

import org.extex.core.exception.helping.NoHelpException;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This class holds an input file from which tokens can be read.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class InputFile implements InFile {

    /**
     * The constant {@code serialVersionUID} contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code standardStream} contains the indicator that the
     * current stream is the standard stream.
     */
    private final boolean standardStream;

    /**
     * The field {@code stream} contains the stream.
     */
    private transient TokenStream stream = null;

    /**
     * Creates a new object.
     * 
     * @param inStream the token stream to read from
     * @param isStandard the stream is the standard input stream
     */
    public InputFile(TokenStream inStream, boolean isStandard) {

        this.stream = inStream;
        this.standardStream = isStandard;
    }

public void close() {

        stream = null;
    }

public boolean isEof() throws NoHelpException {

        try {
            return (stream == null || stream.isEof());
        } catch (ScannerException e) {
            throw new NoHelpException(e);
        }
    }

public boolean isFileStream() {

        return stream != null && this.stream.isFileStream();
    }

public boolean isOpen() {

        return (stream != null);
    }

public boolean isStandardStream() {

        return this.standardStream;
    }

    /**
*      org.extex.scanner.api.Tokenizer)
     */
    public Tokens read(TokenFactory factory, Tokenizer tokenizer)
            throws NoHelpException {

        if (stream == null) {
            return null;
        }

        Tokens toks = new Tokens();
        Token t;

        try {
            for (;;) {
                t = stream.get(factory, tokenizer);
                if (t == null) {
                    return (toks.length() > 0 ? toks : null);
                } else if (stream.isEol()) {
                    return toks;
                }
                toks.add(t);
            }
        } catch (ScannerException e) {
            throw new NoHelpException(e);
        }
    }

}
