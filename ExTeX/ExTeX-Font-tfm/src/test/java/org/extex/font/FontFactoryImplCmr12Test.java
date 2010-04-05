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
import org.junit.Test;

/**
 * Test for the font factory (cmr12).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplCmr12Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplCmr12Test()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();
            key = factory.getFontKey("cmr12");
            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font key: cmr10
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test01() throws Exception {

        assertNotNull(font);
    }

    /**
     * test cmr12 Char 0: Width=481139, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C0() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 0));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(481139)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 1: Width=641700, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C1() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 1));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(641700)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 10: Width=556140, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C10() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 10));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 100: Width=427800, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C100() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 100));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 101: Width=342240, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C101() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 101));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(342240)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 102: Width=235290, Height=546132, Depth=0, IC=54840
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C102() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 102));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(235290)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(54840).eq(i));
    }

    /**
     * test cmr12 Char 103: Width=385020, Height=338603, Depth=152916, IC=10695
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C103() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 103));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(10695).eq(i));
    }

    /**
     * test cmr12 Char 104: Width=427800, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C104() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 104));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 105: Width=213900, Height=521949, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C105() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 105));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(521949)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 106: Width=235290, Height=521949, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C106() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 106));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(235290)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(521949)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 107: Width=406410, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C107() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 107));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(406410)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 108: Width=213900, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C108() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 108));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 109: Width=641700, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C109() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 109));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(641700)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 11: Width=449190, Height=546132, Depth=0, IC=54840
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C11() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 11));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(449190)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(54840).eq(i));
    }

    /**
     * test cmr12 Char 110: Width=427800, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C110() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 110));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 111: Width=385020, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C111() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 111));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 112: Width=427800, Height=338603, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C112() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 112));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 113: Width=406410, Height=338603, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C113() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 113));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(406410)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 114: Width=299460, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C114() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 114));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(299460)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 115: Width=303738, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C115() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 115));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(303738)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 116: Width=299460, Height=483719, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C116() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 116));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(299460)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(483719)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 117: Width=427800, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C117() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 117));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 118: Width=406410, Height=338603, Depth=0, IC=10695
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C118() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 118));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(406410)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(10695).eq(i));
    }

    /**
     * test cmr12 Char 119: Width=556140, Height=338603, Depth=0, IC=10695
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C119() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 119));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(10695).eq(i));
    }

    /**
     * test cmr12 Char 12: Width=427800, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C12() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 12));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 120: Width=406410, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C120() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 120));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(406410)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 121: Width=406410, Height=338603, Depth=152916, IC=10695
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C121() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 121));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(406410)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(10695).eq(i));
    }

    /**
     * test cmr12 Char 122: Width=342240, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C122() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 122));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(342240)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 123: Width=385020, Height=338603, Depth=0, IC=21390
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C123() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 123));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(21390).eq(i));
    }

    /**
     * test cmr12 Char 124: Width=770040, Height=338603, Depth=0, IC=21390
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C124() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 124));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(770040)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(21390).eq(i));
    }

    /**
     * test cmr12 Char 125: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C125() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 125));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 126: Width=385020, Height=521949, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C126() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 126));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(521949)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 127: Width=385020, Height=521949, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C127() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 127));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(521949)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test cmr12 Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test cmr12 Char 13: Width=427800, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C13() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 13));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test cmr12 Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test cmr12 Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test cmr12 Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test cmr12 Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test cmr12 Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test cmr12 Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test cmr12 Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test cmr12 Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test cmr12 Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test cmr12 Char 14: Width=641700, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C14() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 14));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(641700)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test cmr12 Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test cmr12 Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test cmr12 Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test cmr12 Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test cmr12 Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test cmr12 Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test cmr12 Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test cmr12 Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test cmr12 Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test cmr12 Char 15: Width=641700, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C15() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 15));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(641700)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test cmr12 Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test cmr12 Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test cmr12 Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test cmr12 Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test cmr12 Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test cmr12 Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test cmr12 Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test cmr12 Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test cmr12 Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test cmr12 Char 16: Width=213900, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C16() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 16));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test cmr12 Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test cmr12 Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test cmr12 Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test cmr12 Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test cmr12 Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test cmr12 Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test cmr12 Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test cmr12 Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test cmr12 Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test cmr12 Char 17: Width=235290, Height=338603, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C17() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 17));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(235290)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test cmr12 Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test cmr12 Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test cmr12 Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test cmr12 Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test cmr12 Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test cmr12 Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test cmr12 Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test cmr12 Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test cmr12 Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test cmr12 Char 18: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C18() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 18));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test cmr12 Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test cmr12 Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test cmr12 Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test cmr12 Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test cmr12 Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test cmr12 Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test cmr12 Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test cmr12 Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test cmr12 Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test cmr12 Char 19: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C19() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 19));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test cmr12 Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test cmr12 Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test cmr12 Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test cmr12 Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test cmr12 Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test cmr12 Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test cmr12 Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test cmr12 Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test cmr12 Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test cmr12 Char 2: Width=598920, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C2() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 2));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 20: Width=385020, Height=494250, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C20() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 20));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(494250)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test cmr12 Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test cmr12 Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test cmr12 Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test cmr12 Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test cmr12 Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test cmr12 Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test cmr12 Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test cmr12 Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test cmr12 Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test cmr12 Char 21: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C21() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 21));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test cmr12 Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test cmr12 Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test cmr12 Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test cmr12 Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test cmr12 Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test cmr12 Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test cmr12 Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test cmr12 Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test cmr12 Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test cmr12 Char 22: Width=385020, Height=444186, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C22() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 22));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(444186)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test cmr12 Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test cmr12 Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test cmr12 Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test cmr12 Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test cmr12 Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test cmr12 Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test cmr12 Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test cmr12 Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test cmr12 Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test cmr12 Char 23: Width=577257, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C23() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 23));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test cmr12 Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test cmr12 Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test cmr12 Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test cmr12 Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test cmr12 Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test cmr12 Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test cmr12 Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test cmr12 Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test cmr12 Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test cmr12 Char 24: Width=342240, Height=0, Depth=133802, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C24() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 24));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(342240)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(133802)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test cmr12 Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test cmr12 Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test cmr12 Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test cmr12 Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test cmr12 Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test cmr12 Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test cmr12 Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test cmr12 Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test cmr12 Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test cmr12 Char 25: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C25() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 25));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test cmr12 Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test cmr12 Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test cmr12 Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test cmr12 Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test cmr12 Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * test cmr12 Char 26: Width=556140, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C26() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 26));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 27: Width=598920, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C27() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 27));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 28: Width=385020, Height=415062, Depth=76458, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C28() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 28));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(415062)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(76458)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 29: Width=695039, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C29() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 29));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(695039)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 3: Width=534477, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C3() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 3));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(534477)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 30: Width=780599, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C30() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 30));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(780599)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 31: Width=598920, Height=575624, Depth=38229, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C31() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 31));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(575624)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(38229)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 32: Width=213900, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C32() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 32));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 33: Width=213900, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C33() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 33));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 34: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C34() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 34));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 35: Width=641700, Height=546132, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C35() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 35));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(641700)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 36: Width=385020, Height=589824, Depth=43691, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C36() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 36));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(43691)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 37: Width=641700, Height=589824, Depth=43691, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C37() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 37));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(641700)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(43691)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 38: Width=598920, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C38() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 38));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 39: Width=213900, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C39() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 39));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 4: Width=513360, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C4() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 4));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(513360)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 40: Width=299460, Height=589824, Depth=196608, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C40() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 40));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(299460)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(196608)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 41: Width=299460, Height=589824, Depth=196608, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C41() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 41));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(299460)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(196608)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 42: Width=385020, Height=589824, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C42() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 42));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 43: Width=598920, Height=453288, Depth=60072, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C43() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 43));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(453288)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(60072)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 44: Width=213900, Height=76458, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C44() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(76458)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 45: Width=256680, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C45() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 45));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(256680)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 46: Width=213900, Height=76458, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C46() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 46));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(76458)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 47: Width=385020, Height=589824, Depth=196608, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C47() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 47));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(196608)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 48: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C48() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 48));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 49: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C49() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 49));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 5: Width=577257, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C5() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 5));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 50: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C50() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 50));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 51: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C51() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 51));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 52: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C52() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 52));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 53: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C53() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 53));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 54: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C54() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 54));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 55: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C55() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 55));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 56: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C56() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 56));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 57: Width=385020, Height=506811, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C57() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 57));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(506811)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 58: Width=213900, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C58() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 58));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 59: Width=213900, Height=338603, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C59() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 59));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 6: Width=556140, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C6() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 6));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 60: Width=213900, Height=393216, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C60() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 60));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(393216)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 61: Width=598920, Height=283824, Depth=-109392, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C61() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 61));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(283824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-109392)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 62: Width=363630, Height=393216, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C62() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 62));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(363630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(393216)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 63: Width=363630, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C63() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 63));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(363630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 64: Width=598920, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C64() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 64));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 65: Width=577257, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C65() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 65));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 66: Width=545309, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C66() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 66));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(545309)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 67: Width=556140, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C67() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 67));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 68: Width=588089, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C68() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 68));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(588089)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 69: Width=523919, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C69() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 69));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(523919)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 7: Width=598920, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C7() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 7));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 70: Width=502529, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C70() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 70));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(502529)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 71: Width=604200, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C71() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 71));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(604200)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 72: Width=577257, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C72() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 72));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 73: Width=277797, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C73() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 73));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(277797)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 74: Width=395579, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C74() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 74));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(395579)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 75: Width=598647, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C75() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 75));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598647)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 76: Width=481139, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C76() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 76));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(481139)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 77: Width=705597, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C77() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 77));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(705597)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 78: Width=577257, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C78() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 78));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 79: Width=598920, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C79() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 79));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 8: Width=556140, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C8() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 8));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 80: Width=523919, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C80() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 80));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(523919)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 81: Width=598920, Height=537395, Depth=152916, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C81() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 81));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(152916)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 82: Width=566699, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C82() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 82));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(566699)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 83: Width=427800, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C83() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 83));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 84: Width=556140, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C84() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 84));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(556140)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 85: Width=577257, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C85() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 85));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 86: Width=577257, Height=537395, Depth=0, IC=10695
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C86() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 86));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(10695).eq(i));
    }

    /**
     * test cmr12 Char 87: Width=791157, Height=537395, Depth=0, IC=10695
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C87() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 87));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(791157)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(10695).eq(i));
    }

    /**
     * test cmr12 Char 88: Width=577257, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C88() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 88));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 89: Width=577257, Height=537395, Depth=0, IC=19251
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C89() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 89));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(577257)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(19251).eq(i));
    }

    /**
     * test cmr12 Char 9: Width=598920, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C9() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 9));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(598920)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 90: Width=470580, Height=537395, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C90() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 90));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(470580)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(537395)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 91: Width=213900, Height=589824, Depth=196608, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C91() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 91));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(196608)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 92: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C92() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 92));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 93: Width=213900, Height=589824, Depth=196608, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C93() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 93));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(589824)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(196608)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 94: Width=385020, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C94() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 94));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 95: Width=213900, Height=521949, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C95() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 95));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(521949)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 96: Width=213900, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C96() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 96));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213900)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 97: Width=385020, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C97() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 97));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385020)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 98: Width=427800, Height=546132, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C98() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 98));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(546132)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr12 Char 99: Width=342240, Height=338603, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr12C99() throws Exception {

        assertNotNull(font);
        assertNotNull(key);

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 99));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(342240)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(338603)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

}
