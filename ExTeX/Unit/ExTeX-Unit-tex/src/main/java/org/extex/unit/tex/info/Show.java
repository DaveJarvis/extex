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

package org.extex.unit.tex.info;

import java.util.logging.Logger;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.Showable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive <code>\show</code>.
 * 
 * <doc name="show"> <h3>The Primitive <tt>\show</tt></h3>
 * <p>
 * The primitive <tt>\show</tt> consumes the following token and prints the
 * definition of the token to the output stream an into the log file.
 * </p>
 * <ul>
 * <li>If the token is a control sequence or active character and it is
 * undefined then it is reported as <i>undefined</i>.</li>
 * <li>If the token is a control sequence or active character and it is a
 * primitive then it is reported with the original name of the primitive. This
 * applies even if is redefined with <tt>\let</tt> to another name.</li>
 * <li>If the token is a control sequence or active character and it is a macro
 * then it is reported with the pattern and expansion text.</li>
 * <li>Otherwise the long descriptive form of the token is reported.</li>
 * </ul>
 * 
 * <h4>Syntax</h4> The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;show&rang;
 *       &rarr; <tt>\show</tt> {@linkplain
 *           org.extex.interpreter.TokenSource#getToken(Context)
 *           &lang;token&rang;} </pre>
 * 
 * <h4>Examples</h4> Examples:
 * 
 * <pre class="TeXSample">
 *    \show\abc
 *    > \abc=undefined
 *  </pre>
 * 
 * <pre class="TeXSample">
 *    \show \def
 *    > \def=\def.
 *  </pre>
 * 
 * <pre class="TeXSample">
 *    \let\xxx=\def\show \xxx
 *    > \xxx=\def.
 *  </pre>
 * 
 * <pre class="TeXSample">
 *    \def\m{abc}\show \m
 *    > \m=macro:
 *    ->abc.
 *  </pre>
 * 
 * <pre class="TeXSample">
 *    \show a
 *    > the letter a.
 *  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class Show extends AbstractCode implements LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>logger</tt> contains the target channel for the message.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Show(CodeToken token) {

        super(token);
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
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
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException(toText(context));
        }
        logger.info("\n> " + meaning(t, context).toText() + ".\n");
    }

    /**
     * Get the descriptions of a token as token list.
     * 
     * @param t the token to describe
     * @param context the interpreter context
     * 
     * @return the token list describing the token
     * 
     * @throws HelpingException in case of an error
     */
    protected Tokens meaning(Token t, Context context) throws HelpingException {

        Tokens toks;
        try {
            if (!(t instanceof CodeToken)) {
                return context.getTokenFactory().toTokens(t.toString());
            }

            if (t instanceof ControlSequenceToken) {

                toks = context.getTokenFactory().toTokens(context.esc(t));

            } else {
                toks =
                        new Tokens(//
                            context.getTokenFactory().createToken(
                                Catcode.OTHER, t.getChar(),
                                Namespace.DEFAULT_NAMESPACE));
            }

            toks.add(context.getTokenFactory().toTokens("="));
            Code code = context.getCode((CodeToken) t);
            if (code == null) {

                toks.add(context.getTokenFactory().toTokens(
                    getLocalizer().format("TTP.Undefined")));

            } else if ((code instanceof Showable)) {

                toks.add(((Showable) code).show(context));

            } else {

                toks.add(context.getTokenFactory().toTokens(
                    code.getToken().toText(context.escapechar())));

                // } else {
                //
                // toks.add(new Tokens(context, t.getChar().getCodePoint()));
            }
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
        return toks;
    }

}
