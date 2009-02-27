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

import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfConstants;
import org.extex.util.file.random.RandomAccessR;

/**
 * T2 Number: 16-bit two complement number.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class T2Number16 extends T2Number {

    /**
     * the bytes as short-array
     */
    private short[] bytes;

    /**
     * The value.
     */
    private int val;

    /**
     * Create a new object.
     * 
     * @param rar the input
     * @param b0 the b0
     * @throws IOException if an IO-error occurs.
     */
    T2Number16(RandomAccessR rar, int b0) throws IOException {

        super();

        int b1 = rar.readUnsignedByte();
        int b2 = rar.readUnsignedByte();
        val = ((short) (b1 << XtfConstants.SHIFT8) + (short) b2);

        bytes = new short[3];
        bytes[0] = (short) b0;
        bytes[1] = (short) b1;
        bytes[2] = (short) b2;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2CharString#getBytes()
     */
    @Override
    public short[] getBytes() {

        return bytes;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Number#getDouble()
     */
    @Override
    public double getDouble() {

        return val;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2Number#getInteger()
     */
    @Override
    public int getInteger() {

        return val;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.tables.cff.T2CharString#isInteger()
     */
    @Override
    public boolean isInteger() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return String.valueOf(val);
    }

}
