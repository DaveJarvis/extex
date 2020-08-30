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

package org.extex.unit.tex.register.count;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides an implementation for the primitive {@code \mag}. It
 * sets the named count register to the value given, and as a side effect all
 * prefixes are zeroed.
 * 
 * <p>The Primitive {@code \mag}</p>
 * <p>
 * The primitive {@code \mag} provides a means to set the magnification factor
 * for the current document. The primitive acts like a normal count register.
 * The magnification factor is given in multiples of 1000. This means that the
 * default value 1000 corresponds to an unmagnified output.
 * </p>
 * <p>
 * The effect of the setting of the magnification factor is that all length
 * values are multiplied with the magnification factor (divided by 1000). An
 * exception are the <i>true</i> length values. This means a length of 1&nbsp;pt
 * at a magnification of 1200 is in effect 1.2&nbsp;pt long. Whereas a length of
 * 1&nbsp;true&nbsp;pt remains unaffected by the magnification.
 * </p>
 * <p>
 * The magnification can only changed once at the beginning of a run.
 * </p>
 * <p>
 * An attempt to assign a non-positive number to {@code \mag} leads to an
 * error.
 * </p>
 * 
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;mag&rang;
 *      &rarr; &lang;optional prefix&rang; {@code \mag} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}
 *
 *    &lang;optional prefix&rang;
 *      &rarr;
 *       |  {@code \global}
 *       |  {@code \immediate}  </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *    \mag=1600  </pre>
 * 
 * 
 * @see org.extex.interpreter.type.code.Advanceable
 * @see org.extex.interpreter.type.code.Divideable
 * @see org.extex.interpreter.type.code.Multiplyable
 * @see org.extex.interpreter.type.Theable
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Mag extends AbstractCount
        implements
            ExpandableCode,
            Advanceable,
            Multiplyable,
            Divideable,
            Theable,
            CountConvertible {

    /**
     * The constant {@code serialVersionUID} contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public Mag(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");

        long value = source.parseInteger(context, source, null);
        value += context.getMagnification();

        context.setMagnification(value, true);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getOptionalEquals(context);

        long value = source.parseInteger(context, source, typesetter);
        context.setMagnification(value, true);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return context.getMagnification();
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");

        long value = source.parseInteger(context, source, null);

        if (value == 0) {
            throw new ArithmeticOverflowException(toText(context));
        }

        value = context.getMagnification() / value;
        context.setMagnification(value, true);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void expand(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        try {
            source.push(context.getTokenFactory().toTokens(
                context.getMagnification()));
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void init(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                TypesetterException {

        if (source == null) {
            return;
        }
        Token t = source.getNonSpace(context);
        if (t == null) {
            return;
        }
        source.push(t);
        long value = source.parseInteger(context, source, typesetter);
        context.setMagnification(value, false);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");

        long value = source.parseInteger(context, source, null);
        value *= context.getMagnification();
        context.setMagnification(value, true);
    }

    /**
*      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens(context.getMagnification());
    }

}
