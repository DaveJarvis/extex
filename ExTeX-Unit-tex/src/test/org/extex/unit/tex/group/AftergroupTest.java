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

package org.extex.unit.tex.group;

import org.extex.test.NoFlagsPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\aftergroup</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class AftergroupTest extends NoFlagsPrimitiveTester {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(AftergroupTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public AftergroupTest(String arg) {

        super(arg, "aftergroup", " x");
    }

    /**
     * <testcase primitive="\aftergroup">
     *  Test case checking that <tt>\aftergroup</tt> needs a token argument.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        assertFailure(//--- input code ---
            "\\aftergroup",
            //--- output channel ---
            "Unexpected end of file while processing \\aftergroup");
    }

    /**
     * <testcase primitive="\aftergroup">
     *  Test case checking that a <tt>\aftergroup</tt> token of type letter
     *  is shifted to the end of the group.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(//--- input code ---
            "\\catcode`{=1" + "\\catcode`}=2" + "a{b\\aftergroup xd}e\\end",
            //--- output channel ---
            "abdxe" + TERM);
    }

    /**
     * <testcase primitive="\aftergroup">
     *  Test case checking that a <tt>\aftergroup</tt> token of type control
     *  sequence is expanded after the end of the group.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(//--- input code ---
            "\\catcode`{=1" + "\\catcode`}=2" + "\\def\\x{ttt}"
                    + "a{b\\aftergroup\\x d}e\\end",
            //--- output channel ---
            "abdttte" + TERM);
    }

}
