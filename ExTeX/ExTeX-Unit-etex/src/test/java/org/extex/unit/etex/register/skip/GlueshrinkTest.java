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

package org.extex.unit.etex.register.skip;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\glueshrink</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class GlueshrinkTest extends ExTeXLauncher {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(GlueshrinkTest.class);
    }

    /**
     * Creates a new object.
     */
    public GlueshrinkTest() {

        super();
        setConfig("etex-test");
    }

    /**
     * <testcase> Test case showing that <tt>\glueshrink</tt> can not be used
     * to assign something to it. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(// --- input code ---
            "\\glueshrink\\skip0=1pt ",
            // --- error channel ---
            "You can't use `\\glueshrink\' in vertical mode");
    }

    /**
     * <testcase> Test case showing that <tt>\glueshrink</tt> extracts the
     * correct value. In addition it shows that <tt>\glueshrink</tt> is
     * applicable to <tt>\the</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt" + "\\the\\glueshrink\\skip0 "
                    + "\\end",
            // --- output channel ---
            "3.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\glueshrink</tt> extracts the
     * correct value. In addition it shows that <tt>\glueshrink</tt> is
     * assignable to a dimen register. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt" + "\\dimen0=\\glueshrink\\skip0 "
                    + "\\the\\dimen0" + "\\end",
            // --- output channel ---
            "3.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\glueshrink</tt> extracts the
     * correct value. In addition it shows that <tt>\glueshrink</tt> is
     * assignable to a count register. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt" + "\\count0=\\glueshrink\\skip0 "
                    + "\\the\\count0" + "\\end",
            // --- output channel ---
            "196608" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\glueshrink</tt> extracts the
     * correct value from an infinite glue. In addition it shows that
     * <tt>\glueshrink</tt> is applicable to <tt>\the</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3fill" + "\\the\\glueshrink\\skip0 "
                    + "\\end",
            // --- output channel ---
            "3.0pt" + TERM);
    }

}
