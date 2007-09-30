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

package org.extex.unit.etex.scanner;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\detokenize</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class DetokenizeTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(DetokenizeTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public DetokenizeTest(String arg) {

        super(arg, "detokenize", "{}");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\detokenize"> Test case checking that
     * <tt>\detokenize</tt> needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(// --- input code ---
            "\\detokenize",
            // --- output channel ---
            "File ended while scanning text of \\detokenize");
    }

    /**
     * <testcase primitive="\detokenize"> Test case checking that
     * <tt>\detokenize</tt> consumes a letter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\detokenize{a}\\end",
            // --- output channel ---
            "a" + TERM);
    }

    /**
     * <testcase primitive="\detokenize"> Test case checking that
     * <tt>\detokenize</tt> consumes some letters. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "\\detokenize{abc}\\end",
            // --- output channel ---
            "abc" + TERM);
    }

    // TODO implement more primitive specific test cases
}
