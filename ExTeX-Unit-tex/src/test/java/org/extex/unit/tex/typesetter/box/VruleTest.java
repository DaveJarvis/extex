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
 * This is a test suite for the primitive <tt>\vrule</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class VruleTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(VruleTest.class);
    }

    /**
     * Constructor for VruleTest.
     * 
     * @param arg the name
     */
    public VruleTest(String arg) {

        super(arg, "vrule", "");
    }

    /**
     * <testcase primitive="\vrule"> Test case checking that <tt>\vrule</tt>
     * switches to vertical mode and inserts a glue node with the given value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\vrule \\end ",
            // --- output channel ---
            "\\vbox(0.0pt+0.0pt)x0.4pt\n" + //
                    ".\\rule0.0pt+0.0ptx0.4pt\n");
    }

    /**
     * <testcase primitive="\vrule"> Test case checking that <tt>\vrule</tt>
     * switches to vertical mode and inserts a glue node with the given value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\vrule width 12pt\\end ",
            // --- output channel ---
            "\\vbox(0.0pt+0.0pt)x12.0pt\n" + //
                    ".\\rule0.0pt+0.0ptx12.0pt\n");
    }

    /**
     * <testcase primitive="\vrule"> Test case checking that <tt>\vrule</tt>
     * switches to vertical mode and inserts a glue node with the given value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\vrule height 12pt\\end ",
            // --- output channel ---
            "\\vbox(12.0pt+0.0pt)x0.4pt\n" + //
                    ".\\rule12.0pt+0.0ptx0.4pt\n");
    }

    /**
     * <testcase primitive="\vrule"> Test case checking that <tt>\vrule</tt>
     * switches to vertical mode and inserts a glue node with the given value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(showNodesProperties(),
        // --- input code ---
            "\\vrule depth 12pt\\end ",
            // --- output channel ---
            "\\vbox(0.0pt+12.0pt)x0.4pt\n" + //
                    ".\\rule0.0pt+12.0ptx0.4pt\n");
    }

    // TODO implement primitive specific test cases

}
