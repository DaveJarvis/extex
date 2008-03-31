/*
 * Copyright (C) 2003-2008 Gerd Neugebauer
 * This file is part of ExBib a BibTeX compatible database.
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
import org.extex.exbib.core.bst.code.impl.TextPrefix;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>text.prefix$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestTextPrefix extends TestCase {

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

        return new TestSuite(TestTextPrefix.class);
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
    public TestTextPrefix(String name) {

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
     * <testcase> text.prefix$ complains about an empty stack. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new TextPrefix("text.prefix$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> text.prefix$ complains about a stack containing only one
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testShortStack1() throws Exception {

        try {
            p.push(new TInteger(0));
            new TextPrefix("text.prefix$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> "123" #2 text.prefix$ --> "12". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix1() throws Exception {

        testTextPrefixing("123", 2, "12");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix11() throws Exception {

        testTextPrefixing("1{23}4567", 1, "1");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix12() throws Exception {

        testTextPrefixing("1{23}4567", 2, "1{2}");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix13() throws Exception {

        testTextPrefixing("1{23}4567", 3, "1{23}");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix14() throws Exception {

        testTextPrefixing("1{23}4567", 4, "1{23}4");
    }

    /**
     * <testcase> "123" #5 text.prefix$ --> "123". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix2() throws Exception {

        testTextPrefixing("123", 5, "123");
    }

    /**
     * <testcase> "+*@" #2 text.prefix$ --> "+*". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix21() throws Exception {

        testTextPrefixing("+*@", 2, "+*");
    }

    /**
     * <testcase> "+*@" #5 text.prefix$ --> "+*@". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix22() throws Exception {

        testTextPrefixing("+*@", 5, "+*@");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix31() throws Exception {

        testTextPrefixing("+{*@}abc7", 1, "+");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix32() throws Exception {

        testTextPrefixing("+{*@}abc7", 2, "+{*}");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix33() throws Exception {

        testTextPrefixing("+{*@}abc7", 3, "+{*@}");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix34() throws Exception {

        testTextPrefixing("+{*@}abc7", 4, "+{*@}a");
    }

    /**
     * <testcase> text.prefix$ treats braces as units. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefix44() throws Exception {

        testTextPrefixing("Ab{\\def{12}}xyz", 4, "Ab{\\def{12}}x");
    }

    /**
     * <testcase> text.prefix$ can cope with the empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefixEmpty() throws Exception {

        testTextPrefixing("", 0, "");
    }

    /**
     * <testcase> text.prefix$ can cope with the empty string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testTextPrefixEmpty2() throws Exception {

        testTextPrefixing("", 1, "");
    }

    /**
     * Run a test case.
     * 
     * @param s the string
     * @param len the length
     * @param res the result
     * 
     * @throws Exception in case of an error
     */
    private void testTextPrefixing(String s, int len, String res)
            throws Exception {

        p.push(new TString(s));
        p.push(new TInteger(len));
        new TextPrefix("text.prefix$").execute(p, null, null);
        assertEquals(res, p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }
}
