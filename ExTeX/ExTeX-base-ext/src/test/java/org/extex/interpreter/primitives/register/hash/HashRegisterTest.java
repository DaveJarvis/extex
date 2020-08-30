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

package org.extex.interpreter.primitives.register.hash;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * A test for the hash register.
 * 
 * <p>
 * use extex-extension
 * </p>
 * 
 * @author <a href="mailto:m.g.sn@gmx.de">Michael Niedermair</a>
*/
public class HashRegisterTest extends ExTeXLauncher {


    public HashRegisterTest() {

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\booldef">
     * 
     * Test case checking that {@code \hashtoksdef} creates a hashtoks
     * assignable control sequence which is equivalent to the {@code \hashtoks}.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\hashtoksdef\\x=42 "
                    + "\\hashtoks42={{key1}{value1}{key2}{value2}}"
                    + "\\the\\hashtoks42 \\end",
            // --- output channel ---
            "{\n{key1}{value1}\n{key2}{value2}\n}\n" + TERM);
    }

    /**
     * <testcase primitive="\hashtoksdef">
     * 
     * Test case checking that {@code \hashtoksdef} respects a group.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\begingroup\\hashtoksdef\\x=42 \\endgroup"
                    + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\hashtoksdef">
     * 
     * Test case checking that {@code \hashtoksdef} respects a group.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES
                    + "\\begingroup\\global\\hashtoksdef\\x=42 \\x={{key1}{value1}{key2}{value2}}\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "{\n}\n\n\n");
    }

    /**
     * <testcase primitive="\hashtoksdef">
     * 
     * Test case checking that {@code \hashtoksdef} respects a group.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES
                    + "\\begingroup\\global\\hashtoksdef\\x=42 \\global\\hashtoks42={{key1}{value1}{key2}{value2}}\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "{\n{key1}{value1}\n{key2}{value2}\n}\n" + TERM);
    }

    /**
     * <testcase primitive="\hashtoksdef">
     * 
     * Test case checking that {@code \hashtoksdef} respects
     * {@code \globaldefs}.
     * 
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal4() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_CATCODES
                    + "\\globaldefs=1\\begingroup\\hashtoksdef\\x=42 \\hashtoks42={{key1}{value1}{key2}{value2}}\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "{\n{key1}{value1}\n{key2}{value2}\n}\n" + TERM);
    }

}
