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
 * This class provides an implementation for the primitive <code>\ifcase</code>.
 * 
 * <doc name="ifcase"> <h3>The Primitive <tt>\ifcase</tt></h3>
 * <p>
 * The primitive <tt>\ifcase</tt> provides a conditional switch on a numeric
 * value. The next tokens are used as a number. This number determines which
 * branch to expand. The first branch follows the number immediately. This
 * branch is associated to the number 0.
 * </p>
 * <p>
 * The primitive <tt>\or</tt> advances to the next branch. The primitive
 * <tt>\else</tt> starts the else branch. The else branch is used if no other
 * branch fits.
 * </p>
 * 
 * <h4>Syntax</h4> The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;ifcase&rang;
 *     &rarr; <tt>\ifcase</tt> {@linkplain
 *        org.extex.base.parser.ConstantCountParser#scanInteger(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}  &lang;cases&rang; <tt>\fi</tt>
 *
 *    &lang;cases&rang;
 *     &rarr;
 *      |  &lang;branch text&rang; <tt>\else</tt> &lang;else text&rang;
 *      |  &lang;branch text&rang; <tt>\or</tt> &lang;cases&rang;  </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \ifcase\count0 a\or b\or c\else x\fi  </pre>
 * 
 * </doc>
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4439 $
 */
public class Ifcase extends AbstractIf {

    /**
     * This enumeration names the different types of tags following an if.
     */
    private enum Tag {
        /**
         * The constant <tt>OR</tt> contains the value indicating an \or.
         */
        OR,
        /**
         * The constant <tt>ELSE</tt> contains the value indicating an \else.
         */
        ELSE,
        /**
         * The constant <tt>FI</tt> contains the value indicating a \fi.
         */
        FI;
    };

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
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
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.conditional.AbstractIf#conditional(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public boolean conditional(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        throw new ImpossibleException("\\ifcase conditional");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.conditional.AbstractIf#execute(org.extex.interpreter.Flags,
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
     * {@inheritDoc}
     * 
     * @see org.extex.unit.base.conditional.AbstractIf#expand(org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        execute(prefix, context, source, typesetter);
    }

    /**
     * Skip to the next matching <tt>\fi</tt> or <tt>\or</tt> Token counting the
     * intermediate <tt>\if</tt> s and <tt>\fi</tt>s.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * 
     * @return <code>true</code> if a matching <tt>\or</tt> has been found;
     *         otherwise return <code>false</code> if a matching <tt>\fi</tt>
     *         has been found.
     * 
     * @throws HelpingException in case of an error
     */
    private Tag skipToOrOrElseOrFi(Context context, TokenSource source)
            throws HelpingException {

        Code code;
        int n = 0;
        Locator locator = source.getLocator();

        for (Token t = source.getToken(context); t != null; t =
                source.getToken(context)) {
            locator = source.getLocator();
            if (t instanceof CodeToken
                    && (code = context.getCode((CodeToken) t)) != null) {
                if (code instanceof Fi) {
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
        }

        throw new HelpingException(getMyLocalizer(), "TTP.EOFinSkipped",
            toText(context), Integer.toString(locator.getLineNumber()));
    }

}
