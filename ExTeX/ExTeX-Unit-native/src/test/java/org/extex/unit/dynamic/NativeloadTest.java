/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.dynamic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\nativeload</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NativeloadTest extends NoFlagsPrimitiveTester {

    /**
     * Creates a new object.
     */
    public NativeloadTest() {

        super("nativeload", "{java}{org.extex.unit.dynamic.NativeloadSensor}");
        setConfig("native-test");
    }

    /**
     * <testcase primitive="\nativeload">
     *  Test case checking that <tt>\nativeload</tt> produces a proper error
     *  message if an invalid type is specified.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_BRACES
                    + "\\nativeload{undefined type}"
                    + "{org.extex.unit.dynamic.NativeloadSensor}"
                    + " \\end",
            //--- error message ---
            "I don't know how to load native type `undefined type'");
        assertFalse(NativeloadSensor.isSensed());
    }

    /**
     * <testcase primitive="\nativeload">
     *  Test case checking that <tt>\nativeload</tt> produces a proper error
     *  message if an undefined class is specified.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testError2() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_BRACES + "\\nativeload{java}" + "{un.de.fined.Class}"
                    + " \\end",
            //--- error message ---
            "Class not found: un.de.fined.Class");
    }

    /**
     * <testcase primitive="\nativeload">
     *  Test case checking that <tt>\nativeload</tt> produces a proper error
     *  message if an invalid class is specified.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testError3() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_BRACES + "\\nativeload{java}" + "{java.lang.String}"
                    + " \\end",
            //--- error message ---
            "The class java.lang.String does not implement "
            + "the required interface\norg.extex.unit.dynamic.java.Loadable.");
    }

    /**
     * <testcase primitive="\nativeload">
     *  Test case checking that <tt>\nativeload</tt> properly invokes a correct
     *  loader.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(
        //--- input code ---
            DEFINE_BRACES
                    + "\\nativeload{java}"
                    + "{org.extex.unit.dynamic.NativeloadSensor}"
                    + " \\end",
            //--- log message ---
            "");
        assertTrue(NativeloadSensor.isSensed());
    }

}
