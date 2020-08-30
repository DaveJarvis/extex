/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.conditional;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test class for conditional primitives.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class ConditionalTester extends ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive to be
     * tested.
     */
    private final String primitive;

    /**
     * The field {@code arguments} contains arguments for the primitive.
     */
    private final String arguments;

    /**
     * The field {@code init} contains the initializing code.
     */
    private String init = "";

    /**
     * Creates a new object.
     * @param primitive the name pf the primitive to be tested
     * @param arguments the arguments for the primitive
     */
    public ConditionalTester(String primitive, String arguments) {

        this.primitive = primitive;
        this.arguments = arguments;
    }

    /**
     * Creates a new object.
     * @param primitive the name pf the primitive to be tested
     * @param arguments the arguments for the primitive
     * @param init the initializing code
     */
    public ConditionalTester(String primitive, String arguments, String init) {

        this.primitive = primitive;
        this.arguments = arguments;
        this.init = init;
    }

    /**
     * Test case checking that two open blocks leads to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOpenIf() throws Exception {

        assertFailure(// --- input code ---
            init + "\\" + primitive + arguments + "\\end ",
            // --- log message ---
            "(\\end occurred when \\" + primitive + " was incomplete)\n");
    }

}
