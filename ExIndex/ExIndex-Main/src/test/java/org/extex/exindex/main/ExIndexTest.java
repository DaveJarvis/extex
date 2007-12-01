/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExIndexTest {

    /**
     * The field <tt>DEFAULT_LOG</tt> contains the default logging output.
     */
    private static final String DEFAULT_LOG =
            "Generating output...Output written.\n";

    /**
     * Run a test and compare the results. The expected output is empty.
     * 
     * @param args the command line arguments
     * @param exit the exit code
     * @param extectedErr the expected error stream
     * 
     * @return the instance of xindy
     * 
     * @throws Exception in case of an error
     */
    protected ExIndex runTest(String[] args, int exit, String extectedErr)
            throws Exception {

        return runTest(args, exit, "", extectedErr);
    }

    /**
     * Run a test and compare the results.
     * 
     * @param args the command line arguments
     * @param exit the exit code
     * @param expectedOut the expected output
     * @param extectedErr the expected error stream
     * 
     * @return the instance of xindy
     * 
     * @throws Exception in case of an error
     */
    protected ExIndex runTest(String[] args, int exit, String expectedOut,
            String extectedErr) throws Exception {

        PrintStream out = System.out;
        PrintStream err = System.err;
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("".getBytes()));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        try {
            ExIndex x = new ExIndex();
            assertEquals(exit, x.run(args));
            outStream.flush();
            assertEquals("stdout", expectedOut,//
                outStream.toString());
            errStream.flush();
            assertEquals("stderr", extectedErr, errStream.toString()
                .replaceAll("^This is ExIndex [0-9.]+ \\([0-9]*\\)\n", ""));
            return x;
        } finally {
            System.setErr(err);
            System.setOut(out);
            System.setIn(in);
        }
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test(expected = NullPointerException.class)
    public final void test1() throws Exception {

        runTest(null, 0, "");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test10() throws Exception {

        runTest(new String[]{null}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test11() throws Exception {

        runTest(new String[]{""}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test12() throws Exception {

        File log = new File("xyzzy.ilg");
        log.delete();
        ExIndex x =
                runTest(new String[]{"xyzzy"}, -1, "File not found: xyzzy\n");
        x.setLogger(null);
        assertTrue(log.exists());
        log.delete();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test2() throws Exception {

        runTest(new String[]{}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test20() throws Exception {

        File log = new File("UnDeFiNeD.ilg");
        log.delete();
        runTest(new String[]{"-", "UnDeFiNeD"}, -1,
            "File not found: UnDeFiNeD\n");
        assertTrue(log.exists());
        log.delete();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test21() throws Exception {

        runTest(new String[]{"UnDeFiNeD"}, -1, "File not found: UnDeFiNeD\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter0() throws Exception {

        runTest(new String[]{"-filter"}, -1,
            "Missing argument for parameter -filter\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter1() throws Exception {

        runTest(new String[]{"-filter", "undef"}, -1,
            "The filter undef could not be found.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter2() throws Exception {

        runTest(new String[]{"-filter", "test0"}, -1,
            "The class java.lang.String for filter test0 does not extend Reader.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter3() throws Exception {

        runTest(new String[]{"-filter", "test1"}, -1,
            "No filter class found in properties.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter4() throws Exception {

        runTest(new String[]{"-filter", "test2"}, -1,
            "The class org.extex.UnDeF for filter test2 could not be found.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter5() throws Exception {

        runTest(new String[]{"-filter", "test3"}, -1,
            "The filter class java.io.Reader for filter test3 does not have a proper\n"
                    + "constructor.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter6() throws Exception {

        runTest(
            new String[]{"-filter", "test4"},
            -1,
            "The filter class java.io.StringReader for filter test4 does not have a proper\n"
                    + "constructor.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testHelp1() throws Exception {

        runTest(new String[]{"--help"}, 1, //
            "Indexer <options> <idx files>\n" //
                    + "\t-s[tyle] <style>\n" //
                    + "\t- <idx file>\n" //
                    + "\t-o[utput] <ind file>\n" //
                    + "\t-p[age] <page>\n" //
                    + "\t-g\n" //
                    + "\t-l\n" //
                    + "\t-c\n" //
                    + "\t-q\n" //
                    + "\t-i\n" //
                    + "\t-h[elp]\n" //
                    + "\t-v[ersion]\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLogLevel01() throws Exception {

        runTest(new String[]{"-L", "1"}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLogLevel02() throws Exception {

        runTest(new String[]{"-L", "2", ""}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLogLevel03() throws Exception {

        runTest(new String[]{"-L", "3", ""}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLogLevel10() throws Exception {

        runTest(new String[]{"-L"}, -1, "Missing argument for parameter -L\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLogLevel11() throws Exception {

        runTest(new String[]{"-L", "xxx"}, -1,
            "The log level xxx is not defined.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testQuiet1() throws Exception {

        runTest(new String[]{"-q"}, 0, "");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testStyle1() throws Exception {

        runTest(new String[]{"-s"}, -1, "Missing argument for parameter -s\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testStyle2() throws Exception {

        runTest(new String[]{"-s", "undef"}, -1,
            "Scanning style file undef...\nFile not found: undef\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testStyle3() throws Exception {

        runTest(new String[]{"-t", "xxx", "-s",
                "src/test/resources/makindex.xdy"}, -1, "...");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testStyle4() throws Exception {

        runTest(new String[]{"-s", "src/test/resources/doc.ist"}, -1, "...");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTranscript1() throws Exception {

        File log = new File("target/xxx.ilg");
        log.delete();
        runTest(new String[]{"-t", "target/xxx.ilg"}, 0, DEFAULT_LOG
                + "Transcript written in target/xxx.ilg.\n");
        assertTrue(log.exists());
        log.delete();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTranscript2() throws Exception {

        runTest(new String[]{"-t", ""}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTranscript3() throws Exception {

        runTest(new String[]{"-t", "-"}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUnknownArg1() throws Exception {

        runTest(new String[]{"-Undefined"}, -1, "Unknown argument -Undefined\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUnknownArg2() throws Exception {

        runTest(new String[]{"--Undefined"}, -1,
            "Unknown argument -Undefined\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testVersion1() throws Exception {

        runTest(new String[]{"-V"}, 1, "");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testVersion2() throws Exception {

        runTest(new String[]{"--V"}, 1, "");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testVersion3() throws Exception {

        runTest(new String[]{"--Version"}, 1, "");
    }

}
