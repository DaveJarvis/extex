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

package org.extex.unit.tex.typesetter;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \accent}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class AccentTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(AccentTest.class);
    }


    public AccentTest() {

        setPrimitive("accent");setArguments("13 a");setPrepare("");
    }

    /**
     * Test case checking that {@code \accent} does not work in math mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testAccentInMathMode() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_MATH + "$\\accent $\\end",
            // --- log message ---
            "Please use \\mathaccent for accents in math mode");
    }

    /**
     * Test case checking that {@code \accent} does not work in display math mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testAccentInDisplayMathMode() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_MATH + "$$\\accent ",
            // --- log message ---
            "Please use \\mathaccent for accents in math mode");
    }

}
