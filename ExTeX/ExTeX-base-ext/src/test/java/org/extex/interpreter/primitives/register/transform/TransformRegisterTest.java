/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.interpreter.primitives.register.transform;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * A test for the transform register.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
*/
public class TransformRegisterTest extends ExTeXLauncher {


    public TransformRegisterTest() {

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that {@code \transformdef} creates a transform
     * assignable control sequence which is equivalent to the
     * {@code \transform}.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\transformdef\\x=42 " + "\\transform42=1.1 2.2 3.3 4.4 5.5 6.6 "
                    + "\\the\\transform42 \\end",
            // --- output channel ---
            "1.1 2.2 3.3 4.4 5.5 6.6" + TERM);
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that {@code \transformdef} respects a group.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup\\transformdef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that {@code \transformdef} respects a group.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\transformdef\\x=42 \\x=1.1 2.2 3.3 4.4 5.5 6.6\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "0.0 0.0 0.0 0.0 0.0 0.0" + TERM);
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that {@code \transformdef} respects a group.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\transformdef\\x=42 \\global\\x=1.1 2.2 3.3 4.4 5.5 6.6\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "1.1 2.2 3.3 4.4 5.5 6.6" + TERM);
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that {@code \transformdef} respects
     * {@code \globaldefs}.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal4() throws Exception {

        assertSuccess(// --- input code ---
            "\\globaldefs=1\\begingroup\\transformdef\\x=42 \\x=1.1 2.2 3.3 4.4 5.5 6.6\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "1.1 2.2 3.3 4.4 5.5 6.6" + TERM);
    }

}
