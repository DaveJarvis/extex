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

package org.extex.unit.tex.string;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \lowercase}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class LowercaseTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LowercaseTest.class);
    }


    public LowercaseTest() {

        setPrimitive("lowercase");setArguments("{abc}");setPrepare("");
    }

    /**
     * <testcase primitive="\lowercase"> Test case checking that
     * {@code \lowercase} throws an error on eof.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEOF1() throws Exception {

        assertFailure(// --- input code ---
            "\\lowercase",
            // --- log message ---
            "File ended while scanning text of \\lowercase");
    }

    /**
     * <testcase primitive="\lowercase"> Test case checking that
     * {@code \lowercase} is invariant on lowercase letters.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\lowercase{abc}\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\lowercase"> Test case checking that
     * {@code \lowercase} translates uppercase letters.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\lowercase{ABC}\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\lowercase"> Test case checking that
     * {@code \lowercase} translates mixed letters.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\lowercase{aBc}\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\lowercase"> Test case checking that
     * {@code \lowercase} respects lccode.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\lccode`B=`1 " + "\\lowercase{ABC}\\end",
            // --- output channel ---
            "a1c" + TERM);
    }

}
