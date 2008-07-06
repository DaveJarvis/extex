/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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
import java.util.ArrayList;
import java.util.List;

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Type�1 dict keys.
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
    public static final int BLUE_FUZZ = 11;

    /**
     * BlueScale.
     */
    public static final int BLUE_SCALE = 9;

    /**
     * BlueShift.
     */
    public static final int BLUE_SHIFT = 10;

    /**
     * BlueValues.
     */
    public static final int BLUE_VALUES = 6;

    /**
     * defaultWidthX.
     */
    public static final int DEFAULT_WIDTH_X = 20;

    /**
     * ExpansionFactor.
     */
    public static final int EXPANSION_FACTOR = 18;

    /**
     * FamilyBlues.
     */
    public static final int FAMILY_BLUES = 8;

    /**
     * FamilyOtherBlues.
     */
    public static final int FAMILY_OTHER_BLUES = 9;

    /**
     * ForceBold
     */
    public static final int FORCES_BOLD = 14;

    /**
     * initialRandomSeed.
     */
    public static final int INITIAL_RANDOM_SEED = 19;

    /**
     * LanguageGroup.
     */
    public static final int LANGUAGE_GROUP = 17;

    /**
     * nominalWidthX.
     */
    public static final int NOMINAL_WIDTH_X = 21;

    /**
     * OtherBlues.
     */
    public static final int OTHER_BLUES = 7;

    /**
     * StdHW.
     */
    public static final int STD_H_W = 10;

    /**
     * StdVW
     */
    public static final int STD_V_W = 11;

    /**
     * StemSnapH.
     */
    public static final int STEM_SNAP_H = 12;

    /**
     * StemSnapV.
     */
    public static final int STEM_SNAP_V = 13;

    /**
     * Subrs.
     */
    public static final int SUBRS = 19;

    /**
     * Create a new instance.
     * 
     * @param rar the input
     * 
     * @return Returns the new T2Operatorr object.
     * @throws IOException if an IO-error occurs.
     */
    public static T1DictKey newInstance(RandomAccessR rar) throws IOException {

        List<T2Number> stack = new ArrayList<T2Number>();

        while (true) {

            int b = rar.readUnsignedByte();

            switch (b) {
                case BLUE_VALUES:
                    return new T1BlueValues(stack);
                case OTHER_BLUES:
                    return new T1OtherBlues(stack);
                case FAMILY_BLUES:
                    return new T1FamilyBlues(stack);
                case FAMILY_OTHER_BLUES:
                    return new T1FamilyOtherBlues(stack);
                case STD_H_W:
                    return new T1StdHW(stack);
                case STD_V_W:
                    return new T1StdVW(stack);
                case ESCAPE_BYTE:
                    int b1 = rar.readUnsignedByte();
                    switch (b1) {
                        case BLUE_SCALE:
                            return new T1BlueScale(stack);
                        case BLUE_SHIFT:
                            return new T1BlueShift(stack);
                        case BLUE_FUZZ:
                            return new T1BlueFuzz(stack);
                        case STEM_SNAP_H:
                            return new T1StemSnapH(stack);
                        case STEM_SNAP_V:
                            return new T1StemSnapV(stack);
                        case FORCES_BOLD:
                            return new T1ForceBold(stack);
                        case LANGUAGE_GROUP:
                            return new T1LanguageGroup(stack);
                        case EXPANSION_FACTOR:
                            return new T1ExpansionFactor(stack);
                        case INITIAL_RANDOM_SEED:
                            return new T1initialRandomSeed(stack);
                        default:
                            throw new T2NotAOperatorException();

                    }
                case SUBRS:
                    return new T1Subrs(stack);
                case DEFAULT_WIDTH_X:
                    return new T1defaultWidthX(stack);
                case NOMINAL_WIDTH_X:
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
     * @see org.extex.font.format.xtf.tables.cff.T2CharString#init(org.extex.util.file.random.RandomAccessR,
     *      org.extex.font.format.xtf.tables.OtfTableCFF, int,
     *      org.extex.font.format.xtf.tables.cff.CffFont)
     */
    @Override
    public void init(RandomAccessR rar, OtfTableCFF cff, int baseoffset,
            CffFont cffFont) throws IOException {

        // do nothing

    }

}
