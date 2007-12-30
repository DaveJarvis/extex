/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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
import java.util.ArrayList;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Abstract class for all coverage
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfCoverage implements XMLWriterConvertible {

    /**
     * Coverage for FORMAT1
     */
    public static class CoverageFormat1 extends XtfCoverage
            implements
                XMLWriterConvertible {

        /**
         * glyph count
         */
        private int glyphCount;

        /**
         * glyph ids
         */
        private int[] glyphIds;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param xtfGlyph The glyph name.
         * @throws IOException if an IO-error occurs
         */
        CoverageFormat1(RandomAccessR rar, XtfGlyphName xtfGlyph)
                throws IOException {

            super(XtfCoverage.FORMAT1, xtfGlyph);

            glyphCount = rar.readUnsignedShort();
            glyphIds = new int[glyphCount];
            for (int i = 0; i < glyphCount; i++) {
                glyphIds[i] = rar.readUnsignedShort();
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfCoverage#findGlyph(int)
         */
        @Override
        public int findGlyph(int glyphId) {

            for (int i = 0; i < glyphCount; i++) {
                if (glyphIds[i] == glyphId) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfCoverage#getGlyphs()
         */
        @Override
        public int[] getGlyphs() {

            return glyphIds;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("coverage");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("glyphcount", glyphCount);
            for (int i = 0; i < glyphCount; i++) {
                writer.writeStartElement("coverage");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", glyphIds[i]);
                writer.writeAttribute("glyphname", getXtfGlyph().getGlyphName(
                    glyphIds[i]));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * Coverage for FORMAT2.
     * 
     * <p>
     * Format 2 consists of a format code (CoverageFormat) and a count of glyph
     * index ranges (RangeCount), followed by an array of records
     * (RangeRecords). Each RangeRecord consists of a start glyph index (Start),
     * an end glyph index (End), and the Coverage Index associated with the
     * range's Start glyph. Ranges must be in GlyphID order, and they must be
     * distinct, with no overlapping. The Coverage Indexes for the first range
     * begin with zero (0), and the Start Coverage Indexes for each succeeding
     * range are determined by adding the length of the preceding range (End
     * GlyphID - Start GlyphID + 1) to the array Index. This allows for a quick
     * calculation of the Coverage Index for any glyph in any range using the
     * formula:
     * </p>
     * <p>
     * Coverage Index (GlyphID) = StartCoverageIndex + GlyphID - Start GlyphID.
     * </p>
     */
    public static class CoverageFormat2 extends XtfCoverage {

        /**
         * The glyphIds
         */
        private int[] glyphIds = null;

        /**
         * range count
         */
        private int rangeCount;

        /**
         * records
         */
        private RangeRecord[] rangeRecords;

        /**
         * Create a new object
         * 
         * @param rar input
         * @param xtfGlyph The glyph name.
         * @throws IOException if an IO-error occurs
         */
        CoverageFormat2(RandomAccessR rar, XtfGlyphName xtfGlyph)
                throws IOException {

            super(XtfCoverage.FORMAT2, xtfGlyph);

            rangeCount = rar.readUnsignedShort();
            rangeRecords = new RangeRecord[rangeCount];
            for (int i = 0; i < rangeCount; i++) {
                rangeRecords[i] = new RangeRecord(rar, i);
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfCoverage#findGlyph(int)
         */
        @Override
        public int findGlyph(int glyphId) {

            for (int i = 0; i < rangeCount; i++) {
                int n = rangeRecords[i].getCoverageIndex(glyphId);
                if (n > -1) {
                    return n;
                }
            }
            return -1;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfCoverage#getGlyphs()
         */
        @Override
        public int[] getGlyphs() {

            if (glyphIds == null) {
                ArrayList<Integer> glist = new ArrayList<Integer>(200);

                for (int i = 0; i < rangeCount; i++) {
                    int start = rangeRecords[i].getStart();
                    int end = rangeRecords[i].getEnd();

                    for (int g = start; g <= end; g++) {
                        glist.add(g);
                    }
                }

                glyphIds = new int[glist.size()];
                for (int i = 0, n = glist.size(); i < n; i++) {
                    glyphIds[i] = glist.get(i);
                }
            }
            return glyphIds;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("coverage");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("rangecount", rangeCount);
            for (int i = 0; i < rangeCount; i++) {
                rangeRecords[i].writeXML(writer);
            }
            writer.writeEndElement();
        }
    }

    /**
     * Coverage Index (GlyphID) = StartCoverageIndex + GlyphID - Start GlyphID
     */
    public class RangeRecord implements XMLWriterConvertible {

        /**
         * end
         */
        private int end;

        /**
         * The index.
         */
        private int idx;

        /**
         * start
         */
        private int start;

        /**
         * start index
         */
        private int startCoverageIndex;

        /**
         * Create anew object
         * 
         * @param rar the input
         * @param idx The index.
         * @throws IOException if an IO-error occurs
         */
        RangeRecord(RandomAccessR rar, int idx) throws IOException {

            this.idx = idx;
            start = rar.readUnsignedShort();
            end = rar.readUnsignedShort();
            startCoverageIndex = rar.readUnsignedShort();
        }

        /**
         * Returns the coverage index
         * 
         * @param glyphId the glyph id
         * @return Returns the coverage index
         */
        public int getCoverageIndex(int glyphId) {

            if (isInRange(glyphId)) {
                return startCoverageIndex + glyphId - start;
            }
            return -1;
        }

        /**
         * Getter for end.
         * 
         * @return the end
         */
        public int getEnd() {

            return end;
        }

        /**
         * Getter for start.
         * 
         * @return the start
         */
        public int getStart() {

            return start;
        }

        /**
         * Check, if the glyph id is in the range.
         * 
         * @param glyphId the glyph id
         * @return Check, if the glyph id is in the range.
         */
        public boolean isInRange(int glyphId) {

            return (start <= glyphId && glyphId <= end);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("rangerecord");
            writer.writeAttribute("id", idx);
            writer.writeAttribute("start", start);
            writer.writeAttribute("end", end);
            writer.writeAttribute("startcoverageindex", startCoverageIndex);
            writer.writeEndElement();
        }
    }

    /**
     * format 1
     */
    static final int FORMAT1 = 1;

    /**
     * format 2
     */
    static final int FORMAT2 = 2;

    /**
     * Create a new instance and read the coverage
     * 
     * @param rar input
     * @param xtfGlyph The glyph name.
     * @return Returns the new coverage
     * @throws IOException if an IO-error occurred.
     */
    static XtfCoverage newInstance(RandomAccessR rar, XtfGlyphName xtfGlyph)
            throws IOException {

        XtfCoverage c = null;
        int format = rar.readUnsignedShort();
        if (format == FORMAT1) {
            c = new CoverageFormat1(rar, xtfGlyph);
        } else if (format == FORMAT2) {
            c = new CoverageFormat2(rar, xtfGlyph);
        }
        return c;
    }

    /**
     * format
     */
    private int format;

    /**
     * The glyph name.
     */
    private XtfGlyphName xtfGlyph;

    /**
     * Create a new object.
     * 
     * @param fm the format
     */
    XtfCoverage(int fm, XtfGlyphName xtfGlyph) {

        format = fm;
        this.xtfGlyph = xtfGlyph;
    }

    /**
     * Returns the index of the glyph in the coverage table.
     * 
     * @param glyphId The ID of the glyph to find.
     * @return Returns the index of the glyph within the coverage, or -1 if the
     *         glyph can't be found.
     */
    public abstract int findGlyph(int glyphId);

    /**
     * Returns the format
     * 
     * @return Returns the format
     */
    public int getFormat() {

        return format;
    }

    /**
     * Returns the glyphs from the coverage.
     * 
     * @return Returns the glyphs from the coverage.
     */
    public abstract int[] getGlyphs();

    /**
     * Getter for xtfGlyph.
     * 
     * @return the xtfGlyph
     */
    public XtfGlyphName getXtfGlyph() {

        return xtfGlyph;
    }
}
