/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.makeindex.main;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

/**
 * This is a test suite for {@link Makeindex}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
@Ignore
public class MakeindexTest extends AbstractTester {

  private final static String DIR_TARGET = "build";

  @Override
  protected Makeindex makeInstance() {

    return new Makeindex();
  }

  /**
   * The argument to run can not be {@code null}. In this case an
   * {@link IllegalArgumentException} is thrown
   */
  @Test(expected = IllegalArgumentException.class)
  public void test00() {

    PrintStream out = System.out;
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    try {
      System.setErr( new PrintStream( outStream ) );
      makeInstance().run( null );
    } finally {
      System.setErr( out );
    }
  }

  /**
   * An index can be sorted.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile(
        new String[]{f},
        f,
        "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n",
        BANNER
            + "Scanning input file ./target/t1.idx...done (2 entries " +
            "accepted, 0 rejected)\n"
            + "Sorting...done (1 comparisons).\n"
            + "Generating output file ./target/t1.ind...done (0 entries " +
            "written, 0 warnings).\n"
            + "Output written in ./target/t1.ind.\n"
            + "Transcript written in target/t1.ilg.\n",
        "\\begin{theindex}\n"
            + "\n"
            + "  \\item aaa, 3\n"
            + "  \\item bbb, 2\n"
            + "\n"
            + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted with option -g.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1g() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile(
        new String[]{"-g", f},
        f,
        "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n",
        BANNER
            + "Scanning input file ./target/t1.idx...done (2 entries " +
            "accepted, 0 rejected)\n"
            + "Sorting...done (1 comparisons).\n"
            + "Generating output file ./target/t1.ind...done (0 entries " +
            "written, 0 warnings).\n"
            + "Output written in ./target/t1.ind.\n"
            + "Transcript written in target/t1.ilg.\n",
        "\\begin{theindex}\n"
            + "\n"
            + "  \\item aaa, 3\n"
            + "  \\item bbb, 2\n"
            + "\n"
            + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted with option -gc.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1gc() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile(
        new String[]{"-gc", f},
        f,
        "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n",
        BANNER
            + "Scanning input file ./target/t1.idx...done (2 entries " +
            "accepted, 0 rejected)\n"
            + "Sorting...done (1 comparisons).\n"
            + "Generating output file ./target/t1.ind...done (0 entries " +
            "written, 0 warnings).\n"
            + "Output written in ./target/t1.ind.\n"
            + "Transcript written in target/t1.ilg.\n",
        "\\begin{theindex}\n"
            + "\n"
            + "  \\item aaa, 3\n"
            + "  \\item bbb, 2\n"
            + "\n"
            + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted with option -l.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1l() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile(
        new String[]{"-l", f},
        f,
        "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n",
        BANNER
            + "Scanning input file ./target/t1.idx...done (2 entries " +
            "accepted, 0 rejected)\n"
            + "Sorting...done (1 comparisons).\n"
            + "Generating output file ./target/t1.ind...done (0 entries " +
            "written, 0 warnings).\n"
            + "Output written in ./target/t1.ind.\n"
            + "Transcript written in target/t1.ilg.\n",
        "\\begin{theindex}\n"
            + "\n"
            + "  \\item aaa, 3\n"
            + "  \\item bbb, 2\n"
            + "\n"
            + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1m() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile(
        new String[]{"-", f},
        f,
        "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n",
        BANNER
            + "Scanning input file ./target/t1.idx...done (2 entries " +
            "accepted, 0 rejected)\n"
            + "Sorting...done (1 comparisons).\n"
            + "Generating output file ./target/t1.ind...done (0 entries " +
            "written, 0 warnings).\n"
            + "Output written in ./target/t1.ind.\n"
            + "Transcript written in target/t1.ilg.\n",
        "\\begin{theindex}\n"
            + "\n"
            + "  \\item aaa, 3\n"
            + "  \\item bbb, 2\n"
            + "\n"
            + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1q() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile( new String[]{"-q", f}, f, "\\indexentry{bbb}{2}\n"
                   + "\\indexentry{aaa}{3}\n",
               "",
               "\\begin{theindex}\n"
                   + "\n"
                   + "  \\item aaa, 3\n"
                   + "  \\item bbb, 2\n"
                   + "\n"
                   + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test1quiet() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile( new String[]{"-quiet", f}, f, "\\indexentry{bbb}{2}\n"
                   + "\\indexentry{aaa}{3}\n",
               "",
               "\\begin{theindex}\n"
                   + "\n"
                   + "  \\item aaa, 3\n"
                   + "  \\item bbb, 2\n"
                   + "\n"
                   + "\\end{theindex}\n" );
  }

  /**
   * An index (omitting the suffix) can be sorted.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test2() throws IOException {

    Locale.setDefault( Locale.ENGLISH );
    runOnFile( new String[]{DIR_TARGET + "/t1"},
               DIR_TARGET + "/t1.idx",
               "\\indexentry{bbb}{2}\n" + "\\indexentry{aaa}{3}\n",
               BANNER
                   + "Scanning input file ./target/t1.idx...done (2 entries " +
                   "accepted, 0 rejected)\n"
                   + "Sorting...done (1 comparisons).\n"
                   + "Generating output file ./target/t1.ind...done (0 " +
                   "entries written, 0 warnings).\n"
                   + "Output written in ./target/t1.ind.\n"
                   + "Transcript written in target/t1.ilg.\n",
               "\\begin{theindex}\n"
                   + "\n"
                   + "  \\item aaa, 3\n"
                   + "  \\item bbb, 2\n"
                   + "\n"
                   + "\\end{theindex}\n" );
  }

  /**
   * An index with colliding keys can be sorted.
   *
   * @throws IOException in case of an error
   */
  @Test
  public void test3() throws IOException {

    Locale.setDefault( Locale.ENGLISH );
    runOnFile( new String[]{DIR_TARGET + "/t1.idx"},
               DIR_TARGET + "/t1.idx",
               "\\indexentry{bbb}{2}\n"
                   + "\\indexentry{bbb}{4}\n"
                   + "\\indexentry{aaa}{3}\n",
               BANNER
                   + "Scanning input file ./target/t1.idx...done (3 entries " +
                   "accepted, 0 rejected)\n"
                   + "Sorting...done (3 comparisons).\n"
                   + "Generating output file ./target/t1.ind...done (0 " +
                   "entries written, 0 warnings).\n"
                   + "Output written in ./target/t1.ind.\n"
                   + "Transcript written in target/t1.ilg.\n",
               "\\begin{theindex}\n"
                   + "\n"
                   + "  \\item aaa, 3\n"
                   + "  \\item bbb, 2, 4\n"
                   + "\n"
                   + "\\end{theindex}\n" );
  }

  /**
   * An index can be sorted with option -c.
   *
   * @throws IOException in case of an error
   */
  @Test
  @Ignore
  public void testCollateSpaces1() throws IOException {

    String f = DIR_TARGET + "/t1.idx";
    Locale.setDefault( Locale.ENGLISH );
    runOnFile(
        new String[]{"-c", f},
        f,
        "\\indexentry{bb  b}{2}\n" + "\\indexentry{bb b}{3}\n",
        BANNER
            + "Scanning input file ./target/t1.idx...done (2 entries " +
            "accepted, 0 rejected)\n"
            + "Sorting...done (1 comparisons).\n"
            + "Generating output file ./target/t1.ind...done (0 entries " +
            "written, 0 warnings).\n"
            + "Output written in ./target/t1.ind.\n"
            + "Transcript written in target/t1.ilg.\n",
        "\\begin{theindex}\n"
            + "\n"
            + "  \\item bb b, 2, 3\n"
            + "\n"
            + "\\end{theindex}\n" );
  }

  /**
   * An illegal argument for -D is reported.
   */
  @Test
  public void testD01() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-D"},
         BANNER + "Unknown option -D.\n",
         "", -1 );
  }

  /**
   * An illegal argument for -D is reported.
   */
  @Test
  public void testD02() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-Dabc"},
         BANNER + "Unknown option -Dabc.\n",
         "", -1 );
  }

  /**
   * The argument to run can be the empty string array.
   */
  @Test
  public void testEmptyArguments1() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{},
        BANNER
            + "Scanning input file stdin...done (0 entries accepted, 0 " +
            "rejected)\n"
            + "Sorting...done (0 comparisons).\n"
            + "Generating output...done (0 entries written, 0 warnings).\n"
            + "Output written in stdout.\n",
        "\\begin{theindex}\n\n\n\\end{theindex}\n", 0 );
  }

  /**
   * The empty string argument is ignored.
   */
  @Test
  public void testEmptyArguments2() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{""},
        BANNER
            + "Scanning input file stdin...done (0 entries accepted, 0 " +
            "rejected)\n"
            + "Sorting...done (0 comparisons).\n"
            + "Generating output...done (0 entries written, 0 warnings).\n"
            + "Output written in stdout.\n",
        "\\begin{theindex}\n\n\n\\end{theindex}\n", 0 );
  }

  /**
   * An illegal argument for -encoding is reported.
   */
  @Test
  public void testEnc00() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-enc", "xxx"},
         BANNER + "Encoding xxx is not supported.\n",
         "", -1 );
  }

  /**
   * An illegal argument for -encoding is reported.
   */
  @Test
  public void testEnc01() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-enc"},
         BANNER + "Missing argument for option -enc\n",
         "", -1 );
  }

  /**
   * An illegal argument for -encoding is reported.
   */
  @Test
  public void testEnc10() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-encoding="},
         BANNER + "Missing argument for option -encoding=\n",
         "", -1 );
  }

  /**
   * An illegal argument for -encoding is reported.
   */
  @Test
  public void testEncoding00() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-encoding=xxx"},
         BANNER + "Encoding xxx is not supported.\n",
         "", -1 );
  }

  /**
   * Help is provided upon request.
   */
  @Test
  public void testHelp1() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{"--help"},
        BANNER
            + "Usage: makeindex [-ilqrcg] [-s sty] [-o ind] [-t log] [-p num]" +
            " [idx0 idx1 ...]\n",
        "", 1 );
  }

  /**
   * A missing argument for -style is reported.
   */
  @Test
  public void testStyle00() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-s"},
         BANNER + "Missing argument for option -s\n",
         "", -1 );
  }

  /**
   * An empty argument for -style is reported.
   */
  @Test
  public void testStyle01() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-s", ""},
         BANNER + "Empty style is not permitted.\n",
         "", -1 );
  }

  /**
   * A null argument for -style is reported.
   */
  @Test
  public void testStyle02() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-s", null},
         BANNER + "Empty style is not permitted.\n",
         "", -1 );
  }

  /**
   * A null argument for -style is reported.
   */
  @Test
  public void testStyle03() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-style="},
         BANNER + "Empty style is not permitted.\n",
         "", -1 );
  }

  /**
   * A missing argument for -style is reported.
   */
  @Test
  public void testStyle1() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-s", "xyzzy"},
         BANNER + "Input style file xyzzy not found.\n",
         "", -1 );
  }

  /**
   * A empty style is consumed.
   */
  @Test
  public void testStyle2() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{"-s", "src/test/resources/makeindex/empty.ist"},
        BANNER
            + "Scanning style file src/test/resources/makeindex/empty.ist.." +
            ".done (0 attributes\n"
            + "redefined, 0 ignored).\n"
            + "Scanning input file stdin...done (0 entries accepted,\n0 " +
            "rejected)\n"
            + "Sorting...done (0 comparisons).\n"
            + "Generating output...done (0 entries written, 0 " +
            "warnings).\n"
            + "Output written in stdout.\n",
        "\\begin{theindex}\n\n\n\\end{theindex}\n", 0 );
  }

  /**
   * A empty style is consumed.
   */
  @Test
  public void testStyle3() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{"-style=src/test/resources/makeindex/empty.ist"},
        BANNER
            + "Scanning style file src/test/resources/makeindex/empty.ist.." +
            ".done (0 attributes\n"
            + "redefined, 0 ignored).\n"
            + "Scanning input file stdin...done (0 entries accepted,\n0 " +
            "rejected)\n"
            + "Sorting...done (0 comparisons).\n"
            + "Generating output...done (0 entries written, 0 " +
            "warnings).\n"
            + "Output written in stdout.\n",
        "\\begin{theindex}\n\n\n\\end{theindex}\n", 0 );
  }

  /**
   * An unknown file argument is reported.
   */
  @Test
  public void testTranscript1() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{"-t", DIR_TARGET + "/xyz.ilog"},
        BANNER
            + "Scanning input file stdin...done (0 entries accepted, 0 " +
            "rejected)\n"
            + "Sorting...done (0 comparisons).\n"
            + "Generating output...done (0 entries written, 0 warnings).\n"
            + "Output written in stdout.\n"
            + "Transcript written in target/xyz.ilog.\n",
        "\\begin{theindex}\n\n\n\\end{theindex}\n", 0 );
    File t = new File( DIR_TARGET + "/xyz.ilog" );
    assertTrue( t.exists() );
    t.deleteOnExit();
  }

  /**
   * An unknown file argument is reported.
   */
  @Test
  public void testTranscript2() {

    Locale.setDefault( Locale.ENGLISH );
    run(
        new String[]{"-transcript=target/xyz.ilog"},
        BANNER
            + "Scanning input file stdin...done (0 entries accepted, 0 " +
            "rejected)\n"
            + "Sorting...done (0 comparisons).\n"
            + "Generating output...done (0 entries written, 0 warnings).\n"
            + "Output written in stdout.\n"
            + "Transcript written in target/xyz.ilog.\n",
        "\\begin{theindex}\n\n\n\\end{theindex}\n", 0 );
    File t = new File( DIR_TARGET + "/xyz.ilog" );
    assertTrue( t.exists() );
    t.deleteOnExit();
  }

  /**
   * An unknown argument is reported.
   */
  @Test
  public void testUnknownArgument1() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-xyzzy"},
         BANNER + "Unknown option -xyzzy.\n",
         "", -1 );
  }

  /**
   * An unknown file argument is reported.
   */
  @Test
  public void testUnknownFile1() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{DIR_TARGET + "/xyzzy"},
         BANNER + "Input index file target/xyzzy not found.\n",
         "", -1 );
  }

  /**
   * An unknown file argument is reported.
   */
  @Test
  public void testUnknownFile2() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-", DIR_TARGET + "/xyzzy"},
         BANNER + "Input index file target/xyzzy not found.\n",
         "", -1 );
  }

  /**
   * An unknown file argument is reported.
   */
  @Test
  public void testUnknownFile3() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"--", DIR_TARGET + "/xyzzy"},
         BANNER + "Input index file target/xyzzy not found.\n",
         "", -1 );
  }

  /**
   * The version is reported upon request.
   */
  @Test
  public void testVersion1() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-V"}, BANNER, "", 1 );
  }

  /**
   * The version is reported upon request.
   */
  @Test
  public void testVersion2() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"--V"}, BANNER, "", 1 );
  }

  /**
   * The version is reported upon request.
   */
  @Test
  public void testVersion3() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"-Version"}, BANNER, "", 1 );
  }

  /**
   * The version is reported upon request.
   */
  @Test
  public void testVersion4() {

    Locale.setDefault( Locale.ENGLISH );
    run( new String[]{"--Version"}, BANNER, "", 1 );
  }

}
