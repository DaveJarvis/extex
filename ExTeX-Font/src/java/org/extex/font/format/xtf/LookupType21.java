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
import org.extex.util.xml.XMLStreamWriter;

/**
 * PairPosFormat1 subtable: Adjustments for glyph pairs.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public class LookupType21 extends LookupType1 {

    /**
     * Offset to Coverage table-from beginning of PairPos subtable-only the
     * first glyph in each pair.
     */
    private int coverageOffset;

    /**
     * Array of offsets to PairSet tables-from beginning of PairPos
     * subtable-ordered by Coverage Index.
     */
    private int[] pairSetOffsetArray;

    /**
     * Defines the types of data in ValueRecord1-for the first glyph in the pair
     * -may be zero (0).
     */
    private int valueFormat1;

    /**
     * Defines the types of data in ValueRecord2-for the second glyph in the
     * pair -may be zero (0).
     */
    private int valueFormat2;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param type The type.
     * @param posFormat The pos format.
     * @param offset The offset of the table.
     * @throws IOException if a io-error occurred.
     */
    public LookupType21(RandomAccessR rar, int type, int posFormat, int offset)
            throws IOException {

        super(type, posFormat, offset);

        coverageOffset = rar.readUnsignedShort();
        valueFormat1 = rar.readUnsignedShort();
        valueFormat2 = rar.readUnsignedShort();
        int pairSetCount = rar.readUnsignedShort();
        pairSetOffsetArray = new int[pairSetCount];
        for (int i = 0; i < pairSetCount; i++) {
            pairSetOffsetArray[i] = rar.readUnsignedShort();
        }

        PairSetTable[] pairSetTable = new PairSetTable[pairSetCount];
        for (int i = 0; i < pairSetCount; i++) {
            pairSetTable[i] =
                    new PairSetTable(rar, getBaseoffset()
                            + pairSetOffsetArray[i]);
        }

    }

    /**
     * Getter for coverageOffset.
     * 
     * @return the coverageOffset
     */
    public int getCoverageOffset() {

        return coverageOffset;
    }

    /**
     * Getter for pairSetOffsetArray.
     * 
     * @return the pairSetOffsetArray
     */
    public int[] getPairSetOffsetArray() {

        return pairSetOffsetArray;
    }

    /**
     * Getter for valueFormat1.
     * 
     * @return the valueFormat1
     */
    public int getValueFormat1() {

        return valueFormat1;
    }

    /**
     * Getter for valueFormat2.
     * 
     * @return the valueFormat2
     */
    public int getValueFormat2() {

        return valueFormat2;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("lookuptype");
        writer.writeAttribute("type", getType());
        writer.writeAttribute("posformat", getPosFormat());
        writer.writeAttribute("coverageOffset", coverageOffset, 4);
        writer.writeAttribute("valueFormat1", valueFormat1);
        writer.writeAttribute("valueFormat2", valueFormat2);
        writer.writeIntArrayAsEntries(pairSetOffsetArray);
        writer.writeEndElement();

    }
}
