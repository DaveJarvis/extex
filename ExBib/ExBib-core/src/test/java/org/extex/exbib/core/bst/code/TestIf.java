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
import org.extex.exbib.core.bst.code.impl.If;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>if$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestIf extends TestCase {

    /**
     * TODO gene: missing JavaDoc.
     */
    private class ElseCode extends AbstractCode {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.code.AbstractCode#execute(org.extex.exbib.core.bst.Processor,
         *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
         */
        @Override
        public void execute(Processor processor, Entry entry, Locator locator)
                throws ExBibException {

            elseCount++;
        }
    }

    /**
     * TODO gene: missing JavaDoc.
     */
    private class ThenCode extends AbstractCode {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.code.AbstractCode#execute(org.extex.exbib.core.bst.Processor,
         *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
         */
        @Override
        public void execute(Processor processor, Entry entry, Locator locator)
                throws ExBibException {

            thenCount++;
        }
    }

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

        return new TestSuite(TestIf.class);
    }

    /**
     * The field <tt>thenCount</tt> contains the ...
     */
    protected int thenCount = 0;

    /**
     * The field <tt>elseCount</tt> contains the ...
     */
    protected int elseCount = 0;

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestIf(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        db = new DBImpl();
        p = new Processor099Impl(db, new NullWriter(null), null);
        p.addFunction("t", new ThenCode());
        p.addFunction("e", new ElseCode());
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
        db = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        thenCount = elseCount = 0;
        p.push(new TInteger(0));
        p.push(new TLiteral("t"));
        p.push(new TLiteral("e"));
        new If("if$").execute(p, null, null);
        assertEquals(0, thenCount);
        assertEquals(1, elseCount);
        assertTrue(p.popUnchecked() == null);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        thenCount = elseCount = 0;
        p.push(new TInteger(1));
        p.push(new TLiteral("t"));
        p.push(new TLiteral("e"));
        new If("if$").execute(p, null, null);
        assertEquals(1, thenCount);
        assertEquals(0, elseCount);
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new If("if$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack1() throws Exception {

        try {
            p.push(new TLiteral("e"));
            new If("if$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack2() throws Exception {

        try {
            p.push(new TLiteral("t"));
            p.push(new TLiteral("e"));
            new If("if$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }
}
