/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.info;

import org.extex.test.NoFlagsPrimitiveTester;

/**
 * This is a test suite for the primitive <tt>\showtokens</tt>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4808 $
 */
public class ShowtokensTest extends NoFlagsPrimitiveTester {

    /**
     * Constructor for JobnameTest.
     *
     * @param arg the name
     */
    public ShowtokensTest(String arg) {

        super(arg, "showtokens", "{abc}", "", "> abc.\n");
        setConfig("etex-test");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testError1() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens{xxx",
                //--- output channel ---
                "File ended while scanning text of \\showtokens");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testError2() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens ",
                //--- output channel ---
                "Unexpected end of file while processing tokens");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test1() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens{\\relax}"
                + "\\end",
                //--- output channel ---
                "> \\relax.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test2() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens{\\jobname}"
                + "\\end",
                //--- output channel ---
                "> \\jobname.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test3() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_BRACES + "\\catcode`\\~=13 "
                + "\\showtokens{x~x}"
                + "\\end",
                //--- output channel ---
                "> x~x.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test4() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_CATCODES
                + "\\showtokens{x#x}"
                + "\\end",
                //--- output channel ---
                "> x#x.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test5() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_CATCODES
                + "\\showtokens{x_x}"
                + "\\end",
                //--- output channel ---
                "> x_x.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test6() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_CATCODES
                + "\\showtokens{x^x}"
                + "\\end",
                //--- output channel ---
                "> x^x.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test12() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens\\expandafter{\\jobname}"
                + "\\end",
                //--- output channel ---
                "> texput.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test21() throws Exception {

        assertFailure(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens\\relax",
                //--- output channel ---
                "Missing `{' inserted");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test22() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_BRACES
                + "\\toks0={abc}"
                + "\\showtokens\\toks0"
                + "\\end",
                //--- output channel ---
                "> abc.\n",
                "");
    }

    /**
     * <testcase primitive="\showtokens">
     *  Test case checking that <tt>\showtokens</tt> ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void test23() throws Exception {

        assertOutput(//--- input code ---
                DEFINE_BRACES
                + "\\showtokens{x{y}z}"
                + "\\end",
                //--- output channel ---
                "> x{y}z.\n",
                "");
    }

    //TODO implement the primitive specific test cases

}
