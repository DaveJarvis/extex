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
 * This is a test suite for the primitive <tt>\closein</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class CloseinTest extends NoFlagsButGlobalAndImmediatePrimitiveTester {

    /**
     * Method for running the tests standalone.
     *
     * @param args command line parameter
     */
    public static void main(final String[] args) {

        junit.textui.TestRunner.run(CloseinTest.class);
    }

    /**
     * Creates a new object.
     *
     * @param arg the name
     */
    public CloseinTest(final String arg) {

        super(arg, "closein", "1");
    }

    /**
     * <testcase primitive="\closein">
     *  Test case checking that a <tt>\closein</tt> works on unopened file
     *  handles.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test0() throws Exception {

        assertSuccess(//--- input code ---
                "\\closein 1",
                //--- output channel ---
                "");
    }

}