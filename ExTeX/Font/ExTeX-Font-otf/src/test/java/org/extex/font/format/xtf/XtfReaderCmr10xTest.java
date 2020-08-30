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

import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.gps.OtfTableGSUB;
import org.extex.font.format.xtf.tables.gps.XtfGSUBLigatureTable;
import org.extex.font.format.xtf.tables.gps.XtfGSUBLigatureTable.LigatureSet;
import org.extex.font.format.xtf.tables.gps.XtfLookup;
import org.extex.font.format.xtf.tables.gps.XtfLookupTable;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.LangSys;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderCmr10xTest {

    private final static String DIR_TARGET = "build";

    private final XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderCmr10xTest() throws IOException {
        reader = new XtfReader("../ExTeX-Font-otf/src/font/cmr10.ttf");
    }

    /**
     * test 01.
     */
    @Test
    public void test01() {

        Assert.assertNotNull( reader );
    }

    /**
     * Test the gsub table.
     */
    @Test
    public void testGsub01() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );
    }

    /**
     * Test the langsys.
     */
    @Test
    public void testGsubLangSys01() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        LangSys langsys = gsub.findLangSys(ScriptTag.getInstance("XXX"), null);
        Assert.assertNull( "not exists", langsys );

    }

    /**
     * Test the langsys.

     */
    @Test
    public void testGsubLangSys02() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        // grek --------------------------------------
        LangSys langsys = gsub.findLangSys(ScriptTag.getInstance("grek"), null);
        Assert.assertEquals( "count", 1, langsys.getFeatureCount() );
        List<Integer> featurelist = langsys.getFeatureIndexList();
        Assert.assertNotNull( featurelist );

        // liga 0
        int feature = featurelist.get( 0 );
        Assert.assertEquals( 0, feature );
        Assert.assertEquals( "liga", gsub.getFeatureTag( feature ) );

        // latn ------------------------------------
        langsys = gsub.findLangSys(ScriptTag.getInstance("latn"), null);
        Assert.assertEquals( "count", 1, langsys.getFeatureCount() );
        featurelist = langsys.getFeatureIndexList();
        Assert.assertNotNull( featurelist );

        // liga 0
        feature = featurelist.get( 0 );
        Assert.assertEquals( 0, feature );
        Assert.assertEquals( "liga", gsub.getFeatureTag( feature ) );

    }

    /**
     * Test the langsys.
     */
    @Test
    public void testGsubLangSys03() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        // latn - liga - Ligature (4)
        XtfLookup[] lookups =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        Assert.assertNotNull( lookups );
        Assert.assertEquals( 1, lookups.length );
        Assert.assertEquals( "Ligature", lookups[ 0 ].getTypeAsString() );
    }

    /**
     * Test the ligature 01.
     */
    @Test
    public void testGsubLigature01() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        // latn - liga - Ligature (4)
        XtfLookup[] lookup =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        Assert.assertNotNull( lookup );
        Assert.assertEquals( "Ligature", lookup[ 0 ].getTypeAsString() );

        int count = lookup[0].getSubtableCount();
        Assert.assertEquals( 1, count );

        XtfLookupTable subtable = lookup[0].getSubtable(0);
        Assert.assertNotNull( subtable );

        // ligature table - format 1
        Assert.assertEquals( 1, subtable.getFormat() );
        Assert.assertTrue( subtable instanceof XtfGSUBLigatureTable );
        XtfGSUBLigatureTable ligtable = (XtfGSUBLigatureTable) subtable;
        // 3 tables
        Assert.assertEquals( 3, ligtable.getCount() );

        // 14 -> ff
        LigatureSet ligset = ligtable.getLigatureSet(14);
        Assert.assertNotNull( ligset );
        // <ligatureset id="0" count="2">
        Assert.assertEquals( 2, ligset.getLigatureCount() );
        // ff (14) + l (111) => ffl (18)
        Assert.assertEquals( 18, ligset.getLigature( new int[]{111} ) );
        // ff (14) + i (108) => ffi (17)
        Assert.assertEquals( 17, ligset.getLigature( new int[]{108} ) );

        // ff(14) + A (65) => null (-1)
        Assert.assertEquals( -1, ligset.getLigature( new int[]{65} ) );

        // Errors
        Assert.assertEquals( -1, ligset.getLigature( null ) );
        Assert.assertEquals( -1, ligset.getLigature( new int[]{} ) );

    }

    /**
     * Test the ligature 02.
     */
    @Test
    public void testGsubLigature02() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        // latn - liga - Ligature (4)
        XtfLookup[] lookup =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        Assert.assertNotNull( lookup );
        Assert.assertEquals( "Ligature", lookup[ 0 ].getTypeAsString() );

        int count = lookup[0].getSubtableCount();
        Assert.assertEquals( 1, count );

        XtfLookupTable subtable = lookup[0].getSubtable(0);
        Assert.assertNotNull( subtable );

        // ligature table - format 1
        Assert.assertEquals( 1, subtable.getFormat() );
        Assert.assertTrue( subtable instanceof XtfGSUBLigatureTable );
        XtfGSUBLigatureTable ligtable = (XtfGSUBLigatureTable) subtable;
        // 3 tables
        Assert.assertEquals( 3, ligtable.getCount() );

        // 126 -> endash
        LigatureSet ligset = ligtable.getLigatureSet(126);
        Assert.assertNotNull( ligset );
        // <ligatureset id="2" count="1">
        Assert.assertEquals( 1, ligset.getLigatureCount() );

        // endash (126) + hyphen (48) => emdash (127)
        Assert.assertEquals( 127, ligset.getLigature( new int[]{48} ) );

        // endash (126) + A (65) => null (-1)
        Assert.assertEquals( -1, ligset.getLigature( new int[]{65} ) );

    }

    /**
     * Test the ligature 03.
     */
    @Test
    public void testGsubLigature03() {

        Assert.assertNotNull( reader );

        XtfTable table = reader.getTable(XtfReader.GSUB);
        Assert.assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        Assert.assertNotNull( gsub );

        // latn - liga - Ligature (4)
        XtfLookup[] lookup =
                gsub.findLookup(ScriptTag.getInstance("latn"), null, FeatureTag
                    .getInstance("liga"));
        Assert.assertNotNull( lookup );
        Assert.assertEquals( "Ligature", lookup[ 0 ].getTypeAsString() );

        int count = lookup[0].getSubtableCount();
        Assert.assertEquals( 1, count );

        XtfLookupTable subtable = lookup[0].getSubtable(0);
        Assert.assertNotNull( subtable );

        // ligature table - format 1
        Assert.assertEquals( 1, subtable.getFormat() );
        Assert.assertTrue( subtable instanceof XtfGSUBLigatureTable );
        XtfGSUBLigatureTable ligtable = (XtfGSUBLigatureTable) subtable;
        // 3 tables
        Assert.assertEquals( 3, ligtable.getCount() );

        // 105 -> f
        LigatureSet ligset = ligtable.getLigatureSet(105);
        Assert.assertNotNull( ligset );
        // <ligatureset id="1" count="5">
        Assert.assertEquals( 5, ligset.getLigatureCount() );

        // f (105) + f (105), l (111) => ffl (18)
        Assert.assertEquals( 18, ligset.getLigature( new int[]{105, 111} ) );

        // f (105) + f (105), i (108) => ffi (17)
        Assert.assertEquals( 17, ligset.getLigature( new int[]{105, 108} ) );

        // f (105) + l (111) => fl (16)
        Assert.assertEquals( 16, ligset.getLigature( new int[]{111} ) );

        // f (105) + i (108) => fi (15)
        Assert.assertEquals( 15, ligset.getLigature( new int[]{108} ) );

        // f (105) + f (105) => ff (14)
        Assert.assertEquals( 14, ligset.getLigature( new int[]{105} ) );

    }

    /**
     * test 99.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testXMLOutput() throws Exception {

        Assert.assertNotNull( reader );

        XMLStreamWriter writer =
                new XMLStreamWriter(new FileOutputStream(DIR_TARGET + "/cmr10.xml"),
                    "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

    }

}
