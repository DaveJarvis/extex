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

import java.util.HashMap;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.font.exception.CorruptFontException;
import org.extex.font.format.NullExtexFont;

/**
 * Test for the font factory.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplTest extends AbstractFontFactoryTester {

    /**
     * Test for the font key: null 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        ExtexFont font = factory.getInstance(null);

        assertTrue(font instanceof NullExtexFont);
    }

    /**
     * Test for the font key: font key null 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey(null);

        ExtexFont font = factory.getInstance(key);

        assertTrue(font instanceof NullExtexFont);
    }

    /**
     * Test for the font key: font key empty 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("");

        ExtexFont font = factory.getInstance(key);

        assertTrue(font instanceof NullExtexFont);
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey((String) null, new Dimen(
                Dimen.ONE * 20));
        ExtexFont font = factory.getInstance(key);

        assertTrue(font instanceof NullExtexFont);
    }

    /**
     * Test for the font: does not exists 
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("undefined");
        ExtexFont font = factory.getInstance(key);

        assertNull(font);
    }

    /**
     * Test for the font: corrupt font 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("empty");

        try {
            ExtexFont font = factory.getInstance(key);
        } catch (CorruptFontException e) {
            assertEquals("Font file is corrupt: empty\n", e
                    .getLocalizedMessage());
        }

    }

    /**
     * Test for the font: corrupt font 
     * @throws Exception if an error occurred.
     */
    public void test08() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        ExtexFont font = factory.getInstance(key);
        assertNotNull(font);

        FontKey akey = font.getActualFontKey();
        assertNotNull(akey);

        FixedDimen dimen = akey.getDimen("size");
        assertTrue(dimen.toString(), new Dimen(Dimen.ONE * 12).eq(dimen));
    }

    // -------------------------------------------------

    /**
     * Test for the font key: null key 
     * @throws Exception if an error occurred.
     */
    public void testKey01() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey(null);

        assertNotNull(key);

        assertEquals(null, key.getName());
        assertEquals(0, key.getBooleanMap().size());
        assertEquals(0, key.getStringMap().size());
        assertEquals(0, key.getDimenMap().size());
    }

    /**
     * Test for the font key: empty key 
     * @throws Exception if an error occurred.
     */
    public void testKey02() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("");

        assertNotNull(key);

        assertEquals(null, key.getName());
        assertEquals(0, key.getBooleanMap().size());
        assertEquals(0, key.getStringMap().size());
        assertEquals(0, key.getDimenMap().size());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void testKey03() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr10");

        assertNotNull(key);

        assertEquals("cmr10", key.getName());
        assertEquals(0, key.getBooleanMap().size());
        assertEquals(0, key.getStringMap().size());
        assertEquals(0, key.getDimenMap().size());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void testKey04() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr10", new Dimen(Dimen.ONE * 20));

        assertNotNull(key);

        assertEquals("cmr10", key.getName());
        assertTrue(new Dimen(Dimen.ONE * 20).eq(key.getDimen("size")));
        assertEquals("cmr10 size=20.0pt", key.toString());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void testKey05() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr10", null);

        assertNotNull(key);

        assertNull(key.getDimen("size"));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void testKey06() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr10", null, new HashMap());
        assertNotNull(key);

        assertNull(key.getDimen("size"));
    }

}
