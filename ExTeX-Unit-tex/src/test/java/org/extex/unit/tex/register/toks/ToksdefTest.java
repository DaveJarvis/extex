/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.toks;

import org.extex.interpreter.primitives.register.toks.AbstractToksRegisterTester;

/**
 * This is a test suite for the primitive <tt>\toksdef</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class ToksdefTest extends AbstractToksRegisterTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(ToksdefTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public ToksdefTest(String arg) {

        super(arg, "x", "", "", "\\toksdef\\x=42 ");
    }

    /**
     * <testcase primitive="\toksdef">
     *  Test case checking that <tt>\toksdef</tt> creates a toks assignable
     *  control sequence which is equivalent to the <tt>\toks</tt>.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(//--- input code ---
                DEFINE_BRACES + "\\toksdef\\x=42 " + "\\toks42={abc}"
                        + "\\the\\toks42 \\end",
                //--- output channel ---
                "abc" + TERM);
    }

    /**
     * <testcase primitive="\toksdef">
     *  Test case checking that <tt>\toksdef</tt> respects a group.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobal1() throws Exception {

        assertFailure(//--- input code ---
                "\\begingroup\\toksdef\\x=42 \\endgroup" + "\\the\\x \\end",
                //--- error channel ---
                "Undefined control sequence \\x");
    }

    /**
     * <testcase primitive="\toksdef">
     *  Test case checking that <tt>\toksdef</tt> respects a group.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testGlobal2() throws Exception {

        assertSuccess(
                //--- input code ---
                DEFINE_BRACES
                        + "\\begingroup\\global\\toksdef\\x=42 \\x={abc}\\endgroup"
                        + "\\the\\x \\end",
                //--- output channel ---
                "");
    }

}