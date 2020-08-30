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
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \futurelet}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class FutureletTest extends NoFlagsButGlobalPrimitiveTester {


    public FutureletTest() {

        setPrimitive("futurelet");setArguments("\\relax\\relax\\relax");
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} needs an argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\futurelet ",
            // --- error message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} needs more than one tokens as arguments.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            "\\futurelet \\x",
            // --- error message ---
            "Unexpected end of file while processing \\futurelet");
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} needs a control sequence as first character.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(// --- input code ---
            "\\futurelet x",
            // --- error message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} needs a control sequence as first character.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError4() throws Exception {

        assertFailure(// --- input code ---
            "\\futurelet ab",
            // --- error message ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} works on two letters.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\futurelet \\x ab" + "\\end",
            // --- output message ---
            "ab" + TERM);
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} works on a digit ans a letter.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\futurelet \\x 1b" + "\\end",
            // --- output message ---
            "1b" + TERM);
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} defines the control sequence locally.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobalErr1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "{\\futurelet \\x AB}-\\x-",
            // --- output message ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} respects {@code \global}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "{\\global\\futurelet \\x AB}-\\x-" + "\\end",
            // --- output message ---
            "AB-B-" + TERM);
    }

    /**
     * <testcase primitive="\futurelet"> Test case checking that
     * {@code \futurelet} expands a middle macro.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\def\\z{-\\x-}\\futurelet \\x\\z B" + "\\end",
            // --- output message ---
            "-B-B" + TERM);
    }

}
