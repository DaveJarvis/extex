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

package org.extex.unit.tex.group;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \begingroup}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BegingroupTest extends NoFlagsPrimitiveTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(BegingroupTest.class);
    }


    public BegingroupTest() {

        setPrimitive("begingroup");setArguments("");setPrepare("");
    }

    /**
     * <testcase primitive="\begingroup"> Test case checking that a lonely
     * {@code \begingroup} leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnbalanced1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup",
            // --- log message ---
            "(\\end occurred inside a group at level 1)\n");
    }

    /**
     * <testcase primitive="\begingroup"> Test case checking that a group is ok.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroup1() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup abc\\endgroup",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\begingroup"> Test case checking that a group does
     * not destroy a count register.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroup2() throws Exception {

        assertSuccess(// --- input code ---
            "\\count0=123 \\begingroup \\the\\count0\\endgroup",
            // --- output channel ---
            "123" + TERM);
    }

    /**
     * <testcase primitive="\begingroup"> Test case checking that a group does
     * restore a count register after the end.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroup3() throws Exception {

        assertSuccess(// --- input code ---
            "\\count0=123 "
                    + "\\begingroup \\count0=456 \\the\\count0\\endgroup "
                    + "-\\the\\count0",
            // --- output channel ---
            "456-123" + TERM);
    }

    /**
     * <testcase primitive="\begingroup"> Test case checking that a group does
     * restore a count register after the end &ndash; across two levels of
     * grouping.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGroup4() throws Exception {

        assertSuccess(// --- input code ---
            "\\count0=123 " + "\\begingroup \\count0=456 \\the\\count0 "
                    + "\\begingroup \\count0=789 -\\the\\count0\\endgroup "
                    + "-\\the\\count0\\endgroup" + "-\\the\\count0",
            // --- output channel ---
            "456-789-456-123" + TERM);
    }

    /**
     * Test case checking that an open block leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOpenBlock() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup" + "\\end ",
            // --- log message ---
            "(\\end occurred inside a group at level 1)\n");
    }

    /**
     * Test case checking that two open blocks leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOpenBlock2() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup" + "\\begingroup" + "\\end ",
            // --- log message ---
            "(\\end occurred inside a group at level 2)\n");
    }

}
