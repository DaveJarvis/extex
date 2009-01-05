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
import org.extex.exbib.core.bst.code.impl.Cite;
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>cite$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CiteTest {

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * Run a test.
     * 
     * @param key the key string
     * @param result the expected result
     * 
     * @throws Exception in case of an error
     */
    private void runTest(String key, String result) throws Exception {

        Entry e = new Entry(null);
        e.setKey(key);
        new Cite("cite$").execute(p, e, null);
        assertEquals(result, p.popUnchecked().getValue());
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
        p.addCitation("aBc,dEF".split(","));
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     * <testcase> The casing of the key does not matter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        runTest("abc", "aBc");
    }

    /**
     * <testcase> The casing of the key does not matter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        runTest("ABC", "aBc");
    }

    /**
     * <testcase> The casing of the key does not matter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        runTest("abC", "aBc");
    }

    /**
     * <testcase> The casing of the key does not matter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        runTest("def", "dEF");
    }

    /**
     * <testcase> The casing of the key does not matter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        runTest("DEF", "dEF");
    }

    /**
     * <testcase> The casing of the key does not matter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6() throws Exception {

        runTest("dEf", "dEF");
    }

    /**
     * <testcase> An unknown key remains unchanged. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test7() throws Exception {

        runTest("xyz", "xyz");
    }

    /**
     * <testcase> An unknown key remains unchanged. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test8() throws Exception {

        runTest("XYZ", "XYZ");
    }

    /**
     * <testcase> An unknown key remains unchanged. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test9() throws Exception {

        runTest("xYz", "xYz");
    }

    /**
     * <testcase> A missing entry leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingEntryException.class)
    public void testNoEntry() throws Exception {

        new Cite("cite$").execute(p, null, null);
    }

}
