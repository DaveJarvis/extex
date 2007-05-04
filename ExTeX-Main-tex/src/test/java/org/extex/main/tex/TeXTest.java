/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.main.tex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import junit.framework.TestCase;

import org.extex.ExTeX;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.HelpingException;
import org.extex.interpreter.interaction.InteractionUnknownException;

/**
 * This class contains test cases for the command line interface of
 * <logo>ExTeX</logo>.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4708 $
 */
public class TeXTest extends TestCase {

    /**
     * The constant <tt>BANNER</tt> contains the default banner.
     */
    public static final String BANNER =
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n";

    /**
     * The field <tt>BANNER_DE</tt> contains the default banner.
     */
    public static final String BANNER_DE =
            "Dies ist ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n";

    /**
     * The constant <tt>BANNER_TEX</tt> contains the banner for
     * TeX compatibility mode.
     */
    private static final String BANNER_TEX =
            "This is ExTeX, Version " + ExTeX.getVersion()
                    + " (TeX compatibility mode)\n";

    /**
     * The constant <tt>EMPTY_TEX</tt> contains the ...
     */
    private static final String EMPTY_TEX =
            "../ExTeX-Unit-tex/src/test/tex/empty.tex";

    /**
     * The constant <tt>EXIT_ERROR</tt> contains the exit code for an error.
     */
    public static final int EXIT_ERROR = -1;

    /**
     * The constant <tt>EXIT_OK</tt> contains the exit code for success.
     */
    public static final int EXIT_OK = 0;

    /**
     * The constant <tt>PARSE_PATH</tt> contains the full path of the
     * data directory.
     */
    private static final String PARSE_PATH = "../ExTeX-Main-tex/src/test/data/";

    /**
     * The constant <tt>TRANSCRIPT_TEXPUT</tt> contains the full name of the
     * transcript file.
     */
    private static final String TRANSCRIPT_TEXPUT = transcript("texput");

    /**
     * The command line interface.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        junit.textui.TestRunner.run(TeXTest.class);
    }

    /**
     * Create a new instance of properties pre-filled with the java.version.
     *
     * @return the new properties
     */
    private static Properties makeProperties() {

        Properties properties = new Properties();
        properties.put("java.version", System.getProperty("java.version"));
        return properties;
    }

    /**
     * Run a test through the command line with the default properties and
     * expect a failure.
     *
     * @param args the array of command line arguments
     * @param expect the expected result on the error stream or
     *            <code>null</code>
     *
     * @return the result on the error stream
     *
     * @throws HelpingException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runFailure(String[] args, String expect)
            throws HelpingException,
                IOException {

        return runTest(args, makeProperties(), expect, EXIT_ERROR);
    }

    /**
     * Run a test through the command line with the default properties and
     * expect a success.
     *
     * @param args the array of command line arguments
     * @param expect the expected result on the error stream or
     *            <code>null</code>
     *
     * @return the result on the error stream
     *
     * @throws InterpreterException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runSuccess(String[] args, String expect)
            throws HelpingException,
                IOException {

        return runTest(args, makeProperties(), expect, EXIT_OK);
    }

    /**
     * Run a test through the command line.
     *
     * @param args the array of command line arguments
     * @param properties the properties to use
     * @param expect the expected result on the error stream or
     *            <code>null</code>
     * @param exit the expected exit code
     *
     * @return the result on the error stream
     *
     * @throws HelpingException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runTest(String[] args,
            Properties properties, String expect, int exit)
            throws HelpingException,
                IOException {

        Locale.setDefault(new Locale("en"));
        properties.setProperty("extex.config", "tex.xml");

        TeX tex;
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        String result = null;
        try {
            System.setOut(new PrintStream(outBuffer));
            System.setErr(new PrintStream(errBuffer));

            tex = new TeX(properties, null);
            int status = tex.run(args);

            result = errBuffer.toString();
            if (expect != null) {
                assertEquals(expect, result);
            }
            assertEquals("", outBuffer.toString());
            assertEquals("Exit status", exit, status);

        } finally {
            outBuffer.close();
            System.setOut(stdout);
            errBuffer.close();
            System.setErr(stderr);
        }
        return result;
    }

    /**
     * Run a test through the command line with the default properties.
     *
     * @param args the array of command line arguments
     * @param expect the expected result on the error stream or
     *            <code>null</code>
     * @param exit the expected exit code
     *
     * @return the result on the error stream
     *
     * @throws InterpreterException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runTest(String[] args, String expect,
            int exit) throws HelpingException, IOException {

        return runTest(args, makeProperties(), expect, exit);
    }

    /**
     * Determine the transcript message for a file.
     *
     * @param name the name of the log file
     *
     * @return the transcript message
     */
    private static String transcript(String name) {

        File file = new File(".", name + ".log");
        return "Transcript written on " + file.toString() + ".\n";
    }

