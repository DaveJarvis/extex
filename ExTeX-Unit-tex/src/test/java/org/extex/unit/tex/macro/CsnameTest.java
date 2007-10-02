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

package org.extex.unit.tex.macro;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\csname</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class CsnameTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public CsnameTest() {

        super();
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the normal
     * operation is performed on letter inputs only. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetters1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\abc{-a-b-c-}"
                    + "\\csname abc\\endcsname\\end",
            // --- output channel ---
            "-a-b-c-" + TERM);
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the normal
     * operation is performed on letter inputs only and white-space is ignored.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLetters2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\abc{-a-b-c-}"
                    + "\\csname a b  c\\endcsname\\end",
            // --- output channel ---
            "-a-b-c-" + TERM);
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the primitive
     * \string can be used to insert special characters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testString1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\csname \\string\\par \\endcsname\\end",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the normal
     * operation is performed on letter and digit inputs only. Undefined control
     * sequences are treated as <tt>\relax</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMixed1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\def\\abc{-a-b-c-}"
                    + "\\csname abc 123\\endcsname\\end",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the normal
     * operation is performed on letter and digit inputs only. Undefined control
     * sequences are treated as <tt>\relax</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMixed2() throws Exception {

        assertSuccess(// --- input code ---
            "\\catcode`{=1" + "\\catcode`}=2" + "\\catcode`#=6"
                    + "\\def\\abc{-a-b-c-}"
                    + "\\csname abc # 123\\endcsname\\end",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the normal
     * operation is performed on letter and digit inputs only. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\csname abc \\par\\endcsname\\end",
            // --- log message ---
            "Missing \\endcsname inserted");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that the normal
     * operation is performed on letter and digit inputs only. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(// --- input code ---
            "\\csname abc \\relax\\endcsname\\end",
            // --- log message ---
            "Missing \\endcsname inserted");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that <tt>\csname</tt>
     * returns something equivalent to <tt>\relax</tt> if nor defined
     * otherwise. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageErr5() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES
                    + "\\message{a\\csname xyz \\endcsname b}" + "\\end ",
            // --- log message ---
            "a\\xyz b");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that <tt>\csname</tt>
     * complains an undefined control sequence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndef1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\csname \\xxx ",
            // --- log message ---
            "Undefined control sequence \\xxx");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that <tt>\csname</tt>
     * complains a missing \endcsname. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\csname jobname ",
            // --- log message ---
            "Unexpected end of file");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that eof is recognized.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEOF1() throws Exception {

        assertFailure(// --- input code ---
            "\\expandafter\\meaning\\csname ",
            // --- log message ---
            "Unexpected end of file");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that \csname creates a
     * defined control sequence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand0() throws Exception {

        assertOutput(
        // --- input code ---
            DEFINE_BRACES
                    + "\\expandafter\\let \\csname abc\\expandafter\\endcsname\n"
                    + "   \\csname cba\\endcsname\n"
                    + "\\message{\\csname abc\\endcsname}\n" + "\\end",
            // --- log channel ---
            "\\abc",
            // --- output channel ---
            "");
    }

    /**
     * <testcase primitive="\csname"> Test case checking that non-letters can be
     * embedded in the defined control sequence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testExpand1() throws Exception {

        assertOutput(
        // --- input code ---
            DEFINE_BRACES
                    + "\\expandafter\\def\\csname a-cba\\endcsname{A B C}\n"
                    + "\\expandafter\\let \\csname a-abc\\expandafter\\endcsname\n"
                    + "   \\csname a-cba\\endcsname\n"
                    + "\\message{\\csname a-abc\\endcsname}\n" + "\\end",
            // --- log channel ---
            "A B C",
            // --- output channel ---
            "");
    }

}
