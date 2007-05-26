/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.conditional;

/**
 * This is a test suite for the primitive <tt>\ifeof</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IfeofTest extends ConditionalTester {

    /**
     * The field <tt>EMPTY_TEX</tt> contains the location of an empty file.
     */
    private static final String EMPTY_TEX =
            "../ExTeX-Unit-tex/src/test/tex/empty.tex";

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(IfeofTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public IfeofTest(String arg) {

        super(arg, "ifeof", " 11");
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that <tt>\ifeof</tt>
     * ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testErr0() throws Exception {

        assertFailure(// --- input code ---
            "\\ifeof ",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that <tt>\ifeof</tt>
     * ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\ifeof x true\\else false\\fi \\end",
            // --- output channel ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that <tt>\ifeof</tt>
     * ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifeof8 true\\else false\\fi \\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\ifeof"> Test case checking that <tt>\ifeof</tt>
     * ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\openin8 " + EMPTY_TEX + " "
                    + "\\ifeof8 true\\else false\\fi \\end",
            // --- output channel ---
            "true" + TERM);
    }

    // TODO implement the primitive specific test cases

}
