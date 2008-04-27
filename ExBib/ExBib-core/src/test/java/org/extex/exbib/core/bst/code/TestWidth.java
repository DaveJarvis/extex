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

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.ProcessorBibtex099c;
import org.extex.exbib.core.bst.code.impl.Width;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * Test suite for <tt>width$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestWidth extends TestCase {

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

        return new TestSuite(TestWidth.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * The field <tt>err</tt> contains the error output.
     */
    private StringBuffer err = new StringBuffer();

    /**
     * The field <tt>out</tt> contains the standard output.
     */
    private StringBuffer out = new StringBuffer();

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestWidth(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new ProcessorBibtex099c(new DBImpl(), new NullWriter(null), null);
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
     * Run a test.
     * 
     * @param s the string to be tested
     * @param w the expected width
     * 
     * @throws Exception in case of an error
     */
    private void testWidth(String s, int w) throws Exception {

        p.push(new TString(s));
        new Width("width$").execute(p, null, null);
        assertEquals("", err.toString());
        assertEquals("", out.toString());
        assertEquals(w, p.popInteger(null).getInt());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> Test "ae". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0110() throws Exception {

        testWidth("ae", 944);
    }

    /**
     * <testcase> Test "\AE". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0111() throws Exception {

        testWidth("\\AE", 1931);
    }

    /**
     * <testcase> Test "oe". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0112() throws Exception {

        testWidth("oe", 944);
    }

    /**
     * <testcase> Test "\OE". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0113() throws Exception {

        testWidth("\\OE", 1959);
    }

    /**
     * <testcase> Test "\singleletter". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0114() throws Exception {

        testWidth("\\singleletter", 5286);
    }

    /**
     * <testcase> Test ".\singleletter.". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0115() throws Exception {

        testWidth(".\\singleletter.", 5842);
    }

    /**
     * <testcase> Test "OX{\\singleletter{stoc}}83b". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0116() throws Exception {

        testWidth("OX{\\singleletter{stoc}}83b", 4811);
    }

    /**
     * <testcase> Test "OX{\\singleletter{stoc}}83b". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0117() throws Exception {

        testWidth("OX{\\singleletter  {stoc}}83b", 4811);
    }

    /**
     * <testcase> Test "OXstoc83b". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0118() throws Exception {

        testWidth("OXstoc83b", 4811);
    }

    /**
     * <testcase> Test "\AE". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth0119() throws Exception {

        testWidth("{\\AE}", 903);
    }

    /**
     * <testcase> Test "abc". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth012() throws Exception {

        testWidth("abc", 1500);
    }

    /**
     * <testcase> Test lowercase letters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth013() throws Exception {

        testWidth("abcdefghijklmnopqrstuvwxyz", 12706);
    }

    /**
     * <testcase> Test ".,-=)(/&%$§!". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth014() throws Exception {

        testWidth(".,-=)(/&%$§!", 5334);
    }

    /**
     * <testcase> Test "{}". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth015() throws Exception {

        testWidth("{}", 1000);
    }

    /**
     * <testcase> Test "AA". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth016() throws Exception {

        testWidth("AA", 1500);
    }

    /**
     * <testcase> Test "\AA". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth017() throws Exception {

        testWidth("\\AA", 2000);
    }

    /**
     * <testcase> Test "ss". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth018() throws Exception {

        testWidth("ss", 788);
    }

    /**
     * <testcase> Test "\ss". </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWidth019() throws Exception {

        testWidth("\\ss", 1288);
    }

}
