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

package org.extex.exbib.core.node.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.extex.exbib.core.bst.BstInterpreter099c;
import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.TokenFactory;
import org.extex.exbib.core.bst.token.TokenVisitor;
import org.extex.exbib.core.bst.token.impl.TBlock;
import org.extex.exbib.core.bst.token.impl.TChar;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TFieldInteger;
import org.extex.exbib.core.bst.token.impl.TFieldString;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLiteral;
import org.extex.exbib.core.bst.token.impl.TQLiteral;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.bst.token.impl.TokenList;
import org.extex.exbib.core.db.impl.DBImpl;
import org.extex.exbib.core.io.NullWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a test suite for {@link TQLiteral}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.1 $
 */
public class TestTQLiteral implements TokenVisitor {

    /**
     * The field <tt>p</tt> contains the processor.
     */
    private BstProcessor p = null;

    /**
     * The field <tt>visit</tt> contains the visited indicator.
     */
    private boolean visit = false;

    /**
     * Set-up method.
     * 
     * @throws Exception in case of an error
     */
    @Before
    public void setUp() throws Exception {

        p = new BstInterpreter099c(new DBImpl(), new NullWriter(null), null);
        p.addFunction("abc", TokenFactory.T_ONE, null);
    }

    /**
     * Tear-down method.
     */
    @After
    public void tearDown() {

        p = null;
    }

    /**
     * <testcase> A QLiteral executes to itself. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExecute() throws Exception {

        TQLiteral t = new TQLiteral("aaa", null);
        t.execute(p, null, null);

        Token x = p.pop(null);
        assertNull(p.popUnchecked());
        assertTrue(x instanceof TLiteral);
        assertEquals("aaa", x.getValue());
    }

    /**
     * <testcase> A QLiteral executes to itself. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand() throws Exception {

        TQLiteral t = new TQLiteral("aaa", null);
        String s = t.expand(p);

        assertNull(p.popUnchecked());
        assertEquals("aaa", s);
    }

    /**
     * <testcase> The token visitor invokes the correct method. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVisit() throws Exception {

        TQLiteral t = new TQLiteral("acd", null);
        t.visit(this);
        assertTrue(visit);
        assertEquals("acd", t.getValue());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitBlock(
     *      org.extex.exbib.core.bst.token.impl.TBlock)
     */
    public void visitBlock(TBlock block) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitChar(
     *      org.extex.exbib.core.bst.token.impl.TChar)
     */
    public void visitChar(TChar c) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitField(
     *      org.extex.exbib.core.bst.token.impl.TField)
     */
    public void visitField(TField field) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitFieldInteger(
     *      org.extex.exbib.core.bst.token.impl.TFieldInteger)
     */
    public void visitFieldInteger(TFieldInteger integer) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitFieldString(
     *      org.extex.exbib.core.bst.token.impl.TFieldString)
     */
    public void visitFieldString(TFieldString string) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitInteger(
     *      org.extex.exbib.core.bst.token.impl.TInteger)
     */
    public void visitInteger(TInteger integer) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitLiteral(
     *      org.extex.exbib.core.bst.token.impl.TLiteral)
     */
    public void visitLiteral(TLiteral literal) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitQLiteral(
     *      org.extex.exbib.core.bst.token.impl.TQLiteral)
     */
    public void visitQLiteral(TQLiteral qliteral) {

        visit = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitString(
     *      org.extex.exbib.core.bst.token.impl.TString)
     */
    public void visitString(TString string) {

        assertTrue("should not be visited", false);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.token.TokenVisitor#visitTokenList(
     *      org.extex.exbib.core.bst.token.impl.TokenList)
     */
    public void visitTokenList(TokenList string) {

        assertTrue("should not be visited", false);
    }

}
