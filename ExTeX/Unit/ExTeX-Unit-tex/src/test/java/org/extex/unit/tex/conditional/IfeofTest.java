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

package org.extex.unit.tex.conditional;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \ifeof}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class IfeofTest extends ConditionalTester {

    /**
     * The field {@code EMPTY_TEX} contains the location of an empty file.
     */
    private static final String EMPTY_TEX =
            "../ExTeX-Unit-tex/src/test/resources/tex/empty.tex";

    /**
     * The field {@code NON_EMPTY_TEX} contains the location of an non-empty
     * file.
     */
    private static final String NON_EMPTY_TEX =
            "../ExTeX-Unit-tex/src/test/resources/tex/read_data.tex";

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IfeofTest.class);
    }


    public IfeofTest() {

        super("ifeof", " 11");
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that {@code \ifeof}
     * needs an argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(// --- input code ---
            "\\ifeof ",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that {@code \ifeof}
     * needs a file descriptor (number).
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\ifeof x ",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that {@code \ifeof}
     * expands the then branch on an unused file descriptor.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifeof8 true\\else false\\fi \\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that {@code \ifeof}
     * expands the then branch on a newly opened and empty file descriptor.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\openin8 " + EMPTY_TEX + " "
                    + "\\ifeof8 true\\else false\\fi \\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that {@code \ifeof}
     * expands the then branch on a file descriptor for an empty file.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\openin8 " + EMPTY_TEX + " " 
                    + "\\read8 to \\x " 
                    + "\\ifeof8 true\\else false\\fi \\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that {@code \ifeof}
     * expands the else branch on a newly opened file descriptor for a non-empty
     * file.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess(// --- input code ---
            "\\openin8 " + NON_EMPTY_TEX + " " 
                    + "\\ifeof8 true\\else false\\fi \\end",
            // --- output channel ---
            "false" + TERM);
    }

    // TODO implement more primitive specific test cases

}
