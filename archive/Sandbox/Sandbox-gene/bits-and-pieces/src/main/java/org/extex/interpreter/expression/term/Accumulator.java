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

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;

/**
 * This data type contains an accumulator which can contain values of different
 * kinds.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class Accumulator implements EType {

    /**
     * The field {@code value} contains the encapsulated value.
     */
    private EType value;


    public Accumulator() {

        super();
        this.value = null;
    }

@Override
    public EType add(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.add(t) : null);
    }

@Override
    public EType and(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.and(t) : null);
    }

@Override
    public EType divide(EType t)
            throws CastException,
                UnsupportedException,
                ArithmeticOverflowException {

        return (value != null ? value.divide(t) : null);
    }

@Override
    public TBoolean eq(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.eq(t) : null);
    }

@Override
    public TBoolean ge(EType t) throws CastException, UnsupportedException {

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

@Override
    public TBoolean gt(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.gt(t) : null);
    }

@Override
    public TBoolean le(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.le(t) : null);
    }

@Override
    public TBoolean lt(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.lt(t) : null);
    }

@Override
    public EType multiply(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.multiply(t) : null);
    }

@Override
    public TBoolean ne(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.ne(t) : null);
    }

@Override
    public EType negate() throws UnsupportedException {

        return (value != null ? value.negate() : null);
    }

@Override
    public EType not() {

        return null;
    }

@Override
    public EType or(EType t) throws CastException, UnsupportedException {

        return (value != null ? value.or(t) : null);
    }

@Override
    public EType set(EType t) {

        if (t instanceof Accumulator) {
            value = ((Accumulator) t).value;
        } else {
            value = t;
        }
        return this;
    }

@Override
    public EType subtract(EType t) throws CastException, UnsupportedException {

        value = value.subtract(t);
        return this;
    }

@Override
    public String toString() {

        return value.toString();
    }

}
