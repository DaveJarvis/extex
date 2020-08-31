/*
 * Copyright (C) 2008-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.util;

import org.extex.cli.CLI;
import org.extex.exbib.main.AbstractMain;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * This is a test suite for {@link ExBibUtilMain}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExBibUtilMainTest extends BibUtilTester {

  /**
   * The field {@code USAGE} contains the usage message (without the banner).
   */
  private static final String USAGE =
      "Usage: exbibutil <options> file\n"
          + "The following options are supported:\n"
          + "\t-[-] <file>\n"
          + "\t\tUse this argument as file name -- even when it looks like an" +
          " option.\n"
          + "\t--au[xfile] | --ex[tract] | -x <file>\n"
          + "\t\tUse this argument as file name of an aux file to get " +
          "databases and citations\n"
          + "\t\tfrom.\n"
          + "\t--a[vailableCharsets]\n"
          + "\t\tList the available encoding names and exit.\n"
          + "\t--b[ib-encoding] | --bib.[encoding] | -E <enc>\n"
          + "\t\tUse the given encoding for the bib files.\n"
          + "\t--con[figuration] | -c <configuration>\n"
          + "\t\tUse the configuration given. This is not a file!\n"
          + "\t--c[opying]\n"
          + "\t\tDisplay the copyright conditions.\n"
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
          + "\t\tSend the output to the log file named instead of the default" +
          " one.\n"
          + "\t--o[utfile] | --outp[ut] | -o <file>\n"
          + "\t\tRedirect the output to the file given.\n"
          + "\t\tThe file name - can be used to redirect to stdout\n"
          + "\t\tThe empty file name can be used to discard the output " +
          "completely\n"
          + "\t--p[rogname] | --progr[am-name] | --program.[name] | -p " +
          "<program>\n"
          + "\t\tSet the program name for messages.\n"
          + "\t--q[uiet] | --t[erse] | -q\n"
          + "\t\tAct quietly; some informative messages are suppressed.\n"
          + "\t--r[elease]\n"
          + "\t\tPrint the release number and exit.\n"
          + "\t--ty[pe] | -t <type>\n"
          + "\t\tUse the given type as output format (e.g. bib, xml).\n"
          + "\t--v[erbose] | -v\n"
          + "\t\tAct verbosely; some additional informational messages are " +
          "displayed.\n"
          + "\t--vers[ion]\n"
          + "\t\tPrint the version information and exit.\n";

  /**
   * Test that no command line option at all leads to no output.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test001() throws Exception {

    runTest( "test", null, CLI.EXIT_OK, Check.EQ, BANNER );
  }

  /**
   * Test that the command line option {@code --} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test002() throws Exception {

    runFailure( BANNER + "The option `--' needs a parameter.\n",
                "--" );
  }

  /**
   * Test that the command line option {@code -} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test003() throws Exception {

    runFailure( BANNER + "The option `-' needs a parameter.\n",
                "-" );
  }

  /**
   * Test that the input file needs to exist.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test004() throws Exception {

    runFailure( BANNER
                    + "I couldn't open file some/non/existent/file.bib\n",
                "-", "some/non/existent/file" );
  }

  /**
   * Test that the data file in an aux file needs to exist.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test010() throws Exception {

    String basename = "test";
    String auxContents = "\\relax\n"
        + "\\citation{abc}\n"
        + "\\bibdata{some/non/existent/file}\n"
        + "\\bibstyle{undef}\n";
    File aux = new File( basename + ".bib" );
    if( auxContents != null ) {
      Writer w = new FileWriter( aux );
      try {
        w.write( auxContents );
      } finally {
        w.close();
      }
    }

    try {
      runTest( basename, null, CLI.EXIT_FAIL, Check.EQ, BANNER
                   + "I couldn't open file some/non/existent/file.bib\n",
               "-x", aux.toString() );
    } finally {
      if( aux.exists() && !aux.delete() ) {
        assertTrue( aux.toString() + ": deletion failed", false );
      }
    }
  }

  /**
   * Test that the of the aux file selects only the needed entries.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test011() throws Exception {

    String basename = "test";
    String auxContents = "\\relax\n"
        + "\\citation{article-full}\n"
        + "\\bibdata{src/test/resources/bibtex/base/xampl}\n"
        + "\\bibstyle{undef}\n";
    File aux = new File( basename + ".aux" );
    if( auxContents != null ) {
      Writer w = new FileWriter( aux );
      try {
        w.write( auxContents );
      } finally {
        w.close();
      }
    }

    try {
      runTest( basename, null, CLI.EXIT_OK, Check.EQ, BANNER,
               "-x", aux.toString() );
    } finally {
      if( aux.exists() && !aux.delete() ) {
        assertTrue( aux.toString() + ": deletion failed", false );
      }
    }
  }

  /**
   * Test that a missing aux file is reported.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAux1() throws Exception {

    runFailure( BANNER
                    + "I couldn't open file file/which/does/not/exist.aux\n",
                "-x", "file/which/does/not/exist" );
  }

  /**
   * Test that an empty aux file is reported.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAux2() throws Exception {

    runFailure( BANNER + "The file argument can not be empty.\n",
                "-x", "" );
  }

  /**
   * Test that multiple aux files are reported.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAux3() throws Exception {

    runFailure( BANNER + "Only one aux file can be processed.\n",
                "-x", "a", "-x", "b" );
  }

  /**
   * Test that the command line option {@code --config} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConfig1() throws Exception {

    runFailure( BANNER + "The option `--config' needs a parameter.\n",
                "--config" );
  }

  /**
   * Test that the command line option {@code --config} needs an existing
   * configuration
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConfig2() throws Exception {

    runFailure( BANNER + "Configuration `exbib/undef' not found.\n",
                "--config", "undef" );
  }

  /**
   * Test that the command line option {@code --config} needs a valid
   * configuration
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
            + "Configuration syntax error Premature end of file. in " +
            "config/exbib/empty.xml\n",
        "--config", "empty" );
  }

  /**
   * Test that the command line option {@code --config} needs a valid
   * configuration
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConfig4() throws Exception {

    runFailure(
        BANNER
            + "[Fatal Error] :4:1: XML document structures must start and end" +
            " within the same entity.\n"
            +
            "Configuration syntax error XML document structures must start " +
            "and end within\n"
            + "the same entity. in config/exbib/incomplete.xml\n",
        "--config", "incomplete" );
  }

  /**
   * Test that a misconfiguration is reported.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConfig5() throws Exception {

    runFailure( BANNER
                    + "Configuration `exbib/processor/undefined.xml' not " +
                    "found.\n",
                "--config", "misconfigured" );
  }

  /**
   * Test that the command line option {@code --copying} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCopying1() throws Exception {

    runTest( "test", null, CLI.EXIT_FAIL, Check.START,
             "                 GNU LESSER GENERAL PUBLIC LICENSE\n",
             "--copying" );
  }

  /**
   * Test that an empty file name is silently consumed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty1() throws Exception {

    runSuccess( BANNER,
                "" );
  }

  /**
   * Test that an empty file name is silently consumed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty2() throws Exception {

    runSuccess( BANNER,
                "-", "" );
  }

  /**
   * Test that an empty file name is silently consumed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEmpty3() throws Exception {

    runSuccess( BANNER,
                "--", "" );
  }

  /**
   * Test that the command line option {@code --encoding} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEncoding1() throws Exception {

    runFailure( BANNER + "The option `--encoding' needs a parameter.\n",
                "--encoding" );
  }

  /**
   * Test that the command line option {@code --encoding} needs a known encoding
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEncoding2() throws Exception {

    runFailure( BANNER + "The encoding `xyzzy' is not known.\n",
                "--out", "test.out", "--encoding", "xyzzy" );
  }

  /**
   * Test that the command line option {@code --help} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHelp1() throws Exception {

    runFailure( BANNER + USAGE,
                "--help" );
  }

  /**
   * Test that the command line option {@code -h} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHelp2() throws Exception {

    runFailure( BANNER + USAGE,
                "-h" );
  }

  /**
   * Test that the command line option {@code -?} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHelp3() throws Exception {

    runFailure( BANNER + USAGE,
                "-?" );
  }

  /**
   * Test that the command line option {@code --language} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLanguage1() throws Exception {

    runFailure( BANNER + "The option `--language' needs a parameter.\n",
                "--language" );
  }

  /**
   * Test that the command line option {@code --language} falls back to en
   * for an unknown language
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLanguage2() throws Exception {

    runSuccess( BANNER, "--language", "xxx" );
  }

  /**
   * Test that the command line option {@code --language} can be used to
   * set the language
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLanguage3() throws Exception {

    runSuccess( BANNER_DE, "--language", "de" );
  }

  /**
   * Test that the command line option {@code --logfile} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLogfile01() throws Exception {

    runFailure( BANNER + "The option `--logfile' needs a parameter.\n",
                "--logfile" );
  }

  /**
   * Test that the command line option {@code --logfile} can be
   * used to redirect the log output. It is tested that the log file is
   * created and the {@link org.extex.exbib.core.ExBib} instance reports the
   * log file with getLogfile().
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testLogfile02() throws Exception {

    File log = new File( "test.lg" );
    if( log.exists() && !log.delete() ) {
      assertTrue( log.toString() + ": deletion failed", false );
    }
    assertFalse( log.exists() );

    try {
      ExBibUtilMain exbib =
          runTest( "test", "", CLI.EXIT_FAIL, Check.EQ, BANNER,
                   "test.bib", "-log", log.toString() );
      assertTrue( log.exists() );
      assertNotNull( exbib );
      // assertEquals("test.lg", exbib.getLogfile());

    } finally {
      if( log.exists() && !log.delete() ) {
        assertTrue( log.toString() + ": deletion failed", false );
      }
    }
  }

  /**
   * Test that the command line option {@code --logfile} can be used to
   * redirect the log output
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testLogfile03() throws Exception {

    runTest( "test", "", CLI.EXIT_FAIL, Check.EQ, BANNER,
             "test.bib", "-log", "" );
  }

  /**
   * Test that everything might go right.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOk1() throws Exception {

    ExBibUtilMain exbib =
        runTest(
            "test",
            "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_OK, Check.EQ, BANNER,
            "test.bib" );
    assertEquals( "exbibutil", exbib.getProgramName() );
    // assertEquals("test.bbl", exbib.getOutfile());
    // assertEquals("test.blg", exbib.getLogfile());
    // assertFalse("trace", exbib.isTrace());
    // assertFalse("trace", exbib.isDebug());
    assertNull( "logger", exbib.getLogger() ); // since closed already
  }

  /**
   * Test that everything might go right using the static method.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOk2() throws Exception {

    String[] args = {"test.bib"};
    File aux = new File( "test.bib" );
    Writer w = new FileWriter( aux );
    try {
      w.write( "\\citation{*}\n"
                   + "\\bibdata{src/test/resources/bibtex/base/xampl.bib}\n"
                   + "\\bibstyle{src/test/resources/bibtex/base/plain}\n" );
    } finally {
      w.close();
    }

    Locale.setDefault( Locale.ENGLISH );
    PrintStream err = System.err;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      System.setErr( new PrintStream( baos ) );
      assertEquals( 0, ExBibUtilMain.commandLine( args ) );
      assertEquals( BANNER, baos.toString().replaceAll( "\r", "" ) );
    } finally {
      System.setErr( err );
      if( !aux.delete() ) {
        assertTrue( aux.toString() + ": deletion failed", false );
      }
    }
  }

  /**
   * Test that the command line option {@code --outfile} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOutfile1() throws Exception {

    runFailure( BANNER + "The option `--outfile' needs a parameter.\n",
                "--outfile" );
  }

  /**
   * Test that the command line option {@code --outfile} discards the
   * output if the file name is empty
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOutfile2() throws Exception {

    runTest( "test", null, CLI.EXIT_OK, Check.START, BANNER
                 + "The output is discarded.\n" + "Runtime ",
             "-v", "--outfile", "" );
  }

  /**
   * Test that the command line option {@code --outfile} sends the output
   * to stdout if the file name is "-"
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOutfile3() throws Exception {

    runTest( "test", null, CLI.EXIT_OK, Check.START, BANNER
                 + "The output is sent to stdout.\n" + "Runtime ",
             "-v", "--outfile", "-" );
  }

  /**
   * Test that the command line option {@code --outfile} sends the output to
   * the file
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOutfile4() throws Exception {

    File file = new File( "test.output" );
    if( file.exists() && !file.delete() ) {
      assertTrue( file.toString() + ": deletion failed", false );
    }

    try {
      runTest( "test", null, CLI.EXIT_OK, Check.START, BANNER
                   + "The output file: test.output\n" + "Runtime ",
               "-v", "--outfile", file.toString() );
      assertTrue( file.exists() );
    } finally {
      if( file.exists() && !file.delete() ) {
        assertTrue( file.toString() + ": deletion failed", false );
      }

    }
  }

  /**
   * Test that the command line option {@code --outfile} complains if the
   * output file can not be opened
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOutfile5() throws Exception {

    runFailure(
        BANNER
            + "The output file: some/file/for/writing\n"
            + "The output file `some/file/for/writing' could not be " +
            "opened.\n",
        "-v", "--outfile", "some/file/for/writing", "test.bib" );
  }

  /**
   * Test that the command line option {@code --progname} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testProgName1() throws Exception {

    runFailure( BANNER + "The option `--progname' needs a parameter.\n",
                "--progname" );
  }

  /**
   * Test that the command line option {@code --progname} can be queried with
   * getProgramName()
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testProgName11() throws Exception {

    AbstractMain exbib =
        runTest(
            "test",
            "\\citation{*}\n"
                + "\\bibdata{src/test/resources/bibtex/base/xampl" +
                ".bib}\n"
                + "\\bibstyle{src/test/resources/bibtex/base/plain}\n",
            CLI.EXIT_OK, Check.EQ, null,
            "--progname", "xxx", "test.bib" );
    assertEquals( "xxx", exbib.getProgramName() );
  }

  /**
   * Test that the command line option {@code --progname} can be used to set
   * the program name
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testProgName2() throws Exception {

    runSuccess( "This is abc, Version " + ExBibUtilMain.VERSION + "\n",
                "--progname", "abc" );
  }

  /**
   * Test that the command line option {@code --quiet} is honored.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testQuiet1() throws Exception {

    runSuccess( "",
                "--quiet" );
  }

  /**
   * Test that the command line option {@code --release} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRelease1() throws Exception {

    runFailure( ExBibUtilMain.VERSION + "\n",
                "--release" );
  }

  /**
   * Test that the command line option {@code --terse} is honored.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTerse1() throws Exception {

    runSuccess( "",
                "--terse" );
  }

  /**
   * Test that the command line option {@code -type} needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testType1() throws Exception {

    runFailure( BANNER + "The option `--type' needs a parameter.\n",
                "--type" );
  }

  /**
   * Test that the command line option {@code -type} needs a known argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testType2() throws Exception {

    runFailure( BANNER + "The output type `xyzzy' is not known.\n",
                "--type", "xyzzy" );
  }

  /**
   * Test bib is a known output type.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testType3() throws Exception {

    runSuccess( BANNER,
                "--type", "bib" );
  }

  /**
   * Test xml is a known output type.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testType4() throws Exception {

    runSuccess( BANNER,
                "--type", "xml" );
  }

  /**
   * Test that an undefined command line option is reported.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUndefined1() throws Exception {

    runSuccess( BANNER + "Unknown option `--undefined' ignored.\n",
                "--undefined" );
  }

  /**
   * Test that an undefined command line option is reported.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUndefined2() throws Exception {

    runSuccess( BANNER + "Unknown option `--undefined' ignored.\n",
                "--undefined=123" );
  }

  /**
   * Test that the command line option {@code --verbose} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVerbose1() throws Exception {

    runTest( "test", "only comment\n", CLI.EXIT_OK, Check.REGEX, BANNER
                 + "The output is sent to stdout.\n" + "Runtime [0-9]* ms\n",
             "--verbose" );
  }

  /**
   * Test that the command line option {@code --verbose} works.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testVerbose2() throws Exception {

    String bib = new File( "test.bib" ).toString().replaceAll( "\\\\", "." );
    runTest( "test", "only comment\n", CLI.EXIT_OK, Check.REGEX, BANNER
                 + "The output is sent to stdout.\n" + "Database file #1: \n"
                 + bib + ".bib\n" + "Runtime [0-9]* ms\n",
             "--verbose", "test.bib" );
  }

  /**
   * Test that the command line option {@code --version} works.
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
            + "covered by the terms of the GNU Library General Public License" +
            ".\n"
            + "For more information about these matters, use the command line\n"
            + "switch -copying.\n",
        "--version" );
  }

  /**
   * Test that the command line option {@code --version} works when
   * abbreviated as {@code --vers}
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
            + "covered by the terms of the GNU Library General Public License" +
            ".\n"
            + "For more information about these matters, use the command line\n"
            + "switch -copying.\n",
        "--vers" );
  }

}
