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

package org.extex.unit.omega.mode;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\DefaultOutputMode</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class DefaultOutputModeTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * The command line interface.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(DefaultOutputModeTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public DefaultOutputModeTest(final String arg) {

        super(arg, "DefaultOutputMode", " onebyte ");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\DefaultOutputMode">
     *  Test case checking that <tt>\DefaultOutputMode</tt> works ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErrEof1() throws Exception {

        assertFailure(//--- input code ---
                "\\DefaultOutputMode",
                //--- output channel ---
                "Unexpected end of file");
    }

    /**
     * <testcase primitive="\DefaultOutputMode">
     *  Test case checking that <tt>\DefaultOutputMode</tt> works ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErr1() throws Exception {

        assertFailure(//--- input code ---
                "\\DefaultOutputMode xxx",
                //--- output channel ---
                "Bad output mode");
    }

    //TODO implement more primitive specific test cases

}