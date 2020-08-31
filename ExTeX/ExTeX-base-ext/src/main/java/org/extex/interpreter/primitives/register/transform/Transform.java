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

package org.extex.interpreter.primitives.register.transform;

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
 * Transform (transformation with six values).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class Transform implements Serializable {

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * max values
     */
    private static final int MAXVAL = 6;

    /**
     * T1
     */
    private static final int T1 = 0;

    /**
     * T2
     */
    private static final int T2 = 1;

    /**
     * T3
     */
    private static final int T3 = 2;

    /**
     * T4
     */
    private static final int T4 = 3;

    /**
     * T5
     */
    private static final int T5 = 4;

    /**
     * T6
     */
    private static final int T6 = 5;

    /**
     * The value
     */
    private final Real[] val = new Real[MAXVAL];


    public Transform() {

        for (int i = 0; i < MAXVAL; i++) {
            val[i] = new Real(0);
        }
    }

    /**
     * Creates a new object. Scan the {@code TokenSource} for a
     * {@code Transform}.
     * 
     * @param context the context
     * @param source the token source
     * @param typesetter the typesetter
     * 
     * @throws HelpingException in case of an error
     * @throws ConfigurationException in case of an configuration error
     * @throws TypesetterException in case of an error in the typsesetter
     */
    public Transform(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException,
                ConfigurationException,
                TypesetterException {

        for (int i = 0; i < MAXVAL; i++) {
            val[i] = new Real(context, source, typesetter);
        }
    }

    /**
     * Creates a new object.
     * 
     * @param v1 the value 1
     * @param v2 the value 2
     * @param v3 the value 3
     * @param v4 the value 4
     * @param v5 the value 5
     * @param v6 the value 6
     */
    public Transform(double v1, double v2, double v3, double v4, double v5,
            double v6) {

        val[T1] = new Real(v1);
        val[T2] = new Real(v2);
        val[T3] = new Real(v3);
        val[T4] = new Real(v4);
        val[T5] = new Real(v5);
        val[T6] = new Real(v6);
    }

    /**
     * Creates a new object.
     * 
     * @param v1 the value 1
     * @param v2 the value 2
     * @param v3 the value 3
     * @param v4 the value 4
     * @param v5 the value 5
     * @param v6 the value 6
     */
    public Transform(Real v1, Real v2, Real v3, Real v4, Real v5, Real v6) {

        val[T1] = v1;
        val[T2] = v2;
        val[T3] = v3;
        val[T4] = v4;
        val[T5] = v5;
        val[T6] = v6;
    }

    /**
     * Creates a new object.
     * <p>
     * If the string equals {@code null} or empty, the value is set to zero
     * 
     * @param s the value as String
     * 
     * @throws HelpingException if a NumberFormatException is thrown
     */
    public Transform(String s) throws HelpingException {

        if (s == null || s.trim().length() == 0) {
            for (int i = 0; i < MAXVAL; i++) {
                val[i] = new Real(0);
            }
        } else {

            try {
                StringTokenizer st = new StringTokenizer(s);
                for (int i = 0; i < MAXVAL; i++) {
                    if (st.hasMoreTokens()) {
                        val[i] = new Real(st.nextToken());
                    } else {
                        throw new InterpreterNumberFormatException(s);
                    }
                }
            } catch (NumberFormatException e) {
                throw new InterpreterNumberFormatException(s);
            }
        }
    }

    /**
     * Return the idx-value for the transfomation
     * 
     * @param idx the number of the value
     * @return the value on index idx
     */
    public Real get(int idx) {

        if (idx >= 0 && idx < MAXVAL) {
            return val[idx];
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Setter for the value with index idx
     * 
     * @param idx the index
     * @param value the value with index
     */
    public void set(int idx, Real value) {

        if (idx >= 0 && idx < MAXVAL) {
            val[idx] = value;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * Setter for the value.
     * 
     * @param value the new value
     */
    public void set(Transform value) {

        for (int i = 0; i < MAXVAL; i++) {
            val[i] = value.get(i);
        }
    }

    /**
     * Return the value as {@code String}
     * 
     * @return the value as {@code String}
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < MAXVAL; i++) {
            sb.append(val[i].toString());
            if (i < MAXVAL - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
