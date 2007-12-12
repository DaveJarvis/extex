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

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * SingleTable.
 * <p>
 * Single substitution (SingleSubst) subtables tell a client to replace a single
 * glyph with another glyph.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfGSUBSingleTable extends XtfLookupTable {

    /**
     * SingleTable for format 1.
     * 
     * <p>
     * Format 1 calculates the indices of the output glyphs, which are not
     * explicitly defined in the subtable. To calculate an output glyph index,
     * Format 1 adds a constant delta value to the input glyph index. For the
     * substitutions to occur properly, the glyph indices in the input and
     * output ranges must be in the same order. This format does not use the
     * Coverage Index that is returned from the Coverage table. The
     * SingleSubstFormat1 subtable begins with a format identifier (SubstFormat)
     * of 1. An offset references a Coverage table that specifies the indices of
     * the input glyphs.
     * </p>
     * <p>
     * DeltaGlyphID is the constant value added to each input glyph index to
     * calculate the index of the corresponding output glyph.
     * </p>
     */
    public static class SingleTableFormat1 extends XtfGSUBSingleTable {

        /**
         * coverage
         */
        private XtfCoverage coverage;

        /**
         * coverageOffset
         */
        private int coverageOffset;

        /**
         * deltaGlyphID
         */
        private short deltaGlyphID;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @param xtfGlyph The glyph name.
         * @throws IOException if an IO_error occurs
         */
        SingleTableFormat1(RandomAccessR rar, int offset, XtfGlyphName xtfGlyph)
                throws IOException {

            super(FORMAT1, xtfGlyph);
            coverageOffset = rar.readUnsignedShort();
            deltaGlyphID = rar.readShort();
            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar, xtfGlyph);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfGSUBSingleTable#getSubGlyph()
         */
        @Override
        public String[][] getSubGlyph() {

            int[] glyphs = coverage.getGlyphs();
            String[][] tmp = new String[glyphs.length][2];

            for (int i = 0; glyphs != null && i < glyphs.length; i++) {
                tmp[i][0] = getXtfGlyph().getGlyphName(glyphs[i]);
                int out = substitute(glyphs[i]);
                tmp[i][1] = getXtfGlyph().getGlyphName(out);
            }

            return tmp;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfGSUBSingleTable#substitute(int)
         */
        @Override
        public int substitute(int glyphId) {

            int i = coverage.findGlyph(glyphId);
            if (i > -1) {
                return glyphId + deltaGlyphID;
            }
            return glyphId;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("singletable");
            writer.writeAttribute("format", getFormat());

            int[] glyphs = coverage.getGlyphs();
            for (int i = 0; glyphs != null && i < glyphs.length; i++) {
                writer.writeStartElement("sub");
                writer.writeAttribute("id", i);
                writer.writeAttribute("in", glyphs[i]);
                writer.writeAttribute("inName", getXtfGlyph().getGlyphName(
                    glyphs[i]));
                int out = substitute(glyphs[i]);
                writer.writeAttribute("out", out);
                writer.writeAttribute("outName", getXtfGlyph()
                    .getGlyphName(out));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * SingleTable for format 2.
     * 
     * <p>
     * Format 2 is more flexible than Format 1, but requires more space. It
     * provides an array of output glyph indices (Substitute) explicitly matched
     * to the input glyph indices specified in the Coverage table.
     * </p>
     * <p>
     * The SingleSubstFormat2 subtable specifies a format identifier
     * (SubstFormat), an offset to a Coverage table that defines the input glyph
     * indices, a count of output glyph indices in the Substitute array
     * (GlyphCount), and a list of the output glyph indices in the Substitute
     * array (Substitute).
     * </p>
     * <p>
     * The Substitute array must contain the same number of glyph indices as the
     * Coverage table. To locate the corresponding output glyph index in the
     * Substitute array, this format uses the Coverage Index returned from the
     * Coverage table.
     * </p>
     */
    public static class SingleTableFormat2 extends XtfGSUBSingleTable {

        /**
         * coverage
         */
        private XtfCoverage coverage;

        /**
         * coverageOffset
         */
        private int coverageOffset;

        /**
         * glyphCount
         */
        private int glyphCount;

        /**
         * substitutes
         */
        private int[] substitutes;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @param xtfGlyph The glyph name.
         * @throws IOException if an IO-error occurs
         */
        SingleTableFormat2(RandomAccessR rar, int offset, XtfGlyphName xtfGlyph)
                throws IOException {

            super(FORMAT2, xtfGlyph);
            coverageOffset = rar.readUnsignedShort();
            glyphCount = rar.readUnsignedShort();
            substitutes = new int[glyphCount];
            for (int i = 0; i < glyphCount; i++) {
                substitutes[i] = rar.readUnsignedShort();
            }
            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar, xtfGlyph);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfGSUBSingleTable#getSubGlyph()
         */
        @Override
        public String[][] getSubGlyph() {

            int[] glyphs = coverage.getGlyphs();
            String[][] tmp = new String[glyphs.length][2];

            for (int i = 0; glyphs != null && i < glyphs.length; i++) {
                tmp[i][0] = getXtfGlyph().getGlyphName(glyphs[i]);
                int out = substitute(glyphs[i]);
                tmp[i][1] = getXtfGlyph().getGlyphName(out);
            }

            return tmp;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfGSUBSingleTable#substitute(int)
         */
        @Override
        public int substitute(int glyphId) {

            int i = coverage.findGlyph(glyphId);
            if (i > -1) {
                return substitutes[i];
            }
            return glyphId;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("singletable");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("glyphcount", glyphCount);

            int[] glyphs = coverage.getGlyphs();
            for (int i = 0; glyphs != null && i < glyphs.length; i++) {
                writer.writeStartElement("sub");
                writer.writeAttribute("id", i);
                writer.writeAttribute("in", glyphs[i]);
                writer.writeAttribute("inName", getXtfGlyph().getGlyphName(
                    glyphs[i]));
                int out = substitute(glyphs[i]);
                writer.writeAttribute("out", out);
                writer.writeAttribute("outName", getXtfGlyph()
                    .getGlyphName(out));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * format 1.
     */
    private static final int FORMAT1 = 1;

    /**
     * format 2.
     */
    private static final int FORMAT2 = 2;

    /**
     * Create a new Instance.
     * 
     * @param rar the input
     * @param offset the offset
     * @param xtfGlyph The glyph name.
     * @return Returns the new instance.
     * @throws IOException if an IO-error occurs
     */
    public static XtfGSUBSingleTable newInstance(RandomAccessR rar, int offset,
            XtfGlyphName xtfGlyph) throws IOException {

        XtfGSUBSingleTable s = null;
        rar.seek(offset);
        int format = rar.readUnsignedShort();
        if (format == FORMAT1) {
            s = new SingleTableFormat1(rar, offset, xtfGlyph);
        } else if (format == FORMAT2) {
            s = new SingleTableFormat2(rar, offset, xtfGlyph);
        }
        return s;
    }

    /**
     * Create a new object.
     * 
     * @param format the format
     * @param xtfGlyph The glyph name.
     */
    XtfGSUBSingleTable(int format, XtfGlyphName xtfGlyph) {

        super(format, xtfGlyph);

    }

    /**
     * Returns an array with the in/out glyph.
     * <p>
     * Special for the test case
     * </p>
     * 
     * @return Returns an array with the in/out glyph.
     */
    public abstract String[][] getSubGlyph();

    /**
     * Returns the substitute.
     * 
     * @param glyphId the glyph id
     * @return Returns the substitute.
     */
    public abstract int substitute(int glyphId);
}
