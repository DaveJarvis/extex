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

package org.extex.interpreter.primitives.register.bool;

import java.io.Serializable;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.parser.CountConvertible;
import org.extex.interpreter.primitives.register.real.RealConvertible;
import org.extex.interpreter.type.Code;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * The register 'bool'.
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Bool implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The value
     */
    private boolean value = false;

    /**
     * Creates a new object.
     */
    public Bool() {

    }

    /**
     * Creates a new object.
     * 
     * @param val initial boolean value
     */
    public Bool(boolean val) {

        value = val;
    }

    /**
     * Creates a new object. Scan the <code>TokenSource</code> for a
     * <code>Bool</code>.
     * 
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error.
     * @throws ConfigurationException in case if a configuration error
     * @throws TypesetterException in case of an error
     */
    public Bool(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        value = scanBool(context, source, typesetter);
    }

    /**
     * Creates a new object.
     * <p>
     * 0 -> false
     * </p>
     * <p>!= -> true
     * </p>
     * 
     * @param l the value as long
     */
    public Bool(long l) {

        if (l == 0) {
            value = false;
        } else {
            value = true;
        }
    }

    /**
     * Creates a new object. Possible values are <tt>true</tt>,
     * <tt>false</tt> or <tt>on</tt>, <tt>off</tt> or <tt>!0</tt>,
     * <tt>0</tt>
     * 
     * @param s the value as String
     * @throws HelpingException if no boolean-value are found
     */
    public Bool(String s) throws HelpingException {

        if ("true".equalsIgnoreCase(s)) {
            value = true;
        } else if ("on".equalsIgnoreCase(s)) {
            value = true;
        } else if ("off".equalsIgnoreCase(s)) {
            value = false;
        } else if ("false".equalsIgnoreCase(s)) {
            value = false;
        } else if (s.trim().length() == 0) {
            value = false;
        } else if (Integer.parseInt(s) == 0) {
            value = false;
        } else if (Integer.parseInt(s) != 0) {
            value = true;
        } else {
            throw new InterpreterNoBoolValueException(s);
        }
    }

    /**
     * Getter for the value
     * 
     * @return the value
     */
    public boolean getValue() {

        return value;
    }

    /**
     * not
     */
    public void not() {

        value = !value;
    }

    /**
     * Scan the input stream for tokens making up a <code>Bool</code>.
     * 
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @return the boolean value
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case if a configuration error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private boolean scanBool(Context context, TokenSource source,
            Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        Token tok = source.scanNonSpace(context);

        if (tok == null) {
            throw new InterpreterNoBoolValueException();
        } else if (tok instanceof ControlSequenceToken) {
            Code code = context.getCode((ControlSequenceToken) tok);
            if (code instanceof CountConvertible) {
                return (new Bool(((CountConvertible) code).convertCount(
                    context, source, null))).getValue();
            } else if (code instanceof RealConvertible) {
                return (new Bool(((((RealConvertible) code).convertReal(
                    context, source, typesetter)).getLong()))).getValue();
            }
            throw new InterpreterNoBoolValueException();
        }

        source.push(tok);
        if (tok.getChar().isDigit()) {
            return (new Bool(source.parseInteger(context, source, null)))
                .getValue();
        }

        if (source.getKeyword(context, "true")) {
            return true;
        } else if (source.getKeyword(context, "false")) {
            return false;
        } else if (source.getKeyword(context, "on")) {
            return true;
        } else if (source.getKeyword(context, "off")) {
            return false;
        }
        throw new InterpreterNoBoolValueException();
    }

    /**
     * Setter for the value.
     * 
     * @param b the new value
     */
    public void setValue(boolean b) {

        value = b;
    }

    /**
     * Return the value as <code>String</code>
     * 
     * @return the value as <code>String</code>
     */
    @Override
    public String toString() {

        return Boolean.toString(value);
    }
}
