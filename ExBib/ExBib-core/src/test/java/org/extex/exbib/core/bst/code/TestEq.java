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
import org.extex.exbib.core.bst.code.impl.Eq;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>eq$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestEq {

    /**
     * The field <tt>INT_MINUS_123</tt> contains -123.
     */
    private static final TInteger INT_MINUS_123 = new TInteger(-123, null);

    /**
     * The field <tt>INT_12</tt> contains 12.
     */
    private static final TInteger INT_12 = new TInteger(12, null);

    /**
     * The field <tt>INT_123</tt> contains 123.
     */
    private static final TInteger INT_123 = new TInteger(123, null);

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Run a test case.
     * 
     * @param t1 the first token
     * @param t2 the second token
     * @param result the expected result
     * 
     * @throws Exception in case of an error
     */
    private void runTest(Token t1, Token t2, String result) throws Exception {

        p.push(t1);
        p.push(t2);
        new Eq("=").execute(p, null, null);
        assertEquals(result, p.popInteger(null).getValue());
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
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Eq("=").execute(p, null, null);
    }

    /**
     * <testcase> Identical numbers compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI1() throws Exception {

        runTest(INT_123, INT_123, "1");
    }

    /**
     * <testcase> Identical negative numbers compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI1n() throws Exception {

        runTest(INT_MINUS_123, INT_MINUS_123, "1");
    }

    /**
     * <testcase> Identical numbers (0) compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI2() throws Exception {

        runTest(TokenFactory.T_ZERO, TokenFactory.T_ZERO, "1");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI3() throws Exception {

        runTest(INT_12, INT_123, "0");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI4() throws Exception {

        runTest(INT_123, INT_12, "0");
    }

    /**
     * <testcase> Equal numbers (-1) compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI5() throws Exception {

        runTest(new TInteger(-1, null), new TInteger(-1, null), "1");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI6() throws Exception {

        runTest(INT_123, TokenFactory.T_ZERO, "0");
    }

    /**
     * <testcase> Different numbers compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqI7() throws Exception {

        runTest(TokenFactory.T_ONE, TokenFactory.T_ZERO, "0");
    }

    /**
     * <testcase> Identical strings compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS1() throws Exception {

        runTest(new TString("abc", null), new TString("abc", null), "1");
    }

    /**
     * <testcase> Identical empty strings compare as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS2() throws Exception {

        runTest(new TString("", null), new TString("", null), "1");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS3() throws Exception {

        runTest(new TString("ab", null), new TString("abc", null), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS4() throws Exception {

        runTest(new TString("abc", null), new TString("ab", null), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS5() throws Exception {

        runTest(new TString("{}", null), new TString("", null), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS6() throws Exception {

        runTest(new TString("abc", null), new TString("", null), "0");
    }

    /**
     * <testcase> Different strings compare as not equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEqS7() throws Exception {

        runTest(new TString("a", null), new TString("", null), "0");
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack() throws Exception {

        p.push(new TInteger(2, null));
        new Eq("=").execute(p, null, null);
    }

}
