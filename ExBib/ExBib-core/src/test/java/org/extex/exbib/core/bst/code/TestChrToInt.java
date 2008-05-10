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
import org.extex.exbib.core.bst.code.impl.ChrToInt;
import org.extex.exbib.core.bst.code.impl.IntToChr;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>chr.to.int$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestChrToInt extends TestCase {

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

        return new TestSuite(TestChrToInt.class);
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
    public TestChrToInt(String name) {

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
     * Run a test.
     * 
     * @param c the character code
     * 
     * @throws Exception in case of an error
     */
    private void testChrToInt(int c) throws Exception {

        p.push(new TString(String.valueOf((char) c)));
        new ChrToInt("chr.to.int$").execute(p, null, null);
        assertEquals(c, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The character 0 is mapped correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testChrToInt0() throws Exception {

        testChrToInt(0);
    }

    /**
     * <testcase> The character 123 is mapped correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testChrToInt123() throws Exception {

        testChrToInt(123);
    }

    /**
     * <testcase> The character 32 is mapped correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testChrToInt32() throws Exception {

        testChrToInt(32);
    }

    /**
     * <testcase> The first argument can not be a String. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testChrToIntlong() throws Exception {

        try {
            p.push(new TString(".."));
            new ChrToInt("chr.to.int$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibIllegalValueException e) {
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
            new IntToChr("chr.to.int$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

}
