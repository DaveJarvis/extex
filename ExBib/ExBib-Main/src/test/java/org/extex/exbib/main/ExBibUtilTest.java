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

package org.extex.exbib.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Locale;

import org.extex.exbib.main.cli.CLI;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for {@link ExBibUtil}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibUtilTest extends BibUtilTester {

    /**
     * <testcase> Test that no command line option at all leads to no output.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test001() throws Exception {

        runTest("test", null, CLI.EXIT_OK, Check.EQ, BANNER);
    }

    /**
     * <testcase> Test that the command line option <tt>--</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test002() throws Exception {

        runFailure(BANNER + "The option `--\' needs a parameter.\n", //
            "--");
    }

    /**
     * <testcase> Test that the command line option <tt>-</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test003() throws Exception {

        runFailure(BANNER + "The option `-\' needs a parameter.\n", //
            "-");
    }

    /**
     * <testcase> Test that the input file needs to exist. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test004() throws Exception {

        runFailure(BANNER
                + "I couldn\'t open file some/non/existent/file.bib\n", //
            "-", "some/non/existent/file");
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConfig1() throws Exception {

        runFailure(BANNER + "The option `--config\' needs a parameter.\n",
            "--config");
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs an
     * existing configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConfig2() throws Exception {

        runFailure(
            BANNER
                    + "Installation Error: Some parts of exbibutil could not be found: \n"
                    + "\tConfiguration `exbib/undef\' not found.\n"
                    + "\tConsult the log file for details.\n", "--config",
            "undef");
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs a
     * valid configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    // "strange failure in maven"
    public void testConfig3() throws Exception {

        runFailure(
            "[Fatal Error] :1:1: Premature end of file.\n"
                    + BANNER
                    + "Configuration syntax error Premature end of file. in config/exbib/empty.xml\n",
            "--config", "empty");
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs a
     * valid configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testConfig4() throws Exception {

        runFailure(
            "[Fatal Error] :4:1: XML document structures must start and end within the same entity.\n"
                    + BANNER
                    + "Configuration syntax error XML document structures must start and end within\n"
                    + "the same entity. in config/exbib/incomplete.xml\n",
            "--config", "incomplete");
    }

    /**
     * <testcase> Test that a misconfiguration is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testConfig5() throws Exception {

        runFailure(
            BANNER
                    + "Installation Error: Some parts of exbib could not be found: \n"
                    + "\tClass `undefined.Undefined\' not found in\n"
                    + "document(\"config/exbib/misconfigured.xml\")/exbib/Processor\n"
                    + "\tConsult the log\n" //
                    + "file for details.\n", //
            "--config", "misconfigured");
    }

    /**
     * <testcase> Test that the command line option <tt>--copying</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCopying1() throws Exception {

        runTest("test", null, CLI.EXIT_FAIL, Check.START,
            "                 GNU LESSER GENERAL PUBLIC LICENSE\n", //
            "--copying");
    }

    /**
     * <testcase> Test that the command line option <tt>--csfile</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testCsfile1() throws Exception {

        runFailure(BANNER + "The option `--csfile\' needs a parameter.\n",
            "--csfile");
    }

    /**
     * <testcase> Test that the command line option <tt>--csfile</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testCsfile2() throws Exception {

        runFailure(BANNER + "The csf `exbib\' could not be found.\n",
            "--csfile", "xyzzy", "test");
    }

    /**
     * <testcase> Test that an empty file name is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEmpty1() throws Exception {

        runFailure(BANNER + "The file argument can not be empty.\n", //
            "");
    }

    /**
     * <testcase> Test that an empty file name is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEmpty2() throws Exception {

        runFailure(BANNER + "The file argument can not be empty.\n", //
            "-", "");
    }

    /**
     * <testcase> Test that an empty file name is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testEmpty3() throws Exception {

        runFailure(BANNER + "The file argument can not be empty.\n", //
            "--", "");
    }

    /**
     * <testcase> Test that the command line option <tt>--encoding</tt> needs
     * an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEncoding1() throws Exception {

        runFailure(BANNER + "The option `--encoding\' needs a parameter.\n",
            "--encoding");
    }

    /**
     * <testcase> Test that the command line option <tt>--encoding</tt> needs
     * a known encoding. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEncoding2() throws Exception {

        runFailure(BANNER + "The encoding `xyzzy\' is not known.\n", //
            "-out", "test.out", "--encoding", "xyzzy");
    }

    /**
     * <testcase> Test that the command line option <tt>--help</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHelp1() throws Exception {

        runFailure(
            BANNER
                    + "Usage: exbibutil <options> file\n"
                    + "The following options are supported:\n"
                    + "\t-help\n"
                    + "\t    Show a short list of command line arguments. \n"
                    + "\t-copying\n"
                    + "\t    Display the copyright conditions. \n"
                    + "\t-quiet\n"
                    + "\t-terse\n"
                    + "\t    Act quietly; some informative messages are suppressed. \n"
                    + "\t-verbose\n"
                    + "\t    Act verbosely; some additional informational messages are displayed. \n"
                    + "\t-trace\n"
                    + "\t    Show a detailed trace of many operations. \n"
                    + "\t-version\n"
                    + "\t    Print the version information and exit. \n"
                    + "\t-release\n"
                    + "\t    Print the release number and exit. \n"
                    + "\t-strict\n"
                    + "\t    use the settings for BibTeX 0.99c. \n"
                    + "\t-config file\n"
                    + "\t-minCrossrefs value\n"
                    + "\t    Set the value for min.crossrefs. The default is 2. \n"
                    + "\t-bst style\n" + "\t-logfile file\n"
                    + "\t-outfile file\n", //
            "--help");
    }

    /**
     * <testcase> Test that the command line option <tt>-?</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHelp2() throws Exception {

        runFailure(
            BANNER
                    + "Usage: exbibutil <options> file\n"
                    + "The following options are supported:\n"
                    + "\t-help\n"
                    + "\t    Show a short list of command line arguments. \n"
                    + "\t-copying\n"
                    + "\t    Display the copyright conditions. \n"
                    + "\t-quiet\n"
                    + "\t-terse\n"
                    + "\t    Act quietly; some informative messages are suppressed. \n"
                    + "\t-verbose\n"
                    + "\t    Act verbosely; some additional informational messages are displayed. \n"
                    + "\t-trace\n"
                    + "\t    Show a detailed trace of many operations. \n"
                    + "\t-version\n"
                    + "\t    Print the version information and exit. \n"
                    + "\t-release\n"
                    + "\t    Print the release number and exit. \n"
                    + "\t-strict\n"
                    + "\t    use the settings for BibTeX 0.99c. \n"
                    + "\t-config file\n"
                    + "\t-minCrossrefs value\n"
                    + "\t    Set the value for min.crossrefs. The default is 2. \n"
                    + "\t-bst style\n" + "\t-logfile file\n"
                    + "\t-outfile file\n", //
            "-?");
    }

    /**
     * <testcase> Test that the command line option <tt>--logfile</tt> needs
     * an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLogfile01() throws Exception {

        runFailure(BANNER + "The option `--logfile\' needs a parameter.\n",
            "--logfile");
    }

    /**
     * <testcase> Test that the command line option <tt>--logfile</tt> can be
     * used to redirect the log output. It is tested that the log file is
     * created and the ExBib instance reports the log file with getLogfile().
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testLogfile02() throws Exception {

        File log = new File("test.lg");
        if (log.exists() && !log.delete()) {
            assertTrue(log.toString() + ": deletion failed", false);
        }
        assertFalse(log.exists());

        try {
            ExBibUtil exbib =
                    runTest("test", "", CLI.EXIT_FAIL, Check.EQ, BANNER,
                        "test.bib", "-log", log.toString());
            assertTrue(log.exists());
            assertNotNull(exbib);
            // assertEquals("test.lg", exbib.getLogfile());

        } finally {
            if (log.exists() && !log.delete()) {
                assertTrue(log.toString() + ": deletion failed", false);
            }
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--logfile</tt> can be
     * used to redirect the log output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testLogfile03() throws Exception {

        runTest("test", "", CLI.EXIT_FAIL, Check.EQ, BANNER, //
            "test.bib", "-log", "");
    }

    /**
     * <testcase> Test that everything might go right. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOk1() throws Exception {

        ExBibUtil exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER, //
                    "test.bib");
        assertEquals("exbibutil", exbib.getProgramName());
        // assertEquals("test.bbl", exbib.getOutfile());
        // assertEquals("test.blg", exbib.getLogfile());
        // assertFalse("trace", exbib.isTrace());
        // assertFalse("trace", exbib.isDebug());
        assertNull("logger", exbib.getLogger()); // since closed already
    }

    /**
     * <testcase> Test that everything might go right using the static method.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOk2() throws Exception {

        String[] args = {"test.bib"};
        File aux = new File("test.bib");
        Writer w = new FileWriter(aux);
        try {
            w.write("\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n");
        } finally {
            w.close();
        }

        Locale.setDefault(Locale.ENGLISH);
        PrintStream err = System.err;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setErr(new PrintStream(baos));
            assertEquals(0, ExBibUtil.commandLine(args));
            assertEquals(BANNER, baos.toString().replaceAll("\r", ""));
        } finally {
            System.setErr(err);
            if (!aux.delete()) {
                assertTrue(aux.toString() + ": deletion failed", false);
            }
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> needs
     * an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile1() throws Exception {

        runFailure(BANNER + "The option `--outfile\' needs a parameter.\n",
            "--outfile");
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt>
     * discards the output if the file name is empty. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile2() throws Exception {

        runTest("test", null, CLI.EXIT_OK, Check.START, BANNER
                + "The output is discarded.\n" + "Runtime ", //
            "-v", "--outfile", "");
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> sends
     * the output to stdout if the file name is "-". </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile3() throws Exception {

        runTest("test", null, CLI.EXIT_OK, Check.START, BANNER
                + "The output is sent to stdout.\n" + "Runtime ", //
            "-v", "--outfile", "-");
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> sends
     * the output to the file. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile4() throws Exception {

        File file = new File("test.out");
        if (file.exists() && !file.delete()) {
            assertTrue(file.toString() + ": deletion failed", false);
        }

        try {
            runTest("test", null, CLI.EXIT_OK, Check.START, BANNER
                    + "The output file: test.out\n" + "Runtime ", //
                "-v", "--outfile", file.toString());
            assertTrue(file.exists());
        } finally {
            if (file.exists() && !file.delete()) {
                assertTrue(file.toString() + ": deletion failed", false);
            }

        }
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt>
     * complains if the output file can not be opened. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile5() throws Exception {

        runFailure(
            BANNER
                    + "The output file: some/file/for/writing\n"
                    + "The output file `some/file/for/writing\' could not be opened.\n", //
            "-v", "--outfile", "some/file/for/writing", "test.bib");
    }

    /**
     * <testcase> Test that the command line option <tt>--progname</tt> needs
     * an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgName1() throws Exception {

        runFailure(BANNER + "The option `--progname\' needs a parameter.\n",
            "--progname");
    }

    /**
     * <testcase> Test that the command line option <tt>--progname</tt> can be
     * queried with getProgramName(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgName11() throws Exception {

        AbstractMain exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK, Check.EQ, null, //
                    "--progname", "xxx", "test.bib");
        assertEquals("xxx", exbib.getProgramName());
    }

    /**
     * <testcase> Test that the command line option <tt>--progname</tt> can be
     * used to set the program name. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgName2() throws Exception {

        runSuccess("This is abc, Version " + ExBib.VERSION + "\n", //
            "--progname", "abc");
    }

    /**
     * <testcase> Test that the command line option <tt>--quiet</tt> is
     * honored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testQuiet1() throws Exception {

        runSuccess("", //
            "--quiet");
    }

    /**
     * <testcase> Test that the command line option <tt>--release</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRelease1() throws Exception {

        runFailure(ExBib.VERSION + "\n", //
            "--release");
    }

    /**
     * <testcase> Test that the command line option <tt>--terse</tt> is
     * honored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTerse1() throws Exception {

        runSuccess("", //
            "--terse");
    }

    /**
     * <testcase> Test that the command line option <tt>-type</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType1() throws Exception {

        runFailure(BANNER + "The option `--type\' needs a parameter.\n", //
            "--type");
    }

    /**
     * <testcase> Test that the command line option <tt>-type</tt> needs a
     * known argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType2() throws Exception {

        runFailure(BANNER + "The output type `xyzzy\' is not known.\n", //
            "--type", "xyzzy");
    }

    /**
     * <testcase> Test bib is a known output type. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType3() throws Exception {

        runSuccess(BANNER, //
            "--type", "bib");
    }

    /**
     * <testcase> Test xml is a known output type. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testType4() throws Exception {

        runSuccess(BANNER, //
            "--type", "xml");
    }

    /**
     * <testcase> Test that an undefined command line option is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndefined1() throws Exception {

        runSuccess(BANNER + "Unknown option `--undefined\' ignored.\n", //
            "--undefined");
    }

    /**
     * <testcase> Test that an undefined command line option is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndefined2() throws Exception {

        runSuccess(BANNER + "Unknown option `--undefined\' ignored.\n", //
            "--undefined=123");
    }

    /**
     * <testcase> Test that the command line option <tt>--verbose</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVerbose1() throws Exception {

        runTest("test", "only comment\n", CLI.EXIT_OK, Check.REGEX, BANNER
                + "The output is sent to stdout.\n" + "Runtime [0-9]* ms\n", //
            "--verbose");
    }

    /**
     * <testcase> Test that the command line option <tt>--verbose</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testVerbose2() throws Exception {

        String bib = new File("test.bib").toString().replaceAll("\\\\", ".");
        runTest("test", "only comment\n", CLI.EXIT_OK, Check.REGEX, BANNER
                + "The output is sent to stdout.\n" + "Database file #1: \n"
                + bib + ".bib\n" + "Runtime [0-9]* ms\n", //
            "--verbose", "test.bib");
    }

    /**
     * <testcase> Test that the command line option <tt>--version</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVersion1() throws Exception {

        runFailure(
            BANNER
                    + "Copyright (C) 2002-"
                    + YEAR
                    + " Gerd Neugebauer (mailto:gene@gerd-neugebauer.de).\n"
                    + "There is NO warranty.  Redistribution of this software is\n"
                    + "covered by the terms of the GNU Library General Public License.\n"
                    + "For more information about these matters, use the command line\n"
                    + "switch -copying.\n", //
            "--version");
    }

    /**
     * <testcase> Test that the command line option <tt>--version</tt> works
     * when abbreviated as <tt>--vers</tt>. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVersion2() throws Exception {

        runFailure(
            BANNER
                    + "Copyright (C) 2002-"
                    + YEAR
                    + " Gerd Neugebauer (mailto:gene@gerd-neugebauer.de).\n"
                    + "There is NO warranty.  Redistribution of this software is\n"
                    + "covered by the terms of the GNU Library General Public License.\n"
                    + "For more information about these matters, use the command line\n"
                    + "switch -copying.\n", //
            "--vers");
    }

}
