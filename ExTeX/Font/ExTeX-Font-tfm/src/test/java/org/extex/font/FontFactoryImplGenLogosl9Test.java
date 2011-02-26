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
 * Test for the font factory (with font logosl9).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplGenLogosl9Test extends AbstractFontFactoryTester {

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
    public FontFactoryImplGenLogosl9Test()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();

            key = factory.getFontKey("logosl9");

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
     * test logosl9 Char 0: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C0() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 0)));
    }

    /**
     * test logosl9 Char 1: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C1() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 1)));
    }

    /**
     * test logosl9 Char 10: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C10() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 10)));
    }

    /**
     * test logosl9 Char 100: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C100() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 100)));
    }

    /**
     * test logosl9 Char 101: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C101() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 101)));
    }

    /**
     * test logosl9 Char 102: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C102() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 102)));
    }

    /**
     * test logosl9 Char 103: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C103() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 103)));
    }

    /**
     * test logosl9 Char 104: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C104() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 104)));
    }

    /**
     * test logosl9 Char 105: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C105() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 105)));
    }

    /**
     * test logosl9 Char 106: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C106() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 106)));
    }

    /**
     * test logosl9 Char 107: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C107() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 107)));
    }

    /**
     * test logosl9 Char 108: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C108() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 108)));
    }

    /**
     * test logosl9 Char 109: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C109() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 109)));
    }

    /**
     * test logosl9 Char 11: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C11() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 11)));
    }

    /**
     * test logosl9 Char 110: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C110() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 110)));
    }

    /**
     * test logosl9 Char 111: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C111() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 111)));
    }

    /**
     * test logosl9 Char 112: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C112() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 112)));
    }

    /**
     * test logosl9 Char 113: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C113() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 113)));
    }

    /**
     * test logosl9 Char 114: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C114() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 114)));
    }

    /**
     * test logosl9 Char 115: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C115() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 115)));
    }

    /**
     * test logosl9 Char 116: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C116() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 116)));
    }

    /**
     * test logosl9 Char 117: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C117() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 117)));
    }

    /**
     * test logosl9 Char 118: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C118() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 118)));
    }

    /**
     * test logosl9 Char 119: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C119() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 119)));
    }

    /**
     * test logosl9 Char 12: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C12() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 12)));
    }

    /**
     * test logosl9 Char 120: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C120() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 120)));
    }

    /**
     * test logosl9 Char 121: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C121() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 121)));
    }

    /**
     * test logosl9 Char 122: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C122() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 122)));
    }

    /**
     * test logosl9 Char 123: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C123() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 123)));
    }

    /**
     * test logosl9 Char 124: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C124() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 124)));
    }

    /**
     * test logosl9 Char 125: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C125() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 125)));
    }

    /**
     * test logosl9 Char 126: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C126() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 126)));
    }

    /**
     * test logosl9 Char 127: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C127() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 127)));
    }

    /**
     * test logosl9 Char 128: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C128() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 128)));
    }

    /**
     * test logosl9 Char 129: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C129() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 129)));
    }

    /**
     * test logosl9 Char 13: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C13() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 13)));
    }

    /**
     * test logosl9 Char 130: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C130() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 130)));
    }

    /**
     * test logosl9 Char 131: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C131() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 131)));
    }

    /**
     * test logosl9 Char 132: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C132() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 132)));
    }

    /**
     * test logosl9 Char 133: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C133() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 133)));
    }

    /**
     * test logosl9 Char 134: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C134() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 134)));
    }

    /**
     * test logosl9 Char 135: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C135() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 135)));
    }

    /**
     * test logosl9 Char 136: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C136() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 136)));
    }

    /**
     * test logosl9 Char 137: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C137() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 137)));
    }

    /**
     * test logosl9 Char 138: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C138() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 138)));
    }

    /**
     * test logosl9 Char 139: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C139() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 139)));
    }

    /**
     * test logosl9 Char 14: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C14() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 14)));
    }

    /**
     * test logosl9 Char 140: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C140() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 140)));
    }

    /**
     * test logosl9 Char 141: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C141() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 141)));
    }

    /**
     * test logosl9 Char 142: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C142() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 142)));
    }

    /**
     * test logosl9 Char 143: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C143() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 143)));
    }

    /**
     * test logosl9 Char 144: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C144() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 144)));
    }

    /**
     * test logosl9 Char 145: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C145() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 145)));
    }

    /**
     * test logosl9 Char 146: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C146() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 146)));
    }

    /**
     * test logosl9 Char 147: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C147() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 147)));
    }

    /**
     * test logosl9 Char 148: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C148() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 148)));
    }

    /**
     * test logosl9 Char 149: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C149() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 149)));
    }

    /**
     * test logosl9 Char 15: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C15() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 15)));
    }

    /**
     * test logosl9 Char 150: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C150() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 150)));
    }

    /**
     * test logosl9 Char 151: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C151() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 151)));
    }

    /**
     * test logosl9 Char 152: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C152() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 152)));
    }

    /**
     * test logosl9 Char 153: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C153() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 153)));
    }

    /**
     * test logosl9 Char 154: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C154() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 154)));
    }

    /**
     * test logosl9 Char 155: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C155() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 155)));
    }

    /**
     * test logosl9 Char 156: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C156() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 156)));
    }

    /**
     * test logosl9 Char 157: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C157() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 157)));
    }

    /**
     * test logosl9 Char 158: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C158() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 158)));
    }

    /**
     * test logosl9 Char 159: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C159() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 159)));
    }

    /**
     * test logosl9 Char 16: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C16() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 16)));
    }

    /**
     * test logosl9 Char 160: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C160() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 160)));
    }

    /**
     * test logosl9 Char 161: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C161() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 161)));
    }

    /**
     * test logosl9 Char 162: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C162() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 162)));
    }

    /**
     * test logosl9 Char 163: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C163() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 163)));
    }

    /**
     * test logosl9 Char 164: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C164() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 164)));
    }

    /**
     * test logosl9 Char 165: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C165() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 165)));
    }

    /**
     * test logosl9 Char 166: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C166() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 166)));
    }

    /**
     * test logosl9 Char 167: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C167() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 167)));
    }

    /**
     * test logosl9 Char 168: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C168() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 168)));
    }

    /**
     * test logosl9 Char 169: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C169() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 169)));
    }

    /**
     * test logosl9 Char 17: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C17() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 17)));
    }

    /**
     * test logosl9 Char 170: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C170() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 170)));
    }

    /**
     * test logosl9 Char 171: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C171() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 171)));
    }

    /**
     * test logosl9 Char 172: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C172() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 172)));
    }

    /**
     * test logosl9 Char 173: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C173() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 173)));
    }

    /**
     * test logosl9 Char 174: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C174() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 174)));
    }

    /**
     * test logosl9 Char 175: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C175() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 175)));
    }

    /**
     * test logosl9 Char 176: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C176() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 176)));
    }

    /**
     * test logosl9 Char 177: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C177() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 177)));
    }

    /**
     * test logosl9 Char 178: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C178() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 178)));
    }

    /**
     * test logosl9 Char 179: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C179() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 179)));
    }

    /**
     * test logosl9 Char 18: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C18() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 18)));
    }

    /**
     * test logosl9 Char 180: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C180() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 180)));
    }

    /**
     * test logosl9 Char 181: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C181() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 181)));
    }

    /**
     * test logosl9 Char 182: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C182() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 182)));
    }

    /**
     * test logosl9 Char 183: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C183() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 183)));
    }

    /**
     * test logosl9 Char 184: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C184() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 184)));
    }

    /**
     * test logosl9 Char 185: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C185() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 185)));
    }

    /**
     * test logosl9 Char 186: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C186() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 186)));
    }

    /**
     * test logosl9 Char 187: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C187() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 187)));
    }

    /**
     * test logosl9 Char 188: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C188() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 188)));
    }

    /**
     * test logosl9 Char 189: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C189() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 189)));
    }

    /**
     * test logosl9 Char 19: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C19() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 19)));
    }

    /**
     * test logosl9 Char 190: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C190() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 190)));
    }

    /**
     * test logosl9 Char 191: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C191() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 191)));
    }

    /**
     * test logosl9 Char 192: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C192() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 192)));
    }

    /**
     * test logosl9 Char 193: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C193() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 193)));
    }

    /**
     * test logosl9 Char 194: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C194() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 194)));
    }

    /**
     * test logosl9 Char 195: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C195() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 195)));
    }

    /**
     * test logosl9 Char 196: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C196() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 196)));
    }

    /**
     * test logosl9 Char 197: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C197() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 197)));
    }

    /**
     * test logosl9 Char 198: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C198() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 198)));
    }

    /**
     * test logosl9 Char 199: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C199() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 199)));
    }

    /**
     * test logosl9 Char 2: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C2() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 2)));
    }

    /**
     * test logosl9 Char 20: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C20() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 20)));
    }

    /**
     * test logosl9 Char 200: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C200() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 200)));
    }

    /**
     * test logosl9 Char 201: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C201() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 201)));
    }

    /**
     * test logosl9 Char 202: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C202() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 202)));
    }

    /**
     * test logosl9 Char 203: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C203() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 203)));
    }

    /**
     * test logosl9 Char 204: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C204() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 204)));
    }

    /**
     * test logosl9 Char 205: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C205() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 205)));
    }

    /**
     * test logosl9 Char 206: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C206() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 206)));
    }

    /**
     * test logosl9 Char 207: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C207() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 207)));
    }

    /**
     * test logosl9 Char 208: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C208() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 208)));
    }

    /**
     * test logosl9 Char 209: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C209() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 209)));
    }

    /**
     * test logosl9 Char 21: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C21() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 21)));
    }

    /**
     * test logosl9 Char 210: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C210() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 210)));
    }

    /**
     * test logosl9 Char 211: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C211() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 211)));
    }

    /**
     * test logosl9 Char 212: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C212() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 212)));
    }

    /**
     * test logosl9 Char 213: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C213() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 213)));
    }

    /**
     * test logosl9 Char 214: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C214() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 214)));
    }

    /**
     * test logosl9 Char 215: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C215() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 215)));
    }

    /**
     * test logosl9 Char 216: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C216() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 216)));
    }

    /**
     * test logosl9 Char 217: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C217() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 217)));
    }

    /**
     * test logosl9 Char 218: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C218() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 218)));
    }

    /**
     * test logosl9 Char 219: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C219() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 219)));
    }

    /**
     * test logosl9 Char 22: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C22() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 22)));
    }

    /**
     * test logosl9 Char 220: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C220() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 220)));
    }

    /**
     * test logosl9 Char 221: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C221() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 221)));
    }

    /**
     * test logosl9 Char 222: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C222() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 222)));
    }

    /**
     * test logosl9 Char 223: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C223() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 223)));
    }

    /**
     * test logosl9 Char 224: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C224() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 224)));
    }

    /**
     * test logosl9 Char 225: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C225() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 225)));
    }

    /**
     * test logosl9 Char 226: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C226() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 226)));
    }

    /**
     * test logosl9 Char 227: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C227() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 227)));
    }

    /**
     * test logosl9 Char 228: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C228() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 228)));
    }

    /**
     * test logosl9 Char 229: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C229() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 229)));
    }

    /**
     * test logosl9 Char 23: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C23() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 23)));
    }

    /**
     * test logosl9 Char 230: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C230() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 230)));
    }

    /**
     * test logosl9 Char 231: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C231() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 231)));
    }

    /**
     * test logosl9 Char 232: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C232() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 232)));
    }

    /**
     * test logosl9 Char 233: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C233() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 233)));
    }

    /**
     * test logosl9 Char 234: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C234() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 234)));
    }

    /**
     * test logosl9 Char 235: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C235() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 235)));
    }

    /**
     * test logosl9 Char 236: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C236() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 236)));
    }

    /**
     * test logosl9 Char 237: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C237() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 237)));
    }

    /**
     * test logosl9 Char 238: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C238() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 238)));
    }

    /**
     * test logosl9 Char 239: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C239() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 239)));
    }

    /**
     * test logosl9 Char 24: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C24() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 24)));
    }

    /**
     * test logosl9 Char 240: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C240() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 240)));
    }

    /**
     * test logosl9 Char 241: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C241() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 241)));
    }

    /**
     * test logosl9 Char 242: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C242() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 242)));
    }

    /**
     * test logosl9 Char 243: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C243() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 243)));
    }

    /**
     * test logosl9 Char 244: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C244() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 244)));
    }

    /**
     * test logosl9 Char 245: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C245() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 245)));
    }

    /**
     * test logosl9 Char 246: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C246() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 246)));
    }

    /**
     * test logosl9 Char 247: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C247() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 247)));
    }

    /**
     * test logosl9 Char 248: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C248() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 248)));
    }

    /**
     * test logosl9 Char 249: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C249() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 249)));
    }

    /**
     * test logosl9 Char 25: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C25() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 25)));
    }

    /**
     * test logosl9 Char 250: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C250() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 250)));
    }

    /**
     * test logosl9 Char 251: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C251() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 251)));
    }

    /**
     * test logosl9 Char 252: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C252() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 252)));
    }

    /**
     * test logosl9 Char 253: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C253() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 253)));
    }

    /**
     * test logosl9 Char 254: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C254() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 254)));
    }

    /**
     * test logosl9 Char 255: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C255() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 255)));
    }

    /**
     * test logosl9 Char 26: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C26() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 26)));
    }

    /**
     * test logosl9 Char 27: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C27() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 27)));
    }

    /**
     * test logosl9 Char 28: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C28() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 28)));
    }

    /**
     * test logosl9 Char 29: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C29() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 29)));
    }

    /**
     * test logosl9 Char 3: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C3() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 3)));
    }

    /**
     * test logosl9 Char 30: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C30() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 30)));
    }

    /**
     * test logosl9 Char 31: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C31() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 31)));
    }

    /**
     * test logosl9 Char 32: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C32() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 32)));
    }

    /**
     * test logosl9 Char 33: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C33() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 33)));
    }

    /**
     * test logosl9 Char 34: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C34() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 34)));
    }

    /**
     * test logosl9 Char 35: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C35() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 35)));
    }

    /**
     * test logosl9 Char 36: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C36() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 36)));
    }

    /**
     * test logosl9 Char 37: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C37() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 37)));
    }

    /**
     * test logosl9 Char 38: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C38() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 38)));
    }

    /**
     * test logosl9 Char 39: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C39() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 39)));
    }

    /**
     * test logosl9 Char 4: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C4() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 4)));
    }

    /**
     * test logosl9 Char 40: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C40() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 40)));
    }

    /**
     * test logosl9 Char 41: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C41() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 41)));
    }

    /**
     * test logosl9 Char 42: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C42() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 42)));
    }

    /**
     * test logosl9 Char 43: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C43() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 43)));
    }

    /**
     * test logosl9 Char 44: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C44() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 44)));
    }

    /**
     * test logosl9 Char 45: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C45() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 45)));
    }

    /**
     * test logosl9 Char 46: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C46() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 46)));
    }

    /**
     * test logosl9 Char 47: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C47() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 47)));
    }

    /**
     * test logosl9 Char 48: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C48() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 48)));
    }

    /**
     * test logosl9 Char 49: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C49() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 49)));
    }

    /**
     * test logosl9 Char 5: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C5() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 5)));
    }

    /**
     * test logosl9 Char 50: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C50() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 50)));
    }

    /**
     * test logosl9 Char 51: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C51() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 51)));
    }

    /**
     * test logosl9 Char 52: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C52() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 52)));
    }

    /**
     * test logosl9 Char 53: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C53() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 53)));
    }

    /**
     * test logosl9 Char 54: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C54() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 54)));
    }

    /**
     * test logosl9 Char 55: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C55() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 55)));
    }

    /**
     * test logosl9 Char 56: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C56() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 56)));
    }

    /**
     * test logosl9 Char 57: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C57() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 57)));
    }

    /**
     * test logosl9 Char 58: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C58() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 58)));
    }

    /**
     * test logosl9 Char 59: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C59() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 59)));
    }

    /**
     * test logosl9 Char 6: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C6() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 6)));
    }

    /**
     * test logosl9 Char 60: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C60() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 60)));
    }

    /**
     * test logosl9 Char 61: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C61() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 61)));
    }

    /**
     * test logosl9 Char 62: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C62() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 62)));
    }

    /**
     * test logosl9 Char 63: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C63() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 63)));
    }

    /**
     * test logosl9 Char 64: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C64() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 64)));
    }

    /**
     * test logosl9 Char 65: Width=408075, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C65() throws Exception {

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
            new Glue(408075).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 66: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C66() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 66)));
    }

    /**
     * test logosl9 Char 67: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C67() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 67)));
    }

    /**
     * test logosl9 Char 68: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C68() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 68)));
    }

    /**
     * test logosl9 Char 69: Width=381570, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C69() throws Exception {

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
            new Glue(381570).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 7: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C7() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 7)));
    }

    /**
     * test logosl9 Char 70: Width=381570, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C70() throws Exception {

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
            new Glue(381570).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 71: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C71() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 71)));
    }

    /**
     * test logosl9 Char 72: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C72() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 72)));
    }

    /**
     * test logosl9 Char 73: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C73() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 73)));
    }

    /**
     * test logosl9 Char 74: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C74() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 74)));
    }

    /**
     * test logosl9 Char 75: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C75() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 75)));
    }

    /**
     * test logosl9 Char 76: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C76() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 76)));
    }

    /**
     * test logosl9 Char 77: Width=487594, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C77() throws Exception {

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
            new Glue(487594).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 78: Width=408075, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C78() throws Exception {

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
            new Glue(408075).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 79: Width=408075, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C79() throws Exception {

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
            new Glue(408075).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 8: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C8() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 8)));
    }

    /**
     * test logosl9 Char 80: Width=381570, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C80() throws Exception {

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
            new Glue(381570).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 81: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C81() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 81)));
    }

    /**
     * test logosl9 Char 82: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C82() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 82)));
    }

    /**
     * test logosl9 Char 83: Width=381570, Height=353891, Depth=0, IC=0
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C83() throws Exception {

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
            new Glue(381570).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(0).eq(i));
    }

    /**
     * test logosl9 Char 84: Width=355064, Height=353891, Depth=0, IC=101725
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C84() throws Exception {

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
            new Glue(355064).eq(w));
        assertTrue(Long.toString(h.getLength().getValue()),
            new Glue(353891).eq(h));
        assertTrue(Long.toString(d.getLength().getValue()), new Glue(0).eq(d));
        assertTrue(Long.toString(i.getValue()), new Dimen(101725).eq(i));
    }

    /**
     * test logosl9 Char 85: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C85() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 85)));
    }

    /**
     * test logosl9 Char 86: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C86() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 86)));
    }

    /**
     * test logosl9 Char 87: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C87() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 87)));
    }

    /**
     * test logosl9 Char 88: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C88() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 88)));
    }

    /**
     * test logosl9 Char 89: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C89() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 89)));
    }

    /**
     * test logosl9 Char 9: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C9() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 9)));
    }

    /**
     * test logosl9 Char 90: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C90() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 90)));
    }

    /**
     * test logosl9 Char 91: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C91() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 91)));
    }

    /**
     * test logosl9 Char 92: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C92() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 92)));
    }

    /**
     * test logosl9 Char 93: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C93() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 93)));
    }

    /**
     * test logosl9 Char 94: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C94() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 94)));
    }

    /**
     * test logosl9 Char 95: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C95() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 95)));
    }

    /**
     * test logosl9 Char 96: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C96() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 96)));
    }

    /**
     * test logosl9 Char 97: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C97() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 97)));
    }

    /**
     * test logosl9 Char 98: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C98() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 98)));
    }

    /**
     * test logosl9 Char 99: not defined
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testlogosl9C99() throws Exception {

        assertFalse(font.hasGlyph(UnicodeChar.get(Unicode.OFFSET + 99)));
    }

}
