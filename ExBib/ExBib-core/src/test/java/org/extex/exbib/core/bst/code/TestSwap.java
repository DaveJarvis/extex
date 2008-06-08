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
import org.extex.exbib.core.bst.code.impl.Swap;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.extex.exbib.core.node.impl.TInteger;
import org.extex.exbib.core.node.impl.TString;

/**
 * Test suite for <tt>swap$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestSwap extends TestCase {

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

        return new TestSuite(TestSwap.class);
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
    public TestSwap(String name) {

        super(name);
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
     * <testcase> swap$ complains about an empty stack. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Swap("swap$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> swap$ complains about a short stack of one integer.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testStack1() throws Exception {

        p.push(new TInteger(123, null));

        try {
            new Swap("swap$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> swap$ complains about a short stack of one string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testStack2() throws Exception {

        p.push(new TString("123", null));

        try {
            new Swap("swap$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> swap$ swaps two integers. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSwapInteger() throws Exception {

        p.push(new TInteger(123, null));
        p.push(new TInteger(456, null));
        new Swap("swap$").execute(p, null, null);
        assertEquals("123", p.popInteger(null).getValue());
        assertEquals("456", p.popInteger(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> swap$ swaps two strings. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSwapString() throws Exception {

        p.push(new TString("abc", null));
        p.push(new TString("def", null));
        new Swap("swap$").execute(p, null, null);
        assertEquals("abc", p.popString(null).getValue());
        assertEquals("def", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

}
