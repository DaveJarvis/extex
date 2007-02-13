/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\let</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class LetTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public LetTest(final String arg) {

        super(arg, "let", "\\relax\\relax");
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let needs a second argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\let\\a ",
            //--- output message ---
            "Unexpected end of file while processing \\let");
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetLetter1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\a A" + "--\\a--\\end",
            //--- output message ---
            "--A--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetLetterLocal1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\a A"
                    + "\\begingroup \\let\\a B\\endgroup" + "--\\a--\\end",
            //--- output message ---
            "--A--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetLetterGlobal1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\a A"
                    + "\\begingroup \\global\\let\\a B\\endgroup"
                    + "--\\a--\\end",
            //--- output message ---
            "--B--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a digit to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetOther1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\a 1" + "--\\a--\\end",
            //--- output message ---
            "--1--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a digit to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLet1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\let\\a\\a" + "\\a",
            //--- err message ---
            "Undefined control sequence \\a");
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetOtherLocal1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\a 1"
                    + "\\begingroup \\let\\a 2\\endgroup" + "--\\a--\\end",
            //--- output message ---
            "--1--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetOtherGlobal1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\a 1"
                    + "\\begingroup \\global\\let\\a 2\\endgroup"
                    + "--\\a--\\end",
            //--- output message ---
            "--2--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a digit to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetCs1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\x 1" + "\\let\\a \\x" + "--\\a--\\end",
            //--- output message ---
            "--1--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetCsLocal1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\x 1" + "\\let\\a \\x"
                    + "\\begingroup \\let\\a \\a\\endgroup" + "--\\a--\\end",
            //--- output message ---
            "--1--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign a letter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetCsGlobal1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\let\\x 2" + "\\let\\a A"
                    + "\\begingroup \\global\\let\\a \\x\\endgroup"
                    + "--\\a--\\end",
            //--- output message ---
            "--2--" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that let can assign space to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLetDefSpace1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + "\\def\\id#1{#1}" + "\\id{\\let\\a= } "
                    + "+-\\a-+\\end",
            //--- output message ---
            "+- -+" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a opening
     *  brace to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOpenBrace1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + DEFINE_TILDE + "\\let\\bgroup{"
                    + "\\bgroup}abc\\end",
            //--- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a opening
     *  brace to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOpenBrace2() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + DEFINE_TILDE + "\\let\\bgroup{\\def\\a{a}"
                    + "\\bgroup\\def\\a{b}}\\a bc\\end",
            //--- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a opening
     *  brace to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCloseBrace1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + DEFINE_TILDE + "\\let\\egroup}"
                    + "{\\egroup abc\\end",
            //--- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a opening
     *  brace to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCloseBrace2() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + DEFINE_TILDE + "\\let\\egroup}\\def\\a{a}"
                    + "{\\def\\a{b}\\egroup \\a bc\\end",
            //--- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a macro
     *  parameter to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMacroParam1() throws Exception {

        assertSuccess(//--- input code ---
            DEFINE_CATCODES + DEFINE_HASH + "\\let\\x#"
                    + "\\def\\a\\x1{-\\x1-} \\a b\\end",
            //--- output channel ---
            "-b-" + TERM);
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a submark
     *  to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSubmark1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\let\\x_ \\x",
            //--- output channel ---
            "Missing $ inserted");
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a supermark
     *  to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testSupermark1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\let\\x^ \\x",
            //--- output channel ---
            "Missing $ inserted");
    }

    /**
     * <testcase primitive="\let">
     *  Test case checking that <tt>\let</tt> can be used to assign a mathshift
     *  to a control sequence.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMathshift1() throws Exception {

        assertFailure(//--- input code ---
            DEFINE_CATCODES + "\\let\\m$\\m a_b$\\end",
            //--- output channel ---
            "Math formula deleted: Insufficient symbol fonts");
    }

}
