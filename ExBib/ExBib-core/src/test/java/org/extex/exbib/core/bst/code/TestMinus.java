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
import org.extex.exbib.core.bst.code.impl.Minus;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>-</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestMinus extends TestCase {

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

        return new TestSuite(TestMinus.class);
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
    public TestMinus(String name) {

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
    public void test1Stack() throws Exception {

        try {
            p.push(new TInteger(2));
            new Minus("-").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testEmptyStack() throws Exception {

        try {
            new Minus("-").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param t1
     * @param t2
     * @throws Exception
     */
    private void textMinus(int t1, int t2) throws Exception {

        p.push(new TInteger(t2));
        p.push(new TInteger(t1));
        new Minus("-").execute(p, null, null);
        assertEquals(t2 - t1, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus_1_2() throws Exception {

        textMinus(-1, -2);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus_123123() throws Exception {

        textMinus(-123, 123);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus_2_1() throws Exception {

        textMinus(-2, -1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus00() throws Exception {

        textMinus(0, 0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus1_1() throws Exception {

        textMinus(1, -1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus10() throws Exception {

        textMinus(1, 0);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus11() throws Exception {

        textMinus(1, 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus12() throws Exception {

        textMinus(1, 2);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus123_123() throws Exception {

        textMinus(123, -123);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus123123() throws Exception {

        textMinus(123, 123);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus21() throws Exception {

        textMinus(2, 1);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void textMinus22() throws Exception {

        textMinus(2, 2);
    }

}
