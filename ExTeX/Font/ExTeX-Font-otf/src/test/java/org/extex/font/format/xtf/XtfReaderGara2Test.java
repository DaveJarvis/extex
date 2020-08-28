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

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests for the <code>XtfReader</code>.
 *
 * The test use the data from the <code>ttx</code> output.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderGara2Test {

    private final XtfReader reader;

    /**
     * Creates a new object.
     *
     * @throws IOException if an error occurred.
     */
    public XtfReaderGara2Test() throws IOException {
        reader = new XtfReader("../ExTeX-Font-otf/src/font/Gara.ttf");
    }

    /**
     * test 01.
     */
    @Test
    public void test01() {

        Assert.assertNotNull( reader );
    }

    /**
     * test 02.
     */
    @Test
    public void test02() {

        Assert.assertEquals( "Garamond", reader.getFontFamilyName() );
        Assert.assertEquals( 662, reader.getNumberOfGlyphs() );
    }

    /**
     * test a
     */
    @Test
    public void testMtxa() {

        Assert.assertEquals( 832, reader.mapCharCodeToWidth( "a", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test A
     */
    @Test
    public void testMtxA() {

        Assert.assertEquals( 1387, reader.mapCharCodeToWidth( "A", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test aacute
     */
    @Test
    public void testMtxaacute() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "aacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Aacute
     */
    @Test
    public void testMtxAacute() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Aacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test abreve
     */
    @Test
    public void testMtxabreve() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "abreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Abreve
     */
    @Test
    public void testMtxAbreve() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Abreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test acircumflex
     */
    @Test
    public void testMtxacircumflex() {

        Assert.assertEquals( 832, reader.mapCharCodeToWidth( "acircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Acircumflex
     */
    @Test
    public void testMtxAcircumflex() {

        Assert.assertEquals( 1387, reader.mapCharCodeToWidth( "Acircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test acute
     */
    @Test
    public void testMtxacute() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "acute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test adieresis
     */
    @Test
    public void testMtxadieresis() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "adieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Adieresis
     */
    @Test
    public void testMtxAdieresis() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Adieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ae
     */
    @Test
    public void testMtxae() {

        Assert.assertEquals( 1195,
                             reader.mapCharCodeToWidth( "ae", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test AE
     */
    @Test
    public void testMtxAE() {

        Assert.assertEquals( 1749,
                             reader.mapCharCodeToWidth( "AE", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test aeacute
     */
    @Test
    public void testMtxaeacute() {

        Assert.assertEquals( 1195,
                             reader.mapCharCodeToWidth( "aeacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test AEacute
     */
    @Test
    public void testMtxAEacute() {

        Assert.assertEquals( 1749,
                             reader.mapCharCodeToWidth( "AEacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii00208
     */
    @Test
    public void testMtxafii00208() {

        Assert.assertEquals( 1536,
                             reader.mapCharCodeToWidth( "afii00208",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii08941
     */
    @Test
    public void testMtxafii08941() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "afii08941",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10017
     */
    @Test
    public void testMtxafii10017() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "afii10017",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10018
     */
    @Test
    public void testMtxafii10018() {

        Assert.assertEquals( 1275,
                             reader.mapCharCodeToWidth( "afii10018",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10019
     */
    @Test
    public void testMtxafii10019() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "afii10019",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10020
     */
    @Test
    public void testMtxafii10020() {

        Assert.assertEquals( 1105,
                             reader.mapCharCodeToWidth( "afii10020",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10021
     */
    @Test
    public void testMtxafii10021() {

        Assert.assertEquals( 1340,
                             reader.mapCharCodeToWidth( "afii10021",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10022
     */
    @Test
    public void testMtxafii10022() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "afii10022",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10023
     */
    @Test
    public void testMtxafii10023() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "afii10023",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10024
     */
    @Test
    public void testMtxafii10024() {

        Assert.assertEquals( 2069,
                             reader.mapCharCodeToWidth( "afii10024",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10025
     */
    @Test
    public void testMtxafii10025() {

        Assert.assertEquals( 1103,
                             reader.mapCharCodeToWidth( "afii10025",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10026
     */
    @Test
    public void testMtxafii10026() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "afii10026",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10027
     */
    @Test
    public void testMtxafii10027() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "afii10027",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10028
     */
    @Test
    public void testMtxafii10028() {

        Assert.assertEquals( 1351,
                             reader.mapCharCodeToWidth( "afii10028",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10029
     */
    @Test
    public void testMtxafii10029() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "afii10029",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10030
     */
    @Test
    public void testMtxafii10030() {

        Assert.assertEquals( 1707,
                             reader.mapCharCodeToWidth( "afii10030",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10031
     */
    @Test
    public void testMtxafii10031() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "afii10031",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10032
     */
    @Test
    public void testMtxafii10032() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "afii10032",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10033
     */
    @Test
    public void testMtxafii10033() {

        Assert.assertEquals( 1538,
                             reader.mapCharCodeToWidth( "afii10033",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10034
     */
    @Test
    public void testMtxafii10034() {

        Assert.assertEquals( 1152,
                             reader.mapCharCodeToWidth( "afii10034",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10035
     */
    @Test
    public void testMtxafii10035() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "afii10035",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10036
     */
    @Test
    public void testMtxafii10036() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "afii10036",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10037
     */
    @Test
    public void testMtxafii10037() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "afii10037",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10038
     */
    @Test
    public void testMtxafii10038() {

        Assert.assertEquals( 1508,
                             reader.mapCharCodeToWidth( "afii10038",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10039
     */
    @Test
    public void testMtxafii10039() {

        Assert.assertEquals( 1429,
                             reader.mapCharCodeToWidth( "afii10039",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10040
     */
    @Test
    public void testMtxafii10040() {

        Assert.assertEquals( 1543,
                             reader.mapCharCodeToWidth( "afii10040",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10041
     */
    @Test
    public void testMtxafii10041() {

        Assert.assertEquals( 1355,
                             reader.mapCharCodeToWidth( "afii10041",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10042
     */
    @Test
    public void testMtxafii10042() {

        Assert.assertEquals( 2148,
                             reader.mapCharCodeToWidth( "afii10042",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10043
     */
    @Test
    public void testMtxafii10043() {

        Assert.assertEquals( 2115,
                             reader.mapCharCodeToWidth( "afii10043",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10044
     */
    @Test
    public void testMtxafii10044() {

        Assert.assertEquals( 1570,
                             reader.mapCharCodeToWidth( "afii10044",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10045
     */
    @Test
    public void testMtxafii10045() {

        Assert.assertEquals( 1859,
                             reader.mapCharCodeToWidth( "afii10045",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10046
     */
    @Test
    public void testMtxafii10046() {

        Assert.assertEquals( 1275,
                             reader.mapCharCodeToWidth( "afii10046",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10047
     */
    @Test
    public void testMtxafii10047() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "afii10047",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10048
     */
    @Test
    public void testMtxafii10048() {

        Assert.assertEquals( 2275,
                             reader.mapCharCodeToWidth( "afii10048",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10049
     */
    @Test
    public void testMtxafii10049() {

        Assert.assertEquals( 1280,
                             reader.mapCharCodeToWidth( "afii10049",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10050
     */
    @Test
    public void testMtxafii10050() {

        Assert.assertEquals( 1004,
                             reader.mapCharCodeToWidth( "afii10050",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10051
     */
    @Test
    public void testMtxafii10051() {

        Assert.assertEquals( 1547,
                             reader.mapCharCodeToWidth( "afii10051",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10052
     */
    @Test
    public void testMtxafii10052() {

        Assert.assertEquals( 1105,
                             reader.mapCharCodeToWidth( "afii10052",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10053
     */
    @Test
    public void testMtxafii10053() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "afii10053",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10054
     */
    @Test
    public void testMtxafii10054() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "afii10054",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10055
     */
    @Test
    public void testMtxafii10055() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "afii10055",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10056
     */
    @Test
    public void testMtxafii10056() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "afii10056",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10057
     */
    @Test
    public void testMtxafii10057() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "afii10057",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10058
     */
    @Test
    public void testMtxafii10058() {

        Assert.assertEquals( 1962,
                             reader.mapCharCodeToWidth( "afii10058",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10059
     */
    @Test
    public void testMtxafii10059() {

        Assert.assertEquals( 2023,
                             reader.mapCharCodeToWidth( "afii10059",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10060
     */
    @Test
    public void testMtxafii10060() {

        Assert.assertEquals( 1549,
                             reader.mapCharCodeToWidth( "afii10060",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10061
     */
    @Test
    public void testMtxafii10061() {

        Assert.assertEquals( 1351,
                             reader.mapCharCodeToWidth( "afii10061",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10062
     */
    @Test
    public void testMtxafii10062() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "afii10062",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10065
     */
    @Test
    public void testMtxafii10065() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "afii10065",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10066
     */
    @Test
    public void testMtxafii10066() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "afii10066",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10067
     */
    @Test
    public void testMtxafii10067() {

        Assert.assertEquals( 876,
                             reader.mapCharCodeToWidth( "afii10067",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10068
     */
    @Test
    public void testMtxafii10068() {

        Assert.assertEquals( 729,
                             reader.mapCharCodeToWidth( "afii10068",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10069
     */
    @Test
    public void testMtxafii10069() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "afii10069",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10070
     */
    @Test
    public void testMtxafii10070() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "afii10070",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10071
     */
    @Test
    public void testMtxafii10071() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "afii10071",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10072
     */
    @Test
    public void testMtxafii10072() {

        Assert.assertEquals( 1287,
                             reader.mapCharCodeToWidth( "afii10072",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10073
     */
    @Test
    public void testMtxafii10073() {

        Assert.assertEquals( 789,
                             reader.mapCharCodeToWidth( "afii10073",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10074
     */
    @Test
    public void testMtxafii10074() {

        Assert.assertEquals( 1112,
                             reader.mapCharCodeToWidth( "afii10074",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10075
     */
    @Test
    public void testMtxafii10075() {

        Assert.assertEquals( 1112,
                             reader.mapCharCodeToWidth( "afii10075",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10076
     */
    @Test
    public void testMtxafii10076() {

        Assert.assertEquals( 888,
                             reader.mapCharCodeToWidth( "afii10076",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10077
     */
    @Test
    public void testMtxafii10077() {

        Assert.assertEquals( 1009,
                             reader.mapCharCodeToWidth( "afii10077",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10078
     */
    @Test
    public void testMtxafii10078() {

        Assert.assertEquals( 1205,
                             reader.mapCharCodeToWidth( "afii10078",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10079
     */
    @Test
    public void testMtxafii10079() {

        Assert.assertEquals( 1112,
                             reader.mapCharCodeToWidth( "afii10079",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10080
     */
    @Test
    public void testMtxafii10080() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "afii10080",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10081
     */
    @Test
    public void testMtxafii10081() {

        Assert.assertEquals( 1112,
                             reader.mapCharCodeToWidth( "afii10081",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10082
     */
    @Test
    public void testMtxafii10082() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "afii10082",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10083
     */
    @Test
    public void testMtxafii10083() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "afii10083",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10084
     */
    @Test
    public void testMtxafii10084() {

        Assert.assertEquals( 841,
                             reader.mapCharCodeToWidth( "afii10084",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10085
     */
    @Test
    public void testMtxafii10085() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "afii10085",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10086
     */
    @Test
    public void testMtxafii10086() {

        Assert.assertEquals( 1564,
                             reader.mapCharCodeToWidth( "afii10086",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10087
     */
    @Test
    public void testMtxafii10087() {

        Assert.assertEquals( 939,
                             reader.mapCharCodeToWidth( "afii10087",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10088
     */
    @Test
    public void testMtxafii10088() {

        Assert.assertEquals( 1112,
                             reader.mapCharCodeToWidth( "afii10088",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10089
     */
    @Test
    public void testMtxafii10089() {

        Assert.assertEquals( 1044,
                             reader.mapCharCodeToWidth( "afii10089",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10090
     */
    @Test
    public void testMtxafii10090() {

        Assert.assertEquals( 1626,
                             reader.mapCharCodeToWidth( "afii10090",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10091
     */
    @Test
    public void testMtxafii10091() {

        Assert.assertEquals( 1626,
                             reader.mapCharCodeToWidth( "afii10091",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10092
     */
    @Test
    public void testMtxafii10092() {

        Assert.assertEquals( 1070,
                             reader.mapCharCodeToWidth( "afii10092",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10093
     */
    @Test
    public void testMtxafii10093() {

        Assert.assertEquals( 1278,
                             reader.mapCharCodeToWidth( "afii10093",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10094
     */
    @Test
    public void testMtxafii10094() {

        Assert.assertEquals( 888,
                             reader.mapCharCodeToWidth( "afii10094",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10095
     */
    @Test
    public void testMtxafii10095() {

        Assert.assertEquals( 829,
                             reader.mapCharCodeToWidth( "afii10095",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10096
     */
    @Test
    public void testMtxafii10096() {

        Assert.assertEquals( 1595,
                             reader.mapCharCodeToWidth( "afii10096",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10097
     */
    @Test
    public void testMtxafii10097() {

        Assert.assertEquals( 843,
                             reader.mapCharCodeToWidth( "afii10097",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10098
     */
    @Test
    public void testMtxafii10098() {

        Assert.assertEquals( 711,
                             reader.mapCharCodeToWidth( "afii10098",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10099
     */
    @Test
    public void testMtxafii10099() {

        Assert.assertEquals( 1021,
                             reader.mapCharCodeToWidth( "afii10099",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10100
     */
    @Test
    public void testMtxafii10100() {

        Assert.assertEquals( 729,
                             reader.mapCharCodeToWidth( "afii10100",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10101
     */
    @Test
    public void testMtxafii10101() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "afii10101",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10102
     */
    @Test
    public void testMtxafii10102() {

        Assert.assertEquals( 747,
                             reader.mapCharCodeToWidth( "afii10102",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10103
     */
    @Test
    public void testMtxafii10103() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "afii10103",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10104
     */
    @Test
    public void testMtxafii10104() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "afii10104",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10105
     */
    @Test
    public void testMtxafii10105() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "afii10105",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10106
     */
    @Test
    public void testMtxafii10106() {

        Assert.assertEquals( 1397,
                             reader.mapCharCodeToWidth( "afii10106",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10107
     */
    @Test
    public void testMtxafii10107() {

        Assert.assertEquals( 1454,
                             reader.mapCharCodeToWidth( "afii10107",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10108
     */
    @Test
    public void testMtxafii10108() {

        Assert.assertEquals( 1036,
                             reader.mapCharCodeToWidth( "afii10108",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10109
     */
    @Test
    public void testMtxafii10109() {

        Assert.assertEquals( 888,
                             reader.mapCharCodeToWidth( "afii10109",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10110
     */
    @Test
    public void testMtxafii10110() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "afii10110",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10145
     */
    @Test
    public void testMtxafii10145() {

        Assert.assertEquals( 1538,
                             reader.mapCharCodeToWidth( "afii10145",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii10193
     */
    @Test
    public void testMtxafii10193() {

        Assert.assertEquals( 1112,
                             reader.mapCharCodeToWidth( "afii10193",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii61248
     */
    @Test
    public void testMtxafii61248() {

        Assert.assertEquals( 1625,
                             reader.mapCharCodeToWidth( "afii61248",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii61289
     */
    @Test
    public void testMtxafii61289() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "afii61289",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test afii61352
     */
    @Test
    public void testMtxafii61352() {

        Assert.assertEquals( 2103,
                             reader.mapCharCodeToWidth( "afii61352",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test agrave
     */
    @Test
    public void testMtxagrave() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "agrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Agrave
     */
    @Test
    public void testMtxAgrave() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Agrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test alpha
     */
    @Test
    public void testMtxalpha() {

        Assert.assertEquals( 989,
                             reader.mapCharCodeToWidth( "alpha", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Alpha
     */
    @Test
    public void testMtxAlpha() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Alpha", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test alphatonos
     */
    @Test
    public void testMtxalphatonos() {

        Assert.assertEquals( 989,
                             reader.mapCharCodeToWidth( "alphatonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Alphatonos
     */
    @Test
    public void testMtxAlphatonos() {

        Assert.assertEquals( 1387, reader.mapCharCodeToWidth( "Alphatonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test amacron
     */
    @Test
    public void testMtxamacron() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "amacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Amacron
     */
    @Test
    public void testMtxAmacron() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Amacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ampersand
     */
    @Test
    public void testMtxampersand() {

        Assert.assertEquals( 1493,
                             reader.mapCharCodeToWidth( "ampersand",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test anoteleia
     */
    @Test
    public void testMtxanoteleia() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "anoteleia",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test aogonek
     */
    @Test
    public void testMtxaogonek() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "aogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Aogonek
     */
    @Test
    public void testMtxAogonek() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "Aogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test approxequal
     */
    @Test
    public void testMtxapproxequal() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "approxequal",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test aring
     */
    @Test
    public void testMtxaring() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "aring", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Aring
     */
    @Test
    public void testMtxAring() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Aring", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test aringacute
     */
    @Test
    public void testMtxaringacute() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "aringacute",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Aringacute
     */
    @Test
    public void testMtxAringacute() {

        Assert.assertEquals( 1387, reader.mapCharCodeToWidth( "Aringacute",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test arrowboth
     */
    @Test
    public void testMtxarrowboth() {

        Assert.assertEquals( 2048,
                             reader.mapCharCodeToWidth( "arrowboth",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test arrowdown
     */
    @Test
    public void testMtxarrowdown() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "arrowdown",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test arrowleft
     */
    @Test
    public void testMtxarrowleft() {

        Assert.assertEquals( 2048,
                             reader.mapCharCodeToWidth( "arrowleft",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test arrowright
     */
    @Test
    public void testMtxarrowright() {

        Assert.assertEquals( 2048, reader.mapCharCodeToWidth( "arrowright",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test arrowup
     */
    @Test
    public void testMtxarrowup() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "arrowup", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test arrowupdn
     */
    @Test
    public void testMtxarrowupdn() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "arrowupdn",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test arrowupdnbse
     */
    @Test
    public void testMtxarrowupdnbse() {

        Assert.assertEquals( 1024, reader.mapCharCodeToWidth( "arrowupdnbse",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test asciicircum
     */
    @Test
    public void testMtxasciicircum() {

        Assert.assertEquals( 1024, reader.mapCharCodeToWidth( "asciicircum",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test asciitilde
     */
    @Test
    public void testMtxasciitilde() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "asciitilde",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test asterisk
     */
    @Test
    public void testMtxasterisk() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "asterisk",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test at
     */
    @Test
    public void testMtxat() {

        Assert.assertEquals( 1877,
                             reader.mapCharCodeToWidth( "at", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test atilde
     */
    @Test
    public void testMtxatilde() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "atilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Atilde
     */
    @Test
    public void testMtxAtilde() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Atilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test b
     */
    @Test
    public void testMtxb() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "b", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test B
     */
    @Test
    public void testMtxB() {

        Assert.assertEquals( 1259, reader.mapCharCodeToWidth( "B", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test backslash
     */
    @Test
    public void testMtxbackslash() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "backslash",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test bar
     */
    @Test
    public void testMtxbar() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "bar", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test beta
     */
    @Test
    public void testMtxbeta() {

        Assert.assertEquals( 1006,
                             reader.mapCharCodeToWidth( "beta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Beta
     */
    @Test
    public void testMtxBeta() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "Beta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test block
     */
    @Test
    public void testMtxblock() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "block", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test braceleft
     */
    @Test
    public void testMtxbraceleft() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "braceleft",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test braceright
     */
    @Test
    public void testMtxbraceright() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "braceright",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test bracketleft
     */
    @Test
    public void testMtxbracketleft() {

        Assert.assertEquals( 555, reader.mapCharCodeToWidth( "bracketleft",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test bracketright
     */
    @Test
    public void testMtxbracketright() {

        Assert.assertEquals( 555, reader.mapCharCodeToWidth( "bracketright",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test breve
     */
    @Test
    public void testMtxbreve() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "breve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test brokenbar
     */
    @Test
    public void testMtxbrokenbar() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "brokenbar",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test bullet
     */
    @Test
    public void testMtxbullet() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "bullet", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test c
     */
    @Test
    public void testMtxc() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "c", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test C
     */
    @Test
    public void testMtxC() {

        Assert.assertEquals( 1301, reader.mapCharCodeToWidth( "C", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test cacute
     */
    @Test
    public void testMtxcacute() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "cacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Cacute
     */
    @Test
    public void testMtxCacute() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "Cacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test caron
     */
    @Test
    public void testMtxcaron() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "caron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ccaron
     */
    @Test
    public void testMtxccaron() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "ccaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ccaron
     */
    @Test
    public void testMtxCcaron() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "Ccaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ccedilla
     */
    @Test
    public void testMtxccedilla() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "ccedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ccedilla
     */
    @Test
    public void testMtxCcedilla() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "Ccedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ccircumflex
     */
    @Test
    public void testMtxccircumflex() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "ccircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Ccircumflex
     */
    @Test
    public void testMtxCcircumflex() {

        Assert.assertEquals( 1301, reader.mapCharCodeToWidth( "Ccircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test cdot
     */
    @Test
    public void testMtxcdot() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "cdot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Cdot
     */
    @Test
    public void testMtxCdot() {

        Assert.assertEquals( 1301,
                             reader.mapCharCodeToWidth( "Cdot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test cedilla
     */
    @Test
    public void testMtxcedilla() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "cedilla", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test cent
     */
    @Test
    public void testMtxcent() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "cent", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test chi
     */
    @Test
    public void testMtxchi() {

        Assert.assertEquals( 976,
                             reader.mapCharCodeToWidth( "chi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Chi
     */
    @Test
    public void testMtxChi() {

        Assert.assertEquals( 1429,
                             reader.mapCharCodeToWidth( "Chi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test circle
     */
    @Test
    public void testMtxcircle() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "circle", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test circumflex
     */
    @Test
    public void testMtxcircumflex() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "circumflex",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test club
     */
    @Test
    public void testMtxclub() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "club", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test colon
     */
    @Test
    public void testMtxcolon() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "colon", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test comma
     */
    @Test
    public void testMtxcomma() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "comma", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test commaaccent
     */
    @Test
    public void testMtxcommaaccent() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "commaaccent",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test copyright
     */
    @Test
    public void testMtxcopyright() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "copyright",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test currency
     */
    @Test
    public void testMtxcurrency() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "currency",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test d
     */
    @Test
    public void testMtxd() {

        Assert.assertEquals( 1024, reader.mapCharCodeToWidth( "d", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test D
     */
    @Test
    public void testMtxD() {

        Assert.assertEquals( 1579, reader.mapCharCodeToWidth( "D", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test dagger
     */
    @Test
    public void testMtxdagger() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "dagger", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test daggerdbl
     */
    @Test
    public void testMtxdaggerdbl() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "daggerdbl",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dcaron
     */
    @Test
    public void testMtxdcaron() {

        Assert.assertEquals( 1323,
                             reader.mapCharCodeToWidth( "dcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Dcaron
     */
    @Test
    public void testMtxDcaron() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Dcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test degree
     */
    @Test
    public void testMtxdegree() {

        Assert.assertEquals( 811,
                             reader.mapCharCodeToWidth( "degree", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test delta
     */
    @Test
    public void testMtxdelta() {

        Assert.assertEquals( 985,
                             reader.mapCharCodeToWidth( "delta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test diamond
     */
    @Test
    public void testMtxdiamond() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "diamond", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dieresis
     */
    @Test
    public void testMtxdieresis() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "dieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dieresistonos
     */
    @Test
    public void testMtxdieresistonos() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "dieresistonos",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test divide
     */
    @Test
    public void testMtxdivide() {

        Assert.assertEquals( 1124,
                             reader.mapCharCodeToWidth( "divide", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dkshade
     */
    @Test
    public void testMtxdkshade() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "dkshade", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dmacron
     */
    @Test
    public void testMtxdmacron() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "dmacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dnblock
     */
    @Test
    public void testMtxdnblock() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "dnblock", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dollar
     */
    @Test
    public void testMtxdollar() {

        Assert.assertEquals( 917,
                             reader.mapCharCodeToWidth( "dollar", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dotaccent
     */
    @Test
    public void testMtxdotaccent() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "dotaccent",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test dotlessi
     */
    @Test
    public void testMtxdotlessi() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "dotlessi",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Dslash
     */
    @Test
    public void testMtxDslash() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Dslash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test e
     */
    @Test
    public void testMtxe() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "e", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test E
     */
    @Test
    public void testMtxE() {

        Assert.assertEquals( 1344, reader.mapCharCodeToWidth( "E", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test eacute
     */
    @Test
    public void testMtxeacute() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "eacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Eacute
     */
    @Test
    public void testMtxEacute() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Eacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ebreve
     */
    @Test
    public void testMtxebreve() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "ebreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ebreve
     */
    @Test
    public void testMtxEbreve() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Ebreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ecaron
     */
    @Test
    public void testMtxecaron() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "ecaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ecaron
     */
    @Test
    public void testMtxEcaron() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Ecaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ecircumflex
     */
    @Test
    public void testMtxecircumflex() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "ecircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Ecircumflex
     */
    @Test
    public void testMtxEcircumflex() {

        Assert.assertEquals( 1344, reader.mapCharCodeToWidth( "Ecircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test edieresis
     */
    @Test
    public void testMtxedieresis() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "edieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Edieresis
     */
    @Test
    public void testMtxEdieresis() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Edieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test edot
     */
    @Test
    public void testMtxedot() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "edot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Edot
     */
    @Test
    public void testMtxEdot() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Edot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test egrave
     */
    @Test
    public void testMtxegrave() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "egrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Egrave
     */
    @Test
    public void testMtxEgrave() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Egrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test eight
     */
    @Test
    public void testMtxeight() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "eight", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test eightsuperior
     */
    @Test
    public void testMtxeightsuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "eightsuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test ellipsis
     */
    @Test
    public void testMtxellipsis() {

        Assert.assertEquals( 2048,
                             reader.mapCharCodeToWidth( "ellipsis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test emacron
     */
    @Test
    public void testMtxemacron() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "emacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Emacron
     */
    @Test
    public void testMtxEmacron() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Emacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test emdash
     */
    @Test
    public void testMtxemdash() {

        Assert.assertEquals( 2048,
                             reader.mapCharCodeToWidth( "emdash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test endash
     */
    @Test
    public void testMtxendash() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "endash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test eng
     */
    @Test
    public void testMtxeng() {

        Assert.assertEquals( 968,
                             reader.mapCharCodeToWidth( "eng", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Eng
     */
    @Test
    public void testMtxEng() {

        Assert.assertEquals( 1535,
                             reader.mapCharCodeToWidth( "Eng", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test eogonek
     */
    @Test
    public void testMtxeogonek() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "eogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Eogonek
     */
    @Test
    public void testMtxEogonek() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Eogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test epsilon
     */
    @Test
    public void testMtxepsilon() {

        Assert.assertEquals( 766,
                             reader.mapCharCodeToWidth( "epsilon", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Epsilon
     */
    @Test
    public void testMtxEpsilon() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Epsilon", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test epsilontonos
     */
    @Test
    public void testMtxepsilontonos() {

        Assert.assertEquals( 766, reader.mapCharCodeToWidth( "epsilontonos",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Epsilontonos
     */
    @Test
    public void testMtxEpsilontonos() {

        Assert.assertEquals( 1477, reader.mapCharCodeToWidth( "Epsilontonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test equal
     */
    @Test
    public void testMtxequal() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "equal", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test equivalence
     */
    @Test
    public void testMtxequivalence() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "equivalence",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test estimated
     */
    @Test
    public void testMtxestimated() {

        Assert.assertEquals( 1229,
                             reader.mapCharCodeToWidth( "estimated",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test eta
     */
    @Test
    public void testMtxeta() {

        Assert.assertEquals( 1006,
                             reader.mapCharCodeToWidth( "eta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Eta
     */
    @Test
    public void testMtxEta() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "Eta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test etatonos
     */
    @Test
    public void testMtxetatonos() {

        Assert.assertEquals( 1006,
                             reader.mapCharCodeToWidth( "etatonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Etatonos
     */
    @Test
    public void testMtxEtatonos() {

        Assert.assertEquals( 1651,
                             reader.mapCharCodeToWidth( "Etatonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test eth
     */
    @Test
    public void testMtxeth() {

        Assert.assertEquals( 1067,
                             reader.mapCharCodeToWidth( "eth", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Eth
     */
    @Test
    public void testMtxEth() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Eth", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test exclam
     */
    @Test
    public void testMtxexclam() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "exclam", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test exclamdbl
     */
    @Test
    public void testMtxexclamdbl() {

        Assert.assertEquals( 811,
                             reader.mapCharCodeToWidth( "exclamdbl",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test exclamdown
     */
    @Test
    public void testMtxexclamdown() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "exclamdown",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test f
     */
    @Test
    public void testMtxf() {

        Assert.assertEquals( 661, reader.mapCharCodeToWidth( "f", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test F
     */
    @Test
    public void testMtxF() {

        Assert.assertEquals( 1152, reader.mapCharCodeToWidth( "F", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test female
     */
    @Test
    public void testMtxfemale() {

        Assert.assertEquals( 1536,
                             reader.mapCharCodeToWidth( "female", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test fi
     */
    @Test
    public void testMtxfi() {

        Assert.assertEquals( 1109,
                             reader.mapCharCodeToWidth( "fi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test fi1
     */
    @Test
    public void testMtxfi1() {

        Assert.assertEquals( 1109,
                             reader.mapCharCodeToWidth( "fi1", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test filledbox
     */
    @Test
    public void testMtxfilledbox() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "filledbox",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test filledrect
     */
    @Test
    public void testMtxfilledrect() {

        Assert.assertEquals( 2048, reader.mapCharCodeToWidth( "filledrect",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test five
     */
    @Test
    public void testMtxfive() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "five", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test fiveeighths
     */
    @Test
    public void testMtxfiveeighths() {

        Assert.assertEquals( 1685, reader.mapCharCodeToWidth( "fiveeighths",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test fivesuperior
     */
    @Test
    public void testMtxfivesuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "fivesuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test fl
     */
    @Test
    public void testMtxfl() {

        Assert.assertEquals( 1109,
                             reader.mapCharCodeToWidth( "fl", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test fl1
     */
    @Test
    public void testMtxfl1() {

        Assert.assertEquals( 1109,
                             reader.mapCharCodeToWidth( "fl1", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test florin
     */
    @Test
    public void testMtxflorin() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "florin", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test four
     */
    @Test
    public void testMtxfour() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "four", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test foursuperior
     */
    @Test
    public void testMtxfoursuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "foursuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test fraction
     */
    @Test
    public void testMtxfraction() {

        Assert.assertEquals( 384,
                             reader.mapCharCodeToWidth( "fraction",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test fraction1
     */
    @Test
    public void testMtxfraction1() {

        Assert.assertEquals( 384,
                             reader.mapCharCodeToWidth( "fraction1",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test franc
     */
    @Test
    public void testMtxfranc() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "franc", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test g
     */
    @Test
    public void testMtxg() {

        Assert.assertEquals( 917, reader.mapCharCodeToWidth( "g", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test G
     */
    @Test
    public void testMtxG() {

        Assert.assertEquals( 1579, reader.mapCharCodeToWidth( "G", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test gamma
     */
    @Test
    public void testMtxgamma() {

        Assert.assertEquals( 845,
                             reader.mapCharCodeToWidth( "gamma", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Gamma
     */
    @Test
    public void testMtxGamma() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "Gamma", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test gbreve
     */
    @Test
    public void testMtxgbreve() {

        Assert.assertEquals( 917,
                             reader.mapCharCodeToWidth( "gbreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Gbreve
     */
    @Test
    public void testMtxGbreve() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Gbreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test gcedilla
     */
    @Test
    public void testMtxgcedilla() {

        Assert.assertEquals( 917,
                             reader.mapCharCodeToWidth( "gcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Gcedilla
     */
    @Test
    public void testMtxGcedilla() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Gcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test gcircumflex
     */
    @Test
    public void testMtxgcircumflex() {

        Assert.assertEquals( 917, reader.mapCharCodeToWidth( "gcircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Gcircumflex
     */
    @Test
    public void testMtxGcircumflex() {

        Assert.assertEquals( 1579, reader.mapCharCodeToWidth( "Gcircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test gdot
     */
    @Test
    public void testMtxgdot() {

        Assert.assertEquals( 917,
                             reader.mapCharCodeToWidth( "gdot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Gdot
     */
    @Test
    public void testMtxGdot() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Gdot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test germandbls
     */
    @Test
    public void testMtxgermandbls() {

        Assert.assertEquals( 1024, reader.mapCharCodeToWidth( "germandbls",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test grave
     */
    @Test
    public void testMtxgrave() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "grave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test greater
     */
    @Test
    public void testMtxgreater() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "greater", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test greaterequal
     */
    @Test
    public void testMtxgreaterequal() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "greaterequal",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test guillemotleft
     */
    @Test
    public void testMtxguillemotleft() {

        Assert.assertEquals( 747, reader.mapCharCodeToWidth( "guillemotleft",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test guillemotright
     */
    @Test
    public void testMtxguillemotright() {

        Assert.assertEquals( 747,
                             reader.mapCharCodeToWidth( "guillemotright",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test guilsinglleft
     */
    @Test
    public void testMtxguilsinglleft() {

        Assert.assertEquals( 405, reader.mapCharCodeToWidth( "guilsinglleft",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test guilsinglright
     */
    @Test
    public void testMtxguilsinglright() {

        Assert.assertEquals( 405,
                             reader.mapCharCodeToWidth( "guilsinglright",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test h
     */
    @Test
    public void testMtxh() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "h", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test H
     */
    @Test
    public void testMtxH() {

        Assert.assertEquals( 1557, reader.mapCharCodeToWidth( "H", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test H18533
     */
    @Test
    public void testMtxH18533() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "H18533", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test H18543
     */
    @Test
    public void testMtxH18543() {

        Assert.assertEquals( 727,
                             reader.mapCharCodeToWidth( "H18543", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test H18551
     */
    @Test
    public void testMtxH18551() {

        Assert.assertEquals( 727,
                             reader.mapCharCodeToWidth( "H18551", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test H22073
     */
    @Test
    public void testMtxH22073() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "H22073", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test hbar
     */
    @Test
    public void testMtxhbar() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "hbar", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Hbar
     */
    @Test
    public void testMtxHbar() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "Hbar", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test hcircumflex
     */
    @Test
    public void testMtxhcircumflex() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "hcircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Hcircumflex
     */
    @Test
    public void testMtxHcircumflex() {

        Assert.assertEquals( 1557, reader.mapCharCodeToWidth( "Hcircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test heart
     */
    @Test
    public void testMtxheart() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "heart", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test house
     */
    @Test
    public void testMtxhouse() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "house", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test hungarumlaut
     */
    @Test
    public void testMtxhungarumlaut() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "hungarumlaut",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test hyphen
     */
    @Test
    public void testMtxhyphen() {

        Assert.assertEquals( 640,
                             reader.mapCharCodeToWidth( "hyphen", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test i
     */
    @Test
    public void testMtxi() {

        Assert.assertEquals( 469, reader.mapCharCodeToWidth( "i", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test I
     */
    @Test
    public void testMtxI() {

        Assert.assertEquals( 725, reader.mapCharCodeToWidth( "I", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test iacute
     */
    @Test
    public void testMtxiacute() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "iacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Iacute
     */
    @Test
    public void testMtxIacute() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Iacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ibreve
     */
    @Test
    public void testMtxibreve() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "ibreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ibreve
     */
    @Test
    public void testMtxIbreve() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Ibreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test icircumflex
     */
    @Test
    public void testMtxicircumflex() {

        Assert.assertEquals( 469, reader.mapCharCodeToWidth( "icircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Icircumflex
     */
    @Test
    public void testMtxIcircumflex() {

        Assert.assertEquals( 725, reader.mapCharCodeToWidth( "Icircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test idieresis
     */
    @Test
    public void testMtxidieresis() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "idieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Idieresis
     */
    @Test
    public void testMtxIdieresis() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Idieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test igrave
     */
    @Test
    public void testMtxigrave() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "igrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Igrave
     */
    @Test
    public void testMtxIgrave() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Igrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ij
     */
    @Test
    public void testMtxij() {

        Assert.assertEquals( 981, reader.mapCharCodeToWidth( "ij", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test IJ
     */
    @Test
    public void testMtxIJ() {

        Assert.assertEquals( 1408,
                             reader.mapCharCodeToWidth( "IJ", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test imacron
     */
    @Test
    public void testMtximacron() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "imacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Imacron
     */
    @Test
    public void testMtxImacron() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Imacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test infinity
     */
    @Test
    public void testMtxinfinity() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "infinity",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test integralbt
     */
    @Test
    public void testMtxintegralbt() {

        Assert.assertEquals( 1237, reader.mapCharCodeToWidth( "integralbt",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test integraltp
     */
    @Test
    public void testMtxintegraltp() {

        Assert.assertEquals( 1237, reader.mapCharCodeToWidth( "integraltp",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test intersection
     */
    @Test
    public void testMtxintersection() {

        Assert.assertEquals( 1472, reader.mapCharCodeToWidth( "intersection",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test invbullet
     */
    @Test
    public void testMtxinvbullet() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "invbullet",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test invcircle
     */
    @Test
    public void testMtxinvcircle() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "invcircle",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test invsmileface
     */
    @Test
    public void testMtxinvsmileface() {

        Assert.assertEquals( 2048, reader.mapCharCodeToWidth( "invsmileface",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test iogonek
     */
    @Test
    public void testMtxiogonek() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "iogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Iogonek
     */
    @Test
    public void testMtxIogonek() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Iogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test iota
     */
    @Test
    public void testMtxiota() {

        Assert.assertEquals( 468,
                             reader.mapCharCodeToWidth( "iota", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Iota
     */
    @Test
    public void testMtxIota() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Iota", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test iotadieresis
     */
    @Test
    public void testMtxiotadieresis() {

        Assert.assertEquals( 468, reader.mapCharCodeToWidth( "iotadieresis",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Iotadieresis
     */
    @Test
    public void testMtxIotadieresis() {

        Assert.assertEquals( 725, reader.mapCharCodeToWidth( "Iotadieresis",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test iotadieresistonos
     */
    @Test
    public void testMtxiotadieresistonos() {

        Assert.assertEquals( 468,
                             reader.mapCharCodeToWidth( "iotadieresistonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test iotatonos
     */
    @Test
    public void testMtxiotatonos() {

        Assert.assertEquals( 468,
                             reader.mapCharCodeToWidth( "iotatonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Iotatonos
     */
    @Test
    public void testMtxIotatonos() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "Iotatonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test itilde
     */
    @Test
    public void testMtxitilde() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "itilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Itilde
     */
    @Test
    public void testMtxItilde() {

        Assert.assertEquals( 725,
                             reader.mapCharCodeToWidth( "Itilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test j
     */
    @Test
    public void testMtxj() {

        Assert.assertEquals( 469, reader.mapCharCodeToWidth( "j", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test J
     */
    @Test
    public void testMtxJ() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "J", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test jcircumflex
     */
    @Test
    public void testMtxjcircumflex() {

        Assert.assertEquals( 469, reader.mapCharCodeToWidth( "jcircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Jcircumflex
     */
    @Test
    public void testMtxJcircumflex() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "Jcircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test k
     */
    @Test
    public void testMtxk() {

        Assert.assertEquals( 960, reader.mapCharCodeToWidth( "k", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test K
     */
    @Test
    public void testMtxK() {

        Assert.assertEquals( 1515, reader.mapCharCodeToWidth( "K", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test kappa
     */
    @Test
    public void testMtxkappa() {

        Assert.assertEquals( 924,
                             reader.mapCharCodeToWidth( "kappa", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Kappa
     */
    @Test
    public void testMtxKappa() {

        Assert.assertEquals( 1515,
                             reader.mapCharCodeToWidth( "Kappa", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test kcedilla
     */
    @Test
    public void testMtxkcedilla() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "kcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Kcedilla
     */
    @Test
    public void testMtxKcedilla() {

        Assert.assertEquals( 1515,
                             reader.mapCharCodeToWidth( "Kcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test kgreenlandic
     */
    @Test
    public void testMtxkgreenlandic() {

        Assert.assertEquals( 960, reader.mapCharCodeToWidth( "kgreenlandic",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test l
     */
    @Test
    public void testMtxl() {

        Assert.assertEquals( 469, reader.mapCharCodeToWidth( "l", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test L
     */
    @Test
    public void testMtxL() {

        Assert.assertEquals( 1173, reader.mapCharCodeToWidth( "L", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test lacute
     */
    @Test
    public void testMtxlacute() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "lacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Lacute
     */
    @Test
    public void testMtxLacute() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "Lacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lambda
     */
    @Test
    public void testMtxlambda() {

        Assert.assertEquals( 863,
                             reader.mapCharCodeToWidth( "lambda", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Lambda
     */
    @Test
    public void testMtxLambda() {

        Assert.assertEquals( 1387,
                             reader.mapCharCodeToWidth( "Lambda", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lcaron
     */
    @Test
    public void testMtxlcaron() {

        Assert.assertEquals( 768,
                             reader.mapCharCodeToWidth( "lcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Lcaron
     */
    @Test
    public void testMtxLcaron() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "Lcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lcedilla
     */
    @Test
    public void testMtxlcedilla() {

        Assert.assertEquals( 469,
                             reader.mapCharCodeToWidth( "lcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Lcedilla
     */
    @Test
    public void testMtxLcedilla() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "Lcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ldot
     */
    @Test
    public void testMtxldot() {

        Assert.assertEquals( 671,
                             reader.mapCharCodeToWidth( "ldot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ldot
     */
    @Test
    public void testMtxLdot() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "Ldot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test less
     */
    @Test
    public void testMtxless() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "less", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lessequal
     */
    @Test
    public void testMtxlessequal() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "lessequal",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lfblock
     */
    @Test
    public void testMtxlfblock() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "lfblock", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test logicalnot
     */
    @Test
    public void testMtxlogicalnot() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "logicalnot",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test longs
     */
    @Test
    public void testMtxlongs() {

        Assert.assertEquals( 639,
                             reader.mapCharCodeToWidth( "longs", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lozenge
     */
    @Test
    public void testMtxlozenge() {

        Assert.assertEquals( 1012,
                             reader.mapCharCodeToWidth( "lozenge", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test lslash
     */
    @Test
    public void testMtxlslash() {

        Assert.assertEquals( 640,
                             reader.mapCharCodeToWidth( "lslash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Lslash
     */
    @Test
    public void testMtxLslash() {

        Assert.assertEquals( 1237,
                             reader.mapCharCodeToWidth( "Lslash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ltshade
     */
    @Test
    public void testMtxltshade() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "ltshade", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test m
     */
    @Test
    public void testMtxm() {

        Assert.assertEquals( 1579, reader.mapCharCodeToWidth( "m", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test M
     */
    @Test
    public void testMtxM() {

        Assert.assertEquals( 1707, reader.mapCharCodeToWidth( "M", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test male
     */
    @Test
    public void testMtxmale() {

        Assert.assertEquals( 1536,
                             reader.mapCharCodeToWidth( "male", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test minus
     */
    @Test
    public void testMtxminus() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "minus", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test minute
     */
    @Test
    public void testMtxminute() {

        Assert.assertEquals( 299,
                             reader.mapCharCodeToWidth( "minute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test mu
     */
    @Test
    public void testMtxmu() {

        Assert.assertEquals( 1089,
                             reader.mapCharCodeToWidth( "mu", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Mu
     */
    @Test
    public void testMtxMu() {

        Assert.assertEquals( 1707,
                             reader.mapCharCodeToWidth( "Mu", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test mu1
     */
    @Test
    public void testMtxmu1() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "mu1", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test multiply
     */
    @Test
    public void testMtxmultiply() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "multiply",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test musicalnote
     */
    @Test
    public void testMtxmusicalnote() {

        Assert.assertEquals( 1024, reader.mapCharCodeToWidth( "musicalnote",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test musicalnotedbl
     */
    @Test
    public void testMtxmusicalnotedbl() {

        Assert.assertEquals( 1536,
                             reader.mapCharCodeToWidth( "musicalnotedbl",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test n
     */
    @Test
    public void testMtxn() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "n", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test N
     */
    @Test
    public void testMtxN() {

        Assert.assertEquals( 1579, reader.mapCharCodeToWidth( "N", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test nacute
     */
    @Test
    public void testMtxnacute() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "nacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Nacute
     */
    @Test
    public void testMtxNacute() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Nacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test napostrophe
     */
    @Test
    public void testMtxnapostrophe() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "napostrophe",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test ncaron
     */
    @Test
    public void testMtxncaron() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "ncaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ncaron
     */
    @Test
    public void testMtxNcaron() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Ncaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ncedilla
     */
    @Test
    public void testMtxncedilla() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "ncedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ncedilla
     */
    @Test
    public void testMtxNcedilla() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Ncedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test nine
     */
    @Test
    public void testMtxnine() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "nine", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test nonbreakingspace
     */
    @Test
    public void testMtxnonbreakingspace() {

        Assert.assertEquals( 512,
                             reader.mapCharCodeToWidth( "nbspace", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test notequal
     */
    @Test
    public void testMtxnotequal() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "notequal",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test nsuperior
     */
    @Test
    public void testMtxnsuperior() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "nsuperior",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ntilde
     */
    @Test
    public void testMtxntilde() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "ntilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ntilde
     */
    @Test
    public void testMtxNtilde() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Ntilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test nu
     */
    @Test
    public void testMtxnu() {

        Assert.assertEquals( 774, reader.mapCharCodeToWidth( "nu", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Nu
     */
    @Test
    public void testMtxNu() {

        Assert.assertEquals( 1579,
                             reader.mapCharCodeToWidth( "Nu", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test numbersign
     */
    @Test
    public void testMtxnumbersign() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "numbersign",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test o
     */
    @Test
    public void testMtxo() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "o", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test O
     */
    @Test
    public void testMtxO() {

        Assert.assertEquals( 1600, reader.mapCharCodeToWidth( "O", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test oacute
     */
    @Test
    public void testMtxoacute() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "oacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Oacute
     */
    @Test
    public void testMtxOacute() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Oacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test obreve
     */
    @Test
    public void testMtxobreve() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "obreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Obreve
     */
    @Test
    public void testMtxObreve() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Obreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ocircumflex
     */
    @Test
    public void testMtxocircumflex() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "ocircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Ocircumflex
     */
    @Test
    public void testMtxOcircumflex() {

        Assert.assertEquals( 1600, reader.mapCharCodeToWidth( "Ocircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test odblacute
     */
    @Test
    public void testMtxodblacute() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "odblacute",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Odblacute
     */
    @Test
    public void testMtxOdblacute() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Odblacute",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test odieresis
     */
    @Test
    public void testMtxodieresis() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "odieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Odieresis
     */
    @Test
    public void testMtxOdieresis() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Odieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test oe
     */
    @Test
    public void testMtxoe() {

        Assert.assertEquals( 1429,
                             reader.mapCharCodeToWidth( "oe", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test OE
     */
    @Test
    public void testMtxOE() {

        Assert.assertEquals( 1920,
                             reader.mapCharCodeToWidth( "OE", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ogonek
     */
    @Test
    public void testMtxogonek() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "ogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ograve
     */
    @Test
    public void testMtxograve() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "ograve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ograve
     */
    @Test
    public void testMtxOgrave() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Ograve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ohm
     */
    @Test
    public void testMtxOhm() {

        Assert.assertEquals( 1515,
                             reader.mapCharCodeToWidth( "Ohm", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test omacron
     */
    @Test
    public void testMtxomacron() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "omacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Omacron
     */
    @Test
    public void testMtxOmacron() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Omacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test omega
     */
    @Test
    public void testMtxomega() {

        Assert.assertEquals( 1172,
                             reader.mapCharCodeToWidth( "omega", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Omega
     */
    @Test
    public void testMtxOmega() {

        Assert.assertEquals( 1588,
                             reader.mapCharCodeToWidth( "Omega", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test omegatonos
     */
    @Test
    public void testMtxomegatonos() {

        Assert.assertEquals( 1172, reader.mapCharCodeToWidth( "omegatonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Omegatonos
     */
    @Test
    public void testMtxOmegatonos() {

        Assert.assertEquals( 1600, reader.mapCharCodeToWidth( "Omegatonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test omicron
     */
    @Test
    public void testMtxomicron() {

        Assert.assertEquals( 1009,
                             reader.mapCharCodeToWidth( "omicron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Omicron
     */
    @Test
    public void testMtxOmicron() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Omicron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test omicrontonos
     */
    @Test
    public void testMtxomicrontonos() {

        Assert.assertEquals( 1009, reader.mapCharCodeToWidth( "omicrontonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Omicrontonos
     */
    @Test
    public void testMtxOmicrontonos() {

        Assert.assertEquals( 1600, reader.mapCharCodeToWidth( "Omicrontonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test one
     */
    @Test
    public void testMtxone() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "one", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test oneeighth
     */
    @Test
    public void testMtxoneeighth() {

        Assert.assertEquals( 1664,
                             reader.mapCharCodeToWidth( "oneeighth",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test onehalf
     */
    @Test
    public void testMtxonehalf() {

        Assert.assertEquals( 1664,
                             reader.mapCharCodeToWidth( "onehalf", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test onequarter
     */
    @Test
    public void testMtxonequarter() {

        Assert.assertEquals( 1664, reader.mapCharCodeToWidth( "onequarter",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test onesuperior
     */
    @Test
    public void testMtxonesuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "onesuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test openbullet
     */
    @Test
    public void testMtxopenbullet() {

        Assert.assertEquals( 727,
                             reader.mapCharCodeToWidth( "openbullet",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ordfeminine
     */
    @Test
    public void testMtxordfeminine() {

        Assert.assertEquals( 533, reader.mapCharCodeToWidth( "ordfeminine",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test ordmasculine
     */
    @Test
    public void testMtxordmasculine() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "ordmasculine",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test orthogonal
     */
    @Test
    public void testMtxorthogonal() {

        Assert.assertEquals( 2005, reader.mapCharCodeToWidth( "orthogonal",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test oslash
     */
    @Test
    public void testMtxoslash() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "oslash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Oslash
     */
    @Test
    public void testMtxOslash() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Oslash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test oslashacute
     */
    @Test
    public void testMtxoslashacute() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "oslashacute",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Oslashacute
     */
    @Test
    public void testMtxOslashacute() {

        Assert.assertEquals( 1600, reader.mapCharCodeToWidth( "Oslashacute",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test otilde
     */
    @Test
    public void testMtxotilde() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "otilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Otilde
     */
    @Test
    public void testMtxOtilde() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Otilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test p
     */
    @Test
    public void testMtxp() {

        Assert.assertEquals( 1045, reader.mapCharCodeToWidth( "p", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test P
     */
    @Test
    public void testMtxP() {

        Assert.assertEquals( 1152, reader.mapCharCodeToWidth( "P", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test paragraph
     */
    @Test
    public void testMtxparagraph() {

        Assert.assertEquals( 917,
                             reader.mapCharCodeToWidth( "paragraph",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test parenleft
     */
    @Test
    public void testMtxparenleft() {

        Assert.assertEquals( 597,
                             reader.mapCharCodeToWidth( "parenleft",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test parenright
     */
    @Test
    public void testMtxparenright() {

        Assert.assertEquals( 597,
                             reader.mapCharCodeToWidth( "parenright",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test partialdiff
     */
    @Test
    public void testMtxpartialdiff() {

        Assert.assertEquals( 1003, reader.mapCharCodeToWidth( "partialdiff",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test percent
     */
    @Test
    public void testMtxpercent() {

        Assert.assertEquals( 1685,
                             reader.mapCharCodeToWidth( "percent", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test period
     */
    @Test
    public void testMtxperiod() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "period", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test peseta
     */
    @Test
    public void testMtxpeseta() {

        Assert.assertEquals( 2069,
                             reader.mapCharCodeToWidth( "peseta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test phi
     */
    @Test
    public void testMtxphi() {

        Assert.assertEquals( 1007,
                             reader.mapCharCodeToWidth( "phi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Phi
     */
    @Test
    public void testMtxPhi() {

        Assert.assertEquals( 1654,
                             reader.mapCharCodeToWidth( "Phi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test pi
     */
    @Test
    public void testMtxpi() {

        Assert.assertEquals( 1088,
                             reader.mapCharCodeToWidth( "pi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Pi
     */
    @Test
    public void testMtxPi() {

        Assert.assertEquals( 1557,
                             reader.mapCharCodeToWidth( "Pi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test pi1
     */
    @Test
    public void testMtxpi1() {

        Assert.assertEquals( 975,
                             reader.mapCharCodeToWidth( "pi1", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test plus
     */
    @Test
    public void testMtxplus() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "plus", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test plusminus
     */
    @Test
    public void testMtxplusminus() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "plusminus",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test product
     */
    @Test
    public void testMtxproduct() {

        Assert.assertEquals( 1685,
                             reader.mapCharCodeToWidth( "product", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test psi
     */
    @Test
    public void testMtxpsi() {

        Assert.assertEquals( 1026,
                             reader.mapCharCodeToWidth( "psi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Psi
     */
    @Test
    public void testMtxPsi() {

        Assert.assertEquals( 1549,
                             reader.mapCharCodeToWidth( "Psi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test q
     */
    @Test
    public void testMtxq() {

        Assert.assertEquals( 1003, reader.mapCharCodeToWidth( "q", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Q
     */
    @Test
    public void testMtxQ() {

        Assert.assertEquals( 1579, reader.mapCharCodeToWidth( "Q", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test question
     */
    @Test
    public void testMtxquestion() {

        Assert.assertEquals( 747,
                             reader.mapCharCodeToWidth( "question",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test questiondown
     */
    @Test
    public void testMtxquestiondown() {

        Assert.assertEquals( 747, reader.mapCharCodeToWidth( "questiondown",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test quotedbl
     */
    @Test
    public void testMtxquotedbl() {

        Assert.assertEquals( 832,
                             reader.mapCharCodeToWidth( "quotedbl",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test quotedblbase
     */
    @Test
    public void testMtxquotedblbase() {

        Assert.assertEquals( 917, reader.mapCharCodeToWidth( "quotedblbase",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test quotedblleft
     */
    @Test
    public void testMtxquotedblleft() {

        Assert.assertEquals( 917, reader.mapCharCodeToWidth( "quotedblleft",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test quotedblright
     */
    @Test
    public void testMtxquotedblright() {

        Assert.assertEquals( 917, reader.mapCharCodeToWidth( "quotedblright",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test quoteleft
     */
    @Test
    public void testMtxquoteleft() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "quoteleft",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test quotereversed
     */
    @Test
    public void testMtxquotereversed() {

        Assert.assertEquals( 448, reader.mapCharCodeToWidth( "quotereversed",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test quoteright
     */
    @Test
    public void testMtxquoteright() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "quoteright",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test quotesinglbase
     */
    @Test
    public void testMtxquotesinglbase() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "quotesinglbase",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test quotesingle
     */
    @Test
    public void testMtxquotesingle() {

        Assert.assertEquals( 363, reader.mapCharCodeToWidth( "quotesingle",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test r
     */
    @Test
    public void testMtxr() {

        Assert.assertEquals( 683, reader.mapCharCodeToWidth( "r", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test R
     */
    @Test
    public void testMtxR() {

        Assert.assertEquals( 1280, reader.mapCharCodeToWidth( "R", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test racute
     */
    @Test
    public void testMtxracute() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "racute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Racute
     */
    @Test
    public void testMtxRacute() {

        Assert.assertEquals( 1280,
                             reader.mapCharCodeToWidth( "Racute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test radical
     */
    @Test
    public void testMtxradical() {

        Assert.assertEquals( 1131,
                             reader.mapCharCodeToWidth( "radical", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test radicalex
     */
    @Test
    public void testMtxradicalex() {

        Assert.assertEquals( 902,
                             reader.mapCharCodeToWidth( "radicalex",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test rcaron
     */
    @Test
    public void testMtxrcaron() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "rcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Rcaron
     */
    @Test
    public void testMtxRcaron() {

        Assert.assertEquals( 1280,
                             reader.mapCharCodeToWidth( "Rcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test rcedilla
     */
    @Test
    public void testMtxrcedilla() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "rcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Rcedilla
     */
    @Test
    public void testMtxRcedilla() {

        Assert.assertEquals( 1280,
                             reader.mapCharCodeToWidth( "Rcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test registered
     */
    @Test
    public void testMtxregistered() {

        Assert.assertEquals( 1557, reader.mapCharCodeToWidth( "registered",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test revlogicalnot
     */
    @Test
    public void testMtxrevlogicalnot() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "revlogicalnot",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test rho
     */
    @Test
    public void testMtxrho() {

        Assert.assertEquals( 1010,
                             reader.mapCharCodeToWidth( "rho", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Rho
     */
    @Test
    public void testMtxRho() {

        Assert.assertEquals( 1152,
                             reader.mapCharCodeToWidth( "Rho", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ring
     */
    @Test
    public void testMtxring() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "ring", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test rtblock
     */
    @Test
    public void testMtxrtblock() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "rtblock", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test s
     */
    @Test
    public void testMtxs() {

        Assert.assertEquals( 747, reader.mapCharCodeToWidth( "s", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test S
     */
    @Test
    public void testMtxS() {

        Assert.assertEquals( 981, reader.mapCharCodeToWidth( "S", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test sacute
     */
    @Test
    public void testMtxsacute() {

        Assert.assertEquals( 747,
                             reader.mapCharCodeToWidth( "sacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Sacute
     */
    @Test
    public void testMtxSacute() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "Sacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test scaron
     */
    @Test
    public void testMtxscaron() {

        Assert.assertEquals( 747,
                             reader.mapCharCodeToWidth( "scaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Scaron
     */
    @Test
    public void testMtxScaron() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "Scaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test scedilla
     */
    @Test
    public void testMtxscedilla() {

        Assert.assertEquals( 747,
                             reader.mapCharCodeToWidth( "scedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Scedilla
     */
    @Test
    public void testMtxScedilla() {

        Assert.assertEquals( 981,
                             reader.mapCharCodeToWidth( "Scedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test scircumflex
     */
    @Test
    public void testMtxscircumflex() {

        Assert.assertEquals( 747, reader.mapCharCodeToWidth( "scircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Scircumflex
     */
    @Test
    public void testMtxScircumflex() {

        Assert.assertEquals( 981, reader.mapCharCodeToWidth( "Scircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test second
     */
    @Test
    public void testMtxsecond() {

        Assert.assertEquals( 768,
                             reader.mapCharCodeToWidth( "second", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test section
     */
    @Test
    public void testMtxsection() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "section", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test semicolon
     */
    @Test
    public void testMtxsemicolon() {

        Assert.assertEquals( 448,
                             reader.mapCharCodeToWidth( "semicolon",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test seven
     */
    @Test
    public void testMtxseven() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "seven", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test seveneighths
     */
    @Test
    public void testMtxseveneighths() {

        Assert.assertEquals( 1685, reader.mapCharCodeToWidth( "seveneighths",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test sevensuperior
     */
    @Test
    public void testMtxsevensuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "sevensuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test SF010000
     */
    @Test
    public void testMtxSF010000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF010000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF020000
     */
    @Test
    public void testMtxSF020000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF020000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF030000
     */
    @Test
    public void testMtxSF030000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF030000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF040000
     */
    @Test
    public void testMtxSF040000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF040000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF050000
     */
    @Test
    public void testMtxSF050000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF050000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF060000
     */
    @Test
    public void testMtxSF060000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF060000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF070000
     */
    @Test
    public void testMtxSF070000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF070000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF080000
     */
    @Test
    public void testMtxSF080000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF080000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF090000
     */
    @Test
    public void testMtxSF090000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF090000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF100000
     */
    @Test
    public void testMtxSF100000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF100000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF110000
     */
    @Test
    public void testMtxSF110000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF110000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF190000
     */
    @Test
    public void testMtxSF190000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF190000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF200000
     */
    @Test
    public void testMtxSF200000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF200000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF210000
     */
    @Test
    public void testMtxSF210000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF210000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF220000
     */
    @Test
    public void testMtxSF220000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF220000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF230000
     */
    @Test
    public void testMtxSF230000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF230000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF240000
     */
    @Test
    public void testMtxSF240000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF240000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF250000
     */
    @Test
    public void testMtxSF250000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF250000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF260000
     */
    @Test
    public void testMtxSF260000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF260000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF270000
     */
    @Test
    public void testMtxSF270000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF270000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF280000
     */
    @Test
    public void testMtxSF280000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF280000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF360000
     */
    @Test
    public void testMtxSF360000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF360000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF370000
     */
    @Test
    public void testMtxSF370000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF370000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF380000
     */
    @Test
    public void testMtxSF380000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF380000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF390000
     */
    @Test
    public void testMtxSF390000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF390000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF400000
     */
    @Test
    public void testMtxSF400000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF400000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF410000
     */
    @Test
    public void testMtxSF410000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF410000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF420000
     */
    @Test
    public void testMtxSF420000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF420000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF430000
     */
    @Test
    public void testMtxSF430000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF430000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF440000
     */
    @Test
    public void testMtxSF440000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF440000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF450000
     */
    @Test
    public void testMtxSF450000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF450000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF460000
     */
    @Test
    public void testMtxSF460000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF460000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF470000
     */
    @Test
    public void testMtxSF470000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF470000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF480000
     */
    @Test
    public void testMtxSF480000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF480000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF490000
     */
    @Test
    public void testMtxSF490000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF490000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF500000
     */
    @Test
    public void testMtxSF500000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF500000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF510000
     */
    @Test
    public void testMtxSF510000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF510000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF520000
     */
    @Test
    public void testMtxSF520000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF520000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF530000
     */
    @Test
    public void testMtxSF530000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF530000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test SF540000
     */
    @Test
    public void testMtxSF540000() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "SF540000",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test sfthyphen
     */
    @Test
    public void testMtxsfthyphen() {

        Assert.assertEquals( 640,
                             reader.mapCharCodeToWidth( "sfthyphen",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test shade
     */
    @Test
    public void testMtxshade() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "shade", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test sigma
     */
    @Test
    public void testMtxsigma() {

        Assert.assertEquals( 977,
                             reader.mapCharCodeToWidth( "sigma", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Sigma
     */
    @Test
    public void testMtxSigma() {

        Assert.assertEquals( 1308,
                             reader.mapCharCodeToWidth( "Sigma", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test sigma1
     */
    @Test
    public void testMtxsigma1() {

        Assert.assertEquals( 737,
                             reader.mapCharCodeToWidth( "sigma1", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test six
     */
    @Test
    public void testMtxsix() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "six", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test slash
     */
    @Test
    public void testMtxslash() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "slash", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test smileface
     */
    @Test
    public void testMtxsmileface() {

        Assert.assertEquals( 2048,
                             reader.mapCharCodeToWidth( "smileface",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test space
     */
    @Test
    public void testMtxspace() {

        Assert.assertEquals( 512,
                             reader.mapCharCodeToWidth( "space", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test spade
     */
    @Test
    public void testMtxspade() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "spade", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test sterling
     */
    @Test
    public void testMtxsterling() {

        Assert.assertEquals( 1173,
                             reader.mapCharCodeToWidth( "sterling",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test summation
     */
    @Test
    public void testMtxsummation() {

        Assert.assertEquals( 1460,
                             reader.mapCharCodeToWidth( "summation",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test sun
     */
    @Test
    public void testMtxsun() {

        Assert.assertEquals( 1877,
                             reader.mapCharCodeToWidth( "sun", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test t
     */
    @Test
    public void testMtxt() {

        Assert.assertEquals( 597, reader.mapCharCodeToWidth( "t", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test T
     */
    @Test
    public void testMtxT() {

        Assert.assertEquals( 1259, reader.mapCharCodeToWidth( "T", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test tau
     */
    @Test
    public void testMtxtau() {

        Assert.assertEquals( 762,
                             reader.mapCharCodeToWidth( "tau", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Tau
     */
    @Test
    public void testMtxTau() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "Tau", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test tbar
     */
    @Test
    public void testMtxtbar() {

        Assert.assertEquals( 597,
                             reader.mapCharCodeToWidth( "tbar", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Tbar
     */
    @Test
    public void testMtxTbar() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "Tbar", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test tcaron
     */
    @Test
    public void testMtxtcaron() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "tcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Tcaron
     */
    @Test
    public void testMtxTcaron() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "Tcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test tcedilla
     */
    @Test
    public void testMtxtcedilla() {

        Assert.assertEquals( 597,
                             reader.mapCharCodeToWidth( "tcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Tcedilla
     */
    @Test
    public void testMtxTcedilla() {

        Assert.assertEquals( 1259,
                             reader.mapCharCodeToWidth( "Tcedilla",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test theta
     */
    @Test
    public void testMtxtheta() {

        Assert.assertEquals( 911,
                             reader.mapCharCodeToWidth( "theta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Theta
     */
    @Test
    public void testMtxTheta() {

        Assert.assertEquals( 1600,
                             reader.mapCharCodeToWidth( "Theta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test thorn
     */
    @Test
    public void testMtxthorn() {

        Assert.assertEquals( 1045,
                             reader.mapCharCodeToWidth( "thorn", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Thorn
     */
    @Test
    public void testMtxThorn() {

        Assert.assertEquals( 1152,
                             reader.mapCharCodeToWidth( "Thorn", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test three
     */
    @Test
    public void testMtxthree() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "three", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test threeeighths
     */
    @Test
    public void testMtxthreeeighths() {

        Assert.assertEquals( 1685, reader.mapCharCodeToWidth( "threeeighths",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test threequarters
     */
    @Test
    public void testMtxthreequarters() {

        Assert.assertEquals( 1685,
                             reader.mapCharCodeToWidth( "threequarters",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test threesuperior
     */
    @Test
    public void testMtxthreesuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "threesuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test tilde
     */
    @Test
    public void testMtxtilde() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "tilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test tonos
     */
    @Test
    public void testMtxtonos() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "tonos", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test trademark
     */
    @Test
    public void testMtxtrademark() {

        Assert.assertEquals( 2005,
                             reader.mapCharCodeToWidth( "trademark",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test triagdn
     */
    @Test
    public void testMtxtriagdn() {

        Assert.assertEquals( 2027,
                             reader.mapCharCodeToWidth( "triagdn", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test triaglf
     */
    @Test
    public void testMtxtriaglf() {

        Assert.assertEquals( 2027,
                             reader.mapCharCodeToWidth( "triaglf", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test triagrt
     */
    @Test
    public void testMtxtriagrt() {

        Assert.assertEquals( 2027,
                             reader.mapCharCodeToWidth( "triagrt", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test triagup
     */
    @Test
    public void testMtxtriagup() {

        Assert.assertEquals( 2027,
                             reader.mapCharCodeToWidth( "triagup", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test two
     */
    @Test
    public void testMtxtwo() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "two", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test twosuperior
     */
    @Test
    public void testMtxtwosuperior() {

        Assert.assertEquals( 640, reader.mapCharCodeToWidth( "twosuperior",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test u
     */
    @Test
    public void testMtxu() {

        Assert.assertEquals( 1003, reader.mapCharCodeToWidth( "u", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test U
     */
    @Test
    public void testMtxU() {

        Assert.assertEquals( 1451, reader.mapCharCodeToWidth( "U", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test uacute
     */
    @Test
    public void testMtxuacute() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "uacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Uacute
     */
    @Test
    public void testMtxUacute() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Uacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ubreve
     */
    @Test
    public void testMtxubreve() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "ubreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ubreve
     */
    @Test
    public void testMtxUbreve() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Ubreve", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ucircumflex
     */
    @Test
    public void testMtxucircumflex() {

        Assert.assertEquals( 1003, reader.mapCharCodeToWidth( "ucircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Ucircumflex
     */
    @Test
    public void testMtxUcircumflex() {

        Assert.assertEquals( 1451, reader.mapCharCodeToWidth( "Ucircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test udblacute
     */
    @Test
    public void testMtxudblacute() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "udblacute",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Udblacute
     */
    @Test
    public void testMtxUdblacute() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Udblacute",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test udieresis
     */
    @Test
    public void testMtxudieresis() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "udieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Udieresis
     */
    @Test
    public void testMtxUdieresis() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Udieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ugrave
     */
    @Test
    public void testMtxugrave() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "ugrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ugrave
     */
    @Test
    public void testMtxUgrave() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Ugrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test umacron
     */
    @Test
    public void testMtxumacron() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "umacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Umacron
     */
    @Test
    public void testMtxUmacron() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Umacron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test undercommaaccent
     */
    @Test
    public void testMtxundercommaaccent() {

        Assert.assertEquals( 683,
                             reader.mapCharCodeToWidth( "undercommaaccent",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test underscore
     */
    @Test
    public void testMtxunderscore() {

        Assert.assertEquals( 1024, reader.mapCharCodeToWidth( "underscore",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test underscoredbl
     */
    @Test
    public void testMtxunderscoredbl() {

        Assert.assertEquals( 1024,
                             reader.mapCharCodeToWidth( "underscoredbl",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test uogonek
     */
    @Test
    public void testMtxuogonek() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "uogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Uogonek
     */
    @Test
    public void testMtxUogonek() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Uogonek", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test upblock
     */
    @Test
    public void testMtxupblock() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "upblock", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test upsilon
     */
    @Test
    public void testMtxupsilon() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "upsilon", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Upsilon
     */
    @Test
    public void testMtxUpsilon() {

        Assert.assertEquals( 1186,
                             reader.mapCharCodeToWidth( "Upsilon", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test upsilondieresis
     */
    @Test
    public void testMtxupsilondieresis() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "upsilondieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Upsilondieresis
     */
    @Test
    public void testMtxUpsilondieresis() {

        Assert.assertEquals( 1186,
                             reader.mapCharCodeToWidth( "Upsilondieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test upsilondieresistonos
     */
    @Test
    public void testMtxupsilondieresistonos() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "upsilondieresistonos",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test upsilontonos
     */
    @Test
    public void testMtxupsilontonos() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "upsilontonos",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Upsilontonos
     */
    @Test
    public void testMtxUpsilontonos() {

        Assert.assertEquals( 1373, reader.mapCharCodeToWidth( "Upsilontonos",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test uring
     */
    @Test
    public void testMtxuring() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "uring", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Uring
     */
    @Test
    public void testMtxUring() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Uring", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test utilde
     */
    @Test
    public void testMtxutilde() {

        Assert.assertEquals( 1003,
                             reader.mapCharCodeToWidth( "utilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Utilde
     */
    @Test
    public void testMtxUtilde() {

        Assert.assertEquals( 1451,
                             reader.mapCharCodeToWidth( "Utilde", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test v
     */
    @Test
    public void testMtxv() {

        Assert.assertEquals( 960, reader.mapCharCodeToWidth( "v", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test V
     */
    @Test
    public void testMtxV() {

        Assert.assertEquals( 1387, reader.mapCharCodeToWidth( "V", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test w
     */
    @Test
    public void testMtxw() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "w", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test W
     */
    @Test
    public void testMtxW() {

        Assert.assertEquals( 1813, reader.mapCharCodeToWidth( "W", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test wacute
     */
    @Test
    public void testMtxwacute() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "wacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Wacute
     */
    @Test
    public void testMtxWacute() {

        Assert.assertEquals( 1813,
                             reader.mapCharCodeToWidth( "Wacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test wcircumflex
     */
    @Test
    public void testMtxwcircumflex() {

        Assert.assertEquals( 1365, reader.mapCharCodeToWidth( "wcircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test Wcircumflex
     */
    @Test
    public void testMtxWcircumflex() {

        Assert.assertEquals( 1813, reader.mapCharCodeToWidth( "Wcircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test wdieresis
     */
    @Test
    public void testMtxwdieresis() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "wdieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Wdieresis
     */
    @Test
    public void testMtxWdieresis() {

        Assert.assertEquals( 1813,
                             reader.mapCharCodeToWidth( "Wdieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test wgrave
     */
    @Test
    public void testMtxwgrave() {

        Assert.assertEquals( 1365,
                             reader.mapCharCodeToWidth( "wgrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Wgrave
     */
    @Test
    public void testMtxWgrave() {

        Assert.assertEquals( 1813,
                             reader.mapCharCodeToWidth( "Wgrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test x
     */
    @Test
    public void testMtxx() {

        Assert.assertEquals( 939, reader.mapCharCodeToWidth( "x", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test X
     */
    @Test
    public void testMtxX() {

        Assert.assertEquals( 1429, reader.mapCharCodeToWidth( "X", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test xi
     */
    @Test
    public void testMtxxi() {

        Assert.assertEquals( 780, reader.mapCharCodeToWidth( "xi", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Xi
     */
    @Test
    public void testMtxXi() {

        Assert.assertEquals( 1390,
                             reader.mapCharCodeToWidth( "Xi", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test y
     */
    @Test
    public void testMtxy() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "y", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Y
     */
    @Test
    public void testMtxY() {

        Assert.assertEquals( 1344, reader.mapCharCodeToWidth( "Y", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test yacute
     */
    @Test
    public void testMtxyacute() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "yacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Yacute
     */
    @Test
    public void testMtxYacute() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Yacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ycircumflex
     */
    @Test
    public void testMtxycircumflex() {

        Assert.assertEquals( 853, reader.mapCharCodeToWidth( "ycircumflex",
                                                             0,
                                                             (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Ycircumflex
     */
    @Test
    public void testMtxYcircumflex() {

        Assert.assertEquals( 1344, reader.mapCharCodeToWidth( "Ycircumflex",
                                                              0,
                                                              (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test ydieresis
     */
    @Test
    public void testMtxydieresis() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "ydieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ydieresis
     */
    @Test
    public void testMtxYdieresis() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Ydieresis",
                                                        0,
                                                        (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test yen
     */
    @Test
    public void testMtxyen() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "yen", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test ygrave
     */
    @Test
    public void testMtxygrave() {

        Assert.assertEquals( 853,
                             reader.mapCharCodeToWidth( "ygrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Ygrave
     */
    @Test
    public void testMtxYgrave() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Ygrave", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test z
     */
    @Test
    public void testMtxz() {

        Assert.assertEquals( 875, reader.mapCharCodeToWidth( "z", 0, (short) 3,
                                                             (short) 1 ) );
    }

    /**
     * test Z
     */
    @Test
    public void testMtxZ() {

        Assert.assertEquals( 1344, reader.mapCharCodeToWidth( "Z", 0, (short) 3,
                                                              (short) 1 ) );
    }

    /**
     * test zacute
     */
    @Test
    public void testMtxzacute() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "zacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Zacute
     */
    @Test
    public void testMtxZacute() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Zacute", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test zcaron
     */
    @Test
    public void testMtxzcaron() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "zcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Zcaron
     */
    @Test
    public void testMtxZcaron() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Zcaron", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test zdot
     */
    @Test
    public void testMtxzdot() {

        Assert.assertEquals( 875,
                             reader.mapCharCodeToWidth( "zdot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Zdot
     */
    @Test
    public void testMtxZdot() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Zdot", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test zero
     */
    @Test
    public void testMtxzero() {

        Assert.assertEquals( 960,
                             reader.mapCharCodeToWidth( "zero", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test zeta
     */
    @Test
    public void testMtxzeta() {

        Assert.assertEquals( 780,
                             reader.mapCharCodeToWidth( "zeta", 0, (short) 3,
                                                        (short) 1 ) );
    }

    /**
     * test Zeta
     */
    @Test
    public void testMtxZeta() {

        Assert.assertEquals( 1344,
                             reader.mapCharCodeToWidth( "Zeta", 0, (short) 3,
                                                        (short) 1 ) );
    }

}
