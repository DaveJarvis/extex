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

package org.extex.resource.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Locale;

import org.junit.Test;

/**
 * This is a test suite for {@link TocIndex}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TocIndexTest {

    private final static String DIR_TARGET = "build/empty";

    /**
     * Run a test and compare the result.
     * 
     * @param expectedOut the expected output
     * @param expectedErr the expected error
     * @param args the command line arguments
     */
    private void runTest(String expectedOut, String expectedErr, String... args) {

        PrintStream err = System.err;
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        PrintStream out = System.out;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(outStream));
            System.setErr(new PrintStream(errStream));
            TocIndex.commandLine(args);
        } finally {
            System.err.close();
            System.setErr(err);
            System.out.close();
            System.setOut(out);
        }
        if (expectedErr != null) {
            assertEquals(expectedErr, errStream.toString().replaceAll("\r", ""));
        }
        if ("".equals(expectedOut)) {
            String s = outStream.toString().replaceAll("\r", "");
            assertEquals(expectedOut, s.replaceAll("\r", ""));
        } else if (expectedOut != null) {
            String s = outStream.toString().replaceAll("\r", "");
            int i = s.indexOf('\n');
            assertTrue(i >= 0);
            assertEquals("#", s.substring(0, i));
            s = s.substring(i + 1);

            i = s.indexOf('\n');
            assertTrue(i >= 0);
            assertEquals("# Created ", s.substring(0, 10));
            s = s.substring(i + 1);

            assertEquals(expectedOut, s.replaceAll("\r", ""));
        }
    }

    /**
     * Run a test with the English locale and compare the result.
     * 
     * @param expectedOut the expected output
     * @param expectedErr the expected error
     * @param args the command line arguments
     */
    private void runTestEn(String expectedOut, String expectedErr,
            String... args) {

        Locale.setDefault(Locale.ENGLISH);
        runTest(expectedOut, expectedErr, args);
    }

    private void setupTargetDirectory() {
        File dir = new File(DIR_TARGET);
        dir.delete();
        dir.mkdirs();
        dir.deleteOnExit();
    }

    /**
     * Test that a empty directory is correctly indexed.
     */
    @Test
    public final void test1() {
        setupTargetDirectory();
        runTestEn("#\n", "", DIR_TARGET);
    }

    /**
     * Test that an unknown command line argument is reported.
     *
     */
    @Test
    public final void testError01() {

        runTestEn("", "Unknown argument `--undef'\n", "--undef");
    }

    /**
     * Test that too many base directories are reported.
     */
    @Test
    public final void testError02() {

        runTestEn("", "Too many base directories\n", "a", "b");
    }

    /**
     * Test that too many base directories are reported.
     */
    @Test
    public final void testError03() {

        runTestEn("", "Too many base directories\n", "a", "-", "b");
    }

    /**
     * Test that too many base directories are reported.
     */
    @Test
    public final void testError04() {

        runTestEn("", "Too many base directories\n", "-", "a", "-", "b");
    }

    /**
     * Test that a missing base directory for flag - is reported.
     *
     */
    @Test
    public final void testError05() {

        runTestEn("", "Missing argument for `-'\n", "-");
    }

    /**
     * Test that a non-existent base directory is reported.
     *
     */
    @Test
    public final void testError06() {

        String sep = System.getProperty("file.separator");
        runTestEn("", "The base directory `non-existent" + sep + "base" + sep
                + "directory' can not be read\n",
            "non-existent/base/directory");
    }

    /**
     * Test that a missing base directory for flag -out is reported.
     *
     */
    @Test
    public final void testError07() {

        runTestEn("", "Missing argument for `-output'\n", "-out");
    }

    /**
     * Test that an unwritable output file is reported.
     */
    @Test
    public final void testError08() {
        setupTargetDirectory();
        runTestEn(
            "",
            "The output file `file/which/is/not/writable' can not be written\n",
            DIR_TARGET, "-out", "file/which/is/not/writable");
    }

    /**
     * Test that an unwritable output file is reported.
     */
    @Test
    public final void testError09() {
        setupTargetDirectory();
        runTestEn(
            "",
            "The output file `file/which/is/not/writable' can not be written\n",
            DIR_TARGET, "-output=file/which/is/not/writable");
    }

    /**
     * Test that an unwritable output file is reported.
     */
    @Test
    public final void testError10() {
        setupTargetDirectory();
        runTestEn(
            "",
            "The output file `file/which/is/not/writable' can not be written\n",
            DIR_TARGET, "--output=file/which/is/not/writable");
    }

    /**
     * Test that -help works.
     */
    @Test
    public final void testHelp1() {

        runTestEn("", "tocIndex [options] [base directory]\n", "-help");
    }

    /**
     * Test that --help works.
     */
    @Test
    public final void testHelp2() {

        runTestEn("", "tocIndex [options] [base directory]\n", "--help");
    }

    /**
     * Test that a empty directory is correctly indexed when some
     * directories are omitted.
     */
    @Test
    public final void testOmit1() {

        File dir = new File(DIR_TARGET + "/toc-index-test");
        File file = new File(dir, "xxx");
        assertFalse(dir.toString() + " exists. Make sure it is gone",
            dir.exists());
        assertTrue(dir.mkdirs());
        assertTrue(file.mkdirs());

        try {
            runTestEn("#\n", "", dir.getPath(), "-omit=.*/xxx$");
        } finally {
            assertTrue(file.delete());
            assertTrue(dir.delete());
        }
    }

    /**
     * Test that a empty directory is correctly indexed when some
     * directories are omitted.
     */
    @Test
    public final void testOmit2() {

        runTestEn("#\n" + "x.sam=/a/x.sam\n" + "y.sam=/y.sam\n", "",
            "src/test/resources/org/extex/framework/resource/tool/toc",
            "-omit=.*/\\.svn$");
    }

    /**
     * Test that an illegal regex is reported.
     */
    @Test
    public final void testOmit3() {

        runTestEn("", "Illegal regular expression: \\\n",
            "src/test/resources/org/extex/framework/resource/tool/toc",
            "-omit=\\");
    }

    /**
     * Test that a empty directory is correctly indexed when some
     * directories are omitted.
     */
    @Test
    public final void testOut1() {

        runTestEn("", "",
            "src/test/resources/org/extex/framework/resource/tool/toc",
            "-omit=.*/\\.svn$", "--output=target/toc");
    }

    /**
     * Test that an empty output file redirects to stdout.
     */
    @Test
    public final void testOut2() {

        runTestEn("#\n" + "x.sam=/a/x.sam\n" + "y.sam=/y.sam\n", "",
            "src/test/resources/org/extex/framework/resource/tool/toc",
            "-omit=.*/\\.svn$", "--output=");
    }

}
