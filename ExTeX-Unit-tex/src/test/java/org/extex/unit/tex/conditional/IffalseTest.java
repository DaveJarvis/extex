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

package org.extex.unit.tex.conditional;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\iffalse</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IffalseTest extends ConditionalTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IffalseTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public IffalseTest(String arg) {

        super(arg, "iffalse", "\\else");
    }

    /**
     * <testcase primitive="\iffalse"> Test case checking that <tt>\iffalse</tt>
     * selects the else branch. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\iffalse a\\else b\\fi\\end",
            // --- output channel ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\iffalse"> Test case checking that <tt>\iffalse</tt>
     * selects nothing if the else branch is missing. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "x\\iffalse a\\fi x\\end",
            // --- output channel ---
            "xx" + TERM);
    }

    /**
     * <testcase primitive="\iffalse"> Test case checking that <tt>\iffalse</tt>
     * works for a deeply nestes invocation. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "x\\iffalse \\iffalse \\iffalse a\\fi\\fi\\fi x\\end",
            // --- output channel ---
            "xx" + TERM);
    }

}
