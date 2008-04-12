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

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.code.impl.Set;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TFieldString;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.ValueItem;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>:=</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestSet extends TestCase {

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

        return new TestSuite(TestSet.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db;

    /**
     * The field <tt>entry</tt> contains the entry.
     */
    private Entry entry;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestSet(String name) {

        super(name);
    }

    /**
     * Run a test case.
     * 
     * @param name the name
     * @param t the value
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String name, Token t) throws Exception {

        p.push(t);
        p.push(new TLiteral(name, null));
        new Set(":=").execute(p, null, null);
        assertNull(p.popUnchecked());
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
        p.addFunction("abc", new TInteger(1), null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1Stack() throws Exception {

        try {
            p.push(new TInteger(2));
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test abc := 123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet1() throws Exception {

        runTest("abc", new TInteger(123));
    }

    /**
     * <testcase> Test abc := "123". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet2() throws Exception {

        runTest("abc", new TString("123"));
    }

    /**
     * <testcase> Test setting a string entry. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet3() throws Exception {

        List<String> strings = new ArrayList<String>();
        strings.add("aaa");
        p.setEntryStrings(strings, null);

        p.push(new TString("123"));
        p.push(new TFieldString("aaa"));
        new Set(":=").execute(p, entry, null);
        assertNull(p.popUnchecked());
        ValueItem value = entry.getLocal("aaa");
        assertNotNull(value);
        assertEquals("\"123\"", value.toString());
    }

    /**
     * <testcase> Test setting a string entry with an integer. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet4() throws Exception {

        List<String> strings = new ArrayList<String>();
        strings.add("aaa");
        p.setEntryStrings(strings, null);

        p.push(new TInteger(123));
        p.push(new TFieldString("aaa"));
        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingStringException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test setting an integer entry. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet5() throws Exception {

        List<String> integers = new ArrayList<String>();
        integers.add("aaa");
        p.setEntryIntegers(integers, null);

        p.push(new TInteger(123));
        p.push(new TFieldString("aaa"));
        new Set(":=").execute(p, entry, null);
        assertNull(p.popUnchecked());
        ValueItem value = entry.getLocal("aaa");
        assertNotNull(value);
        assertEquals("123", value.toString());
    }

    /**
     * <testcase> Test setting an integer entry with a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet6() throws Exception {

        List<String> integers = new ArrayList<String>();
        integers.add("aaa");
        p.setEntryIntegers(integers, null);

        p.push(new TString("123"));
        p.push(new TFieldString("aaa"));
        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingNumberException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test setting a string entry. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet7() throws Exception {

        List<String> entries = new ArrayList<String>();
        entries.add("aaa");
        p.setEntries(entries, null);

        p.push(new TString("123"));
        p.push(new TFieldString("aaa"));
        new Set(":=").execute(p, entry, null);
        assertNull(p.popUnchecked());
        Value value = entry.get("aaa");
        assertNotNull(value);
        assertEquals("\"123\"", value.toString());
    }

    /**
     * <testcase> Test setting a string entry with an integer. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSet8() throws Exception {

        List<String> entries = new ArrayList<String>();
        entries.add("aaa");
        p.setEntries(entries, null);

        p.push(new TInteger(123));
        p.push(new TFieldString("aaa"));
        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingStringException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test 2 := 2 leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTypeError1() throws Exception {

        try {
            p.push(new TInteger(2));
            p.push(new TInteger(2));
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibFunctionUndefinedException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test 'undef := 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testUndef() throws Exception {

        try {
            p.push(new TInteger(2));
            p.push(new TLiteral("undef", null));
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibFunctionUndefinedException e) {
            assertTrue(true);
        }
    }

}
