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

package org.extex.exbib.core.token.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link TChar}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TestTChar {

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
     * <testcase> A character evaluates to itself. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute() throws Exception {

        TChar t = new TChar('a', null);
        t.execute(p, null, null);
        assertEquals("a", p.pop(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> toString() works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToString() throws Exception {

        assertEquals("a", new TChar('a', null).toString());
    }

    /**
     * <testcase> Visiting a char calls the correct method. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit() throws Exception {

        TChar t = new TChar('.', null);
        RecordingTokenVisitor tv = new RecordingTokenVisitor();
        t.visit(tv);
        assertEquals(t, tv.getVisited());
    }

}
