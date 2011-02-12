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

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.font.format.xtf.tables.TtfTableCMAP;
import org.extex.font.format.xtf.tables.TtfTableCMAP.Format;
import org.extex.font.format.xtf.tables.cff.CffFont;
import org.extex.font.format.xtf.tables.cff.CharString;
import org.extex.font.format.xtf.tables.cff.T2CallSubr;
import org.extex.font.format.xtf.tables.cff.T2EndChar;
import org.extex.font.format.xtf.tables.cff.T2HintMask;
import org.extex.font.format.xtf.tables.cff.T2HstemHm;
import org.extex.font.format.xtf.tables.cff.T2Number;
import org.extex.font.format.xtf.tables.cff.T2Operator;
import org.extex.font.format.xtf.tables.cff.T2PairNumber;
import org.extex.font.format.xtf.tables.cff.T2RLineTo;
import org.extex.font.format.xtf.tables.cff.T2RMoveTo;
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
            reader = new XtfReader("../ExTeX-Font-otf/src/font/fxlr.otf");
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
     * test 02.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test02() throws Exception {

        assertEquals("Linux Libertine", reader.getFontFamilyName());
        assertEquals(2309, reader.getNumberOfGlyphs());
    }

    /**
     * test 03.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
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
    @Test
    public void test04() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        assertEquals("2.6.x", font.getVersion());
        assertEquals(
            "LinuxLibertine by Philipp H. Poll, Free Font under Terms of the GPL (General Public License - http://www.gnu.org/copyleft/gpl.html) and OFL (Open Font Licence). Created with FontForge 1.0 (http://fontforge.sf.net) Sept 2003, 2004, 2005, 2006, 2007",
            font.getNotice());
        assertEquals("Linux Libertine", font.getFullName());
        assertEquals("Linux Libertine", font.getFamilyName());
        assertEquals("Book", font.getWeight());
        assertEquals("StandardEncoding", font.getEncoding());
        assertEquals(false, font.isFixedPitch());
        assertEquals(0, font.getItalicAngle());
        assertEquals(-97, font.getUnderlinePosition());
        assertEquals(39, font.getUnderlineThickness());
        assertEquals(0, font.getPaintType());
        assertEquals(2, font.getCharstringType());
        assertEquals(0, font.getStrokewidth());

        double[] fm = font.getFontMatrix();
        assertNotNull(fm);
        assertEquals(6, fm.length);
        assertEquals(0.001, fm[0], 0);
        assertEquals(0.0, fm[1], 0);
        assertEquals(0.0, fm[2], 0);
        assertEquals(0.001, fm[3], 0);
        assertEquals(0.0, fm[4], 0);
        assertEquals(0.0, fm[5], 0);

        int[] fb = font.getFontBBox();
        assertNotNull(fb);
        assertEquals(4, fb.length);
        assertEquals(-446, fb[0]);
        assertEquals(-312, fb[1]);
        assertEquals(6171, fb[2]);
        assertEquals(894, fb[3]);
    }

    /**
     * test 05.
     * 
     * <pre>
     * -CharString name="amacron"
     *     -50 -12 48 -48 41 383 30 97 44 hstemhm
     *     39 72 -61 73 168 74 53 25 hintmask 10110111
     *     355 -12 rmoveto
     *     -33 callsubr
     *     hintmask 01111011
     *     -32 callsubr
     *     -64 257 rmoveto
     *     -31 callsubr
     *     42 294 rmoveto
     *     82 callsubr
     *     endchar
     * -CharString
     *
     * </pre>
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test05() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        CharString cs = font.getCharstring("amacron");
        assertNotNull(cs);

        assertEquals("amacron", cs.getName());
        assertEquals(11, cs.size());

        // size -50
        // nominalWidthX value="501"
        assertEquals(501 - 50, cs.getWidth().intValue());

        T2Operator op = cs.get(0);
        assertNotNull(op);
        assertTrue(op instanceof T2HstemHm);
        T2HstemHm hstemhm = (T2HstemHm) op;
        T2PairNumber[] pairs = hstemhm.getPairs();
        assertNotNull(pairs);

        // -12 48 -48 41 383 30 97 44 hstemhm
        assertEquals(4, pairs.length);
        assertEquals(-12, pairs[0].getA().getInteger());
        assertEquals(48, pairs[0].getB().getInteger());
        assertEquals(-48, pairs[1].getA().getInteger());
        assertEquals(41, pairs[1].getB().getInteger());
        assertEquals(383, pairs[2].getA().getInteger());
        assertEquals(30, pairs[2].getB().getInteger());
        assertEquals(97, pairs[3].getA().getInteger());
        assertEquals(44, pairs[3].getB().getInteger());

        // 39 72 -61 73 168 74 53 25 hintmask 10110111
        op = cs.get(1);
        assertNotNull(op);
        assertTrue(op instanceof T2HintMask);
        T2HintMask hintmask = (T2HintMask) op;
        T2Number[] hintval = hintmask.getVal();
        assertNotNull(hintval);
        assertEquals(8, hintval.length);
        assertEquals(39, hintval[0].getInteger());
        assertEquals(72, hintval[1].getInteger());
        assertEquals(-61, hintval[2].getInteger());
        assertEquals(73, hintval[3].getInteger());
        assertEquals(168, hintval[4].getInteger());
        assertEquals(74, hintval[5].getInteger());
        assertEquals(53, hintval[6].getInteger());
        assertEquals(25, hintval[7].getInteger());
        assertEquals("10110111", hintmask.getMaskBin());

        // 355 -12 rmoveto
        op = cs.get(2);
        assertNotNull(op);
        assertTrue(op instanceof T2RMoveTo);
        T2RMoveTo rmoveto = (T2RMoveTo) op;
        assertEquals(355, rmoveto.getDx().getInteger());
        assertEquals(-12, rmoveto.getDy().getInteger());

        // -33 callsubr
        op = cs.get(3);
        assertNotNull(op);
        assertTrue(op instanceof T2CallSubr);
        T2CallSubr callsubr = (T2CallSubr) op;
        assertEquals(-33, callsubr.getSubr().getInteger());

        // hintmask 01111011
        op = cs.get(4);
        assertNotNull(op);
        assertTrue(op instanceof T2HintMask);
        hintmask = (T2HintMask) op;
        assertEquals("01111011", hintmask.getMaskBin());
        hintval = hintmask.getVal();
        assertNotNull(hintval);
        assertEquals(0, hintval.length);

        // -32 callsubr
        op = cs.get(5);
        assertNotNull(op);
        assertTrue(op instanceof T2CallSubr);
        callsubr = (T2CallSubr) op;
        assertEquals(-32, callsubr.getSubr().getInteger());

        // -64 257 rmoveto
        op = cs.get(6);
        assertNotNull(op);
        assertTrue(op instanceof T2RMoveTo);
        rmoveto = (T2RMoveTo) op;
        assertEquals(-64, rmoveto.getDx().getInteger());
        assertEquals(257, rmoveto.getDy().getInteger());

        // -31 callsubr
        op = cs.get(7);
        assertNotNull(op);
        assertTrue(op instanceof T2CallSubr);
        callsubr = (T2CallSubr) op;
        assertEquals(-31, callsubr.getSubr().getInteger());

        // 42 294 rmoveto
        op = cs.get(8);
        assertNotNull(op);
        assertTrue(op instanceof T2RMoveTo);
        rmoveto = (T2RMoveTo) op;
        assertEquals(42, rmoveto.getDx().getInteger());
        assertEquals(294, rmoveto.getDy().getInteger());

        // 82 callsubr
        op = cs.get(9);
        assertNotNull(op);
        assertTrue(op instanceof T2CallSubr);
        callsubr = (T2CallSubr) op;
        assertEquals(82, callsubr.getSubr().getInteger());

        // endchar
        op = cs.get(10);
        assertNotNull(op);
        assertTrue(op instanceof T2EndChar);

    }

    /**
     * test 06.
     * 
     * <pre>
     *   -CharString name="Eacute"
     *     68 -1 29 -28 39 288 34 82 -20 183 40 -28 28 74 -21 hstemhm
     *     102 80 194 25 hintmask 0011 1011 1000 0000
     *     353 606 rmoveto
     *     -67 callsubr
     *     hintmask 0011011110000000
     *     -66 callsubr
     *     hintmask 0011101110000000
     *     -65 callsubr
     *     hintmask 1011011110000000
     *     -64 callsubr
     *     hintmask 0111101110000000
     *     -63 callsubr
     *     132 215 rmoveto
     *     -124 -87 rlineto
     *     -13 -8 -2 -6 0 -7 0 -9 6 -5 9 0 8 0 13 3 22 8 rrcurveto
     *     144 59 rlineto
     *     endchar
     *   -CharString
     * </pre>
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test06() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        CharString cs = font.getCharstring("Eacute");
        assertNotNull(cs);

        assertEquals("Eacute", cs.getName());
        assertEquals(17, cs.size());

        T2Operator op = cs.get(0);
        assertNotNull(op);
        assertTrue(op instanceof T2HstemHm);
        T2HstemHm hstemhm = (T2HstemHm) op;
        T2PairNumber[] pairs = hstemhm.getPairs();
        assertNotNull(pairs);

        // 68 -1 29 -28 39 288 34 82 -20 183 40 -28 28 74 -21 hstemhm
        assertEquals(7, pairs.length);
        assertEquals(-1, pairs[0].getA().getInteger());
        assertEquals(29, pairs[0].getB().getInteger());
        assertEquals(-28, pairs[1].getA().getInteger());
        assertEquals(39, pairs[1].getB().getInteger());
        assertEquals(288, pairs[2].getA().getInteger());
        assertEquals(34, pairs[2].getB().getInteger());
        assertEquals(82, pairs[3].getA().getInteger());
        assertEquals(-20, pairs[3].getB().getInteger());
        assertEquals(183, pairs[4].getA().getInteger());
        assertEquals(40, pairs[4].getB().getInteger());
        assertEquals(-28, pairs[5].getA().getInteger());
        assertEquals(28, pairs[5].getB().getInteger());
        assertEquals(74, pairs[6].getA().getInteger());
        assertEquals(-21, pairs[6].getB().getInteger());

        // 102 80 194 25 hintmask 0011101110000000
        op = cs.get(1);
        assertNotNull(op);
        assertTrue(op instanceof T2HintMask);
        T2HintMask hintmask = (T2HintMask) op;
        T2Number[] hintval = hintmask.getVal();
        assertNotNull(hintval);
        assertEquals(4, hintval.length);
        assertEquals(102, hintval[0].getInteger());
        assertEquals(80, hintval[1].getInteger());
        assertEquals(194, hintval[2].getInteger());
        assertEquals(25, hintval[3].getInteger());
        assertEquals("0011101110000000", hintmask.getMaskBin());

        // 144 59 rlineto
        op = cs.get(15);
        assertNotNull(op);
        assertTrue(op instanceof T2RLineTo);
        T2RLineTo rlinto = (T2RLineTo) op;
        pairs = rlinto.getPairs();
        assertNotNull(pairs);
        assertEquals(1, pairs.length);
        assertEquals(144, pairs[0].getA().getInteger());
        assertEquals(59, pairs[0].getB().getInteger());

    }

    /**
     * test 07.
     * 
     * <pre>
     * -CharString name="amacron"
     *     -50 -12 48 -48 41 383 30 97 44 hstemhm
     *     39 72 -61 73 168 74 53 25 hintmask 10110111
     *     355 -12 rmoveto
     *     -33 callsubr
     *     hintmask 01111011
     *     -32 callsubr
     *     -64 257 rmoveto
     *     -31 callsubr
     *     42 294 rmoveto
     *     82 callsubr
     *     endchar
     * -CharString
     *
     * </pre>
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test07() throws Exception {

        OtfTableCFF cff = (OtfTableCFF) reader.getTable(XtfReader.CFF);
        assertNotNull(cff);

        CffFont font = cff.getFont(0);
        assertNotNull(font);

        CharString cs = font.getCharstring("amacron");
        assertNotNull(cs);

        assertEquals("amacron", cs.getName());
        assertEquals(11, cs.size());

        // size -50
        // nominalWidthX value="501"
        assertEquals(501 - 50, cs.getWidth().intValue());

        // -50 -12 48 -48 41 383 30 97 44 hstemhm
        T2Operator op = cs.get(0);
        assertNotNull(op);

        String text = op.toText();
        assertNotNull(text);

        assertEquals("-50 -12 48 -48 41 383 30 97 44 hstemhm", text);

    }

    // --------------------------------------------------------------

    /**
     * test 99.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test99() throws Exception {

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
