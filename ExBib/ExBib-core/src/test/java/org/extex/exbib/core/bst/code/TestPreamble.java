/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
 * This file is part of ExBib a BibTeX compatible database.
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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.code.impl.Preamble;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.VBlock;
import org.extex.exbib.core.db.VNumber;
import org.extex.exbib.core.db.VString;
import org.extex.exbib.core.db.Value;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>preamble$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class TestPreamble extends TestCase {

    /**
     * The main program just uses the text interface of JUnit.
     * 
     * @param args command line parameters are ignored
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * Generate a new test suite
     * 
     * @return the new test suite
     */
    public static Test suite() {

        return new TestSuite(TestPreamble.class);
    }

    /**
     * The field <tt>db</tt> contains the database.
     */
    private DB db = null;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestPreamble(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        // db = new DBImpl();
        // p = new BstProcessor099c(db, new NullWriter(null),
        // new NullWriter(null));
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
        db = null;
    }

    /**
     * <testcase> preamble$ can extract a block preamble. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testBlock() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);
        db.storePreamble(new Value(new VBlock("123")));
        new Preamble("preamble$").execute(p, null, null);
        assertEquals("123", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> initially the preamble is empty. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);
        new Preamble("preamble$").execute(p, null, null);
        assertEquals("", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> preamble$ can extract a numeric preamble. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testNumber() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);
        db.storePreamble(new Value(new VNumber("123")));
        new Preamble("preamble$").execute(p, null, null);
        assertEquals("123", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> preamble$ can extract a string preamble. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testString() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);
        db.storePreamble(new Value(new VString("abc")));
        new Preamble("preamble$").execute(p, null, null);
        assertEquals("abc", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> preamble$ can extract a combined preamble. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testValue() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);

        Value v = new Value();
        v.add(new VBlock("1234"));
        v.add(new VString(" # "));
        v.add(new VNumber("123"));
        db.storePreamble(v);
        new Preamble("preamble$").execute(p, null, null);
        assertEquals("1234 # 123", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

}
