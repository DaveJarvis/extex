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
 * LookupTable for a ligature.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfGSUBLigatureTable extends XtfLookupTable {

    /**
     * ligature
     */
    public class Ligature implements XMLWriterConvertible {

        /**
         * compCount
         */
        private int compCount;

        /**
         * components
         */
        private int[] components;

        /**
         * ligGlyph
         */
        private int ligGlyph;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @throws IOException if an IO-error occurs
         */
        Ligature(RandomAccessR rar) throws IOException {

            ligGlyph = rar.readUnsignedShort();
            compCount = rar.readUnsignedShort();
            components = new int[compCount - 1];
            for (int i = 0; i < compCount - 1; i++) {
                components[i] = rar.readUnsignedShort();
            }
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
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligature");
            writer.writeAttribute("compcount", compCount);
            writer.writeAttribute("ligglyph", ligGlyph);
            for (int i = 0; i < components.length; i++) {
                writer.writeStartElement("component");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", components[i]);
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
         * ligatureCount
         */
        private int ligatureCount;

        /**
         * ligatureOffsets
         */
        private int[] ligatureOffsets;

        /**
         * ligatures
         */
        private Ligature[] ligatures;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @throws IOException if an IO-error occurs
         */
        LigatureSet(RandomAccessR rar, int offset) throws IOException {

            rar.seek(offset);
            ligatureCount = rar.readUnsignedShort();
            ligatureOffsets = new int[ligatureCount];
            ligatures = new Ligature[ligatureCount];
            for (int i = 0; i < ligatureCount; i++) {
                ligatureOffsets[i] = rar.readUnsignedShort();
            }
            for (int i = 0; i < ligatureCount; i++) {
                rar.seek(offset + ligatureOffsets[i]);
                ligatures[i] = new Ligature(rar);
            }
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

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligatureset");
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
        private XtfCoverage coverage;

        /**
         * coverageOffset
         */
        private int coverageOffset;

        /**
         * ligatureSetOffsets
         */
        private int[] ligatureSetOffsets;

        /**
         * ligatureSets
         */
        private LigatureSet[] ligatureSets;

        /**
         * ligSetCount
         */
        private int ligSetCount;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @throws IOException if an IO-error occurs
         */
        LigatureTableFormat1(RandomAccessR rar, int offset) throws IOException {

            super(FORMAT1);
            coverageOffset = rar.readUnsignedShort();
            ligSetCount = rar.readUnsignedShort();
            ligatureSetOffsets = new int[ligSetCount];
            ligatureSets = new LigatureSet[ligSetCount];
            for (int i = 0; i < ligSetCount; i++) {
                ligatureSetOffsets[i] = rar.readUnsignedShort();
            }
            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar);
            for (int i = 0; i < ligSetCount; i++) {
                ligatureSets[i] =
                        new LigatureSet(rar, offset + ligatureSetOffsets[i]);
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

        /**
         * Returns the ligSetCount.
         * 
         * @return Returns the ligSetCount.
         */
        public int getLigSetCount() {

            return ligSetCount;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
         */
        public void writeXML(XMLStreamWriter writer) throws IOException {

            writer.writeStartElement("ligaturetable");
            writer.writeAttribute("format", getFormat());
            writer.writeAttribute("ligsetcount", ligSetCount);

            for (int i = 0; i < ligSetCount; i++) {
                LigatureSet ligset = ligatureSets[i];
                ligset.writeXML(writer);
            }

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
     * @return Returns the new ligature table.
     * @throws IOException if an IO-error occurs.
     */
    static XtfGSUBLigatureTable newInstance(RandomAccessR rar, int offset)
            throws IOException {

        XtfGSUBLigatureTable ls = null;
        rar.seek(offset);
        int format = rar.readUnsignedShort();
        // TODO mgn: missing other formats
        if (format == FORMAT1) {
            ls = new LigatureTableFormat1(rar, offset);
        }
        return ls;
    }

    /**
     * Create a new object.
     * 
     * @param format the format
     */
    XtfGSUBLigatureTable(int format) {

        super(format);

    }

}