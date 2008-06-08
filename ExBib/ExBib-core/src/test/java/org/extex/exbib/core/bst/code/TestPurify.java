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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.code.impl.Purify;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>purify$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestPurify extends TestCase {

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

        return new TestSuite(TestPurify.class);
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
    public TestPurify(String name) {

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
     * Run a test case.
     * 
     * @param in the input
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    private void test(String in, String res) throws Exception {

        p.push(new TString(in, null));
        new Purify("purify$").execute(p, null, null);
        assertEquals(res, p.popString(null).getValue());
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Purify("purify$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Lowercase letters are left unchanged. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify1() throws Exception {

        test("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz");
    }

    /**
     * <testcase> \i is translated to i. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify10() throws Exception {

        test("{\\'\\i}\\'\\i", "ii");
    }

    /**
     * <testcase> \j is translated to j. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify11() throws Exception {

        test("{\\'\\j}\\'\\j", "jj");
    }

    /**
     * <testcase> \LaTeX is translated to LaTeX. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify12() throws Exception {

        test("{{\\LaTeX}}123{x\\LaTeX}123\\LaTeX", "LaTeX123xLaTeX123LaTeX");
    }

    /**
     * <testcase> \TeX is translated to TeX. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify13() throws Exception {

        test("{{\\TeX}}123{xxx\\TeX}123\\TeX", "TeX123xxxTeX123TeX");
    }

    /**
     * <testcase> Uppercase letters are left unchanged. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify2() throws Exception {

        test("ABCDEFGHIJKLMNOPQRSTUVWXYZ", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * <testcase> Numbers are left unchanged. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify3() throws Exception {

        test("0123456789", "0123456789");
    }

    /**
     * <testcase> Whitespace (including ~) is normalized to spaces. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify4() throws Exception {

        test("a  b~c-d", "a  b c d");
    }

    /**
     * <testcase> Parentheses and brackets are discarded. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify5() throws Exception {

        test("[]()", "");
    }

    /**
     * <testcase> Braces are discarded. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify6() throws Exception {

        test("{}", "");
    }

    /**
     * <testcase> \ae and \AE are translated to ae and AE resp. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify7() throws Exception {

        test("{\\ae}\\ae{\\AE}\\AE", "aeaeAEAE");
    }

    /**
     * <testcase> \oe and \OE are translated to oe and OE resp. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify8() throws Exception {

        test("{\\oe}\\oe{\\OE}\\OE", "oeoeOEOE");
    }

    /**
     * <testcase> \ss is translated to ss. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPurify9() throws Exception {

        test("{\\ss}\\ss", "ssss");
    }

}
