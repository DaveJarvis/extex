/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.font.format.xtf.tables.cff.CffFont;
import org.extex.font.format.xtf.tables.cff.CharString;
import org.extex.font.format.xtf.tables.cff.T2Operator;
import org.extex.util.xml.XMLStreamWriter;
import org.junit.Test;

/**
 * Tests for the <code>XtfReader</code> with opentype files.
 * 
 * The test use the data from the <code>ttx</code> output.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderFxlrSubrsTest extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderFxlrSubrsTest() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
        }
    }

    /**
     * Check.
     * 
     * @param cs The CharString
     * @param text The text array.
     */
    private void check(CharString cs, String[] text) {

        for (int i = 0; i < text.length; i++) {
            String cmd = text[i];
            if (cmd == null || cmd.trim().length() == 0) {
                break;
            }
            assertFalse("IndexOutOfBoundsException", i >= cs.size());
            T2Operator c = cs.get(i);
            assertNotNull(c);
            String text2 = c.toText();
            assertEquals("CharStringCommand", cmd.trim(), text2);
        }

    }

    // --------------------------------------

    /**
     * test for index 0.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs0() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(0);
        assertNotNull(cs);
        String text[] =
                {
                        "-29 23 -24 30 29 24 24 29 29 -24 24 -29 -30 -23 -24 -29 vhcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 1.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs1() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(1);
        assertNotNull(cs);
        String text[] =
                {
                        "-22 -32 -19 -54 hvcurveto",
                        "0 -70 16 -35 9 -88 8 -78 5 -90 2 -21 1 -6 2 -7 11 0 10 0 3 9 1 11 2 14 1 60 13 108 10 86 16 42 0 65 rrcurveto",
                        "50 -29 23 -27 vhcurveto", "return"};
        check(cs, text);
    }

    /**
     * test for index 10.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs10() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(10);
        assertNotNull(cs);
        String text[] =
                {
                        "-29 24 -24 29 29 24 24 29 29 -24 24 -29 -29 -24 -24 -29 vhcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 11.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs11() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(11);
        assertNotNull(cs);
        String text[] =
                {
                        "16 0 15 -5 11 -10 29 -25 32 -71 0 -127 0 -87 -7 -60 -13 -48 -21 -78 -48 -11 -17 0 rrcurveto",
                        "-88 -15 165 82 233 63 42 43 hvcurveto", "return"};
        check(cs, text);
    }

    /**
     * test for index 2.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs2() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(2);
        assertNotNull(cs);
        String text[] =
                {
                        "27 4 rlineto",
                        "0 33 165 16 16 -9 17 -26 -30 -21 -28 -22 -9 26 -159 0 hvcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 3.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs3() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(3);
        assertNotNull(cs);
        String text[] =
                {
                        "58 42 83 49 18 27 -11 -49 -58 -36 -89 -55 -18 -27 14 52 vhcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 4.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs4() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(4);
        assertNotNull(cs);
        String text[] =
                {
                        "-18 0 -35 0 -54 25 -10 12 -29 13 -28 0 rrcurveto",
                        "-72 -59 -83 -76 -52 28 -57 62 84 44 112 65 hvcurveto",
                        "0 8 -1 9 -2 8 19 -11 38 -10 23 0 30 0 39 3 39 16 rrcurveto",
                        "-360 -502 37 -9 413 576 -38 7 rlineto",
                        "-41 -44 -67 -10 -42 0 rrcurveto", "return"};
        check(cs, text);
    }

    /**
     * test for index 5.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs5() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(5);
        assertNotNull(cs);
        String text[] =
                {
                        "-52 30 -58 61 83 44 110 68 72 -46 19 -40 -74 -58 -83 -76 vhcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 6.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs6() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(6);
        assertNotNull(cs);
        String text[] =
                {
                        "57 42 84 49 14 31 -9 -52 -55 -36 -92 -55 -19 -26 15 52 vhcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 7.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs7() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(7);
        assertNotNull(cs);
        String text[] =
                {"174 -50 -174 -173 -49 173 -173 50 173 172 49 vlineto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 703.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs703() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(703);
        assertNotNull(cs);
        String text[] =
                {"0 -20 1 -2 98 2 rlineto", "28 0 46 -2 80 0 rrcurveto",
                        "111 134 46 175 133 -109 109 -150 hvcurveto",
                        "-26 0 -61 -1 -53 -1 rrcurveto",
                        "-98 2 -1 -2 0 -21 rlineto", "-3 3 -2 4 vhcurveto",
                        "22 hlineto", "33 7 -8 -26 hvcurveto",
                        "-153 -71 -39 71 -135 vlineto",
                        "-31 -11 -14 -29 vhcurveto", "-23 hlineto",
                        "-4 -2 -3 -4 hvcurveto", "return"};
        check(cs, text);
    }

    /**
     * test for index 704.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs704() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(704);
        assertNotNull(cs);
        String text[] =
                {
                        "-63 159 hlineto",
                        "25 27 3 39 168 42 -121 -110 -144 -81 -32 -110 -76 -9 16 28 vhcurveto",
                        "137 63 vlineto", "return"};
        check(cs, text);
    }

    /**
     * test for index 705.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs705() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(705);
        assertNotNull(cs);
        String text[] =
                {
                        "-25 20 -20 26 24 21 20 25 25 -21 21 -24 -26 -20 -21 -25 vhcurveto",
                        "return"};
        check(cs, text);
    }

    /**
     * test for index 706.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs706() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(706);
        assertNotNull(cs);
        String text[] = {"-27 139 27 vlineto", "return"};
        check(cs, text);
    }

    /**
     * test for index 707.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs707() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(707);
        assertNotNull(cs);
        String text[] = {"159 180 497 0 0 -464 -656 0 rlineto", "return"};
        check(cs, text);
    }

    /**
     * test for index 8.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs8() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(8);
        assertNotNull(cs);
        String text[] =
                {
                        "-31 -22 -23 -28 hvcurveto",
                        "0 -33 26 -8 18 -3 19 -2 17 -4 0 -23 0 -21 -24 -30 -52 -13 rrcurveto",
                        "5 -25 rlineto", "60 11 50 33 0 70 rrcurveto",
                        "60 -26 39 -40 vhcurveto", "return"};
        check(cs, text);
    }

    /**
     * test for index 9.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testSubrs9() throws Exception {

        assertNotNull(reader);
        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);
        CffFont font = cff.getFont(0);
        assertNotNull(font);
        CharString cs = font.getSubrs(9);
        assertNotNull(cs);
        String text[] =
                {"13 14 25 11 9 -4 9 -10 hvcurveto", "-229 hlineto",
                        "-12 -13 -21 -16 -9 6 -8 9 hvcurveto", "return"};
        check(cs, text);
    }

    /**
     * Test: write the xml output to 'target'
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
