/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.extex.count;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\integer</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IntegerTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IntegerTest.class);
    }


    public IntegerTest() {

        setPrimitive("integer");setArguments("\\x=123");setPrepare("123");
        setConfig("extex-test");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be used after \the and \showthe.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertOutput(// --- input code ---
            "\\integer\\x=123 \\showthe\\x" + "\\end",
            // --- output channel ---
            "> 123.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be assigned to a count register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertOutput(// --- input code ---
            "\\integer\\x=123 \\count0=\\x \\showthe\\count0" + "\\end",
            // --- output channel ---
            "> 123.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be assigned a new constant value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAssign1() throws Exception {

        assertOutput(// --- input code ---
            "\\integer\\x=123 \\x=456 \\showthe\\x" + "\\end",
            // --- output channel ---
            "> 456.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be assigned a new value from a count
     * register. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAssign2() throws Exception {

        assertOutput(// --- input code ---
            "\\count0=987 \\integer\\x=123 \\x=\\count0 \\showthe\\x" + "\\end",
            // --- output channel ---
            "> 987.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be advanced. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAdvance1() throws Exception {

        assertOutput(// --- input code ---
            "\\integer\\x=123 \\advance\\x5 \\showthe\\x" + "\\end",
            // --- output channel ---
            "> 128.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be multiplied. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMultiply1() throws Exception {

        assertOutput(// --- input code ---
            "\\integer\\x=123 \\multiply\\x2 \\showthe\\x" + "\\end",
            // --- output channel ---
            "> 246.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be divided. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDivide1() throws Exception {

        assertOutput(// --- input code ---
            "\\integer\\x=128 \\divide\\x 2 \\showthe\\x" + "\\end",
            // --- output channel ---
            "> 64.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer does not respect groups in assignment.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + "{\\integer\\x=123 {\\x=987} \\showthe\\x}"
                    + "\\end",
            // --- output channel ---
            "> 987.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer can be defined \global. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + "{\\global\\integer\\x=123 }\\showthe\\x" + "\\end",
            // --- output channel ---
            "> 123.\n", "");
    }

    /**
     * <testcase primitive="\integer"> Test case showing that the control
     * sequence defined with \integer is not defined outside it's primary group.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + "{\\integer\\x=123 }\\showthe\\x" + "\\end",
            // --- output channel ---
            "You can't use `the control sequence \\x' after \\showthe", "");
    }

    // TODO implement more primitive specific test cases
}
