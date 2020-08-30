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

package org.extex.font.format.xtf.tables.gps;

/**
 * Class for a pair of {@link ValueRecord}s.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class PairValue {

    /**
     * The value 1.
     */
    private final ValueRecord value1;

    /**
     * The value 2.
     */
    private final ValueRecord value2;

    /**
     * Creates a new object.
     * 
     * @param val1 The value 1.
     * @param val2 The value 2.
     */
    public PairValue(ValueRecord val1, ValueRecord val2) {

        value1 = val1;
        value2 = val2;
    }

    /**
     * Getter for value1.
     * 
     * @return the value1
     */
    public ValueRecord getValue1() {

        return value1;
    }

    /**
     * Getter for value2.
     * 
     * @return the value2
     */
    public ValueRecord getValue2() {

        return value2;
    }

}
