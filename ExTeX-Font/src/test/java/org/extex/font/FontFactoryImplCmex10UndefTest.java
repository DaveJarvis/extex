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
import org.extex.typesetter.tc.font.impl.NullFont;

/**
 * Test for the font factory.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplCmex10UndefTest extends AbstractFontFactoryTester {

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
    public FontFactoryImplCmex10UndefTest() throws ConfigurationException,
            FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("cmex10_undef");

            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font key. 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
    }

    /**
     * Test for the font key. 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        assertFalse(font instanceof NullFont);
    }

    /**
     * Test for the font key.
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        assertEquals("cmex10_undef", font.getFontName());
    }

    /**
     * test cmex10_undef
     * Char 0: Width=300375, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC0() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 0));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(300375)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 1: Width=300375, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC1() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 1));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(300375)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 2: Width=273068, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC2() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 2));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273068)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 3: Width=273068, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC3() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 3));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273068)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 4: Width=309476, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC4() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 4));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 5: Width=309476, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC5() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 5));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 6: Width=309476, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC6() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 6));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 7: Width=309476, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC7() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 7));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 8: Width=382295, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC8() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 8));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 9: Width=382295, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC9() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 9));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 10: Width=309476, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC10() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 10));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 11: Width=309476, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC11() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 11));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 12: Width=218453, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC12() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 12));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(218453)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 13: Width=364090, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC13() throws Exception {

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
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 14: Width=378653, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC14() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 14));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(378653)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 15: Width=378653, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC15() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 15));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(378653)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 16: Width=391396, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC16() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 16));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(391396)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 17: Width=391396, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC17() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 17));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(391396)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 18: Width=482420, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC18() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 18));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(482420)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 19: Width=482420, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC19() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 19));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(482420)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 20: Width=345886, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC20() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 20));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 21: Width=345886, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC21() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 21));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 22: Width=382295, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC22() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 22));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 23: Width=382295, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC23() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 23));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 24: Width=382295, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC24() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 24));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 25: Width=382295, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC25() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 25));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 26: Width=491521, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC26() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 26));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 27: Width=491521, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC27() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 27));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 28: Width=491521, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC28() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 28));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 29: Width=491521, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC29() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 29));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 30: Width=684488, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC30() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 30));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(684488)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 31: Width=684488, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC31() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 31));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(684488)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 32: Width=518828, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC32() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 32));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(518828)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 33: Width=518828, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC33() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 33));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(518828)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 34: Width=382295, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC34() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 34));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 35: Width=382295, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC35() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 35));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(382295)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 36: Width=418703, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC36() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 36));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(418703)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 37: Width=418703, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC37() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 37));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(418703)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 38: Width=418703, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC38() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 38));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(418703)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 39: Width=418703, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC39() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 39));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(418703)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 40: Width=527931, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC40() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 40));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(527931)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 41: Width=527931, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC41() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 41));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(527931)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 42: Width=527931, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC42() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 42));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(527931)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 43: Width=527931, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC43() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 43));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(527931)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 44: Width=837406, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC44() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(837406)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 45: Width=837406, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC45() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 45));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(837406)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 46: Width=531571, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC46() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 46));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(531571)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 47: Width=531571, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC47() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 47));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(531571)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 48: Width=573441, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC48() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 48));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(573441)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 49: Width=573441, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC49() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 49));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(573441)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 50: Width=436908, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC50() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 50));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 51: Width=436908, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC51() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 51));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 52: Width=436908, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC52() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 52));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 53: Width=436908, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC53() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 53));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 54: Width=436908, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC54() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 54));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 55: Width=436908, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC55() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 55));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 56: Width=582543, Height=0, Depth=589830, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC56() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 56));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(589830)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 57: Width=582543, Height=0, Depth=589830, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC57() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 57));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(589830)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 58: Width=582543, Height=0, Depth=589830, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC58() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 58));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(589830)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 59: Width=582543, Height=0, Depth=589830, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC59() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 59));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(589830)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 60: Width=582543, Height=0, Depth=1179660, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC60() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 60));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1179660)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 61: Width=582543, Height=0, Depth=1179660, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC61() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 61));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1179660)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 62: Width=582543, Height=0, Depth=196610, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC62() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 62));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(582543)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(196610)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 63: Width=436908, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC63() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 63));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 64: Width=573441, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC64() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 64));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(573441)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 65: Width=573441, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC65() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 65));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(573441)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 66: Width=573441, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC66() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 66));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(573441)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 67: Width=573441, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC67() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 67));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(573441)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 68: Width=400498, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC68() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 68));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(400498)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 69: Width=400498, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC69() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 69));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(400498)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 70: Width=546135, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC70() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 70));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 71: Width=728180, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC71() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 71));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 72: Width=309476, Height=0, Depth=728185, IC=127431
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC72() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 72));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(728185)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(127431).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 73: Width=364090, Height=0, Depth=1456371, IC=291271
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC73() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 73));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1456371)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(291271).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 74: Width=728180, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC74() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 74));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 75: Width=990325, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC75() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 75));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(990325)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 76: Width=728180, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC76() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 76));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 77: Width=990325, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC77() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 77));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(990325)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 78: Width=728180, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC78() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 78));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 79: Width=990325, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC79() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 79));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(990325)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 80: Width=691771, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC80() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 80));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(691771)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 81: Width=618953, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC81() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 81));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(618953)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 82: Width=309476, Height=0, Depth=728185, IC=127431
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC82() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 82));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(728185)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(127431).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 83: Width=546135, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC83() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 83));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 84: Width=546135, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC84() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 84));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 85: Width=546135, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC85() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 85));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 86: Width=546135, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC86() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 86));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 87: Width=546135, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC87() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 87));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(546135)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 88: Width=946633, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC88() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 88));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(946633)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 89: Width=837406, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC89() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 89));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(837406)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 90: Width=364090, Height=0, Depth=1456371, IC=291271
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC90() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 90));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1456371)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(291271).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 91: Width=728180, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC91() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 91));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 92: Width=728180, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC92() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 92));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 93: Width=728180, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC93() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 93));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 94: Width=728180, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC94() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 94));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 95: Width=728180, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC95() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 95));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(728180)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 96: Width=618953, Height=0, Depth=655368, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC96() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 96));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(618953)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(655368)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 97: Width=837406, Height=65536, Depth=983048, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC97() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 97));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(837406)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(65536)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(983048)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 98: Width=364090, Height=473316, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC98() throws Exception {

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
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(473316)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 99: Width=655361, Height=491520, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC99() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 99));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 100: Width=946633, Height=491520, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC100() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 100));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(946633)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 101: Width=364090, Height=473316, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC101() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 101));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364090)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(473316)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 102: Width=655361, Height=491520, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC102() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 102));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 103: Width=946633, Height=491520, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC103() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 103));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(946633)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(491520)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 104: Width=309476, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC104() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 104));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 105: Width=309476, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC105() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 105));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(309476)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 106: Width=345886, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC106() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 106));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 107: Width=345886, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC107() throws Exception {

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
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 108: Width=345886, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC108() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 108));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 109: Width=345886, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC109() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 109));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(345886)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 110: Width=436908, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC110() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 110));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 111: Width=436908, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC111() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 111));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 112: Width=655361, Height=26213, Depth=760226, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC112() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 112));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(760226)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 113: Width=655361, Height=26213, Depth=1153446, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC113() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 113));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1153446)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 114: Width=655361, Height=26213, Depth=1546666, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC114() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 114));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1546666)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 115: Width=655361, Height=26213, Depth=1939886, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC115() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 115));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(655361)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1939886)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 116: Width=691771, Height=0, Depth=1179660, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC116() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 116));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(691771)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(1179660)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 117: Width=691771, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC117() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 117));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(691771)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 118: Width=691771, Height=26213, Depth=367006, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC118() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 118));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(691771)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(26213)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(367006)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 119: Width=509726, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC119() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 119));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 120: Width=436908, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC120() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 120));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 121: Width=436908, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC121() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 121));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(436908)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 122: Width=294915, Height=78641, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC122() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 122));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(294915)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(78641)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 123: Width=294915, Height=78641, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC123() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 123));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(294915)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(78641)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 124: Width=294915, Height=78641, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC124() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 124));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(294915)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(78641)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 125: Width=294915, Height=78641, Depth=0, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC125() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 125));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(294915)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(78641)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 126: Width=509726, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC126() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 126));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 127: Width=509726, Height=0, Depth=393220, IC=0
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC127() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedDimen i = font.getItalicCorrection(UnicodeChar
                .get(Unicode.OFFSET + 127));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509726)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(393220)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmex10_undef
     * Char 128: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test cmex10_undef
     * Char 129: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test cmex10_undef
     * Char 130: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test cmex10_undef
     * Char 131: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test cmex10_undef
     * Char 132: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test cmex10_undef
     * Char 133: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test cmex10_undef
     * Char 134: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test cmex10_undef
     * Char 135: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test cmex10_undef
     * Char 136: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test cmex10_undef
     * Char 137: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test cmex10_undef
     * Char 138: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test cmex10_undef
     * Char 139: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test cmex10_undef
     * Char 140: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test cmex10_undef
     * Char 141: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test cmex10_undef
     * Char 142: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test cmex10_undef
     * Char 143: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test cmex10_undef
     * Char 144: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test cmex10_undef
     * Char 145: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test cmex10_undef
     * Char 146: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test cmex10_undef
     * Char 147: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test cmex10_undef
     * Char 148: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test cmex10_undef
     * Char 149: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test cmex10_undef
     * Char 150: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test cmex10_undef
     * Char 151: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test cmex10_undef
     * Char 152: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test cmex10_undef
     * Char 153: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test cmex10_undef
     * Char 154: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test cmex10_undef
     * Char 155: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test cmex10_undef
     * Char 156: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test cmex10_undef
     * Char 157: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test cmex10_undef
     * Char 158: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test cmex10_undef
     * Char 159: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test cmex10_undef
     * Char 160: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test cmex10_undef
     * Char 161: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test cmex10_undef
     * Char 162: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test cmex10_undef
     * Char 163: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test cmex10_undef
     * Char 164: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test cmex10_undef
     * Char 165: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test cmex10_undef
     * Char 166: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test cmex10_undef
     * Char 167: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test cmex10_undef
     * Char 168: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test cmex10_undef
     * Char 169: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test cmex10_undef
     * Char 170: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test cmex10_undef
     * Char 171: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test cmex10_undef
     * Char 172: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test cmex10_undef
     * Char 173: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test cmex10_undef
     * Char 174: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test cmex10_undef
     * Char 175: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test cmex10_undef
     * Char 176: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test cmex10_undef
     * Char 177: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test cmex10_undef
     * Char 178: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test cmex10_undef
     * Char 179: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test cmex10_undef
     * Char 180: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test cmex10_undef
     * Char 181: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test cmex10_undef
     * Char 182: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test cmex10_undef
     * Char 183: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test cmex10_undef
     * Char 184: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test cmex10_undef
     * Char 185: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test cmex10_undef
     * Char 186: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test cmex10_undef
     * Char 187: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test cmex10_undef
     * Char 188: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test cmex10_undef
     * Char 189: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test cmex10_undef
     * Char 190: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test cmex10_undef
     * Char 191: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test cmex10_undef
     * Char 192: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test cmex10_undef
     * Char 193: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test cmex10_undef
     * Char 194: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test cmex10_undef
     * Char 195: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test cmex10_undef
     * Char 196: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test cmex10_undef
     * Char 197: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test cmex10_undef
     * Char 198: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test cmex10_undef
     * Char 199: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test cmex10_undef
     * Char 200: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test cmex10_undef
     * Char 201: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test cmex10_undef
     * Char 202: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test cmex10_undef
     * Char 203: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test cmex10_undef
     * Char 204: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test cmex10_undef
     * Char 205: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test cmex10_undef
     * Char 206: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test cmex10_undef
     * Char 207: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test cmex10_undef
     * Char 208: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test cmex10_undef
     * Char 209: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test cmex10_undef
     * Char 210: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test cmex10_undef
     * Char 211: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test cmex10_undef
     * Char 212: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test cmex10_undef
     * Char 213: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test cmex10_undef
     * Char 214: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test cmex10_undef
     * Char 215: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test cmex10_undef
     * Char 216: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test cmex10_undef
     * Char 217: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test cmex10_undef
     * Char 218: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test cmex10_undef
     * Char 219: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test cmex10_undef
     * Char 220: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test cmex10_undef
     * Char 221: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test cmex10_undef
     * Char 222: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test cmex10_undef
     * Char 223: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test cmex10_undef
     * Char 224: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test cmex10_undef
     * Char 225: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test cmex10_undef
     * Char 226: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test cmex10_undef
     * Char 227: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test cmex10_undef
     * Char 228: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test cmex10_undef
     * Char 229: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test cmex10_undef
     * Char 230: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test cmex10_undef
     * Char 231: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test cmex10_undef
     * Char 232: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test cmex10_undef
     * Char 233: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test cmex10_undef
     * Char 234: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test cmex10_undef
     * Char 235: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test cmex10_undef
     * Char 236: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test cmex10_undef
     * Char 237: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test cmex10_undef
     * Char 238: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test cmex10_undef
     * Char 239: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test cmex10_undef
     * Char 240: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test cmex10_undef
     * Char 241: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test cmex10_undef
     * Char 242: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test cmex10_undef
     * Char 243: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test cmex10_undef
     * Char 244: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test cmex10_undef
     * Char 245: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test cmex10_undef
     * Char 246: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test cmex10_undef
     * Char 247: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test cmex10_undef
     * Char 248: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test cmex10_undef
     * Char 249: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test cmex10_undef
     * Char 250: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test cmex10_undef
     * Char 251: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test cmex10_undef
     * Char 252: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test cmex10_undef
     * Char 253: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test cmex10_undef
     * Char 254: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test cmex10_undef
     * Char 255: not defined
     *
     * @throws Exception in case of an error
     */
    public void testcmex10_undefC255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

}
