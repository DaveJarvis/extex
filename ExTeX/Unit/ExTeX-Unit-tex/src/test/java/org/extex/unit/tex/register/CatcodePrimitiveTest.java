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
 * This is a test suite for the primitive {@code \catcode}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class CatcodePrimitiveTest extends NoFlagsButGlobalPrimitiveTester {

    /**
     * Command line interface.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(CatcodePrimitiveTest.class);
    }


    public CatcodePrimitiveTest() {

        setPrimitive("catcode");setArguments("1=1 ");setPrepare("");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * needs a char. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof1() throws Exception {

        assertFailure("\\catcode ",
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * needs a token. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEof2() throws Exception {

        assertFailure("\\catcode 65 ",
            "Missing number, treated as zero");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * needs a range for the value. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRange1() throws Exception {

        assertFailure("\\catcode 1=-1",
            "Invalid code (-1), should be in the range 0..15");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * needs a range for the value. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRange2() throws Exception {

        assertFailure("\\catcode 1=16",
            "Invalid code (16), should be in the range 0..15");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * needs a range for the value. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRange4() throws Exception {

        assertFailure("\\catcode -1=1",
            "Bad character code (-1)");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 0. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue0() throws Exception {

        assertSuccess("\\catcode 1=0 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 1. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue1() throws Exception {

        assertSuccess("\\catcode 1=1 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 2. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue2() throws Exception {

        assertSuccess("\\catcode 1=2 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 3. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue3() throws Exception {

        assertSuccess("\\catcode 1=3 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 4. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue4() throws Exception {

        assertSuccess("\\catcode 1=4 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 5. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue5() throws Exception {

        assertSuccess("\\catcode 1=5 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 6. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue6() throws Exception {

        assertSuccess("\\catcode 1=6 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 7. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue7() throws Exception {

        assertSuccess("\\catcode 1= 7 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 8. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue8() throws Exception {

        assertSuccess("\\catcode 1= 8 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 9. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue9() throws Exception {

        assertSuccess("\\catcode 1= 9 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 10. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue10() throws Exception {

        assertSuccess("\\catcode 1= 10 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 11. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue11() throws Exception {

        assertSuccess("\\catcode 1= 11 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 12. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue12() throws Exception {

        assertSuccess("\\catcode 1= 12 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 13. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue13() throws Exception {

        assertSuccess("\\catcode 1= 13 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 14. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue14() throws Exception {

        assertSuccess("\\catcode 1= 14 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * takes a value of 15. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testValue15() throws Exception {

        assertSuccess("\\catcode 1= 15 \\end",
            "");
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * is count convertible. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCount1() throws Exception {

        assertSuccess("\\catcode 1=15 \\count0=\\catcode1 \\the\\count0\\end",
            "15" + TERM);
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * respects \afterassignment. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAfterassignment1() throws Exception {

        assertSuccess("\\afterassignment x--\\catcode 1=15 \\end",
            "--x" + TERM);
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * respects \global. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal1() throws Exception {

        assertSuccess(DEFINE_BRACES
                + "{\\catcode 1=15} \\count0=\\catcode1 \\the\\count0\\end",
            "12" + TERM);
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * respects \global. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal2() throws Exception {

        assertSuccess(DEFINE_BRACES + "{\\global\\catcode 1=15}"
                + "\\count0=\\catcode1 \\the\\count0\\end",
            "15" + TERM);
    }

    /**
     * <testcase primitive="\catcode"> Test case checking that {@code \catcode}
     * respects \globaldefs. 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGlobal3() throws Exception {

        assertSuccess(DEFINE_BRACES + "\\globaldefs=1{\\catcode 1=15}"
                + "\\count0=\\catcode1 \\the\\count0\\end",
            "15" + TERM);
    }

    // TODO implement more primitive specific test cases
}
