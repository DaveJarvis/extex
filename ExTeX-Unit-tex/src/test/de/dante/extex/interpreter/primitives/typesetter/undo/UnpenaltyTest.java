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

package de.dante.extex.interpreter.primitives.typesetter.undo;

import de.dante.test.NoFlagsPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>&#x5c;unpenalty</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class UnpenaltyTest extends NoFlagsPrimitiveTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(UnpenaltyTest.class);
    }

    /**
     * Constructor for UnpenaltyTest.
     *
     * @param arg the name
     */
    public UnpenaltyTest(final String arg) {

        super(arg, "unpenalty", "", "a");
    }

    /**
     * <testcase primitive="&#x5c;unpenalty">
     *  Test case checking that <tt>&#x5c;unpenalty</tt> need some node in the
     *  current list.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErr1() throws Exception {

        assertFailure(
        //--- input code ---
                "\\unpenalty\\end ",
                //--- error channel ---
                "You can't use `\\unpenalty' in vertical mode");
    }

    /**
     * <testcase primitive="&#x5c;unpenalty">
     *  Test case checking that <tt>&#x5c;unpenalty</tt> need some node in the
     *  current list.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testErr2() throws Exception {

        assertFailure(
        //--- input code ---
                "\\penalty123 \\unpenalty\\unpenalty\\end ",
                //--- error channel ---
                "You can't use `\\unpenalty' in vertical mode");
    }

    /**
     * <testcase primitive="&#x5c;unpenalty">
     *  Test case checking that <tt>&#x5c;unpenalty</tt> does not touch a char node
     *  at the end of the current list.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        //--- input code ---
                "a\\unpenalty\\end ",
                //--- output channel ---
                "" + //
                        "\\vbox(8.0pt+0.0pt)x3000.0pt\n" + //
                        ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" + //
                        "..a\n");
    }

    /**
     * <testcase primitive="&#x5c;unpenalty">
     *  Test case checking that <tt>&#x5c;unpenalty</tt> takes the last penalty from
     *  the current list.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertSuccess(showNodesProperties(),
        //--- input code ---
                "a\\penalty123 \\unpenalty\\end ",
                //--- output channel ---
                "" + //
                        "\\vbox(8.0pt+0.0pt)x3000.0pt\n" + //
                        ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" + //
                        "..a\n");
    }

    //TODO implement primitive specific test cases

}
