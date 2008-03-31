/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.core.io.csf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

/**
 * This is a test suite for a {@link CsfReader}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CsfReaderTest {

    /**
     * The field <tt>READER</tt> contains the reader to test.
     */
    private static final CsfReader READER = new CsfReader();

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream("".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream("  ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \n "
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty file with comments is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" %abc \\xxx\n "
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> An unknown section leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testError1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\xyzzy "
                    .getBytes()));
        READER.read(reader);
    }

    /**
     * <testcase> A missing section leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testError2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" xyzzy "
                    .getBytes()));
        READER.read(reader);
    }

    /**
     * <testcase> The empty \lowercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\lowercase{} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \lowercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\lowercase \n {} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\lowercase \n { A a \n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
        assertEquals('x', ret.getLower('x'));
        assertEquals('X', ret.getUpper('X'));
        assertEquals('a', ret.getUpper('a'));
        assertEquals('a', ret.getLower('A'));
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError0() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowercase"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowercase\n"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowercase x"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowercase {"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \lowupcase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\lowupcase{} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \lowupcase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\lowupcase \n {} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowupcase section with one entry is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\lowupcase \n { a A \n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
        assertEquals('x', ret.getLower('x'));
        assertEquals('X', ret.getUpper('X'));
        assertEquals('a', ret.getLower('A'));
        assertEquals('A', ret.getUpper('a'));
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testlowupcaseError0() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowupcase"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowupcase\n"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowupcase x"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\lowupcase {"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order{} \n "
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\order \n {} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\order \n {\n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder4() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\order \n {a b c\n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder5() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\order \n {a\nb\nc\n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError0() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order\n"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order x"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order {"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError4() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order { a-"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError5() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\order { a_"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrderRange10() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\order \n {a-c\n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrderRange20() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\order \n {a_c\n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase{} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase \n {} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase \n {\n\n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase4() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase \n { a A \n} \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase5() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase \n { a A } \n ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ upeprcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError0() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\uppercase"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError1() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\uppercase\n"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError2() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\uppercase x"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError3() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(" \\uppercase {"
                    .getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError4() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase { a A ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError5() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase { a A b B".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError6() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase { a ".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section ... </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError7() throws Exception {

        Reader reader =
                new InputStreamReader(new ByteArrayInputStream(
                    " \\uppercase { a \n".getBytes()));
        CsfSorter ret = READER.read(reader);
        assertNotNull(ret);
    }

}
