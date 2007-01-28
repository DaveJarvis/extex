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

import org.extex.interpreter.Namespace;
import org.extex.interpreter.Tokenizer;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.scanner.TokenStream;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.util.framework.configuration.exception.ConfigurationException;

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
    private class TStream implements TokenStream {

        /**
         * The field <tt>cs</tt> contains the char sequence containing the chars
         * to read.
         */
        private CharSequence cs;

        /**
         * The field <tt>next</tt> contains the pointer to the next char to read.
         */
        private int next = 0;

        /**
         * The field <tt>stack</tt> contains the stack for pushed tokens.
         */
        private List stack = new ArrayList();

        /**
         * Creates a new object.
         *
         * @param cs the character sequence to read from
         */
        protected TStream(final CharSequence cs) {

            this.cs = cs;
        }

        /**
         * @see org.extex.scanner.TokenStream#closeFileStream()
         */
        public boolean closeFileStream() {

            next = cs.length();
            return false;
        }

        /**
         * @see org.extex.scanner.TokenStream#get(
         *      org.extex.scanner.type.token.TokenFactory,
         *      org.extex.interpreter.Tokenizer)
         */
        public Token get(final TokenFactory factory, final Tokenizer tokenizer)
                throws ScannerException {

            int size = stack.size();
            if (size > 0) {
                return (Token) stack.remove(size - 1);
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
         * @see org.extex.scanner.TokenStream#getLocator()
         */
        public Locator getLocator() {

            return new Locator("", 0, cs.toString(), next);
        }

        /**
         * @see org.extex.scanner.TokenStream#isEof()
         */
        public boolean isEof() throws ScannerException {

            return next >= cs.length();
        }

        /**
         * @see org.extex.scanner.TokenStream#isEol()
         */
        public boolean isEol() throws ScannerException {

            return isEof();
        }

        /**
         * @see org.extex.scanner.TokenStream#isFileStream()
         */
        public boolean isFileStream() {

            return false;
        }

        /**
         * @see org.extex.scanner.TokenStream#put(
         *      org.extex.scanner.type.token.Token)
         */
        public void put(final Token token) {

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
    public StringSource(final CharSequence cs) throws ConfigurationException {

        super();
        addStream(new TStream(cs));
    }

    /**
     * Reset the input to come from a new source. Any state information is
     * reset to initial values.
     *
     * @param cs the character sequence to read from
     *
     * @throws InterpreterException in case of an error
     */
    public void reset(final CharSequence cs) throws InterpreterException {

        closeAllStreams(getContext());
        addStream(new TStream(cs));
        skipSpace();
    }

}
