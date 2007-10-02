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

package org.extex.interpreter.primitives.register.real;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * A test for the real register.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class RealRegisterTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public RealRegisterTest() {

        super();

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\realdef">
     * 
     * Test case checking that <tt>\realdef</tt> creates a real assignable
     * control sequence which is equivalent to the <tt>\real</tt>.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\realdef\\x=42 " + "\\real42=1.2 " + "\\the\\real42 \\end",
            // --- output channel ---
            "1.2" + TERM);
    }

    /**
     * <testcase primitive="\realdef">
     * 
     * Test case checking that <tt>\realdef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup\\realdef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\realdef">
     * 
     * Test case checking that <tt>\realdef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\realdef\\x=42 \\x=1.2\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "0.0" + TERM);
    }

    /**
     * <testcase primitive="\realdef">
     * 
     * Test case checking that <tt>\realdef</tt> respects a group.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\realdef\\x=42 \\global\\x=1.2\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "1.2" + TERM);
    }

    /**
     * <testcase primitive="\realdef">
     * 
     * Test case checking that <tt>\realdef</tt> respects <tt>\globaldefs</tt>.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal4() throws Exception {

        assertSuccess(// --- input code ---
            "\\globaldefs=1\\begingroup\\realdef\\x=42 \\x=1.2\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "1.2" + TERM);
    }

}
