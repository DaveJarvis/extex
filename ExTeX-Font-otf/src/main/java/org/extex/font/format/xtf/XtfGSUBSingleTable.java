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

/**
 * SingleTable.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public abstract class XtfGSUBSingleTable extends XtfLookupTable {

    /**
     * format 1
     */
    private static final int FORMAT1 = 1;

    /**
     * format 2
     */
    private static final int FORMAT2 = 2;

    /**
     * Create a new object.
     * 
     * @param format the format
     */
    XtfGSUBSingleTable(int format) {

        super(format);

    }

    /**
     * Returns the substitute.
     * 
     * @param glyphId the glyph id
     * @return Returns the substitute.
     */
    public abstract int substitute(int glyphId);

    /**
     * Create a new Instance.
     * 
     * @param rar the input
     * @param offset the offset
     * @return Returns the new instance.
     * @throws IOException if an IO-error occurs
     */
    public static XtfGSUBSingleTable newInstance(RandomAccessR rar, int offset)
            throws IOException {

        XtfGSUBSingleTable s = null;
        rar.seek(offset);
        int format = rar.readUnsignedShort();
        if (format == 1) {
            s = new SingleTableFormat1(rar, offset);
        } else if (format == 2) {
            s = new SingleTableFormat2(rar, offset);
        }
        return s;
    }

    /**
     * SingleTable for format 1
     */
    public static class SingleTableFormat1 extends XtfGSUBSingleTable {

        /**
         * coverageOffset
         */
        private int coverageOffset;

        /**
         * deltaGlyphID
         */
        private short deltaGlyphID;

        /**
         * coverage
         */
        private XtfCoverage coverage;

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
            deltaGlyphID = rar.readShort();
            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.XtfGSUBSingleTable#substitute(int)
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
            coverage.writeXML(writer);

            writer.writeEndElement();
        }
    }

    /**
     * SingleTable for format 2
     */
    public static class SingleTableFormat2 extends XtfGSUBSingleTable {

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
         * coverage
         */
        private XtfCoverage coverage;

        /**
         * Create a new object.
         * 
         * @param rar the input
         * @param offset the offset
         * @throws IOException if an IO-error occurs
         */
        SingleTableFormat2(RandomAccessR rar, int offset) throws IOException {

            super(FORMAT2);
            coverageOffset = rar.readUnsignedShort();
            glyphCount = rar.readUnsignedShort();
            substitutes = new int[glyphCount];
            for (int i = 0; i < glyphCount; i++) {
                substitutes[i] = rar.readUnsignedShort();
            }
            rar.seek(offset + coverageOffset);
            coverage = XtfCoverage.newInstance(rar);
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.font.format.xtf.XtfGSUBSingleTable#substitute(int)
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

            writer.writeStartElement("substitutes");
            for (int i = 0; i < glyphCount; i++) {
                writer.writeStartElement("sub");
                writer.writeAttribute("id", i);
                writer.writeAttribute("value", substitutes[i]);
                writer.writeEndElement();
            }
            writer.writeEndElement();
            coverage.writeXML(writer);

            writer.writeEndElement();
        }
    }
}
