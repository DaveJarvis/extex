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
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\endgroup</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class EndgroupTest extends NoFlagsPrimitiveTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(EndgroupTest.class);
    }

    /**
     * Creates a new object.
     * 
     * @param arg the name
     */
    public EndgroupTest(String arg) {

        super(arg, "endgroup", "", "\\begingroup");
    }

    /**
     * <testcase primitive="\endgroup"> Test case checking that a lonely
     * <tt>\endgroup</tt> leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertFailure(// --- input code ---
            "\\endgroup",
            // --- log message ---
            "Too many }'s");
    }

    /**
     * <testcase primitive="\endgroup"> Test case checking that
     * <tt>\endgroup</tt> works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\begingroup \\endgroup",
            // --- output channel ---
            "");
    }

}
