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

package org.extex.unit.tex.conditional;

/**
 * This is a test suite for the primitive <tt>\ifcat</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class IfcatTest extends ConditionalTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(IfcatTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public IfcatTest(String arg) {

        super(arg, "ifcat", " xx");
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * needs an argument </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\ifcat",
            // --- output channel ---
            "Unexpected end of file while processing \\ifcat");
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * needs two arguments </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            "\\ifcat \\relax",
            // --- output channel ---
            "Unexpected end of file while processing \\ifcat");
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * detects two identical letters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLetter1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcat AA a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * detects two different letters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLetter2() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcat AB a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies a letter and an other character as different. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLetter3() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcat A1 a\\else b\\fi \\end",
            // --- output channel ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies a letter and a control sequence as different. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testLetter4() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcat A\\relax a\\else b\\fi \\end",
            // --- output channel ---
            "b" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two identical control sequences as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testCs1() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcat \\relax\\relax a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two different control sequences as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testCs2() throws Exception {

        assertSuccess(// --- input code ---
            "\\ifcat \\abc\\relax a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two identical other characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testOther1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat 11 a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two different other characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testOther2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat 12 a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two math shift characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMath1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat $$ a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two subscript characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSub1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat __ a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two tab mark characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testSuper1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat && a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two open group characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testOpen1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat {{ a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two close group characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testClose1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat }} a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\ifcat"> Test case checking that <tt>\ifcat</tt>
     * classifies two macro parameter characters as equal. </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testMacro1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES + "\\ifcat ## a\\else b\\fi \\end",
            // --- output channel ---
            "a" + TERM);
    }

    // TODO implement more primitive specific test cases
}
