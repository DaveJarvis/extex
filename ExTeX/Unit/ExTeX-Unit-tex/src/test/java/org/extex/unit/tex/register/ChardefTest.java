/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive <tt>\chardef</tt>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ChardefTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(ChardefTest.class);
    }


    public ChardefTest() {

        setPrimitive("chardef");setArguments("\\x=123");setPrepare("");
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * needs a cs. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure("\\chardef ", //
            "Missing control sequence inserted");
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * needs a char as second argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof2() throws Exception {

        assertFailure("\\chardef\\x ", //
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * complains about a bad character code. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testError1() throws Exception {

        assertFailure(DEFINE_BRACES + "\\chardef\\x -1 \\x\\end", //
            "Bad character code (-1)");
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * can be used to define a control sequence carrying a character.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess("\\chardef\\x 65 \\x\\end", //
            "A" + TERM);
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * can be used to define a control sequence which is convertible into a
     * count. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess("\\chardef\\x 65 \\count0=\\x \\the\\count0\\end", //
            "65" + TERM);
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * can be used to define a control sequence which is applicable to
     * <tt>\the</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess("\\chardef\\x 65 \\the\\x\\end", //
            "65" + TERM);
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * can be used to define a control sequence which is applicable to
     * <tt>\show</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertOutput("\\chardef\\x 65 \\show\\x\\end", //
            "> \\x=\\char\"41.\n", "");
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * respects grouping. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        assertSuccess(DEFINE_BRACES
                + "\\chardef\\x 65 {\\chardef\\x 66}\\x\\end", //
            "A" + TERM);
    }

    /**
     * <testcase primitive="\chardef"> Test case checking that <tt>\chardef</tt>
     * consumes the <tt>\global</tt> flag. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6() throws Exception {

        assertSuccess(DEFINE_BRACES
                + "\\chardef\\x 65 {\\global\\chardef\\x 66}\\x\\end", //
            "B" + TERM);
    }

}
