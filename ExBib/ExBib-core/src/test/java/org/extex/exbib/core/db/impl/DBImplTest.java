/*
 * Copyright (C) 2010-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.extex.exbib.core.bst.exception.ExBibEntryUndefinedException;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.util.NotObservableException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.junit.Test;

/**
 * This is a test suite for {@link Entry}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DBImplTest {

    /**
     * <testcase> Check the initial settings. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testInit() throws Exception {

        DBImpl db = new DBImpl();
        assertNotNull(db.getEntries());
        assertEquals(0, db.getEntries().size());

        assertEquals(2, db.getMinCrossrefs());

        assertNotNull(db.getPreamble());
        assertEquals("", db.getPreamble().toString());
        assertEquals("", db.getPreambleExpanded());

        assertNull(db.getSorter());
    }

    /**
     * <testcase> Storing an alias leads to an error when the referenced entry
     * is missing . </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibEntryUndefinedException.class)
    public final void testStoreAlias() throws Exception {

        DBImpl db = new DBImpl();
        db.storeAlias("new", "old", null);
    }

    /**
     * <testcase> Check registerObserver(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NotObservableException.class)
    public final void testRegisterObserver() throws Exception {

        new DBImpl().registerObserver(null, null);
    }

    /**
     * <testcase> Check registerObserver(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ConfigurationException.class)
    public final void testSort() throws Exception {

        new DBImpl().sort();
    }

}
