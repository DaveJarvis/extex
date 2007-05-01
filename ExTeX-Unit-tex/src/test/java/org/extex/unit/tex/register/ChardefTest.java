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

package org.extex.unit.tex.register;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\chardef</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ChardefTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(ChardefTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public ChardefTest(String arg) {

        super(arg, "chardef", "\\x=123", "");
    }

    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a cs.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof1() throws Exception {

        assertFailure("\\chardef ", //
            "Missing control sequence inserted");
    }


    /**
     * <testcase primitive="\chardef">
     *  Test case checking that <tt>\chardef</tt> needs a char as second argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEof2() throws Exception {

        assertFailure("\\chardef\\x ", //
            "Missing number, treated as zero");
    }

    //TODO implement the primitive specific test cases

}
