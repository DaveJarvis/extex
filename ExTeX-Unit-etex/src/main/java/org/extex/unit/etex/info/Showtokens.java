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

package org.extex.unit.etex.info;

import java.util.logging.Logger;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.EofInToksException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingLeftBraceException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.tokens.TokensConvertible;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive
 * <code>\showtokens</code>.
 * 
 * <doc name="showtokens">
 * <h3>The Primitive <tt>\showtokens</tt></h3>
 * <p>
 * TODO missing documentation
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;showtokens&rang;
 *       &rarr; <tt>\showtokens</tt> ...  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \showtokens {1234}  </pre>
 *  <pre class="TeXSample">
 *    \showtokens \expandafter{\jobname}  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Showtokens extends AbstractCode implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060603L;

    /**
     * The field <tt>logger</tt> contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param name the name for tracing and debugging
     */
    public Showtokens(String name) {

        super(name);
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        this.logger = log;
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

        Tokens tokens = getTokens(context, source, typesetter);
        logger.info("\n> " + tokens.toText() + ".\n");
    }

    /**
     * Collect some tokens but expand until the starting brace is found.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the tokens collected
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error
     */
    private Tokens getTokens(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Tokens tokens = new Tokens();
        Token token = source.scanToken(context);

        if (token instanceof LeftBraceToken) {
            int balance = 1;

            for (token = source.getToken(context); token != null; token =
                    source.getToken(context)) {

                if (token.isa(Catcode.LEFTBRACE)) {
                    ++balance;
                } else if (token instanceof RightBraceToken && --balance <= 0) {
                    return tokens;
                }

                tokens.add(token);
            }

            throw new EofInToksException(printableControlSequence(context));

        } else if (token instanceof CodeToken) {
            Code code = context.getCode((CodeToken) token);
            if (code instanceof TokensConvertible) {
                return ((TokensConvertible) code).convertTokens(context,
                    source, typesetter);
            }

        } else if (token == null) {
            throw new EofException(getLocalizer().format("Tokens.Text"));
        }

        throw new MissingLeftBraceException("???");
    }

}
