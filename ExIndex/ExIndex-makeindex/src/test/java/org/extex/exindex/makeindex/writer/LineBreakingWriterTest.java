/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.writer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 5432 $
 */
public class LineBreakingWriterTest {

    /**
     * <testcase>No write operation leaves an empty output.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test001() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 72, "\t\t", 16);
        w.close();
        assertEquals("", sw.toString());
    }

    /**
     * <testcase>An empty string written leads to an empty output.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test002() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 72, "\t\t", 16);
        w.write("");
        w.close();
        assertEquals("", sw.toString());
    }

    /**
     * <testcase>A string without whitespace is passed through.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test003() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 72, "\t\t", 16);
        w.write("abc");
        w.close();
        assertEquals("abc", sw.toString());
    }

    /**
     * <testcase>A string with white-space is passed through.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test004() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 72, "\t\t", 16);
        w.write("abc def");
        w.close();
        assertEquals("abc def", sw.toString());
    }

    /**
     * <testcase>A string with several white-spaces is passed
     * through.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test005() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 72, "\t\t", 16);
        w.write("abc def ghi");
        w.close();
        assertEquals("abc def ghi", sw.toString());
    }

    /**
     * <testcase>A string with several white-spaces is passed
     * through.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test006() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 72, "\t\t", 16);
        w.write("abc\ndef\nghi");
        w.close();
        assertEquals("abc\ndef\nghi", sw.toString());
    }

    /**
     * <testcase>A long string with several white-spaces is broken.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test010() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 30, "\t\t", 16);
        w.write("a b c d e f g h i j k l m n o p q r s t u v w x y z");
        w.close();
        assertEquals("a b c d e f g h i j k l m n o \n\t\t"
                + "p q r s t u v \n\t\tw x y z", sw.toString());
    }

    /**
     * <testcase>A long string with several white-spaces and newline is
     * broken.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test011() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 30, "\t\t", 16);
        w.write("abcde\na b c d e f g h i j k l m n o p q r s t u v w x y z");
        w.close();
        assertEquals("abcde\na b c d e f g h i j k l m n o \n\t\t"
                + "p q r s t u v \n\t\tw x y z", sw.toString());
    }

    /**
     * <testcase>A long string with several white-spaces is broken.</testcase>
     * 
     * @throws IOException in case of an I/O error
     */
    @Test
    public void test020() throws IOException {

        StringWriter sw = new StringWriter();
        LineBreakingWriter w = new LineBreakingWriter(sw, 30, "\t\t", 16);
        w.write("abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz");
        w.close();
        assertEquals(
            "abcdefghijklmnopqrstuvwxyz \n\t\tabcdefghijklmnopqrstuvwxyz", sw
                .toString());
    }

}
