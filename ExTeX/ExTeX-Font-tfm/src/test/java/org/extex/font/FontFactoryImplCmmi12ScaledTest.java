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
import java.util.Map;

import org.extex.core.Unicode;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

/**
 * Test for the font factory.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplCmmi12ScaledTest extends AbstractFontFactoryTester {

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
    public FontFactoryImplCmmi12ScaledTest()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            Map<String, Count> map = new HashMap<String, Count>();
            map.put(FontKey.SCALE, new Count(799));
            key = factory.getFontKey("cmmi12", new Dimen(Dimen.ONE * 12), map);
            font = factory.getInstance(key);
        }
    }

    /**
     * Test for the font key.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test01() throws Exception {

        assertNotNull(font);
        assertNotNull(key);
    }

    // ************************************************************

    /**
     * test cmmi12 scaled 799 Char 0: Width=381194, Height=429378, Depth=0,
     * IC=85453
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C0() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(381194)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(85453).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 1: Width=512718, Height=429378, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C1() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(512718)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 10: Width=476065, Height=429378, Depth=0,
     * IC=31709
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C10() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(476065)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(31709).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 100: Width=319703, Height=436359, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C100() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(319703)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 101: Width=285159, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C101() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(285159)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 102: Width=303268, Height=436359,
     * Depth=122180, IC=67089
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C102() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(303268)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(67089).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 103: Width=294612, Height=270543,
     * Depth=122180, IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C103() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(294612)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 104: Width=354175, Height=436359, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C104() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(354175)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 105: Width=209893, Height=411945, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C105() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(209893)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(411945)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 106: Width=254542, Height=411945,
     * Depth=122180, IC=35350
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C106() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(254542)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(411945)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(35350).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 107: Width=319994, Height=436359, Depth=0,
     * IC=21091
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C107() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(319994)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(21091).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 108: Width=183269, Height=436359, Depth=0,
     * IC=13818
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C108() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(183269)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(13818).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 109: Width=538171, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C109() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(538171)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 11: Width=391338, Height=270543, Depth=0,
     * IC=4000
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C11() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(391338)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(4000).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 110: Width=367265, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C110() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(367265)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 111: Width=295775, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C111() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(295775)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 112: Width=308795, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C112() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(308795)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 113: Width=272795, Height=270543,
     * Depth=122180, IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C113() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(272795)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 114: Width=277268, Height=270543, Depth=0,
     * IC=17090
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C114() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(277268)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(17090).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 115: Width=289814, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C115() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(289814)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 116: Width=222177, Height=386491, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C116() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(222177)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(386491)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 117: Width=350175, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C117() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(350175)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 118: Width=297449, Height=270543, Depth=0,
     * IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C118() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(297449)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 119: Width=439810, Height=270543, Depth=0,
     * IC=16909
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C119() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(439810)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(16909).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 12: Width=347339, Height=436359,
     * Depth=122180, IC=34836
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C12() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(347339)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(34836).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 120: Width=349631, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C120() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(349631)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 121: Width=299994, Height=270543,
     * Depth=122180, IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C121() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(299994)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 122: Width=285813, Height=270543, Depth=0,
     * IC=27999
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C122() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(285813)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(27999).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 123: Width=196359, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C123() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(196359)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 124: Width=237451, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C124() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(237451)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 125: Width=391702, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C125() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(391702)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 126: Width=307630, Height=447879, Depth=0,
     * IC=96653
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C126() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(447879)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(96653).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 127: Width=170906, Height=436359, Depth=0,
     * IC=246784
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C127() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(170906)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(246784).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test cmmi12 scaled 799 Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test cmmi12 scaled 799 Char 13: Width=319136, Height=270543,
     * Depth=122180, IC=34181
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C13() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(319136)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(34181).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test cmmi12 scaled 799 Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test cmmi12 scaled 799 Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test cmmi12 scaled 799 Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test cmmi12 scaled 799 Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test cmmi12 scaled 799 Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test cmmi12 scaled 799 Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test cmmi12 scaled 799 Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test cmmi12 scaled 799 Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test cmmi12 scaled 799 Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test cmmi12 scaled 799 Char 14: Width=272503, Height=436359, Depth=0,
     * IC=23563
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C14() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(272503)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(23563).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test cmmi12 scaled 799 Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test cmmi12 scaled 799 Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test cmmi12 scaled 799 Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test cmmi12 scaled 799 Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test cmmi12 scaled 799 Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test cmmi12 scaled 799 Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test cmmi12 scaled 799 Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test cmmi12 scaled 799 Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test cmmi12 scaled 799 Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test cmmi12 scaled 799 Char 15: Width=248432, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C15() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(248432)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test cmmi12 scaled 799 Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test cmmi12 scaled 799 Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test cmmi12 scaled 799 Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test cmmi12 scaled 799 Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test cmmi12 scaled 799 Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test cmmi12 scaled 799 Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test cmmi12 scaled 799 Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test cmmi12 scaled 799 Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test cmmi12 scaled 799 Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test cmmi12 scaled 799 Char 16: Width=268722, Height=436359,
     * Depth=122180, IC=47455
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C16() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(268722)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(47455).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test cmmi12 scaled 799 Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test cmmi12 scaled 799 Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test cmmi12 scaled 799 Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test cmmi12 scaled 799 Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test cmmi12 scaled 799 Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test cmmi12 scaled 799 Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test cmmi12 scaled 799 Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test cmmi12 scaled 799 Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test cmmi12 scaled 799 Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test cmmi12 scaled 799 Char 17: Width=303558, Height=270543,
     * Depth=122180, IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C17() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(303558)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test cmmi12 scaled 799 Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test cmmi12 scaled 799 Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test cmmi12 scaled 799 Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test cmmi12 scaled 799 Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test cmmi12 scaled 799 Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test cmmi12 scaled 799 Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test cmmi12 scaled 799 Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test cmmi12 scaled 799 Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test cmmi12 scaled 799 Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test cmmi12 scaled 799 Char 18: Width=286721, Height=436359, Depth=0,
     * IC=17090
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C18() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(286721)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(17090).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test cmmi12 scaled 799 Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test cmmi12 scaled 799 Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test cmmi12 scaled 799 Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test cmmi12 scaled 799 Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test cmmi12 scaled 799 Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test cmmi12 scaled 799 Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test cmmi12 scaled 799 Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test cmmi12 scaled 799 Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test cmmi12 scaled 799 Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test cmmi12 scaled 799 Char 19: Width=217450, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C19() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(217450)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test cmmi12 scaled 799 Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test cmmi12 scaled 799 Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test cmmi12 scaled 799 Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test cmmi12 scaled 799 Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test cmmi12 scaled 799 Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test cmmi12 scaled 799 Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test cmmi12 scaled 799 Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test cmmi12 scaled 799 Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test cmmi12 scaled 799 Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test cmmi12 scaled 799 Char 2: Width=470202, Height=429378, Depth=0,
     * IC=17090
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C2() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(470202)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(17090).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 20: Width=354175, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C20() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(354175)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test cmmi12 scaled 799 Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test cmmi12 scaled 799 Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test cmmi12 scaled 799 Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test cmmi12 scaled 799 Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test cmmi12 scaled 799 Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test cmmi12 scaled 799 Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test cmmi12 scaled 799 Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test cmmi12 scaled 799 Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test cmmi12 scaled 799 Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test cmmi12 scaled 799 Char 21: Width=358902, Height=436359, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C21() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(358902)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test cmmi12 scaled 799 Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test cmmi12 scaled 799 Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test cmmi12 scaled 799 Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test cmmi12 scaled 799 Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test cmmi12 scaled 799 Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test cmmi12 scaled 799 Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test cmmi12 scaled 799 Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test cmmi12 scaled 799 Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test cmmi12 scaled 799 Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test cmmi12 scaled 799 Char 22: Width=370175, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C22() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(370175)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test cmmi12 scaled 799 Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test cmmi12 scaled 799 Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test cmmi12 scaled 799 Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test cmmi12 scaled 799 Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test cmmi12 scaled 799 Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test cmmi12 scaled 799 Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test cmmi12 scaled 799 Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test cmmi12 scaled 799 Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test cmmi12 scaled 799 Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test cmmi12 scaled 799 Char 23: Width=303995, Height=270543, Depth=0,
     * IC=39636
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C23() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(303995)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(39636).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test cmmi12 scaled 799 Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test cmmi12 scaled 799 Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test cmmi12 scaled 799 Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test cmmi12 scaled 799 Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test cmmi12 scaled 799 Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test cmmi12 scaled 799 Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test cmmi12 scaled 799 Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test cmmi12 scaled 799 Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test cmmi12 scaled 799 Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test cmmi12 scaled 799 Char 24: Width=268722, Height=436359,
     * Depth=122180, IC=30364
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C24() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(268722)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(30364).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test cmmi12 scaled 799 Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test cmmi12 scaled 799 Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test cmmi12 scaled 799 Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test cmmi12 scaled 799 Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test cmmi12 scaled 799 Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test cmmi12 scaled 799 Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test cmmi12 scaled 799 Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test cmmi12 scaled 799 Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test cmmi12 scaled 799 Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test cmmi12 scaled 799 Char 25: Width=349012, Height=270543, Depth=0,
     * IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C25() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(349012)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test cmmi12 scaled 799 Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test cmmi12 scaled 799 Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test cmmi12 scaled 799 Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test cmmi12 scaled 799 Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test cmmi12 scaled 799 Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * test cmmi12 scaled 799 Char 26: Width=317340, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C26() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(317340)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 27: Width=349703, Height=270543, Depth=0,
     * IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C27() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(349703)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 28: Width=267195, Height=270543, Depth=0,
     * IC=70181
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C28() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(267195)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(70181).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 29: Width=331630, Height=270543, Depth=0,
     * IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C29() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(331630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 3: Width=427047, Height=429378, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C3() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(427047)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 30: Width=364138, Height=436359,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C30() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(364138)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 31: Width=385448, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C31() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(385448)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 32: Width=399992, Height=436359,
     * Depth=122180, IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C32() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(399992)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 33: Width=383120, Height=270543, Depth=0,
     * IC=22545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C33() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(383120)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(22545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 34: Width=287922, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C34() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(287922)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 35: Width=362611, Height=436359, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C35() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(362611)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 36: Width=508283, Height=270543, Depth=0,
     * IC=17090
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C36() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(508283)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(17090).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 37: Width=317340, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C37() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(317340)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 38: Width=222540, Height=270543, Depth=61090,
     * IC=50545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C38() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(222540)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(61090)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 39: Width=403046, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C39() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(403046)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 4: Width=457865, Height=429378, Depth=0,
     * IC=47690
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C4() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(457865)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(47690).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 40: Width=615261, Height=226775,
     * Depth=-87404, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C40() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(615261)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(226775)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-87404)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 41: Width=615261, Height=226775,
     * Depth=-87404, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C41() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(615261)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(226775)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-87404)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 42: Width=615261, Height=226775,
     * Depth=-87404, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C42() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(615261)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(226775)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-87404)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 43: Width=615261, Height=226775,
     * Depth=-87404, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C43() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(615261)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(226775)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-87404)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 44: Width=170906, Height=284942,
     * Depth=-29237, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C44() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(170906)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(284942)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-29237)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 45: Width=170906, Height=284942,
     * Depth=-29237, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C45() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(170906)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(284942)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-29237)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 46: Width=307630, Height=292362,
     * Depth=-21818, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C46() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(292362)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-21818)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 47: Width=307630, Height=292362,
     * Depth=-21818, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C47() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(292362)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-21818)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 48: Width=307630, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C48() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 49: Width=307630, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C49() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 5: Width=509774, Height=429378, Depth=0,
     * IC=48545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C5() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509774)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(48545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 50: Width=307630, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C50() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 51: Width=307630, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C51() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 52: Width=307630, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C52() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 53: Width=307630, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C53() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 54: Width=307630, Height=404942, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C54() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(404942)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 55: Width=307630, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C55() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 56: Width=307630, Height=404942, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C56() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(404942)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 57: Width=307630, Height=270543,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C57() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 58: Width=170906, Height=61090, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C58() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(170906)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(61090)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 59: Width=170906, Height=61090, Depth=122180,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C59() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(170906)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(61090)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 6: Width=481192, Height=429378, Depth=0,
     * IC=36836
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C6() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(481192)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(36836).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 60: Width=478536, Height=330936, Depth=16756,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C60() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(478536)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(330936)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(16756)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 61: Width=307630, Height=471269,
     * Depth=157089, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C61() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(471269)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(157089)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 62: Width=478536, Height=330936, Depth=16756,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C62() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(478536)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(330936)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(16756)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 63: Width=307630, Height=292362,
     * Depth=-21818, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C63() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(307630)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(292362)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-21818)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 64: Width=324794, Height=436359, Depth=0,
     * IC=34181
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C64() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(324794)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(34181).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 65: Width=461228, Height=429378, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C65() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(461228)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 66: Width=467410, Height=429378, Depth=0,
     * IC=31709
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C66() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(467410)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(31709).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 67: Width=440189, Height=429378, Depth=0,
     * IC=45126
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C67() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(440189)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(45126).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 68: Width=510843, Height=429378, Depth=0,
     * IC=17090
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C68() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(510843)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(17090).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 69: Width=455447, Height=429378, Depth=0,
     * IC=36836
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C69() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(455447)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(36836).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 7: Width=358902, Height=429378, Depth=0,
     * IC=85453
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C7() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(358902)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(85453).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 70: Width=398285, Height=429378, Depth=0,
     * IC=85453
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C70() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(398285)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(85453).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 71: Width=485315, Height=429378, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C71() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(485315)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 72: Width=509774, Height=429378, Depth=0,
     * IC=48545
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C72() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(509774)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(48545).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 73: Width=271360, Height=429378, Depth=0,
     * IC=49399
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C73() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(271360)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(49399).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 74: Width=340068, Height=429378, Depth=0,
     * IC=58181
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C74() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(340068)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(58181).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 75: Width=523447, Height=429378, Depth=0,
     * IC=45126
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C75() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(523447)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(45126).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 76: Width=418611, Height=429378, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C76() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(418611)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 77: Width=595227, Height=429378, Depth=0,
     * IC=65636
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C77() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(595227)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65636).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 78: Width=492684, Height=429378, Depth=0,
     * IC=65636
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C78() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(492684)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65636).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 79: Width=470202, Height=429378, Depth=0,
     * IC=17090
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C79() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(470202)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(17090).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 8: Width=410174, Height=429378, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C8() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(410174)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 80: Width=396576, Height=429378, Depth=0,
     * IC=85453
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C80() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(396576)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(85453).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 81: Width=487293, Height=429378,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C81() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(487293)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 82: Width=468311, Height=429378, Depth=0,
     * IC=5172
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C82() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(468311)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(5172).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 83: Width=378394, Height=429378, Depth=0,
     * IC=36581
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C83() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(378394)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(36581).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 84: Width=360611, Height=429378, Depth=0,
     * IC=85453
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C84() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(360611)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(85453).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 85: Width=417862, Height=429378, Depth=0,
     * IC=65636
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C85() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(417862)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65636).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 86: Width=358685, Height=429378, Depth=0,
     * IC=136724
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C86() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(358685)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(136724).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 87: Width=580863, Height=429378, Depth=0,
     * IC=85453
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C87() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(580863)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(85453).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 88: Width=510628, Height=429378, Depth=0,
     * IC=49399
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C88() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(510628)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(49399).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 89: Width=356976, Height=429378, Depth=0,
     * IC=136724
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C89() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(356976)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(136724).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 9: Width=375780, Height=429378, Depth=0,
     * IC=68575
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C9() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(375780)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(68575).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 90: Width=421121, Height=429378, Depth=0,
     * IC=45126
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C90() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(421121)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(429378)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(45126).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 91: Width=239268, Height=471269, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C91() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(239268)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(471269)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 92: Width=239268, Height=436359,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C92() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(239268)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 93: Width=239268, Height=436359,
     * Depth=122180, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C93() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(239268)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(122180)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 94: Width=615261, Height=224725,
     * Depth=-89454, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C94() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(615261)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(224725)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-89454)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 95: Width=615261, Height=224725,
     * Depth=-89454, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C95() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(615261)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(224725)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-89454)
            .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 96: Width=258178, Height=436359, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C96() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(258178)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 97: Width=322975, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C97() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(322975)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 98: Width=261594, Height=436359, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C98() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(261594)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436359)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmmi12 scaled 799 Char 99: Width=264794, Height=270543, Depth=0,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmmi12C99() throws Exception {

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
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(264794)
            .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(270543)
            .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

}
