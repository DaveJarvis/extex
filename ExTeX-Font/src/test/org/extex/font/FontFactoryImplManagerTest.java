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

import org.extex.type.StringList;
import org.extex.type.UnicodeChar;

/**
 * Test for the font factory (manager).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontFactoryImplManagerTest extends AbstractFontFactoryTester {

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test01() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        ExtexFont font = factory.getInstance(key);
        assertNotNull(font);
    }

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test02() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        try {
            factory.createManager(null);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test03() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        try {
            factory.createManager(new StringList());
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test04() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        StringList sl = new StringList();
        sl.add("tfm");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);
    }

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test05() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        StringList sl = new StringList();
        sl.add("tfm");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);

        try {
            manager.recognize(null, null);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test06() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        StringList sl = new StringList();
        sl.add("tfm");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);

        try {
            manager.recognize(key, null);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    /**
     * Test for the font manager. 
     * @throws Exception if an error occurred.
     */
    public void test07() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        StringList sl = new StringList();
        sl.add("tfm");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);

        boolean b = manager.recognize(key, UnicodeChar.get('A'));

        assertFalse(b);
    }
}
