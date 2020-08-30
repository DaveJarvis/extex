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

package org.extex.unit.tex.file;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.extex.test.NoFlagsButGlobalAndImmediatePrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \openout}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OpenoutTest extends NoFlagsButGlobalAndImmediatePrimitiveTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(OpenoutTest.class);
    }


    public OpenoutTest() {

        super("openout", "1 texput.test");
    }

    /**
     * <testcase primitive="\openout"> Test case checking that a lonely
     * {@code \openout} leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(// --- input code ---
            "\\openout ",
            // --- log message ---
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\openout"> Test case checking that a lonely
     * {@code \openout} with an index leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof2() throws Exception {

        assertFailure(// --- input code ---
            "\\openout 2",
            // --- log message ---
            "Unexpected end of file while processing \\openout");
    }

    /**
     * <testcase primitive="\openout"> Test case checking that an unknown
     * encoding for immediate {@code \openout} leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        String enc = System.setProperty("extex.encoding", "uuu");

        try {
            assertFailure(
            // --- input code ---
                "\\immediate \\openout 2 texput.tmp ",
                // --- log message ---
                "Unsupported encoding uuu");
        } finally {
            if (enc != null) {
                System.setProperty("extex.encoding", enc);
            } else {
                System.clearProperty("extex.encoding");
            }
            new File("texput.tmp").delete();
        }
    }

    /**
     * <testcase primitive="\openout"> Test case checking that an unknown
     * encoding for non-immediate {@code \openout} leads to an error.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        String enc = System.setProperty("extex.encoding", "uuu");

        try {
            assertFailure(
            // --- input code ---
                "\\openout 2 texput.tmp ",
                // --- log message ---
                "Unsupported encoding uuu\n");
        } finally {
            if (enc != null) {
                System.setProperty("extex.encoding", enc);
            } else {
                System.clearProperty("extex.encoding");
            }
            new File("texput.tmp").delete();
        }
    }

    /**
     * <testcase primitive="\openout"> Test case checking that an immediate
     * {@code \openout} works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        String enc = System.setProperty("extex.encoding", "UTF-8");

        try {
            assertSuccess(
            // --- input code ---
                "\\immediate \\openout 2 texput.tmp \\closeout2\\end",
                // --- log message ---
                "\n");
            assertTrue(new File("texput.tmp").exists());
        } finally {
            if (enc != null) {
                System.setProperty("extex.encoding", enc);
            } else {
                System.clearProperty("extex.encoding");
            }
            new File("texput.tmp").delete();
        }
    }

    /**
     * <testcase primitive="\openout"> Test case checking that a non-immediate
     * {@code \openout} works.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        String enc = System.setProperty("extex.encoding", "UTF-8");

        try {
            assertSuccess(
            // --- input code ---
                "\\openout 2 texput.tmp \\closeout2\\end",
                // --- log message ---
                "\n");
            assertTrue(new File("texput.tmp").exists());
        } finally {
            if (enc != null) {
                System.setProperty("extex.encoding", enc);
            } else {
                System.clearProperty("extex.encoding");
            }
            new File("texput.tmp").delete();
        }
    }

}
