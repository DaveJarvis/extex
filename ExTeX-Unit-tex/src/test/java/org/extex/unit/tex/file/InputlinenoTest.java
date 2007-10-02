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

package org.extex.unit.tex.file;

import org.extex.test.count.AbstractReadonlyCountRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\inputlineno</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class InputlinenoTest extends AbstractReadonlyCountRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(InputlinenoTest.class);
    }

    /**
     * Creates a new object.
     */
    public InputlinenoTest() {

        super("inputlineno", "1");
    }

    /**
     * <testcase> Test case showing that the primitive is applicable for
     * \showthe. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterShowthe1() throws Exception {

        assertFailure(// --- input code ---
            "\\showthe\\inputlineno\\end",
            // --- output channel ---
            "> 1.\n");
    }

    /**
     * <testcase> Test case showing that the primitive is applicable for \the.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterThe1() throws Exception {

        assertSuccess(// --- input code ---
            "\\the\\inputlineno\\end",
            // --- output channel ---
            "1" + TERM);
    }

    /**
     * <testcase> Test case showing that an assignment of a constant 123 works
     * when using an equal sign after the primitive name. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountRegisterAssign1() throws Exception {

        assertSuccess(// --- input code ---
            "\\count0=\\inputlineno \\the\\count0\\end",
            // --- output channel ---
            "1" + TERM);
    }

    // TODO implement the primitive specific test cases

}
