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
import org.extex.exbib.core.bst.exception.ExBibMissingEntryException;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for {@link TField}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TFieldTest {

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
     *  An exception is raised when used outside an entry context.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingEntryException.class)
    public void testExecute1() throws Exception {

        TField t = new TField("aaa", null);
        t.execute(p, null, null);
    }

    /**
     *  A Literal executes to an exception if the field is undefined.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute2() throws Exception {

        TField t = new TField("abc", null);
        Entry entry = new Entry(null);
        t.execute(p, entry, null);

        Token x = p.pop(null);
        assertNull(p.popUnchecked());
        assertTrue(x instanceof TString);
        assertEquals("", x.getValue());
    }

    /**
     *  A Field for an undefined macro expands to the identical
     * string??? 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand1() throws Exception {

        TField t = new TField("aaa", null);
        String s = t.expand(p);

        assertNull(p.popUnchecked());
        assertEquals("aaa", s);
    }

    /**
     *  A Field for an defined macro expands to the name???
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand2() throws Exception {

        p.getDB().storeString("def", new Value(new VString("D E F")));
        TField t = new TField("def", null);
        String s = t.expand(p);

        assertNull(p.popUnchecked());
        assertEquals("def", s);
    }

    /**
     *  toString() works. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString() throws Exception {

        assertEquals("abc", new TField("abc", null).toString());
    }

    /**
     *  The token visitor invokes the correct method. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit() throws Exception {

        TField t = new TField("acd", null);
        RecordingTokenVisitor tv = new RecordingTokenVisitor();
        t.visit(tv);
        assertEquals(t, tv.getVisited());
    }

}
