/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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
 * Test for the font factory (with font lcirclew10).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class FontFactoryImplGenLcirclew10Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplGenLcirclew10Test()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("lcirclew10", new Dimen(Dimen.ONE * 10));

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
     * testlcirclew10C0 Char 0: Width=262143, Height=157286, Depth=104858, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C0() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 0));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(262143).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(157286).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(104858).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C1 Char 1: Width=262143, Height=157286, Depth=104858, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C1() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 1));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(262143).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(157286).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(104858).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C10 Char 10: Width=786431, Height=419431, Depth=367001,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C10() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 10));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(786431).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(419431).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(367001).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C100 Char 100: Width=327680, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C100() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 100));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(327680).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C101 Char 101: Width=393216, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C101() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 101));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(393216).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C102 Char 102: Width=458751, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C102() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 102));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(458751).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C103 Char 103: Width=524288, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C103() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 103));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(524288).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C104 Char 104: Width=589823, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C104() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 104));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589823).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C105 Char 105: Width=655360, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C105() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 105));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(655360).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C106 Char 106: Width=720896, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C106() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 106));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(720896).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C107 Char 107: Width=786431, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C107() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 107));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(786431).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C108 Char 108: Width=851968, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C108() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 108));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(851968).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C109 Char 109: Width=917503, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C109() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 109));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(917503).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C11 Char 11: Width=786431, Height=419431, Depth=367001,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C11() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 11));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(786431).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(419431).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(367001).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C110 Char 110: Width=983040, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C110() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 110));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(983040).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C111 Char 111: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C111() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 111)));
    }

    /**
     * testlcirclew10C112 Char 112: Width=65536, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C112() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 112));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(65536).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C113 Char 113: Width=131071, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C113() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 113));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(131071).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C114 Char 114: Width=196608, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C114() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 114));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(196608).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C115 Char 115: Width=262143, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C115() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 115));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(262143).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C116 Char 116: Width=327680, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C116() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 116));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(327680).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C117 Char 117: Width=393216, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C117() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 117));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(393216).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C118 Char 118: Width=458751, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C118() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 118));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(458751).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C119 Char 119: Width=524288, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C119() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 119));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(524288).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C12 Char 12: Width=1048576, Height=550503, Depth=498073,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C12() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 12));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1048576).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(550503).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(498073).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C120 Char 120: Width=589823, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C120() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 120));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589823).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C121 Char 121: Width=655360, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C121() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 121));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(655360).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C122 Char 122: Width=720896, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C122() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 122));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(720896).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C123 Char 123: Width=786431, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C123() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 123));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(786431).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C124 Char 124: Width=851968, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C124() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 124));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(851968).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C125 Char 125: Width=917503, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C125() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 125));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(917503).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C126 Char 126: Width=983040, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C126() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 126));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(983040).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C127 Char 127: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C127() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 127)));
    }

    /**
     * testlcirclew10C128 Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * testlcirclew10C129 Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * testlcirclew10C13 Char 13: Width=1048576, Height=550503, Depth=498073,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C13() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 13));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1048576).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(550503).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(498073).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C130 Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * testlcirclew10C131 Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * testlcirclew10C132 Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * testlcirclew10C133 Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * testlcirclew10C134 Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * testlcirclew10C135 Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * testlcirclew10C136 Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * testlcirclew10C137 Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * testlcirclew10C138 Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * testlcirclew10C139 Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * testlcirclew10C14 Char 14: Width=1048576, Height=550503, Depth=498073,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C14() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 14));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1048576).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(550503).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(498073).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C140 Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * testlcirclew10C141 Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * testlcirclew10C142 Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * testlcirclew10C143 Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * testlcirclew10C144 Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * testlcirclew10C145 Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * testlcirclew10C146 Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * testlcirclew10C147 Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * testlcirclew10C148 Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * testlcirclew10C149 Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * testlcirclew10C15 Char 15: Width=1048576, Height=550503, Depth=498073,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C15() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 15));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1048576).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(550503).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(498073).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C150 Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * testlcirclew10C151 Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * testlcirclew10C152 Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * testlcirclew10C153 Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * testlcirclew10C154 Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * testlcirclew10C155 Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * testlcirclew10C156 Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * testlcirclew10C157 Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * testlcirclew10C158 Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * testlcirclew10C159 Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * testlcirclew10C16 Char 16: Width=1310720, Height=681575, Depth=629146,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C16() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 16));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1310720).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(681575).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(629146).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C160 Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * testlcirclew10C161 Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * testlcirclew10C162 Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * testlcirclew10C163 Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * testlcirclew10C164 Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * testlcirclew10C165 Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * testlcirclew10C166 Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * testlcirclew10C167 Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * testlcirclew10C168 Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * testlcirclew10C169 Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * testlcirclew10C17 Char 17: Width=1310720, Height=681575, Depth=629146,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C17() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 17));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1310720).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(681575).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(629146).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C170 Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * testlcirclew10C171 Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * testlcirclew10C172 Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * testlcirclew10C173 Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * testlcirclew10C174 Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * testlcirclew10C175 Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * testlcirclew10C176 Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * testlcirclew10C177 Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * testlcirclew10C178 Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * testlcirclew10C179 Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * testlcirclew10C18 Char 18: Width=1310720, Height=681575, Depth=629146,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C18() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 18));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1310720).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(681575).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(629146).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C180 Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * testlcirclew10C181 Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * testlcirclew10C182 Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * testlcirclew10C183 Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * testlcirclew10C184 Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * testlcirclew10C185 Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * testlcirclew10C186 Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * testlcirclew10C187 Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * testlcirclew10C188 Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * testlcirclew10C189 Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * testlcirclew10C19 Char 19: Width=1310720, Height=681575, Depth=629146,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C19() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 19));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1310720).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(681575).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(629146).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C190 Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * testlcirclew10C191 Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * testlcirclew10C192 Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * testlcirclew10C193 Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * testlcirclew10C194 Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * testlcirclew10C195 Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * testlcirclew10C196 Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * testlcirclew10C197 Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * testlcirclew10C198 Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * testlcirclew10C199 Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * testlcirclew10C2 Char 2: Width=262143, Height=157286, Depth=104858, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C2() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 2));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(262143).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(157286).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(104858).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C20 Char 20: Width=1572863, Height=812646, Depth=760218,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C20() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 20));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1572863).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(812646).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(760218).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C200 Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * testlcirclew10C201 Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * testlcirclew10C202 Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * testlcirclew10C203 Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * testlcirclew10C204 Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * testlcirclew10C205 Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * testlcirclew10C206 Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * testlcirclew10C207 Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * testlcirclew10C208 Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * testlcirclew10C209 Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * testlcirclew10C21 Char 21: Width=1572863, Height=812646, Depth=760218,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C21() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 21));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1572863).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(812646).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(760218).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C210 Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * testlcirclew10C211 Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * testlcirclew10C212 Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * testlcirclew10C213 Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * testlcirclew10C214 Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * testlcirclew10C215 Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * testlcirclew10C216 Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * testlcirclew10C217 Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * testlcirclew10C218 Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * testlcirclew10C219 Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * testlcirclew10C22 Char 22: Width=1572863, Height=812646, Depth=760218,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C22() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 22));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1572863).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(812646).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(760218).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C220 Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * testlcirclew10C221 Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * testlcirclew10C222 Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * testlcirclew10C223 Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * testlcirclew10C224 Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * testlcirclew10C225 Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * testlcirclew10C226 Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * testlcirclew10C227 Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * testlcirclew10C228 Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * testlcirclew10C229 Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * testlcirclew10C23 Char 23: Width=1572863, Height=812646, Depth=760218,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C23() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 23));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1572863).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(812646).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(760218).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C230 Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * testlcirclew10C231 Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * testlcirclew10C232 Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * testlcirclew10C233 Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * testlcirclew10C234 Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * testlcirclew10C235 Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * testlcirclew10C236 Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * testlcirclew10C237 Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * testlcirclew10C238 Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * testlcirclew10C239 Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * testlcirclew10C24 Char 24: Width=1835008, Height=943718, Depth=891290,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C24() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 24));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1835008).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(943718).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(891290).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C240 Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * testlcirclew10C241 Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * testlcirclew10C242 Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * testlcirclew10C243 Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * testlcirclew10C244 Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * testlcirclew10C245 Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * testlcirclew10C246 Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * testlcirclew10C247 Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * testlcirclew10C248 Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * testlcirclew10C249 Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * testlcirclew10C25 Char 25: Width=1835008, Height=943718, Depth=891290,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C25() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 25));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1835008).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(943718).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(891290).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C250 Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * testlcirclew10C251 Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * testlcirclew10C252 Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * testlcirclew10C253 Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * testlcirclew10C254 Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * testlcirclew10C255 Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * testlcirclew10C26 Char 26: Width=1835008, Height=943718, Depth=891290,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C26() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 26));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1835008).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(943718).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(891290).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C27 Char 27: Width=1835008, Height=943718, Depth=891290,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C27() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 27));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(1835008).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(943718).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(891290).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C28 Char 28: Width=2097151, Height=1074791, Depth=1022361,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C28() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 28));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2097151).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1074791).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1022361).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C29 Char 29: Width=2097151, Height=1074791, Depth=1022361,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C29() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 29));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2097151).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1074791).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1022361).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C3 Char 3: Width=262143, Height=157286, Depth=104858, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C3() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 3));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(262143).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(157286).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(104858).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C30 Char 30: Width=2097151, Height=1074791, Depth=1022361,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C30() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 30));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2097151).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1074791).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1022361).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C31 Char 31: Width=2097151, Height=1074791, Depth=1022361,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C31() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 31));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2097151).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1074791).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1022361).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C32 Char 32: Width=2359296, Height=1205863, Depth=1153433,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C32() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 32));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2359296).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1205863).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1153433).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C33 Char 33: Width=2359296, Height=1205863, Depth=1153433,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C33() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 33));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2359296).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1205863).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1153433).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C34 Char 34: Width=2359296, Height=1205863, Depth=1153433,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C34() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 34));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2359296).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1205863).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1153433).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C35 Char 35: Width=2359296, Height=1205863, Depth=1153433,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C35() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 35));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2359296).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1205863).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1153433).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C36 Char 36: Width=2621440, Height=1336935, Depth=1284506,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C36() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 36));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2621440).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1336935).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1284506).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C37 Char 37: Width=2621440, Height=1336935, Depth=1284506,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C37() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 37));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2621440).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1336935).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1284506).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C38 Char 38: Width=2621440, Height=1336935, Depth=1284506,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C38() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 38));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2621440).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1336935).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1284506).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C39 Char 39: Width=2621440, Height=1336935, Depth=1284506,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C39() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 39));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(2621440).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(1336935).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(1284506).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C4 Char 4: Width=524288, Height=288358, Depth=235930, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C4() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 4));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(524288).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(288358).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(235930).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C40 Char 40: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C40() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 40)));
    }

    /**
     * testlcirclew10C41 Char 41: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C41() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 41)));
    }

    /**
     * testlcirclew10C42 Char 42: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C42() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 42)));
    }

    /**
     * testlcirclew10C43 Char 43: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C43() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 43)));
    }

    /**
     * testlcirclew10C44 Char 44: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C44() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 44)));
    }

    /**
     * testlcirclew10C45 Char 45: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C45() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 45)));
    }

    /**
     * testlcirclew10C46 Char 46: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C46() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 46)));
    }

    /**
     * testlcirclew10C47 Char 47: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C47() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 47)));
    }

    /**
     * testlcirclew10C48 Char 48: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C48() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 48)));
    }

    /**
     * testlcirclew10C49 Char 49: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C49() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 49)));
    }

    /**
     * testlcirclew10C5 Char 5: Width=524288, Height=288358, Depth=235930, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C5() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 5));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(524288).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(288358).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(235930).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C50 Char 50: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C50() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 50)));
    }

    /**
     * testlcirclew10C51 Char 51: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C51() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 51)));
    }

    /**
     * testlcirclew10C52 Char 52: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C52() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 52)));
    }

    /**
     * testlcirclew10C53 Char 53: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C53() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 53)));
    }

    /**
     * testlcirclew10C54 Char 54: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C54() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 54)));
    }

    /**
     * testlcirclew10C55 Char 55: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C55() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 55)));
    }

    /**
     * testlcirclew10C56 Char 56: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C56() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 56)));
    }

    /**
     * testlcirclew10C57 Char 57: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C57() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 57)));
    }

    /**
     * testlcirclew10C58 Char 58: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C58() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 58)));
    }

    /**
     * testlcirclew10C59 Char 59: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C59() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 59)));
    }

    /**
     * testlcirclew10C6 Char 6: Width=524288, Height=288358, Depth=235930, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C6() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 6));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(524288).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(288358).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(235930).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C60 Char 60: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C60() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 60)));
    }

    /**
     * testlcirclew10C61 Char 61: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C61() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 61)));
    }

    /**
     * testlcirclew10C62 Char 62: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C62() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 62)));
    }

    /**
     * testlcirclew10C63 Char 63: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C63() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 63)));
    }

    /**
     * testlcirclew10C64 Char 64: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C64() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 64)));
    }

    /**
     * testlcirclew10C65 Char 65: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C65() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 65)));
    }

    /**
     * testlcirclew10C66 Char 66: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C66() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 66)));
    }

    /**
     * testlcirclew10C67 Char 67: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C67() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 67)));
    }

    /**
     * testlcirclew10C68 Char 68: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C68() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 68)));
    }

    /**
     * testlcirclew10C69 Char 69: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C69() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 69)));
    }

    /**
     * testlcirclew10C7 Char 7: Width=524288, Height=288358, Depth=235930, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C7() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 7));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(524288).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(288358).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(235930).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C70 Char 70: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C70() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 70)));
    }

    /**
     * testlcirclew10C71 Char 71: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C71() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 71)));
    }

    /**
     * testlcirclew10C72 Char 72: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C72() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 72)));
    }

    /**
     * testlcirclew10C73 Char 73: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C73() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 73)));
    }

    /**
     * testlcirclew10C74 Char 74: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C74() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 74)));
    }

    /**
     * testlcirclew10C75 Char 75: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C75() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 75)));
    }

    /**
     * testlcirclew10C76 Char 76: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C76() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 76)));
    }

    /**
     * testlcirclew10C77 Char 77: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C77() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 77)));
    }

    /**
     * testlcirclew10C78 Char 78: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C78() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 78)));
    }

    /**
     * testlcirclew10C79 Char 79: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C79() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 79)));
    }

    /**
     * testlcirclew10C8 Char 8: Width=786431, Height=419431, Depth=367001, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C8() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 8));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(786431).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(419431).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(367001).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C80 Char 80: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C80() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 80)));
    }

    /**
     * testlcirclew10C81 Char 81: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C81() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 81)));
    }

    /**
     * testlcirclew10C82 Char 82: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C82() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 82)));
    }

    /**
     * testlcirclew10C83 Char 83: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C83() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 83)));
    }

    /**
     * testlcirclew10C84 Char 84: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C84() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 84)));
    }

    /**
     * testlcirclew10C85 Char 85: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C85() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 85)));
    }

    /**
     * testlcirclew10C86 Char 86: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C86() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 86)));
    }

    /**
     * testlcirclew10C87 Char 87: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C87() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 87)));
    }

    /**
     * testlcirclew10C88 Char 88: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C88() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 88)));
    }

    /**
     * testlcirclew10C89 Char 89: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C89() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 89)));
    }

    /**
     * testlcirclew10C9 Char 9: Width=786431, Height=419431, Depth=367001, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C9() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 9));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(786431).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(419431).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(367001).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C90 Char 90: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C90() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 90)));
    }

    /**
     * testlcirclew10C91 Char 91: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C91() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 91)));
    }

    /**
     * testlcirclew10C92 Char 92: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C92() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 92)));
    }

    /**
     * testlcirclew10C93 Char 93: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C93() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 93)));
    }

    /**
     * testlcirclew10C94 Char 94: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C94() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 94)));
    }

    /**
     * testlcirclew10C95 Char 95: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C95() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 95)));
    }

    /**
     * testlcirclew10C96 Char 96: Width=65536, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C96() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 96));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(65536).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C97 Char 97: Width=131071, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C97() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 97));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(131071).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C98 Char 98: Width=196608, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C98() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 98));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(196608).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testlcirclew10C99 Char 99: Width=262143, Height=0, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlcirclew10C99() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 99));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(262143).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

}
