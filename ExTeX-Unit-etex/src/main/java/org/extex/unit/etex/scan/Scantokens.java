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
import org.extex.interpreter.exception.helping.EofException;
import org.extex.interpreter.exception.helping.EofInToksException;
import org.extex.interpreter.exception.helping.HelpingException;
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
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\scantokens</code>.
 * 
 * <doc name="scantokens">
 * <h3>The Primitive <tt>\scantokens</tt></h3>
 * <p>
 * The primitive <tt>\scantokens</tt> takes an unexpanded list of tokens and
 * uses them as a new source for an input stream. For this purpose the tokens
 * are translated into a string which is used as if it where written to a file
 * and read back in.
 * </p>
 * <p>
 * The tokens from the tokens register <tt>\everyeof</tt> are inserted when
 * the stream has no more tokens to read.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;scantokens&rang;
 *      &rarr; <tt>\scantokens</tt> {@linkplain
 *        org.extex.interpreter.TokenSource#getTokens(Context,TokenSource,Typesetter)
 *        &lang;tokens&rang;}  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
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
        public TokenStreamProxy(TokenStream stream) {

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
        public void put(Token token) {

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
    public Scantokens(String codeName) {

        super(codeName);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractCode#execute(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

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
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.ExpandableCode#expand(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter)
            throws ConfigurationException,
                HelpingException,
                TypesetterException {

        execute(prefix, context, source, typesetter);
    }

}
