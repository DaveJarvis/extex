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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.extex.font.format.xtf.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Type 1 dict keys.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class T1DictKey extends T2CharString
        implements
            XMLWriterConvertible {

    /**
     * BlueFuzz.
     */
    public static final int BlueFuzz = 11;

    /**
     * BlueScale.
     */
    public static final int BlueScale = 9;

    /**
     * BlueShift.
     */
    public static final int BlueShift = 10;

    /**
     * BlueValues.
     */
    public static final int BlueValues = 6;

    /**
     * defaultWidthX.
     */
    public static final int defaultWidthX = 20;

    /**
     * ExpansionFactor.
     */
    public static final int ExpansionFactor = 18;

    /**
     * FamilyBlues.
     */
    public static final int FamilyBlues = 8;

    /**
     * FamilyOtherBlues.
     */
    public static final int FamilyOtherBlues = 9;

    /**
     * ForceBold
     */
    public static final int ForceBold = 14;

    /**
     * initialRandomSeed.
     */
    public static final int initialRandomSeed = 19;

    /**
     * LanguageGroup.
     */
    public static final int LanguageGroup = 17;

    /**
     * nominalWidthX.
     */
    public static final int nominalWidthX = 21;

    /**
     * OtherBlues.
     */
    public static final int OtherBlues = 7;

    /**
     * StdHW.
     */
    public static final int StdHW = 10;

    /**
     * StdVW
     */
    public static final int StdVW = 11;

    /**
     * StemSnapH.
     */
    public static final int StemSnapH = 12;

    /**
     * StemSnapV.
     */
    public static final int StemSnapV = 13;

    /**
     * Subrs.
     */
    public static final int Subrs = 19;

    /**
     * Create a new instance.
     * 
     * @param rar the input
     * @return Returns the new T2Operatorr object.
     * @throws IOException if an IO-error occurs.
     */
    public static T1DictKey newInstance(RandomAccessR rar) throws IOException {

        List<T2Number> stack = new ArrayList<T2Number>();

        while (true) {

            int b = rar.readUnsignedByte();

            switch (b) {
                case BlueValues:
                    return new T1BlueValues(stack);
                case OtherBlues:
                    return new T1OtherBlues(stack);
                case FamilyBlues:
                    return new T1FamilyBlues(stack);
                case FamilyOtherBlues:
                    return new T1FamilyOtherBlues(stack);
                case StdHW:
                    return new T1StdHW(stack);
                case StdVW:
                    return new T1StdVW(stack);
                case ESCAPE_BYTE:
                    int b1 = rar.readUnsignedByte();
                    switch (b1) {
                        case BlueScale:
                            return new T1BlueScale(stack);
                        case BlueShift:
                            return new T1BlueShift(stack);
                        case BlueFuzz:
                            return new T1BlueFuzz(stack);
                        case StemSnapH:
                            return new T1StemSnapH(stack);
                        case StemSnapV:
                            return new T1StemSnapV(stack);
                        case ForceBold:
                            return new T1ForceBold(stack);
                        case LanguageGroup:
                            return new T1LanguageGroup(stack);
                        case ExpansionFactor:
                            return new T1ExpansionFactor(stack);
                        case initialRandomSeed:
                            return new T1initialRandomSeed(stack);
                        default:
                            throw new T2NotAOperatorException();

                    }
                case Subrs:
                    return new T1Subrs(stack);
                case defaultWidthX:
                    return new T1defaultWidthX(stack);
                case nominalWidthX:
                    return new T1nominalWidthX(stack);
                default:
                    // number
                    T2Number number = T2CharString.readNumber(rar, b);
                    stack.add(number);
                    break;
            }
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
     * @see org.extex.font.format.xtf.cff.T2CharString#init(org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.OtfTableCFF, int,
     *      org.extex.font.format.xtf.cff.CffFont)
     */
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset,
            CffFont cffFont) throws IOException {

        // do nothing

    }

}
