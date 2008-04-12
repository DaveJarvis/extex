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
import org.extex.exbib.core.bst.code.impl.IntToStr;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>int.to.str$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestIntToStr extends TestCase {

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

        return new TestSuite(TestIntToStr.class);
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
    public TestIntToStr(String name) {

        super(name);
    }

    /**
     * Run a test case.
     * 
     * @param t1 the argument
     * 
     * @throws Exception in case of an error
     */
    private void runTest(int t1) throws Exception {

        p.push(new TInteger(t1));
        new IntToStr("int.to.str$").execute(p, null, null);
        assertEquals(String.valueOf(t1), p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new Processor099Impl(new DBImpl(), new NullWriter(null), null);
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
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new IntToStr("int.to.str$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test int.to.str$(0). </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testIntToStr0() throws Exception {

        runTest(0);
    }

    /**
     * <testcase> Test int.to.str$(123). </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testIntToStr123() throws Exception {

        runTest(123);
    }

    /**
     * <testcase> Test int.to.str$(32). </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testIntToStr32() throws Exception {

        runTest(32);
    }

    /**
     * <testcase> Test int.to.str$(-1). </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testIntToStrMinus1() throws Exception {

        runTest(-1);
    }

    /**
     * <testcase> Test int.to.str$("abc") leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testString() throws Exception {

        try {
            p.push(new TString("abc"));
            new IntToStr("int.to.str$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }
}
