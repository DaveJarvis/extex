/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.pfb;

import java.io.File;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Test for the {@link PfbParser}.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class PfbParserTest extends TestCase {

    /**
     * The file for the test.
     */
    private static final String PFB_FILE =
            "../../../texmf/src/texmf/fonts/afm/fxlr.pfb";

    /**
     * Test the decoder.
     * 
     * @throws Exception if an error occurred.
     */
    public void testDecode01() throws Exception {

        PfbParser parser = new PfbParser(PFB_FILE);
        assertNotNull(parser);

        String[] names = parser.getAllGylyphNames();
        assertNotNull(names);

        Arrays.sort(names);
        try {
            assertEquals("eng.sc", names[Arrays.binarySearch(names, "eng.sc")]);
            assertTrue(Arrays.binarySearch(names, "not found") < 0);

            assertEquals("uniE04F",
                names[Arrays.binarySearch(names, "uniE04F")]);

        } catch (Exception e) {
            assertFalse(true);
        }
    }

    /**
     * Test for the parser.
     * 
     * @throws Exception if an error occurred.
     */
    public void testEncoding() throws Exception {

        PfbParser parser = new PfbParser(PFB_FILE);
        assertNotNull(parser);

        String[] enc = parser.getEncoding();
        assertNotNull(enc);
        assertEquals(256, enc.length);
        assertEquals(".notdef", enc[0]);
        assertEquals(".notdef", enc[1]);
        assertEquals(".notdef", enc[31]);
        assertEquals("space", enc[32]);
        assertEquals("plus", enc[43]);
        assertEquals("ydieresis", enc[255]);

    }

    /**
     * Test, if the pfb file exists.
     * 
     * @throws Exception if an error occurred.
     */
    public void testPfbExists() throws Exception {

        File file = new File(PFB_FILE);
        assertNotNull(file);
        assertTrue(file.exists());
        assertTrue(file.canRead());
    }

    /**
     * Test for the parser.
     * 
     * @throws Exception if an error occurred.
     */
    public void testPfbParser01() throws Exception {

        File file = new File(PFB_FILE);
        assertNotNull(file);
        assertTrue(file.exists());
        assertTrue(file.canRead());

        PfbParser parser = new PfbParser(file);
        assertNotNull(parser);

    }

    // /**
    // * Test for the parser.
    // *
    // * @throws Exception if an error occurred.
    // */
    // public void testXmlExport() throws Exception {

    // PfbParser parser = new PfbParser(PFB_FILE);
    // assertNotNull(parser);

    // XMLStreamWriter writer =
    // new XMLStreamWriter(new FileOutputStream("test.xml"),
    // "ISO-8859-1");
    // writer.setBeauty(true);
    // parser.writeXML(writer);
    // writer.close();
    // }
}
