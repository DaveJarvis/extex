/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

/**
 * A test for the transform register.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class TransformRegisterTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public TransformRegisterTest() {

        super("TransformRegisterTest");

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that <tt>\transformdef</tt> creates a transform
     * assignable control sequence which is equivalent to the
     * <tt>\transform</tt>.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
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
     * Test case checking that <tt>\transformdef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup\\transformdef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\transformdef">
     * 
     * Test case checking that <tt>\transformdef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
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
     * Test case checking that <tt>\transformdef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
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
     * Test case checking that <tt>\transformdef</tt> respects
     * <tt>\globaldefs</tt>.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testGlobal4() throws Exception {

        assertSuccess(// --- input code ---
            "\\globaldefs=1\\begingroup\\transformdef\\x=42 \\x=1.1 2.2 3.3 4.4 5.5 6.6\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "1.1 2.2 3.3 4.4 5.5 6.6" + TERM);
    }

}
