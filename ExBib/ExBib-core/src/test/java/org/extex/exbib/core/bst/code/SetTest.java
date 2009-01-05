/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.Set;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.ValueItem;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibFunctionUndefinedException;
import org.extex.exbib.core.exceptions.ExBibMissingLiteralException;
import org.extex.exbib.core.exceptions.ExBibMissingNumberException;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>:=</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class SetTest {

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db;

    /**
     * The field <tt>entry</tt> contains the entry.
     */
    private Entry entry;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Run a test case.
     * 
     * @param name the name
     * @param t the value
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String name, Token t) throws Exception {

        p.push(t);
        p.push(new TLiteral(name, null));
        new Set(":=").execute(p, null, null);
        assertNull(p.popUnchecked());
    }

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        db = new DBImpl();
        entry = db.makeEntry("book", "abc", null);
        entry.set("author", new Value());
        p = new BstInterpreter099c(db, new NullWriter(), null);
        p.addFunction("abc", TokenFactory.T_ONE, null);
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

        new Set(":=").execute(p, null, null);
    }

    /**
     * <testcase> Test abc := 123. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet1() throws Exception {

        runTest("abc", new TInteger(123, null));
    }

    /**
     * <testcase> Test abc := "123". </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet2() throws Exception {

        runTest("abc", new TString("123", null));
    }

    /**
     * <testcase> Test setting a string entry. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet3() throws Exception {

        List<String> strings = new ArrayList<String>();
        strings.add("aaa");
        p.setEntryStrings(strings, null);

        p.push(new TString("123", null));
        p.push(new TLocalString("aaa", null));
        new Set(":=").execute(p, entry, null);
        assertNull(p.popUnchecked());
        ValueItem value = entry.getLocal("aaa");
        assertNotNull(value);
        assertEquals("\"123\"", value.toString());
    }

    /**
     * <testcase> Test setting a string entry with an integer. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet4() throws Exception {

        List<String> strings = new ArrayList<String>();
        strings.add("aaa");
        p.setEntryStrings(strings, null);

        p.push(new TInteger(123, null));
        p.push(new TLocalString("aaa", null));
        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingStringException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test setting an integer entry. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet5() throws Exception {

        List<String> integers = new ArrayList<String>();
        integers.add("aaa");
        p.setEntryIntegers(integers, null);

        p.push(new TInteger(123, null));
        p.push(new TLocalString("aaa", null));
        new Set(":=").execute(p, entry, null);
        assertNull(p.popUnchecked());
        ValueItem value = entry.getLocal("aaa");
        assertNotNull(value);
        assertEquals("123", value.toString());
    }

    /**
     * <testcase> Test setting an integer entry with a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet6() throws Exception {

        List<String> integers = new ArrayList<String>();
        integers.add("aaa");
        p.setEntryIntegers(integers, null);

        p.push(new TString("123", null));
        p.push(new TLocalString("aaa", null));
        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingNumberException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> Test setting a string entry. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet7() throws Exception {

        List<String> entries = new ArrayList<String>();
        entries.add("aaa");
        p.setEntries(entries, null);

        p.push(new TString("123", null));
        p.push(new TLocalString("aaa", null));
        new Set(":=").execute(p, entry, null);
        assertNull(p.popUnchecked());
        Value value = entry.get("aaa");
        assertNotNull(value);
        assertEquals("\"123\"", value.toString());
    }

    /**
     * <testcase> Test setting a string entry with an integer. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet8() throws Exception {

        List<String> entries = new ArrayList<String>();
        entries.add("aaa");
        p.setEntries(entries, null);

        p.push(new TInteger(123, null));
        p.push(new TLocalString("aaa", null));
        try {
            new Set(":=").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibMissingStringException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> A short stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testShortStack() throws Exception {

        p.push(new TLiteral("a", null));
        new Set(":=").execute(p, null, null);
    }

    /**
     * <testcase> Test 2 := "a" leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingLiteralException.class)
    public void testTypeError1() throws Exception {

        p.push(new TLiteral("a", null));
        p.push(new TInteger(2, null));
        new Set(":=").execute(p, null, null);
    }

    /**
     * <testcase> Test "a" := "a" leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingLiteralException.class)
    public void testTypeError2() throws Exception {

        p.push(new TString("a", null));
        p.push(new TString("a", null));
        new Set(":=").execute(p, null, null);
    }

    /**
     * <testcase> Test 'undef := 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibFunctionUndefinedException.class)
    public void testUndef() throws Exception {

        p.push(new TInteger(2, null));
        p.push(new TLiteral("undef", null));
        new Set(":=").execute(p, null, null);
    }

}
