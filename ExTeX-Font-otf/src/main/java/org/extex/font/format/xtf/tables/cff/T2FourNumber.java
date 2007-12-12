/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.cff;

/**
 * Four {@link T2Number},
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2FourNumber {

    /**
     * The value array.
     */
    private T2Number[] val = new T2Number[4];

    /**
     * Creates a new object.
     * 
     * @param a The value a.
     * @param b The value b.
     * @param c The value c.
     * @param d The value d.
     */
    public T2FourNumber(T2Number a, T2Number b, T2Number c, T2Number d) {

        val[0] = a;
        val[1] = b;
        val[2] = c;
        val[3] = d;
    }

    /**
     * Returns the number.
     * 
     * @param idx The index.
     * @return Returns the number.
     */
    public T2Number getNumber(int idx) {

        return val[idx];
    }

    /**
     * Getter for val.
     * 
     * @return the val
     */
    public T2Number[] getVal() {

        return val;
    }

    /**
     * Set the value.
     * 
     * @param t The number.
     * @param idx The index.
     */
    public void setValue(T2Number t, int idx) {

        val[idx] = t;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < val.length; i++) {
            buf.append(" ").append(val[i].toString());
        }

        return buf.toString().trim();
    }

}
