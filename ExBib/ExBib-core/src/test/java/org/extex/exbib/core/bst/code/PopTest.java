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

import static org.junit.Assert.assertNull;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.impl.Pop;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for {@code pop$}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class PopTest {

    /**
     * The field {@code p} contains the processor.
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
     *  An empty stack leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Pop("pop$").execute(p, null, null);
    }

    /**
     *  An integer can be popped.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPopInteger() throws Exception {

        testToken(new TInteger(123, null));
    }

    /**
     *  A literal can be popped.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPopLiteral() throws Exception {

        testToken(new TLiteral("abc", null));
    }

    /**
     *  A string can be popped.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPopString() throws Exception {

        testToken(new TString("123", null));
    }

    /**
     * Run a test case.
     * 
     * @param t the token
     * 
     * @throws Exception in case of an error
     */
    private void testToken(Token t) throws Exception {

        p.push(t);
        new Pop("pop$").execute(p, null, null);
        assertNull(p.popUnchecked());
    }

}
