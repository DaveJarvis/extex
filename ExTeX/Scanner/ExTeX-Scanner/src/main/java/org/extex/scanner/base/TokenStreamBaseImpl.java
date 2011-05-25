/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.base;

import org.extex.core.Locator;
import org.extex.scanner.api.TokenStream;
import org.extex.scanner.api.Tokenizer;
import org.extex.scanner.api.exception.ScannerException;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;

/**
 * This is the base implementation of a token stream. It has an internal stack
 * of tokens which can be enlarged with push() or reduced with pop().
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4725 $
 */
public class TokenStreamBaseImpl implements TokenStream {

    /**
     * The field <tt>fileStream</tt> contains the indicator whether or not this
     * TokenStream is attached to a file.
     */
    private boolean fileStream;

    /**
     * The field <tt>skipSpaces</tt> contains the indicator that spaces should
     * be ignored before the next token is delivered.
     */
    private boolean skipSpaces = false;

    /**
     * The field <tt>stack</tt> contains the Token stack for the push-back
     * operation.
     */
    private Tokens stack = new Tokens();

    /**
     * Creates a new object.
     * 
     * @param isFile indicator whether or not the token stream is assigned to a
     *        file
     */
    public TokenStreamBaseImpl(boolean isFile) {

        this.fileStream = isFile;
    }

    /**
     * Creates a new object.
     * 
     * @param isFile indicator whether or not the token stream is assigned to a
     *        file
     * @param tokens the tokens to push to the stream initially
     */
    public TokenStreamBaseImpl(boolean isFile, Tokens tokens) {

        this.fileStream = isFile;

        for (int i = tokens.length() - 1; i >= 0; i--) {
            stack.add(tokens.get(i));
        }
    }

    /**
     * Close this stream if it is a file stream.
     * 
     * @return <code>true</code> if the closing was successful
     * 
     * @see org.extex.scanner.api.TokenStream#closeFileStream()
     */
    @Override
    public boolean closeFileStream() {

        stack = new Tokens();
        return fileStream;
    }

    /**
     * Get the next token from the token stream. If tokens are on the push-back
     * stack then those are delivered otherwise new tokens might be extracted
     * utilizing the token factory and the tokenizer.
     * 
     * @param factory the token factory
     * @param tokenizer the tokenizer
     * 
     * @return the next Token or <code>null</code> if no more tokens are
     *         available
     * 
     * @throws ScannerException in case of an error
     * 
     * @see org.extex.scanner.api.TokenStream#get(org.extex.scanner.type.token.TokenFactory,
     *      org.extex.scanner.api.Tokenizer)
     */
    @Override
    public Token get(TokenFactory factory, Tokenizer tokenizer)
            throws ScannerException {

        if (!skipSpaces) {
            return (stack.length() > 0 ? //
                    stack.removeLast()
                    : //
                    getNext(factory, tokenizer));
        }
        Token t;

        for (t = stack.removeLast(); t != null; t = stack.removeLast()) {
            if (!(t instanceof SpaceToken)) {
                return t;
            }
        }

        do {
            t = getNext(factory, tokenizer);
        } while (t instanceof SpaceToken);

        return t;
    }

    /**
     * Getter for the locator. The locator describes the place the tokens have
     * been read from in terms of the user. This information is meant for the
     * end user to track down problems.
     * 
     * @return the locator
     * 
     * @see org.extex.scanner.api.TokenStream#getLocator()
     */
    @Override
    public Locator getLocator() {

        return new Locator(null, 0, null, 0);
    }

    /**
     * Get the next token when the stack is empty. This method is meant to be
     * overloaded by derived classes.
     * 
     * @param factory the factory for new tokens
     * @param tokenizer the classifies for characters
     * 
     * @return the next Token or <code>null</code>
     * @throws ScannerException in case of an error
     */
    protected Token getNext(TokenFactory factory, Tokenizer tokenizer)
            throws ScannerException {

        return null;
    }

    /**
     * Test for end of file.
     * 
     * @return <code>true</code> iff the stream is at its end
     * 
     * @throws ScannerException in case of an error
     */
    @Override
    public boolean isEof() throws ScannerException {

        return (stack.length() == 0);
    }

    /**
     * Check to see if the token stream is currently at the end of line.
     * 
     * @return <code>true</code> if the stream is at end of line
     * 
     * @see org.extex.scanner.api.TokenStream#isEol()
     */
    @Override
    public boolean isEol() throws ScannerException {

        return (stack.length() == 0);
    }

    /**
     * Check whether the current stream is associated with a file to read from.
     * 
     * @return <code>true</code> if the stream is a file stream
     * 
     * @see org.extex.scanner.api.TokenStream#isFileStream()
     */
    @Override
    public boolean isFileStream() {

        return fileStream;
    }

    /**
     * Push back a token into the stream. If the token is <code>null</code> then
     * nothing happens: a <code>null</code> token is not pushed!
     * <p>
     * Note that it is up to the implementation to accept tokens not produced
     * with the token factory for push back. In general the behavior in such a
     * case is not defined and should be avoided.
     * </p>
     * 
     * @param token the token to push back
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [325]"
     * 
     * @see org.extex.scanner.api.TokenStream#put(org.extex.scanner.type.token.Token)
     */
    @Override
    public void put(Token token) {

        if (token != null) {
            stack.add(token);
        }
    }

    /**
     * Setter for skipSpaces.
     */
    public void skipSpaces() {

        this.skipSpaces = true;
    }

}
