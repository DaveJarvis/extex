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

package org.extex.exbib.core.bst.node.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.bst.Processor;
import org.extex.exbib.core.bst.Processor099Impl;
import org.extex.exbib.core.bst.node.TokenVisitor;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TestTString extends TestCase implements TokenVisitor {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return ...
     */
    public static Test suite() {

        return new TestSuite(TestTString.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * The field <tt>visit</tt> contains the ...
     */
    private boolean visit = false;

    /**
     * Creates a new object.
     * 
     * @param name
     */
    public TestTString(String name) {

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
    public void testExecute() throws Exception {

        TString t = new TString("987");
        t.execute(p, null, null);
        assertEquals("987", p.popString(null).getValue());
        assertNull(p.popUnchecked());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testGetNull() throws Exception {

        TString t = new TString(null);
        assertEquals("", t.getValue());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testIsNull1() throws Exception {

        assertTrue(new TString(null).isNull());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testIsNull2() throws Exception {

        assertFalse(new TString("").isNull());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception
     */
    public void testVisit() throws Exception {

        TString t = new TString("x-1");
        t.visit(this);
        assertTrue(visit);
        assertEquals("x-1", t.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitBlock(org.extex.exbib.core.bst.node.impl.TBlock)
     */
    public void visitBlock(TBlock block) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitChar(org.extex.exbib.core.bst.node.impl.TChar)
     */
    public void visitChar(TChar c) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitField(org.extex.exbib.core.bst.node.impl.TField)
     */
    public void visitField(TField field) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitFieldInteger(org.extex.exbib.core.bst.node.impl.TFieldInteger)
     */
    public void visitFieldInteger(TFieldInteger integer) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitFieldString(org.extex.exbib.core.bst.node.impl.TFieldString)
     */
    public void visitFieldString(TFieldString string) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitInteger(org.extex.exbib.core.bst.node.impl.TInteger)
     */
    public void visitInteger(TInteger integer) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitLiteral(org.extex.exbib.core.bst.node.impl.TLiteral)
     */
    public void visitLiteral(TLiteral literal) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitQLiteral(org.extex.exbib.core.bst.node.impl.TQLiteral)
     */
    public void visitQLiteral(TQLiteral qliteral) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitString(org.extex.exbib.core.bst.node.impl.TString)
     */
    public void visitString(TString string) {

        visit = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitTokenList(org.extex.exbib.core.bst.node.impl.TokenList)
     */
    public void visitTokenList(TokenList string) {

        assertTrue("should not be visited", false);
    }

}
