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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.OtfTableGSUB;
import org.extex.font.format.xtf.tables.gps.XtfGSUBSingleTable;
import org.extex.font.format.xtf.tables.gps.XtfLookup;
import org.extex.font.format.xtf.tables.gps.XtfLookupTable;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

/**
 * Tests for the <code>XtfReader</code> with opentype files (GSUB-Single).
 * <p>
 * data from ttx
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrGsubSingleTest {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrGsubSingleTest() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
        }
    }

    /**
     * Check.
     * 
     * @param featureTag The feature tag.
     * @param in The in glyph name.
     * @param out The out glyph name.
     */
    private void check(String featureTag, String in, String out) {

        assertNotNull(reader);
        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        XtfLookup[] lookups =
                gsub.findLookup(ScriptTag.getInstance("DFLT"), null, FeatureTag
                    .getInstance(featureTag));
        assertNotNull(lookups);
        assertEquals(1, lookups.length);
        assertEquals(1, lookups[0].getSubtableCount());
        assertEquals("Single", lookups[0].getTypeAsString());

        int cnt = lookups[0].getSubtableCount();
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            XtfLookupTable subtable = lookups[0].getSubtable(i);
            assertTrue(subtable instanceof XtfGSUBSingleTable);
            XtfGSUBSingleTable single = (XtfGSUBSingleTable) subtable;
            String[][] glyphInOut = single.getSubGlyph();
            assertNotNull(glyphInOut);

            for (int k = 0; k < glyphInOut.length; k++) {
                String inX = glyphInOut[k][0];
                String outX = glyphInOut[k][1];
                if (in.equals(inX)) {
                    if (out.equals(outX)) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        assertTrue("Single found " + in + " " + out, found);

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

    /**
     * test zero.
     */
    @Test
    public void testGSUBSingle_zero() {

        check("zero", "zero", "zero.slash");
    }

    /**
     * test zero.fitted.
     */
    @Test
    public void testGSUBSingle_zerofitted() {

        check("zero", "zero.fitted", "zero.slashfitted");
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
