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
 * SinglePosFormat2 subtable: Array of positioning values.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public class LookupType12 extends LookupType1 {

    /**
     * Offset to Coverage table-from beginning of SinglePos subtable.
     */
    private int coverageOffset;

    /**
     * Defines the types of data in the ValueRecord.
     */
    private int valueFormat;

    /**
     * Array of ValueRecords-positioning values applied to glyphs.
     */
    private ValueRecord[] valueRecordArray;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param type The type.
     * @param posFormat The pos format.
     * @param offset The offset of the table.
     * @throws IOException if a io-error occurred.
     */
    public LookupType12(RandomAccessR rar, int type, int posFormat, int offset)
            throws IOException {

        super(type, posFormat, offset);

        coverageOffset = rar.readUnsignedShort();
        valueFormat = rar.readUnsignedShort();
        int valueCount = rar.readUnsignedShort();

        valueRecordArray = new ValueRecord[valueCount];
        for (int i = 0; i < valueCount; i++) {
            valueRecordArray[i] = new ValueRecord(rar);
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
     * Getter for valueFormat.
     * 
     * @return the valueFormat
     */
    public int getValueFormat() {

        return valueFormat;
    }

    /**
     * Getter for valueRecordArray.
     * 
     * @return the valueRecordArray
     */
    public ValueRecord[] getValueRecordArray() {

        return valueRecordArray;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("lookuptype");
        writer.writeAttribute("type", getType());
        writer.writeAttribute("posformat", getPosFormat());
        writer.writeAttribute("coverageOffset", coverageOffset, 4);
        writer.writeAttribute("valueFormat", valueFormat);
        for (int i = 0; i < valueRecordArray.length; i++) {
            valueRecordArray[i].writeXML(writer);
        }
        writer.writeEndElement();

    }

}
