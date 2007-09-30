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

package org.extex.unit.tex.register.count;

import org.extex.test.count.AbstractCountRegisterTester;

/**
 * This is a test suite for the primitive <tt>\tracinglostchars</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class TracinglostcharsTest extends AbstractCountRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(TracinglostcharsTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public TracinglostcharsTest(String arg) {

        super(arg, "tracinglostchars", "", "0");
    }

    /**
     * <testcase primitive="\tracinglostchars"> Test case checking that without
     * <tt>\tracinglostchars</tt> a lost character is silently dropped.
     * <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        assertSuccess(// --- input code ---
            "\\nullfont a \\end",
            // --- output stream ---
            "\n");
    }

    /**
     * <testcase primitive="\tracinglostchars"> Test case checking that
     * <tt>\tracinglostchars</tt> on an non-existing character does produce an
     * error message. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test01() throws Exception {

        assertOutput(// --- input code ---
            "\\nullfont\\tracinglostchars=1 a \\end",
            // --- log message ---
            "Missing character: There is no a in font nullfont!\n",
            // --- output stream ---
            "\n");
    }

    /**
     * <testcase primitive="\tracinglostchars"> Test case checking that
     * <tt>\tracinglostchars</tt> on an existing character does not produce a
     * message. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertOutput(// --- input code ---
            "\\tracinglostchars=1 a \\end",
            // --- log message ---
            "",
            // --- output stream ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\tracinglostchars"> Test case checking that
     * <tt>\tracinglostchars</tt> on a non-existing character appearing later
     * does produce a message. <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test02() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + "\\nullfont\\tracinglostchars=1\\hbox{a}\\end",
            // --- log message ---
            "Missing character: There is no a in font nullfont!\n",
            // --- output stream ---
            TERM);
    }

    /**
     * <testcase primitive="\tracinglostchars"> Test case checking that
     * <tt>\tracinglostchars</tt> ... <testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + "\\tracinglostchars=1\\hbox{a}\\end",
            // --- log message ---
            "",
            // --- output stream ---
            "a" + TERM);
    }

    // TODO implement more primitive specific test cases (lost chars in math
    // mode, etc)

}
