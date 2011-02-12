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
 * This is a test suite for the primitive <tt>\vbox</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class VboxTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(VboxTest.class);
    }

    /**
     * Constructor for VboxTest.
     */
    public VboxTest() {

        super("vbox", "{}");
    }

    /**
     * <testcase primitive="\vbox"> Test case checking that a vbox needs an
     * argument in braces. Nothing at all is not enough </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\vbox ",
            // --- output channel ---
            "Unexpected end of file while processing \\vbox");
    }

    /**
     * <testcase primitive="\vbox"> Test case checking that a vbox needs an
     * argument in braces. an opening brace is not enough. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertOutput(// --- input code ---
            DEFINE_BRACES + "\\vbox { ",
            // --- output channel ---
            "(\\end occurred inside a group at level 1)\n", TERM);
    }

    /**
     * <testcase primitive="\vbox"> Test case checking that a vbox needs an
     * argument in braces. Anything else than an opening brace is not
     * sufficient. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\vbox x ",
            // --- output channel ---
            "Missing `{' inserted");
    }

    /**
     * <testcase primitive="\vbox"> Test case checking that a vbox with fixed
     * width containing "abc" in font cmtt12 has the correct height and depth.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTo1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt"
                    + "\\setbox1=\\vbox to 20pt{abc} "
                    + "\\the\\ht1 : \\the\\dp1" + "\\end",
            // --- output channel ---
            "20.0pt: 0.0pt" + TERM); // checked with TeX
    }

    /**
     * <testcase primitive="\vbox"> Test case checking that a spread vbox
     * containing "abc" in font cmtt12 has the correct height and depth.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSpread1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt"
                    + "\\setbox1=\\vbox spread 2pt{abc} "
                    + "\\the\\ht1 : \\the\\dp1" + "\\end",
            // --- output channel ---
            "9.33333pt: 0.0pt" + TERM); // checked with TeX
    }

    /**
     * <testcase primitive="\vbox"> Test case checking that a vbox containing
     * "abcd" produces the desired nodes. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt " + "x\\vbox {abcd} "
                    + "\\end",
            // --- output channel ---
            "\\vbox(7.33333pt+0.0pt)x3000.0pt\n" //
                    + ".\\hbox(7.33333pt+0.0pt)x3000.0pt\n" //
                    + "..x\n" //
                    + "..\\vbox(7.33333pt+0.0pt)x3000.0pt\n" //
                    + "...\\vbox(7.33333pt+0.0pt)x3000.0pt\n" //
                    + "....\\hbox(7.33333pt+0.0pt)x3000.0pt\n" //
                    + ".....a\n" //
                    + ".....b\n" //
                    + ".....c\n" //
                    + ".....d\n");
    }

    // TODO implement more primitive specific test cases

}
