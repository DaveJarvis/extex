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
import org.junit.Test;

/**
 * A test for the the mathematical expression function.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class MathExprTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public MathExprTest() {

        super();

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(2*7)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr01() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{2*7} \\end",
            // --- output channel ---
            String.valueOf(2d * 7d) + TERM);
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(2*sin(0.5))
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr02() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{2*sin(0.5)} \\end",
            // --- output channel ---
            String.valueOf(2d * Math.sin(0.5d)) + TERM);
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(2/x)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr03() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{2/x} \\end",
            // --- error channel ---
            "Error in the math expression: Expression is invalid.\n");
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(2+3/0)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr04() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{2+3/0} \\end",
            // --- output channel ---
            "Infinity" + TERM);
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(4+)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr05() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{4+} \\end",
            // --- error channel ---
            "Error in the math expression: Expression is invalid.\n");
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(abs(-1))
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr06() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{abs(-1)} \\end",
            // --- output channel ---
            "1.0" + TERM);
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(3 kleiner 3)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr07() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{3 < 3} \\end",
            // --- output channel ---
            "0.0" + TERM);
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case: expr(3 kleiner 4)
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr08() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\the\\mathexpr{3 < 4} \\end",
            // --- output channel ---
            "1.0" + TERM);
    }

    /**
     * <testcase primitive="\mathexpr">
     * 
     * Test case:
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathExpr09() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES
                    + "\\the\\mathexpr{((2 < 3) || (1 == 1)) && (3 < 3)} \\end",
            // --- output channel ---
            "0.0" + TERM);
    }

}
