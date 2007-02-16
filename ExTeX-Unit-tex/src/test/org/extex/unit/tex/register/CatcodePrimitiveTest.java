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

package org.extex.unit.tex.register;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\catcode</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CatcodePrimitiveTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(CatcodePrimitiveTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public CatcodePrimitiveTest(final String arg) {

        super(arg, "catcode", "1=1 ", "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> needs a char.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof1() throws Exception {

        assertFailure("\\catcode ", //
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> needs a token.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof2() throws Exception {

        assertFailure("\\catcode 65 ", //
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> needs a range for the value.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testRange1() throws Exception {

        assertFailure("\\catcode 1=-1", //
            "Invalid code (-1), should be in the range 0..15");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> needs a range for the value.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testRange2() throws Exception {

        assertFailure("\\catcode 1=16", //
            "Invalid code (16), should be in the range 0..15");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> needs a range for the value.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testRange4() throws Exception {

        assertFailure("\\catcode -1=1", //
            "Bad character code (-1)");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 0.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue0() throws Exception {

        assertSuccess("\\catcode 1=0 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 1.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue1() throws Exception {

        assertSuccess("\\catcode 1=1 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 2.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue2() throws Exception {

        assertSuccess("\\catcode 1=2 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 3.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue3() throws Exception {

        assertSuccess("\\catcode 1=3 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 4.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue4() throws Exception {

        assertSuccess("\\catcode 1=4 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 5.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue5() throws Exception {

        assertSuccess("\\catcode 1=5 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of 6.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue6() throws Exception {

        assertSuccess("\\catcode 1=6 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  7.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue7() throws Exception {

        assertSuccess("\\catcode 1= 7 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  8.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue8() throws Exception {

        assertSuccess("\\catcode 1= 8 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  9.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue9() throws Exception {

        assertSuccess("\\catcode 1= 9 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  10.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue10() throws Exception {

        assertSuccess("\\catcode 1= 10 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  11.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue11() throws Exception {

        assertSuccess("\\catcode 1= 11 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  12.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue12() throws Exception {

        assertSuccess("\\catcode 1= 12 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  13.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue13() throws Exception {

        assertSuccess("\\catcode 1= 13 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  14.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue14() throws Exception {

        assertSuccess("\\catcode 1= 14 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> takes a value of  15.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testValue15() throws Exception {

        assertSuccess("\\catcode 1= 15 \\end", //
            "");
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> is count convertible.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCount1() throws Exception {

        assertSuccess("\\catcode 1=15 \\count0=\\catcode1 \\the\\count0\\end", //
            "15" + TERM);
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> respects \afterassignment.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testAfterassignment1() throws Exception {

        assertSuccess("\\afterassignment x--\\catcode 1=15 \\end", //
            "--x" + TERM);
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> respects \global.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobal1() throws Exception {

        assertSuccess(DEFINE_BRACES
                + "{\\catcode 1=15} \\count0=\\catcode1 \\the\\count0\\end", //
            "12" + TERM);
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> respects \global.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobal2() throws Exception {

        assertSuccess(DEFINE_BRACES + "{\\global\\catcode 1=15}"
                + "\\count0=\\catcode1 \\the\\count0\\end", //
            "15" + TERM);
    }

    /**
     * <testcase primitive="\catcode">
     *  Test case checking that <tt>\catcode</tt> respects \globaldefs.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobal3() throws Exception {

        assertSuccess(DEFINE_BRACES + "\\globaldefs=1{\\catcode 1=15}"
                + "\\count0=\\catcode1 \\the\\count0\\end", //
            "15" + TERM);
    }

    //TODO implement more primitive specific test cases

}
