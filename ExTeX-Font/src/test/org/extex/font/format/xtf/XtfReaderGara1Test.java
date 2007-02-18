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

import org.extex.font.format.xtf.TtfTableCMAP.Format;

/**
 * Tests for the <code>XtfReader</code>.
 *
 * The test use the data from the <code>ttx</code> output. 
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReaderGara1Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     *
     * @throws IOException if an error occurred.
     */
    public XtfReaderGara1Test() throws IOException {

        if (reader == null) {
            reader = new XtfReader("../ExTeX-font/src/font/Gara.ttf");
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
     * test 03.
     *
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        TtfTableCMAP cmap = reader.getCmapTable();
        assertEquals(2, cmap.getNumTables());
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

        assertEquals("space", reader.mapCharCodeToGlyphname(0x20, (short) 3,
                (short) 1));
    }

    // --------------------------------------------------------------

    /**
     * test 0x20
     *
     * @throws Exception if an error occurred.
     */
    public void test0x20() throws Exception {

        assertEquals("space", reader.mapCharCodeToGlyphname(0x20, (short) 3,
                (short) 1));
    }

    /**
     * test 0x21
     *
     * @throws Exception if an error occurred.
     */
    public void test0x21() throws Exception {

        assertEquals("exclam", reader.mapCharCodeToGlyphname(0x21, (short) 3,
                (short) 1));
    }

    /**
     * test 0x22
     *
     * @throws Exception if an error occurred.
     */
    public void test0x22() throws Exception {

        assertEquals("quotedbl", reader.mapCharCodeToGlyphname(0x22, (short) 3,
                (short) 1));
    }

    /**
     * test 0x23
     *
     * @throws Exception if an error occurred.
     */
    public void test0x23() throws Exception {

        assertEquals("numbersign", reader.mapCharCodeToGlyphname(0x23,
                (short) 3, (short) 1));
    }

    /**
     * test 0x24
     *
     * @throws Exception if an error occurred.
     */
    public void test0x24() throws Exception {

        assertEquals("dollar", reader.mapCharCodeToGlyphname(0x24, (short) 3,
                (short) 1));
    }

    /**
     * test 0x25
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25() throws Exception {

        assertEquals("percent", reader.mapCharCodeToGlyphname(0x25, (short) 3,
                (short) 1));
    }

    /**
     * test 0x26
     *
     * @throws Exception if an error occurred.
     */
    public void test0x26() throws Exception {

        assertEquals("ampersand", reader.mapCharCodeToGlyphname(0x26,
                (short) 3, (short) 1));
    }

    /**
     * test 0x27
     *
     * @throws Exception if an error occurred.
     */
    public void test0x27() throws Exception {

        assertEquals("quotesingle", reader.mapCharCodeToGlyphname(0x27,
                (short) 3, (short) 1));
    }

    /**
     * test 0x28
     *
     * @throws Exception if an error occurred.
     */
    public void test0x28() throws Exception {

        assertEquals("parenleft", reader.mapCharCodeToGlyphname(0x28,
                (short) 3, (short) 1));
    }

    /**
     * test 0x29
     *
     * @throws Exception if an error occurred.
     */
    public void test0x29() throws Exception {

        assertEquals("parenright", reader.mapCharCodeToGlyphname(0x29,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2a() throws Exception {

        assertEquals("asterisk", reader.mapCharCodeToGlyphname(0x2a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2b() throws Exception {

        assertEquals("plus", reader.mapCharCodeToGlyphname(0x2b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2c() throws Exception {

        assertEquals("comma", reader.mapCharCodeToGlyphname(0x2c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2d() throws Exception {

        assertEquals("hyphen", reader.mapCharCodeToGlyphname(0x2d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2e() throws Exception {

        assertEquals("period", reader.mapCharCodeToGlyphname(0x2e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2f() throws Exception {

        assertEquals("slash", reader.mapCharCodeToGlyphname(0x2f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x30
     *
     * @throws Exception if an error occurred.
     */
    public void test0x30() throws Exception {

        assertEquals("zero", reader.mapCharCodeToGlyphname(0x30, (short) 3,
                (short) 1));
    }

    /**
     * test 0x31
     *
     * @throws Exception if an error occurred.
     */
    public void test0x31() throws Exception {

        assertEquals("one", reader.mapCharCodeToGlyphname(0x31, (short) 3,
                (short) 1));
    }

    /**
     * test 0x32
     *
     * @throws Exception if an error occurred.
     */
    public void test0x32() throws Exception {

        assertEquals("two", reader.mapCharCodeToGlyphname(0x32, (short) 3,
                (short) 1));
    }

    /**
     * test 0x33
     *
     * @throws Exception if an error occurred.
     */
    public void test0x33() throws Exception {

        assertEquals("three", reader.mapCharCodeToGlyphname(0x33, (short) 3,
                (short) 1));
    }

    /**
     * test 0x34
     *
     * @throws Exception if an error occurred.
     */
    public void test0x34() throws Exception {

        assertEquals("four", reader.mapCharCodeToGlyphname(0x34, (short) 3,
                (short) 1));
    }

    /**
     * test 0x35
     *
     * @throws Exception if an error occurred.
     */
    public void test0x35() throws Exception {

        assertEquals("five", reader.mapCharCodeToGlyphname(0x35, (short) 3,
                (short) 1));
    }

    /**
     * test 0x36
     *
     * @throws Exception if an error occurred.
     */
    public void test0x36() throws Exception {

        assertEquals("six", reader.mapCharCodeToGlyphname(0x36, (short) 3,
                (short) 1));
    }

    /**
     * test 0x37
     *
     * @throws Exception if an error occurred.
     */
    public void test0x37() throws Exception {

        assertEquals("seven", reader.mapCharCodeToGlyphname(0x37, (short) 3,
                (short) 1));
    }

    /**
     * test 0x38
     *
     * @throws Exception if an error occurred.
     */
    public void test0x38() throws Exception {

        assertEquals("eight", reader.mapCharCodeToGlyphname(0x38, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39() throws Exception {

        assertEquals("nine", reader.mapCharCodeToGlyphname(0x39, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a() throws Exception {

        assertEquals("colon", reader.mapCharCodeToGlyphname(0x3a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b() throws Exception {

        assertEquals("semicolon", reader.mapCharCodeToGlyphname(0x3b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c() throws Exception {

        assertEquals("less", reader.mapCharCodeToGlyphname(0x3c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3d() throws Exception {

        assertEquals("equal", reader.mapCharCodeToGlyphname(0x3d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3e() throws Exception {

        assertEquals("greater", reader.mapCharCodeToGlyphname(0x3e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3f() throws Exception {

        assertEquals("question", reader.mapCharCodeToGlyphname(0x3f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x40
     *
     * @throws Exception if an error occurred.
     */
    public void test0x40() throws Exception {

        assertEquals("at", reader.mapCharCodeToGlyphname(0x40, (short) 3,
                (short) 1));
    }

    /**
     * test 0x41
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41() throws Exception {

        assertEquals("A", reader.mapCharCodeToGlyphname(0x41, (short) 3,
                (short) 1));
    }

    /**
     * test 0x42
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42() throws Exception {

        assertEquals("B", reader.mapCharCodeToGlyphname(0x42, (short) 3,
                (short) 1));
    }

    /**
     * test 0x43
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43() throws Exception {

        assertEquals("C", reader.mapCharCodeToGlyphname(0x43, (short) 3,
                (short) 1));
    }

    /**
     * test 0x44
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44() throws Exception {

        assertEquals("D", reader.mapCharCodeToGlyphname(0x44, (short) 3,
                (short) 1));
    }

    /**
     * test 0x45
     *
     * @throws Exception if an error occurred.
     */
    public void test0x45() throws Exception {

        assertEquals("E", reader.mapCharCodeToGlyphname(0x45, (short) 3,
                (short) 1));
    }

    /**
     * test 0x46
     *
     * @throws Exception if an error occurred.
     */
    public void test0x46() throws Exception {

        assertEquals("F", reader.mapCharCodeToGlyphname(0x46, (short) 3,
                (short) 1));
    }

    /**
     * test 0x47
     *
     * @throws Exception if an error occurred.
     */
    public void test0x47() throws Exception {

        assertEquals("G", reader.mapCharCodeToGlyphname(0x47, (short) 3,
                (short) 1));
    }

    /**
     * test 0x48
     *
     * @throws Exception if an error occurred.
     */
    public void test0x48() throws Exception {

        assertEquals("H", reader.mapCharCodeToGlyphname(0x48, (short) 3,
                (short) 1));
    }

    /**
     * test 0x49
     *
     * @throws Exception if an error occurred.
     */
    public void test0x49() throws Exception {

        assertEquals("I", reader.mapCharCodeToGlyphname(0x49, (short) 3,
                (short) 1));
    }

    /**
     * test 0x4a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x4a() throws Exception {

        assertEquals("J", reader.mapCharCodeToGlyphname(0x4a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x4b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x4b() throws Exception {

        assertEquals("K", reader.mapCharCodeToGlyphname(0x4b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x4c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x4c() throws Exception {

        assertEquals("L", reader.mapCharCodeToGlyphname(0x4c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x4d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x4d() throws Exception {

        assertEquals("M", reader.mapCharCodeToGlyphname(0x4d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x4e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x4e() throws Exception {

        assertEquals("N", reader.mapCharCodeToGlyphname(0x4e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x4f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x4f() throws Exception {

        assertEquals("O", reader.mapCharCodeToGlyphname(0x4f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x50
     *
     * @throws Exception if an error occurred.
     */
    public void test0x50() throws Exception {

        assertEquals("P", reader.mapCharCodeToGlyphname(0x50, (short) 3,
                (short) 1));
    }

    /**
     * test 0x51
     *
     * @throws Exception if an error occurred.
     */
    public void test0x51() throws Exception {

        assertEquals("Q", reader.mapCharCodeToGlyphname(0x51, (short) 3,
                (short) 1));
    }

    /**
     * test 0x52
     *
     * @throws Exception if an error occurred.
     */
    public void test0x52() throws Exception {

        assertEquals("R", reader.mapCharCodeToGlyphname(0x52, (short) 3,
                (short) 1));
    }

    /**
     * test 0x53
     *
     * @throws Exception if an error occurred.
     */
    public void test0x53() throws Exception {

        assertEquals("S", reader.mapCharCodeToGlyphname(0x53, (short) 3,
                (short) 1));
    }

    /**
     * test 0x54
     *
     * @throws Exception if an error occurred.
     */
    public void test0x54() throws Exception {

        assertEquals("T", reader.mapCharCodeToGlyphname(0x54, (short) 3,
                (short) 1));
    }

    /**
     * test 0x55
     *
     * @throws Exception if an error occurred.
     */
    public void test0x55() throws Exception {

        assertEquals("U", reader.mapCharCodeToGlyphname(0x55, (short) 3,
                (short) 1));
    }

    /**
     * test 0x56
     *
     * @throws Exception if an error occurred.
     */
    public void test0x56() throws Exception {

        assertEquals("V", reader.mapCharCodeToGlyphname(0x56, (short) 3,
                (short) 1));
    }

    /**
     * test 0x57
     *
     * @throws Exception if an error occurred.
     */
    public void test0x57() throws Exception {

        assertEquals("W", reader.mapCharCodeToGlyphname(0x57, (short) 3,
                (short) 1));
    }

    /**
     * test 0x58
     *
     * @throws Exception if an error occurred.
     */
    public void test0x58() throws Exception {

        assertEquals("X", reader.mapCharCodeToGlyphname(0x58, (short) 3,
                (short) 1));
    }

    /**
     * test 0x59
     *
     * @throws Exception if an error occurred.
     */
    public void test0x59() throws Exception {

        assertEquals("Y", reader.mapCharCodeToGlyphname(0x59, (short) 3,
                (short) 1));
    }

    /**
     * test 0x5a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x5a() throws Exception {

        assertEquals("Z", reader.mapCharCodeToGlyphname(0x5a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x5b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x5b() throws Exception {

        assertEquals("bracketleft", reader.mapCharCodeToGlyphname(0x5b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x5c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x5c() throws Exception {

        assertEquals("backslash", reader.mapCharCodeToGlyphname(0x5c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x5d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x5d() throws Exception {

        assertEquals("bracketright", reader.mapCharCodeToGlyphname(0x5d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x5e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x5e() throws Exception {

        assertEquals("asciicircum", reader.mapCharCodeToGlyphname(0x5e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x5f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x5f() throws Exception {

        assertEquals("underscore", reader.mapCharCodeToGlyphname(0x5f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x60
     *
     * @throws Exception if an error occurred.
     */
    public void test0x60() throws Exception {

        assertEquals("grave", reader.mapCharCodeToGlyphname(0x60, (short) 3,
                (short) 1));
    }

    /**
     * test 0x61
     *
     * @throws Exception if an error occurred.
     */
    public void test0x61() throws Exception {

        assertEquals("a", reader.mapCharCodeToGlyphname(0x61, (short) 3,
                (short) 1));
    }

    /**
     * test 0x62
     *
     * @throws Exception if an error occurred.
     */
    public void test0x62() throws Exception {

        assertEquals("b", reader.mapCharCodeToGlyphname(0x62, (short) 3,
                (short) 1));
    }

    /**
     * test 0x63
     *
     * @throws Exception if an error occurred.
     */
    public void test0x63() throws Exception {

        assertEquals("c", reader.mapCharCodeToGlyphname(0x63, (short) 3,
                (short) 1));
    }

    /**
     * test 0x64
     *
     * @throws Exception if an error occurred.
     */
    public void test0x64() throws Exception {

        assertEquals("d", reader.mapCharCodeToGlyphname(0x64, (short) 3,
                (short) 1));
    }

    /**
     * test 0x65
     *
     * @throws Exception if an error occurred.
     */
    public void test0x65() throws Exception {

        assertEquals("e", reader.mapCharCodeToGlyphname(0x65, (short) 3,
                (short) 1));
    }

    /**
     * test 0x66
     *
     * @throws Exception if an error occurred.
     */
    public void test0x66() throws Exception {

        assertEquals("f", reader.mapCharCodeToGlyphname(0x66, (short) 3,
                (short) 1));
    }

    /**
     * test 0x67
     *
     * @throws Exception if an error occurred.
     */
    public void test0x67() throws Exception {

        assertEquals("g", reader.mapCharCodeToGlyphname(0x67, (short) 3,
                (short) 1));
    }

    /**
     * test 0x68
     *
     * @throws Exception if an error occurred.
     */
    public void test0x68() throws Exception {

        assertEquals("h", reader.mapCharCodeToGlyphname(0x68, (short) 3,
                (short) 1));
    }

    /**
     * test 0x69
     *
     * @throws Exception if an error occurred.
     */
    public void test0x69() throws Exception {

        assertEquals("i", reader.mapCharCodeToGlyphname(0x69, (short) 3,
                (short) 1));
    }

    /**
     * test 0x6a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x6a() throws Exception {

        assertEquals("j", reader.mapCharCodeToGlyphname(0x6a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x6b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x6b() throws Exception {

        assertEquals("k", reader.mapCharCodeToGlyphname(0x6b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x6c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x6c() throws Exception {

        assertEquals("l", reader.mapCharCodeToGlyphname(0x6c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x6d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x6d() throws Exception {

        assertEquals("m", reader.mapCharCodeToGlyphname(0x6d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x6e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x6e() throws Exception {

        assertEquals("n", reader.mapCharCodeToGlyphname(0x6e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x6f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x6f() throws Exception {

        assertEquals("o", reader.mapCharCodeToGlyphname(0x6f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x70
     *
     * @throws Exception if an error occurred.
     */
    public void test0x70() throws Exception {

        assertEquals("p", reader.mapCharCodeToGlyphname(0x70, (short) 3,
                (short) 1));
    }

    /**
     * test 0x71
     *
     * @throws Exception if an error occurred.
     */
    public void test0x71() throws Exception {

        assertEquals("q", reader.mapCharCodeToGlyphname(0x71, (short) 3,
                (short) 1));
    }

    /**
     * test 0x72
     *
     * @throws Exception if an error occurred.
     */
    public void test0x72() throws Exception {

        assertEquals("r", reader.mapCharCodeToGlyphname(0x72, (short) 3,
                (short) 1));
    }

    /**
     * test 0x73
     *
     * @throws Exception if an error occurred.
     */
    public void test0x73() throws Exception {

        assertEquals("s", reader.mapCharCodeToGlyphname(0x73, (short) 3,
                (short) 1));
    }

    /**
     * test 0x74
     *
     * @throws Exception if an error occurred.
     */
    public void test0x74() throws Exception {

        assertEquals("t", reader.mapCharCodeToGlyphname(0x74, (short) 3,
                (short) 1));
    }

    /**
     * test 0x75
     *
     * @throws Exception if an error occurred.
     */
    public void test0x75() throws Exception {

        assertEquals("u", reader.mapCharCodeToGlyphname(0x75, (short) 3,
                (short) 1));
    }

    /**
     * test 0x76
     *
     * @throws Exception if an error occurred.
     */
    public void test0x76() throws Exception {

        assertEquals("v", reader.mapCharCodeToGlyphname(0x76, (short) 3,
                (short) 1));
    }

    /**
     * test 0x77
     *
     * @throws Exception if an error occurred.
     */
    public void test0x77() throws Exception {

        assertEquals("w", reader.mapCharCodeToGlyphname(0x77, (short) 3,
                (short) 1));
    }

    /**
     * test 0x78
     *
     * @throws Exception if an error occurred.
     */
    public void test0x78() throws Exception {

        assertEquals("x", reader.mapCharCodeToGlyphname(0x78, (short) 3,
                (short) 1));
    }

    /**
     * test 0x79
     *
     * @throws Exception if an error occurred.
     */
    public void test0x79() throws Exception {

        assertEquals("y", reader.mapCharCodeToGlyphname(0x79, (short) 3,
                (short) 1));
    }

    /**
     * test 0x7a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x7a() throws Exception {

        assertEquals("z", reader.mapCharCodeToGlyphname(0x7a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x7b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x7b() throws Exception {

        assertEquals("braceleft", reader.mapCharCodeToGlyphname(0x7b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x7c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x7c() throws Exception {

        assertEquals("bar", reader.mapCharCodeToGlyphname(0x7c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x7d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x7d() throws Exception {

        assertEquals("braceright", reader.mapCharCodeToGlyphname(0x7d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x7e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x7e() throws Exception {

        assertEquals("asciitilde", reader.mapCharCodeToGlyphname(0x7e,
                (short) 3, (short) 1));
    }

    /**
     * test 0xa0
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa0() throws Exception {

        assertEquals("nbspace", reader.mapCharCodeToGlyphname(0xa0, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa1
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa1() throws Exception {

        assertEquals("exclamdown", reader.mapCharCodeToGlyphname(0xa1,
                (short) 3, (short) 1));
    }

    /**
     * test 0xa2
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa2() throws Exception {

        assertEquals("cent", reader.mapCharCodeToGlyphname(0xa2, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa3
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa3() throws Exception {

        assertEquals("sterling", reader.mapCharCodeToGlyphname(0xa3, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa4
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa4() throws Exception {

        assertEquals("currency", reader.mapCharCodeToGlyphname(0xa4, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa5
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa5() throws Exception {

        assertEquals("yen", reader.mapCharCodeToGlyphname(0xa5, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa6
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa6() throws Exception {

        assertEquals("brokenbar", reader.mapCharCodeToGlyphname(0xa6,
                (short) 3, (short) 1));
    }

    /**
     * test 0xa7
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa7() throws Exception {

        assertEquals("section", reader.mapCharCodeToGlyphname(0xa7, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa8
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa8() throws Exception {

        assertEquals("dieresis", reader.mapCharCodeToGlyphname(0xa8, (short) 3,
                (short) 1));
    }

    /**
     * test 0xa9
     *
     * @throws Exception if an error occurred.
     */
    public void test0xa9() throws Exception {

        assertEquals("copyright", reader.mapCharCodeToGlyphname(0xa9,
                (short) 3, (short) 1));
    }

    /**
     * test 0xaa
     *
     * @throws Exception if an error occurred.
     */
    public void test0xaa() throws Exception {

        assertEquals("ordfeminine", reader.mapCharCodeToGlyphname(0xaa,
                (short) 3, (short) 1));
    }

    /**
     * test 0xab
     *
     * @throws Exception if an error occurred.
     */
    public void test0xab() throws Exception {

        assertEquals("guillemotleft", reader.mapCharCodeToGlyphname(0xab,
                (short) 3, (short) 1));
    }

    /**
     * test 0xac
     *
     * @throws Exception if an error occurred.
     */
    public void test0xac() throws Exception {

        assertEquals("logicalnot", reader.mapCharCodeToGlyphname(0xac,
                (short) 3, (short) 1));
    }

    /**
     * test 0xad
     *
     * @throws Exception if an error occurred.
     */
    public void test0xad() throws Exception {

        assertEquals("sfthyphen", reader.mapCharCodeToGlyphname(0xad,
                (short) 3, (short) 1));
    }

    /**
     * test 0xae
     *
     * @throws Exception if an error occurred.
     */
    public void test0xae() throws Exception {

        assertEquals("registered", reader.mapCharCodeToGlyphname(0xae,
                (short) 3, (short) 1));
    }

    /**
     * test 0xaf
     *
     * @throws Exception if an error occurred.
     */
    public void test0xaf() throws Exception {

        assertEquals("overscore", reader.mapCharCodeToGlyphname(0xaf,
                (short) 3, (short) 1));
    }

    /**
     * test 0xb0
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb0() throws Exception {

        assertEquals("degree", reader.mapCharCodeToGlyphname(0xb0, (short) 3,
                (short) 1));
    }

    /**
     * test 0xb1
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb1() throws Exception {

        assertEquals("plusminus", reader.mapCharCodeToGlyphname(0xb1,
                (short) 3, (short) 1));
    }

    /**
     * test 0xb2
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb2() throws Exception {

        assertEquals("twosuperior", reader.mapCharCodeToGlyphname(0xb2,
                (short) 3, (short) 1));
    }

    /**
     * test 0xb3
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb3() throws Exception {

        assertEquals("threesuperior", reader.mapCharCodeToGlyphname(0xb3,
                (short) 3, (short) 1));
    }

    /**
     * test 0xb4
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb4() throws Exception {

        assertEquals("acute", reader.mapCharCodeToGlyphname(0xb4, (short) 3,
                (short) 1));
    }

    /**
     * test 0xb5
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb5() throws Exception {

        assertEquals("mu1", reader.mapCharCodeToGlyphname(0xb5, (short) 3,
                (short) 1));
    }

    /**
     * test 0xb6
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb6() throws Exception {

        assertEquals("paragraph", reader.mapCharCodeToGlyphname(0xb6,
                (short) 3, (short) 1));
    }

    /**
     * test 0xb7
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb7() throws Exception {

        assertEquals("middot", reader.mapCharCodeToGlyphname(0xb7, (short) 3,
                (short) 1));
    }

    /**
     * test 0xb8
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb8() throws Exception {

        assertEquals("cedilla", reader.mapCharCodeToGlyphname(0xb8, (short) 3,
                (short) 1));
    }

    /**
     * test 0xb9
     *
     * @throws Exception if an error occurred.
     */
    public void test0xb9() throws Exception {

        assertEquals("onesuperior", reader.mapCharCodeToGlyphname(0xb9,
                (short) 3, (short) 1));
    }

    /**
     * test 0xba
     *
     * @throws Exception if an error occurred.
     */
    public void test0xba() throws Exception {

        assertEquals("ordmasculine", reader.mapCharCodeToGlyphname(0xba,
                (short) 3, (short) 1));
    }

    /**
     * test 0xbb
     *
     * @throws Exception if an error occurred.
     */
    public void test0xbb() throws Exception {

        assertEquals("guillemotright", reader.mapCharCodeToGlyphname(0xbb,
                (short) 3, (short) 1));
    }

    /**
     * test 0xbc
     *
     * @throws Exception if an error occurred.
     */
    public void test0xbc() throws Exception {

        assertEquals("onequarter", reader.mapCharCodeToGlyphname(0xbc,
                (short) 3, (short) 1));
    }

    /**
     * test 0xbd
     *
     * @throws Exception if an error occurred.
     */
    public void test0xbd() throws Exception {

        assertEquals("onehalf", reader.mapCharCodeToGlyphname(0xbd, (short) 3,
                (short) 1));
    }

    /**
     * test 0xbe
     *
     * @throws Exception if an error occurred.
     */
    public void test0xbe() throws Exception {

        assertEquals("threequarters", reader.mapCharCodeToGlyphname(0xbe,
                (short) 3, (short) 1));
    }

    /**
     * test 0xbf
     *
     * @throws Exception if an error occurred.
     */
    public void test0xbf() throws Exception {

        assertEquals("questiondown", reader.mapCharCodeToGlyphname(0xbf,
                (short) 3, (short) 1));
    }

    /**
     * test 0xc0
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc0() throws Exception {

        assertEquals("Agrave", reader.mapCharCodeToGlyphname(0xc0, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc1
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc1() throws Exception {

        assertEquals("Aacute", reader.mapCharCodeToGlyphname(0xc1, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc2
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc2() throws Exception {

        assertEquals("Acircumflex", reader.mapCharCodeToGlyphname(0xc2,
                (short) 3, (short) 1));
    }

    /**
     * test 0xc3
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc3() throws Exception {

        assertEquals("Atilde", reader.mapCharCodeToGlyphname(0xc3, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc4
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc4() throws Exception {

        assertEquals("Adieresis", reader.mapCharCodeToGlyphname(0xc4,
                (short) 3, (short) 1));
    }

    /**
     * test 0xc5
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc5() throws Exception {

        assertEquals("Aring", reader.mapCharCodeToGlyphname(0xc5, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc6
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc6() throws Exception {

        assertEquals("AE", reader.mapCharCodeToGlyphname(0xc6, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc7
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc7() throws Exception {

        assertEquals("Ccedilla", reader.mapCharCodeToGlyphname(0xc7, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc8
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc8() throws Exception {

        assertEquals("Egrave", reader.mapCharCodeToGlyphname(0xc8, (short) 3,
                (short) 1));
    }

    /**
     * test 0xc9
     *
     * @throws Exception if an error occurred.
     */
    public void test0xc9() throws Exception {

        assertEquals("Eacute", reader.mapCharCodeToGlyphname(0xc9, (short) 3,
                (short) 1));
    }

    /**
     * test 0xca
     *
     * @throws Exception if an error occurred.
     */
    public void test0xca() throws Exception {

        assertEquals("Ecircumflex", reader.mapCharCodeToGlyphname(0xca,
                (short) 3, (short) 1));
    }

    /**
     * test 0xcb
     *
     * @throws Exception if an error occurred.
     */
    public void test0xcb() throws Exception {

        assertEquals("Edieresis", reader.mapCharCodeToGlyphname(0xcb,
                (short) 3, (short) 1));
    }

    /**
     * test 0xcc
     *
     * @throws Exception if an error occurred.
     */
    public void test0xcc() throws Exception {

        assertEquals("Igrave", reader.mapCharCodeToGlyphname(0xcc, (short) 3,
                (short) 1));
    }

    /**
     * test 0xcd
     *
     * @throws Exception if an error occurred.
     */
    public void test0xcd() throws Exception {

        assertEquals("Iacute", reader.mapCharCodeToGlyphname(0xcd, (short) 3,
                (short) 1));
    }

    /**
     * test 0xce
     *
     * @throws Exception if an error occurred.
     */
    public void test0xce() throws Exception {

        assertEquals("Icircumflex", reader.mapCharCodeToGlyphname(0xce,
                (short) 3, (short) 1));
    }

    /**
     * test 0xcf
     *
     * @throws Exception if an error occurred.
     */
    public void test0xcf() throws Exception {

        assertEquals("Idieresis", reader.mapCharCodeToGlyphname(0xcf,
                (short) 3, (short) 1));
    }

    /**
     * test 0xd0
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd0() throws Exception {

        assertEquals("Eth", reader.mapCharCodeToGlyphname(0xd0, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd1
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd1() throws Exception {

        assertEquals("Ntilde", reader.mapCharCodeToGlyphname(0xd1, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd2
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd2() throws Exception {

        assertEquals("Ograve", reader.mapCharCodeToGlyphname(0xd2, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd3
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd3() throws Exception {

        assertEquals("Oacute", reader.mapCharCodeToGlyphname(0xd3, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd4
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd4() throws Exception {

        assertEquals("Ocircumflex", reader.mapCharCodeToGlyphname(0xd4,
                (short) 3, (short) 1));
    }

    /**
     * test 0xd5
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd5() throws Exception {

        assertEquals("Otilde", reader.mapCharCodeToGlyphname(0xd5, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd6
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd6() throws Exception {

        assertEquals("Odieresis", reader.mapCharCodeToGlyphname(0xd6,
                (short) 3, (short) 1));
    }

    /**
     * test 0xd7
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd7() throws Exception {

        assertEquals("multiply", reader.mapCharCodeToGlyphname(0xd7, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd8
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd8() throws Exception {

        assertEquals("Oslash", reader.mapCharCodeToGlyphname(0xd8, (short) 3,
                (short) 1));
    }

    /**
     * test 0xd9
     *
     * @throws Exception if an error occurred.
     */
    public void test0xd9() throws Exception {

        assertEquals("Ugrave", reader.mapCharCodeToGlyphname(0xd9, (short) 3,
                (short) 1));
    }

    /**
     * test 0xda
     *
     * @throws Exception if an error occurred.
     */
    public void test0xda() throws Exception {

        assertEquals("Uacute", reader.mapCharCodeToGlyphname(0xda, (short) 3,
                (short) 1));
    }

    /**
     * test 0xdb
     *
     * @throws Exception if an error occurred.
     */
    public void test0xdb() throws Exception {

        assertEquals("Ucircumflex", reader.mapCharCodeToGlyphname(0xdb,
                (short) 3, (short) 1));
    }

    /**
     * test 0xdc
     *
     * @throws Exception if an error occurred.
     */
    public void test0xdc() throws Exception {

        assertEquals("Udieresis", reader.mapCharCodeToGlyphname(0xdc,
                (short) 3, (short) 1));
    }

    /**
     * test 0xdd
     *
     * @throws Exception if an error occurred.
     */
    public void test0xdd() throws Exception {

        assertEquals("Yacute", reader.mapCharCodeToGlyphname(0xdd, (short) 3,
                (short) 1));
    }

    /**
     * test 0xde
     *
     * @throws Exception if an error occurred.
     */
    public void test0xde() throws Exception {

        assertEquals("Thorn", reader.mapCharCodeToGlyphname(0xde, (short) 3,
                (short) 1));
    }

    /**
     * test 0xdf
     *
     * @throws Exception if an error occurred.
     */
    public void test0xdf() throws Exception {

        assertEquals("germandbls", reader.mapCharCodeToGlyphname(0xdf,
                (short) 3, (short) 1));
    }

    /**
     * test 0xe0
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe0() throws Exception {

        assertEquals("agrave", reader.mapCharCodeToGlyphname(0xe0, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe1
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe1() throws Exception {

        assertEquals("aacute", reader.mapCharCodeToGlyphname(0xe1, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe2
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe2() throws Exception {

        assertEquals("acircumflex", reader.mapCharCodeToGlyphname(0xe2,
                (short) 3, (short) 1));
    }

    /**
     * test 0xe3
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe3() throws Exception {

        assertEquals("atilde", reader.mapCharCodeToGlyphname(0xe3, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe4
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe4() throws Exception {

        assertEquals("adieresis", reader.mapCharCodeToGlyphname(0xe4,
                (short) 3, (short) 1));
    }

    /**
     * test 0xe5
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe5() throws Exception {

        assertEquals("aring", reader.mapCharCodeToGlyphname(0xe5, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe6
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe6() throws Exception {

        assertEquals("ae", reader.mapCharCodeToGlyphname(0xe6, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe7
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe7() throws Exception {

        assertEquals("ccedilla", reader.mapCharCodeToGlyphname(0xe7, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe8
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe8() throws Exception {

        assertEquals("egrave", reader.mapCharCodeToGlyphname(0xe8, (short) 3,
                (short) 1));
    }

    /**
     * test 0xe9
     *
     * @throws Exception if an error occurred.
     */
    public void test0xe9() throws Exception {

        assertEquals("eacute", reader.mapCharCodeToGlyphname(0xe9, (short) 3,
                (short) 1));
    }

    /**
     * test 0xea
     *
     * @throws Exception if an error occurred.
     */
    public void test0xea() throws Exception {

        assertEquals("ecircumflex", reader.mapCharCodeToGlyphname(0xea,
                (short) 3, (short) 1));
    }

    /**
     * test 0xeb
     *
     * @throws Exception if an error occurred.
     */
    public void test0xeb() throws Exception {

        assertEquals("edieresis", reader.mapCharCodeToGlyphname(0xeb,
                (short) 3, (short) 1));
    }

    /**
     * test 0xec
     *
     * @throws Exception if an error occurred.
     */
    public void test0xec() throws Exception {

        assertEquals("igrave", reader.mapCharCodeToGlyphname(0xec, (short) 3,
                (short) 1));
    }

    /**
     * test 0xed
     *
     * @throws Exception if an error occurred.
     */
    public void test0xed() throws Exception {

        assertEquals("iacute", reader.mapCharCodeToGlyphname(0xed, (short) 3,
                (short) 1));
    }

    /**
     * test 0xee
     *
     * @throws Exception if an error occurred.
     */
    public void test0xee() throws Exception {

        assertEquals("icircumflex", reader.mapCharCodeToGlyphname(0xee,
                (short) 3, (short) 1));
    }

    /**
     * test 0xef
     *
     * @throws Exception if an error occurred.
     */
    public void test0xef() throws Exception {

        assertEquals("idieresis", reader.mapCharCodeToGlyphname(0xef,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf0
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf0() throws Exception {

        assertEquals("eth", reader.mapCharCodeToGlyphname(0xf0, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf1
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf1() throws Exception {

        assertEquals("ntilde", reader.mapCharCodeToGlyphname(0xf1, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf2
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf2() throws Exception {

        assertEquals("ograve", reader.mapCharCodeToGlyphname(0xf2, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf3
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf3() throws Exception {

        assertEquals("oacute", reader.mapCharCodeToGlyphname(0xf3, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf4
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf4() throws Exception {

        assertEquals("ocircumflex", reader.mapCharCodeToGlyphname(0xf4,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf5
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf5() throws Exception {

        assertEquals("otilde", reader.mapCharCodeToGlyphname(0xf5, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf6
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf6() throws Exception {

        assertEquals("odieresis", reader.mapCharCodeToGlyphname(0xf6,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf7
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf7() throws Exception {

        assertEquals("divide", reader.mapCharCodeToGlyphname(0xf7, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf8
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf8() throws Exception {

        assertEquals("oslash", reader.mapCharCodeToGlyphname(0xf8, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf9
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf9() throws Exception {

        assertEquals("ugrave", reader.mapCharCodeToGlyphname(0xf9, (short) 3,
                (short) 1));
    }

    /**
     * test 0xfa
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfa() throws Exception {

        assertEquals("uacute", reader.mapCharCodeToGlyphname(0xfa, (short) 3,
                (short) 1));
    }

    /**
     * test 0xfb
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfb() throws Exception {

        assertEquals("ucircumflex", reader.mapCharCodeToGlyphname(0xfb,
                (short) 3, (short) 1));
    }

    /**
     * test 0xfc
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfc() throws Exception {

        assertEquals("udieresis", reader.mapCharCodeToGlyphname(0xfc,
                (short) 3, (short) 1));
    }

    /**
     * test 0xfd
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfd() throws Exception {

        assertEquals("yacute", reader.mapCharCodeToGlyphname(0xfd, (short) 3,
                (short) 1));
    }

    /**
     * test 0xfe
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfe() throws Exception {

        assertEquals("thorn", reader.mapCharCodeToGlyphname(0xfe, (short) 3,
                (short) 1));
    }

    /**
     * test 0xff
     *
     * @throws Exception if an error occurred.
     */
    public void test0xff() throws Exception {

        assertEquals("ydieresis", reader.mapCharCodeToGlyphname(0xff,
                (short) 3, (short) 1));
    }

    /**
     * test 0x100
     *
     * @throws Exception if an error occurred.
     */
    public void test0x100() throws Exception {

        assertEquals("Amacron", reader.mapCharCodeToGlyphname(0x100, (short) 3,
                (short) 1));
    }

    /**
     * test 0x101
     *
     * @throws Exception if an error occurred.
     */
    public void test0x101() throws Exception {

        assertEquals("amacron", reader.mapCharCodeToGlyphname(0x101, (short) 3,
                (short) 1));
    }

    /**
     * test 0x102
     *
     * @throws Exception if an error occurred.
     */
    public void test0x102() throws Exception {

        assertEquals("Abreve", reader.mapCharCodeToGlyphname(0x102, (short) 3,
                (short) 1));
    }

    /**
     * test 0x103
     *
     * @throws Exception if an error occurred.
     */
    public void test0x103() throws Exception {

        assertEquals("abreve", reader.mapCharCodeToGlyphname(0x103, (short) 3,
                (short) 1));
    }

    /**
     * test 0x104
     *
     * @throws Exception if an error occurred.
     */
    public void test0x104() throws Exception {

        assertEquals("Aogonek", reader.mapCharCodeToGlyphname(0x104, (short) 3,
                (short) 1));
    }

    /**
     * test 0x105
     *
     * @throws Exception if an error occurred.
     */
    public void test0x105() throws Exception {

        assertEquals("aogonek", reader.mapCharCodeToGlyphname(0x105, (short) 3,
                (short) 1));
    }

    /**
     * test 0x106
     *
     * @throws Exception if an error occurred.
     */
    public void test0x106() throws Exception {

        assertEquals("Cacute", reader.mapCharCodeToGlyphname(0x106, (short) 3,
                (short) 1));
    }

    /**
     * test 0x107
     *
     * @throws Exception if an error occurred.
     */
    public void test0x107() throws Exception {

        assertEquals("cacute", reader.mapCharCodeToGlyphname(0x107, (short) 3,
                (short) 1));
    }

    /**
     * test 0x108
     *
     * @throws Exception if an error occurred.
     */
    public void test0x108() throws Exception {

        assertEquals("Ccircumflex", reader.mapCharCodeToGlyphname(0x108,
                (short) 3, (short) 1));
    }

    /**
     * test 0x109
     *
     * @throws Exception if an error occurred.
     */
    public void test0x109() throws Exception {

        assertEquals("ccircumflex", reader.mapCharCodeToGlyphname(0x109,
                (short) 3, (short) 1));
    }

    /**
     * test 0x10a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x10a() throws Exception {

        assertEquals("Cdot", reader.mapCharCodeToGlyphname(0x10a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x10b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x10b() throws Exception {

        assertEquals("cdot", reader.mapCharCodeToGlyphname(0x10b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x10c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x10c() throws Exception {

        assertEquals("Ccaron", reader.mapCharCodeToGlyphname(0x10c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x10d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x10d() throws Exception {

        assertEquals("ccaron", reader.mapCharCodeToGlyphname(0x10d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x10e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x10e() throws Exception {

        assertEquals("Dcaron", reader.mapCharCodeToGlyphname(0x10e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x10f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x10f() throws Exception {

        assertEquals("dcaron", reader.mapCharCodeToGlyphname(0x10f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x110
     *
     * @throws Exception if an error occurred.
     */
    public void test0x110() throws Exception {

        assertEquals("Dslash", reader.mapCharCodeToGlyphname(0x110, (short) 3,
                (short) 1));
    }

    /**
     * test 0x111
     *
     * @throws Exception if an error occurred.
     */
    public void test0x111() throws Exception {

        assertEquals("dmacron", reader.mapCharCodeToGlyphname(0x111, (short) 3,
                (short) 1));
    }

    /**
     * test 0x112
     *
     * @throws Exception if an error occurred.
     */
    public void test0x112() throws Exception {

        assertEquals("Emacron", reader.mapCharCodeToGlyphname(0x112, (short) 3,
                (short) 1));
    }

    /**
     * test 0x113
     *
     * @throws Exception if an error occurred.
     */
    public void test0x113() throws Exception {

        assertEquals("emacron", reader.mapCharCodeToGlyphname(0x113, (short) 3,
                (short) 1));
    }

    /**
     * test 0x114
     *
     * @throws Exception if an error occurred.
     */
    public void test0x114() throws Exception {

        assertEquals("Ebreve", reader.mapCharCodeToGlyphname(0x114, (short) 3,
                (short) 1));
    }

    /**
     * test 0x115
     *
     * @throws Exception if an error occurred.
     */
    public void test0x115() throws Exception {

        assertEquals("ebreve", reader.mapCharCodeToGlyphname(0x115, (short) 3,
                (short) 1));
    }

    /**
     * test 0x116
     *
     * @throws Exception if an error occurred.
     */
    public void test0x116() throws Exception {

        assertEquals("Edot", reader.mapCharCodeToGlyphname(0x116, (short) 3,
                (short) 1));
    }

    /**
     * test 0x117
     *
     * @throws Exception if an error occurred.
     */
    public void test0x117() throws Exception {

        assertEquals("edot", reader.mapCharCodeToGlyphname(0x117, (short) 3,
                (short) 1));
    }

    /**
     * test 0x118
     *
     * @throws Exception if an error occurred.
     */
    public void test0x118() throws Exception {

        assertEquals("Eogonek", reader.mapCharCodeToGlyphname(0x118, (short) 3,
                (short) 1));
    }

    /**
     * test 0x119
     *
     * @throws Exception if an error occurred.
     */
    public void test0x119() throws Exception {

        assertEquals("eogonek", reader.mapCharCodeToGlyphname(0x119, (short) 3,
                (short) 1));
    }

    /**
     * test 0x11a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x11a() throws Exception {

        assertEquals("Ecaron", reader.mapCharCodeToGlyphname(0x11a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x11b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x11b() throws Exception {

        assertEquals("ecaron", reader.mapCharCodeToGlyphname(0x11b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x11c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x11c() throws Exception {

        assertEquals("Gcircumflex", reader.mapCharCodeToGlyphname(0x11c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x11d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x11d() throws Exception {

        assertEquals("gcircumflex", reader.mapCharCodeToGlyphname(0x11d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x11e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x11e() throws Exception {

        assertEquals("Gbreve", reader.mapCharCodeToGlyphname(0x11e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x11f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x11f() throws Exception {

        assertEquals("gbreve", reader.mapCharCodeToGlyphname(0x11f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x120
     *
     * @throws Exception if an error occurred.
     */
    public void test0x120() throws Exception {

        assertEquals("Gdot", reader.mapCharCodeToGlyphname(0x120, (short) 3,
                (short) 1));
    }

    /**
     * test 0x121
     *
     * @throws Exception if an error occurred.
     */
    public void test0x121() throws Exception {

        assertEquals("gdot", reader.mapCharCodeToGlyphname(0x121, (short) 3,
                (short) 1));
    }

    /**
     * test 0x122
     *
     * @throws Exception if an error occurred.
     */
    public void test0x122() throws Exception {

        assertEquals("Gcedilla", reader.mapCharCodeToGlyphname(0x122,
                (short) 3, (short) 1));
    }

    /**
     * test 0x123
     *
     * @throws Exception if an error occurred.
     */
    public void test0x123() throws Exception {

        assertEquals("gcedilla", reader.mapCharCodeToGlyphname(0x123,
                (short) 3, (short) 1));
    }

    /**
     * test 0x124
     *
     * @throws Exception if an error occurred.
     */
    public void test0x124() throws Exception {

        assertEquals("Hcircumflex", reader.mapCharCodeToGlyphname(0x124,
                (short) 3, (short) 1));
    }

    /**
     * test 0x125
     *
     * @throws Exception if an error occurred.
     */
    public void test0x125() throws Exception {

        assertEquals("hcircumflex", reader.mapCharCodeToGlyphname(0x125,
                (short) 3, (short) 1));
    }

    /**
     * test 0x126
     *
     * @throws Exception if an error occurred.
     */
    public void test0x126() throws Exception {

        assertEquals("Hbar", reader.mapCharCodeToGlyphname(0x126, (short) 3,
                (short) 1));
    }

    /**
     * test 0x127
     *
     * @throws Exception if an error occurred.
     */
    public void test0x127() throws Exception {

        assertEquals("hbar", reader.mapCharCodeToGlyphname(0x127, (short) 3,
                (short) 1));
    }

    /**
     * test 0x128
     *
     * @throws Exception if an error occurred.
     */
    public void test0x128() throws Exception {

        assertEquals("Itilde", reader.mapCharCodeToGlyphname(0x128, (short) 3,
                (short) 1));
    }

    /**
     * test 0x129
     *
     * @throws Exception if an error occurred.
     */
    public void test0x129() throws Exception {

        assertEquals("itilde", reader.mapCharCodeToGlyphname(0x129, (short) 3,
                (short) 1));
    }

    /**
     * test 0x12a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x12a() throws Exception {

        assertEquals("Imacron", reader.mapCharCodeToGlyphname(0x12a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x12b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x12b() throws Exception {

        assertEquals("imacron", reader.mapCharCodeToGlyphname(0x12b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x12c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x12c() throws Exception {

        assertEquals("Ibreve", reader.mapCharCodeToGlyphname(0x12c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x12d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x12d() throws Exception {

        assertEquals("ibreve", reader.mapCharCodeToGlyphname(0x12d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x12e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x12e() throws Exception {

        assertEquals("Iogonek", reader.mapCharCodeToGlyphname(0x12e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x12f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x12f() throws Exception {

        assertEquals("iogonek", reader.mapCharCodeToGlyphname(0x12f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x130
     *
     * @throws Exception if an error occurred.
     */
    public void test0x130() throws Exception {

        assertEquals("Idot", reader.mapCharCodeToGlyphname(0x130, (short) 3,
                (short) 1));
    }

    /**
     * test 0x131
     *
     * @throws Exception if an error occurred.
     */
    public void test0x131() throws Exception {

        assertEquals("dotlessi", reader.mapCharCodeToGlyphname(0x131,
                (short) 3, (short) 1));
    }

    /**
     * test 0x132
     *
     * @throws Exception if an error occurred.
     */
    public void test0x132() throws Exception {

        assertEquals("IJ", reader.mapCharCodeToGlyphname(0x132, (short) 3,
                (short) 1));
    }

    /**
     * test 0x133
     *
     * @throws Exception if an error occurred.
     */
    public void test0x133() throws Exception {

        assertEquals("ij", reader.mapCharCodeToGlyphname(0x133, (short) 3,
                (short) 1));
    }

    /**
     * test 0x134
     *
     * @throws Exception if an error occurred.
     */
    public void test0x134() throws Exception {

        assertEquals("Jcircumflex", reader.mapCharCodeToGlyphname(0x134,
                (short) 3, (short) 1));
    }

    /**
     * test 0x135
     *
     * @throws Exception if an error occurred.
     */
    public void test0x135() throws Exception {

        assertEquals("jcircumflex", reader.mapCharCodeToGlyphname(0x135,
                (short) 3, (short) 1));
    }

    /**
     * test 0x136
     *
     * @throws Exception if an error occurred.
     */
    public void test0x136() throws Exception {

        assertEquals("Kcedilla", reader.mapCharCodeToGlyphname(0x136,
                (short) 3, (short) 1));
    }

    /**
     * test 0x137
     *
     * @throws Exception if an error occurred.
     */
    public void test0x137() throws Exception {

        assertEquals("kcedilla", reader.mapCharCodeToGlyphname(0x137,
                (short) 3, (short) 1));
    }

    /**
     * test 0x138
     *
     * @throws Exception if an error occurred.
     */
    public void test0x138() throws Exception {

        assertEquals("kgreenlandic", reader.mapCharCodeToGlyphname(0x138,
                (short) 3, (short) 1));
    }

    /**
     * test 0x139
     *
     * @throws Exception if an error occurred.
     */
    public void test0x139() throws Exception {

        assertEquals("Lacute", reader.mapCharCodeToGlyphname(0x139, (short) 3,
                (short) 1));
    }

    /**
     * test 0x13a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x13a() throws Exception {

        assertEquals("lacute", reader.mapCharCodeToGlyphname(0x13a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x13b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x13b() throws Exception {

        assertEquals("Lcedilla", reader.mapCharCodeToGlyphname(0x13b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x13c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x13c() throws Exception {

        assertEquals("lcedilla", reader.mapCharCodeToGlyphname(0x13c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x13d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x13d() throws Exception {

        assertEquals("Lcaron", reader.mapCharCodeToGlyphname(0x13d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x13e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x13e() throws Exception {

        assertEquals("lcaron", reader.mapCharCodeToGlyphname(0x13e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x13f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x13f() throws Exception {

        assertEquals("Ldot", reader.mapCharCodeToGlyphname(0x13f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x140
     *
     * @throws Exception if an error occurred.
     */
    public void test0x140() throws Exception {

        assertEquals("ldot", reader.mapCharCodeToGlyphname(0x140, (short) 3,
                (short) 1));
    }

    /**
     * test 0x141
     *
     * @throws Exception if an error occurred.
     */
    public void test0x141() throws Exception {

        assertEquals("Lslash", reader.mapCharCodeToGlyphname(0x141, (short) 3,
                (short) 1));
    }

    /**
     * test 0x142
     *
     * @throws Exception if an error occurred.
     */
    public void test0x142() throws Exception {

        assertEquals("lslash", reader.mapCharCodeToGlyphname(0x142, (short) 3,
                (short) 1));
    }

    /**
     * test 0x143
     *
     * @throws Exception if an error occurred.
     */
    public void test0x143() throws Exception {

        assertEquals("Nacute", reader.mapCharCodeToGlyphname(0x143, (short) 3,
                (short) 1));
    }

    /**
     * test 0x144
     *
     * @throws Exception if an error occurred.
     */
    public void test0x144() throws Exception {

        assertEquals("nacute", reader.mapCharCodeToGlyphname(0x144, (short) 3,
                (short) 1));
    }

    /**
     * test 0x145
     *
     * @throws Exception if an error occurred.
     */
    public void test0x145() throws Exception {

        assertEquals("Ncedilla", reader.mapCharCodeToGlyphname(0x145,
                (short) 3, (short) 1));
    }

    /**
     * test 0x146
     *
     * @throws Exception if an error occurred.
     */
    public void test0x146() throws Exception {

        assertEquals("ncedilla", reader.mapCharCodeToGlyphname(0x146,
                (short) 3, (short) 1));
    }

    /**
     * test 0x147
     *
     * @throws Exception if an error occurred.
     */
    public void test0x147() throws Exception {

        assertEquals("Ncaron", reader.mapCharCodeToGlyphname(0x147, (short) 3,
                (short) 1));
    }

    /**
     * test 0x148
     *
     * @throws Exception if an error occurred.
     */
    public void test0x148() throws Exception {

        assertEquals("ncaron", reader.mapCharCodeToGlyphname(0x148, (short) 3,
                (short) 1));
    }

    /**
     * test 0x149
     *
     * @throws Exception if an error occurred.
     */
    public void test0x149() throws Exception {

        assertEquals("napostrophe", reader.mapCharCodeToGlyphname(0x149,
                (short) 3, (short) 1));
    }

    /**
     * test 0x14a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x14a() throws Exception {

        assertEquals("Eng", reader.mapCharCodeToGlyphname(0x14a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x14b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x14b() throws Exception {

        assertEquals("eng", reader.mapCharCodeToGlyphname(0x14b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x14c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x14c() throws Exception {

        assertEquals("Omacron", reader.mapCharCodeToGlyphname(0x14c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x14d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x14d() throws Exception {

        assertEquals("omacron", reader.mapCharCodeToGlyphname(0x14d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x14e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x14e() throws Exception {

        assertEquals("Obreve", reader.mapCharCodeToGlyphname(0x14e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x14f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x14f() throws Exception {

        assertEquals("obreve", reader.mapCharCodeToGlyphname(0x14f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x150
     *
     * @throws Exception if an error occurred.
     */
    public void test0x150() throws Exception {

        assertEquals("Odblacute", reader.mapCharCodeToGlyphname(0x150,
                (short) 3, (short) 1));
    }

    /**
     * test 0x151
     *
     * @throws Exception if an error occurred.
     */
    public void test0x151() throws Exception {

        assertEquals("odblacute", reader.mapCharCodeToGlyphname(0x151,
                (short) 3, (short) 1));
    }

    /**
     * test 0x152
     *
     * @throws Exception if an error occurred.
     */
    public void test0x152() throws Exception {

        assertEquals("OE", reader.mapCharCodeToGlyphname(0x152, (short) 3,
                (short) 1));
    }

    /**
     * test 0x153
     *
     * @throws Exception if an error occurred.
     */
    public void test0x153() throws Exception {

        assertEquals("oe", reader.mapCharCodeToGlyphname(0x153, (short) 3,
                (short) 1));
    }

    /**
     * test 0x154
     *
     * @throws Exception if an error occurred.
     */
    public void test0x154() throws Exception {

        assertEquals("Racute", reader.mapCharCodeToGlyphname(0x154, (short) 3,
                (short) 1));
    }

    /**
     * test 0x155
     *
     * @throws Exception if an error occurred.
     */
    public void test0x155() throws Exception {

        assertEquals("racute", reader.mapCharCodeToGlyphname(0x155, (short) 3,
                (short) 1));
    }

    /**
     * test 0x156
     *
     * @throws Exception if an error occurred.
     */
    public void test0x156() throws Exception {

        assertEquals("Rcedilla", reader.mapCharCodeToGlyphname(0x156,
                (short) 3, (short) 1));
    }

    /**
     * test 0x157
     *
     * @throws Exception if an error occurred.
     */
    public void test0x157() throws Exception {

        assertEquals("rcedilla", reader.mapCharCodeToGlyphname(0x157,
                (short) 3, (short) 1));
    }

    /**
     * test 0x158
     *
     * @throws Exception if an error occurred.
     */
    public void test0x158() throws Exception {

        assertEquals("Rcaron", reader.mapCharCodeToGlyphname(0x158, (short) 3,
                (short) 1));
    }

    /**
     * test 0x159
     *
     * @throws Exception if an error occurred.
     */
    public void test0x159() throws Exception {

        assertEquals("rcaron", reader.mapCharCodeToGlyphname(0x159, (short) 3,
                (short) 1));
    }

    /**
     * test 0x15a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x15a() throws Exception {

        assertEquals("Sacute", reader.mapCharCodeToGlyphname(0x15a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x15b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x15b() throws Exception {

        assertEquals("sacute", reader.mapCharCodeToGlyphname(0x15b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x15c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x15c() throws Exception {

        assertEquals("Scircumflex", reader.mapCharCodeToGlyphname(0x15c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x15d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x15d() throws Exception {

        assertEquals("scircumflex", reader.mapCharCodeToGlyphname(0x15d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x15e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x15e() throws Exception {

        assertEquals("Scedilla", reader.mapCharCodeToGlyphname(0x15e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x15f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x15f() throws Exception {

        assertEquals("scedilla", reader.mapCharCodeToGlyphname(0x15f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x160
     *
     * @throws Exception if an error occurred.
     */
    public void test0x160() throws Exception {

        assertEquals("Scaron", reader.mapCharCodeToGlyphname(0x160, (short) 3,
                (short) 1));
    }

    /**
     * test 0x161
     *
     * @throws Exception if an error occurred.
     */
    public void test0x161() throws Exception {

        assertEquals("scaron", reader.mapCharCodeToGlyphname(0x161, (short) 3,
                (short) 1));
    }

    /**
     * test 0x162
     *
     * @throws Exception if an error occurred.
     */
    public void test0x162() throws Exception {

        assertEquals("Tcedilla", reader.mapCharCodeToGlyphname(0x162,
                (short) 3, (short) 1));
    }

    /**
     * test 0x163
     *
     * @throws Exception if an error occurred.
     */
    public void test0x163() throws Exception {

        assertEquals("tcedilla", reader.mapCharCodeToGlyphname(0x163,
                (short) 3, (short) 1));
    }

    /**
     * test 0x164
     *
     * @throws Exception if an error occurred.
     */
    public void test0x164() throws Exception {

        assertEquals("Tcaron", reader.mapCharCodeToGlyphname(0x164, (short) 3,
                (short) 1));
    }

    /**
     * test 0x165
     *
     * @throws Exception if an error occurred.
     */
    public void test0x165() throws Exception {

        assertEquals("tcaron", reader.mapCharCodeToGlyphname(0x165, (short) 3,
                (short) 1));
    }

    /**
     * test 0x166
     *
     * @throws Exception if an error occurred.
     */
    public void test0x166() throws Exception {

        assertEquals("Tbar", reader.mapCharCodeToGlyphname(0x166, (short) 3,
                (short) 1));
    }

    /**
     * test 0x167
     *
     * @throws Exception if an error occurred.
     */
    public void test0x167() throws Exception {

        assertEquals("tbar", reader.mapCharCodeToGlyphname(0x167, (short) 3,
                (short) 1));
    }

    /**
     * test 0x168
     *
     * @throws Exception if an error occurred.
     */
    public void test0x168() throws Exception {

        assertEquals("Utilde", reader.mapCharCodeToGlyphname(0x168, (short) 3,
                (short) 1));
    }

    /**
     * test 0x169
     *
     * @throws Exception if an error occurred.
     */
    public void test0x169() throws Exception {

        assertEquals("utilde", reader.mapCharCodeToGlyphname(0x169, (short) 3,
                (short) 1));
    }

    /**
     * test 0x16a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x16a() throws Exception {

        assertEquals("Umacron", reader.mapCharCodeToGlyphname(0x16a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x16b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x16b() throws Exception {

        assertEquals("umacron", reader.mapCharCodeToGlyphname(0x16b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x16c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x16c() throws Exception {

        assertEquals("Ubreve", reader.mapCharCodeToGlyphname(0x16c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x16d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x16d() throws Exception {

        assertEquals("ubreve", reader.mapCharCodeToGlyphname(0x16d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x16e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x16e() throws Exception {

        assertEquals("Uring", reader.mapCharCodeToGlyphname(0x16e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x16f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x16f() throws Exception {

        assertEquals("uring", reader.mapCharCodeToGlyphname(0x16f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x170
     *
     * @throws Exception if an error occurred.
     */
    public void test0x170() throws Exception {

        assertEquals("Udblacute", reader.mapCharCodeToGlyphname(0x170,
                (short) 3, (short) 1));
    }

    /**
     * test 0x171
     *
     * @throws Exception if an error occurred.
     */
    public void test0x171() throws Exception {

        assertEquals("udblacute", reader.mapCharCodeToGlyphname(0x171,
                (short) 3, (short) 1));
    }

    /**
     * test 0x172
     *
     * @throws Exception if an error occurred.
     */
    public void test0x172() throws Exception {

        assertEquals("Uogonek", reader.mapCharCodeToGlyphname(0x172, (short) 3,
                (short) 1));
    }

    /**
     * test 0x173
     *
     * @throws Exception if an error occurred.
     */
    public void test0x173() throws Exception {

        assertEquals("uogonek", reader.mapCharCodeToGlyphname(0x173, (short) 3,
                (short) 1));
    }

    /**
     * test 0x174
     *
     * @throws Exception if an error occurred.
     */
    public void test0x174() throws Exception {

        assertEquals("Wcircumflex", reader.mapCharCodeToGlyphname(0x174,
                (short) 3, (short) 1));
    }

    /**
     * test 0x175
     *
     * @throws Exception if an error occurred.
     */
    public void test0x175() throws Exception {

        assertEquals("wcircumflex", reader.mapCharCodeToGlyphname(0x175,
                (short) 3, (short) 1));
    }

    /**
     * test 0x176
     *
     * @throws Exception if an error occurred.
     */
    public void test0x176() throws Exception {

        assertEquals("Ycircumflex", reader.mapCharCodeToGlyphname(0x176,
                (short) 3, (short) 1));
    }

    /**
     * test 0x177
     *
     * @throws Exception if an error occurred.
     */
    public void test0x177() throws Exception {

        assertEquals("ycircumflex", reader.mapCharCodeToGlyphname(0x177,
                (short) 3, (short) 1));
    }

    /**
     * test 0x178
     *
     * @throws Exception if an error occurred.
     */
    public void test0x178() throws Exception {

        assertEquals("Ydieresis", reader.mapCharCodeToGlyphname(0x178,
                (short) 3, (short) 1));
    }

    /**
     * test 0x179
     *
     * @throws Exception if an error occurred.
     */
    public void test0x179() throws Exception {

        assertEquals("Zacute", reader.mapCharCodeToGlyphname(0x179, (short) 3,
                (short) 1));
    }

    /**
     * test 0x17a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x17a() throws Exception {

        assertEquals("zacute", reader.mapCharCodeToGlyphname(0x17a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x17b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x17b() throws Exception {

        assertEquals("Zdot", reader.mapCharCodeToGlyphname(0x17b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x17c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x17c() throws Exception {

        assertEquals("zdot", reader.mapCharCodeToGlyphname(0x17c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x17d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x17d() throws Exception {

        assertEquals("Zcaron", reader.mapCharCodeToGlyphname(0x17d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x17e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x17e() throws Exception {

        assertEquals("zcaron", reader.mapCharCodeToGlyphname(0x17e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x17f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x17f() throws Exception {

        assertEquals("longs", reader.mapCharCodeToGlyphname(0x17f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x192
     *
     * @throws Exception if an error occurred.
     */
    public void test0x192() throws Exception {

        assertEquals("florin", reader.mapCharCodeToGlyphname(0x192, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1fa
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1fa() throws Exception {

        assertEquals("Aringacute", reader.mapCharCodeToGlyphname(0x1fa,
                (short) 3, (short) 1));
    }

    /**
     * test 0x1fb
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1fb() throws Exception {

        assertEquals("aringacute", reader.mapCharCodeToGlyphname(0x1fb,
                (short) 3, (short) 1));
    }

    /**
     * test 0x1fc
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1fc() throws Exception {

        assertEquals("AEacute", reader.mapCharCodeToGlyphname(0x1fc, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1fd
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1fd() throws Exception {

        assertEquals("aeacute", reader.mapCharCodeToGlyphname(0x1fd, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1fe
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1fe() throws Exception {

        assertEquals("Oslashacute", reader.mapCharCodeToGlyphname(0x1fe,
                (short) 3, (short) 1));
    }

    /**
     * test 0x1ff
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1ff() throws Exception {

        assertEquals("oslashacute", reader.mapCharCodeToGlyphname(0x1ff,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2c6
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2c6() throws Exception {

        assertEquals("circumflex", reader.mapCharCodeToGlyphname(0x2c6,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2c7
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2c7() throws Exception {

        assertEquals("caron", reader.mapCharCodeToGlyphname(0x2c7, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2d8
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2d8() throws Exception {

        assertEquals("breve", reader.mapCharCodeToGlyphname(0x2d8, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2d9
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2d9() throws Exception {

        assertEquals("dotaccent", reader.mapCharCodeToGlyphname(0x2d9,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2da
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2da() throws Exception {

        assertEquals("ring", reader.mapCharCodeToGlyphname(0x2da, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2db
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2db() throws Exception {

        assertEquals("ogonek", reader.mapCharCodeToGlyphname(0x2db, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2dc
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2dc() throws Exception {

        assertEquals("tilde", reader.mapCharCodeToGlyphname(0x2dc, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2dd
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2dd() throws Exception {

        assertEquals("hungarumlaut", reader.mapCharCodeToGlyphname(0x2dd,
                (short) 3, (short) 1));
    }

    /**
     * test 0x37e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x37e() throws Exception {

        assertEquals("semicolon", reader.mapCharCodeToGlyphname(0x37e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x384
     *
     * @throws Exception if an error occurred.
     */
    public void test0x384() throws Exception {

        assertEquals("tonos", reader.mapCharCodeToGlyphname(0x384, (short) 3,
                (short) 1));
    }

    /**
     * test 0x385
     *
     * @throws Exception if an error occurred.
     */
    public void test0x385() throws Exception {

        assertEquals("dieresistonos", reader.mapCharCodeToGlyphname(0x385,
                (short) 3, (short) 1));
    }

    /**
     * test 0x386
     *
     * @throws Exception if an error occurred.
     */
    public void test0x386() throws Exception {

        assertEquals("Alphatonos", reader.mapCharCodeToGlyphname(0x386,
                (short) 3, (short) 1));
    }

    /**
     * test 0x387
     *
     * @throws Exception if an error occurred.
     */
    public void test0x387() throws Exception {

        assertEquals("anoteleia", reader.mapCharCodeToGlyphname(0x387,
                (short) 3, (short) 1));
    }

    /**
     * test 0x388
     *
     * @throws Exception if an error occurred.
     */
    public void test0x388() throws Exception {

        assertEquals("Epsilontonos", reader.mapCharCodeToGlyphname(0x388,
                (short) 3, (short) 1));
    }

    /**
     * test 0x389
     *
     * @throws Exception if an error occurred.
     */
    public void test0x389() throws Exception {

        assertEquals("Etatonos", reader.mapCharCodeToGlyphname(0x389,
                (short) 3, (short) 1));
    }

    /**
     * test 0x38a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x38a() throws Exception {

        assertEquals("Iotatonos", reader.mapCharCodeToGlyphname(0x38a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x38c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x38c() throws Exception {

        assertEquals("Omicrontonos", reader.mapCharCodeToGlyphname(0x38c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x38e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x38e() throws Exception {

        assertEquals("Upsilontonos", reader.mapCharCodeToGlyphname(0x38e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x38f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x38f() throws Exception {

        assertEquals("Omegatonos", reader.mapCharCodeToGlyphname(0x38f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x390
     *
     * @throws Exception if an error occurred.
     */
    public void test0x390() throws Exception {

        assertEquals("iotadieresistonos", reader.mapCharCodeToGlyphname(0x390,
                (short) 3, (short) 1));
    }

    /**
     * test 0x391
     *
     * @throws Exception if an error occurred.
     */
    public void test0x391() throws Exception {

        assertEquals("Alpha", reader.mapCharCodeToGlyphname(0x391, (short) 3,
                (short) 1));
    }

    /**
     * test 0x392
     *
     * @throws Exception if an error occurred.
     */
    public void test0x392() throws Exception {

        assertEquals("Beta", reader.mapCharCodeToGlyphname(0x392, (short) 3,
                (short) 1));
    }

    /**
     * test 0x393
     *
     * @throws Exception if an error occurred.
     */
    public void test0x393() throws Exception {

        assertEquals("Gamma", reader.mapCharCodeToGlyphname(0x393, (short) 3,
                (short) 1));
    }

    /**
     * test 0x394
     *
     * @throws Exception if an error occurred.
     */
    public void test0x394() throws Exception {

        assertEquals("Delta", reader.mapCharCodeToGlyphname(0x394, (short) 3,
                (short) 1));
    }

    /**
     * test 0x395
     *
     * @throws Exception if an error occurred.
     */
    public void test0x395() throws Exception {

        assertEquals("Epsilon", reader.mapCharCodeToGlyphname(0x395, (short) 3,
                (short) 1));
    }

    /**
     * test 0x396
     *
     * @throws Exception if an error occurred.
     */
    public void test0x396() throws Exception {

        assertEquals("Zeta", reader.mapCharCodeToGlyphname(0x396, (short) 3,
                (short) 1));
    }

    /**
     * test 0x397
     *
     * @throws Exception if an error occurred.
     */
    public void test0x397() throws Exception {

        assertEquals("Eta", reader.mapCharCodeToGlyphname(0x397, (short) 3,
                (short) 1));
    }

    /**
     * test 0x398
     *
     * @throws Exception if an error occurred.
     */
    public void test0x398() throws Exception {

        assertEquals("Theta", reader.mapCharCodeToGlyphname(0x398, (short) 3,
                (short) 1));
    }

    /**
     * test 0x399
     *
     * @throws Exception if an error occurred.
     */
    public void test0x399() throws Exception {

        assertEquals("Iota", reader.mapCharCodeToGlyphname(0x399, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39a() throws Exception {

        assertEquals("Kappa", reader.mapCharCodeToGlyphname(0x39a, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39b() throws Exception {

        assertEquals("Lambda", reader.mapCharCodeToGlyphname(0x39b, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39c() throws Exception {

        assertEquals("Mu", reader.mapCharCodeToGlyphname(0x39c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39d() throws Exception {

        assertEquals("Nu", reader.mapCharCodeToGlyphname(0x39d, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39e() throws Exception {

        assertEquals("Xi", reader.mapCharCodeToGlyphname(0x39e, (short) 3,
                (short) 1));
    }

    /**
     * test 0x39f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x39f() throws Exception {

        assertEquals("Omicron", reader.mapCharCodeToGlyphname(0x39f, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a0
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a0() throws Exception {

        assertEquals("Pi", reader.mapCharCodeToGlyphname(0x3a0, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a1
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a1() throws Exception {

        assertEquals("Rho", reader.mapCharCodeToGlyphname(0x3a1, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a3
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a3() throws Exception {

        assertEquals("Sigma", reader.mapCharCodeToGlyphname(0x3a3, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a4
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a4() throws Exception {

        assertEquals("Tau", reader.mapCharCodeToGlyphname(0x3a4, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a5
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a5() throws Exception {

        assertEquals("Upsilon", reader.mapCharCodeToGlyphname(0x3a5, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a6
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a6() throws Exception {

        assertEquals("Phi", reader.mapCharCodeToGlyphname(0x3a6, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a7
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a7() throws Exception {

        assertEquals("Chi", reader.mapCharCodeToGlyphname(0x3a7, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a8
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a8() throws Exception {

        assertEquals("Psi", reader.mapCharCodeToGlyphname(0x3a8, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3a9
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3a9() throws Exception {

        assertEquals("Omega", reader.mapCharCodeToGlyphname(0x3a9, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3aa
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3aa() throws Exception {

        assertEquals("Iotadieresis", reader.mapCharCodeToGlyphname(0x3aa,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3ab
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ab() throws Exception {

        assertEquals("Upsilondieresis", reader.mapCharCodeToGlyphname(0x3ab,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3ac
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ac() throws Exception {

        assertEquals("alphatonos", reader.mapCharCodeToGlyphname(0x3ac,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3ad
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ad() throws Exception {

        assertEquals("epsilontonos", reader.mapCharCodeToGlyphname(0x3ad,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3ae
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ae() throws Exception {

        assertEquals("etatonos", reader.mapCharCodeToGlyphname(0x3ae,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3af
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3af() throws Exception {

        assertEquals("iotatonos", reader.mapCharCodeToGlyphname(0x3af,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3b0
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b0() throws Exception {

        assertEquals("upsilondieresistonos", reader.mapCharCodeToGlyphname(
                0x3b0, (short) 3, (short) 1));
    }

    /**
     * test 0x3b1
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b1() throws Exception {

        assertEquals("alpha", reader.mapCharCodeToGlyphname(0x3b1, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b2
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b2() throws Exception {

        assertEquals("beta", reader.mapCharCodeToGlyphname(0x3b2, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b3
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b3() throws Exception {

        assertEquals("gamma", reader.mapCharCodeToGlyphname(0x3b3, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b4
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b4() throws Exception {

        assertEquals("delta", reader.mapCharCodeToGlyphname(0x3b4, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b5
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b5() throws Exception {

        assertEquals("epsilon", reader.mapCharCodeToGlyphname(0x3b5, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b6
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b6() throws Exception {

        assertEquals("zeta", reader.mapCharCodeToGlyphname(0x3b6, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b7
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b7() throws Exception {

        assertEquals("eta", reader.mapCharCodeToGlyphname(0x3b7, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b8
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b8() throws Exception {

        assertEquals("theta", reader.mapCharCodeToGlyphname(0x3b8, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3b9
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3b9() throws Exception {

        assertEquals("iota", reader.mapCharCodeToGlyphname(0x3b9, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3ba
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ba() throws Exception {

        assertEquals("kappa", reader.mapCharCodeToGlyphname(0x3ba, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3bb
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3bb() throws Exception {

        assertEquals("lambda", reader.mapCharCodeToGlyphname(0x3bb, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3bc
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3bc() throws Exception {

        assertEquals("mu", reader.mapCharCodeToGlyphname(0x3bc, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3bd
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3bd() throws Exception {

        assertEquals("nu", reader.mapCharCodeToGlyphname(0x3bd, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3be
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3be() throws Exception {

        assertEquals("xi", reader.mapCharCodeToGlyphname(0x3be, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3bf
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3bf() throws Exception {

        assertEquals("omicron", reader.mapCharCodeToGlyphname(0x3bf, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c0
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c0() throws Exception {

        assertEquals("pi1", reader.mapCharCodeToGlyphname(0x3c0, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c1
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c1() throws Exception {

        assertEquals("rho", reader.mapCharCodeToGlyphname(0x3c1, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c2
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c2() throws Exception {

        assertEquals("sigma1", reader.mapCharCodeToGlyphname(0x3c2, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c3
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c3() throws Exception {

        assertEquals("sigma", reader.mapCharCodeToGlyphname(0x3c3, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c4
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c4() throws Exception {

        assertEquals("tau", reader.mapCharCodeToGlyphname(0x3c4, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c5
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c5() throws Exception {

        assertEquals("upsilon", reader.mapCharCodeToGlyphname(0x3c5, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c6
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c6() throws Exception {

        assertEquals("phi", reader.mapCharCodeToGlyphname(0x3c6, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c7
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c7() throws Exception {

        assertEquals("chi", reader.mapCharCodeToGlyphname(0x3c7, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c8
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c8() throws Exception {

        assertEquals("psi", reader.mapCharCodeToGlyphname(0x3c8, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3c9
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3c9() throws Exception {

        assertEquals("omega", reader.mapCharCodeToGlyphname(0x3c9, (short) 3,
                (short) 1));
    }

    /**
     * test 0x3ca
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ca() throws Exception {

        assertEquals("iotadieresis", reader.mapCharCodeToGlyphname(0x3ca,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3cb
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3cb() throws Exception {

        assertEquals("upsilondieresis", reader.mapCharCodeToGlyphname(0x3cb,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3cc
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3cc() throws Exception {

        assertEquals("omicrontonos", reader.mapCharCodeToGlyphname(0x3cc,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3cd
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3cd() throws Exception {

        assertEquals("upsilontonos", reader.mapCharCodeToGlyphname(0x3cd,
                (short) 3, (short) 1));
    }

    /**
     * test 0x3ce
     *
     * @throws Exception if an error occurred.
     */
    public void test0x3ce() throws Exception {

        assertEquals("omegatonos", reader.mapCharCodeToGlyphname(0x3ce,
                (short) 3, (short) 1));
    }

    /**
     * test 0x401
     *
     * @throws Exception if an error occurred.
     */
    public void test0x401() throws Exception {

        assertEquals("afii10023", reader.mapCharCodeToGlyphname(0x401,
                (short) 3, (short) 1));
    }

    /**
     * test 0x402
     *
     * @throws Exception if an error occurred.
     */
    public void test0x402() throws Exception {

        assertEquals("afii10051", reader.mapCharCodeToGlyphname(0x402,
                (short) 3, (short) 1));
    }

    /**
     * test 0x403
     *
     * @throws Exception if an error occurred.
     */
    public void test0x403() throws Exception {

        assertEquals("afii10052", reader.mapCharCodeToGlyphname(0x403,
                (short) 3, (short) 1));
    }

    /**
     * test 0x404
     *
     * @throws Exception if an error occurred.
     */
    public void test0x404() throws Exception {

        assertEquals("afii10053", reader.mapCharCodeToGlyphname(0x404,
                (short) 3, (short) 1));
    }

    /**
     * test 0x405
     *
     * @throws Exception if an error occurred.
     */
    public void test0x405() throws Exception {

        assertEquals("afii10054", reader.mapCharCodeToGlyphname(0x405,
                (short) 3, (short) 1));
    }

    /**
     * test 0x406
     *
     * @throws Exception if an error occurred.
     */
    public void test0x406() throws Exception {

        assertEquals("afii10055", reader.mapCharCodeToGlyphname(0x406,
                (short) 3, (short) 1));
    }

    /**
     * test 0x407
     *
     * @throws Exception if an error occurred.
     */
    public void test0x407() throws Exception {

        assertEquals("afii10056", reader.mapCharCodeToGlyphname(0x407,
                (short) 3, (short) 1));
    }

    /**
     * test 0x408
     *
     * @throws Exception if an error occurred.
     */
    public void test0x408() throws Exception {

        assertEquals("afii10057", reader.mapCharCodeToGlyphname(0x408,
                (short) 3, (short) 1));
    }

    /**
     * test 0x409
     *
     * @throws Exception if an error occurred.
     */
    public void test0x409() throws Exception {

        assertEquals("afii10058", reader.mapCharCodeToGlyphname(0x409,
                (short) 3, (short) 1));
    }

    /**
     * test 0x40a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x40a() throws Exception {

        assertEquals("afii10059", reader.mapCharCodeToGlyphname(0x40a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x40b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x40b() throws Exception {

        assertEquals("afii10060", reader.mapCharCodeToGlyphname(0x40b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x40c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x40c() throws Exception {

        assertEquals("afii10061", reader.mapCharCodeToGlyphname(0x40c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x40e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x40e() throws Exception {

        assertEquals("afii10062", reader.mapCharCodeToGlyphname(0x40e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x40f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x40f() throws Exception {

        assertEquals("afii10145", reader.mapCharCodeToGlyphname(0x40f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x410
     *
     * @throws Exception if an error occurred.
     */
    public void test0x410() throws Exception {

        assertEquals("afii10017", reader.mapCharCodeToGlyphname(0x410,
                (short) 3, (short) 1));
    }

    /**
     * test 0x411
     *
     * @throws Exception if an error occurred.
     */
    public void test0x411() throws Exception {

        assertEquals("afii10018", reader.mapCharCodeToGlyphname(0x411,
                (short) 3, (short) 1));
    }

    /**
     * test 0x412
     *
     * @throws Exception if an error occurred.
     */
    public void test0x412() throws Exception {

        assertEquals("afii10019", reader.mapCharCodeToGlyphname(0x412,
                (short) 3, (short) 1));
    }

    /**
     * test 0x413
     *
     * @throws Exception if an error occurred.
     */
    public void test0x413() throws Exception {

        assertEquals("afii10020", reader.mapCharCodeToGlyphname(0x413,
                (short) 3, (short) 1));
    }

    /**
     * test 0x414
     *
     * @throws Exception if an error occurred.
     */
    public void test0x414() throws Exception {

        assertEquals("afii10021", reader.mapCharCodeToGlyphname(0x414,
                (short) 3, (short) 1));
    }

    /**
     * test 0x415
     *
     * @throws Exception if an error occurred.
     */
    public void test0x415() throws Exception {

        assertEquals("afii10022", reader.mapCharCodeToGlyphname(0x415,
                (short) 3, (short) 1));
    }

    /**
     * test 0x416
     *
     * @throws Exception if an error occurred.
     */
    public void test0x416() throws Exception {

        assertEquals("afii10024", reader.mapCharCodeToGlyphname(0x416,
                (short) 3, (short) 1));
    }

    /**
     * test 0x417
     *
     * @throws Exception if an error occurred.
     */
    public void test0x417() throws Exception {

        assertEquals("afii10025", reader.mapCharCodeToGlyphname(0x417,
                (short) 3, (short) 1));
    }

    /**
     * test 0x418
     *
     * @throws Exception if an error occurred.
     */
    public void test0x418() throws Exception {

        assertEquals("afii10026", reader.mapCharCodeToGlyphname(0x418,
                (short) 3, (short) 1));
    }

    /**
     * test 0x419
     *
     * @throws Exception if an error occurred.
     */
    public void test0x419() throws Exception {

        assertEquals("afii10027", reader.mapCharCodeToGlyphname(0x419,
                (short) 3, (short) 1));
    }

    /**
     * test 0x41a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41a() throws Exception {

        assertEquals("afii10028", reader.mapCharCodeToGlyphname(0x41a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x41b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41b() throws Exception {

        assertEquals("afii10029", reader.mapCharCodeToGlyphname(0x41b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x41c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41c() throws Exception {

        assertEquals("afii10030", reader.mapCharCodeToGlyphname(0x41c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x41d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41d() throws Exception {

        assertEquals("afii10031", reader.mapCharCodeToGlyphname(0x41d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x41e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41e() throws Exception {

        assertEquals("afii10032", reader.mapCharCodeToGlyphname(0x41e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x41f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x41f() throws Exception {

        assertEquals("afii10033", reader.mapCharCodeToGlyphname(0x41f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x420
     *
     * @throws Exception if an error occurred.
     */
    public void test0x420() throws Exception {

        assertEquals("afii10034", reader.mapCharCodeToGlyphname(0x420,
                (short) 3, (short) 1));
    }

    /**
     * test 0x421
     *
     * @throws Exception if an error occurred.
     */
    public void test0x421() throws Exception {

        assertEquals("afii10035", reader.mapCharCodeToGlyphname(0x421,
                (short) 3, (short) 1));
    }

    /**
     * test 0x422
     *
     * @throws Exception if an error occurred.
     */
    public void test0x422() throws Exception {

        assertEquals("afii10036", reader.mapCharCodeToGlyphname(0x422,
                (short) 3, (short) 1));
    }

    /**
     * test 0x423
     *
     * @throws Exception if an error occurred.
     */
    public void test0x423() throws Exception {

        assertEquals("afii10037", reader.mapCharCodeToGlyphname(0x423,
                (short) 3, (short) 1));
    }

    /**
     * test 0x424
     *
     * @throws Exception if an error occurred.
     */
    public void test0x424() throws Exception {

        assertEquals("afii10038", reader.mapCharCodeToGlyphname(0x424,
                (short) 3, (short) 1));
    }

    /**
     * test 0x425
     *
     * @throws Exception if an error occurred.
     */
    public void test0x425() throws Exception {

        assertEquals("afii10039", reader.mapCharCodeToGlyphname(0x425,
                (short) 3, (short) 1));
    }

    /**
     * test 0x426
     *
     * @throws Exception if an error occurred.
     */
    public void test0x426() throws Exception {

        assertEquals("afii10040", reader.mapCharCodeToGlyphname(0x426,
                (short) 3, (short) 1));
    }

    /**
     * test 0x427
     *
     * @throws Exception if an error occurred.
     */
    public void test0x427() throws Exception {

        assertEquals("afii10041", reader.mapCharCodeToGlyphname(0x427,
                (short) 3, (short) 1));
    }

    /**
     * test 0x428
     *
     * @throws Exception if an error occurred.
     */
    public void test0x428() throws Exception {

        assertEquals("afii10042", reader.mapCharCodeToGlyphname(0x428,
                (short) 3, (short) 1));
    }

    /**
     * test 0x429
     *
     * @throws Exception if an error occurred.
     */
    public void test0x429() throws Exception {

        assertEquals("afii10043", reader.mapCharCodeToGlyphname(0x429,
                (short) 3, (short) 1));
    }

    /**
     * test 0x42a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42a() throws Exception {

        assertEquals("afii10044", reader.mapCharCodeToGlyphname(0x42a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x42b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42b() throws Exception {

        assertEquals("afii10045", reader.mapCharCodeToGlyphname(0x42b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x42c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42c() throws Exception {

        assertEquals("afii10046", reader.mapCharCodeToGlyphname(0x42c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x42d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42d() throws Exception {

        assertEquals("afii10047", reader.mapCharCodeToGlyphname(0x42d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x42e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42e() throws Exception {

        assertEquals("afii10048", reader.mapCharCodeToGlyphname(0x42e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x42f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x42f() throws Exception {

        assertEquals("afii10049", reader.mapCharCodeToGlyphname(0x42f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x430
     *
     * @throws Exception if an error occurred.
     */
    public void test0x430() throws Exception {

        assertEquals("afii10065", reader.mapCharCodeToGlyphname(0x430,
                (short) 3, (short) 1));
    }

    /**
     * test 0x431
     *
     * @throws Exception if an error occurred.
     */
    public void test0x431() throws Exception {

        assertEquals("afii10066", reader.mapCharCodeToGlyphname(0x431,
                (short) 3, (short) 1));
    }

    /**
     * test 0x432
     *
     * @throws Exception if an error occurred.
     */
    public void test0x432() throws Exception {

        assertEquals("afii10067", reader.mapCharCodeToGlyphname(0x432,
                (short) 3, (short) 1));
    }

    /**
     * test 0x433
     *
     * @throws Exception if an error occurred.
     */
    public void test0x433() throws Exception {

        assertEquals("afii10068", reader.mapCharCodeToGlyphname(0x433,
                (short) 3, (short) 1));
    }

    /**
     * test 0x434
     *
     * @throws Exception if an error occurred.
     */
    public void test0x434() throws Exception {

        assertEquals("afii10069", reader.mapCharCodeToGlyphname(0x434,
                (short) 3, (short) 1));
    }

    /**
     * test 0x435
     *
     * @throws Exception if an error occurred.
     */
    public void test0x435() throws Exception {

        assertEquals("afii10070", reader.mapCharCodeToGlyphname(0x435,
                (short) 3, (short) 1));
    }

    /**
     * test 0x436
     *
     * @throws Exception if an error occurred.
     */
    public void test0x436() throws Exception {

        assertEquals("afii10072", reader.mapCharCodeToGlyphname(0x436,
                (short) 3, (short) 1));
    }

    /**
     * test 0x437
     *
     * @throws Exception if an error occurred.
     */
    public void test0x437() throws Exception {

        assertEquals("afii10073", reader.mapCharCodeToGlyphname(0x437,
                (short) 3, (short) 1));
    }

    /**
     * test 0x438
     *
     * @throws Exception if an error occurred.
     */
    public void test0x438() throws Exception {

        assertEquals("afii10074", reader.mapCharCodeToGlyphname(0x438,
                (short) 3, (short) 1));
    }

    /**
     * test 0x439
     *
     * @throws Exception if an error occurred.
     */
    public void test0x439() throws Exception {

        assertEquals("afii10075", reader.mapCharCodeToGlyphname(0x439,
                (short) 3, (short) 1));
    }

    /**
     * test 0x43a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43a() throws Exception {

        assertEquals("afii10076", reader.mapCharCodeToGlyphname(0x43a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x43b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43b() throws Exception {

        assertEquals("afii10077", reader.mapCharCodeToGlyphname(0x43b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x43c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43c() throws Exception {

        assertEquals("afii10078", reader.mapCharCodeToGlyphname(0x43c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x43d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43d() throws Exception {

        assertEquals("afii10079", reader.mapCharCodeToGlyphname(0x43d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x43e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43e() throws Exception {

        assertEquals("afii10080", reader.mapCharCodeToGlyphname(0x43e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x43f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x43f() throws Exception {

        assertEquals("afii10081", reader.mapCharCodeToGlyphname(0x43f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x440
     *
     * @throws Exception if an error occurred.
     */
    public void test0x440() throws Exception {

        assertEquals("afii10082", reader.mapCharCodeToGlyphname(0x440,
                (short) 3, (short) 1));
    }

    /**
     * test 0x441
     *
     * @throws Exception if an error occurred.
     */
    public void test0x441() throws Exception {

        assertEquals("afii10083", reader.mapCharCodeToGlyphname(0x441,
                (short) 3, (short) 1));
    }

    /**
     * test 0x442
     *
     * @throws Exception if an error occurred.
     */
    public void test0x442() throws Exception {

        assertEquals("afii10084", reader.mapCharCodeToGlyphname(0x442,
                (short) 3, (short) 1));
    }

    /**
     * test 0x443
     *
     * @throws Exception if an error occurred.
     */
    public void test0x443() throws Exception {

        assertEquals("afii10085", reader.mapCharCodeToGlyphname(0x443,
                (short) 3, (short) 1));
    }

    /**
     * test 0x444
     *
     * @throws Exception if an error occurred.
     */
    public void test0x444() throws Exception {

        assertEquals("afii10086", reader.mapCharCodeToGlyphname(0x444,
                (short) 3, (short) 1));
    }

    /**
     * test 0x445
     *
     * @throws Exception if an error occurred.
     */
    public void test0x445() throws Exception {

        assertEquals("afii10087", reader.mapCharCodeToGlyphname(0x445,
                (short) 3, (short) 1));
    }

    /**
     * test 0x446
     *
     * @throws Exception if an error occurred.
     */
    public void test0x446() throws Exception {

        assertEquals("afii10088", reader.mapCharCodeToGlyphname(0x446,
                (short) 3, (short) 1));
    }

    /**
     * test 0x447
     *
     * @throws Exception if an error occurred.
     */
    public void test0x447() throws Exception {

        assertEquals("afii10089", reader.mapCharCodeToGlyphname(0x447,
                (short) 3, (short) 1));
    }

    /**
     * test 0x448
     *
     * @throws Exception if an error occurred.
     */
    public void test0x448() throws Exception {

        assertEquals("afii10090", reader.mapCharCodeToGlyphname(0x448,
                (short) 3, (short) 1));
    }

    /**
     * test 0x449
     *
     * @throws Exception if an error occurred.
     */
    public void test0x449() throws Exception {

        assertEquals("afii10091", reader.mapCharCodeToGlyphname(0x449,
                (short) 3, (short) 1));
    }

    /**
     * test 0x44a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44a() throws Exception {

        assertEquals("afii10092", reader.mapCharCodeToGlyphname(0x44a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x44b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44b() throws Exception {

        assertEquals("afii10093", reader.mapCharCodeToGlyphname(0x44b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x44c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44c() throws Exception {

        assertEquals("afii10094", reader.mapCharCodeToGlyphname(0x44c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x44d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44d() throws Exception {

        assertEquals("afii10095", reader.mapCharCodeToGlyphname(0x44d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x44e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44e() throws Exception {

        assertEquals("afii10096", reader.mapCharCodeToGlyphname(0x44e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x44f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x44f() throws Exception {

        assertEquals("afii10097", reader.mapCharCodeToGlyphname(0x44f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x451
     *
     * @throws Exception if an error occurred.
     */
    public void test0x451() throws Exception {

        assertEquals("afii10071", reader.mapCharCodeToGlyphname(0x451,
                (short) 3, (short) 1));
    }

    /**
     * test 0x452
     *
     * @throws Exception if an error occurred.
     */
    public void test0x452() throws Exception {

        assertEquals("afii10099", reader.mapCharCodeToGlyphname(0x452,
                (short) 3, (short) 1));
    }

    /**
     * test 0x453
     *
     * @throws Exception if an error occurred.
     */
    public void test0x453() throws Exception {

        assertEquals("afii10100", reader.mapCharCodeToGlyphname(0x453,
                (short) 3, (short) 1));
    }

    /**
     * test 0x454
     *
     * @throws Exception if an error occurred.
     */
    public void test0x454() throws Exception {

        assertEquals("afii10101", reader.mapCharCodeToGlyphname(0x454,
                (short) 3, (short) 1));
    }

    /**
     * test 0x455
     *
     * @throws Exception if an error occurred.
     */
    public void test0x455() throws Exception {

        assertEquals("afii10102", reader.mapCharCodeToGlyphname(0x455,
                (short) 3, (short) 1));
    }

    /**
     * test 0x456
     *
     * @throws Exception if an error occurred.
     */
    public void test0x456() throws Exception {

        assertEquals("afii10103", reader.mapCharCodeToGlyphname(0x456,
                (short) 3, (short) 1));
    }

    /**
     * test 0x457
     *
     * @throws Exception if an error occurred.
     */
    public void test0x457() throws Exception {

        assertEquals("afii10104", reader.mapCharCodeToGlyphname(0x457,
                (short) 3, (short) 1));
    }

    /**
     * test 0x458
     *
     * @throws Exception if an error occurred.
     */
    public void test0x458() throws Exception {

        assertEquals("afii10105", reader.mapCharCodeToGlyphname(0x458,
                (short) 3, (short) 1));
    }

    /**
     * test 0x459
     *
     * @throws Exception if an error occurred.
     */
    public void test0x459() throws Exception {

        assertEquals("afii10106", reader.mapCharCodeToGlyphname(0x459,
                (short) 3, (short) 1));
    }

    /**
     * test 0x45a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x45a() throws Exception {

        assertEquals("afii10107", reader.mapCharCodeToGlyphname(0x45a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x45b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x45b() throws Exception {

        assertEquals("afii10108", reader.mapCharCodeToGlyphname(0x45b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x45c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x45c() throws Exception {

        assertEquals("afii10109", reader.mapCharCodeToGlyphname(0x45c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x45e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x45e() throws Exception {

        assertEquals("afii10110", reader.mapCharCodeToGlyphname(0x45e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x45f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x45f() throws Exception {

        assertEquals("afii10193", reader.mapCharCodeToGlyphname(0x45f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x490
     *
     * @throws Exception if an error occurred.
     */
    public void test0x490() throws Exception {

        assertEquals("afii10050", reader.mapCharCodeToGlyphname(0x490,
                (short) 3, (short) 1));
    }

    /**
     * test 0x491
     *
     * @throws Exception if an error occurred.
     */
    public void test0x491() throws Exception {

        assertEquals("afii10098", reader.mapCharCodeToGlyphname(0x491,
                (short) 3, (short) 1));
    }

    /**
     * test 0x1e80
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1e80() throws Exception {

        assertEquals("Wgrave", reader.mapCharCodeToGlyphname(0x1e80, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1e81
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1e81() throws Exception {

        assertEquals("wgrave", reader.mapCharCodeToGlyphname(0x1e81, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1e82
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1e82() throws Exception {

        assertEquals("Wacute", reader.mapCharCodeToGlyphname(0x1e82, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1e83
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1e83() throws Exception {

        assertEquals("wacute", reader.mapCharCodeToGlyphname(0x1e83, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1e84
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1e84() throws Exception {

        assertEquals("Wdieresis", reader.mapCharCodeToGlyphname(0x1e84,
                (short) 3, (short) 1));
    }

    /**
     * test 0x1e85
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1e85() throws Exception {

        assertEquals("wdieresis", reader.mapCharCodeToGlyphname(0x1e85,
                (short) 3, (short) 1));
    }

    /**
     * test 0x1ef2
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1ef2() throws Exception {

        assertEquals("Ygrave", reader.mapCharCodeToGlyphname(0x1ef2, (short) 3,
                (short) 1));
    }

    /**
     * test 0x1ef3
     *
     * @throws Exception if an error occurred.
     */
    public void test0x1ef3() throws Exception {

        assertEquals("ygrave", reader.mapCharCodeToGlyphname(0x1ef3, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2013
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2013() throws Exception {

        assertEquals("endash", reader.mapCharCodeToGlyphname(0x2013, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2014
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2014() throws Exception {

        assertEquals("emdash", reader.mapCharCodeToGlyphname(0x2014, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2015
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2015() throws Exception {

        assertEquals("afii00208", reader.mapCharCodeToGlyphname(0x2015,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2017
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2017() throws Exception {

        assertEquals("underscoredbl", reader.mapCharCodeToGlyphname(0x2017,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2018
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2018() throws Exception {

        assertEquals("quoteleft", reader.mapCharCodeToGlyphname(0x2018,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2019
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2019() throws Exception {

        assertEquals("quoteright", reader.mapCharCodeToGlyphname(0x2019,
                (short) 3, (short) 1));
    }

    /**
     * test 0x201a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x201a() throws Exception {

        assertEquals("quotesinglbase", reader.mapCharCodeToGlyphname(0x201a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x201b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x201b() throws Exception {

        assertEquals("quotereversed", reader.mapCharCodeToGlyphname(0x201b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x201c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x201c() throws Exception {

        assertEquals("quotedblleft", reader.mapCharCodeToGlyphname(0x201c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x201d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x201d() throws Exception {

        assertEquals("quotedblright", reader.mapCharCodeToGlyphname(0x201d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x201e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x201e() throws Exception {

        assertEquals("quotedblbase", reader.mapCharCodeToGlyphname(0x201e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2020
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2020() throws Exception {

        assertEquals("dagger", reader.mapCharCodeToGlyphname(0x2020, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2021
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2021() throws Exception {

        assertEquals("daggerdbl", reader.mapCharCodeToGlyphname(0x2021,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2022
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2022() throws Exception {

        assertEquals("bullet", reader.mapCharCodeToGlyphname(0x2022, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2026
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2026() throws Exception {

        assertEquals("ellipsis", reader.mapCharCodeToGlyphname(0x2026,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2030
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2030() throws Exception {

        assertEquals("perthousand", reader.mapCharCodeToGlyphname(0x2030,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2032
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2032() throws Exception {

        assertEquals("minute", reader.mapCharCodeToGlyphname(0x2032, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2033
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2033() throws Exception {

        assertEquals("second", reader.mapCharCodeToGlyphname(0x2033, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2039
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2039() throws Exception {

        assertEquals("guilsinglleft", reader.mapCharCodeToGlyphname(0x2039,
                (short) 3, (short) 1));
    }

    /**
     * test 0x203a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x203a() throws Exception {

        assertEquals("guilsinglright", reader.mapCharCodeToGlyphname(0x203a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x203c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x203c() throws Exception {

        assertEquals("exclamdbl", reader.mapCharCodeToGlyphname(0x203c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x203e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x203e() throws Exception {

        assertEquals("radicalex", reader.mapCharCodeToGlyphname(0x203e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2044
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2044() throws Exception {

        assertEquals("fraction1", reader.mapCharCodeToGlyphname(0x2044,
                (short) 3, (short) 1));
    }

    /**
     * test 0x207f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x207f() throws Exception {

        assertEquals("nsuperior", reader.mapCharCodeToGlyphname(0x207f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x20a3
     *
     * @throws Exception if an error occurred.
     */
    public void test0x20a3() throws Exception {

        assertEquals("franc", reader.mapCharCodeToGlyphname(0x20a3, (short) 3,
                (short) 1));
    }

    /**
     * test 0x20a4
     *
     * @throws Exception if an error occurred.
     */
    public void test0x20a4() throws Exception {

        assertEquals("afii08941", reader.mapCharCodeToGlyphname(0x20a4,
                (short) 3, (short) 1));
    }

    /**
     * test 0x20a7
     *
     * @throws Exception if an error occurred.
     */
    public void test0x20a7() throws Exception {

        assertEquals("peseta", reader.mapCharCodeToGlyphname(0x20a7, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2105
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2105() throws Exception {

        assertEquals("afii61248", reader.mapCharCodeToGlyphname(0x2105,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2113
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2113() throws Exception {

        assertEquals("afii61289", reader.mapCharCodeToGlyphname(0x2113,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2116
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2116() throws Exception {

        assertEquals("afii61352", reader.mapCharCodeToGlyphname(0x2116,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2122
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2122() throws Exception {

        assertEquals("trademark", reader.mapCharCodeToGlyphname(0x2122,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2126
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2126() throws Exception {

        assertEquals("Ohm", reader.mapCharCodeToGlyphname(0x2126, (short) 3,
                (short) 1));
    }

    /**
     * test 0x212e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x212e() throws Exception {

        assertEquals("estimated", reader.mapCharCodeToGlyphname(0x212e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x215b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x215b() throws Exception {

        assertEquals("oneeighth", reader.mapCharCodeToGlyphname(0x215b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x215c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x215c() throws Exception {

        assertEquals("threeeighths", reader.mapCharCodeToGlyphname(0x215c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x215d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x215d() throws Exception {

        assertEquals("fiveeighths", reader.mapCharCodeToGlyphname(0x215d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x215e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x215e() throws Exception {

        assertEquals("seveneighths", reader.mapCharCodeToGlyphname(0x215e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2190
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2190() throws Exception {

        assertEquals("arrowleft", reader.mapCharCodeToGlyphname(0x2190,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2191
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2191() throws Exception {

        assertEquals("arrowup", reader.mapCharCodeToGlyphname(0x2191,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2192
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2192() throws Exception {

        assertEquals("arrowright", reader.mapCharCodeToGlyphname(0x2192,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2193
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2193() throws Exception {

        assertEquals("arrowdown", reader.mapCharCodeToGlyphname(0x2193,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2194
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2194() throws Exception {

        assertEquals("arrowboth", reader.mapCharCodeToGlyphname(0x2194,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2195
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2195() throws Exception {

        assertEquals("arrowupdn", reader.mapCharCodeToGlyphname(0x2195,
                (short) 3, (short) 1));
    }

    /**
     * test 0x21a8
     *
     * @throws Exception if an error occurred.
     */
    public void test0x21a8() throws Exception {

        assertEquals("arrowupdnbse", reader.mapCharCodeToGlyphname(0x21a8,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2202
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2202() throws Exception {

        assertEquals("partialdiff", reader.mapCharCodeToGlyphname(0x2202,
                (short) 3, (short) 1));
    }

    /**
     * test 0x220f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x220f() throws Exception {

        assertEquals("product", reader.mapCharCodeToGlyphname(0x220f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2211
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2211() throws Exception {

        assertEquals("summation", reader.mapCharCodeToGlyphname(0x2211,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2212
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2212() throws Exception {

        assertEquals("minus", reader.mapCharCodeToGlyphname(0x2212, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2215
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2215() throws Exception {

        assertEquals("fraction", reader.mapCharCodeToGlyphname(0x2215,
                (short) 3, (short) 1));
    }

    /**
     * test 0x221a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x221a() throws Exception {

        assertEquals("radical", reader.mapCharCodeToGlyphname(0x221a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x221e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x221e() throws Exception {

        assertEquals("infinity", reader.mapCharCodeToGlyphname(0x221e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x221f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x221f() throws Exception {

        assertEquals("orthogonal", reader.mapCharCodeToGlyphname(0x221f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2229
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2229() throws Exception {

        assertEquals("intersection", reader.mapCharCodeToGlyphname(0x2229,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2248
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2248() throws Exception {

        assertEquals("approxequal", reader.mapCharCodeToGlyphname(0x2248,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2260
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2260() throws Exception {

        assertEquals("notequal", reader.mapCharCodeToGlyphname(0x2260,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2261
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2261() throws Exception {

        assertEquals("equivalence", reader.mapCharCodeToGlyphname(0x2261,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2264
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2264() throws Exception {

        assertEquals("lessequal", reader.mapCharCodeToGlyphname(0x2264,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2265
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2265() throws Exception {

        assertEquals("greaterequal", reader.mapCharCodeToGlyphname(0x2265,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2302
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2302() throws Exception {

        assertEquals("house", reader.mapCharCodeToGlyphname(0x2302, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2310
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2310() throws Exception {

        assertEquals("revlogicalnot", reader.mapCharCodeToGlyphname(0x2310,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2320
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2320() throws Exception {

        assertEquals("integraltp", reader.mapCharCodeToGlyphname(0x2320,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2321
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2321() throws Exception {

        assertEquals("integralbt", reader.mapCharCodeToGlyphname(0x2321,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2500
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2500() throws Exception {

        assertEquals("SF100000", reader.mapCharCodeToGlyphname(0x2500,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2502
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2502() throws Exception {

        assertEquals("SF110000", reader.mapCharCodeToGlyphname(0x2502,
                (short) 3, (short) 1));
    }

    /**
     * test 0x250c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x250c() throws Exception {

        assertEquals("SF010000", reader.mapCharCodeToGlyphname(0x250c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2510
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2510() throws Exception {

        assertEquals("SF030000", reader.mapCharCodeToGlyphname(0x2510,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2514
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2514() throws Exception {

        assertEquals("SF020000", reader.mapCharCodeToGlyphname(0x2514,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2518
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2518() throws Exception {

        assertEquals("SF040000", reader.mapCharCodeToGlyphname(0x2518,
                (short) 3, (short) 1));
    }

    /**
     * test 0x251c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x251c() throws Exception {

        assertEquals("SF080000", reader.mapCharCodeToGlyphname(0x251c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2524
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2524() throws Exception {

        assertEquals("SF090000", reader.mapCharCodeToGlyphname(0x2524,
                (short) 3, (short) 1));
    }

    /**
     * test 0x252c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x252c() throws Exception {

        assertEquals("SF060000", reader.mapCharCodeToGlyphname(0x252c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2534
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2534() throws Exception {

        assertEquals("SF070000", reader.mapCharCodeToGlyphname(0x2534,
                (short) 3, (short) 1));
    }

    /**
     * test 0x253c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x253c() throws Exception {

        assertEquals("SF050000", reader.mapCharCodeToGlyphname(0x253c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2550
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2550() throws Exception {

        assertEquals("SF430000", reader.mapCharCodeToGlyphname(0x2550,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2551
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2551() throws Exception {

        assertEquals("SF240000", reader.mapCharCodeToGlyphname(0x2551,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2552
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2552() throws Exception {

        assertEquals("SF510000", reader.mapCharCodeToGlyphname(0x2552,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2553
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2553() throws Exception {

        assertEquals("SF520000", reader.mapCharCodeToGlyphname(0x2553,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2554
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2554() throws Exception {

        assertEquals("SF390000", reader.mapCharCodeToGlyphname(0x2554,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2555
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2555() throws Exception {

        assertEquals("SF220000", reader.mapCharCodeToGlyphname(0x2555,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2556
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2556() throws Exception {

        assertEquals("SF210000", reader.mapCharCodeToGlyphname(0x2556,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2557
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2557() throws Exception {

        assertEquals("SF250000", reader.mapCharCodeToGlyphname(0x2557,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2558
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2558() throws Exception {

        assertEquals("SF500000", reader.mapCharCodeToGlyphname(0x2558,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2559
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2559() throws Exception {

        assertEquals("SF490000", reader.mapCharCodeToGlyphname(0x2559,
                (short) 3, (short) 1));
    }

    /**
     * test 0x255a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x255a() throws Exception {

        assertEquals("SF380000", reader.mapCharCodeToGlyphname(0x255a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x255b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x255b() throws Exception {

        assertEquals("SF280000", reader.mapCharCodeToGlyphname(0x255b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x255c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x255c() throws Exception {

        assertEquals("SF270000", reader.mapCharCodeToGlyphname(0x255c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x255d
     *
     * @throws Exception if an error occurred.
     */
    public void test0x255d() throws Exception {

        assertEquals("SF260000", reader.mapCharCodeToGlyphname(0x255d,
                (short) 3, (short) 1));
    }

    /**
     * test 0x255e
     *
     * @throws Exception if an error occurred.
     */
    public void test0x255e() throws Exception {

        assertEquals("SF360000", reader.mapCharCodeToGlyphname(0x255e,
                (short) 3, (short) 1));
    }

    /**
     * test 0x255f
     *
     * @throws Exception if an error occurred.
     */
    public void test0x255f() throws Exception {

        assertEquals("SF370000", reader.mapCharCodeToGlyphname(0x255f,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2560
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2560() throws Exception {

        assertEquals("SF420000", reader.mapCharCodeToGlyphname(0x2560,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2561
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2561() throws Exception {

        assertEquals("SF190000", reader.mapCharCodeToGlyphname(0x2561,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2562
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2562() throws Exception {

        assertEquals("SF200000", reader.mapCharCodeToGlyphname(0x2562,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2563
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2563() throws Exception {

        assertEquals("SF230000", reader.mapCharCodeToGlyphname(0x2563,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2564
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2564() throws Exception {

        assertEquals("SF470000", reader.mapCharCodeToGlyphname(0x2564,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2565
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2565() throws Exception {

        assertEquals("SF480000", reader.mapCharCodeToGlyphname(0x2565,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2566
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2566() throws Exception {

        assertEquals("SF410000", reader.mapCharCodeToGlyphname(0x2566,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2567
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2567() throws Exception {

        assertEquals("SF450000", reader.mapCharCodeToGlyphname(0x2567,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2568
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2568() throws Exception {

        assertEquals("SF460000", reader.mapCharCodeToGlyphname(0x2568,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2569
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2569() throws Exception {

        assertEquals("SF400000", reader.mapCharCodeToGlyphname(0x2569,
                (short) 3, (short) 1));
    }

    /**
     * test 0x256a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x256a() throws Exception {

        assertEquals("SF540000", reader.mapCharCodeToGlyphname(0x256a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x256b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x256b() throws Exception {

        assertEquals("SF530000", reader.mapCharCodeToGlyphname(0x256b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x256c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x256c() throws Exception {

        assertEquals("SF440000", reader.mapCharCodeToGlyphname(0x256c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2580
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2580() throws Exception {

        assertEquals("upblock", reader.mapCharCodeToGlyphname(0x2580,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2584
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2584() throws Exception {

        assertEquals("dnblock", reader.mapCharCodeToGlyphname(0x2584,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2588
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2588() throws Exception {

        assertEquals("block", reader.mapCharCodeToGlyphname(0x2588, (short) 3,
                (short) 1));
    }

    /**
     * test 0x258c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x258c() throws Exception {

        assertEquals("lfblock", reader.mapCharCodeToGlyphname(0x258c,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2590
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2590() throws Exception {

        assertEquals("rtblock", reader.mapCharCodeToGlyphname(0x2590,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2591
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2591() throws Exception {

        assertEquals("ltshade", reader.mapCharCodeToGlyphname(0x2591,
                (short) 3, (short) 1));
    }

    /**
     * test 0x2592
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2592() throws Exception {

        assertEquals("shade", reader.mapCharCodeToGlyphname(0x2592, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2593
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2593() throws Exception {

        assertEquals("dkshade", reader.mapCharCodeToGlyphname(0x2593,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25a0
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25a0() throws Exception {

        assertEquals("filledbox", reader.mapCharCodeToGlyphname(0x25a0,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25a1
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25a1() throws Exception {

        assertEquals("H22073", reader.mapCharCodeToGlyphname(0x25a1, (short) 3,
                (short) 1));
    }

    /**
     * test 0x25aa
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25aa() throws Exception {

        assertEquals("H18543", reader.mapCharCodeToGlyphname(0x25aa, (short) 3,
                (short) 1));
    }

    /**
     * test 0x25ab
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25ab() throws Exception {

        assertEquals("H18551", reader.mapCharCodeToGlyphname(0x25ab, (short) 3,
                (short) 1));
    }

    /**
     * test 0x25ac
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25ac() throws Exception {

        assertEquals("filledrect", reader.mapCharCodeToGlyphname(0x25ac,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25b2
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25b2() throws Exception {

        assertEquals("triagup", reader.mapCharCodeToGlyphname(0x25b2,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25ba
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25ba() throws Exception {

        assertEquals("triagrt", reader.mapCharCodeToGlyphname(0x25ba,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25bc
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25bc() throws Exception {

        assertEquals("triagdn", reader.mapCharCodeToGlyphname(0x25bc,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25c4
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25c4() throws Exception {

        assertEquals("triaglf", reader.mapCharCodeToGlyphname(0x25c4,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25ca
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25ca() throws Exception {

        assertEquals("lozenge", reader.mapCharCodeToGlyphname(0x25ca,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25cb
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25cb() throws Exception {

        assertEquals("circle", reader.mapCharCodeToGlyphname(0x25cb, (short) 3,
                (short) 1));
    }

    /**
     * test 0x25cf
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25cf() throws Exception {

        assertEquals("H18533", reader.mapCharCodeToGlyphname(0x25cf, (short) 3,
                (short) 1));
    }

    /**
     * test 0x25d8
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25d8() throws Exception {

        assertEquals("invbullet", reader.mapCharCodeToGlyphname(0x25d8,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25d9
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25d9() throws Exception {

        assertEquals("invcircle", reader.mapCharCodeToGlyphname(0x25d9,
                (short) 3, (short) 1));
    }

    /**
     * test 0x25e6
     *
     * @throws Exception if an error occurred.
     */
    public void test0x25e6() throws Exception {

        assertEquals("openbullet", reader.mapCharCodeToGlyphname(0x25e6,
                (short) 3, (short) 1));
    }

    /**
     * test 0x263a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x263a() throws Exception {

        assertEquals("smileface", reader.mapCharCodeToGlyphname(0x263a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x263b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x263b() throws Exception {

        assertEquals("invsmileface", reader.mapCharCodeToGlyphname(0x263b,
                (short) 3, (short) 1));
    }

    /**
     * test 0x263c
     *
     * @throws Exception if an error occurred.
     */
    public void test0x263c() throws Exception {

        assertEquals("sun", reader.mapCharCodeToGlyphname(0x263c, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2640
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2640() throws Exception {

        assertEquals("female", reader.mapCharCodeToGlyphname(0x2640, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2642
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2642() throws Exception {

        assertEquals("male", reader.mapCharCodeToGlyphname(0x2642, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2660
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2660() throws Exception {

        assertEquals("spade", reader.mapCharCodeToGlyphname(0x2660, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2663
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2663() throws Exception {

        assertEquals("club", reader.mapCharCodeToGlyphname(0x2663, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2665
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2665() throws Exception {

        assertEquals("heart", reader.mapCharCodeToGlyphname(0x2665, (short) 3,
                (short) 1));
    }

    /**
     * test 0x2666
     *
     * @throws Exception if an error occurred.
     */
    public void test0x2666() throws Exception {

        assertEquals("diamond", reader.mapCharCodeToGlyphname(0x2666,
                (short) 3, (short) 1));
    }

    /**
     * test 0x266a
     *
     * @throws Exception if an error occurred.
     */
    public void test0x266a() throws Exception {

        assertEquals("musicalnote", reader.mapCharCodeToGlyphname(0x266a,
                (short) 3, (short) 1));
    }

    /**
     * test 0x266b
     *
     * @throws Exception if an error occurred.
     */
    public void test0x266b() throws Exception {

        assertEquals("musicalnotedbl", reader.mapCharCodeToGlyphname(0x266b,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf001
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf001() throws Exception {

        assertEquals("fi1", reader.mapCharCodeToGlyphname(0xf001, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf002
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf002() throws Exception {

        assertEquals("fl1", reader.mapCharCodeToGlyphname(0xf002, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf003
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf003() throws Exception {

        assertEquals("foursuperior", reader.mapCharCodeToGlyphname(0xf003,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf004
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf004() throws Exception {

        assertEquals("commaaccent", reader.mapCharCodeToGlyphname(0xf004,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf005
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf005() throws Exception {

        assertEquals("undercommaaccent", reader.mapCharCodeToGlyphname(0xf005,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf006
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf006() throws Exception {

        assertEquals("pi", reader.mapCharCodeToGlyphname(0xf006, (short) 3,
                (short) 1));
    }

    /**
     * test 0xf007
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf007() throws Exception {

        assertEquals("fivesuperior", reader.mapCharCodeToGlyphname(0xf007,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf008
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf008() throws Exception {

        assertEquals("sevensuperior", reader.mapCharCodeToGlyphname(0xf008,
                (short) 3, (short) 1));
    }

    /**
     * test 0xf009
     *
     * @throws Exception if an error occurred.
     */
    public void test0xf009() throws Exception {

        assertEquals("eightsuperior", reader.mapCharCodeToGlyphname(0xf009,
                (short) 3, (short) 1));
    }

    /**
     * test 0xfb01
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfb01() throws Exception {

        assertEquals("fi", reader.mapCharCodeToGlyphname(0xfb01, (short) 3,
                (short) 1));
    }

    /**
     * test 0xfb02
     *
     * @throws Exception if an error occurred.
     */
    public void test0xfb02() throws Exception {

        assertEquals("fl", reader.mapCharCodeToGlyphname(0xfb02, (short) 3,
                (short) 1));
    }

}
