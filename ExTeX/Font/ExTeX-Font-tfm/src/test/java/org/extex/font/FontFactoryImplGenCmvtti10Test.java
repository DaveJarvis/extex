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
 * Test for the font factory (with font cmvtti10).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class FontFactoryImplGenCmvtti10Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplGenCmvtti10Test()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("cmvtti10");

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
     * test cmvtti10 Char 0: Width=475133, Height=400498, Depth=0, IC=90568
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C0() throws Exception {

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
            new Glue(475133).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(90568).eq(i));
    }

    /**
     * test cmvtti10 Char 1: Width=628048, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C1() throws Exception {

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
            new Glue(628048).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 10: Width=551591, Height=400498, Depth=0, IC=55978
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C10() throws Exception {

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
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55978).eq(i));
    }

    /**
     * test cmvtti10 Char 100: Width=398675, Height=400498, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C100() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 101: Width=360446, Height=282168, Depth=0, IC=55068
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C101() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55068).eq(i));
    }

    /**
     * test cmvtti10 Char 102: Width=245760, Height=400498, Depth=145636,
     * IC=128796
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C102() throws Exception {

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
            new Glue(245760).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(128796).eq(i));
    }

    /**
     * test cmvtti10 Char 103: Width=360446, Height=282168, Depth=145636,
     * IC=55068
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C103() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55068).eq(i));
    }

    /**
     * test cmvtti10 Char 104: Width=398675, Height=400498, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C104() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 105: Width=245758, Height=400498, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C105() throws Exception {

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
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 106: Width=245758, Height=400498, Depth=145636,
     * IC=84651
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C106() throws Exception {

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
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(84651).eq(i));
    }

    /**
     * test cmvtti10 Char 107: Width=360446, Height=400498, Depth=0, IC=70541
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C107() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(70541).eq(i));
    }

    /**
     * test cmvtti10 Char 108: Width=207530, Height=400498, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C108() throws Exception {

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
            new Glue(207530).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 109: Width=628048, Height=282168, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C109() throws Exception {

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
            new Glue(628048).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 11: Width=491518, Height=400498, Depth=145636,
     * IC=128796
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C11() throws Exception {

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
            new Glue(491518).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(128796).eq(i));
    }

    /**
     * test cmvtti10 Char 110: Width=436903, Height=282168, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C110() throws Exception {

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
            new Glue(436903).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 111: Width=398675, Height=282168, Depth=0, IC=33906
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C111() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 111));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(33906).eq(i));
    }

    /**
     * test cmvtti10 Char 112: Width=398675, Height=282168, Depth=145636,
     * IC=33906
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C112() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(33906).eq(i));
    }

    /**
     * test cmvtti10 Char 113: Width=360446, Height=282168, Depth=145636,
     * IC=55068
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C113() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55068).eq(i));
    }

    /**
     * test cmvtti10 Char 114: Width=331775, Height=282168, Depth=0, IC=70541
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C114() throws Exception {

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
            new Glue(331775).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(70541).eq(i));
    }

    /**
     * test cmvtti10 Char 115: Width=322216, Height=282168, Depth=0, IC=51426
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C115() throws Exception {

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
            new Glue(322216).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(51426).eq(i));
    }

    /**
     * test cmvtti10 Char 116: Width=264873, Height=400498, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C116() throws Exception {

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
            new Glue(264873).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 117: Width=417790, Height=282168, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C117() throws Exception {

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
            new Glue(417790).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 118: Width=360446, Height=282168, Depth=0, IC=70541
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C118() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(70541).eq(i));
    }

    /**
     * test cmvtti10 Char 119: Width=513361, Height=282168, Depth=0, IC=70541
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C119() throws Exception {

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
            new Glue(513361).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(70541).eq(i));
    }

    /**
     * test cmvtti10 Char 12: Width=453290, Height=400498, Depth=145636,
     * IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C12() throws Exception {

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
            new Glue(453290).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 120: Width=361356, Height=282168, Depth=0, IC=80098
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C120() throws Exception {

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
            new Glue(361356).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(80098).eq(i));
    }

    /**
     * test cmvtti10 Char 121: Width=379561, Height=282168, Depth=145636,
     * IC=55068
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C121() throws Exception {

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
            new Glue(379561).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55068).eq(i));
    }

    /**
     * test cmvtti10 Char 122: Width=322218, Height=282168, Depth=0, IC=93298
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C122() throws Exception {

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
            new Glue(322218).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(93298).eq(i));
    }

    /**
     * test cmvtti10 Char 123: Width=398675, Height=282168, Depth=0, IC=62711
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C123() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(62711).eq(i));
    }

    /**
     * test cmvtti10 Char 124: Width=797350, Height=282168, Depth=0, IC=62711
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C124() throws Exception {

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
            new Glue(797350).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(62711).eq(i));
    }

    /**
     * test cmvtti10 Char 125: Width=398675, Height=400498, Depth=0, IC=61896
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C125() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(61896).eq(i));
    }

    /**
     * test cmvtti10 Char 126: Width=398675, Height=400498, Depth=0, IC=61896
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C126() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(61896).eq(i));
    }

    /**
     * test cmvtti10 Char 127: Width=398675, Height=400498, Depth=0, IC=46878
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C127() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 127));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(46878).eq(i));
    }

    /**
     * test cmvtti10 Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test cmvtti10 Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test cmvtti10 Char 13: Width=472403, Height=400498, Depth=145636,
     * IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C13() throws Exception {

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
            new Glue(472403).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test cmvtti10 Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test cmvtti10 Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test cmvtti10 Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test cmvtti10 Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test cmvtti10 Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test cmvtti10 Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test cmvtti10 Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test cmvtti10 Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test cmvtti10 Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test cmvtti10 Char 14: Width=708606, Height=400498, Depth=145636,
     * IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C14() throws Exception {

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
            new Glue(708606).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test cmvtti10 Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test cmvtti10 Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test cmvtti10 Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test cmvtti10 Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test cmvtti10 Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test cmvtti10 Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test cmvtti10 Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test cmvtti10 Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test cmvtti10 Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test cmvtti10 Char 15: Width=718163, Height=400498, Depth=145636,
     * IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C15() throws Exception {

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
            new Glue(718163).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test cmvtti10 Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test cmvtti10 Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test cmvtti10 Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test cmvtti10 Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test cmvtti10 Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test cmvtti10 Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test cmvtti10 Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test cmvtti10 Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test cmvtti10 Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test cmvtti10 Char 16: Width=245758, Height=282168, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C16() throws Exception {

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
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test cmvtti10 Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test cmvtti10 Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test cmvtti10 Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test cmvtti10 Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test cmvtti10 Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test cmvtti10 Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test cmvtti10 Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test cmvtti10 Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test cmvtti10 Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test cmvtti10 Char 17: Width=264873, Height=282168, Depth=145636,
     * IC=16840
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C17() throws Exception {

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
            new Glue(264873).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(16840).eq(i));
    }

    /**
     * test cmvtti10 Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test cmvtti10 Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test cmvtti10 Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test cmvtti10 Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test cmvtti10 Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test cmvtti10 Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test cmvtti10 Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test cmvtti10 Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test cmvtti10 Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test cmvtti10 Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test cmvtti10 Char 18: Width=398675, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C18() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test cmvtti10 Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test cmvtti10 Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test cmvtti10 Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test cmvtti10 Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test cmvtti10 Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test cmvtti10 Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test cmvtti10 Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test cmvtti10 Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test cmvtti10 Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test cmvtti10 Char 19: Width=398675, Height=400498, Depth=0, IC=42781
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C19() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(42781).eq(i));
    }

    /**
     * test cmvtti10 Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test cmvtti10 Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test cmvtti10 Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test cmvtti10 Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test cmvtti10 Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test cmvtti10 Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test cmvtti10 Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test cmvtti10 Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test cmvtti10 Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test cmvtti10 Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test cmvtti10 Char 2: Width=589820, Height=400498, Depth=0, IC=50971
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C2() throws Exception {

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
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50971).eq(i));
    }

    /**
     * test cmvtti10 Char 20: Width=398675, Height=370916, Depth=0, IC=48583
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C20() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(370916).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(48583).eq(i));
    }

    /**
     * test cmvtti10 Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test cmvtti10 Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test cmvtti10 Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test cmvtti10 Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test cmvtti10 Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test cmvtti10 Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test cmvtti10 Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test cmvtti10 Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test cmvtti10 Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test cmvtti10 Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test cmvtti10 Char 21: Width=398675, Height=400498, Depth=0, IC=62806
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C21() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(62806).eq(i));
    }

    /**
     * test cmvtti10 Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test cmvtti10 Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test cmvtti10 Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test cmvtti10 Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test cmvtti10 Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test cmvtti10 Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test cmvtti10 Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test cmvtti10 Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test cmvtti10 Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test cmvtti10 Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test cmvtti10 Char 22: Width=398675, Height=370641, Depth=0, IC=63988
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C22() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(370641).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(63988).eq(i));
    }

    /**
     * test cmvtti10 Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test cmvtti10 Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test cmvtti10 Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test cmvtti10 Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test cmvtti10 Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test cmvtti10 Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test cmvtti10 Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test cmvtti10 Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test cmvtti10 Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test cmvtti10 Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test cmvtti10 Char 23: Width=591033, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C23() throws Exception {

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
            new Glue(591033).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test cmvtti10 Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test cmvtti10 Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test cmvtti10 Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test cmvtti10 Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test cmvtti10 Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test cmvtti10 Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test cmvtti10 Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test cmvtti10 Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test cmvtti10 Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test cmvtti10 Char 24: Width=360446, Height=0, Depth=127431, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C24() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(127431).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test cmvtti10 Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test cmvtti10 Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test cmvtti10 Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test cmvtti10 Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test cmvtti10 Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test cmvtti10 Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test cmvtti10 Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test cmvtti10 Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test cmvtti10 Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test cmvtti10 Char 25: Width=417791, Height=400498, Depth=145636,
     * IC=51883
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C25() throws Exception {

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
            new Glue(417791).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(51883).eq(i));
    }

    /**
     * test cmvtti10 Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test cmvtti10 Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test cmvtti10 Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test cmvtti10 Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test cmvtti10 Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test cmvtti10 Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * test cmvtti10 Char 26: Width=551591, Height=282168, Depth=0, IC=55068
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C26() throws Exception {

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
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55068).eq(i));
    }

    /**
     * test cmvtti10 Char 27: Width=551591, Height=282168, Depth=0, IC=55068
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C27() throws Exception {

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
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55068).eq(i));
    }

    /**
     * test cmvtti10 Char 28: Width=398675, Height=354986, Depth=72818, IC=70543
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C28() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(354986).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(72818).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(70543).eq(i));
    }

    /**
     * test cmvtti10 Char 29: Width=666278, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C29() throws Exception {

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
            new Glue(666278).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 3: Width=513361, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C3() throws Exception {

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
            new Glue(513361).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 30: Width=742736, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C30() throws Exception {

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
            new Glue(742736).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 31: Width=589820, Height=436906, Depth=36408, IC=50971
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C31() throws Exception {

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
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(436906).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(36408).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50971).eq(i));
    }

    /**
     * test cmvtti10 Char 32: Width=207530, Height=282168, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C32() throws Exception {

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
            new Glue(207530).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 33: Width=245758, Height=400498, Depth=0, IC=56435
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C33() throws Exception {

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
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(56435).eq(i));
    }

    /**
     * test cmvtti10 Char 34: Width=398675, Height=400498, Depth=0, IC=8921
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C34() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(8921).eq(i));
    }

    /**
     * test cmvtti10 Char 35: Width=628048, Height=400498, Depth=0, IC=48981
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C35() throws Exception {

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
            new Glue(628048).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(48981).eq(i));
    }

    /**
     * test cmvtti10 Char 36: Width=569341, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C36() throws Exception {

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
            new Glue(569341).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 37: Width=628048, Height=455111, Depth=54613, IC=20933
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C37() throws Exception {

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
            new Glue(628048).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(455111).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(54613).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(20933).eq(i));
    }

    /**
     * test cmvtti10 Char 38: Width=589820, Height=400498, Depth=0, IC=42781
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C38() throws Exception {

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
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(42781).eq(i));
    }

    /**
     * test cmvtti10 Char 39: Width=245758, Height=400498, Depth=0, IC=56435
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C39() throws Exception {

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
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(56435).eq(i));
    }

    /**
     * test cmvtti10 Char 4: Width=513361, Height=400498, Depth=0, IC=86745
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C4() throws Exception {

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
            new Glue(513361).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(86745).eq(i));
    }

    /**
     * test cmvtti10 Char 40: Width=322216, Height=455111, Depth=54611, IC=94663
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C40() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 40));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(322216).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(455111).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(54611).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(94663).eq(i));
    }

    /**
     * test cmvtti10 Char 41: Width=322216, Height=455111, Depth=54611, IC=30946
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C41() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 41));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(322216).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(455111).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(54611).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(30946).eq(i));
    }

    /**
     * test cmvtti10 Char 42: Width=398675, Height=341333, Depth=0, IC=56661
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C42() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 42));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(341333).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(56661).eq(i));
    }

    /**
     * test cmvtti10 Char 43: Width=589820, Height=429623, Depth=29125, IC=30946
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C43() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 43));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(429623).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(29125).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(30946).eq(i));
    }

    /**
     * test cmvtti10 Char 44: Width=245758, Height=65536, Depth=91021, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C44() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(65536).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(91021).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 45: Width=283988, Height=282168, Depth=0, IC=16156
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C45() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 45));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(283988).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(16156).eq(i));
    }

    /**
     * test cmvtti10 Char 46: Width=245758, Height=65536, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C46() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 46));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(65536).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 47: Width=398675, Height=455111, Depth=54611, IC=94663
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C47() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 47));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(455111).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(54611).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(94663).eq(i));
    }

    /**
     * test cmvtti10 Char 48: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C48() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 48));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 49: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C49() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 49));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 5: Width=551591, Height=400498, Depth=0, IC=89203
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C5() throws Exception {

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
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(89203).eq(i));
    }

    /**
     * test cmvtti10 Char 50: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C50() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 50));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 51: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C51() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 51));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 52: Width=398675, Height=400498, Depth=145636,
     * IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C52() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 52));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 53: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C53() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 53));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 54: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C54() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 54));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 55: Width=398675, Height=400498, Depth=145636,
     * IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C55() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 55));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 56: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C56() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 56));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 57: Width=398675, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C57() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 57));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 58: Width=245758, Height=282168, Depth=0, IC=26851
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C58() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 58));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(26851).eq(i));
    }

    /**
     * test cmvtti10 Char 59: Width=245758, Height=282168, Depth=91021, IC=26851
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C59() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 59));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(91021).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(26851).eq(i));
    }

    /**
     * test cmvtti10 Char 6: Width=551591, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C6() throws Exception {

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
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 60: Width=245758, Height=254861, Depth=145636,
     * IC=20026
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C60() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 60));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(254861).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(20026).eq(i));
    }

    /**
     * test cmvtti10 Char 61: Width=589820, Height=272383, Depth=-128115,
     * IC=48981
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C61() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 61));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(272383).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-128115).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(48981).eq(i));
    }

    /**
     * test cmvtti10 Char 62: Width=398675, Height=254861, Depth=145636, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C62() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 62));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(254861).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(145636).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 63: Width=398675, Height=400498, Depth=0, IC=61896
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C63() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 63));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(61896).eq(i));
    }

    /**
     * test cmvtti10 Char 64: Width=513361, Height=400498, Depth=0, IC=50971
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C64() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 64));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(513361).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50971).eq(i));
    }

    /**
     * test cmvtti10 Char 65: Width=551591, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C65() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 65));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 66: Width=532476, Height=400498, Depth=0, IC=55978
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C66() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 66));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(532476).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55978).eq(i));
    }

    /**
     * test cmvtti10 Char 67: Width=551591, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C67() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 67));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 68: Width=570706, Height=400498, Depth=0, IC=50971
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C68() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 68));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(570706).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50971).eq(i));
    }

    /**
     * test cmvtti10 Char 69: Width=513361, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C69() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 69));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(513361).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 7: Width=589820, Height=400498, Depth=0, IC=60985
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C7() throws Exception {

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
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(60985).eq(i));
    }

    /**
     * test cmvtti10 Char 70: Width=494248, Height=400498, Depth=0, IC=90568
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C70() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 70));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(494248).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(90568).eq(i));
    }

    /**
     * test cmvtti10 Char 71: Width=589820, Height=400498, Depth=0, IC=42781
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C71() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 71));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(42781).eq(i));
    }

    /**
     * test cmvtti10 Char 72: Width=551591, Height=400498, Depth=0, IC=89203
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C72() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 72));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(89203).eq(i));
    }

    /**
     * test cmvtti10 Char 73: Width=283988, Height=400498, Depth=0, IC=90568
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C73() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 73));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(283988).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(90568).eq(i));
    }

    /**
     * test cmvtti10 Char 74: Width=398675, Height=400498, Depth=0, IC=73728
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C74() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 74));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(73728).eq(i));
    }

    /**
     * test cmvtti10 Char 75: Width=570706, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C75() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 75));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(570706).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 76: Width=475133, Height=400498, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C76() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 76));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(475133).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test cmvtti10 Char 77: Width=666278, Height=400498, Depth=0, IC=89203
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C77() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 77));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(666278).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(89203).eq(i));
    }

    /**
     * test cmvtti10 Char 78: Width=551591, Height=400498, Depth=0, IC=89203
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C78() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 78));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(89203).eq(i));
    }

    /**
     * test cmvtti10 Char 79: Width=589820, Height=400498, Depth=0, IC=50971
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C79() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 79));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50971).eq(i));
    }

    /**
     * test cmvtti10 Char 8: Width=551591, Height=400498, Depth=0, IC=30946
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C8() throws Exception {

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
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(30946).eq(i));
    }

    /**
     * test cmvtti10 Char 80: Width=513361, Height=400498, Depth=0, IC=55978
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C80() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 80));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(513361).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(55978).eq(i));
    }

    /**
     * test cmvtti10 Char 81: Width=589820, Height=400498, Depth=91021, IC=50971
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C81() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 81));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(91021).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(50971).eq(i));
    }

    /**
     * test cmvtti10 Char 82: Width=551591, Height=400498, Depth=0, IC=8193
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C82() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 82));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(8193).eq(i));
    }

    /**
     * test cmvtti10 Char 83: Width=436903, Height=400498, Depth=0, IC=61896
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C83() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 83));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(436903).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(61896).eq(i));
    }

    /**
     * test cmvtti10 Char 84: Width=551591, Height=400498, Depth=0, IC=90568
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C84() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 84));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(90568).eq(i));
    }

    /**
     * test cmvtti10 Char 85: Width=551591, Height=400498, Depth=0, IC=89203
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C85() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 85));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(89203).eq(i));
    }

    /**
     * test cmvtti10 Char 86: Width=551591, Height=400498, Depth=0, IC=109681
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C86() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 86));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(109681).eq(i));
    }

    /**
     * test cmvtti10 Char 87: Width=742736, Height=400498, Depth=0, IC=109681
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C87() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 87));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(742736).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(109681).eq(i));
    }

    /**
     * test cmvtti10 Char 88: Width=551591, Height=400498, Depth=0, IC=90568
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C88() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 88));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(90568).eq(i));
    }

    /**
     * test cmvtti10 Char 89: Width=551591, Height=400498, Depth=0, IC=117328
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C89() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 89));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(551591).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(117328).eq(i));
    }

    /**
     * test cmvtti10 Char 9: Width=589820, Height=400498, Depth=0, IC=60985
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C9() throws Exception {

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
            new Glue(589820).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(60985).eq(i));
    }

    /**
     * test cmvtti10 Char 90: Width=475133, Height=400498, Depth=0, IC=81010
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C90() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 90));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(475133).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(81010).eq(i));
    }

    /**
     * test cmvtti10 Char 91: Width=249401, Height=455111, Depth=54611,
     * IC=113778
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C91() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 91));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(249401).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(455111).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(54611).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(113778).eq(i));
    }

    /**
     * test cmvtti10 Char 92: Width=398675, Height=400498, Depth=0, IC=96301
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C92() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 92));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(96301).eq(i));
    }

    /**
     * test cmvtti10 Char 93: Width=249401, Height=455111, Depth=54611, IC=60076
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C93() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 93));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(249401).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(455111).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(54611).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(60076).eq(i));
    }

    /**
     * test cmvtti10 Char 94: Width=398675, Height=400498, Depth=0, IC=41188
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C94() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 94));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(41188).eq(i));
    }

    /**
     * test cmvtti10 Char 95: Width=245758, Height=400498, Depth=0, IC=56435
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C95() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedDimen i =
                font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 95));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()),
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(56435).eq(i));
    }

    /**
     * test cmvtti10 Char 96: Width=245758, Height=400498, Depth=0, IC=56435
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C96() throws Exception {

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
            new Glue(245758).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(56435).eq(i));
    }

    /**
     * test cmvtti10 Char 97: Width=398675, Height=282168, Depth=0, IC=65385
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C97() throws Exception {

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
            new Glue(398675).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(65385).eq(i));
    }

    /**
     * test cmvtti10 Char 98: Width=360446, Height=400498, Depth=0, IC=33906
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C98() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(400498).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(33906).eq(i));
    }

    /**
     * test cmvtti10 Char 99: Width=360446, Height=282168, Depth=0, IC=32313
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testcmvtti10C99() throws Exception {

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
            new Glue(360446).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(282168).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(32313).eq(i));
    }

}
