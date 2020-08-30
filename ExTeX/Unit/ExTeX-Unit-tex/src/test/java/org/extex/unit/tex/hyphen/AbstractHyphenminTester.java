/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.hyphen;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a abstract base class for testing left/right hyphenation min.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractHyphenminTester extends ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive to test.
     */
    private String primitive;

    /**
     * The field {@code invocation} contains the concatenation of primitive
     * name and arguments.
     */
    private String invocation;

    /**
     * The field {@code init} contains the default value.
     */
    private String init;

    /**
     * The field {@code prepare} contains the prefix to be prepended before the
     * code for the primitive.
     */
    private String prepare;

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param args the arguments
     * @param init the initial value returned by the primitive
     */
    public AbstractHyphenminTester(String primitive, String args, String init) {

        this(null, primitive, args, init, "");
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name of the test suite
     * @param primitive the name of the integer register to test
     * @param args the arguments
     * @param init the initial value returned by the primitive
     * @param prepare the preparation code
     */
    public AbstractHyphenminTester(String arg, String primitive, String args,
            String init, String prepare) {

        this.primitive = primitive;
        this.invocation = primitive + args;
        this.init = init;
        this.prepare = prepare;
    }

    /**
     * Test case showing that the prefix {@code \immediate} is not applicable
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterImmediatePrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\immediate\\" + invocation + "=92 ",
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
    public void testCountRegisterLongPrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\long\\" + invocation + "=92 ",
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
    public void testCountRegisterOuterPrefix1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\outer\\" + invocation + "=92 ",
            // --- error channel ---
            "You can't use the prefix `\\outer' with the control sequence"
                    + (primitive.length() > 18 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * Test case showing that the primitive is defined and its default value is 0
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDefault1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            init + TERM);
    }

    /**
     * Test case showing that an assignment of a constant 123 works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=123 \\the\\" + invocation + "\\end",
            // --- output channel ---
            "123" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant 123 works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign2() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + " 123 \\the\\" + invocation + "\\end",
            // --- output channel ---
            "123" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant -123 works when using an equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign3() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=-123 \\the\\" + invocation
                    + "\\end",
            // --- output channel ---
            "-123" + TERM);
    }

    /**
     * Test case showing that an assignment of a constant -123 works when using no equal sign after the primitive name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign4() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "-123 \\the\\" + invocation + "\\end",
            // --- output channel ---
            "-123" + TERM);
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\afterassignment b a" + "\\" + invocation
                    + "-123 c\\the\\" + invocation + "\\end",
            // --- output channel ---
            "abc-123" + TERM);
    }

    /**
     *  Test case showing that the value is count convertible.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterConvertible1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "-123 \\count0=\\" + invocation
                    + " \\the\\count0 \\end",
            // --- output channel ---
            "-123" + TERM);
    }

    /**
     * Test case showing that an advancement by the constant 12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + " 12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "35" + TERM);
    }

    /**
     * Test case showing that an advancement by the constant 12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance2() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + " by 12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "35" + TERM);
    }

    /**
     * Test case showing that an advancement by the constant -12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance3() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + "-12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "11" + TERM);
    }

    /**
     * Test case showing that an advancement by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAdvance4() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=23 " + "\\advance\\" + invocation
                    + " by -12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "11" + TERM);
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment2() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=0 " + "\\afterassignment b a"
                    + "\\advance\\" + invocation + "-123 c\\the\\" + invocation
                    + "\\end",
            // --- output channel ---
            "abc-123" + TERM);
    }

    /**
     * Test case showing that an multiplication with the constant 0 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply0() throws Exception {

        assertSuccess(
        // --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " 0 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "0" + TERM);
    }

    /**
     * Test case showing that an multiplication with the constant 12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " 12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "36" + TERM);
    }

    /**
     * Test case showing that an multiplication with the constant 12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply2() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " by 12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "36" + TERM);
    }

    /**
     * Test case showing that an multiplication by the constant -12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply3() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + "-12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "-36" + TERM);
    }

    /**
     * Test case showing that an multiplication by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterMultiply4() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=3 " + "\\multiply\\" + invocation
                    + " by -12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "-36" + TERM);
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment3() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=0 " + "\\afterassignment b a"
                    + "\\multiply\\" + invocation + "-123 c\\the\\"
                    + invocation + "\\end",
            // --- output channel ---
            "abc0" + TERM);
    }

    /**
     *  Test case showing that an division by the constant 12 works.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide0() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " 0 " + "\\the\\" + invocation + "\\end",
            // --- error channel ---
            "Arithmetic overflow");
    }

    /**
     *  Test case showing that an division by the constant 12 works.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " 12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * Test case showing that an division by the constant 12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide2() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " by 12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * Test case showing that an multiplication by the constant -12 works
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide3() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + "-12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "-3" + TERM);
    }

    /**
     * Test case showing that an multiplication by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide4() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=36 " + "\\divide\\" + invocation
                    + " by -12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "-3" + TERM);
    }

    /**
     * Test case showing that an assignment respects {@code \\afterassignment}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAfterassignment4() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\afterassignment b a" + "\\divide\\" + invocation
                    + "-123 c\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * Test case showing that division by the constant -12 works when using the keyword {@code by}
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterDivide6() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\" + invocation + "=-36 " + "\\divide\\" + invocation
                    + " by -12 " + "\\the\\" + invocation + "\\end",
            // --- output channel ---
            "3" + TERM);
    }

}
