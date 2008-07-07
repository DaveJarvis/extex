/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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
     * <testcase> ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGetLocator1() throws Exception {

        Entry entry = new Entry(null);
        assertNull(entry.getLocator());
    }

    /**
     * <testcase> ... </testcase>
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
     * <testcase> ... </testcase>
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
     * <testcase> ... </testcase>
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
