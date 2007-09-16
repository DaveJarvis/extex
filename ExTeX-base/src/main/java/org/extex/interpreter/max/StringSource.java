/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.max;

import java.util.ArrayList;
import java.util.List;

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.scanner.TokenStream;
import org.extex.scanner.Tokenizer;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;

/**
 * This class provides a token source which is fed from a string.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4408 $
 */
public class StringSource extends Moritz {

    /**
     * This Token stream is fed from a CharSequence.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4408 $
     */
    private static class TStream implements TokenStream {

        /**
         * The field <tt>cs</tt> contains the char sequence containing the
         * chars to read.
         */
        private CharSequence cs;

        /**
         * The field <tt>next</tt> contains the pointer to the next char to
         * read.
         */
        private int next = 0;

        /**
         * The field <tt>stack</tt> contains the stack for pushed tokens.
         */
        private List<Token> stack = new ArrayList<Token>();

        /**
         * Creates a new object.
         * 
         * @param cs the character sequence to read from
         */
        protected TStream(CharSequence cs) {

            this.cs = cs;
        }

        /**
         * Close this stream if it is a file stream.
         * 
         * @return <code>true</code> if the closing was successful
         * 
         * @see org.extex.scanner.TokenStream#closeFileStream()
         */
        public boolean closeFileStream() {

            next = cs.length();
            return false;
        }

        /**
         * Get the next token from the token stream. If tokens are on the
         * push-back stack then those are delivered otherwise new tokens might
         * be extracted utilizing the token factory and the tokenizer.
         * 
         * @param factory the token factory
         * @param tokenizer the tokenizer
         * 
         * @return the next Token or <code>null</code> if no more tokens are
         *         available
         * 
         * @throws ScannerException in case of an error
         * 
         * @see org.extex.scanner.TokenStream#get(
         *      org.extex.scanner.type.token.TokenFactory,
         *      org.extex.scanner.Tokenizer)
         */
        public Token get(TokenFactory factory, Tokenizer tokenizer)
                throws ScannerException {

            int size = stack.size();
            if (size > 0) {
                return stack.remove(size - 1);
            }
            if (next < cs.length()) {
                UnicodeChar c = UnicodeChar.get(cs.charAt(next++));
                try {
                    return factory.createToken(tokenizer.getCatcode(c), c,
                        Namespace.DEFAULT_NAMESPACE);
                } catch (CatcodeException e) {
                    throw new ScannerException(e);
                }
            }
            return null;
        }

        /**
         * Getter for the locator. The locator describes the place the tokens
         * have been read from in terms of the user. This information is meant
         * for the end user to track down problems.
         * 
         * @return the locator
         * 
         * @see org.extex.scanner.TokenStream#getLocator()
         */
        public Locator getLocator() {

            return new Locator("", 0, cs.toString(), next);
        }

        /**
         * Check to see if a further token can be acquired from the token
         * stream.
         * 
         * @return <code>true</code> if the stream is at its end
         * 
         * @throws ScannerException in case that an error has been encountered.
         *         Especially if an IO exceptions occurs it is delivered as
         *         chained exception in a ScannerException.
         * 
         * @see org.extex.scanner.TokenStream#isEof()
         */
        public boolean isEof() throws ScannerException {

            return next >= cs.length();
        }

        /**
         * Check to see if the token stream is currently at the end of line.
         * 
         * @return <code>true</code> if the stream is at end of line
         * 
         * @throws ScannerException in case that an error has been encountered.
         *         Especially if an IO exceptions occurs it is delivered as
         *         chained exception in a ScannerException.
         * 
         * @see org.extex.scanner.TokenStream#isEol()
         */
        public boolean isEol() throws ScannerException {

            return isEof();
        }

        /**
         * Check whether the current stream is associated with a file to read
         * from.
         * 
         * @return <code>true</code> if the stream is a file stream
         * 
         * @see org.extex.scanner.TokenStream#isFileStream()
         */
        public boolean isFileStream() {

            return false;
        }

        /**
         * Push back a token into the stream. If the token is <code>null</code>
         * then nothing happens: a <code>null</code> token is not pushed!
         * <p>
         * Note that it is up to the implementation to accept tokens not
         * produced with the token factory for push back. In general the
         * behavior in such a case is not defined and should be avoided.
         * </p>
         * 
         * @param token the token to push back
         * 
         * @see org.extex.scanner.TokenStream#put(
         *      org.extex.scanner.type.token.Token)
         */
        public void put(Token token) {

            stack.add(token);
        }

    }

    /**
     * Creates a new object.
     */
    public StringSource() {

        super();
    }

    /**
     * Creates a new object.
     * 
     * @param cs the character sequence to read from
     * 
     * @throws ConfigurationException in case of errors in the configuration
     */
    public StringSource(CharSequence cs) throws ConfigurationException {

        super();
        addStream(new TStream(cs));
    }

    /**
     * Reset the input to come from a new source. Any state information is reset
     * to initial values.
     * 
     * @param cs the character sequence to read from
     * 
     * @throws HelpingException in case of an error
     */
    public void reset(CharSequence cs) throws HelpingException {

        closeAllStreams(getContext());
        addStream(new TStream(cs));
        skipSpace();
    }

}
