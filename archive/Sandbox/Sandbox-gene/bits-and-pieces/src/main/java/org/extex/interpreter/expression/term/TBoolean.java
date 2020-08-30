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

import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;

/**
 * This class encapsulates a boolean value for the use in the expression
 * evaluator.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
*/
public class TBoolean implements EType {

    /**
     * Cast a terminal to a double.
     * 
     * @param t the terminal to cast
     * 
     * @return the double encountered
     * 
     * @throws CastException in case of an error
     */
    protected static boolean castTerminal(EType t) throws CastException {

        if (t instanceof TBoolean) {
            return ((TBoolean) t).isValue();
        } else if (t instanceof Accumulator) {
            return castTerminal(((Accumulator) t).getValue());
        } else {
            throw new CastException(t.toString(), LocalizerFactory
                .getLocalizer(TBoolean.class).format("Name"));
        }
    }

    /**
     * The field {@code value} contains the value.
     */
    private boolean value;


    public TBoolean() {

        super();
        this.value = false;
    }

    /**
     * Creates a new object.
     * 
     * @param value the initial value
     */
    public TBoolean(boolean value) {

        super();
        this.value = value;
    }

@Override
    public EType add(EType t) throws UnsupportedException {

        throw new UnsupportedException("+", toString());
    }

@Override
    public EType and(EType t) throws CastException {

        this.value &= castTerminal(t);
        return this;
    }

@Override
    public EType divide(EType t) throws CastException, UnsupportedException {

        throw new UnsupportedException("/", toString());
    }

@Override
    public TBoolean eq(EType t) throws UnsupportedException {

        throw new UnsupportedException("eq", toString());
    }

@Override
    public TBoolean ge(EType t) throws UnsupportedException {

        throw new UnsupportedException("ge", toString());
    }

@Override
    public TBoolean gt(EType t) throws UnsupportedException {

        throw new UnsupportedException("gt", toString());
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public boolean isValue() {

        return this.value;
    }

@Override
    public TBoolean le(EType t) throws UnsupportedException {

        throw new UnsupportedException("le", toString());
    }

@Override
    public TBoolean lt(EType t) throws UnsupportedException {

        throw new UnsupportedException("lt", toString());
    }

@Override
    public EType multiply(EType t) throws CastException, UnsupportedException {

        throw new UnsupportedException("*", toString());
    }

@Override
    public TBoolean ne(EType t) throws UnsupportedException {

        throw new UnsupportedException("ne", toString());
    }

@Override
    public EType negate() throws UnsupportedException {

        throw new UnsupportedException("-", toString());
    }

@Override
    public EType not() throws UnsupportedException {

        value = !value;
        return this;
    }

@Override
    public EType or(EType t) throws CastException {

        this.value |= castTerminal(t);
        return this;
    }

public EType parse(CharSequence sequence) {

        switch (sequence.length()) {
            case 4:
                if (sequence.charAt(0) == 't' && sequence.charAt(1) == 'r'
                        && sequence.charAt(1) == 'u'
                        && sequence.charAt(1) == 'e') {
                    return new TBoolean(true);
                }
                break;
            case 5:
                if (sequence.charAt(0) == 'f' && sequence.charAt(1) == 'a'
                        && sequence.charAt(1) == 'l'
                        && sequence.charAt(1) == 's'
                        && sequence.charAt(1) == 'e') {
                    return new TBoolean(false);
                }
                break;
        }
        return null;
    }

@Override
    public EType set(EType t) throws CastException {

        this.value = castTerminal(t);
        return this;
    }

    /**
     * Setter for value.
     * 
     * @param value the value to set
     */
    public void setValue(boolean value) {

        this.value = value;
    }

@Override
    public EType subtract(EType t) throws UnsupportedException {

        throw new UnsupportedException("-", toString());
    }

@Override
    public String toString() {

        return LocalizerFactory.getLocalizer(TBoolean.class).format("Format",
            Boolean.toString(this.value));
    }

}
