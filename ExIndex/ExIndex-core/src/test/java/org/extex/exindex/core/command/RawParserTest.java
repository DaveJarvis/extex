/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.command.type.Attribute;
import org.extex.exindex.core.command.type.AttributesContainer;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.junit.Test;

/**
 * This is a test suite for (define-attributes).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6598 $
 */
public class RawParserTest {

    /**
     * The field <tt>DEFINE_ATTRIBUTES</tt> contains the symbol for
     * define-attributes.
     */
    private static final LSymbol DEFINE_ATTRIBUTES =
            LSymbol.get("define-attributes");

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    private Logger makeLogger() {

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);
        return logger;
    }

    /**
     * Run a test. This means load some configuration instruction into an
     * Indexer.
     * 
     * @param in the option string
     * 
     * @return the function binding for define-attributes
     * 
     * @throws Exception in case of an error
     */
    private AttributesContainer runTest(String in) throws Exception {

        Indexer indexer = new Indexer();
        assertNotNull(indexer);
        indexer.load(new StringReader(in), "<reader>");
        AttributesContainer container = indexer.getContainer();
        assertNotNull(container);
        return container;
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        runTest("(define-attributes ())");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        AttributesContainer def = runTest("(define-attributes (\"abc\"))");
        Attribute att = def.lookupAttribute("abc");
        assertNotNull(att);
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test03() throws Exception {

        AttributesContainer def =
                runTest("(define-attributes (\"abc\" \"def\"))");
        Attribute att = def.lookupAttribute("abc");
        assertNotNull(att);
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
        att = def.lookupAttribute("def");
        assertNotNull(att);
        assertEquals(1, att.getGroup());
        assertEquals(1, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        AttributesContainer def = runTest("(define-attributes ((\"abc\")))");
        Attribute att = def.lookupAttribute("abc");
        assertNotNull(att);
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test11() throws Exception {

        AttributesContainer def =
                runTest("(define-attributes ((\"abc\" \"def\")))");
        Attribute att = def.lookupAttribute("abc");
        assertNotNull(att);
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
        att = def.lookupAttribute("def");
        assertNotNull(att);
        assertEquals(0, att.getGroup());
        assertEquals(1, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LMissingArgumentsException.class)
    public final void testError01() throws Exception {

        runTest("(define-attributes )");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError02() throws Exception {

        runTest("(define-attributes \"abc\")");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError03() throws Exception {

        runTest("(define-attributes ((())))");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = SyntaxException.class)
    public final void testError04() throws Exception {

        runTest("(define-attributes (123)");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError05() throws Exception {

        runTest("(define-attributes (\"x\" \"x\"))");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError06() throws Exception {

        runTest("(define-attributes (\"x\" (\"x\")))");
    }

    /**
     * <testcase> Test that an undefined attribute leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testFull01Fail() throws Exception {

        Indexer indexer = new TestableIndexer();
        indexer.load(new StringReader("(define-attributes ((\"abc\")))"),
            "<reader>");
        List<String> rsc = new ArrayList<String>();
        rsc
            .add("(indexentry :tkey ((\"abc\")) :locref \"123\" :attr \"none\")");
        indexer.run(null, rsc, null, makeLogger());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(
     * org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFull01Ok() throws Exception {

        Indexer indexer = new TestableIndexer();
        indexer.load(new StringReader("(define-attributes ((\"none\")))"),
            "<reader>");
        List<String> rsc = new ArrayList<String>();
        rsc.add("(indexentry :key (\"abc\") :locref \"123\" :attr \"none\")");
        indexer.run(null, rsc, null, makeLogger());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(
     * org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFull02Ok() throws Exception {

        Indexer indexer = new TestableIndexer();
        List<String> rsc = new ArrayList<String>();
        rsc.add("(indexentry :key (\"abc\") :locref \"123\")");
        indexer.run(null, rsc, null, makeLogger());
    }

}
