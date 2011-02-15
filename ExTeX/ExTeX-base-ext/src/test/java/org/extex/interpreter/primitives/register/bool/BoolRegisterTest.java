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

package org.extex.interpreter.primitives.register.bool;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * A test for the bool register.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class BoolRegisterTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public BoolRegisterTest() {

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\booldef">
     * 
     * Test case checking that <tt>\booldef</tt> creates a bool assignable
     * control sequence which is equivalent to the <tt>\bool</tt>.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\booldef\\x=42 " + "\\bool42=true " + "\\the\\bool42 \\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\booldef">
     * 
     * Test case checking that <tt>\booldef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup\\booldef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\booldef">
     * 
     * Test case checking that <tt>\booldef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\booldef\\x=42 \\x=true\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "false" + TERM);
    }

    /**
     * <testcase primitive="\booldef">
     * 
     * Test case checking that <tt>\booldef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\booldef\\x=42 \\global\\x=true\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "true" + TERM);
    }

    /**
     * <testcase primitive="\booldef">
     * 
     * Test case checking that <tt>\booldef</tt> respects <tt>\globaldefs</tt>.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal4() throws Exception {

        assertSuccess(// --- input code ---
            "\\globaldefs=1\\begingroup\\booldef\\x=42 \\x=true\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "true" + TERM);
    }

}
