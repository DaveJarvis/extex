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

package org.extex.unit.omega.typesetter.spacing;

import org.extex.unit.tex.typesetter.spacing.AbstractHfillTester;

/**
 * This is a test suite for the primitive <tt>\hfi</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class HfiTest extends AbstractHfillTester {

    /**
     * Command line interface.
     * @param args the arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(HfiTest.class);
    }

    /**
     * Constructor for HfiTest.
     *
     * @param arg the name
     */
    public HfiTest(String arg) {

        super(arg, "hfi", "");
        setConfig("omega-test");
    }

    /**
     * <testcase primitive="\hfi">
     *  Test case checking that <tt>\hfi</tt> is ignored at the beginning
     *  of a paragraph.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertSuccess(showNodesProperties(),
        //--- input code ---
                "\\hfi\\end ",
                //--- output channel ---
                "");
    }

}