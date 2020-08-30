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

package org.extex.unit.tex.file;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \read}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ReadTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * The constant {@code DATA_FILE} contains the name of the file to use
     * for testing.
     */
    private static final String DATA_FILE =
        "../ExTeX-Unit-tex/src/test/resources/tex/read_data.tex";

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(ReadTest.class);
    }

    public ReadTest() {
        setPrimitive( "read" );
        setArguments( "1 to \\x" );
        setPrepare( "\\openin1 " + DATA_FILE + " " );
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * from the terminal in batch mode leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTerm0() throws Exception {

        assertFailure(// --- input code ---
            "\\batchmode\\read 1 to \\x ",
            // --- error channel ---
            "*** (cannot \\read from terminal in nonstop modes)");
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * from the terminal in batch mode leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTerm1() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode\\read 1 to \\x ",
            // --- error channel ---
            "*** (cannot \\read from terminal in nonstop modes)");
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * from the terminal in batch mode leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTerm2() throws Exception {

        assertFailure(// --- input code ---
            "\\nonstopmode\\read 1 to \\x ",
            // --- error channel ---
            "*** (cannot \\read from terminal in nonstop modes)");
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * needs the {@code to} after the stream name.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr0() throws Exception {

        assertFailure(// --- input code ---
            "\\read 1",
            // --- error channel ---
            "Missing `to' inserted");
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * needs a control sequence to store the result in.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\read 1 to ",
            // --- error channel ---
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * can read the first line of the test file.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\openin1 " + DATA_FILE + " "
                    + "\\read 1 to \\x " + "\\x" + "\\end",
            // --- output channel ---
            "123xyz" + TERM);
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * honors the {@code \global} prefix by defining the macro globally.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\openin1 " + DATA_FILE + " "
                    + "{\\global\\read 1 to \\x}" + "\\x" + "\\end",
            // --- output channel ---
            "123xyz" + TERM);
    }

    /**
     * <testcase primitive="\read"> Test case checking that a {@code \read}
     * makes its definition locally to the current group per default.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\openin1 " + DATA_FILE + " "
                    + "{\\read 1 to \\x}" + "\\x" + "\\end",
            // --- output channel ---
            "Undefined control sequence \\x");
    }

    // TODO implement more primitive specific test cases

}
