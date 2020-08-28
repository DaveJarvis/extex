/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.Locator;
import org.junit.Test;

/**
 * This is a test suite for {@link Entry}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EntryTest {

    /**
     * <testcase> Test that the locator is initially <code>null</code>.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetLocator1() throws Exception {

        Entry entry = new Entry(null);
        assertNull(entry.getLocator());
    }

    /**
     * <testcase> Test that the locator can be passed in with the constructor.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetLocator2() throws Exception {

        Locator locator = new Locator("", 0);
        Entry entry = new Entry(locator);
        assertEquals(locator, entry.getLocator());
    }

    /**
     * <testcase> Test that a getter uses the crossref field. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingEntryException.class)
    public final void testGetStringDB() throws Exception {

        Entry entry = new Entry(null);
        entry.set("crossref", "xr");
        entry.get("abc", new DBImpl());
    }

    /**
     * <testcase> Test that a getter uses the crossref field. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetStringDB2() throws Exception {

        DBImpl db = new DBImpl();
        Entry e = db.makeEntry("book", "xr", null);
        e.set("abc", "XYZ");

        Entry entry = new Entry(null);
        entry.set("crossref", "xr");
        assertEquals("\"XYZ\"", entry.get("abc", db).toString());
    }

    /**
     * <testcase> Test that a getter uses the crossref field only if required.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetStringDB3() throws Exception {

        DBImpl db = new DBImpl();
        Entry e = db.makeEntry("book", "xr", null);
        e.set("abc", "XYZ");

        Entry entry = new Entry(null);
        entry.set("crossref", "xr");
        entry.set("abc", "ABC");
        assertEquals("\"ABC\"", entry.get("abc", db).toString());
    }

    /**
     * <testcase> Test that setters for type and key work. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testToString() throws Exception {

        Entry entry = new Entry(null);
        entry.setKey("abc");
        entry.setType("book");
        assertEquals("@book{abc,...}", entry.toString());
    }

}
