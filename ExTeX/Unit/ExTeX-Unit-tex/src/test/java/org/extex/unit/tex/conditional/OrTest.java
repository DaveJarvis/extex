/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\or</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class OrTest extends ExTeXLauncher {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(OrTest.class);
    }


    public OrTest() {

    }

    /**
     * <testcase primitive="\or"> Test case checking that a lonely <tt>\or</tt>
     * leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertFailure(// --- input code ---
            "\\or",
            // --- log message ---
            "Extra \\or");
    }

    /**
     * <testcase primitive="\or"> Test case checking that \or terminates a case.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcase\\count0 a\\or b\\fi\\end",
            // --- output ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\or"> Test case checking that a lonely <tt>\or</tt>
     * leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertFailure(// --- input code ---
            "\\ifcase1\\or\\fi\\or",
            // --- log message ---
            "Extra \\or");
    }
}
