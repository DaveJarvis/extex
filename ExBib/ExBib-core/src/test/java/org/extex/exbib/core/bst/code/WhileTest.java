/*
 * Copyright (C) 2003-2009 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.While;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>while$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class WhileTest {

    /**
     * The code to be repeated.
     */
    private class BodyCode extends AbstractCode {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.code.AbstractCode#execute(BstProcessor,
         *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
         */
        public void execute(BstProcessor processor, Entry entry, Locator locator)
                throws ExBibException {

            bodyCount++;
        }
    }

    /**
     * The code to do the check.
     */
    private class CondCode extends AbstractCode {

        /**
         * The field <tt>rep</tt> contains the repetition counter.
         */
        private int rep;

        /**
         * Creates a new object.
         * 
         * @param rep the repetition counter
         */
        public CondCode(int rep) {

            this.rep = rep;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.core.bst.code.AbstractCode#execute(BstProcessor,
         *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
         */
        public void execute(BstProcessor processor, Entry entry, Locator locator)
                throws ExBibException {

            condCount++;
            processor
                .push(rep-- > 0 ? TokenFactory.T_ONE : TokenFactory.T_ZERO);
        }
    }

    /**
     * The field <tt>bodyCount</tt> contains the body counter.
     */
    private int bodyCount = 0;

    /**
     * The field <tt>condCount</tt> contains the condition counter.
     */
    private int condCount = 0;

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        db = new DBImpl();
        p = new BstInterpreter099c(db, new NullWriter(), null);
        p.addFunction("body", new BodyCode(), null);
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
     * <testcase> The body is not executed when the condition immediately fails.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        p.addFunction("cond0", new CondCode(0), null);
        bodyCount = 0;
        condCount = 0;
        p.push(new TLiteral("cond0", null));
        p.push(new TLiteral("body", null));
        new While("while$").execute(p, null, null);
        assertEquals(0, bodyCount);
        assertEquals(1, condCount);
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The body is executed once if the condition fails the second
     * time it is invoked. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        p.addFunction("cond1", new CondCode(1), null);
        bodyCount = 0;
        condCount = 0;
        p.push(new TLiteral("cond1", null));
        p.push(new TLiteral("body", null));
        new While("while$").execute(p, null, null);
        assertEquals(1, bodyCount);
        assertEquals(2, condCount);
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The body is executed 12 times if the condition fails the 13th
     * time it is invoked. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        p.addFunction("cond12", new CondCode(12), null);
        bodyCount = 0;
        condCount = 0;
        p.push(new TLiteral("cond12", null));
        p.push(new TLiteral("body", null));
        new While("while$").execute(p, null, null);
        assertEquals(12, bodyCount);
        assertEquals(13, condCount);
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The empoty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new While("while$").execute(p, null, null);
    }

    /**
     * <testcase> A stack containing only one element leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack1() throws Exception {

        p.push(new TLiteral("e", null));
        new While("while$").execute(p, null, null);
    }

}
