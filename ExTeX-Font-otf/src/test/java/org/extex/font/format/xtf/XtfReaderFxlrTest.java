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
import org.extex.font.format.xtf.cff.CffFont;
import org.extex.font.format.xtf.cff.CharString;
import org.extex.font.format.xtf.cff.T2HintMask;
import org.extex.font.format.xtf.cff.T2HstemHm;
import org.extex.font.format.xtf.cff.T2Number;
import org.extex.font.format.xtf.cff.T2Operator;
import org.extex.font.format.xtf.cff.T2PairNumber;
import org.extex.font.format.xtf.cff.T2RMoveTo;
import org.extex.util.xml.XMLStreamWriter;

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
        assertEquals(2309, reader.getNumberOfGlyphs());
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
        assertEquals(0.001, fm[0]);
        assertEquals(0.0, fm[1]);
        assertEquals(0.0, fm[2]);
        assertEquals(0.001, fm[3]);
        assertEquals(0.0, fm[4]);
        assertEquals(0.0, fm[5]);

        int[] fb = font.getFontBBox();
        assertNotNull(fb);
        assertEquals(4, fb.length);
        assertEquals(-446, fb[0]);
        assertEquals(-312, fb[1]);
        assertEquals(6171, fb[2]);
        assertEquals(894, fb[3]);
    }

    /**
     * test 99.
     * 
     * @throws Exception if an error occurred.
     */
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
        // assertTrue(op instanceof t2c)

    }
    // --------------------------------------------------------------
}
