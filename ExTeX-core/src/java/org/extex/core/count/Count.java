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

package org.extex.core.count;

import java.io.Serializable;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.exception.helping.ArithmeticOverflowException;

/**
 * This class represents a long integer value. It is used for instance as count
 * register.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4399 $
 */
public class Count implements Serializable, FixedCount {

    /**
     * The constant <tt>ONE</tt> contains the count register with the value 1.
     * This count register is in fact immutable.
     */
    public static final FixedCount ONE = new ImmutableCount(1);

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2005L;

    /**
     * The constant <tt>THOUSAND</tt> contains the count register with the
     * value 1000. This count register is in fact immutable.
     */
    public static final FixedCount THOUSAND = new ImmutableCount(1000);

    /**
     * The constant <tt>ZERO</tt> contains the count register with the value
     * 0. This count register is in fact immutable.
     */
    public static final FixedCount ZERO = new ImmutableCount(0);

    /**
     * The field <tt>value</tt> contains the value of the count register.
     */
    private long value = 0;

    /**
     * Creates a new object.
     *
     * @param count the reference to be copied
     */
    public Count(final FixedCount count) {

        super();
        this.value = count.getValue();
    }

    /**
     * Creates a new object.
     *
     * @param value the value
     */
    public Count(final long value) {

        super();
        this.value = value;
    }

    /**
     * Add a long to the value. This operation modifies the value.
     *
     * @param val the value to add to
     */
    public void add(final long val) {

        value += val;
    }

    /**
     * Add a Count to the value. This operation modifies the value.
     *
     * @param val the value to add to
     */
    public void add(final Count val) {

        value += val.getValue();
    }

    /**
     * Divide the value by a long. This operation modifies the value.
     *
     * @param denom the denominator to divide by
     *
     * @throws ArithmeticOverflowException in case of a division by zero
     */
    public void divide(final long denom) throws ArithmeticOverflowException {

        if (denom == 0) {
            throw new ArithmeticOverflowException(null);
        }

        value /= denom;
    }

    /**
     * Divide the value by a denominator. This operation modifies the value.
     *
     * @param denom the denominator to divide by
     *
     * @throws ArithmeticOverflowException in case of a division by zero
     */
    public void divide(final Count denom) throws ArithmeticOverflowException {

        long d = denom.getValue();
        if (d == 0) {
            throw new ArithmeticOverflowException(null);
        }

        value /= d;
    }

    /**
     * @see org.extex.core.count.FixedCount#eq(
     *      org.extex.core.count.FixedCount)
     */
    public boolean eq(final FixedCount count) {

        return count.getValue() == value;
    }

    /**
     * @see org.extex.core.count.FixedCount#ge(
     *      org.extex.core.count.FixedCount)
     */
    public boolean ge(final FixedCount count) {

        return value >= count.getValue();
    }

    /**
     * Getter for the localizer. The localizer is initialized from the name of
     * the Count class.
     *
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(Count.class);
    }

    /**
     * Getter for the value
     *
     * @return the value
     */
    public long getValue() {

        return value;
    }

    /**
     * @see org.extex.core.count.FixedCount#gt(
     *      org.extex.core.count.FixedCount)
     */
    public boolean gt(final FixedCount count) {

        return value > count.getValue();
    }

    /**
     * @see org.extex.core.count.FixedCount#le(
     *      org.extex.core.count.FixedCount)
     */
    public boolean le(final FixedCount count) {

        return value <= count.getValue();
    }

    /**
     * @see org.extex.core.count.FixedCount#lt(
     *      org.extex.core.count.FixedCount)
     */
    public boolean lt(final FixedCount count) {

        return value < count.getValue();
    }

    /**
     * Multiply the value with a factor. This operation modifies the value.
     *
     * @param factor the factor to multiply with
     */
    public void multiply(final long factor) {

        value *= factor;
    }

    /**
     * Multiply the value with a factor. This operation modifies the value.
     *
     * @param factor the factor to multiply with
     */
    public void multiply(final Count factor) {

        value *= factor.getValue();
    }

    /**
     * @see org.extex.core.count.FixedCount#ne(
     *      org.extex.core.count.FixedCount)
     */
    public boolean ne(final FixedCount count) {

        return value != count.getValue();
    }

    /**
     * Setter for the value.
     *
     * @param l the new value
     *
     * @see #setValue(long)
     */
    public void set(final long l) {

        value = l;
    }

    /**
     * Setter for the value.
     *
     * @param c the new value
     *
     * @see #setValue(long)
     */
    public void set(final Count c) {

        value = c.getValue();
    }

    /**
     * Determine the printable representation of the object. The value returned
     * is exactly the string which would be produced by <logo>TeX</logo> to
     * print the Count.
     *
     * @return the printable representation
     *
     * @see #toString(StringBuffer)
     */
    public String toString() {

        return Long.toString(value);
    }

    /**
     * Determine the printable representation of the object. The value returned
     * is exactly the string which would be produced by <logo>TeX</logo> to
     * print the Count.
     *
     * @param sb the target string buffer
     *
     * @see #toString()
     */
    public void toString(final StringBuffer sb) {

        sb.append(value);
    }

}
