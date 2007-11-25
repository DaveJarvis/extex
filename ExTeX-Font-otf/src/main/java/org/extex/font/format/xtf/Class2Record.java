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
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Class for a <code>Class2Record</code>.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class Class2Record implements XMLWriterConvertible {

    /**
     * Positioning for first glyph-empty if ValueFormat1 = 0.
     */
    private ValueRecord value1;

    /**
     * Positioning for second glyph-empty if ValueFormat2 = 0.
     */
    private ValueRecord value2;

    /**
     * Creates a new object.
     * 
     * @param rar The input.
     * @param xtfGlyph The glyph name.
     * @throws IOException if a io-error occurred.
     */
    public Class2Record(RandomAccessR rar, XtfGlyphName xtfGlyph)
            throws IOException {

        value1 = new ValueRecord(rar, xtfGlyph);
        value2 = new ValueRecord(rar, xtfGlyph);
    }

    /**
     * Getter for value1.
     * 
     * @return the value1
     */
    public ValueRecord getValue1() {

        return value1;
    }

    /**
     * Getter for value2.
     * 
     * @return the value2
     */
    public ValueRecord getValue2() {

        return value2;
    }

    /**
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("class2record");
        value1.writeXML(writer);
        value2.writeXML(writer);
        writer.writeEndElement();
    }
}
