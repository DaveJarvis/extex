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

package org.extex.unit.etex.group;

import org.extex.test.count.AbstractReadonlyCountRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \currentgrouplevel}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CurrentgrouplevelTest extends AbstractReadonlyCountRegisterTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(CurrentgrouplevelTest.class);
    }


    public CurrentgrouplevelTest() {

        super("currentgrouplevel", "0");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\currentgrouplevel"> Test case checking that
     * {@code \currentgrouplevel} inside a group returns 1.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLevel1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "{\\the\\currentgrouplevel}\\end",
            // --- log message ---
            "1" + TERM);
    }

    /**
     * <testcase primitive="\currentgrouplevel"> Test case checking that
     * {@code \currentgrouplevel} inside a group in a group returns 2.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLevel2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "{{\\the\\currentgrouplevel}}\\end",
            // --- log message ---
            "2" + TERM);
    }

    /**
     * <testcase primitive="\currentgrouplevel"> Test case checking that
     * {@code \currentgrouplevel} is count convertible.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConvertible1() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES
                    + "{{\\count0=\\currentgrouplevel \\the\\count0}}\\end",
            // --- log message ---
            "2" + TERM);
    }

    // TODO implement more primitive specific test cases
}
