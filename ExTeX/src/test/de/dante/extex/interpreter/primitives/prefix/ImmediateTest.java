/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.primitives.prefix;

/**
 * This is a test suite for the primitive <tt>\immediate</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ImmediateTest extends PrefixTester {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(ImmediateTest.class);
    }

    /**
     * Constructor for RelaxTest.
     *
     * @param arg the name
     */
    public ImmediateTest(final String arg) {

        super(arg, "immediate");
    }

    /**
     * <testcase primitive="\immediate">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test10() throws Exception {

        assertOutput(showPrefixProperties(),
        //--- input code ---
                "\\immediate\\showprefix\\end",
                //--- error channel ---
                "immediate\n",
                //--- output channel ---
                "");
    }

    /**
     * <testcase primitive="\immediate">
     *  Test case checking that double <tt>\immediate</tt> has the same effect as
     *  one.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test11() throws Exception {

        assertOutput(showPrefixProperties(),
        //--- input code ---
                "\\immediate\\immediate\\showprefix\\end",
                //--- error channel ---
                "immediate\n",
                //--- output channel ---
                "");
    }

    /**
     * <testcase primitive="\immediate">
     *  Test case checking that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        assertOutput(showPrefixProperties(),
        //--- input code ---
                "\\immediate\\long\\showprefix\\end",
                //--- error channel ---
                "long and immediate\n",
                //--- output channel ---
                "");
    }

}
