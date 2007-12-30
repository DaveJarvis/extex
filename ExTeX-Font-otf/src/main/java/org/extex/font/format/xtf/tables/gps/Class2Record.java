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

import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a <code>Class2Record</code>.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Class2Record implements XMLWriterConvertible {

    /**
     * The index.
     */
    private int idx;

    /**
     * Positioning data for the first and second glyph.
     */
    private PairValue pairValue;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param posOffset The offset of the pos table (GSUB, GPOS).
     * @param xtfGlyph The glyph name.
     * @param idx The index.
     * @param valueFormat2 The format for the second glyph.
     * @param valueFormat1 The format for the first glyph.
     * @throws IOException if a io-error occurred.
     */
    public Class2Record(RandomAccessR rar, int posOffset,
            XtfGlyphName xtfGlyph, int idx, int valueFormat1, int valueFormat2)
            throws IOException {

        this.idx = idx;
        ValueRecord value1 =
                new ValueRecord(rar, posOffset, xtfGlyph, valueFormat1);
        ValueRecord value2 =
                new ValueRecord(rar, posOffset, xtfGlyph, valueFormat2);
        pairValue = new PairValue(value1, value2);
    }

    /**
     * Getter for pairValue.
     * 
     * @return the pairValue
     */
    public PairValue getPairValue() {

        return pairValue;
    }

    /**
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("class2record");
        writer.writeAttribute("class", idx);
        pairValue.getValue1().writeXML(writer);
        pairValue.getValue2().writeXML(writer);
        writer.writeEndElement();
    }
}
