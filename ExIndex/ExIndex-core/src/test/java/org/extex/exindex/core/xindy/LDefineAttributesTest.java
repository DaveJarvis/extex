/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core.xindy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringReader;

import org.extex.exindex.core.xindy.type.Attribute;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.extex.exindex.lisp.exception.LSettingConstantException;
import org.extex.exindex.lisp.exception.SyntaxException;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.junit.Test;

/**
 * This is a test suite for (define-attributes).
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LDefineAttributesTest {

    /**
     * The field <tt>DEFINE_ATTRIBUTES</tt> contains the symbol for
     * define-attributes.
     */
    private static final LSymbol DEFINE_ATTRIBUTES =
            LSymbol.get("define-attributes");

    /**
     * Run a test. This means load some configuration instruction into an
     * Indexer.
     * 
     * @param in the option string
     * 
     * @return the function binding for define-attributes
     * 
     * @throws NoSuchMethodException in case of an error
     * @throws LSettingConstantException in case of an error
     * @throws IOException in case of an error
     * @throws LException in case of an error
     */
    private LDefineAttributes runTest(String in)
            throws NoSuchMethodException,
                LSettingConstantException,
                IOException,
                LException {

        Indexer indexer = new Indexer();
        assertNotNull(indexer);
        indexer.load(new StringReader(in), "<reader>");
        LDefineAttributes function =
                (LDefineAttributes) indexer.getFunction(DEFINE_ATTRIBUTES);
        assertNotNull(function);
        return function;
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test01() throws Exception {

        runTest("(define-attributes ())");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test02() throws Exception {

        LDefineAttributes def = runTest("(define-attributes (\"abc\"))");
        Attribute att = def.lookup("abc");
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test03() throws Exception {

        LDefineAttributes def =
                runTest("(define-attributes (\"abc\" \"def\"))");
        Attribute att = def.lookup("abc");
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
        att = def.lookup("def");
        assertEquals(1, att.getGroup());
        assertEquals(1, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        LDefineAttributes def = runTest("(define-attributes ((\"abc\")))");
        Attribute att = def.lookup("abc");
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test11() throws Exception {

        LDefineAttributes def =
                runTest("(define-attributes ((\"abc\" \"def\")))");
        Attribute att = def.lookup("abc");
        assertEquals(0, att.getGroup());
        assertEquals(0, att.getOrd());
        att = def.lookup("def");
        assertEquals(0, att.getGroup());
        assertEquals(1, att.getOrd());
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LMissingArgumentsException.class)
    public final void testError01() throws Exception {

        runTest("(define-attributes )");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError02() throws Exception {

        runTest("(define-attributes \"abc\")");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = LException.class)
    public final void testError03() throws Exception {

        runTest("(define-attributes ((())))");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.core.xindy.LDefineAttributes#eval(org.extex.exindex.lisp.LInterpreter, java.util.List)}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = SyntaxException.class)
    public final void testError04() throws Exception {

        runTest("(define-attributes (123)");
    }

}
