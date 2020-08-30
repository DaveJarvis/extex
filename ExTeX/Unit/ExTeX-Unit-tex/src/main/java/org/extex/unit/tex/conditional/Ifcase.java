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

package org.extex.unit.tex.conditional;

import org.extex.core.Locator;
import org.extex.core.exception.ImpossibleException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.base.conditional.AbstractIf;
import org.extex.unit.base.conditional.Else;
import org.extex.unit.base.conditional.Fi;

/**
 * This class provides an implementation for the primitive {@code \ifcase}.
 * 
 * <p>The Primitive {@code \ifcase}</p>
 * <p>
 * The primitive {@code \ifcase} provides a conditional switch on a numeric
 * value. The next tokens are used as a number. This number determines which
 * branch to expand. The first branch follows the number immediately. This
 * branch is associated to the number 0.
 * </p>
 * <p>
 * The primitive {@code \or} advances to the next branch. The primitive
 * {@code \else} starts the else branch. The else branch is used if no other
 * branch fits.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ifcase&rang;
 *     &rarr; {@code \ifcase} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#scanInteger(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}  &lang;cases&rang; {@code \fi}
 *
 *    &lang;cases&rang;
 *     &rarr;
 *      |  &lang;branch text&rang; {@code \else} &lang;else text&rang;
 *      |  &lang;branch text&rang; {@code \or} &lang;cases&rang;  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \ifcase\count0 a\or b\or c\else x\fi  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Ifcase extends AbstractIf {

    /**
     * This enumeration names the different types of tags following an if.
     */
    private enum Tag {
        /**
         * The constant {@code OR} contains the value indicating an \or.
         */
        OR,
        /**
         * The constant {@code ELSE} contains the value indicating an \else.
         */
        ELSE,
        /**
         * The constant {@code FI} contains the value indicating a \fi.
         */
        FI;
    };

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2011L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Ifcase(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        throw new ImpossibleException("\\ifcase conditional");
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        long branch = source.parseInteger(context, source, typesetter);
        if (branch < 0) {
            if (skipToElseOrFi(context, source, getToken())) {
                context.pushConditional(source.getLocator(), true, this, -1,
                    false);
            }
            return;
        }

        for (long i = branch; i > 0;) {
            Tag tag = skipToOrOrElseOrFi(context, source);
            if (tag == Tag.OR) {
                i--;
            } else if (tag == Tag.ELSE) {
                context.pushConditional(source.getLocator(), true, this, -1,
                    false);
                return;
            } else if (tag == Tag.FI) {
                return;
            } else {
                throw new ImpossibleException("impossible tag encountered");
            }
        }
        context.pushConditional(source.getLocator(), true, this, branch, false);
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
     * Skip to the next matching {@code \fi} or {@code \or} Token counting the
     * intermediate {@code \if} s and {@code \fi}s.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * 
     * @return {@code true} if a matching {@code \or} has been found;
     *         otherwise return {@code false} if a matching {@code \fi}
     *         has been found.
     * 
     * @throws HelpingException in case of an error
     */
    private Tag skipToOrOrElseOrFi(Context context, TokenSource source)
            throws HelpingException {

        int n = 0;
        Locator locator = source.getLocator();

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {
            locator = source.getLocator();
            Code code =
                    (t instanceof CodeToken
                            ? context.getCode((CodeToken) t)
                            : null);
            if (code == null) {
                // continue
            } else if (code instanceof Fi) {
                if (--n < 0) {
                    return Tag.FI;
                }
            } else if (code instanceof Or) {
                if (n <= 0) {
                    return Tag.OR;
                }
            } else if (code instanceof Else) {
                if (n <= 0) {
                    return Tag.ELSE;
                }
            } else if (code.isIf()) {
                n++;
            }
        }

        throw new HelpingException(getMyLocalizer(), "TTP.EOFinSkipped",
            toText(context), Integer.toString(locator.getLineNumber()));
    }

}
