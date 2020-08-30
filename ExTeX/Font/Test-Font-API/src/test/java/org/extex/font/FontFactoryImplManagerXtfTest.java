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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.extex.core.UnicodeChar;
import org.extex.font.manager.ManagerInfo;
import org.junit.Test;

/**
 * Test for the font factory (manager) with a ttf font.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class FontFactoryImplManagerXtfTest extends AbstractFontFactoryTester {

    /**
     * The factory.
     */
    private static CoreFontFactory factory;

    /**
     * The font key.
     */
    private static FontKey key;


    public FontFactoryImplManagerXtfTest() {

        if (factory == null) {
            factory = makeFontFactory();
            key = factory.getFontKey("fxlrttf");
        }
    }

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test01() throws Exception {

        try {
            factory.createManager(new ArrayList<String>());
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test02() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);
    }

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test03() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");

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
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test04() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");

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
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test05() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);
        assertNotNull(key);
        UnicodeChar uc = UnicodeChar.get('A');
        assertNotNull(uc);
        assertTrue(manager.recognize(key, uc));
    }

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testFont01() throws Exception {

        assertNotNull(factory);
        assertNotNull(key);
        ExtexFont font = factory.getInstance(key);
        assertNotNull(font);
    }

    /**
     * Test that the font manager recognizes no character before the first
     * character and font.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager0() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        assertFalse("nothing recognized yet", manager.isNewRecongnizedFont());
        assertNull("no char recognized yet", manager.getRecognizedCharId());
        assertNull("no font recognized yet", manager.getRecognizedFont());
        Iterator<ManagerInfo> it = manager.iterate();
        assertNotNull(it);
        boolean next = it.hasNext();
        assertFalse("no font enlisted", next);
    }

    /**
     * Test that the font manager recognizes the first character and font.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager1() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        assertTrue("A in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('A')));
        assertTrue("first always is new", manager.isNewRecongnizedFont());
        BackendCharacter cid = manager.getRecognizedCharId();
        assertEquals('A', (char) cid.getId());
        assertEquals("A", cid.getName());
        Iterator<ManagerInfo> it = manager.iterate();
        assertTrue(it.hasNext());
        ManagerInfo man = it.next();
        assertNotNull(man);
        Object fnt = man.getBackendFont();
        assertNotNull(fnt);
        assertTrue(fnt instanceof BackendFont);
        BackendFont bfnt = (BackendFont) fnt;
        assertEquals("fxlrttf", bfnt.getName());
        assertEquals(0, bfnt.getCheckSum());
        assertFalse(it.hasNext());
    }

    /**
     * Test that the font manager iterate the manager info.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager2() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        assertTrue("A in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('A')));
        assertTrue("first always is new", manager.isNewRecongnizedFont());
        BackendCharacter cid = manager.getRecognizedCharId();
        assertEquals('A', (char) cid.getId());
        assertEquals("A", cid.getName());
        assertTrue("B in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('B')));
        assertTrue("C in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('C')));

        Iterator<ManagerInfo> it = manager.iterate();
        assertNotNull(it);
        assertTrue(it.hasNext());
        ManagerInfo info = it.next();
        assertNotNull(info);
        assertEquals("fxlrttf", info.getFontKey().getName());
        assertFalse(it.hasNext());

    }

    /**
     * Test that the font manager iterate the manager info.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager3() throws Exception {

        List<String> sl = new ArrayList<String>();
        sl.add("ttf");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        assertTrue("A in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('A')));
        assertTrue("first always is new", manager.isNewRecongnizedFont());
        BackendCharacter cid = manager.getRecognizedCharId();
        assertEquals('A', (char) cid.getId());
        assertEquals("A", cid.getName());
        assertTrue("B in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('B')));
        assertTrue("C in fxlr should be managable",
            manager.recognize(key, UnicodeChar.get('C')));

        Iterator<ManagerInfo> it = manager.iterate();
        assertNotNull(it);
        assertTrue(it.hasNext());
        ManagerInfo info = it.next();
        assertNotNull(info);
        assertEquals("fxlrttf", info.getFontKey().getName());

        Iterator<BackendCharacter> bcit = info.iterate();
        assertNotNull(bcit);
        assertTrue(bcit.hasNext());

    }

}
