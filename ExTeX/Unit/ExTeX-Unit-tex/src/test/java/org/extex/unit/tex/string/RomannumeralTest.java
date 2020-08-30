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

package org.extex.unit.tex.string;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \romannumeral}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class RomannumeralTest extends NoFlagsPrimitiveTester {

    /**
     * Method for running the tests standalone.
     * 
     * @param args command line parameter
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(RomannumeralTest.class);
    }


    public RomannumeralTest() {

        setPrimitive("romannumeral");setArguments("1");setPrepare("");
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on a count with the value 2 gives {@code ii}.
     *
     * 
     * @throws Exception in case of an error
     * 
     */
    @Test
    public void testCount1() throws Exception {

        assertSuccess("\\count0=2 \\romannumeral\\count0 \\end", "ii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on -1 gives the empty token list.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus1() throws Exception {

        assertSuccess("\\romannumeral -1 \\end", "");
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 0 gives the empty token list.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test0() throws Exception {

        assertSuccess("\\romannumeral 0 \\end", "");
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 1 gives {@code i}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertSuccess("\\romannumeral 1 \\end", "i" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 2 gives {@code ii}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertSuccess("\\romannumeral 2 \\end", "ii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 3 gives {@code iii}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertSuccess("\\romannumeral 3 \\end", "iii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 4 gives {@code iv}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertSuccess("\\romannumeral 4 \\end", "iv" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 5 gives {@code v}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        assertSuccess("\\romannumeral 5 \\end", "v" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 6 gives {@code vi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6() throws Exception {

        assertSuccess("\\romannumeral 6 \\end", "vi" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 7 gives {@code vii}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test7() throws Exception {

        assertSuccess("\\romannumeral 7 \\end", "vii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 8 gives {@code viii}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test8() throws Exception {

        assertSuccess("\\romannumeral 8 \\end", "viii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 9 gives {@code ix}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test9() throws Exception {

        assertSuccess("\\romannumeral 9 \\end", "ix" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 10 gives {@code x}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test10() throws Exception {

        assertSuccess("\\romannumeral 10 \\end", "x" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 11 gives {@code xi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test11() throws Exception {

        assertSuccess("\\romannumeral 11 \\end", "xi" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 12 gives {@code xii}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test12() throws Exception {

        assertSuccess("\\romannumeral 12 \\end", "xii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 13 gives {@code xiii}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test13() throws Exception {

        assertSuccess("\\romannumeral 13 \\end", "xiii" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 14 gives {@code xiv}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test14() throws Exception {

        assertSuccess("\\romannumeral 14 \\end", "xiv" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 15 gives {@code xv}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test15() throws Exception {

        assertSuccess("\\romannumeral 15 \\end", "xv" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 49 gives {@code xlix}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test49() throws Exception {

        assertSuccess("\\romannumeral 49 \\end", "xlix" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 50 gives {@code l}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test50() throws Exception {

        assertSuccess("\\romannumeral 50 \\end", "l" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 51 gives {@code li}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test51() throws Exception {

        assertSuccess("\\romannumeral 51 \\end", "li" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 99 gives {@code xcix}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test99() throws Exception {

        assertSuccess("\\romannumeral 99 \\end", "xcix" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 100 gives {@code c}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test100() throws Exception {

        assertSuccess("\\romannumeral 100 \\end", "c" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 101 gives {@code ci}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test101() throws Exception {

        assertSuccess("\\romannumeral 101 \\end", "ci" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 499 gives {@code cdxcix}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test499() throws Exception {

        assertSuccess("\\romannumeral 499 \\end", "cdxcix" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 500 gives {@code d}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test500() throws Exception {

        assertSuccess("\\romannumeral 500 \\end", "d" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 501 gives {@code di}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test501() throws Exception {

        assertSuccess("\\romannumeral 501 \\end", "di" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 999 gives {@code cmxcix}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test999() throws Exception {

        assertSuccess("\\romannumeral 999 \\end", "cmxcix" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 1000 gives {@code m}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1000() throws Exception {

        assertSuccess("\\romannumeral 1000 \\end", "m" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 1001 gives {@code mi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1001() throws Exception {

        assertSuccess("\\romannumeral 1001 \\end", "mi" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 1999 gives {@code mcmxcix}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1999() throws Exception {

        assertSuccess("\\romannumeral 1999 \\end", "mcmxcix" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 2000 gives {@code mm}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2000() throws Exception {

        assertSuccess("\\romannumeral 2000 \\end", "mm" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 3001 gives {@code mmmi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3001() throws Exception {

        assertSuccess("\\romannumeral 3001 \\end", "mmmi" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 4001 gives {@code mmmmi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4001() throws Exception {

        assertSuccess("\\romannumeral 4001 \\end", "mmmmi" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 5001 gives {@code mmmmmi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5001() throws Exception {

        assertSuccess("\\romannumeral 5001 \\end", "mmmmmi" + TERM);
    }

    /**
     * <testcase primitive="\romannumeral"> Test case checking that
     * {@code \romannumeral} on 6001 gives {@code mmmmmmi}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test6001() throws Exception {

        assertSuccess("\\romannumeral 6001 \\end", "mmmmmmi" + TERM);
    }
}
