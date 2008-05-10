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

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.code.impl.Lt;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>&lt;</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestLt extends TestCase {

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

        return new TestSuite(TestLt.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestLt(String name) {

        super(name);
    }

    /**
     * Run a test case.
     * 
     * @param t1 the first number
     * @param t2 the second number
     * 
     * @throws Exception in case of an error
     */
    private void runTest(int t1, int t2) throws Exception {

        p.push(new TInteger(t2));
        p.push(new TInteger(t1));
        new Lt("<").execute(p, null, null);
        assertEquals((t2 < t1 ? 1 : 0), p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new BstProcessor099c(new DBImpl(), new NullWriter(null), null);
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
            new Lt("<").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Lt("<").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test 0 < 0. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt00() throws Exception {

        runTest(0, 0);
    }

    /**
     * <testcase> Test 1 < 0. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt10() throws Exception {

        runTest(1, 0);
    }

    /**
     * <testcase> Test 1 < 1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt11() throws Exception {

        runTest(1, 1);
    }

    /**
     * <testcase> Test 1 < 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt12() throws Exception {

        runTest(1, 2);
    }

    /**
     * <testcase> Test 123 < 123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt123123() throws Exception {

        runTest(123, 123);
    }

    /**
     * <testcase> Test 123 < -123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt123Minus123() throws Exception {

        runTest(123, -123);
    }

    /**
     * <testcase> Test 1 < -1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt1Minus1() throws Exception {

        runTest(1, -1);
    }

    /**
     * <testcase> Test 2 < 1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt21() throws Exception {

        runTest(2, 1);
    }

    /**
     * <testcase> Test 2 < 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLt22() throws Exception {

        runTest(2, 2);
    }

    /**
     * <testcase> Test -123 < 123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLtMinus123123() throws Exception {

        runTest(-123, 123);
    }

    /**
     * <testcase> Test -1 < -2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLtMinus1Minus2() throws Exception {

        runTest(-1, -2);
    }

    /**
     * <testcase> Test -2 < -1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLtMinus2Minus1() throws Exception {

        runTest(-2, -1);
    }

    /**
     * <testcase> A type error in the first argument leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTypeError1() throws Exception {

        try {
            p.push(new TString("2"));
            p.push(new TInteger(2));
            new Lt("<").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingNumberException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A type error in the second argument leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTypeError2() throws Exception {

        try {
            p.push(new TString("2"));
            p.push(new TInteger(2));
            new Lt("<").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingNumberException e) {
            assertTrue(true);
        }
    }

}
