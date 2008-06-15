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

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.code.impl.Substring;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.TokenFactory;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>substring$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestSubstring {

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Run a test case.
     * 
     * @param s the string
     * @param from the start index
     * @param len the length
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String s, int from, int len, String res)
            throws Exception {

        p.push(new TString(s, null));
        p.push(new TInteger(from, null));
        p.push(new TInteger(len, null));
        new Substring("substring$").execute(p, null, null);
        assertEquals(res, p.popString(null).getValue());
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
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Substring("substring$").execute(p, null, null);
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack1() throws Exception {

        p.push(TokenFactory.T_ZERO);
        new Substring("substring$").execute(p, null, null);
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack2() throws Exception {

        p.push(TokenFactory.T_ZERO);
        p.push(TokenFactory.T_ZERO);
        new Substring("substring$").execute(p, null, null);
    }

    /**
     * <testcase> substring$("abcd", 0, 0) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour1() throws Exception {

        runTest("abcd", 0, 0, "");
    }

    /**
     * <testcase> substring$("abcd", 1, 1) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour2() throws Exception {

        runTest("abcd", 1, 1, "a");
    }

    /**
     * <testcase> substring$("abcd", -1, 1) = "d" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour3() throws Exception {

        runTest("abcd", -1, 1, "d");
    }

    /**
     * <testcase> substring$("abcd", 2, 1) = "b" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour4() throws Exception {

        runTest("abcd", 2, 1, "b");
    }

    /**
     * <testcase> substring$("abcd", -2, 1) = "c" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour5() throws Exception {

        runTest("abcd", -2, 1, "c");
    }

    /**
     * <testcase> substring$("abcd", 1, 2) = "ab" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour6() throws Exception {

        runTest("abcd", 1, 2, "ab");
    }

    /**
     * <testcase> substring$("abcd", -1, 2) = "cd" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringFour7() throws Exception {

        runTest("abcd", -1, 2, "cd");
    }

    /**
     * <testcase> substring$("a", 0, 0) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne1() throws Exception {

        runTest("a", 0, 0, "");
    }

    /**
     * <testcase> substring$("a", -2, -4) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne10() throws Exception {

        runTest("a", -2, -4, "");
    }

    /**
     * <testcase> substring$("a", -3, -6) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne11() throws Exception {

        runTest("a", -3, -6, "");
    }

    /**
     * <testcase> substring$("a", 1, 1) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne2() throws Exception {

        runTest("a", 1, 1, "a");
    }

    /**
     * <testcase> substring$("a", -1, 1) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne3() throws Exception {

        runTest("a", -1, 1, "a");
    }

    /**
     * <testcase> substring$("a", 2, 1) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne4() throws Exception {

        runTest("a", 2, 1, "");
    }

    /**
     * <testcase> substring$("a", -2, 1) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne5() throws Exception {

        runTest("a", -2, 1, "");
    }

    /**
     * <testcase> substring$("a", 1, 2) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne6() throws Exception {

        runTest("a", 1, 2, "a");
    }

    /**
     * <testcase> substring$("a", -1, 2) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne7() throws Exception {

        runTest("a", -1, 2, "a");
    }

    /**
     * <testcase> substring$("a", -2, -2) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne8() throws Exception {

        runTest("a", -2, -2, "");
    }

    /**
     * <testcase> substring$("a", -2, -3) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringOne9() throws Exception {

        runTest("a", -2, -3, "");
    }

    /**
     * <testcase> substring$("abc", 0, 0) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree1() throws Exception {

        runTest("abc", 0, 0, "");
    }

    /**
     * <testcase> substring$("abc", 1, 1) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree2() throws Exception {

        runTest("abc", 1, 1, "a");
    }

    /**
     * <testcase> substring$("abc", -1, 1) = "c" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree3() throws Exception {

        runTest("abc", -1, 1, "c");
    }

    /**
     * <testcase> substring$("abc", 2, 1) = "b" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree4() throws Exception {

        runTest("abc", 2, 1, "b");
    }

    /**
     * <testcase> substring$("abc", -2, 1) = "b" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree5() throws Exception {

        runTest("abc", -2, 1, "b");
    }

    /**
     * <testcase> substring$("abc", 1, 2) = "ab" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree6() throws Exception {

        runTest("abc", 1, 2, "ab");
    }

    /**
     * <testcase> substring$("abc", -1, 2) = "bc" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringThree7() throws Exception {

        runTest("abc", -1, 2, "bc");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", 0, 2) = ""
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix1() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", 0, 2, "");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", -26, 2) = "a"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix10() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", -26, 2, "a");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", -27, 2) = ""
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix11() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", -27, 2, "");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", 1, 2) = "ab"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix2() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", 1, 2, "ab");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", 2, 2) = "bc"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix3() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", 2, 2, "bc");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", 25, 2) = "yz"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix4() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", 25, 2, "yz");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", 26, 2) = "z"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix5() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", 26, 2, "z");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", 27, 2) = ""
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix6() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", 27, 2, "");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", -1, 2) = "yz"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix7() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", -1, 2, "yz");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", -2, 2) = "xy"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix8() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", -2, 2, "xy");
    }

    /**
     * <testcase> substring$("abcdefghijklmnopqrstuvwxyz", -25, 2) = "ab"
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwentysix9() throws Exception {

        runTest("abcdefghijklmnopqrstuvwxyz", -25, 2, "ab");
    }

    /**
     * <testcase> substring$("ab", 0, 0) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo1() throws Exception {

        runTest("ab", 0, 0, "");
    }

    /**
     * <testcase> substring$("ab", 1, 1) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo2() throws Exception {

        runTest("ab", 1, 1, "a");
    }

    /**
     * <testcase> substring$("ab", -1, 1) = "b" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo3() throws Exception {

        runTest("ab", -1, 1, "b");
    }

    /**
     * <testcase> substring$("ab", 2, 1) = "b" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo4() throws Exception {

        runTest("ab", 2, 1, "b");
    }

    /**
     * <testcase> substring$("ab", -2, 1) = "a" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo5() throws Exception {

        runTest("ab", -2, 1, "a");
    }

    /**
     * <testcase> substring$("ab", 1, 2) = "ab" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo6() throws Exception {

        runTest("ab", 1, 2, "ab");
    }

    /**
     * <testcase> substring$("ab", -1, 2) = "ab" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringTwo7() throws Exception {

        runTest("ab", -1, 2, "ab");
    }

    /**
     * <testcase> substring$("", 0, 0) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero1() throws Exception {

        runTest("", 0, 0, "");
    }

    /**
     * <testcase> substring$("", 1, 1) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero2() throws Exception {

        runTest("", 1, 1, "");
    }

    /**
     * <testcase> substring$("", -1, 1) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero3() throws Exception {

        runTest("", -1, 1, "");
    }

    /**
     * <testcase> substring$("", 2, 1) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero4() throws Exception {

        runTest("", 2, 1, "");
    }

    /**
     * <testcase> substring$("", -2, 1) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero5() throws Exception {

        runTest("", -2, 1, "");
    }

    /**
     * <testcase> substring$("", 1, 2) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero6() throws Exception {

        runTest("", 1, 2, "");
    }

    /**
     * <testcase> substring$("", -1, 2) = "" </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstringZero7() throws Exception {

        runTest("", -1, 2, "");
    }

}
