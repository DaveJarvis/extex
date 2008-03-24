/*
 * This file is part of ExBib a BibTeX compatible database.
 * Copyright (C) 2003-2008 Gerd Neugebauer
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.exbib.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * ...
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1.2 $
 */
public class TestBuiltin extends CommandLineTester {

    /**
     * The field <tt>clear</tt> contains the ...
     */
    private boolean clear = true;

    /**
     * The field <tt>verbose</tt> contains the ...
     */
    private boolean verbose = false;

    /**
     * Create a new object.
     */
    public TestBuiltin() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.main.CommandLineTester#setupAux(java.io.File,
     *      java.lang.String)
     */
    @Override
    protected void setupAux(File aux, String name) throws IOException {

        FileWriter out = new FileWriter(aux);
        out.write("\\relax\n");
        out.write("\\citation{*}\n");
        out.write("\\bibstyle{" + name + "}\n");
        out.write("\\bibdata{a}\n");
        out.close();
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    public void testAddPeriod1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "add_period", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testCallType1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "call_type", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testChangeCase1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "change_case", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testChrToInt1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "chr_to_int", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testCite1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "cite", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testConcat1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "concat", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testDuplicate1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "duplicate", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEmpty1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "empty", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEq1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "eq", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testFormatNames1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "format_names", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testGt1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "gt", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testIf1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "if", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testIntToChr1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "int_to_chr", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testIntToStr1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "int_to_str", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testLt1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "lt", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testMinus1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "minus", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testMissing1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "missing", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testNewline1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "newline", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testNumNames1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "num_names", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testPlus1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "plus", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testPop1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "pop", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testPreamble1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "preamble", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testPurify1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "purify", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testQuote1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "quote", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testSet1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "set", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testSkip1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "skip", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testStack1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "stack", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testSubstring1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "substring", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testSwap1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "swap", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testTextLength1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "text_length", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testTextPrefix1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "text_prefix", verbose,
            clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testTop1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "top", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testType1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "type", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testWarning1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "warning", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testWhile1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "while", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testWidth1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "width", verbose, clear);
    }

    /**
     * <testcase> TODO gene: missing JavaDoc </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testWrite1() throws Exception {

        runTest("src/test/resources/bibtex/builtin", "write", verbose, clear);
    }

}
