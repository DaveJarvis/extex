/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.conditional.analyze;

import org.extex.test.count.AbstractReadonlyCountRegisterTester;

/**
 * This is a test suite for the primitive <tt>\currentiflevel</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class CurrentiflevelTest extends AbstractReadonlyCountRegisterTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(CurrentiflevelTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public CurrentiflevelTest(String arg) {

        super(arg, "currentiflevel", "0");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\currentiflevel"> Test case checking that
     * <tt>\currentiflevel</tt> returns 0 outside any conditional. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        assertSuccess(// --- input code ---
            "\\the\\currentiflevel\\end",
            // --- log message ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\currentiflevel"> Test case checking that
     * <tt>\currentiflevel</tt> returns 0 outside any conditional. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test0b() throws Exception {

        assertSuccess(// --- input code ---
            "\\iffalse abc\\fi\\the\\currentiflevel\\end",
            // --- log message ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\currentiflevel"> Test case checking that
     * <tt>\currentiflevel</tt> is 1 inside a single conditional. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\iftrue\\the\\currentiflevel \\fi\\end",
            // --- log message ---
            "1" + TERM);
    }

    /**
     * <testcase primitive="\currentiflevel"> Test case checking that
     * <tt>\currentiflevel</tt> is 2 inside two conditionals. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\iftrue\\iftrue\\the\\currentiflevel \\fi\\fi\\end",
            // --- log message ---
            "2" + TERM);
    }

}
