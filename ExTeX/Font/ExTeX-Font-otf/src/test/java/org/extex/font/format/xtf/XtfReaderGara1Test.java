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

import org.extex.font.format.xtf.tables.TtfTableCMAP;
import org.extex.font.format.xtf.tables.TtfTableCMAP.Format;
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
public class XtfReaderGara1Test {

    private final XtfReader reader;

    /**
     * Creates a new object.
     *
     * @throws IOException if an error occurred.
     */
    public XtfReaderGara1Test() throws IOException {
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
     * test 03.
     */
    @Test
    public void test03() {

        TtfTableCMAP cmap = reader.getCmapTable();
        Assert.assertEquals( 2, cmap.getNumTables() );
        // windows - unicode
        Format format = cmap.getFormat((short) 3, (short) 1);
        Assert.assertNotNull( format );
        Assert.assertEquals( 4, format.getFormat() );
    }

    /**
     * test 04.
     */
    @Test
    public void test04() {

        Assert.assertEquals( "space",
                             reader.mapCharCodeToGlyphname( 0x20, 0, (short) 3,
                                                            (short) 1 ) );
    }

    // --------------------------------------------------------------

    /**
     * test 0x100
     */
    @Test
    public void test0x100() {

        Assert.assertEquals( "Amacron", reader.mapCharCodeToGlyphname( 0x100,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x101
     */
    @Test
    public void test0x101() {

        Assert.assertEquals( "amacron", reader.mapCharCodeToGlyphname( 0x101,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x102
     */
    @Test
    public void test0x102() {

        Assert.assertEquals( "Abreve", reader.mapCharCodeToGlyphname( 0x102,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x103
     */
    @Test
    public void test0x103() {

        Assert.assertEquals( "abreve", reader.mapCharCodeToGlyphname( 0x103,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x104
     */
    @Test
    public void test0x104() {

        Assert.assertEquals( "Aogonek", reader.mapCharCodeToGlyphname( 0x104,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x105
     */
    @Test
    public void test0x105() {

        Assert.assertEquals( "aogonek", reader.mapCharCodeToGlyphname( 0x105,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x106
     */
    @Test
    public void test0x106() {

        Assert.assertEquals( "Cacute", reader.mapCharCodeToGlyphname( 0x106,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x107
     */
    @Test
    public void test0x107() {

        Assert.assertEquals( "cacute", reader.mapCharCodeToGlyphname( 0x107,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x108
     */
    @Test
    public void test0x108() {

        Assert.assertEquals( "Ccircumflex",
                             reader.mapCharCodeToGlyphname( 0x108,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x109
     */
    @Test
    public void test0x109() {

        Assert.assertEquals( "ccircumflex",
                             reader.mapCharCodeToGlyphname( 0x109,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x10a
     */
    @Test
    public void test0x10a() {

        Assert.assertEquals( "Cdot",
                             reader.mapCharCodeToGlyphname( 0x10a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x10b
     */
    @Test
    public void test0x10b() {

        Assert.assertEquals( "cdot",
                             reader.mapCharCodeToGlyphname( 0x10b, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x10c
     */
    @Test
    public void test0x10c() {

        Assert.assertEquals( "Ccaron", reader.mapCharCodeToGlyphname( 0x10c,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x10d
     */
    @Test
    public void test0x10d() {

        Assert.assertEquals( "ccaron", reader.mapCharCodeToGlyphname( 0x10d,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x10e
     */
    @Test
    public void test0x10e() {

        Assert.assertEquals( "Dcaron", reader.mapCharCodeToGlyphname( 0x10e,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x10f
     */
    @Test
    public void test0x10f() {

        Assert.assertEquals( "dcaron", reader.mapCharCodeToGlyphname( 0x10f,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x110
     */
    @Test
    public void test0x110() {

        Assert.assertEquals( "Dslash", reader.mapCharCodeToGlyphname( 0x110,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x111
     */
    @Test
    public void test0x111() {

        Assert.assertEquals( "dmacron", reader.mapCharCodeToGlyphname( 0x111,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x112
     */
    @Test
    public void test0x112() {

        Assert.assertEquals( "Emacron", reader.mapCharCodeToGlyphname( 0x112,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x113
     */
    @Test
    public void test0x113() {

        Assert.assertEquals( "emacron", reader.mapCharCodeToGlyphname( 0x113,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x114
     */
    @Test
    public void test0x114() {

        Assert.assertEquals( "Ebreve", reader.mapCharCodeToGlyphname( 0x114,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x115
     */
    @Test
    public void test0x115() {

        Assert.assertEquals( "ebreve", reader.mapCharCodeToGlyphname( 0x115,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x116
     */
    @Test
    public void test0x116() {

        Assert.assertEquals( "Edot",
                             reader.mapCharCodeToGlyphname( 0x116, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x117
     */
    @Test
    public void test0x117() {

        Assert.assertEquals( "edot",
                             reader.mapCharCodeToGlyphname( 0x117, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x118
     */
    @Test
    public void test0x118() {

        Assert.assertEquals( "Eogonek", reader.mapCharCodeToGlyphname( 0x118,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x119
     */
    @Test
    public void test0x119() {

        Assert.assertEquals( "eogonek", reader.mapCharCodeToGlyphname( 0x119,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x11a
     */
    @Test
    public void test0x11a() {

        Assert.assertEquals( "Ecaron", reader.mapCharCodeToGlyphname( 0x11a,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x11b
     */
    @Test
    public void test0x11b() {

        Assert.assertEquals( "ecaron", reader.mapCharCodeToGlyphname( 0x11b,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x11c
     */
    @Test
    public void test0x11c() {

        Assert.assertEquals( "Gcircumflex",
                             reader.mapCharCodeToGlyphname( 0x11c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x11d
     */
    @Test
    public void test0x11d() {

        Assert.assertEquals( "gcircumflex",
                             reader.mapCharCodeToGlyphname( 0x11d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x11e
     */
    @Test
    public void test0x11e() {

        Assert.assertEquals( "Gbreve", reader.mapCharCodeToGlyphname( 0x11e,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x11f
     */
    @Test
    public void test0x11f() {

        Assert.assertEquals( "gbreve", reader.mapCharCodeToGlyphname( 0x11f,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x120
     */
    @Test
    public void test0x120() {

        Assert.assertEquals( "Gdot",
                             reader.mapCharCodeToGlyphname( 0x120, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x121
     */
    @Test
    public void test0x121() {

        Assert.assertEquals( "gdot",
                             reader.mapCharCodeToGlyphname( 0x121, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x122
     */
    @Test
    public void test0x122() {

        Assert.assertEquals( "Gcedilla",
                             reader.mapCharCodeToGlyphname( 0x122,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x123
     */
    @Test
    public void test0x123() {

        Assert.assertEquals( "gcedilla",
                             reader.mapCharCodeToGlyphname( 0x123,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x124
     */
    @Test
    public void test0x124() {

        Assert.assertEquals( "Hcircumflex",
                             reader.mapCharCodeToGlyphname( 0x124,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x125
     */
    @Test
    public void test0x125() {

        Assert.assertEquals( "hcircumflex",
                             reader.mapCharCodeToGlyphname( 0x125,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x126
     */
    @Test
    public void test0x126() {

        Assert.assertEquals( "Hbar",
                             reader.mapCharCodeToGlyphname( 0x126, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x127
     */
    @Test
    public void test0x127() {

        Assert.assertEquals( "hbar",
                             reader.mapCharCodeToGlyphname( 0x127, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x128
     */
    @Test
    public void test0x128() {

        Assert.assertEquals( "Itilde", reader.mapCharCodeToGlyphname( 0x128,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x129
     */
    @Test
    public void test0x129() {

        Assert.assertEquals( "itilde", reader.mapCharCodeToGlyphname( 0x129,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x12a
     */
    @Test
    public void test0x12a() {

        Assert.assertEquals( "Imacron", reader.mapCharCodeToGlyphname( 0x12a,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x12b
     */
    @Test
    public void test0x12b() {

        Assert.assertEquals( "imacron", reader.mapCharCodeToGlyphname( 0x12b,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x12c
     */
    @Test
    public void test0x12c() {

        Assert.assertEquals( "Ibreve", reader.mapCharCodeToGlyphname( 0x12c,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x12d
     */
    @Test
    public void test0x12d() {

        Assert.assertEquals( "ibreve", reader.mapCharCodeToGlyphname( 0x12d,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x12e
     */
    @Test
    public void test0x12e() {

        Assert.assertEquals( "Iogonek", reader.mapCharCodeToGlyphname( 0x12e,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x12f
     */
    @Test
    public void test0x12f() {

        Assert.assertEquals( "iogonek", reader.mapCharCodeToGlyphname( 0x12f,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x130
     */
    @Test
    public void test0x130() {

        Assert.assertEquals( "Idot",
                             reader.mapCharCodeToGlyphname( 0x130, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x131
     */
    @Test
    public void test0x131() {

        Assert.assertEquals( "dotlessi",
                             reader.mapCharCodeToGlyphname( 0x131,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x132
     */
    @Test
    public void test0x132() {

        Assert.assertEquals( "IJ",
                             reader.mapCharCodeToGlyphname( 0x132, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x133
     */
    @Test
    public void test0x133() {

        Assert.assertEquals( "ij",
                             reader.mapCharCodeToGlyphname( 0x133, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x134
     */
    @Test
    public void test0x134() {

        Assert.assertEquals( "Jcircumflex",
                             reader.mapCharCodeToGlyphname( 0x134,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x135
     */
    @Test
    public void test0x135() {

        Assert.assertEquals( "jcircumflex",
                             reader.mapCharCodeToGlyphname( 0x135,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x136
     */
    @Test
    public void test0x136() {

        Assert.assertEquals( "Kcedilla",
                             reader.mapCharCodeToGlyphname( 0x136,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x137
     */
    @Test
    public void test0x137() {

        Assert.assertEquals( "kcedilla",
                             reader.mapCharCodeToGlyphname( 0x137,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x138
     */
    @Test
    public void test0x138() {

        Assert.assertEquals( "kgreenlandic",
                             reader.mapCharCodeToGlyphname( 0x138,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x139
     */
    @Test
    public void test0x139() {

        Assert.assertEquals( "Lacute", reader.mapCharCodeToGlyphname( 0x139,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x13a
     */
    @Test
    public void test0x13a() {

        Assert.assertEquals( "lacute", reader.mapCharCodeToGlyphname( 0x13a,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x13b
     */
    @Test
    public void test0x13b() {

        Assert.assertEquals( "Lcedilla",
                             reader.mapCharCodeToGlyphname( 0x13b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x13c
     */
    @Test
    public void test0x13c() {

        Assert.assertEquals( "lcedilla",
                             reader.mapCharCodeToGlyphname( 0x13c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x13d
     */
    @Test
    public void test0x13d() {

        Assert.assertEquals( "Lcaron", reader.mapCharCodeToGlyphname( 0x13d,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x13e
     */
    @Test
    public void test0x13e() {

        Assert.assertEquals( "lcaron", reader.mapCharCodeToGlyphname( 0x13e,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x13f
     */
    @Test
    public void test0x13f() {

        Assert.assertEquals( "Ldot",
                             reader.mapCharCodeToGlyphname( 0x13f, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x140
     */
    @Test
    public void test0x140() {

        Assert.assertEquals( "ldot",
                             reader.mapCharCodeToGlyphname( 0x140, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x141
     */
    @Test
    public void test0x141() {

        Assert.assertEquals( "Lslash", reader.mapCharCodeToGlyphname( 0x141,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x142
     */
    @Test
    public void test0x142() {

        Assert.assertEquals( "lslash", reader.mapCharCodeToGlyphname( 0x142,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x143
     */
    @Test
    public void test0x143() {

        Assert.assertEquals( "Nacute", reader.mapCharCodeToGlyphname( 0x143,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x144
     */
    @Test
    public void test0x144() {

        Assert.assertEquals( "nacute", reader.mapCharCodeToGlyphname( 0x144,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x145
     */
    @Test
    public void test0x145() {

        Assert.assertEquals( "Ncedilla",
                             reader.mapCharCodeToGlyphname( 0x145,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x146
     */
    @Test
    public void test0x146() {

        Assert.assertEquals( "ncedilla",
                             reader.mapCharCodeToGlyphname( 0x146,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x147
     */
    @Test
    public void test0x147() {

        Assert.assertEquals( "Ncaron", reader.mapCharCodeToGlyphname( 0x147,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x148
     */
    @Test
    public void test0x148() {

        Assert.assertEquals( "ncaron", reader.mapCharCodeToGlyphname( 0x148,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x149
     */
    @Test
    public void test0x149() {

        Assert.assertEquals( "napostrophe",
                             reader.mapCharCodeToGlyphname( 0x149,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x14a
     */
    @Test
    public void test0x14a() {

        Assert.assertEquals( "Eng",
                             reader.mapCharCodeToGlyphname( 0x14a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x14b
     */
    @Test
    public void test0x14b() {

        Assert.assertEquals( "eng",
                             reader.mapCharCodeToGlyphname( 0x14b, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x14c
     */
    @Test
    public void test0x14c() {

        Assert.assertEquals( "Omacron", reader.mapCharCodeToGlyphname( 0x14c,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x14d
     */
    @Test
    public void test0x14d() {

        Assert.assertEquals( "omacron", reader.mapCharCodeToGlyphname( 0x14d,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x14e
     */
    @Test
    public void test0x14e() {

        Assert.assertEquals( "Obreve", reader.mapCharCodeToGlyphname( 0x14e,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x14f
     */
    @Test
    public void test0x14f() {

        Assert.assertEquals( "obreve", reader.mapCharCodeToGlyphname( 0x14f,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x150
     */
    @Test
    public void test0x150() {

        Assert.assertEquals( "Odblacute",
                             reader.mapCharCodeToGlyphname( 0x150,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x151
     */
    @Test
    public void test0x151() {

        Assert.assertEquals( "odblacute",
                             reader.mapCharCodeToGlyphname( 0x151,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x152
     */
    @Test
    public void test0x152() {

        Assert.assertEquals( "OE",
                             reader.mapCharCodeToGlyphname( 0x152, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x153
     */
    @Test
    public void test0x153() {

        Assert.assertEquals( "oe",
                             reader.mapCharCodeToGlyphname( 0x153, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x154
     */
    @Test
    public void test0x154() {

        Assert.assertEquals( "Racute", reader.mapCharCodeToGlyphname( 0x154,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x155
     */
    @Test
    public void test0x155() {

        Assert.assertEquals( "racute", reader.mapCharCodeToGlyphname( 0x155,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x156
     */
    @Test
    public void test0x156() {

        Assert.assertEquals( "Rcedilla",
                             reader.mapCharCodeToGlyphname( 0x156,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x157
     */
    @Test
    public void test0x157() {

        Assert.assertEquals( "rcedilla",
                             reader.mapCharCodeToGlyphname( 0x157,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x158
     */
    @Test
    public void test0x158() {

        Assert.assertEquals( "Rcaron", reader.mapCharCodeToGlyphname( 0x158,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x159
     */
    @Test
    public void test0x159() {

        Assert.assertEquals( "rcaron", reader.mapCharCodeToGlyphname( 0x159,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x15a
     */
    @Test
    public void test0x15a() {

        Assert.assertEquals( "Sacute", reader.mapCharCodeToGlyphname( 0x15a,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x15b
     */
    @Test
    public void test0x15b() {

        Assert.assertEquals( "sacute", reader.mapCharCodeToGlyphname( 0x15b,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x15c
     */
    @Test
    public void test0x15c() {

        Assert.assertEquals( "Scircumflex",
                             reader.mapCharCodeToGlyphname( 0x15c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x15d
     */
    @Test
    public void test0x15d() {

        Assert.assertEquals( "scircumflex",
                             reader.mapCharCodeToGlyphname( 0x15d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x15e
     */
    @Test
    public void test0x15e() {

        Assert.assertEquals( "Scedilla",
                             reader.mapCharCodeToGlyphname( 0x15e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x15f
     */
    @Test
    public void test0x15f() {

        Assert.assertEquals( "scedilla",
                             reader.mapCharCodeToGlyphname( 0x15f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x160
     */
    @Test
    public void test0x160() {

        Assert.assertEquals( "Scaron", reader.mapCharCodeToGlyphname( 0x160,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x161
     */
    @Test
    public void test0x161() {

        Assert.assertEquals( "scaron", reader.mapCharCodeToGlyphname( 0x161,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x162
     */
    @Test
    public void test0x162() {

        Assert.assertEquals( "Tcedilla",
                             reader.mapCharCodeToGlyphname( 0x162,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x163
     */
    @Test
    public void test0x163() {

        Assert.assertEquals( "tcedilla",
                             reader.mapCharCodeToGlyphname( 0x163,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x164
     */
    @Test
    public void test0x164() {

        Assert.assertEquals( "Tcaron", reader.mapCharCodeToGlyphname( 0x164,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x165
     */
    @Test
    public void test0x165() {

        Assert.assertEquals( "tcaron", reader.mapCharCodeToGlyphname( 0x165,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x166
     */
    @Test
    public void test0x166() {

        Assert.assertEquals( "Tbar",
                             reader.mapCharCodeToGlyphname( 0x166, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x167
     */
    @Test
    public void test0x167() {

        Assert.assertEquals( "tbar",
                             reader.mapCharCodeToGlyphname( 0x167, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x168
     */
    @Test
    public void test0x168() {

        Assert.assertEquals( "Utilde", reader.mapCharCodeToGlyphname( 0x168,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x169
     */
    @Test
    public void test0x169() {

        Assert.assertEquals( "utilde", reader.mapCharCodeToGlyphname( 0x169,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x16a
     */
    @Test
    public void test0x16a() {

        Assert.assertEquals( "Umacron", reader.mapCharCodeToGlyphname( 0x16a,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x16b
     */
    @Test
    public void test0x16b() {

        Assert.assertEquals( "umacron", reader.mapCharCodeToGlyphname( 0x16b,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x16c
     */
    @Test
    public void test0x16c() {

        Assert.assertEquals( "Ubreve", reader.mapCharCodeToGlyphname( 0x16c,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x16d
     */
    @Test
    public void test0x16d() {

        Assert.assertEquals( "ubreve", reader.mapCharCodeToGlyphname( 0x16d,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x16e
     */
    @Test
    public void test0x16e() {

        Assert.assertEquals( "Uring", reader.mapCharCodeToGlyphname( 0x16e,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x16f
     */
    @Test
    public void test0x16f() {

        Assert.assertEquals( "uring", reader.mapCharCodeToGlyphname( 0x16f,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x170
     */
    @Test
    public void test0x170() {

        Assert.assertEquals( "Udblacute",
                             reader.mapCharCodeToGlyphname( 0x170,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x171
     */
    @Test
    public void test0x171() {

        Assert.assertEquals( "udblacute",
                             reader.mapCharCodeToGlyphname( 0x171,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x172
     */
    @Test
    public void test0x172() {

        Assert.assertEquals( "Uogonek", reader.mapCharCodeToGlyphname( 0x172,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x173
     */
    @Test
    public void test0x173() {

        Assert.assertEquals( "uogonek", reader.mapCharCodeToGlyphname( 0x173,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x174
     */
    @Test
    public void test0x174() {

        Assert.assertEquals( "Wcircumflex",
                             reader.mapCharCodeToGlyphname( 0x174,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x175
     */
    @Test
    public void test0x175() {

        Assert.assertEquals( "wcircumflex",
                             reader.mapCharCodeToGlyphname( 0x175,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x176
     */
    @Test
    public void test0x176() {

        Assert.assertEquals( "Ycircumflex",
                             reader.mapCharCodeToGlyphname( 0x176,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x177
     */
    @Test
    public void test0x177() {

        Assert.assertEquals( "ycircumflex",
                             reader.mapCharCodeToGlyphname( 0x177,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x178
     */
    @Test
    public void test0x178() {

        Assert.assertEquals( "Ydieresis",
                             reader.mapCharCodeToGlyphname( 0x178,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x179
     */
    @Test
    public void test0x179() {

        Assert.assertEquals( "Zacute", reader.mapCharCodeToGlyphname( 0x179,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x17a
     */
    @Test
    public void test0x17a() {

        Assert.assertEquals( "zacute", reader.mapCharCodeToGlyphname( 0x17a,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x17b
     */
    @Test
    public void test0x17b() {

        Assert.assertEquals( "Zdot",
                             reader.mapCharCodeToGlyphname( 0x17b, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x17c
     */
    @Test
    public void test0x17c() {

        Assert.assertEquals( "zdot",
                             reader.mapCharCodeToGlyphname( 0x17c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x17d
     */
    @Test
    public void test0x17d() {

        Assert.assertEquals( "Zcaron", reader.mapCharCodeToGlyphname( 0x17d,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x17e
     */
    @Test
    public void test0x17e() {

        Assert.assertEquals( "zcaron", reader.mapCharCodeToGlyphname( 0x17e,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x17f
     */
    @Test
    public void test0x17f() {

        Assert.assertEquals( "longs", reader.mapCharCodeToGlyphname( 0x17f,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x192
     */
    @Test
    public void test0x192() {

        Assert.assertEquals( "florin", reader.mapCharCodeToGlyphname( 0x192,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1e80
     */
    @Test
    public void test0x1e80() {

        Assert.assertEquals( "Wgrave", reader.mapCharCodeToGlyphname( 0x1e80,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1e81
     */
    @Test
    public void test0x1e81() {

        Assert.assertEquals( "wgrave", reader.mapCharCodeToGlyphname( 0x1e81,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1e82
     */
    @Test
    public void test0x1e82() {

        Assert.assertEquals( "Wacute", reader.mapCharCodeToGlyphname( 0x1e82,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1e83
     */
    @Test
    public void test0x1e83() {

        Assert.assertEquals( "wacute", reader.mapCharCodeToGlyphname( 0x1e83,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1e84
     */
    @Test
    public void test0x1e84() {

        Assert.assertEquals( "Wdieresis",
                             reader.mapCharCodeToGlyphname( 0x1e84,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x1e85
     */
    @Test
    public void test0x1e85() {

        Assert.assertEquals( "wdieresis",
                             reader.mapCharCodeToGlyphname( 0x1e85,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x1ef2
     */
    @Test
    public void test0x1ef2() {

        Assert.assertEquals( "Ygrave", reader.mapCharCodeToGlyphname( 0x1ef2,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1ef3
     */
    @Test
    public void test0x1ef3() {

        Assert.assertEquals( "ygrave", reader.mapCharCodeToGlyphname( 0x1ef3,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x1fa
     */
    @Test
    public void test0x1fa() {

        Assert.assertEquals( "Aringacute",
                             reader.mapCharCodeToGlyphname( 0x1fa,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x1fb
     */
    @Test
    public void test0x1fb() {

        Assert.assertEquals( "aringacute",
                             reader.mapCharCodeToGlyphname( 0x1fb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x1fc
     */
    @Test
    public void test0x1fc() {

        Assert.assertEquals( "AEacute", reader.mapCharCodeToGlyphname( 0x1fc,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x1fd
     */
    @Test
    public void test0x1fd() {

        Assert.assertEquals( "aeacute", reader.mapCharCodeToGlyphname( 0x1fd,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x1fe
     */
    @Test
    public void test0x1fe() {

        Assert.assertEquals( "Oslashacute",
                             reader.mapCharCodeToGlyphname( 0x1fe,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x1ff
     */
    @Test
    public void test0x1ff() {

        Assert.assertEquals( "oslashacute",
                             reader.mapCharCodeToGlyphname( 0x1ff,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x20
     */
    @Test
    public void test0x20() {

        Assert.assertEquals( "space",
                             reader.mapCharCodeToGlyphname( 0x20, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2013
     */
    @Test
    public void test0x2013() {

        Assert.assertEquals( "endash", reader.mapCharCodeToGlyphname( 0x2013,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2014
     */
    @Test
    public void test0x2014() {

        Assert.assertEquals( "emdash", reader.mapCharCodeToGlyphname( 0x2014,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2015
     */
    @Test
    public void test0x2015() {

        Assert.assertEquals( "afii00208",
                             reader.mapCharCodeToGlyphname( 0x2015,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2017
     */
    @Test
    public void test0x2017() {

        Assert.assertEquals( "underscoredbl",
                             reader.mapCharCodeToGlyphname( 0x2017,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2018
     */
    @Test
    public void test0x2018() {

        Assert.assertEquals( "quoteleft",
                             reader.mapCharCodeToGlyphname( 0x2018,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2019
     */
    @Test
    public void test0x2019() {

        Assert.assertEquals( "quoteright",
                             reader.mapCharCodeToGlyphname( 0x2019,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x201a
     */
    @Test
    public void test0x201a() {

        Assert.assertEquals( "quotesinglbase",
                             reader.mapCharCodeToGlyphname( 0x201a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x201b
     */
    @Test
    public void test0x201b() {

        Assert.assertEquals( "quotereversed",
                             reader.mapCharCodeToGlyphname( 0x201b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x201c
     */
    @Test
    public void test0x201c() {

        Assert.assertEquals( "quotedblleft",
                             reader.mapCharCodeToGlyphname( 0x201c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x201d
     */
    @Test
    public void test0x201d() {

        Assert.assertEquals( "quotedblright",
                             reader.mapCharCodeToGlyphname( 0x201d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x201e
     */
    @Test
    public void test0x201e() {

        Assert.assertEquals( "quotedblbase",
                             reader.mapCharCodeToGlyphname( 0x201e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2020
     */
    @Test
    public void test0x2020() {

        Assert.assertEquals( "dagger", reader.mapCharCodeToGlyphname( 0x2020,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2021
     */
    @Test
    public void test0x2021() {

        Assert.assertEquals( "daggerdbl",
                             reader.mapCharCodeToGlyphname( 0x2021,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2022
     */
    @Test
    public void test0x2022() {

        Assert.assertEquals( "bullet", reader.mapCharCodeToGlyphname( 0x2022,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2026
     */
    @Test
    public void test0x2026() {

        Assert.assertEquals( "ellipsis",
                             reader.mapCharCodeToGlyphname( 0x2026,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2030
     */
    @Test
    public void test0x2030() {

        Assert.assertEquals( "perthousand",
                             reader.mapCharCodeToGlyphname( 0x2030,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2032
     */
    @Test
    public void test0x2032() {

        Assert.assertEquals( "minute", reader.mapCharCodeToGlyphname( 0x2032,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2033
     */
    @Test
    public void test0x2033() {

        Assert.assertEquals( "second", reader.mapCharCodeToGlyphname( 0x2033,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2039
     */
    @Test
    public void test0x2039() {

        Assert.assertEquals( "guilsinglleft",
                             reader.mapCharCodeToGlyphname( 0x2039,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x203a
     */
    @Test
    public void test0x203a() {

        Assert.assertEquals( "guilsinglright",
                             reader.mapCharCodeToGlyphname( 0x203a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x203c
     */
    @Test
    public void test0x203c() {

        Assert.assertEquals( "exclamdbl",
                             reader.mapCharCodeToGlyphname( 0x203c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x203e
     */
    @Test
    public void test0x203e() {

        Assert.assertEquals( "radicalex",
                             reader.mapCharCodeToGlyphname( 0x203e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2044
     */
    @Test
    public void test0x2044() {

        Assert.assertEquals( "fraction1",
                             reader.mapCharCodeToGlyphname( 0x2044,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x207f
     */
    @Test
    public void test0x207f() {

        Assert.assertEquals( "nsuperior",
                             reader.mapCharCodeToGlyphname( 0x207f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x20a3
     */
    @Test
    public void test0x20a3() {

        Assert.assertEquals( "franc", reader.mapCharCodeToGlyphname( 0x20a3,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x20a4
     */
    @Test
    public void test0x20a4() {

        Assert.assertEquals( "afii08941",
                             reader.mapCharCodeToGlyphname( 0x20a4,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x20a7
     */
    @Test
    public void test0x20a7() {

        Assert.assertEquals( "peseta", reader.mapCharCodeToGlyphname( 0x20a7,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x21
     */
    @Test
    public void test0x21() {

        Assert.assertEquals( "exclam", reader.mapCharCodeToGlyphname( 0x21,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2105
     */
    @Test
    public void test0x2105() {

        Assert.assertEquals( "afii61248",
                             reader.mapCharCodeToGlyphname( 0x2105,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2113
     */
    @Test
    public void test0x2113() {

        Assert.assertEquals( "afii61289",
                             reader.mapCharCodeToGlyphname( 0x2113,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2116
     */
    @Test
    public void test0x2116() {

        Assert.assertEquals( "afii61352",
                             reader.mapCharCodeToGlyphname( 0x2116,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2122
     */
    @Test
    public void test0x2122() {

        Assert.assertEquals( "trademark",
                             reader.mapCharCodeToGlyphname( 0x2122,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2126
     */
    @Test
    public void test0x2126() {

        Assert.assertEquals( "Ohm",
                             reader.mapCharCodeToGlyphname( 0x2126,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x212e
     */
    @Test
    public void test0x212e() {

        Assert.assertEquals( "estimated",
                             reader.mapCharCodeToGlyphname( 0x212e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x215b
     */
    @Test
    public void test0x215b() {

        Assert.assertEquals( "oneeighth",
                             reader.mapCharCodeToGlyphname( 0x215b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x215c
     */
    @Test
    public void test0x215c() {

        Assert.assertEquals( "threeeighths",
                             reader.mapCharCodeToGlyphname( 0x215c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x215d
     */
    @Test
    public void test0x215d() {

        Assert.assertEquals( "fiveeighths",
                             reader.mapCharCodeToGlyphname( 0x215d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x215e
     */
    @Test
    public void test0x215e() {

        Assert.assertEquals( "seveneighths",
                             reader.mapCharCodeToGlyphname( 0x215e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2190
     */
    @Test
    public void test0x2190() {

        Assert.assertEquals( "arrowleft",
                             reader.mapCharCodeToGlyphname( 0x2190,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2191
     */
    @Test
    public void test0x2191() {

        Assert.assertEquals( "arrowup",
                             reader.mapCharCodeToGlyphname( 0x2191,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2192
     */
    @Test
    public void test0x2192() {

        Assert.assertEquals( "arrowright",
                             reader.mapCharCodeToGlyphname( 0x2192,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2193
     */
    @Test
    public void test0x2193() {

        Assert.assertEquals( "arrowdown",
                             reader.mapCharCodeToGlyphname( 0x2193,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2194
     */
    @Test
    public void test0x2194() {

        Assert.assertEquals( "arrowboth",
                             reader.mapCharCodeToGlyphname( 0x2194,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2195
     */
    @Test
    public void test0x2195() {

        Assert.assertEquals( "arrowupdn",
                             reader.mapCharCodeToGlyphname( 0x2195,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x21a8
     */
    @Test
    public void test0x21a8() {

        Assert.assertEquals( "arrowupdnbse",
                             reader.mapCharCodeToGlyphname( 0x21a8,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x22
     */
    @Test
    public void test0x22() {

        Assert.assertEquals( "quotedbl", reader.mapCharCodeToGlyphname( 0x22,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0x2202
     */
    @Test
    public void test0x2202() {

        Assert.assertEquals( "partialdiff",
                             reader.mapCharCodeToGlyphname( 0x2202,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x220f
     */
    @Test
    public void test0x220f() {

        Assert.assertEquals( "product",
                             reader.mapCharCodeToGlyphname( 0x220f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2211
     */
    @Test
    public void test0x2211() {

        Assert.assertEquals( "summation",
                             reader.mapCharCodeToGlyphname( 0x2211,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2212
     */
    @Test
    public void test0x2212() {

        Assert.assertEquals( "minus", reader.mapCharCodeToGlyphname( 0x2212,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2215
     */
    @Test
    public void test0x2215() {

        Assert.assertEquals( "fraction",
                             reader.mapCharCodeToGlyphname( 0x2215,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x221a
     */
    @Test
    public void test0x221a() {

        Assert.assertEquals( "radical",
                             reader.mapCharCodeToGlyphname( 0x221a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x221e
     */
    @Test
    public void test0x221e() {

        Assert.assertEquals( "infinity",
                             reader.mapCharCodeToGlyphname( 0x221e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x221f
     */
    @Test
    public void test0x221f() {

        Assert.assertEquals( "orthogonal",
                             reader.mapCharCodeToGlyphname( 0x221f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2229
     */
    @Test
    public void test0x2229() {

        Assert.assertEquals( "intersection",
                             reader.mapCharCodeToGlyphname( 0x2229,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2248
     */
    @Test
    public void test0x2248() {

        Assert.assertEquals( "approxequal",
                             reader.mapCharCodeToGlyphname( 0x2248,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2260
     */
    @Test
    public void test0x2260() {

        Assert.assertEquals( "notequal",
                             reader.mapCharCodeToGlyphname( 0x2260,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2261
     */
    @Test
    public void test0x2261() {

        Assert.assertEquals( "equivalence",
                             reader.mapCharCodeToGlyphname( 0x2261,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2264
     */
    @Test
    public void test0x2264() {

        Assert.assertEquals( "lessequal",
                             reader.mapCharCodeToGlyphname( 0x2264,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2265
     */
    @Test
    public void test0x2265() {

        Assert.assertEquals( "greaterequal",
                             reader.mapCharCodeToGlyphname( 0x2265,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x23
     */
    @Test
    public void test0x23() {

        Assert.assertEquals( "numbersign",
                             reader.mapCharCodeToGlyphname( 0x23,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2302
     */
    @Test
    public void test0x2302() {

        Assert.assertEquals( "house", reader.mapCharCodeToGlyphname( 0x2302,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2310
     */
    @Test
    public void test0x2310() {

        Assert.assertEquals( "revlogicalnot",
                             reader.mapCharCodeToGlyphname( 0x2310,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2320
     */
    @Test
    public void test0x2320() {

        Assert.assertEquals( "integraltp",
                             reader.mapCharCodeToGlyphname( 0x2320,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2321
     */
    @Test
    public void test0x2321() {

        Assert.assertEquals( "integralbt",
                             reader.mapCharCodeToGlyphname( 0x2321,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x24
     */
    @Test
    public void test0x24() {

        Assert.assertEquals( "dollar", reader.mapCharCodeToGlyphname( 0x24,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x25
     */
    @Test
    public void test0x25() {

        Assert.assertEquals( "percent", reader.mapCharCodeToGlyphname( 0x25,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x2500
     */
    @Test
    public void test0x2500() {

        Assert.assertEquals( "SF100000",
                             reader.mapCharCodeToGlyphname( 0x2500,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2502
     */
    @Test
    public void test0x2502() {

        Assert.assertEquals( "SF110000",
                             reader.mapCharCodeToGlyphname( 0x2502,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x250c
     */
    @Test
    public void test0x250c() {

        Assert.assertEquals( "SF010000",
                             reader.mapCharCodeToGlyphname( 0x250c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2510
     */
    @Test
    public void test0x2510() {

        Assert.assertEquals( "SF030000",
                             reader.mapCharCodeToGlyphname( 0x2510,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2514
     */
    @Test
    public void test0x2514() {

        Assert.assertEquals( "SF020000",
                             reader.mapCharCodeToGlyphname( 0x2514,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2518
     */
    @Test
    public void test0x2518() {

        Assert.assertEquals( "SF040000",
                             reader.mapCharCodeToGlyphname( 0x2518,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x251c
     */
    @Test
    public void test0x251c() {

        Assert.assertEquals( "SF080000",
                             reader.mapCharCodeToGlyphname( 0x251c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2524
     */
    @Test
    public void test0x2524() {

        Assert.assertEquals( "SF090000",
                             reader.mapCharCodeToGlyphname( 0x2524,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x252c
     */
    @Test
    public void test0x252c() {

        Assert.assertEquals( "SF060000",
                             reader.mapCharCodeToGlyphname( 0x252c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2534
     */
    @Test
    public void test0x2534() {

        Assert.assertEquals( "SF070000",
                             reader.mapCharCodeToGlyphname( 0x2534,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x253c
     */
    @Test
    public void test0x253c() {

        Assert.assertEquals( "SF050000",
                             reader.mapCharCodeToGlyphname( 0x253c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2550
     */
    @Test
    public void test0x2550() {

        Assert.assertEquals( "SF430000",
                             reader.mapCharCodeToGlyphname( 0x2550,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2551
     */
    @Test
    public void test0x2551() {

        Assert.assertEquals( "SF240000",
                             reader.mapCharCodeToGlyphname( 0x2551,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2552
     */
    @Test
    public void test0x2552() {

        Assert.assertEquals( "SF510000",
                             reader.mapCharCodeToGlyphname( 0x2552,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2553
     */
    @Test
    public void test0x2553() {

        Assert.assertEquals( "SF520000",
                             reader.mapCharCodeToGlyphname( 0x2553,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2554
     */
    @Test
    public void test0x2554() {

        Assert.assertEquals( "SF390000",
                             reader.mapCharCodeToGlyphname( 0x2554,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2555
     */
    @Test
    public void test0x2555() {

        Assert.assertEquals( "SF220000",
                             reader.mapCharCodeToGlyphname( 0x2555,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2556
     */
    @Test
    public void test0x2556() {

        Assert.assertEquals( "SF210000",
                             reader.mapCharCodeToGlyphname( 0x2556,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2557
     */
    @Test
    public void test0x2557() {

        Assert.assertEquals( "SF250000",
                             reader.mapCharCodeToGlyphname( 0x2557,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2558
     */
    @Test
    public void test0x2558() {

        Assert.assertEquals( "SF500000",
                             reader.mapCharCodeToGlyphname( 0x2558,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2559
     */
    @Test
    public void test0x2559() {

        Assert.assertEquals( "SF490000",
                             reader.mapCharCodeToGlyphname( 0x2559,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x255a
     */
    @Test
    public void test0x255a() {

        Assert.assertEquals( "SF380000",
                             reader.mapCharCodeToGlyphname( 0x255a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x255b
     */
    @Test
    public void test0x255b() {

        Assert.assertEquals( "SF280000",
                             reader.mapCharCodeToGlyphname( 0x255b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x255c
     */
    @Test
    public void test0x255c() {

        Assert.assertEquals( "SF270000",
                             reader.mapCharCodeToGlyphname( 0x255c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x255d
     */
    @Test
    public void test0x255d() {

        Assert.assertEquals( "SF260000",
                             reader.mapCharCodeToGlyphname( 0x255d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x255e
     */
    @Test
    public void test0x255e() {

        Assert.assertEquals( "SF360000",
                             reader.mapCharCodeToGlyphname( 0x255e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x255f
     */
    @Test
    public void test0x255f() {

        Assert.assertEquals( "SF370000",
                             reader.mapCharCodeToGlyphname( 0x255f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2560
     */
    @Test
    public void test0x2560() {

        Assert.assertEquals( "SF420000",
                             reader.mapCharCodeToGlyphname( 0x2560,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2561
     */
    @Test
    public void test0x2561() {

        Assert.assertEquals( "SF190000",
                             reader.mapCharCodeToGlyphname( 0x2561,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2562
     */
    @Test
    public void test0x2562() {

        Assert.assertEquals( "SF200000",
                             reader.mapCharCodeToGlyphname( 0x2562,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2563
     */
    @Test
    public void test0x2563() {

        Assert.assertEquals( "SF230000",
                             reader.mapCharCodeToGlyphname( 0x2563,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2564
     */
    @Test
    public void test0x2564() {

        Assert.assertEquals( "SF470000",
                             reader.mapCharCodeToGlyphname( 0x2564,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2565
     */
    @Test
    public void test0x2565() {

        Assert.assertEquals( "SF480000",
                             reader.mapCharCodeToGlyphname( 0x2565,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2566
     */
    @Test
    public void test0x2566() {

        Assert.assertEquals( "SF410000",
                             reader.mapCharCodeToGlyphname( 0x2566,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2567
     */
    @Test
    public void test0x2567() {

        Assert.assertEquals( "SF450000",
                             reader.mapCharCodeToGlyphname( 0x2567,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2568
     */
    @Test
    public void test0x2568() {

        Assert.assertEquals( "SF460000",
                             reader.mapCharCodeToGlyphname( 0x2568,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2569
     */
    @Test
    public void test0x2569() {

        Assert.assertEquals( "SF400000",
                             reader.mapCharCodeToGlyphname( 0x2569,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x256a
     */
    @Test
    public void test0x256a() {

        Assert.assertEquals( "SF540000",
                             reader.mapCharCodeToGlyphname( 0x256a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x256b
     */
    @Test
    public void test0x256b() {

        Assert.assertEquals( "SF530000",
                             reader.mapCharCodeToGlyphname( 0x256b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x256c
     */
    @Test
    public void test0x256c() {

        Assert.assertEquals( "SF440000",
                             reader.mapCharCodeToGlyphname( 0x256c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2580
     */
    @Test
    public void test0x2580() {

        Assert.assertEquals( "upblock",
                             reader.mapCharCodeToGlyphname( 0x2580,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2584
     */
    @Test
    public void test0x2584() {

        Assert.assertEquals( "dnblock",
                             reader.mapCharCodeToGlyphname( 0x2584,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2588
     */
    @Test
    public void test0x2588() {

        Assert.assertEquals( "block", reader.mapCharCodeToGlyphname( 0x2588,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x258c
     */
    @Test
    public void test0x258c() {

        Assert.assertEquals( "lfblock",
                             reader.mapCharCodeToGlyphname( 0x258c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2590
     */
    @Test
    public void test0x2590() {

        Assert.assertEquals( "rtblock",
                             reader.mapCharCodeToGlyphname( 0x2590,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2591
     */
    @Test
    public void test0x2591() {

        Assert.assertEquals( "ltshade",
                             reader.mapCharCodeToGlyphname( 0x2591,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2592
     */
    @Test
    public void test0x2592() {

        Assert.assertEquals( "shade", reader.mapCharCodeToGlyphname( 0x2592,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2593
     */
    @Test
    public void test0x2593() {

        Assert.assertEquals( "dkshade",
                             reader.mapCharCodeToGlyphname( 0x2593,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25a0
     */
    @Test
    public void test0x25a0() {

        Assert.assertEquals( "filledbox",
                             reader.mapCharCodeToGlyphname( 0x25a0,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25a1
     */
    @Test
    public void test0x25a1() {

        Assert.assertEquals( "H22073", reader.mapCharCodeToGlyphname( 0x25a1,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x25aa
     */
    @Test
    public void test0x25aa() {

        Assert.assertEquals( "H18543", reader.mapCharCodeToGlyphname( 0x25aa,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x25ab
     */
    @Test
    public void test0x25ab() {

        Assert.assertEquals( "H18551", reader.mapCharCodeToGlyphname( 0x25ab,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x25ac
     */
    @Test
    public void test0x25ac() {

        Assert.assertEquals( "filledrect",
                             reader.mapCharCodeToGlyphname( 0x25ac,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25b2
     */
    @Test
    public void test0x25b2() {

        Assert.assertEquals( "triagup",
                             reader.mapCharCodeToGlyphname( 0x25b2,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25ba
     */
    @Test
    public void test0x25ba() {

        Assert.assertEquals( "triagrt",
                             reader.mapCharCodeToGlyphname( 0x25ba,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25bc
     */
    @Test
    public void test0x25bc() {

        Assert.assertEquals( "triagdn",
                             reader.mapCharCodeToGlyphname( 0x25bc,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25c4
     */
    @Test
    public void test0x25c4() {

        Assert.assertEquals( "triaglf",
                             reader.mapCharCodeToGlyphname( 0x25c4,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25ca
     */
    @Test
    public void test0x25ca() {

        Assert.assertEquals( "lozenge",
                             reader.mapCharCodeToGlyphname( 0x25ca,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25cb
     */
    @Test
    public void test0x25cb() {

        Assert.assertEquals( "circle", reader.mapCharCodeToGlyphname( 0x25cb,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x25cf
     */
    @Test
    public void test0x25cf() {

        Assert.assertEquals( "H18533", reader.mapCharCodeToGlyphname( 0x25cf,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x25d8
     */
    @Test
    public void test0x25d8() {

        Assert.assertEquals( "invbullet",
                             reader.mapCharCodeToGlyphname( 0x25d8,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25d9
     */
    @Test
    public void test0x25d9() {

        Assert.assertEquals( "invcircle",
                             reader.mapCharCodeToGlyphname( 0x25d9,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x25e6
     */
    @Test
    public void test0x25e6() {

        Assert.assertEquals( "openbullet",
                             reader.mapCharCodeToGlyphname( 0x25e6,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x26
     */
    @Test
    public void test0x26() {

        Assert.assertEquals( "ampersand",
                             reader.mapCharCodeToGlyphname( 0x26,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x263a
     */
    @Test
    public void test0x263a() {

        Assert.assertEquals( "smileface",
                             reader.mapCharCodeToGlyphname( 0x263a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x263b
     */
    @Test
    public void test0x263b() {

        Assert.assertEquals( "invsmileface",
                             reader.mapCharCodeToGlyphname( 0x263b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x263c
     */
    @Test
    public void test0x263c() {

        Assert.assertEquals( "sun",
                             reader.mapCharCodeToGlyphname( 0x263c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2640
     */
    @Test
    public void test0x2640() {

        Assert.assertEquals( "female", reader.mapCharCodeToGlyphname( 0x2640,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2642
     */
    @Test
    public void test0x2642() {

        Assert.assertEquals( "male", reader.mapCharCodeToGlyphname( 0x2642,
                                                                    0,
                                                                    (short) 3,
                                                                    (short) 1 ) );
    }

    /**
     * test 0x2660
     */
    @Test
    public void test0x2660() {

        Assert.assertEquals( "spade", reader.mapCharCodeToGlyphname( 0x2660,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2663
     */
    @Test
    public void test0x2663() {

        Assert.assertEquals( "club", reader.mapCharCodeToGlyphname( 0x2663,
                                                                    0,
                                                                    (short) 3,
                                                                    (short) 1 ) );
    }

    /**
     * test 0x2665
     */
    @Test
    public void test0x2665() {

        Assert.assertEquals( "heart", reader.mapCharCodeToGlyphname( 0x2665,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2666
     */
    @Test
    public void test0x2666() {

        Assert.assertEquals( "diamond",
                             reader.mapCharCodeToGlyphname( 0x2666,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x266a
     */
    @Test
    public void test0x266a() {

        Assert.assertEquals( "musicalnote",
                             reader.mapCharCodeToGlyphname( 0x266a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x266b
     */
    @Test
    public void test0x266b() {

        Assert.assertEquals( "musicalnotedbl",
                             reader.mapCharCodeToGlyphname( 0x266b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x27
     */
    @Test
    public void test0x27() {

        Assert.assertEquals( "quotesingle",
                             reader.mapCharCodeToGlyphname( 0x27,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x28
     */
    @Test
    public void test0x28() {

        Assert.assertEquals( "parenleft",
                             reader.mapCharCodeToGlyphname( 0x28,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x29
     */
    @Test
    public void test0x29() {

        Assert.assertEquals( "parenright",
                             reader.mapCharCodeToGlyphname( 0x29,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2a
     */
    @Test
    public void test0x2a() {

        Assert.assertEquals( "asterisk", reader.mapCharCodeToGlyphname( 0x2a,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0x2b
     */
    @Test
    public void test0x2b() {

        Assert.assertEquals( "plus",
                             reader.mapCharCodeToGlyphname( 0x2b, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2c
     */
    @Test
    public void test0x2c() {

        Assert.assertEquals( "comma",
                             reader.mapCharCodeToGlyphname( 0x2c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2c6
     */
    @Test
    public void test0x2c6() {

        Assert.assertEquals( "circumflex",
                             reader.mapCharCodeToGlyphname( 0x2c6,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2c7
     */
    @Test
    public void test0x2c7() {

        Assert.assertEquals( "caron", reader.mapCharCodeToGlyphname( 0x2c7,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2d
     */
    @Test
    public void test0x2d() {

        Assert.assertEquals( "hyphen", reader.mapCharCodeToGlyphname( 0x2d,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2d8
     */
    @Test
    public void test0x2d8() {

        Assert.assertEquals( "breve", reader.mapCharCodeToGlyphname( 0x2d8,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2d9
     */
    @Test
    public void test0x2d9() {

        Assert.assertEquals( "dotaccent",
                             reader.mapCharCodeToGlyphname( 0x2d9,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2da
     */
    @Test
    public void test0x2da() {

        Assert.assertEquals( "ring",
                             reader.mapCharCodeToGlyphname( 0x2da, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2db
     */
    @Test
    public void test0x2db() {

        Assert.assertEquals( "ogonek", reader.mapCharCodeToGlyphname( 0x2db,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2dc
     */
    @Test
    public void test0x2dc() {

        Assert.assertEquals( "tilde", reader.mapCharCodeToGlyphname( 0x2dc,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x2dd
     */
    @Test
    public void test0x2dd() {

        Assert.assertEquals( "hungarumlaut",
                             reader.mapCharCodeToGlyphname( 0x2dd,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x2e
     */
    @Test
    public void test0x2e() {

        Assert.assertEquals( "period", reader.mapCharCodeToGlyphname( 0x2e,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x2f
     */
    @Test
    public void test0x2f() {

        Assert.assertEquals( "slash",
                             reader.mapCharCodeToGlyphname( 0x2f, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x30
     */
    @Test
    public void test0x30() {

        Assert.assertEquals( "zero",
                             reader.mapCharCodeToGlyphname( 0x30, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x31
     */
    @Test
    public void test0x31() {

        Assert.assertEquals( "one",
                             reader.mapCharCodeToGlyphname( 0x31, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x32
     */
    @Test
    public void test0x32() {

        Assert.assertEquals( "two",
                             reader.mapCharCodeToGlyphname( 0x32, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x33
     */
    @Test
    public void test0x33() {

        Assert.assertEquals( "three",
                             reader.mapCharCodeToGlyphname( 0x33, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x34
     */
    @Test
    public void test0x34() {

        Assert.assertEquals( "four",
                             reader.mapCharCodeToGlyphname( 0x34, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x35
     */
    @Test
    public void test0x35() {

        Assert.assertEquals( "five",
                             reader.mapCharCodeToGlyphname( 0x35, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x36
     */
    @Test
    public void test0x36() {

        Assert.assertEquals( "six",
                             reader.mapCharCodeToGlyphname( 0x36, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x37
     */
    @Test
    public void test0x37() {

        Assert.assertEquals( "seven",
                             reader.mapCharCodeToGlyphname( 0x37, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x37e
     */
    @Test
    public void test0x37e() {

        Assert.assertEquals( "semicolon",
                             reader.mapCharCodeToGlyphname( 0x37e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x38
     */
    @Test
    public void test0x38() {

        Assert.assertEquals( "eight",
                             reader.mapCharCodeToGlyphname( 0x38, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x384
     */
    @Test
    public void test0x384() {

        Assert.assertEquals( "tonos", reader.mapCharCodeToGlyphname( 0x384,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x385
     */
    @Test
    public void test0x385() {

        Assert.assertEquals( "dieresistonos",
                             reader.mapCharCodeToGlyphname( 0x385,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x386
     */
    @Test
    public void test0x386() {

        Assert.assertEquals( "Alphatonos",
                             reader.mapCharCodeToGlyphname( 0x386,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x387
     */
    @Test
    public void test0x387() {

        Assert.assertEquals( "anoteleia",
                             reader.mapCharCodeToGlyphname( 0x387,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x388
     */
    @Test
    public void test0x388() {

        Assert.assertEquals( "Epsilontonos",
                             reader.mapCharCodeToGlyphname( 0x388,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x389
     */
    @Test
    public void test0x389() {

        Assert.assertEquals( "Etatonos",
                             reader.mapCharCodeToGlyphname( 0x389,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x38a
     */
    @Test
    public void test0x38a() {

        Assert.assertEquals( "Iotatonos",
                             reader.mapCharCodeToGlyphname( 0x38a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x38c
     */
    @Test
    public void test0x38c() {

        Assert.assertEquals( "Omicrontonos",
                             reader.mapCharCodeToGlyphname( 0x38c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x38e
     */
    @Test
    public void test0x38e() {

        Assert.assertEquals( "Upsilontonos",
                             reader.mapCharCodeToGlyphname( 0x38e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x38f
     */
    @Test
    public void test0x38f() {

        Assert.assertEquals( "Omegatonos",
                             reader.mapCharCodeToGlyphname( 0x38f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x39
     */
    @Test
    public void test0x39() {

        Assert.assertEquals( "nine",
                             reader.mapCharCodeToGlyphname( 0x39, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x390
     */
    @Test
    public void test0x390() {

        Assert.assertEquals( "iotadieresistonos",
                             reader.mapCharCodeToGlyphname( 0x390,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x391
     */
    @Test
    public void test0x391() {

        Assert.assertEquals( "Alpha", reader.mapCharCodeToGlyphname( 0x391,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x392
     */
    @Test
    public void test0x392() {

        Assert.assertEquals( "Beta",
                             reader.mapCharCodeToGlyphname( 0x392, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x393
     */
    @Test
    public void test0x393() {

        Assert.assertEquals( "Gamma", reader.mapCharCodeToGlyphname( 0x393,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x394
     */
    @Test
    public void test0x394() {

        Assert.assertEquals( "Delta", reader.mapCharCodeToGlyphname( 0x394,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x395
     */
    @Test
    public void test0x395() {

        Assert.assertEquals( "Epsilon", reader.mapCharCodeToGlyphname( 0x395,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x396
     */
    @Test
    public void test0x396() {

        Assert.assertEquals( "Zeta",
                             reader.mapCharCodeToGlyphname( 0x396, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x397
     */
    @Test
    public void test0x397() {

        Assert.assertEquals( "Eta",
                             reader.mapCharCodeToGlyphname( 0x397, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x398
     */
    @Test
    public void test0x398() {

        Assert.assertEquals( "Theta", reader.mapCharCodeToGlyphname( 0x398,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x399
     */
    @Test
    public void test0x399() {

        Assert.assertEquals( "Iota",
                             reader.mapCharCodeToGlyphname( 0x399, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x39a
     */
    @Test
    public void test0x39a() {

        Assert.assertEquals( "Kappa", reader.mapCharCodeToGlyphname( 0x39a,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x39b
     */
    @Test
    public void test0x39b() {

        Assert.assertEquals( "Lambda", reader.mapCharCodeToGlyphname( 0x39b,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x39c
     */
    @Test
    public void test0x39c() {

        Assert.assertEquals( "Mu",
                             reader.mapCharCodeToGlyphname( 0x39c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x39d
     */
    @Test
    public void test0x39d() {

        Assert.assertEquals( "Nu",
                             reader.mapCharCodeToGlyphname( 0x39d, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x39e
     */
    @Test
    public void test0x39e() {

        Assert.assertEquals( "Xi",
                             reader.mapCharCodeToGlyphname( 0x39e, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x39f
     */
    @Test
    public void test0x39f() {

        Assert.assertEquals( "Omicron", reader.mapCharCodeToGlyphname( 0x39f,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x3a
     */
    @Test
    public void test0x3a() {

        Assert.assertEquals( "colon",
                             reader.mapCharCodeToGlyphname( 0x3a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a0
     */
    @Test
    public void test0x3a0() {

        Assert.assertEquals( "Pi",
                             reader.mapCharCodeToGlyphname( 0x3a0, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a1
     */
    @Test
    public void test0x3a1() {

        Assert.assertEquals( "Rho",
                             reader.mapCharCodeToGlyphname( 0x3a1, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a3
     */
    @Test
    public void test0x3a3() {

        Assert.assertEquals( "Sigma", reader.mapCharCodeToGlyphname( 0x3a3,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3a4
     */
    @Test
    public void test0x3a4() {

        Assert.assertEquals( "Tau",
                             reader.mapCharCodeToGlyphname( 0x3a4, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a5
     */
    @Test
    public void test0x3a5() {

        Assert.assertEquals( "Upsilon", reader.mapCharCodeToGlyphname( 0x3a5,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x3a6
     */
    @Test
    public void test0x3a6() {

        Assert.assertEquals( "Phi",
                             reader.mapCharCodeToGlyphname( 0x3a6, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a7
     */
    @Test
    public void test0x3a7() {

        Assert.assertEquals( "Chi",
                             reader.mapCharCodeToGlyphname( 0x3a7, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a8
     */
    @Test
    public void test0x3a8() {

        Assert.assertEquals( "Psi",
                             reader.mapCharCodeToGlyphname( 0x3a8, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3a9
     */
    @Test
    public void test0x3a9() {

        Assert.assertEquals( "Omega", reader.mapCharCodeToGlyphname( 0x3a9,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3aa
     */
    @Test
    public void test0x3aa() {

        Assert.assertEquals( "Iotadieresis",
                             reader.mapCharCodeToGlyphname( 0x3aa,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3ab
     */
    @Test
    public void test0x3ab() {

        Assert.assertEquals( "Upsilondieresis",
                             reader.mapCharCodeToGlyphname( 0x3ab,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3ac
     */
    @Test
    public void test0x3ac() {

        Assert.assertEquals( "alphatonos",
                             reader.mapCharCodeToGlyphname( 0x3ac,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3ad
     */
    @Test
    public void test0x3ad() {

        Assert.assertEquals( "epsilontonos",
                             reader.mapCharCodeToGlyphname( 0x3ad,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3ae
     */
    @Test
    public void test0x3ae() {

        Assert.assertEquals( "etatonos",
                             reader.mapCharCodeToGlyphname( 0x3ae,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3af
     */
    @Test
    public void test0x3af() {

        Assert.assertEquals( "iotatonos",
                             reader.mapCharCodeToGlyphname( 0x3af,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3b
     */
    @Test
    public void test0x3b() {

        Assert.assertEquals( "semicolon",
                             reader.mapCharCodeToGlyphname( 0x3b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3b0
     */
    @Test
    public void test0x3b0() {

        Assert.assertEquals( "upsilondieresistonos",
                             reader.mapCharCodeToGlyphname(
                                 0x3b0, 0, (short) 3, (short) 1 ) );
    }

    /**
     * test 0x3b1
     */
    @Test
    public void test0x3b1() {

        Assert.assertEquals( "alpha", reader.mapCharCodeToGlyphname( 0x3b1,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3b2
     */
    @Test
    public void test0x3b2() {

        Assert.assertEquals( "beta",
                             reader.mapCharCodeToGlyphname( 0x3b2, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3b3
     */
    @Test
    public void test0x3b3() {

        Assert.assertEquals( "gamma", reader.mapCharCodeToGlyphname( 0x3b3,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3b4
     */
    @Test
    public void test0x3b4() {

        Assert.assertEquals( "delta", reader.mapCharCodeToGlyphname( 0x3b4,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3b5
     */
    @Test
    public void test0x3b5() {

        Assert.assertEquals( "epsilon", reader.mapCharCodeToGlyphname( 0x3b5,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x3b6
     */
    @Test
    public void test0x3b6() {

        Assert.assertEquals( "zeta",
                             reader.mapCharCodeToGlyphname( 0x3b6, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3b7
     */
    @Test
    public void test0x3b7() {

        Assert.assertEquals( "eta",
                             reader.mapCharCodeToGlyphname( 0x3b7, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3b8
     */
    @Test
    public void test0x3b8() {

        Assert.assertEquals( "theta", reader.mapCharCodeToGlyphname( 0x3b8,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3b9
     */
    @Test
    public void test0x3b9() {

        Assert.assertEquals( "iota",
                             reader.mapCharCodeToGlyphname( 0x3b9, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3ba
     */
    @Test
    public void test0x3ba() {

        Assert.assertEquals( "kappa", reader.mapCharCodeToGlyphname( 0x3ba,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3bb
     */
    @Test
    public void test0x3bb() {

        Assert.assertEquals( "lambda", reader.mapCharCodeToGlyphname( 0x3bb,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x3bc
     */
    @Test
    public void test0x3bc() {

        Assert.assertEquals( "mu",
                             reader.mapCharCodeToGlyphname( 0x3bc, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3bd
     */
    @Test
    public void test0x3bd() {

        Assert.assertEquals( "nu",
                             reader.mapCharCodeToGlyphname( 0x3bd, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3be
     */
    @Test
    public void test0x3be() {

        Assert.assertEquals( "xi",
                             reader.mapCharCodeToGlyphname( 0x3be, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3bf
     */
    @Test
    public void test0x3bf() {

        Assert.assertEquals( "omicron", reader.mapCharCodeToGlyphname( 0x3bf,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x3c
     */
    @Test
    public void test0x3c() {

        Assert.assertEquals( "less",
                             reader.mapCharCodeToGlyphname( 0x3c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c0
     */
    @Test
    public void test0x3c0() {

        Assert.assertEquals( "pi1",
                             reader.mapCharCodeToGlyphname( 0x3c0, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c1
     */
    @Test
    public void test0x3c1() {

        Assert.assertEquals( "rho",
                             reader.mapCharCodeToGlyphname( 0x3c1, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c2
     */
    @Test
    public void test0x3c2() {

        Assert.assertEquals( "sigma1", reader.mapCharCodeToGlyphname( 0x3c2,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0x3c3
     */
    @Test
    public void test0x3c3() {

        Assert.assertEquals( "sigma", reader.mapCharCodeToGlyphname( 0x3c3,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3c4
     */
    @Test
    public void test0x3c4() {

        Assert.assertEquals( "tau",
                             reader.mapCharCodeToGlyphname( 0x3c4, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c5
     */
    @Test
    public void test0x3c5() {

        Assert.assertEquals( "upsilon", reader.mapCharCodeToGlyphname( 0x3c5,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x3c6
     */
    @Test
    public void test0x3c6() {

        Assert.assertEquals( "phi",
                             reader.mapCharCodeToGlyphname( 0x3c6, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c7
     */
    @Test
    public void test0x3c7() {

        Assert.assertEquals( "chi",
                             reader.mapCharCodeToGlyphname( 0x3c7, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c8
     */
    @Test
    public void test0x3c8() {

        Assert.assertEquals( "psi",
                             reader.mapCharCodeToGlyphname( 0x3c8, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3c9
     */
    @Test
    public void test0x3c9() {

        Assert.assertEquals( "omega", reader.mapCharCodeToGlyphname( 0x3c9,
                                                                     0,
                                                                     (short) 3,
                                                                     (short) 1 ) );
    }

    /**
     * test 0x3ca
     */
    @Test
    public void test0x3ca() {

        Assert.assertEquals( "iotadieresis",
                             reader.mapCharCodeToGlyphname( 0x3ca,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3cb
     */
    @Test
    public void test0x3cb() {

        Assert.assertEquals( "upsilondieresis",
                             reader.mapCharCodeToGlyphname( 0x3cb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3cc
     */
    @Test
    public void test0x3cc() {

        Assert.assertEquals( "omicrontonos",
                             reader.mapCharCodeToGlyphname( 0x3cc,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3cd
     */
    @Test
    public void test0x3cd() {

        Assert.assertEquals( "upsilontonos",
                             reader.mapCharCodeToGlyphname( 0x3cd,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3ce
     */
    @Test
    public void test0x3ce() {

        Assert.assertEquals( "omegatonos",
                             reader.mapCharCodeToGlyphname( 0x3ce,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3d
     */
    @Test
    public void test0x3d() {

        Assert.assertEquals( "equal",
                             reader.mapCharCodeToGlyphname( 0x3d, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x3e
     */
    @Test
    public void test0x3e() {

        Assert.assertEquals( "greater", reader.mapCharCodeToGlyphname( 0x3e,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0x3f
     */
    @Test
    public void test0x3f() {

        Assert.assertEquals( "question", reader.mapCharCodeToGlyphname( 0x3f,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0x40
     */
    @Test
    public void test0x40() {

        Assert.assertEquals( "at",
                             reader.mapCharCodeToGlyphname( 0x40, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x401
     */
    @Test
    public void test0x401() {

        Assert.assertEquals( "afii10023",
                             reader.mapCharCodeToGlyphname( 0x401,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x402
     */
    @Test
    public void test0x402() {

        Assert.assertEquals( "afii10051",
                             reader.mapCharCodeToGlyphname( 0x402,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x403
     */
    @Test
    public void test0x403() {

        Assert.assertEquals( "afii10052",
                             reader.mapCharCodeToGlyphname( 0x403,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x404
     */
    @Test
    public void test0x404() {

        Assert.assertEquals( "afii10053",
                             reader.mapCharCodeToGlyphname( 0x404,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x405
     */
    @Test
    public void test0x405() {

        Assert.assertEquals( "afii10054",
                             reader.mapCharCodeToGlyphname( 0x405,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x406
     */
    @Test
    public void test0x406() {

        Assert.assertEquals( "afii10055",
                             reader.mapCharCodeToGlyphname( 0x406,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x407
     */
    @Test
    public void test0x407() {

        Assert.assertEquals( "afii10056",
                             reader.mapCharCodeToGlyphname( 0x407,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x408
     */
    @Test
    public void test0x408() {

        Assert.assertEquals( "afii10057",
                             reader.mapCharCodeToGlyphname( 0x408,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x409
     */
    @Test
    public void test0x409() {

        Assert.assertEquals( "afii10058",
                             reader.mapCharCodeToGlyphname( 0x409,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x40a
     */
    @Test
    public void test0x40a() {

        Assert.assertEquals( "afii10059",
                             reader.mapCharCodeToGlyphname( 0x40a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x40b
     */
    @Test
    public void test0x40b() {

        Assert.assertEquals( "afii10060",
                             reader.mapCharCodeToGlyphname( 0x40b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x40c
     */
    @Test
    public void test0x40c() {

        Assert.assertEquals( "afii10061",
                             reader.mapCharCodeToGlyphname( 0x40c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x40e
     */
    @Test
    public void test0x40e() {

        Assert.assertEquals( "afii10062",
                             reader.mapCharCodeToGlyphname( 0x40e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x40f
     */
    @Test
    public void test0x40f() {

        Assert.assertEquals( "afii10145",
                             reader.mapCharCodeToGlyphname( 0x40f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41
     */
    @Test
    public void test0x41() {

        Assert.assertEquals( "A",
                             reader.mapCharCodeToGlyphname( 0x41, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x410
     */
    @Test
    public void test0x410() {

        Assert.assertEquals( "afii10017",
                             reader.mapCharCodeToGlyphname( 0x410,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x411
     */
    @Test
    public void test0x411() {

        Assert.assertEquals( "afii10018",
                             reader.mapCharCodeToGlyphname( 0x411,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x412
     */
    @Test
    public void test0x412() {

        Assert.assertEquals( "afii10019",
                             reader.mapCharCodeToGlyphname( 0x412,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x413
     */
    @Test
    public void test0x413() {

        Assert.assertEquals( "afii10020",
                             reader.mapCharCodeToGlyphname( 0x413,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x414
     */
    @Test
    public void test0x414() {

        Assert.assertEquals( "afii10021",
                             reader.mapCharCodeToGlyphname( 0x414,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x415
     */
    @Test
    public void test0x415() {

        Assert.assertEquals( "afii10022",
                             reader.mapCharCodeToGlyphname( 0x415,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x416
     */
    @Test
    public void test0x416() {

        Assert.assertEquals( "afii10024",
                             reader.mapCharCodeToGlyphname( 0x416,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x417
     */
    @Test
    public void test0x417() {

        Assert.assertEquals( "afii10025",
                             reader.mapCharCodeToGlyphname( 0x417,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x418
     */
    @Test
    public void test0x418() {

        Assert.assertEquals( "afii10026",
                             reader.mapCharCodeToGlyphname( 0x418,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x419
     */
    @Test
    public void test0x419() {

        Assert.assertEquals( "afii10027",
                             reader.mapCharCodeToGlyphname( 0x419,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41a
     */
    @Test
    public void test0x41a() {

        Assert.assertEquals( "afii10028",
                             reader.mapCharCodeToGlyphname( 0x41a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41b
     */
    @Test
    public void test0x41b() {

        Assert.assertEquals( "afii10029",
                             reader.mapCharCodeToGlyphname( 0x41b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41c
     */
    @Test
    public void test0x41c() {

        Assert.assertEquals( "afii10030",
                             reader.mapCharCodeToGlyphname( 0x41c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41d
     */
    @Test
    public void test0x41d() {

        Assert.assertEquals( "afii10031",
                             reader.mapCharCodeToGlyphname( 0x41d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41e
     */
    @Test
    public void test0x41e() {

        Assert.assertEquals( "afii10032",
                             reader.mapCharCodeToGlyphname( 0x41e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x41f
     */
    @Test
    public void test0x41f() {

        Assert.assertEquals( "afii10033",
                             reader.mapCharCodeToGlyphname( 0x41f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42
     */
    @Test
    public void test0x42() {

        Assert.assertEquals( "B",
                             reader.mapCharCodeToGlyphname( 0x42, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x420
     */
    @Test
    public void test0x420() {

        Assert.assertEquals( "afii10034",
                             reader.mapCharCodeToGlyphname( 0x420,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x421
     */
    @Test
    public void test0x421() {

        Assert.assertEquals( "afii10035",
                             reader.mapCharCodeToGlyphname( 0x421,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x422
     */
    @Test
    public void test0x422() {

        Assert.assertEquals( "afii10036",
                             reader.mapCharCodeToGlyphname( 0x422,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x423
     */
    @Test
    public void test0x423() {

        Assert.assertEquals( "afii10037",
                             reader.mapCharCodeToGlyphname( 0x423,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x424
     */
    @Test
    public void test0x424() {

        Assert.assertEquals( "afii10038",
                             reader.mapCharCodeToGlyphname( 0x424,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x425
     */
    @Test
    public void test0x425() {

        Assert.assertEquals( "afii10039",
                             reader.mapCharCodeToGlyphname( 0x425,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x426
     */
    @Test
    public void test0x426() {

        Assert.assertEquals( "afii10040",
                             reader.mapCharCodeToGlyphname( 0x426,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x427
     */
    @Test
    public void test0x427() {

        Assert.assertEquals( "afii10041",
                             reader.mapCharCodeToGlyphname( 0x427,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x428
     */
    @Test
    public void test0x428() {

        Assert.assertEquals( "afii10042",
                             reader.mapCharCodeToGlyphname( 0x428,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x429
     */
    @Test
    public void test0x429() {

        Assert.assertEquals( "afii10043",
                             reader.mapCharCodeToGlyphname( 0x429,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42a
     */
    @Test
    public void test0x42a() {

        Assert.assertEquals( "afii10044",
                             reader.mapCharCodeToGlyphname( 0x42a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42b
     */
    @Test
    public void test0x42b() {

        Assert.assertEquals( "afii10045",
                             reader.mapCharCodeToGlyphname( 0x42b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42c
     */
    @Test
    public void test0x42c() {

        Assert.assertEquals( "afii10046",
                             reader.mapCharCodeToGlyphname( 0x42c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42d
     */
    @Test
    public void test0x42d() {

        Assert.assertEquals( "afii10047",
                             reader.mapCharCodeToGlyphname( 0x42d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42e
     */
    @Test
    public void test0x42e() {

        Assert.assertEquals( "afii10048",
                             reader.mapCharCodeToGlyphname( 0x42e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x42f
     */
    @Test
    public void test0x42f() {

        Assert.assertEquals( "afii10049",
                             reader.mapCharCodeToGlyphname( 0x42f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43
     */
    @Test
    public void test0x43() {

        Assert.assertEquals( "C",
                             reader.mapCharCodeToGlyphname( 0x43, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x430
     */
    @Test
    public void test0x430() {

        Assert.assertEquals( "afii10065",
                             reader.mapCharCodeToGlyphname( 0x430,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x431
     */
    @Test
    public void test0x431() {

        Assert.assertEquals( "afii10066",
                             reader.mapCharCodeToGlyphname( 0x431,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x432
     */
    @Test
    public void test0x432() {

        Assert.assertEquals( "afii10067",
                             reader.mapCharCodeToGlyphname( 0x432,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x433
     */
    @Test
    public void test0x433() {

        Assert.assertEquals( "afii10068",
                             reader.mapCharCodeToGlyphname( 0x433,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x434
     */
    @Test
    public void test0x434() {

        Assert.assertEquals( "afii10069",
                             reader.mapCharCodeToGlyphname( 0x434,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x435
     */
    @Test
    public void test0x435() {

        Assert.assertEquals( "afii10070",
                             reader.mapCharCodeToGlyphname( 0x435,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x436
     */
    @Test
    public void test0x436() {

        Assert.assertEquals( "afii10072",
                             reader.mapCharCodeToGlyphname( 0x436,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x437
     */
    @Test
    public void test0x437() {

        Assert.assertEquals( "afii10073",
                             reader.mapCharCodeToGlyphname( 0x437,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x438
     */
    @Test
    public void test0x438() {

        Assert.assertEquals( "afii10074",
                             reader.mapCharCodeToGlyphname( 0x438,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x439
     */
    @Test
    public void test0x439() {

        Assert.assertEquals( "afii10075",
                             reader.mapCharCodeToGlyphname( 0x439,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43a
     */
    @Test
    public void test0x43a() {

        Assert.assertEquals( "afii10076",
                             reader.mapCharCodeToGlyphname( 0x43a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43b
     */
    @Test
    public void test0x43b() {

        Assert.assertEquals( "afii10077",
                             reader.mapCharCodeToGlyphname( 0x43b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43c
     */
    @Test
    public void test0x43c() {

        Assert.assertEquals( "afii10078",
                             reader.mapCharCodeToGlyphname( 0x43c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43d
     */
    @Test
    public void test0x43d() {

        Assert.assertEquals( "afii10079",
                             reader.mapCharCodeToGlyphname( 0x43d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43e
     */
    @Test
    public void test0x43e() {

        Assert.assertEquals( "afii10080",
                             reader.mapCharCodeToGlyphname( 0x43e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x43f
     */
    @Test
    public void test0x43f() {

        Assert.assertEquals( "afii10081",
                             reader.mapCharCodeToGlyphname( 0x43f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44
     */
    @Test
    public void test0x44() {

        Assert.assertEquals( "D",
                             reader.mapCharCodeToGlyphname( 0x44, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x440
     */
    @Test
    public void test0x440() {

        Assert.assertEquals( "afii10082",
                             reader.mapCharCodeToGlyphname( 0x440,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x441
     */
    @Test
    public void test0x441() {

        Assert.assertEquals( "afii10083",
                             reader.mapCharCodeToGlyphname( 0x441,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x442
     */
    @Test
    public void test0x442() {

        Assert.assertEquals( "afii10084",
                             reader.mapCharCodeToGlyphname( 0x442,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x443
     */
    @Test
    public void test0x443() {

        Assert.assertEquals( "afii10085",
                             reader.mapCharCodeToGlyphname( 0x443,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x444
     */
    @Test
    public void test0x444() {

        Assert.assertEquals( "afii10086",
                             reader.mapCharCodeToGlyphname( 0x444,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x445
     */
    @Test
    public void test0x445() {

        Assert.assertEquals( "afii10087",
                             reader.mapCharCodeToGlyphname( 0x445,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x446
     */
    @Test
    public void test0x446() {

        Assert.assertEquals( "afii10088",
                             reader.mapCharCodeToGlyphname( 0x446,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x447
     */
    @Test
    public void test0x447() {

        Assert.assertEquals( "afii10089",
                             reader.mapCharCodeToGlyphname( 0x447,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x448
     */
    @Test
    public void test0x448() {

        Assert.assertEquals( "afii10090",
                             reader.mapCharCodeToGlyphname( 0x448,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x449
     */
    @Test
    public void test0x449() {

        Assert.assertEquals( "afii10091",
                             reader.mapCharCodeToGlyphname( 0x449,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44a
     */
    @Test
    public void test0x44a() {

        Assert.assertEquals( "afii10092",
                             reader.mapCharCodeToGlyphname( 0x44a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44b
     */
    @Test
    public void test0x44b() {

        Assert.assertEquals( "afii10093",
                             reader.mapCharCodeToGlyphname( 0x44b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44c
     */
    @Test
    public void test0x44c() {

        Assert.assertEquals( "afii10094",
                             reader.mapCharCodeToGlyphname( 0x44c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44d
     */
    @Test
    public void test0x44d() {

        Assert.assertEquals( "afii10095",
                             reader.mapCharCodeToGlyphname( 0x44d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44e
     */
    @Test
    public void test0x44e() {

        Assert.assertEquals( "afii10096",
                             reader.mapCharCodeToGlyphname( 0x44e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x44f
     */
    @Test
    public void test0x44f() {

        Assert.assertEquals( "afii10097",
                             reader.mapCharCodeToGlyphname( 0x44f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x45
     */
    @Test
    public void test0x45() {

        Assert.assertEquals( "E",
                             reader.mapCharCodeToGlyphname( 0x45, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x451
     */
    @Test
    public void test0x451() {

        Assert.assertEquals( "afii10071",
                             reader.mapCharCodeToGlyphname( 0x451,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x452
     */
    @Test
    public void test0x452() {

        Assert.assertEquals( "afii10099",
                             reader.mapCharCodeToGlyphname( 0x452,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x453
     */
    @Test
    public void test0x453() {

        Assert.assertEquals( "afii10100",
                             reader.mapCharCodeToGlyphname( 0x453,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x454
     */
    @Test
    public void test0x454() {

        Assert.assertEquals( "afii10101",
                             reader.mapCharCodeToGlyphname( 0x454,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x455
     */
    @Test
    public void test0x455() {

        Assert.assertEquals( "afii10102",
                             reader.mapCharCodeToGlyphname( 0x455,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x456
     */
    @Test
    public void test0x456() {

        Assert.assertEquals( "afii10103",
                             reader.mapCharCodeToGlyphname( 0x456,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x457
     */
    @Test
    public void test0x457() {

        Assert.assertEquals( "afii10104",
                             reader.mapCharCodeToGlyphname( 0x457,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x458
     */
    @Test
    public void test0x458() {

        Assert.assertEquals( "afii10105",
                             reader.mapCharCodeToGlyphname( 0x458,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x459
     */
    @Test
    public void test0x459() {

        Assert.assertEquals( "afii10106",
                             reader.mapCharCodeToGlyphname( 0x459,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x45a
     */
    @Test
    public void test0x45a() {

        Assert.assertEquals( "afii10107",
                             reader.mapCharCodeToGlyphname( 0x45a,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x45b
     */
    @Test
    public void test0x45b() {

        Assert.assertEquals( "afii10108",
                             reader.mapCharCodeToGlyphname( 0x45b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x45c
     */
    @Test
    public void test0x45c() {

        Assert.assertEquals( "afii10109",
                             reader.mapCharCodeToGlyphname( 0x45c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x45e
     */
    @Test
    public void test0x45e() {

        Assert.assertEquals( "afii10110",
                             reader.mapCharCodeToGlyphname( 0x45e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x45f
     */
    @Test
    public void test0x45f() {

        Assert.assertEquals( "afii10193",
                             reader.mapCharCodeToGlyphname( 0x45f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x46
     */
    @Test
    public void test0x46() {

        Assert.assertEquals( "F",
                             reader.mapCharCodeToGlyphname( 0x46, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x47
     */
    @Test
    public void test0x47() {

        Assert.assertEquals( "G",
                             reader.mapCharCodeToGlyphname( 0x47, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x48
     */
    @Test
    public void test0x48() {

        Assert.assertEquals( "H",
                             reader.mapCharCodeToGlyphname( 0x48, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x49
     */
    @Test
    public void test0x49() {

        Assert.assertEquals( "I",
                             reader.mapCharCodeToGlyphname( 0x49, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x490
     */
    @Test
    public void test0x490() {

        Assert.assertEquals( "afii10050",
                             reader.mapCharCodeToGlyphname( 0x490,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x491
     */
    @Test
    public void test0x491() {

        Assert.assertEquals( "afii10098",
                             reader.mapCharCodeToGlyphname( 0x491,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x4a
     */
    @Test
    public void test0x4a() {

        Assert.assertEquals( "J",
                             reader.mapCharCodeToGlyphname( 0x4a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x4b
     */
    @Test
    public void test0x4b() {

        Assert.assertEquals( "K",
                             reader.mapCharCodeToGlyphname( 0x4b, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x4c
     */
    @Test
    public void test0x4c() {

        Assert.assertEquals( "L",
                             reader.mapCharCodeToGlyphname( 0x4c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x4d
     */
    @Test
    public void test0x4d() {

        Assert.assertEquals( "M",
                             reader.mapCharCodeToGlyphname( 0x4d, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x4e
     */
    @Test
    public void test0x4e() {

        Assert.assertEquals( "N",
                             reader.mapCharCodeToGlyphname( 0x4e, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x4f
     */
    @Test
    public void test0x4f() {

        Assert.assertEquals( "O",
                             reader.mapCharCodeToGlyphname( 0x4f, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x50
     */
    @Test
    public void test0x50() {

        Assert.assertEquals( "P",
                             reader.mapCharCodeToGlyphname( 0x50, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x51
     */
    @Test
    public void test0x51() {

        Assert.assertEquals( "Q",
                             reader.mapCharCodeToGlyphname( 0x51, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x52
     */
    @Test
    public void test0x52() {

        Assert.assertEquals( "R",
                             reader.mapCharCodeToGlyphname( 0x52, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x53
     */
    @Test
    public void test0x53() {

        Assert.assertEquals( "S",
                             reader.mapCharCodeToGlyphname( 0x53, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x54
     */
    @Test
    public void test0x54() {

        Assert.assertEquals( "T",
                             reader.mapCharCodeToGlyphname( 0x54, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x55
     */
    @Test
    public void test0x55() {

        Assert.assertEquals( "U",
                             reader.mapCharCodeToGlyphname( 0x55, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x56
     */
    @Test
    public void test0x56() {

        Assert.assertEquals( "V",
                             reader.mapCharCodeToGlyphname( 0x56, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x57
     */
    @Test
    public void test0x57() {

        Assert.assertEquals( "W",
                             reader.mapCharCodeToGlyphname( 0x57, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x58
     */
    @Test
    public void test0x58() {

        Assert.assertEquals( "X",
                             reader.mapCharCodeToGlyphname( 0x58, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x59
     */
    @Test
    public void test0x59() {

        Assert.assertEquals( "Y",
                             reader.mapCharCodeToGlyphname( 0x59, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x5a
     */
    @Test
    public void test0x5a() {

        Assert.assertEquals( "Z",
                             reader.mapCharCodeToGlyphname( 0x5a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x5b
     */
    @Test
    public void test0x5b() {

        Assert.assertEquals( "bracketleft",
                             reader.mapCharCodeToGlyphname( 0x5b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x5c
     */
    @Test
    public void test0x5c() {

        Assert.assertEquals( "backslash",
                             reader.mapCharCodeToGlyphname( 0x5c,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x5d
     */
    @Test
    public void test0x5d() {

        Assert.assertEquals( "bracketright",
                             reader.mapCharCodeToGlyphname( 0x5d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x5e
     */
    @Test
    public void test0x5e() {

        Assert.assertEquals( "asciicircum",
                             reader.mapCharCodeToGlyphname( 0x5e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x5f
     */
    @Test
    public void test0x5f() {

        Assert.assertEquals( "underscore",
                             reader.mapCharCodeToGlyphname( 0x5f,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x60
     */
    @Test
    public void test0x60() {

        Assert.assertEquals( "grave",
                             reader.mapCharCodeToGlyphname( 0x60, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x61
     */
    @Test
    public void test0x61() {

        Assert.assertEquals( "a",
                             reader.mapCharCodeToGlyphname( 0x61, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x62
     */
    @Test
    public void test0x62() {

        Assert.assertEquals( "b",
                             reader.mapCharCodeToGlyphname( 0x62, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x63
     */
    @Test
    public void test0x63() {

        Assert.assertEquals( "c",
                             reader.mapCharCodeToGlyphname( 0x63, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x64
     */
    @Test
    public void test0x64() {

        Assert.assertEquals( "d",
                             reader.mapCharCodeToGlyphname( 0x64, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x65
     */
    @Test
    public void test0x65() {

        Assert.assertEquals( "e",
                             reader.mapCharCodeToGlyphname( 0x65, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x66
     */
    @Test
    public void test0x66() {

        Assert.assertEquals( "f",
                             reader.mapCharCodeToGlyphname( 0x66, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x67
     */
    @Test
    public void test0x67() {

        Assert.assertEquals( "g",
                             reader.mapCharCodeToGlyphname( 0x67, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x68
     */
    @Test
    public void test0x68() {

        Assert.assertEquals( "h",
                             reader.mapCharCodeToGlyphname( 0x68, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x69
     */
    @Test
    public void test0x69() {

        Assert.assertEquals( "i",
                             reader.mapCharCodeToGlyphname( 0x69, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x6a
     */
    @Test
    public void test0x6a() {

        Assert.assertEquals( "j",
                             reader.mapCharCodeToGlyphname( 0x6a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x6b
     */
    @Test
    public void test0x6b() {

        Assert.assertEquals( "k",
                             reader.mapCharCodeToGlyphname( 0x6b, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x6c
     */
    @Test
    public void test0x6c() {

        Assert.assertEquals( "l",
                             reader.mapCharCodeToGlyphname( 0x6c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x6d
     */
    @Test
    public void test0x6d() {

        Assert.assertEquals( "m",
                             reader.mapCharCodeToGlyphname( 0x6d, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x6e
     */
    @Test
    public void test0x6e() {

        Assert.assertEquals( "n",
                             reader.mapCharCodeToGlyphname( 0x6e, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x6f
     */
    @Test
    public void test0x6f() {

        Assert.assertEquals( "o",
                             reader.mapCharCodeToGlyphname( 0x6f, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x70
     */
    @Test
    public void test0x70() {

        Assert.assertEquals( "p",
                             reader.mapCharCodeToGlyphname( 0x70, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x71
     */
    @Test
    public void test0x71() {

        Assert.assertEquals( "q",
                             reader.mapCharCodeToGlyphname( 0x71, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x72
     */
    @Test
    public void test0x72() {

        Assert.assertEquals( "r",
                             reader.mapCharCodeToGlyphname( 0x72, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x73
     */
    @Test
    public void test0x73() {

        Assert.assertEquals( "s",
                             reader.mapCharCodeToGlyphname( 0x73, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x74
     */
    @Test
    public void test0x74() {

        Assert.assertEquals( "t",
                             reader.mapCharCodeToGlyphname( 0x74, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x75
     */
    @Test
    public void test0x75() {

        Assert.assertEquals( "u",
                             reader.mapCharCodeToGlyphname( 0x75, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x76
     */
    @Test
    public void test0x76() {

        Assert.assertEquals( "v",
                             reader.mapCharCodeToGlyphname( 0x76, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x77
     */
    @Test
    public void test0x77() {

        Assert.assertEquals( "w",
                             reader.mapCharCodeToGlyphname( 0x77, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x78
     */
    @Test
    public void test0x78() {

        Assert.assertEquals( "x",
                             reader.mapCharCodeToGlyphname( 0x78, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x79
     */
    @Test
    public void test0x79() {

        Assert.assertEquals( "y",
                             reader.mapCharCodeToGlyphname( 0x79, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x7a
     */
    @Test
    public void test0x7a() {

        Assert.assertEquals( "z",
                             reader.mapCharCodeToGlyphname( 0x7a, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x7b
     */
    @Test
    public void test0x7b() {

        Assert.assertEquals( "braceleft",
                             reader.mapCharCodeToGlyphname( 0x7b,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x7c
     */
    @Test
    public void test0x7c() {

        Assert.assertEquals( "bar",
                             reader.mapCharCodeToGlyphname( 0x7c, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x7d
     */
    @Test
    public void test0x7d() {

        Assert.assertEquals( "braceright",
                             reader.mapCharCodeToGlyphname( 0x7d,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0x7e
     */
    @Test
    public void test0x7e() {

        Assert.assertEquals( "asciitilde",
                             reader.mapCharCodeToGlyphname( 0x7e,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xa0
     */
    @Test
    public void test0xa0() {

        Assert.assertEquals( "nbspace", reader.mapCharCodeToGlyphname( 0xa0,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0xa1
     */
    @Test
    public void test0xa1() {

        Assert.assertEquals( "exclamdown",
                             reader.mapCharCodeToGlyphname( 0xa1,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xa2
     */
    @Test
    public void test0xa2() {

        Assert.assertEquals( "cent",
                             reader.mapCharCodeToGlyphname( 0xa2, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xa3
     */
    @Test
    public void test0xa3() {

        Assert.assertEquals( "sterling", reader.mapCharCodeToGlyphname( 0xa3,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0xa4
     */
    @Test
    public void test0xa4() {

        Assert.assertEquals( "currency", reader.mapCharCodeToGlyphname( 0xa4,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0xa5
     */
    @Test
    public void test0xa5() {

        Assert.assertEquals( "yen",
                             reader.mapCharCodeToGlyphname( 0xa5, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xa6
     */
    @Test
    public void test0xa6() {

        Assert.assertEquals( "brokenbar",
                             reader.mapCharCodeToGlyphname( 0xa6,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xa7
     */
    @Test
    public void test0xa7() {

        Assert.assertEquals( "section", reader.mapCharCodeToGlyphname( 0xa7,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0xa8
     */
    @Test
    public void test0xa8() {

        Assert.assertEquals( "dieresis", reader.mapCharCodeToGlyphname( 0xa8,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0xa9
     */
    @Test
    public void test0xa9() {

        Assert.assertEquals( "copyright",
                             reader.mapCharCodeToGlyphname( 0xa9,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xaa
     */
    @Test
    public void test0xaa() {

        Assert.assertEquals( "ordfeminine",
                             reader.mapCharCodeToGlyphname( 0xaa,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xab
     */
    @Test
    public void test0xab() {

        Assert.assertEquals( "guillemotleft",
                             reader.mapCharCodeToGlyphname( 0xab,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xac
     */
    @Test
    public void test0xac() {

        Assert.assertEquals( "logicalnot",
                             reader.mapCharCodeToGlyphname( 0xac,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xad
     */
    @Test
    public void test0xad() {

        Assert.assertEquals( "sfthyphen",
                             reader.mapCharCodeToGlyphname( 0xad,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xae
     */
    @Test
    public void test0xae() {

        Assert.assertEquals( "registered",
                             reader.mapCharCodeToGlyphname( 0xae,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xaf
     */
    @Test
    public void test0xaf() {

        Assert.assertEquals( "overscore",
                             reader.mapCharCodeToGlyphname( 0xaf,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb0
     */
    @Test
    public void test0xb0() {

        Assert.assertEquals( "degree", reader.mapCharCodeToGlyphname( 0xb0,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xb1
     */
    @Test
    public void test0xb1() {

        Assert.assertEquals( "plusminus",
                             reader.mapCharCodeToGlyphname( 0xb1,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb2
     */
    @Test
    public void test0xb2() {

        Assert.assertEquals( "twosuperior",
                             reader.mapCharCodeToGlyphname( 0xb2,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb3
     */
    @Test
    public void test0xb3() {

        Assert.assertEquals( "threesuperior",
                             reader.mapCharCodeToGlyphname( 0xb3,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb4
     */
    @Test
    public void test0xb4() {

        Assert.assertEquals( "acute",
                             reader.mapCharCodeToGlyphname( 0xb4, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb5
     */
    @Test
    public void test0xb5() {

        Assert.assertEquals( "mu1",
                             reader.mapCharCodeToGlyphname( 0xb5, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb6
     */
    @Test
    public void test0xb6() {

        Assert.assertEquals( "paragraph",
                             reader.mapCharCodeToGlyphname( 0xb6,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xb7
     */
    @Test
    public void test0xb7() {

        Assert.assertEquals( "middot", reader.mapCharCodeToGlyphname( 0xb7,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xb8
     */
    @Test
    public void test0xb8() {

        Assert.assertEquals( "cedilla", reader.mapCharCodeToGlyphname( 0xb8,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0xb9
     */
    @Test
    public void test0xb9() {

        Assert.assertEquals( "onesuperior",
                             reader.mapCharCodeToGlyphname( 0xb9,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xba
     */
    @Test
    public void test0xba() {

        Assert.assertEquals( "ordmasculine",
                             reader.mapCharCodeToGlyphname( 0xba,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xbb
     */
    @Test
    public void test0xbb() {

        Assert.assertEquals( "guillemotright",
                             reader.mapCharCodeToGlyphname( 0xbb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xbc
     */
    @Test
    public void test0xbc() {

        Assert.assertEquals( "onequarter",
                             reader.mapCharCodeToGlyphname( 0xbc,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xbd
     */
    @Test
    public void test0xbd() {

        Assert.assertEquals( "onehalf", reader.mapCharCodeToGlyphname( 0xbd,
                                                                       0,
                                                                       (short) 3,
                                                                       (short) 1 ) );
    }

    /**
     * test 0xbe
     */
    @Test
    public void test0xbe() {

        Assert.assertEquals( "threequarters",
                             reader.mapCharCodeToGlyphname( 0xbe,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xbf
     */
    @Test
    public void test0xbf() {

        Assert.assertEquals( "questiondown",
                             reader.mapCharCodeToGlyphname( 0xbf,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xc0
     */
    @Test
    public void test0xc0() {

        Assert.assertEquals( "Agrave", reader.mapCharCodeToGlyphname( 0xc0,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xc1
     */
    @Test
    public void test0xc1() {

        Assert.assertEquals( "Aacute", reader.mapCharCodeToGlyphname( 0xc1,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xc2
     */
    @Test
    public void test0xc2() {

        Assert.assertEquals( "Acircumflex",
                             reader.mapCharCodeToGlyphname( 0xc2,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xc3
     */
    @Test
    public void test0xc3() {

        Assert.assertEquals( "Atilde", reader.mapCharCodeToGlyphname( 0xc3,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xc4
     */
    @Test
    public void test0xc4() {

        Assert.assertEquals( "Adieresis",
                             reader.mapCharCodeToGlyphname( 0xc4,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xc5
     */
    @Test
    public void test0xc5() {

        Assert.assertEquals( "Aring",
                             reader.mapCharCodeToGlyphname( 0xc5, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xc6
     */
    @Test
    public void test0xc6() {

        Assert.assertEquals( "AE",
                             reader.mapCharCodeToGlyphname( 0xc6, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xc7
     */
    @Test
    public void test0xc7() {

        Assert.assertEquals( "Ccedilla", reader.mapCharCodeToGlyphname( 0xc7,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0xc8
     */
    @Test
    public void test0xc8() {

        Assert.assertEquals( "Egrave", reader.mapCharCodeToGlyphname( 0xc8,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xc9
     */
    @Test
    public void test0xc9() {

        Assert.assertEquals( "Eacute", reader.mapCharCodeToGlyphname( 0xc9,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xca
     */
    @Test
    public void test0xca() {

        Assert.assertEquals( "Ecircumflex",
                             reader.mapCharCodeToGlyphname( 0xca,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xcb
     */
    @Test
    public void test0xcb() {

        Assert.assertEquals( "Edieresis",
                             reader.mapCharCodeToGlyphname( 0xcb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xcc
     */
    @Test
    public void test0xcc() {

        Assert.assertEquals( "Igrave", reader.mapCharCodeToGlyphname( 0xcc,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xcd
     */
    @Test
    public void test0xcd() {

        Assert.assertEquals( "Iacute", reader.mapCharCodeToGlyphname( 0xcd,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xce
     */
    @Test
    public void test0xce() {

        Assert.assertEquals( "Icircumflex",
                             reader.mapCharCodeToGlyphname( 0xce,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xcf
     */
    @Test
    public void test0xcf() {

        Assert.assertEquals( "Idieresis",
                             reader.mapCharCodeToGlyphname( 0xcf,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xd0
     */
    @Test
    public void test0xd0() {

        Assert.assertEquals( "Eth",
                             reader.mapCharCodeToGlyphname( 0xd0, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xd1
     */
    @Test
    public void test0xd1() {

        Assert.assertEquals( "Ntilde", reader.mapCharCodeToGlyphname( 0xd1,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xd2
     */
    @Test
    public void test0xd2() {

        Assert.assertEquals( "Ograve", reader.mapCharCodeToGlyphname( 0xd2,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xd3
     */
    @Test
    public void test0xd3() {

        Assert.assertEquals( "Oacute", reader.mapCharCodeToGlyphname( 0xd3,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xd4
     */
    @Test
    public void test0xd4() {

        Assert.assertEquals( "Ocircumflex",
                             reader.mapCharCodeToGlyphname( 0xd4,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xd5
     */
    @Test
    public void test0xd5() {

        Assert.assertEquals( "Otilde", reader.mapCharCodeToGlyphname( 0xd5,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xd6
     */
    @Test
    public void test0xd6() {

        Assert.assertEquals( "Odieresis",
                             reader.mapCharCodeToGlyphname( 0xd6,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xd7
     */
    @Test
    public void test0xd7() {

        Assert.assertEquals( "multiply", reader.mapCharCodeToGlyphname( 0xd7,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0xd8
     */
    @Test
    public void test0xd8() {

        Assert.assertEquals( "Oslash", reader.mapCharCodeToGlyphname( 0xd8,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xd9
     */
    @Test
    public void test0xd9() {

        Assert.assertEquals( "Ugrave", reader.mapCharCodeToGlyphname( 0xd9,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xda
     */
    @Test
    public void test0xda() {

        Assert.assertEquals( "Uacute", reader.mapCharCodeToGlyphname( 0xda,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xdb
     */
    @Test
    public void test0xdb() {

        Assert.assertEquals( "Ucircumflex",
                             reader.mapCharCodeToGlyphname( 0xdb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xdc
     */
    @Test
    public void test0xdc() {

        Assert.assertEquals( "Udieresis",
                             reader.mapCharCodeToGlyphname( 0xdc,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xdd
     */
    @Test
    public void test0xdd() {

        Assert.assertEquals( "Yacute", reader.mapCharCodeToGlyphname( 0xdd,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xde
     */
    @Test
    public void test0xde() {

        Assert.assertEquals( "Thorn",
                             reader.mapCharCodeToGlyphname( 0xde, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xdf
     */
    @Test
    public void test0xdf() {

        Assert.assertEquals( "germandbls",
                             reader.mapCharCodeToGlyphname( 0xdf,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xe0
     */
    @Test
    public void test0xe0() {

        Assert.assertEquals( "agrave", reader.mapCharCodeToGlyphname( 0xe0,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xe1
     */
    @Test
    public void test0xe1() {

        Assert.assertEquals( "aacute", reader.mapCharCodeToGlyphname( 0xe1,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xe2
     */
    @Test
    public void test0xe2() {

        Assert.assertEquals( "acircumflex",
                             reader.mapCharCodeToGlyphname( 0xe2,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xe3
     */
    @Test
    public void test0xe3() {

        Assert.assertEquals( "atilde", reader.mapCharCodeToGlyphname( 0xe3,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xe4
     */
    @Test
    public void test0xe4() {

        Assert.assertEquals( "adieresis",
                             reader.mapCharCodeToGlyphname( 0xe4,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xe5
     */
    @Test
    public void test0xe5() {

        Assert.assertEquals( "aring",
                             reader.mapCharCodeToGlyphname( 0xe5, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xe6
     */
    @Test
    public void test0xe6() {

        Assert.assertEquals( "ae",
                             reader.mapCharCodeToGlyphname( 0xe6, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xe7
     */
    @Test
    public void test0xe7() {

        Assert.assertEquals( "ccedilla", reader.mapCharCodeToGlyphname( 0xe7,
                                                                        0,
                                                                        (short) 3,
                                                                        (short) 1 ) );
    }

    /**
     * test 0xe8
     */
    @Test
    public void test0xe8() {

        Assert.assertEquals( "egrave", reader.mapCharCodeToGlyphname( 0xe8,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xe9
     */
    @Test
    public void test0xe9() {

        Assert.assertEquals( "eacute", reader.mapCharCodeToGlyphname( 0xe9,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xea
     */
    @Test
    public void test0xea() {

        Assert.assertEquals( "ecircumflex",
                             reader.mapCharCodeToGlyphname( 0xea,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xeb
     */
    @Test
    public void test0xeb() {

        Assert.assertEquals( "edieresis",
                             reader.mapCharCodeToGlyphname( 0xeb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xec
     */
    @Test
    public void test0xec() {

        Assert.assertEquals( "igrave", reader.mapCharCodeToGlyphname( 0xec,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xed
     */
    @Test
    public void test0xed() {

        Assert.assertEquals( "iacute", reader.mapCharCodeToGlyphname( 0xed,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xee
     */
    @Test
    public void test0xee() {

        Assert.assertEquals( "icircumflex",
                             reader.mapCharCodeToGlyphname( 0xee,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xef
     */
    @Test
    public void test0xef() {

        Assert.assertEquals( "idieresis",
                             reader.mapCharCodeToGlyphname( 0xef,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf0
     */
    @Test
    public void test0xf0() {

        Assert.assertEquals( "eth",
                             reader.mapCharCodeToGlyphname( 0xf0, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf001
     */
    @Test
    public void test0xf001() {

        Assert.assertEquals( "fi1",
                             reader.mapCharCodeToGlyphname( 0xf001,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf002
     */
    @Test
    public void test0xf002() {

        Assert.assertEquals( "fl1",
                             reader.mapCharCodeToGlyphname( 0xf002,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf003
     */
    @Test
    public void test0xf003() {

        Assert.assertEquals( "foursuperior",
                             reader.mapCharCodeToGlyphname( 0xf003,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf004
     */
    @Test
    public void test0xf004() {

        Assert.assertEquals( "commaaccent",
                             reader.mapCharCodeToGlyphname( 0xf004,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf005
     */
    @Test
    public void test0xf005() {

        Assert.assertEquals( "undercommaaccent",
                             reader.mapCharCodeToGlyphname( 0xf005,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf006
     */
    @Test
    public void test0xf006() {

        Assert.assertEquals( "pi",
                             reader.mapCharCodeToGlyphname( 0xf006,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf007
     */
    @Test
    public void test0xf007() {

        Assert.assertEquals( "fivesuperior",
                             reader.mapCharCodeToGlyphname( 0xf007,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf008
     */
    @Test
    public void test0xf008() {

        Assert.assertEquals( "sevensuperior",
                             reader.mapCharCodeToGlyphname( 0xf008,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf009
     */
    @Test
    public void test0xf009() {

        Assert.assertEquals( "eightsuperior",
                             reader.mapCharCodeToGlyphname( 0xf009,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf1
     */
    @Test
    public void test0xf1() {

        Assert.assertEquals( "ntilde", reader.mapCharCodeToGlyphname( 0xf1,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xf2
     */
    @Test
    public void test0xf2() {

        Assert.assertEquals( "ograve", reader.mapCharCodeToGlyphname( 0xf2,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xf3
     */
    @Test
    public void test0xf3() {

        Assert.assertEquals( "oacute", reader.mapCharCodeToGlyphname( 0xf3,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xf4
     */
    @Test
    public void test0xf4() {

        Assert.assertEquals( "ocircumflex",
                             reader.mapCharCodeToGlyphname( 0xf4,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf5
     */
    @Test
    public void test0xf5() {

        Assert.assertEquals( "otilde", reader.mapCharCodeToGlyphname( 0xf5,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xf6
     */
    @Test
    public void test0xf6() {

        Assert.assertEquals( "odieresis",
                             reader.mapCharCodeToGlyphname( 0xf6,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xf7
     */
    @Test
    public void test0xf7() {

        Assert.assertEquals( "divide", reader.mapCharCodeToGlyphname( 0xf7,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xf8
     */
    @Test
    public void test0xf8() {

        Assert.assertEquals( "oslash", reader.mapCharCodeToGlyphname( 0xf8,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xf9
     */
    @Test
    public void test0xf9() {

        Assert.assertEquals( "ugrave", reader.mapCharCodeToGlyphname( 0xf9,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xfa
     */
    @Test
    public void test0xfa() {

        Assert.assertEquals( "uacute", reader.mapCharCodeToGlyphname( 0xfa,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xfb
     */
    @Test
    public void test0xfb() {

        Assert.assertEquals( "ucircumflex",
                             reader.mapCharCodeToGlyphname( 0xfb,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xfb01
     */
    @Test
    public void test0xfb01() {

        Assert.assertEquals( "fi",
                             reader.mapCharCodeToGlyphname( 0xfb01,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xfb02
     */
    @Test
    public void test0xfb02() {

        Assert.assertEquals( "fl",
                             reader.mapCharCodeToGlyphname( 0xfb02,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xfc
     */
    @Test
    public void test0xfc() {

        Assert.assertEquals( "udieresis",
                             reader.mapCharCodeToGlyphname( 0xfc,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xfd
     */
    @Test
    public void test0xfd() {

        Assert.assertEquals( "yacute", reader.mapCharCodeToGlyphname( 0xfd,
                                                                      0,
                                                                      (short) 3,
                                                                      (short) 1 ) );
    }

    /**
     * test 0xfe
     */
    @Test
    public void test0xfe() {

        Assert.assertEquals( "thorn",
                             reader.mapCharCodeToGlyphname( 0xfe, 0, (short) 3,
                                                            (short) 1 ) );
    }

    /**
     * test 0xff
     */
    @Test
    public void test0xff() {

        Assert.assertEquals( "ydieresis",
                             reader.mapCharCodeToGlyphname( 0xff,
                                                            0,
                                                            (short) 3,
                                                            (short) 1 ) );
    }

}
