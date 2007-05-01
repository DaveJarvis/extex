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
     * Creates a new object.
     * 
     * @param rar The input.
     * @param class2Count The class2count value.
     * @throws IOException if a io-error occurred.
     */
    public Class1Record(RandomAccessR rar, int class2Count) throws IOException {

        class2RecordArray = new Class2Record[class2Count];
        for (int i = 0; i < class2Count; i++) {
            class2RecordArray[i] = new Class2Record(rar);
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
     * @see org.extex.util.XMLWriterConvertible#writeXML(org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("class1record");
        for (int i = 0; i < class2RecordArray.length; i++) {
            class2RecordArray[i].writeXML(writer);
        }
        writer.writeEndElement();
    }

}
