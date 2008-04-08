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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.code.impl.AddPeriod;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>add.period$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestAddPeriod extends TestCase {

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

        return new TestSuite(TestAddPeriod.class);
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
    public TestAddPeriod(String name) {

        super(name);
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
            new AddPeriod("add.period$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> An integer gets a period added. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testInteger() throws Exception {

        p.push(new TInteger(123));
        new AddPeriod("add.period$").execute(p, null, null);
        assertEquals("123.", p.popString(null).getValue());
    }

    /**
     * Run a test case where no period is added.
     * 
     * @param in the input (and output) string
     * 
     * @throws Exception in case of an error
     */
    private void testNoAdd(String in) throws Exception {

        p.push(new TString(in));
        new AddPeriod("add.period$").execute(p, null, null);
        assertEquals(in, p.popString(null).getValue());
    }

    /**
     * <testcase> No period is added to the empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoAddEmpty() throws Exception {

        testNoAdd("");
    }

    /**
     * <testcase> No period is added if the last non-brace character is an
     * exclamation mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoAddExclamationMark() throws Exception {

        testNoAdd("abc!");
        testNoAdd("abc!}");

        testNoAdd("abc!}}");
        testNoAdd("abc!}}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * period. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoAddPeriod() throws Exception {

        testNoAdd("abc.");
        testNoAdd("abc.}");
        testNoAdd("abc.}}");
        testNoAdd("abc.}}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * question mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNoAddQuestionMark() throws Exception {

        testNoAdd("abc?");
        testNoAdd("abc?}");
        testNoAdd("abc?}}");
        testNoAdd("abc?}}}");
    }

    /**
     * <testcase> A period is added if the input consists of text only.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testString() throws Exception {

        p.push(new TString("abc"));
        new AddPeriod("add.period$").execute(p, null, null);
        assertTrue(p.popString(null).getValue().equals("abc."));
    }

}
