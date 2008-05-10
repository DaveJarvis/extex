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

package org.extex.exbib.core.bst.node.impl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.extex.exbib.core.Processor;
import org.extex.exbib.core.bst.BstProcessor099c;
import org.extex.exbib.core.bst.node.Token;
import org.extex.exbib.core.bst.node.TokenVisitor;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;

/**
 * This is a test suite for {@link TQLiteral}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TestTQLiteral extends TestCase implements TokenVisitor {

    /**
     * Main program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }

    /**
     * Getter for the test suite.
     * 
     * @return the suite
     */
    public static Test suite() {

        return new TestSuite(TestTQLiteral.class);
    }

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private Processor p = null;

    /**
     * The field <tt>visit</tt> contains the visited indicator.
     */
    private boolean visit = false;

    /**
     * Creates a new object.
     * 
     * @param name the name
     */
    public TestTQLiteral(String name) {

        super(name);
    }

    /**
     * {@inheritDoc}
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {

        p = new BstProcessor099c(new DBImpl(), new NullWriter(null), null);
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
     * <testcase> A QLiteral executes to itself. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testExecute() throws Exception {

        TQLiteral t = new TQLiteral("aaa", null);
        t.execute(p, null, null);

        Token x = p.pop(null);
        assertNull(p.popUnchecked());
        assertTrue(x instanceof TLiteral);
        assertEquals("aaa", x.getValue());
    }

    /**
     * <testcase> The token visitor invokes the correct method. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testVisit() throws Exception {

        TQLiteral t = new TQLiteral("acd", null);
        t.visit(this);
        assertTrue(visit);
        assertEquals("acd", t.getValue());
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

        visit = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.node.TokenVisitor#visitString(org.extex.exbib.core.bst.node.impl.TString)
     */
    public void visitString(TString string) {

        assertTrue("should not be visited", false);
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
