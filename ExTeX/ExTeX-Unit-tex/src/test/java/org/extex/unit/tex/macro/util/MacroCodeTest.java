/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro.util;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the macro code defined with the primitive
 * <tt>\def</tt> or friends.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class MacroCodeTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public MacroCodeTest() {

        super();
    }

    /**
     * <testcase> Test case checking that an empty pattern does not absorb
     * anything. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc{--}" + "\\abc987\\end",
            // --- output channel ---
            "--987" + TERM);
    }

    /**
     * <testcase> Test case checking that a simple macro parameter absorbs a
     * single token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{-#1-}" + "\\abc987\\end",
            // --- output channel ---
            "-9-87" + TERM);
    }

    /**
     * <testcase> Test case checking that two macro parameters absorb a token
     * each. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2{-#1-#2-}" + "\\abc987\\end",
            // --- output channel ---
            "-9-8-7" + TERM);
    }

    /**
     * <testcase> Test case checking that en embedded constant token is
     * respected when the preceding macro parameter consumes one token.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1x#2{-#1-#2-}" + "\\abc9x87\\end",
            // --- output channel ---
            "-9-8-7" + TERM);
    }

    /**
     * <testcase> Test case checking that en embedded constant token is
     * respected when the preceding macro parameter consumes two tokens.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1x#2{-#1-#2-}" + "\\abc98x7\\end",
            // --- output channel ---
            "-98-7-" + TERM);
    }

    /**
     * <testcase> Test case checking that en embedded constant token is
     * respected when the preceding macro parameter consumes one token group.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2{-#1-#2-}" + "\\abc{98}7\\end",
            // --- output channel ---
            "-98-7-" + TERM);
    }

    /**
     * <testcase> Test case checking that the second macro parameter can take a
     * token group. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2{-#1-#2-}" + "\\abc9{87}6\\end",
            // --- output channel ---
            "-9-87-6" + TERM);
    }

    /**
     * <testcase> Test case checking that scanning for a parameter at eof leads
     * to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{}" + "\\abc",
            // --- error channel ---
            "File ended while scanning use of \\abc");
    }

    /**
     * <testcase> Test case checking that scanning for a token which does never
     * come leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc1{}" + "\\abc",
            // --- error channel ---
            "Use of \\abc doesn't match its definition");
    }

    /**
     * <testcase> Test case checking that scanning for a token after a parameter
     * which does never come leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1:{}" + "\\abc",
            // --- error channel ---
            "File ended while scanning use of \\abc");
    }

    /**
     * <testcase> Test case checking that scanning for an unclosed argument
     * block leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError4() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{}" + "\\abc{",
            // --- error channel ---
            "File ended while scanning use of \\abc");
    }

    /**
     * <testcase> Test case checking that scanning for an unopened argument
     * block leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError5() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{}" + "\\abc}",
            // --- error channel ---
            "Argument of \\abc has an extra }");
    }

    /**
     * <testcase> Test case checking that scanning for an unopened argument
     * block leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError6() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{#}" + "\\abc9\\end",
            // --- error channel ---
            "File ended while scanning definition of \\abc");
    }

    /**
     * <testcase> Test case checking that braces are removed when scanning for a
     * following token. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#2#3\\then{--#1--#2--#3--}"
                    + "\\abc12{3}\\then\\end",
            // --- output channel ---
            "--1--2--3--" + TERM);
    }

    /**
     * <testcase> Test case checking that braces are added when the last # is
     * followed by a {. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#{\\write22 }" + "\\abc1{23}\\end",
            // --- error channel ---
            "23",
            // --- output channel ---
            "\n");
    }

    /**
     * <testcase> Test case checking that ## in parameters are matched against a
     * single #. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1##{\\write22{-#1-}}"
                    + "\\abc1#\\end",
            // --- error channel ---
            "-1-",
            // --- output channel ---
            "\n");
    }

    /**
     * <testcase> Test case checking that ## in parameters are matched against a
     * single #. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1##{\\write22{-#1-}}" + "\\abc1",
            // --- error channel ---
            "Use of \\abc doesn't match its definition");
    }

    /**
     * <testcase> Test case checking that a \par in a macro argument of a macro
     * which is not long leads to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test14() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1.{}" + "\\abc a\\par b.",
            // --- error channel ---
            "Paragraph ended before \\abc was complete");
    }

    /**
     * <testcase> Test case checking that a \par in a macro argument of a macro
     * which is long does not lead to an exception. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test15() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\long\\def\\abc#1.{}" + "\\abc a\\par b.\\end",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a # in a macro body is passed out.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test16() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc{\\def\\x##1{-##1-}}"
                    + "\\abc\\x9 \\end",
            // --- output channel ---
            "-9-" + TERM);
    }

    /**
     * <testcase> Test case checking that a macro without parameters and an
     * empty replacement text is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow1() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc{}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "->.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro without parameters and a
     * non-empty replacement text is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow2() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc{xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "->xyz.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro with parameter and a non-empty
     * replacement text is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow3() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "#1->xyz.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro with parameter and a non-empty
     * replacement text and trailing { is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow4() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1#{xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "#1{->xyz{.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro with parameter and a non-empty
     * replacement text with the parameter is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow5() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{x#1x}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "#1->x#1x.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro with parameter and a non-empty
     * replacement text with doubled # is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow6() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{x##1x}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "#1->x##1x.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a long macro is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow7() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\long\\def\\abc#1#{xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=\\long macro:\n" + "#1{->xyz{.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a outer macro is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow8() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\outer\\def\\abc#1#{xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=\\outer macro:\n" + "#1{->xyz{.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a outer and long macro is showable.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow9() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\outer\\long\\def\\abc#1#{xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=\\long\\outer macro:\n" + "#1{->xyz{.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro with macros in the expansion
     * text is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow10() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\def\\abc#1{\\xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> \\abc=macro:\n" + "#1->\\xyz.\n",
            // --- output channel ---
            "");
    }

    /**
     * <testcase> Test case checking that a macro with macros in the expansion
     * text is showable and respects \escapechar. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testShow11() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_CATCODES + "\\escapechar=-1 " + "\\def\\abc#1{\\xyz}" //
                    + "\\show\\abc \\end",
            // --- error channel ---
            "> abc=macro:\n" + "#1->xyz.\n",
            // --- output channel ---
            "");
    }

}