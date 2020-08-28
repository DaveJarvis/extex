/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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
import java.io.IOException;
import java.io.InputStreamReader;

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
     * Verify a CSF for validity.
     * 
     * @param csf the CSF
     */
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
     * Check that the sorter has an expected mapping when exactly one character
     * is lowered.
     * 
     * @param csf the sorter
     * @param x the lowered letter
     * @param lowX the lower letter
     */
    private static void checkLower(CsfSorter csf, char x, char lowX) {

        assertNotNull(csf);
        for (int c = 0x00; c <= 0xff; c++) {
            assertEquals((x == c ? lowX : c), csf.getLower((char) c));
            assertEquals(c, csf.getUpper((char) c));
        }
    }

    /**
     * Check that the sorter has an expected mapping when exactly one character
     * is uppered.
     * 
     * @param csf the sorter
     * @param x the uppered letter
     * @param upX the upper letter
     */
    private static void checkUpper(CsfSorter csf, char x, char upX) {

        assertNotNull(csf);
        for (char c = '\0'; c < 0xff; c++) {
            assertEquals(c, csf.getLower(c));
            assertEquals((x == c ? upX : c), csf.getUpper(c));
        }
    }

    /**
     * Run a reader on some content.
     * 
     * @param content the content
     * @return the CSF
     * @throws IOException in case of an I/O error
     * @throws CsfException in case of an error
     */
    private CsfSorter runReader(String content)
            throws IOException,
                CsfException {

        InputStreamReader r =
                new InputStreamReader(new ByteArrayInputStream(
                    content.getBytes()));
        try {
            return READER.read(r, new CsfSorter());
        } finally {
            r.close();
        }
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test1() throws Exception {

        assertCsf(runReader(""));
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        assertCsf(runReader("  "));
    }

    /**
     * <testcase> The empty file is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        assertCsf(runReader(" \n "));
    }

    /**
     * <testcase> The empty file with comments is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        assertCsf(runReader(" %abc \\xxx\n "));
    }

    /**
     * <testcase> The empty file with comments is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        assertCsf(runReader(" ^^25abc \\xxx\n "));
    }

    /**
     * <testcase> An unknown section leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testError1() throws Exception {

        runReader(" \\xyzzy ");
    }

    /**
     * <testcase> A missing section leads to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testError2() throws Exception {

        runReader(" xyzzy ");
    }

    /**
     * <testcase> The empty \lowercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase1() throws Exception {

        assertCsf(runReader(" \\lowercase{} \n "));
    }

    /**
     * <testcase> The empty \lowercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase2() throws Exception {

        assertCsf(runReader(" \\lowercase \n {} \n "));
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase3() throws Exception {

        CsfSorter csf = runReader(" \\lowercase \n { A a \n} \n ");
        checkLower(csf, 'A', 'a');
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase4() throws Exception {

        CsfSorter csf = runReader(" \\lowercase \n { ^^41 a \n} \n ");
        checkLower(csf, 'A', 'a');
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase5() throws Exception {

        CsfSorter csf = runReader(" \\lowercase \n { ^^41 a \n} \n ");
        checkLower(csf, 'A', 'a');
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase6() throws Exception {

        CsfSorter csf = runReader(" \\lowercase \n { ^^4a a \n} \n ");
        checkLower(csf, (char) 0x4a, 'a');
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase7() throws Exception {

        CsfSorter csf = runReader(" \\lowercase \n { ^^4A a \n} \n ");
        checkLower(csf, (char) 0x4A, 'a');
    }

    /**
     * <testcase> The \lowercase section with one entry is ok. ^^ Notation is
     * accepted.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowercase8() throws Exception {

        CsfSorter csf = runReader(" \\lowercase \n { ^ a \n} \n ");
        checkLower(csf, '^', 'a');
    }

    /**
     * <testcase> Invalid ^^ Notation leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseCaret1() throws Exception {

        runReader(" \\lowercase \n { ^a");
    }

    /**
     * <testcase> Invalid ^^ Notation leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseCaret2() throws Exception {

        runReader(" \\lowercase \n { ^^x");
    }

    /**
     * <testcase> Invalid ^^ Notation leads to an error.</testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseCaret3() throws Exception {

        runReader(" \\lowercase \n { ^^1x");
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError0() throws Exception {

        runReader(" \\lowercase");
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError1() throws Exception {

        runReader(" \\lowercase\n");
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError2() throws Exception {

        runReader(" \\lowercase x");
    }

    /**
     * <testcase> The \lowercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowercaseError3() throws Exception {

        runReader(" \\lowercase {");
    }

    /**
     * <testcase> The empty \lowupcase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase1() throws Exception {

        assertCsf(runReader(" \\lowupcase{} \n "));
    }

    /**
     * <testcase> The empty \lowupcase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase2() throws Exception {

        assertCsf(runReader(" \\lowupcase \n {} \n "));
    }

    /**
     * <testcase> The \lowupcase section with one entry is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLowupcase3() throws Exception {

        CsfSorter csf = runReader(" \\lowupcase \n { a A \n} \n ");
        assertNotNull(csf);
        assertEquals('x', csf.getLower('x'));
        assertEquals('X', csf.getUpper('X'));
        assertEquals('a', csf.getLower('A'));
        assertEquals('A', csf.getUpper('a'));
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testlowupcaseError0() throws Exception {

        runReader(" \\lowupcase");
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError1() throws Exception {

        runReader(" \\lowupcase\n");
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError2() throws Exception {

        runReader(" \\lowupcase x");
    }

    /**
     * <testcase> The \lowupcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testLowupcaseError3() throws Exception {

        runReader(" \\lowupcase {");
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder1() throws Exception {

        assertCsf(runReader(" \\order{} \n "));
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder2() throws Exception {

        assertCsf(runReader(" \\order \n {} \n "));
    }

    /**
     * <testcase> The empty \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder3() throws Exception {

        assertCsf(runReader(" \\order \n {\n} \n "));
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder4() throws Exception {

        CsfSorter ret = runReader(" \\order \n {a b c\n} \n ");
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrder5() throws Exception {

        CsfSorter ret = runReader(" \\order \n {a\nb\nc\n} \n ");
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError0() throws Exception {

        runReader(" \\order");
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError1() throws Exception {

        CsfSorter ret = runReader(" \\order\n");
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError2() throws Exception {

        runReader(" \\order x");
    }

    /**
     * <testcase> The \order section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError3() throws Exception {

        runReader(" \\order {");
    }

    /**
     * <testcase> The \order section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError4() throws Exception {

        runReader(" \\order { a-");
    }

    /**
     * <testcase> The \order section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testOrderError5() throws Exception {

        runReader(" \\order { a_");
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrderRange10() throws Exception {

        CsfSorter ret = runReader(" \\order \n {a-c\n} \n ");
        assertNotNull(ret);
    }

    /**
     * <testcase> The \order section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOrderRange20() throws Exception {

        CsfSorter ret = runReader(" \\order \n {a_c\n} \n ");
        assertNotNull(ret);
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase1() throws Exception {

        CsfSorter csf = runReader(" \\uppercase{} \n ");
        checkUpper(csf, '\0', '\0');
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase2() throws Exception {

        CsfSorter csf = runReader(" \\uppercase \n {} \n ");
        checkUpper(csf, '\0', '\0');
    }

    /**
     * <testcase> The empty \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase3() throws Exception {

        CsfSorter csf = runReader(" \\uppercase \n {\n\n} \n ");
        checkUpper(csf, '\0', '\0');
    }

    /**
     * <testcase> The \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase4() throws Exception {

        CsfSorter csf = runReader(" \\uppercase \n { a A \n} \n ");
        checkUpper(csf, 'a', 'A');
    }

    /**
     * <testcase> The \ uppercase section is ok. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUppercase5() throws Exception {

        CsfSorter csf = runReader(" \\uppercase \n { a A } \n ");
        checkUpper(csf, 'a', 'A');
    }

    /**
     * <testcase> The \ upeprcase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError0() throws Exception {

        runReader(" \\uppercase");
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError1() throws Exception {

        runReader(" \\uppercase\n");
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError2() throws Exception {

        runReader(" \\uppercase x");
    }

    /**
     * <testcase> The \ uppercase section needs a block. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError3() throws Exception {

        runReader(" \\uppercase {");
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError4() throws Exception {

        runReader(" \\uppercase { a A ");
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError5() throws Exception {

        runReader(" \\uppercase { a A b B");
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError6() throws Exception {

        runReader(" \\uppercase { a ");
    }

    /**
     * <testcase> The \ uppercase section needs to be complete. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = CsfException.class)
    public void testUppercaseError7() throws Exception {

        runReader(" \\uppercase { a \n");
    }

}
