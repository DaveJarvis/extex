/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp;

import org.extex.test.ExTeXLauncher;

/**
 * This is a test suite for the primitive <tt>\pushocplist</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class PushocplistTest extends ExTeXLauncher {

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(PushocplistTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public PushocplistTest(String arg) {

        super(arg);
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\pushocp"> Test case checking that <tt>\pushocp</tt>
     * needs an active ocp list. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\pushocplist \\relax",
            // --- output channel ---
            "Missing ocp list identifier");
    }

    // TODO implement more primitive specific test cases
}