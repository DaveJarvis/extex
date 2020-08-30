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

package org.extex.font.format.xtf.tables.gps;

import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * The GPOS table <b>Cursive</b>.
 * 
 * <p>
 * Cursive Attachment Positioning Subtable
 * </p>
 * 
 * Some cursive fonts are designed so that adjacent glyphs join when rendered
 * with their default positioning. However, if positioning adjustments are
 * needed to join the glyphs, a cursive attachment positioning (CursivePos)
 * subtable can describe how to connect the glyphs by aligning two anchor
 * points: the designated exit point of a glyph, and the designated entry point
 * of the following glyph.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public abstract class XtfGPOSCursiveTable extends XtfLookupTable {

    /**
     * CursivePosFormat1.
     * 
     * The subtable has one format: CursivePosFormat1. It begins with a format
     * identifier (PosFormat) and an offset to a Coverage table (Coverage),
     * which lists all the glyphs that define cursive attachment data.
     * 
     * In addition, the subtable contains one EntryExitRecord for each glyph
     * listed in the Coverage table, a count of those records (EntryExitCount),
     * and an array of those records in the same order as the Coverage Index
     * (EntryExitRecord).
     * 
     * <p>
     * CursivePosFormat1 subtable: Cursive attachment
     * </p>
     * <table>
 * <caption>TBD</caption>
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
     * <td>Offset to Coverage table-from beginning of CursivePos subtable</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>EntryExitCount</td>
     * <td>Number of EntryExit records</td>
     * </tr>
     * <tr>
     * <td>struct</td>
     * <td>EntryExitRecord[EntryExitCount]</td>
     * <td>Array of EntryExit records-in Coverage Index order</td>
     * </tr>
     * </table>
     * 
     * <p>
     * Each EntryExitRecord consists of two offsets: one to an Anchor table that
     * identifies the entry point on the glyph (EntryAnchor), and an offset to
     * an Anchor table that identifies the exit point on the glyph (ExitAnchor).
     * (For a complete description of the Anchor table, see the end of this
     * chapter.)
     * </p>
     * 
     * <p>
     * To position glyphs using the CursivePosFormat1 subtable, a
     * text-processing client aligns the ExitAnchor point of a glyph with the
     * EntryAnchor point of the following glyph. If no corresponding anchor
     * point exists, either the EntryAnchor or ExitAnchor offset may be NULL.
     * </p>
     * 
     * <p>
     * At the end of this chapter, Example 6 describes cursive glyph attachment
     * in the Urdu language.
     * </p>
     */
    public static class CursiveTableFormat1 extends XtfGPOSCursiveTable
            implements
                XMLWriterConvertible {

        /**
         * The entry count.
         */
        private final int entryExitCount;

        /**
         * The entry exit record array.
         */
        private final EntryExitRecord[] entryExitRecord;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @param xtfGlyp The glyph name.
         * @throws IOException if an IO-error occurred.
         */
        CursiveTableFormat1(RandomAccessR rar, int offset, XtfGlyphName xtfGlyp)
                throws IOException {

            super(FORMAT1, xtfGlyp);

            /* int coverageOffset = */rar.readUnsignedShort();
            entryExitCount = rar.readUnsignedShort();

            entryExitRecord = new EntryExitRecord[entryExitCount];
            for (int i = 0; i < entryExitCount; i++) {
                entryExitRecord[i] = new EntryExitRecord(rar);
            }
        }

    public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("cursivetable");
            writer.writeAttribute("format", getFormat());
            writer.writeStartElement("entryexitrecords");
            for (int i = 0; i < entryExitRecord.length; i++) {
                entryExitRecord[i].writeXML(writer);
            }
            writer.writeEndElement();
            writer.writeEndElement();
        }

    }

    /**
     * The class for EntryExitRecord.
     * 
     * <table>
 * <caption>TBD</caption>
 * <tr>
* <td><b>Value</b></td>
     * <td><b>Type</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>EntryAnchor</td>
     * <td>Offset to EntryAnchor table-from beginning of CursivePos
     * subtable-may be NULL</td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>ExitAnchor</td>
     * <td>Offset to ExitAnchor table-from beginning of CursivePos subtable-may
     * be NULL</td>
     * </tr>
     * </table>
     */
    public static class EntryExitRecord implements XMLWriterConvertible {

        /**
         * Offset to EntryAnchor table-from beginning of CursivePos subtable-may
         * be NULL.
         */
        private final int entryAnchor;

        /**
         * Offset to ExitAnchor table-from beginning of CursivePos subtable-may
         * be NULL.
         */
        private final int exitAnchor;

        /**
         * Creates a new object.
         * 
         * @param rar The input
         * @throws IOException if a io-error occurred.
         */
        public EntryExitRecord(RandomAccessR rar) throws IOException {

            entryAnchor = rar.readUnsignedShort();
            exitAnchor = rar.readUnsignedShort();
        }

        /**
         * Getter for entryAnchor.
         * 
         * @return the entryAnchor
         */
        public int getEntryAnchor() {

            return entryAnchor;
        }

        /**
         * Getter for exitAnchor.
         * 
         * @return the exitAnchor
         */
        public int getExitAnchor() {

            return exitAnchor;
        }

    public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("enryexitrecord");
            writer.writeAttribute("entryanchor", entryAnchor);
            writer.writeAttribute("exitanchor", exitAnchor);
            writer.writeEndElement();
        }
    }

    /**
     * format 1
     */
    private static final int FORMAT1 = 1;

    /**
     * Create a new Instance.
     * 
     * @param rar the input
     * @param offset the offset
     * @param xtfGlyp The glyph name.
     * @return Returns the new instance.
     * @throws IOException if an IO-error occurs
     */
    public static XtfGPOSCursiveTable newInstance(RandomAccessR rar,
            int offset, XtfGlyphName xtfGlyp) throws IOException {

        XtfGPOSCursiveTable s = null;
        rar.seek(offset);
        int format = rar.readUnsignedShort();

        if (format == FORMAT1) {
            s = new CursiveTableFormat1(rar, offset, xtfGlyp);
        }
        return s;
    }

    /**
     * Create a new object.
     * 
     * @param format the format
     * @param xtfGlyp The glyph name.
     */
    XtfGPOSCursiveTable(int format, XtfGlyphName xtfGlyp) {

        super(format, xtfGlyp);
    }
}
