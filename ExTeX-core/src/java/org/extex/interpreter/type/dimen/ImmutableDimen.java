/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.dimen;

import java.io.Serializable;

/**
 * This class provides objects of type
 * {@link org.extex.interpreter.type.dimen.Dimen Dimen} where all
 * assignment methods are redefined to produce a run-time exception.
 * Thus the object is in fact immutable.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4399 $
 */
public class ImmutableDimen extends Dimen implements Serializable {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060502L;

    /**
     * Creates a new object.
     *
     * @param value the value to be stored
     */
    public ImmutableDimen(final Dimen value) {

        super(value);
    }

    /**
     * Creates a new object.
     *
     * @param value the value to be stored
     */
    public ImmutableDimen(final long value) {

        super(value);
    }

    /**
     * Add the value of the argument to the current value.
     * This operation is not supported.
     *
     * @param d the Dimen to add
     *
     * @see org.extex.interpreter.type.dimen.Dimen#add(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void add(final Dimen d) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

    /**
     * Divide the current value with a given number.
     * This operation is not supported.
     *
     * @param denom denominator to divide by
     *
     * @see org.extex.interpreter.type.dimen.Dimen#divide(long)
     */
    public void divide(final long denom) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

    /**
     * Sets the value of the dimen to the maximum of the value already stored
     * and a given argument.
     * This operation is not supported.
     *
     * @param d the other dimen
     *
     * @see org.extex.interpreter.type.dimen.Dimen#max(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void max(final Dimen d) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

    /**
     * Multiply the current value with a given number.
     * This operation is not supported.
     *
     * @param factor the factor to multiply with
     *
     * @see org.extex.interpreter.type.dimen.Dimen#multiply(long)
     */
    public void multiply(final long factor) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

    /**
     * Subtract the value of the argument from the current value.
     * This operation is not supported.
     *
     * @param d the Dimen to subtract
     *
     * @see org.extex.interpreter.type.dimen.Dimen#subtract(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void subtract(final FixedDimen d) {

        throw new UnsupportedOperationException(
            "Unable to add to an immutable object");
    }

}
