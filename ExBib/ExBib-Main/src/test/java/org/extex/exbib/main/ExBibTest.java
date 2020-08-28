/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.main;

import org.extex.cli.CLI;
import org.extex.exbib.core.ExBib;
import org.extex.exbib.core.ExBib.ExBibDebug;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link ExBib}.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class ExBibTest extends BibTester {

    /**
     * The field <tt>USAGE</tt> contains the expected usage description.
     */
    private static final String USAGE =
            "Usage: exbib <options> file\n"
                    + "The following options are supported:\n"
                    + "\t-D<property>=<value>\n"
                    + "\t\tSet the given property to the value.\n"
                    + "\t-[-] <file>\n"
                    + "\t\tUse this argument as file name -- even when it looks like an option.\n"
                    + "\t--trad[itional] | -7\n"
                    + "\t\toperate in the original 7-bit mode.\n"
                    + "\t--8[bit] | -8\n"
                    + "\t\tforce 8-bit mode, no CS file used.\n"
                    + "\t--a[vailableCharsets]\n"
                    + "\t\tList the available encoding names and exit.\n"
                    + "\t--bib-[encoding] | --bib.[encoding] | -E <enc>\n"
                    + "\t\tUse the given encoding for the bib files.\n"
                    + "\t--bs[t] <bst style>\n"
                    + "\t\tspecify the bst style.\n"
                    + "\t--con[figuration] | -c <configuration>\n"
                    + "\t\tUse the configuration given. This is not a file!\n"
                    + "\t--c[opying]\n"
                    + "\t\tDisplay the copyright conditions.\n"
                    + "\t--csf-[encoding] | --csf.[encoding] <enc>\n"
                    + "\t\tUse the given encoding for the csf files.\n"
                    + "\t--cs[file] <csfile>\n"
                    + "\t\tName the csf for defining characters and the sort order\n"
                    + "\t--d[ebug] | -d\n"
                    + "\t\tRun in debug mode.\n"
                    + "\t--e[ncoding] | -e <enc>\n"
                    + "\t\tUse the given encoding for the output file.\n"
                    + "\t--h[elp] | -? | -h\n"
                    + "\t\tShow a short list of command line arguments.\n"
                    + "\t--la[nguage] | -L <language>\n"
                    + "\t\tUse the named language for message.\n"
                    + "\t\tThe argument is a two-letter ISO code.\n"
                    + "\t--loa[d] <file>\n"
                    + "\t\tAdditionally load settings from the file given.\n"
                    + "\t--l[ogfile] | -l <file>\n"
                    + "\t\tSend the output to the log file named instead of the default one.\n"
                    + "\t--m[in-crossrefs] | --min.[crossrefs] | --min_[crossrefs] | -M <n>\n"
                    + "\t\tSet the value for min.crossrefs. The default is 2.\n"
                    + "\t--o[utfile] | --outp[ut] | -o <file>\n"
                    + "\t\tRedirect the output to the file given.\n"
                    + "\t\tThe file name - can be used to redirect to stdout\n"
                    + "\t\tThe empty file name can be used to discard the output completely\n"
                    + "\t--p[rogname] | --progr[am-name] | --program.[name] | -p <program>\n"
                    + "\t\tSet the program name for messages.\n"
                    + "\t--q[uiet] | --t[erse] | -q\n"
                    + "\t\tAct quietly; some informative messages are suppressed.\n"
                    + "\t--r[elease]\n"
                    + "\t\tPrint the release number and exit.\n"
                    + "\t--so[rter] | -s <sort>\n"
                    + "\t\tUse the specified sorter, e.g. locale:de or csf:ascii.\n"
                    + "\t--b[ibtex] | --s[trict]\n"
                    + "\t\tUse the configuration for BibTeX 0.99c.\n"
                    + "\t--tr[ace] | -t\n"
                    + "\t\tShow a detailed trace of many operations.\n"
                    + "\t--v[erbose] | -v\n"
                    + "\t\tAct verbosely; some additional informational messages are displayed.\n"
                    + "\t--vers[ion]\n"
                    + "\t\tPrint the version information and exit.\n";

    /**
     * <testcase> Test that no command line option at all leads to an error.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test001() throws Exception {

        runFailure(BANNER + "Missing aux file parameter.\n"
                + "(There was 1 error)\n");
    }

    /**
     * <testcase> Test that the command line option <tt>--</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test002() throws Exception {

        runFailure( BANNER + "The option `--' needs a parameter.\n",
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

        runFailure( BANNER + "The option `-' needs a parameter.\n",
                    "-");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test004() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",
            "abc.aux", "abc.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test005() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",
            "abc.aux", "--", "abc.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test006() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",
            "--", "abc.aux", "abc.aux");
    }

    /**
     * <testcase> Test that an empty aux file is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux01() throws Exception {

        runTest("test", "", CLI.EXIT_FAIL, Check.EQ, BANNER
                + "I found no style file while reading test.aux\n"
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n"
                + "(There were 3 errors)\n", "test.aux");
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

        runTest("undefined", null, CLI.EXIT_FAIL, Check.EQ, BANNER
                + "I couldn't open file undefined.aux\n"
                + "(There was 1 error)\n",
            "undefined.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux03() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",
            "abc.aux", "abc.aux");
    }

    /**
     * <testcase> Test that multiple files lead to an error. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux04() throws Exception {

        runFailure(BANNER + "Only one aux file can be processed.\n",
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

        runTest("test", "\\bibdata{abc}\n" + "\\citation{*}\n"
                + "\\bibstyle{xyzzy}\n", CLI.EXIT_FAIL,
            Check.EQ,
            BANNER
                    + "I couldn't open style file xyzzy\n"
                    + "(There was 1 error)\n",
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

        runTest("test", "\\bibdata{abc}\n" + "\\citation{*}\n"
                + "\\bibstyle{xyzzy.bst}\n", CLI.EXIT_FAIL, Check.EQ, BANNER
                + "I couldn't open style file xyzzy.bst\n"
                + "(There was 1 error)\n",
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

        runTest("test", "\\bibdata{abc}\n" + "\\citation{*}\n", CLI.EXIT_FAIL,
            Check.EQ, BANNER + "I couldn't open style file xyzzy\n"
                    + "(There was 1 error)\n",
            "test.aux", "--bst", "xyzzy");
    }

    /**
     * <testcase> Test that a missing bibliography in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux13() throws Exception {

        runTest(
            "test",
            "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_FAIL,
            Check.EQ,
            BANNER
                    + "I found no \\bibdata commands for bbl while reading test.aux\n"
                    + "I found no \\citation commands for bbl while reading test.aux\n"
                    + "(There were 2 errors)\n",
            "test.aux");
    }

    /**
     * <testcase> Test that an missing bibdata in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux15() throws Exception {

        runTest(
            "test",
            "\\citation{*}\n\\bibstyle{xyzzy}\n",
            CLI.EXIT_FAIL,
            Check.EQ,
            BANNER
                    + "I found no \\bibdata commands for bbl while reading test.aux\n"
                    + "(There was 1 error)\n",
            "test.aux");
    }

    /**
     * <testcase> Test that a mising bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux16() throws Exception {

        runTest("test",
            "\\citation{*}\n\\bibdata{qqq}\n",
            CLI.EXIT_FAIL,
            Check.EQ,
            BANNER + "I found no style file for bbl while reading test.aux\n"
                    + "(There was 1 error)\n",
            "test.aux");
    }

    /**
     * <testcase> Test that an unknown bst in the aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux17() throws Exception {

        runTest("test", "\\citation{*}\n\\bibdata{qqq}\n\\bibstyle{xyzzy}\n",
            CLI.EXIT_FAIL, Check.EQ, BANNER
                    + "I couldn't open style file xyzzy\n"
                    + "(There was 1 error)\n",
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
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_FAIL, Check.EQ, BANNER + "File `test' not found\n"
                    + "(There was 1 error)\n",
            "test.aux");
    }

    /**
     * <testcase> Test that an aux file contained in an aux file is reported.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux41() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        try( final Writer w = new FileWriter( aux2 ) ) {
            w.write( "\\relax\n" );
        }

        try {
            runTest(
                "test",
                "\\citation{*}\n"
                        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                        + "\\@include{test2}\n"
                        + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                0,
                Check.START,
                BANNER
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "The style file src/test/resources/bibtex/base/plain.bst\n"
                        + "The output file: test.bbl\n"
                        + "Database file #1: src/test/resources/bibtex/base/xampl\n"
                        + "Warning: empty author in whole-journal\n"
                        + "Warning: empty title in whole-journal\n",
                "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                fail( aux2.toString() + ": deletion failed" );
            }
        }
    }

    /**
     * <testcase> Test that an aux file contained in an aux file twice is
     * reported twice. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux42() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        try( final Writer w = new FileWriter( aux2 ) ) {
            w.write( "\\relax\n" );
        }

        try {
            runTest(
                "test",
                "\\citation{*}\n"
                        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                        + "\\@include{test2}\n"
                        + "\\@include{test2}\n"
                        + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                0,
                Check.START,
                BANNER
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "The style file src/test/resources/bibtex/base/plain.bst\n"
                        + "The output file: test.bbl\n"
                        + "Database file #1: src/test/resources/bibtex/base/xampl\n"
                        + "Warning: empty author in whole-journal\n"
                        + "Warning: empty title in whole-journal\n",
                "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                fail( aux2.toString() + ": deletion failed" );
            }
        }
    }

    /**
     * <testcase> Test that an aux file contained in an aux file thrice is
     * reported thrice. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux43() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        try( Writer w = new FileWriter( aux2 ) ) {
            w.write( "\\relax\n" );
        }

        try {
            runTest(
                "test",
                "\\citation{*}\n"
                        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                        + "\\@include{test2}\n"
                        + "\\@include{test2}\n"
                        + "\\@include{test2}\n"
                        + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                0,
                Check.START,
                BANNER
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "The style file src/test/resources/bibtex/base/plain.bst\n"
                        + "The output file: test.bbl\n"
                        + "Database file #1: src/test/resources/bibtex/base/xampl\n"
                        + "Warning: empty author in whole-journal\n"
                        + "Warning: empty title in whole-journal\n",
                "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                fail( aux2.toString() + ": deletion failed" );
            }
        }
    }

    /**
     * <testcase> Test that a non-existing aux file contained in an aux file is
     * reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testAux44() throws Exception {

        File aux = new File(".", "test.aux");
        File aux2 = new File(".", "test2.aux");
        try( final Writer w = new FileWriter( aux2 ) ) {
            w.write( "\\relax\n" );
            w.write( "\\@include{xyzzy}\n" );
        }

        try {
            runTest("test", "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                    + "\\@include{test2}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                CLI.EXIT_FAIL, Check.EQ, BANNER
                        + "The top-level auxiliary file: "
                        + aux.toString()
                        + "\n" + "A level-1 auxiliary file: "
                        + aux2.toString()
                        + "\n"
                        + "I couldn't open file xyzzy.aux\n"
                        + "(There was 1 error)\n",
                "-v", "test.aux");
        } finally {
            if (aux2.exists() && !aux2.delete()) {
                fail( aux2.toString() + ": deletion failed" );
            }
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConfig1() throws Exception {

        runFailure(BANNER + "The option `--config' needs a parameter.\n",
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

        runFailure(BANNER + "Configuration `exbib/undef' not found.\n"
                + "(There was 1 error)\n", "--config", "undef", "test");
    }

    /**
     * <testcase> Test that the command line option <tt>--config</tt> needs a
     * valid configuration. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    // strange failure in maven
    public void testConfig3() throws Exception {

        runFailure(
            "[Fatal Error] :1:1: Premature end of file.\n"
                    + BANNER
                    + "Configuration syntax error Premature end of file. in config/exbib/empty.xml\n",
            "--config", "empty", "test");
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
            BANNER
                    + "[Fatal Error] :4:1: XML document structures must start and end within the same entity.\n"
                    + "Configuration syntax error XML document structures must start and end within\n"
                    + "the same entity. in config/exbib/incomplete.xml\n"
                    + "(There was 1 error)\n",
            "--config", "incomplete", "test");
    }

    /**
     * <testcase> Test that a misconfiguration is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testConfig5() throws Exception {

        runTest(
            "test",
            "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_FAIL,
            Check.EQ,
            BANNER
                    + "Configuration `exbib/processor/undefined.xml' not " +
                "found.\n"
                    + "(There was 1 error)\n",
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

        runTest("test", null, CLI.EXIT_FAIL, Check.START,
            "                 GNU LESSER GENERAL PUBLIC LICENSE\n",
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

        runFailure(BANNER + "The option `--csfile' needs a parameter.\n",
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

        runFailure(BANNER + "The csf `xyzzy' could not be found.\n"
                + "(There was 1 error)\n",
            "--csfile", "xyzzy", "test");
    }

    /**
     * <testcase> Test that the command line option <tt>--csfile ''</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testCsfile3() throws Exception {

        runTest("test", "\\citation{Meone:Title}\n"
                + "\\bibdata{src/test/resources/bibtex/empty/none}\n"
                + "\\bibstyle{src/test/resources/bibtex/empty/empty_2}\n",
            CLI.EXIT_OK, Check.EQ, BANNER, "--csfile=", "test");
    }

    /**
     * <testcase> Test that the command line option <tt>--debug</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDebug1() throws Exception {

        runFailure(BANNER + "The option `--debug' needs a parameter.\n",
            "--debug");
    }

    /**
     * <testcase> Test that the command line option <tt>--debug</tt> needs a
     * known argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDebug2() throws Exception {

        runFailure(BANNER + "The debug mode `xxx' is unknown.\n",
            "--debug=xxx");
    }

    /**
     * <testcase> Test that the command line option <tt>--debug all</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDebug3() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{Meone:Title}\n"
                            + "\\bibdata{src/test/resources/bibtex/empty/none}\n"
                            + "\\bibstyle{src/test/resources/bibtex/empty/empty_2}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER, "--debug=all", "test");
        Set<ExBibDebug> d = exbib.getDebug();
        for (ExBibDebug x : d) {
            assertTrue(x.toString(), d.contains(x));
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--debug=all,search</tt>
     * works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDebug4() throws Exception {

        runTest("test", "\\citation{Meone:Title}\n"
                + "\\bibdata{src/test/resources/bibtex/empty/none}\n"
                + "\\bibstyle{src/test/resources/bibtex/empty/empty_2}\n",
            CLI.EXIT_OK, Check.EQ, BANNER, "--debug=all,search", "test");
    }

    /**
     * <testcase> Test that the command line option
     * <tt>--debug=search,search</tt>works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDebug5() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{Meone:Title}\n"
                            + "\\bibdata{src/test/resources/bibtex/empty/none}\n"
                            + "\\bibstyle{src/test/resources/bibtex/empty/empty_2}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER, "--debug=search,search",
                    "test");
        Set<ExBibDebug> d = exbib.getDebug();
        for (ExBibDebug x : d) {
            if (x == ExBibDebug.SEARCH) {
                assertTrue(x.toString(), d.contains(x));
            } else {
                assertFalse(x.toString(), d.contains(x));
            }
        }
    }

    /**
     * <testcase> Test that the command line option
     * <tt>--debug=all,none,search</tt>works. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testDebug6() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{Meone:Title}\n"
                            + "\\bibdata{src/test/resources/bibtex/empty/none}\n"
                            + "\\bibstyle{src/test/resources/bibtex/empty/empty_2}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER, "--debug=all,none,search",
                    "test");
        Set<ExBibDebug> d = exbib.getDebug();
        for (ExBibDebug x : d) {
            if (x == ExBibDebug.SEARCH) {
                assertTrue(x.toString(), d.contains(x));
            } else {
                assertFalse(x.toString(), d.contains(x));
            }
        }
    }

    /**
     * <testcase> Test that an empty file name is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty1() throws Exception {

        runFailure(BANNER + "The file argument can not be empty.\n",
            "");
    }

    /**
     * <testcase> Test that an empty file name is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty2() throws Exception {

        runFailure(BANNER + "The file argument can not be empty.\n",
            "-", "");
    }

    /**
     * <testcase> Test that an empty file name is reported. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testEmpty3() throws Exception {

        runFailure(BANNER + "The file argument can not be empty.\n",
            "--", "");
    }

    /**
     * <testcase> Test that the command line option <tt>--help</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testHelp1() throws Exception {

        runFailure(BANNER + USAGE,
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

        runFailure(BANNER + USAGE,
            "-?");
    }

    /**
     * <testcase> Test that the command line option <tt>-B</tt> is an ignored
     * option. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnored1() throws Exception {

        runFailure( "Ignoring bibtex8 option `-B'.\n" + BANNER
                + "Missing aux file parameter.\n" + "(There was 1 error)\n",
                    "-v", "-B");
    }

    /**
     * <testcase> Test that the command line option <tt>-B</tt> is an ignored
     * option. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnored2() throws Exception {

        runFailure( "Ignoring bibtex8 option `--big'.\n" + BANNER
                + "Missing aux file parameter.\n" + "(There was 1 error)\n",
                    "-v", "--big");
    }

    /**
     * <testcase> Test that the command line option <tt>--mcites</tt> is an
     * ignored option. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testIgnored3() throws Exception {

        runFailure( "Ignoring bibtex8 option `--mcites'.\n" + BANNER
                + "Missing aux file parameter.\n" + "(There was 1 error)\n",
                    "-v", "--mcites=123");
    }

    /**
     * <testcase> Test that the command line option <tt>--language</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLanguage1() throws Exception {

        runFailure(BANNER + "The option `--language' needs a parameter.\n",
            "--language");
    }

    /**
     * <testcase> Test that the command line option <tt>--language</tt> falls
     * back to en for an unknown language. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLanguage2() throws Exception {

        runFailure(BANNER + "Missing aux file parameter.\n"
                + "(There was 1 error)\n",
            "--language", "xxx");
    }

    /**
     * <testcase> Test that the command line option <tt>--language</tt> can be
     * used to set the language. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLanguage3() throws Exception {

        runFailure(BANNER_DE + "Ein Parameter mit der aux-Datei fehlt.\n"
                + "(Es gab 1 Fehler)\n",
            "--language", "de");
    }

    /**
     * <testcase> Test that the command line option <tt>-Dlanguage=</tt> can be
     * used to set the language. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLanguage4() throws Exception {

        runFailure(BANNER_DE + "Ein Parameter mit der aux-Datei fehlt.\n"
                + "(Es gab 1 Fehler)\n",
            "-Dlanguage=de");
    }

    /**
     * <testcase> Test that the command line option <tt>--load</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLoad1() throws Exception {

        runFailure(BANNER + "The option `--load' needs a parameter.\n",
            "--load");
    }

    /**
     * <testcase> Test that the command line option <tt>--load</tt> does not
     * work with an empty argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLoad2() throws Exception {

        runFailure( BANNER + "The file `' could not be loaded.\n", "--load=");
    }

    /**
     * <testcase> Test that a file for <tt>--load</tt> has to exist. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLoad3() throws Exception {

        runFailure(
            BANNER
                    + "The file `file/which/does/not/exist' could not be " +
                "loaded.\n",
            "--load", "file/which/does/not/exist");
    }

    /**
     * <testcase> Test that a file for <tt>--load</tt> has to exist. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLoad4() throws Exception {

        runFailure(
            BANNER
                    + "The file `file/which/does/not/exist' could not be " +
                "loaded.\n",
            "--load=file/which/does/not/exist");
    }

    /**
     * <testcase> Test that a file for <tt>--load</tt> has to exist. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLoad5() throws Exception {

        runFailure(BANNER + "Missing aux file parameter.\n"
                + "(There was 1 error)\n",
            "--load=src/test/resources/dot/empty");
    }

    /**
     * <testcase> Test that the command line option <tt>--logfile</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLogfile01() throws Exception {

        runFailure(BANNER + "The option `--logfile' needs a parameter.\n",
            "--logfile");
    }

    /**
     * <testcase> Test that the command line option <tt>--logfile</tt> can be
     * used to redirect the log output. It is tested that the log file is
     * created and the {@link ExBib} instance reports the log file with
     * getLogfile(). </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testLogfile02() throws Exception {

        File log = new File("test.lg");
        if (log.exists() && !log.delete()) {
            fail( log.toString() + ": deletion failed" );
        }
        assertFalse(log.exists());

        try {
            AbstractMain exbib =
                    runTest(
                        "test",
                        "",
                        CLI.EXIT_FAIL,
                        Check.EQ,
                        BANNER
                                + "I found no style file while reading test.aux\n"
                                + "I found no \\bibdata commands while reading test.aux\n"
                                + "I found no \\citation commands while reading test.aux\n"
                                + "(There were 3 errors)\n", "test.aux",
                        "--log", log.toString());
            assertTrue(log.exists());
            assertNotNull(exbib);
            // assertEquals("test.lg", exbib.getLogfile());

        } finally {
            if (log.exists() && !log.delete()) {
                fail( log.toString() + ": deletion failed" );
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
    public void testLogfile03() throws Exception {

        runTest("test", "", CLI.EXIT_FAIL, Check.EQ, BANNER
                + "I found no style file while reading test.aux\n"
                + "I found no \\bibdata commands while reading test.aux\n"
                + "I found no \\citation commands while reading test.aux\n"
                + "(There were 3 errors)\n", "test.aux", "--log", "");
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
                + "The option `--min_crossrefs' needs a parameter.\n",
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
                    + "The option `--min_crossrefs' needs an integer " +
                "parameter.\n",
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
                + "The option `--min.crossrefs' needs a parameter.\n",
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
                    + "The option `--min.crossrefs' needs an integer " +
                "parameter.\n",
            "--min.crossrefs", "abc");
    }

    /**
     * <testcase> Test that the command line option <tt>--min.crossrefs</tt>
     * stores its numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefs5() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK,
                    Check.EQ,
                    BANNER + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",
                    "test.aux", "--min.crossrefs", "3");
        assertEquals("3", exbib.getProperty(ExBib.PROP_MIN_CROSSREF));
    }

    /**
     * <testcase> Test that the command line option <tt>--min.crossrefs</tt>
     * stores its numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefs6() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                            + "\\biboption{min.crossref=3}\n",
                    CLI.EXIT_OK,
                    Check.EQ,
                    BANNER + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",
                    "test.aux");
        assertEquals("3", exbib.getProperty("min.crossref.bbl"));
    }

    /**
     * <testcase> Test that the command line option <tt>--min.crossrefs</tt>
     * stores its numeric argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testMinCrossrefsErr1() throws Exception {

        runTest("test",
            "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n"
                    + "\\biboption{min.crossref=xyz}\n",
            CLI.EXIT_OK,
            Check.EQ,
            BANNER
                    + "The option `min.crossref' must be numeric instead of `xyz'.\n"
                    + "Warning: empty author in whole-journal\n"
                    + "Warning: empty title in whole-journal\n",
            "test.aux");
    }

    /**
     * <testcase> Test that everything might go right. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOk1() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER
                            + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",
                    "test.aux");
        assertEquals("exbib", exbib.getProgramName());
        // assertEquals("test.bbl", exbib.getOutfile());
        // assertEquals("test.blg", exbib.getLogfile());
        assertFalse("trace", exbib.isTrace());
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

        String[] args = {"test.aux"};
        File aux = new File("test.aux");
        try( final Writer w = new FileWriter( aux ) ) {
            w.write( "\\citation{*}\n"
                         + "\\bibdata{src/test/resources/bibtex/base/xampl" +
                         ".bib}\n"
                         + "\\bibstyle{src/test/resources/bibtex/base/plain}\n" );
        }

        Locale.setDefault(Locale.ENGLISH);
        PrintStream err = System.err;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.setErr(new PrintStream(baos));
            assertEquals(0, ExBibMain.commandLine(args));
            assertEquals(BANNER + "Warning: empty author in whole-journal\n"
                    + "Warning: empty title in whole-journal\n", baos
                .toString().replaceAll("", ""));
        } finally {
            System.setErr(err);
            if (!aux.delete()) {
                fail( aux.toString() + ": deletion failed" );
            }
            if (!new File("test.bbl").delete()) {
                fail( "test.bbl: deletion failed" );
            }
            if (!new File("test.blg").delete()) {
                fail( "test.blg: deletion failed" );
            }
        }
    }

    /**
     * <testcase> Test that the optional argument in aux file macros redirects
     * the output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOk3() throws Exception {

        File xxx = new File("test.xxx");
        if (xxx.exists() && !xxx.delete()) {
            fail( "test.xxx: deletion failed" );
        }
        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation[xxx]{*}\n"
                            + "\\bibdata[xxx]{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle[xxx]{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER
                            + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",
                    "test.aux");
        assertEquals("exbib", exbib.getProgramName());
        // assertNull(exbib.getOutfile());
        // assertEquals("test.blg", exbib.getLogfile());
        assertFalse("trace", exbib.isTrace());
        // assertFalse("trace", exbib.isDebug());
        assertNull("logger", exbib.getLogger()); // since closed already
        if (!xxx.exists()) {
            fail( "test.xxx missing" );
        }

        if (xxx.exists() && !xxx.delete()) {
            fail( "test.xxx: deletion failed" );
        }
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile1() throws Exception {

        runFailure(BANNER + "The option `--outfile' needs a parameter.\n",
            "--outfile");
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> complains
     * if the output file can not be opened. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile2() throws Exception {

        runTest("test", "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_FAIL, Check.EQ,
                "The output file `some/file/for/writing' could not be opened.\n"
                    + "(There was 1 error)\n", "-q", "--outfile",
            "some/file/for/writing", "test.aux");
    }

    /**
     * <testcase> Test that the output can e redirected to stdout. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile3() throws Exception {

        runTest(
            "test",
            "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            0,
            Check.START,
            "\\newcommand{\\noopsort}[1]{} \\newcommand{\\printfirst}[2]{#1}\n"
                    + "  \\newcommand{\\singleletter}[1]{#1} \\newcommand{\\switchargs}[2]{#2#1}\n"
                    + "\\begin{thebibliography}{10}\n"
                    + "\n"
                    + "\\bibitem{article-minimal}\n"
                    + "L[eslie]~A. Aamport.\n"
                    + "\\newblock The gnats and gnus document preparation system.\n"
                    + "\\newblock {\\em \\mbox{G-Animal's} Journal}, 1986.\n"
                    + "\n"
                    + "\\bibitem{article-full}\n"
                    + "L[eslie]~A. Aamport.\n"
                    + "\\newblock The gnats and gnus document preparation system.\n"
                    + "\\newblock {\\em \\mbox{G-Animal's} Journal}, 41(7)" +
                ":73+, July 1986.\n"
                    + "\\newblock This is a full ARTICLE entry.\n", Check.EQ,
            null,
            "test.aux", "--out", "-");
    }

    /**
     * <testcase> Test that the command line option <tt>--outfile</tt> takes an
     * empty argument to discard the output. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testOutfile4() throws Exception {

        runTest("test", "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n", 0,
            Check.EQ, null,
            "test.aux", "-v", "--out", "");
    }

    /**
     * <testcase> Test that the command line option <tt>--progname</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testProgName1() throws Exception {

        runFailure(BANNER + "The option `--progname' needs a parameter.\n",
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
                    CLI.EXIT_OK, Check.EQ, null,
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
                + "Missing aux file parameter.\n" + "(There was 1 error)\n",
            "--progname", "abc");
    }

    /**
     * <testcase> Test that the command line option <tt>--quiet</tt> is honored.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testQuiet1() throws Exception {

        runFailure("Missing aux file parameter.\n" + "(There was 1 error)\n",
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

        runFailure(ExBib.VERSION + "\n",
            "--release");
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> needs an
     * argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter01() throws Exception {

        runFailure(BANNER + "The option `--sorter' needs a parameter.\n",
            "--sorter");
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> needs a
     * valid argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter10() throws Exception {

        runFailure(BANNER + "The sorter `unknown' could not be found.\n"
                + "(There was 1 error)\n",
            "--sorter=unknown:xxx", "test.aux");
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> needs a
     * valid argument. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorter11() throws Exception {

        runFailure(BANNER + "The sorter `unknown' could not be found.\n"
                + "(There was 1 error)\n",
            "--sorter", "unknown:xxx", "test.aux");
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> knows a
     * locale sorter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorterLocale01() throws Exception {
        File data =
            makeFile( "target/data.bib", "UTF-8",
                      "@Item{ a,  value = {a} }\n"
                          + "@Item{ ae,  value = {ae} }\n"
                          + "@Item{ ab,  value = {ab} }\n"
                          + "@Item{ af,  value = {af} }\n"
                          + "@Item{ a1,  value = {A} }\n"
                          + "@Item{ ae1, value = {\u00e4} }\n"
                          + "@Item{ ac,  value = {ac} }\n" );
        data.deleteOnExit();

        runTest( "test", "\\citation{*}\n" + "\\bibdata{target/data.bib}\n"
                     + "\\bibstyle{src/test/resources/bibtex/sort/sort}\n",
                 CLI.EXIT_OK, Check.EQ, "a\nA\n\u00e4\nab\nac\nae\naf\n",
                 Check.EQ,
                 BANNER,
                 "--sorter", "locale:en_US", "test.aux", "--out=-",
                 "--enc=UTF-8" );
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> knows a
     * locale sorter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorterLocale02() throws Exception {
        File data =
                makeFile("target/data.bib", "UTF-8",
                    "@Item{ a,  value = {a} }\n"
                            + "@Item{ ae,  value = {ae} }\n"
                            + "@Item{ ab,  value = {ab} }\n"
                            + "@Item{ af,  value = {af} }\n"
                            + "@Item{ a1,  value = {A} }\n"
                            + "@Item{ ae1, value = {\u00e4} }\n"
                            + "@Item{ ac,  value = {ac} }\n");

        data.deleteOnExit();

        runTest("test", "\\citation{*}\n" + "\\bibdata{target/data.bib}\n"
                + "\\bibstyle{src/test/resources/bibtex/sort/sort}\n",
            CLI.EXIT_OK, Check.EQ, "a\nA\n\u00e4\nab\nac\nae\naf\n",
            Check.EQ, BANNER,
            "--sorter", "locale:de", "test.aux", "--out=-", "--enc=UTF-8");
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> knows a
     * locale sorter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorterLocale03() throws Exception {
        File data =
            makeFile( "target/data.bib", "UTF-8",
                      "@Item{ a,  value = {a} }\n"
                          + "@Item{ ae,  value = {ae} }\n"
                          + "@Item{ ab,  value = {ab} }\n"
                          + "@Item{ af,  value = {af} }\n"
                          + "@Item{ a1,  value = {A} }\n"
                          + "@Item{ ae1, value = {\u00e4} }\n"
                          + "@Item{ ac,  value = {ac} }\n" );

        data.deleteOnExit();

        runTest( "test",
                 "\\citation{*}\n" + "\\bibdata{target/data.bib}\n"
                     + "\\bibstyle{src/test/resources/bibtex/sort/sort}\n"
                     + "\\biboption{sort=locale:de}\n",
                 CLI.EXIT_OK, Check.EQ, "a\nA\n\u00e4\nab\nac\nae\naf\n",
                 Check.EQ, BANNER,
                 "test.aux", "--out=-", "--enc=UTF-8" );
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> knows a
     * rbc sorter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testSorterRbc01() throws Exception {
        File data =
            makeFile( "target/data.bib", "UTF-8",
                      "@Item{ a,  value = {a} }\n"
                          + "@Item{ ae,  value = {ae} }\n"
                          + "@Item{ ab,  value = {ab} }\n"
                          + "@Item{ af,  value = {af} }\n"
                          + "@Item{ a1,  value = {A} }\n"
                          + "@Item{ ae1, value = {\u00e4} }\n"
                          + "@Item{ ac,  value = {ac} }\n" );

        data.deleteOnExit();

        runTest( "test", "\\citation{*}\n" + "\\bibdata{target/data.bib}\n"
                     + "\\bibstyle{src/test/resources/bibtex/sort/sort}\n",
                 CLI.EXIT_OK, Check.EQ, "A\na\nab\nac\nae\n\u00e4\naf\n",
                 Check.EQ, BANNER,
                 "--sorter", "de-ae", "test.aux", "--out=-", "--enc=UTF-8" );
    }

    /**
     * <testcase> Test that the command line option <tt>--sorter</tt> knows a
     * locale sorter. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testSorterUnicode01() throws Exception {
        File data =
            makeFile( "target/data.bib", "UTF-8",
                      "@Item{ a,  value = {a} }\n"
                          + "@Item{ ae,  value = {ae} }\n"
                          + "@Item{ ab,  value = {ab} }\n"
                          + "@Item{ af,  value = {af} }\n"
                          + "@Item{ a1,  value = {A} }\n"
                          + "@Item{ ae1, value = {\u00e4} }\n"
                          + "@Item{ ac,  value = {ac} }\n" );

        data.deleteOnExit();

        runTest( "test", "\\citation{*}\n" + "\\bibdata{target/data.bib}\n"
                     + "\\bibstyle{src/test/resources/bibtex/sort/sort}\n",
                 CLI.EXIT_OK, Check.EQ, "A\na\nab\nac\nae\naf\n\u00e4\n",
                 Check.EQ, BANNER,
                 "--sorter", "unicode", "test.aux", "--out=-", "--enc=UTF-8" );
    }

    /**
     * <testcase> Test that everything might go right in strict mode.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testStrict1() throws Exception {

        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK, Check.EQ, BANNER
                            + "Warning: empty author in whole-journal\n"
                            + "Warning: empty title in whole-journal\n",
                    "test.aux", "--strict");
        assertEquals("exbib", exbib.getProgramName());
        // assertEquals("test.bbl", exbib.getOutfile());
        // assertEquals("test.blg", exbib.getLogfile());
        assertFalse("trace", exbib.isTrace());
        // assertFalse("trace", exbib.isDebug());
        assertNull("logger", exbib.getLogger()); // since closed already
    }

    /**
     * <testcase> Test that the command line option <tt>--terse</tt> is honored.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testTerse1() throws Exception {

        runFailure("Missing aux file parameter.\n" + "(There was 1 error)\n",
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

        String aux = new File(".", "test.aux").toString();
        ExBibMain exbib =
                runTest(
                    "test",
                    "\\citation{*}\n"
                            + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                            + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
                    CLI.EXIT_OK,
                    Check.START,
                    BANNER
                            + "The top-level auxiliary file: "
                            + aux
                            + "\n"
                            + "The style file src/test/resources/bibtex/base/plain.bst\n"
                            + "The output file: test.bbl\n"
                            + "--- do READ\n"
                            + "Database file #1: src/test/resources/bibtex/base/xampl.bib\n"
                            + "--- do ITERATE { presort }\n"
                            + "--- step presort\n"
                            + "--- step type$\n"
                            + "--- push \"article\"\n"
                            + "--- step \"book\"\n"
                            + "--- push \"book\"\n"
                            + "--- step =\n"
                            + "--- push #0\n"
                            + "--- step type$\n"
                            + "--- push \"article\"\n"
                            + "--- step \"inbook\"\n"
                            + "--- push \"inbook\"\n",
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

        runFailure(BANNER + "Unknown option `--undefined' ignored.\n"
                + "Missing aux file parameter.\n" + "(There was 1 error)\n",
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

        runFailure(BANNER + "Unknown option `--undefined' ignored.\n"
                + "Missing aux file parameter.\n" + "(There was 1 error)\n",
            "--undefined=123");
    }

    /**
     * <testcase> Test that the command line option <tt>--verbose</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void testVerbose1() throws Exception {

        String aux =
                new File(".", "test.aux").toString().replaceAll("\\\\", ".");
        runTest(
            "test",
            "\\citation{*}\n"
                    + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                    + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_OK,
            Check.REGEX,
            BANNER
                    + "The top-level auxiliary file: "
                    + aux
                    + "\n"
                    + "The style file src/test/resources/bibtex/base/plain.bst\n"
                    + "The output file: test.bbl\n"
                    + "Database file #1: src/test/resources/bibtex/base/xampl.bib\n"
                    + "Warning: empty author in whole-journal\n"
                    + "Warning: empty title in whole-journal\n"
                    + "Runtime [0-9]* ms\n"
                    + "\\(There were 2 warnings\\)\n",
            "--verbose", "test.aux");
    }

    /**
     * <testcase> Test that the command line option <tt>--verbose</tt> works.
     * </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testVerbose2() throws Exception {

        runFailure( BANNER
                + "The option `--verbose' does not cope with arguments.\n",
                    "--verbose=123", "test.aux");
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
                    + "switch -copying.\n",
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
                    + "switch -copying.\n",
            "--vers");
    }

}
