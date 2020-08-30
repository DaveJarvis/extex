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
 * Test for the font factory (with font lasy5).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class FontFactoryImplGenLasy5Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplGenLasy5Test()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("lasy5", new Dimen(Dimen.ONE * 5));

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
     * test lasy5 at 5pt Char 0: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C0() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 0)));
    }

    /**
     * test lasy5 at 5pt Char 1: Width=354991, Height=196908, Depth=33068, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C1() throws Exception {

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
            new Glue(354991).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(196908).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(33068).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 10: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C10() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 10)));
    }

    /**
     * test lasy5 at 5pt Char 100: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C100() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 100)));
    }

    /**
     * test lasy5 at 5pt Char 101: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C101() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 101)));
    }

    /**
     * test lasy5 at 5pt Char 102: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C102() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 102)));
    }

    /**
     * test lasy5 at 5pt Char 103: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C103() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 103)));
    }

    /**
     * test lasy5 at 5pt Char 104: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C104() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 104)));
    }

    /**
     * test lasy5 at 5pt Char 105: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C105() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 105)));
    }

    /**
     * test lasy5 at 5pt Char 106: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C106() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 106)));
    }

    /**
     * test lasy5 at 5pt Char 107: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C107() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 107)));
    }

    /**
     * test lasy5 at 5pt Char 108: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C108() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 108)));
    }

    /**
     * test lasy5 at 5pt Char 109: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C109() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 109)));
    }

    /**
     * test lasy5 at 5pt Char 11: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C11() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 11)));
    }

    /**
     * test lasy5 at 5pt Char 110: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C110() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 110)));
    }

    /**
     * test lasy5 at 5pt Char 111: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C111() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 111)));
    }

    /**
     * test lasy5 at 5pt Char 112: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C112() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 112)));
    }

    /**
     * test lasy5 at 5pt Char 113: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C113() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 113)));
    }

    /**
     * test lasy5 at 5pt Char 114: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C114() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 114)));
    }

    /**
     * test lasy5 at 5pt Char 115: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C115() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 115)));
    }

    /**
     * test lasy5 at 5pt Char 116: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C116() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 116)));
    }

    /**
     * test lasy5 at 5pt Char 117: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C117() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 117)));
    }

    /**
     * test lasy5 at 5pt Char 118: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C118() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 118)));
    }

    /**
     * test lasy5 at 5pt Char 119: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C119() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 119)));
    }

    /**
     * test lasy5 at 5pt Char 12: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C12() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 12)));
    }

    /**
     * test lasy5 at 5pt Char 120: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C120() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 120)));
    }

    /**
     * test lasy5 at 5pt Char 121: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C121() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 121)));
    }

    /**
     * test lasy5 at 5pt Char 122: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C122() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 122)));
    }

    /**
     * test lasy5 at 5pt Char 123: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C123() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 123)));
    }

    /**
     * test lasy5 at 5pt Char 124: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C124() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 124)));
    }

    /**
     * test lasy5 at 5pt Char 125: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C125() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 125)));
    }

    /**
     * test lasy5 at 5pt Char 126: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C126() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 126)));
    }

    /**
     * test lasy5 at 5pt Char 127: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C127() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 127)));
    }

    /**
     * test lasy5 at 5pt Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test lasy5 at 5pt Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test lasy5 at 5pt Char 13: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C13() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 13)));
    }

    /**
     * test lasy5 at 5pt Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test lasy5 at 5pt Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test lasy5 at 5pt Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test lasy5 at 5pt Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test lasy5 at 5pt Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test lasy5 at 5pt Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test lasy5 at 5pt Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test lasy5 at 5pt Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test lasy5 at 5pt Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test lasy5 at 5pt Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test lasy5 at 5pt Char 14: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C14() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 14)));
    }

    /**
     * test lasy5 at 5pt Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test lasy5 at 5pt Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test lasy5 at 5pt Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test lasy5 at 5pt Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test lasy5 at 5pt Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test lasy5 at 5pt Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test lasy5 at 5pt Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test lasy5 at 5pt Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test lasy5 at 5pt Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test lasy5 at 5pt Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test lasy5 at 5pt Char 15: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C15() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 15)));
    }

    /**
     * test lasy5 at 5pt Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test lasy5 at 5pt Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test lasy5 at 5pt Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test lasy5 at 5pt Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test lasy5 at 5pt Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test lasy5 at 5pt Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test lasy5 at 5pt Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test lasy5 at 5pt Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test lasy5 at 5pt Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test lasy5 at 5pt Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test lasy5 at 5pt Char 16: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C16() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 16)));
    }

    /**
     * test lasy5 at 5pt Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test lasy5 at 5pt Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test lasy5 at 5pt Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test lasy5 at 5pt Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test lasy5 at 5pt Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test lasy5 at 5pt Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test lasy5 at 5pt Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test lasy5 at 5pt Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test lasy5 at 5pt Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test lasy5 at 5pt Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test lasy5 at 5pt Char 17: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C17() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 17)));
    }

    /**
     * test lasy5 at 5pt Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test lasy5 at 5pt Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test lasy5 at 5pt Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test lasy5 at 5pt Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test lasy5 at 5pt Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test lasy5 at 5pt Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test lasy5 at 5pt Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test lasy5 at 5pt Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test lasy5 at 5pt Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test lasy5 at 5pt Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test lasy5 at 5pt Char 18: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C18() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 18)));
    }

    /**
     * test lasy5 at 5pt Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test lasy5 at 5pt Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test lasy5 at 5pt Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test lasy5 at 5pt Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test lasy5 at 5pt Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test lasy5 at 5pt Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test lasy5 at 5pt Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test lasy5 at 5pt Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test lasy5 at 5pt Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test lasy5 at 5pt Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test lasy5 at 5pt Char 19: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C19() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 19)));
    }

    /**
     * test lasy5 at 5pt Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test lasy5 at 5pt Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test lasy5 at 5pt Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test lasy5 at 5pt Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test lasy5 at 5pt Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test lasy5 at 5pt Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test lasy5 at 5pt Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test lasy5 at 5pt Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test lasy5 at 5pt Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test lasy5 at 5pt Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test lasy5 at 5pt Char 2: Width=354991, Height=235706, Depth=71866, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C2() throws Exception {

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
            new Glue(354991).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(235706).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(71866).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 20: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C20() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 20)));
    }

    /**
     * test lasy5 at 5pt Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test lasy5 at 5pt Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test lasy5 at 5pt Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test lasy5 at 5pt Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test lasy5 at 5pt Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test lasy5 at 5pt Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test lasy5 at 5pt Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test lasy5 at 5pt Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test lasy5 at 5pt Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test lasy5 at 5pt Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test lasy5 at 5pt Char 21: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C21() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 21)));
    }

    /**
     * test lasy5 at 5pt Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test lasy5 at 5pt Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test lasy5 at 5pt Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test lasy5 at 5pt Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test lasy5 at 5pt Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test lasy5 at 5pt Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test lasy5 at 5pt Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test lasy5 at 5pt Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test lasy5 at 5pt Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test lasy5 at 5pt Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test lasy5 at 5pt Char 22: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C22() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 22)));
    }

    /**
     * test lasy5 at 5pt Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test lasy5 at 5pt Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test lasy5 at 5pt Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test lasy5 at 5pt Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test lasy5 at 5pt Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test lasy5 at 5pt Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test lasy5 at 5pt Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test lasy5 at 5pt Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test lasy5 at 5pt Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test lasy5 at 5pt Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test lasy5 at 5pt Char 23: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C23() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 23)));
    }

    /**
     * test lasy5 at 5pt Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test lasy5 at 5pt Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test lasy5 at 5pt Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test lasy5 at 5pt Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test lasy5 at 5pt Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test lasy5 at 5pt Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test lasy5 at 5pt Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test lasy5 at 5pt Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test lasy5 at 5pt Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test lasy5 at 5pt Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test lasy5 at 5pt Char 24: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C24() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 24)));
    }

    /**
     * test lasy5 at 5pt Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test lasy5 at 5pt Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test lasy5 at 5pt Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test lasy5 at 5pt Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test lasy5 at 5pt Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test lasy5 at 5pt Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test lasy5 at 5pt Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test lasy5 at 5pt Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test lasy5 at 5pt Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test lasy5 at 5pt Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test lasy5 at 5pt Char 25: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C25() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 25)));
    }

    /**
     * test lasy5 at 5pt Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test lasy5 at 5pt Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test lasy5 at 5pt Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test lasy5 at 5pt Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test lasy5 at 5pt Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test lasy5 at 5pt Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * test lasy5 at 5pt Char 26: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C26() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 26)));
    }

    /**
     * test lasy5 at 5pt Char 27: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C27() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 27)));
    }

    /**
     * test lasy5 at 5pt Char 28: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C28() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 28)));
    }

    /**
     * test lasy5 at 5pt Char 29: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C29() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 29)));
    }

    /**
     * test lasy5 at 5pt Char 3: Width=354991, Height=196908, Depth=33068, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C3() throws Exception {

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
            new Glue(354991).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(196908).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(33068).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 30: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C30() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 30)));
    }

    /**
     * test lasy5 at 5pt Char 31: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C31() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 31)));
    }

    /**
     * test lasy5 at 5pt Char 32: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C32() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 32)));
    }

    /**
     * test lasy5 at 5pt Char 33: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C33() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 33)));
    }

    /**
     * test lasy5 at 5pt Char 34: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C34() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 34)));
    }

    /**
     * test lasy5 at 5pt Char 35: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C35() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 35)));
    }

    /**
     * test lasy5 at 5pt Char 36: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C36() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 36)));
    }

    /**
     * test lasy5 at 5pt Char 37: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C37() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 37)));
    }

    /**
     * test lasy5 at 5pt Char 38: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C38() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 38)));
    }

    /**
     * test lasy5 at 5pt Char 39: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C39() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 39)));
    }

    /**
     * test lasy5 at 5pt Char 4: Width=354991, Height=235706, Depth=71866, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C4() throws Exception {

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
            new Glue(354991).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(235706).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(71866).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 40: Width=172944, Height=129894, Depth=-33946,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C40() throws Exception {

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
            new Glue(172944).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(129894).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-33946).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 41: Width=172944, Height=129894, Depth=-33946,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C41() throws Exception {

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
            new Glue(172944).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(129894).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-33946).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 42: Width=241211, Height=227555, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C42() throws Exception {

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
            new Glue(241211).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(227555).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(63715).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 43: Width=241211, Height=227555, Depth=63715, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C43() throws Exception {

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
            new Glue(241211).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(227555).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(63715).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 44: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C44() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 44)));
    }

    /**
     * test lasy5 at 5pt Char 45: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C45() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 45)));
    }

    /**
     * test lasy5 at 5pt Char 46: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C46() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 46)));
    }

    /**
     * test lasy5 at 5pt Char 47: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C47() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 47)));
    }

    /**
     * test lasy5 at 5pt Char 48: Width=332235, Height=223915, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C48() throws Exception {

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
            new Glue(332235).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(223915).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 49: Width=332235, Height=161565, Depth=-2275, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C49() throws Exception {

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
            new Glue(332235).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(161565).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-2275).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 5: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C5() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 5)));
    }

    /**
     * test lasy5 at 5pt Char 50: Width=299465, Height=161565, Depth=-2275, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C50() throws Exception {

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
            new Glue(299465).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(161565).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-2275).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 51: Width=304928, Height=161565, Depth=-2275, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C51() throws Exception {

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
            new Glue(304928).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(161565).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-2275).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 52: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C52() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 52)));
    }

    /**
     * test lasy5 at 5pt Char 53: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C53() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 53)));
    }

    /**
     * test lasy5 at 5pt Char 54: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C54() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 54)));
    }

    /**
     * test lasy5 at 5pt Char 55: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C55() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 55)));
    }

    /**
     * test lasy5 at 5pt Char 56: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C56() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 56)));
    }

    /**
     * test lasy5 at 5pt Char 57: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C57() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 57)));
    }

    /**
     * test lasy5 at 5pt Char 58: Width=309480, Height=129894, Depth=-33946,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C58() throws Exception {

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
            new Glue(309480).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(129894).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-33946).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 59: Width=446015, Height=129894, Depth=-33946,
     * IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C59() throws Exception {

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
            new Glue(446015).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(129894).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(-33946).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 6: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C6() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 6)));
    }

    /**
     * test lasy5 at 5pt Char 60: Width=354991, Height=196908, Depth=33068, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C60() throws Exception {

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
            new Glue(354991).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(196908).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(33068).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 61: Width=354991, Height=196908, Depth=33068, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C61() throws Exception {

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
            new Glue(354991).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(196908).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()),
            new Glue(33068).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test lasy5 at 5pt Char 62: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C62() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 62)));
    }

    /**
     * test lasy5 at 5pt Char 63: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C63() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 63)));
    }

    /**
     * test lasy5 at 5pt Char 64: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C64() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 64)));
    }

    /**
     * test lasy5 at 5pt Char 65: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C65() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 65)));
    }

    /**
     * test lasy5 at 5pt Char 66: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C66() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 66)));
    }

    /**
     * test lasy5 at 5pt Char 67: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C67() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 67)));
    }

    /**
     * test lasy5 at 5pt Char 68: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C68() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 68)));
    }

    /**
     * test lasy5 at 5pt Char 69: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C69() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 69)));
    }

    /**
     * test lasy5 at 5pt Char 7: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C7() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 7)));
    }

    /**
     * test lasy5 at 5pt Char 70: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C70() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 70)));
    }

    /**
     * test lasy5 at 5pt Char 71: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C71() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 71)));
    }

    /**
     * test lasy5 at 5pt Char 72: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C72() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 72)));
    }

    /**
     * test lasy5 at 5pt Char 73: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C73() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 73)));
    }

    /**
     * test lasy5 at 5pt Char 74: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C74() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 74)));
    }

    /**
     * test lasy5 at 5pt Char 75: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C75() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 75)));
    }

    /**
     * test lasy5 at 5pt Char 76: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C76() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 76)));
    }

    /**
     * test lasy5 at 5pt Char 77: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C77() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 77)));
    }

    /**
     * test lasy5 at 5pt Char 78: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C78() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 78)));
    }

    /**
     * test lasy5 at 5pt Char 79: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C79() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 79)));
    }

    /**
     * test lasy5 at 5pt Char 8: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C8() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 8)));
    }

    /**
     * test lasy5 at 5pt Char 80: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C80() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 80)));
    }

    /**
     * test lasy5 at 5pt Char 81: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C81() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 81)));
    }

    /**
     * test lasy5 at 5pt Char 82: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C82() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 82)));
    }

    /**
     * test lasy5 at 5pt Char 83: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C83() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 83)));
    }

    /**
     * test lasy5 at 5pt Char 84: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C84() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 84)));
    }

    /**
     * test lasy5 at 5pt Char 85: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C85() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 85)));
    }

    /**
     * test lasy5 at 5pt Char 86: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C86() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 86)));
    }

    /**
     * test lasy5 at 5pt Char 87: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C87() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 87)));
    }

    /**
     * test lasy5 at 5pt Char 88: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C88() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 88)));
    }

    /**
     * test lasy5 at 5pt Char 89: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C89() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 89)));
    }

    /**
     * test lasy5 at 5pt Char 9: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C9() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 9)));
    }

    /**
     * test lasy5 at 5pt Char 90: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C90() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 90)));
    }

    /**
     * test lasy5 at 5pt Char 91: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C91() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 91)));
    }

    /**
     * test lasy5 at 5pt Char 92: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C92() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 92)));
    }

    /**
     * test lasy5 at 5pt Char 93: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C93() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 93)));
    }

    /**
     * test lasy5 at 5pt Char 94: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C94() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 94)));
    }

    /**
     * test lasy5 at 5pt Char 95: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C95() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 95)));
    }

    /**
     * test lasy5 at 5pt Char 96: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C96() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 96)));
    }

    /**
     * test lasy5 at 5pt Char 97: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C97() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 97)));
    }

    /**
     * test lasy5 at 5pt Char 98: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C98() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 98)));
    }

    /**
     * test lasy5 at 5pt Char 99: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlasy5C99() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 99)));
    }
}
