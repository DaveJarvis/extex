/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf;

import java.io.IOException;

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * The GPOS table <b>Single</b>.
 * <p>
 * Lookup Type 1: Single Adjustment Positioning Subtable
 * </p>
 * 
 * <p>
 * A single adjustment positioning subtable (SinglePos) is used to adjust the
 * position of a single glyph, such as a subscript or superscript. In addition,
 * a SinglePos subtable is commonly used to implement lookup data for contextual
 * positioning.
 * </p>
 * 
 * <p>
 * A SinglePos subtable will have one of two formats: one that applies the same
 * adjustment to a series of glyphs, or one that applies a different adjustment
 * for each unique glyph.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfGPOSSingleTable extends XtfLookupTable {

    /**
     * Create a new object.
     * 
     * @param format the format
     */
    XtfGPOSSingleTable(int format) {

        super(format);

    }

    /**
     * format 1
     */
    private static final int FORMAT1 = 1;

    /**
     * format 2
     */
    private static final int FORMAT2 = 2;

    /**
     * Create a new Instance.
     * 
     * @param rar the input
     * @param offset the offset
     * @return Returns the new instance.
     * @throws IOException if an IO-error occurs
     */
    public static XtfGPOSSingleTable newInstance(RandomAccessR rar, int offset)
            throws IOException {

        XtfGPOSSingleTable s = null;
        rar.seek(offset);
        int format = rar.readUnsignedShort();

        if (format == FORMAT1) {
            s = new SingleTableFormat1(rar, offset);
        } else if (format == FORMAT2) {
            s = new SingleTableFormat2(rar, offset);
        }
        return s;
    }

    /**
     * SingleTable for format 1.
     * 
     * <p>
     * Single Adjustment Positioning: Format 1
     * </p>
     * 
     * <p>
     * A SinglePosFormat1 subtable applies the same positioning value or values
     * to each glyph listed in its Coverage table. For instance, when a font
     * uses old-style numerals, this format could be applied to uniformly lower
     * the position of all math operator glyphs.
     * </p>
     * 
     * <p>
     * The Format 1 subtable consists of a format identifier (PosFormat), an
     * offset to a Coverage table that defines the glyphs to be adjusted by the
     * positioning values (Coverage), and the format identifier (ValueFormat)
     * that describes the amount and kinds of data in the ValueRecord.
     * </p>
     * 
     * <p>
     * The ValueRecord specifies one or more positioning values to be applied to
     * all covered glyphs (Value). For example, if all glyphs in the Coverage
     * table require both horizontal and vertical adjustments, the ValueRecord
     * will specify values for both XPlacement and Yplacement.
     * </p>
     * 
     * <p>
     * SinglePosFormat1 subtable: Single positioning value
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
     * <td>Defines positioning value(s)-applied to all glyphs in the Coverage
     * table</td>
     * </tr>
     * </table>
     */
    public static class SingleTableFormat1 extends XtfGPOSSingleTable
            implements
                XMLWriterConvertible {

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @throws IOException if an IO_error occurs
         */
        SingleTableFormat1(RandomAccessR rar, int offset) throws IOException {

            super(FORMAT1);

            coverageOffset = rar.readUnsignedShort();
            valueFormat = rar.readUnsignedShort();
            valueRecord = new ValueRecord(rar);

        }

        /**
         * Offset to Coverage table-from beginning of SinglePos subtable.
         */
        private int coverageOffset;

        /**
         * Defines the types of data in the ValueRecord.
         */
        private int valueFormat;

        /**
         * Defines positioning value(s)-applied to all glyphs in the Coverage
         * table
         */
        private ValueRecord valueRecord;

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("singletable");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("coverageOffset", coverageOffset, 4);
            writer.writeAttribute("valueFormat", valueFormat);
            valueRecord.writeXML(writer);
            writer.writeEndElement();
        }

    }

    /**
     * <p>
     * Single Adjustment Positioning: Format 2
     * </p>
     * 
     * <p>
     * A SinglePosFormat2 subtable provides an array of ValueRecords that
     * contains one positioning value for each glyph in the Coverage table. This
     * format is more flexible than Format 1, but it requires more space in the
     * font file.
     * </p>
     * 
     * <p>
     * For example, assume that the Cyrillic script will be used in
     * left-justified text. For all glyphs, Format 2 could define position
     * adjustments for left side bearings to align the left edges of the
     * paragraphs. To achieve this, the Coverage table would list every glyph in
     * the script, and the SinglePosFormat2 subtable would define a ValueRecord
     * for each covered glyph. Correspondingly, each ValueRecord would specify
     * an XPlacement adjustment value for the left side bearing.
     * </p>
     * 
     * <p>
     * Note: All ValueRecords defined in a SinglePos subtable must have the same
     * ValueFormat. In this example, if XPlacement is the only value that a
     * ValueRecord needs to optically align the glyphs, then XPlacement will be
     * the only value specified in the ValueFormat of the subtable.
     * </p>
     * 
     * <p>
     * As in Format 1, the Format 2 subtable consists of a format identifier
     * (PosFormat), an offset to a Coverage table that defines the glyphs to be
     * adjusted by the positioning values (Coverage), and the format identifier
     * (ValueFormat) that describes the amount and kinds of data in the
     * ValueRecords. In addition, the Format 2 subtable includes:
     * </p>
     * 
     * <nl>
     * <li>A count of the ValueRecords (ValueCount). One ValueRecord is defined
     * for each glyph in the Coverage table.</li>
     * <li>An array of ValueRecords that specify positioning values (Value).
     * Because the array follows the Coverage Index order, the first ValueRecord
     * applies to the first glyph listed in the Coverage table, and so on.</li>
     * </nl>
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
     * <td>Value [ValueCount]</td>
     * <td>Array of ValueRecords-positioning values applied to glyphs</td>
     * </tr>
     * </table>
     */
    public static class SingleTableFormat2 extends XtfGPOSSingleTable
            implements
                XMLWriterConvertible {

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @throws IOException if an IO_error occurs
         */
        SingleTableFormat2(RandomAccessR rar, int offset) throws IOException {

            super(FORMAT2);

            coverageOffset = rar.readUnsignedShort();
            valueFormat = rar.readUnsignedShort();
            int valueCount = rar.readUnsignedShort();

            valueRecordArray = new ValueRecord[valueCount];
            for (int i = 0; i < valueCount; i++) {
                valueRecordArray[i] = new ValueRecord(rar);
            }

        }

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
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("singletable");
            writer.writeAttribute("format", getFormat());

            writer.writeAttribute("coverageOffset", coverageOffset, 4);
            writer.writeAttribute("valueFormat", valueFormat);
            for (int i = 0; i < valueRecordArray.length; i++) {
                valueRecordArray[i].writeXML(writer);
            }

            writer.writeEndElement();
        }

    }

}
