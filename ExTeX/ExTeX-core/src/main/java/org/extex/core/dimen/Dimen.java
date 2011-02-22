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

package org.extex.core.dimen;

import org.extex.core.glue.GlueComponent;

/**
 * This class implements the dimen value. This is a length with fixed point
 * arithmetic.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4726 $
 */
public class Dimen extends DimenConstant {

    /**
     * The constant <tt>ZERO</tt> contains the non-stretchable and
     * non-shrinkable value of 0&nbsp;pt.
     */
    public static final FixedDimen ZERO = new DimenConstant(0);

    /**
     * The constant <tt>ONE</tt> contains the internal representation for 1pt.
     * 
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [101]"
     */
    public static final long ONE = GlueComponent.ONE;

    /**
     * The constant <tt>ONE_INCH</tt> contains the immutable dimen register
     * representing the length of 1&nbsp;in.
     */
    public static final FixedDimen ONE_INCH = new DimenConstant(
        ONE * 7227 / 100);

    /**
     * The constant <tt>ONE_PT</tt> contains the immutable dimen register
     * representing the length of 1&nbsp;pt.
     */
    public static final FixedDimen ONE_PT = new DimenConstant(ONE);

    /**
     * The constant <tt>ONE_SP</tt> contains the immutable dimen register
     * representing the length of 1&nbsp;scaled point.
     */
    public static final FixedDimen ONE_SP = new DimenConstant(1);

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The constant <tt>ZERO_PT</tt> contains the immutable dimen register
     * representing the length of 0&nbsp;pt.
     */
    public static final FixedDimen ZERO_PT = new DimenConstant(0);

    /**
     * Creates a new object. The length stored in it is initialized to
     * 0&nbsp;pt.
     */
    public Dimen() {

        super(0);
    }

    /**
     * Creates a new object. This method makes a new instance of the class with
     * the same value as the given instance. I.e. it acts like clone().
     * 
     * @param value the value to imitate
     */
    public Dimen(FixedDimen value) {

        super(value == null ? 0 : value.getValue());
    }

    /**
     * Creates a new object. This method makes a new instance of the class with
     * the given value.
     * 
     * @param value the value to set
     */
    public Dimen(long value) {

        super(value);
    }

    /**
     * Set the value of this dimen to the absolute value of a given value.
     * 
     * @param d the given value
     */
    public void abs(FixedDimen d) {

        long v = d.getValue();
        value = (v < 0 ? -v : v);
    }

    /**
     * Add the value of the argument to the current value. This operation
     * modifies the instance.
     * 
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| + |<i>d</i>|
     * </p>
     * 
     * @param d the Dimen to add
     */
    public void add(FixedDimen d) {

        value += d.getValue();
    }

    /**
     * Add the value of the argument to the current value. This operation
     * modifies the instance.
     * 
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| + |<i>d</i>|
     * </p>
     * 
     * @param d the value to add in sp
     */
    public void add(long d) {

        if (d != 0) {
            value += d;
        }
    }

    /**
     * Divide the current value with a given number. This operation modifies
     * this instance.
     * 
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| / <i>denom</i>
     * </p>
     * 
     * @param denom denominator to divide by
     * 
     * @throws ArithmeticException in case of a division by 0, i.e. if denom is
     *         0.
     */
    public void divide(long denom) throws ArithmeticException {

        if (denom == 0) {
            throw new ArithmeticException();
        }
        if (denom != 1) {
            value /= denom;
        }
    }

    /**
     * Test for a value of zero.
     * 
     * @return <code>true</code> iff the value is 0
     */
    public boolean isZero() {

        return value == 0;
    }

    /**
     * Sets the value of the dimen to the maximum of the value already stored
     * and a given argument.
     * 
     * <i>|this| = max(|this|, |d|)</i>
     * 
     * @param d the other dimen
     */
    public void max(FixedDimen d) {

        long val = d.getValue();
        if (val > getValue()) {
            value = val;
        }
    }

    /**
     * Sets the value of the dimen to the minimum of the value already stored
     * and a given argument.
     * 
     * <i>|this| = min(|this|, |d|)</i>
     * 
     * @param d the other dimen
     */
    public void min(FixedDimen d) {

        long val = d.getValue();
        if (val < getValue()) {
            value = val;
        }
    }

    /**
     * Multiply the value by an integer fraction.
     * <p>
     * <i>length</i> = <i>length</i> * <i>nom</i> / <i>denom</i>
     * </p>
     * 
     * @param nom nominator
     * @param denom denominator
     */
    public void multiply(long nom, long denom) {

        value = value * nom / denom;
    }

    /**
     * Multiply the current value with a given number. This operation modifies
     * this instance.
     * 
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| * <i>factor</i>
     * </p>
     * 
     * @param factor the factor to multiply with
     */
    public void multiply(long factor) {

        if (factor != 1) {
            value = getValue() * factor;
        }
    }

    /**
     * Negate the value of the argument to the current value. This operation
     * modifies the instance.
     * 
     * <p>
     * |<i>this</i>| &rarr; - |<i>this</i>|
     * </p>
     */
    public void negate() {

        value = -getValue();
    }

    /**
     * Setter for the value. The order of the argument is ignored.
     * 
     * @param d the new value
     */
    public void set(FixedDimen d) {

        value = d.getValue();
    }

    /**
     * Setter for the value. The order of the argument is ignored.
     * 
     * @param d the new value
     */
    public void set(long d) {

        value = d;
    }

    /**
     * Subtract the value of the argument from the current value. This operation
     * modifies the instance.
     * 
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| - |<i>d</i>|
     * </p>
     * 
     * @param d the Dimen to subtract
     */
    public void subtract(FixedDimen d) {

        value -= d.getValue();
    }

}
