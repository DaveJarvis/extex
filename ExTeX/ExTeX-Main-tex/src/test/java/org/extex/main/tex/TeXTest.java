/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

import org.extex.ExTeX;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.exception.InteractionUnknownException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * This class contains test cases for the command line interface of εχTeX.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class TeXTest {

    /**
     * The constant {@code BANNER} contains the default banner.
     */
    public static final String BANNER = "This is ExTeX, Version "
            + ExTeX.getVersion() + " (" + System.getProperty("java.version")
            + ")\n";

    /**
     * The field {@code BANNER_DE} contains the default banner.
     */
    public static final String BANNER_DE = "Dies ist ExTeX, Version "
            + ExTeX.getVersion() + " (" + System.getProperty("java.version")
            + ")\n";

    /**
     * The constant {@code BANNER_TEX} contains the banner for TeX compatibility mode.
     */
    private static final String BANNER_TEX = "This is ExTeX, Version "
            + ExTeX.getVersion() + " (TeX compatibility mode)\n";

    /**
     * The constant {@code EMPTY_TEX} contains the name of an empty
     * TeX file.
     */
    private static final String EMPTY_TEX =
            "../Unit/ExTeX-Unit-tex/src/test/resources/tex/empty.tex";

    /**
     * The constant {@code EXIT_ERROR} contains the exit code for an error.
     */
    public static final int EXIT_ERROR = -1;

    /**
     * The constant {@code EXIT_OK} contains the exit code for success.
     */
    public static final int EXIT_OK = 0;

    /**
     * The constant {@code PARSE_PATH} contains the full path of the data
     * directory.
     */
    private static final String PARSE_PATH = "../ExTeX-Main-tex/src/test/data/";

    /**
     * The constant {@code TRANSCRIPT_TEXPUT} contains the full name of the
     * transcript file.
     */
    private static final String TRANSCRIPT_TEXPUT = transcript("texput");

    /**
     * The command line interface.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        (new JUnitCore()).run(TeXTest.class);
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
     *        {@code null}
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
     *        {@code null}
     * @param logfile the name of the log file. It will be deleted at the end
     * 
     * @return the result on the error stream
     * 
     * @throws HelpingException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runSuccess(String[] args, String expect, String logfile)
            throws HelpingException,
                IOException {

        try {
            return runTest(args, makeProperties(), expect, EXIT_OK);
        } finally {
            if (logfile != null) {
                new File(".", logfile).deleteOnExit();
            }
        }
    }

    /**
     * Run a test through the command line.
     * 
     * @param args the array of command line arguments
     * @param properties the properties to use
     * @param expect the expected result on the error stream or
     *        {@code null}
     * @param exit the expected exit code
     * 
     * @return the result on the error stream
     * 
     * @throws HelpingException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runTest(String[] args, Properties properties,
            String expect, int exit) throws HelpingException, IOException {

        Locale.setDefault(new Locale("en"));
        properties.setProperty("extex.config", "tex.xml");

        TeX tex = null;
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        String result;
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
            if (tex != null) {
                tex.close();
            }
        }
        return result;
    }

    /**
     * Run a test through the command line with the default properties.
     * 
     * @param args the array of command line arguments
     * @param expect the expected result on the error stream or
     *        {@code null}
     * @param exit the expected exit code
     * 
     * @return the result on the error stream
     * 
     * @throws HelpingException in case of an interpreter error
     * @throws IOException in case of an io error
     */
    public static String runTest(String[] args, String expect, int exit)
            throws HelpingException,
                IOException {

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


    public TeXTest() {

        Locale.setDefault(Locale.ENGLISH);
    }

    /**
     *  This test case validates that a code argument is used.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCode() throws Exception {

        runSuccess(
            new String[]{"-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that no input produces no output
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCommandInput1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\\n".getBytes()));
        runSuccess(
            new String[]{"-ini"},
            BANNER_TEX + "**\n*\nNo pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that an unrecognized configuration is encountered
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testConfgurationError1() throws Exception {

        runFailure(new String[]{"-conf=xyz"},
            BANNER + "**" + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration `xyz' not found.\n");
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that an unrecognized configuration is encountered
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testConfgurationError2() throws Exception {

        runFailure(new String[]{"-conf", "xyz"},
            BANNER + "**" + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration `xyz' not found.\n");
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that -copying produces the copyright notice
* 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testCopying() throws Exception {

        String s = runSuccess(new String[]{"-copying"}, null, null);
        assertTrue("No match:\n" + s,
                   s.contains( "GNU LIBRARY GENERAL PUBLIC LICENSE" ) );
    }

    /**
     * This test case validates that -copyrigh produces the short copyright note
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testCopyright() throws Exception {

        runSuccess(
            new String[]{"-copyright"},
            "Copyright (C) 2003-"
                    + Calendar.getInstance().get(Calendar.YEAR)
                    + " The ExTeX Group (mailto:extex@dante.de).\n"
                    + "There is NO warranty.  Redistribution of this software is\n"
                    + "covered by the terms of the GNU Library General Public License.\n"
                    + "For more information about these matters, use the command line\n"
                    + "switch -copying.\n", null);
    }

    /**
     *  This test case validates that an empty argument is ignored.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty() throws Exception {

        runSuccess(new String[]{"", "-version"}, BANNER, null);
    }

    /**
     * This test case validates that an unrecognized encoding is encountered
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testEncodingError1() throws Exception {

        runFailure(
            new String[]{"--extex.encoding=xyz", "-ini"},
            BANNER_TEX
                    + "**"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Unsupported encoding xyz in <stdin>");
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that an unrecognized encoding is encountered
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testEncodingError2() throws Exception {

        runFailure(
            new String[]{"--extex.encoding", "xyz", "-ini"},
            BANNER_TEX
                    + "**"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Unsupported encoding xyz in <stdin>");
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that a properties file on the class path is found
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testExternal1() throws Exception {

        runSuccess(new String[]{"-abc", "-init", "\\end"},
            TRANSCRIPT_TEXPUT, "texput.log");
    }

    /**
     * This test case validates that a dot file in the current directory is found
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testExternal2() throws Exception {

        String cfg = "p-p-p";
        File f = new File(".extex-" + cfg);
        try {
            FileWriter w = new FileWriter(f);
            w.write("extex.nobanner:true\n");
            w.close();

            runSuccess(new String[]{"-" + cfg, "-init", "\\end"},
                TRANSCRIPT_TEXPUT, "texput.log");
        } finally {
            f.deleteOnExit();
        }
    }

    /**
     * This test case validates that the property extex.banner can be used to set the name shown in the version banner
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testExTeXBanner1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\\n".getBytes()));
        runSuccess(new String[]{"--extex.banner=xyz", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " (xyz)\n", null);
    }

    /**
     * This test case validates that a non-existent file leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFile1() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runSuccess(
            new String[]{"-ini", "UndefinedFile"},
            BANNER_TEX + "I can't find file `UndefinedFile'\n" + "*\n"
                    + "No pages of output.\n" + transcript("UndefinedFile"),
            "UndefinedFile.log");
    }

    /**
     * This test case validates that an existent file on the command line is processed
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFile10() throws Exception {

        runSuccess(
            new String[]{"-ini", EMPTY_TEX},
            BANNER_TEX
                    + "(../Unit/ExTeX-Unit-tex/src/test/resources/tex/empty.tex )\n"
                    + "*\n" + "No pages of output.\n" + transcript("empty"),
            "empty.log");
    }

    /**
     * This test case validates that an existent file on the input stream is prcessed
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFile11() throws Exception {

        System.setIn(new ByteArrayInputStream((EMPTY_TEX + "\n\\end\n")
            .getBytes()));
        runSuccess(
            new String[]{"-ini"},
            BANNER_TEX + "**(" + EMPTY_TEX + " )\n" + "*\n"
                    + "No pages of output.\n" + transcript("empty"),
            "empty.log");
    }

    /**
     * This test case validates that a non-existent file leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFile2() throws Exception {

        runSuccess(
            new String[]{"-ini", "UndefinedFile.tex"},
            BANNER_TEX + "I can't find file `UndefinedFile.tex'\n" + "*\n"
                    + "No pages of output.\n" + transcript("UndefinedFile"),
            "UndefinedFile.log");
    }

    /**
     * This test case validates that a non-existent file leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFile3() throws Exception {

        runSuccess(
            new String[]{"-ini", "-", "-UndefinedFile"},
            BANNER_TEX + "I can't find file `-UndefinedFile'\n" + "*\n"
                    + "No pages of output.\n" + transcript("-UndefinedFile"),
            "-UndefinedFile.log");
    }

    /**
     * This test case validates that a missing format on the command line in &amp; notation is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFmt1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\n".getBytes()));
        runFailure(
            new String[]{"&xyzzy"},
            BANNER_TEX
                    + "**\nSorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that a missing format on the command line with the -fmt= flag is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFmt2() throws Exception {

        runFailure(
            new String[]{"-fmt=xyzzy"},
            BANNER_TEX
                    + "**\nSorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that a missing format on the command line with the -fmt flag is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFmt3() throws Exception {

        runFailure(
            new String[]{"-fmt", "xyzzy"},
            BANNER_TEX
                    + "**\n"
                    + "Sorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that a missing format on the command line with the -format= flag is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat2() throws Exception {

        runFailure(
            new String[]{"-format=xyzzy"},
            BANNER_TEX
                    + "**\n"
                    + "Sorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that a missing format on the command line with the -format flag is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testFormat3() throws Exception {

        runFailure(
            new String[]{"-format", "xyzzy"},
            BANNER_TEX
                    + "**\n"
                    + "Sorry, I can't find the format `xyzzy.fmt'; will try `tex.fmt'.\n"
                    + "Sorry, I can't find the format `tex.fmt'!\n"
                    + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that a non-existent file leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testHaltOnError() throws Exception {

        runFailure(new String[]{"-ini", "-halt-on-error", "\\xxxx"},
            BANNER_TEX + "*:1: Undefined control sequence \\xxxx\n"
                    + "\\xxxx\n" + "_____^\n" + "? \n"
                    + "End of file on the terminal!\n" + TRANSCRIPT_TEXPUT);
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     *  This test case validates that -help produces some help text.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHelp() throws Exception {

        String s = runSuccess(new String[]{"-help"}, null, null);
        assertTrue(s + "\ndoes  not match",
            s.startsWith("Usage: extex <options> file\n"));
    }

    /**
     * This test case validates that the command line option -prog= can be used to set the program name for the help text
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testHelp2() throws Exception {

        String s = runSuccess(new String[]{"-prog=abc", "-help"}, null, null);
        assertTrue(s + "\ndoes  not match",
            s.startsWith("Usage: abc <options> file\n"));
    }

    /**
     * This test case validates that the command line option -interaction needs a parameter
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction1() throws Exception {

        runFailure(new String[]{"-interaction"},
            BANNER + "Missing argument for extex.interaction");
    }

    /**
     * This test case validates that the command line option -interaction reports am unknown interaction mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction12() throws Exception {

        runFailure(new String[]{"-interaction", "xxx"},
            BANNER + "Bad interaction mode (xxx)");
    }

    /**
     * This test case validates that the command line option -interaction takes an interaction batchmode as parameter
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction14() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction", "batchmode"},
            "");
    }

    /**
     *  This test case validates that the command line option
     * -interaction takes an interaction batchmode abbreviated ab b as
     * parameter.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction15() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction", "b"},
            "");
    }

    /**
     *  This test case validates that the command line option
     * -interaction abbreviated as -int takes an integer mode as parameter.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction16() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-int", "0"},
            "");
    }

    /**
     * This test case validates that the command line option -interaction= reports am unknown interaction mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction2() throws Exception {

        runFailure(new String[]{"-interaction=xxx"},
            BANNER + "Bad interaction mode (xxx)");
    }

    /**
     * This test case validates that the property extex.interaction reports an illegal interaction mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction20() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        Properties p = makeProperties();
        p.put("extex.interaction", "illegal");
        try {
            runTest(new String[]{"-ini"}, p, "", EXIT_ERROR);
            fail();
        } catch (InteractionUnknownException e) {
            assertTrue(true);
        }
    }

    /**
     * This test case validates that the command line option -interaction= needs an interaction mode
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction3() throws Exception {

        runFailure(new String[]{"-interaction="},
            BANNER + "Bad interaction mode ()");
    }

    /**
     *  This test case validates that the command line option
     * -interaction= takes the interaction mode batchmode as argument.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction4() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction=batchmode"},
            "");
    }

    /**
     *  This test case validates that the command line option
     * -interaction= takes the interaction mode batchmode abbreviated as b as
     * argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction5() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-interaction=b"},
            "");
    }

    /**
     *  This test case validates that the command line option
     * -interaction= abbreviated as -int= takes an numeric interaction mode 0 as
     * argument.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testInteraction6() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runFailure(new String[]{"-ini", "-int=0"},
            "");
    }

    /**
     * This test case validates that the command line option -jobname= can be used to set the job name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testJobname1() throws Exception {

        runSuccess(new String[]{"-jobname=abc", "-ini",
                "--extex.nobanner=true", "\\end"},
            transcript("abc"), "abc.log");
    }

    /**
     * This test case validates that the command line option -jobname can be used to set the job name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testJobname2() throws Exception {

        runSuccess(new String[]{"-jobname", "abc", "-ini",
                "--extex.nobanner=true", "\\end"},
            transcript("abc"), "abc.log");
    }

    /**
     *  This test case validates that {@code -version} prints the
     * version number and exists with code 0 when the language is set to German.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLanguageVersion() throws Exception {

        runSuccess(new String[]{"-l=de", "-version"},
            BANNER_DE, "texput.log");
    }

    /**
     *  This test case validates that {@code -version} prints the
     * version number and exists with code 0 when the language is set to German.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLanguageVersion2() throws Exception {

        runSuccess(new String[]{"-lan", "de", "-version"},
            BANNER_DE, "texput.log");
    }

    /**
     * This test case validates that an unknown option terminates the program with code -1
*/
    @Test
    public void testMain1() {

        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        assertEquals(-1, TeX.mainProgram(new String[]{"-xxx"}));
    }

    /**
     * This test case validates that an undefined parameter list terminates the program with code -1
*/
    @Test
    public void testMainError1() {

        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        assertEquals(-1, TeX.mainProgram(null));
    }

    /**
     *  This test case validates that an empty argument is ignored.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinus() throws Exception {

        runSuccess(
            new String[]{"-ini", "-"},
            BANNER_TEX + "**\n" + "*\n" + "No pages of output.\n"
                    + "Transcript written on "
                    + (new File(".", "texput.log")).toString() + ".\n",
            "texput.log");
    }

    /**
     * This test case validates that the command lie parameter -- expects something to follow
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testMissingProperty() throws Exception {

        runFailure(new String[]{"--"},
            BANNER + "Missing argument for --");
    }

    /**
     * This test case validates that the property extex.nobanner suppresses the banner
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testNobanner1() throws Exception {

        System.setIn(new ByteArrayInputStream("\\relax\n\\end\\n".getBytes()));
        runSuccess(new String[]{"-ini", "--extex.nobanner=true"},
            "**\n*" + TRANSCRIPT_TEXPUT, "texput.log");
    }

    /**
     * This test case validates that the parameter -out needs an value
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputError1() throws Exception {

        runFailure(new String[]{"-out"},
            BANNER + "Missing argument for extex.output");
    }

    /**
     * This test case validates that an unknown value for -out= is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputError2() throws Exception {

        runFailure(
            new String[]{"-ini", "-out=undefined", "\\relax abc"},
            BANNER_TEX
                    + "*"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration " +
                "`backend/undefined.xml' not found.\n" );
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that an unknown value for -out is reported
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputError3() throws Exception {

        runFailure(
            new String[]{"-ini", "-out", "undefined", "\\relax abc"},
            BANNER_TEX
                    + "*"
                    + TRANSCRIPT_TEXPUT
                    + "Configuration problem: Configuration " +
                "`backend/undefined.xml' not found.\n" );
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     * This test case validates that the parameter -output-path= can be used to redirect the log file
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputpath2() throws Exception {

        runSuccess(
            new String[]{"-ini", "-output-path=.", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that the parameter -output-path can be used to redirect the log file
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputpath3() throws Exception {

        runSuccess(
            new String[]{"-ini", "-output-path", ".", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that the parameter -output-dir can be used to redirect the log file
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputpath4() throws Exception {

        runSuccess(
            new String[]{"-ini", "-output-path=.", "-output-dir=.", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that the parameter -output-path needs a value
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutputpathError1() throws Exception {

        runFailure(new String[]{"-output-path"},
            BANNER + "Missing argument for extex.output.directories");
    }

    /**
     *  This test case validates that -parse_first-line does no harm
     * when using without a file.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testParseFirstLine() throws Exception {

        System.setIn(new ByteArrayInputStream("".getBytes()));
        runSuccess(
            new String[]{"-ini", "-parse", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that -parse_first-line does no harm
     * when using on an empty file.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testParseFirstLine2() throws Exception {

        runSuccess(
            new String[]{"-ini", "-parse", EMPTY_TEX},
            BANNER_TEX + "(" + EMPTY_TEX + " )\n" + "*\n"
                    + "No pages of output.\n" + transcript("empty"),
            "empty.log");
    }

    /**
     *  This test case validates that -parse_first-line does evaluate
     * the first line if one is given. For instance a reference to an undefined
     * format should lead to an error.
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testParseFirstLine3() throws Exception {

        runSuccess(
            new String[]{"-ini", "-parse", PARSE_PATH + "parse1.tex"},
            BANNER_TEX
                    + "("
                    + PARSE_PATH
                    + "parse1.tex \n"
                    + "Sorry, I can't find the format `undef.fmt'; will try " +
                "`tex.fmt'.\n"
                    + ")\n" + "*\n" + "No pages of output.\n"
                    + transcript("parse1"), "parse1.log");
    }

    /**
     * This test case validates that the option -progname sets the program name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgname1() throws Exception {

        runSuccess(
            new String[]{"-progname", "abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that the option -progname sets the program name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgname2() throws Exception {

        runSuccess(
            new String[]{"-prog", "abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that the option -progname= sets the program name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgname3() throws Exception {

        runSuccess(
            new String[]{"-progname=abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that the option -progname= abbreviated as -prog= sets the program name
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgname4() throws Exception {

        runSuccess(
            new String[]{"-prog=abc", "-version"},
            "This is ExTeX, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that -- reads the property value from the following argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testPropertyName1() throws Exception {

        runSuccess(
            new String[]{"--extex.name", "abc", "-version"},
            "This is abc, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that -- reads the property from the same argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testPropertyName2() throws Exception {

        runSuccess(
            new String[]{"--extex.name=abc", "-version"},
            "This is abc, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that -- reads the property from the following argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testPropertyName3() throws Exception {

        runSuccess(
            new String[]{"--", "extex.name=abc", "-version"},
            "This is abc, Version " + ExTeX.getVersion() + " ("
                    + System.getProperty("java.version") + ")\n", "texput.log");
    }

    /**
     * This test case validates that an non-existing file argument leads to an error
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testStarStar1() throws Exception {

        System.setIn(new ByteArrayInputStream("xyzzy\n".getBytes()));
        runSuccess(
            new String[]{"-ini"},
            BANNER_TEX + "**I can't find file `xyzzy'\n" + "*\n"
                    + "No pages of output.\n" + transcript("xyzzy"),
            "xyzzy.log");
    }

    /**
     * This test case validates that -texinputs= takes a path as argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexinputs1() throws Exception {

        runSuccess(
            new String[]{"-texinputs=.", "-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that -texinputs takes a path as argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexinputs2() throws Exception {

        runSuccess(
            new String[]{"-texinputs", ".", "-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that -texinputs needs an argument.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexinputsError1() throws Exception {

        runFailure(new String[]{"-texinputs"},
            BANNER + "Missing argument for extex.texinputs");
    }

    /**
     * This test case validates that -texmfoutputs= takes a path as argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexmfoutputs2() throws Exception {

        runSuccess(
            new String[]{"-ini", "-texmfoutputs=.", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that -texmfoutputs takes a path as argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexmfoutputs3() throws Exception {

        runSuccess(
            new String[]{"-ini", "-texmfoutputs", ".", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that -texmfoutputs needs an argument.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexmfoutputsError1() throws Exception {

        runFailure(new String[]{"-texmfoutputs"},
            BANNER + "Missing argument for tex.output.dir.fallback");
    }

    /**
     * This test case validates that -texoutputs= takes a path as argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexoutputs2() throws Exception {

        runSuccess(
            new String[]{"-ini", "-texoutputs=.", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     * This test case validates that -texoutputs takes a path as argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexoutputs3() throws Exception {

        runSuccess(
            new String[]{"-ini", "-texoutputs", ".", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that -texoutputs needs an argument.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTexoutputsError1() throws Exception {

        runFailure(new String[]{"-texoutputs"},
            BANNER + "Missing argument for tex.output.dir");
    }

    /**
     *  This test case validates that debug option can be set.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTrace1() throws Exception {

        runSuccess(
            new String[]{"-d=fFTM", "-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that debug option can be set.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTrace11() throws Exception {

        runSuccess(
            new String[]{"-d", "fFTM", "-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that debug option can be set.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTrace12() throws Exception {

        runSuccess(
            new String[]{"-debug", "fFTM", "-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that debug option can be set.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTrace2() throws Exception {

        runSuccess(
            new String[]{"-debug=fFTM", "-ini", "\\end"},
            BANNER_TEX + "No pages of output.\n" + TRANSCRIPT_TEXPUT,
            "texput.log");
    }

    /**
     *  This test case validates that a debug code might be unknown.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTraceError1() throws Exception {

        runFailure(new String[]{"-d=^"},
            BANNER + "Unknown debug option: ^\n");
        new File(".", "texput.log").deleteOnExit();
    }

    /**
     *  This test case validates that a debug code might be unknown.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTraceError2() throws Exception {

        runFailure(new String[]{"-d"},
            BANNER + "Missing argument for d");
    }

    /**
     * This test case validates that --&lt;property&gt; needs an argument
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndefinedProperty() throws Exception {

        runFailure(new String[]{"--undefined"},
            BANNER + "Missing argument for --undefined");
    }

    /**
     *  This test case validates that an empty argument is ignored.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnknown1() throws Exception {

        runFailure(new String[]{"-xxx"},
            BANNER + "Unknown option: xxx\n");
    }

    /**
     *  This test case validates that an empty argument is ignored.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnknown2() throws Exception {

        for (char c = 'a'; c <= 'z'; c++) {
            runFailure(new String[]{"-" + c + "xxx"},
                BANNER + "Unknown option: " + c + "xxx\n");
        }
    }

    /**
     *  This test case validates that an empty argument is ignored.
     *
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUnknown3() throws Exception {

        for (char c = 'A'; c <= 'Z'; c++) {
            runFailure(new String[]{"-" + c + "xxx"},
                BANNER + "Unknown option: " + c + "xxx\n");
        }
    }

    /**
     * This test case validates that {@code -ver} prints the version number and exists with code 0
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testVer() throws Exception {

        runSuccess(new String[]{"-ver"},
            BANNER, "texput.log");
    }

    /**
     * This test case validates that {@code -version} prints the version number and exists with code 0
* 
     * @throws Exception in case of an error
     */
    @Test
    public void testVersion() throws Exception {

        runSuccess(new String[]{"-version"},
            BANNER, "texput.log");
    }

}
