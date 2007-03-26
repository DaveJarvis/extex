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

package org.extex.unit.base.register.count.util;

import org.extex.core.count.Count;
import org.extex.core.count.CountConvertible;
import org.extex.core.count.CountParser;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.ExpandableCode;
import org.extex.interpreter.type.InitializableCode;
import org.extex.interpreter.type.Theable;
import org.extex.interpreter.type.arithmetic.Advanceable;
import org.extex.interpreter.type.arithmetic.Divideable;
import org.extex.interpreter.type.arithmetic.Multiplyable;
import org.extex.scanner.type.CatcodeException;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.Typesetter;

/**
 * This class provides an object which acts like a count register.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class IntegerCode extends AbstractAssignment
        implements
            CountConvertible,
            Theable,
            Multiplyable,
            Divideable,
            Advanceable,
            ExpandableCode,
            InitializableCode {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060607L;

    /**
     * The field <tt>value</tt> contains the value stored in this object.
     */
    private long value;

    /**
     * Creates a new object.
     *
     * @param name the first name of the primitive
     */
    public IntegerCode(String name) {

        super(name);
        this.value = 0;
    }

    /**
     * Creates a new object.
     *
     * @param name the first name of the primitive
     * @param value the initial value
     */
    public IntegerCode(String name, long value) {

        super(name);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.arithmetic.Advanceable#advance(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void advance(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        source.getKeyword(context, "by");
        Count v = CountParser.parse(context, source, typesetter);
        value += v.getValue();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void assign(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        source.getOptionalEquals(context);
        Count v = CountParser.parse(context, source, typesetter);
        value = v.getValue();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.core.count.CountConvertible#convertCount(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public long convertCount(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.arithmetic.Divideable#divide(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void divide(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        source.getKeyword(context, "by");
        Count v = CountParser.parse(context, source, typesetter);
        if (v.eq(Count.ZERO)) {
            throw new ArithmeticOverflowException(
                printableControlSequence(context));
        }
        value /= v.getValue();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.ExpandableCode#expand(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void expand(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        try {
            source.push(context.getTokenFactory().toTokens(value));
        } catch (CatcodeException e) {
            throw new InterpreterException(e);
        }
    }

    /**
     * Getter for value.
     *
     * @return the value
     */
    public long getValue() {

        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.InitializableCode#init(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void init(Context context, TokenSource source,
            Typesetter typesetter) throws InterpreterException {

        if (source == null) {
            return;
        }
        value = CountParser.scanInteger(context, source, typesetter);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.arithmetic.Multiplyable#multiply(
     *      org.extex.interpreter.Flags,
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public void multiply(Flags prefix, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException {

        source.getKeyword(context, "by");
        Count v = CountParser.parse(context, source, typesetter);
        value *= v.getValue();
    }

    /**
     * Setter for value.
     *
     * @param value the value to set
     */
    public void setValue(long value) {

        this.value = value;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.extex.interpreter.type.Theable#the(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter)
     */
    public Tokens the(Context context, TokenSource source,
            Typesetter typesetter)
            throws InterpreterException,
                CatcodeException {

        return context.getTokenFactory().toTokens(value);
    }

}
