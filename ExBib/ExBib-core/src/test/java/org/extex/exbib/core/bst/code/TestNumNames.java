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
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.code.impl.NumNames;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>num.names$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestNumNames {

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        p = new BstProcessor099c(new DBImpl(), new NullWriter(null), null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     * Run a single test.
     * 
     * @param in the input string
     * @param len the length
     * 
     * @throws Exception in case of an error
     */
    private void test(String in, int len) throws Exception {

        p.push(new TString(in, null));
        new NumNames("num.names$").execute(p, null, null);
        assertEquals(len, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> A single name is correctly recognized.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        test("Gerd Neugebauer", 1);
    }

    /**
     * <testcase> A company should be enclosed in braces. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCompany() throws Exception {

        test("{Dun and Bradstreet}", 1);
    }

    /**
     * <testcase> The empty name has 0 names in it. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty() throws Exception {

        test("", 0);
    }

    /**
     * <testcase> num.names$ needs an argument on the stack. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new NumNames("num.names$").execute(p, null, null);
    }

    /**
     * <testcase> A trailing "and" is ignored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEnd() throws Exception {

        test("Gerd Neugebauer and", 1);
    }

    /**
     * <testcase> A trailing "and " is ignored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEnd2() throws Exception {

        test("Gerd Neugebauer and ", 1);
    }

    /**
     * <testcase> An integer argument leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingStringException.class)
    public void testInteger() throws Exception {

        p.push(new TInteger(9876, null));
        new NumNames("num.names$").execute(p, null, null);
    }

    /**
     * <testcase> "\tand " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOthers1() throws Exception {

        test("Gerd Neugebauer\tand others", 2);
    }

    /**
     * <testcase> " and " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOthers2() throws Exception {

        test("Gerd Neugebauer and others", 2);
    }

    /**
     * <testcase> "\tand\n" is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOthers3() throws Exception {

        test("Gerd Neugebauer\tand\nothers", 2);
    }

    /**
     * <testcase> " and " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTwo1() throws Exception {

        test("Stan Laurel and Oliver Hardy", 2);
    }

    /**
     * <testcase> "\tand " is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTwo2() throws Exception {

        test("Stan Laurel\tand Oliver Hardy", 2);
    }

    /**
     * <testcase> " and\t" is recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTwo3() throws Exception {

        test("Stan Laurel and\tOliver Hardy", 2);
    }

    /**
     * <testcase> "and" in a name is not recognized as separator. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testX() throws Exception {

        test("Arhur Landry", 1);
    }

}
