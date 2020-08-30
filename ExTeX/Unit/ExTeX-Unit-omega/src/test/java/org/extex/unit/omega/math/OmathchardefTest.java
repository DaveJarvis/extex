/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.math;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \omathchardef}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OmathchardefTest extends NoFlagsButGlobalPrimitiveTester {

    public OmathchardefTest() {
        setPrimitive("omathchardef");
        setArguments("\\a=\"32 ");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} needs an argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(
        // --- input code ---
            "\\omathchardef",
            // --- output message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} defines a control sequence which can be used in
     * math mode only.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(
        // --- input code ---
            "\\omathchardef\\alpha=\"010B " 
                    + "\\alpha\\end",
            // --- output message ---
            "Missing $ inserted");
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} does not acept a negatibe math code.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(
        // --- input code ---
            "\\omathchardef\\alpha=-1 ",
            // --- output message ---
            "Bad mathchar (-1)");
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} does not accept a too large math code.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(
        // --- input code ---
            "\\omathchardef\\alpha=\"8000001 ",
            // --- output message ---
            "Bad mathchar (134217729)");
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} produces a desirable result.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
                    + "\\omathchardef\\alpha=\"010B" 
                    + "$a\\alpha b$\\end",
            // --- output message ---
            "a\013b" + TERM);
    }

    /**
     * <testcase primitive="\mathchardef"> Test case checking that
     * {@code \omathchardef} can take a defined code as argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\omathchardef\\alpha=\"010B " 
                    + "\\omathchardef\\beta=\\alpha " 
                    + "\\the\\beta\\end",
            // --- output message ---
            "267" + TERM);
    }

    /**
     * <testcase primitive="\mathchardef"> Test case checking that
     * {@code \omathchardef} can take a character defined with
     * {@code \chardef} as math code.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCross1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\mathchardef\\alpha=\"010B " 
                    + "\\omathchardef\\beta=\\alpha " 
                    + "\\the\\beta\\end",
            // --- output message ---
            "65547" + TERM);
    }

    /**
     * <testcase primitive="\mathchardef"> Test case checking that
     * {@code \mathchardef} can take a mathdode defined with
     * {@code \omathchardef} as argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCross2() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\omathchardef\\alpha=\"01000B " 
                    + "\\mathchardef\\beta=\\alpha " 
                    + "\\the\\beta\\end",
            // --- output message ---
            "267" + TERM);
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} is count convertible.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\omathchardef\\alpha=\"010B " 
                    + "\\count0=\\alpha \\the\\count0\\end",
            // --- output message ---
            "267" + TERM);
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} can be assigned to an active character.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount2() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\catcode`~=13 " + "\\omathchardef~=\"010B " 
                    + "\\count0=~ \\the\\count0\\end",
            // --- output message ---
            "267" + TERM);
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that the controls
     * seqeunce defined with {@code \omathchardef} is acceptabe as arginet of
     * \the.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testThe1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\omathchardef\\alpha=\"010B " 
                    + "\\the\\alpha\\end",
            // --- output message ---
            "267" + TERM);
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} uses the {@code \global} flag.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroup1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\begingroup\\global\\omathchardef\\alpha=\"010B \\endgroup" 
                    + "\\the\\alpha\\end",
            // --- output message ---
            "267" + TERM);
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} respects groups.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroup2() throws Exception {

        assertFailure(
        // --- input code ---
            "\\begingroup\\omathchardef\\alpha=\"010B \\endgroup" 
                    + "\\the\\alpha\\end",
            // --- output message ---
            "Undefined control sequence \\alpha");
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} can parse the extended mach code syntax with
     * symbolic names.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExt1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\omathchardef\\alpha={ord 1 `A} " 
                    + "\\the\\alpha\\end",
            // --- output message ---
            "65601" + TERM);
    }

    /**
     * <testcase primitive="\omathchardef"> Test case checking that
     * {@code \omathchardef} can parse the extended mach code syntax with
     * numeric code.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExt2() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\omathchardef\\alpha={0 1 `A} " 
                    + "\\the\\alpha\\end",
            // --- output message ---
            "65601" + TERM);
    }

    /**
     * <testcase primitive="\show"> Test case checking that {@code \show}
     * works with a defined Omega math character.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOmathchardef1() throws Exception {

        assertFailure(// --- input code ---
            "\\omathchardef\\x=123 \\show\\x" + "\\end",
            // --- output channel ---
            "> \\x=\\omathchar\"7B.\n");
    }

    // TODO implement more primitive specific test cases
}
