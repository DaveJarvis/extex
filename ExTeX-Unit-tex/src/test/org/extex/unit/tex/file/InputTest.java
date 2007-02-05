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

package org.extex.unit.tex.file;

import org.extex.test.NoFlagsPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\input</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class InputTest extends NoFlagsPrimitiveTester {

    /**
     * The field <tt>EMPTY_TEX</tt> contains the location of an empty file.
     */
    private static final String EMPTY_TEX = "../ExTeX-Unit-tex/src/test/tex/empty.tex";

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(InputTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public InputTest(final String arg) {

        super(arg, "input", " " + EMPTY_TEX + " ", "\\nonstopmode");
    }

    /**
     * <testcase primitive="\input">
     *  Test case checking that a <tt>\input</tt> works.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        assertSuccess(//--- input code ---
                "\\input " + EMPTY_TEX,
                //--- output channel ---
                "");
    }

    /**
     * <testcase primitive="\input">
     *  Test case checking that a <tt>\input</tt> works.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErr0() throws Exception {

        assertFailure(//--- input code ---
                "\\input DoesNotExist",
                //--- output channel ---
                "I can't find file `DoesNotExist'");
    }

}
