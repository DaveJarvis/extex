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
import java.util.List;

import junit.framework.TestCase;

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.OtfTableGSUB;
import org.extex.font.format.xtf.tables.gps.XtfGSUBLigatureTable;
import org.extex.font.format.xtf.tables.gps.XtfLookup;
import org.extex.font.format.xtf.tables.gps.XtfLookupTable;
import org.extex.font.format.xtf.tables.gps.XtfGSUBLigatureTable.LigatureSet;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.LangSys;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

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
    @Test
    public void test01() throws Exception {

        assertNotNull(reader);
    }

    /**
     * Test the gsub table.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsub01() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);
    }

    /**
     * Test the langsys.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsubLangSys01() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        LangSys langsys = gsub.findLangSys(ScriptTag.getInstance("XXX"), null);
        assertNull("not exists", langsys);

    }

    /**
     * Test the langsys.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsubLangSys02() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        // grek --------------------------------------
        LangSys langsys = gsub.findLangSys(ScriptTag.getInstance("grek"), null);
        assertEquals("count", 1, langsys.getFeatureCount());
        List<Integer> featurelist = langsys.getFeatureIndexList();
        assertNotNull(featurelist);

        // liga 0
        int feature = featurelist.get(0).intValue();
        assertEquals(0, feature);
        assertEquals("liga", gsub.getFeatureTag(feature));

        // latn ------------------------------------
        langsys = gsub.findLangSys(ScriptTag.getInstance("latn"), null);
        assertEquals("count", 1, langsys.getFeatureCount());
        featurelist = langsys.getFeatureIndexList();
        assertNotNull(featurelist);

        // liga 0
        feature = featurelist.get(0).intValue();
        assertEquals(0, feature);
        assertEquals("liga", gsub.getFeatureTag(feature));

    }

    /**
     * Test the langsys.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsubLangSys03() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        // latn - liga - Ligature (4)
        XtfLookup[] lookups =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        assertNotNull(lookups);
        assertEquals(1, lookups.length);
        assertEquals("Ligature", lookups[0].getTypeAsString());
    }

    /**
     * Test the ligature 01.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsubLigature01() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        // latn - liga - Ligature (4)
        XtfLookup[] lookup =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        assertNotNull(lookup);
        assertEquals("Ligature", lookup[0].getTypeAsString());

        int count = lookup[0].getSubtableCount();
        assertEquals(1, count);

        XtfLookupTable subtable = lookup[0].getSubtable(0);
        assertNotNull(subtable);

        // ligature table - format 1
        assertEquals(1, subtable.getFormat());
        assertTrue(subtable instanceof XtfGSUBLigatureTable);
        XtfGSUBLigatureTable ligtable = (XtfGSUBLigatureTable) subtable;
        // 3 tables
        assertEquals(3, ligtable.getCount());

        // 14 -> ff
        LigatureSet ligset = ligtable.getLigatureSet(14);
        assertNotNull(ligset);
        // <ligatureset id="0" count="2">
        assertEquals(2, ligset.getLigatureCount());
        // ff (14) + l (111) => ffl (18)
        assertEquals(18, ligset.getLigature(new int[]{111}));
        // ff (14) + i (108) => ffi (17)
        assertEquals(17, ligset.getLigature(new int[]{108}));

        // ff(14) + A (65) => null (-1)
        assertEquals(-1, ligset.getLigature(new int[]{65}));

        // Errors
        assertEquals(-1, ligset.getLigature(null));
        assertEquals(-1, ligset.getLigature(new int[]{}));

    }

    /**
     * Test the ligature 02.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsubLigature02() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        // latn - liga - Ligature (4)
        XtfLookup[] lookup =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        assertNotNull(lookup);
        assertEquals("Ligature", lookup[0].getTypeAsString());

        int count = lookup[0].getSubtableCount();
        assertEquals(1, count);

        XtfLookupTable subtable = lookup[0].getSubtable(0);
        assertNotNull(subtable);

        // ligature table - format 1
        assertEquals(1, subtable.getFormat());
        assertTrue(subtable instanceof XtfGSUBLigatureTable);
        XtfGSUBLigatureTable ligtable = (XtfGSUBLigatureTable) subtable;
        // 3 tables
        assertEquals(3, ligtable.getCount());

        // 126 -> endash
        LigatureSet ligset = ligtable.getLigatureSet(126);
        assertNotNull(ligset);
        // <ligatureset id="2" count="1">
        assertEquals(1, ligset.getLigatureCount());

        // endash (126) + hyphen (48) => emdash (127)
        assertEquals(127, ligset.getLigature(new int[]{48}));

        // endash (126) + A (65) => null (-1)
        assertEquals(-1, ligset.getLigature(new int[]{65}));

    }

    /**
     * Test the ligature 03.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsubLigature03() throws Exception {

        assertNotNull(reader);

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        // latn - liga - Ligature (4)
        XtfLookup[] lookup =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        assertNotNull(lookup);
        assertEquals("Ligature", lookup[0].getTypeAsString());

        int count = lookup[0].getSubtableCount();
        assertEquals(1, count);

        XtfLookupTable subtable = lookup[0].getSubtable(0);
        assertNotNull(subtable);

        // ligature table - format 1
        assertEquals(1, subtable.getFormat());
        assertTrue(subtable instanceof XtfGSUBLigatureTable);
        XtfGSUBLigatureTable ligtable = (XtfGSUBLigatureTable) subtable;
        // 3 tables
        assertEquals(3, ligtable.getCount());

        // 105 -> f
        LigatureSet ligset = ligtable.getLigatureSet(105);
        assertNotNull(ligset);
        // <ligatureset id="1" count="5">
        assertEquals(5, ligset.getLigatureCount());

        // f (105) + f (105), l (111) => ffl (18)
        assertEquals(18, ligset.getLigature(new int[]{105, 111}));

        // f (105) + f (105), i (108) => ffi (17)
        assertEquals(17, ligset.getLigature(new int[]{105, 108}));

        // f (105) + l (111) => fl (16)
        assertEquals(16, ligset.getLigature(new int[]{111}));

        // f (105) + i (108) => fi (15)
        assertEquals(15, ligset.getLigature(new int[]{108}));

        // f (105) + f (105) => ff (14)
        assertEquals(14, ligset.getLigature(new int[]{105}));

    }

    /**
     * test 99.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testXMLOutput() throws Exception {

        assertNotNull(reader);

        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream("target/cmr10.xml"),
                    "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

    }

}
