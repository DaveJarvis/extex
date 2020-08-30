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
import org.extex.util.xml.XMLWriterConvertible;

/**
 * LookupTable for a ligature.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public abstract class XtfGSUBLigatureTable extends XtfLookupTable {

    /**
     * ligature
     */
    public class Ligature implements XMLWriterConvertible {

        /**
         * compCount
         */
        private final int compCount;

        /**
         * components
         */
        private final int[] components;

        /**
         * The index.
         */
        private final int idx;

        /**
         * ligGlyph
         */
        private final int ligGlyph;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param idx
         * @throws IOException if an IO-error occurs
         */
        Ligature(RandomAccessR rar, int idx) throws IOException {

            this.idx = idx;
            ligGlyph = rar.readUnsignedShort();
            compCount = rar.readUnsignedShort();
            components = new int[compCount - 1];
            for (int i = 0; i < compCount - 1; i++) {
                components[i] = rar.readUnsignedShort();
            }
        }

        /**
         * Getter for components.
         * 
         * @return the components
         */
        public int[] getComponents() {

            return components;
        }

        /**
         * Returns the glyph count.
         * 
         * @return Returns the glyph count.
         */
        public int getGlyphCount() {

            return compCount;
        }

        /**
         * Returns the glyph id.
         * 
         * @param i the index
         * @return Returns the glyph id.
         */
        public int getGlyphId(int i) {

            return (i == 0) ? ligGlyph : components[i - 1];
        }

        /**
         * Getter for idx.
         * 
         * @return the idx
         */
        public int getIdx() {

            return idx;
        }

        /**
         * Getter for ligGlyph.
         * 
         * @return the ligGlyph
         */
        public int getLigGlyph() {

            return ligGlyph;
        }

    public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligature");
            writer.writeAttribute("id", idx);
            writer.writeAttribute("compcount", compCount);
            writer.writeAttribute("ligglyph", ligGlyph);
            writer.writeAttribute("glyphname", getXtfGlyph().getGlyphName(
                ligGlyph));
            for (int i = 0; i < components.length; i++) {
                writer.writeStartElement("component");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", components[i]);
                writer.writeAttribute("glyphname", getXtfGlyph().getGlyphName(
                    components[i]));
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
    }

    /**
     * ligature set
     */
    public class LigatureSet implements XMLWriterConvertible {

        /**
         * The index.
         */
        private final int idx;

        /**
         * ligatureCount
         */
        private final int ligatureCount;

        /**
         * ligatureOffsets
         */
        private final int[] ligatureOffsets;

        /**
         * ligatures
         */
        private final Ligature[] ligatures;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @param idx
         * @throws IOException if an IO-error occurs
         */
        LigatureSet(RandomAccessR rar, int offset, int idx) throws IOException {

            this.idx = idx;
            rar.seek(offset);
            ligatureCount = rar.readUnsignedShort();
            ligatureOffsets = new int[ligatureCount];
            ligatures = new Ligature[ligatureCount];
            for (int i = 0; i < ligatureCount; i++) {
                ligatureOffsets[i] = rar.readUnsignedShort();
            }
            for (int i = 0; i < ligatureCount; i++) {
                rar.seek(offset + ligatureOffsets[i]);
                ligatures[i] = new Ligature(rar, i);
            }
        }

        /**
         * Check, if the components fits to the glyphs.
         * 
         * @param glyphCount The count of glyphs.
         * @param components The components.
         * @param glyphIds The glyph ids.
         * @return Returns {@code true}, if the glyphs are found, or
         *         {@code false}, if not.
         */
        private boolean checkComponents(int glyphCount, int[] components,
                int[] glyphIds) {

            if (glyphIds == null) {
                return false;
            }
            if (glyphCount - 1 > glyphIds.length) {
                return false;
            }
            for (int i = 0; i < glyphCount - 1; i++) {
                if (components[i] != glyphIds[i]) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Getter for idx.
         * 
         * @return the idx
         */
        public int getIdx() {

            return idx;
        }

        /**
         * Returns the ligature glyph id, or -1, if not found.
         * 
         * @param glyphIds The glyph ids.
         * @return Returns the ligature glyph id, or -1, if not found.
         */
        public int getLigature(int[] glyphIds) {

            for (int i = 0; i < ligatureCount; i++) {
                Ligature lig = ligatures[i];
                if (lig != null) {
                    boolean found =
                            checkComponents(lig.getGlyphCount(), lig
                                .getComponents(), glyphIds);
                    if (found) {
                        return lig.getLigGlyph();
                    }
                }
            }
            return -1;
        }

        /**
         * Returns the ligatureCount.
         * 
         * @return Returns the ligatureCount.
         */
        public int getLigatureCount() {

            return ligatureCount;
        }

        /**
         * Returns the ligatureOffsets.
         * 
         * @return Returns the ligatureOffsets.
         */
        public int[] getLigatureOffsets() {

            return ligatureOffsets;
        }

        /**
         * Returns the ligatures.
         * 
         * @return Returns the ligatures.
         */
        public Ligature[] getLigatures() {

            return ligatures;
        }

    public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligatureset");
            writer.writeAttribute("id", idx);
            writer.writeAttribute("count", ligatureCount);
            for (int i = 0; i < ligatureCount; i++) {
                Ligature lig = ligatures[i];
                lig.writeXML(writer);
            }
            writer.writeEndElement();
        }
    }

    /**
     * Table for format1
     */
    public static class LigatureTableFormat1 extends XtfGSUBLigatureTable {

        /**
         * coverage
         */
        private final XtfCoverage coverage;

        /**
         * coverageOffset
         */
        private final int coverageOffset;

        /**
         * ligatureSetOffsets
         */
        private final int[] ligatureSetOffsets;

        /**
         * ligatureSets
         */
        private final LigatureSet[] ligatureSets;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @param xtfGlyph The glyph name.
         * @param format The table format.
         * @throws IOException if an IO-error occurs
         */
        LigatureTableFormat1(RandomAccessR rar, int offset,
                XtfGlyphName xtfGlyph, int format) throws IOException {

            super(format, xtfGlyph);
            coverageOffset = rar.readUnsignedShort();
            count = rar.readUnsignedShort();
            ligatureSetOffsets = new int[count];
            ligatureSets = new LigatureSet[count];
            for (int i = 0; i < count; i++) {
                ligatureSetOffsets[i] = rar.readUnsignedShort();
            }
            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar, xtfGlyph);
            for (int i = 0; i < count; i++) {
                ligatureSets[i] =
                        new LigatureSet(rar, offset + ligatureSetOffsets[i], i);
            }
        }

        /**
         * Returns the coverage.
         * 
         * @return Returns the coverage.
         */
        public XtfCoverage getCoverage() {

            return coverage;
        }

        /**
         * Returns the coverageOffset.
         * 
         * @return Returns the coverageOffset.
         */
        public int getCoverageOffset() {

            return coverageOffset;
        }

    @Override
        public LigatureSet getLigatureSet(int glyphId) {

            int ligSetIndex = coverage.findGlyph(glyphId);
            if (ligSetIndex >= 0 && ligSetIndex < count) {
                return ligatureSets[ligSetIndex];
            }

            return null;
        }

        /**
         * Returns the ligatureSetOffsets.
         * 
         * @return Returns the ligatureSetOffsets.
         */
        public int[] getLigatureSetOffsets() {

            return ligatureSetOffsets;
        }

        /**
         * Returns the ligatureSets.
         * 
         * @return Returns the ligatureSets.
         */
        public LigatureSet[] getLigatureSets() {

            return ligatureSets;
        }

    public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligaturetable");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("count", getCount());

            coverage.writeXML(writer);

            for (int i = 0; i < count; i++) {
                LigatureSet ligset = ligatureSets[i];
                ligset.writeXML(writer);
            }

            writer.writeEndElement();
        }
    }

    /**
     * Table for format x.
     */
    public static class LigatureTableFormatX extends XtfGSUBLigatureTable {

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @param xtfGlyph The glyph name.
         * @param format The table format.
         * @throws IOException if an IO-error occurs
         */
        LigatureTableFormatX(RandomAccessR rar, int offset,
                XtfGlyphName xtfGlyph, int format) throws IOException {

            super(format, xtfGlyph);
        }

    @Override
        public LigatureSet getLigatureSet(int glyphiD) {

            return null;
        }

    public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligaturetable");
            writer.writeAttribute("format", getFormat());
            writer.writeEndElement();
        }
    }

    /**
     * format 1
     */
    private static final int FORMAT1 = 1;

    /**
     * Create a new instance.
     * 
     * @param rar the input
     * @param offset the offset
     * @param xtfGlyph The glyph name.
     * @return Returns the new ligature table.
     * @throws IOException if an IO-error occurs.
     */
    public static XtfGSUBLigatureTable newInstance(RandomAccessR rar,
            int offset, XtfGlyphName xtfGlyph) throws IOException {

        XtfGSUBLigatureTable ls = null;
        rar.seek(offset);
        int format = rar.readUnsignedShort();
        if (format == FORMAT1) {
            ls = new LigatureTableFormat1(rar, offset, xtfGlyph, format);
        } else {
            ls = new LigatureTableFormatX(rar, offset, xtfGlyph, format);
        }
        return ls;
    }

    /**
     * Create a new object.
     * 
     * @param format the format
     * @param xtfGlyp The glyph name.
     */
    XtfGSUBLigatureTable(int format, XtfGlyphName xtfGlyp) {

        super(format, xtfGlyp);
    }

    /**
     * Returns the ligature set or {@code null}, if not found.
     * 
     * @param glyphiD TODO
     * @return Returns the ligature set.
     */
    public abstract LigatureSet getLigatureSet(int glyphiD);
}
