/*
 * Copyright (C) 2007-2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.main;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Locale;
import java.util.logging.Handler;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * This is a test suite for ExIndex.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExIndexTest {

    private static final String DIR_TARGET = "build";

    /**
     * The field <tt>DEFAULT_LOG</tt> contains the default logging output.
     */
    private static final String DEFAULT_LOG =
            "Starting the processing phase.\n" + "Reading <stdin>.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Generating output...Starting the markup phase.\n"
                    + "Output written.\n";

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

        Locale.setDefault(Locale.ENGLISH);
        PrintStream out = System.out;
        PrintStream err = System.err;
        InputStream in = System.in;
        System.setIn(new ByteArrayInputStream("".getBytes()));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        ExIndex index;
        try {
            index = new ExIndex();
            int ex = index.run(args);
            if (expectedOut != null) {
                outStream.flush();
                assertEquals("stdout", expectedOut,
                    outStream.toString());
            }
            if (extectedErr != null) {
                errStream.flush();
                assertEquals("stderr", extectedErr, errStream.toString()
                    .replaceAll("^This is ExIndex [0-9.]+ \\([0-9]*\\)\n", ""));
            }
            assertEquals(exit, ex);
        } finally {
            System.setErr(err);
            System.setOut(out);
            System.setIn(in);
        }

        Logger logger = index.getLogger();
        assertNotNull(logger);
        for (Handler h : logger.getHandlers()) {
            h.close();
        }
        return index;
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
        runTest(new String[]{"xyzzy"}, -1, "Starting the processing phase.\n"
                + "Reading xyzzy.\n"
                + "Resources xyzzy not found.\nFile not found: xyzzy\n");
        assertTrue(log.exists());
        log.deleteOnExit();
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
        runTest(
            new String[]{"-", "UnDeFiNeD"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading UnDeFiNeD.\n"
                    + "Resources UnDeFiNeD not found.\nFile not found: UnDeFiNeD\n");
        assertTrue(log.exists());
        log.deleteOnExit();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test21() throws Exception {

        File log = new File("UnDeFiNeD.ilg");
        log.delete();
        runTest(
            new String[]{"UnDeFiNeD"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading UnDeFiNeD.\n"
                    + "Resources UnDeFiNeD not found.\nFile not found: UnDeFiNeD\n");
        assertTrue(log.exists());
        log.deleteOnExit();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCharset1() throws Exception {

        runTest(new String[]{"-Charset"}, -1,
            "Missing argument for parameter -Charset\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCharset2() throws Exception {

        runTest(new String[]{"-Charset", "abc"}, -1,
            "Unsupported character set `abc'.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCharset3() throws Exception {

        runTest(new String[]{"-Charset", "latin1"}, 0, DEFAULT_LOG);
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
            "Starting the processing phase.\n" + "Reading <stdin>.\n"
                    + "The filter undef could not be found.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter2() throws Exception {

        runTest(
            new String[]{"-filter", "test0"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "The class java.lang.String for filter test0 does not extend Reader. This is\n"
                    + "most probable a programming or configuration error.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter3() throws Exception {

        runTest(
            new String[]{"-filter", "test1"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "No filter class found in properties.This is most probable a programming or\n"
                    + "configuration error.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter4() throws Exception {

        runTest(
            new String[]{"-filter", "test2"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "The class org.extex.UnDeF for filter test2 could not be found. This is most\n"
                    + "probable a programming or configuration error.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter5() throws Exception {

        runTest(
            new String[]{"-filter", "test3"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "The filter class java.io.Reader for filter test3 does not have a proper\n"
                    + "constructor. This is most probable a programming or configuration error.\n");
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
            "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "The filter class java.io.StringReader for filter test4 does not have a proper\n"
                    + "constructor. This is most probable a programming or configuration error.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFilter7() throws Exception {

        runTest(
            new String[]{"-filter", "testBad1"},
            -1,
            "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "Filter error for filter testBad1: java.lang.IllegalArgumentException\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void testFilterMakeindex() throws Exception {

        runTest(new String[]{"-filter", "makeindex"}, -1, "...");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testHelp1() throws Exception {

        runTest(new String[]{"--help"}, 1,
            "ExIndex <options> <idx files>\n" + "\t-s[tyle] <style>\n"
                    + "\t- <idx file>\n" + "\t-o[utput] <ind file>\n"
                    + "\t-g[erman]\n" + "\t-l\n" + "\t-c\n" + "\t-r\n"
                    + "\t-q[uiet]\n" + "\t-i\n"
                    + "\t-tran[script] <log-file>\n" + "\t-trac[e]\n"
                    + "\t-h[elp]\n" + "\t-V[ersion]\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testInput1() throws Exception {

        runTest(new String[]{"-in", ""}, 0, DEFAULT_LOG);
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
            "The log level `xxx' is not defined.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOutput0() throws Exception {

        runTest(new String[]{"-out"}, -1,
            "Missing argument for parameter -out\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOutput1() throws Exception {

        runTest(new String[]{"-out", ""}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testOutput2() throws Exception {

        runTest(new String[]{"-out", "-"}, 0, DEFAULT_LOG);
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
            "Scanning style file undef...\n" + "File not found: undef\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    // define see
    public final void testStyle3() throws Exception {

        runTest(new String[]{"-trans", "xxx", "-s",
                "../ExIndex-Main/src/test/resources/makeidx"}, 0, null,
            "Scanning style file ../ExIndex-Main/src/test/resources/makeidx...done (?\n"
                    + "attributes redefined, ? ignored).\n"
                    + "Starting the processing phase.\n" + "Reading <stdin>.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Generating output...Starting the markup phase.\n"
                    + "Output written.\n" + "Transcript written in xxx.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testStyle4() throws Exception {

        runTest(
            new String[]{"-s", "../ExIndex-Main/src/test/resources/doc"},
            0,
            "Scanning style file ../ExIndex-Main/src/test/resources/doc...done (7 attributes\n"
                    + "redefined, 0 ignored).\n"
                    + "Starting the processing phase.\n"
                    + "Reading <stdin>.\n"
                    + "Starting the pre-processing phase.\n"
                    + "Generating output...Starting the markup phase.\n"
                    + "Output written.\n");
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public final void testTranscript1() throws Exception {

        File log = new File(DIR_TARGET + "/xxx.ilg");
        log.delete();
        runTest(new String[]{"-trans", DIR_TARGET + "/xxx.ilg"}, 0, DEFAULT_LOG
                + "Transcript written in "+DIR_TARGET+"/xxx.ilg.\n");
        assertTrue(log.exists());
        log.deleteOnExit();
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTranscript2() throws Exception {

        runTest(new String[]{"-trans", ""}, 0, DEFAULT_LOG);
    }

    /**
     * Test method for
     * {@link org.extex.exindex.main.ExIndex#run(java.lang.String[])}.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testTranscript3() throws Exception {

        runTest(new String[]{"-trans", "-"}, 0, DEFAULT_LOG);
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
