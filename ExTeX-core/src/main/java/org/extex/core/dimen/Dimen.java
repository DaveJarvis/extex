/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

import java.io.Serializable;

import org.extex.core.glue.FixedGlueComponent;
import org.extex.core.glue.GlueComponent;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class implements the dimen value. This is a length with fixed point
 * arithmetic.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4726 $
 */
public class Dimen extends GlueComponent implements Serializable, FixedDimen {

    /**
     * The constant <tt>ZERO</tt> contains the non-stretchable and
     * non-shrinkable value of 0&nbsp;pt.
     */
    public static final FixedGlueComponent ZERO = GlueComponent.ZERO;

    /**
     * The constant <tt>ONE</tt> contains the internal representation for 1pt.
     * @see "<logo>TeX</logo> &ndash; The Program [101]"
     */
    public static final long ONE = GlueComponent.ONE;

    /**
     * The constant <tt>ONE_INCH</tt> contains the immutable dimen register
     * representing the length of 1&nbsp;in.
     */
    public static final ImmutableDimen ONE_INCH =
            new ImmutableDimen(ONE * 7227 / 100);

    /**
     * The constant <tt>ONE_PT</tt> contains the immutable dimen register
     * representing the length of 1&nbsp;pt.
     */
    public static final ImmutableDimen ONE_PT = new ImmutableDimen(ONE);

    /**
     * The constant <tt>ONE_SP</tt> contains the immutable dimen register
     * representing the length of 1&nbsp;scaled point.
     */
    public static final ImmutableDimen ONE_SP = new ImmutableDimen(1);

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060513L;

    /**
     * The constant <tt>ZERO_PT</tt> contains the immutable dimen register
     * representing the length of 0&nbsp;pt.
     */
    public static final ImmutableDimen ZERO_PT = new ImmutableDimen(0);

    /**
     * Creates a new object.
     * The length stored in it is initialized to 0&nbsp;pt.
     */
    public Dimen() {

        super();
    }

    /**
     * Creates a new object.
     * This method makes a new instance of the class with the same value as
     * the given instance. I.e. it acts like clone().
     *
     * @param value the value to imitate
     */
    public Dimen(FixedDimen value) {

        super(value == null ? 0 : value.getValue());
    }

    /**
     * Creates a new object.
     * This method makes a new instance of the class with the given value.
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
        setValue(v < 0 ? -v : v);
    }

    /**
     * Add the value of the argument to the current value.
     * This operation modifies the instance.
     *
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| + |<i>d</i>|
     * </p>
     *
     * @param d the Dimen to add
     */
    public void add(FixedDimen d) {

        setValue(getValue() + d.getValue());
    }

    /**
     * Add the value of the argument to the current value.
     * This operation modifies the instance.
     *
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| + |<i>d</i>|
     * </p>
     *
     * @param d the value to add in sp
     */
    public void add(long d) {

        if (d != 0) {
            setValue(getValue() + d);
        }
    }

    /**
     * Divide the current value with a given number.
     * This operation modifies this instance.
     *
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| / <i>denom</i>
     * </p>
     *
     * @param denom denominator to divide by
     *
     * @throws ArithmeticException in case of a division by 0, i.e. if
     *  denom is 0.
     */
    public void divide(long denom) throws ArithmeticException {

        if (denom == 0) {
            throw new ArithmeticException();
        }
        setValue(getValue() / denom);
    }

    /**
     * Getter for the localizer.
     * The localizer is initialized from the name of the Dimen class.
     *
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(Dimen.class);
    }

    /**
     * Test for a value of zero.
     *
     * @return <code>true</code> iff the value is 0
     */
    public boolean isZero() {

        return getValue() == 0;
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
            setValue(val);
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
            setValue(val);
        }
    }

    /**
     * Multiply the current value with a given number.
     * This operation modifies this instance.
     *
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| * <i>factor</i>
     * </p>
     *
     * @param factor the factor to multiply with
     */
    public void multiply(long factor) {

        setValue(getValue() * factor);
    }

    /**
     * Negate the value of the argument to the current value.
     * This operation modifies the instance.
     *
     * <p>
     * |<i>this</i>| &rarr;  - |<i>this</i>|
     * </p>
     */
    public void negate() {

        setValue(-getValue());
    }

    /**
     * Setter for the value.
     * The order of the argument is ignored.
     *
     * @param d the new value
     */
    public void set(FixedGlueComponent d) {

        set(d.getValue());
    }

    /**
     * Subtract the value of the argument from the current value.
     * This operation modifies the instance.
     *
     * <p>
     * |<i>this</i>| &rarr; |<i>this</i>| - |<i>d</i>|
     * </p>
     *
     * @param d the Dimen to subtract
     */
    public void subtract(FixedDimen d) {

        setValue(getValue() - d.getValue());
    }

}
