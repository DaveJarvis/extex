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

package org.extex.font.format.xtf.tables.cff;

import java.io.IOException;

import org.extex.util.file.random.RandomAccessR;

/**
 * Double.
 * 
 * <p>
 * A real number operand is provided in addition to integer operands. This
 * operand begins with a byte value of 30 followed by a variable-length sequence
 * of bytes. Each byte is composed of two 4-bit nibbles as defined in Table. The
 * first nibble of a pair is stored in the most significant 4 bits of a byte and
 * the second nibble of a pair is stored in the least significant 4 bits of a
 * byte.
 * </p>
 * <p>
 * Nibble Represents 0 - 9 0 - 9 a . (decimal point) b E c E- d reserved e -
 * (minus) f end of number
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

public class T2Double extends T2Number {

    /**
     * ID: identifier (30)
     */
    static final int ID = 30;

    /**
     * end marker.
     */
    private static final int END = 0xf;

    /**
     * nibble n1.
     */
    private static final int NIBBLE1 = 0xf0;

    /**
     * shift 4
     */
    private static final int SHIFT4 = 4;

    /**
     * max size
     */
    private static final int MAXSIZE = 100;

    /**
     * the bytes as short-array
     */
    private short[] bytes;

    /**
     * the value
     */
    private final double value;

    /**
     * Create a new object.
     * 
     * @param rar the input
     * @param b0 the b0
     * @throws IOException if an IO-error occurs.
     */
    T2Double(RandomAccessR rar, int b0) throws IOException {

        byte[] data = readNibble(rar);
        StringBuilder buf = new StringBuilder();
        int i = 0;
        while (data[i] != END) {
            if (data[i] >= 0 && data[i] <= 9) {
                buf.append(String.valueOf(data[i]));
            } else {
                switch (data[i]) {
                    case 10:
                        buf.append(".");
                        break;
                    case 11:
                        buf.append("E");
                        break;
                    case 12:
                        buf.append("E-");
                        break;
                    case 13:
                        break;
                    case 14:
                        buf.append("-");
                        break;
                    case 15:
                        break;
                    default:
                        break;
                }
            }
            i++;
        }
        try {
            value = Double.parseDouble(buf.toString());
        } catch (NumberFormatException e) {
            throw new T2NumberFormatException(e.getMessage());
        }

    }

@Override
    public short[] getBytes() {

        return bytes;
    }

@Override
    public double getDouble() {

        return value;
    }

@Override
    public int getInteger() {

        return (int) value;
    }

@Override
    public boolean isDouble() {

        return true;
    }

    /**
     * Read all nibbles until 0xf.
     * 
     * @param rar the input
     * @return Return the nibbles
     * @throws IOException if an IO-error occurs.
     */
    private byte[] readNibble(RandomAccessR rar) throws IOException {

        byte[] data = new byte[MAXSIZE];
        short[] sdata = new short[MAXSIZE];
        int i = 0;
        while (true) {
            int b = rar.readUnsignedByte();
            sdata[i] = (short) b;
            int n1 = (b & NIBBLE1) >> SHIFT4;
            int n2 = b & END;
            data[i++] = (byte) n1;
            data[i++] = (byte) n2;
            if (n1 == END || n2 == END) {
                break;
            }
        }
        // copy read values
        bytes = new short[i + 2];
        bytes[0] = ID;
        for (int c = 0; c <= i; c++) {
            bytes[c + 1] = sdata[c];
        }
        return data;
    }

@Override
    public String toString() {

        return String.valueOf(value);
    }
}
