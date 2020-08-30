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

package org.extex.unit.etex.register.dimen;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \dimenexpr}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class DimenexprTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(DimenexprTest.class);
    }


    public DimenexprTest() {

        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} without any term produces an error. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEOF1() throws Exception {

        assertFailure(// --- input code ---
            "\\dimen1=\\dimenexpr \\relax",
            // --- log message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} with a non-balanced parenthesis produces an error.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            "\\dimen1=\\dimenexpr ( 123 \\relax",
            // --- log message ---
            "Missing ) inserted for expression");
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} with a non-balanced parenthesis produces an error.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError4() throws Exception {

        assertFailure(// --- input code ---
            "\\dimen1=\\dimenexpr ( 123pt \\relax",
            // --- log message ---
            "Missing ) inserted for expression");
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} with a non-balanced parenthesis produces an error.
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\dimen1=\\dimenexpr ( \\relax",
            // --- log message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} produces an error for division by zero. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(// --- input code ---
            "\\dimen1=\\dimenexpr 6pt/0\\relax" + "\\the\\dimen1 ",
            // --- log message ---
            "Arithmetic overflow");
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} can add two numbers. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 1pt + 2pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "3.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} can add multiply numbers. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 2*3pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "6.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} can subtract two numbers. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 5pt-2pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "3.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} can divide two numbers. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 6pt/2\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "3.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} * binds more than +. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 1pt + 2*3pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "7.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} * binds more than +. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 2pt*3 + 1pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "7.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} / binds more than +. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 4pt/2 + 10pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "12.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} / binds more than +. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 10pt + 4pt/2\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "12.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} * binds more than -. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test14() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 1pt - 2pt*3\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "-5.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} * binds more than -. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test15() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 2pt*3 - 1pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "5.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} / binds more than -. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test16() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 4pt/2 - 10pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "-8.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} / binds more than -. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test17() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 10pt - 4pt/2\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "8.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} parentheses work. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test21() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 2*(1pt + 2pt)\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "6.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} the unary minus is treated correctly. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test31() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 2*-3pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "-6.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} the unary minus is treated correctly &ndash; even
     * if repeated twice. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test32() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr 2*--3pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "6.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} the unary minus is treated correctly. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test33() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr -2pt + 3pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "1.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that in
     * {@code \dimenexpr} the unary minus is treated correctly &ndash; even
     * if repeated twice. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test34() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1=\\dimenexpr --2pt+3pt\\relax" + "\\the\\dimen1 ",
            // --- output channel ---
            "5.0pt" + TERM);
    }

    /**
     * <testcase primitive="\dimenexpr"> Test case checking that
     * {@code \dimenexpr} can be used after {@code \the}. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testThe0() throws Exception {

        assertSuccess(// --- input code ---
            "\\the\\dimenexpr 2*3pt\\relax",
            // --- output channel ---
            "6.0pt" + TERM);
    }

}
