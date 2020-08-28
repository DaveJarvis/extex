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

package org.extex.unit.tex.register.toks;

import org.extex.test.toks.AbstractToksRegisterTester;
import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\everymath</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class EverymathTest extends AbstractToksRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(EverymathTest.class);
    }


    public EverymathTest() {

        super("everymath", "", "");
    }

    /**
     * <testcase primitive="\everymath"> Test case showing that the token is
     * absorbed if no math happens. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + DEFINE_MATH + "\\everymath{x}BC" + "\\end",
            // --- output channel ---
            "BC" + TERM);
    }

    /**
     * <testcase primitive="\everymath"> Test case showing that the token is
     * absorbed if display math happens. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_BRACES + DEFINE_MATH
                    + "\\everymath{x}B$$  $$C" + "\\end",
            // --- output channel ---
            "BC" + TERM);
    }

    /**
     * <testcase primitive="\everymath"> Test case showing that the token is
     * inserted if inline math happens. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_BRACES + DEFINE_MATH
                    + "\\everymath{x}BC$ z $" + "\\end",
            // --- output channel ---
            "BCxz" + TERM);
    }

    // TODO implement more primitive specific test cases
}
