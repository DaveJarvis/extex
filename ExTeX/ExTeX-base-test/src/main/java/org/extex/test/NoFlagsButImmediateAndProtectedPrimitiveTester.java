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

package org.extex.test;

import org.junit.Test;

/**
 * This class is an abstract base class for test suited containing some test
 * cases to verify that the prefixes <tt>\global</tt>, <tt>\long</tt>, and
 * <tt>\outer</tt> lead to an error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class NoFlagsButImmediateAndProtectedPrimitiveTester
        extends
            ExTeXLauncher {

    /**
     * The field <tt>primitive</tt> contains the name of the primitive.
     */
    private String primitive;

    /**
     * The field <tt>args</tt> contains the additional arguments for the flag
     * test.
     */
    private String args;

    /**
     * The field <tt>prepare</tt> contains the prepare code.
     */
    private String prepare = "";

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the primitive
     * @param args additional arguments for the flag test
     */
    public NoFlagsButImmediateAndProtectedPrimitiveTester(String primitive,
            String args) {

        super();
        this.primitive = primitive;
        this.args = args;
    }

    /**
     * Creates a new object.
     * 
     * @param primitive the name of the primitive
     * @param args additional arguments for the flag test
     * @param prepare the preparing code
     */
    public NoFlagsButImmediateAndProtectedPrimitiveTester(String primitive,
            String args, String prepare) {

        super();
        this.primitive = primitive;
        this.args = args;
        this.prepare = prepare;
    }

    /**
     * <testcase> Test case checking that the <tt>\global</tt> flag leads to
     * an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoGlobalFlag() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + prepare + "\\global\\" + primitive + args,
            // --- log message ---
            "You can\'t use the prefix `\\global\' with the control sequence \\"
                    + primitive);
    }

    /**
     * <testcase> Test case checking that the <tt>\long</tt> flag leads to an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoLongFlag() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + prepare + "\\long\\" + primitive + args,
            // --- log message ---
            "You can\'t use the prefix `\\long\' with the control sequence \\"
                    + primitive);
    }

    /**
     * <testcase> Test case checking that the <tt>\outer</tt> flag leads to an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoOuterFlag() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_CATCODES + prepare + "\\outer\\" + primitive + args,
            // --- log message ---
            "You can\'t use the prefix `\\outer\' with the control sequence \\"
                    + primitive);
    }

}