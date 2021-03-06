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

package org.extex.exbib.core.bst.token.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.exception.ExBibEmptyFunctionNameException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for {@link TQLiteral}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TQLiteralTest {

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
     *  A QLiteral can nor have an {@code null} name.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibEmptyFunctionNameException.class)
    public void testError1() throws Exception {

        new TQLiteral(null, null);
    }

    /**
     *  A QLiteral can nor have an empty name.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibEmptyFunctionNameException.class)
    public void testError2() throws Exception {

        new TQLiteral("", null);
    }

    /**
     *  A QLiteral executes to itself.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute() throws Exception {

        TQLiteral t = new TQLiteral("aaa", null);
        t.execute(p, null, null);

        Token x = p.pop(null);
        assertNull(p.popUnchecked());
        assertTrue(x instanceof TLiteral);
        assertEquals("aaa", x.getValue());
    }

    /**
     *  A QLiteral executes to itself.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand() throws Exception {

        TQLiteral t = new TQLiteral("aaa", null);
        String s = t.expand(p);

        assertNull(p.popUnchecked());
        assertEquals("aaa", s);
    }

    /**
     *  toString() works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString() throws Exception {

        assertEquals("'abc", new TQLiteral("abc", null).toString());
    }

    /**
     *  The token visitor invokes the correct method.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit() throws Exception {

        TQLiteral t = new TQLiteral("acd", null);
        RecordingTokenVisitor tv = new RecordingTokenVisitor();
        t.visit(tv);
        assertEquals(t, tv.getVisited());
    }

}
