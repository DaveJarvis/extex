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

package org.extex.font.format.xtf;

import java.io.IOException;

import org.extex.util.file.random.RandomAccessR;

/**
 * Lookup Type 1: Single Adjustment Positioning Subtable.
 * <p>
 * A single adjustment positioning subtable (SinglePos) is used to adjust the
 * position of a single glyph, such as a subscript or superscript. In addition,
 * a SinglePos subtable is commonly used to implement lookup data for contextual
 * positioning. <br/> A SinglePos subtable will have one of two formats: one
 * that applies the same adjustment to a series of glyphs, or one that applies a
 * different adjustment for each unique glyph. <br/> Single Adjustment
 * Positioning: Format 1 <br/> A SinglePosFormat1 subtable applies the same
 * positioning value or values to each glyph listed in its Coverage table. For
 * instance, when a font uses old-style numerals, this format could be applied
 * to uniformly lower the position of all math operator glyphs. <br/> The Format
 * 1 subtable consists of a format identifier (PosFormat), an offset to a
 * Coverage table that defines the glyphs to be adjusted by the positioning
 * values (Coverage), and the format identifier (ValueFormat) that describes the
 * amount and kinds of data in the ValueRecord. <br/> The ValueRecord specifies
 * one or more positioning values to be applied to all covered glyphs (Value).
 * For example, if all glyphs in the Coverage table require both horizontal and
 * vertical adjustments, the ValueRecord will specify values for both XPlacement
 * and Yplacement.
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>PosFormat</td>
 * <td>Format identifier-format = 1</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Coverage</td>
 * <td>Offset to Coverage table-from beginning of SinglePos subtable</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueFormat</td>
 * <td>Defines the types of data in the ValueRecord</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value</td>
 * <td>Defines positioning value(s)-applied to all glyphs in the Coverage table</td>
 * </tr>
 * </table>
 * <p>
 * </p>
 * 
 * <p>
 * SinglePosFormat2 subtable: Array of positioning values
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>PosFormat</td>
 * <td>Format identifier-format = 2</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Coverage</td>
 * <td>Offset to Coverage table-from beginning of SinglePos subtable</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueFormat</td>
 * <td>Defines the types of data in the ValueRecord</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>ValueCount</td>
 * <td>Number of ValueRecords</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value<br>
 * [ValueCount]</td>
 * <td>Array of ValueRecords-positioning values applied to glyphs</td>
 * </tr>
 * </table>
 * 
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class LookupType1 extends LookupType {

    /**
     * Format identifier-format.
     */
    private int posFormat;

    /**
     * Creates a new object.
     * 
     * @param type The lookup tpye.
     * @param format The pos format.
     * @param offset The offset of the table.
     * @throws IOException if a io-error occurred.
     */
    protected LookupType1(int type, int format, int offset) throws IOException {

        super(type, offset);

        posFormat = format;

    }

    /**
     * Returns a instance of the lookup type.
     * 
     * @param rar The input.
     * @param type The lookup tpye.
     * @param offset The offset.
     * @return Returns a instance of the lookup type.
     * @throws IOException if a io-error occurred.
     */
    public static LookupType getInstance(RandomAccessR rar, int type, int offset)
            throws IOException {

        rar.seek(offset);

        int posFormat = rar.readUnsignedShort();

        switch (posFormat) {
            case 1:
                return new LookupType11(rar, type, posFormat, offset);
            case 2:
                return new LookupType12(rar, type, posFormat, offset);

            default:
                throw new IOException("wrong posformat");
        }
    }

    /**
     * Getter for posFormat.
     * 
     * @return the posFormat
     */
    public int getPosFormat() {

        return posFormat;
    }

}
