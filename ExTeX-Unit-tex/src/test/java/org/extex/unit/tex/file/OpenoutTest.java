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

import org.extex.test.NoFlagsButGlobalAndImmediatePrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\openout</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class OpenoutTest extends NoFlagsButGlobalAndImmediatePrimitiveTester {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(OpenoutTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public OpenoutTest(String arg) {

        super(arg, "openout", "1 texput.test");
    }

    /**
     * <testcase primitive="\openout">
     *  Test case checking that a lonely <tt>\openout</tt> leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof1() throws Exception {

        assertFailure(//--- input code ---
                "\\openout ",
                //--- log message ---
                "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\openout">
     *  Test case checking that a lonely <tt>\openout</tt> with an index
     *  leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof2() throws Exception {

        assertFailure(//--- input code ---
                "\\openout 2",
                //--- log message ---
                "Unexpected end of file while processing \\openout");
    }

}