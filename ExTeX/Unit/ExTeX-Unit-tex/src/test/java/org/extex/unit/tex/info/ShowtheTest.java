/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \showthe}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ShowtheTest extends NoFlagsPrimitiveTester {


    public ShowtheTest() {

        setPrimitive("showthe");setArguments("\\count1 ");setPrepare("");setOut("> 0.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} produces an error message
     *  when applied to {@code \relax}.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testFailure1() throws Exception {

        assertFailure(//--- input code ---
                "\\showthe\\relax",
                //--- log message ---
                "You can't use `the control sequence \\relax' after \\showthe");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} produces an error message
     *  when applied to a digit.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testFailure2() throws Exception {

        assertFailure(//--- input code ---
                "\\showthe 4",
                //--- log message ---
                "You can't use `the character 4' after \\showthe");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a count register
     *  with value 0.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testCount1() throws Exception {

        assertFailure(//--- input code ---
                "\\showthe\\count1 \\end",
                //--- log message ---
                "> 0.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a count register
     *   with value 42.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testCount2() throws Exception {

        assertFailure(//--- input code ---
                "\\count1=42 "
                + "\\showthe\\count1 \\end",
                //--- log message ---
                "> 42.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a dimen register
     *  with value 0pt.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimen1() throws Exception {

        assertFailure(//--- input code ---
                "\\showthe\\dimen1 \\end",
                //--- log message ---
                "> 0.0pt.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a dimen register
     *  with value 42pt.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testDimen2() throws Exception {

        assertFailure(//--- input code ---
                "\\dimen1=42pt "
                + "\\showthe\\dimen1 \\end",
                //--- log message ---
                "> 42.0pt.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a glue register
     *  with zero length and no stretch or shrink.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testGlue1() throws Exception {

        assertFailure(//--- input code ---
                "\\showthe\\skip1 \\end",
                //--- log message ---
                "> 0.0pt.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a glue register
     *  with length and stretch and shrink components.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testGlue2() throws Exception {

        assertFailure(//--- input code ---
                "\\skip1= 12pt plus 3pt minus 4pt "
                + "\\showthe\\skip1 \\end",
                //--- log message ---
                "> 12.0pt plus 3.0pt minus 4.0pt.\n");
    }

    /**
     * <testcase primitive="\showthe">
     *  Test case checking that {@code \showthe} works on a undefined tokens
     *  register.
     *
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testToks1() throws Exception {

        assertFailure(//--- input code ---
                "\\showthe\\toks1 \\end",
                //--- log message ---
                "> .\n");
    }

    //TODO implement more primitive specific test cases

}
