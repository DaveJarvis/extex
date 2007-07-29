/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.primitives.format;

import org.extex.test.ExTeXLauncher;

/**
 * Test for {@link PrintFormat}.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class PrintFormatTest extends ExTeXLauncher {

    /**
     * Creates a new object.
     */
    public PrintFormatTest() {

        super("PrintFormatTest");

        setConfig("base-ext-test.xml");
    }

    /**
     * <testcase primitive="\printformat">
     * 
     * Test with a count register.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testCount1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\countdef\\x=42 " + "\\x=12 "
                    + "\\printformat{0.00}\\x \\end",
            // --- output channel ---
            "12.00" + TERM);
    }

    /**
     * <testcase primitive="\printformat">
     * 
     * Test with a pair register.
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testPair1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\pairdef\\x=42 " + "\\x=1.234567 123.119 "
                    + "\\printformat{0.00}\\x \\end",
            // --- output channel ---
            "1.23 123.12" + TERM);
    }

    /**
     * <testcase primitive="\printformat">
     * 
     * Test: 1.23456788 - 0.00 - 1.23
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testReal1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\realdef\\x=42 " + "\\x=1.23456788 "
                    + "\\printformat{0.00}\\x \\end",
            // --- output channel ---
            "1.23" + TERM);
    }

    /**
     * <testcase primitive="\printformat">
     * 
     * Test: 1.23756788 - 0.00 - 1.24
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testReal2() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\realdef\\x=42 " + "\\x=1.23756788 "
                    + "\\printformat{0.00}\\x \\end",
            // --- output channel ---
            "1.24" + TERM);
    }

    /**
     * <testcase primitive="\printformat">
     * 
     * Test: 11.23756788 - 0.0000 - 11.2376
     * 
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testReal3() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_CATCODES + "\\realdef\\x=42 " + "\\x=11.23756788 "
                    + "\\printformat{0.0000}\\x \\end",
            // --- output channel ---
            "11.2376" + TERM);
    }

}
