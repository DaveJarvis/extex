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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
 * Test for the font factory (with font cmr10).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplGenCmr10At5PtTest extends AbstractFontFactoryTester {

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
    public FontFactoryImplGenCmr10At5PtTest()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("cmr10", new Dimen(Dimen.ONE * 5));

            font = factory.getInstance(key);
        }
    }

    /**
     * test 0
     */
    @Test
    public void test0() {

        assertNotNull(key);
        assertNotNull(font);
    }

    /**
     * test cmr10 at 5pt Char 0: Width=204800, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C0() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 0));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(204800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 1: Width=273067, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C1() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 1));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273067)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 10: Width=236658, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C10() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 10));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 100: Width=182045, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C100() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 100));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 101: Width=145635, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C101() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 101));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(145635)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 102: Width=100125, Height=227555, Depth=0,
     * IC=25486
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C102() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 102));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(100125)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(25486).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 103: Width=163840, Height=141084, Depth=63715,
     * IC=4550
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C103() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 103));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4550).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 104: Width=182045, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C104() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 104));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 105: Width=91022, Height=218844, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C105() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 105));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(218844)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 106: Width=100125, Height=218844, Depth=63715,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C106() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 106));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(100125)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(218844)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 107: Width=172943, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C107() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 107));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(172943)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 108: Width=91022, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C108() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 108));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 109: Width=273067, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C109() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 109));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273067)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 11: Width=191147, Height=227555, Depth=0, IC=25486
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C11() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 11));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(191147)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(25486).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 110: Width=182045, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C110() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 110));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 111: Width=163840, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C111() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 111));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 112: Width=182045, Height=141084, Depth=63715,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C112() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 112));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 113: Width=172942, Height=141084, Depth=63715,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C113() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 113));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(172942)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 114: Width=128341, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C114() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 114));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(128341)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 115: Width=129251, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C115() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 115));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(129251)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 116: Width=127431, Height=201549, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C116() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 116));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(127431)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(201549)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 117: Width=182045, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C117() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 117));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 118: Width=172943, Height=141084, Depth=0, IC=4550
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C118() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 118));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(172943)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4550).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 119: Width=236658, Height=141084, Depth=0, IC=4550
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C119() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 119));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4550).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 12: Width=182045, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C12() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 12));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 120: Width=172943, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C120() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 120));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(172943)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 121: Width=172943, Height=141084, Depth=63715,
     * IC=4550
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C121() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 121));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(172943)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4550).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 122: Width=145635, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C122() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 122));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(145635)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 123: Width=163840, Height=141084, Depth=0, IC=9102
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C123() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 123));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9102).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 124: Width=327680, Height=141084, Depth=0, IC=9102
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C124() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 124));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(327680)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(9102).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 125: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C125() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 125));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 126: Width=163840, Height=218844, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C126() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 126));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(218844)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 127: Width=163840, Height=218844, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C127() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 127));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(218844)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test cmr10 at 5pt Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test cmr10 at 5pt Char 13: Width=182045, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C13() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 13));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test cmr10 at 5pt Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test cmr10 at 5pt Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test cmr10 at 5pt Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test cmr10 at 5pt Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test cmr10 at 5pt Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test cmr10 at 5pt Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test cmr10 at 5pt Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test cmr10 at 5pt Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test cmr10 at 5pt Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test cmr10 at 5pt Char 14: Width=273067, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C14() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 14));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273067)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test cmr10 at 5pt Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test cmr10 at 5pt Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test cmr10 at 5pt Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test cmr10 at 5pt Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test cmr10 at 5pt Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test cmr10 at 5pt Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test cmr10 at 5pt Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test cmr10 at 5pt Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test cmr10 at 5pt Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test cmr10 at 5pt Char 15: Width=273067, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C15() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 15));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273067)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test cmr10 at 5pt Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test cmr10 at 5pt Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test cmr10 at 5pt Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test cmr10 at 5pt Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test cmr10 at 5pt Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test cmr10 at 5pt Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test cmr10 at 5pt Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test cmr10 at 5pt Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test cmr10 at 5pt Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test cmr10 at 5pt Char 16: Width=91022, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C16() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 16));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test cmr10 at 5pt Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test cmr10 at 5pt Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test cmr10 at 5pt Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test cmr10 at 5pt Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test cmr10 at 5pt Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test cmr10 at 5pt Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test cmr10 at 5pt Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test cmr10 at 5pt Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test cmr10 at 5pt Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test cmr10 at 5pt Char 17: Width=100125, Height=141084, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C17() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 17));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(100125)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test cmr10 at 5pt Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test cmr10 at 5pt Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test cmr10 at 5pt Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test cmr10 at 5pt Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test cmr10 at 5pt Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test cmr10 at 5pt Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test cmr10 at 5pt Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test cmr10 at 5pt Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test cmr10 at 5pt Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test cmr10 at 5pt Char 18: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C18() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 18));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test cmr10 at 5pt Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test cmr10 at 5pt Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test cmr10 at 5pt Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test cmr10 at 5pt Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test cmr10 at 5pt Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test cmr10 at 5pt Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test cmr10 at 5pt Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test cmr10 at 5pt Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test cmr10 at 5pt Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test cmr10 at 5pt Char 19: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C19() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 19));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test cmr10 at 5pt Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test cmr10 at 5pt Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test cmr10 at 5pt Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test cmr10 at 5pt Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test cmr10 at 5pt Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test cmr10 at 5pt Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test cmr10 at 5pt Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test cmr10 at 5pt Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test cmr10 at 5pt Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test cmr10 at 5pt Char 2: Width=254863, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C2() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 2));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 20: Width=163840, Height=205938, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C20() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 20));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(205938)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test cmr10 at 5pt Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test cmr10 at 5pt Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test cmr10 at 5pt Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test cmr10 at 5pt Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test cmr10 at 5pt Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test cmr10 at 5pt Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test cmr10 at 5pt Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test cmr10 at 5pt Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test cmr10 at 5pt Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test cmr10 at 5pt Char 21: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C21() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 21));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test cmr10 at 5pt Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test cmr10 at 5pt Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test cmr10 at 5pt Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test cmr10 at 5pt Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test cmr10 at 5pt Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test cmr10 at 5pt Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test cmr10 at 5pt Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test cmr10 at 5pt Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test cmr10 at 5pt Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test cmr10 at 5pt Char 22: Width=163840, Height=186049, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C22() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 22));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(186049)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test cmr10 at 5pt Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test cmr10 at 5pt Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test cmr10 at 5pt Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test cmr10 at 5pt Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test cmr10 at 5pt Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test cmr10 at 5pt Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test cmr10 at 5pt Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test cmr10 at 5pt Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test cmr10 at 5pt Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test cmr10 at 5pt Char 23: Width=245760, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C23() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 23));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test cmr10 at 5pt Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test cmr10 at 5pt Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test cmr10 at 5pt Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test cmr10 at 5pt Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test cmr10 at 5pt Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test cmr10 at 5pt Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test cmr10 at 5pt Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test cmr10 at 5pt Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test cmr10 at 5pt Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test cmr10 at 5pt Char 24: Width=145635, Height=0, Depth=55750, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C24() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 24));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(145635)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(55750)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test cmr10 at 5pt Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test cmr10 at 5pt Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test cmr10 at 5pt Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test cmr10 at 5pt Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test cmr10 at 5pt Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test cmr10 at 5pt Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test cmr10 at 5pt Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test cmr10 at 5pt Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test cmr10 at 5pt Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test cmr10 at 5pt Char 25: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C25() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 25));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test cmr10 at 5pt Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test cmr10 at 5pt Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test cmr10 at 5pt Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test cmr10 at 5pt Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test cmr10 at 5pt Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * test cmr10 at 5pt Char 26: Width=236658, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C26() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 26));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 27: Width=254863, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C27() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 27));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 28: Width=163840, Height=172942, Depth=31858, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C28() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 28));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(172942)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(31858)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 29: Width=295823, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C29() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 29));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(295823)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 3: Width=227555, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C3() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 3));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(227555)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 30: Width=332231, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C30() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 30));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(332231)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 31: Width=254863, Height=239843, Depth=15929, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C31() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 31));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(239843)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(15929)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 32: Width=91022, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C32() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 32));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 33: Width=91022, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C33() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 33));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 34: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C34() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 34));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 35: Width=273067, Height=227555, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C35() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 35));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273067)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 36: Width=163840, Height=245760, Depth=18204, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C36() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 36));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(18204)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 37: Width=273067, Height=245760, Depth=18204, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C37() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 37));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(273067)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(18204)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 38: Width=254863, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C38() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 38));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 39: Width=91022, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C39() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 39));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 4: Width=218454, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C4() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 4));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(218454)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 40: Width=127431, Height=245760, Depth=81920, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C40() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 40));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(127431)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(81920)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 41: Width=127431, Height=245760, Depth=81920, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C41() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 41));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(127431)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(81920)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 42: Width=163840, Height=245760, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C42() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 42));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 43: Width=254863, Height=191146, Depth=27306, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C43() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 43));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(191146)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(27306)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 44: Width=91022, Height=34588, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C44() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(34588)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 45: Width=109226, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C45() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 45));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(109226)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 46: Width=91022, Height=34588, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C46() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 46));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(34588)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 47: Width=163840, Height=245760, Depth=81920, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C47() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 47));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(81920)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 48: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C48() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 48));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 49: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C49() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 49));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 5: Width=245760, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C5() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 5));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 50: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C50() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 50));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 51: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C51() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 51));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 52: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C52() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 52));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 53: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C53() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 53));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 54: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C54() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 54));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 55: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C55() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 55));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 56: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C56() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 56));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 57: Width=163840, Height=211171, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C57() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 57));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(211171)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 58: Width=91022, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C58() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 58));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 59: Width=91022, Height=141084, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C59() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 59));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 6: Width=236658, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C6() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 6));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 60: Width=91022, Height=163840, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C60() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 60));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(163840)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 61: Width=254863, Height=120217, Depth=-43623,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C61() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 61));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(120217)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-43623)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 62: Width=154738, Height=163840, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C62() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 62));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(154738)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(163840)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 63: Width=154738, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C63() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 63));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(154738)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 64: Width=254863, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C64() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 64));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 65: Width=245760, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C65() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 65));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 66: Width=232107, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C66() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 66));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(232107)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 67: Width=236658, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C67() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 67));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 68: Width=250311, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C68() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 68));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(250311)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 69: Width=223005, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C69() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 69));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(223005)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 7: Width=254863, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C7() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 7));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 70: Width=213903, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C70() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 70));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(213903)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 71: Width=257138, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C71() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 71));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(257138)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 72: Width=245760, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C72() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 72));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 73: Width=118329, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C73() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 73));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(118329)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 74: Width=168391, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C74() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 74));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(168391)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 75: Width=254863, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C75() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 75));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 76: Width=204800, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C76() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 76));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(204800)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 77: Width=300374, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C77() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 77));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(300374)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 78: Width=245760, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C78() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 78));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 79: Width=254863, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C79() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 79));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 8: Width=236658, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C8() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 8));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 80: Width=223005, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C80() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 80));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(223005)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 81: Width=254863, Height=223914, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C81() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 81));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(63715)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 82: Width=241209, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C82() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 82));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(241209)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 83: Width=182045, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C83() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 83));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 84: Width=236658, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C84() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 84));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(236658)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 85: Width=245760, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C85() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 85));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 86: Width=245760, Height=223914, Depth=0, IC=4550
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C86() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 86));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4550).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 87: Width=336783, Height=223914, Depth=0, IC=4550
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C87() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 87));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(336783)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4550).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 88: Width=245760, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C88() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 88));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 89: Width=245760, Height=223914, Depth=0, IC=8191
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C89() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 89));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(245760)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(8191).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 9: Width=254863, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C9() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 9));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 90: Width=200249, Height=223914, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C90() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 90));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(200249)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(223914)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 91: Width=91022, Height=245760, Depth=81920, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C91() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 91));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(81920)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 92: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C92() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 92));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 93: Width=91022, Height=245760, Depth=81920, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C93() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 93));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(245760)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(81920)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 94: Width=163840, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C94() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 94));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 95: Width=91022, Height=218844, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C95() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 95));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(218844)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 96: Width=91022, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C96() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 96));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(91022)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 97: Width=163840, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C97() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 97));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(163840)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 98: Width=182045, Height=227555, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C98() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 98));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(182045)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(227555)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmr10 at 5pt Char 99: Width=145635, Height=141084, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmr10C99() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 99));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(145635)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(141084)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * kerning test cmr10 at 5pt Kerning: AV: -72819
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testKerning1() throws Exception {

        FixedDimen k =
                font.getKerning(UnicodeChar.get('A'), UnicodeChar.get('V'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-72819).eq(k));
    }

    /**
     * kerning test cmr10 at 5pt Kerning: Ve: -54614
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testKerning2() throws Exception {

        FixedDimen k =
                font.getKerning(UnicodeChar.get('V'), UnicodeChar.get('e'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-54614).eq(k));
    }

    /**
     * kerning test cmr10 at 5pt Kerning: V,: 0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testKerning3() throws Exception {

        FixedDimen k =
                font.getKerning(UnicodeChar.get('V'), UnicodeChar.get(','));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(0).eq(k));
    }

    /**
     * kerning test cmr10 at 5pt Kerning: AW: -72819
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testKerning4() throws Exception {

        FixedDimen k =
                font.getKerning(UnicodeChar.get('A'), UnicodeChar.get('W'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(-72819).eq(k));
    }

    /**
     * kerning test cmr10 at 5pt Kerning: W.: 0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testKerning5() throws Exception {

        FixedDimen k =
                font.getKerning(UnicodeChar.get('W'), UnicodeChar.get('.'));
        assertNotNull(k);
        assertTrue(Long.toString(k.getValue()), new Dimen(0).eq(k));
    }

}
