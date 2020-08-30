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
import org.extex.exbib.core.bst.code.impl.TextLength;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@code text.length$}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TextLengthTest {

    /**
     * The field {@code p} contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Run a test case.
     * 
     * @param in the input string
     * @param len the length
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String in, int len) throws Exception {

        p.push(new TString(in, null));
        new TextLength("text.length$").execute(p, null, null);
        assertEquals(len, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        p = new BstInterpreter099c(new DBImpl(), new NullWriter(), null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     *  The "a" has length 1.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        runTest("a", 1);
    }

    /**
     *  The "a!" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1a() throws Exception {

        runTest("a!", 2);
    }

    /**
     *  The "a!}" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1b() throws Exception {

        runTest("a!}", 2);
    }

    /**
     *  The "a!}}" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1c() throws Exception {

        runTest("a!}}", 2);
    }

    /**
     *  The "a!}}}" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1d() throws Exception {

        runTest("a!}}}", 2);
    }

    /**
     *  The "a." has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1e() throws Exception {

        runTest("a.", 2);
    }

    /**
     *  The "a.}" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1f() throws Exception {

        runTest("a.}", 2);
    }

    /**
     *  The "a.}}" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1g() throws Exception {

        runTest("a.}}", 2);
    }

    /**
     *  The "a.}}}" has length 2.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1h() throws Exception {

        runTest("a.}}}", 2);
    }

    /**
     *  The "ac!" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3a() throws Exception {

        runTest("ac!", 3);
    }

    /**
     *  The "ac!}" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3b() throws Exception {

        runTest("ac!}", 3);
    }

    /**
     *  The "ac!}}" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3c() throws Exception {

        runTest("ac!}}", 3);
    }

    /**
     *  The "ac!}}}" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3d() throws Exception {

        runTest("ac!}}}", 3);
    }

    /**
     *  The "ac." has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3e() throws Exception {

        runTest("ac.", 3);
    }

    /**
     *  The "ac.}" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3f() throws Exception {

        runTest("ac.}", 3);
    }

    /**
     *  The "ac.}}" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3g() throws Exception {

        runTest("ac.}}", 3);
    }

    /**
     *  The "ac.}}" has length 3.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3h() throws Exception {

        runTest("ac.}}}", 3);
    }

    /**
     *  The control sequence \" at level 0 has no influence.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAcc1() throws Exception {

        runTest("a\\\"bc", 3);
    }

    /**
     * The argument of a control sequence \" at level 0 has no influence
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testAcc2() throws Exception {

        runTest("a\\\"{b}c", 3);
    }

    /**
     * The argument of a control sequence \" at level 0 has no influence
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testAcc3() throws Exception {

        runTest("a\\'{b}c", 3);
    }

    /**
     * The argument of a control sequence \" at level 0 has no influence
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testAcc4() throws Exception {

        runTest("a\\^{b}c", 3);
    }

    /**
     *  The empty string has length 0.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty() throws Exception {

        runTest("", 0);
    }

    /**
     *  Braces and brackets do not count.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty2() throws Exception {

        runTest("{{[[][\t\n ", 0);
    }

    /**
     *  The empty stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new TextLength("text.length$").execute(p, null, null);
    }

    /**
     *  The length of an integer is the number of digits.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteger() throws Exception {

        p.push(new TInteger(9876, null));
        new TextLength("text.length$").execute(p, null, null);
        assertEquals(4, p.popInteger(null).getInt());
        assertEquals(null, p.popUnchecked());
    }

    /**
     *  The backslash at the end does not count.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro1() throws Exception {

        runTest("\\", 0);
    }

    /**
     *  A macro of length 1 counts as one character.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro2() throws Exception {

        runTest("\\a", 1);
    }

    /**
     *  A macro of length 3 counts as one character.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro3() throws Exception {

        runTest("\\abc", 1);
    }

    /**
     *  A macro of length 3 counts as one character.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro4() throws Exception {

        runTest("\\abc x", 2);
    }

}
