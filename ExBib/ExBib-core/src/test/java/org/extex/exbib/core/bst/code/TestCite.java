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

package org.extex.exbib.core.bst.code;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.code.impl.Cite;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>cite$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestCite extends TestCase {

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

        return new TestSuite(TestCite.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestCite(String name) {

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
        p.addCitation("aBc,dEF".split(","));
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown() {

        p = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test1() throws Exception {

        testCite("abc", "aBc");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test2() throws Exception {

        testCite("ABC", "aBc");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test3() throws Exception {

        testCite("abC", "aBc");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test4() throws Exception {

        testCite("def", "dEF");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test5() throws Exception {

        testCite("DEF", "dEF");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test6() throws Exception {

        testCite("dEf", "dEF");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test7() throws Exception {

        testCite("xyz", "xyz");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test8() throws Exception {

        testCite("XYZ", "XYZ");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void test9() throws Exception {

        testCite("xYz", "xYz");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param s
     * @param result
     * @throws Exception
     */
    private void testCite(String s, String result) throws Exception {

        Entry e = new Entry(null);
        e.setKey(s);
        new Cite("cite$").execute(p, e, null);
        assertEquals(result, p.popUnchecked().getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testNoEntry() throws Exception {

        try {
            new Cite("cite$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibException e) {
            assertTrue(true);
        }
    }
}
