/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp.util;

import org.extex.test.ExTeXLauncher;

/**
 * This is a test suite for the primitives definex with <tt>\ocp</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class OcpTest extends ExTeXLauncher {

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(OcpTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public OcpTest(String arg) {

        super(arg);
        setConfig("omega-test");
    }

    /**
     * <testcase> Test case checking that an OCP needs ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testErrEof1() throws Exception {

        assertFailure(// --- input code ---
            "\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy " + "\\x ",
            // --- output channel ---
            "To use ocps, use the \\pushocplist primitive");
    }

    /**
     * <testcase> Test case checking that an OCP is showable. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertFailure(// --- input code ---
            "\\ocp\\x=../ExTeX-Unit-omega/src/test/resources/destroy " + "\\show\\x ",
            // --- output channel ---
            "> \\x=select ocp ../ExTeX-Unit-omega/src/test/resources/destroy.");
    }

    // TODO implement more primitive specific test cases
}
