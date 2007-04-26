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
 * @version $Revision$
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

        assertEquals(750, reader.mapCharCodeToWidth("A", 0, (short) 3,
            (short) 1));
    }

    /**
     * test AE
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAE() throws Exception {

        assertEquals(903, reader.mapCharCodeToWidth("AE", 0, (short) 3,
            (short) 1));
    }

    /**
     * test AE.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAEdup() throws Exception {

        assertEquals(903, reader.mapCharCodeToWidth("AE.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test AEacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAEacute() throws Exception {

        assertEquals(903, reader.mapCharCodeToWidth("AEacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbreve() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Abreveacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbreveacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abreveacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Abrevedotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevedotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevedotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Abrevegrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevegrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevegrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Abrevehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevehookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevehookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Abrevetilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAbrevetilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Abrevetilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexdotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexgrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflexhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflexhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Acircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAcircumflextilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Acircumflextilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Adblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdblgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Adblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdieresis() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Adieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Adotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Adotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Agrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ahookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ahookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAmacron() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Amacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAogonek() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAogonekacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAring() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAringacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Aringacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxAtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Atilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxB() throws Exception {

        assertEquals(708, reader.mapCharCodeToWidth("B", 0, (short) 3,
            (short) 1));
    }

    /**
     * test C
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxC() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("C", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCacute() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Cacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcaron() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Ccaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcedilla() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Ccedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCcircumflex() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Ccircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Cdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxCdotaccent() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Cdotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test D
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxD() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("D", 0, (short) 3,
            (short) 1));
    }

    /**
     * test D_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxD_uni0323() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("D_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDcaron() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("Dcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Dcroat
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDcroat() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("Dcroat", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Delta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxDelta() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("Delta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test E
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxE() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("E", 0, (short) 3,
            (short) 1));
    }

    /**
     * test E.reversed
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEreversed() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("E.reversed", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEacute() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Eacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEbreve() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ebreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcaron() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflex() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexacute() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexdotbelow() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexdotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexgrave() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexgrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflexhookabove() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflexhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ecircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEcircumflextilde() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ecircumflextilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Edblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdblgrave() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdieresis() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Edotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdotaccent() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Edotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEdotbelow() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Edotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEgrave() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Egrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEhookabove() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Ehookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEmacron() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Emacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEng() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Eng", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEogonek() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Eogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Eogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEogonekacute() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Eogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEth() throws Exception {

        assertEquals(764, reader.mapCharCodeToWidth("Eth", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Etilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEtilde() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Etilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Euro
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxEuro() throws Exception {

        assertEquals(627, reader.mapCharCodeToWidth("Euro", 0, (short) 3,
            (short) 1));
    }

    /**
     * test F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxF() throws Exception {

        assertEquals(653, reader.mapCharCodeToWidth("F", 0, (short) 3,
            (short) 1));
    }

    /**
     * test G
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxG() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("G", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGacute() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gamma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGamma() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Gamma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGbreve() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gbreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcaron() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcedilla() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcircumflex() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Gcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGcommaaccent() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Gdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxGdotaccent() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("Gdotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("H", 0, (short) 3,
            (short) 1));
    }

    /**
     * test H_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxH_uni0323() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("H_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxHbar() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Hbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxHcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Hcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test I
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxI() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("I", 0, (short) 3,
            (short) 1));
    }

    /**
     * test I_J
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxI_J() throws Exception {

        assertEquals(839, reader.mapCharCodeToWidth("I_J", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIacute() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Iacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIbreve() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Ibreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIcircumflex() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Icircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Idblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdblgrave() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdieresis() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Idotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdotaccent() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Idotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIdotbelow() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Idotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIgrave() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Igrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ihookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIhookabove() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Ihookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxImacron() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Imacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIogonek() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Iogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Iogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxIogonekacute() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Iogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxItilde() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("Itilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test J
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJ() throws Exception {

        assertEquals(514, reader.mapCharCodeToWidth("J", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Jacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJacute() throws Exception {

        assertEquals(514, reader.mapCharCodeToWidth("Jacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxJcircumflex() throws Exception {

        assertEquals(514, reader.mapCharCodeToWidth("Jcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test K
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxK() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("K", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxKcedilla() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Kcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Kcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxKcommaaccent() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Kcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test L
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("L", 0, (short) 3,
            (short) 1));
    }

    /**
     * test L_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL_uni0323() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("L_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test L_uni0323_uni0304.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxL_uni0323_uni0304cap() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("L_uni0323_uni0304.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLacute() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lambda
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLambda() throws Exception {

        assertEquals(694, reader.mapCharCodeToWidth("Lambda", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcaron() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcedilla() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLcommaaccent() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLdot() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Ldot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxLslash() throws Exception {

        assertEquals(625, reader.mapCharCodeToWidth("Lslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test M
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxM() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("M", 0, (short) 3,
            (short) 1));
    }

    /**
     * test M_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxM_uni0323() throws Exception {

        assertEquals(917, reader.mapCharCodeToWidth("M_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test N
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("N", 0, (short) 3,
            (short) 1));
    }

    /**
     * test N_uni0307.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN_uni0307cap() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("N_uni0307.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test N_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxN_uni0323() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("N_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Nacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcaron() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ncaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcedilla() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ncedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ncommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNcommaaccent() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ncommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxNtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test O
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxO() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("O", 0, (short) 3,
            (short) 1));
    }

    /**
     * test OE
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOE() throws Exception {

        assertEquals(1014, reader.mapCharCodeToWidth("OE", 0, (short) 3,
            (short) 1));
    }

    /**
     * test OE.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOEdup() throws Exception {

        assertEquals(1014, reader.mapCharCodeToWidth("OE.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxObreve() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Obreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflex() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexdotbelow() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexdotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexgrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexgrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflexhookabove() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflexhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ocircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOcircumflextilde() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ocircumflextilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Odblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdblgrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Odblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdieresis() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Odieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Odotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOdotbelow() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Odotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOgrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ograve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhookabove() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorn() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhornacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohornacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorndotbelow() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorndotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ohorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorngrave() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorngrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhornhookabove() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohornhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ohorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhorntilde() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohorntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ohungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOhungarumlaut() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Ohungarumlaut", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmacron() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Omacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Omega
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOmega() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Omega", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOogonek() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOogonekacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslash() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oslash.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslashdup() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oslash.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOslashacute() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Oslashacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxOtilde() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Otilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test P
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxP() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("P", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Phi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPhi() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Phi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Pi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPi() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Pi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Psi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxPsi() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Psi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Q
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxQ() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Q", 0, (short) 3,
            (short) 1));
    }

    /**
     * test R
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R", 0, (short) 3,
            (short) 1));
    }

    /**
     * test R_uni0307.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR_uni0307cap() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R_uni0307.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test R_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR_uni0323() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test R_uni0323_uni0304.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxR_uni0323_uni0304cap() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("R_uni0323_uni0304.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRacute() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Racute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcaron() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcedilla() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Rcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRcommaaccent() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Rdblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxRdblgrave() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("Rdblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test S
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxS() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("S", 0, (short) 3,
            (short) 1));
    }

    /**
     * test S_S
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxS_S() throws Exception {

        assertEquals(1111, reader.mapCharCodeToWidth("S_S", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Sacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScaron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Scaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScedilla() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Scedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxScircumflex() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("Scircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Sigma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxSigma() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Sigma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test T
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxT() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("T", 0, (short) 3,
            (short) 1));
    }

    /**
     * test T_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxT_uni0323() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("T_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTcaron() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Tcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTcedilla() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("Tcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Theta
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxTheta() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Theta", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxThorn() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("Thorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test U
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxU() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("U", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUbreve() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ubreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ubreveinvertedlow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUbreveinvertedlow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ubreveinvertedlow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ucircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Udblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdblgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Udblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdieresis() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Udieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Udotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Udotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ugrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uhorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorn() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uhornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhornacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhornacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uhorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorndotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorndotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Uhorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorngrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorngrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uhornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhornhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhornhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Uhorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhorntilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhorntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uhungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUhungarumlaut() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uhungarumlaut", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUmacron() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Umacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUogonek() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Upsilon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUpsilon() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("Upsilon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUring() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Uring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxUtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Utilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test V
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxV() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("V", 0, (short) 3,
            (short) 1));
    }

    /**
     * test W
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxW() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("W", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWacute() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWcircumflex() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWdieresis() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wdieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxWgrave() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("Wgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test X
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxX() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("X", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Xi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxXi() throws Exception {

        assertEquals(667, reader.mapCharCodeToWidth("Xi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Y
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxY() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Y", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYacute() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Yacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYcircumflex() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ycircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test Ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYdieresis() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ydieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ydotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYdotbelow() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ydotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYgrave() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ygrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Yhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYhookabove() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Yhookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Ytilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxYtilde() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("Ytilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Z
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZ() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Z", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZacute() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Zacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZcaron() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Zcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test Zdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxZdotaccent() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("Zdotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test a
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxa() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("a", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test abreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test abreveacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabreveacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abreveacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test abrevedotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevedotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevedotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test abrevegrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevegrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevegrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test abrevehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevehookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevehookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test abrevetilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxabrevetilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("abrevetilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexdotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexdotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexgrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflexhookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflexhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacircumflextilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acircumflextilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test acute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test acute.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacutecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test acute.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacutedup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test acute.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxacutets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("acute.ts1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test adblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadblgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("adblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test adieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadieresis() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("adieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test adotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxadotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("adotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ae
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxae() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("ae", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ae.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaedup() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("ae.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aeacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaeacute() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("aeacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test afii61352
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxafii61352() throws Exception {

        assertEquals(916, reader.mapCharCodeToWidth("afii61352", 0, (short) 3,
            (short) 1));
    }

    /**
     * test agrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxagrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("agrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ahookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxahookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ahookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test amacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxamacron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("amacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ampersand
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxampersand() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("ampersand", 0, (short) 3,
            (short) 1));
    }

    /**
     * test anglearc
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxanglearc() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("anglearc", 0, (short) 3,
            (short) 1));
    }

    /**
     * test angleleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxangleleft() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("angleleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test angleright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxangleright() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("angleright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaogonek() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaogonekacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test aring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaring() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test aringacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxaringacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("aringacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test asciicircum
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasciicircum() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("asciicircum", 0,
            (short) 3, (short) 1));
    }

    /**
     * test asciitilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasciitilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("asciitilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test asterisk
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasterisk() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("asterisk", 0, (short) 3,
            (short) 1));
    }

    /**
     * test asterisk.math
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxasteriskmath() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("asterisk.math", 0,
            (short) 3, (short) 1));
    }

    /**
     * test at
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxat() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("at", 0, (short) 3,
            (short) 1));
    }

    /**
     * test atilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxatilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("atilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test b
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxb() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("b", 0, (short) 3,
            (short) 1));
    }

    /**
     * test backslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbackslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("backslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test baht
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbaht() throws Exception {

        assertEquals(708, reader.mapCharCodeToWidth("baht", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbar() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("bar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bigcircle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbigcircle() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("bigcircle", 0, (short) 3,
            (short) 1));
    }

    /**
     * test blanksymbol
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxblanksymbol() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("blanksymbol", 0,
            (short) 3, (short) 1));
    }

    /**
     * test braceleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbraceleft() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("braceleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test braceright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbraceright() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("braceright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbracketleft() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("bracketleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test bracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbracketright() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("bracketright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test breve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("breve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test breve.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrevecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("breve.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test breve.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrevets1() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("breve.ts1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test brevelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrevelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("brevelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test brokenbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbrokenbar() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("brokenbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test bullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxbullet() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("bullet", 0, (short) 3,
            (short) 1));
    }

    /**
     * test c
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxc() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("c", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test caron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("caron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test caron.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaroncap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("caron.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test caron.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcaronts1() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("caron.ts1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ccaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccaron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ccaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ccedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccedilla() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ccedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ccircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxccircumflex() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ccircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test cdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcdotaccent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cdotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcedilla() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cedilla.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcedilladup() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cedilla.dup", 0,
            (short) 3, (short) 1));
    }

    /**
     * test cent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cent.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcentoldstyle() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("cent.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test centigrade
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcentigrade() throws Exception {

        assertEquals(944, reader.mapCharCodeToWidth("centigrade", 0, (short) 3,
            (short) 1));
    }

    /**
     * test circumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("circumflex", 0, (short) 3,
            (short) 1));
    }

    /**
     * test circumflex.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflexcap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("circumflex.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test circumflex.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcircumflexdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("circumflex.dup", 0,
            (short) 3, (short) 1));
    }

    /**
     * test colon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcolon() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("colon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test colonmonetary
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcolonmonetary() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("colonmonetary", 0,
            (short) 3, (short) 1));
    }

    /**
     * test comma
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcomma() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("comma", 0, (short) 3,
            (short) 1));
    }

    /**
     * test commaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcommaaccent() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("commaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test copyleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyleft() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("copyleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test copyright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyright() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("copyright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test copyright.var
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcopyrightvar() throws Exception {

        assertEquals(659, reader.mapCharCodeToWidth("copyright.var", 0,
            (short) 3, (short) 1));
    }

    /**
     * test currency
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcurrency() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("currency", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cwm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcwm() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("cwm", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cwmascender
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcwmascender() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("cwmascender", 0, (short) 3,
            (short) 1));
    }

    /**
     * test cwmcapital
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxcwmcapital() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("cwmcapital", 0, (short) 3,
            (short) 1));
    }

    /**
     * test d
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxd() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("d", 0, (short) 3,
            (short) 1));
    }

    /**
     * test d_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxd_uni0323() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("d_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dagger
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdagger() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("dagger", 0, (short) 3,
            (short) 1));
    }

    /**
     * test daggerdbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdaggerdbl() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("daggerdbl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dblbracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblbracketleft() throws Exception {

        assertEquals(403, reader.mapCharCodeToWidth("dblbracketleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dblbracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblbracketright() throws Exception {

        assertEquals(403, reader.mapCharCodeToWidth("dblbracketright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dblgrave.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblgravets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dblgrave.ts1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dblverticalbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdblverticalbar() throws Exception {

        assertEquals(398, reader.mapCharCodeToWidth("dblverticalbar", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdcaron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("dcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dcroat
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdcroat() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("dcroat", 0, (short) 3,
            (short) 1));
    }

    /**
     * test degree
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdegree() throws Exception {

        assertEquals(375, reader.mapCharCodeToWidth("degree", 0, (short) 3,
            (short) 1));
    }

    /**
     * test diameter
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdiameter() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("diameter", 0, (short) 3,
            (short) 1));
    }

    /**
     * test died
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdied() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("died", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresis() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dieresis.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresiscap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dieresis.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresisdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis.dup", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dieresis.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdieresists1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dieresis.ts1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test discount
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdiscount() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("discount", 0, (short) 3,
            (short) 1));
    }

    /**
     * test divide
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdivide() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("divide", 0, (short) 3,
            (short) 1));
    }

    /**
     * test divorced
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdivorced() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("divorced", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dollar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdollar() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("dollar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dollar.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdollaroldstyle() throws Exception {

        assertEquals(610, reader.mapCharCodeToWidth("dollar.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dong
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdong() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("dong", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccent() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test dotaccent.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccentcap() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotaccent.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dotaccent.var
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotaccentvar() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotaccent.var", 0,
            (short) 3, (short) 1));
    }

    /**
     * test dotlessi
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxdotlessi() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("dotlessi", 0, (short) 3,
            (short) 1));
    }

    /**
     * test e
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxe() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("e", 0, (short) 3,
            (short) 1));
    }

    /**
     * test e.reversed
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxereversed() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("e.reversed", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("eacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ebreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxebreve() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ebreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ecaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecaron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ecircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflex() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexdotbelow() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexdotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexgrave() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexgrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflexhookabove() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflexhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ecircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxecircumflextilde() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ecircumflextilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test edblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedblgrave() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test edieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedieresis() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test edotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedotaccent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test edotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxedotbelow() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("edotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test egrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxegrave() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("egrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ehookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxehookabove() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("ehookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eight
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeight() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("eight", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eight.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeightoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("eight.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test eight.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeightprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("eight.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eight.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeighttaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("eight.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ellipsis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxellipsis() throws Exception {

        assertEquals(670, reader.mapCharCodeToWidth("ellipsis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test emacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxemacron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("emacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test emdash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxemdash() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("emdash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test endash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxendash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("endash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eng
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeng() throws Exception {

        assertEquals(506, reader.mapCharCodeToWidth("eng", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeogonek() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("eogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeogonekacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("eogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test equal
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxequal() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("equal", 0, (short) 3,
            (short) 1));
    }

    /**
     * test estimated
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxestimated() throws Exception {

        assertEquals(676, reader.mapCharCodeToWidth("estimated", 0, (short) 3,
            (short) 1));
    }

    /**
     * test eth
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxeth() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("eth", 0, (short) 3,
            (short) 1));
    }

    /**
     * test etilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxetilde() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("etilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test exclam
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclam() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("exclam", 0, (short) 3,
            (short) 1));
    }

    /**
     * test exclamdown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxexclamdown() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("exclamdown", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("f", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f_f
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_f() throws Exception {

        assertEquals(583, reader.mapCharCodeToWidth("f_f", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f_f_i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_f_i() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("f_f_i", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f_f_l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_f_l() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("f_f_l", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f_i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_i() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("f_i", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f_k
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_k() throws Exception {

        assertEquals(816, reader.mapCharCodeToWidth("f_k", 0, (short) 3,
            (short) 1));
    }

    /**
     * test f_l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxf_l() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("f_l", 0, (short) 3,
            (short) 1));
    }

    /**
     * test five
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfive() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("five", 0, (short) 3,
            (short) 1));
    }

    /**
     * test five.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfiveoldstyle() throws Exception {

        assertEquals(547, reader.mapCharCodeToWidth("five.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test five.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfiveprop() throws Exception {

        assertEquals(547, reader.mapCharCodeToWidth("five.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test five.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfivetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("five.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test florin
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxflorin() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("florin", 0, (short) 3,
            (short) 1));
    }

    /**
     * test four
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfour() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("four", 0, (short) 3,
            (short) 1));
    }

    /**
     * test four.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfouroldstyle() throws Exception {

        assertEquals(517, reader.mapCharCodeToWidth("four.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test four.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfourprop() throws Exception {

        assertEquals(517, reader.mapCharCodeToWidth("four.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test four.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfourtaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("four.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test fraction
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfraction() throws Exception {

        assertEquals(551, reader.mapCharCodeToWidth("fraction", 0, (short) 3,
            (short) 1));
    }

    /**
     * test fraction.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxfractionalt() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("fraction.alt", 0, (short) 3,
            (short) 1));
    }

    /**
     * test g
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxg() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("g", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gbreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgbreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gbreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcaron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcedilla() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test gcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test gcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgcommaaccent() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test gdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgdotaccent() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("gdotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test germandbls
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgermandbls() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("germandbls", 0, (short) 3,
            (short) 1));
    }

    /**
     * test germandbls.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgermandblsdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("germandbls.dup", 0,
            (short) 3, (short) 1));
    }

    /**
     * test gnaborretni
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgnaborretni() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("gnaborretni", 0,
            (short) 3, (short) 1));
    }

    /**
     * test grave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("grave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test grave.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgravecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("grave.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test grave.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgravets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("grave.ts1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test greater
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxgreater() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("greater", 0, (short) 3,
            (short) 1));
    }

    /**
     * test guarani
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguarani() throws Exception {

        assertEquals(785, reader.mapCharCodeToWidth("guarani", 0, (short) 3,
            (short) 1));
    }

    /**
     * test guillemotleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguillemotleft() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("guillemotleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guillemotright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguillemotright() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("guillemotright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guilsinglleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguilsinglleft() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("guilsinglleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test guilsinglright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxguilsinglright() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("guilsinglright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test h
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxh() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("h", 0, (short) 3,
            (short) 1));
    }

    /**
     * test h_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxh_uni0323() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("h_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hbar
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhbar() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("hbar", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhcircumflex() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("hcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test hungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlaut() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("hungarumlaut", 0,
            (short) 3, (short) 1));
    }

    /**
     * test hungarumlaut.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlautcap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("hungarumlaut.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test hungarumlaut.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhungarumlautts1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("hungarumlaut.ts1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test hyphen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphen() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphen", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hyphen.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphenalt() throws Exception {

        assertEquals(167, reader.mapCharCodeToWidth("hyphen.alt", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hyphen.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphendup() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphen.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hyphen.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphenprop() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphen.prop", 0,
            (short) 3, (short) 1));
    }

    /**
     * test hyphendbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphendbl() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("hyphendbl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test hyphendbl.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxhyphendblalt() throws Exception {

        assertEquals(167, reader.mapCharCodeToWidth("hyphendbl.alt", 0,
            (short) 3, (short) 1));
    }

    /**
     * test i
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxi() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("i", 0, (short) 3,
            (short) 1));
    }

    /**
     * test i.TRK
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiTRK() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("i.TRK", 0, (short) 3,
            (short) 1));
    }

    /**
     * test i_j
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxi_j() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("i_j", 0, (short) 3,
            (short) 1));
    }

    /**
     * test iacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiacute() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("iacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ibreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxibreve() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("ibreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test icircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxicircumflex() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("icircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test idblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidblgrave() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("idblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test idieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidieresis() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("idieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test idotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxidotbelow() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("idotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test igrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxigrave() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("igrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ihookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxihookabove() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("ihookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test imacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtximacron() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("imacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test interrobang
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxinterrobang() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("interrobang", 0,
            (short) 3, (short) 1));
    }

    /**
     * test iogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiogonek() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("iogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test iogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxiogonekacute() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("iogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test itilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxitilde() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("itilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test j
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxj() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("j", 0, (short) 3,
            (short) 1));
    }

    /**
     * test j.dotless
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjdotless() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("j.dotless", 0, (short) 3,
            (short) 1));
    }

    /**
     * test jacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjacute() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("jacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test jcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxjcircumflex() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("jcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test k
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxk() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("k", 0, (short) 3,
            (short) 1));
    }

    /**
     * test kcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkcedilla() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("kcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test kcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxkcommaaccent() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("kcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test l
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("l", 0, (short) 3,
            (short) 1));
    }

    /**
     * test l_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl_uni0323() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("l_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test l_uni0323_uni0304
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxl_uni0323_uni0304() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("l_uni0323_uni0304", 0,
            (short) 3, (short) 1));
    }

    /**
     * test lacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlacute() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcaron() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcedilla() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlcommaaccent() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("lcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ldot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxldot() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("ldot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test leaf
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxleaf() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("leaf", 0, (short) 3,
            (short) 1));
    }

    /**
     * test less
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxless() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("less", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lira
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlira() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("lira", 0, (short) 3,
            (short) 1));
    }

    /**
     * test logicalnot
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlogicalnot() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("logicalnot", 0, (short) 3,
            (short) 1));
    }

    /**
     * test longs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlongs() throws Exception {

        assertEquals(306, reader.mapCharCodeToWidth("longs", 0, (short) 3,
            (short) 1));
    }

    /**
     * test lslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxlslash() throws Exception {

        assertEquals(336, reader.mapCharCodeToWidth("lslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test m
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxm() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("m", 0, (short) 3,
            (short) 1));
    }

    /**
     * test m_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxm_uni0323() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("m_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test macron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("macron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test macron.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacroncap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("macron.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test macron.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacrondup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("macron.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test macron.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmacronts1() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("macron.ts1", 0, (short) 3,
            (short) 1));
    }

    /**
     * test married
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmarried() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("married", 0, (short) 3,
            (short) 1));
    }

    /**
     * test minus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxminus() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("minus", 0, (short) 3,
            (short) 1));
    }

    /**
     * test mu
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmu() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("mu", 0, (short) 3,
            (short) 1));
    }

    /**
     * test multiply
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxmultiply() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("multiply", 0, (short) 3,
            (short) 1));
    }

    /**
     * test n
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("n", 0, (short) 3,
            (short) 1));
    }

    /**
     * test n_uni0307
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn_uni0307() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("n_uni0307", 0, (short) 3,
            (short) 1));
    }

    /**
     * test n_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxn_uni0323() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("n_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("nacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test naira
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnaira() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("naira", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ncaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncaron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ncaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ncedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncedilla() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ncedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ncommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxncommaaccent() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ncommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test nine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnine() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("nine", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nine.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnineoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("nine.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test nine.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnineprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("nine.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test nine.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxninetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("nine.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxntilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test numbersign
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxnumbersign() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("numbersign", 0, (short) 3,
            (short) 1));
    }

    /**
     * test o
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxo() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("o", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test obreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxobreve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("obreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ocircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflex() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexdotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexdotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexdotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexgrave", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflexhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflexhookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflexhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ocircumflextilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxocircumflextilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ocircumflextilde", 0,
            (short) 3, (short) 1));
    }

    /**
     * test odblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodblgrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("odblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test odieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodieresis() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("odieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test odotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxodotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("odotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oe
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoe() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("oe", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oe.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoedup() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("oe.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxogonek() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ograve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxograve() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ograve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohm() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("ohm", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorn() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohornacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohornacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorndotbelow() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorndotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ohorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorngrave() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorngrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohornhookabove() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohornhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ohorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohorntilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohorntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ohungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxohungarumlaut() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("ohungarumlaut", 0,
            (short) 3, (short) 1));
    }

    /**
     * test omacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxomacron() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("omacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test one
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxone() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("one", 0, (short) 3,
            (short) 1));
    }

    /**
     * test one.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoneoldstyle() throws Exception {

        assertEquals(404, reader.mapCharCodeToWidth("one.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test one.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoneprop() throws Exception {

        assertEquals(404, reader.mapCharCodeToWidth("one.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test one.superior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonesuperior() throws Exception {

        assertEquals(366, reader.mapCharCodeToWidth("one.superior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test one.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("one.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test onehalf
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonehalf() throws Exception {

        assertEquals(825, reader.mapCharCodeToWidth("onehalf", 0, (short) 3,
            (short) 1));
    }

    /**
     * test onequarter
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxonequarter() throws Exception {

        assertEquals(825, reader.mapCharCodeToWidth("onequarter", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoogonek() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oogonekacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoogonekacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oogonekacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test openbullet
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxopenbullet() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("openbullet", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ordfeminine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxordfeminine() throws Exception {

        assertEquals(449, reader.mapCharCodeToWidth("ordfeminine", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ordmasculine
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxordmasculine() throws Exception {

        assertEquals(419, reader.mapCharCodeToWidth("ordmasculine", 0,
            (short) 3, (short) 1));
    }

    /**
     * test oslash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oslash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oslash.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslashdup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oslash.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test oslashacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxoslashacute() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("oslashacute", 0,
            (short) 3, (short) 1));
    }

    /**
     * test otilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxotilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("otilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test p
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxp() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("p", 0, (short) 3,
            (short) 1));
    }

    /**
     * test paragraph
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparagraph() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("paragraph", 0, (short) 3,
            (short) 1));
    }

    /**
     * test paragraph.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparagraphalt() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("paragraph.alt", 0,
            (short) 3, (short) 1));
    }

    /**
     * test parenleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparenleft() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("parenleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test parenright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxparenright() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("parenright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test percent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpercent() throws Exception {

        assertEquals(833, reader.mapCharCodeToWidth("percent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test period
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperiod() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("period", 0, (short) 3,
            (short) 1));
    }

    /**
     * test periodcentered
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperiodcentered() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("periodcentered", 0,
            (short) 3, (short) 1));
    }

    /**
     * test permyriad
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpermyriad() throws Exception {

        assertEquals(1457, reader.mapCharCodeToWidth("permyriad", 0, (short) 3,
            (short) 1));
    }

    /**
     * test perthousand
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperthousand() throws Exception {

        assertEquals(1140, reader.mapCharCodeToWidth("perthousand", 0,
            (short) 3, (short) 1));
    }

    /**
     * test perthousandzero
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxperthousandzero() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("perthousandzero", 0,
            (short) 3, (short) 1));
    }

    /**
     * test peso
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpeso() throws Exception {

        assertEquals(681, reader.mapCharCodeToWidth("peso", 0, (short) 3,
            (short) 1));
    }

    /**
     * test plus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxplus() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("plus", 0, (short) 3,
            (short) 1));
    }

    /**
     * test plusminus
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxplusminus() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("plusminus", 0, (short) 3,
            (short) 1));
    }

    /**
     * test published
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxpublished() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("published", 0, (short) 3,
            (short) 1));
    }

    /**
     * test q
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxq() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("q", 0, (short) 3,
            (short) 1));
    }

    /**
     * test question
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquestion() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("question", 0, (short) 3,
            (short) 1));
    }

    /**
     * test questiondown
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquestiondown() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("questiondown", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quillbracketleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquillbracketleft() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("quillbracketleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quillbracketright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquillbracketright() throws Exception {

        assertEquals(361, reader.mapCharCodeToWidth("quillbracketright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedbl
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedbl() throws Exception {

        assertEquals(374, reader.mapCharCodeToWidth("quotedbl", 0, (short) 3,
            (short) 1));
    }

    /**
     * test quotedblbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbase() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("quotedblbase", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblbase.cm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbasecm() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblbase.cm", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblbase.cs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbasecs() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblbase.cs", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblbase.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblbasets1() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblbase.ts1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblleft() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("quotedblleft", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblleft.cm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblleftcm() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblleft.cm", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblright() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("quotedblright", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblright.cm
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblrightcm() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblright.cm", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotedblright.cs
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotedblrightcs() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("quotedblright.cs", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quoteleft
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteleft() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteleft", 0, (short) 3,
            (short) 1));
    }

    /**
     * test quoteleft.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteleftdup() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteleft.dup", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quoteright
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoteright() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteright", 0, (short) 3,
            (short) 1));
    }

    /**
     * test quoteright.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquoterightdup() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quoteright.dup", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotesinglbase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglbase() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quotesinglbase", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotesinglbase.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglbasets1() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("quotesinglbase.ts1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotesingle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesingle() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quotesingle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test quotesingle.ts1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxquotesinglets1() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("quotesingle.ts1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test r
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r", 0, (short) 3,
            (short) 1));
    }

    /**
     * test r_uni0307
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr_uni0307() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r_uni0307", 0, (short) 3,
            (short) 1));
    }

    /**
     * test r_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr_uni0323() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test r_uni0323_uni0304
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxr_uni0323_uni0304() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("r_uni0323_uni0304", 0,
            (short) 3, (short) 1));
    }

    /**
     * test racute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxracute() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("racute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test radical
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxradical() throws Exception {

        assertEquals(472, reader.mapCharCodeToWidth("radical", 0, (short) 3,
            (short) 1));
    }

    /**
     * test rcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcaron() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test rcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcedilla() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test rcommaaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrcommaaccent() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rcommaaccent", 0,
            (short) 3, (short) 1));
    }

    /**
     * test rdblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrdblgrave() throws Exception {

        assertEquals(392, reader.mapCharCodeToWidth("rdblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test recipe
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxrecipe() throws Exception {

        assertEquals(736, reader.mapCharCodeToWidth("recipe", 0, (short) 3,
            (short) 1));
    }

    /**
     * test referencemark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxreferencemark() throws Exception {

        assertEquals(778, reader.mapCharCodeToWidth("referencemark", 0,
            (short) 3, (short) 1));
    }

    /**
     * test registered
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregistered() throws Exception {

        assertEquals(683, reader.mapCharCodeToWidth("registered", 0, (short) 3,
            (short) 1));
    }

    /**
     * test registered.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregisteredalt() throws Exception {

        assertEquals(470, reader.mapCharCodeToWidth("registered.alt", 0,
            (short) 3, (short) 1));
    }

    /**
     * test registered.var
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxregisteredvar() throws Exception {

        assertEquals(659, reader.mapCharCodeToWidth("registered.var", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxring() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("ring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ring.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxringcap() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("ring.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test s
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxs() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("s", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsacute() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("sacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test scaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscaron() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("scaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test scedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscedilla() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("scedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test scircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxscircumflex() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("scircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test section
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsection() throws Exception {

        assertEquals(484, reader.mapCharCodeToWidth("section", 0, (short) 3,
            (short) 1));
    }

    /**
     * test semicolon
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsemicolon() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("semicolon", 0, (short) 3,
            (short) 1));
    }

    /**
     * test servicemark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxservicemark() throws Exception {

        assertEquals(883, reader.mapCharCodeToWidth("servicemark", 0,
            (short) 3, (short) 1));
    }

    /**
     * test seven
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxseven() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("seven", 0, (short) 3,
            (short) 1));
    }

    /**
     * test seven.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsevenoldstyle() throws Exception {

        assertEquals(503, reader.mapCharCodeToWidth("seven.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test seven.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsevenprop() throws Exception {

        assertEquals(503, reader.mapCharCodeToWidth("seven.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test seven.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxseventaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("seven.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test six
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsix() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("six", 0, (short) 3,
            (short) 1));
    }

    /**
     * test six.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsixoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("six.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test six.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsixprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("six.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test six.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsixtaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("six.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test slash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("slash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test space
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("space", 0, (short) 3,
            (short) 1));
    }

    /**
     * test space.visible
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspacevisible() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space.visible", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0300
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0300() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0300", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0300.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0300cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0300.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0301() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0301", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0301cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0301.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0303
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0303() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0303", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0303.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0303cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0303.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0309() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0302_uni0309", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0302_uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0302_uni0309cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0302_uni0309.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0300
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0300() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0300", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0300.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0300cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0300.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0301() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0301", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0301cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0301.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0303
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0303() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0303", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0303.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0303cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0303.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0309() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0306_uni0309", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0306_uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0306_uni0309cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth(
            "space_uni0306_uni0309.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0309() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0309", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0309cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0309.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni030A_uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030A_uni0301() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("space_uni030A_uni0301", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni030A_uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030A_uni0301cap() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth(
            "space_uni030A_uni0301.cap", 0, (short) 3, (short) 1));
    }

    /**
     * test space_uni030F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030F() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni030F", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni030F.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni030Fcap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni030F.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0311
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0311() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0311", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0311.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0311cap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni0311.cap", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0323() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("space_uni0323", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni032F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni032F() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("space_uni032F", 0,
            (short) 3, (short) 1));
    }

    /**
     * test space_uni0330
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxspace_uni0330() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("space_uni0330", 0,
            (short) 3, (short) 1));
    }

    /**
     * test star.alt
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxstaralt() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("star.alt", 0, (short) 3,
            (short) 1));
    }

    /**
     * test sterling
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsterling() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("sterling", 0, (short) 3,
            (short) 1));
    }

    /**
     * test suppress
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxsuppress() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("suppress", 0, (short) 3,
            (short) 1));
    }

    /**
     * test t
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxt() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("t", 0, (short) 3,
            (short) 1));
    }

    /**
     * test t_uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxt_uni0323() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("t_uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtcaron() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("tcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tcedilla
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtcedilla() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("tcedilla", 0, (short) 3,
            (short) 1));
    }

    /**
     * test thorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthorn() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("thorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test three
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthree() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("three", 0, (short) 3,
            (short) 1));
    }

    /**
     * test three.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreeoldstyle() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("three.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test three.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreeprop() throws Exception {

        assertEquals(563, reader.mapCharCodeToWidth("three.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test three.superior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreesuperior() throws Exception {

        assertEquals(359, reader.mapCharCodeToWidth("three.superior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test three.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreetaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("three.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test threequarters
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxthreequarters() throws Exception {

        assertEquals(825, reader.mapCharCodeToWidth("threequarters", 0,
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentcapital
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentcapital() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("tieaccentcapital", 0,
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentcapital.new
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentcapitalnew() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tieaccentcapital.new", 0,
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentlowercase
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentlowercase() throws Exception {

        assertEquals(278, reader.mapCharCodeToWidth("tieaccentlowercase", 0,
            (short) 3, (short) 1));
    }

    /**
     * test tieaccentlowercase.new
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtieaccentlowercasenew() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tieaccentlowercase.new",
            0, (short) 3, (short) 1));
    }

    /**
     * test tilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtilde() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tilde.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtildecap() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tilde.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test tilde.dup
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtildedup() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("tilde.dup", 0, (short) 3,
            (short) 1));
    }

    /**
     * test trademark
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtrademark() throws Exception {

        assertEquals(983, reader.mapCharCodeToWidth("trademark", 0, (short) 3,
            (short) 1));
    }

    /**
     * test two
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwo() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("two", 0, (short) 3,
            (short) 1));
    }

    /**
     * test two.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwooldstyle() throws Exception {

        assertEquals(554, reader.mapCharCodeToWidth("two.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test two.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwoprop() throws Exception {

        assertEquals(547, reader.mapCharCodeToWidth("two.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test two.superior
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwosuperior() throws Exception {

        assertEquals(359, reader.mapCharCodeToWidth("two.superior", 0,
            (short) 3, (short) 1));
    }

    /**
     * test two.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxtwotaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("two.taboldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test u
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxu() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("u", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ubreve
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxubreve() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ubreve", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ubreveinvertedlow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxubreveinvertedlow() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ubreveinvertedlow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ucircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxucircumflex() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ucircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test udblgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudblgrave() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("udblgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test udieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudieresis() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("udieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test udotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxudotbelow() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("udotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ugrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxugrave() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("ugrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhookabove() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uhorn
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorn() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorn", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uhornacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhornacute() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhornacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uhorndotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorndotbelow() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorndotbelow", 0,
            (short) 3, (short) 1));
    }

    /**
     * test uhorngrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorngrave() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorngrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uhornhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhornhookabove() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhornhookabove", 0,
            (short) 3, (short) 1));
    }

    /**
     * test uhorntilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhorntilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhorntilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uhungarumlaut
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuhungarumlaut() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uhungarumlaut", 0,
            (short) 3, (short) 1));
    }

    /**
     * test umacron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxumacron() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("umacron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test underscore
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxunderscore() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("underscore", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni00A0
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni00A0() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("uni00A0", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni00AD
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni00AD() throws Exception {

        assertEquals(333, reader.mapCharCodeToWidth("uni00AD", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0218
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0218() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uni0218", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0219
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0219() throws Exception {

        assertEquals(394, reader.mapCharCodeToWidth("uni0219", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni021A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni021A() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("uni021A", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni021B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni021B() throws Exception {

        assertEquals(389, reader.mapCharCodeToWidth("uni021B", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0300
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0300() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0300", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0300.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0300cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0300.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0301
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0301() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0301", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0301.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0301cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0301.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0302
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0302() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0302", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0302.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0302cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0302.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0303
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0303() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0303", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0303.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0303cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0303.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0304
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0304() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0304", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0304.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0304cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0304.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0306
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0306() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0306", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0306.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0306cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0306.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0307
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0307() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0307", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0307.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0307cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0307.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0308
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0308() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0308", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0308.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0308cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0308.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0309
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0309() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0309", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0309.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0309cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0309.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030A() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030A", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030A.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Acap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030A.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030B
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030B() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030B", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030B.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Bcap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030B.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030C
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030C() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030C", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030C.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Ccap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030C.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030F() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030F", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni030F.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni030Fcap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni030F.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0311
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0311() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0311", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0311.cap
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0311cap() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0311.cap", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0323
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0323() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0323", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni0326
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni0326() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni0326", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni032E
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni032E() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni032E", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni032F
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni032F() throws Exception {

        assertEquals(0, reader.mapCharCodeToWidth("uni032F", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni2014.alt1
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2014alt1() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("uni2014.alt1", 0,
            (short) 3, (short) 1));
    }

    /**
     * test uni2014.alt2
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2014alt2() throws Exception {

        assertEquals(667, reader.mapCharCodeToWidth("uni2014.alt2", 0,
            (short) 3, (short) 1));
    }

    /**
     * test uni2127
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2127() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("uni2127", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni2190
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2190() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("uni2190", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni2191
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2191() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("uni2191", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni2192
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2192() throws Exception {

        assertEquals(1000, reader.mapCharCodeToWidth("uni2192", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni2193
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni2193() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("uni2193", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uni266A
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuni266A() throws Exception {

        assertEquals(611, reader.mapCharCodeToWidth("uni266A", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uogonek
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuogonek() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uogonek", 0, (short) 3,
            (short) 1));
    }

    /**
     * test uring
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxuring() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("uring", 0, (short) 3,
            (short) 1));
    }

    /**
     * test utilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxutilde() throws Exception {

        assertEquals(556, reader.mapCharCodeToWidth("utilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test v
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxv() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("v", 0, (short) 3,
            (short) 1));
    }

    /**
     * test w
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxw() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("w", 0, (short) 3,
            (short) 1));
    }

    /**
     * test wacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwacute() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test wcircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwcircumflex() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wcircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test wdieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwdieresis() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wdieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test wgrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwgrave() throws Exception {

        assertEquals(722, reader.mapCharCodeToWidth("wgrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test won
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxwon() throws Exception {

        assertEquals(1028, reader.mapCharCodeToWidth("won", 0, (short) 3,
            (short) 1));
    }

    /**
     * test x
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxx() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("x", 0, (short) 3,
            (short) 1));
    }

    /**
     * test y
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxy() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("y", 0, (short) 3,
            (short) 1));
    }

    /**
     * test yacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyacute() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("yacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ycircumflex
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxycircumflex() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ycircumflex", 0,
            (short) 3, (short) 1));
    }

    /**
     * test ydieresis
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxydieresis() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ydieresis", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ydotbelow
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxydotbelow() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ydotbelow", 0, (short) 3,
            (short) 1));
    }

    /**
     * test yen
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyen() throws Exception {

        assertEquals(750, reader.mapCharCodeToWidth("yen", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ygrave
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxygrave() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ygrave", 0, (short) 3,
            (short) 1));
    }

    /**
     * test yhookabove
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxyhookabove() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("yhookabove", 0, (short) 3,
            (short) 1));
    }

    /**
     * test ytilde
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxytilde() throws Exception {

        assertEquals(528, reader.mapCharCodeToWidth("ytilde", 0, (short) 3,
            (short) 1));
    }

    /**
     * test z
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxz() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("z", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zacute
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzacute() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("zacute", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zcaron
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzcaron() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("zcaron", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zdotaccent
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzdotaccent() throws Exception {

        assertEquals(444, reader.mapCharCodeToWidth("zdotaccent", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zero
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzero() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("zero", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zero.oldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzerooldstyle() throws Exception {

        assertEquals(570, reader.mapCharCodeToWidth("zero.oldstyle", 0,
            (short) 3, (short) 1));
    }

    /**
     * test zero.prop
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzeroprop() throws Exception {

        assertEquals(569, reader.mapCharCodeToWidth("zero.prop", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zero.slash
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzeroslash() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("zero.slash", 0, (short) 3,
            (short) 1));
    }

    /**
     * test zero.taboldstyle
     * 
     * @throws Exception if an error occurred.
     */
    public void testMtxzerotaboldstyle() throws Exception {

        assertEquals(500, reader.mapCharCodeToWidth("zero.taboldstyle", 0,
            (short) 3, (short) 1));
    }

}
