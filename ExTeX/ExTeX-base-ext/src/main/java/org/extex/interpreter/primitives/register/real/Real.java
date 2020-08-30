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

package org.extex.interpreter.primitives.register.real;

import java.io.Serializable;

import org.extex.core.exception.helping.ArithmeticOverflowException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.MissingNumberException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.parser.DimenConvertible;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * Real (with a double value).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Real implements Serializable {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ZERO-Real
     */
    public static final Real ZERO = new ImmutableReal(0);

    /**
     * max-Real
     */
    public static final Real MAX_VALUE = new ImmutableReal(Double.MAX_VALUE);

    /**
     * The value
     */
    private double value = 0.0d;

    /**
     * Creates a new object.
     * 
     * Scan the {@code TokenSource} for a {@code Real}.
     * 
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public Real(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        value = scanReal(context, source, typesetter);
    }

    /**
     * Creates a new object.
     * 
     * @param val initialize with double value
     */
    public Real(double val) {

        value = val;
    }

    /**
     * Creates a new object.
     * 
     * @param val the value as float
     */
    public Real(float val) {

        value = val;
    }

    /**
     * Creates a new object.
     * 
     * @param i the value as int
     */
    public Real(int i) {

        value = i;
    }

    /**
     * Creates a new object.
     * 
     * @param l the value as long
     */
    public Real(long l) {

        value = l;
    }

    /**
     * Creates a new object.
     * 
     * @param val the value
     */
    public Real(Real val) {

        value = val.getValue();
    }

    /**
     * Creates a new object.
     * <p>
     * If the string equals {@code null} or empty, the value is set to zero
     * 
     * @param s the value as String
     * 
     * @throws HelpingException if a NumberFormatException is throws
     */
    public Real(String s) throws HelpingException {

        if (s == null || s.trim().length() == 0) {
            value = 0.0d;
        } else {

            try {
                value = Double.valueOf(s).doubleValue();
            } catch (NumberFormatException e) {
                throw new MissingNumberException();
            }
        }
    }

    /**
     * add
     * 
     * @param val the value to add
     */
    public void add(double val) {

        value += val;
    }

    /**
     * add
     * 
     * @param real the value to add
     */
    public void add(Real real) {

        value += real.getValue();
    }

    /**
     * divide
     * 
     * @param val the value to divide
     * 
     * @throws HelpingException in case of a division by zero
     */
    public void divide(double val) throws HelpingException {

        if (val == 0.0d) {
            throw new ArithmeticOverflowException("");
        }

        value /= val;
    }

    /**
     * divide
     * 
     * @param val the value to divide
     * 
     * @throws HelpingException in case of a division by zero
     */
    public void divide(Real val) throws HelpingException {

        divide(val.getValue());
    }

    /**
     * Return the value as long.
     * 
     * @return the value as long
     */
    public long getLong() {

        return (long) value;
    }

    /**
     * Getter for the value
     * 
     * @return the value
     */
    public double getValue() {

        return value;
    }

    /**
     * multiply
     * 
     * @param val the value to multiply
     */
    public void multiply(double val) {

        value *= val;
    }

    /**
     * multiply
     * 
     * @param val the value to multiply
     */
    public void multiply(Real val) {

        value *= val.getValue();
    }

    /**
     * Scan the input stream for tokens making up a {@code Real}.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the {@code Real}-value
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private double scanReal(Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        long val = 0;
        boolean neg = false;

        // get number
        Token t = source.scanNonSpace(context);
        if (t == null) {
            throw new MissingNumberException();
        } else if (t.eq(Catcode.OTHER, "-")) {
            neg = true;
            t = source.scanNonSpace(context);
        } else if (t.eq(Catcode.OTHER, "+")) {
            t = source.scanNonSpace(context);
        } else if (t instanceof ControlSequenceToken) {
            Code code = context.getCode((ControlSequenceToken) t);
            if (code instanceof RealConvertible) {
                return (((RealConvertible) code).convertReal(context, source,
                    typesetter)).getValue();
            } else if (code instanceof CountConvertible) {
                return (new Real(((CountConvertible) code).convertCount(
                    context, source, null))).getValue();
            } else if (code instanceof DimenConvertible) {
                return (new Real(((DimenConvertible) code).convertDimen(
                    context, source, null))).getValue();
            }
        }

        StringBuilder sb = new StringBuilder();
        if (neg) {
            sb.append('-');
        }

        if (t != null && !t.eq(Catcode.OTHER, ".") && !t.eq(Catcode.OTHER, ",")) {
            source.push(t);
            val = source.parseNumber(context, source, typesetter);
            t = source.getToken(context);
        }

        sb.append(Long.toString(val));
        sb.append('.');
        val = 0;

        if (t != null && (t.eq(Catcode.OTHER, ".") || t.eq(Catcode.OTHER, ","))) {
            val = source.parseNumber(context, source, typesetter);
        } else {
            source.push(t);
        }
        sb.append(Long.toString(val));

        return (new Real(sb.toString())).getValue();
    }

    /**
     * Setter for the value.
     * 
     * @param d the new value
     */
    public void setValue(double d) {

        value = d;
    }

    /**
     * Return the value as {@code String}
     * 
     * @return the value as {@code String}
     */
    @Override
    public String toString() {

        return Double.toString(value);
    }
}
