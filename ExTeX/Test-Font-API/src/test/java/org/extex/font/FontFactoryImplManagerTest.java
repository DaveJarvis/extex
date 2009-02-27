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
 * Test for the font factory (manager).
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class FontFactoryImplManagerTest extends AbstractFontFactoryTester {

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test01() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        ExtexFont font = factory.getInstance(key);
        assertNotNull(font);
    }

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
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
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test03() throws Exception {

        CoreFontFactory factory = makeFontFactory();

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
    public void test04() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        List<String> sl = new ArrayList<String>();
        sl.add("tfm");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);
    }

    /**
     * Test for the font manager.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test05() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        List<String> sl = new ArrayList<String>();
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
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test06() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        List<String> sl = new ArrayList<String>();
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
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void test07() throws Exception {

        CoreFontFactory factory = makeFontFactory();

        FontKey key = factory.getFontKey("cmr12");

        List<String> sl = new ArrayList<String>();
        sl.add("tfm");

        BackendFontManager manager = factory.createManager(sl);

        assertNotNull(manager);
        assertTrue(manager.recognize(key, UnicodeChar.get('A')));
    }

    /**
     * Test that the font manager recognizes no character before the first
     * character and font.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager0() throws Exception {

        CoreFontFactory factory = makeFontFactory();
        List<String> sl = new ArrayList<String>();
        sl.add("tfm");
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

        CoreFontFactory factory = makeFontFactory();
        List<String> sl = new ArrayList<String>();
        sl.add("tfm");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        FontKey key = factory.getFontKey("cmr12");
        assertTrue("A in cmr12 should be managable", //
            manager.recognize(key, UnicodeChar.get('A')));
        assertTrue("first always is new", manager.isNewRecongnizedFont());
        BackendCharacter cid = manager.getRecognizedCharId();
        assertEquals('A', (char) cid.getId());
        assertEquals("65", cid.getName());
        Iterator<ManagerInfo> it = manager.iterate();
        assertTrue(it.hasNext());
        ManagerInfo man = it.next();
        assertNotNull(man);
        Object fnt = man.getBackendFont();
        assertNotNull(fnt);
        assertTrue(fnt instanceof BackendFont);
        BackendFont bfnt = (BackendFont) fnt;
        assertEquals("cmr12", bfnt.getName());
        assertEquals(1487622411, bfnt.getCheckSum());
        assertFalse(it.hasNext());
    }

    /**
     * Test that the font manager recognizes the second character and font.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager2() throws Exception {

        CoreFontFactory factory = makeFontFactory();
        List<String> sl = new ArrayList<String>();
        sl.add("tfm");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        FontKey key = factory.getFontKey("cmr12");
        assertTrue("A in cmr12 should be managable", //
            manager.recognize(key, UnicodeChar.get('A')));
        assertTrue("B in cmr12 should be managable", //
            manager.recognize(key, UnicodeChar.get('B')));
        assertFalse("second is not new any more", manager
            .isNewRecongnizedFont());
        BackendCharacter cid = manager.getRecognizedCharId();
        assertEquals('B', (char) cid.getId());
        assertEquals("66", cid.getName());
        Iterator<ManagerInfo> it = manager.iterate();
        assertNotNull(it);
        assertTrue(it.hasNext());
        ManagerInfo man = it.next();
        assertNotNull(man);
        Object fnt = man.getBackendFont();
        assertNotNull(fnt);
        assertTrue(fnt instanceof BackendFont);
        BackendFont bfnt = (BackendFont) fnt;
        assertEquals("cmr12", bfnt.getName());
        assertEquals(1487622411, bfnt.getCheckSum());
        assertFalse(it.hasNext());
    }

    /**
     * Test that the font manager recognizes the second character of a different
     * font.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManager3() throws Exception {

        CoreFontFactory factory = makeFontFactory();
        List<String> sl = new ArrayList<String>();
        sl.add("tfm");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);

        FontKey key = factory.getFontKey("cmr12");
        assertTrue("A in cmr12 should be managable", //
            manager.recognize(key, UnicodeChar.get('A')));
        key = factory.getFontKey("cmr10");
        assertTrue("B in cmr12 should be managable", //
            manager.recognize(key, UnicodeChar.get('B')));
        assertTrue("second is new", manager.isNewRecongnizedFont());
        BackendCharacter cid = manager.getRecognizedCharId();
        assertEquals('B', (char) cid.getId());
        assertEquals("66", cid.getName());
        Iterator<ManagerInfo> it = manager.iterate();
        assertNotNull(it);

        assertTrue(it.hasNext());
        ManagerInfo man = it.next();
        assertNotNull(man);
        Object fnt = man.getBackendFont();
        assertNotNull(fnt);
        assertTrue(fnt instanceof BackendFont);
        BackendFont bfnt = (BackendFont) fnt;
        assertEquals("cmr10", bfnt.getName());
        assertEquals(1274110073, bfnt.getCheckSum());
        assertTrue(it.hasNext());
        man = it.next();
        fnt = man.getBackendFont();
        assertNotNull(fnt);
        assertTrue(fnt instanceof BackendFont);
        bfnt = (BackendFont) fnt;
        assertEquals("cmr12", bfnt.getName());
        assertEquals(1487622411, bfnt.getCheckSum());

        assertFalse(it.hasNext());
    }

    /**
     * Test that the font manager can be reset.
     * 
     * @throws Exception if an error occurred.
     */
    @Test
    public void testManagerReset1() throws Exception {

        CoreFontFactory factory = makeFontFactory();
        List<String> sl = new ArrayList<String>();
        sl.add("tfm");
        BackendFontManager manager = factory.createManager(sl);
        assertNotNull(manager);
        assertTrue("A in cmr12 should be managable", //
            manager
                .recognize(factory.getFontKey("cmr12"), UnicodeChar.get('A')));
        assertTrue("B in cmr12 should be managable", //
            manager
                .recognize(factory.getFontKey("cmr10"), UnicodeChar.get('B')));

        manager.reset();

        assertFalse("nothing recognized any more", manager
            .isNewRecongnizedFont());
        assertNull("no char recognized any more", manager.getRecognizedCharId());
        assertNull("no font recognized any more", manager.getRecognizedFont());
        Iterator<ManagerInfo> it = manager.iterate();
        assertNotNull(it);
        assertFalse("no font enlisted", it.hasNext());
    }

}
