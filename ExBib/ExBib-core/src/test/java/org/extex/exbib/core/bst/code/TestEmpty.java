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
     * The field <tt>entry</tt> contains an entry.
     */
    private Entry entry;

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
        entry = db.makeEntry("book", "abc", null);
        entry.set("author", new Value());
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
     * Run one test for the function empty$.
     * 
     * @param val the value of the argument
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty(String val, int res) throws Exception {

        p.push(new TString(val, null));
        new Empty("empty$").execute(p, entry, null);
        assertEquals(res, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The empty stack leads to an error. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Empty("empty$").execute(p, entry, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A non-string (Integer) argument leads to an error. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testInteger() throws Exception {

        try {
            p.push(new TInteger(123));
            new Empty("empty$").execute(p, entry, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A longer string ist not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNo0() throws Exception {

        testEmpty("title", 0);
    }

    /**
     * <testcase> A one-letter string is not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNo1() throws Exception {

        testEmpty("a", 0);
    }

    /**
     * <testcase> A one-character string is not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNo2() throws Exception {

        testEmpty(".", 0);
    }

    /**
     * <testcase> A one-digit string is not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNo3() throws Exception {

        testEmpty("1", 0);
    }

    /**
     * <testcase> A <code>null</code> is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes1() throws Exception {

        testEmpty(null, 1);
    }

    /**
     * <testcase> The empty string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes2() throws Exception {

        testEmpty("", 1);
    }

    /**
     * <testcase> A space-only string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes3() throws Exception {

        testEmpty(" ", 1);
    }

    /**
     * <testcase> A tab-only string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes4() throws Exception {

        testEmpty("\t", 1);
    }

    /**
     * <testcase> A multi-space string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes5() throws Exception {

        testEmpty("  ", 1);
    }

    /**
     * <testcase> A multi-whitespace string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes6() throws Exception {

        testEmpty(" \n", 1);
    }

    /**
     * <testcase> A multi-whitespace string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testYes7() throws Exception {

        testEmpty(" \t", 1);
    }
}
