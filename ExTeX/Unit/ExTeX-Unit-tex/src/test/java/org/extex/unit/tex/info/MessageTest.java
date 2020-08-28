/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\message</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class MessageTest extends NoFlagsPrimitiveTester {


    public MessageTest() {

        setPrimitive("message");setArguments("{}");setPrepare("");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message prints
     * its plain argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessage1() throws Exception {

        assertFailure(// --- input code ---
            "\\errorstopmode" + DEFINE_BRACES + "\\message{Hello world!}"
                    + "\\end ",
            // --- log message ---
            "Hello world!");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message expands
     * macros in the argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessage10() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES + "\\def\\x{abc}" + "\\message{\\x}"
                    + "\\end ",
            // --- log message ---
            "abc");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message prints
     * its plain argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessage2() throws Exception {

        assertFailure(// --- input code ---
            "\\batchmode" + DEFINE_BRACES + "\\message{Hello world!}"
                    + "\\end ",
            // --- log message ---
            "Hello world!");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message prints
     * its plain argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessage3() throws Exception {

        assertFailure(// --- input code ---
            "\\nonstopmode" + DEFINE_BRACES + "\\message{Hello world!}"
                    + "\\end ",
            // --- log message ---
            "Hello world!");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message prints
     * its plain argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessage4() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES + "\\message{Hello world!}"
                    + "\\end ",
            // --- log message ---
            "Hello world!");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message
     * complains about undefined control sequences as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES + "\\message{\\x}" + "\\end ",
            // --- log message ---
            "Undefined control sequence the control sequence \\x");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message
     * complains about undefined control sequences embedded in the argument.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageErr2() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES + "\\message{a\\x b}" + "\\end ",
            // --- log message ---
            "Undefined control sequence the control sequence \\x");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message on
     * embedded primitives works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageErr3() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES + "\\message{a\\hfill b}" + "\\end ",
            // --- log message ---
            "a\\hfill b");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message on
     * embedded primitives works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageErr4() throws Exception {

        assertFailure(// --- input code ---
            "\\scrollmode" + DEFINE_BRACES + "\\message{a\\par b}" + "\\end ",
            // --- log message ---
            "a\\par b");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message can be
     * redirected. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageLog1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\message to log {x}",
            // --- log message ---
            "");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message needs an
     * argument for <tt>to</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageLog2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\message to {x}",
            // --- log message ---
            "`log' target missing");
    }

    /**
     * <testcase primitive="\message"> Test case checking that \message in
     * default mode complains about an undefined control sequence. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMessageUndef1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\message{\\x}",
            // --- log message ---
            "Undefined control sequence the control sequence \\x");
    }

    /**
     * <testcase primitive="\message"> Test case checking that <tt>\message</tt>
     * results in an error message, if the following token is not a left brace.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissingBrace1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\message }" + "\\end ",
            // --- log message ---
            "Missing `{' inserted");
    }

    /**
     * <testcase primitive="\message"> Test case checking that <tt>\message</tt>
     * results in an error message, if the following token is not a left brace.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissingBrace2() throws Exception {

        assertFailure(// --- input code ---
            "\\message {" + "\\end ",
            // --- log message ---
            "Missing `{' inserted");
    }

    // /**
    // * <testcase primitive="\message"> Test case checking that \message . . .
    // * </testcase>
    // *
    // * @throws Exception in case of an error
    // */
    // @Test
    // public void testMessageProtected1() throws Exception {

    // assertFailure(//--- input code ---
    // DEFINE_BRACES
    // + "\\protected\\def\\x{abc}"
    // + "\\message{\\x}",
    // //--- log message ---
    // "\\x");
    // }
}
