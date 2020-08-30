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
import org.extex.exbib.core.bst.code.impl.ChangeCase;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@code change.case$}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ChangeCaseTest {

    /**
     * The field {@code db} contains the database.
     */
    private DB db = null;

    /**
     * The field {@code p} contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Create an object to be tested.
     * 
     * @return the test object
     */
    protected ChangeCase makeTestInstance() {

        return new ChangeCase("change.case$");
    }

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        db = new DBImpl();
        p = new BstInterpreter099c(db, new NullWriter(), null);
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
     *  A lower case letter is not altered by converting to lower
     * case.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test01l() throws Exception {

        testChangeCaseL("a", "a");
    }

    /**
     *  The format letter "t" leaves alone a single lowercase letter.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test01t() throws Exception {

        testChangeCaseT("a", "a");
    }

    /**
     *  A lower case letter is properly translated to upper case.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test01u() throws Exception {

        testChangeCaseU("a", "A");
    }

    /**
     *  A upper case letter is properly translated to lower case.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test02l() throws Exception {

        testChangeCaseL("A", "a");
    }

    /**
     *  An uppercase letter remains unchanged by format "t".
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test02t() throws Exception {

        testChangeCaseT("A", "A");
    }

    /**
     *  An uppercase letter remains unchanged by format "u".
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test02u() throws Exception {

        testChangeCaseU("A", "A");
    }

    /**
     *  An empty string remains unchanged for format "l".
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0l() throws Exception {

        testChangeCaseL("", "");
    }

    /**
     *  An empty string remains unchanged for format "t".
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0t() throws Exception {

        testChangeCaseT("", "");
    }

    /**
     *  An empty string remains unchanged for format "u".
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0u() throws Exception {

        testChangeCaseU("", "");
    }

    /**
     *  Several words are treated separately.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11l() throws Exception {

        testChangeCaseL("abc: def", "abc: def");
    }

    /**
     *  Several words are treated separately.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11t() throws Exception {

        testChangeCaseT("abc: def", "abc: def");
    }

    /**
     *  Several words are treated separately.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11u() throws Exception {

        testChangeCaseU("abc: def", "ABC: DEF");
    }

    /**
     *  Several words are treated separately.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12l() throws Exception {

        testChangeCaseL("ABC: DEF", "abc: def");
    }

    /**
     *  Several words are treated separately.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12t() throws Exception {

        testChangeCaseT("ABC: DEF", "Abc: Def");
    }

    /**
     *  Several words are treated separately.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12u() throws Exception {

        testChangeCaseU("ABC: DEF", "ABC: DEF");
    }

    /**
     *  Braces protect a string from translation.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13l() throws Exception {

        testChangeCaseL("ABC{TeX}DEF", "abc{TeX}def");
    }

    /**
     *  Braces protect a string from translation.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13t() throws Exception {

        testChangeCaseT("ABC {TeX}DEF", "Abc {TeX}def");
    }

    /**
     *  Braces protect a string from translation.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13u() throws Exception {

        testChangeCaseU("ABC{TeX}DEF", "ABC{TeX}DEF");
    }

    /**
     *  Braces protect a string from translation.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13ub() throws Exception {

        testChangeCaseU("abc{TeX}def", "ABC{TeX}DEF");
    }

    /**
     *  Braces protect a string from translation.
     * 
     * @throws Exception in case of an error
     */
    @Test
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

        p.push(new TString(s, null));
        p.push(new TString(fmt, null));
        makeTestInstance().execute(p, null, null);
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
     *  The empty stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        makeTestInstance().execute(p, null, null);
    }

    /**
     *  A short stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack() throws Exception {

        p.push(new TString("e", null));
        makeTestInstance().execute(p, null, null);
    }

    /**
     *  The format "e" is unknown.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibIllegalValueException.class)
    public void testTypeError() throws Exception {

        p.push(new TString("e", null));
        p.push(new TString("", null));
        makeTestInstance().execute(p, null, null);
    }

    /**
     *  A group protects from translating to lower case.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testx2l() throws Exception {

        testChangeCaseL("ABC: {DE}F", "abc: {DE}f");
    }

    /**
     *  A group protects from translating to mixed case.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testx2t() throws Exception {

        testChangeCaseT("AB{C}: DEF", "Ab{C}: Def");
    }

    /**
     *  A group protects from translating to upper case.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testx2u() throws Exception {

        testChangeCaseU("abc: d{e}f", "ABC: D{e}F");
    }

}
