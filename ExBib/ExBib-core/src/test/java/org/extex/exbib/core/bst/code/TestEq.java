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
import org.extex.exbib.core.bst.code.impl.Eq;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>eq$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestEq extends TestCase {

    /**
     * The field <tt>INT_MINUS_123</tt> contains -123.
     */
    private static final TInteger INT_MINUS_123 = new TInteger(-123);

    /**
     * The field <tt>INT_12</tt> contains 12.
     */
    private static final TInteger INT_12 = new TInteger(12);

    /**
     * The field <tt>INT_123</tt> contains 123.
     */
    private static final TInteger INT_123 = new TInteger(123);

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

        return new TestSuite(TestEq.class);
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
    public TestEq(String name) {

        super(name);
    }

    /**
     * Run a test case.
     * 
     * @param t1 the first token
     * @param t2 the second token
     * @param result the expected result
     * 
     * @throws Exception in case of an error
     */
    private void runTest(Token t1, Token t2, String result) throws Exception {

        p.push(t1);
        p.push(t2);
        new Eq("=").execute(p, null, null);
        assertEquals(result, p.popInteger(null).getValue());
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
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Eq("=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Identical numbers compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI1() throws Exception {

        runTest(INT_123, INT_123, "1");
    }

    /**
     * <testcase> Identical negative numbers compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI1n() throws Exception {

        runTest(INT_MINUS_123, INT_MINUS_123, "1");
    }

    /**
     * <testcase> Identical numbers (0) compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI2() throws Exception {

        runTest(new TInteger(0), new TInteger(0), "1");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI3() throws Exception {

        runTest(INT_12, INT_123, "0");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI4() throws Exception {

        runTest(INT_123, INT_12, "0");
    }

    /**
     * <testcase> Equal numbers (-1) compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI5() throws Exception {

        runTest(new TInteger(-1), new TInteger(-1), "1");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI6() throws Exception {

        runTest(INT_123, new TInteger(0), "0");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqI7() throws Exception {

        runTest(new TInteger(1), new TInteger(0), "0");
    }

    /**
     * <testcase> Identical strings compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS1() throws Exception {

        runTest(new TString("abc"), new TString("abc"), "1");
    }

    /**
     * <testcase> Identical empty strings compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS2() throws Exception {

        runTest(new TString(""), new TString(""), "1");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS3() throws Exception {

        runTest(new TString("ab"), new TString("abc"), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS4() throws Exception {

        runTest(new TString("abc"), new TString("ab"), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS5() throws Exception {

        runTest(new TString("{}"), new TString(""), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS6() throws Exception {

        runTest(new TString("abc"), new TString(""), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEqS7() throws Exception {

        runTest(new TString("a"), new TString(""), "0");
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack() throws Exception {

        try {
            p.push(new TInteger(2));
            new Eq("=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

}
