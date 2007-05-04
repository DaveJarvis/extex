/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.core.muskip;

import java.io.Serializable;

import org.extex.core.glue.GlueComponent;

/**
 * This class provides a dimen value with a length which is a multiple of
 * math units (mu).
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public class Mudimen implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060605L;

    /**
     * The field <tt>length</tt> contains the the natural length.
     */
    private GlueComponent length = new GlueComponent(0);

    /**
     * Creates a new object.
     * All components are 0.
     */
    public Mudimen() {

        super();
    }

    /**
     * Creates a new object.
     *
     * @param len the length
     */
    public Mudimen(long len) {

        super();
        length.set(len);
    }

    /**
     * Add some other length to the current value.
     *
     * @param value the value to add
     */
    public void add(long value) {

        this.length.add(new GlueComponent(value));
    }

    /**
     * Getter for length.
     *
     * @return the length
     */
    public GlueComponent getLength() {

        return this.length;
    }

    /**
     * Check for a zero value.
     *
     * @return <code>true</code> iff the length is zero
     */
    public boolean isZero() {

        return length.eq(GlueComponent.ZERO);
    }

    /**
     * Multiply the value by an integer fraction.
     * <p>
     *  <i>length</i> = <i>length</i> * <i>nom</i> / <i>denom</i>
     * </p>
     *
     * @param nom nominator
     * @param denom denominator
     */
    public void multiply(long nom, long denom) {

        length.multiply(nom, denom);
    }

    /**
     * Return the string representation of the instance.
     *
     * @return the string representation of this glue
     * @see "<logo>TeX</logo> &ndash; The Program [???]"
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }

    /**
     * Append the string representation of the instance to a string buffer.
     *
     * @param sb the target string buffer
     */
    public void toString(StringBuffer sb) {

        length.toString(sb, 'm', 'u');
    }

    //TODO gene: remove?
//    /**
//     * Determine the printable representation of the object and return it as a
//     * list of Tokens.
//     * The value returned is exactly the string which would be produced by
//     * <logo>TeX</logo> to print the Mudimen. This means the result is expressed
//     * in mu and properly rounded to be read back in again without loss of
//     * information.
//     *
//     * @param toks the tokens to append to
//     * @param factory the token factory to get the required tokens from
//     * @param c1 the first character of the unit
//     * @param c2 the second character of the unit
//     *
//     * @throws CatcodeException in case of an error
//     */
//    public void toToks(Tokens toks, TokenFactory factory,
//            char c1, char c2) throws CatcodeException {
//
//        length.toToks(toks, factory, c1, c2);
//    }

}
