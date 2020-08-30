/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.test.count;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for read-only count registers.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class AbstractReadonlyCountRegisterTester extends ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive to
     * test.
     */
    private final String primitive;

    /**
     * The field {@code defaultValue} contains the default value.
     */
    private final String defaultValue;

    /**
     * The field {@code argument} contains the argument.
     */
    private String argument = "";

    /**
     * The field {@code prepare} contains the preparation code inserted
     * before the invocation.
     */
    private String prepare = "";

    /**
     * Creates a new object.
     * @param primitive the name of the primitive
     * @param defaultValue the default value
     */
    public AbstractReadonlyCountRegisterTester(String primitive, String defaultValue) {

        this.primitive = primitive;
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new object.
     * @param primitive the name of the primitive
     * @param argument the argument
     * @param defaultValue the default value
     */
    public AbstractReadonlyCountRegisterTester(String primitive, String argument,
            String defaultValue) {

        this.primitive = primitive;
        this.defaultValue = defaultValue;
        this.argument = argument;
    }

    /**
     * Creates a new object.
     * @param primitive the name of the primitive
     * @param argument the argument
     * @param defaultValue the default value
     * @param prepare the preparation code inserted before the invocation
     */
    public AbstractReadonlyCountRegisterTester(String primitive, String argument,
            String defaultValue, String prepare) {

        this.primitive = primitive;
        this.defaultValue = defaultValue;
        this.argument = argument;
        this.prepare = prepare;
    }

    /**
     * Test case checking that the primitive is not allowed in vertical mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrorVerticalMode1() throws Exception {

        assertFailure(// --- input code ---
            "\\" + primitive + " ",
            // --- log message ---
            "You can't use `\\" + primitive + "' in vertical mode");
    }

    /**
     * Test case checking that the primitive is not allowed in inner vertical mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrorVerticalMode2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + prepare + "\\vbox{\\" + primitive + "} ",
            // --- log message ---
            "You can't use `\\" + primitive + "' in inner vertical mode");
    }

    /**
     * Test case checking that the primitive is not allowed in horizontal mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrorHorizonalMode1() throws Exception {

        assertFailure(// --- input code ---
            "x\\" + primitive + " ",
            // --- log message ---
            "You can't use `\\" + primitive + "' in horizontal mode");
    }

    /**
     * Test case checking that the primitive is not allowed in restricted horizontal mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrorHorizonalMode2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "\\hbox{\\" + primitive + "} ",
            // --- log message ---
            "You can't use `\\" + primitive + "' in restricted horizontal mode");
    }

    /**
     * Test case checking that the primitive is not allowed in math mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrorMathMode1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_MATH + "$\\" + primitive + "$ ",
            // --- log message ---
            "You can't use `\\" + primitive + "' in math mode");
    }

    /**
     * Test case checking that the primitive is not allowed in math mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErrorMathMode2() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_MATH + "$$\\" + primitive + "$$ ",
            // --- log message ---
            "You can't use `\\" + primitive + "' in displaymath mode");
    }

    /**
     * Test case checking that the primitive is theable and has the default value 0
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testDefaultValue1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\showthe\\" + primitive + argument + " \\end",
            // --- log message ---
            "> " + defaultValue + ".\n");
    }

    /**
     * Test case checking that the primitive is assignable to a count register
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCountAssignment1() throws Exception {

        assertFailure(// --- input code ---
            prepare + "\\count0=\\" + primitive + argument
                    + "\\showthe\\count0\\end",
            // --- log message ---
            "> " + defaultValue + ".\n");
    }

}
