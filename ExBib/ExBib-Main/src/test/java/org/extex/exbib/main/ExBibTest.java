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

import org.junit.Test;

/**
 * This is a test suite for {@link ExBib}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibTest extends BibTester {

    /**
     * <testcase> Test that no command line option at all leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test001() throws Exception {

        runFailure(BANNER + "Missing aux file parameter.\n");
    }

    /**
     * <testcase> Test that the command line option <tt>--</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test002() throws Exception {

        runFailure(BANNER + "The option `--\' needs a parameter.\n",//
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

        runFailure(BANNER + "The option `-\' needs a parameter.\n",//
            "-");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test004() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",//
            "abc.aux", "abc.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test005() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",//
            "abc.aux", "--", "abc.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test006() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",//
            "--", "abc.aux", "abc.aux");
    }

    /**
     * <testcase> Test that an empty aux file is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux01() throws Exception {

        runTest("test", "", 1, check.EQ, BANNER
                + "I found no style file while reading test.aux\n"
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n",
            "test.aux");
    }

    /**
     * <testcase> Test that a non-existent aux file is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux02() throws Exception {

        File aux = new File("undefined.aux");
        assertFalse(aux.exists());

        runTest("undefined", null, 1, check.EQ, BANNER
                + "I couldn\'t open file undefined.aux\n",//
            "undefined.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux03() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",//
            "abc.aux", "abc.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux04() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",//
            "abc.aux", "--", "abc.aux");
    }

    /**
     * <testcase> Test that an unknown bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux10() throws Exception {

        runTest("test", "\\bibstyle{xyzzy}\n", 1, check.EQ, BANNER
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n"
                + "I couldn\'t open style file xyzzy\n",//
            "test.aux");
    }

    /**
     * <testcase> Test that an unknown bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux11() throws Exception {

        runTest("test", "\\bibstyle{xyzzy.bst}\n", 1, check.EQ, BANNER
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n"
                + "I couldn\'t open style file xyzzy.bst\n",//
            "test.aux");
    }

    /**
     * <testcase> Test that a missing bst in the aux file can be specified on
     * the command line. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux12() throws Exception {

        runTest("test", "", 1, check.EQ, BANNER
                + "I found no style file while reading test.aux\n"
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n"
                + "I couldn\'t open style file xyzzy\n",//
            "test.aux", "-bst", "xyzzy");
    }

    /**
     * <testcase> Test that a missing bibliography in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux13() throws Exception {

        runTest("test", "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            1, check.EQ, BANNER
                    + "I found no \\bibdata commands while reading test.aux\n"
                    + "I found no \\citation commands while reading test.aux\n"
                    + "(There were 2 errors)\n",//
            "test.aux");
    }

    /**
     * <testcase> Test that an unknown bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux14() throws Exception {

        runTest("test", "\\bibstyle{xyzzy}\n", 1, check.EQ, BANNER
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n"
                + "I couldn\'t open style file xyzzy\n",//
            "test.aux");
    }

    /**
     * <testcase> Test that an unknown bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux15() throws Exception {

        runTest("test", "\\citation{*}\n\\bibstyle{xyzzy}\n", 1, check.EQ,
            BANNER + "I found no \\bibdata commands while reading test.aux\n"
                    + "I couldn\'t open style file xyzzy\n",//
            "test.aux");
    }

    /**
     * <testcase> Test that an unknown bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux16() throws Exception {

        runTest("test", "\\citation{*}\n\\bibdata{qqq}\n\\bibstyle{xyzzy}\n",
            1, check.EQ, BANNER + "I couldn\'t open style file xyzzy\n",
            "test.aux");
    }

    /**
     * <testcase> Test that a missing bibliography is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux20() throws Exception {

        runTest("test", "\\citation{*}\n\\bibdata{test}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n", 1,
            check.EQ, BANNER + "File `test\' not found\n",//
            "test.aux");
    }

    /**
     * <testcase> Test that the command line option <tt>--bst</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testBst1() throws Exception {

        runFailure(BANNER + "The option `--bst\' needs a parameter.\n", "--bst");
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

        runFailure(BANNER + "Configuration `exbib/undef\' not found.\n",
            "--config", "undef");
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs a
     * valid configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
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
    public void testConfig5() throws Exception {

        runFailure(
            BANNER
                    + "Installation Error: Some parts of exbib could not be found: \n"
                    + "\tClass `undefined.Undefined\' not found in\n"
                    + "document(\"config/exbib/misconfigured.xml\")/exbib/Processor\n"
                    + "\tConsult the log\n" //
                    + "file for details.\n",//
            "--config", "misconfigured", "test");
    }

    /**
     * <testcase> Test that the command line option <tt>--copying</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCopying1() throws Exception {

        runTest("test", null, 1, check.START,
            "                 GNU LESSER GENERAL PUBLIC LICENSE\n",//
            "--copying");
    }

    /**
     * <testcase> Test that the command line option <tt>--csfile</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
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
    public void testCsfile2() throws Exception {

        runFailure(BANNER + "The csf `exbib\' could not be found.\n",
            "--csfile", "xyzzy", "test");
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
                    + "Usage: exbib <options> file\n"
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
                    + "\t-outfile file\n",//
            "--help");
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
    public void testLogfile02() throws Exception {

        File log = new File("test.lg");
        log.delete();
        assertFalse(log.exists());

        try {
            ExBib exbib =
                    runTest(
                        "test",
                        "",
                        1,
                        check.EQ,
                        BANNER
                                + "I found no style file while reading test.aux\n"
                                + "I found no \\bibdata commands while reading test.aux\n"
                                + "I found no \\citation commands while reading test.aux\n",
                        "test.aux", "-log", log.toString());
            assertTrue(log.exists());
            assertNotNull(exbib);
            assertEquals("test.lg", exbib.getLogfile());

        } finally {
            log.delete();
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--logfile</tt> can be
     * used to redirect the log output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLogfile03() throws Exception {

        runTest("test", "", 1, check.EQ, BANNER
                + "I found no style file while reading test.aux\n"
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n",
            "test.aux", "-log", "");
    }

    /**
     * <testcase> Test that the command line option <tt>--min_crossrefs</tt>
     * needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefs1() throws Exception {

        runFailure(BANNER
                + "The option `--min_crossrefs\' needs a parameter.\n",
            "--min_crossrefs");
    }

    /**
     * <testcase> Test that the command line option <tt>--min_crossrefs</tt>
     * needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefs2() throws Exception {

        runFailure(
            BANNER
                    + "The option `--min_crossrefs\' needs an integer parameter.\n",
            "--min_crossrefs", "abc");
    }

    /**
     * <testcase> Test that the command line option <tt>--min.crossrefs</tt>
     * needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefs3() throws Exception {

        runFailure(BANNER
                + "The option `--min.crossrefs\' needs a parameter.\n",
            "--min.crossrefs");
    }

    /**
     * <testcase> Test that the command line option <tt>--min.crossrefs</tt>
     * needs an argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefs4() throws Exception {

        runFailure(
            BANNER
                    + "The option `--min.crossrefs\' needs an integer parameter.\n",
            "--min.crossrefs", "abc");
    }

    /**
     * <testcase> Test that everything might go right. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOk1() throws Exception {

        ExBib exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    0, check.EQ, BANNER
                            + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",//
                    "test.aux");
        assertEquals("exbib", exbib.getProgramName());
        assertEquals("test.bbl", exbib.getOutfile());
        assertEquals("test.blg", exbib.getLogfile());
        assertFalse("trace", exbib.isTrace());
        assertFalse("trace", exbib.isDebug());
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

        String[] args = {"test.aux"};
        File aux = new File("test.aux");
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
            assertEquals(0, ExBib.commandLine(args));
            assertEquals(BANNER + "Warning: empty author in whole-journal\n"
                    + "Warning: empty title in whole-journal\n", baos
                .toString().replaceAll("\r", ""));
        } finally {
            System.setErr(err);
            aux.delete();
            new File("test.bbl").delete();
            new File("test.blg").delete();
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
     * complains if the output file can not be opened. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile2() throws Exception {

        runFailure(
            BANNER
                    + "The output file `some/file/for/writing\' could not be opened.\n",//
            "-v", "--outfile", "some/file/for/writing", "test.aux");
    }

    /**
     * <testcase> Test that the output can e redirected to stdout. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile3() throws Exception {

        PrintStream out = System.out;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(baos));
            runTest("test", "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n", 0,
                check.EQ, null,//
                "test.aux", "--out", "-");
            assertTrue(baos
                .toString()
                .replaceAll("\r", "")
                .startsWith(
                    "\\newcommand{\\noopsort}[1]{} \\newcommand{\\printfirst}[2]{#1}\n"
                            + "  \\newcommand{\\singleletter}[1]{#1} \\newcommand{\\switchargs}[2]{#2#1}\n"
                            + "\\begin{thebibliography}{10}\n"
                            + "\n"
                            + "\\bibitem{article-minimal}\n"
                            + "L[eslie]~A. Aamport.\n"
                            + "\\newblock The gnats and gnus document preparation system.\n"
                            + "\\newblock {\\em \\mbox{G-Animal\'s} Journal}, 1986.\n"
                            + "\n"
                            + "\\bibitem{article-full}\n"
                            + "L[eslie]~A. Aamport.\n"
                            + "\\newblock The gnats and gnus document preparation system.\n"
                            + "\\newblock {\\em \\mbox{G-Animal\'s} Journal}, 41(7):73+, July 1986.\n"
                            + "\\newblock This is a full ARTICLE entry.\n"));
        } finally {
            System.setOut(out);
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> takes
     * an empty argument to discard the output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile4() throws Exception {

        runTest("test", "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n", 0,
            check.EQ, null,//
            "test.aux", "-v", "--out", "");
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

        ExBib exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    0, check.EQ, null,//
                    "--progname", "xxx", "test.aux");
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

        runFailure("This is abc, Version " + ExBib.VERSION + "\n"
                + "Missing aux file parameter.\n",//
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

        runFailure("Missing aux file parameter.\n",//
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

        runFailure(ExBib.VERSION + "\n",//
            "--release");
    }

    /**
     * <testcase> Test that everything might go right in strict mode.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStrict1() throws Exception {

        ExBib exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    0, check.EQ, BANNER
                            + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",//
                    "test.aux", "--strict");
        assertEquals("exbib", exbib.getProgramName());
        assertEquals("test.bbl", exbib.getOutfile());
        assertEquals("test.blg", exbib.getLogfile());
        assertFalse("trace", exbib.isTrace());
        assertFalse("trace", exbib.isDebug());
        assertNull("logger", exbib.getLogger()); // since closed already
    }

    /**
     * <testcase> Test that the command line option <tt>--terse</tt> is
     * honored. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTerse1() throws Exception {

        runFailure("Missing aux file parameter.\n",//
            "--terse");
    }

    /**
     * <testcase> Test that the command line option <tt>--trace</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTrace1() throws Exception {

        ExBib exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    0,
                    check.START, //
                    BANNER
                            + "The output file: test.bbl\n"
                            + "The top-level auxiliary file: test\n" // TODO
                            // .aux
                            + "The style file src/test/resources/bibtex/base/plain.bst\n"
                            + "--- do READ\n"
                            + "Database file #1: src/test/resources/bibtex/base/xampl.bib\n"
                            + "--- do ITERATE { presort }\n"//
                            + "--- step presort\n"//
                            + "--- step type$\n"//
                            + "--- push \"article\"\n"//
                            + "--- step \"book\"\n"//
                            + "--- push \"book\"\n"//
                            + "--- step =\n"//
                            + "--- push #0\n"//
                            + "--- step type$\n"//
                            + "--- push \"article\"\n"//
                            + "--- step \"inbook\"\n"//
                            + "--- push \"inbook\"\n",//
                    "--trace", "--verbose", "test.aux");
        assertTrue("trace", exbib.isTrace());
    }

    /**
     * <testcase> Test that an undefined command line option is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testUndefined1() throws Exception {

        runFailure(BANNER + "Unknown option `undefined\' ignored.\n"
                + "Missing aux file parameter.\n",//
            "--undefined");
    }

    /**
     * <testcase> Test that the command line option <tt>--verbose</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVerbose1() throws Exception {

        runTest(
            "test",
            "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            0,
            check.REGEX,
            BANNER
                    + "The output file: test.bbl\n"
                    + "The top-level auxiliary file: test\n" // TODO .aux
                    + "The style file src/test/resources/bibtex/base/plain.bst\n"
                    + "Database file #1: src/test/resources/bibtex/base/xampl.bib\n"
                    + "Warning: empty author in whole-journal\n"
                    + "Warning: empty title in whole-journal\n"
                    + "\\(There were 2 warnings\\)\n" //
                    + "Runtime [0-9]* ms\n",//
            "--verbose", "test.aux");
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
                    + "switch -copying.\n",//
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
                    + "switch -copying.\n",//
            "--vers");
    }

}
