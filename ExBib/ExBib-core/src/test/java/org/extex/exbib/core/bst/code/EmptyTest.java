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
import static org.junit.Assert.assertNull;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.Empty;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>empty$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class EmptyTest {

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>entry</tt> contains an entry.
     */
    private Entry entry;

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
        entry = db.makeEntry("book", "abc", null);
        entry.set("author", new Value());
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
     * Run one test for the function empty$.
     * 
     * @param val the value of the argument
     * @param res the expected result
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty(String val, int res) throws Exception {

        p.push(new TString(val, null));
        new Empty("empty$").execute(p, entry, null);
        assertEquals(res, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The empty stack leads to an error. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Empty("empty$").execute(p, entry, null);
    }

    /**
     * <testcase> A non-string (Integer) argument leads to an error. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingStringException.class)
    public void testInteger() throws Exception {

        p.push(new TInteger(123, null));
        new Empty("empty$").execute(p, entry, null);
    }

    /**
     * <testcase> A longer string ist not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNo0() throws Exception {

        testEmpty("title", 0);
    }

    /**
     * <testcase> A one-letter string is not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNo1() throws Exception {

        testEmpty("a", 0);
    }

    /**
     * <testcase> A one-character string is not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNo2() throws Exception {

        testEmpty(".", 0);
    }

    /**
     * <testcase> A one-digit string is not empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNo3() throws Exception {

        testEmpty("1", 0);
    }

    /**
     * <testcase> A <code>null</code> is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes1() throws Exception {

        testEmpty(null, 1);
    }

    /**
     * <testcase> The empty string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes2() throws Exception {

        testEmpty("", 1);
    }

    /**
     * <testcase> A space-only string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes3() throws Exception {

        testEmpty(" ", 1);
    }

    /**
     * <testcase> A tab-only string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes4() throws Exception {

        testEmpty("\t", 1);
    }

    /**
     * <testcase> A multi-space string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes5() throws Exception {

        testEmpty("  ", 1);
    }

    /**
     * <testcase> A multi-whitespace string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes6() throws Exception {

        testEmpty(" \n", 1);
    }

    /**
     * <testcase> A multi-whitespace string is empty. <testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testYes7() throws Exception {

        testEmpty(" \t", 1);
    }

}
