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
 * This is a test suite for the primitive <tt>\futurelet</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class FutureletTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public FutureletTest(String arg) {

        super(arg, "futurelet", "\\relax\\relax\\relax");
    }

    /**
     * <testcase primitive="\futurelet">
     *  Test case checking that <tt>\futurelet</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErr1() throws Exception {

        assertFailure(//--- input code ---
                "\\futurelet ab",
                //--- error message ---
                "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\futurelet">
     *  Test case checking that <tt>\futurelet</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(//--- input code ---
                "\\futurelet \\x ab" + "\\end",
                //--- output message ---
                "ab" + TERM);
    }

    /**
     * <testcase primitive="\futurelet">
     *  Test case checking that <tt>\futurelet</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(//--- input code ---
                "\\futurelet \\x 1b" + "\\end",
                //--- output message ---
                "1b" + TERM);
    }

    /**
     * <testcase primitive="\futurelet">
     *  Test case checking that <tt>\futurelet</tt> defines the control sequence
     *  locally.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobalErr1() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES + "{\\futurelet \\x AB}-\\x-",
                //--- output message ---
                "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\futurelet">
     *  Test case checking that <tt>\futurelet</tt> respects <tt>\global</tt>.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobal1() throws Exception {

        assertSuccess(//--- input code ---
                DEFINE_BRACES + "{\\global\\futurelet \\x AB}-\\x-" + "\\end",
                //--- output message ---
                "AB-B-" + TERM);
    }

    /**
     * <testcase primitive="\futurelet">
     *  Test case checking that <tt>\futurelet</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test3() throws Exception {

        assertSuccess(
                //--- input code ---
                DEFINE_BRACES + "\\def\\z{-\\x-}\\futurelet \\x\\z B" + "\\end",
                //--- output message ---
                "-B-B" + TERM);
    }

}