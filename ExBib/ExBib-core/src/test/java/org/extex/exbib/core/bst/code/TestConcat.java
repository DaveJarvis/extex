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
import org.extex.exbib.core.bst.code.impl.Concat;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>concat$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestConcat extends TestCase {

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

        return new TestSuite(TestConcat.class);
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
    public TestConcat(String name) {

        super(name);
    }

    /**
     * Apply concat$ on two strings and check the result.
     * 
     * @param s1 the first string to concat
     * @param s2 the second string to concat
     * @param result the expected result
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String s1, String s2, String result) throws Exception {

        p.push(new TString(s1, null));
        p.push(new TString(s2, null));
        new Concat("*").execute(p, null, null);
        assertEquals(result, p.popUnchecked().getValue());
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
     * <testcase> An short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1Stack() throws Exception {

        p.push(new TString("abc", null));

        try {
            new Concat("*").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> "ab" + "cd" = "abcd" </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testConcat() throws Exception {

        runTest("ab", "cd", "abcd");
    }

    /**
     * <testcase> "" + "" = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testConcatEmpty() throws Exception {

        runTest("", "", "");
    }

    /**
     * <testcase> "" + "abc" = "abc" </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testConcatEmptyLeft() throws Exception {

        runTest("", "abc", "abc");
    }

    /**
     * <testcase> "abc" + "" = "abc" </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testConcatEmptyRight() throws Exception {

        runTest("abc", "", "abc");
    }

    /**
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Concat("*").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> The first argument needs to be a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTypeError1() throws Exception {

        p.push(new TInteger(123, null));
        try {
            new Concat("*").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingStringException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> The second argument needs to be a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTypeError2() throws Exception {

        p.push(new TInteger(123, null));
        p.push(new TString("123", null));
        try {
            new Concat("*").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingStringException e) {
            assertTrue(true);
        }
    }

}
