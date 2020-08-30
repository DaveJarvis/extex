/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * omuion) any later version.
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

package org.extex.test.toks;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a abstract base class for testing tokens registers. It provides some
 * test cases common to all tokens registers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractToksRegisterTester extends ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive to
     * test.
     */
    private final String primitive;

    /**
     * The field {@code invocation} contains the concatenation of primitive
     * name and arguments.
     */
    private final String invocation;

    /**
     * The field {@code init} contains the default value.
     */
    private final String init;

    /**
     * The field {@code prepare} contains the preparation code.
     */
    private String prepare = DEFINE_BRACES;

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param args the parameters for the invocation
     * @param init the default value
     */
    public AbstractToksRegisterTester(String primitive, String args, String init) {

        this.primitive = primitive;
        this.invocation = primitive + args;
        this.init = init;
    }

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param args the parameters for the invocation
     * @param init the default value
     * @param prepare the preparation code
     */
    public AbstractToksRegisterTester(String primitive, String args,
            String init, String prepare) {

        this(primitive, args, init);
        this.prepare = DEFINE_BRACES + prepare;
    }

    /**
     *  Test case showing that the tokens register needs a parameter.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\" + invocation,
            // --- output channel ---
            "File ended while scanning text of \\" + primitive);
    }

    /**
     * Test case showing that the tokens register needs a complete parameter
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof2() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\" + invocation + "{",
            // --- output channel ---
            "File ended while scanning text of \\" + primitive);
    }

    /**
     * Test case showing that an assignment respects {@code \afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterAfterassignment1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\afterassignment b a" + "\\" + invocation
                    + "{xyz}c\\the\\" + invocation + "\\end",
            // --- output channel ---
            "abcxyz" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant {abc} works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterAssign1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "={abc}\\the\\" + invocation
                    + "\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant {abc} works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterAssign2() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + " {abc}\\the\\" + invocation
                    + "\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant {a{b}c} works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterAssign3() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + " {a{b}c}\\the\\" + invocation
                    + "\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant {a#c} results in a#c when the catcode of # is OTHER
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterAssign4() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + " {a#c}\\the\\" + invocation
                    + "\\end",
            // --- output channel ---
            "a#c" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant {a#c} results in a#c when the catcode of # is OTHER
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterAssign5() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + prepare + "\\" + invocation + " {a#c}\\showthe\\"
                    + invocation + "\\end",
            // --- error channel ---
            "> a##c.\n",
            // --- output channel ---
            ""); // checked with TeX
    }

    /**
     * Test case showing that the primitive is defined and has its default value
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterDefault1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            init + (init.length() != 0 ? TERM : ""));
    }

    /**
     * Test case showing that an assignment of a constant 12.3mu works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterGlobalAssign1() throws Exception {

        assertSuccess(
        // --- input code ---
            prepare + "\\begingroup\\global\\" + invocation
                    + "={abc}\\endgroup" + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant 12.3mu works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterGlobalAssign2() throws Exception {

        assertSuccess(
        // --- input code ---
            prepare + "\\begingroup\\global\\" + invocation
                    + " {abc}\\endgroup" + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     *  Test case showing that an assignment respects grouping.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterGroup1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "={xyz}\\begingroup\\" + invocation
                    + "={abc}\\endgroup" + " \\the\\" + invocation + "\\end",
            // --- output channel ---
            "xyz" + TERM);
    }

    /**
     * Test case showing that the prefix {@code \immediate} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterImmediatePrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\immediate\\" + invocation + "= {} ",
            // --- error channel ---
            "You can't use the prefix `\\immediate' with the control sequence"
                    + (primitive.length() > 14 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that the prefix {@code \long} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterLongPrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\long\\" + invocation + "= {} ",
            // --- error channel ---
            "You can't use the prefix `\\long' with the control sequence"
                    + (primitive.length() > 19 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that the prefix {@code \outer} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterOuterPrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\outer\\" + invocation + "= {} ",
            // --- error channel ---
            "You can't use the prefix `\\outer' with the control sequence"
                    + (primitive.length() > 18 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that the primitive is applicable to \showthe
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testToksRegisterShowthe1() throws Exception {

        assertOutput(// --- input code ---
            prepare + "\\showthe\\" + invocation + "\\end",
            // --- output channel ---
            out(init), "");
    }

    // TODO gene: add more test cases
}
