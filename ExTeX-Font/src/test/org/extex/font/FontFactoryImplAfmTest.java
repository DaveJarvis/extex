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

import org.extex.font.type.other.NullFont;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.type.UnicodeChar;

/**
 * Test for the font factory (afm).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplAfmTest extends AbstractFontFactoryTester {

    /**
     * The font key.
     */
    private FontKey key;

    /**
     * The font.
     */
    private ExtexFont font;

    /**
     * Creates a new object.
     * @throws Exception if an error occurred. 
     */
    public FontFactoryImplAfmTest() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        if (key == null) {
            key = factory.getFontKey("fxlr");
            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);
        assertEquals("fxlr", font.getFontName());
    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        assertNotNull(font.getFontKey());
        assertEquals(key, font.getFontKey());
        assertNotNull(font.getActualFontKey());
        assertEquals(key, font.getActualFontKey());

    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        FixedDimen ds = font.getDesignSize();
        assertNull(ds);

    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        assertNotNull(font.getActualSize());
        assertTrue(new Dimen(Dimen.ONE * 10).eq(font.getActualSize()));
    }

    /**
     * Test for the font: fxlr 
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
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        FixedDimen em = font.getEm();
        assertNotNull(em);
        assertTrue(em.toString(), new Dimen(Dimen.ONE * 10).eq(em));
    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        FixedDimen fd0 = font.getFontDimen("0");
        assertNotNull(fd0);
        assertTrue(fd0.toString(), Dimen.ZERO_PT.eq(fd0));
    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test08() throws Exception {

        FixedDimen fd1 = font.getFontDimen("1");
        assertNotNull(fd1);
        assertTrue(fd1.toString(), Dimen.ZERO_PT.eq(fd1));
    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test09() throws Exception {

        FixedCount sf = font.getScaleFactor();
        assertNotNull(sf);
        assertEquals(1, sf.getValue());

    }

    /**
     * Test for the font: fxlr 
     * @throws Exception if an error occurred.
     */
    public void test10() throws Exception {

        assertNotNull(font);
        assertFalse(font instanceof NullFont);
        assertEquals("fxlr", font.getFontName());

        assertTrue(font.hasGlyph(UnicodeChar.get(' ')));
        assertFalse(font.hasGlyph(UnicodeChar.get(65535)));

        FixedGlue wx = font.getWidth(UnicodeChar.get(65535));
        assertNotNull(wx);
        assertTrue(wx.toString(), Glue.ZERO.eq(wx));

        // C 65 ; WX 695 ; N A ; B 2 -1 690 662
        FixedGlue w = font.getWidth(UnicodeChar.get('A'));
        assertNotNull(w);
        assertTrue(w.toString(), new Glue(Dimen.ONE * 10 * 695 / 1000).eq(w));

        FixedGlue h = font.getHeight(UnicodeChar.get('A'));
        assertNotNull(h);
        assertTrue(h.toString(), new Glue(Dimen.ONE * 10 * 662 / 1000).eq(h));

        FixedGlue d = font.getDepth(UnicodeChar.get('A'));
        assertNotNull(d);
        assertTrue(d.toString(), new Glue(Dimen.ONE * 10 * 1 / 1000).eq(d));

    }

}
