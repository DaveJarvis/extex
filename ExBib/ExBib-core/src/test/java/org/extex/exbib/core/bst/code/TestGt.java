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

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.Gt;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>&gt;</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestGt {

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Run a test case.
     * 
     * @param t1 the first integer
     * @param t2 the second integer
     * 
     * @throws Exception in case of an error
     */
    private void run(int t1, int t2) throws Exception {

        p.push(new TInteger(t2, null));
        p.push(new TInteger(t1, null));
        new Gt(">").execute(p, null, null);
        assertEquals((t1 < t2 ? 1 : 0), p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        p = new BstInterpreter099c(new DBImpl(), new NullWriter(null), null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void test1Stack() throws Exception {

        p.push(new TInteger(2, null));
        new Gt(">").execute(p, null, null);
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Gt(">").execute(p, null, null);
    }

    /**
     * <testcase> Test 0 > 0. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt00() throws Exception {

        run(0, 0);
    }

    /**
     * <testcase> Test 1 > 0. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt10() throws Exception {

        run(1, 0);
    }

    /**
     * <testcase> Test 1 > 1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt11() throws Exception {

        run(1, 1);
    }

    /**
     * <testcase> Test 1 > 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt12() throws Exception {

        run(1, 2);
    }

    /**
     * <testcase> Test 123 > 123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt123123() throws Exception {

        run(123, 123);
    }

    /**
     * <testcase> Test 123 > -123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt123Minus123() throws Exception {

        run(123, -123);
    }

    /**
     * <testcase> Test 1 > -1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt1Minus1() throws Exception {

        run(1, -1);
    }

    /**
     * <testcase> Test 2 > 1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt21() throws Exception {

        run(2, 1);
    }

    /**
     * <testcase> Test 2 > 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt22() throws Exception {

        run(2, 2);
    }

    /**
     * <testcase> Test -123 > 123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGtMinus123123() throws Exception {

        run(-123, 123);
    }

    /**
     * <testcase> Test -1 > -2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGtMinus1Minus2() throws Exception {

        run(-1, -2);
    }

    /**
     * <testcase> Test -2 > -1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGtMinus2Minus1() throws Exception {

        run(-2, -1);
    }

    /**
     * <testcase> A type error in the first argument leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public void testTypeError1() throws Exception {

        p.push(new TString("2", null));
        p.push(new TInteger(2, null));
        new Gt(">").execute(p, null, null);
    }

    /**
     * <testcase> A type error in the second argument leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingNumberException.class)
    public void testTypeError2() throws Exception {

        p.push(new TString("2", null));
        p.push(new TInteger(2, null));
        new Gt(">").execute(p, null, null);
    }

}
