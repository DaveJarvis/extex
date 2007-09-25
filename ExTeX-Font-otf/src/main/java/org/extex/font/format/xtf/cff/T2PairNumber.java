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

package org.extex.font.format.xtf.cff;

/**
 * A pair of two {@link T2Number},
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class T2PairNumber {

    /**
     * The value a.
     */
    private T2Number a;

    /**
     * The value b.
     */
    private T2Number b;

    /**
     * Creates a new object.
     * 
     * @param a The value a.
     * @param b The value b.
     */
    public T2PairNumber(T2Number a, T2Number b) {

        this.a = a;
        this.b = b;
    }

    /**
     * Getter for a.
     * 
     * @return the a
     */
    public T2Number getA() {

        return a;
    }

    /**
     * Setter for a.
     * 
     * @param a the a to set
     */
    public void setA(T2Number a) {

        this.a = a;
    }

    /**
     * Getter for b.
     * 
     * @return the b
     */
    public T2Number getB() {

        return b;
    }

    /**
     * Setter for b.
     * 
     * @param b the b to set
     */
    public void setB(T2Number b) {

        this.b = b;
    }

    @Override
    public String toString() {

        StringBuffer buf = new StringBuffer(a.toString());
        buf.append(" ").append(b.toString());
        return buf.toString();
    }
}
