/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.interaction;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\interactionmode</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InteractionmodeTest extends ExTeXLauncher {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(InteractionmodeTest.class);
    }

    /**
     * Constructor for RelaxTest.
     */
    public InteractionmodeTest() {

        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that batch
     * mode is reported as 0. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertSuccess(// --- input code ---
            "\\batchmode\\count0=\\interactionmode" + " \\the\\count0 \\end",
            // --- output channel ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that non-stop
     * mode is reported as 1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\nonstopmode\\count0=\\interactionmode" + " \\the\\count0 \\end",
            // --- output channel ---
            "1" + TERM);
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that scroll
     * mode is reported as 2. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\scrollmode\\count0=\\interactionmode" + " \\the\\count0 \\end",
            // --- output channel ---
            "2" + TERM);
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that error
     * stop mode is reported as 3. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\errorstopmode\\count0=\\interactionmode"
                    + " \\the\\count0 \\end",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that
     * <tt>\interactionmode</tt> can be used to set the interaction mode.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet1() throws Exception {

        assertSuccess(// --- input code ---
            "\\interactionmode=3 \\count0=\\interactionmode"
                    + " \\the\\count0 \\end",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that
     * <tt>\interactionmode</tt> can be used to set the interaction mode.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet2() throws Exception {

        assertSuccess(// --- input code ---
            "\\interactionmode 3 \\count0=\\interactionmode"
                    + " \\the\\count0 \\end",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that
     * <tt>\interactionmode</tt> does not accept 4. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSetErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\interactionmode=4 ",
            // --- log message ---
            "Bad interaction mode (4)");
    }

    /**
     * <testcase primitive="\interactionmode"> Test case checking that
     * <tt>\interactionmode</tt> does not accept -1. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSetErr2() throws Exception {

        assertFailure(// --- input code ---
            "\\interactionmode=-1 ",
            // --- log message ---
            "Bad interaction mode (-1)");
    }

}
