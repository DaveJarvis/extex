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

import junit.framework.TestCase;

import org.extex.font.format.xtf.TtfTableCMAP.Format;
import org.extex.font.format.xtf.cff.CffFont;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 *
 * The test use the data from the <code>ttx</code> output. 
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrTest extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     *
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrTest() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font/src/font/lmotf/fxlr.otf");
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

        assertEquals("Linux Libertine", reader.getFontFamilyName());
        assertEquals(2272, reader.getNumberOfGlyphs());
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

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        assertEquals("2.4.9", font.getVersion());
        assertEquals(
                "LinuxLibertine by Philipp H. Poll, Free Font under Terms of the GPL (General Public License - http://www.gnu.org/copyleft/gpl.html) and OFL (Open Font Licence). Created with FontForge 1.0 (http://fontforge.sf.net) Sept 2003, 2004, 2005, 2006, 2007",
                font.getNotice());
        assertEquals("Linux Libertine", font.getFullName());
        assertEquals("Linux Libertine", font.getFamilyName());
        assertEquals("Regular",font.getWeight());
        //    assertEquals("StandardEncoding", cff.getEncoding());
        //        assertEquals(false, cff.isFixedPitch());
        //        assertEquals(0, cff.getItalicAngle());
        //        assertEquals(-146, cff.getUnderlinePosition());
        //        assertEquals(40, cff.getUnderlineThicknessn());
        //        assertEquals("0", cff.getTopDictIndex("PaintType").getValue());
        //        assertEquals("2", cff.getTopDictIndex("CharstringType").getValue());
        //        assertEquals("0", cff.getTopDictIndex("StrokeWidth").getValue());

        //        <FontMatrix value="0.001 0 0 0.001 0 0"/>
        //        <FontBBox value="-430 -290 1417 1127"/>
        //        <StrokeWidth value="0"/>

    }

    /**
     * test 99.
     *
     * @throws Exception if an error occurred.
     */
    public void test99() throws Exception {

        //        XMLStreamWriter writer = new XMLStreamWriter(new FileOutputStream(
        //                "/home/mgn/extex/Sandbox-mgn/src/xml/fxlr.xml"),
        //                "ISO8859-1");
        //        writer.setBeauty(true);
        //        writer.writeStartDocument();
        //        reader.writeXML(writer);
        //        writer.writeEndDocument();
        //        writer.close();

        //        assertEquals("space", reader.mapCharCodeToGlyphname(0x20, (short) 3,
        //                (short) 1));
    }

    // --------------------------------------------------------------
}
