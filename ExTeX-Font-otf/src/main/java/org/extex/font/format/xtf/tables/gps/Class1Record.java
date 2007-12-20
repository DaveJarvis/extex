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

package org.extex.font.format.xtf.tables.gps;

import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a <code>Class1Record</code>.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Class1Record implements XMLWriterConvertible {

    /**
     * Array of Class2 records-ordered by Class2.
     */
    private Class2Record[] class2RecordArray;

    /**
     * The index.
     */
    private int idx;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param class2Count The class2count value.
     * @param posOffset The offset of the pos table (GPOS, GSUB).
     * @param idx The index.
     * @param valueFormat2 The format for the second glyph.
     * @param valueFormat1 The format for the first glyph.
     * @param xtfGlyph The glyph name.
     * @throws IOException if a io-error occurred.
     */
    public Class1Record(RandomAccessR rar, int posOffset, int class2Count,
            XtfGlyphName xtfGlyp, int idx, int valueFormat1, int valueFormat2)
            throws IOException {

        this.idx = idx;
        class2RecordArray = new Class2Record[class2Count];
        for (int i = 0; i < class2Count; i++) {
            class2RecordArray[i] =
                    new Class2Record(rar, posOffset, xtfGlyp, i, valueFormat1,
                        valueFormat2);
        }
    }

    /**
     * Getter for class2RecordArray.
     * 
     * @return the class2RecordArray
     */
    public Class2Record[] getClass2RecordArray() {

        return class2RecordArray;
    }

    /**
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("class");
        writer.writeAttribute("class", idx);
        for (int i = 0; i < class2RecordArray.length; i++) {
            class2RecordArray[i].writeXML(writer);
        }
        writer.writeEndElement();
    }

}
