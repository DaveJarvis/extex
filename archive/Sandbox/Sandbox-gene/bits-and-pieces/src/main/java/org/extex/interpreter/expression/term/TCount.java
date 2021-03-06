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

import org.extex.core.count.Count;
import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;

/**
 * This class encapsulates a long value for the use in the expression evaluator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TCount extends Count implements EType {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 1L;

    /**
     * Cast a terminal to a double.
     * 
     * @param t the terminal to cast
     * 
     * @return the double encountered
     * 
     * @throws CastException in case of an error
     */
    protected static long castTerminal(EType t) throws CastException {

        if (t instanceof TCount) {
            return ((TCount) t).getValue();
        } else if (t instanceof Accumulator) {
            return castTerminal(((Accumulator) t).getValue());
        } else {
            throw new CastException(t.toString(), LocalizerFactory
                .getLocalizer(TCount.class).format("Name"));
        }
    }

    /**
     * Creates a new object.
     * 
     */
    public TCount() {

        super(0);
    }

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    protected TCount(long value) {

        super(value);
    }

@Override
    public EType add(EType t) throws CastException {

        add(castTerminal(t));
        return this;
    }

@Override
    public EType and(EType t) throws UnsupportedException {

        throw new UnsupportedException("&&", toString());
    }

@Override
    public EType divide(EType t)
            throws CastException,
                ArithmeticOverflowException {

        super.divide(castTerminal(t));
        return this;
    }

@Override
    public TBoolean eq(EType t) throws CastException {

        return new TBoolean(getValue() == castTerminal(t));
    }

@Override
    public TBoolean ge(EType t) throws CastException {

        return new TBoolean(getValue() >= castTerminal(t));
    }

@Override
    public TBoolean gt(EType t) throws CastException {

        return new TBoolean(getValue() > castTerminal(t));
    }

@Override
    public TBoolean le(EType t) throws CastException {

        return new TBoolean(getValue() <= castTerminal(t));
    }

@Override
    public TBoolean lt(EType t) throws CastException {

        return new TBoolean(getValue() < castTerminal(t));
    }

@Override
    public EType multiply(EType t) throws CastException {

        multiply(castTerminal(t));
        return this;
    }

@Override
    public TBoolean ne(EType t) throws CastException {

        return new TBoolean(getValue() != castTerminal(t));
    }

@Override
    public EType negate() {

        set(-getValue());
        return this;
    }

@Override
    public EType not() throws UnsupportedException {

        throw new UnsupportedException("!", toString());
    }

@Override
    public EType or(EType t) throws CastException, UnsupportedException {

        throw new UnsupportedException("||", toString());
    }

public EType parse(CharSequence sequence) {

        long val = 0;
        int length = sequence.length();
        if (length == 0) {
            return null;
        }

        int i = 0;
        char c = sequence.charAt(0);

        for (; i < length; i++) {
            c = sequence.charAt(i);
            switch (c) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    val = val * 10 + c - '0';
                    break;
                case ' ':
                    for (; i < length; i++) {
                        c = sequence.charAt(i);
                        if (c != ' ') {
                            return null;
                        }
                    }
                    break;
                default:
                    return null;
            }
        }
        return new TCount(val);
    }

@Override
    public EType set(EType t) throws CastException {

        set(castTerminal(t));
        return this;
    }

@Override
    public EType subtract(EType t) throws CastException {

        add(-castTerminal(t));
        return this;
    }

@Override
    public String toString() {

        return LocalizerFactory.getLocalizer(TCount.class).format("Format",
            Long.toString(this.getValue()));
    }

}