    /**
     * Creates a new object.
     */
    public TeXTest() {

        super();
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * Creates a new object.
     *
     * @param name the name
     */
    public TeXTest(String name) {

        super(name);
        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     * <testcase>
     *   This test case validates that a code argument is used.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCode() throws Exception {

        runSuccess(new String[]{"-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCommandInput1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\\n".getBytes()));
        runSuccess(new String[]{"-ini"}, //
            BANNER_TEX + "**\n*\nNo pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that an unrecognized configuration is
     *   encountered.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testConfgurationError1() throws Exception {

        runFailure(new String[]{"-conf=xyz"}, //
            BANNER + "**" + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration `xyz' not found");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that an unrecognized configuration is
     *   encountered.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testConfgurationError2() throws Exception {

        runFailure(new String[]{"-conf", "xyz"}, //
            BANNER + "**" + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration `xyz' not found");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCopying() throws Exception {

        String s = runSuccess(new String[]{"-copying"}, null);
        assertTrue("No match:\n" + s, //
            s.indexOf("GNU LIBRARY GENERAL PUBLIC LICENSE") >= 0);
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testCopyright() throws Exception {

        runSuccess(
            new String[]{"-copyright"}, //
            "Copyright (C) 2003-"
                    + Calendar.getInstance().get(Calendar.YEAR)
                    + " The ExTeX Group (mailto:extex@dante.de).\n"
                    + "There is NO warranty.  Redistribution of this software is\n"
                    + "covered by the terms of the GNU Library General Public License.\n"
                    + "For more information about these matters, use the command line\n"
                    + "switch -copying.\n");
    }

    /**
     * <testcase>
     *   This test case validates that an empty argument is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEmpty() throws Exception {

        runSuccess(new String[]{"", "-version"}, BANNER);
    }

    /**
     * <testcase>
     *   This test case validates that an unrecognized encoding is encountered.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEncodingError1() throws Exception {

        runFailure(
            new String[]{"--extex.encoding=xyz", "-ini"}, //
            BANNER_TEX
                    + "**"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Unsupported encoding xyz in <stdin>");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that an unrecognized encoding is encountered.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testEncodingError2() throws Exception {

        runFailure(
            new String[]{"--extex.encoding", "xyz", "-ini"}, //
            BANNER_TEX
                    + "**"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Unsupported encoding xyz in <stdin>");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testExternal1() throws Exception {

        runSuccess(new String[]{"-abc.properties", "-init", "\\end"}, //
            TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testExternal2() throws Exception {

        runSuccess(new String[]{"-ppp.properties", "-init", "\\end"}, //
            TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testExTeXBanner1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\\n".getBytes()));
        runSuccess(new String[]{"--extex.banner=xyz", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " (xyz)\n");
    }

    /**
     * <testcase>
     *   This test case validates that a non-existent file leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFile1() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runSuccess(new String[]{"-ini", "UndefinedFile"}, //
            BANNER_TEX + "I can\'t find file `UndefinedFile\'\n" + "*\n"
                    + "No pages of output.\n" + transcript("UndefinedFile"));
        new File(".", "UndefinedFile.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a existent file ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFile10() throws Exception {

        runSuccess(new String[]{"-ini", EMPTY_TEX}, //
            BANNER_TEX + "(../ExTeX-Unit-tex/src/test/tex/empty.tex )\n"
                    + "*\n" + "No pages of output.\n" + transcript("empty"));
        new File(".", "empty.log").delete();
    }

    /**
     * <testcase>
     *  This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFile11() throws Exception {

        System.setIn(new ByteArrayInputStream((EMPTY_TEX + "\n\\end\n")
            .getBytes()));
        runSuccess(new String[]{"-ini"},//
            BANNER_TEX + "**(" + EMPTY_TEX + " )\n" + "*\n"
                    + "No pages of output.\n" + transcript("empty"));
        new File(".", "empty.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a non-existent file leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFile2() throws Exception {

        runSuccess(new String[]{"-ini", "UndefinedFile.tex"}, //
            BANNER_TEX + "I can\'t find file `UndefinedFile.tex\'\n" + "*\n"
                    + "No pages of output.\n" + transcript("UndefinedFile"));
        new File(".", "UndefinedFile.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a non-existent file leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFile3() throws Exception {

        runSuccess(new String[]{"-ini", "-", "-UndefinedFile"}, //
            BANNER_TEX + "I can\'t find file `-UndefinedFile\'\n" + "*\n"
                    + "No pages of output.\n" + transcript("-UndefinedFile"));
        new File(".", "-UndefinedFile.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFmt1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\n".getBytes()));
        runFailure(
            new String[]{"&xyzzy"},
            BANNER_TEX
                    + "**\nSorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFmt2() throws Exception {

        runFailure(
            new String[]{"-fmt=xyzzy"},
            BANNER_TEX
                    + "**\nSorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFmt3() throws Exception {

        runFailure(
            new String[]{"-fmt", "xyzzy"},
            BANNER_TEX
                    + "**\n"
                    + "Sorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFormat2() throws Exception {

        runFailure(
            new String[]{"-format=xyzzy"},
            BANNER_TEX
                    + "**\n"
                    + "Sorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testFormat3() throws Exception {

        runFailure(
            new String[]{"-format", "xyzzy"},
            BANNER_TEX
                    + "**\n"
                    + "Sorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a non-existent file leads to an error.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testHaltOnError() throws Exception {

        runFailure(new String[]{"-ini", "-halt-on-error", "\\xxxx"}, //
            BANNER_TEX + ":1: Undefined control sequence \\xxxx\n" + "\\xxxx\n"
                    + "_____^\n" + "? \n" + "End of file on the terminal!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testHelp() throws Exception {

        String s = runSuccess(new String[]{"-help"}, null);
        assertTrue(s + "\ndoes  not match", s
            .startsWith("Usage: extex <options> file\n"));
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testHelp2() throws Exception {

        String s = runSuccess(new String[]{"-prog=abc", "-help"}, null);
        assertTrue(s + "\ndoes  not match", s
            .startsWith("Usage: abc <options> file\n"));
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction1() throws Exception {

        runFailure(new String[]{"-interaction"}, //
            BANNER + "Missing argument for extex.interaction");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction12() throws Exception {

        runFailure(new String[]{"-interaction", "xxx"}, //
            BANNER + "Interaction xxx is unknown\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction14() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction", "batchmode"}, //
            "");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction15() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction", "b"}, //
            "");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction16() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-int", "0"}, //
            "");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction2() throws Exception {

        runFailure(new String[]{"-interaction=xxx"}, //
            BANNER + "Interaction xxx is unknown\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction20() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        Properties p = makeProperties();
        p.put("extex.interaction", "illegal");
        try {
            runTest(new String[]{"-ini"}, p, "", EXIT_ERROR);
            assertFalse(true);
        } catch (InteractionUnknownException e) {
            assertTrue(true);
        }
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction3() throws Exception {

        runFailure(new String[]{"-interaction="}, //
            BANNER + "Interaction  is unknown\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction4() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction=batchmode"}, //
            "");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction5() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction=b"}, //
            "");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testInteraction6() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-int=0"}, //
            "");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testJobname1() throws Exception {

        runSuccess(new String[]{"-jobname=abc", "-ini",
                "--extex.nobanner=true", "\\end"}, //
            transcript("abc"));
        new File(".", "abc.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testJobname2() throws Exception {

        runSuccess(new String[]{"-jobname", "abc", "-ini",
                "--extex.nobanner=true", "\\end"}, //
            transcript("abc"));
        new File(".", "abc.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that <tt>-version</tt> prints the version
     *   number and exists with code 0 when the language is set to German.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLanguageVersion() throws Exception {

        runSuccess(new String[]{"-l=de", "-version"}, //
            BANNER_DE);
    }

    /**
     * <testcase>
     *   This test case validates that <tt>-version</tt> prints the version
     *   number and exists with code 0 when the language is set to German.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testLanguageVersion2() throws Exception {

        runSuccess(new String[]{"-lan", "de", "-version"}, //
            BANNER_DE);
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMain1() throws Exception {

        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        assertEquals(-1, TeX.mainProgram(new String[]{"-xxx"}));
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMainError1() throws Exception {

        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        assertEquals(-1, TeX.mainProgram(null));
    }

    /**
     * <testcase>
     *   This test case validates that an empty argument is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMinus() throws Exception {

        runSuccess(new String[]{"-ini", "-"}, //
            BANNER_TEX + "**\n" + "*\n" + "No pages of output.\n"
                    + "Transcript written on "
                    + (new File(".", "texput.log")).toString() + ".\n");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testMissingProperty() throws Exception {

        runFailure(new String[]{"--"}, //
            BANNER + "Missing argument for --");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testNobanner1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\\n".getBytes()));
        runSuccess(new String[]{"-ini", "--extex.nobanner=true"}, //
            "**\n*" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputError1() throws Exception {

        runFailure(new String[]{"-out"}, //
            BANNER + "Missing argument for extex.output");
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputError2() throws Exception {

        runFailure(
            new String[]{"-ini", "-out=undefined"}, //
            BANNER_TEX
                    + "**"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration `backend/undefined.xml\' not found");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputError3() throws Exception {

        runFailure(
            new String[]{"-ini", "-out", "undefined"}, //
            BANNER_TEX
                    + "**"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration `backend/undefined.xml\' not found");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputpath2() throws Exception {

        runSuccess(new String[]{"-ini", "-output-path=.", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputpath3() throws Exception {

        runSuccess(new String[]{"-ini", "-output-path", ".", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputpath4() throws Exception {

        runSuccess(new String[]{"-ini", "-output-path=.", "-output-dir=.",
                "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testOutputpathError1() throws Exception {

        runFailure(new String[]{"-output-path"}, //
            BANNER + "Missing argument for extex.output.directories");
    }

    /**
     * <testcase>
     *  This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testParseFirstLine() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runSuccess(new String[]{"-ini", "-parse", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a existent file ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testParseFirstLine2() throws Exception {

        runSuccess(new String[]{"-ini", "-parse", EMPTY_TEX}, //
            BANNER_TEX + "(" + EMPTY_TEX + " )\n" + "*\n"
                    + "No pages of output.\n" + transcript("empty"));
        new File(".", "empty.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a existent file ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testParseFirstLine3() throws Exception {

        runSuccess(
            new String[]{"-ini", "-parse", PARSE_PATH + "parse1.tex"}, //
            BANNER_TEX
                    + "("
                    + PARSE_PATH
                    + "parse1.tex \n"
                    + "Sorry, I can\'t find the format `undef.fmt\'; will try `tex.fmt\'.\n"
                    + ")\n" + "*\n" + "No pages of output.\n"
                    + transcript("parse1"));
        new File(".", "parse1.log").delete();
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testProgname1() throws Exception {

        runSuccess(new String[]{"-progname", "abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testProgname2() throws Exception {

        runSuccess(new String[]{"-prog", "abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testProgname3() throws Exception {

        runSuccess(new String[]{"-progname=abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testProgname4() throws Exception {

        runSuccess(new String[]{"-prog=abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testPropertyName1() throws Exception {

        runSuccess(new String[]{"--extex.name", "abc", "-version"},
            "This is abc, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testPropertyName2() throws Exception {

        runSuccess(new String[]{"--extex.name=abc", "-version"},
            "This is abc, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testPropertyName3() throws Exception {

        runSuccess(new String[]{"--", "extex.name=abc", "-version"},
            "This is abc, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testStarStar1() throws Exception {

        System.setIn(new ByteArrayInputStream("xyzzy\n".getBytes()));
        runSuccess(new String[]{"-ini"}, //
            BANNER_TEX + "**I can't find file `xyzzy'\n" + "*\n"
                    + "No pages of output.\n" + transcript("xyzzy"));
        new File(".", "xyzzy.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexinputs1() throws Exception {

        runSuccess(new String[]{"-texinputs=.", "-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexinputs2() throws Exception {

        runSuccess(new String[]{"-texinputs", ".", "-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexinputsError1() throws Exception {

        runFailure(new String[]{"-texinputs"}, //
            BANNER + "Missing argument for extex.texinputs");
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexmfoutputs2() throws Exception {

        runSuccess(new String[]{"-ini", "-texmfoutputs=.", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexmfoutputs3() throws Exception {

        runSuccess(new String[]{"-ini", "-texmfoutputs", ".", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexmfoutputsError1() throws Exception {

        runFailure(new String[]{"-texmfoutputs"}, //
            BANNER + "Missing argument for tex.output.dir.fallback");
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexoutputs2() throws Exception {

        runSuccess(new String[]{"-ini", "-texoutputs=.", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexoutputs3() throws Exception {

        runSuccess(new String[]{"-ini", "-texoutputs", ".", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that ...
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTexoutputsError1() throws Exception {

        runFailure(new String[]{"-texoutputs"}, //
            BANNER + "Missing argument for tex.output.dir");
    }

    /**
     * <testcase>
     *   This test case validates that debug option can be set.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTrace1() throws Exception {

        runSuccess(new String[]{"-d=fFTM", "-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that debug option can be set.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTrace11() throws Exception {

        runSuccess(new String[]{"-d", "fFTM", "-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that debug option can be set.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTrace12() throws Exception {

        runSuccess(new String[]{"-debug", "fFTM", "-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that debug option can be set.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTrace2() throws Exception {

        runSuccess(new String[]{"-debug=fFTM", "-ini", "\\end"}, //
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a debug code might be unknown.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTraceError1() throws Exception {

        runFailure(new String[]{"-d=^"}, //
            BANNER + "Unknown debug option: ^\n");
        new File(".", "texput.log").delete();
    }

    /**
     * <testcase>
     *   This test case validates that a debug code might be unknown.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testTraceError2() throws Exception {

        runFailure(new String[]{"-d"}, //
            BANNER + "Missing argument for d");
    }

    /**
     * <testcase> This test case validates that ... </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testUndefinedProperty() throws Exception {

        runFailure(new String[]{"--undefined"}, //
            BANNER + "Missing argument for --undefined");
    }

    /**
     * <testcase>
     *   This test case validates that an empty argument is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testUnknown1() throws Exception {

        runFailure(new String[]{"-xxx"}, //
            BANNER + "Unknown option: xxx\n");
    }

    /**
     * <testcase>
     *   This test case validates that an empty argument is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testUnknown2() throws Exception {

        for (char c = 'a'; c <= 'z'; c++) {
            runFailure(new String[]{"-" + c + "xxx"}, //
                BANNER + "Unknown option: " + c + "xxx\n");
        }
    }

    /**
     * <testcase>
     *   This test case validates that an empty argument is ignored.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testUnknown3() throws Exception {

        for (char c = 'A'; c <= 'Z'; c++) {
            runFailure(new String[]{"-" + c + "xxx"}, //
                BANNER + "Unknown option: " + c + "xxx\n");
        }
    }

    /**
     * <testcase>
     *  This test case validates that <tt>-ver</tt> prints the
     *  version number and exists with code 0.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testVer() throws Exception {

        runSuccess(new String[]{"-ver"}, //
            BANNER);
    }

    /**
     * <testcase>
     *  This test case validates that <tt>-version</tt> prints the
     *  version number and exists with code 0.
     * </testcase>
     *
     * @throws Exception in case of an error
     */
    public void testVersion() throws Exception {

        runSuccess(new String[]{"-version"}, //
            BANNER);
    }

    // TODO add more test cases
}
