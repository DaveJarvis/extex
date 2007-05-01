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

import org.extex.util.XMLWriterConvertible;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Class for a pair set table.
 * 
 * <p>
 * A PairSet table enumerates all the glyph pairs that begin with a covered
 * glyph. An array of PairValueRecords (PairValueRecord) contains one record for
 * each pair and lists the records sorted by the GlyphID of the second glyph in
 * each pair. PairValueCount specifies the number of PairValueRecords in the
 * set.
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
 * <td>PairValueCount</td>
 * <td>Number of PairValueRecords</td>
 * </tr>
 * <tr>
 * <td>struct</td>
 * <td>PairValueRecord<br>
 * [PairValueCount]</td>
 * <td>Array of PairValueRecords-ordered by GlyphID of the second glyph</td>
 * </tr>
 * </table>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class PairSetTable implements XMLWriterConvertible {

    /**
     * Array of PairValueRecords-ordered by GlyphID of the second glyph.
     */
    private PairValueRecord[] pairValueRecords;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param offset The offset of the table.
     * @throws IOException if a io-error occurred.
     */
    public PairSetTable(RandomAccessR rar, int offset) throws IOException {

        rar.seek(offset);
        int pairValueCount = rar.readUnsignedShort();

        pairValueRecords = new PairValueRecord[pairValueCount];
        // TODO mgn: Fehler
//        for (int i = 0; i < pairValueRecords.length; i++) {
//            pairValueRecords[i] = new PairValueRecord(rar);
//        }
    }

    /**
     * Getter for pairValueRecords.
     * 
     * @return the pairValueRecords
     */
    public PairValueRecord[] getPairValueRecords() {

        return pairValueRecords;
    }

    /**
     * @see org.extex.util.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("pairsettable");
        for (int i = 0; i < pairValueRecords.length; i++) {
            pairValueRecords[i].writeXML(writer);
        }
        writer.writeEndElement();
    }

}
