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

import org.junit.Ignore;
import org.junit.Test;

/**
 * This abstract test suite contains some tests to check that all flags but the
 * global flag lead to an error.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class NoFlagsButGlobalPrimitiveTester extends ExTeXLauncher {

    /**
     * The field <tt>primitive</tt> contains the name of the primitive.
     */
    private String primitive = "";

    /**
     * The field <tt>arguments</tt> contains the additional arguments for the
     * flag test.
     */
    private String arguments = "";

    /**
     * The field <tt>prepare</tt> contains the preparation code.
     */
    private String prepare = DEFINE_BRACES;

    /**
     * The field <tt>out</tt> contains the prefix of the output message.
     */
    private String out = "";

    public NoFlagsButGlobalPrimitiveTester() {
    }

    public void setPrimitive( final String primitive ) {
        this.primitive = primitive;
    }

    public void setArguments( final String arguments ) {
        this.arguments = arguments;
    }

    public void setPrepare( final String prepare ) {
        this.prepare = DEFINE_BRACES + prepare;
    }

    public void setOut( final String out ) {
        this.out = out;
    }

    /**
     * <testcase> Test case checking that the <tt>\immediate</tt> flag leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoImmediateFlag() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\immediate\\" + primitive + arguments,
            // --- log message ---
            out + "You can't use the prefix `\\immediate' "
                    + "with the control sequence"
                    + (primitive.length() < 16 ? " " : "\n") + "\\" + primitive);
    }

    /**
     * <testcase> Test case checking that the <tt>\long</tt> flag leads to an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoLongFlag() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\long\\" + primitive + arguments,
            // --- log message ---
            out + "You can't use the prefix `\\long' "
                    + "with the control sequence"
                    + (primitive.length() > 19 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * <testcase> Test case checking that the <tt>\outer</tt> flag leads to an
     * error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNoOuterFlag() throws Exception {

        assertFailure(
        // --- input code ---
            prepare + "\\outer\\" + primitive + arguments,
            // --- log message ---
            out + "You can't use the prefix `\\outer' "
                    + "with the control sequence"
                    + (primitive.length() > 18 ? "\n" : " ") + "\\" + primitive);
    }

    /**
     * <testcase> Test case checking that the <tt>\protected</tt> flag leads
     * to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
     @Test
     @Ignore
     public void testNoProtectedFlag() throws Exception {
         assertFailure(
             //--- input code ---
             prepare + "\\protected\\" + primitive + arguments,
             //--- log message ---
             out + "You can\'t use the prefix `\\protected\' "
                 + "with the control sequence"
                 + (primitive.length() < 16 ? " " : "\n") + "\\"
                 + primitive );
     }
}
