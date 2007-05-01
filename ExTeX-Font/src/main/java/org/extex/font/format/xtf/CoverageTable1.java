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
 * <p>
 * Coverage Format 1
 * </p>
 * <p>
 * Coverage Format 1 consists of a format code (CoverageFormat) and a count of
 * covered glyphs (GlyphCount), followed by an array of glyph indices
 * (GlyphArray). The glyph indices must be in numerical order for binary
 * searching of the list. When a glyph is found in the Coverage table, its
 * position in the GlyphArray determines the Coverage Index that is returned-the
 * first glyph has a Coverage Index = 0, and the last glyph has a Coverage Index =
 * GlyphCount -1.
 * </p>
 * <p>
 * CoverageFormat1 table: Individual glyph indices
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
 * <td>Format identifier-format = 1</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>GlyphCount</td>
 * <td>Number of glyphs in the GlyphArray</td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>GlyphArray[GlyphCount]</td>
 * <td>Array of GlyphIDs-in numerical order</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class CoverageTable1 extends CoverageTable {

    /**
     * Array of GlyphIDs-in numerical order
     */
    private int[] glyphArray;

    /**
     * Creates a new object.
     * 
     * @param format The format.
     * @param rar The input.
     * @throws IOException if a io-error occuured.
     */
    protected CoverageTable1(int format, RandomAccessR rar) throws IOException {

        super(format);

        int glyphCount = rar.readUnsignedShort();

        glyphArray = new int[0];
        // for (int i = 0; i < glyphCount; i++) {
        // glyphArray[i] = rar.readUnsignedShort();
        // }
    }

    /**
     * Getter for glyphArray.
     * 
     * @return the glyphArray
     */
    public int[] getGlyphArray() {

        return glyphArray;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("covertable");
        writer.writeAttribute("format", getCoverageFormat());
        writer.writeIntArrayAsEntries(glyphArray);
        writer.writeEndElement();
    }

}
