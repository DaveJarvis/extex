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
import static org.junit.Assert.assertTrue;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.AddPeriod;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>add.period$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestAddPeriod {

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
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new AddPeriod("add.period$").execute(p, null, null);
    }

    /**
     * <testcase> An integer gets a period added. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testInteger() throws Exception {

        p.push(new TInteger(123, null));
        new AddPeriod("add.period$").execute(p, null, null);
        assertEquals("123.", p.popString(null).getValue());
    }

    /**
     * Run a test case where no period is added.
     * 
     * @param in the input (and output) string
     * 
     * @throws Exception in case of an error
     */
    private void testNoAdd(String in) throws Exception {

        p.push(new TString(in, null));
        new AddPeriod("add.period$").execute(p, null, null);
        assertEquals(in, p.popString(null).getValue());
    }

    /**
     * <testcase> No period is added to the empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddEmpty() throws Exception {

        testNoAdd("");
    }

    /**
     * <testcase> No period is added if the last non-brace character is an
     * exclamation mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddExclamationMark1() throws Exception {

        testNoAdd("abc!");
    }

    /**
     * <testcase> No period is added if the last non-brace character is an
     * exclamation mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddExclamationMark2() throws Exception {

        testNoAdd("abc!}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is an
     * exclamation mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddExclamationMark3() throws Exception {

        testNoAdd("abc!}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is an
     * exclamation mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddExclamationMark4() throws Exception {

        testNoAdd("abc!}}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * period. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddPeriod1() throws Exception {

        testNoAdd("abc.");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * period. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddPeriod2() throws Exception {

        testNoAdd("abc.}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * period. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddPeriod3() throws Exception {

        testNoAdd("abc.}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * period. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddPeriod4() throws Exception {

        testNoAdd("abc.}}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * question mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddQuestionMark1() throws Exception {

        testNoAdd("abc?");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * question mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddQuestionMark2() throws Exception {

        testNoAdd("abc?}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * question mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddQuestionMark3() throws Exception {

        testNoAdd("abc?}}");
    }

    /**
     * <testcase> No period is added if the last non-brace character is a
     * question mark. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoAddQuestionMark4() throws Exception {

        testNoAdd("abc?}}}");
    }

    /**
     * <testcase> A period is added if the input consists of text only.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testString() throws Exception {

        p.push(new TString("abc", null));
        new AddPeriod("add.period$").execute(p, null, null);
        assertTrue(p.popString(null).getValue().equals("abc."));
    }

}
