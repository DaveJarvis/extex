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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for {@link TString}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TStringTest {

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
     * <testcase> A TString can be executed and returns itself. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute() throws Exception {

        TString t = new TString("987", null);
        t.execute(p, null, null);
        assertEquals("987", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> getValue() of a null value returns the empty string.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGetValue1() throws Exception {

        TString t = new TString(null, null);
        assertEquals("", t.getValue());
    }

    /**
     * <testcase> A TString can be stored in a Hash and retrieved with another
     * instance. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHash1() throws Exception {

        TString t = new TString("abc", null);
        Map<Token, String> map = new HashMap<Token, String>();
        map.put(t, "value");
        String s = map.get(new TString("abc", null));
        assertNotNull(s);
        assertEquals("value", s);
    }

    /**
     * <testcase> isNull() can detect a null value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIsNull1() throws Exception {

        assertTrue(new TString(null, null).isNull());
    }

    /**
     * <testcase> isNull() can detect a null value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIsNull2() throws Exception {

        assertFalse(new TString("", null).isNull());
    }

    /**
     * <testcase> Test the visiting. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit() throws Exception {

        TString t = new TString("x-1", null);
        RecordingTokenVisitor tv = new RecordingTokenVisitor();
        t.visit(tv);
        assertEquals(t, tv.getVisited());
    }

}
