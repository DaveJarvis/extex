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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.Code;
import org.extex.exbib.core.bst.code.impl.CallType;
import org.extex.exbib.core.db.DB;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.exbib.core.io.NullWriter;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * Test suite for <tt>call.type$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestCallType extends TestCase implements Code {

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

        return new TestSuite(TestCallType.class);
    }

    /**
     * The field <tt>executed</tt> contains the executed flag.
     */
    private boolean executed = false;

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
    public TestCallType(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#configure(
     *      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        //
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.Code#execute(
     *      org.extex.exbib.core.Processor, org.extex.exbib.core.db.Entry,
     *      org.extex.exbib.core.io.Locator)
     */
    public void execute(Processor processor, Entry entry, Locator locator)
            throws ExBibException {

        executed = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        db = new DBImpl();
        p = new BstProcessor099c(db, new NullWriter(null), null);
        p.addFunction("book", this, null);
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
     * <testcase> call.type$ invokes an appropriate function. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        Entry e = new Entry(null);
        e.setType("book");
        executed = false;
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(executed);
    }

    /**
     * <testcase> call.type$ invokes an appropriate function ignoring case.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        Entry e = new Entry(null);
        e.setType("Book");
        executed = false;
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(executed);
    }

    /**
     * <testcase> call.type$ invokes an appropriate function ignoring case.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        Entry e = new Entry(null);
        e.setType("BOOK");
        executed = false;
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(executed);
    }

    /**
     * <testcase> call.type$ invokes an appropriate function. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test3() throws Exception {

        Entry e = new Entry(null);
        e.setType("article");
        executed = false;
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(!executed);

        p.addFunction("article", this, null);
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(executed);
    }

    /**
     * <testcase> call.type$ invokes an appropriate function. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test4() throws Exception {

        Entry e = new Entry(null);
        e.setType("misc");
        executed = false;
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(!executed);

        p.addFunction("default.type", this, null);
        new CallType("call.type$").execute(p, e, null);
        assertNull(p.popUnchecked());
        assertTrue(executed);
    }

}
