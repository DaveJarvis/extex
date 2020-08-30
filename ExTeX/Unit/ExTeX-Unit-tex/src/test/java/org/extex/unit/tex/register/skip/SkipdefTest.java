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

package org.extex.unit.tex.register.skip;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \skipdef}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class SkipdefTest extends AbstractSkipRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(SkipdefTest.class);
    }


    public SkipdefTest() {

        super("x", "", "0.0pt", "\\skipdef\\x=42 ");
    }

    /**
     * <testcase primitive="\skipdef"> Test case checking that {@code \skipdef}
     * creates a skip assignable control sequence which is equivalent to the
     * {@code \skip}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\skipdef\\x=42 " + "\\skip42=123pt " + "\\the\\skip42 \\end",
            // --- output channel ---
            "123.0pt" + TERM);
    }

    /**
     * <testcase primitive="\skipdef"> Test case checking that {@code \skipdef}
     * respects a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup\\skipdef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\skipdef"> Test case checking that {@code \skipdef}
     * respects a group.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\skipdef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- output channel ---
            "0.0pt" + TERM);
    }

    /**
     * <testcase primitive="\skipdef"> Test case checking that {@code \skipdef}
     * respects {@code \globaldefs}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal10() throws Exception {

        assertSuccess(// --- input code ---
            "\\globaldefs=1\\begingroup\\skipdef\\x=42 \\x=123pt\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "123.0pt" + TERM);
    }

}
