/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
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

    private static void assertCsf(CsfSorter csf) {

        assertNotNull(csf);
        for (int i = 0; i < 128; i++) {
            assertEquals(i, csf.getLower((char) i));
            assertEquals(i, csf.getUpper((char) i));
            assertEquals(i, csf.getOrder((char) i));
        }
        for (int i = 128; i < 256; i++) {
            assertEquals(i, csf.getLower((char) i));
            assertEquals(i, csf.getUpper((char) i));
            assertEquals(Integer.MAX_VALUE, csf.getOrder((char) i));
        }
    }

    /**
     * Create a reader with the given content.
     * 
     * @param content the content to be delivered by the reaer
     * 
     * @return the new reader
     */
    private static Reader makeReader(String content) {

        return new InputStreamReader(new ByteArrayInputStream(
            content.getBytes()));
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertCsf(READER.read(makeReader("")));
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertCsf(READER.read(makeReader("  ")));
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertCsf(READER.read(makeReader(" \n ")));
    }

    /**
     * <testcase> The empty file with comments is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertCsf(READER.read(makeReader(" %abc \\xxx\n ")));
    }

    /**
     * <testcase> An unknown section leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testError1() throws Exception {

        READER.read(makeReader(" \\xyzzy "));
    }

    /**
     * <testcase> A missing section leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testError2() throws Exception {

        READER.read(makeReader(" xyzzy "));
    }

    /**
     * <testcase> The empty \lowercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase1() throws Exception {

        assertCsf(READER.read(makeReader(" \\lowercase{} \n ")));
    }

    /**
     * <testcase> The empty \lowercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase2() throws Exception {

        assertCsf(READER.read(makeReader(" \\lowercase \n {} \n ")));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase3() throws Exception {

        CsfSorter csf =
                READER.read(makeReader(" \\lowercase \n { A a \n} \n "));
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getUpper('a'));
        assertEquals('a', csf.getLower('A'));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase4() throws Exception {

        CsfSorter csf =
                READER.read(makeReader(" \\lowercase \n { ^^41 a \n} \n "));
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getUpper('a'));
        assertEquals('a', csf.getLower('A'));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase5() throws Exception {

        CsfSorter csf =
                READER.read(makeReader(" \\lowercase \n { ^^41 a \n} \n "));
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getUpper('a'));
        assertEquals('a', csf.getLower('A'));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase6() throws Exception {

        CsfSorter csf =
                READER.read(makeReader(" \\lowercase \n { ^^4a a \n} \n "));
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getUpper('a'));
        assertEquals('a', csf.getLower((char) 0x4a));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase7() throws Exception {

        CsfSorter csf =
                READER.read(makeReader(" \\lowercase \n { ^^4A a \n} \n "));
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getUpper('a'));
        assertEquals('a', csf.getLower((char) 0x4A));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase8() throws Exception {

        CsfSorter csf =
                READER.read(makeReader(" \\lowercase \n { ^ a \n} \n "));
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getUpper('a'));
        assertEquals('a', csf.getLower('^'));
    }

    /**
     * <testcase> Invalid ^^ Notation leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseCaret1() throws Exception {

        READER.read(makeReader(" \\lowercase \n { ^a"));
    }

    /**
     * <testcase> Invalid ^^ Notation leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseCaret2() throws Exception {

        READER.read(makeReader(" \\lowercase \n { ^^x"));
    }

    /**
     * <testcase> Invalid ^^ Notation leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseCaret3() throws Exception {

        READER.read(makeReader(" \\lowercase \n { ^^1x"));
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError0() throws Exception {

        READER.read(makeReader(" \\lowercase"));
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError1() throws Exception {

        READER.read(makeReader(" \\lowercase\n"));
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError2() throws Exception {

        READER.read(makeReader(" \\lowercase x"));
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError3() throws Exception {

        READER.read(makeReader(" \\lowercase {"));
    }

    /**
     * <testcase> The empty \lowupcase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase1() throws Exception {

        assertCsf(READER.read(makeReader(" \\lowupcase{} \n ")));
    }

    /**
     * <testcase> The empty \lowupcase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase2() throws Exception {

        assertCsf(READER.read(makeReader(" \\lowupcase \n {} \n ")));
    }

    /**
     * <testcase> The \lowupcase section with one entry is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase3() throws Exception {

        CsfSorter ret =
                READER.read(makeReader(" \\lowupcase \n { a A \n} \n "));
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

        READER.read(makeReader(" \\lowupcase"));
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError1() throws Exception {

        READER.read(makeReader(" \\lowupcase\n"));
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError2() throws Exception {

        READER.read(makeReader(" \\lowupcase x"));
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError3() throws Exception {

        READER.read(makeReader(" \\lowupcase {"));
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder1() throws Exception {

        assertCsf(READER.read(makeReader(" \\order{} \n ")));
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder2() throws Exception {

        assertCsf(READER.read(makeReader(" \\order \n {} \n ")));
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder3() throws Exception {

        assertCsf(READER.read(makeReader(" \\order \n {\n} \n ")));
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder4() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\order \n {a b c\n} \n "));
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder5() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\order \n {a\nb\nc\n} \n "));
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError0() throws Exception {

        READER.read(makeReader(" \\order"));
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError1() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\order\n"));
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError2() throws Exception {

        READER.read(makeReader(" \\order x"));
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError3() throws Exception {

        READER.read(makeReader(" \\order {"));
    }

    /**
     * <testcase> The \order section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError4() throws Exception {

        READER.read(makeReader(" \\order { a-"));
    }

    /**
     * <testcase> The \order section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError5() throws Exception {

        READER.read(makeReader(" \\order { a_"));
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrderRange10() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\order \n {a-c\n} \n "));
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrderRange20() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\order \n {a_c\n} \n "));
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase1() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\uppercase{} \n "));
        assertCsf(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase2() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\uppercase \n {} \n "));
        assertCsf(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase3() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\uppercase \n {\n\n} \n "));
        assertCsf(ret);
    }

    /**
     * <testcase> The \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase4() throws Exception {

        CsfSorter ret =
                READER.read(makeReader(" \\uppercase \n { a A \n} \n "));
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase5() throws Exception {

        CsfSorter ret = READER.read(makeReader(" \\uppercase \n { a A } \n "));
        assertNotNull(ret);
    }

    /**
     * <testcase> The \ upeprcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError0() throws Exception {

        READER.read(makeReader(" \\uppercase"));
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError1() throws Exception {

        READER.read(makeReader(" \\uppercase\n"));
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError2() throws Exception {

        READER.read(makeReader(" \\uppercase x"));
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError3() throws Exception {

        READER.read(makeReader(" \\uppercase {"));
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError4() throws Exception {

        READER.read(makeReader(" \\uppercase { a A "));
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError5() throws Exception {

        READER.read(makeReader(" \\uppercase { a A b B"));
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError6() throws Exception {

        READER.read(makeReader(" \\uppercase { a "));
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError7() throws Exception {

        READER.read(makeReader(" \\uppercase { a \n"));
    }

}
