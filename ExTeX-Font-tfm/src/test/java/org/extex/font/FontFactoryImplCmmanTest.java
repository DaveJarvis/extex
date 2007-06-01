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

import org.extex.core.UnicodeChar;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * Test for the font factory (with font logosl9).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FontFactoryImplCmmanTest extends AbstractFontFactoryTester {

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
    public FontFactoryImplCmmanTest()
            throws ConfigurationException,
                FontException {

        if (key == null) {
            CoreFontFactory factory = makeFontFactory();
            key = factory.getFontKey("cmman");
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
     * test cmman Char T defined
     * 
     * @throws Exception if an error occurred.
     */
    public void testcmmanT() throws Exception {

        assertTrue(font.hasGlyph(UnicodeChar.get('T')));
    }

    /**
     * test cmman Char h defined
     * 
     * @throws Exception if an error occurred.
     */
    public void testcmmanh() throws Exception {

        assertTrue(font.hasGlyph(UnicodeChar.get('h')));
    }

    /**
     * test cmman ligature
     * 
     * @throws Exception if an error occurred.
     */
    public void testcmmanLig() throws Exception {

        UnicodeChar ligature =
                font.getLigature(UnicodeChar.get('h'), UnicodeChar.get('h'));
        assertNull(ligature);
    }

}
