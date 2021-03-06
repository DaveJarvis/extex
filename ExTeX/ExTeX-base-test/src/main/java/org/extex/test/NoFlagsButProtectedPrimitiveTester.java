/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.test;

import org.junit.Test;

/**
 * This class provides an abstract base class containing some test cases for
 * primitives. They verify that prefix macros always lead to an error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class NoFlagsButProtectedPrimitiveTester extends ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive.
     */
    private final String primitive;

    /**
     * The field {@code arguments} contains the additional arguments for the
     * flag test.
     */
    private final String arguments;

    /**
     * The field {@code prepare} contains the preparation code.
     */
    private String prepare = DEFINE_BRACES;

    /**
     * The field {@code out} contains the prefix expected on the output
     * stream.
     */
    private String out = "";

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the primitive
     * @param arguments additional arguments for the flag test
     */
    public NoFlagsButProtectedPrimitiveTester(String primitive, String arguments) {

        this.primitive = primitive;
        this.arguments = arguments;
    }

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param arguments the parameters for the invocation
     * @param prepare the preparation code
     */
    public NoFlagsButProtectedPrimitiveTester(String primitive,
            String arguments, String prepare) {

        this(primitive, arguments);
        this.prepare = DEFINE_BRACES + prepare;
    }

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the integer register to test
     * @param arguments the parameters for the invocation
     * @param prepare the preparation code
     * @param out the prefix of the expected output
     */
    public NoFlagsButProtectedPrimitiveTester(String primitive,
            String arguments, String prepare, String out) {

        this(primitive, arguments);
        this.prepare = DEFINE_BRACES + prepare;
        this.out = out;
    }

    /**
     *  Test case checking that the prefix {@code \global} leads to
     * the expected error message
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoGlobalFlag() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\global\\" + primitive + arguments + "\\end",
            // --- log message ---
            out + "You can't use the prefix `\\global' with"
                    + " the control sequence \\" + primitive);
    }

    /**
     *  Test case checking that the prefix {@code \immediate} leads
     * to the expected error message
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoImmediateFlag() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\immediate\\" + primitive + arguments + "\\end",
            // --- log message ---
            out + "You can't use the prefix `\\immediate' with"
                    + " the control sequence"
                    + (primitive.length() >= 15 ? "\n" : " ") + "\\"
                    + primitive);
    }

    /**
     *  Test case checking that the prefix {@code \long} leads to
     * the expected error message
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoLongFlag() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\long\\" + primitive + arguments + "\\end",
            // --- log message ---
            out + "You can't use the prefix `\\long' with"
                    + " the control sequence \\" + primitive);
    }

    /**
     *  Test case checking that the prefix {@code \outer} leads to
     * the expected error message
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoOuterFlag() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\outer\\" + primitive + arguments + "\\end",
            // --- log message ---
            out + "You can't use the prefix `\\outer' with"
                    + " the control sequence \\" + primitive);
    }

}
