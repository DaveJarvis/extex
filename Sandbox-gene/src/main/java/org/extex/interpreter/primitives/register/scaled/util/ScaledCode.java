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

package org.extex.interpreter.primitives.register.scaled.util;

import org.extex.base.parser.ScaledNumberParser;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.scaled.ScaledNumber;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.ScaledConvertible;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.code.Advanceable;
import org.extex.interpreter.type.code.Divideable;
import org.extex.interpreter.type.code.Multiplyable;
import org.extex.scanner.exception.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class provides a object usable as Code carrying a scaled number.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4787 $
 */
public class ScaledCode extends AbstractAssignment
        implements
            ScaledConvertible,
            Advanceable,
            Divideable,
            Multiplyable,
            Theable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060606L;

    /**
     * The field <tt>value</tt> contains the encapsulated value. Unfortunately
     * Java does not allow multiple inheritance. Thus we have to use delegation
     * instead.
     */
    private ScaledNumber value = new ScaledNumber();

    /**
     * Creates a new object.
     * 
     * @param name the initial name of the primitive
     * @param scaled the initial value
     */
    public ScaledCode(String name, ScaledNumber scaled) {

        super(name);
        value.set(scaled);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.code.Advanceable#advance(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void advance(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");
        long scaled = ScaledNumberParser.parse(context, source, typesetter);
        value.add(scaled);
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

        source.getOptionalEquals(context);
        long scaled = ScaledNumberParser.parse(context, source, typesetter);
        value.set(scaled);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.parser.ScaledConvertible#convertScaled(org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public long convertScaled(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        return value.getValue();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.code.Divideable#divide(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");
        long scaled = ScaledNumberParser.parse(context, source, typesetter);
        if (scaled == 0) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }
        value.divide(scaled);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.code.Multiplyable#multiply(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        source.getKeyword(context, "by");
        long scaled = ScaledNumberParser.parse(context, source, typesetter);
        value.multiply(scaled);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException, TypesetterException {

        try {
            return context.getTokenFactory().toTokens(value.toString());
        } catch (CatcodeException e) {
            throw new NoHelpException(e);
        }
    }

}
