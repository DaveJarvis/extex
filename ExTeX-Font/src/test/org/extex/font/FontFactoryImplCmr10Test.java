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

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.exception.FontException;
import org.extex.font.type.other.NullFont;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * Test for the font factory (cmr10).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplCmr10Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplCmr10Test() throws ConfigurationException,
            FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("cmr10");

            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        assertFalse(font instanceof NullFont);
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        assertEquals("cmr10", font.getFontName());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        assertNotNull(font.getFontKey());
        assertEquals(key, font.getFontKey());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        FontKey actualFontKey = font.getActualFontKey();
        assertNotNull(actualFontKey);
        assertEquals(key.getName(), actualFontKey.getName());

        FixedDimen ds = actualFontKey.getDimen("size");
        assertTrue(ds.toString(), new Dimen(Dimen.ONE * 10).eq(ds));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        FixedDimen ds = font.getDesignSize();
        assertNotNull(ds);
        assertTrue(ds.toString(), new Dimen(Dimen.ONE * 10).eq(ds));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        assertNotNull(font.getActualSize());
        assertTrue(new Dimen(Dimen.ONE * 10).eq(font.getActualSize()));
        //assertEquals(1274110073, font.getCheckSum());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test08() throws Exception {

        FixedDimen ex = font.getEx();
        assertNotNull(ex);
        assertTrue(ex.toString(), new Dimen(282168).eq(ex));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test09() throws Exception {

        FixedDimen em = font.getEm();
        assertNotNull(em);
        assertTrue(em.toString(), new Dimen(655361).eq(em));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test10() throws Exception {

        FixedDimen fd0 = font.getFontDimen("0");
        assertNotNull(fd0);
        assertTrue(fd0.toString(), Dimen.ZERO_PT.eq(fd0));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test11() throws Exception {

        FixedDimen fd1 = font.getFontDimen("1");
        assertNotNull(fd1);
        assertTrue(fd1.toString(), Dimen.ZERO_PT.eq(fd1));

    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void testScale01() throws Exception {

        FixedCount scale = font.getScaleFactor();
        assertNotNull(scale);
        // scale = 1 -> 1 * 1000
        assertEquals(1000, scale.getValue());
    }

    // -----------------------------------------------

    /**
     * Test for the font: cmr10 - width 
     * @throws Exception if an error occurred.
     */
    public void testWidth01() throws Exception {

        FixedGlue w = font.getWidth(null);

        assertNotNull(w);
        assertTrue(w.toString(), Glue.ZERO.eq(w));

    }

    /**
     * Test for the font: cmr10 - width 
     * @throws Exception if an error occurred.
     */
    public void testWidth02() throws Exception {

        FixedGlue w = font.getWidth(UnicodeChar.get(0xEE00));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(409601)
                .eq(w));

    }

    /**
     * Test for the font: cmr10 - width 
     * @throws Exception if an error occurred.
     */
    public void testWidth03() throws Exception {

        FixedGlue w = font.getWidth(UnicodeChar.get(0x03a0));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));

    }

    /**
     * Test for the font: cmr10 - width 
     * @throws Exception if an error occurred.
     */
    public void testWidth04() throws Exception {

        FixedGlue w = font.getWidth(UnicodeChar.get(65));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(491521)
                .eq(w));

    }

    // -----------------------------------------------

    /**
     * Test for the font: cmr10 - depth 
     * @throws Exception if an error occurred.
     */
    public void testDepth01() throws Exception {

        FixedGlue w = font.getDepth(null);

        assertNotNull(w);
        assertTrue(w.toString(), Glue.ZERO.eq(w));

    }

    /**
     * Test for the font: cmr10 - depth 
     * @throws Exception if an error occurred.
     */
    public void testDepth02() throws Exception {

        FixedGlue w = font.getDepth(UnicodeChar.get(0xEE00));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(0).eq(w));

    }

    /**
     * Test for the font: cmr10 - depth 
     * @throws Exception if an error occurred.
     */
    public void testDepth03() throws Exception {

        FixedGlue w = font.getDepth(UnicodeChar.get(0x03a0));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(0).eq(w));

    }

    /**
     * Test for the font: cmr10 - depth 
     * @throws Exception if an error occurred.
     */
    public void testDepth04() throws Exception {

        FixedGlue w = font.getDepth(UnicodeChar.get(65));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(0).eq(w));

    }

    // -----------------------------------------------

    /**
     * Test for the font: cmr10 - height 
     * @throws Exception if an error occurred.
     */
    public void testHeight01() throws Exception {

        FixedGlue w = font.getHeight(null);

        assertNotNull(w);
        assertTrue(w.toString(), Glue.ZERO.eq(w));

    }

    /**
     * Test for the font: cmr10 - height 
     * @throws Exception if an error occurred.
     */
    public void testHeight02() throws Exception {

        FixedGlue w = font.getHeight(UnicodeChar.get(0xEE00));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(447828)
                .eq(w));

    }

    /**
     * Test for the font: cmr10 - height 
     * @throws Exception if an error occurred.
     */
    public void testHeight03() throws Exception {

        FixedGlue w = font.getHeight(UnicodeChar.get(0x03a0));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(447828)
                .eq(w));

    }

    /**
     * Test for the font: cmr10 - height 
     * @throws Exception if an error occurred.
     */
    public void testHeight04() throws Exception {

        FixedGlue w = font.getHeight(UnicodeChar.get(65));

        assertNotNull(w);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(447828)
                .eq(w));

    }

    // -----------------------------------------------

    /**
     * Test for the font: cmr10 - italic correction
     * @throws Exception if an error occurred.
     */
    public void testItalic01() throws Exception {

        FixedDimen w = font.getItalicCorrection(null);

        assertNotNull(w);
        assertTrue(w.toString(), Dimen.ZERO_PT.eq(w));

    }

    /**
     * Test for the font: cmr10 - italic correction 
     * @throws Exception if an error occurred.
     */
    public void testItalic02() throws Exception {

        FixedDimen w = font.getItalicCorrection(UnicodeChar.get(0xEE00));

        assertNotNull(w);
        assertTrue(Long.toString(w.getValue()), new Dimen(0).eq(w));

    }

    /**
     * Test for the font: cmr10 - italic correction 
     * @throws Exception if an error occurred.
     */
    public void testItalic03() throws Exception {

        FixedDimen w = font.getItalicCorrection(UnicodeChar.get(0x03a0));

        assertNotNull(w);
        assertTrue(Long.toString(w.getValue()), new Dimen(0).eq(w));

    }

    /**
     * Test for the font: cmr10 - italic correction 
     * @throws Exception if an error occurred.
     */
    public void testItalic04() throws Exception {

        FixedDimen w = font.getItalicCorrection(UnicodeChar.get(86));

        assertNotNull(w);
        assertTrue(Long.toString(w.getValue()), new Dimen(9101).eq(w));

    }

    /**
     * Test for the font: cmr10 - has glyph
     * @throws Exception if an error occurred.
     */
    public void testHasGlyph01() throws Exception {

        assertFalse(font.hasGlyph(null));
    }

    /**
     * Test for the font: cmr10 - has glyph
     * @throws Exception if an error occurred.
     */
    public void testHasGlyph02() throws Exception {

        assertTrue(font.hasGlyph(UnicodeChar.get(86)));
    }

    /**
     * Test for the font: cmr10 - space
     * @throws Exception if an error occurred.
     */
    public void testSpace01() throws Exception {

        FixedGlue g = font.getSpace();

        assertNotNull(g);
        assertTrue(g.toString(), new Glue(new Dimen(218453), new Dimen(109226),
                new Dimen(72818)).eq(g));

    }

    /**
     * Test for the font: cmr10 - kerning
     * @throws Exception if an error occurred.
     */
    public void testKerning01() throws Exception {

        FixedDimen k = font.getKerning(null, null);

        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: cmr10 - kerning
     * @throws Exception if an error occurred.
     */
    public void testKerning02() throws Exception {

        FixedDimen k = font.getKerning(null, UnicodeChar.get('A'));

        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: cmr10 - kerning
     * @throws Exception if an error occurred.
     */
    public void testKerning03() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('A'), UnicodeChar
                .get('A'));

        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: cmr10 - kerning
     * @throws Exception if an error occurred.
     */
    public void testKerning04() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('A'), null);

        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: cmr10 - kerning
     * @throws Exception if an error occurred.
     */
    public void testKerning05() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get('A'), UnicodeChar
                .get('V'));

        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-72819).eq(k));

    }

    /**
     * Test for the font: cmr10 - kerning
     * @throws Exception if an error occurred.
     */
    public void testKerning06() throws Exception {

        FixedDimen k = font.getKerning(UnicodeChar.get(0xee41), UnicodeChar
                .get('V'));

        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-72819).eq(k));

    }

    // ------------------------------------------------------------
    /**
     * Test for the font: cmr10 - Ligature
     * @throws Exception if an error occurred.
     */
    public void testLigature01() throws Exception {

        UnicodeChar uc = font.getLigature(null, null);

        assertNull(uc);

    }

    /**
     * Test for the font: cmr10 - Ligature
     * @throws Exception if an error occurred.
     */
    public void testLigature02() throws Exception {

        UnicodeChar uc = font.getLigature(null, UnicodeChar.get('A'));

        assertNull(uc);

    }

    /**
     * Test for the font: cmr10 - Ligature
     * @throws Exception if an error occurred.
     */
    public void testLigature03() throws Exception {

        UnicodeChar uc = font.getLigature(UnicodeChar.get('A'), UnicodeChar
                .get('A'));

        assertNull(uc);

    }

    /**
     * Test for the font: cmr10 - Ligature
     * @throws Exception if an error occurred.
     */
    public void testLigature04() throws Exception {

        UnicodeChar uc = font.getLigature(UnicodeChar.get('A'), null);

        assertNull(uc);

    }

    /**
     * Test for the font: cmr10 - Ligature
     * @throws Exception if an error occurred.
     */
    public void testLigature05() throws Exception {

        UnicodeChar uc = font.getLigature(UnicodeChar.get('f'), UnicodeChar
                .get('f'));

        assertNotNull(uc);
        assertEquals(0xee0b, uc.getCodePoint());

    }

    /**
     * Test for the font: cmr10 - Ligature
     * @throws Exception if an error occurred.
     */
    public void testLigature06() throws Exception {

        UnicodeChar uc = font.getLigature(UnicodeChar.get(0xee0b), UnicodeChar
                .get('l'));

        assertNotNull(uc);
        assertEquals(0xee0f, uc.getCodePoint());

    }

}
