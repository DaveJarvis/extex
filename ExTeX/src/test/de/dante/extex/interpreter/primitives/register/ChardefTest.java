/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.register;

import de.dante.test.NoFlagsButGlobalPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\chardef</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ChardefTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(ChardefTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public ChardefTest(final String arg) {

        super(arg, "chardef", "\\x=42 ");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> throws an error on eof.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEOF1() throws Exception {

        assertFailure(//--- input code ---
                "\\chardef",
                //--- log message ---
                "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> throws an error on eof.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEOF2() throws Exception {

        assertFailure(//--- input code ---
                "\\chardef\\x",
                //--- log message ---
                "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a code token as
     *  first argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testNonCode1() throws Exception {

        assertFailure(//--- input code ---
                "\\chardef a =123",
                //--- log message ---
                "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a code token as
     *  first argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testNonCode2() throws Exception {

        assertFailure(//--- input code ---
                "\\chardef 12=123",
                //--- log message ---
                "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a code token as
     *  first argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErr1() throws Exception {

        assertFailure(//--- input code ---
                "\\chardef\\x=-1",
                //--- log message ---
                "Bad character code (-1)");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a code token as
     *  first argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        assertSuccess(//--- input code ---
                "\\chardef\\x=0 \\the\\x\\end",
                //--- output channel ---
                "0" + TERM);
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a code token as
     *  first argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(//--- input code ---
                "\\chardef\\x=65 \\the\\x\\end",
                //--- output channel ---
                "65" + TERM);
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> defines something usable as
     *  count.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(//--- input code ---
                "\\chardef\\x=65 \\count0=\\x\\the\\count0\\end",
                //--- output channel ---
                "65" + TERM);
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> interacts with groups.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test10() throws Exception {

        assertSuccess(//--- input code ---
                DEFINE_BRACES
                + "\\def\\x{-}{\\chardef\\x=65 }\\x\\end",
                //--- output channel ---
                "-" + TERM);
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> interacts with groups.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test11() throws Exception {

        assertSuccess(//--- input code ---
                DEFINE_BRACES
                + "{\\global\\chardef\\x=65 }\\the\\x\\end",
                //--- output channel ---
                "65" + TERM);
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> interacts with groups.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        assertSuccess(//--- input code ---
                DEFINE_BRACES
                + "\\globaldefs=1"
                + "{\\chardef\\x=65 }\\the\\x\\end",
                //--- output channel ---
                "65" + TERM);
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> is an assignment.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test21() throws Exception {

        assertSuccess(//--- input code ---
                "\\afterassignment abc"
                + "\\chardef\\x=65 \\the\\x\\end",
                //--- output channel ---
                "bca65" + TERM);
    }

}
