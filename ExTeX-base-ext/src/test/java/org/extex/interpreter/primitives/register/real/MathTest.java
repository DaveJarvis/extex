/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.interpreter.primitives.register.real;

import org.extex.test.ExTeXLauncher;

/**
 * A test for the the mathematical function with real register.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class MathTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public MathTest() {

        super("MathTest");

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\mathpi">
     * 
     * Test case checking that <tt>\mathpi</tt> returns the right value.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathPi01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathpi \\end",
            // --- output channel ---
            String.valueOf(Math.PI) + TERM);
    }

    /**
     * <testcase primitive="\mathpi">
     * 
     * Test case: 3 + pi
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathPi02() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\realdef\\x=42 " + "\\real42=3.0 "
                    + "\\advance\\real42 by \\mathpi" + "\\the\\real42 \\end",
            // --- output channel ---
            String.valueOf(3.0 + Math.PI) + TERM);
    }

    /**
     * <testcase primitive="\mathabs">
     * 
     * Test case: abs(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathAbs01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathabs 0.234 \\end",
            // --- output channel ---
            String.valueOf(0.234d) + TERM);
    }

    /**
     * <testcase primitive="\mathabs">
     * 
     * Test case: abs(-0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathAbs02() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathabs -0.234 \\end",
            // --- output channel ---
            String.valueOf(0.234d) + TERM);
    }

    /**
     * <testcase primitive="\mathacos">
     * 
     * Test case: acos(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathACos01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathacos 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.acos(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathadd">
     * 
     * Test case: 0.234 + 0.34
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathAdd01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathadd 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(0.234d + 0.34d) + TERM);
    }

    /**
     * <testcase primitive="\mathasin">
     * 
     * Test case: asin(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathASin01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathasin 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.asin(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathatan">
     * 
     * Test case: atan(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathATan01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathatan 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.atan(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathatantwo">
     * 
     * Test case: atan2(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathATanTwo01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathatantwo 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(Math.atan2(0.234d, 0.34d)) + TERM);
    }

    /**
     * <testcase primitive="\mathceil">
     * 
     * Test case: ceil(1.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathCeil01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathceil 1.234 \\end",
            // --- output channel ---
            String.valueOf(Math.ceil(1.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathcos">
     * 
     * Test case: cos(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathCos01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathcos 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.cos(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathdiv">
     * 
     * Test case: 1.234 / 2.1
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathDiv01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathdiv 1.234 2.1 \\end",
            // --- output channel ---
            String.valueOf(1.234d / 2.1d) + TERM);
    }

    /**
     * <testcase primitive="\mathe">
     * 
     * Test case checking that <tt>\mathe</tt> returns the right value.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathE01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathe \\end",
            // --- output channel ---
            String.valueOf(Math.E) + TERM);
    }

    /**
     * <testcase primitive="\mathexp">
     * 
     * Test case: exp(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathExp01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathexp 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.exp(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathfloor">
     * 
     * Test case: floor(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathFloor01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathfloor 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.floor(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathlog">
     * 
     * Test case: log(22)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathLog01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathlog 22 \\end",
            // --- output channel ---
            String.valueOf(Math.log(22.0d)) + TERM);
    }

    /**
     * <testcase primitive="\mathmax">
     * 
     * Test case: max(0.234,0.34)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathMax01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathmax 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(Math.max(0.234d, 0.34d)) + TERM);
    }

    /**
     * <testcase primitive="\mathmin">
     * 
     * Test case: min(0.234,0.34)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathMin01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathmin 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(Math.min(0.234d, 0.34d)) + TERM);
    }

    /**
     * <testcase primitive="\mathmul">
     * 
     * Test case: 0.234 * 0.34
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathMul01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathmul 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(0.234d * 0.34d) + TERM);
    }

    /**
     * <testcase primitive="\mathpow">
     * 
     * Test case: 0.234 * 0.34
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathMPow01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathpow 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(Math.pow(0.234d, 0.34d)) + TERM);
    }

    /**
     * <testcase primitive="\mathrint">
     * 
     * Test case: 0.234 * 0.34
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathRint01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathrint 1.234 \\end",
            // --- output channel ---
            String.valueOf(Math.rint(1.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathround">
     * 
     * Test case: round(1.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathRound01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathround 1.234 \\end",
            // --- output channel ---
            String.valueOf((double) Math.round(1.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathsin">
     * 
     * Test case: sin(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathSin01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathsin 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.sin(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathsqrt">
     * 
     * Test case: sqrt(4)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathSqrt01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathsqrt 4 \\end",
            // --- output channel ---
            String.valueOf(Math.sqrt(4d)) + TERM);
    }

    /**
     * <testcase primitive="\mathsub">
     * 
     * Test case: 0.234 - 0.34
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathSub01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathsub 0.234 0.34 \\end",
            // --- output channel ---
            String.valueOf(0.234d - 0.34d) + TERM);
    }

    /**
     * <testcase primitive="\mathtan">
     * 
     * Test case: tan(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathTan01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathtan 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.tan(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathtodegrees">
     * 
     * Test case: todegreess(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathToDegrees01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathtodegrees 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.toDegrees(0.234d)) + TERM);
    }

    /**
     * <testcase primitive="\mathtoradians">
     * 
     * Test case: toradians(0.234)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMathToRadians01() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\the\\mathtoradians 0.234 \\end",
            // --- output channel ---
            String.valueOf(Math.toRadians(0.234d)) + TERM);
    }

}
