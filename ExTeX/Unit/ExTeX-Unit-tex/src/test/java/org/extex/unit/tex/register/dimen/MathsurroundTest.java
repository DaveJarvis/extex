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

package org.extex.unit.tex.register.dimen;

import org.extex.test.AbstractDimenRegisterTester;
import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \mathsurround}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MathsurroundTest extends AbstractDimenRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(MathsurroundTest.class);
    }


    public MathsurroundTest() {

        super("mathsurround", "", "0.0pt");
    }

    /**
     * Test case checking that {@code \mathsurround} is inserted before and after math
* 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                    + "\\mathsurround=10pt" + "o$x$o \\end",
            // --- output channel ---
            "\\vbox(8.0pt+0.0pt)x3000.0pt\n"
                    + ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" + "..o\n"
                    + "..\\mathon, surrounded 10.0pt\n" + "..x\n"
                    + "..\\mathoff, surrounded 10.0pt\n" + "..o\n");
    }

    /**
     *  Test case checking that {@code \mathsurround} is inserted
     * before and after math &ndash; even when set within the math environment.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                    + "o$x\\mathsurround=10pt$o \\end",
            // --- output channel ---
            "\\vbox(8.0pt+0.0pt)x3000.0pt\n"
                    + ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" + "..o\n"
                    + "..\\mathon, surrounded 10.0pt\n" + "..x\n"
                    + "..\\mathoff, surrounded 10.0pt\n" + "..o\n");
    }

}
