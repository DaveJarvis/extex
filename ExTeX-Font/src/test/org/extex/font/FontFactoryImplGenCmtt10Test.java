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
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.interpreter.type.glue.FixedGlue;
import org.extex.interpreter.type.glue.Glue;
import org.extex.type.UnicodeChar;
import org.extex.unicode.Unicode;
import org.extex.util.framework.configuration.exception.ConfigurationException;

/**
 * Test for the font factory (with font cmtt10).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplGenCmtt10Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplGenCmtt10Test() throws ConfigurationException,
            FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("cmtt10", new Dimen(Dimen.ONE * 10));

            font = factory.getInstance(key);
        }
    }

    /**
     * test 0
     */
    public void test0() {

        assertNotNull(key);
        assertNotNull(font);
    }

    /**
     * testcmtt10C0
     */
    public void testcmtt10C0() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 0));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 0));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C1
     */
    public void testcmtt10C1() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 1));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 1));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C2
     */
    public void testcmtt10C2() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 2));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 2));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C3
     */
    public void testcmtt10C3() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 3));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 3));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C4
     */
    public void testcmtt10C4() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 4));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 4));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C5
     */
    public void testcmtt10C5() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 5));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 5));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C6
     */
    public void testcmtt10C6() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 6));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 6));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C7
     */
    public void testcmtt10C7() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 7));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 7));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C8
     */
    public void testcmtt10C8() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 8));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 8));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C9
     */
    public void testcmtt10C9() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 9));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 9));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C10
     */
    public void testcmtt10C10() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 10));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 10));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C11
     */
    public void testcmtt10C11() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 11));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 11));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C12
     */
    public void testcmtt10C12() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 12));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 12));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C13
     */
    public void testcmtt10C13() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 13));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 13));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C14
     */
    public void testcmtt10C14() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 14));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 14));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(254861)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C15
     */
    public void testcmtt10C15() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 15));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 15));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(254861)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C16
     */
    public void testcmtt10C16() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 16));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 16));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C17
     */
    public void testcmtt10C17() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 17));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 17));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C18
     */
    public void testcmtt10C18() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 18));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 18));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C19
     */
    public void testcmtt10C19() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 19));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 19));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C20
     */
    public void testcmtt10C20() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 20));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 20));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(370916)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C21
     */
    public void testcmtt10C21() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 21));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 21));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C22
     */
    public void testcmtt10C22() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 22));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 22));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(370641)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C23
     */
    public void testcmtt10C23() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 23));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 23));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C24
     */
    public void testcmtt10C24() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 24));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 24));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(127431)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C25
     */
    public void testcmtt10C25() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 25));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 25));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C26
     */
    public void testcmtt10C26() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 26));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 26));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C27
     */
    public void testcmtt10C27() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 27));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 27));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C28
     */
    public void testcmtt10C28() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 28));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 28));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(354986)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(72818)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C29
     */
    public void testcmtt10C29() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 29));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 29));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C30
     */
    public void testcmtt10C30() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 30));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 30));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C31
     */
    public void testcmtt10C31() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 31));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 31));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(436906)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(36408)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C32
     */
    public void testcmtt10C32() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 32));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 32));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(143815)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(72818)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C33
     */
    public void testcmtt10C33() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 33));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 33));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C34
     */
    public void testcmtt10C34() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 34));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 34));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C35
     */
    public void testcmtt10C35() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 35));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 35));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C36
     */
    public void testcmtt10C36() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 36));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 36));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54613)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C37
     */
    public void testcmtt10C37() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 37));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 37));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54613)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C38
     */
    public void testcmtt10C38() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 38));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 38));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C39
     */
    public void testcmtt10C39() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 39));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 39));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C40
     */
    public void testcmtt10C40() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 40));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 40));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C41
     */
    public void testcmtt10C41() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 41));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 41));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C42
     */
    public void testcmtt10C42() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 42));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 42));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(341333)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C43
     */
    public void testcmtt10C43() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 43));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 43));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(347703)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-52794)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C44
     */
    public void testcmtt10C44() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 44));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 44));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(81920)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(91021)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C45
     */
    public void testcmtt10C45() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 45));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 45));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(347703)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-52794)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C46
     */
    public void testcmtt10C46() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 46));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 46));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(81920)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C47
     */
    public void testcmtt10C47() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 47));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 47));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C48
     */
    public void testcmtt10C48() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 48));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 48));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C49
     */
    public void testcmtt10C49() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 49));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 49));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C50
     */
    public void testcmtt10C50() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 50));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 50));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C51
     */
    public void testcmtt10C51() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 51));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 51));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C52
     */
    public void testcmtt10C52() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 52));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 52));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C53
     */
    public void testcmtt10C53() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 53));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 53));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C54
     */
    public void testcmtt10C54() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 54));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 54));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C55
     */
    public void testcmtt10C55() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 55));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 55));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C56
     */
    public void testcmtt10C56() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 56));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 56));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C57
     */
    public void testcmtt10C57() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 57));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 57));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C58
     */
    public void testcmtt10C58() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 58));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 58));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C59
     */
    public void testcmtt10C59() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 59));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 59));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(91021)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C60
     */
    public void testcmtt10C60() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 60));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 60));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(364088)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-36409)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C61
     */
    public void testcmtt10C61() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 61));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 61));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(272383)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-128115)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C62
     */
    public void testcmtt10C62() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 62));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 62));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(364088)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(-36409)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C63
     */
    public void testcmtt10C63() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 63));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 63));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C64
     */
    public void testcmtt10C64() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 64));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 64));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C65
     */
    public void testcmtt10C65() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 65));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 65));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C66
     */
    public void testcmtt10C66() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 66));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 66));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C67
     */
    public void testcmtt10C67() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 67));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 67));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C68
     */
    public void testcmtt10C68() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 68));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 68));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C69
     */
    public void testcmtt10C69() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 69));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 69));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C70
     */
    public void testcmtt10C70() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 70));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 70));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C71
     */
    public void testcmtt10C71() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 71));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 71));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C72
     */
    public void testcmtt10C72() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 72));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 72));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C73
     */
    public void testcmtt10C73() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 73));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 73));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C74
     */
    public void testcmtt10C74() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 74));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 74));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C75
     */
    public void testcmtt10C75() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 75));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 75));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C76
     */
    public void testcmtt10C76() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 76));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 76));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C77
     */
    public void testcmtt10C77() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 77));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 77));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C78
     */
    public void testcmtt10C78() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 78));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 78));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C79
     */
    public void testcmtt10C79() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 79));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 79));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C80
     */
    public void testcmtt10C80() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 80));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 80));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C81
     */
    public void testcmtt10C81() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 81));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 81));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(91021)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C82
     */
    public void testcmtt10C82() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 82));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 82));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C83
     */
    public void testcmtt10C83() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 83));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 83));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C84
     */
    public void testcmtt10C84() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 84));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 84));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C85
     */
    public void testcmtt10C85() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 85));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 85));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C86
     */
    public void testcmtt10C86() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 86));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 86));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C87
     */
    public void testcmtt10C87() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 87));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 87));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C88
     */
    public void testcmtt10C88() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 88));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 88));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C89
     */
    public void testcmtt10C89() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 89));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 89));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C90
     */
    public void testcmtt10C90() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 90));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 90));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C91
     */
    public void testcmtt10C91() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 91));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 91));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C92
     */
    public void testcmtt10C92() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 92));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 92));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C93
     */
    public void testcmtt10C93() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 93));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 93));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C94
     */
    public void testcmtt10C94() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 94));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 94));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C95
     */
    public void testcmtt10C95() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 95));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 95));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(0).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(62348)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C96
     */
    public void testcmtt10C96() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 96));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 96));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C97
     */
    public void testcmtt10C97() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 97));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 97));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C98
     */
    public void testcmtt10C98() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 98));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 98));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C99
     */
    public void testcmtt10C99() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 99));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 99));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C100
     */
    public void testcmtt10C100() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 100));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 100));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C101
     */
    public void testcmtt10C101() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 101));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 101));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C102
     */
    public void testcmtt10C102() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 102));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 102));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C103
     */
    public void testcmtt10C103() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 103));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 103));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C104
     */
    public void testcmtt10C104() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 104));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 104));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C105
     */
    public void testcmtt10C105() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 105));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 105));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C106
     */
    public void testcmtt10C106() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 106));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 106));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C107
     */
    public void testcmtt10C107() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 107));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 107));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C108
     */
    public void testcmtt10C108() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 108));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 108));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C109
     */
    public void testcmtt10C109() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 109));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 109));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C110
     */
    public void testcmtt10C110() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 110));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 110));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C111
     */
    public void testcmtt10C111() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 111));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 111));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C112
     */
    public void testcmtt10C112() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 112));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 112));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C113
     */
    public void testcmtt10C113() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 113));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 113));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C114
     */
    public void testcmtt10C114() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 114));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 114));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C115
     */
    public void testcmtt10C115() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 115));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 115));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C116
     */
    public void testcmtt10C116() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 116));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 116));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(362791)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C117
     */
    public void testcmtt10C117() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 117));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 117));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C118
     */
    public void testcmtt10C118() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 118));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 118));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C119
     */
    public void testcmtt10C119() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 119));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 119));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C120
     */
    public void testcmtt10C120() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 120));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 120));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C121
     */
    public void testcmtt10C121() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 121));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 121));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(145636)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C122
     */
    public void testcmtt10C122() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 122));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 122));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(282168)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C123
     */
    public void testcmtt10C123() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 123));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 123));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C124
     */
    public void testcmtt10C124() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 124));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 124));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C125
     */
    public void testcmtt10C125() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 125));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 125));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(455111)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(54611)
                .eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C126
     */
    public void testcmtt10C126() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 126));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 126));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C127
     */
    public void testcmtt10C127() throws Exception {

        FixedGlue h = font.getHeight(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue w = font.getWidth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedGlue d = font.getDepth(UnicodeChar.get(Unicode.OFFSET + 127));
        FixedDimen i = font.getItalicCorrection(UnicodeChar.get(Unicode.OFFSET + 127));
        assertNotNull(w);
        assertNotNull(h);
        assertNotNull(d);
        assertNotNull(i);
        assertTrue(Long.toString(w.getLength().getValue()), new Glue(344061)
                .eq(w));
        assertTrue(Long.toString(h.getLength().getValue()), new Glue(400498)
                .eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * testcmtt10C128
     */
    public void testcmtt10C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * testcmtt10C129
     */
    public void testcmtt10C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * testcmtt10C130
     */
    public void testcmtt10C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * testcmtt10C131
     */
    public void testcmtt10C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * testcmtt10C132
     */
    public void testcmtt10C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * testcmtt10C133
     */
    public void testcmtt10C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * testcmtt10C134
     */
    public void testcmtt10C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * testcmtt10C135
     */
    public void testcmtt10C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * testcmtt10C136
     */
    public void testcmtt10C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * testcmtt10C137
     */
    public void testcmtt10C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * testcmtt10C138
     */
    public void testcmtt10C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * testcmtt10C139
     */
    public void testcmtt10C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * testcmtt10C140
     */
    public void testcmtt10C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * testcmtt10C141
     */
    public void testcmtt10C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * testcmtt10C142
     */
    public void testcmtt10C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * testcmtt10C143
     */
    public void testcmtt10C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * testcmtt10C144
     */
    public void testcmtt10C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * testcmtt10C145
     */
    public void testcmtt10C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * testcmtt10C146
     */
    public void testcmtt10C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * testcmtt10C147
     */
    public void testcmtt10C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * testcmtt10C148
     */
    public void testcmtt10C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * testcmtt10C149
     */
    public void testcmtt10C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * testcmtt10C150
     */
    public void testcmtt10C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * testcmtt10C151
     */
    public void testcmtt10C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * testcmtt10C152
     */
    public void testcmtt10C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * testcmtt10C153
     */
    public void testcmtt10C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * testcmtt10C154
     */
    public void testcmtt10C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * testcmtt10C155
     */
    public void testcmtt10C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * testcmtt10C156
     */
    public void testcmtt10C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * testcmtt10C157
     */
    public void testcmtt10C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * testcmtt10C158
     */
    public void testcmtt10C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * testcmtt10C159
     */
    public void testcmtt10C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * testcmtt10C160
     */
    public void testcmtt10C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * testcmtt10C161
     */
    public void testcmtt10C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * testcmtt10C162
     */
    public void testcmtt10C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * testcmtt10C163
     */
    public void testcmtt10C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * testcmtt10C164
     */
    public void testcmtt10C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * testcmtt10C165
     */
    public void testcmtt10C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * testcmtt10C166
     */
    public void testcmtt10C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * testcmtt10C167
     */
    public void testcmtt10C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * testcmtt10C168
     */
    public void testcmtt10C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * testcmtt10C169
     */
    public void testcmtt10C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * testcmtt10C170
     */
    public void testcmtt10C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * testcmtt10C171
     */
    public void testcmtt10C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * testcmtt10C172
     */
    public void testcmtt10C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * testcmtt10C173
     */
    public void testcmtt10C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * testcmtt10C174
     */
    public void testcmtt10C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * testcmtt10C175
     */
    public void testcmtt10C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * testcmtt10C176
     */
    public void testcmtt10C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * testcmtt10C177
     */
    public void testcmtt10C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * testcmtt10C178
     */
    public void testcmtt10C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * testcmtt10C179
     */
    public void testcmtt10C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * testcmtt10C180
     */
    public void testcmtt10C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * testcmtt10C181
     */
    public void testcmtt10C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * testcmtt10C182
     */
    public void testcmtt10C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * testcmtt10C183
     */
    public void testcmtt10C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * testcmtt10C184
     */
    public void testcmtt10C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * testcmtt10C185
     */
    public void testcmtt10C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * testcmtt10C186
     */
    public void testcmtt10C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * testcmtt10C187
     */
    public void testcmtt10C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * testcmtt10C188
     */
    public void testcmtt10C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * testcmtt10C189
     */
    public void testcmtt10C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * testcmtt10C190
     */
    public void testcmtt10C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * testcmtt10C191
     */
    public void testcmtt10C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * testcmtt10C192
     */
    public void testcmtt10C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * testcmtt10C193
     */
    public void testcmtt10C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * testcmtt10C194
     */
    public void testcmtt10C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * testcmtt10C195
     */
    public void testcmtt10C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * testcmtt10C196
     */
    public void testcmtt10C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * testcmtt10C197
     */
    public void testcmtt10C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * testcmtt10C198
     */
    public void testcmtt10C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * testcmtt10C199
     */
    public void testcmtt10C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * testcmtt10C200
     */
    public void testcmtt10C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * testcmtt10C201
     */
    public void testcmtt10C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * testcmtt10C202
     */
    public void testcmtt10C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * testcmtt10C203
     */
    public void testcmtt10C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * testcmtt10C204
     */
    public void testcmtt10C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * testcmtt10C205
     */
    public void testcmtt10C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * testcmtt10C206
     */
    public void testcmtt10C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * testcmtt10C207
     */
    public void testcmtt10C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * testcmtt10C208
     */
    public void testcmtt10C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * testcmtt10C209
     */
    public void testcmtt10C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * testcmtt10C210
     */
    public void testcmtt10C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * testcmtt10C211
     */
    public void testcmtt10C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * testcmtt10C212
     */
    public void testcmtt10C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * testcmtt10C213
     */
    public void testcmtt10C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * testcmtt10C214
     */
    public void testcmtt10C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * testcmtt10C215
     */
    public void testcmtt10C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * testcmtt10C216
     */
    public void testcmtt10C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * testcmtt10C217
     */
    public void testcmtt10C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * testcmtt10C218
     */
    public void testcmtt10C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * testcmtt10C219
     */
    public void testcmtt10C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * testcmtt10C220
     */
    public void testcmtt10C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * testcmtt10C221
     */
    public void testcmtt10C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * testcmtt10C222
     */
    public void testcmtt10C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * testcmtt10C223
     */
    public void testcmtt10C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * testcmtt10C224
     */
    public void testcmtt10C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * testcmtt10C225
     */
    public void testcmtt10C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * testcmtt10C226
     */
    public void testcmtt10C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * testcmtt10C227
     */
    public void testcmtt10C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * testcmtt10C228
     */
    public void testcmtt10C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * testcmtt10C229
     */
    public void testcmtt10C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * testcmtt10C230
     */
    public void testcmtt10C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * testcmtt10C231
     */
    public void testcmtt10C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * testcmtt10C232
     */
    public void testcmtt10C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * testcmtt10C233
     */
    public void testcmtt10C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * testcmtt10C234
     */
    public void testcmtt10C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * testcmtt10C235
     */
    public void testcmtt10C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * testcmtt10C236
     */
    public void testcmtt10C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * testcmtt10C237
     */
    public void testcmtt10C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * testcmtt10C238
     */
    public void testcmtt10C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * testcmtt10C239
     */
    public void testcmtt10C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * testcmtt10C240
     */
    public void testcmtt10C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * testcmtt10C241
     */
    public void testcmtt10C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * testcmtt10C242
     */
    public void testcmtt10C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * testcmtt10C243
     */
    public void testcmtt10C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * testcmtt10C244
     */
    public void testcmtt10C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * testcmtt10C245
     */
    public void testcmtt10C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * testcmtt10C246
     */
    public void testcmtt10C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * testcmtt10C247
     */
    public void testcmtt10C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * testcmtt10C248
     */
    public void testcmtt10C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * testcmtt10C249
     */
    public void testcmtt10C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * testcmtt10C250
     */
    public void testcmtt10C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * testcmtt10C251
     */
    public void testcmtt10C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * testcmtt10C252
     */
    public void testcmtt10C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * testcmtt10C253
     */
    public void testcmtt10C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * testcmtt10C254
     */
    public void testcmtt10C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * testcmtt10C255
     */
    public void testcmtt10C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

}
