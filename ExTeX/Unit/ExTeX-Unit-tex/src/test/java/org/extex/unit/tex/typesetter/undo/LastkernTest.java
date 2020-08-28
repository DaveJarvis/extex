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

package org.extex.unit.tex.typesetter.undo;

import org.extex.unit.tex.register.dimen.AbstractReadonlyDimenRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\lastkern</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LastkernTest extends AbstractReadonlyDimenRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(LastkernTest.class);
    }


    public LastkernTest() {

        super("lastkern", "", "0.0pt");
    }

    /**
     * <testcase> Test case checking that <tt>\lastkern</tt> on an empty list
     * returns 0pt. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertOutput(
        // --- input code ---
            "\\showthe\\lastkern\\end",
            // --- output channel ---
            "> 0.0pt.\n",
            //
            null);
    }

    /**
     * <testcase> Test case checking that <tt>\lastkern</tt> is showable.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertOutput(
        // --- input code ---
            "a\\kern123pt\\showthe\\lastkern\\end",
            // --- output channel ---
            "> 123.0pt.\n",
            //
            null);
    }

    /**
     * <testcase> Test case checking that \relax does not produce a node.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertOutput(
        // --- input code ---
            "a\\kern123pt\\relax\\showthe\\lastkern\\end",
            // --- output channel ---
            "> 123.0pt.\n",
            //
            null);
    }

    /**
     * <testcase> Test case checking that <tt>\lastkern</tt> returns 0pt if
     * the last node is not a kern node. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertOutput(
        // --- input code ---
            "a\\showthe\\lastkern\\end",
            // --- output channel ---
            "> 0.0pt.\n",
            //
            null);
    }

    /**
     * <testcase> Test case checking that <tt>\lastkern</tt> is a dimen value.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertOutput(
        // --- input code ---
            "a\\kern123pt\\dimen0=\\lastkern x\\showthe\\dimen0\\end",
            // --- output channel ---
            "> 123.0pt.\n",
            //
            null);
    }

    /**
     * <testcase> Test case checking that <tt>\lastkern</tt> is count
     * convertible. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertOutput(
        // --- input code ---
            "a\\kern123pt\\count0=\\lastkern x\\showthe\\count0\\end",
            // --- output channel ---
            "> 8060928.\n",
            //
            null);
    }

}
