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
import org.extex.exbib.core.bst.code.impl.Missing;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibMissingStringException;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for <tt>missing$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MissingTest {

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>entry</tt> contains the entry.
     */
    private Entry entry;

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

        db = new DBImpl();
        entry = db.makeEntry("book", "abc", null);
        entry.set("author", new Value());
        p = new BstInterpreter099c(db, new NullWriter(), null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
        db = null;
    }

    /**
     * <testcase> An existing field is reported as not missing. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        p.push(new TString("title", null));
        new Missing("missing$").execute(p, entry, null);
        assertEquals("0", p.popInteger(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> An non-existing field is reported as missing. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        p.push(new TString(null, null));
        new Missing("missing$").execute(p, entry, null);
        assertEquals("1", p.popInteger(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> The empty stack leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibStackEmptyException.class)
    public void testEmptyStack() throws Exception {

        new Missing("missing$").execute(p, entry, null);
    }

    /**
     * <testcase> The first argument needs to be a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = ExBibMissingStringException.class)
    public void testInteger() throws Exception {

        p.push(new TInteger(123, null));
        new Missing("missing$").execute(p, entry, null);
    }

}
