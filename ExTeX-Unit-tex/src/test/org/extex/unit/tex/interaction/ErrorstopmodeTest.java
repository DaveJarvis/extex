/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.interaction;

import org.extex.interpreter.Interpreter;
import org.extex.interpreter.interaction.Interaction;
import org.extex.test.NoFlagsPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\errorstopmode</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class ErrorstopmodeTest extends NoFlagsPrimitiveTester {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(ErrorstopmodeTest.class);
    }

    /**
     * Constructor for RelaxTest.
     *
     * @param arg the name
     */
    public ErrorstopmodeTest(String arg) {

        super(arg, "errorstopmode", "");
    }

    /**
     * <testcase primitive="\errorstopmode">
     *  Test case checking that errorstop mode is reported as 0.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        Interpreter interpreter = assertSuccess(//--- input code ---
            "\\errorstopmode" + "\\end",
            //--- output channel ---
            "");
        assertEquals(Interaction.ERRORSTOPMODE, //
            interpreter.getContext().getInteraction());
    }

    /**
     * <testcase primitive="\errorstopmode">
     *  Test case checking that errorstop mode is always global.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        Interpreter interpreter = assertSuccess(//--- input code ---
            "\\batchmode\\begingroup\\errorstopmode\\endgroup" + "\\end",
            //--- output channel ---
            "");
        assertEquals(Interaction.ERRORSTOPMODE, //
            interpreter.getContext().getInteraction());
    }

}
