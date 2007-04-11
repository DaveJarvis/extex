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

import org.extex.util.XMLWriterConvertible;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Class for a PairValueRecord.
 * 
 * <p>
 * A PairValueRecord specifies the second glyph in a pair (SecondGlyph) and
 * defines a ValueRecord for each glyph (Value1 and Value2). If ValueFormat1 is
 * set to zero (0) in the PairPos subtable, ValueRecord1 will be empty;
 * similarly, if ValueFormat2 is 0, Value2 will be empty.
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>SecondGlyph</td>
 * <td>GlyphID of second glyph in the pair-first glyph is listed in the
 * Coverage table</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value1</td>
 * <td>Positioning data for the first glyph in the pair</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value2</td>
 * <td>Positioning data for the second glyph in the pair</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class PairValueRecord implements XMLWriterConvertible {

    /**
     * GlyphID of second glyph in the pair-first glyph is listed in the Coverage
     * table.
     */
    private int secondGlyph;

    /**
     * Positioning data for the first glyph in the pair.
     */
    private ValueRecord value1;

    /**
     * Positioning data for the second glyph in the pair.
     */
    private ValueRecord value2;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @throws IOException if an io-error occurred.
     */
    public PairValueRecord(RandomAccessR rar) throws IOException {

        secondGlyph = rar.readUnsignedShort();
        value1 = new ValueRecord(rar);
        value2 = new ValueRecord(rar);
    }

    /**
     * Getter for secondGlyph.
     * 
     * @return the secondGlyph
     */
    public int getSecondGlyph() {

        return secondGlyph;
    }

    /**
     * Getter for value1.
     * 
     * @return the value1
     */
    public ValueRecord getValue1() {

        return value1;
    }

    /**
     * Getter for value2.
     * 
     * @return the value2
     */
    public ValueRecord getValue2() {

        return value2;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("pairvaluerecord");
        writer.writeAttribute("secondGlyph", secondGlyph);
        value1.writeXML(writer);
        value2.writeXML(writer);
        writer.writeEndElement();
    }

}
