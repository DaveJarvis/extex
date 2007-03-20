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

import org.extex.font.format.xtf.TtfTableCMAP.Format;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 *
 * The test use the data from the <code>ttx</code> output. 
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderLmRoman10Regular01Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     *
     * @throws IOException if an error occurred.
     */
    public XtfReaderLmRoman10Regular01Test() throws IOException {

        if (reader == null) {
            reader = new XtfReader(
                    "../ExTeX-Font/src/font/lmotf/lmroman10-regular.otf");
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
     * test 02.
     *
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        assertEquals("LMRoman10 Regular", reader.getFontFamilyName());
        assertEquals(743, reader.getNumberOfGlyphs());
    }

    /**
     * test 03.
     *
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        TtfTableCMAP cmap = reader.getCmapTable();
        assertEquals(3, cmap.getNumTables());
        // windows - unicode
        Format format = cmap.getFormat((short) 3, (short) 1);
        assertNotNull(format);
        assertEquals(4, format.getFormat());
    }

    /**
     * test 04.
     *
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        assertEquals("1.010", cff.getTopDictIndex("version").getValue());
        assertEquals(
                "Copyright 2003, 2007 B. Jackowski and J. M. Nowacki (on behalf of TeX users groups). This work is released under the GUST Font License --  see http://tug.org/fonts/licenses/GUST-FONT-LICENSE.txt for details.",
                cff.getTopDictIndex("notice").getValue());
        assertEquals("LMRoman10-Regular", cff.getTopDictIndex("fullname")
                .getValue());
        assertEquals("LMRoman10", cff.getTopDictIndex("familyname").getValue());
        assertEquals("Normal", cff.getTopDictIndex("weight").getValue());
        assertEquals("StandardEncoding", cff.getEncoding());
        assertEquals(false, cff.isFixedPitch());
        assertEquals(0, cff.getItalicAngle());
        assertEquals(-146, cff.getUnderlinePosition());
        assertEquals(40, cff.getUnderlineThicknessn());
        //        assertEquals("0", cff.getTopDictIndex("PaintType").getValue());
        //        assertEquals("2", cff.getTopDictIndex("CharstringType").getValue());
        //        assertEquals("0", cff.getTopDictIndex("StrokeWidth").getValue());

        //        <FontMatrix value="0.001 0 0 0.001 0 0"/>
        //        <FontBBox value="-430 -290 1417 1127"/>
        //        <StrokeWidth value="0"/>

    }

    //    /**
    //     * test 05.
    //     *
    //     * @throws Exception if an error occurred.
    //     */
    //    public void test05() throws Exception {
    //
    //        // plattformid = 3 (Windows) , encodingid = 1 (Unicode)
    //        assertEquals("space", reader.mapCharCodeToGlyphname(0x20, (short) 3,
    //                (short) 1));
    //
    //    }

    /**
     * test 99.
     *
     * @throws Exception if an error occurred.
     */
    public void test99() throws Exception {

        XMLStreamWriter writer = new XMLStreamWriter(new FileOutputStream(
                "/home/mgn/extex/Sandbox-mgn/src/xml/lmroman10-regular.xml"),
                "ISO8859-1");
        writer.setBeauty(true);
        writer.writeStartDocument();
        reader.writeXML(writer);
        writer.writeEndDocument();
        writer.close();

        //        assertEquals("space", reader.mapCharCodeToGlyphname(0x20, (short) 3,
        //                (short) 1));
    }

    // --------------------------------------------------------------
}
