/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.code.impl.IntToChr;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>int.to.chr$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestIntToChr extends TestCase {

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

        return new TestSuite(TestIntToChr.class);
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
    public TestIntToChr(String name) {

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
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testEmptyStack() throws Exception {

        try {
            new IntToChr("int.to.chr$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param t1
     * @throws Exception
     */
    private void testIntToChr(int t1) throws Exception {

        p.push(new TInteger(t1));
        new IntToChr("int.to.chr$").execute(p, null, null);
        assertEquals(String.valueOf((char) t1), p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testIntToChr_1() throws Exception {

        try {
            testIntToChr(-1);
            assertTrue(false);
        } catch (ExBibIllegalValueException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testIntToChr0() throws Exception {

        testIntToChr(0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testIntToChr123() throws Exception {

        testIntToChr(123);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testIntToChr32() throws Exception {

        testIntToChr(32);
    }

}