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

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.OtfTableGPOS;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 * <p>
 * Table GPOS
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrGpos1Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrGpos1Test() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
            // reader =
            // new XtfReader(
            // "../texmf/src/texmf/fonts/ttf/LinLibertine.ttf");
        }
    }

    /**
     * Test, if the reader exits.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testExists() throws Exception {

        assertNotNull(reader);
    }

    // /**
    // * Test the name of the font.
    // *
    // * @throws Exception if an error occurred.
    // */
    // @Test
    // public void testName() throws Exception {
    //
    // assertNotNull(reader);
    // assertEquals("Linux Libertine", reader.getFontFamilyName());
    // assertEquals(2309, reader.getNumberOfGlyphs());
    // }

    /**
     * Test the gpos table.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGpos01() throws Exception {

        assertNotNull(reader);
        XtfTable table = reader.getTable(XtfReader.GPOS);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGPOS);
        OtfTableGPOS gpos = (OtfTableGPOS) table;
        assertNotNull(gpos);
    }

    /**
     * Test: write the xml output to 'tartet'
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testXmlOut() throws Exception {

        assertNotNull(reader);
        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream("target/fxlr.xml"),
                    "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

    }
}
