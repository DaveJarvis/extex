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

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.extex.exindex.core.Indexer;
import org.extex.exindex.core.type.attribute.Attribute;
import org.extex.exindex.core.type.attribute.AttributesContainer;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.logging.LogFormatter;
import org.junit.Test;

/**
 * This is a test suite for (define-attributes).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6598 $
 */
public class RawParserTest {

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        TestUtils.runTest("(define-attributes ())");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        AttributesContainer def =
                TestUtils.runTest("(define-attributes (\"abc\"))");
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
                TestUtils.runTest("(define-attributes (\"abc\" \"def\"))");
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

        AttributesContainer def =
                TestUtils.runTest("(define-attributes ((\"abc\")))");
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
                TestUtils.runTest("(define-attributes ((\"abc\" \"def\")))");
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

        TestUtils.runTest("(define-attributes )");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError02() throws Exception {

        TestUtils.runTest("(define-attributes \"abc\")");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError03() throws Exception {

        TestUtils.runTest("(define-attributes ((())))");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = SyntaxException.class)
    public final void testError04() throws Exception {

        TestUtils.runTest("(define-attributes (123)");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError05() throws Exception {

        TestUtils.runTest("(define-attributes (\"x\" \"x\"))");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.command.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError06() throws Exception {

        TestUtils.runTest("(define-attributes (\"x\" (\"x\")))");
    }

    /**
     * <testcase> Test that an undefined attribute leads to a message.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFull01Fail() throws Exception {

        Locale.setDefault(Locale.ENGLISH);

        Indexer indexer = new TestableIndexer(TestUtils.DIRECT_FINDER);

        indexer.load(new StringReader("(define-attributes ((\"abc\")))"),
            "<reader>");
        List<String> rsc = new ArrayList<String>();
        rsc
            .add("(indexentry :tkey ((\"abc\")) :locref \"123\" :attr \"none\")");
        ByteArrayOutputStream log = new ByteArrayOutputStream();
        Logger logger = TestUtils.makeLogger();
        Handler handler = new StreamHandler(log, new LogFormatter());
        logger.setLevel(Level.INFO);
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        indexer.run(null, rsc, null, logger);
        handler.flush();
        handler.close();
        assertEquals("Das Attribut none ist unbekannt.\n", log.toString());
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

        Indexer indexer = new TestableIndexer(TestUtils.DIRECT_FINDER);
        indexer.load(new StringReader("(define-attributes ((\"none\")))"),
            "<reader>");
        List<String> rsc = new ArrayList<String>();
        rsc.add("(indexentry :key (\"abc\") :locref \"123\" :attr \"none\")");
        indexer.run(null, rsc, null, TestUtils.makeLogger());
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

        Indexer indexer = new TestableIndexer(TestUtils.DIRECT_FINDER);
        List<String> rsc = new ArrayList<String>();
        rsc.add("(indexentry :key (\"abc\") :locref \"123\")");
        indexer.run(null, rsc, null, TestUtils.makeLogger());
    }

}
