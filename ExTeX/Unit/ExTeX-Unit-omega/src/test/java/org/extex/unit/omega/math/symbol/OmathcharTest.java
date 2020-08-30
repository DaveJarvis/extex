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

package org.extex.unit.omega.math.symbol;

import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \omathchar}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OmathcharTest extends AbstractMathTester {


    public OmathcharTest() {

        setPrimitive("omathchar");setArguments("123 ");setPrepare("");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} needs an argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH + "$\\omathchar",
            // --- output message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} doe not accept a negative value.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH + "$\\omathchar-1 ",
            // --- output message ---
            "Bad mathchar (-1)");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} complains about a too large value.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH + "$\\omathchar\"8000001 ",
            // --- output message ---
            "Bad mathchar (134217729)");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} inserts ab appropriate character in math mode.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
                    + "$a\\omathchar\"010B b$\\end",
            // --- output message ---
            "a\013b" + TERM);
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} inserts ab appropriate character in display math
     * mode.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDM1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
                    + "$$a\\omathchar\"010B b$$\\end",
            // --- output message ---
            "a\013b" + TERM);
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} inserts ab appropriate character in display math
     * mode. with extended codesyntax
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDMExt1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_BRACES + DEFINE_MATH
                    + "$$\\omathchar{ord 1 `A}$$\\end ",
            // --- output message ---
            "A" + TERM);
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * {@code \omathchar} in extended notation works for an ordinary
     * character.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testExt1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_BRACES + DEFINE_MATH
                    + "$\\omathchar{ord 1 `A}$\\end ",
            // --- output message ---
            "A" + TERM);
    }

}
