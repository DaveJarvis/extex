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

/**
 * Tests for the <code>XtfReader</code>.
 * 
 * The test use the data from the <code>ttx</code> output.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderGara4Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderGara4Test() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-Font-otf/src/font/Gara.ttf");
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

        assertEquals("Garamond", reader.getFontFamilyName());
        assertEquals(662, reader.getNumberOfGlyphs());
    }

    /**
     * test 02.
     * 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("A", 0, (short) 3, (short) 1);
        assertNotNull(bb);

        // name="A" xMin="-15" yMin="0" xMax="1371" yMax="1343"
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    // ---------------------------------------------------

    /**
     * test A
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfA() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("A", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test AE
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAE() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("AE", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-127, -10, 1697, 1286));
    }

    /**
     * test AEacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAEacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("AEacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-127, -10, 1697, 1720));
    }

    /**
     * test Aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Aacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1714));
    }

    /**
     * test Abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Abreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1697));
    }

    /**
     * test Acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Acircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1761));
    }

    /**
     * test Adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Adieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1579));
    }

    /**
     * test Agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Agrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1715));
    }

    /**
     * test Alpha
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAlpha() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Alpha", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test Alphatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAlphatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Alphatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test Amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAmacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Amacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1593));
    }

    /**
     * test Aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Aogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-14, -328, 1475, 1343));
    }

    /**
     * test Aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAring() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Aring", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1654));
    }

    /**
     * test Aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAringacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Aringacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 2020));
    }

    /**
     * test Atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfAtilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Atilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1608));
    }

    /**
     * test B
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfB() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("B", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1164, 1298));
    }

    /**
     * test Beta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfBeta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Beta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1164, 1298));
    }

    /**
     * test C
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfC() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("C", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1311));
    }

    /**
     * test Cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfCacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Cacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1714));
    }

    /**
     * test Ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfCcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ccaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1761));
    }

    /**
     * test Ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfCcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ccedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -431, 1231, 1311));
    }

    /**
     * test Ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfCcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ccircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1760));
    }

    /**
     * test Cdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfCdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Cdot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1573));
    }

    /**
     * test Chi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfChi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Chi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -21, 1449, 1277));
    }

    /**
     * test D
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfD() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("D", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(22, -17, 1479, 1301));
    }

    /**
     * test Dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfDcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Dcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(22, -17, 1479, 1768));
    }

    /**
     * test Dslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfDslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Dslash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(15, -17, 1480, 1300));
    }

    /**
     * test E
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfE() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("E", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1274));
    }

    /**
     * test Eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Eacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1714));
    }

    /**
     * test Ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ebreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1697));
    }

    /**
     * test Ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ecaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1768));
    }

    /**
     * test Ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ecircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1761));
    }

    /**
     * test Edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Edieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1579));
    }

    /**
     * test Edot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Edot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1573));
    }

    /**
     * test Egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Egrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1715));
    }

    /**
     * test Emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEmacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Emacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1593));
    }

    /**
     * test Eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEng() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Eng", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(100, -17, 1437, 1311));
    }

    /**
     * test Eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Eogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -328, 1296, 1274));
    }

    /**
     * test Epsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEpsilon() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Epsilon", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1274));
    }

    /**
     * test Epsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEpsilontonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Epsilontonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -14, 1429, 1292));
    }

    /**
     * test Eta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Eta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test Etatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEtatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Etatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -21, 1598, 1292));
    }

    /**
     * test Eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfEth() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Eth", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(15, -17, 1480, 1301));
    }

    /**
     * test F
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfF() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("F", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(59, -20, 1107, 1294));
    }

    /**
     * test G
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfG() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("G", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1312));
    }

    /**
     * test Gamma
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfGamma() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Gamma", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1274));
    }

    /**
     * test Gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfGbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Gbreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1688));
    }

    /**
     * test Gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfGcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Gcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -621, 1553, 1312));
    }

    /**
     * test Gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfGcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Gcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1760));
    }

    /**
     * test Gdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfGdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Gdot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1573));
    }

    /**
     * test H
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfH() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("H", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test H18533
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfH18533() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("H18533", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(178, 137, 1059, 1018));
    }

    /**
     * test H18543
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfH18543() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("H18543", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(92, 405, 635, 948));
    }

    /**
     * test H18551
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfH18551() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("H18551", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(92, 405, 635, 948));
    }

    /**
     * test H22073
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfH22073() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("H22073", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 0, 1090, 944));
    }

    /**
     * test Hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfHbar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Hbar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test Hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfHcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Hcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1764));
    }

    /**
     * test I
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfI() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("I", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1279));
    }

    /**
     * test IJ
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIJ() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("IJ", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -518, 1295, 1279));
    }

    /**
     * test Iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Iacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1714));
    }

    /**
     * test Ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ibreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1697));
    }

    /**
     * test Icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Icircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1761));
    }

    /**
     * test Idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Idieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1579));
    }

    /**
     * test Igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Igrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1715));
    }

    /**
     * test Imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfImacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Imacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1589));
    }

    /**
     * test Iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Iogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -364, 664, 1279));
    }

    /**
     * test Iota
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIota() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Iota", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1279));
    }

    /**
     * test Iotadieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIotadieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Iotadieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1577));
    }

    /**
     * test Iotatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfIotatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Iotatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, 0, 814, 1292));
    }

    /**
     * test Itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfItilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Itilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1589));
    }

    /**
     * test J
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfJ() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("J", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-173, -518, 569, 1279));
    }

    /**
     * test Jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfJcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Jcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-173, -518, 569, 1764));
    }

    /**
     * test K
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfK() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("K", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1556, 1282));
    }

    /**
     * test Kappa
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfKappa() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Kappa", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1556, 1282));
    }

    /**
     * test Kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfKcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Kcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -435, 1556, 1282));
    }

    /**
     * test L
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfL() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("L", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1274));
    }

    /**
     * test Lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfLacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Lacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1716));
    }

    /**
     * test Lambda
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfLambda() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Lambda", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test Lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfLcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Lcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1303));
    }

    /**
     * test Lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfLcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Lcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -435, 1177, 1274));
    }

    /**
     * test Ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfLdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ldot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1274));
    }

    /**
     * test Lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfLslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Lslash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -6, 1184, 1274));
    }

    /**
     * test M
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfM() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("M", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, -9, 1692, 1289));
    }

    /**
     * test Mu
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfMu() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Mu", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, -9, 1692, 1289));
    }

    /**
     * test N
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfN() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("N", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1286));
    }

    /**
     * test Nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfNacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Nacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1724));
    }

    /**
     * test Ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfNcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ncaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1768));
    }

    /**
     * test Ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfNcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ncedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -435, 1500, 1286));
    }

    /**
     * test Ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfNtilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ntilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1608));
    }

    /**
     * test Nu
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfNu() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Nu", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1286));
    }

    /**
     * test O
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfO() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("O", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1292));
    }

    /**
     * test OE
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOE() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("OE", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -17, 1863, 1289));
    }

    /**
     * test Oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Oacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1714));
    }

    /**
     * test Obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfObreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Obreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1697));
    }

    /**
     * test Ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ocircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1761));
    }

    /**
     * test Odblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOdblacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Odblacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1720));
    }

    /**
     * test Odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Odieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1579));
    }

    /**
     * test Ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ograve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1715));
    }

    /**
     * test Ohm
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOhm() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ohm", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(57, 0, 1457, 1387));
    }

    /**
     * test Omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Omacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1593));
    }

    /**
     * test Omega
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmega() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Omega", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(103, -8, 1486, 1292));
    }

    /**
     * test Omegatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmegatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Omegatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -8, 1498, 1292));
    }

    /**
     * test Omicron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmicron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Omicron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1292));
    }

    /**
     * test Omicrontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmicrontonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Omicrontonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -20, 1502, 1292));
    }

    /**
     * test Oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Oslash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -62, 1502, 1335));
    }

    /**
     * test Oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOslashacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Oslashacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -62, 1502, 1728));
    }

    /**
     * test Otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfOtilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Otilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1608));
    }

    /**
     * test P
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfP() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("P", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1295));
    }

    /**
     * test Phi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfPhi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Phi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -8, 1556, 1284));
    }

    /**
     * test Pi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfPi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Pi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1486, 1284));
    }

    /**
     * test Psi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfPsi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Psi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, 0, 1537, 1292));
    }

    /**
     * test Q
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfQ() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Q", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(97, -446, 1532, 1316));
    }

    /**
     * test R
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfR() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("R", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -5, 1313, 1289));
    }

    /**
     * test Racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfRacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Racute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -5, 1313, 1720));
    }

    /**
     * test Rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfRcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Rcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -5, 1313, 1768));
    }

    /**
     * test Rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfRcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Rcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -435, 1313, 1289));
    }

    /**
     * test Rho
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfRho() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Rho", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1295));
    }

    /**
     * test S
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfS() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("S", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1316));
    }

    /**
     * test SF010000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF010000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF010000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 709));
    }

    /**
     * test SF020000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF020000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF020000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, 534, 1474, 1864));
    }

    /**
     * test SF030000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF030000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF030000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 709));
    }

    /**
     * test SF040000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF040000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF040000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 812, 1864));
    }

    /**
     * test SF050000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF050000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF050000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test SF060000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF060000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF060000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 709));
    }

    /**
     * test SF070000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF070000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF070000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1473, 1864));
    }

    /**
     * test SF080000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF080000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF080000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 1864));
    }

    /**
     * test SF090000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF090000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF090000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 1864));
    }

    /**
     * test SF100000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF100000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF100000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1473, 709));
    }

    /**
     * test SF110000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF110000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF110000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 813, 1864));
    }

    /**
     * test SF190000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF190000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF190000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 1864));
    }

    /**
     * test SF200000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF200000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF200000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 1864));
    }

    /**
     * test SF210000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF210000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF210000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 709));
    }

    /**
     * test SF220000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF220000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF220000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 899));
    }

    /**
     * test SF230000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF230000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF230000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 1864));
    }

    /**
     * test SF240000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF240000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF240000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1004, 1864));
    }

    /**
     * test SF250000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF250000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF250000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 899));
    }

    /**
     * test SF260000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF260000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF260000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1003, 1864));
    }

    /**
     * test SF270000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF270000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF270000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1003, 1864));
    }

    /**
     * test SF280000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF280000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF280000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 812, 1864));
    }

    /**
     * test SF360000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF360000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF360000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 1864));
    }

    /**
     * test SF370000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF370000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF370000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1474, 1864));
    }

    /**
     * test SF380000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF380000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF380000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, 344, 1474, 1864));
    }

    /**
     * test SF390000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF390000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF390000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1474, 899));
    }

    /**
     * test SF400000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF400000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF400000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1473, 1864));
    }

    /**
     * test SF410000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF410000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF410000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 899));
    }

    /**
     * test SF420000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF420000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF420000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1474, 1864));
    }

    /**
     * test SF430000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF430000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF430000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1473, 899));
    }

    /**
     * test SF440000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF440000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF440000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test SF450000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF450000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF450000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1473, 1864));
    }

    /**
     * test SF460000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF460000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF460000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1473, 1864));
    }

    /**
     * test SF470000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF470000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF470000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 899));
    }

    /**
     * test SF480000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF480000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF480000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 709));
    }

    /**
     * test SF490000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF490000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF490000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, 534, 1475, 1864));
    }

    /**
     * test SF500000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF500000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF500000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, 344, 1474, 1864));
    }

    /**
     * test SF510000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF510000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF510000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 899));
    }

    /**
     * test SF520000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF520000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF520000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1475, 709));
    }

    /**
     * test SF530000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF530000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF530000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test SF540000
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF540000() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("SF540000", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test Sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Sacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1720));
    }

    /**
     * test Scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfScaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Scaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1761));
    }

    /**
     * test Scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfScedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Scedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -431, 895, 1316));
    }

    /**
     * test Scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfScircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Scircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1764));
    }

    /**
     * test Sigma
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfSigma() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Sigma", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -15, 1211, 1272));
    }

    /**
     * test T
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfT() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("T", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test Tau
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfTau() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Tau", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test Tbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfTbar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Tbar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test Tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfTcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Tcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1768));
    }

    /**
     * test Tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfTcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Tcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -589, 1233, 1331));
    }

    /**
     * test Theta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfTheta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Theta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1503, 1292));
    }

    /**
     * test Thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfThorn() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Thorn", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1280));
    }

    /**
     * test U
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfU() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("U", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1286));
    }

    /**
     * test Uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Uacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1714));
    }

    /**
     * test Ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ubreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1697));
    }

    /**
     * test Ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ucircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1761));
    }

    /**
     * test Udblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUdblacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Udblacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1724));
    }

    /**
     * test Udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Udieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1579));
    }

    /**
     * test Ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ugrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1715));
    }

    /**
     * test Umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUmacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Umacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1589));
    }

    /**
     * test Uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Uogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -364, 1384, 1286));
    }

    /**
     * test Upsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUpsilon() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Upsilon", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -13, 1168, 1292));
    }

    /**
     * test Upsilondieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUpsilondieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Upsilondieresis", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -13, 1168, 1577));
    }

    /**
     * test Upsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUpsilontonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Upsilontonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-44, -13, 1352, 1292));
    }

    /**
     * test Uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUring() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Uring", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1746));
    }

    /**
     * test Utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfUtilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Utilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1585));
    }

    /**
     * test V
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfV() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("V", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-17, -39, 1406, 1287));
    }

    /**
     * test W
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfW() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("W", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1279));
    }

    /**
     * test Wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfWacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Wacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1720));
    }

    /**
     * test Wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfWcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Wcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1764));
    }

    /**
     * test Wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfWdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Wdieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1577));
    }

    /**
     * test Wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfWgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Wgrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1717));
    }

    /**
     * test X
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfX() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("X", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -21, 1449, 1277));
    }

    /**
     * test Xi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfXi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Xi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(69, -3, 1320, 1272));
    }

    /**
     * test Y
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfY() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Y", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1290));
    }

    /**
     * test Yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfYacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Yacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1714));
    }

    /**
     * test Ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfYcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ycircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1760));
    }

    /**
     * test Ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfYdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ydieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1579));
    }

    /**
     * test Ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfYgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Ygrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1717));
    }

    /**
     * test Z
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfZ() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Z", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1346));
    }

    /**
     * test Zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfZacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Zacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1720));
    }

    /**
     * test Zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfZcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Zcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1761));
    }

    /**
     * test Zdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfZdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Zdot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1573));
    }

    /**
     * test Zeta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfZeta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("Zeta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1346));
    }

    /**
     * test a
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfa() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("a", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 817));
    }

    /**
     * test aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfaacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("aacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1292));
    }

    /**
     * test abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfabreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("abreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1233));
    }

    /**
     * test acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfacircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("acircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1332));
    }

    /**
     * test acute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("acute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(244, 983, 582, 1292));
    }

    /**
     * test adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfadieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("adieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1229));
    }

    /**
     * test ae
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfae() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ae", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(74, -32, 1149, 818));
    }

    /**
     * test aeacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfaeacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("aeacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(74, -32, 1149, 1292));
    }

    /**
     * test afii00208
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii00208() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii00208", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, 345, 1548, 438));
    }

    /**
     * test afii08941
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii08941() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii08941", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -482, 1211, 1297));
    }

    /**
     * test afii10017
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10017() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10017", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test afii10018
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10018() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10018", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1180, 1274));
    }

    /**
     * test afii10019
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10019() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10019", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1164, 1298));
    }

    /**
     * test afii10020
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10020() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10020", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -6, 1105, 1273));
    }

    /**
     * test afii10021
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10021() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10021", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-49, -299, 1385, 1343));
    }

    /**
     * test afii10022
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10022() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10022", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1274));
    }

    /**
     * test afii10023
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10023() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10023", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1577));
    }

    /**
     * test afii10024
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10024() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10024", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -18, 2069, 1292));
    }

    /**
     * test afii10025
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10025() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10025", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -27, 1026, 1311));
    }

    /**
     * test afii10026
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10026() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10026", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -12, 1505, 1284));
    }

    /**
     * test afii10027
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10027() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10027", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -12, 1505, 1656));
    }

    /**
     * test afii10028
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10028() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10028", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1348, 1292));
    }

    /**
     * test afii10029
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10029() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10029", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test afii10030
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10030() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10030", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, -9, 1692, 1289));
    }

    /**
     * test afii10031
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10031() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10031", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test afii10032
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10032() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10032", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1292));
    }

    /**
     * test afii10033
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10033() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10033", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1486, 1284));
    }

    /**
     * test afii10034
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10034() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10034", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1295));
    }

    /**
     * test afii10035
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10035() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10035", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1311));
    }

    /**
     * test afii10036
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10036() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10036", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test afii10037
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10037() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10037", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, -27, 1366, 1290));
    }

    /**
     * test afii10038
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10038() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10038", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -8, 1411, 1284));
    }

    /**
     * test afii10039
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10039() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10039", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -21, 1449, 1277));
    }

    /**
     * test afii10040
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10040() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10040", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -292, 1532, 1284));
    }

    /**
     * test afii10041
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10041() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10041", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -6, 1302, 1284));
    }

    /**
     * test afii10042
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10042() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10042", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 2095, 1284));
    }

    /**
     * test afii10043
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10043() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10043", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -292, 2103, 1284));
    }

    /**
     * test afii10044
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10044() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10044", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -2, 1475, 1275));
    }

    /**
     * test afii10045
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10045() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10045", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -6, 1807, 1284));
    }

    /**
     * test afii10046
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10046() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10046", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1180, 1284));
    }

    /**
     * test afii10047
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10047() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10047", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(70, -26, 1213, 1311));
    }

    /**
     * test afii10048
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10048() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10048", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 2177, 1292));
    }

    /**
     * test afii10049
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10049() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10049", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-33, -5, 1238, 1289));
    }

    /**
     * test afii10050
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10050() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10050", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -6, 1005, 1555));
    }

    /**
     * test afii10051
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10051() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10051", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -293, 1463, 1331));
    }

    /**
     * test afii10052
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10052() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10052", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -6, 1105, 1720));
    }

    /**
     * test afii10053
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10053() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10053", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -26, 1231, 1311));
    }

    /**
     * test afii10054
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10054() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10054", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1316));
    }

    /**
     * test afii10055
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10055() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10055", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1279));
    }

    /**
     * test afii10056
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10056() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10056", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -4, 664, 1573));
    }

    /**
     * test afii10057
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10057() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10057", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-173, -518, 569, 1279));
    }

    /**
     * test afii10058
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10058() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10058", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, -1, 1868, 1343));
    }

    /**
     * test afii10059
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10059() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10059", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1928, 1289));
    }

    /**
     * test afii10060
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10060() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10060", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1513, 1331));
    }

    /**
     * test afii10061
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10061() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10061", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1348, 1720));
    }

    /**
     * test afii10062
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10062() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10062", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, -27, 1366, 1656));
    }

    /**
     * test afii10065
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10065() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10065", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 817));
    }

    /**
     * test afii10066
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10066() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10066", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, -27, 976, 1339));
    }

    /**
     * test afii10067
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10067() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10067", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -1, 804, 820));
    }

    /**
     * test afii10068
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10068() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10068", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 732, 796));
    }

    /**
     * test afii10069
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10069() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10069", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, -213, 941, 847));
    }

    /**
     * test afii10070
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10070() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10070", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 822));
    }

    /**
     * test afii10071
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10071() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10071", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1229));
    }

    /**
     * test afii10072
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10072() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10072", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -3, 1287, 820));
    }

    /**
     * test afii10073
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10073() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10073", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -18, 695, 820));
    }

    /**
     * test afii10074
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10074() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10074", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 796));
    }

    /**
     * test afii10075
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10075() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10075", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 1256));
    }

    /**
     * test afii10076
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10076() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10076", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -3, 889, 820));
    }

    /**
     * test afii10077
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10077() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10077", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, 0, 1002, 847));
    }

    /**
     * test afii10078
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10078() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10078", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, -21, 1187, 795));
    }

    /**
     * test afii10079
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10079() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10079", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 796));
    }

    /**
     * test afii10080
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10080() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10080", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 820));
    }

    /**
     * test afii10081
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10081() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10081", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 800));
    }

    /**
     * test afii10082
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10082() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10082", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -525, 971, 890));
    }

    /**
     * test afii10083
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10083() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10083", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 816));
    }

    /**
     * test afii10084
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10084() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10084", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -17, 834, 851));
    }

    /**
     * test afii10085
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10085() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10085", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 792));
    }

    /**
     * test afii10086
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10086() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10086", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -525, 1493, 1329));
    }

    /**
     * test afii10087
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10087() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10087", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(28, -1, 911, 789));
    }

    /**
     * test afii10088
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10088() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10088", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -213, 1093, 795));
    }

    /**
     * test afii10089
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10089() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10089", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -5, 1026, 796));
    }

    /**
     * test afii10090
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10090() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10090", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1607, 796));
    }

    /**
     * test afii10091
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10091() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10091", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -213, 1607, 796));
    }

    /**
     * test afii10092
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10092() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10092", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-7, -4, 1003, 793));
    }

    /**
     * test afii10093
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10093() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10093", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1259, 796));
    }

    /**
     * test afii10094
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10094() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10094", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -1, 821, 796));
    }

    /**
     * test afii10095
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10095() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10095", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(63, -18, 763, 819));
    }

    /**
     * test afii10096
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10096() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10096", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -27, 1521, 820));
    }

    /**
     * test afii10097
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10097() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10097", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -5, 825, 820));
    }

    /**
     * test afii10098
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10098() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10098", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(16, -5, 712, 933));
    }

    /**
     * test afii10099
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10099() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10099", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -282, 947, 1332));
    }

    /**
     * test afii10100
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10100() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10100", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 732, 1292));
    }

    /**
     * test afii10101
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10101() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10101", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 820));
    }

    /**
     * test afii10102
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10102() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10102", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 828));
    }

    /**
     * test afii10103
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10103() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10103", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -5, 454, 1310));
    }

    /**
     * test afii10104
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10104() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10104", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 542, 1229));
    }

    /**
     * test afii10105
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10105() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10105", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -539, 315, 1299));
    }

    /**
     * test afii10106
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10106() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10106", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, 0, 1331, 847));
    }

    /**
     * test afii10107
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10107() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10107", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1387, 796));
    }

    /**
     * test afii10108
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10108() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10108", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -7, 1018, 1332));
    }

    /**
     * test afii10109
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10109() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10109", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -3, 889, 1292));
    }

    /**
     * test afii10110
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10110() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10110", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1256));
    }

    /**
     * test afii10145
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10145() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10145", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -236, 1486, 1284));
    }

    /**
     * test afii10193
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10193() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii10193", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -213, 1093, 796));
    }

    /**
     * test afii61248
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii61248() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii61248", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(119, -67, 1506, 1305));
    }

    /**
     * test afii61289
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii61289() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii61289", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(100, -26, 893, 1384));
    }

    /**
     * test afii61352
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii61352() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("afii61352", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -47, 2028, 1292));
    }

    /**
     * test agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfagrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("agrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1293));
    }

    /**
     * test alpha
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfalpha() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("alpha", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -22, 935, 820));
    }

    /**
     * test alphatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfalphatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("alphatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -22, 935, 1315));
    }

    /**
     * test amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfamacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("amacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1185));
    }

    /**
     * test ampersand
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfampersand() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ampersand", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -30, 1461, 1217));
    }

    /**
     * test anoteleia
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfanoteleia() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("anoteleia", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(117, 568, 331, 794));
    }

    /**
     * test aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfaogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("aogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -328, 967, 817));
    }

    /**
     * test approxequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfapproxequal() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("approxequal", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 245, 1219, 1030));
    }

    /**
     * test aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfaring() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("aring", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1259));
    }

    /**
     * test aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfaringacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("aringacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1668));
    }

    /**
     * test arrowboth
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowboth() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowboth", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, 158, 1968, 878));
    }

    /**
     * test arrowdown
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowdown() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowdown", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(153, -429, 872, 1339));
    }

    /**
     * test arrowleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowleft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, 157, 1968, 876));
    }

    /**
     * test arrowright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, 157, 1968, 876));
    }

    /**
     * test arrowup
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowup() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowup", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(153, -429, 872, 1339));
    }

    /**
     * test arrowupdn
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowupdn() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowupdn", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(152, -427, 871, 1463));
    }

    /**
     * test arrowupdnbse
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowupdnbse() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("arrowupdnbse", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(152, -620, 871, 1463));
    }

    /**
     * test asciicircum
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfasciicircum() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("asciicircum", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, 783, 961, 1374));
    }

    /**
     * test asciitilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfasciitilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("asciitilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(150, 499, 1215, 775));
    }

    /**
     * test asterisk
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfasterisk() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("asterisk", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(59, 492, 805, 1294));
    }

    /**
     * test at
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfat() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("at", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(97, -442, 1836, 1422));
    }

    /**
     * test atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfatilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("atilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1237));
    }

    /**
     * test b
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfb() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("b", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(34, -43, 965, 1349));
    }

    /**
     * test backslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbackslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("backslash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -277, 911, 1426));
    }

    /**
     * test bar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("bar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(468, -528, 557, 1339));
    }

    /**
     * test beta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbeta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("beta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, -505, 908, 1319));
    }

    /**
     * test block
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfblock() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("block", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test braceleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbraceleft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("braceleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(283, -442, 841, 1422));
    }

    /**
     * test braceright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbraceright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("braceright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(177, -442, 735, 1422));
    }

    /**
     * test bracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbracketleft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("bracketleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(208, -475, 605, 1285));
    }

    /**
     * test bracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbracketright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("bracketright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-43, -477, 357, 1285));
    }

    /**
     * test breve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("breve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, 951, 625, 1233));
    }

    /**
     * test brokenbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbrokenbar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("brokenbar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(468, -528, 557, 1339));
    }

    /**
     * test bullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfbullet() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("bullet", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(112, 426, 614, 928));
    }

    /**
     * test c
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfc() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("c", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 816));
    }

    /**
     * test cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("cacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1292));
    }

    /**
     * test caron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("caron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(148, 977, 587, 1332));
    }

    /**
     * test ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfccaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ccaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1332));
    }

    /**
     * test ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfccedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ccedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -431, 800, 816));
    }

    /**
     * test ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfccircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ccircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1332));
    }

    /**
     * test cdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("cdot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1229));
    }

    /**
     * test cedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("cedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(1, -431, 300, 14));
    }

    /**
     * test cent
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcent() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("cent", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, -346, 798, 1188));
    }

    /**
     * test chi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfchi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("chi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(48, -506, 952, 820));
    }

    /**
     * test circle
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcircle() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("circle", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(178, 137, 1059, 1018));
    }

    /**
     * test circumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("circumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 977, 586, 1332));
    }

    /**
     * test club
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfclub() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("club", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, 0, 1288, 1231));
    }

    /**
     * test colon
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcolon() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("colon", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(117, -27, 331, 794));
    }

    /**
     * test comma
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcomma() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("comma", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(84, -356, 388, 141));
    }

    /**
     * test commaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcommaaccent() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("commaaccent", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(192, 851, 486, 1348));
    }

    /**
     * test copyright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcopyright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("copyright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(69, -31, 1488, 1387));
    }

    /**
     * test currency
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfcurrency() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("currency", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(202, 183, 1157, 1137));
    }

    /**
     * test d
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfd() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("d", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -37, 999, 1348));
    }

    /**
     * test dagger
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdagger() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dagger", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -499, 865, 1312));
    }

    /**
     * test daggerdbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdaggerdbl() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("daggerdbl", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(31, -493, 843, 1317));
    }

    /**
     * test dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -37, 1266, 1348));
    }

    /**
     * test degree
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdegree() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("degree", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(98, 771, 713, 1386));
    }

    /**
     * test delta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdelta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("delta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(86, -22, 896, 1316));
    }

    /**
     * test diamond
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdiamond() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("diamond", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(213, -24, 1131, 1234));
    }

    /**
     * test dieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, 1056, 649, 1229));
    }

    /**
     * test dieresistonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdieresistonos() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("dieresistonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(41, 937, 643, 1315));
    }

    /**
     * test divide
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdivide() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("divide", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(23, 279, 1101, 1075));
    }

    /**
     * test dkshade
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdkshade() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dkshade", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -628, 1447, 1864));
    }

    /**
     * test dmacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdmacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dmacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -37, 1038, 1348));
    }

    /**
     * test dnblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdnblock() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dnblock", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 621));
    }

    /**
     * test dollar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdollar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dollar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -273, 829, 1342));
    }

    /**
     * test dotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdotaccent() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dotaccent", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(291, 1056, 463, 1229));
    }

    /**
     * test dotlessi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfdotlessi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("dotlessi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 450, 890));
    }

    /**
     * test e
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfe() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("e", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 822));
    }

    /**
     * test eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("eacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1292));
    }

    /**
     * test ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfebreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ebreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1233));
    }

    /**
     * test ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfecaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ecaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1332));
    }

    /**
     * test ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfecircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ecircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1332));
    }

    /**
     * test edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfedieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("edieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1229));
    }

    /**
     * test edot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfedot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("edot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1229));
    }

    /**
     * test egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfegrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("egrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1293));
    }

    /**
     * test eight
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeight() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("eight", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -28, 879, 1297));
    }

    /**
     * test eightsuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeightsuperior() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("eightsuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(84, 614, 559, 1304));
    }

    /**
     * test ellipsis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfellipsis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ellipsis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(234, -19, 1814, 197));
    }

    /**
     * test emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfemacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("emacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1185));
    }

    /**
     * test emdash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfemdash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("emdash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, 345, 2060, 438));
    }

    /**
     * test endash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfendash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("endash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, 345, 1036, 438));
    }

    /**
     * test eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeng() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("eng", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -498, 869, 843));
    }

    /**
     * test eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("eogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -328, 803, 822));
    }

    /**
     * test epsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfepsilon() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("epsilon", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -22, 716, 820));
    }

    /**
     * test epsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfepsilontonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("epsilontonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -22, 716, 1315));
    }

    /**
     * test equal
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfequal() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("equal", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 361, 1219, 913));
    }

    /**
     * test equivalence
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfequivalence() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("equivalence", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(203, 131, 1275, 1147));
    }

    /**
     * test estimated
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfestimated() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("estimated", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(90, -34, 1148, 1096));
    }

    /**
     * test eta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("eta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(50, -505, 894, 821));
    }

    /**
     * test etatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfetatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("etatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(50, -505, 894, 1315));
    }

    /**
     * test eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfeth() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("eth", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(92, -24, 995, 1315));
    }

    /**
     * test exclam
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfexclam() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("exclam", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(125, -25, 329, 1307));
    }

    /**
     * test exclamdbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfexclamdbl() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("exclamdbl", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(129, -25, 689, 1307));
    }

    /**
     * test exclamdown
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfexclamdown() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("exclamdown", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(121, -492, 326, 836));
    }

    /**
     * test f
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyff() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("f", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(96, -1, 825, 1339));
    }

    /**
     * test female
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffemale() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("female", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(244, -439, 1291, 1507));
    }

    /**
     * test fi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -1, 1066, 1343));
    }

    /**
     * test fi1
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffi1() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fi1", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -1, 1066, 1343));
    }

    /**
     * test filledbox
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffilledbox() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("filledbox", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 0, 1090, 944));
    }

    /**
     * test filledrect
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffilledrect() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("filledrect", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, 317, 2047, 703));
    }

    /**
     * test five
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffive() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("five", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(105, -33, 857, 1307));
    }

    /**
     * test fiveeighths
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffiveeighths() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fiveeighths", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -67, 1607, 1305));
    }

    /**
     * test fivesuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffivesuperior() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fivesuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, 608, 541, 1305));
    }

    /**
     * test fl
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffl() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fl", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -4, 1052, 1336));
    }

    /**
     * test fl1
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffl1() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fl1", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -4, 1052, 1336));
    }

    /**
     * test florin
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfflorin() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("florin", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-2, -525, 1261, 1315));
    }

    /**
     * test four
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffour() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("four", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -24, 935, 1304));
    }

    /**
     * test foursuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffoursuperior() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("foursuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(57, 612, 600, 1304));
    }

    /**
     * test fraction
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffraction() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fraction", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-284, -67, 645, 1305));
    }

    /**
     * test fraction1
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffraction1() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("fraction1", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-284, -67, 645, 1305));
    }

    /**
     * test franc
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyffranc() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("franc", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -20, 1341, 1294));
    }

    /**
     * test g
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfg() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("g", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 820));
    }

    /**
     * test gamma
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgamma() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("gamma", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, -505, 791, 820));
    }

    /**
     * test gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgbreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("gbreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1233));
    }

    /**
     * test gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("gcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1407));
    }

    /**
     * test gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("gcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1332));
    }

    /**
     * test gdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("gdot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1229));
    }

    /**
     * test germandbls
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgermandbls() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("germandbls", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(15, -32, 962, 1318));
    }

    /**
     * test grave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("grave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(199, 983, 535, 1293));
    }

    /**
     * test greater
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgreater() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("greater", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 144, 1218, 1130));
    }

    /**
     * test greaterequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfgreaterequal() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("greaterequal", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, -41, 1220, 1356));
    }

    /**
     * test guillemotleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfguillemotleft() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("guillemotleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, 11, 749, 800));
    }

    /**
     * test guillemotright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfguillemotright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("guillemotright", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(1, 11, 739, 799));
    }

    /**
     * test guilsinglleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfguilsinglleft() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("guilsinglleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, 13, 390, 806));
    }

    /**
     * test guilsinglright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfguilsinglright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("guilsinglright", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(17, 16, 391, 811));
    }

    /**
     * test h
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfh() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("h", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(30, -7, 1018, 1332));
    }

    /**
     * test hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfhbar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("hbar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-11, -7, 1018, 1332));
    }

    /**
     * test hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfhcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("hcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(30, -7, 1018, 1792));
    }

    /**
     * test heart
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfheart() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("heart", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(127, -24, 1217, 1231));
    }

    /**
     * test house
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfhouse() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("house", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(157, 0, 1080, 1153));
    }

    /**
     * test hungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfhungarumlaut() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("hungarumlaut", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(172, 983, 764, 1292));
    }

    /**
     * test hyphen
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfhyphen() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("hyphen", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, 352, 564, 446));
    }

    /**
     * test i
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("i", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -5, 454, 1310));
    }

    /**
     * test iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfiacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("iacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 475, 1292));
    }

    /**
     * test ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfibreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ibreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 506, 1269));
    }

    /**
     * test icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyficircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("icircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 459, 1332));
    }

    /**
     * test idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfidieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("idieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -5, 512, 1229));
    }

    /**
     * test igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfigrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("igrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 450, 1293));
    }

    /**
     * test ij
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfij() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ij", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -525, 831, 1313));
    }

    /**
     * test imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfimacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("imacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-21, -5, 516, 1185));
    }

    /**
     * test infinity
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfinfinity() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("infinity", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, 219, 1402, 1055));
    }

    /**
     * test integralbt
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfintegralbt() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("integralbt", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(261, -515, 691, 1737));
    }

    /**
     * test integraltp
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfintegraltp() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("integraltp", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(546, -515, 976, 1737));
    }

    /**
     * test intersection
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfintersection() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("intersection", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(192, 0, 1281, 1297));
    }

    /**
     * test invbullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfinvbullet() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("invbullet", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(128, 0, 1108, 980));
    }

    /**
     * test invcircle
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfinvcircle() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("invcircle", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, 0, 1197, 1155));
    }

    /**
     * test invsmileface
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfinvsmileface() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("invsmileface", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(386, -119, 1662, 1156));
    }

    /**
     * test iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfiogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("iogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -328, 489, 1310));
    }

    /**
     * test iota
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfiota() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("iota", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -22, 409, 802));
    }

    /**
     * test iotadieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfiotadieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("iotadieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-24, -22, 492, 1229));
    }

    /**
     * test iotadieresistonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfiotadieresistonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("iotadieresistonos", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-101, -22, 501, 1315));
    }

    /**
     * test iotatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfiotatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("iotatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -22, 409, 1315));
    }

    /**
     * test itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfitilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("itilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-29, -5, 544, 1237));
    }

    /**
     * test j
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfj() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("j", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -539, 315, 1299));
    }

    /**
     * test jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfjcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("jcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -539, 466, 1332));
    }

    /**
     * test k
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfk() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("k", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -2, 977, 1340));
    }

    /**
     * test kappa
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfkappa() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("kappa", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -22, 899, 820));
    }

    /**
     * test kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfkcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("kcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -435, 977, 1340));
    }

    /**
     * test kgreenlandic
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfkgreenlandic() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("kgreenlandic", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -13, 926, 781));
    }

    /**
     * test l
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfl() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("l", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 465, 1329));
    }

    /**
     * test lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 465, 1662));
    }

    /**
     * test lambda
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflambda() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lambda", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -22, 863, 1297));
    }

    /**
     * test lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 712, 1329));
    }

    /**
     * test lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -435, 465, 1329));
    }

    /**
     * test ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfldot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ldot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 615, 1329));
    }

    /**
     * test less
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfless() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("less", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 144, 1218, 1130));
    }

    /**
     * test lessequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflessequal() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lessequal", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, -41, 1220, 1356));
    }

    /**
     * test lfblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflfblock() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lfblock", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 725, 1864));
    }

    /**
     * test logicalnot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflogicalnot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("logicalnot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 369, 1220, 945));
    }

    /**
     * test longs
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflongs() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("longs", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, -1, 806, 1339));
    }

    /**
     * test lozenge
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflozenge() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lozenge", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(47, 0, 967, 1422));
    }

    /**
     * test lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyflslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("lslash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, 6, 563, 1338));
    }

    /**
     * test ltshade
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfltshade() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ltshade", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -504, 1327, 1864));
    }

    /**
     * test m
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfm() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("m", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(36, -2, 1544, 856));
    }

    /**
     * test male
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfmale() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("male", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(111, -262, 1415, 1620));
    }

    /**
     * test minus
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfminus() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("minus", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 593, 1220, 681));
    }

    /**
     * test minute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfminute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("minute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, 803, 250, 1387));
    }

    /**
     * test mu
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfmu() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("mu", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(140, -505, 1032, 820));
    }

    /**
     * test mu1
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfmu1() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("mu1", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(47, -443, 1018, 785));
    }

    /**
     * test multiply
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfmultiply() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("multiply", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(197, 151, 1171, 1123));
    }

    /**
     * test musicalnote
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfmusicalnote() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("musicalnote", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -37, 987, 1363));
    }

    /**
     * test musicalnotedbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfmusicalnotedbl() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("musicalnotedbl", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -128, 1330, 1519));
    }

    /**
     * test n
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfn() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("n", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 843));
    }

    /**
     * test nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("nacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 1292));
    }

    /**
     * test napostrophe
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnapostrophe() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("napostrophe", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(96, 1, 1352, 1304));
    }

    /**
     * test ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfncaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ncaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 1332));
    }

    /**
     * test ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfncedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ncedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -435, 1024, 843));
    }

    /**
     * test nine
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnine() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("nine", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(90, -30, 864, 1307));
    }

    /**
     * test nonbreakingspace
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnonbreakingspace() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("nbspace", 0, (short) 3, (short) 1);
        assertNull(bb);
    }

    /**
     * test notequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnotequal() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("notequal", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, -170, 1219, 1446));
    }

    /**
     * test nsuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnsuperior() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("nsuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, 519, 678, 1038));
    }

    /**
     * test ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfntilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ntilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 1237));
    }

    /**
     * test nu
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnu() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("nu", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(36, -22, 700, 820));
    }

    /**
     * test numbersign
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfnumbersign() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("numbersign", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -47, 1271, 1365));
    }

    /**
     * test o
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfo() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("o", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 820));
    }

    /**
     * test oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfoacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("oacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1292));
    }

    /**
     * test obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfobreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("obreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1233));
    }

    /**
     * test ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfocircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ocircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1332));
    }

    /**
     * test odblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfodblacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("odblacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1292));
    }

    /**
     * test odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfodieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("odieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1229));
    }

    /**
     * test oe
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfoe() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("oe", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -33, 1364, 820));
    }

    /**
     * test ogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(185, -328, 603, 1));
    }

    /**
     * test ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfograve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ograve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1293));
    }

    /**
     * test omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfomacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("omacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1185));
    }

    /**
     * test omega
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfomega() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("omega", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, -22, 1095, 810));
    }

    /**
     * test omegatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfomegatonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("omegatonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, -22, 1095, 1315));
    }

    /**
     * test omicron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfomicron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("omicron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -22, 936, 820));
    }

    /**
     * test omicrontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfomicrontonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("omicrontonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -22, 936, 1315));
    }

    /**
     * test one
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfone() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("one", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(154, 2, 727, 1298));
    }

    /**
     * test oneeighth
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfoneeighth() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("oneeighth", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(112, -67, 1586, 1305));
    }

    /**
     * test onehalf
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfonehalf() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("onehalf", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -67, 1590, 1305));
    }

    /**
     * test onequarter
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfonequarter() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("onequarter", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -71, 1608, 1301));
    }

    /**
     * test onesuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfonesuperior() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("onesuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, 626, 475, 1301));
    }

    /**
     * test openbullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfopenbullet() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("openbullet", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, 390, 649, 962));
    }

    /**
     * test ordfeminine
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfordfeminine() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ordfeminine", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, 774, 541, 1292));
    }

    /**
     * test ordmasculine
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfordmasculine() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ordmasculine", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, 771, 645, 1292));
    }

    /**
     * test orthogonal
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyforthogonal() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("orthogonal", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(362, 0, 1643, 1279));
    }

    /**
     * test oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfoslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("oslash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -48, 976, 845));
    }

    /**
     * test oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfoslashacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("oslashacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -48, 976, 1292));
    }

    /**
     * test otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfotilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("otilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1237));
    }

    /**
     * test p
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfp() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("p", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -525, 971, 890));
    }

    /**
     * test paragraph
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfparagraph() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("paragraph", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-14, -442, 931, 1356));
    }

    /**
     * test parenleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfparenleft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("parenleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(157, -502, 634, 1309));
    }

    /**
     * test parenright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfparenright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("parenright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-44, -500, 438, 1311));
    }

    /**
     * test partialdiff
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfpartialdiff() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("partialdiff", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -31, 991, 1422));
    }

    /**
     * test percent
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfpercent() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("percent", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(74, -67, 1617, 1305));
    }

    /**
     * test period
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfperiod() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("period", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(120, -29, 328, 191));
    }

    /**
     * test perthousand
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfperthousand() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("perthousand", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -67, 2023, 1305));
    }

    /**
     * test peseta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfpeseta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("peseta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -32, 1982, 1295));
    }

    /**
     * test phi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfphi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("phi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -505, 935, 820));
    }

    /**
     * test pi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfpi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("pi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(44, -28, 1006, 916));
    }

    /**
     * test pi1
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfpi1() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("pi1", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -22, 914, 805));
    }

    /**
     * test plus
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfplus() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("plus", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(145, 101, 1220, 1173));
    }

    /**
     * test plusminus
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfplusminus() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("plusminus", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(145, -37, 1220, 1353));
    }

    /**
     * test product
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfproduct() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("product", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(46, -528, 1640, 1339));
    }

    /**
     * test psi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfpsi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("psi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -505, 953, 1315));
    }

    /**
     * test q
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfq() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("q", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(70, -524, 1021, 845));
    }

    /**
     * test question
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquestion() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("question", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -30, 677, 1311));
    }

    /**
     * test questiondown
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquestiondown() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("questiondown", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -502, 619, 836));
    }

    /**
     * test quotedbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedbl() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quotedbl", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, 803, 700, 1387));
    }

    /**
     * test quotedblbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedblbase() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quotedblbase", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(65, -354, 832, 147));
    }

    /**
     * test quotedblleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedblleft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quotedblleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, 803, 858, 1301));
    }

    /**
     * test quotedblright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedblright() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("quotedblright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, 811, 845, 1317));
    }

    /**
     * test quoteleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquoteleft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quoteleft", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(105, 806, 408, 1306));
    }

    /**
     * test quotereversed
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotereversed() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("quotereversed", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(102, 806, 396, 1303));
    }

    /**
     * test quoteright
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquoteright() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quoteright", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(102, 806, 396, 1303));
    }

    /**
     * test quotesinglbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotesinglbase() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quotesinglbase", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -356, 387, 141));
    }

    /**
     * test quotesingle
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotesingle() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("quotesingle", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(81, 803, 282, 1387));
    }

    /**
     * test r
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfr() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("r", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -3, 680, 865));
    }

    /**
     * test racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfracute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("racute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -3, 680, 1292));
    }

    /**
     * test radical
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfradical() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("radical", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, -78, 1127, 1869));
    }

    /**
     * test radicalex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfradicalex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("radicalex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-24, 1795, 926, 1869));
    }

    /**
     * test rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfrcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("rcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -3, 680, 1332));
    }

    /**
     * test rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfrcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("rcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -435, 680, 865));
    }

    /**
     * test registered
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfregistered() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("registered", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(69, -31, 1488, 1387));
    }

    /**
     * test revlogicalnot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfrevlogicalnot() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("revlogicalnot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 369, 1220, 945));
    }

    /**
     * test rho
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfrho() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("rho", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -505, 936, 820));
    }

    /**
     * test ring
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfring() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ring", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(215, 930, 543, 1259));
    }

    /**
     * test rtblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfrtblock() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("rtblock", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(726, -621, 1474, 1864));
    }

    /**
     * test s
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfs() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("s", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 828));
    }

    /**
     * test sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("sacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 1292));
    }

    /**
     * test scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfscaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("scaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 1332));
    }

    /**
     * test scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfscedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("scedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -431, 659, 828));
    }

    /**
     * test scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfscircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("scircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 1332));
    }

    /**
     * test second
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsecond() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("second", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(101, 803, 668, 1387));
    }

    /**
     * test section
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsection() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("section", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -498, 757, 1314));
    }

    /**
     * test semicolon
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsemicolon() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("semicolon", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, -321, 387, 802));
    }

    /**
     * test seven
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfseven() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("seven", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -26, 883, 1269));
    }

    /**
     * test seveneighths
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfseveneighths() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("seveneighths", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, -67, 1607, 1305));
    }

    /**
     * test sevensuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsevensuperior() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("sevensuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, 612, 576, 1286));
    }

    /**
     * test sfthyphen
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsfthyphen() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("sfthyphen", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, 352, 564, 446));
    }

    /**
     * test shade
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfshade() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("shade", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -504, 1447, 1864));
    }

    /**
     * test sigma
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsigma() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("sigma", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -20, 954, 788));
    }

    /**
     * test sigma1
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsigma1() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("sigma1", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -300, 719, 820));
    }

    /**
     * test six
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsix() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("six", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(100, -28, 875, 1310));
    }

    /**
     * test slash
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfslash() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("slash", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -277, 908, 1426));
    }

    /**
     * test smileface
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsmileface() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("smileface", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(386, -119, 1662, 1156));
    }

    /**
     * test space
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfspace() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("space", 0, (short) 3, (short) 1);
        assertNull(bb);
    }

    /**
     * test spade
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfspade() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("spade", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(186, 0, 1158, 1231));
    }

    /**
     * test sterling
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsterling() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("sterling", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -482, 1211, 1297));
    }

    /**
     * test summation
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsummation() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("summation", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -528, 1420, 1339));
    }

    /**
     * test sun
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfsun() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("sun", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(16, -223, 1862, 1621));
    }

    /**
     * test t
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyft() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("t", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(56, -21, 605, 988));
    }

    /**
     * test tau
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftau() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("tau", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -22, 702, 805));
    }

    /**
     * test tbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftbar() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("tbar", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 606, 988));
    }

    /**
     * test tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("tcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(56, -21, 958, 1300));
    }

    /**
     * test tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftcedilla() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("tcedilla", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(56, -581, 605, 988));
    }

    /**
     * test theta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftheta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("theta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -22, 832, 1315));
    }

    /**
     * test thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfthorn() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("thorn", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -525, 971, 1329));
    }

    /**
     * test three
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfthree() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("three", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, -28, 869, 1304));
    }

    /**
     * test threeeighths
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfthreeeighths() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("threeeighths", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -67, 1607, 1305));
    }

    /**
     * test threequarters
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfthreequarters() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("threequarters", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -67, 1620, 1305));
    }

    /**
     * test threesuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfthreesuperior() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("threesuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, 610, 562, 1304));
    }

    /**
     * test tilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("tilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(88, 1033, 661, 1237));
    }

    /**
     * test tonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("tonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(260, 937, 423, 1315));
    }

    /**
     * test trademark
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftrademark() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("trademark", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(30, 549, 1973, 1356));
    }

    /**
     * test triagdn
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftriagdn() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("triagdn", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(304, -31, 1724, 1388));
    }

    /**
     * test triaglf
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftriaglf() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("triaglf", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(288, -31, 1739, 1417));
    }

    /**
     * test triagrt
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftriagrt() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("triagrt", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(288, -31, 1739, 1417));
    }

    /**
     * test triagup
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftriagup() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("triagup", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(304, 0, 1724, 1419));
    }

    /**
     * test two
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftwo() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("two", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(45, 2, 904, 1298));
    }

    /**
     * test twosuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyftwosuperior() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("twosuperior", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(51, 626, 582, 1301));
    }

    /**
     * test u
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfu() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("u", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 785));
    }

    /**
     * test uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfuacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("uacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1292));
    }

    /**
     * test ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfubreve() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ubreve", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1233));
    }

    /**
     * test ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfucircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ucircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1332));
    }

    /**
     * test udblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfudblacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("udblacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1292));
    }

    /**
     * test udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfudieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("udieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1229));
    }

    /**
     * test ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfugrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ugrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1293));
    }

    /**
     * test umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfumacron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("umacron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1185));
    }

    /**
     * test undercommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfundercommaaccent() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("undercommaaccent", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(274, -435, 518, -87));
    }

    /**
     * test underscore
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfunderscore() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("underscore", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, -256, 1036, -154));
    }

    /**
     * test underscoredbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfunderscoredbl() throws Exception {

        XtfBoundingBox bb =
                reader
                    .mapCharCodeToBB("underscoredbl", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, -509, 1036, -154));
    }

    /**
     * test uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfuogonek() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("uogonek", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -328, 1071, 785));
    }

    /**
     * test upblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfupblock() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("upblock", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 621, 1473, 1864));
    }

    /**
     * test upsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilon() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("upsilon", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 820));
    }

    /**
     * test upsilondieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilondieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("upsilondieresis", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 1229));
    }

    /**
     * test upsilondieresistonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilondieresistonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("upsilondieresistonos", 0, (short) 3,
                    (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 1315));
    }

    /**
     * test upsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilontonos() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("upsilontonos", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 1315));
    }

    /**
     * test uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfuring() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("uring", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1259));
    }

    /**
     * test utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfutilde() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("utilde", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1237));
    }

    /**
     * test v
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfv() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("v", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, -42, 978, 794));
    }

    /**
     * test w
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfw() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("w", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 789));
    }

    /**
     * test wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfwacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("wacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1292));
    }

    /**
     * test wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfwcircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("wcircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1332));
    }

    /**
     * test wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfwdieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("wdieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1229));
    }

    /**
     * test wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfwgrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("wgrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1293));
    }

    /**
     * test x
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfx() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("x", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(28, -1, 911, 789));
    }

    /**
     * test xi
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfxi() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("xi", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -300, 762, 1315));
    }

    /**
     * test y
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfy() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("y", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 792));
    }

    /**
     * test yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfyacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("yacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1292));
    }

    /**
     * test ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfycircumflex() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ycircumflex", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1332));
    }

    /**
     * test ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfydieresis() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ydieresis", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1229));
    }

    /**
     * test yen
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfyen() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("yen", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1290));
    }

    /**
     * test ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfygrave() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("ygrave", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1293));
    }

    /**
     * test z
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfz() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("z", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 866));
    }

    /**
     * test zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfzacute() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("zacute", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 1292));
    }

    /**
     * test zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfzcaron() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("zcaron", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 1332));
    }

    /**
     * test zdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfzdot() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("zdot", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 1229));
    }

    /**
     * test zero
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfzero() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("zero", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -30, 896, 1304));
    }

    /**
     * test zeta
     * 
     * @throws Exception if an error occurred.
     */
    public void testGlyfzeta() throws Exception {

        XtfBoundingBox bb =
                reader.mapCharCodeToBB("zeta", 0, (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -300, 762, 1315));
    }

}