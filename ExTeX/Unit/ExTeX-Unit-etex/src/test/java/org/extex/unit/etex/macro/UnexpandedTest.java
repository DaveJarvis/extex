/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.macro;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code &#005c;unexpanded}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class UnexpandedTest extends ExTeXLauncher {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(UnexpandedTest.class);
    }


    public UnexpandedTest() {

        setConfig("etex-test");
    }

    /**
     * <testcase primitive="&#005c;unexpanded"> Test case checking that
     * {@code &#005c;unexpanded} needs an argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unexpanded",
            // --- error channel ---
            "Unexpected end of file while processing \\unexpanded");
    }

    /**
     * <testcase primitive="&#005c;unexpanded"> Test case checking that
     * {@code &#005c;unexpanded} needs a group argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unexpanded a",
            // --- error channel ---
            "Missing `{' inserted");
    }

    /**
     * <testcase primitive="&#005c;unexpanded"> Test case checking that
     * {@code &#005c;unexpanded} sets the flag.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\unexpanded{abc}\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    // TODO implement more primitive specific test cases
}
