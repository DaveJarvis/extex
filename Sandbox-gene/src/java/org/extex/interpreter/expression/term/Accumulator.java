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

package org.extex.interpreter.expression.term;

import org.extex.interpreter.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;

/**
 * This data type contains an accumulator which can contain values of
 * different kinds.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4733 $
 */
public class Accumulator implements EType {

    /**
     * The field <tt>value</tt> contains the encapsulated value.
     */
    private EType value;

    /**
     * Creates a new object.
     */
    public Accumulator() {

        super();
        this.value = null;
    }

    /**
     * @see org.extex.interpreter.expression.EType#add(
     *      org.extex.interpreter.expression.EType)
     */
    public EType add(final EType t) throws CastException, UnsupportedException {

        return (value != null ? value.add(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#and(
     *      org.extex.interpreter.expression.EType)
     */
    public EType and(final EType t) throws CastException, UnsupportedException {

        return (value != null ? value.and(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#divide(
     *      org.extex.interpreter.expression.EType)
     */
    public EType divide(final EType t)
            throws CastException,
                UnsupportedException,
                ArithmeticOverflowException {

        return (value != null ? value.divide(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#eq(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean eq(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.eq(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#ge(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean ge(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.ge(t) : null);
    }

    /**
     * Getter for value.
     *
     * @return the value
     */
    public EType getValue() {

        return this.value;
    }

    /**
     * @see org.extex.interpreter.expression.EType#gt(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean gt(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.gt(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#le(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean le(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.le(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#lt(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean lt(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.lt(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#multiply(
     *      org.extex.interpreter.expression.EType)
     */
    public EType multiply(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.multiply(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#ne(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean ne(final EType t)
            throws CastException,
                UnsupportedException {

        return (value != null ? value.ne(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#negate()
     */
    public EType negate() throws UnsupportedException {

        return (value != null ? value.negate() : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#not()
     */
    public EType not() {

        return null;
    }

    /**
     * @see org.extex.interpreter.expression.EType#or(
     *      org.extex.interpreter.expression.EType)
     */
    public EType or(final EType t) throws CastException, UnsupportedException {

        return (value != null ? value.or(t) : null);
    }

    /**
     * @see org.extex.interpreter.expression.EType#set(
     *      org.extex.interpreter.expression.EType)
     */
    public EType set(final EType t) {

        if (t instanceof Accumulator) {
            value = ((Accumulator) t).value;
        } else {
            value = t;
        }
        return this;
    }

    /**
     * @see org.extex.interpreter.expression.EType#subtract(
     *      org.extex.interpreter.expression.EType)
     */
    public EType subtract(final EType t)
            throws CastException,
                UnsupportedException {

        value = value.subtract(t);
        return this;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return value.toString();
    }

}
