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

/**
 * This is a test suite for the primitive <tt>\omathchar</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OmathcharTest extends AbstractMathTester {

    /**
     * Constructor for MathcharTest.
     * 
     * @param arg the name
     */
    public OmathcharTest(String arg) {

        super(arg, "omathchar", "123 ");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * <tt>\omathchar</tt> needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testError0() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH + "$\\omathchar",
            // --- output message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * <tt>\omathchar</tt> doe not accept a negative value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH + "$\\omathchar-1 ",
            // --- output message ---
            "Bad mathchar (-1)");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * <tt>\omathchar</tt> complains about a too large value. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testError2() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH + "$\\omathchar\"8000001 ",
            // --- output message ---
            "Bad mathchar (134217729)");
    }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * <tt>\omathchar</tt> inserts ab appropriate character in math mode.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
                    + "$a\\omathchar\"010B b$\\end",
            // --- output message ---
            "a\013b" + TERM);
    }

    // /**
    // * <testcase primitive="\omathchar"> Test case checking that
    // * <tt>\omathchar</tt> inserts ab appropriate character in display math mode.
    // * </testcase>
    // *
    // * @throws Exception in case of an error
    // */
    // public void testDM1() throws Exception {
    //
    // assertSuccess(
    // //--- input code ---
    // AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
    // + "$$a\\omathchar\"010B b$$\\end",
    // //--- output message ---
    // "a\013b" + TERM);
    // }
    //
    // /**
    // * <testcase primitive="\omathchar"> Test case checking that
    // * <tt>\omathchar</tt> inserts ab appropriate character in display math mode.
    // * with extended codesyntax
    // * </testcase>
    // *
    // * @throws Exception in case of an error
    // */
    // public void testDMExt1() throws Exception {
    //
    // assertSuccess(
    // //--- input code ---
    // AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_BRACES
    // + DEFINE_MATH + "$$\\omathchar{ord 1 `A}$$\\end ",
    // //--- output message ---
    // "A" + TERM);
    // }

    /**
     * <testcase primitive="\omathchar"> Test case checking that
     * <tt>\omathchar</tt> in extended notation works for an ordinary
     * character. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testExt1() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_BRACES + DEFINE_MATH
                    + "$\\omathchar{ord 1 `A}$\\end ",
            // --- output message ---
            "A" + TERM);
    }

}
