/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional;

import org.extex.unit.tex.conditional.ConditionalTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\ifdefined</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IfdefinedTest extends ConditionalTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(IfdefinedTest.class);
    }

    /**
     * Creates a new object.
     */
    public IfdefinedTest() {

        super("ifdefined", "\\relax");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\ifdefined"> Test case checking that
     * <tt>\ifdefined</tt> on \relax expands the then branch. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifdefined \\relax a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifdefined"> Test case checking that
     * <tt>\ifdefined</tt> on \par expands the then branch. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifdefined \\par a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifdefined"> Test case checking that
     * <tt>\ifdefined</tt> on an undefined control sequence expands the else
     * branch. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifdefined \\x a\\else b\\fi \\end",
            // --- output channel ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\ifdefined"> Test case checking that
     * <tt>\ifdefined</tt> on a defined macro expands the then branch.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_BRACES + "\\def\\x{}\\ifdefined \\x a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    // TODO implement more primitive specific test cases
}
