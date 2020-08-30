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

package org.extex.unit.tex.conditional;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \ifx}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class IfxTest extends ConditionalTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IfxTest.class);
    }


    public IfxTest() {

        super("ifx", " aa");
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * complains at end of line.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\ifx ",
            // --- output channel ---
            "Unexpected end of file while processing \\ifx");
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * recognized identical letters.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifx aa true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * recognized different letters as differnt.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifx ab true\\else false\\fi",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies different undefined control sequences as identical.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifx \\xx\\yy true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies an undefined control sequences as identical to itself.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro2() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifx \\xx\\xx true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies an undefined control sequences as different to {@code \relax}.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro3() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifx \\relax\\yy true\\else false\\fi",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies an undefined control sequences as identical to one let to an
     * undefined control sequence.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro4() throws Exception {

        assertSuccess(// --- input code ---
            "\\let\\xx\\undefined\\ifx \\xx\\undefined true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies a let control sequences as identical to the original meaning.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro5() throws Exception {

        assertSuccess(// --- input code ---
            "\\let\\xx\\def\\ifx \\xx\\def true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies a let control sequences as identical to the original meaning.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMacro6() throws Exception {

        assertSuccess(// --- input code ---
            "\\let\\def\\xx\\ifx \\xx\\def true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * classifies a control sequence defined with {@code \csname} as
     * identical to {@code \relax}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCsname1() throws Exception {

        assertSuccess(// --- input code ---
            "\\expandafter\\ifx \\csname xx\\endcsname\\relax true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * reproduces the example from the TeXbook.
     * 
     * <p>
     * For example, after `{@code \def\a{\c}} {@code \def\b{\d}}
     *  {@code \def\c{\e}} {@code \def\d{\e}} {@code \def\e{A}}',
     * an {@code \ifx} test will find {@code \c} and {@code \d} equal,
     * but not {@code \a} and {@code \b}, nor {@code \d} and
     * {@code \e}, nor any other combinations of {@code \a}, {@code \b},
     * {@code \c}, {@code \d}, {@code \e}.
     * <p>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTeXbook() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\a{\\c}" + "\\def\\b{\\d}" + "\\def\\c{\\e}"
                    + "\\def\\d{\\e}" + "\\def\\e{A}"
                    + "\\ifx\\c\\d t \\else f \\fi "
                    + "\\ifx\\a\\b t \\else f \\fi "
                    + "\\ifx\\a\\c t \\else f \\fi "
                    + "\\ifx\\a\\d t \\else f \\fi "
                    + "\\ifx\\a\\e t \\else f \\fi "
                    + "\\ifx\\b\\c t \\else f \\fi "
                    + "\\ifx\\b\\d t \\else f \\fi "
                    + "\\ifx\\b\\e t \\else f \\fi "
                    + "\\ifx\\c\\e t \\else f \\fi "
                    + "\\ifx\\d\\e t \\else f \\fi " + "\\end",
            // --- output channel ---
            "t f f f f f f f f f" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * recognizes identical fonts.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFont1() throws Exception {

        assertSuccess(// --- input code ---
            "\\font\\f cmr10 " + "\\ifx\\f\\f t\\else f\\fi" + "\\end",
            // --- output channel ---
            "t" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares a font against an undefined control sequence as false.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFont2() throws Exception {

        assertSuccess(// --- input code ---
            "\\font\\f cmr10 " + "\\ifx\\f\\g t\\else f\\fi" + "\\end",
            // --- output channel ---
            "f" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares two fonts mapping to the same external font as different.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFont3() throws Exception {

        assertSuccess(// --- input code ---
            "\\font\\f cmr10 " + "\\font\\g cmr10 "
                    + "\\ifx\\f\\g t\\else f\\fi" + "\\end",
            // --- output channel ---
            "f" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares a font and an alias via \let as equal.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testFont4() throws Exception {

        assertSuccess(// --- input code ---
            "\\font\\f cmr10 " + "\\let\\g\\f " + "\\ifx\\f\\g t\\else f\\fi"
                    + "\\end",
            // --- output channel ---
            "t" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares a letter against \relax as different.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifx a\\relax true\\else false\\fi",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares a letter against a defined macro as different.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\x{a}" + "\\ifx a\\x true\\else false\\fi",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares a defined macro against \relax as different.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\x{a}"
                    + "\\ifx \\relax\\x true\\else false\\fi",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\ifx"> Test case checking that {@code \ifx}
     * compares two undefined macros as equal.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\ifx \\a\\b true\\else false\\fi",
            // --- output channel ---
            "true" + TERM);
    }

    // TODO implement more primitive specific test cases

}
