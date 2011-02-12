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

package org.extex.unit.omega.math;

import org.extex.core.UnicodeChar;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.scanner.api.exception.CatcodeException;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.math.MathCode;

/**
 * This class provides an implementation for the primitive
 * <code>\mathcode</code>.
 * 
 * <doc name="omathcode">
 * <h3>The Math Primitive <tt>\omathcode</tt></h3>
 * <p>
 * The math primitive <tt>\omathcode</tt> sets a math code for a character.
 * For this purpose it takes a character and a math code as arguments.
 * </p>
 * <p>
 * The math code is made up of three components: the class, the family, and a
 * character.
 * </p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>
 * If the math code of a character has the special value "8000000 then it is
 * treated like an active character. In this case the expansion is initiated.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;omathcode&rang;
 *      &rarr; <tt>\omathcode</tt> {@link
 *        org.extex.interpreter.TokenSource#scanCharacterCode(Context,Typesetter,CodeToken)
 *        &lang;character code&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} &lang;omega math code&rang;
 *        </pre>
 * 
 * <h4>Examples</h4>
 * 
 * <pre class="TeXSample">
 *    \omathcode`.="41  </pre>
 * 
 * <pre class="TeXSample">
 *    \omathcode`.={}  </pre>
 * 
 * </doc>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OmathcodePrimitive extends AbstractAssignment
        implements
            CountConvertible,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public OmathcodePrimitive(CodeToken token) {

        super(token);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getToken());
        source.getOptionalEquals(context);
        MathCode mathchar =
                AbstractOmegaMathCode.parseMathCode(context, source,
                    typesetter, getToken());
        context.setMathcode(charCode, mathchar, prefix.clearGlobal());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        UnicodeChar charCode =
                source.scanCharacterCode(context, typesetter, getToken());
        return AbstractOmegaMathCode.mathCodeToLong(context
            .getMathcode(charCode));
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws CatcodeException,
                HelpingException,
                TypesetterException {

        return context.getTokenFactory().toTokens( //
            convertCount(context, source, typesetter));
    }

}
