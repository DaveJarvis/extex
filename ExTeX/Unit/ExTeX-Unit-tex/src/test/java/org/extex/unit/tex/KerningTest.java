/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the kerning.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4527 $
 */
public class KerningTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(KerningTest.class);
    }


    public KerningTest() {

    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAVAV() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\font\\f=cmr10 \\f " + "AVA",
            // --- output channel ---
            "\\vbox(6.83331pt+0.0pt)x3000.0pt\n" //
                    + ".\\hbox(6.83331pt+0.0pt)x3000.0pt\n" //
                    + "..A\n" //
                    + "..\\kern -1.11113pt\n" //
                    + "..V\n" //
                    + "..\\kern -1.11113pt\n" //
                    + "..A\n");
    }

    /**
     * <testcase> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testXyz() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\font\\f=cmr10 \\f " + "xyz",
            // --- output channel ---
            "\\vbox(4.30554pt+1.94444pt)x3000.0pt\n" //
                    + ".\\hbox(4.30554pt+1.94444pt)x3000.0pt\n" //
                    + "..x\n" //
                    + "..y\n" //
                    + "..z\n");
    }

}
