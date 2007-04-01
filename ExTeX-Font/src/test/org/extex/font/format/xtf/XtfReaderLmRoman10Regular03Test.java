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
 * Tests for the <code>XtfReader</code> with opentype files.
 * 
 * The test use the data from the <code>ttx</code> output.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 5430 $
 */
public class XtfReaderLmRoman10Regular03Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderLmRoman10Regular03Test() throws IOException {

        if (reader == null) {
            reader =
                    new XtfReader(
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

    // --------------------------------------------------------------

    /**
     * test A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxA() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("A", (short) 3, (short) 1));
    }

    /**
     * test AE
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAE() throws Exception {

        assertEquals(903, reader.mapCharCodeToWidth("AE", (short) 3, (short) 1));
    }

    /**
     * test AE.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAEdup() throws Exception {

        assertEquals(903, reader.mapCharCodeToWidth("AE.dup", (short) 3,
            (short) 1));
    }

    /**
     * test AEacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAEacute() throws Exception {

        assertEquals(903, reader.mapCharCodeToWidth("AEacute", (short) 3,
            (short) 1));
    }

    /**
     * test Aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aacute", (short) 3,
            (short) 1));
    }

    /**
     * test Abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbreve() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abreve", (short) 3,
            (short) 1));
    }

    /**
     * test Abreveacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbreveacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abreveacute", (short) 3,
            (short) 1));
    }

    /**
     * test Abrevedotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevedotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevedotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test Abrevegrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevegrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevegrave", (short) 3,
            (short) 1));
    }

    /**
     * test Abrevehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevehookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevehookabove",
            (short) 3, (short) 1));
    }

    /**
     * test Abrevetilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevetilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevetilde", (short) 3,
            (short) 1));
    }

    /**
     * test Acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Acircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexacute",
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexdotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexgrave",
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflextilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflextilde",
            (short) 3, (short) 1));
    }

    /**
     * test Adblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdblgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Adblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test Adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdieresis() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Adieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Adotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Adotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Agrave", (short) 3,
            (short) 1));
    }

    /**
     * test Ahookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ahookabove", (short) 3,
            (short) 1));
    }

    /**
     * test Amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAmacron() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Amacron", (short) 3,
            (short) 1));
    }

    /**
     * test Aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAogonek() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aogonek", (short) 3,
            (short) 1));
    }

    /**
     * test Aogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAogonekacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test Aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAring() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aring", (short) 3,
            (short) 1));
    }

    /**
     * test Aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAringacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aringacute", (short) 3,
            (short) 1));
    }

    /**
     * test Atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Atilde", (short) 3,
            (short) 1));
    }

    /**
     * test B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxB() throws Exception {

        assertEquals(708, reader.mapCharCodeToWidth("B", (short) 3, (short) 1));
    }

    /**
     * test C
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxC() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("C", (short) 3, (short) 1));
    }

    /**
     * test Cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCacute() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Cacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcaron() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Ccaron", (short) 3,
            (short) 1));
    }

    /**
     * test Ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcedilla() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Ccedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcircumflex() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Ccircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Cdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCdotaccent() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Cdotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test D
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxD() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("D", (short) 3, (short) 1));
    }

    /**
     * test D_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxD_uni0323() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("D_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test Dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDcaron() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("Dcaron", (short) 3,
            (short) 1));
    }

    /**
     * test Dcroat
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDcroat() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("Dcroat", (short) 3,
            (short) 1));
    }

    /**
     * test Delta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDelta() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("Delta", (short) 3,
            (short) 1));
    }

    /**
     * test E
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxE() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("E", (short) 3, (short) 1));
    }

    /**
     * test E.reversed
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEreversed() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("E.reversed", (short) 3,
            (short) 1));
    }

    /**
     * test Eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEacute() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Eacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEbreve() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ebreve", (short) 3,
            (short) 1));
    }

    /**
     * test Ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcaron() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecaron", (short) 3,
            (short) 1));
    }

    /**
     * test Ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflex() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Ecircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexacute() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexacute",
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexdotbelow() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexdotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexgrave() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexgrave",
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexhookabove() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflextilde() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflextilde",
            (short) 3, (short) 1));
    }

    /**
     * test Edblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdblgrave() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test Edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdieresis() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Edotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdotaccent() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test Edotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdotbelow() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEgrave() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Egrave", (short) 3,
            (short) 1));
    }

    /**
     * test Ehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEhookabove() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ehookabove", (short) 3,
            (short) 1));
    }

    /**
     * test Emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEmacron() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Emacron", (short) 3,
            (short) 1));
    }

    /**
     * test Eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEng() throws Exception {

        assertEquals(750, reader
            .mapCharCodeToWidth("Eng", (short) 3, (short) 1));
    }

    /**
     * test Eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEogonek() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Eogonek", (short) 3,
            (short) 1));
    }

    /**
     * test Eogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEogonekacute() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Eogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test Eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEth() throws Exception {

        assertEquals(764, reader
            .mapCharCodeToWidth("Eth", (short) 3, (short) 1));
    }

    /**
     * test Etilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEtilde() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Etilde", (short) 3,
            (short) 1));
    }

    /**
     * test Euro
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEuro() throws Exception {

        assertEquals(627, reader.mapCharCodeToWidth("Euro", (short) 3,
            (short) 1));
    }

    /**
     * test F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxF() throws Exception {

        assertEquals(653, reader.mapCharCodeToWidth("F", (short) 3, (short) 1));
    }

    /**
     * test G
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxG() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("G", (short) 3, (short) 1));
    }

    /**
     * test Gacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGacute() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gacute", (short) 3,
            (short) 1));
    }

    /**
     * test Gamma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGamma() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Gamma", (short) 3,
            (short) 1));
    }

    /**
     * test Gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGbreve() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gbreve", (short) 3,
            (short) 1));
    }

    /**
     * test Gcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcaron() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcaron", (short) 3,
            (short) 1));
    }

    /**
     * test Gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcedilla() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcircumflex() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Gcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcommaaccent() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test Gdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGdotaccent() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gdotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test H
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("H", (short) 3, (short) 1));
    }

    /**
     * test H_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH_uni0323() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("H_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test Hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxHbar() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Hbar", (short) 3,
            (short) 1));
    }

    /**
     * test Hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxHcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Hcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test I
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxI() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("I", (short) 3, (short) 1));
    }

    /**
     * test I_J
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxI_J() throws Exception {

        assertEquals(839, reader
            .mapCharCodeToWidth("I_J", (short) 3, (short) 1));
    }

    /**
     * test Iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIacute() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Iacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIbreve() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Ibreve", (short) 3,
            (short) 1));
    }

    /**
     * test Icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIcircumflex() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Icircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Idblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdblgrave() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test Idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdieresis() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Idotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdotaccent() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test Idotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdotbelow() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIgrave() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Igrave", (short) 3,
            (short) 1));
    }

    /**
     * test Ihookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIhookabove() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Ihookabove", (short) 3,
            (short) 1));
    }

    /**
     * test Imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxImacron() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Imacron", (short) 3,
            (short) 1));
    }

    /**
     * test Iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIogonek() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Iogonek", (short) 3,
            (short) 1));
    }

    /**
     * test Iogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIogonekacute() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Iogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test Itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxItilde() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Itilde", (short) 3,
            (short) 1));
    }

    /**
     * test J
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJ() throws Exception {

        assertEquals(514, reader.mapCharCodeToWidth("J", (short) 3, (short) 1));
    }

    /**
     * test Jacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJacute() throws Exception {

        assertEquals(514, reader.mapCharCodeToWidth("Jacute", (short) 3,
            (short) 1));
    }

    /**
     * test Jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJcircumflex() throws Exception {

        assertEquals(514, reader.mapCharCodeToWidth("Jcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test K
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxK() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("K", (short) 3, (short) 1));
    }

    /**
     * test Kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxKcedilla() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Kcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Kcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxKcommaaccent() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Kcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test L
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("L", (short) 3, (short) 1));
    }

    /**
     * test L_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL_uni0323() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("L_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test L_uni0323_uni0304.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL_uni0323_uni0304cap() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("L_uni0323_uni0304.cap",
            (short) 3, (short) 1));
    }

    /**
     * test Lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLacute() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lacute", (short) 3,
            (short) 1));
    }

    /**
     * test Lambda
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLambda() throws Exception {

        assertEquals(694, reader.mapCharCodeToWidth("Lambda", (short) 3,
            (short) 1));
    }

    /**
     * test Lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcaron() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lcaron", (short) 3,
            (short) 1));
    }

    /**
     * test Lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcedilla() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Lcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcommaaccent() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test Ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLdot() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Ldot", (short) 3,
            (short) 1));
    }

    /**
     * test Lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLslash() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lslash", (short) 3,
            (short) 1));
    }

    /**
     * test M
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxM() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("M", (short) 3, (short) 1));
    }

    /**
     * test M_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxM_uni0323() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("M_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test N
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("N", (short) 3, (short) 1));
    }

    /**
     * test N_uni0307.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN_uni0307cap() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("N_uni0307.cap", (short) 3,
            (short) 1));
    }

    /**
     * test N_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN_uni0323() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("N_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test Nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Nacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcaron() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ncaron", (short) 3,
            (short) 1));
    }

    /**
     * test Ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcedilla() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ncedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Ncommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcommaaccent() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ncommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test Ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ntilde", (short) 3,
            (short) 1));
    }

    /**
     * test O
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxO() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("O", (short) 3, (short) 1));
    }

    /**
     * test OE
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOE() throws Exception {

        assertEquals(1014, reader
            .mapCharCodeToWidth("OE", (short) 3, (short) 1));
    }

    /**
     * test OE.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOEdup() throws Exception {

        assertEquals(1014, reader.mapCharCodeToWidth("OE.dup", (short) 3,
            (short) 1));
    }

    /**
     * test Oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oacute", (short) 3,
            (short) 1));
    }

    /**
     * test Obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxObreve() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Obreve", (short) 3,
            (short) 1));
    }

    /**
     * test Ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflex() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Ocircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexacute",
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexdotbelow() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexdotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexgrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexgrave",
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexhookabove() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflextilde() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflextilde",
            (short) 3, (short) 1));
    }

    /**
     * test Odblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdblgrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Odblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test Odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdieresis() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Odieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Odotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdotbelow() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Odotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOgrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ograve", (short) 3,
            (short) 1));
    }

    /**
     * test Ohookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhookabove() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohookabove", (short) 3,
            (short) 1));
    }

    /**
     * test Ohorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorn() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorn", (short) 3,
            (short) 1));
    }

    /**
     * test Ohornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhornacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohornacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ohorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorndotbelow() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorndotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Ohorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorngrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorngrave", (short) 3,
            (short) 1));
    }

    /**
     * test Ohornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhornhookabove() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohornhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test Ohorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorntilde() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorntilde", (short) 3,
            (short) 1));
    }

    /**
     * test Ohungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhungarumlaut() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohungarumlaut", (short) 3,
            (short) 1));
    }

    /**
     * test Omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmacron() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Omacron", (short) 3,
            (short) 1));
    }

    /**
     * test Omega
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmega() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Omega", (short) 3,
            (short) 1));
    }

    /**
     * test Oogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOogonek() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oogonek", (short) 3,
            (short) 1));
    }

    /**
     * test Oogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOogonekacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test Oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslash() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oslash", (short) 3,
            (short) 1));
    }

    /**
     * test Oslash.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslashdup() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oslash.dup", (short) 3,
            (short) 1));
    }

    /**
     * test Oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslashacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oslashacute", (short) 3,
            (short) 1));
    }

    /**
     * test Otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOtilde() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Otilde", (short) 3,
            (short) 1));
    }

    /**
     * test P
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxP() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("P", (short) 3, (short) 1));
    }

    /**
     * test Phi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPhi() throws Exception {

        assertEquals(722, reader
            .mapCharCodeToWidth("Phi", (short) 3, (short) 1));
    }

    /**
     * test Pi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPi() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Pi", (short) 3, (short) 1));
    }

    /**
     * test Psi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPsi() throws Exception {

        assertEquals(778, reader
            .mapCharCodeToWidth("Psi", (short) 3, (short) 1));
    }

    /**
     * test Q
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxQ() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Q", (short) 3, (short) 1));
    }

    /**
     * test R
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R", (short) 3, (short) 1));
    }

    /**
     * test R_uni0307.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR_uni0307cap() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R_uni0307.cap", (short) 3,
            (short) 1));
    }

    /**
     * test R_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR_uni0323() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test R_uni0323_uni0304.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR_uni0323_uni0304cap() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R_uni0323_uni0304.cap",
            (short) 3, (short) 1));
    }

    /**
     * test Racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRacute() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Racute", (short) 3,
            (short) 1));
    }

    /**
     * test Rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcaron() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rcaron", (short) 3,
            (short) 1));
    }

    /**
     * test Rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcedilla() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Rcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcommaaccent() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test Rdblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRdblgrave() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rdblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test S
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxS() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("S", (short) 3, (short) 1));
    }

    /**
     * test S_S
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxS_S() throws Exception {

        assertEquals(1111, reader.mapCharCodeToWidth("S_S", (short) 3,
            (short) 1));
    }

    /**
     * test Sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Sacute", (short) 3,
            (short) 1));
    }

    /**
     * test Scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScaron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Scaron", (short) 3,
            (short) 1));
    }

    /**
     * test Scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScedilla() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Scedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScircumflex() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Scircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Sigma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSigma() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Sigma", (short) 3,
            (short) 1));
    }

    /**
     * test T
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxT() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("T", (short) 3, (short) 1));
    }

    /**
     * test T_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxT_uni0323() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("T_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test Tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTcaron() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Tcaron", (short) 3,
            (short) 1));
    }

    /**
     * test Tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTcedilla() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Tcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test Theta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTheta() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Theta", (short) 3,
            (short) 1));
    }

    /**
     * test Thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxThorn() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Thorn", (short) 3,
            (short) 1));
    }

    /**
     * test U
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxU() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("U", (short) 3, (short) 1));
    }

    /**
     * test Uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUbreve() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ubreve", (short) 3,
            (short) 1));
    }

    /**
     * test Ubreveinvertedlow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUbreveinvertedlow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ubreveinvertedlow",
            (short) 3, (short) 1));
    }

    /**
     * test Ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ucircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Udblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdblgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Udblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test Udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdieresis() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Udieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Udotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Udotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ugrave", (short) 3,
            (short) 1));
    }

    /**
     * test Uhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhookabove", (short) 3,
            (short) 1));
    }

    /**
     * test Uhorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorn() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorn", (short) 3,
            (short) 1));
    }

    /**
     * test Uhornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhornacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhornacute", (short) 3,
            (short) 1));
    }

    /**
     * test Uhorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorndotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorndotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Uhorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorngrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorngrave", (short) 3,
            (short) 1));
    }

    /**
     * test Uhornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhornhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhornhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test Uhorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorntilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorntilde", (short) 3,
            (short) 1));
    }

    /**
     * test Uhungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhungarumlaut() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhungarumlaut", (short) 3,
            (short) 1));
    }

    /**
     * test Umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUmacron() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Umacron", (short) 3,
            (short) 1));
    }

    /**
     * test Uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUogonek() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uogonek", (short) 3,
            (short) 1));
    }

    /**
     * test Upsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUpsilon() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Upsilon", (short) 3,
            (short) 1));
    }

    /**
     * test Uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUring() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uring", (short) 3,
            (short) 1));
    }

    /**
     * test Utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Utilde", (short) 3,
            (short) 1));
    }

    /**
     * test V
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxV() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("V", (short) 3, (short) 1));
    }

    /**
     * test W
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxW() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("W", (short) 3, (short) 1));
    }

    /**
     * test Wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWacute() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wacute", (short) 3,
            (short) 1));
    }

    /**
     * test Wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWcircumflex() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWdieresis() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wdieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWgrave() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wgrave", (short) 3,
            (short) 1));
    }

    /**
     * test X
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxX() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("X", (short) 3, (short) 1));
    }

    /**
     * test Xi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxXi() throws Exception {

        assertEquals(667, reader.mapCharCodeToWidth("Xi", (short) 3, (short) 1));
    }

    /**
     * test Y
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxY() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Y", (short) 3, (short) 1));
    }

    /**
     * test Yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Yacute", (short) 3,
            (short) 1));
    }

    /**
     * test Ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ycircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test Ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYdieresis() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ydieresis", (short) 3,
            (short) 1));
    }

    /**
     * test Ydotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ydotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test Ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ygrave", (short) 3,
            (short) 1));
    }

    /**
     * test Yhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Yhookabove", (short) 3,
            (short) 1));
    }

    /**
     * test Ytilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ytilde", (short) 3,
            (short) 1));
    }

    /**
     * test Z
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZ() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Z", (short) 3, (short) 1));
    }

    /**
     * test Zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZacute() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Zacute", (short) 3,
            (short) 1));
    }

    /**
     * test Zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZcaron() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Zcaron", (short) 3,
            (short) 1));
    }

    /**
     * test Zdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZdotaccent() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Zdotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test a
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxa() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("a", (short) 3, (short) 1));
    }

    /**
     * test aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aacute", (short) 3,
            (short) 1));
    }

    /**
     * test abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abreve", (short) 3,
            (short) 1));
    }

    /**
     * test abreveacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabreveacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abreveacute", (short) 3,
            (short) 1));
    }

    /**
     * test abrevedotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevedotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevedotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test abrevegrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevegrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevegrave", (short) 3,
            (short) 1));
    }

    /**
     * test abrevehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevehookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevehookabove",
            (short) 3, (short) 1));
    }

    /**
     * test abrevetilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevetilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevetilde", (short) 3,
            (short) 1));
    }

    /**
     * test acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test acircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexacute",
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexdotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexdotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexgrave",
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexhookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test acircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflextilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflextilde",
            (short) 3, (short) 1));
    }

    /**
     * test acute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute", (short) 3,
            (short) 1));
    }

    /**
     * test acute.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacutecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute.cap", (short) 3,
            (short) 1));
    }

    /**
     * test acute.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacutedup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute.dup", (short) 3,
            (short) 1));
    }

    /**
     * test acute.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacutets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test adblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadblgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("adblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadieresis() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("adieresis", (short) 3,
            (short) 1));
    }

    /**
     * test adotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("adotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test ae
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxae() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("ae", (short) 3, (short) 1));
    }

    /**
     * test ae.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaedup() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("ae.dup", (short) 3,
            (short) 1));
    }

    /**
     * test aeacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaeacute() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("aeacute", (short) 3,
            (short) 1));
    }

    /**
     * test afii61352
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii61352() throws Exception {

        assertEquals(916, reader.mapCharCodeToWidth("afii61352", (short) 3,
            (short) 1));
    }

    /**
     * test agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxagrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("agrave", (short) 3,
            (short) 1));
    }

    /**
     * test ahookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxahookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ahookabove", (short) 3,
            (short) 1));
    }

    /**
     * test amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxamacron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("amacron", (short) 3,
            (short) 1));
    }

    /**
     * test ampersand
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxampersand() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("ampersand", (short) 3,
            (short) 1));
    }

    /**
     * test anglearc
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxanglearc() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("anglearc", (short) 3,
            (short) 1));
    }

    /**
     * test angleleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxangleleft() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("angleleft", (short) 3,
            (short) 1));
    }

    /**
     * test angleright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxangleright() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("angleright", (short) 3,
            (short) 1));
    }

    /**
     * test aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaogonek() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aogonek", (short) 3,
            (short) 1));
    }

    /**
     * test aogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaogonekacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaring() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aring", (short) 3,
            (short) 1));
    }

    /**
     * test aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaringacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aringacute", (short) 3,
            (short) 1));
    }

    /**
     * test asciicircum
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasciicircum() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("asciicircum", (short) 3,
            (short) 1));
    }

    /**
     * test asciitilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasciitilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("asciitilde", (short) 3,
            (short) 1));
    }

    /**
     * test asterisk
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasterisk() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("asterisk", (short) 3,
            (short) 1));
    }

    /**
     * test asterisk.math
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasteriskmath() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("asterisk.math", (short) 3,
            (short) 1));
    }

    /**
     * test at
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxat() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("at", (short) 3, (short) 1));
    }

    /**
     * test atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxatilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("atilde", (short) 3,
            (short) 1));
    }

    /**
     * test b
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxb() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("b", (short) 3, (short) 1));
    }

    /**
     * test backslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbackslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("backslash", (short) 3,
            (short) 1));
    }

    /**
     * test baht
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbaht() throws Exception {

        assertEquals(708, reader.mapCharCodeToWidth("baht", (short) 3,
            (short) 1));
    }

    /**
     * test bar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbar() throws Exception {

        assertEquals(278, reader
            .mapCharCodeToWidth("bar", (short) 3, (short) 1));
    }

    /**
     * test bigcircle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbigcircle() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("bigcircle", (short) 3,
            (short) 1));
    }

    /**
     * test blanksymbol
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxblanksymbol() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("blanksymbol", (short) 3,
            (short) 1));
    }

    /**
     * test braceleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbraceleft() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("braceleft", (short) 3,
            (short) 1));
    }

    /**
     * test braceright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbraceright() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("braceright", (short) 3,
            (short) 1));
    }

    /**
     * test bracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbracketleft() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("bracketleft", (short) 3,
            (short) 1));
    }

    /**
     * test bracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbracketright() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("bracketright", (short) 3,
            (short) 1));
    }

    /**
     * test breve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("breve", (short) 3,
            (short) 1));
    }

    /**
     * test breve.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrevecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("breve.cap", (short) 3,
            (short) 1));
    }

    /**
     * test breve.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrevets1() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("breve.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test brevelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrevelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("brevelow", (short) 3,
            (short) 1));
    }

    /**
     * test brokenbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrokenbar() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("brokenbar", (short) 3,
            (short) 1));
    }

    /**
     * test bullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbullet() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("bullet", (short) 3,
            (short) 1));
    }

    /**
     * test c
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxc() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("c", (short) 3, (short) 1));
    }

    /**
     * test cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cacute", (short) 3,
            (short) 1));
    }

    /**
     * test caron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("caron", (short) 3,
            (short) 1));
    }

    /**
     * test caron.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaroncap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("caron.cap", (short) 3,
            (short) 1));
    }

    /**
     * test caron.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaronts1() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("caron.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccaron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ccaron", (short) 3,
            (short) 1));
    }

    /**
     * test ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccedilla() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ccedilla", (short) 3,
            (short) 1));
    }

    /**
     * test ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccircumflex() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ccircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test cdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcdotaccent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cdotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test cedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcedilla() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cedilla", (short) 3,
            (short) 1));
    }

    /**
     * test cedilla.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcedilladup() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cedilla.dup", (short) 3,
            (short) 1));
    }

    /**
     * test cent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cent", (short) 3,
            (short) 1));
    }

    /**
     * test cent.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcentoldstyle() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cent.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test centigrade
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcentigrade() throws Exception {

        assertEquals(944, reader.mapCharCodeToWidth("centigrade", (short) 3,
            (short) 1));
    }

    /**
     * test circumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("circumflex", (short) 3,
            (short) 1));
    }

    /**
     * test circumflex.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflexcap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("circumflex.cap",
            (short) 3, (short) 1));
    }

    /**
     * test circumflex.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflexdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("circumflex.dup",
            (short) 3, (short) 1));
    }

    /**
     * test colon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcolon() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("colon", (short) 3,
            (short) 1));
    }

    /**
     * test colonmonetary
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcolonmonetary() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("colonmonetary", (short) 3,
            (short) 1));
    }

    /**
     * test comma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcomma() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("comma", (short) 3,
            (short) 1));
    }

    /**
     * test commaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcommaaccent() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("commaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test copyleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyleft() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("copyleft", (short) 3,
            (short) 1));
    }

    /**
     * test copyright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyright() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("copyright", (short) 3,
            (short) 1));
    }

    /**
     * test copyright.var
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyrightvar() throws Exception {

        assertEquals(659, reader.mapCharCodeToWidth("copyright.var", (short) 3,
            (short) 1));
    }

    /**
     * test currency
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcurrency() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("currency", (short) 3,
            (short) 1));
    }

    /**
     * test cwm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcwm() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("cwm", (short) 3, (short) 1));
    }

    /**
     * test cwmascender
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcwmascender() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("cwmascender", (short) 3,
            (short) 1));
    }

    /**
     * test cwmcapital
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcwmcapital() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("cwmcapital", (short) 3,
            (short) 1));
    }

    /**
     * test d
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxd() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("d", (short) 3, (short) 1));
    }

    /**
     * test d_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxd_uni0323() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("d_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test dagger
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdagger() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("dagger", (short) 3,
            (short) 1));
    }

    /**
     * test daggerdbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdaggerdbl() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("daggerdbl", (short) 3,
            (short) 1));
    }

    /**
     * test dblbracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblbracketleft() throws Exception {

        assertEquals(403, reader.mapCharCodeToWidth("dblbracketleft",
            (short) 3, (short) 1));
    }

    /**
     * test dblbracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblbracketright() throws Exception {

        assertEquals(403, reader.mapCharCodeToWidth("dblbracketright",
            (short) 3, (short) 1));
    }

    /**
     * test dblgrave.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblgravets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dblgrave.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test dblverticalbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblverticalbar() throws Exception {

        assertEquals(398, reader.mapCharCodeToWidth("dblverticalbar",
            (short) 3, (short) 1));
    }

    /**
     * test dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdcaron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("dcaron", (short) 3,
            (short) 1));
    }

    /**
     * test dcroat
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdcroat() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("dcroat", (short) 3,
            (short) 1));
    }

    /**
     * test degree
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdegree() throws Exception {

        assertEquals(375, reader.mapCharCodeToWidth("degree", (short) 3,
            (short) 1));
    }

    /**
     * test diameter
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdiameter() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("diameter", (short) 3,
            (short) 1));
    }

    /**
     * test died
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdied() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("died", (short) 3,
            (short) 1));
    }

    /**
     * test dieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresis() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis", (short) 3,
            (short) 1));
    }

    /**
     * test dieresis.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresiscap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis.cap", (short) 3,
            (short) 1));
    }

    /**
     * test dieresis.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresisdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis.dup", (short) 3,
            (short) 1));
    }

    /**
     * test dieresis.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresists1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test discount
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdiscount() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("discount", (short) 3,
            (short) 1));
    }

    /**
     * test divide
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdivide() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("divide", (short) 3,
            (short) 1));
    }

    /**
     * test divorced
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdivorced() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("divorced", (short) 3,
            (short) 1));
    }

    /**
     * test dollar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdollar() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dollar", (short) 3,
            (short) 1));
    }

    /**
     * test dollar.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdollaroldstyle() throws Exception {

        assertEquals(610, reader.mapCharCodeToWidth("dollar.oldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test dong
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdong() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("dong", (short) 3,
            (short) 1));
    }

    /**
     * test dotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccent() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test dotaccent.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccentcap() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotaccent.cap", (short) 3,
            (short) 1));
    }

    /**
     * test dotaccent.var
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccentvar() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotaccent.var", (short) 3,
            (short) 1));
    }

    /**
     * test dotlessi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotlessi() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotlessi", (short) 3,
            (short) 1));
    }

    /**
     * test e
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxe() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("e", (short) 3, (short) 1));
    }

    /**
     * test e.reversed
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxereversed() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("e.reversed", (short) 3,
            (short) 1));
    }

    /**
     * test eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("eacute", (short) 3,
            (short) 1));
    }

    /**
     * test ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxebreve() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ebreve", (short) 3,
            (short) 1));
    }

    /**
     * test ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecaron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecaron", (short) 3,
            (short) 1));
    }

    /**
     * test ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflex() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test ecircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexacute",
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexdotbelow() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexdotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexgrave() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexgrave",
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexhookabove() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflextilde() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflextilde",
            (short) 3, (short) 1));
    }

    /**
     * test edblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedblgrave() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedieresis() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edieresis", (short) 3,
            (short) 1));
    }

    /**
     * test edotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedotaccent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test edotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedotbelow() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxegrave() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("egrave", (short) 3,
            (short) 1));
    }

    /**
     * test ehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxehookabove() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ehookabove", (short) 3,
            (short) 1));
    }

    /**
     * test eight
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeight() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("eight", (short) 3,
            (short) 1));
    }

    /**
     * test eight.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeightoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("eight.oldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test eight.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeightprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("eight.prop", (short) 3,
            (short) 1));
    }

    /**
     * test eight.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeighttaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("eight.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test ellipsis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxellipsis() throws Exception {

        assertEquals(670, reader.mapCharCodeToWidth("ellipsis", (short) 3,
            (short) 1));
    }

    /**
     * test emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxemacron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("emacron", (short) 3,
            (short) 1));
    }

    /**
     * test emdash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxemdash() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("emdash", (short) 3,
            (short) 1));
    }

    /**
     * test endash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxendash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("endash", (short) 3,
            (short) 1));
    }

    /**
     * test eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeng() throws Exception {

        assertEquals(506, reader
            .mapCharCodeToWidth("eng", (short) 3, (short) 1));
    }

    /**
     * test eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeogonek() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("eogonek", (short) 3,
            (short) 1));
    }

    /**
     * test eogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeogonekacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("eogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test equal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxequal() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("equal", (short) 3,
            (short) 1));
    }

    /**
     * test estimated
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxestimated() throws Exception {

        assertEquals(676, reader.mapCharCodeToWidth("estimated", (short) 3,
            (short) 1));
    }

    /**
     * test eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeth() throws Exception {

        assertEquals(500, reader
            .mapCharCodeToWidth("eth", (short) 3, (short) 1));
    }

    /**
     * test etilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxetilde() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("etilde", (short) 3,
            (short) 1));
    }

    /**
     * test exclam
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclam() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("exclam", (short) 3,
            (short) 1));
    }

    /**
     * test exclamdown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclamdown() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("exclamdown", (short) 3,
            (short) 1));
    }

    /**
     * test f
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("f", (short) 3, (short) 1));
    }

    /**
     * test f_f
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_f() throws Exception {

        assertEquals(583, reader
            .mapCharCodeToWidth("f_f", (short) 3, (short) 1));
    }

    /**
     * test f_f_i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_f_i() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("f_f_i", (short) 3,
            (short) 1));
    }

    /**
     * test f_f_l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_f_l() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("f_f_l", (short) 3,
            (short) 1));
    }

    /**
     * test f_i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_i() throws Exception {

        assertEquals(556, reader
            .mapCharCodeToWidth("f_i", (short) 3, (short) 1));
    }

    /**
     * test f_k
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_k() throws Exception {

        assertEquals(816, reader
            .mapCharCodeToWidth("f_k", (short) 3, (short) 1));
    }

    /**
     * test f_l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_l() throws Exception {

        assertEquals(556, reader
            .mapCharCodeToWidth("f_l", (short) 3, (short) 1));
    }

    /**
     * test five
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfive() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("five", (short) 3,
            (short) 1));
    }

    /**
     * test five.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfiveoldstyle() throws Exception {

        assertEquals(547, reader.mapCharCodeToWidth("five.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test five.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfiveprop() throws Exception {

        assertEquals(547, reader.mapCharCodeToWidth("five.prop", (short) 3,
            (short) 1));
    }

    /**
     * test five.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfivetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("five.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test florin
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxflorin() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("florin", (short) 3,
            (short) 1));
    }

    /**
     * test four
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfour() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("four", (short) 3,
            (short) 1));
    }

    /**
     * test four.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfouroldstyle() throws Exception {

        assertEquals(517, reader.mapCharCodeToWidth("four.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test four.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfourprop() throws Exception {

        assertEquals(517, reader.mapCharCodeToWidth("four.prop", (short) 3,
            (short) 1));
    }

    /**
     * test four.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfourtaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("four.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test fraction
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfraction() throws Exception {

        assertEquals(551, reader.mapCharCodeToWidth("fraction", (short) 3,
            (short) 1));
    }

    /**
     * test fraction.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfractionalt() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("fraction.alt", (short) 3,
            (short) 1));
    }

    /**
     * test g
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxg() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("g", (short) 3, (short) 1));
    }

    /**
     * test gacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gacute", (short) 3,
            (short) 1));
    }

    /**
     * test gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgbreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gbreve", (short) 3,
            (short) 1));
    }

    /**
     * test gcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcaron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcaron", (short) 3,
            (short) 1));
    }

    /**
     * test gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcedilla() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test gcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcommaaccent() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test gdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgdotaccent() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gdotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test germandbls
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgermandbls() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("germandbls", (short) 3,
            (short) 1));
    }

    /**
     * test germandbls.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgermandblsdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("germandbls.dup",
            (short) 3, (short) 1));
    }

    /**
     * test gnaborretni
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgnaborretni() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("gnaborretni", (short) 3,
            (short) 1));
    }

    /**
     * test grave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("grave", (short) 3,
            (short) 1));
    }

    /**
     * test grave.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgravecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("grave.cap", (short) 3,
            (short) 1));
    }

    /**
     * test grave.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgravets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("grave.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test greater
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgreater() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("greater", (short) 3,
            (short) 1));
    }

    /**
     * test guarani
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguarani() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("guarani", (short) 3,
            (short) 1));
    }

    /**
     * test guillemotleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguillemotleft() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("guillemotleft", (short) 3,
            (short) 1));
    }

    /**
     * test guillemotright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguillemotright() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("guillemotright",
            (short) 3, (short) 1));
    }

    /**
     * test guilsinglleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguilsinglleft() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("guilsinglleft", (short) 3,
            (short) 1));
    }

    /**
     * test guilsinglright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguilsinglright() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("guilsinglright",
            (short) 3, (short) 1));
    }

    /**
     * test h
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxh() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("h", (short) 3, (short) 1));
    }

    /**
     * test h_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxh_uni0323() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("h_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhbar() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("hbar", (short) 3,
            (short) 1));
    }

    /**
     * test hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhcircumflex() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("hcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test hungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlaut() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("hungarumlaut", (short) 3,
            (short) 1));
    }

    /**
     * test hungarumlaut.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlautcap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("hungarumlaut.cap",
            (short) 3, (short) 1));
    }

    /**
     * test hungarumlaut.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlautts1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("hungarumlaut.ts1",
            (short) 3, (short) 1));
    }

    /**
     * test hyphen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphen() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphen", (short) 3,
            (short) 1));
    }

    /**
     * test hyphen.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphenalt() throws Exception {

        assertEquals(167, reader.mapCharCodeToWidth("hyphen.alt", (short) 3,
            (short) 1));
    }

    /**
     * test hyphen.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphendup() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphen.dup", (short) 3,
            (short) 1));
    }

    /**
     * test hyphen.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphenprop() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphen.prop", (short) 3,
            (short) 1));
    }

    /**
     * test hyphendbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphendbl() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphendbl", (short) 3,
            (short) 1));
    }

    /**
     * test hyphendbl.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphendblalt() throws Exception {

        assertEquals(167, reader.mapCharCodeToWidth("hyphendbl.alt", (short) 3,
            (short) 1));
    }

    /**
     * test i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxi() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("i", (short) 3, (short) 1));
    }

    /**
     * test i.TRK
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiTRK() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("i.TRK", (short) 3,
            (short) 1));
    }

    /**
     * test i_j
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxi_j() throws Exception {

        assertEquals(556, reader
            .mapCharCodeToWidth("i_j", (short) 3, (short) 1));
    }

    /**
     * test iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiacute() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("iacute", (short) 3,
            (short) 1));
    }

    /**
     * test ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxibreve() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("ibreve", (short) 3,
            (short) 1));
    }

    /**
     * test icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxicircumflex() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("icircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test idblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidblgrave() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("idblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidieresis() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("idieresis", (short) 3,
            (short) 1));
    }

    /**
     * test idotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidotbelow() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("idotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxigrave() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("igrave", (short) 3,
            (short) 1));
    }

    /**
     * test ihookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxihookabove() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("ihookabove", (short) 3,
            (short) 1));
    }

    /**
     * test imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtximacron() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("imacron", (short) 3,
            (short) 1));
    }

    /**
     * test interrobang
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxinterrobang() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("interrobang", (short) 3,
            (short) 1));
    }

    /**
     * test iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiogonek() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("iogonek", (short) 3,
            (short) 1));
    }

    /**
     * test iogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiogonekacute() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("iogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxitilde() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("itilde", (short) 3,
            (short) 1));
    }

    /**
     * test j
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxj() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("j", (short) 3, (short) 1));
    }

    /**
     * test j.dotless
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjdotless() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("j.dotless", (short) 3,
            (short) 1));
    }

    /**
     * test jacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjacute() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("jacute", (short) 3,
            (short) 1));
    }

    /**
     * test jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjcircumflex() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("jcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test k
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxk() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("k", (short) 3, (short) 1));
    }

    /**
     * test kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkcedilla() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("kcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test kcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkcommaaccent() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("kcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("l", (short) 3, (short) 1));
    }

    /**
     * test l_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl_uni0323() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("l_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test l_uni0323_uni0304
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl_uni0323_uni0304() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("l_uni0323_uni0304",
            (short) 3, (short) 1));
    }

    /**
     * test lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlacute() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lacute", (short) 3,
            (short) 1));
    }

    /**
     * test lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcaron() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lcaron", (short) 3,
            (short) 1));
    }

    /**
     * test lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcedilla() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test lcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcommaaccent() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxldot() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("ldot", (short) 3,
            (short) 1));
    }

    /**
     * test leaf
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxleaf() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("leaf", (short) 3,
            (short) 1));
    }

    /**
     * test less
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxless() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("less", (short) 3,
            (short) 1));
    }

    /**
     * test lira
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlira() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("lira", (short) 3,
            (short) 1));
    }

    /**
     * test logicalnot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlogicalnot() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("logicalnot", (short) 3,
            (short) 1));
    }

    /**
     * test longs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlongs() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("longs", (short) 3,
            (short) 1));
    }

    /**
     * test lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlslash() throws Exception {

        assertEquals(336, reader.mapCharCodeToWidth("lslash", (short) 3,
            (short) 1));
    }

    /**
     * test m
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxm() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("m", (short) 3, (short) 1));
    }

    /**
     * test m_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxm_uni0323() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("m_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test macron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("macron", (short) 3,
            (short) 1));
    }

    /**
     * test macron.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacroncap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("macron.cap", (short) 3,
            (short) 1));
    }

    /**
     * test macron.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacrondup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("macron.dup", (short) 3,
            (short) 1));
    }

    /**
     * test macron.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacronts1() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("macron.ts1", (short) 3,
            (short) 1));
    }

    /**
     * test married
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmarried() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("married", (short) 3,
            (short) 1));
    }

    /**
     * test minus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxminus() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("minus", (short) 3,
            (short) 1));
    }

    /**
     * test mu
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmu() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("mu", (short) 3, (short) 1));
    }

    /**
     * test multiply
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmultiply() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("multiply", (short) 3,
            (short) 1));
    }

    /**
     * test n
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("n", (short) 3, (short) 1));
    }

    /**
     * test n_uni0307
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn_uni0307() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("n_uni0307", (short) 3,
            (short) 1));
    }

    /**
     * test n_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn_uni0323() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("n_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("nacute", (short) 3,
            (short) 1));
    }

    /**
     * test naira
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnaira() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("naira", (short) 3,
            (short) 1));
    }

    /**
     * test ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncaron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ncaron", (short) 3,
            (short) 1));
    }

    /**
     * test ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncedilla() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ncedilla", (short) 3,
            (short) 1));
    }

    /**
     * test ncommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncommaaccent() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ncommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test nine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnine() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("nine", (short) 3,
            (short) 1));
    }

    /**
     * test nine.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnineoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("nine.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test nine.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnineprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("nine.prop", (short) 3,
            (short) 1));
    }

    /**
     * test nine.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxninetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("nine.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxntilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ntilde", (short) 3,
            (short) 1));
    }

    /**
     * test numbersign
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnumbersign() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("numbersign", (short) 3,
            (short) 1));
    }

    /**
     * test o
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxo() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("o", (short) 3, (short) 1));
    }

    /**
     * test oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oacute", (short) 3,
            (short) 1));
    }

    /**
     * test obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxobreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("obreve", (short) 3,
            (short) 1));
    }

    /**
     * test ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test ocircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexacute",
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexdotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexdotbelow",
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexgrave",
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexhookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflextilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflextilde",
            (short) 3, (short) 1));
    }

    /**
     * test odblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodblgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("odblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodieresis() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("odieresis", (short) 3,
            (short) 1));
    }

    /**
     * test odotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("odotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test oe
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoe() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("oe", (short) 3, (short) 1));
    }

    /**
     * test oe.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoedup() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("oe.dup", (short) 3,
            (short) 1));
    }

    /**
     * test ogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxogonek() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ogonek", (short) 3,
            (short) 1));
    }

    /**
     * test ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxograve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ograve", (short) 3,
            (short) 1));
    }

    /**
     * test ohm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohm() throws Exception {

        assertEquals(722, reader
            .mapCharCodeToWidth("ohm", (short) 3, (short) 1));
    }

    /**
     * test ohookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohookabove", (short) 3,
            (short) 1));
    }

    /**
     * test ohorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorn() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorn", (short) 3,
            (short) 1));
    }

    /**
     * test ohornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohornacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohornacute", (short) 3,
            (short) 1));
    }

    /**
     * test ohorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorndotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorndotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test ohorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorngrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorngrave", (short) 3,
            (short) 1));
    }

    /**
     * test ohornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohornhookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohornhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test ohorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorntilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorntilde", (short) 3,
            (short) 1));
    }

    /**
     * test ohungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohungarumlaut() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohungarumlaut", (short) 3,
            (short) 1));
    }

    /**
     * test omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomacron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("omacron", (short) 3,
            (short) 1));
    }

    /**
     * test one
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxone() throws Exception {

        assertEquals(500, reader
            .mapCharCodeToWidth("one", (short) 3, (short) 1));
    }

    /**
     * test one.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoneoldstyle() throws Exception {

        assertEquals(404, reader.mapCharCodeToWidth("one.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test one.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoneprop() throws Exception {

        assertEquals(404, reader.mapCharCodeToWidth("one.prop", (short) 3,
            (short) 1));
    }

    /**
     * test one.superior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonesuperior() throws Exception {

        assertEquals(366, reader.mapCharCodeToWidth("one.superior", (short) 3,
            (short) 1));
    }

    /**
     * test one.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("one.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test onehalf
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonehalf() throws Exception {

        assertEquals(825, reader.mapCharCodeToWidth("onehalf", (short) 3,
            (short) 1));
    }

    /**
     * test onequarter
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonequarter() throws Exception {

        assertEquals(825, reader.mapCharCodeToWidth("onequarter", (short) 3,
            (short) 1));
    }

    /**
     * test oogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoogonek() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oogonek", (short) 3,
            (short) 1));
    }

    /**
     * test oogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoogonekacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oogonekacute", (short) 3,
            (short) 1));
    }

    /**
     * test openbullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxopenbullet() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("openbullet", (short) 3,
            (short) 1));
    }

    /**
     * test ordfeminine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxordfeminine() throws Exception {

        assertEquals(449, reader.mapCharCodeToWidth("ordfeminine", (short) 3,
            (short) 1));
    }

    /**
     * test ordmasculine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxordmasculine() throws Exception {

        assertEquals(419, reader.mapCharCodeToWidth("ordmasculine", (short) 3,
            (short) 1));
    }

    /**
     * test oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oslash", (short) 3,
            (short) 1));
    }

    /**
     * test oslash.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslashdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oslash.dup", (short) 3,
            (short) 1));
    }

    /**
     * test oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslashacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oslashacute", (short) 3,
            (short) 1));
    }

    /**
     * test otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxotilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("otilde", (short) 3,
            (short) 1));
    }

    /**
     * test p
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxp() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("p", (short) 3, (short) 1));
    }

    /**
     * test paragraph
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparagraph() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("paragraph", (short) 3,
            (short) 1));
    }

    /**
     * test paragraph.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparagraphalt() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("paragraph.alt", (short) 3,
            (short) 1));
    }

    /**
     * test parenleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparenleft() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("parenleft", (short) 3,
            (short) 1));
    }

    /**
     * test parenright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparenright() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("parenright", (short) 3,
            (short) 1));
    }

    /**
     * test percent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpercent() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("percent", (short) 3,
            (short) 1));
    }

    /**
     * test period
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperiod() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("period", (short) 3,
            (short) 1));
    }

    /**
     * test periodcentered
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperiodcentered() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("periodcentered",
            (short) 3, (short) 1));
    }

    /**
     * test permyriad
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpermyriad() throws Exception {

        assertEquals(1457, reader.mapCharCodeToWidth("permyriad", (short) 3,
            (short) 1));
    }

    /**
     * test perthousand
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperthousand() throws Exception {

        assertEquals(1140, reader.mapCharCodeToWidth("perthousand", (short) 3,
            (short) 1));
    }

    /**
     * test perthousandzero
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperthousandzero() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("perthousandzero",
            (short) 3, (short) 1));
    }

    /**
     * test peso
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpeso() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("peso", (short) 3,
            (short) 1));
    }

    /**
     * test plus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxplus() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("plus", (short) 3,
            (short) 1));
    }

    /**
     * test plusminus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxplusminus() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("plusminus", (short) 3,
            (short) 1));
    }

    /**
     * test published
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpublished() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("published", (short) 3,
            (short) 1));
    }

    /**
     * test q
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxq() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("q", (short) 3, (short) 1));
    }

    /**
     * test question
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquestion() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("question", (short) 3,
            (short) 1));
    }

    /**
     * test questiondown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquestiondown() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("questiondown", (short) 3,
            (short) 1));
    }

    /**
     * test quillbracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquillbracketleft() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("quillbracketleft",
            (short) 3, (short) 1));
    }

    /**
     * test quillbracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquillbracketright() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("quillbracketright",
            (short) 3, (short) 1));
    }

    /**
     * test quotedbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedbl() throws Exception {

        assertEquals(374, reader.mapCharCodeToWidth("quotedbl", (short) 3,
            (short) 1));
    }

    /**
     * test quotedblbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbase() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("quotedblbase", (short) 3,
            (short) 1));
    }

    /**
     * test quotedblbase.cm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbasecm() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblbase.cm",
            (short) 3, (short) 1));
    }

    /**
     * test quotedblbase.cs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbasecs() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblbase.cs",
            (short) 3, (short) 1));
    }

    /**
     * test quotedblbase.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbasets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblbase.ts1",
            (short) 3, (short) 1));
    }

    /**
     * test quotedblleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblleft() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("quotedblleft", (short) 3,
            (short) 1));
    }

    /**
     * test quotedblleft.cm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblleftcm() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblleft.cm",
            (short) 3, (short) 1));
    }

    /**
     * test quotedblright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblright() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("quotedblright", (short) 3,
            (short) 1));
    }

    /**
     * test quotedblright.cm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblrightcm() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblright.cm",
            (short) 3, (short) 1));
    }

    /**
     * test quotedblright.cs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblrightcs() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblright.cs",
            (short) 3, (short) 1));
    }

    /**
     * test quoteleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteleft() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteleft", (short) 3,
            (short) 1));
    }

    /**
     * test quoteleft.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteleftdup() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteleft.dup", (short) 3,
            (short) 1));
    }

    /**
     * test quoteright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteright() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteright", (short) 3,
            (short) 1));
    }

    /**
     * test quoteright.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoterightdup() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteright.dup",
            (short) 3, (short) 1));
    }

    /**
     * test quotesinglbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglbase() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quotesinglbase",
            (short) 3, (short) 1));
    }

    /**
     * test quotesinglbase.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglbasets1() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("quotesinglbase.ts1",
            (short) 3, (short) 1));
    }

    /**
     * test quotesingle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesingle() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quotesingle", (short) 3,
            (short) 1));
    }

    /**
     * test quotesingle.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglets1() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quotesingle.ts1",
            (short) 3, (short) 1));
    }

    /**
     * test r
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r", (short) 3, (short) 1));
    }

    /**
     * test r_uni0307
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr_uni0307() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r_uni0307", (short) 3,
            (short) 1));
    }

    /**
     * test r_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr_uni0323() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test r_uni0323_uni0304
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr_uni0323_uni0304() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r_uni0323_uni0304",
            (short) 3, (short) 1));
    }

    /**
     * test racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxracute() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("racute", (short) 3,
            (short) 1));
    }

    /**
     * test radical
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxradical() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("radical", (short) 3,
            (short) 1));
    }

    /**
     * test rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcaron() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rcaron", (short) 3,
            (short) 1));
    }

    /**
     * test rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcedilla() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test rcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcommaaccent() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rcommaaccent", (short) 3,
            (short) 1));
    }

    /**
     * test rdblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrdblgrave() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rdblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test recipe
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrecipe() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("recipe", (short) 3,
            (short) 1));
    }

    /**
     * test referencemark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxreferencemark() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("referencemark", (short) 3,
            (short) 1));
    }

    /**
     * test registered
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregistered() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("registered", (short) 3,
            (short) 1));
    }

    /**
     * test registered.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregisteredalt() throws Exception {

        assertEquals(470, reader.mapCharCodeToWidth("registered.alt",
            (short) 3, (short) 1));
    }

    /**
     * test registered.var
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregisteredvar() throws Exception {

        assertEquals(659, reader.mapCharCodeToWidth("registered.var",
            (short) 3, (short) 1));
    }

    /**
     * test ring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxring() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("ring", (short) 3,
            (short) 1));
    }

    /**
     * test ring.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxringcap() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("ring.cap", (short) 3,
            (short) 1));
    }

    /**
     * test s
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxs() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("s", (short) 3, (short) 1));
    }

    /**
     * test sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsacute() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("sacute", (short) 3,
            (short) 1));
    }

    /**
     * test scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscaron() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("scaron", (short) 3,
            (short) 1));
    }

    /**
     * test scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscedilla() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("scedilla", (short) 3,
            (short) 1));
    }

    /**
     * test scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscircumflex() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("scircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test section
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsection() throws Exception {

        assertEquals(484, reader.mapCharCodeToWidth("section", (short) 3,
            (short) 1));
    }

    /**
     * test semicolon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsemicolon() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("semicolon", (short) 3,
            (short) 1));
    }

    /**
     * test servicemark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxservicemark() throws Exception {

        assertEquals(883, reader.mapCharCodeToWidth("servicemark", (short) 3,
            (short) 1));
    }

    /**
     * test seven
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxseven() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("seven", (short) 3,
            (short) 1));
    }

    /**
     * test seven.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsevenoldstyle() throws Exception {

        assertEquals(503, reader.mapCharCodeToWidth("seven.oldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test seven.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsevenprop() throws Exception {

        assertEquals(503, reader.mapCharCodeToWidth("seven.prop", (short) 3,
            (short) 1));
    }

    /**
     * test seven.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxseventaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("seven.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test six
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsix() throws Exception {

        assertEquals(500, reader
            .mapCharCodeToWidth("six", (short) 3, (short) 1));
    }

    /**
     * test six.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsixoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("six.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test six.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsixprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("six.prop", (short) 3,
            (short) 1));
    }

    /**
     * test six.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsixtaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("six.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test slash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("slash", (short) 3,
            (short) 1));
    }

    /**
     * test space
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("space", (short) 3,
            (short) 1));
    }

    /**
     * test space.visible
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspacevisible() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space.visible", (short) 3,
            (short) 1));
    }

    /**
     * test space_uni0302_uni0300
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0300() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0300",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0300.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0300cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0300.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0301() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0301",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0301cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0301.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0303
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0303() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0303",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0303.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0303cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0303.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0309() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0309",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0309cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0309.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0300
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0300() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0300",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0300.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0300cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0300.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0301() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0301",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0301cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0301.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0303
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0303() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0303",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0303.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0303cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0303.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0309() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0309",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0309cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0309.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0309() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0309", (short) 3,
            (short) 1));
    }

    /**
     * test space_uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0309cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0309.cap",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni030A_uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030A_uni0301() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("space_uni030A_uni0301",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni030A_uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030A_uni0301cap() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth(
            "space_uni030A_uni0301.cap", (short) 3, (short) 1));
    }

    /**
     * test space_uni030F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030F() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni030F", (short) 3,
            (short) 1));
    }

    /**
     * test space_uni030F.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030Fcap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni030F.cap",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0311
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0311() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0311", (short) 3,
            (short) 1));
    }

    /**
     * test space_uni0311.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0311cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0311.cap",
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0323() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("space_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test space_uni032F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni032F() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni032F", (short) 3,
            (short) 1));
    }

    /**
     * test space_uni0330
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0330() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("space_uni0330", (short) 3,
            (short) 1));
    }

    /**
     * test star.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxstaralt() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("star.alt", (short) 3,
            (short) 1));
    }

    /**
     * test sterling
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsterling() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("sterling", (short) 3,
            (short) 1));
    }

    /**
     * test suppress
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsuppress() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("suppress", (short) 3,
            (short) 1));
    }

    /**
     * test t
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxt() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("t", (short) 3, (short) 1));
    }

    /**
     * test t_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxt_uni0323() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("t_uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtcaron() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("tcaron", (short) 3,
            (short) 1));
    }

    /**
     * test tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtcedilla() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("tcedilla", (short) 3,
            (short) 1));
    }

    /**
     * test thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthorn() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("thorn", (short) 3,
            (short) 1));
    }

    /**
     * test three
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthree() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("three", (short) 3,
            (short) 1));
    }

    /**
     * test three.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreeoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("three.oldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test three.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreeprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("three.prop", (short) 3,
            (short) 1));
    }

    /**
     * test three.superior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreesuperior() throws Exception {

        assertEquals(359, reader.mapCharCodeToWidth("three.superior",
            (short) 3, (short) 1));
    }

    /**
     * test three.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("three.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test threequarters
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreequarters() throws Exception {

        assertEquals(825, reader.mapCharCodeToWidth("threequarters", (short) 3,
            (short) 1));
    }

    /**
     * test tieaccentcapital
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentcapital() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("tieaccentcapital",
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentcapital.new
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentcapitalnew() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tieaccentcapital.new",
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentlowercase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentlowercase() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("tieaccentlowercase",
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentlowercase.new
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentlowercasenew() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tieaccentlowercase.new",
            (short) 3, (short) 1));
    }

    /**
     * test tilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tilde", (short) 3,
            (short) 1));
    }

    /**
     * test tilde.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtildecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tilde.cap", (short) 3,
            (short) 1));
    }

    /**
     * test tilde.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtildedup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tilde.dup", (short) 3,
            (short) 1));
    }

    /**
     * test trademark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtrademark() throws Exception {

        assertEquals(983, reader.mapCharCodeToWidth("trademark", (short) 3,
            (short) 1));
    }

    /**
     * test two
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwo() throws Exception {

        assertEquals(500, reader
            .mapCharCodeToWidth("two", (short) 3, (short) 1));
    }

    /**
     * test two.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwooldstyle() throws Exception {

        assertEquals(554, reader.mapCharCodeToWidth("two.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test two.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwoprop() throws Exception {

        assertEquals(547, reader.mapCharCodeToWidth("two.prop", (short) 3,
            (short) 1));
    }

    /**
     * test two.superior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwosuperior() throws Exception {

        assertEquals(359, reader.mapCharCodeToWidth("two.superior", (short) 3,
            (short) 1));
    }

    /**
     * test two.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwotaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("two.taboldstyle",
            (short) 3, (short) 1));
    }

    /**
     * test u
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxu() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("u", (short) 3, (short) 1));
    }

    /**
     * test uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uacute", (short) 3,
            (short) 1));
    }

    /**
     * test ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxubreve() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ubreve", (short) 3,
            (short) 1));
    }

    /**
     * test ubreveinvertedlow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxubreveinvertedlow() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ubreveinvertedlow",
            (short) 3, (short) 1));
    }

    /**
     * test ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxucircumflex() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ucircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test udblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudblgrave() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("udblgrave", (short) 3,
            (short) 1));
    }

    /**
     * test udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudieresis() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("udieresis", (short) 3,
            (short) 1));
    }

    /**
     * test udotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudotbelow() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("udotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxugrave() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ugrave", (short) 3,
            (short) 1));
    }

    /**
     * test uhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhookabove() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhookabove", (short) 3,
            (short) 1));
    }

    /**
     * test uhorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorn() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorn", (short) 3,
            (short) 1));
    }

    /**
     * test uhornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhornacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhornacute", (short) 3,
            (short) 1));
    }

    /**
     * test uhorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorndotbelow() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorndotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test uhorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorngrave() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorngrave", (short) 3,
            (short) 1));
    }

    /**
     * test uhornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhornhookabove() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhornhookabove",
            (short) 3, (short) 1));
    }

    /**
     * test uhorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorntilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorntilde", (short) 3,
            (short) 1));
    }

    /**
     * test uhungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhungarumlaut() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhungarumlaut", (short) 3,
            (short) 1));
    }

    /**
     * test umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxumacron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("umacron", (short) 3,
            (short) 1));
    }

    /**
     * test underscore
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxunderscore() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("underscore", (short) 3,
            (short) 1));
    }

    /**
     * test uni00A0
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni00A0() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("uni00A0", (short) 3,
            (short) 1));
    }

    /**
     * test uni00AD
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni00AD() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("uni00AD", (short) 3,
            (short) 1));
    }

    /**
     * test uni0218
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0218() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uni0218", (short) 3,
            (short) 1));
    }

    /**
     * test uni0219
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0219() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("uni0219", (short) 3,
            (short) 1));
    }

    /**
     * test uni021A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni021A() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("uni021A", (short) 3,
            (short) 1));
    }

    /**
     * test uni021B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni021B() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("uni021B", (short) 3,
            (short) 1));
    }

    /**
     * test uni0300
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0300() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0300", (short) 3,
            (short) 1));
    }

    /**
     * test uni0300.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0300cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0300.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0301() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0301", (short) 3,
            (short) 1));
    }

    /**
     * test uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0301cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0301.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0302
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0302() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0302", (short) 3,
            (short) 1));
    }

    /**
     * test uni0302.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0302cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0302.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0303
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0303() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0303", (short) 3,
            (short) 1));
    }

    /**
     * test uni0303.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0303cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0303.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0304
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0304() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0304", (short) 3,
            (short) 1));
    }

    /**
     * test uni0304.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0304cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0304.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0306
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0306() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0306", (short) 3,
            (short) 1));
    }

    /**
     * test uni0306.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0306cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0306.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0307
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0307() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0307", (short) 3,
            (short) 1));
    }

    /**
     * test uni0307.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0307cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0307.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0308
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0308() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0308", (short) 3,
            (short) 1));
    }

    /**
     * test uni0308.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0308cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0308.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0309() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0309", (short) 3,
            (short) 1));
    }

    /**
     * test uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0309cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0309.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni030A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030A() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030A", (short) 3,
            (short) 1));
    }

    /**
     * test uni030A.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Acap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030A.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni030B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030B() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030B", (short) 3,
            (short) 1));
    }

    /**
     * test uni030B.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Bcap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030B.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni030C
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030C() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030C", (short) 3,
            (short) 1));
    }

    /**
     * test uni030C.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Ccap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030C.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni030F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030F() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030F", (short) 3,
            (short) 1));
    }

    /**
     * test uni030F.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Fcap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030F.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0311
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0311() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0311", (short) 3,
            (short) 1));
    }

    /**
     * test uni0311.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0311cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0311.cap", (short) 3,
            (short) 1));
    }

    /**
     * test uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0323() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0323", (short) 3,
            (short) 1));
    }

    /**
     * test uni0326
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0326() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0326", (short) 3,
            (short) 1));
    }

    /**
     * test uni032E
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni032E() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni032E", (short) 3,
            (short) 1));
    }

    /**
     * test uni032F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni032F() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni032F", (short) 3,
            (short) 1));
    }

    /**
     * test uni2014.alt1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2014alt1() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("uni2014.alt1", (short) 3,
            (short) 1));
    }

    /**
     * test uni2014.alt2
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2014alt2() throws Exception {

        assertEquals(667, reader.mapCharCodeToWidth("uni2014.alt2", (short) 3,
            (short) 1));
    }

    /**
     * test uni2127
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2127() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("uni2127", (short) 3,
            (short) 1));
    }

    /**
     * test uni2190
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2190() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("uni2190", (short) 3,
            (short) 1));
    }

    /**
     * test uni2191
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2191() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("uni2191", (short) 3,
            (short) 1));
    }

    /**
     * test uni2192
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2192() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("uni2192", (short) 3,
            (short) 1));
    }

    /**
     * test uni2193
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2193() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("uni2193", (short) 3,
            (short) 1));
    }

    /**
     * test uni266A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni266A() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("uni266A", (short) 3,
            (short) 1));
    }

    /**
     * test uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuogonek() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uogonek", (short) 3,
            (short) 1));
    }

    /**
     * test uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuring() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uring", (short) 3,
            (short) 1));
    }

    /**
     * test utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxutilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("utilde", (short) 3,
            (short) 1));
    }

    /**
     * test v
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxv() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("v", (short) 3, (short) 1));
    }

    /**
     * test w
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxw() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("w", (short) 3, (short) 1));
    }

    /**
     * test wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwacute() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wacute", (short) 3,
            (short) 1));
    }

    /**
     * test wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwcircumflex() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wcircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwdieresis() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wdieresis", (short) 3,
            (short) 1));
    }

    /**
     * test wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwgrave() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wgrave", (short) 3,
            (short) 1));
    }

    /**
     * test won
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwon() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("won", (short) 3,
            (short) 1));
    }

    /**
     * test x
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxx() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("x", (short) 3, (short) 1));
    }

    /**
     * test y
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxy() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("y", (short) 3, (short) 1));
    }

    /**
     * test yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyacute() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("yacute", (short) 3,
            (short) 1));
    }

    /**
     * test ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxycircumflex() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ycircumflex", (short) 3,
            (short) 1));
    }

    /**
     * test ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxydieresis() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ydieresis", (short) 3,
            (short) 1));
    }

    /**
     * test ydotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxydotbelow() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ydotbelow", (short) 3,
            (short) 1));
    }

    /**
     * test yen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyen() throws Exception {

        assertEquals(750, reader
            .mapCharCodeToWidth("yen", (short) 3, (short) 1));
    }

    /**
     * test ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxygrave() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ygrave", (short) 3,
            (short) 1));
    }

    /**
     * test yhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyhookabove() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("yhookabove", (short) 3,
            (short) 1));
    }

    /**
     * test ytilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxytilde() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ytilde", (short) 3,
            (short) 1));
    }

    /**
     * test z
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxz() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("z", (short) 3, (short) 1));
    }

    /**
     * test zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("zacute", (short) 3,
            (short) 1));
    }

    /**
     * test zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzcaron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("zcaron", (short) 3,
            (short) 1));
    }

    /**
     * test zdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzdotaccent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("zdotaccent", (short) 3,
            (short) 1));
    }

    /**
     * test zero
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzero() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("zero", (short) 3,
            (short) 1));
    }

    /**
     * test zero.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzerooldstyle() throws Exception {

        assertEquals(570, reader.mapCharCodeToWidth("zero.oldstyle", (short) 3,
            (short) 1));
    }

    /**
     * test zero.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzeroprop() throws Exception {

        assertEquals(569, reader.mapCharCodeToWidth("zero.prop", (short) 3,
            (short) 1));
    }

    /**
     * test zero.slash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzeroslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("zero.slash", (short) 3,
            (short) 1));
    }

    /**
     * test zero.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzerotaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("zero.taboldstyle",
            (short) 3, (short) 1));
    }

}
