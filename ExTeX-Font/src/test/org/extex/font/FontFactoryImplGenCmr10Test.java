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

package org.extex.font;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * Test for the font factory (with font cmr10).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplGenCmr10Test extends AbstractFontFactoryTester {

    /**
     * The font.
     */
    private static ExtexFont font;

    /**
     * The font key.
     */
    private static FontKey key;

    /**
     * Creates a new object.
     *
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    public FontFactoryImplGenCmr10Test() throws ConfigurationException,
            FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("cmr10", new Dimen(Dimen.ONE * 10));

            font = factory.getInstance(key);
        }
    }

    /**
     * test 0
     */
    public void test0() {

        assertNotNull(key);
        assertNotNull(font);
    }

    /**
     * testcmr10C0
     * Char 0: Width=409601, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C0() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 0));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(409601)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C1
     * Char 1: Width=546135, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C1() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 1));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C2
     * Char 2: Width=509726, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C2() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 2));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C3
     * Char 3: Width=455111, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C3() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 3));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(455111)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C4
     * Char 4: Width=436908, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C4() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 4));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C5
     * Char 5: Width=491521, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C5() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 5));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C6
     * Char 6: Width=473316, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C6() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 6));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C7
     * Char 7: Width=509726, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C7() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 7));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C8
     * Char 8: Width=473316, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C8() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 8));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C9
     * Char 9: Width=509726, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C9() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 9));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C10
     * Char 10: Width=473316, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C10() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 10));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C11
     * Char 11: Width=382295, Height=455111, Depth=0, IC=50973
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C11() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 11));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50973).eq(i));
    }

    /**
     * testcmr10C12
     * Char 12: Width=364090, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C12() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 12));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C13
     * Char 13: Width=364090, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C13() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 13));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C14
     * Char 14: Width=546135, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C14() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 14));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C15
     * Char 15: Width=546135, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C15() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 15));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C16
     * Char 16: Width=182045, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C16() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 16));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C17
     * Char 17: Width=200250, Height=282168, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C17() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 17));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(200250)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C18
     * Char 18: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C18() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 18));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C19
     * Char 19: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C19() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 19));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C20
     * Char 20: Width=327681, Height=411876, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C20() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 20));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(411876)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C21
     * Char 21: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C21() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 21));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C22
     * Char 22: Width=327681, Height=372098, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C22() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 22));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(372098)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C23
     * Char 23: Width=491521, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C23() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 23));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C24
     * Char 24: Width=291271, Height=0, Depth=111501, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C24() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 24));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(291271)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(111501)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C25
     * Char 25: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C25() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 25));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C26
     * Char 26: Width=473316, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C26() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 26));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C27
     * Char 27: Width=509726, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C27() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 27));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C28
     * Char 28: Width=327681, Height=345885, Depth=63716, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C28() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 28));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(345885)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63716)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C29
     * Char 29: Width=591646, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C29() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 29));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(591646)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C30
     * Char 30: Width=664463, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C30() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 30));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(664463)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C31
     * Char 31: Width=509726, Height=479686, Depth=31858, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C31() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 31));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(479686)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(31858)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C32
     * Char 32: Width=182045, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C32() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 32));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C33
     * Char 33: Width=182045, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C33() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 33));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C34
     * Char 34: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C34() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 34));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C35
     * Char 35: Width=546135, Height=455111, Depth=127430, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C35() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 35));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127430)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C36
     * Char 36: Width=327681, Height=491520, Depth=36408, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C36() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 36));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(36408)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C37
     * Char 37: Width=546135, Height=491520, Depth=36408, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C37() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 37));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(36408)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C38
     * Char 38: Width=509726, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C38() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 38));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C39
     * Char 39: Width=182045, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C39() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 39));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C40
     * Char 40: Width=254863, Height=491520, Depth=163840, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C40() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 40));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(163840)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C41
     * Char 41: Width=254863, Height=491520, Depth=163840, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C41() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 41));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(163840)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C42
     * Char 42: Width=327681, Height=491520, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C42() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 42));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C43
     * Char 43: Width=509726, Height=382293, Depth=54613, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C43() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 43));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(382293)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54613)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C44
     * Char 44: Width=182045, Height=69176, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C44() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(69176)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C45
     * Char 45: Width=218453, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C45() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 45));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(218453)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C46
     * Char 46: Width=182045, Height=69176, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C46() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 46));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(69176)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C47
     * Char 47: Width=327681, Height=491520, Depth=163840, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C47() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 47));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(163840)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C48
     * Char 48: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C48() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 48));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C49
     * Char 49: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C49() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 49));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C50
     * Char 50: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C50() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 50));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C51
     * Char 51: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C51() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 51));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C52
     * Char 52: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C52() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 52));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C53
     * Char 53: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C53() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 53));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C54
     * Char 54: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C54() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 54));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C55
     * Char 55: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C55() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 55));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C56
     * Char 56: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C56() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 56));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C57
     * Char 57: Width=327681, Height=422343, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C57() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 57));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(422343)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C58
     * Char 58: Width=182045, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C58() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 58));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C59
     * Char 59: Width=182045, Height=282168, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C59() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 59));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C60
     * Char 60: Width=182045, Height=327680, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C60() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 60));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(327680)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C61
     * Char 61: Width=509726, Height=240435, Depth=-87245, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C61() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 61));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(240435)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-87245)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C62
     * Char 62: Width=309476, Height=327680, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C62() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 62));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(327680)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C63
     * Char 63: Width=309476, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C63() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 63));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C64
     * Char 64: Width=509726, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C64() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 64));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C65
     * Char 65: Width=491521, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C65() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 65));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C66
     * Char 66: Width=464215, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C66() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 66));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(464215)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C67
     * Char 67: Width=473316, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C67() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 67));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C68
     * Char 68: Width=500623, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C68() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 68));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(500623)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C69
     * Char 69: Width=446010, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C69() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 69));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(446010)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C70
     * Char 70: Width=427806, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C70() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 70));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427806)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C71
     * Char 71: Width=514276, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C71() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 71));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(514276)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C72
     * Char 72: Width=491521, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C72() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 72));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C73
     * Char 73: Width=236658, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C73() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 73));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C74
     * Char 74: Width=336783, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C74() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 74));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(336783)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C75
     * Char 75: Width=509726, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C75() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 75));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C76
     * Char 76: Width=409601, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C76() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 76));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(409601)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C77
     * Char 77: Width=600748, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C77() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 77));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(600748)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C78
     * Char 78: Width=491521, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C78() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 78));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C79
     * Char 79: Width=509726, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C79() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 79));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C80
     * Char 80: Width=446010, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C80() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 80));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(446010)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C81
     * Char 81: Width=509726, Height=447828, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C81() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 81));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C82
     * Char 82: Width=482418, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C82() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 82));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(482418)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C83
     * Char 83: Width=364090, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C83() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 83));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C84
     * Char 84: Width=473316, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C84() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 84));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C85
     * Char 85: Width=491521, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C85() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 85));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C86
     * Char 86: Width=491521, Height=447828, Depth=0, IC=9101
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C86() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 86));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9101).eq(i));
    }

    /**
     * testcmr10C87
     * Char 87: Width=673566, Height=447828, Depth=0, IC=9101
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C87() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 87));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(673566)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9101).eq(i));
    }

    /**
     * testcmr10C88
     * Char 88: Width=491521, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C88() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 88));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C89
     * Char 89: Width=491521, Height=447828, Depth=0, IC=16383
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C89() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 89));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(16383).eq(i));
    }

    /**
     * testcmr10C90
     * Char 90: Width=400498, Height=447828, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C90() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 90));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(400498)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447828)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C91
     * Char 91: Width=182045, Height=491520, Depth=163840, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C91() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 91));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(163840)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C92
     * Char 92: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C92() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 92));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C93
     * Char 93: Width=182045, Height=491520, Depth=163840, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C93() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 93));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(163840)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C94
     * Char 94: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C94() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 94));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C95
     * Char 95: Width=182045, Height=437688, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C95() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 95));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(437688)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C96
     * Char 96: Width=182045, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C96() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 96));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C97
     * Char 97: Width=327681, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C97() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 97));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C98
     * Char 98: Width=364090, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C98() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 98));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C99
     * Char 99: Width=291271, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C99() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 99));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(291271)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C100
     * Char 100: Width=364090, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C100() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 100));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C101
     * Char 101: Width=291271, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C101() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 101));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(291271)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C102
     * Char 102: Width=200250, Height=455111, Depth=0, IC=50973
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C102() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 102));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(200250)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50973).eq(i));
    }

    /**
     * testcmr10C103
     * Char 103: Width=327681, Height=282168, Depth=127431, IC=9101
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C103() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 103));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9101).eq(i));
    }

    /**
     * testcmr10C104
     * Char 104: Width=364090, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C104() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 104));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C105
     * Char 105: Width=182045, Height=437688, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C105() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 105));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(437688)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C106
     * Char 106: Width=200250, Height=437688, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C106() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 106));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(200250)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(437688)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C107
     * Char 107: Width=345886, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C107() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 107));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C108
     * Char 108: Width=182045, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C108() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 108));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C109
     * Char 109: Width=546135, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C109() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 109));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C110
     * Char 110: Width=364090, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C110() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 110));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C111
     * Char 111: Width=327681, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C111() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 111));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C112
     * Char 112: Width=364090, Height=282168, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C112() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 112));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C113
     * Char 113: Width=345885, Height=282168, Depth=127431, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C113() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 113));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345885)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C114
     * Char 114: Width=256683, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C114() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 114));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(256683)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C115
     * Char 115: Width=258503, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C115() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 115));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(258503)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C116
     * Char 116: Width=254863, Height=403098, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C116() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 116));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(403098)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C117
     * Char 117: Width=364090, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C117() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 117));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C118
     * Char 118: Width=345886, Height=282168, Depth=0, IC=9101
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C118() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 118));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9101).eq(i));
    }

    /**
     * testcmr10C119
     * Char 119: Width=473316, Height=282168, Depth=0, IC=9101
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C119() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 119));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(473316)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9101).eq(i));
    }

    /**
     * testcmr10C120
     * Char 120: Width=345886, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C120() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 120));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C121
     * Char 121: Width=345886, Height=282168, Depth=127431, IC=9101
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C121() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 121));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9101).eq(i));
    }

    /**
     * testcmr10C122
     * Char 122: Width=291271, Height=282168, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C122() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 122));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(291271)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C123
     * Char 123: Width=327681, Height=282168, Depth=0, IC=18205
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C123() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 123));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(18205).eq(i));
    }

    /**
     * testcmr10C124
     * Char 124: Width=655361, Height=282168, Depth=0, IC=18205
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C124() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 124));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(18205).eq(i));
    }

    /**
     * testcmr10C125
     * Char 125: Width=327681, Height=455111, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C125() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 125));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C126
     * Char 126: Width=327681, Height=437688, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C126() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 126));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(437688)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C127
     * Char 127: Width=327681, Height=437688, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C127() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 127));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327681)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(437688)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmr10C128
     * Char 128: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * testcmr10C129
     * Char 129: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * testcmr10C130
     * Char 130: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * testcmr10C131
     * Char 131: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * testcmr10C132
     * Char 132: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * testcmr10C133
     * Char 133: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * testcmr10C134
     * Char 134: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * testcmr10C135
     * Char 135: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * testcmr10C136
     * Char 136: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * testcmr10C137
     * Char 137: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * testcmr10C138
     * Char 138: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * testcmr10C139
     * Char 139: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * testcmr10C140
     * Char 140: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * testcmr10C141
     * Char 141: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * testcmr10C142
     * Char 142: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * testcmr10C143
     * Char 143: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * testcmr10C144
     * Char 144: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * testcmr10C145
     * Char 145: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * testcmr10C146
     * Char 146: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * testcmr10C147
     * Char 147: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * testcmr10C148
     * Char 148: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * testcmr10C149
     * Char 149: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * testcmr10C150
     * Char 150: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * testcmr10C151
     * Char 151: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * testcmr10C152
     * Char 152: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * testcmr10C153
     * Char 153: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * testcmr10C154
     * Char 154: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * testcmr10C155
     * Char 155: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * testcmr10C156
     * Char 156: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * testcmr10C157
     * Char 157: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * testcmr10C158
     * Char 158: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * testcmr10C159
     * Char 159: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * testcmr10C160
     * Char 160: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * testcmr10C161
     * Char 161: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * testcmr10C162
     * Char 162: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * testcmr10C163
     * Char 163: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * testcmr10C164
     * Char 164: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * testcmr10C165
     * Char 165: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * testcmr10C166
     * Char 166: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * testcmr10C167
     * Char 167: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * testcmr10C168
     * Char 168: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * testcmr10C169
     * Char 169: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * testcmr10C170
     * Char 170: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * testcmr10C171
     * Char 171: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * testcmr10C172
     * Char 172: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * testcmr10C173
     * Char 173: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * testcmr10C174
     * Char 174: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * testcmr10C175
     * Char 175: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * testcmr10C176
     * Char 176: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * testcmr10C177
     * Char 177: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * testcmr10C178
     * Char 178: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * testcmr10C179
     * Char 179: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * testcmr10C180
     * Char 180: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * testcmr10C181
     * Char 181: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * testcmr10C182
     * Char 182: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * testcmr10C183
     * Char 183: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * testcmr10C184
     * Char 184: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * testcmr10C185
     * Char 185: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * testcmr10C186
     * Char 186: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * testcmr10C187
     * Char 187: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * testcmr10C188
     * Char 188: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * testcmr10C189
     * Char 189: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * testcmr10C190
     * Char 190: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * testcmr10C191
     * Char 191: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * testcmr10C192
     * Char 192: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * testcmr10C193
     * Char 193: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * testcmr10C194
     * Char 194: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * testcmr10C195
     * Char 195: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * testcmr10C196
     * Char 196: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * testcmr10C197
     * Char 197: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * testcmr10C198
     * Char 198: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * testcmr10C199
     * Char 199: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * testcmr10C200
     * Char 200: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * testcmr10C201
     * Char 201: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * testcmr10C202
     * Char 202: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * testcmr10C203
     * Char 203: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * testcmr10C204
     * Char 204: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * testcmr10C205
     * Char 205: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * testcmr10C206
     * Char 206: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * testcmr10C207
     * Char 207: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * testcmr10C208
     * Char 208: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * testcmr10C209
     * Char 209: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * testcmr10C210
     * Char 210: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * testcmr10C211
     * Char 211: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * testcmr10C212
     * Char 212: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * testcmr10C213
     * Char 213: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * testcmr10C214
     * Char 214: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * testcmr10C215
     * Char 215: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * testcmr10C216
     * Char 216: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * testcmr10C217
     * Char 217: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * testcmr10C218
     * Char 218: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * testcmr10C219
     * Char 219: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * testcmr10C220
     * Char 220: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * testcmr10C221
     * Char 221: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * testcmr10C222
     * Char 222: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * testcmr10C223
     * Char 223: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * testcmr10C224
     * Char 224: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * testcmr10C225
     * Char 225: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * testcmr10C226
     * Char 226: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * testcmr10C227
     * Char 227: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * testcmr10C228
     * Char 228: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * testcmr10C229
     * Char 229: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * testcmr10C230
     * Char 230: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * testcmr10C231
     * Char 231: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * testcmr10C232
     * Char 232: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * testcmr10C233
     * Char 233: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * testcmr10C234
     * Char 234: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * testcmr10C235
     * Char 235: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * testcmr10C236
     * Char 236: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * testcmr10C237
     * Char 237: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * testcmr10C238
     * Char 238: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * testcmr10C239
     * Char 239: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * testcmr10C240
     * Char 240: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * testcmr10C241
     * Char 241: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * testcmr10C242
     * Char 242: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * testcmr10C243
     * Char 243: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * testcmr10C244
     * Char 244: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * testcmr10C245
     * Char 245: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * testcmr10C246
     * Char 246: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * testcmr10C247
     * Char 247: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * testcmr10C248
     * Char 248: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * testcmr10C249
     * Char 249: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * testcmr10C250
     * Char 250: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * testcmr10C251
     * Char 251: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * testcmr10C252
     * Char 252: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * testcmr10C253
     * Char 253: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * testcmr10C254
     * Char 254: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * testcmr10C255
     * Char 255: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmr10C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * kerning test cmr10
     * Kerning: AV: -72819
     *
     * @throws Exception in case of an error
     */
    public void testKerning1() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('A'), UnicodeChar
                .get('V'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-72819).eq(k));
    }

    /**
     * kerning test cmr10
     * Kerning: Ve: -54614
     *
     * @throws Exception in case of an error
     */
    public void testKerning2() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('V'), UnicodeChar
                .get('e'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-54614).eq(k));
    }

    /**
     * kerning test cmr10
     * Kerning: V,: 0
     *
     * @throws Exception in case of an error
     */
    public void testKerning3() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('V'), UnicodeChar
                .get(','));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(0).eq(k));
    }

    /**
     * kerning test cmr10
     * Kerning: AW: -72819
     *
     * @throws Exception in case of an error
     */
    public void testKerning4() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('A'), UnicodeChar
                .get('W'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-72819).eq(k));
    }

    /**
     * kerning test cmr10
     * Kerning: W.: 0
     *
     * @throws Exception in case of an error
     */
    public void testKerning5() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('W'), UnicodeChar
                .get('.'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(0).eq(k));
    }
}
