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

import org.extex.test.count.AbstractReadonlyCountRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\gluestretchorder</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class GluestretchorderTest extends AbstractReadonlyCountRegisterTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(GluestretchorderTest.class);
    }

    /**
     * Creates a new object.
     */
    public GluestretchorderTest() {

        super("gluestretchorder", "\\skip0 ", "0");
        setConfig("etex-test");
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fixed stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is applicable to <tt>\the</tt>.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt"
                    + "\\the\\gluestretchorder\\skip0 " + "\\end",
            // --- output channel ---
            "0" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fixed stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a dimen register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt"
                    + "\\dimen0=\\gluestretchorder\\skip0 " + "\\the\\dimen0"
                    + "\\end",
            // --- output channel ---
            "0.0pt" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fixed stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a count register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2pt minus 3pt"
                    + "\\count0=\\gluestretchorder\\skip0 " + "\\the\\count0"
                    + "\\end",
            // --- output channel ---
            "0" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fil stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is applicable to <tt>\the</tt>.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2fil minus 3fil"
                    + "\\the\\gluestretchorder\\skip0 " + "\\end",
            // --- output channel ---
            "1" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fil stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a dimen register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2fil minus 3fil"
                    + "\\dimen0=\\gluestretchorder\\skip0 " + "\\the\\dimen0"
                    + "\\end",
            // --- output channel ---
            "0.00002pt" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fil stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a count register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2fil minus 3fil"
                    + "\\count0=\\gluestretchorder\\skip0 " + "\\the\\count0"
                    + "\\end",
            // --- output channel ---
            "1" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fill stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is applicable to <tt>\the</tt>.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test21() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2fill minus 3fill"
                    + "\\the\\gluestretchorder\\skip0 " + "\\end",
            // --- output channel ---
            "2" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fill stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a dimen register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test22() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2fill minus 3fill"
                    + "\\dimen0=\\gluestretchorder\\skip0 " + "\\the\\dimen0"
                    + "\\end",
            // --- output channel ---
            "0.00003pt" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a fill stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a count register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test23() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2fill minus 3fill"
                    + "\\count0=\\gluestretchorder\\skip0 " + "\\the\\count0"
                    + "\\end",
            // --- output channel ---
            "2" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a filll stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is applicable to <tt>\the</tt>.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test31() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2filll minus 3filll"
                    + "\\the\\gluestretchorder\\skip0 " + "\\end",
            // --- output channel ---
            "3" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a filll stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a dimen register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test32() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2filll minus 3filll"
                    + "\\dimen0=\\gluestretchorder\\skip0 " + "\\the\\dimen0"
                    + "\\end",
            // --- output channel ---
            "0.00005pt" + TERM);
    }

    /**
     * <testcase> Test case showing that <tt>\gluestretchorder</tt> extracts
     * the correct value from a filll stretch. In addition it shows that
     * <tt>\gluestretchorder</tt> is assignable to a count register.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test33() throws Exception {

        assertSuccess(// --- input code ---
            "\\skip0=1pt plus 2filll minus 3filll"
                    + "\\count0=\\gluestretchorder\\skip0 " + "\\the\\count0"
                    + "\\end",
            // --- output channel ---
            "3" + TERM);
    }

}
