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

package org.extex.font.format.xtf;

import java.io.IOException;

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
         * @see org.extex.font.format.xtf.XtfCoverage#findGlyph(int)
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
     * Coverage for FORMAT2
     */
    public static class CoverageFormat2 extends XtfCoverage {

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
                rangeRecords[i] = new RangeRecord(rar);
            }
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.XtfCoverage#findGlyph(int)
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
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("coverage");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("rangecount", rangeCount);
            for (int i = 0; i < rangeCount; i++) {
                writer.writeStartElement("range");
                RangeRecord rec = rangeRecords[i];
                rec.writeXML(writer);
                writer.writeEndElement();
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
         * @throws IOException if an IO-error occurs
         */
        RangeRecord(RandomAccessR rar) throws IOException {

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
     * Returns the index of the glyph within the coverage
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
     * Getter for xtfGlyph.
     * 
     * @return the xtfGlyph
     */
    public XtfGlyphName getXtfGlyph() {

        return xtfGlyph;
    }
}
