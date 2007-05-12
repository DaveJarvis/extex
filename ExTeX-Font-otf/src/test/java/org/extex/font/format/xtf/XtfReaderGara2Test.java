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
public class XtfReaderGara2Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderGara2Test() throws IOException {

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
     * test A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxA() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("A", 0, (short) 3,
            (short) 1));
    }

    /**
     * test AE
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAE() throws Exception {

        assertEquals(1749, reader.mapCharCodeToWidth("AE", 0, (short) 3,
            (short) 1));
    }

    /**
     * test AEacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAEacute() throws Exception {

        assertEquals(1749, reader.mapCharCodeToWidth("AEacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAacute() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Aacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbreve() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Abreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflex() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Acircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdieresis() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Adieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAgrave() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Agrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Alpha
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAlpha() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Alpha", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Alphatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAlphatonos() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Alphatonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAmacron() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Amacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAogonek() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("Aogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAring() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Aring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAringacute() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Aringacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAtilde() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Atilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxB() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("B", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Beta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxBeta() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("Beta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test C
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxC() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("C", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCacute() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("Cacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcaron() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("Ccaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcedilla() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("Ccedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcircumflex() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("Ccircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Cdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCdot() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("Cdot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Chi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxChi() throws Exception {

        assertEquals(1429, reader.mapCharCodeToWidth("Chi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test D
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxD() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("D", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDcaron() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Dcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Dslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDslash() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Dslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test E
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxE() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("E", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEacute() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Eacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEbreve() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Ebreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcaron() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Ecaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflex() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Ecircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdieresis() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Edieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Edot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdot() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Edot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEgrave() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Egrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEmacron() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Emacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEng() throws Exception {

        assertEquals(1535, reader.mapCharCodeToWidth("Eng", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEogonek() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Eogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Epsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEpsilon() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Epsilon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Epsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEpsilontonos() throws Exception {

        assertEquals(1477, reader.mapCharCodeToWidth("Epsilontonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Eta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEta() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("Eta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Etatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEtatonos() throws Exception {

        assertEquals(1651, reader.mapCharCodeToWidth("Etatonos", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEth() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Eth", 0, (short) 3,
            (short) 1));
    }

    /**
     * test F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxF() throws Exception {

        assertEquals(1152, reader.mapCharCodeToWidth("F", 0, (short) 3,
            (short) 1));
    }

    /**
     * test G
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxG() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("G", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gamma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGamma() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("Gamma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGbreve() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Gbreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcedilla() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Gcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcircumflex() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Gcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Gdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGdot() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Gdot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("H", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H18533
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH18533() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("H18533", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H18543
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH18543() throws Exception {

        assertEquals(727, reader.mapCharCodeToWidth("H18543", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H18551
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH18551() throws Exception {

        assertEquals(727, reader.mapCharCodeToWidth("H18551", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H22073
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH22073() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("H22073", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxHbar() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("Hbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxHcircumflex() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("Hcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test I
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxI() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("I", 0, (short) 3,
            (short) 1));
    }

    /**
     * test IJ
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIJ() throws Exception {

        assertEquals(1408, reader.mapCharCodeToWidth("IJ", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIacute() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Iacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIbreve() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Ibreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIcircumflex() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Icircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdieresis() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Idieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIgrave() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Igrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxImacron() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Imacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIogonek() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Iogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iota
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIota() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Iota", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iotadieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIotadieresis() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Iotadieresis", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Iotatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIotatonos() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("Iotatonos", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxItilde() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("Itilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test J
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJ() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("J", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJcircumflex() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("Jcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test K
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxK() throws Exception {

        assertEquals(1515, reader.mapCharCodeToWidth("K", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Kappa
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxKappa() throws Exception {

        assertEquals(1515, reader.mapCharCodeToWidth("Kappa", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxKcedilla() throws Exception {

        assertEquals(1515, reader.mapCharCodeToWidth("Kcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test L
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("L", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLacute() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("Lacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lambda
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLambda() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("Lambda", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcaron() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("Lcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcedilla() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("Lcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLdot() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("Ldot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLslash() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("Lslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test M
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxM() throws Exception {

        assertEquals(1707, reader.mapCharCodeToWidth("M", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Mu
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxMu() throws Exception {

        assertEquals(1707, reader.mapCharCodeToWidth("Mu", 0, (short) 3,
            (short) 1));
    }

    /**
     * test N
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("N", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNacute() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Nacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcaron() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Ncaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcedilla() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Ncedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNtilde() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Ntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Nu
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNu() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Nu", 0, (short) 3,
            (short) 1));
    }

    /**
     * test O
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxO() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("O", 0, (short) 3,
            (short) 1));
    }

    /**
     * test OE
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOE() throws Exception {

        assertEquals(1920, reader.mapCharCodeToWidth("OE", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOacute() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Oacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxObreve() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Obreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflex() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Ocircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Odblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdblacute() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Odblacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdieresis() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Odieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOgrave() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Ograve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhm() throws Exception {

        assertEquals(1515, reader.mapCharCodeToWidth("Ohm", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmacron() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Omacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Omega
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmega() throws Exception {

        assertEquals(1588, reader.mapCharCodeToWidth("Omega", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Omegatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmegatonos() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Omegatonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Omicron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmicron() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Omicron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Omicrontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmicrontonos() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Omicrontonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslash() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Oslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslashacute() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Oslashacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOtilde() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Otilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test P
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxP() throws Exception {

        assertEquals(1152, reader.mapCharCodeToWidth("P", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Phi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPhi() throws Exception {

        assertEquals(1654, reader.mapCharCodeToWidth("Phi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Pi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPi() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("Pi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Psi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPsi() throws Exception {

        assertEquals(1549, reader.mapCharCodeToWidth("Psi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Q
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxQ() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("Q", 0, (short) 3,
            (short) 1));
    }

    /**
     * test R
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR() throws Exception {

        assertEquals(1280, reader.mapCharCodeToWidth("R", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRacute() throws Exception {

        assertEquals(1280, reader.mapCharCodeToWidth("Racute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcaron() throws Exception {

        assertEquals(1280, reader.mapCharCodeToWidth("Rcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcedilla() throws Exception {

        assertEquals(1280, reader.mapCharCodeToWidth("Rcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Rho
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRho() throws Exception {

        assertEquals(1152, reader.mapCharCodeToWidth("Rho", 0, (short) 3,
            (short) 1));
    }

    /**
     * test S
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxS() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("S", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF010000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF010000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF010000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF020000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF020000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF020000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF030000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF030000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF030000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF040000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF040000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF040000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF050000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF050000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF050000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF060000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF060000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF060000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF070000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF070000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF070000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF080000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF080000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF080000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF090000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF090000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF090000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF100000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF100000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF100000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF110000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF110000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF110000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF190000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF190000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF190000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF200000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF200000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF200000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF210000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF210000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF210000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF220000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF220000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF220000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF230000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF230000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF230000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF240000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF240000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF240000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF250000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF250000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF250000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF260000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF260000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF260000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF270000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF270000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF270000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF280000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF280000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF280000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF360000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF360000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF360000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF370000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF370000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF370000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF380000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF380000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF380000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF390000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF390000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF390000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF400000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF400000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF400000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF410000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF410000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF410000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF420000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF420000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF420000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF430000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF430000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF430000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF440000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF440000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF440000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF450000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF450000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF450000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF460000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF460000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF460000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF470000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF470000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF470000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF480000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF480000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF480000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF490000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF490000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF490000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF500000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF500000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF500000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF510000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF510000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF510000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF520000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF520000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF520000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF530000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF530000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF530000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test SF540000
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSF540000() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("SF540000", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSacute() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("Sacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScaron() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("Scaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScedilla() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("Scedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScircumflex() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("Scircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Sigma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSigma() throws Exception {

        assertEquals(1308, reader.mapCharCodeToWidth("Sigma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test T
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxT() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("T", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Tau
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTau() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("Tau", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Tbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTbar() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("Tbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTcaron() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("Tcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTcedilla() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("Tcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Theta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTheta() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("Theta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxThorn() throws Exception {

        assertEquals(1152, reader.mapCharCodeToWidth("Thorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test U
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxU() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("U", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUacute() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Uacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUbreve() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Ubreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUcircumflex() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Ucircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Udblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdblacute() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Udblacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdieresis() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Udieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUgrave() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Ugrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUmacron() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Umacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUogonek() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Uogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Upsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUpsilon() throws Exception {

        assertEquals(1186, reader.mapCharCodeToWidth("Upsilon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Upsilondieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUpsilondieresis() throws Exception {

        assertEquals(1186, reader.mapCharCodeToWidth("Upsilondieresis", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Upsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUpsilontonos() throws Exception {

        assertEquals(1373, reader.mapCharCodeToWidth("Upsilontonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUring() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Uring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUtilde() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("Utilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test V
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxV() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("V", 0, (short) 3,
            (short) 1));
    }

    /**
     * test W
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxW() throws Exception {

        assertEquals(1813, reader.mapCharCodeToWidth("W", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWacute() throws Exception {

        assertEquals(1813, reader.mapCharCodeToWidth("Wacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWcircumflex() throws Exception {

        assertEquals(1813, reader.mapCharCodeToWidth("Wcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWdieresis() throws Exception {

        assertEquals(1813, reader.mapCharCodeToWidth("Wdieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWgrave() throws Exception {

        assertEquals(1813, reader.mapCharCodeToWidth("Wgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test X
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxX() throws Exception {

        assertEquals(1429, reader.mapCharCodeToWidth("X", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Xi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxXi() throws Exception {

        assertEquals(1390, reader.mapCharCodeToWidth("Xi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Y
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxY() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Y", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYacute() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Yacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYcircumflex() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Ycircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYdieresis() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Ydieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYgrave() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Ygrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Z
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZ() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Z", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZacute() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Zacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZcaron() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Zcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZdot() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Zdot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zeta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZeta() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("Zeta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test a
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxa() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("a", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaacute() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("aacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabreve() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("abreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflex() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("acircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacute() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("acute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadieresis() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("adieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ae
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxae() throws Exception {

        assertEquals(1195, reader.mapCharCodeToWidth("ae", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aeacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaeacute() throws Exception {

        assertEquals(1195, reader.mapCharCodeToWidth("aeacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii00208
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii00208() throws Exception {

        assertEquals(1536, reader.mapCharCodeToWidth("afii00208", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii08941
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii08941() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("afii08941", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10017
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10017() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("afii10017", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10018
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10018() throws Exception {

        assertEquals(1275, reader.mapCharCodeToWidth("afii10018", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10019
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10019() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("afii10019", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10020
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10020() throws Exception {

        assertEquals(1105, reader.mapCharCodeToWidth("afii10020", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10021
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10021() throws Exception {

        assertEquals(1340, reader.mapCharCodeToWidth("afii10021", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10022
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10022() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("afii10022", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10023
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10023() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("afii10023", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10024
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10024() throws Exception {

        assertEquals(2069, reader.mapCharCodeToWidth("afii10024", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10025
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10025() throws Exception {

        assertEquals(1103, reader.mapCharCodeToWidth("afii10025", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10026
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10026() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("afii10026", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10027
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10027() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("afii10027", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10028
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10028() throws Exception {

        assertEquals(1351, reader.mapCharCodeToWidth("afii10028", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10029
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10029() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("afii10029", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10030
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10030() throws Exception {

        assertEquals(1707, reader.mapCharCodeToWidth("afii10030", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10031
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10031() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("afii10031", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10032
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10032() throws Exception {

        assertEquals(1600, reader.mapCharCodeToWidth("afii10032", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10033
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10033() throws Exception {

        assertEquals(1538, reader.mapCharCodeToWidth("afii10033", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10034
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10034() throws Exception {

        assertEquals(1152, reader.mapCharCodeToWidth("afii10034", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10035
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10035() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("afii10035", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10036
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10036() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("afii10036", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10037
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10037() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("afii10037", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10038
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10038() throws Exception {

        assertEquals(1508, reader.mapCharCodeToWidth("afii10038", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10039
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10039() throws Exception {

        assertEquals(1429, reader.mapCharCodeToWidth("afii10039", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10040
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10040() throws Exception {

        assertEquals(1543, reader.mapCharCodeToWidth("afii10040", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10041
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10041() throws Exception {

        assertEquals(1355, reader.mapCharCodeToWidth("afii10041", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10042
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10042() throws Exception {

        assertEquals(2148, reader.mapCharCodeToWidth("afii10042", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10043
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10043() throws Exception {

        assertEquals(2115, reader.mapCharCodeToWidth("afii10043", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10044
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10044() throws Exception {

        assertEquals(1570, reader.mapCharCodeToWidth("afii10044", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10045
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10045() throws Exception {

        assertEquals(1859, reader.mapCharCodeToWidth("afii10045", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10046
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10046() throws Exception {

        assertEquals(1275, reader.mapCharCodeToWidth("afii10046", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10047
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10047() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("afii10047", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10048
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10048() throws Exception {

        assertEquals(2275, reader.mapCharCodeToWidth("afii10048", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10049
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10049() throws Exception {

        assertEquals(1280, reader.mapCharCodeToWidth("afii10049", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10050
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10050() throws Exception {

        assertEquals(1004, reader.mapCharCodeToWidth("afii10050", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10051
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10051() throws Exception {

        assertEquals(1547, reader.mapCharCodeToWidth("afii10051", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10052
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10052() throws Exception {

        assertEquals(1105, reader.mapCharCodeToWidth("afii10052", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10053
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10053() throws Exception {

        assertEquals(1301, reader.mapCharCodeToWidth("afii10053", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10054
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10054() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("afii10054", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10055
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10055() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("afii10055", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10056
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10056() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("afii10056", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10057
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10057() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("afii10057", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10058
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10058() throws Exception {

        assertEquals(1962, reader.mapCharCodeToWidth("afii10058", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10059
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10059() throws Exception {

        assertEquals(2023, reader.mapCharCodeToWidth("afii10059", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10060
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10060() throws Exception {

        assertEquals(1549, reader.mapCharCodeToWidth("afii10060", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10061
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10061() throws Exception {

        assertEquals(1351, reader.mapCharCodeToWidth("afii10061", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10062
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10062() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("afii10062", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10065
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10065() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("afii10065", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10066
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10066() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("afii10066", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10067
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10067() throws Exception {

        assertEquals(876, reader.mapCharCodeToWidth("afii10067", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10068
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10068() throws Exception {

        assertEquals(729, reader.mapCharCodeToWidth("afii10068", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10069
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10069() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("afii10069", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10070
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10070() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("afii10070", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10071
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10071() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("afii10071", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10072
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10072() throws Exception {

        assertEquals(1287, reader.mapCharCodeToWidth("afii10072", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10073
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10073() throws Exception {

        assertEquals(789, reader.mapCharCodeToWidth("afii10073", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10074
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10074() throws Exception {

        assertEquals(1112, reader.mapCharCodeToWidth("afii10074", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10075
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10075() throws Exception {

        assertEquals(1112, reader.mapCharCodeToWidth("afii10075", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10076
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10076() throws Exception {

        assertEquals(888, reader.mapCharCodeToWidth("afii10076", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10077
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10077() throws Exception {

        assertEquals(1009, reader.mapCharCodeToWidth("afii10077", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10078
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10078() throws Exception {

        assertEquals(1205, reader.mapCharCodeToWidth("afii10078", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10079
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10079() throws Exception {

        assertEquals(1112, reader.mapCharCodeToWidth("afii10079", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10080
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10080() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("afii10080", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10081
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10081() throws Exception {

        assertEquals(1112, reader.mapCharCodeToWidth("afii10081", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10082
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10082() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("afii10082", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10083
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10083() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("afii10083", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10084
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10084() throws Exception {

        assertEquals(841, reader.mapCharCodeToWidth("afii10084", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10085
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10085() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("afii10085", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10086
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10086() throws Exception {

        assertEquals(1564, reader.mapCharCodeToWidth("afii10086", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10087
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10087() throws Exception {

        assertEquals(939, reader.mapCharCodeToWidth("afii10087", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10088
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10088() throws Exception {

        assertEquals(1112, reader.mapCharCodeToWidth("afii10088", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10089
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10089() throws Exception {

        assertEquals(1044, reader.mapCharCodeToWidth("afii10089", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10090
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10090() throws Exception {

        assertEquals(1626, reader.mapCharCodeToWidth("afii10090", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10091
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10091() throws Exception {

        assertEquals(1626, reader.mapCharCodeToWidth("afii10091", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10092
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10092() throws Exception {

        assertEquals(1070, reader.mapCharCodeToWidth("afii10092", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10093
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10093() throws Exception {

        assertEquals(1278, reader.mapCharCodeToWidth("afii10093", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10094
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10094() throws Exception {

        assertEquals(888, reader.mapCharCodeToWidth("afii10094", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10095
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10095() throws Exception {

        assertEquals(829, reader.mapCharCodeToWidth("afii10095", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10096
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10096() throws Exception {

        assertEquals(1595, reader.mapCharCodeToWidth("afii10096", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10097
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10097() throws Exception {

        assertEquals(843, reader.mapCharCodeToWidth("afii10097", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10098
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10098() throws Exception {

        assertEquals(711, reader.mapCharCodeToWidth("afii10098", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10099
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10099() throws Exception {

        assertEquals(1021, reader.mapCharCodeToWidth("afii10099", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10100
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10100() throws Exception {

        assertEquals(729, reader.mapCharCodeToWidth("afii10100", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10101
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10101() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("afii10101", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10102
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10102() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("afii10102", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10103
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10103() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("afii10103", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10104
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10104() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("afii10104", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10105
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10105() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("afii10105", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10106
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10106() throws Exception {

        assertEquals(1397, reader.mapCharCodeToWidth("afii10106", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10107
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10107() throws Exception {

        assertEquals(1454, reader.mapCharCodeToWidth("afii10107", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10108
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10108() throws Exception {

        assertEquals(1036, reader.mapCharCodeToWidth("afii10108", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10109
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10109() throws Exception {

        assertEquals(888, reader.mapCharCodeToWidth("afii10109", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10110
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10110() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("afii10110", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10145
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10145() throws Exception {

        assertEquals(1538, reader.mapCharCodeToWidth("afii10145", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii10193
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii10193() throws Exception {

        assertEquals(1112, reader.mapCharCodeToWidth("afii10193", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii61248
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii61248() throws Exception {

        assertEquals(1625, reader.mapCharCodeToWidth("afii61248", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii61289
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii61289() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("afii61289", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii61352
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii61352() throws Exception {

        assertEquals(2103, reader.mapCharCodeToWidth("afii61352", 0, (short) 3,
            (short) 1));
    }

    /**
     * test agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxagrave() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("agrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test alpha
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxalpha() throws Exception {

        assertEquals(989, reader.mapCharCodeToWidth("alpha", 0, (short) 3,
            (short) 1));
    }

    /**
     * test alphatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxalphatonos() throws Exception {

        assertEquals(989, reader.mapCharCodeToWidth("alphatonos", 0, (short) 3,
            (short) 1));
    }

    /**
     * test amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxamacron() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("amacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ampersand
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxampersand() throws Exception {

        assertEquals(1493, reader.mapCharCodeToWidth("ampersand", 0, (short) 3,
            (short) 1));
    }

    /**
     * test anoteleia
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxanoteleia() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("anoteleia", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaogonek() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("aogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test approxequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxapproxequal() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("approxequal", 0,
            (short) 3, (short) 1));
    }

    /**
     * test aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaring() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("aring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaringacute() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("aringacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test arrowboth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowboth() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("arrowboth", 0, (short) 3,
            (short) 1));
    }

    /**
     * test arrowdown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowdown() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("arrowdown", 0, (short) 3,
            (short) 1));
    }

    /**
     * test arrowleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowleft() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("arrowleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test arrowright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowright() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("arrowright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test arrowup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowup() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("arrowup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test arrowupdn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowupdn() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("arrowupdn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test arrowupdnbse
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxarrowupdnbse() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("arrowupdnbse", 0,
            (short) 3, (short) 1));
    }

    /**
     * test asciicircum
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasciicircum() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("asciicircum", 0,
            (short) 3, (short) 1));
    }

    /**
     * test asciitilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasciitilde() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("asciitilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test asterisk
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasterisk() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("asterisk", 0, (short) 3,
            (short) 1));
    }

    /**
     * test at
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxat() throws Exception {

        assertEquals(1877, reader.mapCharCodeToWidth("at", 0, (short) 3,
            (short) 1));
    }

    /**
     * test atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxatilde() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("atilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test b
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxb() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("b", 0, (short) 3,
            (short) 1));
    }

    /**
     * test backslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbackslash() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("backslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbar() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("bar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test beta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbeta() throws Exception {

        assertEquals(1006, reader.mapCharCodeToWidth("beta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test block
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxblock() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("block", 0, (short) 3,
            (short) 1));
    }

    /**
     * test braceleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbraceleft() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("braceleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test braceright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbraceright() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("braceright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbracketleft() throws Exception {

        assertEquals(555, reader.mapCharCodeToWidth("bracketleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test bracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbracketright() throws Exception {

        assertEquals(555, reader.mapCharCodeToWidth("bracketright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test breve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbreve() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("breve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test brokenbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrokenbar() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("brokenbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbullet() throws Exception {

        assertEquals(725, reader.mapCharCodeToWidth("bullet", 0, (short) 3,
            (short) 1));
    }

    /**
     * test c
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxc() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("c", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcacute() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("cacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test caron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaron() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("caron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccaron() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ccaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccedilla() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ccedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccircumflex() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ccircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test cdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcdot() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("cdot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcedilla() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("cedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcent() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("cent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test chi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxchi() throws Exception {

        assertEquals(976, reader.mapCharCodeToWidth("chi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test circle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircle() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("circle", 0, (short) 3,
            (short) 1));
    }

    /**
     * test circumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflex() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("circumflex", 0, (short) 3,
            (short) 1));
    }

    /**
     * test club
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxclub() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("club", 0, (short) 3,
            (short) 1));
    }

    /**
     * test colon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcolon() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("colon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test comma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcomma() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("comma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test commaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcommaaccent() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("commaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test copyright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyright() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("copyright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test currency
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcurrency() throws Exception {

        assertEquals(1387, reader.mapCharCodeToWidth("currency", 0, (short) 3,
            (short) 1));
    }

    /**
     * test d
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxd() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("d", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dagger
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdagger() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("dagger", 0, (short) 3,
            (short) 1));
    }

    /**
     * test daggerdbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdaggerdbl() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("daggerdbl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdcaron() throws Exception {

        assertEquals(1323, reader.mapCharCodeToWidth("dcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test degree
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdegree() throws Exception {

        assertEquals(811, reader.mapCharCodeToWidth("degree", 0, (short) 3,
            (short) 1));
    }

    /**
     * test delta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdelta() throws Exception {

        assertEquals(985, reader.mapCharCodeToWidth("delta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test diamond
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdiamond() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("diamond", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresis() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("dieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dieresistonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresistonos() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("dieresistonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test divide
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdivide() throws Exception {

        assertEquals(1124, reader.mapCharCodeToWidth("divide", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dkshade
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdkshade() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("dkshade", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dmacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdmacron() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("dmacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dnblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdnblock() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("dnblock", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dollar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdollar() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("dollar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccent() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("dotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dotlessi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotlessi() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("dotlessi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test e
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxe() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("e", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeacute() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("eacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxebreve() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ebreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecaron() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ecaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflex() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ecircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedieresis() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("edieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test edot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedot() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("edot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxegrave() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("egrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eight
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeight() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("eight", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eightsuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeightsuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("eightsuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ellipsis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxellipsis() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("ellipsis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxemacron() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("emacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test emdash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxemdash() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("emdash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test endash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxendash() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("endash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeng() throws Exception {

        assertEquals(968, reader.mapCharCodeToWidth("eng", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeogonek() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("eogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test epsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxepsilon() throws Exception {

        assertEquals(766, reader.mapCharCodeToWidth("epsilon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test epsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxepsilontonos() throws Exception {

        assertEquals(766, reader.mapCharCodeToWidth("epsilontonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test equal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxequal() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("equal", 0, (short) 3,
            (short) 1));
    }

    /**
     * test equivalence
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxequivalence() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("equivalence", 0,
            (short) 3, (short) 1));
    }

    /**
     * test estimated
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxestimated() throws Exception {

        assertEquals(1229, reader.mapCharCodeToWidth("estimated", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeta() throws Exception {

        assertEquals(1006, reader.mapCharCodeToWidth("eta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test etatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxetatonos() throws Exception {

        assertEquals(1006, reader.mapCharCodeToWidth("etatonos", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeth() throws Exception {

        assertEquals(1067, reader.mapCharCodeToWidth("eth", 0, (short) 3,
            (short) 1));
    }

    /**
     * test exclam
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclam() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("exclam", 0, (short) 3,
            (short) 1));
    }

    /**
     * test exclamdbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclamdbl() throws Exception {

        assertEquals(811, reader.mapCharCodeToWidth("exclamdbl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test exclamdown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclamdown() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("exclamdown", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf() throws Exception {

        assertEquals(661, reader.mapCharCodeToWidth("f", 0, (short) 3,
            (short) 1));
    }

    /**
     * test female
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfemale() throws Exception {

        assertEquals(1536, reader.mapCharCodeToWidth("female", 0, (short) 3,
            (short) 1));
    }

    /**
     * test fi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfi() throws Exception {

        assertEquals(1109, reader.mapCharCodeToWidth("fi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test fi1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfi1() throws Exception {

        assertEquals(1109, reader.mapCharCodeToWidth("fi1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test filledbox
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfilledbox() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("filledbox", 0, (short) 3,
            (short) 1));
    }

    /**
     * test filledrect
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfilledrect() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("filledrect", 0,
            (short) 3, (short) 1));
    }

    /**
     * test five
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfive() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("five", 0, (short) 3,
            (short) 1));
    }

    /**
     * test fiveeighths
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfiveeighths() throws Exception {

        assertEquals(1685, reader.mapCharCodeToWidth("fiveeighths", 0,
            (short) 3, (short) 1));
    }

    /**
     * test fivesuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfivesuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("fivesuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test fl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfl() throws Exception {

        assertEquals(1109, reader.mapCharCodeToWidth("fl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test fl1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfl1() throws Exception {

        assertEquals(1109, reader.mapCharCodeToWidth("fl1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test florin
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxflorin() throws Exception {

        assertEquals(1259, reader.mapCharCodeToWidth("florin", 0, (short) 3,
            (short) 1));
    }

    /**
     * test four
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfour() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("four", 0, (short) 3,
            (short) 1));
    }

    /**
     * test foursuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfoursuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("foursuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test fraction
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfraction() throws Exception {

        assertEquals(384, reader.mapCharCodeToWidth("fraction", 0, (short) 3,
            (short) 1));
    }

    /**
     * test fraction1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfraction1() throws Exception {

        assertEquals(384, reader.mapCharCodeToWidth("fraction1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test franc
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfranc() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("franc", 0, (short) 3,
            (short) 1));
    }

    /**
     * test g
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxg() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("g", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gamma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgamma() throws Exception {

        assertEquals(845, reader.mapCharCodeToWidth("gamma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgbreve() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("gbreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcedilla() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("gcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcircumflex() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("gcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test gdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgdot() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("gdot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test germandbls
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgermandbls() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("germandbls", 0,
            (short) 3, (short) 1));
    }

    /**
     * test grave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgrave() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("grave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test greater
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgreater() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("greater", 0, (short) 3,
            (short) 1));
    }

    /**
     * test greaterequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgreaterequal() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("greaterequal", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guillemotleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguillemotleft() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("guillemotleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guillemotright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguillemotright() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("guillemotright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guilsinglleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguilsinglleft() throws Exception {

        assertEquals(405, reader.mapCharCodeToWidth("guilsinglleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guilsinglright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguilsinglright() throws Exception {

        assertEquals(405, reader.mapCharCodeToWidth("guilsinglright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test h
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxh() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("h", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhbar() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("hbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhcircumflex() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("hcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test heart
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxheart() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("heart", 0, (short) 3,
            (short) 1));
    }

    /**
     * test house
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhouse() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("house", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlaut() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("hungarumlaut", 0,
            (short) 3, (short) 1));
    }

    /**
     * test hyphen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphen() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("hyphen", 0, (short) 3,
            (short) 1));
    }

    /**
     * test i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxi() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("i", 0, (short) 3,
            (short) 1));
    }

    /**
     * test iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiacute() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("iacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxibreve() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("ibreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxicircumflex() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("icircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidieresis() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("idieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxigrave() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("igrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ij
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxij() throws Exception {

        assertEquals(981, reader.mapCharCodeToWidth("ij", 0, (short) 3,
            (short) 1));
    }

    /**
     * test imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtximacron() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("imacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test infinity
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxinfinity() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("infinity", 0, (short) 3,
            (short) 1));
    }

    /**
     * test integralbt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxintegralbt() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("integralbt", 0,
            (short) 3, (short) 1));
    }

    /**
     * test integraltp
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxintegraltp() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("integraltp", 0,
            (short) 3, (short) 1));
    }

    /**
     * test intersection
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxintersection() throws Exception {

        assertEquals(1472, reader.mapCharCodeToWidth("intersection", 0,
            (short) 3, (short) 1));
    }

    /**
     * test invbullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxinvbullet() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("invbullet", 0, (short) 3,
            (short) 1));
    }

    /**
     * test invcircle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxinvcircle() throws Exception {

        assertEquals(1237, reader.mapCharCodeToWidth("invcircle", 0, (short) 3,
            (short) 1));
    }

    /**
     * test invsmileface
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxinvsmileface() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("invsmileface", 0,
            (short) 3, (short) 1));
    }

    /**
     * test iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiogonek() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("iogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test iota
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiota() throws Exception {

        assertEquals(468, reader.mapCharCodeToWidth("iota", 0, (short) 3,
            (short) 1));
    }

    /**
     * test iotadieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiotadieresis() throws Exception {

        assertEquals(468, reader.mapCharCodeToWidth("iotadieresis", 0,
            (short) 3, (short) 1));
    }

    /**
     * test iotadieresistonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiotadieresistonos() throws Exception {

        assertEquals(468, reader.mapCharCodeToWidth("iotadieresistonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test iotatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiotatonos() throws Exception {

        assertEquals(468, reader.mapCharCodeToWidth("iotatonos", 0, (short) 3,
            (short) 1));
    }

    /**
     * test itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxitilde() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("itilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test j
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxj() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("j", 0, (short) 3,
            (short) 1));
    }

    /**
     * test jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjcircumflex() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("jcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test k
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxk() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("k", 0, (short) 3,
            (short) 1));
    }

    /**
     * test kappa
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkappa() throws Exception {

        assertEquals(924, reader.mapCharCodeToWidth("kappa", 0, (short) 3,
            (short) 1));
    }

    /**
     * test kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkcedilla() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("kcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test kgreenlandic
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkgreenlandic() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("kgreenlandic", 0,
            (short) 3, (short) 1));
    }

    /**
     * test l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("l", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlacute() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("lacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lambda
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlambda() throws Exception {

        assertEquals(863, reader.mapCharCodeToWidth("lambda", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcaron() throws Exception {

        assertEquals(768, reader.mapCharCodeToWidth("lcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcedilla() throws Exception {

        assertEquals(469, reader.mapCharCodeToWidth("lcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxldot() throws Exception {

        assertEquals(671, reader.mapCharCodeToWidth("ldot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test less
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxless() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("less", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lessequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlessequal() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("lessequal", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lfblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlfblock() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("lfblock", 0, (short) 3,
            (short) 1));
    }

    /**
     * test logicalnot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlogicalnot() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("logicalnot", 0,
            (short) 3, (short) 1));
    }

    /**
     * test longs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlongs() throws Exception {

        assertEquals(639, reader.mapCharCodeToWidth("longs", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lozenge
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlozenge() throws Exception {

        assertEquals(1012, reader.mapCharCodeToWidth("lozenge", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlslash() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("lslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ltshade
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxltshade() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("ltshade", 0, (short) 3,
            (short) 1));
    }

    /**
     * test m
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxm() throws Exception {

        assertEquals(1579, reader.mapCharCodeToWidth("m", 0, (short) 3,
            (short) 1));
    }

    /**
     * test male
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmale() throws Exception {

        assertEquals(1536, reader.mapCharCodeToWidth("male", 0, (short) 3,
            (short) 1));
    }

    /**
     * test minus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxminus() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("minus", 0, (short) 3,
            (short) 1));
    }

    /**
     * test minute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxminute() throws Exception {

        assertEquals(299, reader.mapCharCodeToWidth("minute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test mu
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmu() throws Exception {

        assertEquals(1089, reader.mapCharCodeToWidth("mu", 0, (short) 3,
            (short) 1));
    }

    /**
     * test mu1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmu1() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("mu1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test multiply
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmultiply() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("multiply", 0, (short) 3,
            (short) 1));
    }

    /**
     * test musicalnote
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmusicalnote() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("musicalnote", 0,
            (short) 3, (short) 1));
    }

    /**
     * test musicalnotedbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmusicalnotedbl() throws Exception {

        assertEquals(1536, reader.mapCharCodeToWidth("musicalnotedbl", 0,
            (short) 3, (short) 1));
    }

    /**
     * test n
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("n", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnacute() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("nacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test napostrophe
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnapostrophe() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("napostrophe", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncaron() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("ncaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncedilla() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("ncedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnine() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("nine", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nonbreakingspace
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnonbreakingspace() throws Exception {

        assertEquals(512, reader.mapCharCodeToWidth("nbspace", 0, (short) 3,
            (short) 1));
    }

    /**
     * test notequal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnotequal() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("notequal", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nsuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnsuperior() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("nsuperior", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxntilde() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("ntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nu
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnu() throws Exception {

        assertEquals(774, reader.mapCharCodeToWidth("nu", 0, (short) 3,
            (short) 1));
    }

    /**
     * test numbersign
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnumbersign() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("numbersign", 0,
            (short) 3, (short) 1));
    }

    /**
     * test o
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxo() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("o", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoacute() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("oacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxobreve() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("obreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflex() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("ocircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test odblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodblacute() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("odblacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodieresis() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("odieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oe
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoe() throws Exception {

        assertEquals(1429, reader.mapCharCodeToWidth("oe", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxogonek() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("ogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxograve() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("ograve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomacron() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("omacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test omega
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomega() throws Exception {

        assertEquals(1172, reader.mapCharCodeToWidth("omega", 0, (short) 3,
            (short) 1));
    }

    /**
     * test omegatonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomegatonos() throws Exception {

        assertEquals(1172, reader.mapCharCodeToWidth("omegatonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test omicron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomicron() throws Exception {

        assertEquals(1009, reader.mapCharCodeToWidth("omicron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test omicrontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomicrontonos() throws Exception {

        assertEquals(1009, reader.mapCharCodeToWidth("omicrontonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test one
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxone() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("one", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oneeighth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoneeighth() throws Exception {

        assertEquals(1664, reader.mapCharCodeToWidth("oneeighth", 0, (short) 3,
            (short) 1));
    }

    /**
     * test onehalf
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonehalf() throws Exception {

        assertEquals(1664, reader.mapCharCodeToWidth("onehalf", 0, (short) 3,
            (short) 1));
    }

    /**
     * test onequarter
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonequarter() throws Exception {

        assertEquals(1664, reader.mapCharCodeToWidth("onequarter", 0,
            (short) 3, (short) 1));
    }

    /**
     * test onesuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonesuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("onesuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test openbullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxopenbullet() throws Exception {

        assertEquals(727, reader.mapCharCodeToWidth("openbullet", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ordfeminine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxordfeminine() throws Exception {

        assertEquals(533, reader.mapCharCodeToWidth("ordfeminine", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ordmasculine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxordmasculine() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("ordmasculine", 0,
            (short) 3, (short) 1));
    }

    /**
     * test orthogonal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxorthogonal() throws Exception {

        assertEquals(2005, reader.mapCharCodeToWidth("orthogonal", 0,
            (short) 3, (short) 1));
    }

    /**
     * test oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslash() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("oslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslashacute() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("oslashacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxotilde() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("otilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test p
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxp() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("p", 0, (short) 3,
            (short) 1));
    }

    /**
     * test paragraph
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparagraph() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("paragraph", 0, (short) 3,
            (short) 1));
    }

    /**
     * test parenleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparenleft() throws Exception {

        assertEquals(597, reader.mapCharCodeToWidth("parenleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test parenright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparenright() throws Exception {

        assertEquals(597, reader.mapCharCodeToWidth("parenright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test partialdiff
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpartialdiff() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("partialdiff", 0,
            (short) 3, (short) 1));
    }

    /**
     * test percent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpercent() throws Exception {

        assertEquals(1685, reader.mapCharCodeToWidth("percent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test period
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperiod() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("period", 0, (short) 3,
            (short) 1));
    }

    /**
     * test peseta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpeseta() throws Exception {

        assertEquals(2069, reader.mapCharCodeToWidth("peseta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test phi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxphi() throws Exception {

        assertEquals(1007, reader.mapCharCodeToWidth("phi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test pi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpi() throws Exception {

        assertEquals(1088, reader.mapCharCodeToWidth("pi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test pi1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpi1() throws Exception {

        assertEquals(975, reader.mapCharCodeToWidth("pi1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test plus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxplus() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("plus", 0, (short) 3,
            (short) 1));
    }

    /**
     * test plusminus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxplusminus() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("plusminus", 0, (short) 3,
            (short) 1));
    }

    /**
     * test product
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxproduct() throws Exception {

        assertEquals(1685, reader.mapCharCodeToWidth("product", 0, (short) 3,
            (short) 1));
    }

    /**
     * test psi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpsi() throws Exception {

        assertEquals(1026, reader.mapCharCodeToWidth("psi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test q
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxq() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("q", 0, (short) 3,
            (short) 1));
    }

    /**
     * test question
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquestion() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("question", 0, (short) 3,
            (short) 1));
    }

    /**
     * test questiondown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquestiondown() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("questiondown", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedbl() throws Exception {

        assertEquals(832, reader.mapCharCodeToWidth("quotedbl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test quotedblbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbase() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("quotedblbase", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblleft() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("quotedblleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblright() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("quotedblright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quoteleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteleft() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("quoteleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test quotereversed
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotereversed() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("quotereversed", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quoteright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteright() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("quoteright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test quotesinglbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglbase() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("quotesinglbase", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotesingle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesingle() throws Exception {

        assertEquals(363, reader.mapCharCodeToWidth("quotesingle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test r
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("r", 0, (short) 3,
            (short) 1));
    }

    /**
     * test racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxracute() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("racute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test radical
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxradical() throws Exception {

        assertEquals(1131, reader.mapCharCodeToWidth("radical", 0, (short) 3,
            (short) 1));
    }

    /**
     * test radicalex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxradicalex() throws Exception {

        assertEquals(902, reader.mapCharCodeToWidth("radicalex", 0, (short) 3,
            (short) 1));
    }

    /**
     * test rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcaron() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("rcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcedilla() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("rcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test registered
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregistered() throws Exception {

        assertEquals(1557, reader.mapCharCodeToWidth("registered", 0,
            (short) 3, (short) 1));
    }

    /**
     * test revlogicalnot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrevlogicalnot() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("revlogicalnot", 0,
            (short) 3, (short) 1));
    }

    /**
     * test rho
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrho() throws Exception {

        assertEquals(1010, reader.mapCharCodeToWidth("rho", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxring() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("ring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test rtblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrtblock() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("rtblock", 0, (short) 3,
            (short) 1));
    }

    /**
     * test s
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxs() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("s", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsacute() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("sacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscaron() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("scaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscedilla() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("scedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscircumflex() throws Exception {

        assertEquals(747, reader.mapCharCodeToWidth("scircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test second
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsecond() throws Exception {

        assertEquals(768, reader.mapCharCodeToWidth("second", 0, (short) 3,
            (short) 1));
    }

    /**
     * test section
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsection() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("section", 0, (short) 3,
            (short) 1));
    }

    /**
     * test semicolon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsemicolon() throws Exception {

        assertEquals(448, reader.mapCharCodeToWidth("semicolon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test seven
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxseven() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("seven", 0, (short) 3,
            (short) 1));
    }

    /**
     * test seveneighths
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxseveneighths() throws Exception {

        assertEquals(1685, reader.mapCharCodeToWidth("seveneighths", 0,
            (short) 3, (short) 1));
    }

    /**
     * test sevensuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsevensuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("sevensuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test sfthyphen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsfthyphen() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("sfthyphen", 0, (short) 3,
            (short) 1));
    }

    /**
     * test shade
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxshade() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("shade", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sigma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsigma() throws Exception {

        assertEquals(977, reader.mapCharCodeToWidth("sigma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sigma1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsigma1() throws Exception {

        assertEquals(737, reader.mapCharCodeToWidth("sigma1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test six
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsix() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("six", 0, (short) 3,
            (short) 1));
    }

    /**
     * test slash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxslash() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("slash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test smileface
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsmileface() throws Exception {

        assertEquals(2048, reader.mapCharCodeToWidth("smileface", 0, (short) 3,
            (short) 1));
    }

    /**
     * test space
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace() throws Exception {

        assertEquals(512, reader.mapCharCodeToWidth("space", 0, (short) 3,
            (short) 1));
    }

    /**
     * test spade
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspade() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("spade", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sterling
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsterling() throws Exception {

        assertEquals(1173, reader.mapCharCodeToWidth("sterling", 0, (short) 3,
            (short) 1));
    }

    /**
     * test summation
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsummation() throws Exception {

        assertEquals(1460, reader.mapCharCodeToWidth("summation", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sun
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsun() throws Exception {

        assertEquals(1877, reader.mapCharCodeToWidth("sun", 0, (short) 3,
            (short) 1));
    }

    /**
     * test t
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxt() throws Exception {

        assertEquals(597, reader.mapCharCodeToWidth("t", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tau
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtau() throws Exception {

        assertEquals(762, reader.mapCharCodeToWidth("tau", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtbar() throws Exception {

        assertEquals(597, reader.mapCharCodeToWidth("tbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtcaron() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("tcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtcedilla() throws Exception {

        assertEquals(597, reader.mapCharCodeToWidth("tcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test theta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtheta() throws Exception {

        assertEquals(911, reader.mapCharCodeToWidth("theta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthorn() throws Exception {

        assertEquals(1045, reader.mapCharCodeToWidth("thorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test three
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthree() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("three", 0, (short) 3,
            (short) 1));
    }

    /**
     * test threeeighths
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreeeighths() throws Exception {

        assertEquals(1685, reader.mapCharCodeToWidth("threeeighths", 0,
            (short) 3, (short) 1));
    }

    /**
     * test threequarters
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreequarters() throws Exception {

        assertEquals(1685, reader.mapCharCodeToWidth("threequarters", 0,
            (short) 3, (short) 1));
    }

    /**
     * test threesuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreesuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("threesuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test tilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtilde() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("tilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtonos() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("tonos", 0, (short) 3,
            (short) 1));
    }

    /**
     * test trademark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtrademark() throws Exception {

        assertEquals(2005, reader.mapCharCodeToWidth("trademark", 0, (short) 3,
            (short) 1));
    }

    /**
     * test triagdn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtriagdn() throws Exception {

        assertEquals(2027, reader.mapCharCodeToWidth("triagdn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test triaglf
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtriaglf() throws Exception {

        assertEquals(2027, reader.mapCharCodeToWidth("triaglf", 0, (short) 3,
            (short) 1));
    }

    /**
     * test triagrt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtriagrt() throws Exception {

        assertEquals(2027, reader.mapCharCodeToWidth("triagrt", 0, (short) 3,
            (short) 1));
    }

    /**
     * test triagup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtriagup() throws Exception {

        assertEquals(2027, reader.mapCharCodeToWidth("triagup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test two
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwo() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("two", 0, (short) 3,
            (short) 1));
    }

    /**
     * test twosuperior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwosuperior() throws Exception {

        assertEquals(640, reader.mapCharCodeToWidth("twosuperior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test u
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxu() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("u", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuacute() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("uacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxubreve() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("ubreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxucircumflex() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("ucircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test udblacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudblacute() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("udblacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudieresis() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("udieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxugrave() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("ugrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxumacron() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("umacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test undercommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxundercommaaccent() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("undercommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test underscore
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxunderscore() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("underscore", 0,
            (short) 3, (short) 1));
    }

    /**
     * test underscoredbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxunderscoredbl() throws Exception {

        assertEquals(1024, reader.mapCharCodeToWidth("underscoredbl", 0,
            (short) 3, (short) 1));
    }

    /**
     * test uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuogonek() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("uogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test upblock
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxupblock() throws Exception {

        assertEquals(1451, reader.mapCharCodeToWidth("upblock", 0, (short) 3,
            (short) 1));
    }

    /**
     * test upsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxupsilon() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("upsilon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test upsilondieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxupsilondieresis() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("upsilondieresis", 0,
            (short) 3, (short) 1));
    }

    /**
     * test upsilondieresistonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxupsilondieresistonos() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("upsilondieresistonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test upsilontonos
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxupsilontonos() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("upsilontonos", 0,
            (short) 3, (short) 1));
    }

    /**
     * test uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuring() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("uring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxutilde() throws Exception {

        assertEquals(1003, reader.mapCharCodeToWidth("utilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test v
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxv() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("v", 0, (short) 3,
            (short) 1));
    }

    /**
     * test w
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxw() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("w", 0, (short) 3,
            (short) 1));
    }

    /**
     * test wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwacute() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("wacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwcircumflex() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("wcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwdieresis() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("wdieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwgrave() throws Exception {

        assertEquals(1365, reader.mapCharCodeToWidth("wgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test x
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxx() throws Exception {

        assertEquals(939, reader.mapCharCodeToWidth("x", 0, (short) 3,
            (short) 1));
    }

    /**
     * test xi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxxi() throws Exception {

        assertEquals(780, reader.mapCharCodeToWidth("xi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test y
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxy() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("y", 0, (short) 3,
            (short) 1));
    }

    /**
     * test yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyacute() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("yacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxycircumflex() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ycircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxydieresis() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ydieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test yen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyen() throws Exception {

        assertEquals(1344, reader.mapCharCodeToWidth("yen", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxygrave() throws Exception {

        assertEquals(853, reader.mapCharCodeToWidth("ygrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test z
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxz() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("z", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzacute() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("zacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzcaron() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("zcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zdot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzdot() throws Exception {

        assertEquals(875, reader.mapCharCodeToWidth("zdot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zero
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzero() throws Exception {

        assertEquals(960, reader.mapCharCodeToWidth("zero", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zeta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzeta() throws Exception {

        assertEquals(780, reader.mapCharCodeToWidth("zeta", 0, (short) 3,
            (short) 1));
    }

}
