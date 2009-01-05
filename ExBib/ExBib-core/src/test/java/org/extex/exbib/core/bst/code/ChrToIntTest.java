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
import org.extex.exbib.core.bst.code.impl.ChrToInt;
import org.extex.exbib.core.bst.code.impl.IntToChr;
import org.extex.exbib.core.bst.exception.ExBibIllegalValueException;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>chr.to.int$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ChrToIntTest {

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
     * Run a test.
     * 
     * @param c the character code
     * 
     * @throws Exception in case of an error
     */
    private void testChrToInt(int c) throws Exception {

        p.push(new TString(String.valueOf((char) c), null));
        new ChrToInt("chr.to.int$").execute(p, null, null);
        assertEquals(c, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The character 0 is mapped correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChrToInt0() throws Exception {

        testChrToInt(0);
    }

    /**
     * <testcase> The character 123 is mapped correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChrToInt123() throws Exception {

        testChrToInt(123);
    }

    /**
     * <testcase> The character 32 is mapped correctly. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChrToInt32() throws Exception {

        testChrToInt(32);
    }

    /**
     * <testcase> The first argument can not be a String. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibIllegalValueException.class)
    public void testChrToIntErr1() throws Exception {

        p.push(new TString("..", null));
        new ChrToInt("chr.to.int$").execute(p, null, null);
    }

    /**
     * <testcase> The first argument can not be an empty String. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibIllegalValueException.class)
    public void testChrToIntErr2() throws Exception {

        p.push(new TString("", null));
        new ChrToInt("chr.to.int$").execute(p, null, null);
    }

    /**
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new IntToChr("chr.to.int$").execute(p, null, null);
    }

}
