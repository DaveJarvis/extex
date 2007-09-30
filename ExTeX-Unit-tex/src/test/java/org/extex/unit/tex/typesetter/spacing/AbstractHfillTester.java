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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.test.NoFlagsPrimitiveTester;

/**
 * This is a test suite for horizontal filling primitives.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public abstract class AbstractHfillTester extends NoFlagsPrimitiveTester {

    /**
     * The field <tt>invocation</tt> contains the concatenation of primitive
     * name and arguments.
     */
    private String invocation;

    /**
     * The field <tt>prepare</tt> contains the preparation code.
     */
    private String prepare = DEFINE_BRACES;

    /**
     * Constructor for HfillTest.
     * 
     * @param arg the name
     * @param primitive the name of the primitive
     * @param args the arguments for the invocation
     */
    public AbstractHfillTester(String arg, String primitive, String args) {

        super(arg, primitive, args);
        this.invocation = primitive + args;
    }

    /**
     * <testcase> Test case showing that the primitive in vertical mode appears
     * like a space. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testVbox1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\vbox to 12pt{a\\" + invocation + " b} \\end",
            // --- error channel ---
            "a b\n\n" + TERM);
    }

    /**
     * <testcase> Test case showing that the primitive i horizontal mode has a
     * natural width of 0pt. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testHbox1() throws Exception {

        assertSuccess(// --- input code ---
            prepare + "\\hbox{a\\" + invocation + " b} \\end",
            // --- error channel ---
            "ab" + TERM);
    }


    /**
     * <testcase> Test case checking that <tt>\hfi*</tt> is ignored at the
     * beginning of a paragraph. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testIgnore1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\" + invocation + "\\end ",
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
    public void testIgnore2() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\font\\f cmr10 \\f\\hsize=100pt \\" + invocation + " x\\end ",
            // --- output channel ---
            "\\vbox(4.30554pt+0.0pt)x100.0pt\n" + //
                    ".\\hbox(4.30554pt+0.0pt)x100.0pt\n" + //
                    "..x\n");
    }

    // TODO implement more primitive specific test cases
}
