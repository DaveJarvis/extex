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
 * This is a test suite for primitives which can not be executed. They lead to
 * an error in each mode.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class NonExecuteTester extends ExTeXLauncher {

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
    private String prepare = DEFINE_CATCODES;

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the primitive
     * @param arguments additional arguments for the flag test
     */
    public NonExecuteTester(String primitive, String arguments) {

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
    public NonExecuteTester(String primitive, String arguments, String prepare) {

        this(primitive, arguments);
        this.prepare = DEFINE_CATCODES + prepare;
    }

    /**
     * Test case showing that the primitive can not be used in vertical mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testVerticalMode1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\" + primitive + arguments,
            // --- error channel ---
            "You can't use `\\" + primitive + "' in vertical mode");
    }

    /**
     * Test case showing that the primitive can not be used in inner vertical mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInnerVerticalMode1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\vbox{\\" + primitive + arguments + "}\\end",
            // --- error channel ---
            "You can't use `\\" + primitive + "' in inner vertical mode");
    }

    /**
     * Test case showing that the primitive can not be used in vertical mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testHorizontalMode1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "x\\" + primitive + arguments + "\\end",
            // --- error channel ---
            "You can't use `\\" + primitive + "' in horizontal mode");
    }

    /**
     * Test case showing that the primitive can not be used in restricted horizontal mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testRestrictedHorizontalMode1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\hbox{\\" + primitive + arguments + "}\\end",
            // --- error channel ---
            "You can't use `\\" + primitive + "' in restricted horizontal mode");
    }

    /**
     * Test case showing that the primitive can not be used in math mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testMathMode1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "$\\" + primitive + arguments + "$\\end",
            // --- error channel ---
            "You can't use `\\" + primitive + "' in math mode");
    }

    /**
     * Test case showing that the primitive can not be used in display math mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testDisplayMathMode1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "$$\\" + primitive + arguments + "$$\\end",
            // --- error channel ---
            "You can't use `\\" + primitive + "' in displaymath mode");
    }

}
