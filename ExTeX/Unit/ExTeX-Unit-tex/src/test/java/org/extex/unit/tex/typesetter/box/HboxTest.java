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

package org.extex.unit.tex.typesetter.box;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\hbox</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class HboxTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(HboxTest.class);
    }


    public HboxTest() {

        setPrimitive("hbox");setArguments("{} ");setPrepare("");
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that <tt>\hbox</tt> on
     * the empty box works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty1() throws Exception {

        assertSuccess(DEFINE_BRACES + "\\hbox{}\\end",

            "" + TERM);
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that <tt>\hbox</tt> on a
     * non-empty box works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(DEFINE_BRACES + "\\hbox{abc}\\end",

            "abc" + TERM);
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that a missing left brace
     * directly after the macro token leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissingBrace1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "" + "\\hbox a" + "\\end ",
            // --- log message ---
            "Missing `{' inserted");
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that a missing left brace
     * after a "to" specification leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissingBrace2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\hbox to 2pt a" + "\\end ",
            // --- log message ---
            "Missing `{' inserted");
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that an outer macro in
     * the preamble leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\hbox ",
            // --- log message ---
            "Unexpected end of file while processing \\hbox");
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that a correct hbox
     * passes its contents to the typesetter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testHbox1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt" + "\\hbox{abc}"
                    + "\\end ",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that an outer macro in
     * the preamble leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHbox2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\hbox{}",
            // --- output channel ---
            "" + TERM);
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHbox3() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\hbox{abc}",
            // --- output channel ---
            "abc" + TERM);
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that a hbox containing
     * "abc" in font cmtt12 has the width 18.52501pt. This value has been
     * computed with TeX. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testHbox4() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt"
                    + "\\setbox1=\\hbox{abc} " + "\\the\\wd1 " + "\\end",
            // --- output channel ---
            "18.52501pt" + TERM); // checked wih TeX
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that a hbox containing
     * "abc" ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testHboxTo1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt"
                    + "\\setbox1=\\hbox to 20pt{abc} " + "\\the\\wd1 "
                    + "\\end",
            // --- output channel ---
            "20.0pt" + TERM); // checked with TeX
    }

    /**
     * <testcase primitive="\hbox"> Test case checking that a hbox containing
     * "abc" ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testHboxSpread1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\font\\fnt cmtt12 \\fnt"
                    + "\\setbox1=\\hbox spread 2pt{abc} " + "\\the\\wd1 "
                    + "\\end",
            // --- output channel ---
            "20.52501pt" + TERM); // checked with TeX
    }

    // TODO implement primitive specific test cases
}
