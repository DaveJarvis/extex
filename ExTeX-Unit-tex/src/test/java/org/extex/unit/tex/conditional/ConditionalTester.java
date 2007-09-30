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

package org.extex.unit.tex.conditional;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test class for conditional primitives.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class ConditionalTester extends ExTeXLauncher {

    /**
     * The field <tt>primitive</tt> contains the name of the primitive to be
     * tested.
     */
    private String primitive;

    /**
     * The field <tt>arguments</tt> contains arguments for the primitive.
     */
    private String arguments;

    /**
     * The field <tt>init</tt> contains the initializing code.
     */
    private String init = "";

    /**
     * Creates a new object.
     * 
     * @param arg the name
     * @param primitive the name pf the primitive to be tested
     * @param arguments the arguments for the primitive
     */
    public ConditionalTester(String arg, String primitive, String arguments) {

        super(arg);
        this.primitive = primitive;
        this.arguments = arguments;
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     * @param primitive the name pf the primitive to be tested
     * @param arguments the arguments for the primitive
     * @param init the initializing code
     */
    public ConditionalTester(String arg, String primitive, String arguments,
            String init) {

        super(arg);
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
