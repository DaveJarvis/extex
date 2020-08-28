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
import org.extex.font.format.xtf.tables.gps.XtfLookup;
import org.extex.font.format.xtf.tables.gps.XtfScriptList;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.LangSys;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.Script;
import org.extex.font.format.xtf.tables.tag.FeatureTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrGsub1Test {

    private final XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrGsub1Test() throws IOException {
        reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
    }

    /**
     * Test, if the reader exits.
     */
    @Test
    public void testExists() {
        assertNotNull( reader );
    }

    /**
     * Test the gsub table.
     */
    @Test
    public void testGsub01() {

        assertNotNull( reader );
        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull( gsub );

        XtfScriptList scriptlist = gsub.getScriptList();
        assertNotNull( scriptlist );

        // ScriptCount=6
        assertEquals( "scriptcount", 6, scriptlist.getCount() );

        Script script = gsub.findScript(ScriptTag.getDefault());
        assertNotNull( script );
        assertEquals( "tag name", "DFLT", script.getTag() );
        LangSys defLangSys = script.getDefaultLangSys();
        assertNotNull( defLangSys );
        // FeatureCount=19
        assertEquals( "featurecount", 19, defLangSys.getFeatureCount() );
        for (int i = 0; i < 19; i++) {
            assertEquals( "featureindex",
                                 i,
                                 defLangSys.getFeatureIndex( i ) );
        }

        script = gsub.findScript(ScriptTag.getInstance("cyrl"));
        assertNotNull( script );
        assertEquals( "tag name", "cyrl", script.getTag() );

        script = gsub.findScript(ScriptTag.getInstance("goth"));
        assertNotNull( script );
        assertEquals( "tag name", "goth", script.getTag() );

        script = gsub.findScript(ScriptTag.getInstance("grek"));
        assertNotNull( script );
        assertEquals( "tag name", "grek", script.getTag() );

        script = gsub.findScript(ScriptTag.getInstance("hebr"));
        assertNotNull( script );
        assertEquals( "tag name", "hebr", script.getTag() );

        script = gsub.findScript(ScriptTag.getInstance("latn"));
        assertNotNull( script );
        assertEquals( "tag name", "latn", script.getTag() );

    }

    /**
     * Test the gsub table 02.
     */
    @Test
    public void testGsub02() {
        assertNotNull( reader );
        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull( table );

        Assert.assertTrue( table instanceof OtfTableGSUB );
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull( gsub );

        XtfLookup[] lookups =
                gsub.findLookup(ScriptTag.getDefault(), null, FeatureTag
                    .getInstance("zero"));
        assertNotNull( lookups );
        assertEquals( 1, lookups.length );
        assertEquals( 1, lookups[ 0 ].getSubtableCount() );
        assertEquals( "Single", lookups[ 0 ].getTypeAsString() );

    }

    /**
     * Test the name of the font.
     */
    @Test
    public void testName() {

        assertNotNull( reader );
        assertEquals( "Linux Libertine", reader.getFontFamilyName() );
        assertEquals( 2309, reader.getNumberOfGlyphs() );
    }

    /**
     * Test: write the xml output to 'tartet'
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testXmlOut() throws Exception {

        assertNotNull( reader );
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
