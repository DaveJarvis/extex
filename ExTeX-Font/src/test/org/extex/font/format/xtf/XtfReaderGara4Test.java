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
            reader = new XtfReader("../ExTeX-Font/src/font/Gara.ttf");
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

        XtfBoundingBox bb = reader.mapCharCodeToBB("A", (short) 3, (short) 1);
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

        XtfBoundingBox bb = reader.mapCharCodeToBB("A", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test AE
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAE() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("AE", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-127, -10, 1697, 1286));
    }

    /**
     * test AEacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAEacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("AEacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-127, -10, 1697, 1720));
    }

    /**
     * test Aacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Aacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1714));
    }

    /**
     * test Abreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Abreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1697));
    }

    /**
     * test Acircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Acircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1761));
    }

    /**
     * test Adieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Adieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1579));
    }

    /**
     * test Agrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Agrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1715));
    }

    /**
     * test Alpha
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAlpha() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Alpha", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test Alphatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAlphatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Alphatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test Amacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAmacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Amacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1593));
    }

    /**
     * test Aogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Aogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-14, -328, 1475, 1343));
    }

    /**
     * test Aring
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAring() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Aring", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1654));
    }

    /**
     * test Aringacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAringacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Aringacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 2020));
    }

    /**
     * test Atilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfAtilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Atilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1608));
    }

    /**
     * test B
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfB() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("B", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1164, 1298));
    }

    /**
     * test Beta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfBeta() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Beta", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1164, 1298));
    }

    /**
     * test C
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfC() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("C", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1311));
    }

    /**
     * test Cacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfCacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Cacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1714));
    }

    /**
     * test Ccaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfCcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ccaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1761));
    }

    /**
     * test Ccedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfCcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ccedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -431, 1231, 1311));
    }

    /**
     * test Ccircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfCcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ccircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1760));
    }

    /**
     * test Cdot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfCdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Cdot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1573));
    }

    /**
     * test Chi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfChi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Chi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -21, 1449, 1277));
    }

    /**
     * test D
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfD() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("D", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(22, -17, 1479, 1301));
    }

    /**
     * test Dcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfDcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Dcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(22, -17, 1479, 1768));
    }

    /**
     * test Dslash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfDslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Dslash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(15, -17, 1480, 1300));
    }

    /**
     * test E
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfE() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("E", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1274));
    }

    /**
     * test Eacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Eacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1714));
    }

    /**
     * test Ebreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ebreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1697));
    }

    /**
     * test Ecaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ecaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1768));
    }

    /**
     * test Ecircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ecircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1761));
    }

    /**
     * test Edieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Edieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1579));
    }

    /**
     * test Edot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Edot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1573));
    }

    /**
     * test Egrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Egrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1715));
    }

    /**
     * test Emacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEmacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Emacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1593));
    }

    /**
     * test Eng
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEng() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Eng", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(100, -17, 1437, 1311));
    }

    /**
     * test Eogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Eogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -328, 1296, 1274));
    }

    /**
     * test Epsilon
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEpsilon() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Epsilon", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1274));
    }

    /**
     * test Epsilontonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEpsilontonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Epsilontonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -14, 1429, 1292));
    }

    /**
     * test Eta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEta() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Eta", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test Etatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEtatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Etatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -21, 1598, 1292));
    }

    /**
     * test Eth
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfEth() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Eth", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(15, -17, 1480, 1301));
    }

    /**
     * test F
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfF() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("F", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(59, -20, 1107, 1294));
    }

    /**
     * test G
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfG() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("G", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1312));
    }

    /**
     * test Gamma
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfGamma() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Gamma", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1274));
    }

    /**
     * test Gbreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfGbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Gbreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1688));
    }

    /**
     * test Gcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfGcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Gcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -621, 1553, 1312));
    }

    /**
     * test Gcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfGcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Gcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1760));
    }

    /**
     * test Gdot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfGdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Gdot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -25, 1553, 1573));
    }

    /**
     * test H
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfH() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("H", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test H18533
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfH18533() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("H18533", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(178, 137, 1059, 1018));
    }

    /**
     * test H18543
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfH18543() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("H18543", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(92, 405, 635, 948));
    }

    /**
     * test H18551
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfH18551() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("H18551", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(92, 405, 635, 948));
    }

    /**
     * test H22073
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfH22073() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("H22073", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 0, 1090, 944));
    }

    /**
     * test Hbar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfHbar() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Hbar", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test Hcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfHcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Hcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1764));
    }

    /**
     * test I
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfI() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("I", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1279));
    }

    /**
     * test IJ
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIJ() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("IJ", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -518, 1295, 1279));
    }

    /**
     * test Iacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Iacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1714));
    }

    /**
     * test Ibreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ibreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1697));
    }

    /**
     * test Icircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Icircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1761));
    }

    /**
     * test Idieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Idieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1579));
    }

    /**
     * test Igrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Igrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1715));
    }

    /**
     * test Imacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfImacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Imacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1589));
    }

    /**
     * test Iogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Iogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -364, 664, 1279));
    }

    /**
     * test Iota
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIota() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Iota", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1279));
    }

    /**
     * test Iotadieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIotadieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Iotadieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1577));
    }

    /**
     * test Iotatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfIotatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Iotatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, 0, 814, 1292));
    }

    /**
     * test Itilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfItilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Itilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1589));
    }

    /**
     * test J
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfJ() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("J", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-173, -518, 569, 1279));
    }

    /**
     * test Jcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfJcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Jcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-173, -518, 569, 1764));
    }

    /**
     * test K
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfK() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("K", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1556, 1282));
    }

    /**
     * test Kappa
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfKappa() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Kappa", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1556, 1282));
    }

    /**
     * test Kcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfKcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Kcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -435, 1556, 1282));
    }

    /**
     * test L
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfL() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("L", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1274));
    }

    /**
     * test Lacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfLacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Lacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1716));
    }

    /**
     * test Lambda
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfLambda() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Lambda", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test Lcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfLcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Lcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1303));
    }

    /**
     * test Lcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfLcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Lcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -435, 1177, 1274));
    }

    /**
     * test Ldot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfLdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Ldot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(11, -6, 1177, 1274));
    }

    /**
     * test Lslash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfLslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Lslash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -6, 1184, 1274));
    }

    /**
     * test M
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfM() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("M", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, -9, 1692, 1289));
    }

    /**
     * test Mu
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfMu() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Mu", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, -9, 1692, 1289));
    }

    /**
     * test N
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfN() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("N", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1286));
    }

    /**
     * test Nacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfNacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Nacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1724));
    }

    /**
     * test Ncaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfNcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ncaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1768));
    }

    /**
     * test Ncedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfNcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ncedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -435, 1500, 1286));
    }

    /**
     * test Ntilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfNtilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ntilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1608));
    }

    /**
     * test Nu
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfNu() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Nu", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(26, -47, 1500, 1286));
    }

    /**
     * test O
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfO() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("O", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1292));
    }

    /**
     * test OE
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOE() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("OE", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(95, -17, 1863, 1289));
    }

    /**
     * test Oacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Oacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1714));
    }

    /**
     * test Obreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfObreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Obreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1697));
    }

    /**
     * test Ocircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ocircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1761));
    }

    /**
     * test Odblacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOdblacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Odblacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1720));
    }

    /**
     * test Odieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Odieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1579));
    }

    /**
     * test Ograve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ograve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1715));
    }

    /**
     * test Ohm
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOhm() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ohm", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(57, 0, 1457, 1387));
    }

    /**
     * test Omacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Omacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1593));
    }

    /**
     * test Omega
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmega() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Omega", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(103, -8, 1486, 1292));
    }

    /**
     * test Omegatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmegatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Omegatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -8, 1498, 1292));
    }

    /**
     * test Omicron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmicron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Omicron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1292));
    }

    /**
     * test Omicrontonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOmicrontonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Omicrontonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-48, -20, 1502, 1292));
    }

    /**
     * test Oslash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Oslash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -62, 1502, 1335));
    }

    /**
     * test Oslashacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOslashacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Oslashacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -62, 1502, 1728));
    }

    /**
     * test Otilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfOtilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Otilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1608));
    }

    /**
     * test P
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfP() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("P", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1295));
    }

    /**
     * test Phi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfPhi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Phi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -8, 1556, 1284));
    }

    /**
     * test Pi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfPi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Pi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1486, 1284));
    }

    /**
     * test Psi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfPsi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Psi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, 0, 1537, 1292));
    }

    /**
     * test Q
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfQ() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Q", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(97, -446, 1532, 1316));
    }

    /**
     * test R
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfR() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("R", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -5, 1313, 1289));
    }

    /**
     * test Racute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfRacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Racute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -5, 1313, 1720));
    }

    /**
     * test Rcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfRcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Rcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -5, 1313, 1768));
    }

    /**
     * test Rcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfRcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Rcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -435, 1313, 1289));
    }

    /**
     * test Rho
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfRho() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Rho", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1295));
    }

    /**
     * test S
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfS() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("S", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1316));
    }

    /**
     * test SF010000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF010000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF010000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 709));
    }

    /**
     * test SF020000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF020000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF020000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, 534, 1474, 1864));
    }

    /**
     * test SF030000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF030000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF030000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 709));
    }

    /**
     * test SF040000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF040000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF040000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 812, 1864));
    }

    /**
     * test SF050000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF050000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF050000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test SF060000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF060000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF060000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 709));
    }

    /**
     * test SF070000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF070000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF070000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1473, 1864));
    }

    /**
     * test SF080000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF080000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF080000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 1864));
    }

    /**
     * test SF090000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF090000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF090000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 1864));
    }

    /**
     * test SF100000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF100000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF100000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1473, 709));
    }

    /**
     * test SF110000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF110000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF110000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 813, 1864));
    }

    /**
     * test SF190000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF190000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF190000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 1864));
    }

    /**
     * test SF200000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF200000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF200000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 1864));
    }

    /**
     * test SF210000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF210000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF210000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 709));
    }

    /**
     * test SF220000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF220000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF220000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 812, 899));
    }

    /**
     * test SF230000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF230000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF230000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 1864));
    }

    /**
     * test SF240000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF240000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF240000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1004, 1864));
    }

    /**
     * test SF250000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF250000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF250000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1003, 899));
    }

    /**
     * test SF260000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF260000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF260000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1003, 1864));
    }

    /**
     * test SF270000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF270000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF270000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1003, 1864));
    }

    /**
     * test SF280000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF280000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF280000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 812, 1864));
    }

    /**
     * test SF360000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF360000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF360000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 1864));
    }

    /**
     * test SF370000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF370000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF370000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1474, 1864));
    }

    /**
     * test SF380000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF380000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF380000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, 344, 1474, 1864));
    }

    /**
     * test SF390000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF390000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF390000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1474, 899));
    }

    /**
     * test SF400000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF400000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF400000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1473, 1864));
    }

    /**
     * test SF410000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF410000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF410000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 899));
    }

    /**
     * test SF420000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF420000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF420000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1474, 1864));
    }

    /**
     * test SF430000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF430000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF430000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1473, 899));
    }

    /**
     * test SF440000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF440000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF440000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test SF450000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF450000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF450000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 344, 1473, 1864));
    }

    /**
     * test SF460000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF460000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF460000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 534, 1473, 1864));
    }

    /**
     * test SF470000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF470000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF470000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 899));
    }

    /**
     * test SF480000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF480000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF480000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 709));
    }

    /**
     * test SF490000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF490000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF490000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, 534, 1475, 1864));
    }

    /**
     * test SF500000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF500000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF500000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, 344, 1474, 1864));
    }

    /**
     * test SF510000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF510000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF510000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(638, -621, 1474, 899));
    }

    /**
     * test SF520000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF520000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF520000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(448, -621, 1475, 709));
    }

    /**
     * test SF530000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF530000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF530000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test SF540000
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSF540000() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("SF540000", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test Sacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Sacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1720));
    }

    /**
     * test Scaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfScaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Scaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1761));
    }

    /**
     * test Scedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfScedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Scedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -431, 895, 1316));
    }

    /**
     * test Scircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfScircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Scircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1764));
    }

    /**
     * test Sigma
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfSigma() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Sigma", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -15, 1211, 1272));
    }

    /**
     * test T
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfT() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("T", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test Tau
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfTau() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Tau", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test Tbar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfTbar() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Tbar", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test Tcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfTcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Tcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1768));
    }

    /**
     * test Tcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfTcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Tcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -589, 1233, 1331));
    }

    /**
     * test Theta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfTheta() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Theta", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1503, 1292));
    }

    /**
     * test Thorn
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfThorn() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Thorn", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1280));
    }

    /**
     * test U
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfU() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("U", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1286));
    }

    /**
     * test Uacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Uacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1714));
    }

    /**
     * test Ubreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ubreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1697));
    }

    /**
     * test Ucircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ucircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1761));
    }

    /**
     * test Udblacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUdblacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Udblacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1724));
    }

    /**
     * test Udieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Udieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1579));
    }

    /**
     * test Ugrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ugrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1715));
    }

    /**
     * test Umacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUmacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Umacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1589));
    }

    /**
     * test Uogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Uogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -364, 1384, 1286));
    }

    /**
     * test Upsilon
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUpsilon() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Upsilon", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -13, 1168, 1292));
    }

    /**
     * test Upsilondieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUpsilondieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Upsilondieresis",
                (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -13, 1168, 1577));
    }

    /**
     * test Upsilontonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUpsilontonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Upsilontonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-44, -13, 1352, 1292));
    }

    /**
     * test Uring
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUring() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Uring", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1746));
    }

    /**
     * test Utilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfUtilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Utilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -34, 1384, 1585));
    }

    /**
     * test V
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfV() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("V", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-17, -39, 1406, 1287));
    }

    /**
     * test W
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfW() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("W", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1279));
    }

    /**
     * test Wacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfWacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Wacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1720));
    }

    /**
     * test Wcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfWcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Wcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1764));
    }

    /**
     * test Wdieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfWdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Wdieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1577));
    }

    /**
     * test Wgrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfWgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Wgrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -56, 1826, 1717));
    }

    /**
     * test X
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfX() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("X", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -21, 1449, 1277));
    }

    /**
     * test Xi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfXi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Xi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(69, -3, 1320, 1272));
    }

    /**
     * test Y
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfY() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Y", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1290));
    }

    /**
     * test Yacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfYacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Yacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1714));
    }

    /**
     * test Ycircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfYcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ycircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1760));
    }

    /**
     * test Ydieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfYdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ydieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1579));
    }

    /**
     * test Ygrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfYgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Ygrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1717));
    }

    /**
     * test Z
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfZ() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Z", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1346));
    }

    /**
     * test Zacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfZacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Zacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1720));
    }

    /**
     * test Zcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfZcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("Zcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1761));
    }

    /**
     * test Zdot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfZdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Zdot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1573));
    }

    /**
     * test Zeta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfZeta() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("Zeta", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -15, 1247, 1346));
    }

    /**
     * test a
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfa() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("a", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 817));
    }

    /**
     * test aacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfaacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("aacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1292));
    }

    /**
     * test abreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfabreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("abreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1233));
    }

    /**
     * test acircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfacircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("acircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1332));
    }

    /**
     * test acute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("acute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(244, 983, 582, 1292));
    }

    /**
     * test adieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfadieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("adieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1229));
    }

    /**
     * test ae
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfae() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ae", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(74, -32, 1149, 818));
    }

    /**
     * test aeacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfaeacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("aeacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(74, -32, 1149, 1292));
    }

    /**
     * test afii00208
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii00208() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii00208", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, 345, 1548, 438));
    }

    /**
     * test afii08941
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii08941() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii08941", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -482, 1211, 1297));
    }

    /**
     * test afii10017
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10017() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10017", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test afii10018
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10018() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10018", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1180, 1274));
    }

    /**
     * test afii10019
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10019() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10019", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1164, 1298));
    }

    /**
     * test afii10020
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10020() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10020", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -6, 1105, 1273));
    }

    /**
     * test afii10021
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10021() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10021", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-49, -299, 1385, 1343));
    }

    /**
     * test afii10022
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10022() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10022", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1274));
    }

    /**
     * test afii10023
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10023() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10023", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, -14, 1296, 1577));
    }

    /**
     * test afii10024
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10024() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10024", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -18, 2069, 1292));
    }

    /**
     * test afii10025
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10025() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10025", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -27, 1026, 1311));
    }

    /**
     * test afii10026
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10026() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10026", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -12, 1505, 1284));
    }

    /**
     * test afii10027
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10027() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10027", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -12, 1505, 1656));
    }

    /**
     * test afii10028
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10028() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10028", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1348, 1292));
    }

    /**
     * test afii10029
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10029() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10029", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, 0, 1371, 1343));
    }

    /**
     * test afii10030
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10030() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10030", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, -9, 1692, 1289));
    }

    /**
     * test afii10031
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10031() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10031", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1504, 1289));
    }

    /**
     * test afii10032
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10032() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10032", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -20, 1502, 1292));
    }

    /**
     * test afii10033
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10033() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10033", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1486, 1284));
    }

    /**
     * test afii10034
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10034() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10034", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(38, -20, 1098, 1295));
    }

    /**
     * test afii10035
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10035() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10035", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -27, 1231, 1311));
    }

    /**
     * test afii10036
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10036() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10036", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1233, 1331));
    }

    /**
     * test afii10037
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10037() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10037", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, -27, 1366, 1290));
    }

    /**
     * test afii10038
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10038() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10038", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -8, 1411, 1284));
    }

    /**
     * test afii10039
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10039() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10039", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -21, 1449, 1277));
    }

    /**
     * test afii10040
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10040() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10040", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -292, 1532, 1284));
    }

    /**
     * test afii10041
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10041() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10041", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -6, 1302, 1284));
    }

    /**
     * test afii10042
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10042() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10042", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 2095, 1284));
    }

    /**
     * test afii10043
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10043() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10043", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -292, 2103, 1284));
    }

    /**
     * test afii10044
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10044() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10044", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -2, 1475, 1275));
    }

    /**
     * test afii10045
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10045() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10045", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -6, 1807, 1284));
    }

    /**
     * test afii10046
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10046() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10046", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -2, 1180, 1284));
    }

    /**
     * test afii10047
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10047() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10047", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(70, -26, 1213, 1311));
    }

    /**
     * test afii10048
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10048() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10048", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 2177, 1292));
    }

    /**
     * test afii10049
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10049() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10049", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-33, -5, 1238, 1289));
    }

    /**
     * test afii10050
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10050() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10050", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -6, 1005, 1555));
    }

    /**
     * test afii10051
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10051() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10051", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -293, 1463, 1331));
    }

    /**
     * test afii10052
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10052() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10052", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -6, 1105, 1720));
    }

    /**
     * test afii10053
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10053() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10053", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -26, 1231, 1311));
    }

    /**
     * test afii10054
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10054() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10054", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(77, -33, 895, 1316));
    }

    /**
     * test afii10055
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10055() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10055", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, 0, 664, 1279));
    }

    /**
     * test afii10056
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10056() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10056", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -4, 664, 1573));
    }

    /**
     * test afii10057
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10057() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10057", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-173, -518, 569, 1279));
    }

    /**
     * test afii10058
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10058() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10058", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, -1, 1868, 1343));
    }

    /**
     * test afii10059
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10059() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10059", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 1928, 1289));
    }

    /**
     * test afii10060
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10060() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10060", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -25, 1513, 1331));
    }

    /**
     * test afii10061
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10061() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10061", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(58, -18, 1348, 1720));
    }

    /**
     * test afii10062
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10062() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10062", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-15, -27, 1366, 1656));
    }

    /**
     * test afii10065
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10065() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10065", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 817));
    }

    /**
     * test afii10066
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10066() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10066", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, -27, 976, 1339));
    }

    /**
     * test afii10067
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10067() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10067", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -1, 804, 820));
    }

    /**
     * test afii10068
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10068() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10068", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 732, 796));
    }

    /**
     * test afii10069
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10069() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10069", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, -213, 941, 847));
    }

    /**
     * test afii10070
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10070() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10070", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 822));
    }

    /**
     * test afii10071
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10071() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10071", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1229));
    }

    /**
     * test afii10072
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10072() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10072", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -3, 1287, 820));
    }

    /**
     * test afii10073
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10073() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10073", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -18, 695, 820));
    }

    /**
     * test afii10074
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10074() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10074", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 796));
    }

    /**
     * test afii10075
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10075() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10075", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 1256));
    }

    /**
     * test afii10076
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10076() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10076", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -3, 889, 820));
    }

    /**
     * test afii10077
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10077() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10077", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, 0, 1002, 847));
    }

    /**
     * test afii10078
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10078() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10078", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, -21, 1187, 795));
    }

    /**
     * test afii10079
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10079() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10079", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 796));
    }

    /**
     * test afii10080
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10080() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10080", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 820));
    }

    /**
     * test afii10081
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10081() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10081", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1093, 800));
    }

    /**
     * test afii10082
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10082() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10082", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -525, 971, 890));
    }

    /**
     * test afii10083
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10083() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10083", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 816));
    }

    /**
     * test afii10084
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10084() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10084", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -17, 834, 851));
    }

    /**
     * test afii10085
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10085() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10085", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 792));
    }

    /**
     * test afii10086
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10086() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10086", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -525, 1493, 1329));
    }

    /**
     * test afii10087
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10087() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10087", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(28, -1, 911, 789));
    }

    /**
     * test afii10088
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10088() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10088", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -213, 1093, 795));
    }

    /**
     * test afii10089
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10089() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10089", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -5, 1026, 796));
    }

    /**
     * test afii10090
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10090() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10090", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1607, 796));
    }

    /**
     * test afii10091
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10091() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10091", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -213, 1607, 796));
    }

    /**
     * test afii10092
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10092() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10092", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-7, -4, 1003, 793));
    }

    /**
     * test afii10093
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10093() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10093", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1259, 796));
    }

    /**
     * test afii10094
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10094() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10094", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -1, 821, 796));
    }

    /**
     * test afii10095
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10095() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10095", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(63, -18, 763, 819));
    }

    /**
     * test afii10096
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10096() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10096", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -27, 1521, 820));
    }

    /**
     * test afii10097
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10097() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10097", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -5, 825, 820));
    }

    /**
     * test afii10098
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10098() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10098", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(16, -5, 712, 933));
    }

    /**
     * test afii10099
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10099() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10099", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -282, 947, 1332));
    }

    /**
     * test afii10100
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10100() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10100", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 732, 1292));
    }

    /**
     * test afii10101
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10101() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10101", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 820));
    }

    /**
     * test afii10102
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10102() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10102", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 828));
    }

    /**
     * test afii10103
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10103() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10103", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -5, 454, 1310));
    }

    /**
     * test afii10104
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10104() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10104", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 542, 1229));
    }

    /**
     * test afii10105
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10105() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10105", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -539, 315, 1299));
    }

    /**
     * test afii10106
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10106() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10106", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, 0, 1331, 847));
    }

    /**
     * test afii10107
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10107() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10107", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -5, 1387, 796));
    }

    /**
     * test afii10108
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10108() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10108", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -7, 1018, 1332));
    }

    /**
     * test afii10109
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10109() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10109", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -3, 889, 1292));
    }

    /**
     * test afii10110
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10110() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10110", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1256));
    }

    /**
     * test afii10145
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10145() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10145", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -236, 1486, 1284));
    }

    /**
     * test afii10193
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii10193() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii10193", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(18, -213, 1093, 796));
    }

    /**
     * test afii61248
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii61248() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii61248", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(119, -67, 1506, 1305));
    }

    /**
     * test afii61289
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii61289() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii61289", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(100, -26, 893, 1384));
    }

    /**
     * test afii61352
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfafii61352() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("afii61352", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -47, 2028, 1292));
    }

    /**
     * test agrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfagrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("agrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1293));
    }

    /**
     * test alpha
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfalpha() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("alpha", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -22, 935, 820));
    }

    /**
     * test alphatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfalphatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("alphatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -22, 935, 1315));
    }

    /**
     * test amacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfamacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("amacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1185));
    }

    /**
     * test ampersand
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfampersand() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ampersand", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -30, 1461, 1217));
    }

    /**
     * test anoteleia
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfanoteleia() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("anoteleia", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(117, 568, 331, 794));
    }

    /**
     * test aogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfaogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("aogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -328, 967, 817));
    }

    /**
     * test approxequal
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfapproxequal() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("approxequal", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 245, 1219, 1030));
    }

    /**
     * test aring
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfaring() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("aring", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1259));
    }

    /**
     * test aringacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfaringacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("aringacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1668));
    }

    /**
     * test arrowboth
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowboth() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowboth", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, 158, 1968, 878));
    }

    /**
     * test arrowdown
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowdown() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowdown", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(153, -429, 872, 1339));
    }

    /**
     * test arrowleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, 157, 1968, 876));
    }

    /**
     * test arrowright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, 157, 1968, 876));
    }

    /**
     * test arrowup
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowup() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowup", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(153, -429, 872, 1339));
    }

    /**
     * test arrowupdn
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowupdn() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowupdn", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(152, -427, 871, 1463));
    }

    /**
     * test arrowupdnbse
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfarrowupdnbse() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("arrowupdnbse", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(152, -620, 871, 1463));
    }

    /**
     * test asciicircum
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfasciicircum() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("asciicircum", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, 783, 961, 1374));
    }

    /**
     * test asciitilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfasciitilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("asciitilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(150, 499, 1215, 775));
    }

    /**
     * test asterisk
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfasterisk() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("asterisk", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(59, 492, 805, 1294));
    }

    /**
     * test at
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfat() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("at", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(97, -442, 1836, 1422));
    }

    /**
     * test atilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfatilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("atilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(66, -24, 819, 1237));
    }

    /**
     * test b
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfb() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("b", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(34, -43, 965, 1349));
    }

    /**
     * test backslash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbackslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("backslash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -277, 911, 1426));
    }

    /**
     * test bar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbar() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("bar", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(468, -528, 557, 1339));
    }

    /**
     * test beta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbeta() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("beta", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, -505, 908, 1319));
    }

    /**
     * test block
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfblock() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("block", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 1864));
    }

    /**
     * test braceleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbraceleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("braceleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(283, -442, 841, 1422));
    }

    /**
     * test braceright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbraceright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("braceright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(177, -442, 735, 1422));
    }

    /**
     * test bracketleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbracketleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("bracketleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(208, -475, 605, 1285));
    }

    /**
     * test bracketright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbracketright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("bracketright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-43, -477, 357, 1285));
    }

    /**
     * test breve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("breve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, 951, 625, 1233));
    }

    /**
     * test brokenbar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbrokenbar() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("brokenbar", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(468, -528, 557, 1339));
    }

    /**
     * test bullet
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfbullet() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("bullet", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(112, 426, 614, 928));
    }

    /**
     * test c
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfc() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("c", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 816));
    }

    /**
     * test cacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("cacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1292));
    }

    /**
     * test caron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("caron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(148, 977, 587, 1332));
    }

    /**
     * test ccaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfccaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ccaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1332));
    }

    /**
     * test ccedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfccedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ccedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -431, 800, 816));
    }

    /**
     * test ccircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfccircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ccircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1332));
    }

    /**
     * test cdot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("cdot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -31, 800, 1229));
    }

    /**
     * test cedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("cedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(1, -431, 300, 14));
    }

    /**
     * test cent
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcent() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("cent", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, -346, 798, 1188));
    }

    /**
     * test chi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfchi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("chi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(48, -506, 952, 820));
    }

    /**
     * test circle
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcircle() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("circle", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(178, 137, 1059, 1018));
    }

    /**
     * test circumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("circumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 977, 586, 1332));
    }

    /**
     * test club
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfclub() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("club", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, 0, 1288, 1231));
    }

    /**
     * test colon
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcolon() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("colon", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(117, -27, 331, 794));
    }

    /**
     * test comma
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcomma() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("comma", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(84, -356, 388, 141));
    }

    /**
     * test commaaccent
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcommaaccent() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("commaaccent", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(192, 851, 486, 1348));
    }

    /**
     * test copyright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcopyright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("copyright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(69, -31, 1488, 1387));
    }

    /**
     * test currency
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfcurrency() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("currency", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(202, 183, 1157, 1137));
    }

    /**
     * test d
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfd() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("d", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -37, 999, 1348));
    }

    /**
     * test dagger
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdagger() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dagger", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -499, 865, 1312));
    }

    /**
     * test daggerdbl
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdaggerdbl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("daggerdbl", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(31, -493, 843, 1317));
    }

    /**
     * test dcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -37, 1266, 1348));
    }

    /**
     * test degree
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdegree() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("degree", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(98, 771, 713, 1386));
    }

    /**
     * test delta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdelta() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("delta", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(86, -22, 896, 1316));
    }

    /**
     * test diamond
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdiamond() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("diamond", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(213, -24, 1131, 1234));
    }

    /**
     * test dieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, 1056, 649, 1229));
    }

    /**
     * test dieresistonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdieresistonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dieresistonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(41, 937, 643, 1315));
    }

    /**
     * test divide
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdivide() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("divide", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(23, 279, 1101, 1075));
    }

    /**
     * test dkshade
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdkshade() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dkshade", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -628, 1447, 1864));
    }

    /**
     * test dmacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdmacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dmacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(67, -37, 1038, 1348));
    }

    /**
     * test dnblock
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdnblock() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dnblock", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 1473, 621));
    }

    /**
     * test dollar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdollar() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dollar", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -273, 829, 1342));
    }

    /**
     * test dotaccent
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdotaccent() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dotaccent", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(291, 1056, 463, 1229));
    }

    /**
     * test dotlessi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfdotlessi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("dotlessi", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 450, 890));
    }

    /**
     * test e
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfe() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("e", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 822));
    }

    /**
     * test eacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1292));
    }

    /**
     * test ebreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfebreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ebreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1233));
    }

    /**
     * test ecaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfecaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ecaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1332));
    }

    /**
     * test ecircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfecircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ecircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1332));
    }

    /**
     * test edieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfedieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("edieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1229));
    }

    /**
     * test edot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfedot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("edot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1229));
    }

    /**
     * test egrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfegrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("egrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1293));
    }

    /**
     * test eight
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeight() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eight", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -28, 879, 1297));
    }

    /**
     * test eightsuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeightsuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eightsuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(84, 614, 559, 1304));
    }

    /**
     * test ellipsis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfellipsis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ellipsis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(234, -19, 1814, 197));
    }

    /**
     * test emacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfemacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("emacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -26, 804, 1185));
    }

    /**
     * test emdash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfemdash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("emdash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, 345, 2060, 438));
    }

    /**
     * test endash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfendash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("endash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, 345, 1036, 438));
    }

    /**
     * test eng
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeng() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eng", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -498, 869, 843));
    }

    /**
     * test eogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -328, 803, 822));
    }

    /**
     * test epsilon
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfepsilon() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("epsilon", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -22, 716, 820));
    }

    /**
     * test epsilontonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfepsilontonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("epsilontonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -22, 716, 1315));
    }

    /**
     * test equal
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfequal() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("equal", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 361, 1219, 913));
    }

    /**
     * test equivalence
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfequivalence() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("equivalence", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(203, 131, 1275, 1147));
    }

    /**
     * test estimated
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfestimated() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("estimated", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(90, -34, 1148, 1096));
    }

    /**
     * test eta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeta() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eta", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(50, -505, 894, 821));
    }

    /**
     * test etatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfetatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("etatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(50, -505, 894, 1315));
    }

    /**
     * test eth
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfeth() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("eth", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(92, -24, 995, 1315));
    }

    /**
     * test exclam
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfexclam() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("exclam", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(125, -25, 329, 1307));
    }

    /**
     * test exclamdbl
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfexclamdbl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("exclamdbl", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(129, -25, 689, 1307));
    }

    /**
     * test exclamdown
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfexclamdown() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("exclamdown", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(121, -492, 326, 836));
    }

    /**
     * test f
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyff() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("f", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(96, -1, 825, 1339));
    }

    /**
     * test female
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffemale() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("female", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(244, -439, 1291, 1507));
    }

    /**
     * test fi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -1, 1066, 1343));
    }

    /**
     * test fi1
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffi1() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fi1", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -1, 1066, 1343));
    }

    /**
     * test filledbox
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffilledbox() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("filledbox", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 0, 1090, 944));
    }

    /**
     * test filledrect
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffilledrect() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("filledrect", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, 317, 2047, 703));
    }

    /**
     * test five
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffive() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("five", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(105, -33, 857, 1307));
    }

    /**
     * test fiveeighths
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffiveeighths() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fiveeighths", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -67, 1607, 1305));
    }

    /**
     * test fivesuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffivesuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fivesuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, 608, 541, 1305));
    }

    /**
     * test fl
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fl", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -4, 1052, 1336));
    }

    /**
     * test fl1
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffl1() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fl1", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(93, -4, 1052, 1336));
    }

    /**
     * test florin
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfflorin() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("florin", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-2, -525, 1261, 1315));
    }

    /**
     * test four
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffour() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("four", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(55, -24, 935, 1304));
    }

    /**
     * test foursuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffoursuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("foursuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(57, 612, 600, 1304));
    }

    /**
     * test fraction
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffraction() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fraction", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-284, -67, 645, 1305));
    }

    /**
     * test fraction1
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffraction1() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("fraction1", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-284, -67, 645, 1305));
    }

    /**
     * test franc
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyffranc() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("franc", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -20, 1341, 1294));
    }

    /**
     * test g
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfg() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("g", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 820));
    }

    /**
     * test gamma
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgamma() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("gamma", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, -505, 791, 820));
    }

    /**
     * test gbreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgbreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("gbreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1233));
    }

    /**
     * test gcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("gcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1407));
    }

    /**
     * test gcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("gcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1332));
    }

    /**
     * test gdot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("gdot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, -528, 944, 1229));
    }

    /**
     * test germandbls
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgermandbls() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("germandbls", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(15, -32, 962, 1318));
    }

    /**
     * test grave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("grave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(199, 983, 535, 1293));
    }

    /**
     * test greater
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgreater() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("greater", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 144, 1218, 1130));
    }

    /**
     * test greaterequal
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfgreaterequal() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("greaterequal", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, -41, 1220, 1356));
    }

    /**
     * test guillemotleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfguillemotleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("guillemotleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(12, 11, 749, 800));
    }

    /**
     * test guillemotright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfguillemotright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("guillemotright", (short) 3,
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

        XtfBoundingBox bb = reader.mapCharCodeToBB("guilsinglleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(14, 13, 390, 806));
    }

    /**
     * test guilsinglright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfguilsinglright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("guilsinglright", (short) 3,
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

        XtfBoundingBox bb = reader.mapCharCodeToBB("h", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(30, -7, 1018, 1332));
    }

    /**
     * test hbar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfhbar() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("hbar", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-11, -7, 1018, 1332));
    }

    /**
     * test hcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfhcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("hcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(30, -7, 1018, 1792));
    }

    /**
     * test heart
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfheart() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("heart", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(127, -24, 1217, 1231));
    }

    /**
     * test house
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfhouse() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("house", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(157, 0, 1080, 1153));
    }

    /**
     * test hungarumlaut
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfhungarumlaut() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("hungarumlaut", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(172, 983, 764, 1292));
    }

    /**
     * test hyphen
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfhyphen() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("hyphen", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, 352, 564, 446));
    }

    /**
     * test i
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("i", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -5, 454, 1310));
    }

    /**
     * test iacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfiacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("iacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 475, 1292));
    }

    /**
     * test ibreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfibreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ibreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 506, 1269));
    }

    /**
     * test icircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyficircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("icircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 459, 1332));
    }

    /**
     * test idieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfidieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("idieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-4, -5, 512, 1229));
    }

    /**
     * test igrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfigrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("igrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -5, 450, 1293));
    }

    /**
     * test ij
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfij() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ij", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-3, -525, 831, 1313));
    }

    /**
     * test imacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfimacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("imacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-21, -5, 516, 1185));
    }

    /**
     * test infinity
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfinfinity() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("infinity", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, 219, 1402, 1055));
    }

    /**
     * test integralbt
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfintegralbt() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("integralbt", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(261, -515, 691, 1737));
    }

    /**
     * test integraltp
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfintegraltp() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("integraltp", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(546, -515, 976, 1737));
    }

    /**
     * test intersection
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfintersection() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("intersection", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(192, 0, 1281, 1297));
    }

    /**
     * test invbullet
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfinvbullet() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("invbullet", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(128, 0, 1108, 980));
    }

    /**
     * test invcircle
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfinvcircle() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("invcircle", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, 0, 1197, 1155));
    }

    /**
     * test invsmileface
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfinvsmileface() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("invsmileface", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(386, -119, 1662, 1156));
    }

    /**
     * test iogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfiogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("iogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -328, 489, 1310));
    }

    /**
     * test iota
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfiota() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("iota", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -22, 409, 802));
    }

    /**
     * test iotadieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfiotadieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("iotadieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-24, -22, 492, 1229));
    }

    /**
     * test iotadieresistonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfiotadieresistonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("iotadieresistonos",
                (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-101, -22, 501, 1315));
    }

    /**
     * test iotatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfiotatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("iotatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -22, 409, 1315));
    }

    /**
     * test itilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfitilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("itilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-29, -5, 544, 1237));
    }

    /**
     * test j
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfj() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("j", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(42, -539, 315, 1299));
    }

    /**
     * test jcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfjcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("jcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, -539, 466, 1332));
    }

    /**
     * test k
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfk() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("k", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -2, 977, 1340));
    }

    /**
     * test kappa
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfkappa() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("kappa", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -22, 899, 820));
    }

    /**
     * test kcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfkcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("kcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(52, -435, 977, 1340));
    }

    /**
     * test kgreenlandic
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfkgreenlandic() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("kgreenlandic", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -13, 926, 781));
    }

    /**
     * test l
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("l", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 465, 1329));
    }

    /**
     * test lacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 465, 1662));
    }

    /**
     * test lambda
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflambda() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lambda", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -22, 863, 1297));
    }

    /**
     * test lcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 712, 1329));
    }

    /**
     * test lcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -435, 465, 1329));
    }

    /**
     * test ldot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfldot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("ldot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(10, -3, 615, 1329));
    }

    /**
     * test less
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfless() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("less", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, 144, 1218, 1130));
    }

    /**
     * test lessequal
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflessequal() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lessequal", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, -41, 1220, 1356));
    }

    /**
     * test lfblock
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflfblock() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lfblock", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, -621, 725, 1864));
    }

    /**
     * test logicalnot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflogicalnot() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("logicalnot", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 369, 1220, 945));
    }

    /**
     * test longs
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflongs() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("longs", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, -1, 806, 1339));
    }

    /**
     * test lozenge
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflozenge() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lozenge", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(47, 0, 967, 1422));
    }

    /**
     * test lslash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyflslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("lslash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, 6, 563, 1338));
    }

    /**
     * test ltshade
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfltshade() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ltshade", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -504, 1327, 1864));
    }

    /**
     * test m
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfm() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("m", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(36, -2, 1544, 856));
    }

    /**
     * test male
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfmale() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("male", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(111, -262, 1415, 1620));
    }

    /**
     * test minus
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfminus() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("minus", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 593, 1220, 681));
    }

    /**
     * test minute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfminute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("minute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(49, 803, 250, 1387));
    }

    /**
     * test mu
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfmu() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("mu", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(140, -505, 1032, 820));
    }

    /**
     * test mu1
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfmu1() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("mu1", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(47, -443, 1018, 785));
    }

    /**
     * test multiply
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfmultiply() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("multiply", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(197, 151, 1171, 1123));
    }

    /**
     * test musicalnote
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfmusicalnote() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("musicalnote", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -37, 987, 1363));
    }

    /**
     * test musicalnotedbl
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfmusicalnotedbl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("musicalnotedbl", (short) 3,
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

        XtfBoundingBox bb = reader.mapCharCodeToBB("n", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 843));
    }

    /**
     * test nacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("nacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 1292));
    }

    /**
     * test napostrophe
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnapostrophe() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("napostrophe", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(96, 1, 1352, 1304));
    }

    /**
     * test ncaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfncaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ncaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 1332));
    }

    /**
     * test ncedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfncedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ncedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -435, 1024, 843));
    }

    /**
     * test nine
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnine() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("nine", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(90, -30, 864, 1307));
    }

    /**
     * test nonbreakingspace
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnonbreakingspace() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("nbspace", (short) 3,
                (short) 1);
        assertNull(bb);
    }

    /**
     * test notequal
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnotequal() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("notequal", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(147, -170, 1219, 1446));
    }

    /**
     * test nsuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnsuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("nsuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(13, 519, 678, 1038));
    }

    /**
     * test ntilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfntilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ntilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(35, -3, 1024, 1237));
    }

    /**
     * test nu
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnu() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("nu", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(36, -22, 700, 820));
    }

    /**
     * test numbersign
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfnumbersign() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("numbersign", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -47, 1271, 1365));
    }

    /**
     * test o
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfo() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("o", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 820));
    }

    /**
     * test oacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfoacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("oacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1292));
    }

    /**
     * test obreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfobreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("obreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1233));
    }

    /**
     * test ocircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfocircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ocircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1332));
    }

    /**
     * test odblacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfodblacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("odblacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1292));
    }

    /**
     * test odieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfodieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("odieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1229));
    }

    /**
     * test oe
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfoe() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("oe", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -33, 1364, 820));
    }

    /**
     * test ogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(185, -328, 603, 1));
    }

    /**
     * test ograve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfograve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ograve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1293));
    }

    /**
     * test omacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfomacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("omacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1185));
    }

    /**
     * test omega
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfomega() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("omega", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, -22, 1095, 810));
    }

    /**
     * test omegatonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfomegatonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("omegatonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, -22, 1095, 1315));
    }

    /**
     * test omicron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfomicron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("omicron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -22, 936, 820));
    }

    /**
     * test omicrontonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfomicrontonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("omicrontonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -22, 936, 1315));
    }

    /**
     * test one
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfone() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("one", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(154, 2, 727, 1298));
    }

    /**
     * test oneeighth
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfoneeighth() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("oneeighth", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(112, -67, 1586, 1305));
    }

    /**
     * test onehalf
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfonehalf() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("onehalf", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -67, 1590, 1305));
    }

    /**
     * test onequarter
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfonequarter() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("onequarter", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -71, 1608, 1301));
    }

    /**
     * test onesuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfonesuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("onesuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, 626, 475, 1301));
    }

    /**
     * test openbullet
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfopenbullet() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("openbullet", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, 390, 649, 962));
    }

    /**
     * test ordfeminine
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfordfeminine() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ordfeminine", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(27, 774, 541, 1292));
    }

    /**
     * test ordmasculine
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfordmasculine() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ordmasculine", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, 771, 645, 1292));
    }

    /**
     * test orthogonal
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyforthogonal() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("orthogonal", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(362, 0, 1643, 1279));
    }

    /**
     * test oslash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfoslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("oslash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -48, 976, 845));
    }

    /**
     * test oslashacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfoslashacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("oslashacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -48, 976, 1292));
    }

    /**
     * test otilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfotilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("otilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -27, 971, 1237));
    }

    /**
     * test p
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfp() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("p", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -525, 971, 890));
    }

    /**
     * test paragraph
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfparagraph() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("paragraph", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-14, -442, 931, 1356));
    }

    /**
     * test parenleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfparenleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("parenleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(157, -502, 634, 1309));
    }

    /**
     * test parenright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfparenright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("parenright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-44, -500, 438, 1311));
    }

    /**
     * test partialdiff
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfpartialdiff() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("partialdiff", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -31, 991, 1422));
    }

    /**
     * test percent
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfpercent() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("percent", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(74, -67, 1617, 1305));
    }

    /**
     * test period
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfperiod() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("period", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(120, -29, 328, 191));
    }

    /**
     * test perthousand
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfperthousand() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("perthousand", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -67, 2023, 1305));
    }

    /**
     * test peseta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfpeseta() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("peseta", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -32, 1982, 1295));
    }

    /**
     * test phi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfphi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("phi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -505, 935, 820));
    }

    /**
     * test pi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfpi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("pi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(44, -28, 1006, 916));
    }

    /**
     * test pi1
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfpi1() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("pi1", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -22, 914, 805));
    }

    /**
     * test plus
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfplus() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("plus", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(145, 101, 1220, 1173));
    }

    /**
     * test plusminus
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfplusminus() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("plusminus", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(145, -37, 1220, 1353));
    }

    /**
     * test product
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfproduct() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("product", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(46, -528, 1640, 1339));
    }

    /**
     * test psi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfpsi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("psi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -505, 953, 1315));
    }

    /**
     * test q
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfq() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("q", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(70, -524, 1021, 845));
    }

    /**
     * test question
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquestion() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("question", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, -30, 677, 1311));
    }

    /**
     * test questiondown
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquestiondown() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("questiondown", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -502, 619, 836));
    }

    /**
     * test quotedbl
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedbl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotedbl", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(133, 803, 700, 1387));
    }

    /**
     * test quotedblbase
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedblbase() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotedblbase", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(65, -354, 832, 147));
    }

    /**
     * test quotedblleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedblleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotedblleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(89, 803, 858, 1301));
    }

    /**
     * test quotedblright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotedblright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotedblright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, 811, 845, 1317));
    }

    /**
     * test quoteleft
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquoteleft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quoteleft", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(105, 806, 408, 1306));
    }

    /**
     * test quotereversed
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotereversed() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotereversed", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(102, 806, 396, 1303));
    }

    /**
     * test quoteright
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquoteright() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quoteright", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(102, 806, 396, 1303));
    }

    /**
     * test quotesinglbase
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfquotesinglbase() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotesinglbase", (short) 3,
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

        XtfBoundingBox bb = reader.mapCharCodeToBB("quotesingle", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(81, 803, 282, 1387));
    }

    /**
     * test r
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfr() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("r", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -3, 680, 865));
    }

    /**
     * test racute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfracute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("racute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -3, 680, 1292));
    }

    /**
     * test radical
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfradical() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("radical", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, -78, 1127, 1869));
    }

    /**
     * test radicalex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfradicalex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("radicalex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-24, 1795, 926, 1869));
    }

    /**
     * test rcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfrcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("rcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -3, 680, 1332));
    }

    /**
     * test rcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfrcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("rcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(37, -435, 680, 865));
    }

    /**
     * test registered
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfregistered() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("registered", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(69, -31, 1488, 1387));
    }

    /**
     * test revlogicalnot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfrevlogicalnot() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("revlogicalnot", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(146, 369, 1220, 945));
    }

    /**
     * test rho
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfrho() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("rho", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -505, 936, 820));
    }

    /**
     * test ring
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfring() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("ring", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(215, 930, 543, 1259));
    }

    /**
     * test rtblock
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfrtblock() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("rtblock", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(726, -621, 1474, 1864));
    }

    /**
     * test s
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfs() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("s", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 828));
    }

    /**
     * test sacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 1292));
    }

    /**
     * test scaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfscaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("scaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 1332));
    }

    /**
     * test scedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfscedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("scedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -431, 659, 828));
    }

    /**
     * test scircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfscircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("scircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(113, -32, 659, 1332));
    }

    /**
     * test second
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsecond() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("second", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(101, 803, 668, 1387));
    }

    /**
     * test section
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsection() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("section", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -498, 757, 1314));
    }

    /**
     * test semicolon
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsemicolon() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("semicolon", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, -321, 387, 802));
    }

    /**
     * test seven
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfseven() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("seven", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(94, -26, 883, 1269));
    }

    /**
     * test seveneighths
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfseveneighths() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("seveneighths", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, -67, 1607, 1305));
    }

    /**
     * test sevensuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsevensuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sevensuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(87, 612, 576, 1286));
    }

    /**
     * test sfthyphen
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsfthyphen() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sfthyphen", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(76, 352, 564, 446));
    }

    /**
     * test shade
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfshade() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("shade", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(0, -504, 1447, 1864));
    }

    /**
     * test sigma
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsigma() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sigma", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -20, 954, 788));
    }

    /**
     * test sigma1
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsigma1() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sigma1", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(73, -300, 719, 820));
    }

    /**
     * test six
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsix() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("six", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(100, -28, 875, 1310));
    }

    /**
     * test slash
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfslash() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("slash", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(116, -277, 908, 1426));
    }

    /**
     * test smileface
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsmileface() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("smileface", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(386, -119, 1662, 1156));
    }

    /**
     * test space
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfspace() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("space", (short) 3,
                (short) 1);
        assertNull(bb);
    }

    /**
     * test spade
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfspade() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("spade", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(186, 0, 1158, 1231));
    }

    /**
     * test sterling
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsterling() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sterling", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -482, 1211, 1297));
    }

    /**
     * test summation
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsummation() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("summation", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(43, -528, 1420, 1339));
    }

    /**
     * test sun
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfsun() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("sun", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(16, -223, 1862, 1621));
    }

    /**
     * test t
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyft() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("t", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(56, -21, 605, 988));
    }

    /**
     * test tau
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftau() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("tau", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(60, -22, 702, 805));
    }

    /**
     * test tbar
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftbar() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("tbar", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(39, -21, 606, 988));
    }

    /**
     * test tcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("tcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(56, -21, 958, 1300));
    }

    /**
     * test tcedilla
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftcedilla() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("tcedilla", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(56, -581, 605, 988));
    }

    /**
     * test theta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftheta() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("theta", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -22, 832, 1315));
    }

    /**
     * test thorn
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfthorn() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("thorn", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(24, -525, 971, 1329));
    }

    /**
     * test three
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfthree() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("three", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(79, -28, 869, 1304));
    }

    /**
     * test threeeighths
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfthreeeighths() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("threeeighths", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(78, -67, 1607, 1305));
    }

    /**
     * test threequarters
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfthreequarters() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("threequarters", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -67, 1620, 1305));
    }

    /**
     * test threesuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfthreesuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("threesuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, 610, 562, 1304));
    }

    /**
     * test tilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("tilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(88, 1033, 661, 1237));
    }

    /**
     * test tonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("tonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(260, 937, 423, 1315));
    }

    /**
     * test trademark
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftrademark() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("trademark", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(30, 549, 1973, 1356));
    }

    /**
     * test triagdn
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftriagdn() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("triagdn", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(304, -31, 1724, 1388));
    }

    /**
     * test triaglf
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftriaglf() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("triaglf", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(288, -31, 1739, 1417));
    }

    /**
     * test triagrt
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftriagrt() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("triagrt", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(288, -31, 1739, 1417));
    }

    /**
     * test triagup
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftriagup() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("triagup", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(304, 0, 1724, 1419));
    }

    /**
     * test two
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftwo() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("two", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(45, 2, 904, 1298));
    }

    /**
     * test twosuperior
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyftwosuperior() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("twosuperior", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(51, 626, 582, 1301));
    }

    /**
     * test u
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfu() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("u", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 785));
    }

    /**
     * test uacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfuacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("uacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1292));
    }

    /**
     * test ubreve
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfubreve() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ubreve", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1233));
    }

    /**
     * test ucircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfucircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ucircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1332));
    }

    /**
     * test udblacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfudblacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("udblacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1292));
    }

    /**
     * test udieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfudieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("udieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1229));
    }

    /**
     * test ugrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfugrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ugrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1293));
    }

    /**
     * test umacron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfumacron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("umacron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1185));
    }

    /**
     * test undercommaaccent
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfundercommaaccent() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("undercommaaccent",
                (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(274, -435, 518, -87));
    }

    /**
     * test underscore
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfunderscore() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("underscore", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, -256, 1036, -154));
    }

    /**
     * test underscoredbl
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfunderscoredbl() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("underscoredbl", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, -509, 1036, -154));
    }

    /**
     * test uogonek
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfuogonek() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("uogonek", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -328, 1071, 785));
    }

    /**
     * test upblock
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfupblock() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("upblock", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-23, 621, 1473, 1864));
    }

    /**
     * test upsilon
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilon() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("upsilon", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 820));
    }

    /**
     * test upsilondieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilondieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("upsilondieresis",
                (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 1229));
    }

    /**
     * test upsilondieresistonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilondieresistonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("upsilondieresistonos",
                (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 1315));
    }

    /**
     * test upsilontonos
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfupsilontonos() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("upsilontonos", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(85, -22, 780, 1315));
    }

    /**
     * test uring
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfuring() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("uring", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1259));
    }

    /**
     * test utilde
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfutilde() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("utilde", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(33, -19, 991, 1237));
    }

    /**
     * test v
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfv() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("v", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-12, -42, 978, 794));
    }

    /**
     * test w
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfw() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("w", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 789));
    }

    /**
     * test wacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfwacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("wacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1292));
    }

    /**
     * test wcircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfwcircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("wcircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1332));
    }

    /**
     * test wdieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfwdieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("wdieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1229));
    }

    /**
     * test wgrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfwgrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("wgrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-22, -47, 1383, 1293));
    }

    /**
     * test x
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfx() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("x", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(28, -1, 911, 789));
    }

    /**
     * test xi
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfxi() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("xi", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -300, 762, 1315));
    }

    /**
     * test y
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfy() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("y", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 792));
    }

    /**
     * test yacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfyacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("yacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1292));
    }

    /**
     * test ycircumflex
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfycircumflex() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ycircumflex", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1332));
    }

    /**
     * test ydieresis
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfydieresis() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ydieresis", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1229));
    }

    /**
     * test yen
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfyen() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("yen", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(-20, -13, 1361, 1290));
    }

    /**
     * test ygrave
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfygrave() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("ygrave", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(7, -505, 881, 1293));
    }

    /**
     * test z
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfz() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("z", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 866));
    }

    /**
     * test zacute
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfzacute() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("zacute", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 1292));
    }

    /**
     * test zcaron
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfzcaron() throws Exception {

        XtfBoundingBox bb = reader.mapCharCodeToBB("zcaron", (short) 3,
                (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 1332));
    }

    /**
     * test zdot
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfzdot() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("zdot", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(54, -6, 797, 1229));
    }

    /**
     * test zero
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfzero() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("zero", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(72, -30, 896, 1304));
    }

    /**
     * test zeta
     *
     * @throws Exception if an error occurred.
     */
    public void testGlyfzeta() throws Exception {

        XtfBoundingBox bb = reader
                .mapCharCodeToBB("zeta", (short) 3, (short) 1);
        assertNotNull(bb);
        assertTrue(bb.eq(80, -300, 762, 1315));
    }

}
