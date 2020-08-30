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

package org.extex.unit.tex.typesetter.undo;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code &#x5c;unkern}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class UnkernTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(UnkernTest.class);
    }


    public UnkernTest() {

        setPrimitive("unkern");setArguments("");setPrepare("\\kern1pt");
    }

    /**
     * <testcase primitive="&#x5c;unkern"> Test case checking that
     * {@code &#x5c;unkern} need some node in the current list.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(
        // --- input code ---
            "\\unkern\\end ",
            // --- error channel ---
            "You can't use `\\unkern' in vertical mode");
    }

    /**
     * <testcase primitive="&#x5c;unkern"> Test case checking that
     * {@code &#x5c;unkern} need some node in the current list.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr2() throws Exception {

        assertFailure(
        // --- input code ---
            "\\kern1pt\\unkern\\unkern\\end ",
            // --- error channel ---
            "You can't use `\\unkern' in vertical mode");
    }

    /**
     * <testcase primitive="&#x5c;unkern"> Test case checking that
     * {@code &#x5c;unkern} does not touch a char node at the end of the
     * current list.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "a\\unkern\\end ",
            // --- output channel ---
            "" +
                    "\\vbox(8.0pt+0.0pt)x3000.0pt\n" +
                    ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" +
                    "..a\n");
    }

    /**
     * <testcase primitive="&#x5c;unkern"> Test case checking that
     * {@code &#x5c;unkern} takes the last kern from the current list.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "a\\kern1pt\\unkern\\end ",
            // --- output channel ---
            "" +
                    "\\vbox(8.0pt+0.0pt)x3000.0pt\n" +
                    ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" +
                    "..a\n");
    }

    // TODO implement more primitive specific test cases
}
