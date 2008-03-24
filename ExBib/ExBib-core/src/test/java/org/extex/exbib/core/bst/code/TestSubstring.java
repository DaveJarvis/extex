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
import org.extex.exbib.core.bst.code.impl.Substring;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>substring$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestSubstring extends TestCase {

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

        return new TestSuite(TestSubstring.class);
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
    public TestSubstring(String name) {

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
            new Substring("substring$").execute(p, null, null);
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
    public void testShortStack1() throws Exception {

        try {
            p.push(new TInteger(0));
            new Substring("substring$").execute(p, null, null);
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
    public void testShortStack2() throws Exception {

        try {
            p.push(new TInteger(0));
            p.push(new TInteger(0));
            new Substring("substring$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s
     * @param from
     * @param len
     * @param res
     * @throws Exception
     */
    private void testSubstring(String s, int from, int len, String res)
            throws Exception {

        p.push(new TString(s));
        p.push(new TInteger(from));
        p.push(new TInteger(len));
        new Substring("substring$").execute(p, null, null);
        assertEquals(res, p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour1() throws Exception {

        testSubstring("abcd", 0, 0, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour2() throws Exception {

        testSubstring("abcd", 1, 1, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour3() throws Exception {

        testSubstring("abcd", -1, 1, "d");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour4() throws Exception {

        testSubstring("abcd", 2, 1, "b");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour5() throws Exception {

        testSubstring("abcd", -2, 1, "c");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour6() throws Exception {

        testSubstring("abcd", 1, 2, "ab");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringFour7() throws Exception {

        testSubstring("abcd", -1, 2, "cd");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne1() throws Exception {

        testSubstring("a", 0, 0, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne10() throws Exception {

        testSubstring("a", -2, -4, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne11() throws Exception {

        testSubstring("a", -3, -6, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne2() throws Exception {

        testSubstring("a", 1, 1, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne3() throws Exception {

        testSubstring("a", -1, 1, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne4() throws Exception {

        testSubstring("a", 2, 1, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne5() throws Exception {

        testSubstring("a", -2, 1, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne6() throws Exception {

        testSubstring("a", 1, 2, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne7() throws Exception {

        testSubstring("a", -1, 2, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne8() throws Exception {

        testSubstring("a", -2, -2, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringOne9() throws Exception {

        testSubstring("a", -2, -3, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree1() throws Exception {

        testSubstring("abc", 0, 0, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree2() throws Exception {

        testSubstring("abc", 1, 1, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree3() throws Exception {

        testSubstring("abc", -1, 1, "c");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree4() throws Exception {

        testSubstring("abc", 2, 1, "b");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree5() throws Exception {

        testSubstring("abc", -2, 1, "b");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree6() throws Exception {

        testSubstring("abc", 1, 2, "ab");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringThree7() throws Exception {

        testSubstring("abc", -1, 2, "bc");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix1() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", 0, 2, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix10() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", -26, 2, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix11() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", -27, 2, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix2() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", 1, 2, "ab");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix3() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", 2, 2, "bc");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix4() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", 25, 2, "yz");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix5() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", 26, 2, "z");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix6() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", 27, 2, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix7() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", -1, 2, "yz");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix8() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", -2, 2, "xy");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwentysix9() throws Exception {

        testSubstring("abcdefghijklmnopqrstuvwxyz", -25, 2, "ab");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo1() throws Exception {

        testSubstring("ab", 0, 0, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo2() throws Exception {

        testSubstring("ab", 1, 1, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo3() throws Exception {

        testSubstring("ab", -1, 1, "b");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo4() throws Exception {

        testSubstring("ab", 2, 1, "b");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo5() throws Exception {

        testSubstring("ab", -2, 1, "a");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo6() throws Exception {

        testSubstring("ab", 1, 2, "ab");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringTwo7() throws Exception {

        testSubstring("ab", -1, 2, "ab");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero1() throws Exception {

        testSubstring("", 0, 0, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero2() throws Exception {

        testSubstring("", 1, 1, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero3() throws Exception {

        testSubstring("", -1, 1, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero4() throws Exception {

        testSubstring("", 2, 1, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero5() throws Exception {

        testSubstring("", -2, 1, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero6() throws Exception {

        testSubstring("", 1, 2, "");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSubstringZero7() throws Exception {

        testSubstring("", -1, 2, "");
    }

}
