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

package org.extex.unit.tex.register.box;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\setbox</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class SetboxTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(SetboxTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public SetboxTest(String arg) {

        super(arg, "setbox", "1=\\hbox{}", "0");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> needs a key.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof1() throws Exception {

        assertFailure("\\setbox ", //
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> needs a box.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof2() throws Exception {

        assertFailure("\\setbox 1", //
            "A <box> was supposed to be here");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> respects groups.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGroup1() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "{\\setbox1=\\hbox{abc}}\\box1\\end ", //
            "");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> respects \global.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGroup2() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "{\\global\\setbox1=\\hbox{abc}}\\box1\\end ", //
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> respects \globaldefs.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGroup3() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "\\globaldefs=1{\\setbox1=\\hbox{abc}}\\box1\\end ", //
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> respects \everyhbox.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEveryhbox1() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "\\everyhbox{x}-\\setbox1=\\hbox{abc}\\box1\\end ", //
            "-xabc" + TERM);
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> respects \afterassignment.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testAfterassignment1() throws Exception {

        assertSuccess(//
            DEFINE_BRACES
                    + "\\afterassignment x-\\setbox1=\\hbox{abc}\\box1\\end ", //
            "-xabc" + TERM);
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> takes some kind of a box.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testAssign1() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "\\setbox1=\\box0\\end ", //
            "");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> takes some kind of a box.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testAssign2() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "\\setbox1=\\copy0\\end ", //
            "");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> takes some kind of a box.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testAssign3() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "\\setbox1=\\lastbox\\end ", //
            "");
    }

    /**
     * <testcase primitive="\setbox">
     *  Test case checking that <tt>\setbox</tt> assigns without the =.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testAssign4() throws Exception {

        assertSuccess(//
            DEFINE_BRACES + "\\setbox1\\hbox{abc}--\\box1\\end ", //
            "--abc" + TERM);
    }

    //TODO implement more primitive specific test cases

}
