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
 * Coverage Format 2
 * <p>
 * Format 2 consists of a format code (CoverageFormat) and a count of glyph
 * index ranges (RangeCount), followed by an array of records (RangeRecords).
 * Each RangeRecord consists of a start glyph index (Start), an end glyph index
 * (End), and the Coverage Index associated with the range's Start glyph. Ranges
 * must be in GlyphID order, and they must be distinct, with no overlapping.
 * </p>
 * <p>
 * The Coverage Indexes for the first range begin with zero (0), and the Start
 * Coverage Indexes for each succeeding range are determined by adding the
 * length of the preceding range (End GlyphID - Start GlyphID + 1) to the array
 * Index. This allows for a quick calculation of the Coverage Index for any
 * glyph in any range using the formula: Coverage Index (GlyphID) =
 * StartCoverageIndex + GlyphID - Start GlyphID.
 * </p>
 * <p>
 * CoverageFormat2 table: Range of glyphs
 * </p>
 * <table border="1">
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>CoverageFormat</td>
 * <td>Format identifier-format = 2</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>RangeCount</td>
 * <td>Number of RangeRecords</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>RangeRecord<br>
 * [RangeCount]</td>
 * <td>Array of glyph ranges-ordered by Start GlyphID</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5438 $
 */
public class CoverageTable2 extends CoverageTable {

    /**
     * The range record array.
     */
    private RangeRecord[] rangeRecord;

    /**
     * Creates a new object.
     * 
     * @param format The format.
     * @param rar The input.
     * @throws IOException if a io-error occuured.
     */
    protected CoverageTable2(int format, RandomAccessR rar) throws IOException {

        super(format);

        int rangeCount = rar.readUnsignedShort();

        rangeRecord = new RangeRecord[rangeCount];
        for (int i = 0; i < rangeCount; i++) {
            rangeRecord[i] = new RangeRecord(rar);
        }
    }

    /**
     * Getter for rangeRecord.
     * 
     * @return the rangeRecord
     */
    public RangeRecord[] getRangeRecord() {

        return rangeRecord;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("covertable");
        writer.writeAttribute("format", getCoverageFormat());
        for (int i = 0; i < rangeRecord.length; i++) {
            rangeRecord[i].writeXML(writer);
        }
        writer.writeEndElement();
    }

}
