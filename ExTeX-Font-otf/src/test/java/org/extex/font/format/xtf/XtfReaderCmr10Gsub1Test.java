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

import org.extex.font.format.xtf.XtfScriptList.LangSys;
import org.extex.font.format.xtf.XtfScriptList.Script;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

/**
 * Tests for the <code>XtfReader</code> with cmr10.ttf (gsub).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderCmr10Gsub1Test {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderCmr10Gsub1Test() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/cmr10.ttf");
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

    /**
     * Test the name of the font.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testName() throws Exception {

        assertEquals("Computer Modern", reader.getFontFamilyName());
        assertEquals(132, reader.getNumberOfGlyphs());
    }

    /**
     * Test the gsub table.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsub01() throws Exception {

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        XtfScriptList scriptlist = gsub.getScriptList();
        assertNotNull(scriptlist);

        // ScriptCount=2
        assertEquals("scriptcount", 2, scriptlist.getCount());

        Script script = gsub.findScript("grek");
        assertNotNull(script);
        assertEquals("tag name", "grek", script.getTag());

        LangSys defLangSys = script.getDefaultLangSys();
        assertNotNull(defLangSys);
        // FeatureCount=1
        assertEquals("featurecount", 1, defLangSys.getFeatureCount());
        assertEquals("featureindex", 0, defLangSys.getFeatureIndex(0));

        script = gsub.findScript("latn");
        assertNotNull(script);
        assertEquals("tag name", "latn", script.getTag());

    }

    /**
     * Test the gsub table.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testGsub02() throws Exception {

        XtfTable table = reader.getTable(XtfReader.GSUB);
        assertNotNull(table);

        assertTrue(table instanceof OtfTableGSUB);
        OtfTableGSUB gsub = (OtfTableGSUB) table;
        assertNotNull(gsub);

        LangSys langSys = gsub.findLangSys("latn", null);
        assertNotNull(langSys);

    }

    /**
     * Test: write the xml output to 'target'
     * 
     * @throws Exception if an error occurred.
     */
    @Test
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

}
