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

/**
 * Alternate Substitution Subtable.
 * 
 * <p>
 * An Alternate Substitution (AlternateSubst) subtable identifies any number of
 * aesthetic alternatives from which a user can choose a glyph variant to
 * replace the input glyph.
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfGSUBAlternateTable extends XtfLookupTable {

    /**
     * AlternateSet.
     * 
     * <p>
     * For each glyph, an AlternateSet subtable contains a count of the
     * alternative glyphs (GlyphCount) and an array of their glyph indices
     * (Alternate). Because all the glyphs are functionally equivalent, they can
     * be in any order in the array.
     * </p>
     * 
     * <table border="1">
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>GlyphCount</td>
     * <td>Number of GlyphIDs in the Alternate array</td>
     * </tr>
     * <tr>
     * <td>GlyphID</td>
     * <td>Alternate[GlyphCount]</td>
     * <td>Array of alternate GlyphIDs-in arbitrary order</td>
     * </tr>
     * </table>
     */
    public static class AlternateSet {

        /**
         * The alternate glyphs.
         */
        private int[] glyphs;

        /**
         * Creates a new object.
         * 
         * @param offset The offset
         * @param rar The input.
         * @throws IOException if a IO-error is occurred.
         * 
         */
        public AlternateSet(RandomAccessR rar, int offset) throws IOException {

            rar.seek(offset);
            int glyphCount = rar.readUnsignedShort();
            glyphs = new int[glyphCount];

            for (int i = 0; i < glyphCount; i++) {
                glyphs[i] = rar.readUnsignedShort();
            }
        }

        /**
         * Getter for glyphs.
         * 
         * @return the glyphs
         */
        public int[] getGlyphs() {

            return glyphs;
        }

    }

    /**
     * AlternateSubstFormat1 subtable.
     * 
     * <table border="1">
     * <tr>
     * <td><b>Type</b></td>
     * <td><b>Name</b></td>
     * <td><b>Description</b></td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>SubstFormat</td>
     * <td>Format identifier-format = 1</td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>Coverage</td>
     * <td>Offset to Coverage table-from beginning of Substitution table</td>
     * </tr>
     * <tr>
     * <td>uint16</td>
     * <td>AlternateSetCount</td>
     * <td>Number of AlternateSet tables</td>
     * </tr>
     * <tr>
     * <td>Offset</td>
     * <td>AlternateSet [AlternateSetCount]</td>
     * <td>Array of offsets to AlternateSet tables-from beginning of
     * Substitution table-ordered by Coverage Index</td>
     * </tr>
     * </table>
     */
    public static class XtfGSUBAlternateTable1 extends XtfGSUBAlternateTable {

        /**
         * The alternate set.
         */
        private AlternateSet[] alternateSet;

        /**
         * The coverage.
         */

        private XtfCoverage coverage;

        /**
         * Creates a new object.
         * 
         * @param rar The input.
         * @param offset The offset.
         * @param xtfGlyph The glyph name.
         * @throws IOException if an IO_error occurs
         */
        public XtfGSUBAlternateTable1(RandomAccessR rar, int offset,
                XtfGlyphName xtfGlyph) throws IOException {

            super(FORMAT1, xtfGlyph);
            int coverageOffset = rar.readUnsignedShort();
            int alternateSetCount = rar.readUnsignedShort();

            int[] alternateSetOffset = new int[alternateSetCount];
            for (int i = 0; i < alternateSetCount; i++) {
                alternateSetOffset[i] = rar.readUnsignedShort();
            }

            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar, xtfGlyph);

            alternateSet = new AlternateSet[alternateSetCount];
            for (int i = 0; i < alternateSetCount; i++) {
                alternateSet[i] =
                        new AlternateSet(rar, offset + alternateSetOffset[i]);
            }

        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.tables.gps.XtfGSUBAlternateTable#getSubGlyph()
         */
        @Override
        public String[][] getSubGlyph() {

            int[] glyphs = coverage.getGlyphs();
            String[][] tmp = new String[glyphs.length][2];

            for (int i = 0; glyphs != null && i < glyphs.length; i++) {
                tmp[i][0] = getXtfGlyph().getGlyphName(glyphs[i]);
                StringBuffer buf = new StringBuffer();

                int[] aGlyphs = alternateSet[i].getGlyphs();
                for (int k = 0; k < aGlyphs.length; k++) {
                    buf.append(getXtfGlyph().getGlyphName(aGlyphs[k]));
                    if (k < aGlyphs.length - 1) {
                        buf.append(",");
                    }
                }
                tmp[i][1] = buf.toString();
            }

            return tmp;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("alternatetable");
            writer.writeAttribute("format", getFormat());

            // coverage.writeXML(writer);

            int[] glyphs = coverage.getGlyphs();
            for (int i = 0; glyphs != null && i < glyphs.length; i++) {
                writer.writeStartElement("alternate");
                writer.writeAttribute("id", i);
                writer.writeAttribute("in", glyphs[i]);
                writer.writeAttribute("inName", getXtfGlyph().getGlyphName(
                    glyphs[i]));

                int[] aGlyphs = alternateSet[i].getGlyphs();
                for (int k = 0; k < aGlyphs.length; k++) {
                    writer.writeStartElement("glyph");
                    writer.writeAttribute("id", k);
                    writer.writeAttribute("out", aGlyphs[k]);
                    writer.writeAttribute("outName", getXtfGlyph()
                        .getGlyphName(aGlyphs[k]));
                    writer.writeEndElement();
                }
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
     * Create a new Instance.
     * 
     * @param rar the input
     * @param offset the offset
     * @param xtfGlyph The glyph name.
     * @return Returns the new instance.
     * @throws IOException if an IO-error occurs
     */
    public static XtfGSUBAlternateTable newInstance(RandomAccessR rar,
            int offset, XtfGlyphName xtfGlyph) throws IOException {

        XtfGSUBAlternateTable s = null;

        rar.seek(offset);
        int format = rar.readUnsignedShort();
        // only format 1
        if (format == FORMAT1) {
            s = new XtfGSUBAlternateTable1(rar, offset, xtfGlyph);
        }
        return s;
    }

    /**
     * Create a new object.
     * 
     * @param format the format
     * @param xtfGlyp The glyph name.
     */
    XtfGSUBAlternateTable(int format, XtfGlyphName xtfGlyp) {

        super(format, xtfGlyp);

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

}
