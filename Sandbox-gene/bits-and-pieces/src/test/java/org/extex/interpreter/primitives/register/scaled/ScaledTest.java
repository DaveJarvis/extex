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

package org.extex.interpreter.primitives.register.scaled;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\countdef</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ScaledTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(ScaledTest.class);
    }

    /**
     * Creates a new object.
     */
    public ScaledTest() {

        super("scaled", "\\x=0", "");
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=4.2 " + "\\the\\x \\end",
            // --- output channel ---
            "4.2" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=-4.2 " + "\\the\\x \\end",
            // --- output channel ---
            "-4.2" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=--4.2 " + "\\the\\x \\end",
            // --- output channel ---
            "4.2" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=4 " + "\\the\\x \\end",
            // --- output channel ---
            "4.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=.4 " + "\\the\\x \\end",
            // --- output channel ---
            "0.4" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=(4) " + "\\the\\x \\end",
            // --- output channel ---
            "4.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test7() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\count0=123" + "\\scaled\\x=\\count0 " + "\\the\\x \\end",
            // --- output channel ---
            "123.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test8() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\a=123 " + "\\scaled\\x=\\a " + "\\the\\x \\end",
            // --- output channel ---
            "123.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test9() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\a=123 " + "\\scaled\\x=-\\a " + "\\the\\x \\end",
            // --- output channel ---
            "-123.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\a=-123 " + "\\scaled\\x=-\\a " + "\\the\\x \\end",
            // --- output channel ---
            "123.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\a=123 " + "\\scaled\\x=\\a+1 " + "\\the\\x \\end",
            // --- output channel ---
            "+1 123.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\a=123 " + "\\scaled\\x=(\\a+1) " + "\\the\\x \\end",
            // --- output channel ---
            "124.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=(1+2*3) " + "\\the\\x \\end",
            // --- output channel ---
            "7.0" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test14() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=(1+2/3) " + "\\the\\x \\end",
            // --- output channel ---
            "1.66666" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test15() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=(1+2/3-2) " + "\\the\\x \\end",
            // --- output channel ---
            "-0.33334" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test16() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\scaled\\x=(1+2/(1+1+1)-2) " + "\\the\\x \\end",
            // --- output channel ---
            "-0.33334" + TERM);
    }

    /**
     * <testcase primitive="\scaled"> Test case checking that <tt>\scaled</tt>
     * creates a ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test17() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\count0=1 \\count1=2" + "\\scaled\\x=(\\count0/\\count1) "
                    + "\\the\\x \\end",
            // --- output channel ---
            "0.5" + TERM);
    }

}