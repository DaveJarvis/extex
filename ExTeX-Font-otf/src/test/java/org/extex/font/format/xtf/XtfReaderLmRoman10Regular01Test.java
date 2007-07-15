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
            reader =
                    new XtfReader(
                        "../ExTeX-Font-otf/src/font/lmroman10-regular.otf");
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
     * test 04a.
     * 
     * @throws Exception if an error occurred.
     */
    public void test04a() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);

        assertEquals("1.010", font.getVersion());
        assertEquals(
            "Copyright 2003, 2007 B. Jackowski and J. M. Nowacki (on behalf of TeX users groups). This work is released under the GUST Font License --  see http://tug.org/fonts/licenses/GUST-FONT-LICENSE.txt for details.",
            font.getNotice());
        assertEquals("LMRoman10-Regular", font.getFullName());
        assertEquals("LMRoman10", font.getFamilyName());
        assertEquals("Normal", font.getWeight());
        assertEquals("StandardEncoding", font.getEncoding());
        assertEquals(false, font.isFixedPitch());
        assertEquals(0, font.getItalicAngle());
        assertEquals(-146, font.getUnderlinePosition());
        assertEquals(40, font.getUnderlineThickness());
        assertEquals(0, font.getPaintType());
        assertEquals(2, font.getCharstringType());
        assertEquals(0, font.getStrokewidth());

    }

    /**
     * test 04b.
     * 
     * @throws Exception if an error occurred.
     */
    public void test04b() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        double[] fm = font.getFontMatrix();
        assertNotNull(fm);
        assertEquals(6, fm.length);
        // <FontMatrix value="0.001 0 0 0.001 0 0"/>
        double[] d = {0.001, 0, 0, 0.001, 0, 0};
        for (int i = 0; i < d.length; i++) {
            assertEquals(d[i], fm[i]);
        }

    }

    /**
     * test 04c.
     * 
     * @throws Exception if an error occurred.
     */
    public void test04c() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        int[] fb = font.getFontBBox();
        assertNotNull(fb);
        assertEquals(4, fb.length);
        // <FontBBox value="-430 -290 1417 1127"/>
        int[] iarr = {-430, -290, 1417, 1127};
        for (int i = 0; i < iarr.length; i++) {
            assertEquals(iarr[i], fb[i]);
        }
    }

    /**
     * test 05a.
     * 
     * @throws Exception if an error occurred.
     */
    public void test05a() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        int[] val = font.getBlueValues();
        assertNotNull(val);
        assertEquals(8, val.length);
        // <BlueValues value="-22 0 431 448 666 677 683 705"/>
        int[] iarr = {-22, 0, 431, 448, 666, 677, 683, 705};

        for (int i = 0; i < iarr.length; i++) {
            assertEquals(iarr[i], val[i]);
        }
    }

    /**
     * test 05b.
     * 
     * @throws Exception if an error occurred.
     */
    public void test05b() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        int[] val = font.getStemSnapH();
        assertNotNull(val);
        assertEquals(12, val.length);
        // <StemSnapH value="21 22 23 25 26 28 30 31 40 42 45 106"/>
        int[] iarr = {21, 22, 23, 25, 26, 28, 30, 31, 40, 42, 45, 106};

        for (int i = 0; i < iarr.length; i++) {
            assertEquals(iarr[i], val[i]);
        }

    }

    /**
     * test 05c.
     * 
     * @throws Exception if an error occurred.
     */
    public void test05c() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        int[] val = font.getStemSnapV();
        assertNotNull(val);
        assertEquals(12, val.length);
        // <StemSnapV value="25 30 40 66 69 77 83 89 92 97 103 107"/>
        int[] iarr = {25, 30, 40, 66, 69, 77, 83, 89, 92, 97, 103, 107};

        for (int i = 0; i < iarr.length; i++) {
            assertEquals(iarr[i], val[i]);
        }

    }

    /**
     * test 05d.
     * 
     * @throws Exception if an error occurred.
     */
    public void test05d() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        // <BlueScale value="0.04546"/>
        assertEquals(0.04546, font.getBlueScale());

        // <BlueShift value="7"/>
        assertEquals(7, font.getBlueShift());

        // <BlueFuzz value="0"/>
        assertEquals(0, font.getBlueFuzz());

        // <StdHW value="31"/>
        assertEquals(31, font.getStdHW());

        // <StdVW value="25"/>
        assertEquals(25, font.getStdVW());

        // <ForceBold value="0"/>
        assertEquals(false, font.getForceBold());

        // <LanguageGroup value="0"/>
        assertEquals(0, font.getLanguageGroup());

        // <ExpansionFactor value="0.06"/>
        assertEquals(0.06, font.getExpansionFactor());

        // <initialRandomSeed value="0"/>
        assertEquals(0, font.getInitialRandomSeed());

        // <defaultWidthX value="500"/>
        assertEquals(500, font.getDefaultWidthX());

        // <nominalWidthX value="658"/>
        assertEquals(658, font.getNominalWidthX());

    }

    /**
     * test 05.
     * 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        // plattformid = 3 (Windows) , encodingid = 1 (Unicode)
        assertEquals("space", reader.mapCharCodeToGlyphname(0x20, 0, (short) 3,
            (short) 1));
        assertEquals("zero", reader.mapCharCodeToGlyphname(0x30, 0, (short) 3,
            (short) 1));

    }

    /**
     * test 99.
     * 
     * @throws Exception if an error occurred.
     */
    public void test99() throws Exception {

        // XMLStreamWriter writer =
        // new XMLStreamWriter(
        // new FileOutputStream(
        // "/home/mgn/extex/Sandbox-mgn/src/xml/lmroman10-regular.xml"),
        // "ISO8859-1");
        // writer.setBeauty(true);
        // writer.writeStartDocument();
        // reader.writeXML(writer);
        // writer.writeEndDocument();
        // writer.close();

    }

    // --------------------------------------------------------------
}
