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

package org.extex.base.parser.dimen;

import org.extex.core.scaled.ScaledNumber;

/**
 * This data type contains an accumulator which can contain values of different
 * kinds.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Accumulator {

    /**
     * The field {@code sp} contains the number of sp units encountered.
     */
    protected int sp;

    /**
     * The field {@code value} contains the numerical value in multiples of
     * 2<sup>-16</sup>.
     */
    protected long value;

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Accumulator(Accumulator value) {

        this.value = value.value;
        this.sp = value.sp;
    }

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Accumulator(long value) {

        this.value = value;
        this.sp = 0;
    }

    /**
     * Creates a new object.
     * 
     * @param value the value
     * @param sp the order
     */
    public Accumulator(long value, int sp) {

        this.value = value;
        this.sp = sp;
    }

    /**
     * Negate the value.
     * 
     * @return the current instance
     */
    public Accumulator negate() {

        this.value = -this.value;
        return this;
    }

    /**
     * Return the ord in a printable form.
     * 
     * @return the ord in a printable form
     */
    public String ordToString() {

        return "sp^" + sp;
    }

    /**
     * Scale this instance by multiplying the value and norming it. In addition
     * the order is adjusted:
     * <p>
     * <i>value</i> = <i>value</i> * <i>factor</i> / <i>divisor</i>
     * </p>
     * <p>
     * <i>sp</i> = <i>sp</i> + <i>order</i>
     * </p>
     * 
     * @param factor the factor
     * @param divisor the divisor
     * @param order the adjustment for the order
     */
    public void scale(long factor, long divisor, int order) {

        value *= factor;
        value /= divisor;
        sp += order;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        ScaledNumber.toString(sb, value);
        if (sp != 0) {
            sb.append("sp");
            if (sp != 1) {
                sb.append("^");
                sb.append(sp);
            }
        }
        return sb.toString();
    }

}
