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

package org.extex.unit.tex.register.count;

import org.extex.test.count.AbstractCountRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\countdef</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class CountdefTest extends AbstractCountRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(CountdefTest.class);
    }

    /**
     * Creates a new object.
     */
    public CountdefTest() {

        super("cc", "", "0", "\\countdef\\cc=42 ");
    }

    /**
     * <testcase primitive="\countdef"> Test case checking that
     * <tt>\countdef</tt> creates a count assignable control sequence which is
     * equivalent to the <tt>\count</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        // --- input code ---
            "\\countdef\\x=42 " + "\\count42=123 " + "\\the\\count42 \\end",
            // --- output channel ---
            "123" + TERM);
    }

    /**
     * <testcase primitive="\countdef"> Test case checking that
     * <tt>\countdef</tt> respects a group. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertFailure(// --- input code ---
            "\\begingroup\\countdef\\x=42 \\endgroup" + "\\the\\x \\end",
            // --- error channel ---
            "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\countdef"> Test case checking that
     * <tt>\countdef</tt> respects a group. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup\\global\\countdef\\x=42 \\x=123\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "0" + TERM);
    }

    /**
     * <testcase primitive="\countdef"> Test case checking that
     * <tt>\countdef</tt> respects <tt>\globaldefs</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal10() throws Exception {

        assertSuccess(// --- input code ---
            "\\globaldefs=1\\begingroup\\countdef\\x=42 \\x=123\\endgroup"
                    + "\\the\\x \\end",
            // --- output channel ---
            "123" + TERM);
    }

}