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

import org.extex.font.exception.FontException;
import org.extex.font.type.other.NullFont;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * Test for the font factory.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplCmr10At12Test extends AbstractFontFactoryTester {

    /**
     * The font.
     */
    private ExtexFont font;

    /**
     * The font key.
     */
    private FontKey key;

    /**
     * Creates a new object.
     *
     * @throws ConfigurationException from the configuration system.
     * @throws FontException if a font error occurred.
     */
    public FontFactoryImplCmr10At12Test() throws ConfigurationException,
            FontException {

        CoreFontFactory factory = makeFontFactory();

        key = factory.getFontKey("cmr10", new Dimen(Dimen.ONE * 12));

        font = factory.getInstance(key);

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

        assertNotNull(font.getActualFontKey());
        assertEquals(key, font.getActualFontKey());
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
        assertTrue(new Dimen(Dimen.ONE * 12).eq(font.getActualSize()));
        //assertEquals(1274110073, font.getCheckSum());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test08() throws Exception {

        FixedCount scale = font.getScaleFactor();
        assertNotNull(scale);
        assertEquals(Dimen.ONE * 12 / 10, scale.getValue());
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test09() throws Exception {

        FixedDimen ex = font.getEx();
        assertNotNull(ex);
        //assertTrue(ex.toString(), new Dimen(338602).eq(ex));
        assertTrue(Long.toString(ex.getValue()), new Dimen(338602).eq(ex));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test10() throws Exception {

        FixedDimen em = font.getEm();
        assertNotNull(em);
        assertTrue(Long.toString(em.getValue()), new Dimen(786434).eq(em));
        assertTrue(em.toString(), new Dimen(786434).eq(em));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test11() throws Exception {

        FixedDimen fd0 = font.getFontDimen("0");
        assertNotNull(fd0);
        assertTrue(fd0.toString(), Dimen.ZERO_PT.eq(fd0));
    }

    /**
     * Test for the font key: cmr10 
     * @throws Exception if an error occurred.
     */
    public void test12() throws Exception {

        FixedDimen fd1 = font.getFontDimen("1");
        assertNotNull(fd1);
        assertTrue(fd1.toString(), Dimen.ZERO_PT.eq(fd1));

    }
}
