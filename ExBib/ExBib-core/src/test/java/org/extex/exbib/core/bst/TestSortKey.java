/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.core.bst;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.bst.node.impl.TFieldString;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestSortKey extends TestCase {

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

        return new TestSuite(TestSortKey.class);
    }

    /**
     * The field <tt>e</tt> contains the ...
     */
    private Entry e;

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestSortKey(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new Processor099Impl(new DBImpl(), new NullWriter(null), null);
        p.addFunction("abc", new TInteger(1), null);
        e = p.getDB().makeEntry("book", "k1", null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
        e = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testNoSortKey() throws Exception {

        new TFieldString("sort.key$").execute(p, e, null);
        assertEquals("", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSortKey1() throws Exception {

        e.setSortKey("xyz");
        assertEquals("xyz", e.getSortKey());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testSortKey11() throws Exception {

        e.setSortKey("xyz");
        new TFieldString("sort.key$").execute(p, e, null);
        assertEquals("xyz", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

}
