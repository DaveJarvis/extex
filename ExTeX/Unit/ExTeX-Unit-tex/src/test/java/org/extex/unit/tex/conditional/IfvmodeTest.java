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
 * This is a test suite for the primitive {@code \ifvmode}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class IfvmodeTest extends ConditionalTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IfvmodeTest.class);
    }


    public IfvmodeTest() {

        super("ifvmode", "");
    }

    /**
     * <testcase primitive="\ifvmode"> Test case checking that {@code \ifvmode}
     * is true initially. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifvmode a\\else b\\fi\\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifvmode"> Test case checking that {@code \ifvmode}
     * is false when in a paragraph. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "x\\ifvmode a\\else b\\fi x\\end",
            // --- output channel ---
            "xbx" + TERM);
    }

    /**
     * <testcase primitive="\ifvmode"> Test case checking that {@code \ifvmode}
     * is true in a vbox. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\catcode`{=1 " + "\\catcode`}=2 "
                    + "\\vbox{\\ifvmode a\\else b\\fi}\\end",
            // --- output channel ---
            "a\n\n" + TERM);
    }

    /**
     * <testcase primitive="\ifvmode"> Test case checking that {@code \ifvmode}
     * is false in an hbox. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess(// --- input code ---
            "\\catcode`{=1 " + "\\catcode`}=2 "
                    + "\\hbox{\\ifvmode a\\else b\\fi}\\end",
            // --- output channel ---
            "b" + TERM);
    }

}
