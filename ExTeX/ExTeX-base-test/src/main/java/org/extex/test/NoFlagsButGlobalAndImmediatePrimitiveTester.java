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
 * This abstract base class for tests contains some tests which check that all
 * flag primitives but {@code \global} and {@code \immediate} lead to an
 * error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public abstract class NoFlagsButGlobalAndImmediatePrimitiveTester
        extends
            ExTeXLauncher {

    /**
     * The field {@code primitive} contains the name of the primitive.
     */
    private final String primitive;

    /**
     * The field {@code args} contains the additional arguments for the flag
     * test.
     */
    private final String args;

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the primitive
     * @param args additional arguments for the flag test
     */
    public NoFlagsButGlobalAndImmediatePrimitiveTester(String primitive,
            String args) {

        this.primitive = primitive;
        this.args = args;
    }

    /**
     * Test case checking that the {@code \long} flag leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoLongFlag() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\long\\" + primitive + args,
            // --- log message ---
                      "You can't use the prefix `\\long' with the control " +
                          "sequence \\"
                    + primitive);
    }

    /**
     * Test case checking that the {@code \outer} flag leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoOuterFlag() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + "\\outer\\" + primitive + args,
            // --- log message ---
                      "You can't use the prefix `\\outer' with the control " +
                          "sequence \\"
                    + primitive);
    }

    /**
     * Test case checking that the {@code \protected} flag leads to an error
* 
     * @throws Exception in case of an error
     */
    // @Test
    // public void testNoProtectedFlag() throws Exception {
    
    // assertFailure(//--- input code ---
    // DEFINE_CATCODES + "\\protected\\" + primitive + args,
    // //--- log message ---
    // "You can\'t use the prefix `\\protected\' with the control sequence \\"
    // + primitive);
    // }
}
