/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.core.scaled;

/**
 * This class provides a fixed point number.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4404 $
 */
public class ScaledNumber {

    /**
     * The constant <tt>ONE</tt> contains the internal representation for 1pt.
     * 
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [101]"
     */
    public static final long ONE = 1 << 16;

    /**
     * Determine the printable representation of the object and append it to the
     * given StringBuffer.
     * 
     * @param sb the output string buffer
     * @param value the internal value in multiples of ONE
     */
    public static void toString(StringBuffer sb, long value) {

        long val = value;

        if (val < 0) {
            sb.append('-');
            val = -val;
        }

        long v = val / ONE;
        if (v == 0) {
            sb.append('0');
        } else {
            long m = 1;
            while (m <= v) {
                m *= 10;
            }
            m /= 10;
            while (m > 0) {
                sb.append((char) ('0' + (v / m)));
                v = v % m;
                m /= 10;
            }
        }

        sb.append('.');

        val = 10 * (val % ONE) + 5;
        long delta = 10;
        do {
            if (delta > ONE) {
                val = val + 0100000 - 50000; // round the last digit
            }
            int i = (int) (val / ONE);
            sb.append((char) ('0' + i));
            val = 10 * (val % ONE);
            delta *= 10;
        } while (val > delta);
    }

    /**
     * The field <tt>value</tt> contains the value.
     */
    private long value;

    /**
     * Creates a new object.
     * 
     */
    public ScaledNumber() {

        this.value = 0;
    }

    /**
     * Creates a new object.
     * 
     * @param value the initial value
     */
    public ScaledNumber(long value) {

        this.value = value;
    }

    /**
     * Add a number to the current one.
     * 
     * @param scaled the number to add
     */
    public void add(long scaled) {

        this.value += scaled;
    }

    /**
     * Add a number to the current one.
     * 
     * @param scaled the number to add
     */
    public void add(ScaledNumber scaled) {

        this.value += scaled.value;
    }

    /**
     * Divide the scaled value by a number.
     * 
     * @param scaled the divisor
     */
    public void divide(long scaled) {

        value = value / scaled * ONE;
    }

    /**
     * Compares the current instance with another ScaledNumber for equality.
     * 
     * @param d the other ScaledNumber to compare to. If this parameter is
     *        <code>null</code> then the comparison fails.
     * 
     * @return <code>true</code> iff <i>|this| == |d|</i>
     */
    public boolean eq(ScaledNumber d) {

        return (d != null && value == d.value);
    }

    /**
     * Compares the current instance with another ScaledNumber.
     * 
     * @param d the other ScaledNumber to compare to
     * 
     * @return <code>true</code> iff this is greater or equal to d
     */
    public boolean ge(ScaledNumber d) {

        return (value >= d.value);
    }

    /**
     * Getter for the value.
     * 
     * @return the value
     */
    public long getValue() {

        return value;
    }

    /**
     * Compares the current instance with another ScaledNumber.
     * 
     * @param d the other ScaledNumber to compare to
     * 
     * @return <code>true</code> iff this is less or equal to d
     */
    public boolean le(ScaledNumber d) {

        return (value <= d.value);
    }

    /**
     * Compares the current instance with another ScaledNumber.
     * 
     * @param d the other ScaledNumber to compare to
     * 
     * @return <code>true</code> iff |this| &lt; |d|</i>
     */
    public boolean lt(ScaledNumber d) {

        return (value < d.value);
    }

    /**
     * Multiply the current value by a scaled number.
     * 
     * @param scaled the multiplicant
     */
    public void multiply(long scaled) {

        value = value * scaled / ONE;
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

        this.value = this.value * nom / denom;
    }

    /**
     * Negate the current value.
     */
    public void negate() {

        this.value = -this.value;
    }

    /**
     * Set the value to a new one
     * 
     * @param scaled the new value
     */
    public void set(long scaled) {

        value = scaled;
    }

    /**
     * Setter for the value
     * 
     * @param scaled the new value
     */
    public void set(ScaledNumber scaled) {

        value = scaled.value;
    }

    /**
     * Subtract a number from the current one.
     * 
     * @param scaled the number to subtract
     */
    public void subtract(ScaledNumber scaled) {

        this.value -= scaled.value;
    }

    /**
     * Determine the printable representation of the object.
     * 
     * @return the printable representation
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        toString(sb, this.value);
        return sb.toString();
    }

}
