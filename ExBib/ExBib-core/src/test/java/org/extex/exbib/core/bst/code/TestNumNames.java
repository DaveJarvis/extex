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
import org.extex.exbib.core.bst.ProcessorBibtex099c;
import org.extex.exbib.core.bst.code.impl.NumNames;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>num.names$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestNumNames extends TestCase {

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

        return new TestSuite(TestNumNames.class);
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
    public TestNumNames(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new ProcessorBibtex099c(new DBImpl(), new NullWriter(null), null);
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
     * Run a single test.
     * 
     * @param in the input string
     * @param len the length
     * 
     * @throws Exception in case of an error
     */
    private void test(String in, int len) throws Exception {

        p.push(new TString(in));
        new NumNames("num.names$").execute(p, null, null);
        assertEquals(len, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> A single name is correclty recognized.</testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        test("Gerd Neugebauer", 1);
    }

    /**
     * <testcase> A company should be enclosed in braces. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testCompany() throws Exception {

        test("{Dun and Bradstreet}", 1);
    }

    /**
     * <testcase> The empty name has 0 names in it. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        test("", 0);
    }

    /**
     * <testcase> num.names$ nees an argument on the stack. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new NumNames("num.names$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A trailing "and" is ignored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEnd() throws Exception {

        test("Gerd Neugebauer and", 1);
    }

    /**
     * <testcase> A trailing "and " is ignored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEnd2() throws Exception {

        test("Gerd Neugebauer and ", 1);
    }

    /**
     * <testcase> An integer argument leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testInteger() throws Exception {

        try {
            p.push(new TInteger(9876));
            new NumNames("num.names$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> "\tand " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testOthers1() throws Exception {

        test("Gerd Neugebauer\tand others", 2);
    }

    /**
     * <testcase> " and " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testOthers2() throws Exception {

        test("Gerd Neugebauer and others", 2);
    }

    /**
     * <testcase> "\tand\n" is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testOthers3() throws Exception {

        test("Gerd Neugebauer\tand\nothers", 2);
    }

    /**
     * <testcase> " and " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTwo1() throws Exception {

        test("Stan Laurel and Oliver Hardy", 2);
    }

    /**
     * <testcase> "\tand " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTwo2() throws Exception {

        test("Stan Laurel\tand Oliver Hardy", 2);
    }

    /**
     * <testcase> " and\t" is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTwo3() throws Exception {

        test("Stan Laurel and\tOliver Hardy", 2);
    }

    /**
     * <testcase> "and" in a name is not recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testX() throws Exception {

        test("Arhur Landry", 1);
    }

}
