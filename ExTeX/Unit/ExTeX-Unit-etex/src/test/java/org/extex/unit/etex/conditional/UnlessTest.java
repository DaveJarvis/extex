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

package org.extex.unit.etex.conditional;

import org.extex.test.ExTeXLauncher;
import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \u005cnless}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class UnlessTest extends ExTeXLauncher {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(UnlessTest.class);
    }


    public UnlessTest() {

        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} needs a following control sequence.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unless",
            // --- error channel ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} needs a following control sequence.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unless a",
            // --- error channel ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} needs a following conditional.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unless\\relax",
            // --- error channel ---
            "You can't use `\\relax' after \\unless");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} needs a following control sequence.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unless 1",
            // --- error channel ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} does not work with \ifcase.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError4() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unless\\ifcase",
            // --- error channel ---
            "You can't use `\\ifcase' after \\unless");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} does not work with \u005cunless.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError5() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unless\\unless\\ifcase",
            // --- error channel ---
            "You can't use `\\unless' after \\unless");
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifnum on equals.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\unless\\ifnum1=1 a\\else b\\fi \\end",
            // --- output channel ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifnum.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\unless\\ifnum1=3 a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \if.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA1() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\if AA a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifcat.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA2() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifcat AA a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifnum (ne case).
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA3() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifnum 1=2 a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifdim.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA4() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifdim 1sp=2sp a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifodd.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA5() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifodd2 a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifvmode.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA6() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifvmode a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifmmode.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA8() throws Exception {

        assertSuccess(
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                    + "$\\unless\\ifmmode a\\else b\\fi$\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifvoid.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA10() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifvoid0 a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifhbox.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA11() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifhbox1 a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifvbox.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA12() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifvbox1 a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifx.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA13() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifx AA a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifeof.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA14() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\ifeof1 a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \iftrue.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA15() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\iftrue a\\else b\\fi\\end",
            // --- log message ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \iffalse.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA16() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\iffalse a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \ifcsname.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA19() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\unless\\ifcsname aaa\\endcsname a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\u005cnless"> Test case checking that
     * {@code \u005cnless} reverses the expansion of \iffontchar.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testA20() throws Exception {

        assertSuccess(// --- input code ---
            "\\unless\\iffontchar\\nullfont`\\a a\\else b\\fi\\end",
            // --- log message ---
            "a" + TERM);
    }

}
