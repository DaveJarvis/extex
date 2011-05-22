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

package org.extex.core.dimen;

import java.io.Serializable;

/**
 * This class provides objects of type {@link org.extex.core.dimen.Dimen Dimen}
 * where all assignment methods are nit present. Thus the object is in fact
 * immutable.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4399 $
 */
public class DimenConstant implements FixedDimen, Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The constant <tt>ONE</tt> contains the internal representation for 1pt.
     * 
     * @see "<logo>T<span style=
     *      "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"
     *      >e</span>X</logo> &ndash; The Program [101]"
     */
    public static final long ONE = 1 << 16;

    /**
     * The field <tt>value</tt> contains the integer representation of the dimen
     * register in sp if the order is 0. If the order is not 0 then the value
     * holds the factor to the order in units of 2<sup>16</sup>.
     */
    protected long value;

    /**
     * Creates a new object.
     * 
     * @param value the value to be stored
     */
    public DimenConstant(FixedDimen value) {

        this.value = value.getValue();
    }

    /**
     * Creates a new object.
     * 
     * @param value the value to be stored
     */
    public DimenConstant(long value) {

        this.value = value;
    }

    /**
     * Create a copy of this instance with the same order and value.
     * 
     * @return a new copy of this instance
     */
    @Override
    public FixedDimen copy() {

        return new DimenConstant(value);
    }

    /**
     * Compares the current instance with another GlueComponent for equality.
     * 
     * @param d the other GlueComponent to compare to. If this parameter is
     *        <code>null</code> then the comparison fails.
     * 
     * @return <code>true</code> iff <i>|this| == |d| and ord(this) ==
     *         ord(d)</i>
     */
    @Override
    public boolean eq(FixedDimen d) {

        return (d != null && value == d.getValue());
    }

    /**
     * Compares the current instance with another GlueComponent.
     * 
     * @param d the other GlueComponent to compare to
     * 
     * @return <code>true</code> iff this is greater or equal to d
     */
    @Override
    public boolean ge(FixedDimen d) {

        return (!lt(d));
    }

    /**
     * Getter for the value in scaled points (sp).
     * 
     * @return the value in internal units of scaled points (sp)
     */
    @Override
    public long getValue() {

        return this.value;
    }

    /**
     * Compares the current instance with another GlueComponent.
     * 
     * @param d the other GlueComponent to compare to
     * 
     * @return <code>true</code> iff |this| &gt; |d|</i>
     */
    @Override
    public boolean gt(FixedDimen d) {

        return (value > d.getValue());
    }

    /**
     * Compares the current instance with another GlueComponent.
     * 
     * @param d the other GlueComponent to compare to
     * 
     * @return <code>true</code> iff this is less or equal to d
     */
    @Override
    public boolean le(FixedDimen d) {

        return !gt(d);
    }

    /**
     * Compares the current instance with another GlueComponent.
     * 
     * @param d the other GlueComponent to compare to
     * 
     * @return <code>true</code> iff <i>ord(this) == ord(d) && |this| &lt;
     *         |d|</i> or <i>ord(this) &lt; ord(d)</i>
     */
    @Override
    public boolean lt(FixedDimen d) {

        return (value < d.getValue());
    }

    /**
     * Compares the current instance with another GlueComponent for equality.
     * 
     * @param d the other GlueComponent to compare to. If this parameter is
     *        <code>null</code> then the comparison fails.
     * 
     * @return <code>false</code> iff <i>|this| == |d| </i>
     */
    @Override
    public boolean ne(FixedDimen d) {

        return (d != null && (value != d.getValue()));
    }

    /**
     * Determine the printable representation of the object.
     * 
     * @return the printable representation
     * 
     * @see #toString(StringBuilder)
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    /**
     * Determine the printable representation of the object and append it to the
     * given StringBuilder.
     * 
     * @param sb the output string buffer
     * 
     * @see #toString()
     */
    @Override
    public void toString(StringBuilder sb) {

        toString(sb, 'p', 't');
    }

    /**
     * Determine the printable representation of the object and append it to the
     * given StringBuilder.
     * 
     * @param sb the output string buffer
     * @param c1 the first character for the length of order 0
     * @param c2 the second character for the length of order 0
     * 
     * @see #toString(StringBuilder)
     */
    public void toString(StringBuilder sb, char c1, char c2) {

        long val = getValue();

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

        sb.append(c1);
        sb.append(c2);
    }

}
