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
 * Pair Positioning Adjustment: Format 2.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public class LookupType22 extends LookupType1 {

    /**
     * Array of Class1 records-ordered by Class1.
     */
    private Class1Record[] class1RecordArray;

    /**
     * Offset to ClassDef table-from beginning of PairPos subtable-for the first
     * glyph of the pair.
     */
    private int classDef1Offset;

    /**
     * Offset to ClassDef table-from beginning of PairPos subtable-for the
     * second glyph of the pair.
     */
    private int classDef2Offset;

    /**
     * Offset to Coverage table-from beginning of PairPos subtable-for the first
     * glyph of the pair.
     */
    private int coverageOffset;

    /**
     * ValueRecord definition-for the first glyph of the pair-may be zero (0)
     */
    private int valueFormat1;

    /**
     * ValueRecord definition-for the second glyph of the pair-may be zero (0).
     */
    private int valueFormat2;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param type The type.
     * @param posFormat The pos format.
     * @throws IOException if a io-error occurred.
     */
    public LookupType22(RandomAccessR rar, int type, int posFormat)
            throws IOException {

        super(type, posFormat);

        coverageOffset = rar.readUnsignedShort();
        valueFormat1 = rar.readUnsignedShort();
        valueFormat2 = rar.readUnsignedShort();
        classDef1Offset = rar.readUnsignedShort();
        classDef2Offset = rar.readUnsignedShort();
        int class1Count = rar.readUnsignedShort();
        int class2Count = rar.readUnsignedShort();

        class1RecordArray = new Class1Record[class1Count];
        for (int i = 0; i < class1Count; i++) {
            class1RecordArray[i] = new Class1Record(rar, class2Count);
        }

    }

    /**
     * Getter for class1RecordArray.
     * 
     * @return the class1RecordArray
     */
    public Class1Record[] getClass1RecordArray() {

        return class1RecordArray;
    }

    /**
     * Getter for classDef1Offset.
     * 
     * @return the classDef1Offset
     */
    public int getClassDef1Offset() {

        return classDef1Offset;
    }

    /**
     * Getter for classDef2Offset.
     * 
     * @return the classDef2Offset
     */
    public int getClassDef2Offset() {

        return classDef2Offset;
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
        writer.writeAttribute("classDef1Offset", classDef1Offset);
        writer.writeAttribute("classDef2Offset", classDef2Offset);
        for (int i = 0; i < class1RecordArray.length; i++) {
            class1RecordArray[i].writeXML(writer);
        }
        writer.writeEndElement();
    }

}
