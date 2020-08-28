/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.math.delimiter;

import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive <tt>\left</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LeftTest extends AbstractMathTester {


    public LeftTest() {

        setPrimitive("left");setArguments("123 ");setPrepare("");
    }

    /**
     * <testcase>
     *  Test case checking that <tt>\left</tt> needs a delimiter.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testErr1() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_MATH + "$\\left $\\end",
            //--- output channel ---
            "Missing delimiter (. inserted)");
    }

    /**
     * <testcase>
     *  Test case checking that <tt>\left</tt> needs closing <tt>\right</tt>.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testErr2() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_MATH + "$\\left. $\\end",
            //--- output channel ---
            "Missing \\right. inserted");
    }

    /**
     * <testcase>
     *  Test case checking that <tt>\left</tt> needs closing <tt>\right</tt>.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    public void testErr3() throws Exception {

        assertFailure(
        //--- input code ---
            DEFINE_BRACES + DEFINE_MATH + "$\\left. } $\\end",
            //--- output channel ---
            "Too many }'s");
    }

    /**
     * <testcase>
     *  Test case checking that <tt>\left</tt> needs closing <tt>\right</tt>.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test1() throws Exception {

        assertSuccess(
        //--- input code ---
            DEFINE_BRACES + DEFINE_MATH + DEFINE_MATH_FONTS
                    + "$\\left. A \\right. $\\end",
            //--- output channel ---
            "A" + TERM);
    }

}
