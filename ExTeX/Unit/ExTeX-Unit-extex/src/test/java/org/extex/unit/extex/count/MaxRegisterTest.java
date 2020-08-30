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

package org.extex.unit.extex.count;

import org.extex.test.count.AbstractNonGroupIntegerTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \maxRegister}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class MaxRegisterTest extends AbstractNonGroupIntegerTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(MaxRegisterTest.class);
    }


    public MaxRegisterTest() {

        super("maxRegister", "", "-1", DEFINE_BRACES + "\\namespace{system}");
        setConfig("extex-test");
    }

    /**
     * Test case showing that {@code \maxRegister} controls count registers
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount1() throws Exception {

        assertFailure(// --- input code ---
            DEFINE_BRACES + "{\\namespace{system}\\maxRegister=128}"
                    + "\\count129=1 ",
            // --- error channel ---
            "Bad register code (129)");
    }

    /**
     * Test case showing that {@code \maxRegister} controls count registers
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount2() throws Exception {

        assertSuccess(// --- input code ---
            DEFINE_BRACES + "{\\namespace{system}\\maxRegister=256}"
                    + "\\count129=1 ",
            // --- error channel ---
            "");
    }

    // TODO implement the primitive specific test cases
}
