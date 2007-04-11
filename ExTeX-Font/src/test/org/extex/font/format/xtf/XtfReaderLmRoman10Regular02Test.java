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
public class XtfReaderLmRoman10Regular02Test extends TestCase {

    /**
     * The xtf reader.
     */
    private static XtfReader reader;

    /**
     * Creates a new object.
     * 
     * @throws IOException if an error occurred.
     */
    public XtfReaderLmRoman10Regular02Test() throws IOException {

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

        assertEquals("uni00A0", reader.mapCharCodeToGlyphname(0xa0, (short) 3,
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

        assertEquals("uni00AD", reader.mapCharCodeToGlyphname(0xad, (short) 3,
            (short) 1));
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

        assertEquals("macron", reader.mapCharCodeToGlyphname(0xaf, (short) 3,
            (short) 1));
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

        assertEquals("two.superior", reader.mapCharCodeToGlyphname(0xb2,
            (short) 3, (short) 1));
    }

    /**
     * test 0xb3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xb3() throws Exception {

        assertEquals("three.superior", reader.mapCharCodeToGlyphname(0xb3,
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

        assertEquals("mu", reader.mapCharCodeToGlyphname(0xb5, (short) 3,
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

        assertEquals("periodcentered", reader.mapCharCodeToGlyphname(0xb7,
            (short) 3, (short) 1));
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

        assertEquals("one.superior", reader.mapCharCodeToGlyphname(0xb9,
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

        assertEquals("Cdotaccent", reader.mapCharCodeToGlyphname(0x10a,
            (short) 3, (short) 1));
    }

    /**
     * test 0x10b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x10b() throws Exception {

        assertEquals("cdotaccent", reader.mapCharCodeToGlyphname(0x10b,
            (short) 3, (short) 1));
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

        assertEquals("Dcroat", reader.mapCharCodeToGlyphname(0x110, (short) 3,
            (short) 1));
    }

    /**
     * test 0x111
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x111() throws Exception {

        assertEquals("dcroat", reader.mapCharCodeToGlyphname(0x111, (short) 3,
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

        assertEquals("Edotaccent", reader.mapCharCodeToGlyphname(0x116,
            (short) 3, (short) 1));
    }

    /**
     * test 0x117
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x117() throws Exception {

        assertEquals("edotaccent", reader.mapCharCodeToGlyphname(0x117,
            (short) 3, (short) 1));
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

        assertEquals("Gdotaccent", reader.mapCharCodeToGlyphname(0x120,
            (short) 3, (short) 1));
    }

    /**
     * test 0x121
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x121() throws Exception {

        assertEquals("gdotaccent", reader.mapCharCodeToGlyphname(0x121,
            (short) 3, (short) 1));
    }

    /**
     * test 0x122
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x122() throws Exception {

        assertEquals("Gcommaaccent", reader.mapCharCodeToGlyphname(0x122,
            (short) 3, (short) 1));
    }

    /**
     * test 0x123
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x123() throws Exception {

        assertEquals("gcommaaccent", reader.mapCharCodeToGlyphname(0x123,
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

        assertEquals("Idotaccent", reader.mapCharCodeToGlyphname(0x130,
            (short) 3, (short) 1));
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

        assertEquals("I_J", reader.mapCharCodeToGlyphname(0x132, (short) 3,
            (short) 1));
    }

    /**
     * test 0x133
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x133() throws Exception {

        assertEquals("i_j", reader.mapCharCodeToGlyphname(0x133, (short) 3,
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

        assertEquals("Kcommaaccent", reader.mapCharCodeToGlyphname(0x136,
            (short) 3, (short) 1));
    }

    /**
     * test 0x137
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x137() throws Exception {

        assertEquals("kcommaaccent", reader.mapCharCodeToGlyphname(0x137,
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

        assertEquals("Lcommaaccent", reader.mapCharCodeToGlyphname(0x13b,
            (short) 3, (short) 1));
    }

    /**
     * test 0x13c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x13c() throws Exception {

        assertEquals("lcommaaccent", reader.mapCharCodeToGlyphname(0x13c,
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

        assertEquals("Ncommaaccent", reader.mapCharCodeToGlyphname(0x145,
            (short) 3, (short) 1));
    }

    /**
     * test 0x146
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x146() throws Exception {

        assertEquals("ncommaaccent", reader.mapCharCodeToGlyphname(0x146,
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

        assertEquals("Ohungarumlaut", reader.mapCharCodeToGlyphname(0x150,
            (short) 3, (short) 1));
    }

    /**
     * test 0x151
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x151() throws Exception {

        assertEquals("ohungarumlaut", reader.mapCharCodeToGlyphname(0x151,
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

        assertEquals("Rcommaaccent", reader.mapCharCodeToGlyphname(0x156,
            (short) 3, (short) 1));
    }

    /**
     * test 0x157
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x157() throws Exception {

        assertEquals("rcommaaccent", reader.mapCharCodeToGlyphname(0x157,
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

        assertEquals("Uhungarumlaut", reader.mapCharCodeToGlyphname(0x170,
            (short) 3, (short) 1));
    }

    /**
     * test 0x171
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x171() throws Exception {

        assertEquals("uhungarumlaut", reader.mapCharCodeToGlyphname(0x171,
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

        assertEquals("Zdotaccent", reader.mapCharCodeToGlyphname(0x17b,
            (short) 3, (short) 1));
    }

    /**
     * test 0x17c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x17c() throws Exception {

        assertEquals("zdotaccent", reader.mapCharCodeToGlyphname(0x17c,
            (short) 3, (short) 1));
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
     * test 0x1a0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1a0() throws Exception {

        assertEquals("Ohorn", reader.mapCharCodeToGlyphname(0x1a0, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1a1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1a1() throws Exception {

        assertEquals("ohorn", reader.mapCharCodeToGlyphname(0x1a1, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1af
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1af() throws Exception {

        assertEquals("Uhorn", reader.mapCharCodeToGlyphname(0x1af, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1b0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1b0() throws Exception {

        assertEquals("uhorn", reader.mapCharCodeToGlyphname(0x1b0, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1e6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e6() throws Exception {

        assertEquals("Gcaron", reader.mapCharCodeToGlyphname(0x1e6, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1e7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e7() throws Exception {

        assertEquals("gcaron", reader.mapCharCodeToGlyphname(0x1e7, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1ea
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea() throws Exception {

        assertEquals("Oogonek", reader.mapCharCodeToGlyphname(0x1ea, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1eb
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb() throws Exception {

        assertEquals("oogonek", reader.mapCharCodeToGlyphname(0x1eb, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1f4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1f4() throws Exception {

        assertEquals("Gacute", reader.mapCharCodeToGlyphname(0x1f4, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1f5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1f5() throws Exception {

        assertEquals("gacute", reader.mapCharCodeToGlyphname(0x1f5, (short) 3,
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
     * test 0x200
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x200() throws Exception {

        assertEquals("Adblgrave", reader.mapCharCodeToGlyphname(0x200,
            (short) 3, (short) 1));
    }

    /**
     * test 0x201
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x201() throws Exception {

        assertEquals("adblgrave", reader.mapCharCodeToGlyphname(0x201,
            (short) 3, (short) 1));
    }

    /**
     * test 0x204
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x204() throws Exception {

        assertEquals("Edblgrave", reader.mapCharCodeToGlyphname(0x204,
            (short) 3, (short) 1));
    }

    /**
     * test 0x205
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x205() throws Exception {

        assertEquals("edblgrave", reader.mapCharCodeToGlyphname(0x205,
            (short) 3, (short) 1));
    }

    /**
     * test 0x208
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x208() throws Exception {

        assertEquals("Idblgrave", reader.mapCharCodeToGlyphname(0x208,
            (short) 3, (short) 1));
    }

    /**
     * test 0x209
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x209() throws Exception {

        assertEquals("idblgrave", reader.mapCharCodeToGlyphname(0x209,
            (short) 3, (short) 1));
    }

    /**
     * test 0x20c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20c() throws Exception {

        assertEquals("Odblgrave", reader.mapCharCodeToGlyphname(0x20c,
            (short) 3, (short) 1));
    }

    /**
     * test 0x20d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20d() throws Exception {

        assertEquals("odblgrave", reader.mapCharCodeToGlyphname(0x20d,
            (short) 3, (short) 1));
    }

    /**
     * test 0x210
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x210() throws Exception {

        assertEquals("Rdblgrave", reader.mapCharCodeToGlyphname(0x210,
            (short) 3, (short) 1));
    }

    /**
     * test 0x211
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x211() throws Exception {

        assertEquals("rdblgrave", reader.mapCharCodeToGlyphname(0x211,
            (short) 3, (short) 1));
    }

    /**
     * test 0x214
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x214() throws Exception {

        assertEquals("Udblgrave", reader.mapCharCodeToGlyphname(0x214,
            (short) 3, (short) 1));
    }

    /**
     * test 0x215
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x215() throws Exception {

        assertEquals("udblgrave", reader.mapCharCodeToGlyphname(0x215,
            (short) 3, (short) 1));
    }

    /**
     * test 0x218
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x218() throws Exception {

        assertEquals("uni0218", reader.mapCharCodeToGlyphname(0x218, (short) 3,
            (short) 1));
    }

    /**
     * test 0x219
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x219() throws Exception {

        assertEquals("uni0219", reader.mapCharCodeToGlyphname(0x219, (short) 3,
            (short) 1));
    }

    /**
     * test 0x21a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x21a() throws Exception {

        assertEquals("uni021A", reader.mapCharCodeToGlyphname(0x21a, (short) 3,
            (short) 1));
    }

    /**
     * test 0x21b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x21b() throws Exception {

        assertEquals("uni021B", reader.mapCharCodeToGlyphname(0x21b, (short) 3,
            (short) 1));
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
     * test 0x300
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x300() throws Exception {

        assertEquals("uni0300", reader.mapCharCodeToGlyphname(0x300, (short) 3,
            (short) 1));
    }

    /**
     * test 0x301
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x301() throws Exception {

        assertEquals("uni0301", reader.mapCharCodeToGlyphname(0x301, (short) 3,
            (short) 1));
    }

    /**
     * test 0x302
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x302() throws Exception {

        assertEquals("uni0302", reader.mapCharCodeToGlyphname(0x302, (short) 3,
            (short) 1));
    }

    /**
     * test 0x303
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x303() throws Exception {

        assertEquals("uni0303", reader.mapCharCodeToGlyphname(0x303, (short) 3,
            (short) 1));
    }

    /**
     * test 0x304
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x304() throws Exception {

        assertEquals("uni0304", reader.mapCharCodeToGlyphname(0x304, (short) 3,
            (short) 1));
    }

    /**
     * test 0x306
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x306() throws Exception {

        assertEquals("uni0306", reader.mapCharCodeToGlyphname(0x306, (short) 3,
            (short) 1));
    }

    /**
     * test 0x307
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x307() throws Exception {

        assertEquals("uni0307", reader.mapCharCodeToGlyphname(0x307, (short) 3,
            (short) 1));
    }

    /**
     * test 0x308
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x308() throws Exception {

        assertEquals("uni0308", reader.mapCharCodeToGlyphname(0x308, (short) 3,
            (short) 1));
    }

    /**
     * test 0x309
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x309() throws Exception {

        assertEquals("uni0309", reader.mapCharCodeToGlyphname(0x309, (short) 3,
            (short) 1));
    }

    /**
     * test 0x30a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x30a() throws Exception {

        assertEquals("uni030A", reader.mapCharCodeToGlyphname(0x30a, (short) 3,
            (short) 1));
    }

    /**
     * test 0x30b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x30b() throws Exception {

        assertEquals("uni030B", reader.mapCharCodeToGlyphname(0x30b, (short) 3,
            (short) 1));
    }

    /**
     * test 0x30c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x30c() throws Exception {

        assertEquals("uni030C", reader.mapCharCodeToGlyphname(0x30c, (short) 3,
            (short) 1));
    }

    /**
     * test 0x30f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x30f() throws Exception {

        assertEquals("uni030F", reader.mapCharCodeToGlyphname(0x30f, (short) 3,
            (short) 1));
    }

    /**
     * test 0x311
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x311() throws Exception {

        assertEquals("uni0311", reader.mapCharCodeToGlyphname(0x311, (short) 3,
            (short) 1));
    }

    /**
     * test 0x323
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x323() throws Exception {

        assertEquals("uni0323", reader.mapCharCodeToGlyphname(0x323, (short) 3,
            (short) 1));
    }

    /**
     * test 0x326
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x326() throws Exception {

        assertEquals("uni0326", reader.mapCharCodeToGlyphname(0x326, (short) 3,
            (short) 1));
    }

    /**
     * test 0x32e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x32e() throws Exception {

        assertEquals("uni032E", reader.mapCharCodeToGlyphname(0x32e, (short) 3,
            (short) 1));
    }

    /**
     * test 0x32f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x32f() throws Exception {

        assertEquals("uni032F", reader.mapCharCodeToGlyphname(0x32f, (short) 3,
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
     * test 0x398
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x398() throws Exception {

        assertEquals("Theta", reader.mapCharCodeToGlyphname(0x398, (short) 3,
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
     * test 0x39e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x39e() throws Exception {

        assertEquals("Xi", reader.mapCharCodeToGlyphname(0x39e, (short) 3,
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
     * test 0x3a3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x3a3() throws Exception {

        assertEquals("Sigma", reader.mapCharCodeToGlyphname(0x3a3, (short) 3,
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
     * test 0xe3f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe3f() throws Exception {

        assertEquals("baht", reader.mapCharCodeToGlyphname(0xe3f, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1e0c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e0c() throws Exception {

        assertEquals("D_uni0323", reader.mapCharCodeToGlyphname(0x1e0c,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e0d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e0d() throws Exception {

        assertEquals("d_uni0323", reader.mapCharCodeToGlyphname(0x1e0d,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e24
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e24() throws Exception {

        assertEquals("H_uni0323", reader.mapCharCodeToGlyphname(0x1e24,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e25
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e25() throws Exception {

        assertEquals("h_uni0323", reader.mapCharCodeToGlyphname(0x1e25,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e36
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e36() throws Exception {

        assertEquals("L_uni0323", reader.mapCharCodeToGlyphname(0x1e36,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e37
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e37() throws Exception {

        assertEquals("l_uni0323", reader.mapCharCodeToGlyphname(0x1e37,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e38
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e38() throws Exception {

        assertEquals("L_uni0323_uni0304.cap", reader.mapCharCodeToGlyphname(
            0x1e38, (short) 3, (short) 1));
    }

    /**
     * test 0x1e39
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e39() throws Exception {

        assertEquals("l_uni0323_uni0304", reader.mapCharCodeToGlyphname(0x1e39,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e42
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e42() throws Exception {

        assertEquals("M_uni0323", reader.mapCharCodeToGlyphname(0x1e42,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e43
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e43() throws Exception {

        assertEquals("m_uni0323", reader.mapCharCodeToGlyphname(0x1e43,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e44
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e44() throws Exception {

        assertEquals("N_uni0307.cap", reader.mapCharCodeToGlyphname(0x1e44,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e45
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e45() throws Exception {

        assertEquals("n_uni0307", reader.mapCharCodeToGlyphname(0x1e45,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e46
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e46() throws Exception {

        assertEquals("N_uni0323", reader.mapCharCodeToGlyphname(0x1e46,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e47
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e47() throws Exception {

        assertEquals("n_uni0323", reader.mapCharCodeToGlyphname(0x1e47,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e58
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e58() throws Exception {

        assertEquals("R_uni0307.cap", reader.mapCharCodeToGlyphname(0x1e58,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e59
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e59() throws Exception {

        assertEquals("r_uni0307", reader.mapCharCodeToGlyphname(0x1e59,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e5a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e5a() throws Exception {

        assertEquals("R_uni0323", reader.mapCharCodeToGlyphname(0x1e5a,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e5b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e5b() throws Exception {

        assertEquals("r_uni0323", reader.mapCharCodeToGlyphname(0x1e5b,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e5c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e5c() throws Exception {

        assertEquals("R_uni0323_uni0304.cap", reader.mapCharCodeToGlyphname(
            0x1e5c, (short) 3, (short) 1));
    }

    /**
     * test 0x1e5d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e5d() throws Exception {

        assertEquals("r_uni0323_uni0304", reader.mapCharCodeToGlyphname(0x1e5d,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e6c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e6c() throws Exception {

        assertEquals("T_uni0323", reader.mapCharCodeToGlyphname(0x1e6c,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1e6d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1e6d() throws Exception {

        assertEquals("t_uni0323", reader.mapCharCodeToGlyphname(0x1e6d,
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
     * test 0x1ea0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea0() throws Exception {

        assertEquals("Adotbelow", reader.mapCharCodeToGlyphname(0x1ea0,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea1() throws Exception {

        assertEquals("adotbelow", reader.mapCharCodeToGlyphname(0x1ea1,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea2
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea2() throws Exception {

        assertEquals("Ahookabove", reader.mapCharCodeToGlyphname(0x1ea2,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea3() throws Exception {

        assertEquals("ahookabove", reader.mapCharCodeToGlyphname(0x1ea3,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea4() throws Exception {

        assertEquals("Acircumflexacute", reader.mapCharCodeToGlyphname(0x1ea4,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea5() throws Exception {

        assertEquals("acircumflexacute", reader.mapCharCodeToGlyphname(0x1ea5,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea6() throws Exception {

        assertEquals("Acircumflexgrave", reader.mapCharCodeToGlyphname(0x1ea6,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea7() throws Exception {

        assertEquals("acircumflexgrave", reader.mapCharCodeToGlyphname(0x1ea7,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ea8
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea8() throws Exception {

        assertEquals("Acircumflexhookabove", reader.mapCharCodeToGlyphname(
            0x1ea8, (short) 3, (short) 1));
    }

    /**
     * test 0x1ea9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ea9() throws Exception {

        assertEquals("acircumflexhookabove", reader.mapCharCodeToGlyphname(
            0x1ea9, (short) 3, (short) 1));
    }

    /**
     * test 0x1eaa
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eaa() throws Exception {

        assertEquals("Acircumflextilde", reader.mapCharCodeToGlyphname(0x1eaa,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eab
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eab() throws Exception {

        assertEquals("acircumflextilde", reader.mapCharCodeToGlyphname(0x1eab,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eac
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eac() throws Exception {

        assertEquals("Acircumflexdotbelow", reader.mapCharCodeToGlyphname(
            0x1eac, (short) 3, (short) 1));
    }

    /**
     * test 0x1ead
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ead() throws Exception {

        assertEquals("acircumflexdotbelow", reader.mapCharCodeToGlyphname(
            0x1ead, (short) 3, (short) 1));
    }

    /**
     * test 0x1eae
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eae() throws Exception {

        assertEquals("Abreveacute", reader.mapCharCodeToGlyphname(0x1eae,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eaf
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eaf() throws Exception {

        assertEquals("abreveacute", reader.mapCharCodeToGlyphname(0x1eaf,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb0() throws Exception {

        assertEquals("Abrevegrave", reader.mapCharCodeToGlyphname(0x1eb0,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb1() throws Exception {

        assertEquals("abrevegrave", reader.mapCharCodeToGlyphname(0x1eb1,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb2
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb2() throws Exception {

        assertEquals("Abrevehookabove", reader.mapCharCodeToGlyphname(0x1eb2,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb3() throws Exception {

        assertEquals("abrevehookabove", reader.mapCharCodeToGlyphname(0x1eb3,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb4() throws Exception {

        assertEquals("Abrevetilde", reader.mapCharCodeToGlyphname(0x1eb4,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb5() throws Exception {

        assertEquals("abrevetilde", reader.mapCharCodeToGlyphname(0x1eb5,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb6() throws Exception {

        assertEquals("Abrevedotbelow", reader.mapCharCodeToGlyphname(0x1eb6,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb7() throws Exception {

        assertEquals("abrevedotbelow", reader.mapCharCodeToGlyphname(0x1eb7,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb8
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb8() throws Exception {

        assertEquals("Edotbelow", reader.mapCharCodeToGlyphname(0x1eb8,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eb9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eb9() throws Exception {

        assertEquals("edotbelow", reader.mapCharCodeToGlyphname(0x1eb9,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eba
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eba() throws Exception {

        assertEquals("Ehookabove", reader.mapCharCodeToGlyphname(0x1eba,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ebb
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ebb() throws Exception {

        assertEquals("ehookabove", reader.mapCharCodeToGlyphname(0x1ebb,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ebc
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ebc() throws Exception {

        assertEquals("Etilde", reader.mapCharCodeToGlyphname(0x1ebc, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1ebd
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ebd() throws Exception {

        assertEquals("etilde", reader.mapCharCodeToGlyphname(0x1ebd, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1ebe
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ebe() throws Exception {

        assertEquals("Ecircumflexacute", reader.mapCharCodeToGlyphname(0x1ebe,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ebf
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ebf() throws Exception {

        assertEquals("ecircumflexacute", reader.mapCharCodeToGlyphname(0x1ebf,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ec0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec0() throws Exception {

        assertEquals("Ecircumflexgrave", reader.mapCharCodeToGlyphname(0x1ec0,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ec1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec1() throws Exception {

        assertEquals("ecircumflexgrave", reader.mapCharCodeToGlyphname(0x1ec1,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ec2
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec2() throws Exception {

        assertEquals("Ecircumflexhookabove", reader.mapCharCodeToGlyphname(
            0x1ec2, (short) 3, (short) 1));
    }

    /**
     * test 0x1ec3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec3() throws Exception {

        assertEquals("ecircumflexhookabove", reader.mapCharCodeToGlyphname(
            0x1ec3, (short) 3, (short) 1));
    }

    /**
     * test 0x1ec4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec4() throws Exception {

        assertEquals("Ecircumflextilde", reader.mapCharCodeToGlyphname(0x1ec4,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ec5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec5() throws Exception {

        assertEquals("ecircumflextilde", reader.mapCharCodeToGlyphname(0x1ec5,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ec6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec6() throws Exception {

        assertEquals("Ecircumflexdotbelow", reader.mapCharCodeToGlyphname(
            0x1ec6, (short) 3, (short) 1));
    }

    /**
     * test 0x1ec7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec7() throws Exception {

        assertEquals("ecircumflexdotbelow", reader.mapCharCodeToGlyphname(
            0x1ec7, (short) 3, (short) 1));
    }

    /**
     * test 0x1ec8
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec8() throws Exception {

        assertEquals("Ihookabove", reader.mapCharCodeToGlyphname(0x1ec8,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ec9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ec9() throws Exception {

        assertEquals("ihookabove", reader.mapCharCodeToGlyphname(0x1ec9,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eca
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eca() throws Exception {

        assertEquals("Idotbelow", reader.mapCharCodeToGlyphname(0x1eca,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ecb
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ecb() throws Exception {

        assertEquals("idotbelow", reader.mapCharCodeToGlyphname(0x1ecb,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ecc
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ecc() throws Exception {

        assertEquals("Odotbelow", reader.mapCharCodeToGlyphname(0x1ecc,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ecd
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ecd() throws Exception {

        assertEquals("odotbelow", reader.mapCharCodeToGlyphname(0x1ecd,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ece
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ece() throws Exception {

        assertEquals("Ohookabove", reader.mapCharCodeToGlyphname(0x1ece,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ecf
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ecf() throws Exception {

        assertEquals("ohookabove", reader.mapCharCodeToGlyphname(0x1ecf,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed0() throws Exception {

        assertEquals("Ocircumflexacute", reader.mapCharCodeToGlyphname(0x1ed0,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed1() throws Exception {

        assertEquals("ocircumflexacute", reader.mapCharCodeToGlyphname(0x1ed1,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed2
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed2() throws Exception {

        assertEquals("Ocircumflexgrave", reader.mapCharCodeToGlyphname(0x1ed2,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed3() throws Exception {

        assertEquals("ocircumflexgrave", reader.mapCharCodeToGlyphname(0x1ed3,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed4() throws Exception {

        assertEquals("Ocircumflexhookabove", reader.mapCharCodeToGlyphname(
            0x1ed4, (short) 3, (short) 1));
    }

    /**
     * test 0x1ed5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed5() throws Exception {

        assertEquals("ocircumflexhookabove", reader.mapCharCodeToGlyphname(
            0x1ed5, (short) 3, (short) 1));
    }

    /**
     * test 0x1ed6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed6() throws Exception {

        assertEquals("Ocircumflextilde", reader.mapCharCodeToGlyphname(0x1ed6,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed7() throws Exception {

        assertEquals("ocircumflextilde", reader.mapCharCodeToGlyphname(0x1ed7,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ed8
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed8() throws Exception {

        assertEquals("Ocircumflexdotbelow", reader.mapCharCodeToGlyphname(
            0x1ed8, (short) 3, (short) 1));
    }

    /**
     * test 0x1ed9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ed9() throws Exception {

        assertEquals("ocircumflexdotbelow", reader.mapCharCodeToGlyphname(
            0x1ed9, (short) 3, (short) 1));
    }

    /**
     * test 0x1eda
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eda() throws Exception {

        assertEquals("Ohornacute", reader.mapCharCodeToGlyphname(0x1eda,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1edb
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1edb() throws Exception {

        assertEquals("ohornacute", reader.mapCharCodeToGlyphname(0x1edb,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1edc
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1edc() throws Exception {

        assertEquals("Ohorngrave", reader.mapCharCodeToGlyphname(0x1edc,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1edd
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1edd() throws Exception {

        assertEquals("ohorngrave", reader.mapCharCodeToGlyphname(0x1edd,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ede
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ede() throws Exception {

        assertEquals("Ohornhookabove", reader.mapCharCodeToGlyphname(0x1ede,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1edf
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1edf() throws Exception {

        assertEquals("ohornhookabove", reader.mapCharCodeToGlyphname(0x1edf,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee0() throws Exception {

        assertEquals("Ohorntilde", reader.mapCharCodeToGlyphname(0x1ee0,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee1() throws Exception {

        assertEquals("ohorntilde", reader.mapCharCodeToGlyphname(0x1ee1,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee2
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee2() throws Exception {

        assertEquals("Ohorndotbelow", reader.mapCharCodeToGlyphname(0x1ee2,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee3() throws Exception {

        assertEquals("ohorndotbelow", reader.mapCharCodeToGlyphname(0x1ee3,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee4() throws Exception {

        assertEquals("Udotbelow", reader.mapCharCodeToGlyphname(0x1ee4,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee5() throws Exception {

        assertEquals("udotbelow", reader.mapCharCodeToGlyphname(0x1ee5,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee6() throws Exception {

        assertEquals("Uhookabove", reader.mapCharCodeToGlyphname(0x1ee6,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee7() throws Exception {

        assertEquals("uhookabove", reader.mapCharCodeToGlyphname(0x1ee7,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee8
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee8() throws Exception {

        assertEquals("Uhornacute", reader.mapCharCodeToGlyphname(0x1ee8,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ee9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ee9() throws Exception {

        assertEquals("uhornacute", reader.mapCharCodeToGlyphname(0x1ee9,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eea
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eea() throws Exception {

        assertEquals("Uhorngrave", reader.mapCharCodeToGlyphname(0x1eea,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eeb
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eeb() throws Exception {

        assertEquals("uhorngrave", reader.mapCharCodeToGlyphname(0x1eeb,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eec
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eec() throws Exception {

        assertEquals("Uhornhookabove", reader.mapCharCodeToGlyphname(0x1eec,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eed
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eed() throws Exception {

        assertEquals("uhornhookabove", reader.mapCharCodeToGlyphname(0x1eed,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eee
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eee() throws Exception {

        assertEquals("Uhorntilde", reader.mapCharCodeToGlyphname(0x1eee,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1eef
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1eef() throws Exception {

        assertEquals("uhorntilde", reader.mapCharCodeToGlyphname(0x1eef,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ef0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef0() throws Exception {

        assertEquals("Uhorndotbelow", reader.mapCharCodeToGlyphname(0x1ef0,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ef1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef1() throws Exception {

        assertEquals("uhorndotbelow", reader.mapCharCodeToGlyphname(0x1ef1,
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
     * test 0x1ef4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef4() throws Exception {

        assertEquals("Ydotbelow", reader.mapCharCodeToGlyphname(0x1ef4,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ef5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef5() throws Exception {

        assertEquals("ydotbelow", reader.mapCharCodeToGlyphname(0x1ef5,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ef6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef6() throws Exception {

        assertEquals("Yhookabove", reader.mapCharCodeToGlyphname(0x1ef6,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ef7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef7() throws Exception {

        assertEquals("yhookabove", reader.mapCharCodeToGlyphname(0x1ef7,
            (short) 3, (short) 1));
    }

    /**
     * test 0x1ef8
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef8() throws Exception {

        assertEquals("Ytilde", reader.mapCharCodeToGlyphname(0x1ef8, (short) 3,
            (short) 1));
    }

    /**
     * test 0x1ef9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x1ef9() throws Exception {

        assertEquals("ytilde", reader.mapCharCodeToGlyphname(0x1ef9, (short) 3,
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
     * test 0x2016
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2016() throws Exception {

        assertEquals("dblverticalbar", reader.mapCharCodeToGlyphname(0x2016,
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
     * test 0x2031
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2031() throws Exception {

        assertEquals("permyriad", reader.mapCharCodeToGlyphname(0x2031,
            (short) 3, (short) 1));
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
     * test 0x203b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x203b() throws Exception {

        assertEquals("referencemark", reader.mapCharCodeToGlyphname(0x203b,
            (short) 3, (short) 1));
    }

    /**
     * test 0x203d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x203d() throws Exception {

        assertEquals("interrobang", reader.mapCharCodeToGlyphname(0x203d,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2044
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2044() throws Exception {

        assertEquals("fraction", reader.mapCharCodeToGlyphname(0x2044,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2045
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2045() throws Exception {

        assertEquals("quillbracketleft", reader.mapCharCodeToGlyphname(0x2045,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2046
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2046() throws Exception {

        assertEquals("quillbracketright", reader.mapCharCodeToGlyphname(0x2046,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2052
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2052() throws Exception {

        assertEquals("discount", reader.mapCharCodeToGlyphname(0x2052,
            (short) 3, (short) 1));
    }

    /**
     * test 0x20a1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20a1() throws Exception {

        assertEquals("colonmonetary", reader.mapCharCodeToGlyphname(0x20a1,
            (short) 3, (short) 1));
    }

    /**
     * test 0x20a4
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20a4() throws Exception {

        assertEquals("lira", reader.mapCharCodeToGlyphname(0x20a4, (short) 3,
            (short) 1));
    }

    /**
     * test 0x20a6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20a6() throws Exception {

        assertEquals("naira", reader.mapCharCodeToGlyphname(0x20a6, (short) 3,
            (short) 1));
    }

    /**
     * test 0x20a9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20a9() throws Exception {

        assertEquals("won", reader.mapCharCodeToGlyphname(0x20a9, (short) 3,
            (short) 1));
    }

    /**
     * test 0x20ab
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20ab() throws Exception {

        assertEquals("dong", reader.mapCharCodeToGlyphname(0x20ab, (short) 3,
            (short) 1));
    }

    /**
     * test 0x20ac
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20ac() throws Exception {

        assertEquals("Euro", reader.mapCharCodeToGlyphname(0x20ac, (short) 3,
            (short) 1));
    }

    /**
     * test 0x20b1
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x20b1() throws Exception {

        assertEquals("peso", reader.mapCharCodeToGlyphname(0x20b1, (short) 3,
            (short) 1));
    }

    /**
     * test 0x2103
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2103() throws Exception {

        assertEquals("centigrade", reader.mapCharCodeToGlyphname(0x2103,
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
     * test 0x2117
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2117() throws Exception {

        assertEquals("published", reader.mapCharCodeToGlyphname(0x2117,
            (short) 3, (short) 1));
    }

    /**
     * test 0x211e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x211e() throws Exception {

        assertEquals("recipe", reader.mapCharCodeToGlyphname(0x211e, (short) 3,
            (short) 1));
    }

    /**
     * test 0x2120
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2120() throws Exception {

        assertEquals("servicemark", reader.mapCharCodeToGlyphname(0x2120,
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

        assertEquals("ohm", reader.mapCharCodeToGlyphname(0x2126, (short) 3,
            (short) 1));
    }

    /**
     * test 0x2127
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2127() throws Exception {

        assertEquals("uni2127", reader.mapCharCodeToGlyphname(0x2127,
            (short) 3, (short) 1));
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
     * test 0x2190
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2190() throws Exception {

        assertEquals("uni2190", reader.mapCharCodeToGlyphname(0x2190,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2191
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2191() throws Exception {

        assertEquals("uni2191", reader.mapCharCodeToGlyphname(0x2191,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2192
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2192() throws Exception {

        assertEquals("uni2192", reader.mapCharCodeToGlyphname(0x2192,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2193
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2193() throws Exception {

        assertEquals("uni2193", reader.mapCharCodeToGlyphname(0x2193,
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

        assertEquals("fraction.alt", reader.mapCharCodeToGlyphname(0x2215,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2217
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2217() throws Exception {

        assertEquals("asterisk.math", reader.mapCharCodeToGlyphname(0x2217,
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
     * test 0x2222
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2222() throws Exception {

        assertEquals("anglearc", reader.mapCharCodeToGlyphname(0x2222,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2300
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2300() throws Exception {

        assertEquals("diameter", reader.mapCharCodeToGlyphname(0x2300,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2329
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2329() throws Exception {

        assertEquals("angleleft", reader.mapCharCodeToGlyphname(0x2329,
            (short) 3, (short) 1));
    }

    /**
     * test 0x232a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x232a() throws Exception {

        assertEquals("angleright", reader.mapCharCodeToGlyphname(0x232a,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2422
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2422() throws Exception {

        assertEquals("blanksymbol", reader.mapCharCodeToGlyphname(0x2422,
            (short) 3, (short) 1));
    }

    /**
     * test 0x2423
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x2423() throws Exception {

        assertEquals("space.visible", reader.mapCharCodeToGlyphname(0x2423,
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
     * test 0x266a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x266a() throws Exception {

        assertEquals("uni266A", reader.mapCharCodeToGlyphname(0x266a,
            (short) 3, (short) 1));
    }

    /**
     * test 0x26ad
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x26ad() throws Exception {

        assertEquals("married", reader.mapCharCodeToGlyphname(0x26ad,
            (short) 3, (short) 1));
    }

    /**
     * test 0x26ae
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x26ae() throws Exception {

        assertEquals("divorced", reader.mapCharCodeToGlyphname(0x26ae,
            (short) 3, (short) 1));
    }

    /**
     * test 0x27e6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x27e6() throws Exception {

        assertEquals("dblbracketleft", reader.mapCharCodeToGlyphname(0x27e6,
            (short) 3, (short) 1));
    }

    /**
     * test 0x27e7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0x27e7() throws Exception {

        assertEquals("dblbracketright", reader.mapCharCodeToGlyphname(0x27e7,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe09b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe09b() throws Exception {

        assertEquals("f_k", reader.mapCharCodeToGlyphname(0xe09b, (short) 3,
            (short) 1));
    }

    /**
     * test 0xe300
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe300() throws Exception {

        assertEquals("uni0300.cap", reader.mapCharCodeToGlyphname(0xe300,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe301
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe301() throws Exception {

        assertEquals("uni0301.cap", reader.mapCharCodeToGlyphname(0xe301,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe302
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe302() throws Exception {

        assertEquals("uni0302.cap", reader.mapCharCodeToGlyphname(0xe302,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe303
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe303() throws Exception {

        assertEquals("uni0303.cap", reader.mapCharCodeToGlyphname(0xe303,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe304
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe304() throws Exception {

        assertEquals("uni0304.cap", reader.mapCharCodeToGlyphname(0xe304,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe306
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe306() throws Exception {

        assertEquals("uni0306.cap", reader.mapCharCodeToGlyphname(0xe306,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe307
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe307() throws Exception {

        assertEquals("uni0307.cap", reader.mapCharCodeToGlyphname(0xe307,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe308
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe308() throws Exception {

        assertEquals("uni0308.cap", reader.mapCharCodeToGlyphname(0xe308,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe309
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe309() throws Exception {

        assertEquals("uni0309.cap", reader.mapCharCodeToGlyphname(0xe309,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe30a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe30a() throws Exception {

        assertEquals("uni030A.cap", reader.mapCharCodeToGlyphname(0xe30a,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe30b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe30b() throws Exception {

        assertEquals("uni030B.cap", reader.mapCharCodeToGlyphname(0xe30b,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe30c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe30c() throws Exception {

        assertEquals("uni030C.cap", reader.mapCharCodeToGlyphname(0xe30c,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe30f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe30f() throws Exception {

        assertEquals("uni030F.cap", reader.mapCharCodeToGlyphname(0xe30f,
            (short) 3, (short) 1));
    }

    /**
     * test 0xe311
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xe311() throws Exception {

        assertEquals("uni0311.cap", reader.mapCharCodeToGlyphname(0xe311,
            (short) 3, (short) 1));
    }

    /**
     * test 0xea00
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea00() throws Exception {

        assertEquals("space_uni0306_uni0301.cap", reader
            .mapCharCodeToGlyphname(0xea00, (short) 3, (short) 1));
    }

    /**
     * test 0xea01
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea01() throws Exception {

        assertEquals("space_uni0306_uni0301", reader.mapCharCodeToGlyphname(
            0xea01, (short) 3, (short) 1));
    }

    /**
     * test 0xea02
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea02() throws Exception {

        assertEquals("space_uni0306_uni0300.cap", reader
            .mapCharCodeToGlyphname(0xea02, (short) 3, (short) 1));
    }

    /**
     * test 0xea03
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea03() throws Exception {

        assertEquals("space_uni0306_uni0300", reader.mapCharCodeToGlyphname(
            0xea03, (short) 3, (short) 1));
    }

    /**
     * test 0xea04
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea04() throws Exception {

        assertEquals("space_uni0306_uni0309.cap", reader
            .mapCharCodeToGlyphname(0xea04, (short) 3, (short) 1));
    }

    /**
     * test 0xea05
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea05() throws Exception {

        assertEquals("space_uni0306_uni0309", reader.mapCharCodeToGlyphname(
            0xea05, (short) 3, (short) 1));
    }

    /**
     * test 0xea06
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea06() throws Exception {

        assertEquals("space_uni0311.cap", reader.mapCharCodeToGlyphname(0xea06,
            (short) 3, (short) 1));
    }

    /**
     * test 0xea07
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea07() throws Exception {

        assertEquals("space_uni0311", reader.mapCharCodeToGlyphname(0xea07,
            (short) 3, (short) 1));
    }

    /**
     * test 0xea08
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea08() throws Exception {

        assertEquals("space_uni032F", reader.mapCharCodeToGlyphname(0xea08,
            (short) 3, (short) 1));
    }

    /**
     * test 0xea09
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea09() throws Exception {

        assertEquals("space_uni0306_uni0303.cap", reader
            .mapCharCodeToGlyphname(0xea09, (short) 3, (short) 1));
    }

    /**
     * test 0xea0a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea0a() throws Exception {

        assertEquals("space_uni0306_uni0303", reader.mapCharCodeToGlyphname(
            0xea0a, (short) 3, (short) 1));
    }

    /**
     * test 0xea0b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea0b() throws Exception {

        assertEquals("space_uni0302_uni0301.cap", reader
            .mapCharCodeToGlyphname(0xea0b, (short) 3, (short) 1));
    }

    /**
     * test 0xea0c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea0c() throws Exception {

        assertEquals("space_uni0302_uni0301", reader.mapCharCodeToGlyphname(
            0xea0c, (short) 3, (short) 1));
    }

    /**
     * test 0xea0d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea0d() throws Exception {

        assertEquals("space_uni0302_uni0300.cap", reader
            .mapCharCodeToGlyphname(0xea0d, (short) 3, (short) 1));
    }

    /**
     * test 0xea0e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea0e() throws Exception {

        assertEquals("space_uni0302_uni0300", reader.mapCharCodeToGlyphname(
            0xea0e, (short) 3, (short) 1));
    }

    /**
     * test 0xea0f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea0f() throws Exception {

        assertEquals("space_uni0302_uni0309.cap", reader
            .mapCharCodeToGlyphname(0xea0f, (short) 3, (short) 1));
    }

    /**
     * test 0xea10
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea10() throws Exception {

        assertEquals("space_uni0302_uni0309", reader.mapCharCodeToGlyphname(
            0xea10, (short) 3, (short) 1));
    }

    /**
     * test 0xea11
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea11() throws Exception {

        assertEquals("space_uni0302_uni0303.cap", reader
            .mapCharCodeToGlyphname(0xea11, (short) 3, (short) 1));
    }

    /**
     * test 0xea12
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea12() throws Exception {

        assertEquals("space_uni0302_uni0303", reader.mapCharCodeToGlyphname(
            0xea12, (short) 3, (short) 1));
    }

    /**
     * test 0xea13
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea13() throws Exception {

        assertEquals("space_uni0309.cap", reader.mapCharCodeToGlyphname(0xea13,
            (short) 3, (short) 1));
    }

    /**
     * test 0xea14
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea14() throws Exception {

        assertEquals("space_uni0309", reader.mapCharCodeToGlyphname(0xea14,
            (short) 3, (short) 1));
    }

    /**
     * test 0xea16
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea16() throws Exception {

        assertEquals("space_uni030A_uni0301.cap", reader
            .mapCharCodeToGlyphname(0xea16, (short) 3, (short) 1));
    }

    /**
     * test 0xea17
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xea17() throws Exception {

        assertEquals("space_uni030A_uni0301", reader.mapCharCodeToGlyphname(
            0xea17, (short) 3, (short) 1));
    }

    /**
     * test 0xeb02
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb02() throws Exception {

        assertEquals("acute.ts1", reader.mapCharCodeToGlyphname(0xeb02,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb03
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb03() throws Exception {

        assertEquals("Aogonekacute", reader.mapCharCodeToGlyphname(0xeb03,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb04
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb04() throws Exception {

        assertEquals("aogonekacute", reader.mapCharCodeToGlyphname(0xeb04,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb08
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb08() throws Exception {

        assertEquals("bigcircle", reader.mapCharCodeToGlyphname(0xeb08,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb09
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb09() throws Exception {

        assertEquals("star.alt", reader.mapCharCodeToGlyphname(0xeb09,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb0a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb0a() throws Exception {

        assertEquals("breve.ts1", reader.mapCharCodeToGlyphname(0xeb0a,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb0d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb0d() throws Exception {

        assertEquals("caron.ts1", reader.mapCharCodeToGlyphname(0xeb0d,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb0f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb0f() throws Exception {

        assertEquals("copyleft", reader.mapCharCodeToGlyphname(0xeb0f,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb10
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb10() throws Exception {

        assertEquals("cwm", reader.mapCharCodeToGlyphname(0xeb10, (short) 3,
            (short) 1));
    }

    /**
     * test 0xeb11
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb11() throws Exception {

        assertEquals("cwmascender", reader.mapCharCodeToGlyphname(0xeb11,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb12
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb12() throws Exception {

        assertEquals("cwmcapital", reader.mapCharCodeToGlyphname(0xeb12,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb15
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb15() throws Exception {

        assertEquals("dblgrave.ts1", reader.mapCharCodeToGlyphname(0xeb15,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb16
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb16() throws Exception {

        assertEquals("died", reader.mapCharCodeToGlyphname(0xeb16, (short) 3,
            (short) 1));
    }

    /**
     * test 0xeb17
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb17() throws Exception {

        assertEquals("dieresis.ts1", reader.mapCharCodeToGlyphname(0xeb17,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb19
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb19() throws Exception {

        assertEquals("space_uni0323", reader.mapCharCodeToGlyphname(0xeb19,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb1e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb1e() throws Exception {

        assertEquals("E.reversed", reader.mapCharCodeToGlyphname(0xeb1e,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb1f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb1f() throws Exception {

        assertEquals("e.reversed", reader.mapCharCodeToGlyphname(0xeb1f,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb20
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb20() throws Exception {

        assertEquals("Eogonekacute", reader.mapCharCodeToGlyphname(0xeb20,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb21
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb21() throws Exception {

        assertEquals("eogonekacute", reader.mapCharCodeToGlyphname(0xeb21,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb2a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb2a() throws Exception {

        assertEquals("S_S", reader.mapCharCodeToGlyphname(0xeb2a, (short) 3,
            (short) 1));
    }

    /**
     * test 0xeb2b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb2b() throws Exception {

        assertEquals("gnaborretni", reader.mapCharCodeToGlyphname(0xeb2b,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb2c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb2c() throws Exception {

        assertEquals("grave.ts1", reader.mapCharCodeToGlyphname(0xeb2c,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb2d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb2d() throws Exception {

        assertEquals("guarani", reader.mapCharCodeToGlyphname(0xeb2d,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb30
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb30() throws Exception {

        assertEquals("hungarumlaut.ts1", reader.mapCharCodeToGlyphname(0xeb30,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb31
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb31() throws Exception {

        assertEquals("hyphen.alt", reader.mapCharCodeToGlyphname(0xeb31,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb32
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb32() throws Exception {

        assertEquals("hyphen.prop", reader.mapCharCodeToGlyphname(0xeb32,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb33
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb33() throws Exception {

        assertEquals("hyphendbl", reader.mapCharCodeToGlyphname(0xeb33,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb34
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb34() throws Exception {

        assertEquals("hyphendbl.alt", reader.mapCharCodeToGlyphname(0xeb34,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb37
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb37() throws Exception {

        assertEquals("Iogonekacute", reader.mapCharCodeToGlyphname(0xeb37,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb38
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb38() throws Exception {

        assertEquals("iogonekacute", reader.mapCharCodeToGlyphname(0xeb38,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb3c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb3c() throws Exception {

        assertEquals("Jacute", reader.mapCharCodeToGlyphname(0xeb3c, (short) 3,
            (short) 1));
    }

    /**
     * test 0xeb3d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb3d() throws Exception {

        assertEquals("jacute", reader.mapCharCodeToGlyphname(0xeb3d, (short) 3,
            (short) 1));
    }

    /**
     * test 0xeb42
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb42() throws Exception {

        assertEquals("leaf", reader.mapCharCodeToGlyphname(0xeb42, (short) 3,
            (short) 1));
    }

    /**
     * test 0xeb45
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb45() throws Exception {

        assertEquals("macron.ts1", reader.mapCharCodeToGlyphname(0xeb45,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb4a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb4a() throws Exception {

        assertEquals("Oogonekacute", reader.mapCharCodeToGlyphname(0xeb4a,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb4b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb4b() throws Exception {

        assertEquals("oogonekacute", reader.mapCharCodeToGlyphname(0xeb4b,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb4e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb4e() throws Exception {

        assertEquals("paragraph.alt", reader.mapCharCodeToGlyphname(0xeb4e,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb4f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb4f() throws Exception {

        assertEquals("perthousandzero", reader.mapCharCodeToGlyphname(0xeb4f,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb52
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb52() throws Exception {

        assertEquals("quotedblbase.cm", reader.mapCharCodeToGlyphname(0xeb52,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb53
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb53() throws Exception {

        assertEquals("quotedblbase.cs", reader.mapCharCodeToGlyphname(0xeb53,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb54
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb54() throws Exception {

        assertEquals("quotedblbase.ts1", reader.mapCharCodeToGlyphname(0xeb54,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb55
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb55() throws Exception {

        assertEquals("quotedblleft.cm", reader.mapCharCodeToGlyphname(0xeb55,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb56
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb56() throws Exception {

        assertEquals("quotedblright.cm", reader.mapCharCodeToGlyphname(0xeb56,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb57
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb57() throws Exception {

        assertEquals("quotedblright.cs", reader.mapCharCodeToGlyphname(0xeb57,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb58
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb58() throws Exception {

        assertEquals("quotesinglbase.ts1", reader.mapCharCodeToGlyphname(
            0xeb58, (short) 3, (short) 1));
    }

    /**
     * test 0xeb59
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb59() throws Exception {

        assertEquals("quotesingle.ts1", reader.mapCharCodeToGlyphname(0xeb59,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb5c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb5c() throws Exception {

        assertEquals("registered.alt", reader.mapCharCodeToGlyphname(0xeb5c,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb62
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb62() throws Exception {

        assertEquals("suppress", reader.mapCharCodeToGlyphname(0xeb62,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb65
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb65() throws Exception {

        assertEquals("tieaccentcapital", reader.mapCharCodeToGlyphname(0xeb65,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb66
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb66() throws Exception {

        assertEquals("tieaccentcapital.new", reader.mapCharCodeToGlyphname(
            0xeb66, (short) 3, (short) 1));
    }

    /**
     * test 0xeb67
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb67() throws Exception {

        assertEquals("tieaccentlowercase", reader.mapCharCodeToGlyphname(
            0xeb67, (short) 3, (short) 1));
    }

    /**
     * test 0xeb68
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb68() throws Exception {

        assertEquals("tieaccentlowercase.new", reader.mapCharCodeToGlyphname(
            0xeb68, (short) 3, (short) 1));
    }

    /**
     * test 0xeb69
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb69() throws Exception {

        assertEquals("space_uni0330", reader.mapCharCodeToGlyphname(0xeb69,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb6d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb6d() throws Exception {

        assertEquals("uni2014.alt2", reader.mapCharCodeToGlyphname(0xeb6d,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb70
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb70() throws Exception {

        assertEquals("Ubreveinvertedlow", reader.mapCharCodeToGlyphname(0xeb70,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb71
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb71() throws Exception {

        assertEquals("ubreveinvertedlow", reader.mapCharCodeToGlyphname(0xeb71,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeb80
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeb80() throws Exception {

        assertEquals("i.TRK", reader.mapCharCodeToGlyphname(0xeb80, (short) 3,
            (short) 1));
    }

    /**
     * test 0xefed
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xefed() throws Exception {

        assertEquals("dotaccent.cap", reader.mapCharCodeToGlyphname(0xefed,
            (short) 3, (short) 1));
    }

    /**
     * test 0xefee
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xefee() throws Exception {

        assertEquals("breve.cap", reader.mapCharCodeToGlyphname(0xefee,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeff3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeff3() throws Exception {

        assertEquals("ring.cap", reader.mapCharCodeToGlyphname(0xeff3,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeff5
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeff5() throws Exception {

        assertEquals("tilde.cap", reader.mapCharCodeToGlyphname(0xeff5,
            (short) 3, (short) 1));
    }

    /**
     * test 0xeff7
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xeff7() throws Exception {

        assertEquals("circumflex.cap", reader.mapCharCodeToGlyphname(0xeff7,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf638
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf638() throws Exception {

        assertEquals("zero.slash", reader.mapCharCodeToGlyphname(0xf638,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf639
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf639() throws Exception {

        assertEquals("zero.prop", reader.mapCharCodeToGlyphname(0xf639,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf63a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf63a() throws Exception {

        assertEquals("two.prop", reader.mapCharCodeToGlyphname(0xf63a,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf63b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf63b() throws Exception {

        assertEquals("three.prop", reader.mapCharCodeToGlyphname(0xf63b,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf63c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf63c() throws Exception {

        assertEquals("four.prop", reader.mapCharCodeToGlyphname(0xf63c,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf63d
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf63d() throws Exception {

        assertEquals("five.prop", reader.mapCharCodeToGlyphname(0xf63d,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf63e
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf63e() throws Exception {

        assertEquals("six.prop", reader.mapCharCodeToGlyphname(0xf63e,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf63f
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf63f() throws Exception {

        assertEquals("seven.prop", reader.mapCharCodeToGlyphname(0xf63f,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf640
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf640() throws Exception {

        assertEquals("eight.prop", reader.mapCharCodeToGlyphname(0xf640,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf641
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf641() throws Exception {

        assertEquals("nine.prop", reader.mapCharCodeToGlyphname(0xf641,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf643
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf643() throws Exception {

        assertEquals("zero.taboldstyle", reader.mapCharCodeToGlyphname(0xf643,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf644
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf644() throws Exception {

        assertEquals("one.taboldstyle", reader.mapCharCodeToGlyphname(0xf644,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf645
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf645() throws Exception {

        assertEquals("two.taboldstyle", reader.mapCharCodeToGlyphname(0xf645,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf646
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf646() throws Exception {

        assertEquals("three.taboldstyle", reader.mapCharCodeToGlyphname(0xf646,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf647
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf647() throws Exception {

        assertEquals("four.taboldstyle", reader.mapCharCodeToGlyphname(0xf647,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf648
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf648() throws Exception {

        assertEquals("five.taboldstyle", reader.mapCharCodeToGlyphname(0xf648,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf649
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf649() throws Exception {

        assertEquals("six.taboldstyle", reader.mapCharCodeToGlyphname(0xf649,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf64a
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf64a() throws Exception {

        assertEquals("seven.taboldstyle", reader.mapCharCodeToGlyphname(0xf64a,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf64b
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf64b() throws Exception {

        assertEquals("eight.taboldstyle", reader.mapCharCodeToGlyphname(0xf64b,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf64c
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf64c() throws Exception {

        assertEquals("nine.taboldstyle", reader.mapCharCodeToGlyphname(0xf64c,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6be
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6be() throws Exception {

        assertEquals("j.dotless", reader.mapCharCodeToGlyphname(0xf6be,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6c3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6c3() throws Exception {

        assertEquals("commaaccent", reader.mapCharCodeToGlyphname(0xf6c3,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6c9
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6c9() throws Exception {

        assertEquals("acute.cap", reader.mapCharCodeToGlyphname(0xf6c9,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6ca
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6ca() throws Exception {

        assertEquals("caron.cap", reader.mapCharCodeToGlyphname(0xf6ca,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6cb
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6cb() throws Exception {

        assertEquals("dieresis.cap", reader.mapCharCodeToGlyphname(0xf6cb,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6ce
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6ce() throws Exception {

        assertEquals("grave.cap", reader.mapCharCodeToGlyphname(0xf6ce,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6cf
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6cf() throws Exception {

        assertEquals("hungarumlaut.cap", reader.mapCharCodeToGlyphname(0xf6cf,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6d0
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6d0() throws Exception {

        assertEquals("macron.cap", reader.mapCharCodeToGlyphname(0xf6d0,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6d3
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6d3() throws Exception {

        assertEquals("space_uni030F", reader.mapCharCodeToGlyphname(0xf6d3,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6d6
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6d6() throws Exception {

        assertEquals("space_uni030F.cap", reader.mapCharCodeToGlyphname(0xf6d6,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6dc
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6dc() throws Exception {

        assertEquals("one.prop", reader.mapCharCodeToGlyphname(0xf6dc,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf6de
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf6de() throws Exception {

        assertEquals("uni2014.alt1", reader.mapCharCodeToGlyphname(0xf6de,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf724
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf724() throws Exception {

        assertEquals("dollar.oldstyle", reader.mapCharCodeToGlyphname(0xf724,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf730
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf730() throws Exception {

        assertEquals("zero.oldstyle", reader.mapCharCodeToGlyphname(0xf730,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf731
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf731() throws Exception {

        assertEquals("one.oldstyle", reader.mapCharCodeToGlyphname(0xf731,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf732
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf732() throws Exception {

        assertEquals("two.oldstyle", reader.mapCharCodeToGlyphname(0xf732,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf733
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf733() throws Exception {

        assertEquals("three.oldstyle", reader.mapCharCodeToGlyphname(0xf733,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf734
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf734() throws Exception {

        assertEquals("four.oldstyle", reader.mapCharCodeToGlyphname(0xf734,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf735
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf735() throws Exception {

        assertEquals("five.oldstyle", reader.mapCharCodeToGlyphname(0xf735,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf736
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf736() throws Exception {

        assertEquals("six.oldstyle", reader.mapCharCodeToGlyphname(0xf736,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf737
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf737() throws Exception {

        assertEquals("seven.oldstyle", reader.mapCharCodeToGlyphname(0xf737,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf738
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf738() throws Exception {

        assertEquals("eight.oldstyle", reader.mapCharCodeToGlyphname(0xf738,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf739
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf739() throws Exception {

        assertEquals("nine.oldstyle", reader.mapCharCodeToGlyphname(0xf739,
            (short) 3, (short) 1));
    }

    /**
     * test 0xf7a2
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xf7a2() throws Exception {

        assertEquals("cent.oldstyle", reader.mapCharCodeToGlyphname(0xf7a2,
            (short) 3, (short) 1));
    }

    /**
     * test 0xfb00
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xfb00() throws Exception {

        assertEquals("f_f", reader.mapCharCodeToGlyphname(0xfb00, (short) 3,
            (short) 1));
    }

    /**
     * test 0xfb01
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xfb01() throws Exception {

        assertEquals("f_i", reader.mapCharCodeToGlyphname(0xfb01, (short) 3,
            (short) 1));
    }

    /**
     * test 0xfb02
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xfb02() throws Exception {

        assertEquals("f_l", reader.mapCharCodeToGlyphname(0xfb02, (short) 3,
            (short) 1));
    }

    /**
     * test 0xfb03
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xfb03() throws Exception {

        assertEquals("f_f_i", reader.mapCharCodeToGlyphname(0xfb03, (short) 3,
            (short) 1));
    }

    /**
     * test 0xfb04
     * 
     * @throws Exception if an error occurred.
     */
    public void test0xfb04() throws Exception {

        assertEquals("f_f_l", reader.mapCharCodeToGlyphname(0xfb04, (short) 3,
            (short) 1));
    }

}
