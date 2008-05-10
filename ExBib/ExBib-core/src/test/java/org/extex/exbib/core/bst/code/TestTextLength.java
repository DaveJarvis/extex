/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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
import org.extex.exbib.core.bst.code.impl.TextLength;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>text.length$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestTextLength extends TestCase {

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

        return new TestSuite(TestTextLength.class);
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
    public TestTextLength(String name) {

        super(name);
    }

    /**
     * Run a test case.
     * 
     * @param in the input string
     * @param len the length
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String in, int len) throws Exception {

        p.push(new TString(in));
        new TextLength("text.length$").execute(p, null, null);
        assertEquals(len, p.popInteger(null).getInt());
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
     * <testcase> The "a" has length 1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        runTest("a", 1);
    }

    /**
     * <testcase> The "a!" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1a() throws Exception {

        runTest("a!", 2);
    }

    /**
     * <testcase> The "a!}" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1b() throws Exception {

        runTest("a!}", 2);
    }

    /**
     * <testcase> The "a!}}" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1c() throws Exception {

        runTest("a!}}", 2);
    }

    /**
     * <testcase> The "a!}}}" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1d() throws Exception {

        runTest("a!}}}", 2);
    }

    /**
     * <testcase> The "a." has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1e() throws Exception {

        runTest("a.", 2);
    }

    /**
     * <testcase> The "a.}" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1f() throws Exception {

        runTest("a.}", 2);
    }

    /**
     * <testcase> The "a.}}" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1g() throws Exception {

        runTest("a.}}", 2);
    }

    /**
     * <testcase> The "a.}}}" has length 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1h() throws Exception {

        runTest("a.}}}", 2);
    }

    /**
     * <testcase> The "ac!" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3a() throws Exception {

        runTest("ac!", 3);
    }

    /**
     * <testcase> The "ac!}" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3b() throws Exception {

        runTest("ac!}", 3);
    }

    /**
     * <testcase> The "ac!}}" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3c() throws Exception {

        runTest("ac!}}", 3);
    }

    /**
     * <testcase> The "ac!}}}" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3d() throws Exception {

        runTest("ac!}}}", 3);
    }

    /**
     * <testcase> The "ac." has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3e() throws Exception {

        runTest("ac.", 3);
    }

    /**
     * <testcase> The "ac.}" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3f() throws Exception {

        runTest("ac.}", 3);
    }

    /**
     * <testcase> The "ac.}}" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3g() throws Exception {

        runTest("ac.}}", 3);
    }

    /**
     * <testcase> The "ac.}}" has length 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3h() throws Exception {

        runTest("ac.}}}", 3);
    }

    /**
     * <testcase> The control sequence \" at level 0 has no influence.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testAcc1() throws Exception {

        runTest("a\\\"bc", 3);
    }

    /**
     * <testcase> The argument of a control sequence \" at level 0 has no
     * influence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testAcc2() throws Exception {

        runTest("a\\\"{b}c", 3);
    }

    /**
     * <testcase> The argument of a control sequence \" at level 0 has no
     * influence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testAcc3() throws Exception {

        runTest("a\\'{b}c", 3);
    }

    /**
     * <testcase> The argument of a control sequence \" at level 0 has no
     * influence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testAcc4() throws Exception {

        runTest("a\\^{b}c", 3);
    }

    /**
     * <testcase> The empty string has length 0. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        runTest("", 0);
    }

    /**
     * <testcase> Braces and brackets do not count. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty2() throws Exception {

        runTest("{{[[][ ", 0);
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new TextLength("text.length$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> The length of an integer is the number of digits. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testInteger() throws Exception {

        p.push(new TInteger(9876));
        new TextLength("text.length$").execute(p, null, null);
        assertEquals(4, p.popInteger(null).getInt());
        assertEquals(null, p.popUnchecked());
    }

}
