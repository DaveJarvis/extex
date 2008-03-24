/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.code.impl.Empty;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>empty$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestEmpty extends TestCase {

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

        return new TestSuite(TestEmpty.class);
    }

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>e</tt> contains the ...
     */
    private Entry e;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestEmpty(String name) {

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
        e = db.makeEntry("book", "abc", null);
        e.set("author", new Value());
        p = new Processor099Impl(db, new NullWriter(null), null);
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
     * TODO gene: missing JavaDoc
     * 
     * @param val
     * @param res
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty(String val, int res) throws Exception {

        p.push(new TString(val, null));
        new Empty("empty$").execute(p, e, null);
        assertEquals(res, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Empty("empty$").execute(p, e, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testInteger() throws Exception {

        try {
            p.push(new TInteger(123));
            new Empty("empty$").execute(p, e, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testNo0() throws Exception {

        testEmpty("title", 0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testNo1() throws Exception {

        testEmpty("a", 0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testNo2() throws Exception {

        testEmpty(".", 0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testNo3() throws Exception {

        testEmpty("1", 0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes1() throws Exception {

        testEmpty(null, 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes2() throws Exception {

        testEmpty("", 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes3() throws Exception {

        testEmpty(" ", 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes4() throws Exception {

        testEmpty("\t", 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes5() throws Exception {

        testEmpty("  ", 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes6() throws Exception {

        testEmpty(" \n", 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testYes7() throws Exception {

        testEmpty(" \t", 1);
    }
}
