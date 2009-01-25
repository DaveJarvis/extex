/*
 * Copyright (C) 2008-2009 The ExTeX Group and individual authors listed below
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
     * 
     * @throws IOException in case of an I/O error
     */
    private void run(String in, String expected) throws IOException {

        StringWriter w = new StringWriter();
        CodeWriter cw = new CodeWriter(w);
        cw.write(in);
        cw.close();
        assertEquals(expected, w.toString());
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test01() throws IOException {

        run("", "");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test02() throws IOException {

        run("abc", "abc");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test03() throws IOException {

        run("ab\ncd", "ab\ncd");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test04() throws IOException {

        run("ab\n\ncd", "ab\n\ncd");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test05() throws IOException {

        run("\ncd", "cd");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test06() throws IOException {

        run("ab\n\n\ncd", "ab\n\ncd");
    }

    /**
     * <testcase> ... </testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test07() throws IOException {

        run("\n\tcd", "  cd");
    }

}
