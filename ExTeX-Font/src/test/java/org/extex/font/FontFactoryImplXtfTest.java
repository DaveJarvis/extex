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
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.typesetter.tc.font.impl.NullFont;

/**
 * Test for the font factory (xtf).
 * (At the moment only ttf!)
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplXtfTest extends AbstractFontFactoryTester {

    /**
     * The font key. (only one instance for all tests)
     */
    private static FontKey key;

    /**
     * The font. (only one instance for all tests)
     */
    private static ExtexFont font;

    /**
     * The factory. (only one instance for all tests)
     */
    private static CoreFontFactory factory;

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {

        if (factory == null) {
            factory = makeFontFactory();
            key = factory.getFontKey("Gara");
            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font: name
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);
        assertEquals("Gara", font.getFontName());
    }

    /**
     * Test for the font: key
     * 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        assertNotNull(font.getFontKey());
        assertEquals(key, font.getFontKey());
        assertNotNull(font.getActualFontKey());
        assertEquals(key, font.getFontKey());
        assertEquals("Gara size=10.0pt", font.getActualFontKey().toString());

    }

    /**
     * Test for the font: designsize
     * 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        FixedDimen ds = font.getDesignSize();
        assertNull(ds);

    }

    /**
     * Test for the font: actualsize
     * 
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        assertNotNull(font.getActualSize());
        assertTrue(new Dimen(Dimen.ONE * 10).eq(font.getActualSize()));
    }

    /**
     * Test for the font: ex
     * 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        FixedDimen ex = font.getEx();
        assertNotNull(ex);
        // XHeight 385 (mit ttf2tfm)
        assertTrue(ex.toString(), new Dimen(Dimen.ONE * 10 * 385 / 1000).eq(ex));

    }

    /**
     * Test for the font: em
     * 
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        FixedDimen em = font.getEm();
        assertNotNull(em);
        assertTrue(em.toString(), new Dimen(Dimen.ONE * 10).eq(em));
    }

    /**
     * Test for the font: space
     * 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        FixedGlue v = font.getSpace();
        assertNotNull(v);
        Glue glue = new Glue(Dimen.ONE * 10 * 250 / 1000);
        // ttf2tfm (SPACE D 250)
        assertTrue(v.toString(), glue.eq(v));
    }

    /**
     * Test for the font: font dimen 0
     * 
     * @throws Exception if an error occurred.
     */
    public void test08a() throws Exception {

        FixedDimen fd0 = font.getFontDimen("0");
        assertNull(fd0);
        // assertTrue(fd0.toString(), Dimen.ZERO_PT.eq(fd0));
    }

    /**
     * Test for the font: font dimen 1
     * 
     * @throws Exception if an error occurred.
     */
    public void test08b() throws Exception {

        FixedDimen fd1 = font.getFontDimen("1");
        assertNull(fd1);
        // assertTrue(fd1.toString(), Dimen.ZERO_PT.eq(fd1));
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test09() throws Exception {

        FixedCount sf = font.getScaleFactor();
        assertNotNull(sf);
        assertEquals(1, sf.getValue());

    }

    /**
     * Test for the font: glyph
     * 
     * @throws Exception if an error occurred. //
     */
    public void test10() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);
        assertEquals("Gara", font.getFontName());

        assertTrue(font.hasGlyph(UnicodeChar.get(' ')));
        assertFalse(font.hasGlyph(UnicodeChar.get(65535)));

        FixedGlue wx = font.getWidth(UnicodeChar.get(65535));
        assertNotNull(wx);
        assertTrue(wx.toString(), FixedGlue.ZERO.eq(wx));

        // CHARACTER C A (CHARWD R 677) (CHARHT R 655)
        FixedGlue w = font.getWidth(UnicodeChar.get('A'));
        assertNotNull(w);
        assertTrue(w.toString(), new Glue(Dimen.ONE * 10 * 677 / 1000).eq(w));

        FixedGlue h = font.getHeight(UnicodeChar.get('A'));
        assertNotNull(h);
        assertTrue(h.toString(), new Glue(Dimen.ONE * 10 * 655 / 1000).eq(h));

        FixedGlue d = font.getDepth(UnicodeChar.get('A'));
        assertNotNull(d);
        assertTrue(d.toString(), new Glue(0).eq(d));

    }

    /**
     * Test for the font: kerning 1
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning01() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        FixedDimen k = font.getKerning(null, null);
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: kerning
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning02() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        FixedDimen k = font.getKerning(UnicodeChar.get('@'), null);
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: kerning
     * 
     * @throws Exception if an error occurred. //
     */
    public void testKerning03() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        FixedDimen k = font.getKerning(null, UnicodeChar.get('@'));
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: kerning
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning04() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        FixedDimen k =
                font.getKerning(UnicodeChar.get('@'), UnicodeChar.get('@'));
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: kerning
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning05() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        // (LABEL C A) (KRN C C R -38)
        FixedDimen k =
                font.getKerning(UnicodeChar.get('A'), UnicodeChar.get('C'));
        assertNotNull(k);
        assertTrue(k.toString(), new Dimen(Dimen.ONE * 10 * -38 / 1000).eq(k));

    }

    /**
     * Test for the font: kerning
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning06() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        // (LABEL C A) (KRN C u R -19)
        FixedDimen k =
                font.getKerning(UnicodeChar.get('A'), UnicodeChar.get('u'));
        assertNotNull(k);
        assertTrue(k.toString(), new Dimen(Dimen.ONE * 10 * -19 / 1000).eq(k));

    }

    /**
     * Test for the font: ligature
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature01() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        UnicodeChar l = font.getLigature(null, null);
        assertNull(l);
    }

    /**
     * Test for the font: ligature
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature02() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        UnicodeChar l = font.getLigature(UnicodeChar.get('A'), null);
        assertNull(l);
    }

    /**
     * Test for the font: ligature
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature03() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        UnicodeChar l = font.getLigature(null, UnicodeChar.get('A'));
        assertNull(l);
    }

    /**
     * Test for the font: ligature
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature04() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        UnicodeChar l =
                font.getLigature(UnicodeChar.get('A'), UnicodeChar.get('A'));
        assertNull(l);
    }

    /**
     * Test for the font: ligature
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature05() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);

        UnicodeChar l =
                font.getLigature(UnicodeChar.get('f'), UnicodeChar.get('f'));

        // Font gara has no ligatures!
        assertNull(l);
    }

}
