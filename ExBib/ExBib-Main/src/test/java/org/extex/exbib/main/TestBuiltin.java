/*
 * Copyright (C) 2003-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test suite for built-ins.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TestBuiltin {

    /**
     * The field <tt>DATA_DIR</tt> contains the directory containing database,
     * styles and results.
     */
    private static final String DATA_DIR = "src/test/resources/bibtex/builtin";

    /**
     * Create a new object.
     */
    public TestBuiltin() {

        super();
    }

    /**
     * Run a test case.
     * 
     * @param style the file name
     * 
     * @throws IOException in case of an I/O error
     */
    private void runTest(String style) throws IOException {

        TRunner.runTest(DATA_DIR + "/" + style, //
            DATA_DIR + "/a", //
            "*", //
            new File(DATA_DIR, style + ".result"));
    }

    /**
     * <testcase> Run test for add.period$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAddPeriod1() throws Exception {

        runTest("add_period");
    }

    /**
     * <testcase> Run tests for call.type$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCallType1() throws Exception {

        runTest("call_type");
    }

    /**
     * <testcase> Run tests for change.case$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChangeCase1() throws Exception {

        runTest("change_case");
    }

    /**
     * <testcase> Run tests for chr.to.int$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testChrToInt1() throws Exception {

        runTest("chr_to_int");
    }

    /**
     * <testcase> Run tests for cite$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCite1() throws Exception {

        runTest("cite");
    }

    /**
     * <testcase> Run tests for concat$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConcat1() throws Exception {

        runTest("concat");
    }

    /**
     * <testcase> Run tests for duplicate$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDuplicate1() throws Exception {

        runTest("duplicate");
    }

    /**
     * <testcase> Run tests for empty$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty1() throws Exception {

        runTest("empty");
    }

    /**
     * <testcase> Run tests for =$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEq1() throws Exception {

        runTest("eq");
    }

    /**
     * <testcase> Run tests for format.names$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testFormatNames1() throws Exception {

        runTest("format_names");
    }

    /**
     * <testcase> Run tests for &gt;$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testGt1() throws Exception {

        runTest("gt");
    }

    /**
     * <testcase> Run tests for if$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIf1() throws Exception {

        runTest("if");
    }

    /**
     * <testcase> Run tests for int.to.chr$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntToChr1() throws Exception {

        runTest("int_to_chr");
    }

    /**
     * <testcase> Run tests for int.to.str$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIntToStr1() throws Exception {

        runTest("int_to_str");
    }

    /**
     * <testcase> Run tests for &lt;. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLt1() throws Exception {

        runTest("lt");
    }

    /**
     * <testcase> Run tests for -. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus1() throws Exception {

        runTest("minus");
    }

    /**
     * <testcase> Run tests for missing$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissing1() throws Exception {

        runTest("missing");
    }

    /**
     * <testcase> Run tests for newline$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNewline1() throws Exception {

        runTest("newline");
    }

    /**
     * <testcase> Run tests for num.names$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testNumNames1() throws Exception {

        runTest("num_names");
    }

    /**
     * <testcase> Run tests for plus$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPlus1() throws Exception {

        runTest("plus");
    }

    /**
     * <testcase> Run tests for pop$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPop1() throws Exception {

        runTest("pop");
    }

    /**
     * <testcase> Run tests for preamble$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPreamble1() throws Exception {

        runTest("preamble");
    }

    /**
     * <testcase> Run tests for purify$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testPurify1() throws Exception {

        runTest("purify");
    }

    /**
     * <testcase> Run tests for quote$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testQuote1() throws Exception {

        runTest("quote");
    }

    /**
     * <testcase> Run tests for set$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSet1() throws Exception {

        runTest("set");
    }

    /**
     * <testcase> Run tests for skip$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSkip1() throws Exception {

        runTest("skip");
    }

    /**
     * <testcase> Run tests for stack$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStack1() throws Exception {

        runTest("stack");
    }

    /**
     * <testcase> Run tests for substring$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSubstring1() throws Exception {

        runTest("substring");
    }

    /**
     * <testcase> Run tests for swap$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSwap1() throws Exception {

        runTest("swap");
    }

    /**
     * <testcase> Run tests for text.length$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTextLength1() throws Exception {

        runTest("text_length");
    }

    /**
     * <testcase> Run tests for text.prefix$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTextPrefix1() throws Exception {

        runTest("text_prefix");
    }

    /**
     * <testcase> Run tests for top$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTop1() throws Exception {

        runTest("top");
    }

    /**
     * <testcase> Run tests for type$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType1() throws Exception {

        runTest("type");
    }

    /**
     * <testcase> Run tests for warning$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWarning1() throws Exception {

        runTest("warning");
    }

    /**
     * <testcase> Run tests for while$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWhile1() throws Exception {

        runTest("while");
    }

    /**
     * <testcase> Run tests for width$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testWidth1() throws Exception {

        runTest("width");
    }

    /**
     * <testcase> Run tests for write$. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testWrite1() throws Exception {

        runTest("write");
    }

}
