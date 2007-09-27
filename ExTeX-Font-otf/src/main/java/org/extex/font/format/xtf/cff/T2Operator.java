/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Operator.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public abstract class T2Operator extends T2CharString
        implements
            XMLWriterConvertible {

    /**
     * Create a new instance.
     * 
     * @param rar the input
     * @param cffFont The cff font.
     * @return Returns the new T2Operatorr object.
     * @throws IOException if an IO-error occurs.
     */
    public static T2Operator newInstance(RandomAccessR rar, CharString ch)
            throws IOException {

        List<T2CharString> stack = new ArrayList<T2CharString>();

        while (true) {

            int b0 = rar.readUnsignedByte();

            switch (b0) {
                case 0:
                    return new T2Dummy(stack, ch, "Reserved"); // Reserved
                case T2HSTEM:
                    return new T2Hstem(stack, ch);
                case 2:
                    return new T2Dummy(stack, ch, "Reserved"); // Reserved
                case T2VSTEM:
                    return new T2Vstem(stack, ch);
                case T2VMOVETO:
                    return new T2VMoveTo(stack, ch);
                case T2RLINETO:
                    return new T2RLineTo(stack, ch);
                case T2HLINETO:
                    return new T2HLineTo(stack, ch);
                case T2VLINETO:
                    return new T2VLineTo(stack, ch);
                case T2RRCURVETO:
                    return new T2RrCurveTo(stack, ch);
                case 9:
                    return new T2Dummy(stack, ch, "Reserved");// Reserved
                case 10:
                    return new T2Dummy(stack, ch, "callsubr"); // callsubr
                case 11:
                    break; // return
                case ESCAPE_BYTE:
                    int b1 = rar.readUnsignedByte();

                    return new T2Dummy(stack, ch, "escape");
                case 13:
                    return new T2Dummy(stack, ch, "Reserved"); // Reserved
                case 14:
                    return new T2Dummy(stack, ch, "endchar"); // endchar
                case 15:
                    return new T2Dummy(stack, ch, "Reserved"); // Reserved
                case 16:
                    return new T2Dummy(stack, ch, "Reserved"); // Reserved
                case 17:
                    return new T2Dummy(stack, ch, "Reserved"); // Reserved
                case T2HSTEMHM:
                    return new T2HstemHm(stack, ch);
                case T2HINTMASK:
                    return new T2HintMask(stack, ch, rar);
                case 20:
                    return new T2Dummy(stack, ch, "cntrmask"); // cntrmask
                case T2RMOVETO:
                    return new T2RMoveTo(stack, ch);
                case T2HMOVETO:
                    return new T2HMoveTo(stack, ch);
                case T2VSTEMHM:
                    return new T2VstemHm(stack, ch);
                case 24:
                    return new T2Dummy(stack, ch, "rcurveline");// rcurveline
                case 25:
                    return new T2Dummy(stack, ch, "rlinecurve"); // rlinecurve
                case 26:
                    return new T2Dummy(stack, ch, "vvcurveto"); // vvcurveto
                case 27:
                    return new T2Dummy(stack, ch, "hhcurveto"); // hhcurveto
                case 28:
                    return new T2Dummy(stack, ch, "shortint"); // shortint
                case 29:
                    return new T2Dummy(stack, ch, "callgsubr"); // callgsubr
                case 30:
                    return new T2Dummy(stack, ch, "vhcurveto"); // vhcurveto
                case 31:
                    return new T2Dummy(stack, ch, "hvcurveto"); // hvcurveto
                default:
                    // number
                    T2Number number = T2CharString.readNumber(rar, b0);
                    stack.add(number);
                    break;
            }
        }
    }

    /**
     * Create a new object.
     */
    protected T2Operator() {

        super();
    }

    /**
     * Check, if a width is set.
     * 
     * <p>
     * The first stack-clearing operator, which must be one of hstem, hstemhm,
     * vstem, vstemhm, cntrmask, hintmask, hmoveto, vmoveto, rmoveto, or
     * endchar, takes an additional argument - the width (as described earlier),
     * which may be expressed as zero or one numeric argument.
     * </p>
     * 
     * @param stack The stack.
     * @param ch The char string.
     * @throws T2MissingNumberException if an error occurred.
     */
    protected void checkWidth(List<T2CharString> stack, CharString ch)
            throws T2MissingNumberException {

        // if (ch.getWidth() != null) {
        // throw new T2MissingNumberException();
        // }
        T2CharString w = stack.get(0);
        if (w instanceof T2Number) {
            ch.setWidth((T2Number) w);
            stack.remove(0);
        } else {
            throw new T2MissingNumberException();
        }
    }

    /**
     * Convert a stack (a List) into an array and add at the top the id-array.
     * 
     * @param stack the stack
     * @param id the id-array
     * @return Return the byte-array
     */
    protected short[] convertStackaddID(List<? extends T2CharString> stack,
            short[] id) {

        // calculate size
        int size = id.length;

        for (int i = 0; i < stack.size(); i++) {
            T2CharString obj = stack.get(i);
            short[] tmp = obj.getBytes();
            if (tmp != null) {
                size += tmp.length;
            }
        }

        // copy elements
        short[] bytes = new short[size];
        System.arraycopy(id, 0, bytes, 0, id.length);

        int pos = id.length;
        for (int i = 0; i < stack.size(); i++) {
            T2CharString obj = stack.get(i);
            short[] tmp = obj.getBytes();
            if (tmp != null) {
                System.arraycopy(obj.getBytes(), 0, bytes, pos, tmp.length);
                pos += tmp.length;
            }
        }
        return bytes;
    }

    /**
     * Returns the id of the operator.
     * 
     * @return Returns the id of the operator.
     */
    public abstract int getID();

    /**
     * Return the name of the operator.
     * 
     * @return Return the name of the operator.
     */
    public abstract String getName();

    /**
     * Returns the value of the operator.
     * 
     * @return Returns the value of the operator.
     */
    public abstract Object getValue();

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2CharString#init(
     *      org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.OtfTableCFF, int)
     */
    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset,
            CffFont cffFont) throws IOException {

        // do nothing
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.xtf.cff.T2CharString#isOperator()
     */
    @Override
    public boolean isOperator() {

        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return getName();
    }

}
