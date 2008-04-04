/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * This file is part of ExBib a BibTeX compatible database.
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

import org.extex.exbib.core.db.impl.DBImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@link Value}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TestValue {

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db;

    /**
     * Create a new object.
     */
    public TestValue() {

        super();
    }

    /**
     * Set-up.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        db = new DBImpl();
        db.storeString("jan", new Value(new VString("January")));
        db.storeString("feb", new Value(new VString("February")));
        db.storeString("mar", new Value(new VString("March")));
    }

    /**
     * <testcase> An empty value expands to the empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand0() throws Exception {

        assertEquals("", new Value().expand(db));
    }

    /**
     * <testcase> An the value "jan" expands to "January". </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand1() throws Exception {

        assertEquals("January", new Value(new VMacro("jan")).expand(db));
    }

    /**
     * <testcase> An composed value expands its compnents. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand2() throws Exception {

        Value v = new Value(new VMacro("jan"));
        v.add(new VString(" 21st"));
        assertEquals("January 21st", v.expand(db));
    }

}
