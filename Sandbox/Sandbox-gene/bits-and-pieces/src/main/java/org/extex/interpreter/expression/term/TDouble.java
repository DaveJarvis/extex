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
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.expression.EType;
import org.extex.interpreter.expression.exception.CastException;
import org.extex.interpreter.expression.exception.UnsupportedException;

/*
 * This implementation of a Terminal encapsulates a floating point number.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision: 4733 $
 */
/*
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision$
 */
public class TDouble implements EType {

    /**
     * Cast a terminal to a double.
     *
     * @param t the terminal to cast
     *
     * @return the double encountered
     *
     * @throws CastException in case of an error
     */
    protected static double castTerminal(EType t) throws CastException {

        if (t instanceof TDouble) {
            return ((TDouble) t).getValue();
        } else if (t instanceof TCount) {
            return ((TCount) t).getValue();
        } else if (t instanceof Accumulator) {
            return castTerminal(((Accumulator) t).getValue());
        } else {
            throw new CastException(t.toString(), LocalizerFactory
                    .getLocalizer(TDouble.class).format("Name"));
        }
    }

    /**
     * The field <tt>value</tt> contains the value contained within this
     * instance.
     */
    private double value = 0.;

    /**
     * Creates a new object.
     *
     */
    public TDouble() {

        super();
    }

    /**
     * Creates a new object.
     *
     * @param d the value
     */
    public TDouble(double d) {

        super();
        this.value = d;
    }

    /**
     * @see org.extex.interpreter.expression.EType#add(
     *      org.extex.interpreter.expression.EType)
     */
    public EType add(EType t) throws CastException {

        this.value += castTerminal(t);
        return this;
    }

    /**
     * @see org.extex.interpreter.expression.EType#and(
     *      org.extex.interpreter.expression.EType)
     */
    public EType and(EType t) throws CastException, UnsupportedException {

        throw new UnsupportedException("&&", toString());
    }

    /**
     * @see org.extex.interpreter.expression.EType#divide(
     *      org.extex.interpreter.expression.EType)
     */
    public EType divide(EType t)
            throws CastException,
                ArithmeticOverflowException {

        double x = castTerminal(t);
        if (x == 0.) {
            throw new ArithmeticOverflowException("");
        }
        this.value /= x;
        return this;
    }

    /**
     * @see org.extex.interpreter.expression.EType#eq(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean eq(EType t) throws CastException {

        return new TBoolean(this.value == castTerminal(t));
    }

    /**
     * @see org.extex.interpreter.expression.EType#ge(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean ge(EType t) throws CastException {

        return new TBoolean(this.value >= castTerminal(t));
    }

    /*
     * Getter for value.
     * 
     * @return the value
     * 
     * @uml.property name="value"
     */
    /*
     * TODO gene: missing JavaDoc
     * 
     * @return
     * 
     * @uml.property name="value"
     */
    public double getValue() {

        return this.value;
    }

    /**
     * @see org.extex.interpreter.expression.EType#gt(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean gt(EType t) throws CastException {

        return new TBoolean(this.value > castTerminal(t));
    }

    /**
     * @see org.extex.interpreter.expression.EType#le(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean le(EType t) throws CastException {

        return new TBoolean(this.value <= castTerminal(t));
    }

    /**
     * @see org.extex.interpreter.expression.EType#lt(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean lt(EType t) throws CastException {

        return new TBoolean(this.value < castTerminal(t));
    }

    /**
     * @see org.extex.interpreter.expression.EType#multiply(
     *      org.extex.interpreter.expression.EType)
     */
    public EType multiply(EType t) throws CastException {

        this.value *= castTerminal(t);
        return this;
    }

    /**
     * @see org.extex.interpreter.expression.EType#ne(
     *      org.extex.interpreter.expression.EType)
     */
    public TBoolean ne(EType t) throws CastException {

        return new TBoolean(this.value != castTerminal(t));
    }

    /**
     * @see org.extex.interpreter.expression.EType#negate()
     */
    public EType negate() {

        value = -value;
        return this;
    }

    /**
     * @see org.extex.interpreter.expression.EType#not()
     */
    public EType not() throws UnsupportedException {

        throw new UnsupportedException("!", toString());
    }

    /**
     * @see org.extex.interpreter.expression.EType#or(
     *      org.extex.interpreter.expression.EType)
     */
    public EType or(EType t) throws CastException, UnsupportedException {

        throw new UnsupportedException("||", toString());
    }

//    /**
//     * @see org.extex.interpreter.expression.EType#parse(
//     *      java.lang.CharSequence)
//     */
//    public EType parse(CharSequence sequence) {
//
//        try {
//            this.value = Double.parseDouble(sequence.toString());
//            return this;
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }

    /**
     * @see org.extex.interpreter.expression.EType#set(
     *      org.extex.interpreter.expression.EType)
     */
    public EType set(EType t) throws CastException {

        this.value = castTerminal(t);
        return this;
    }

    /*
     * Setter for value.
     * 
     * @param value the value to set
     * 
     * @uml.property name="value"
     */
    /*
     * TODO gene: missing JavaDoc
     * 
     * @param value
     * 
     * @uml.property name="value"
     */
    protected void setValue(double value) {

        this.value = value;
    }

    /**
     * @see org.extex.interpreter.expression.EType#subtract(
     *      org.extex.interpreter.expression.EType)
     */
    public EType subtract(EType t) throws CastException {

        this.value -= castTerminal(t);
        return null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return LocalizerFactory.getLocalizer(TDouble.class).format("Format",
                Double.toString(this.value));
    }

}
