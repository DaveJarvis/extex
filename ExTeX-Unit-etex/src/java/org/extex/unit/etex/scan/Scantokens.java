/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.scan;

import org.extex.core.Locator;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.scanner.TokenStream;
import org.extex.scanner.Tokenizer;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.stream.TokenStreamFactory;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an implementation for the primitive
 * <code>\scantokens</code>.
 *
 * <doc name="scantokens">
 * <h3>The Primitive <tt>\scantokens</tt></h3>
 * <p>
 *  The primitive <tt>\scantokens</tt> takes an unexpanded list of tokens and
 *  uses them as a new source for an input stream. For this purpose the tokens
 *  are translated into a string which is used as if it where written to a file
 *  and read back in.
 * </p>
 * <p>
 *  The tokens from the tokens register <tt>\everyeof</tt> are inserted when the
 *  stream has no more tokens to read.
 * </p>
 *
 * <h4>Syntax</h4>
 *  The formal description of this primitive is the following:
 *  <pre class="syntax">
 *    &lang;scantokens&rang;
 *      &rarr; <tt>\scantokens</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;}  </pre>
 *
 * <h4>Examples</h4>
 *  <pre class="TeXSample">
 *    \scantokens{abc}  </pre>
 *
 * </doc>
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4435 $
 */
public class Scantokens extends AbstractCode implements ExpandableCode {

    /**
     * This class encapsulates a Token stream pretending that it is a file
     * stream.
     *
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:4435 $
     */
    private static class TokenStreamProxy implements TokenStream {

        /**
         * The field <tt>stream</tt> contains the proxied token stream.
         */
        private TokenStream stream;

        /**
         * Creates a new object.
         *
         * @param stream the stream
         */
        public TokenStreamProxy(final TokenStream stream) {

            super();
            this.stream = stream;
        }

        /**
         * Close this stream if it is a file stream.
         *
         * @return <code>true</code> if the closing was successful
         *
         * @see org.extex.scanner.TokenStream#closeFileStream()
         */
        public boolean closeFileStream() {

            stream.closeFileStream();
            return false;
        }

        /**
         * Get the next token from the token stream.
         * If tokens are on the push-back stack then those are delivered otherwise
         * new tokens might be extracted utilizing the token factory and the
         * tokenizer.
         *
         * @param factory the token factory
         * @param tokenizer the tokenizer
         *
         * @return the next Token or <code>null</code> if no more tokens are
         * available
         *
         * @throws ScannerException in case of an error
         *
         * @see org.extex.scanner.TokenStream#get(
         *      org.extex.scanner.type.token.TokenFactory,
         *      org.extex.scanner.Tokenizer)
         */
        public Token get(final TokenFactory factory, final Tokenizer tokenizer)
                throws ScannerException {

            return stream.get(factory, tokenizer);
        }

        /**
         * @see org.extex.scanner.TokenStream#getLocator()
         */
        public Locator getLocator() {

            return stream.getLocator();
        }

        /**
         * @see org.extex.scanner.TokenStream#isEof()
         */
        public boolean isEof() throws ScannerException {

            return stream.isEof();
        }

        /**
         * @see org.extex.scanner.TokenStream#isEol()
         */
        public boolean isEol() throws ScannerException {

            return stream.isEol();
        }

        /**
         * @see org.extex.scanner.TokenStream#isFileStream()
         */
        public boolean isFileStream() {

            return true;
        }

        /**
         * @see org.extex.scanner.TokenStream#put(
         *      org.extex.scanner.type.token.Token)
         */
        public void put(final Token token) {

            stream.put(token);
        }

    }

    /**
     * The field <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 04022007L;

    /**
     * Creates a new object.
     *
     * @param codeName the name of the primitive for debugging
     */
    public Scantokens(final String codeName) {

        super(codeName);
    }

    /**
     * This method takes the first token and executes it. The result is placed
     * on the stack. This operation might have side effects. To execute a token
     * it might be necessary to consume further tokens.
     *
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.AbstractCode#execute(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void execute(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException,
                ConfigurationException {

        Tokens tokens;
        try {
            tokens = source.getTokens(context, source, typesetter);
        } catch (EofException e) {
            throw new EofInToksException(printableControlSequence(context));
        }
        TokenStreamFactory factory = source.getTokenStreamFactory();
        String t = tokens.toText(context.escapechar());
        source.addStream(new TokenStreamProxy(factory.newInstance(t)));
    }

    /**
     * This method takes the first token and expands it. The result is placed
     * on the stack.
     * This means that expandable code does one step of expansion and puts the
     * result on the stack. To expand a token it might be necessary to consume
     * further tokens.
     *
     * @param prefix the prefix flags controlling the expansion
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     * @throws ConfigurationException in case of an configuration error
     *
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(final Flags prefix, final Context context,
            final TokenSource source, final Typesetter typesetter)
            throws InterpreterException,
                ConfigurationException {

        execute(prefix, context, source, typesetter);
    }

}
