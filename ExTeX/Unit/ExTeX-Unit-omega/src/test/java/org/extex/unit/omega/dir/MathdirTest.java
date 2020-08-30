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

package org.extex.unit.omega.dir;

import static org.junit.Assert.assertEquals;

import org.extex.interpreter.Interpreter;
import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code mathdir}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MathdirTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * The command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(MathdirTest.class);
    }


    public MathdirTest() {

        setPrimitive("mathdir");setArguments(" LRL");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\mathdir"> Test case checking that {@code \mathdir}
     * sets the math direction in the context. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        Interpreter in = assertSuccess(// --- input code ---
            "\\mathdir LRL" + "\\end",
            // --- output channel ---
            "");
        assertEquals("LRL", in.getContext().getTypesettingContext()
            .getDirection().toString());
        // TODO gene: correct???
    }

    /**
     * <testcase primitive="\mathdir"> Test case checking that {@code \mathdir}
     * needs a direction as argument. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\mathdir" + "\\end",
            // --- output channel ---
            "Bad direction");
    }

    /**
     * <testcase primitive="\mathdir"> Test case checking that {@code \mathdir}
     * needs an argument. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError0() throws Exception {

        assertFailure(// --- input code ---
            "\\mathdir",
            // --- output channel ---
            "Unexpected end of file");
    }

}
