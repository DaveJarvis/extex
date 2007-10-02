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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\hss</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class HssTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(HssTest.class);
    }

    /**
     * Constructor for HssTest.
     */
    public HssTest() {

        super("hss", "");
    }

    /**
     * <testcase> Test case showing that \hss expands to the width needed.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVbox1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\vbox to 12pt{a\\hss b} \\end",
            // --- error channel ---
            "a b\n\n" + TERM);
    }

    /**
     * <testcase> Test case showing that the natural width of \hss is 0pt.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHbox1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\hbox{a\\hss b} \\end",
            // --- error channel ---
            "ab" + TERM);
    }

    /**
     * <testcase> Test case checking that <tt>\hfi*</tt> is ignored at the
     * beginning of a paragraph. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnore1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\hss\\end ",
            // --- output channel ---
            "\\vbox(0.0pt+0.0pt)x3000.0pt\n" //
                    + ".\\hbox(0.0pt+0.0pt)x3000.0pt\n");
    }

    /**
     * <testcase> Test case checking that <tt>\hfi*</tt> is ignored at the
     * beginning of a paragraph. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnore2() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\font\\f cmr10 \\f\\hsize=100pt \\hss x\\end ",
            // --- output channel ---
            "\\vbox(4.30554pt+0.0pt)x100.0pt\n" + //
                    ".\\hbox(4.30554pt+0.0pt)x100.0pt\n" + //
                    "..x\n");
    }

    // TODO implement more primitive specific test cases

}
