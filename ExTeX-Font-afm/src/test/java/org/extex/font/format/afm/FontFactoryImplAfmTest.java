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

package org.extex.font.format.afm;

import java.io.InputStream;

import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.AbstractFontFactoryTester;
import org.extex.font.CoreFontFactory;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.font.fontparameter.FontParameter;
import org.extex.font.unicode.GlyphName;

/**
 * Test for the font factory (afm).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplAfmTest extends AbstractFontFactoryTester {

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
     * The font parameter. (only one instance for all tests)
     */
    private static FontParameter param;

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {

        if (factory == null) {
            factory = makeFontFactory();
            key = factory.getFontKey("fxlr");
            font = factory.getInstance(key);
            InputStream in = finder.findResource("fxlr", "fontinfo");
            param = new FontParameter(in);
        }
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
        assertEquals("fxlr", font.getFontName());
        assertNotNull(param);
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        assertNotNull(font.getFontKey());
        assertEquals(key, font.getFontKey());
        assertNotNull(font.getActualFontKey());
        assertEquals(key, font.getFontKey());
        assertEquals("fxlr size=10.0pt", font.getActualFontKey().toString());

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        FixedDimen ds = font.getDesignSize();
        assertNull(ds);

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        assertNotNull(font.getActualSize());
        assertTrue(new Dimen(Dimen.ONE * 10).eq(font.getActualSize()));
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        FixedDimen ex = font.getEx();
        assertNotNull(ex);
        // afm XHeight 431
        assertTrue(ex.toString(), new Dimen(Dimen.ONE * 10 * 431 / 1000).eq(ex));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        FixedDimen em = font.getEm();
        assertNotNull(em);
        assertTrue(em.toString(), new Dimen(Dimen.ONE * 10).eq(em));
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        FixedDimen fd0 = font.getFontDimen("0");
        assertNotNull(fd0);
        assertTrue(fd0.toString(), Dimen.ZERO_PT.eq(fd0));
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test08() throws Exception {

        FixedDimen fd1 = font.getFontDimen("1");
        assertNotNull(fd1);
        assertTrue(fd1.toString(), Dimen.ZERO_PT.eq(fd1));
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
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void test10() throws Exception {

        assertNotNull(font);
        assertEquals("fxlr", font.getFontName());

        assertTrue(font.hasGlyph(UnicodeChar.get(' ')));
        assertFalse(font.hasGlyph(UnicodeChar.get(65535)));

        FixedGlue wx = font.getWidth(UnicodeChar.get(65535));
        assertNotNull(wx);
        assertTrue(wx.toString(), FixedGlue.ZERO.eq(wx));

        // C 65 ; WX 695 ; N A ; B 2 -1 690 658 ;
        FixedGlue w = font.getWidth(UnicodeChar.get('A'));
        assertNotNull(w);
        assertTrue(w.toString(), new Glue(Dimen.ONE * 10 * 695 / 1000).eq(w));

        FixedGlue h = font.getHeight(UnicodeChar.get('A'));
        assertNotNull(h);
        assertTrue(h.toString(), new Glue(Dimen.ONE * 10 * 658 / 1000).eq(h));

        FixedGlue d = font.getDepth(UnicodeChar.get('A'));
        assertNotNull(d);
        assertTrue(d.toString(), new Glue(Dimen.ONE * 10 * 1 / 1000).eq(d));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning01() throws Exception {

        assertNotNull(font);

        FixedDimen k = font.getKerning(null, null);
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning02() throws Exception {

        assertNotNull(font);

        FixedDimen k = font.getKerning(UnicodeChar.get('@'), null);
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning03() throws Exception {

        assertNotNull(font);

        FixedDimen k = font.getKerning(null, UnicodeChar.get('@'));
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning04() throws Exception {

        assertNotNull(font);

        FixedDimen k =
                font.getKerning(UnicodeChar.get('@'), UnicodeChar.get('@'));
        assertNotNull(k);
        assertTrue(k.toString(), Dimen.ZERO_PT.eq(k));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning05() throws Exception {

        assertNotNull(font);

        // KPX at Alpha -29
        FixedDimen k =
                font.getKerning(UnicodeChar.get('@'), GlyphName.getInstance()
                    .getUnicode("Alpha"));
        assertNotNull(k);
        assertTrue(k.toString(), new Dimen(Dimen.ONE * 10 * -29 / 1000).eq(k));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testKerning06() throws Exception {

        assertNotNull(font);

        // KPX A c -12
        FixedDimen k =
                font.getKerning(UnicodeChar.get('A'), UnicodeChar.get('c'));
        assertNotNull(k);
        assertTrue(k.toString(), new Dimen(Dimen.ONE * 10 * -12 / 1000).eq(k));

    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature01() throws Exception {

        assertNotNull(font);

        UnicodeChar l = font.getLigature(null, null);
        assertNull(l);
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature02() throws Exception {

        assertNotNull(font);

        UnicodeChar l = font.getLigature(UnicodeChar.get('A'), null);
        assertNull(l);
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature03() throws Exception {

        assertNotNull(font);

        UnicodeChar l = font.getLigature(null, UnicodeChar.get('A'));
        assertNull(l);
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature04() throws Exception {

        assertNotNull(font);

        UnicodeChar l =
                font.getLigature(UnicodeChar.get('A'), UnicodeChar.get('A'));
        assertNull(l);
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature05() throws Exception {

        assertNotNull(font);
        UnicodeChar l =
                font.getLigature(UnicodeChar.get('f'), UnicodeChar.get('f'));
        assertNotNull(l);
        assertEquals(param.getUnicode("f_f").getCodePoint(), l.getCodePoint());
    }

    /**
     * Test for the font: fxlr
     * 
     * @throws Exception if an error occurred.
     */
    public void testLigature06() throws Exception {

        assertNotNull(font);

        UnicodeChar l =
                font.getLigature(param.getUnicode("f_f"), UnicodeChar.get('i'));
        assertNotNull(l);
        assertEquals(param.getUnicode("f_f_i").getCodePoint(), l.getCodePoint());

    }

}
