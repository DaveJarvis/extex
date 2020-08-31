/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.arithmetic;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \advance}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AdvanceTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(AdvanceTest.class);
    }


    public AdvanceTest() {

        setPrimitive("advance");setArguments("\\count0 1 ");
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * needs one arguments.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(// --- input code ---
            "\\advance ",
            // --- log message ---
            "Unexpected end of file while processing \\advance");
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * needs a defined control sequence as first argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndef1() throws Exception {

        assertFailure(// --- input code ---
            "\\advance \\x ",
            // --- log message ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a letter leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetter1() throws Exception {

        assertFailure(// --- input code ---
            "\\advance a",
            // --- log message ---
                      "You can't use `the letter a' after \\advance" );
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a other token leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOther1() throws Exception {

        assertFailure(// --- input code ---
            "\\advance 12 ",
            // --- log message ---
                      "You can't use `the character 1' after \\advance" );
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a macro parameter token leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro1() throws Exception {

        assertFailure(// --- input code ---
            "\\catcode`#=6 " + "\\advance #2 ",
            // --- log message ---
                      "You can't use `macro parameter character #' after " +
                          "\\advance" );
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a non-advancable primitive (\\relax) leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRelax1() throws Exception {

        assertFailure(// --- input code ---
            "\\advance \\relax ",
            // --- log message ---
                      "You can't use `the control sequence \\relax' after " +
                          "\\advance" );
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a count register name works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount1() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1 5 " + "\\advance \\count1 123 " + "\\the\\count1 \\end",
            // --- output channel ---
            "128" + TERM);
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a count register name works with a global flag.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount2() throws Exception {

        assertSuccess(// --- input code ---
            "\\count1 5 "
                    + "\\begingroup\\global\\advance \\count1 123 \\endgroup "
                    + "\\the\\count1 \\end",
            // --- output channel ---
            "128" + TERM);
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a dimen register name works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDimen1() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1 5pt " + "\\advance \\dimen1 123pt "
                    + "\\the\\dimen1 \\end",
            // --- output channel ---
            "128.0pt" + TERM);
    }

    /**
     * <testcase primitive="\advance"> Test case checking that {@code \advance}
     * on a dimen register name works with a global flag.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDimen2() throws Exception {

        assertSuccess(// --- input code ---
            "\\dimen1 5pt "
                    + "\\begingroup\\global\\advance \\dimen1 123pt \\endgroup "
                    + "\\the\\dimen1 \\end",
            // --- output channel ---
            "128.0pt" + TERM);
    }

}
