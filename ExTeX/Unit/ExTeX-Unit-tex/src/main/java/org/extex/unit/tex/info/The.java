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

package org.extex.unit.tex.info;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractCode;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.CodeExpander;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \the}.
 * 
 * <p>The Primitive {@code \the}</p>
 * <p>
 * The primitive {@code \the} inserts the definition of certain primitives into
 * the input stream. If the token following {@code \the} is not theable then an
 * error is raised.
 * </p>
 * <p>
 * During the expansion of arguments of macros like {@code \edef},
 * {@code \xdef}, {@code \message}, and others the further expansion of the
 * tokens is inhibited.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;the&rang;
 *      &rarr; {@code \the} &lang;internal quantity&rang; </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \the\count123  </pre>
 * 
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class The extends AbstractCode implements ExpandableCode, CodeExpander {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public The(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        Token cs = source.getToken(context);

        if (cs == null) {
            throw new EofException(toText(context));
        }
        if (cs instanceof CodeToken) {

            Code code = context.getCode((CodeToken) cs);

            if (code instanceof Theable) {
                Tokens toks;
                try {
                    toks = ((Theable) code).the(context, source, typesetter);
                } catch (CatcodeException e) {
                    throw new NoHelpException(e);
                }
                source.push(toks);
                return;
            } else if (code == null) {
                throw new UndefinedControlSequenceException(
                    cs.toText(context.escapechar()));
            }
        }

        throw new HelpingException(getLocalizer(), "TTP.CantUseAfterThe",
            cs.toString(), toText(context));
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        execute(prefix, context, source, typesetter);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.tokens.Tokens)
     */
    @Override
    public void expandCode(Context context, TokenSource source,
            Typesetter typesetter, Tokens tokens)
            throws HelpingException,
                TypesetterException {

        Token cs = source.getToken(context);

        if (cs == null) {
            throw new EofException(toText(context));
        }
        if (cs instanceof CodeToken) {

            Code code = context.getCode((CodeToken) cs);

            if (code instanceof Theable) {
                try {
                    tokens.add(((Theable) code)
                        .the(context, source, typesetter));
                } catch (CatcodeException e) {
                    throw new NoHelpException(e);
                }
                return;
            }
        }

        throw new HelpingException(getLocalizer(), "TTP.CantUseAfterThe",
            cs.toString(), toText(context));
    }
}
