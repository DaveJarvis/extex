/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.type.pair;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.InterpreterNumberFormatException;
import org.extex.typesetter.Typesetter;

import de.dante.extex.interpreter.type.real.Real;

/**
 * Pair (tow real-values).
 *
 * <p>[x-real] [y-real]</p>
 *
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Pair implements Serializable {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * The value x.
     */
    private Real xval;

    /**
     * The value y.
     */
    private Real yval;

    /**
     * Creates a new object.
     */
    public Pair() {

        super();
        xval = new Real(0);
        yval = new Real(0);
    }

    /**
     * Creates a new object.
     *
     * @param x    the x-value
     * @param y    the y-value
     */
    public Pair(Real x, Real y) {

        super();
        xval = x;
        yval = y;
    }

    /**
     * Creates a new object.
     *
     * @param x    the x-value
     * @param y    the y-value
     */
    public Pair(double x, double y) {

        super();
        xval = new Real(x);
        yval = new Real(y);
    }

    /**
     * Creates a new object.
     * Scan the <code>TokenSource</code> for a <code>Pair</code>.
     *
     * @param context   the context
     * @param source    the token source
     * @param typesetter TODO
     * @throws InterpreterException ...
     * @throws ConfigurationException in case of an configuration error
     */
    public Pair(Context context, TokenSource source, Typesetter typesetter)
            throws InterpreterException, ConfigurationException {

        super();
        xval = new Real(context, source, typesetter);
        yval = new Real(context, source, typesetter);
    }

    /**
     * Creates a new object.<p>
     * If the string equlas <code>null</code> or empty, the value is set to zero
     * @param s     the value as String
     * @throws InterpreterException if a NumberFormatException is throws
     */
    public Pair(String s) throws InterpreterException {

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
     * Setter for the x-value.
     * @param x the new x-value
     */
    public void setX(Real x) {

        xval = x;
    }

    /**
     * Setter for the y-value.
     * @param y the new y-value
     */
    public void setY(Real y) {

        xval = y;
    }

    /**
     * Setter for the x-value.
     * @param x the new x-value
     */
    public void setX(double x) {

        xval = new Real(x);
    }

    /**
     * Setter for the y-value.
     * @param y the new y-value
     */
    public void setY(double y) {

        xval = new Real(y);
    }

    /**
     * Getter for the x-value
     * @return the x-value
     */
    public Real getX() {

        return xval;
    }

    /**
     * Getter for the y-value
     * @return the y-value
     */
    public Real getY() {

        return yval;
    }

    /**
     * add
     * @param val the value to add
     */
    public void add(Pair val) {

        xval.add(val.getX());
        yval.add(val.getY());
    }

    /**
     * add
     * @param x the x-value to add
     * @param y the x-value to add
     */
    public void add(Real x, Real y) {

        xval.add(x);
        yval.add(y);
    }

    /**
     * add
     * @param x the x-value to add
     * @param y the x-value to add
     */
    public void add(double x, double y) {

        xval.add(x);
        yval.add(y);
    }

    /**
     * Return the value as <code>String</code>
     * @return the value as <code>String</code>
     */
    public String toString() {

        return xval.toString() + " " + yval.toString();
    }
}