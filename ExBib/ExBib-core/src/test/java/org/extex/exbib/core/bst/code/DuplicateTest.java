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
import org.extex.exbib.core.bst.code.impl.Duplicate;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>duplicate$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DuplicateTest {

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
     * <testcase> A integer can be duplicated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate1() throws Exception {

        p.push(new TString("abc", null));
        new Duplicate("duplicate$").execute(p, null, null);
        assertEquals("abc", p.popString(null).getValue());
        assertEquals("abc", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> A string can be duplicated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate2() throws Exception {

        p.push(new TInteger(123, null));
        new Duplicate("duplicate$").execute(p, null, null);
        assertEquals("123", p.popInteger(null).getValue());
        assertEquals("123", p.popInteger(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> A char can be duplicated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate3() throws Exception {

        p.push(new TChar('a', null));
        new Duplicate("duplicate$").execute(p, null, null);
        assertEquals("a", p.pop(null).getValue());
        assertEquals("a", p.pop(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> A literal can be duplicated. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate4() throws Exception {

        p.push(new TLiteral("a", null));
        new Duplicate("duplicate$").execute(p, null, null);
        assertEquals("a", p.pop(null).getValue());
        assertEquals("a", p.pop(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> An empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Duplicate("duplicate$").execute(p, null, null);
    }

}
