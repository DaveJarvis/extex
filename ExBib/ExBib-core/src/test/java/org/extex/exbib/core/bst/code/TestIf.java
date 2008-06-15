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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.code.impl.If;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.TokenFactory;
import org.extex.exbib.core.bst.node.impl.TChar;
import org.extex.exbib.core.bst.node.impl.TLiteral;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>if$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestIf {

    /**
     * Test case emulating the else branch.
     */
    private class ElseCode extends AbstractCode {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
         *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
         *      org.extex.exbib.core.io.Locator)
         */
        @Override
        public void execute(Processor processor, Entry entry, Locator locator)
                throws ExBibException {

            elseCount++;
        }
    }

    /**
     * Test case emulating the then branch.
     */
    private class ThenCode extends AbstractCode {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.code.AbstractCode#execute(
         *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
         *      org.extex.exbib.core.io.Locator)
         */
        @Override
        public void execute(Processor processor, Entry entry, Locator locator)
                throws ExBibException {

            thenCount++;
        }
    }

    /**
     * The field <tt>thenCount</tt> contains the counter for the then branch.
     */
    private int thenCount = 0;

    /**
     * The field <tt>elseCount</tt> contains the counter for the else branch.
     */
    private int elseCount = 0;

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        db = new DBImpl();
        p = new BstInterpreter099c(db, new NullWriter(null), null);
        p.addFunction("t", new ThenCode(), null);
        p.addFunction("e", new ElseCode(), null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
        db = null;
    }

    /**
     * <testcase> If the condition evaluates to 0 then only the else branch is
     * executed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        thenCount = 0;
        elseCount = 0;
        p.push(TokenFactory.T_ZERO);
        p.push(new TLiteral("t", null));
        p.push(new TLiteral("e", null));
        new If("if$").execute(p, null, null);
        assertEquals(0, thenCount);
        assertEquals(1, elseCount);
        assertTrue(p.popUnchecked() == null);
    }

    /**
     * <testcase> If the condition evaluates to 1 then only the then branch is
     * executed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        thenCount = 0;
        elseCount = 0;
        p.push(TokenFactory.T_ONE);
        p.push(new TLiteral("t", null));
        p.push(new TLiteral("e", null));
        new If("if$").execute(p, null, null);
        assertEquals(1, thenCount);
        assertEquals(0, elseCount);
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new If("if$").execute(p, null, null);
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack1() throws Exception {

        p.push(new TLiteral("e", null));
        new If("if$").execute(p, null, null);
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack2() throws Exception {

        p.push(new TLiteral("t", null));
        p.push(new TLiteral("e", null));
        new If("if$").execute(p, null, null);
    }

    /**
     * <testcase> The first argument needs to be a number. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public void testTypeError1() throws Exception {

        p.push(new TString("xxx", null));
        p.push(new TLiteral("t", null));
        p.push(new TLiteral("e", null));
        new If("if$").execute(p, null, null);
    }

    /**
     * <testcase> The first argument needs to be a number. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public void testTypeError2() throws Exception {

        p.push(new TChar('x', null));
        p.push(new TLiteral("t", null));
        p.push(new TLiteral("e", null));
        new If("if$").execute(p, null, null);
    }

}
