/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.table;

import org.extex.test.ExTeXLauncher;

/**
 * This is a test suite for the primitive <tt>\noalign</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class NoalignTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(NoalignTest.class);
    }

    /**
     * Constructor for NoalignTest.
     *
     * @param arg the name
     */
    public NoalignTest(String arg) {

        super(arg);
    }

    /**
     * <testcase primitive="\noalign">
     *  Test case checking that <tt>\noalign</tt> needs to be used in a
     *  tabulating context.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLonelyCr() throws Exception {

        assertFailure(//--- input code ---
                "\\noalign" + "\\end ",
                //--- log message ---
                "Misplaced \\noalign");
    }

}
