/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * This file is part of ExBib a BibTeX compatible database.
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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.ProcessorBibtex099c;
import org.extex.exbib.core.bst.code.impl.ChangeCase;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>change.case$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestChangeCase extends TestCase {

    /**
     * The main program just uses the text interface of JUnit.
     * 
     * @param args command-line parameters are ignored
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

        return new TestSuite(TestChangeCase.class);
    }

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
    public TestChangeCase(String name) {

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
        p = new ProcessorBibtex099c(db, new NullWriter(null), null);
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
     * <testcase> A lower case letter is not altered by converting to lower
     * case.</testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test01l() throws Exception {

        testChangeCaseL("a", "a");
    }

    /**
     * <testcase> The format letter "t" leaves alone a single lowercase letter.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test01t() throws Exception {

        testChangeCaseT("a", "a");
    }

    /**
     * <testcase> A lower case letter is properly translated to upper case.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test01u() throws Exception {

        testChangeCaseU("a", "A");
    }

    /**
     * <testcase> A upper case letter is properly translated to lower case.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test02l() throws Exception {

        testChangeCaseL("A", "a");
    }

    /**
     * <testcase> An uppercase letter remains unchanged by format "t".
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test02t() throws Exception {

        testChangeCaseT("A", "A");
    }

    /**
     * <testcase> An uppercase letter remains unchanged by format "u".
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test02u() throws Exception {

        testChangeCaseU("A", "A");
    }

    /**
     * <testcase> An empty string remains unchanged for format "l". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0l() throws Exception {

        testChangeCaseL("", "");
    }

    /**
     * <testcase> An empty string remains unchanged for format "t". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0t() throws Exception {

        testChangeCaseT("", "");
    }

    /**
     * <testcase> An empty string remains unchanged for format "u". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0u() throws Exception {

        testChangeCaseU("", "");
    }

    /**
     * <testcase> Several words are treated separately. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test11l() throws Exception {

        testChangeCaseL("abc: def", "abc: def");
    }

    /**
     * <testcase> Several words are treated separately. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test11t() throws Exception {

        testChangeCaseT("abc: def", "abc: def");
    }

    /**
     * <testcase> Several words are treated separately. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test11u() throws Exception {

        testChangeCaseU("abc: def", "ABC: DEF");
    }

    /**
     * <testcase> Several words are treated separately. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test12l() throws Exception {

        testChangeCaseL("ABC: DEF", "abc: def");
    }

    /**
     * <testcase> Several words are treated separately. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test12t() throws Exception {

        testChangeCaseT("ABC: DEF", "Abc: Def");
    }

    /**
     * <testcase> Several words are treated separately. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test12u() throws Exception {

        testChangeCaseU("ABC: DEF", "ABC: DEF");
    }

    /**
     * <testcase> Braces protect a string from translation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test13l() throws Exception {

        testChangeCaseL("ABC{TeX}DEF", "abc{TeX}def");
    }

    /**
     * <testcase> Braces protect a string from translation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test13t() throws Exception {

        testChangeCaseT("ABC {TeX}DEF", "Abc {TeX}def");
    }

    /**
     * <testcase> Braces protect a string from translation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test13u() throws Exception {

        testChangeCaseU("ABC{TeX}DEF", "ABC{TeX}DEF");
    }

    /**
     * <testcase> Braces protect a string from translation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test13ub() throws Exception {

        testChangeCaseU("abc{TeX}def", "ABC{TeX}DEF");
    }

    /**
     * <testcase> Braces protect a string from translation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test20t() throws Exception {

        testChangeCaseT("{PhD} Dissertation", "{PhD} dissertation");
    }

    /**
     * Run a test.
     * 
     * @param fmt the format
     * @param s the input string
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    private void testChangeCase(String fmt, String s, String res)
            throws Exception {

        p.push(new TString(s));
        p.push(new TString(fmt));
        new ChangeCase("change.case$").execute(p, null, null);
        assertEquals(res, p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * Run a test with the formats "l" and "L".
     * 
     * @param s the input string
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    private void testChangeCaseL(String s, String res) throws Exception {

        testChangeCase("l", s, res);
        testChangeCase("L", s, res);
    }

    /**
     * Run a test with the formats "t" and "T".
     * 
     * @param s the input string
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    private void testChangeCaseT(String s, String res) throws Exception {

        testChangeCase("t", s, res);
        testChangeCase("T", s, res);
    }

    /**
     * Run a test with the formats "u" and "U".
     * 
     * @param s the input string
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    private void testChangeCaseU(String s, String res) throws Exception {

        testChangeCase("u", s, res);
        testChangeCase("U", s, res);
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new ChangeCase("change.case$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack() throws Exception {

        try {
            p.push(new TString("e"));
            new ChangeCase("change.case$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> The format "e" is unknown. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTypeError() throws Exception {

        try {
            p.push(new TString("e"));
            p.push(new TString(""));
            new ChangeCase("change.case$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibIllegalValueException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A group protects from translating to lower case.</testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testx2l() throws Exception {

        testChangeCaseL("ABC: {DE}F", "abc: {DE}f");
    }

    /**
     * <testcase> A group protects from translating to mixed case.</testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testx2t() throws Exception {

        testChangeCaseT("AB{C}: DEF", "Ab{C}: Def");
    }

    /**
     * <testcase> A group protects from translating to upper case.</testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testx2u() throws Exception {

        testChangeCaseU("abc: d{e}f", "ABC: D{e}F");
    }

}
