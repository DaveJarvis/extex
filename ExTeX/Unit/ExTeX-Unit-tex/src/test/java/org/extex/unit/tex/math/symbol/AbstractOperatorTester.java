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

package org.extex.unit.tex.math.symbol;

import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;

/**
 * This is an abstract tester for a symbol primitive.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractOperatorTester extends AbstractMathTester {

    public AbstractOperatorTester() {
        setArguments( " x" );
        setPrepare( "" );
    }

    /**
     * <testcase> Test case checking that the primitive complains when the
     * argument is missing. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH_FONTS + DEFINE_MATH + "$\\" + getPrimitive(),
            // --- output message ---
            "Unexpected end of file while processing \\" + getPrimitive());
    }

    /**
     * <testcase> Test case checking that the primitive needs a defined control
     * sequence as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(
        // --- input code ---
            DEFINE_MATH_FONTS + DEFINE_MATH + "$\\" + getPrimitive()
                    + "\\undef$\\end",
            // --- output message ---
            "Undefined control sequence \\undef");
    }

    /**
     * <testcase> Test case checking that the primitive can take a character
     * defined with <tt>\chardef</tt> as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChardef1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_MATH_FONTS + DEFINE_MATH + "\\chardef\\x `x" + "$\\"
                    + getPrimitive() + "\\x$",
            // --- output message ---
            "x" + TERM);
    }

    /**
     * <testcase> Test case checking that the primitive can take a character
     * defined with <tt>\let</tt> as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLet1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_MATH_FONTS + DEFINE_MATH + "\\let\\x x" + "$\\"
                    + getPrimitive() + "\\x$",
            // --- output message ---
            "x" + TERM);
    }

    /**
     * <testcase> Test case checking that the primitive can take a character
     * enclosed in braces as argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBraces1() throws Exception {

        assertSuccess(
        // --- input code ---
            DEFINE_MATH_FONTS + DEFINE_MATH + DEFINE_BRACES + "$\\"
                    + getPrimitive() + "{x}$",
            // --- output message ---
            "x" + TERM);
    }

}
