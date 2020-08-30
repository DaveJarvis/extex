/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.register.skip;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \gluestretch}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class GluestretchTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(GluestretchTest.class);
    }


    public GluestretchTest() {

        setConfig("etex-test");
    }

    /**
     * Test case showing that {@code \gluestretch} can not be used to assign something to it
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\gluestretch\\skip0=1pt ",
            // --- error channel ---
            "You can't use `\\gluestretch\' in vertical mode");
    }

    /**
     *  Test case showing that {@code \gluestretch} extracts the
     * correct value. In addition it shows that {@code \gluestretch} is
     * applicable to {@code \the}. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt" + "\\the\\gluestretch\\skip0 "
                    + "\\end",
            // --- output channel ---
            "2.0pt" + TERM);
    }

    /**
     *  Test case showing that {@code \gluestretch} extracts the
     * correct value. In addition it shows that {@code \gluestretch} is
     * assignable to a dimen register. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt" + "\\dimen0=\\gluestretch\\skip0 "
                    + "\\the\\dimen0" + "\\end",
            // --- output channel ---
            "2.0pt" + TERM);
    }

    /**
     *  Test case showing that {@code \gluestretch} extracts the
     * correct value. In addition it shows that {@code \gluestretch} is
     * assignable to a count register. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt" + "\\count0=\\gluestretch\\skip0 "
                    + "\\the\\count0" + "\\end",
            // --- output channel ---
            "196608" + TERM);
    }

    /**
     *  Test case showing that {@code \gluestretch} extracts the
     * correct value from an infinite glue. In addition it shows that
     * {@code \gluestretch} is applicable to {@code \the}. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3fill" + "\\the\\gluestretch\\skip0 "
                    + "\\end",
            // --- output channel ---
            "2.0pt" + TERM);
    }

}
