/*
 * Copyright (C) 2008-2010 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

/**
 * This is a test suite for the code writer.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class CodeWriterTest {

    /**
     * Run the test case.
     * 
     * @param in the input
     * @param expected the expected output
     * @param column the expected column at the end
     * 
     * @throws IOException in case of an I/O error
     */
    private void run(String in, String expected, int column) throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        codeWriter.write(in);
        codeWriter.close();
        assertEquals(expected, w.toString());
        assertEquals(column, codeWriter.getColumn());
    }

    /**
     * <testcase> The empty string is just passed through. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test01() throws IOException {

        run("", "", 0);
    }

    /**
     * <testcase> A symbol "abc" is writeen unchanged. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test02() throws IOException {

        run("abc", "abc", 3);
    }

    /**
     * <testcase> An embedded newline is written to the output. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test03() throws IOException {

        run("ab\ncd", "ab\ncd", 2);
    }

    /**
     * <testcase> Double embedded newlines in row are written to the output.
     * </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test04() throws IOException {

        run("ab\n\ncd", "ab\n\ncd", 2);
    }

    /**
     * <testcase> A leading newline is discarded. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test05() throws IOException {

        run("\ncd", "cd", 2);
    }

    /**
     * <testcase> Triple embedded newlines in row are reduced to two newlines.
     * </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test06() throws IOException {

        run("ab\n\n\ncd", "ab\n\ncd", 2);
    }

    /**
     * <testcase> Tabs are expanded to spaces. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test07() throws IOException {

        run("\n\tcd", "  cd", 4);
    }

    /**
     * <testcase> Tabs are expanded to spaces. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test08() throws IOException {

        run("abc\tcd", "abc cd", 6);
    }

    /**
     * <testcase> Tabs are expanded to spaces. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test09() throws IOException {

        run("abcd\tcd", "abcd  cd", 8);
    }

    /**
     * <testcase> Tabs are expanded to spaces. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test10() throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        codeWriter.setTabSize(8);
        codeWriter.write("abc\tdef");
        codeWriter.close();
        assertEquals("abc     def", w.toString());
        assertEquals(11, codeWriter.getColumn());
    }

    /**
     * <testcase> CR do not contribute to the column count. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test11() throws IOException {

        run("abc\rcd", "abc\rcd", 5);
    }

    /**
     * <testcase> nl() indents correctly. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void testNl1() throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        codeWriter.setTabSize(8);
        codeWriter.nl(4);
        codeWriter.close();
        assertEquals("    ", w.toString());
        assertEquals(4, codeWriter.getColumn());
    }

    /**
     * <testcase> nl() produces a newlines. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void testNl2() throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        codeWriter.setTabSize(8);
        codeWriter.write("abc");
        codeWriter.nl(4);
        codeWriter.close();
        assertEquals("abc\n    ", w.toString());
        assertEquals(4, codeWriter.getColumn());
    }

    /**
     * <testcase> nl() discards leading newlines. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void testNl3() throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        codeWriter.setTabSize(8);
        codeWriter.write("abc\n\n");
        codeWriter.nl(4);
        codeWriter.close();
        assertEquals("abc\n\n    ", w.toString());
        assertEquals(4, codeWriter.getColumn());
    }

    /**
     * <testcase> Multi-valued write works. </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void testWrite1() throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter codeWriter = new CodeWriter(w);
        codeWriter.setTabSize(8);
        codeWriter.write("abc", "def");
        codeWriter.close();
        assertEquals("abcdef", w.toString());
        assertEquals(6, codeWriter.getColumn());
    }

}
