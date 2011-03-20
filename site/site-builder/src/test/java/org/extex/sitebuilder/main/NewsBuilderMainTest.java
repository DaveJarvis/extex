/*
 * Copyright (C) 2011 The ExTeX Group and individual authors listed below
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

package org.extex.sitebuilder.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;

import org.junit.Test;

/**
 * This is a test suite for the {@link NewsBuilderMain}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class NewsBuilderMainTest {

    /**
     * Run the news builder with a given command line.
     * 
     * @param args the command line arguments
     * @param expected the output expected on stderr or <code>null</code> to
     *        suppress the comparison
     * 
     * @return the exit code
     */
    private static final int run(String[] args, String expected) {

        PrintStream err = System.err;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setErr(new PrintStream(out, true));
        try {
            int exit = NewsBuilderMain.run(args);
            if (expected != null) {
                assertEquals(expected, out.toString().replaceAll("\r", ""));
            }
            return exit;
        } finally {
            System.setErr(err);
        }
    }

    /**
     * <testcase> An rss file is created if everything is all right. </testcase>
     * 
     */
    @Test
    public void test01() {

        File file = new File("target/test-site/rss/2.0/news.rss");
        if (file.exists()) {
            assertTrue("deletion failed: " + file, file.delete());
        }
        try {
            assertEquals(0, run(new String[]{}, null));
            assertTrue("Expected file is missing: " + file.toString(),
                file.exists());
        } finally {
            // assertTrue("deletion failed: " + file, file.delete());
        }
    }

    /**
     * <testcase> A missing argument for the option <tt>-baseDirectory</tt> is
     * recognized. </testcase>
     * 
     */
    @Test
    public void testBase01() {

        assertEquals(-1,
            run(new String[]{"-base"}, "*** Missing argument for -base\n"));
    }

    /**
     * <testcase> A missing argument for the option <tt>-baseDirectory</tt> is
     * recognized. </testcase>
     * 
     */
    @Test
    public void testBase02() {

        assertEquals(-1,
            run(new String[]{"--base"}, "*** Missing argument for -base\n"));
    }

    /**
     * <testcase> An error is raised if base is not a file. </testcase>
     * 
     */
    @Test
    public void testBase03() {

        File file = new File("target/test-site/rss/2.0/news.rss");
        if (file.exists()) {
            assertTrue("deletion failed: " + file, file.delete());
        }
        try {
            assertEquals(-1, run(new String[]{"-base", "target/xyzzy"}, null));
            assertFalse("Unexpected file: " + file.toString(), file.exists());
        } finally {
            file.deleteOnExit();
        }
    }

    /**
     * <testcase> An error is raised if base is not a file. </testcase>
     * 
     */
    @Test
    public void testBase04() {

        File file = new File("target/test-site/rss/2.0/news.rss");
        if (file.exists()) {
            assertTrue("deletion failed: " + file, file.delete());
        }
        try {
            assertEquals(-1, run(new String[]{"target/xyzzy"}, null));
            assertFalse("Unexpected file: " + file.toString(), file.exists());
        } finally {
            file.deleteOnExit();
        }
    }

    /**
     * <testcase> A unknown option leads to the help message. </testcase>
     * 
     */
    @Test
    public void testHelp01() {

        assertEquals(-111,
            run(new String[]{"--help"}, NewsBuilderMain.HELP_INFO + "\n"));
    }

    /**
     * /** <testcase> A missing argument for the option <tt>-max</tt> is
     * recognized. </testcase>
     * 
     */
    @Test
    public void testMax01() {

        assertEquals(-1,
            run(new String[]{"-max"}, "*** Missing argument for -max\n"));
    }

    /**
     * <testcase> An invalid argument for the option <tt>-max</tt> is
     * recognized. </testcase>
     * 
     */
    @Test
    public void testMax02() {

        assertEquals(
            -1,
            run(new String[]{"-max", "x"}, "*** Invalid argument for -max: x\n"));
    }

    /**
     * <testcase> The max value determines the number of items in the RSS file.
     * </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testMax03() throws IOException {

        File file = new File("target/test-site/rss/2.0/news.rss");
        if (file.exists()) {
            assertTrue("deletion failed: " + file, file.delete());
        }
        LineNumberReader r = null;
        try {
            assertEquals(0, run(new String[]{"-max", "3"}, null));
            assertTrue("Expected file is missing: " + file.toString(),
                file.exists());
            r = new LineNumberReader(new FileReader(file));
            int no = 0;
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                if (line.contains("<item>")) {
                    no++;
                }
            }
            assertEquals("number of items", 3, no);
        } finally {
            file.deleteOnExit();
            if (r != null) {
                r.close();
            }
        }
    }

    /**
     * <testcase> A missing argument for the option <tt>-output</tt> is
     * recognized. </testcase>
     * 
     */
    @Test
    public void testOutput01() {

        assertEquals(-1,
            run(new String[]{"-output"}, "*** Missing argument for -output\n"));
    }

    /**
     * <testcase> The output can be redirected to another file. </testcase>
     * 
     * @throws IOException in case of an error
     */
    @Test
    public void testOutput02() throws IOException {

        File file = new File("target/news.rss");
        if (file.exists()) {
            assertTrue("deletion failed: " + file, file.delete());
        }
        try {
            assertEquals(0, run(new String[]{"-out", file.toString()}, null));
            assertTrue("Expected file is missing: " + file.toString(),
                file.exists());
        } finally {
            assertTrue("deletion failed: " + file, file.delete());
        }
    }

    /**
     * <testcase> A missing argument for the option <tt>-template</tt> is
     * recognized. </testcase>
     * 
     */
    @Test
    public void testTemplate01() {

        assertEquals(
            -1,
            run(new String[]{"-template"},
                "*** Missing argument for -template\n"));
    }

    /**
     * <testcase> A non-existent file for the option <tt>-template</tt> leads to
     * an error. </testcase>
     * 
     */
    @Test
    public void testTemplate02() {

        assertEquals(-1, run(new String[]{"-template", "xyzzy"}, null));
    }

    /**
     * <testcase> A unknown option leads to the help message. </testcase>
     * 
     */
    @Test
    public void testUnknown01() {

        assertEquals(-2,
            run(new String[]{"-xyzzy"}, "*** Unknown option: -xyzzy\n"));
    }

}
