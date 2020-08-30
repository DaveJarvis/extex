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

package org.extex.unit.etex.register.count;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \numexpr}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class NumexprTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(NumexprTest.class);
    }


    public NumexprTest() {

        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * without any term produces an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEOF1() throws Exception {

        assertFailure(// --- input code ---
            "\\count1=\\numexpr \\relax",
            // --- log message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * with a non-balanced parenthesis produces an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            "\\count1=\\numexpr ( 123 \\relax",
            // --- log message ---
            "Missing ) inserted for expression");
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * with a non-balanced parenthesis produces an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\count1=\\numexpr ( \\relax",
            // --- log message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * produces an error for division by zero.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(// --- input code ---
            "\\count1=\\numexpr 6/0\\relax" + "\\the\\count1 ",
            // --- log message ---
            "Arithmetic overflow");
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * can add two numbers.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 1+2\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * can add multiply numbers.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 2*3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "6" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * can subtract two numbers.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 5-2\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * can divide two numbers.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 6/2\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} * binds more than +.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 1+2*3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "7" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} * binds more than +.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 2*3+1\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "7" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} / binds more than +.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 4/2+10\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "12" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} / binds more than +.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 10+4/2\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "12" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} * binds more than -.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test14() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 1-2*3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "-5" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} * binds more than -.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test15() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 2*3-1\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "5" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} / binds more than -.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test16() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 4/2-10\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "-8" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} / binds more than -.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test17() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 10-4/2\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "8" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} parentheses work.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test21() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 2*(1+2)\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "6" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} the unary minus is treated correctly.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test31() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 2*-3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "-6" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} the unary minus is treated correctly &ndash; even if
     * repeated twice.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test32() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr 2*--3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "6" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} the unary minus is treated correctly.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test33() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr -2+3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "1" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that in
     * {@code \numexpr} the unary minus is treated correctly &ndash; even if
     * repeated twice.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test34() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1=\\numexpr --2+3\\relax" + "\\the\\count1 ",
            // --- output channel ---
            "5" + TERM);
    }

    /**
     * <testcase primitive="\numexpr"> Test case checking that {@code \numexpr}
     * can be used after {@code \the}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testThe0() throws Exception {

        assertSuccess(// --- input code ---
            "\\the\\numexpr 2*3\\relax",
            // --- output channel ---
            "6" + TERM);
    }

}
