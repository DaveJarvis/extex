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
import org.extex.exbib.core.bst.ProcessorBibtex099c;
import org.extex.exbib.core.bst.code.impl.Write;
import org.extex.exbib.core.bst.exception.ExBibStackEmptyException;
import org.extex.exbib.core.bst.node.impl.TBlock;
import org.extex.exbib.core.bst.node.impl.TChar;
import org.extex.exbib.core.bst.node.impl.TInteger;
import org.extex.exbib.core.bst.node.impl.TQLiteral;
import org.extex.exbib.core.bst.node.impl.TString;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.StringBufferWriter;

/**
 * Test suite for <tt>write$</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.3 $
 */
public class TestWrite extends TestCase {

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

        return new TestSuite(TestWrite.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * The field <tt>out</tt> contains the output buffer.
     */
    private StringBuffer out = null;

    /**
     * Create a new object.
     * 
     * @param name the name
     */
    public TestWrite(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        out = new StringBuffer();
        p = new ProcessorBibtex099c(new DBImpl(), //
            new StringBufferWriter(out), null);
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
     * <testcase> write$ needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testEmptyStack() throws Exception {

        try {
            new Write("write$").execute(p, null, null);
            assertTrue(false);
        } catch (ExBibStackEmptyException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> write$ can write a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWriteBlock1() throws Exception {

        TBlock block = new TBlock(null);
        block.add(new TString("abc"));
        p.push(block);
        new Write("write$").execute(p, null, null);
        assertEquals("abc", out.toString());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> write$ can write a character. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWriteChar1() throws Exception {

        p.push(new TChar("a", null));
        new Write("write$").execute(p, null, null);
        assertEquals("a", out.toString());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> write$ can write a number. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWriteNumber1() throws Exception {

        p.push(new TInteger(123));
        new Write("write$").execute(p, null, null);
        assertEquals("123", out.toString());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> write$ can write a QLiteral. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWriteQLiteral1() throws Exception {

        p.push(new TQLiteral("abc", null));
        new Write("write$").execute(p, null, null);
        assertEquals("abc", out.toString());
        assertNull(p.popUnchecked());
    }

    /**
     * <testcase> write$ can write a string. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testWriteString1() throws Exception {

        p.push(new TString("abc"));
        new Write("write$").execute(p, null, null);
        assertEquals("abc", out.toString());
        assertNull(p.popUnchecked());
    }

}
