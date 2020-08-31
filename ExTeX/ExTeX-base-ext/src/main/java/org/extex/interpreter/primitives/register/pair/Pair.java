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

package org.extex.interpreter.primitives.register.pair;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.InterpreterNumberFormatException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.primitives.register.real.Real;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * Pair (tow real-values).
 * 
 * <p>
 * [x-real] [y-real]
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Pair implements Serializable {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The value x.
     */
    private Real xval;

    /**
     * The value y.
     */
    private final Real yval;


    public Pair() {

        xval = new Real(0);
        yval = new Real(0);
    }

    /**
     * Creates a new object. Scan the {@code TokenSource} for a
     * {@code Pair}.
     * 
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public Pair(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        xval = new Real(context, source, typesetter);
        yval = new Real(context, source, typesetter);
    }

    /**
     * Creates a new object.
     * 
     * @param x the x-value
     * @param y the y-value
     */
    public Pair(double x, double y) {

        xval = new Real(x);
        yval = new Real(y);
    }

    /**
     * Creates a new object.
     * 
     * @param x the x-value
     * @param y the y-value
     */
    public Pair(Real x, Real y) {

        xval = x;
        yval = y;
    }

    /**
     * Creates a new object.
     * <p>
     * If the string equals {@code null} or empty, the value is set to
     * zero
     * 
     * @param s the value as String
     * @throws HelpingException if a NumberFormatException is throws
     */
    public Pair(String s) throws HelpingException {

        if (s == null || s.trim().length() == 0) {
            xval = new Real(0);
            yval = new Real(0);
        } else {

            try {
                StringTokenizer st = new StringTokenizer(s);
                if (st.hasMoreTokens()) {
                    xval = new Real(st.nextToken());
                } else {
                    throw new InterpreterNumberFormatException(s);
                }
                if (st.hasMoreTokens()) {
                    yval = new Real(st.nextToken());
                } else {
                    throw new InterpreterNumberFormatException(s);
                }
            } catch (NumberFormatException e) {
                throw new InterpreterNumberFormatException(s);
            }
        }
    }

    /**
     * add
     * 
     * @param x the x-value to add
     * @param y the x-value to add
     */
    public void add(double x, double y) {

        xval.add(x);
        yval.add(y);
    }

    /**
     * add
     * 
     * @param val the value to add
     */
    public void add(Pair val) {

        xval.add(val.getX());
        yval.add(val.getY());
    }

    /**
     * add
     * 
     * @param x the x-value to add
     * @param y the x-value to add
     */
    public void add(Real x, Real y) {

        xval.add(x);
        yval.add(y);
    }

    /**
     * Getter for the x-value
     * 
     * @return the x-value
     */
    public Real getX() {

        return xval;
    }

    /**
     * Getter for the y-value
     * 
     * @return the y-value
     */
    public Real getY() {

        return yval;
    }

    /**
     * Setter for the x-value.
     * 
     * @param x the new x-value
     */
    public void setX(double x) {

        xval = new Real(x);
    }

    /**
     * Setter for the x-value.
     * 
     * @param x the new x-value
     */
    public void setX(Real x) {

        xval = x;
    }

    /**
     * Setter for the y-value.
     * 
     * @param y the new y-value
     */
    public void setY(double y) {

        xval = new Real(y);
    }

    /**
     * Setter for the y-value.
     * 
     * @param y the new y-value
     */
    public void setY(Real y) {

        xval = y;
    }

    /**
     * Return the value as {@code String}
     * 
     * @return the value as {@code String}
     */
    @Override
    public String toString() {

        return xval.toString() + " " + yval.toString();
    }
}
