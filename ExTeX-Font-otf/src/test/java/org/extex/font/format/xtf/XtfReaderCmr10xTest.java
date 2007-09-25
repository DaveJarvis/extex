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

import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.extex.util.xml.XMLStreamWriter;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderCmr10xTest extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderCmr10xTest() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/cmr10.ttf");
        }
    }

    /**
     * test 01.
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(reader);
    }

    /**
     * test 99.
     * 
     * @throws Exception if an error occurred.
     */
    public void test99() throws Exception {

        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream("target/cmr10.xml"),
                    "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

    }

    // --------------------------------------------------------------
}
