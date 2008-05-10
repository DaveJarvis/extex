/*
 * Copyright (C) 2003-2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst.code;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.code.impl.Type;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>type$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class TestType extends TestCase {

    /**
     * The main program just uses the text interface of JUnit.
     * 
     * @param args command line parameters are ignored
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * Generate a new test suite
     * 
     * @return the new test suite
     */
    public static Test suite() {

        return new TestSuite(TestType.class);
    }

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestType(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
        db = null;
    }

    /**
     * <testcase> A lowercase type is taken as is. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        Entry e = new Entry(null);
        e.setType("book");
        new Type("type$").execute(p, e, null);
        assertEquals("book", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> A cased type is taken as is. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        Entry e = new Entry(null);
        e.setType("Book");
        new Type("type$").execute(p, e, null);
        assertEquals("book", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> An uppercase type is taken as is. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        Entry e = new Entry(null);
        e.setType("BOOK");
        new Type("type$").execute(p, e, null);
        assertEquals("book", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }
}
