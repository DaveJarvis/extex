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

package org.extex.unit.tex.typesetter.box;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \vtop}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class VtopTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(VtopTest.class);
    }


    public VtopTest() {

        setPrimitive("vtop");setArguments("{}");setPrepare("");
    }

    /**
     * <testcase primitive="\vtop"> Test case checking that {@code \vtop} ...
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            DEFINE_BRACES + "\\vtop{abc} ",
            // --- output channel ---
            "\\vbox(8.0pt+0.0pt)x3000.0pt\n"
                    + ".\\vbox(8.0pt+0.0pt)x3000.0pt\n"
                    + "..\\vbox(8.0pt+0.0pt)x3000.0pt\n"
                    + "...\\hbox(8.0pt+0.0pt)x3000.0pt\n"
                    + "....a\n"
                    + "....b\n"
                    + "....c\n"
        );
    }

    // TODO implement primitive specific test cases

}
