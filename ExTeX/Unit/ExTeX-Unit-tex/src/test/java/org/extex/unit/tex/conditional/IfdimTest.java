/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.conditional;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \ifdim}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IfdimTest extends ConditionalTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfdimTest.class );
  }


  public IfdimTest() {

    super( "ifdim", " 0pt=0in " );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "x\\ifdim 0pt",
                  // --- output channel ---
                  "Unexpected end of file while processing \\ifdim" );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * needs an operator after the first argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "x\\ifdim 0pt",
                  // --- output channel ---
                  "Unexpected end of file while processing \\ifdim" );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * does not accept ! as operator.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "x\\ifdim 0pt!",
                  // --- output channel ---
                  "Missing = inserted for \\ifdim" );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|0pt<1pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess1() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 0pt<1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|1pt<1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 1pt<1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|2pt<1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess3() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 2pt<1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|0pt=1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals1() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 0pt=1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|1pt=1pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 1pt=1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|2pt=1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals3() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 2pt=1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|0pt>1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater1() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 0pt>1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|1pt>1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 1pt>1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|2pt>1pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater3() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 2pt>1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|0.pt<1.pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess11() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 0.pt<1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|1.pt<1.pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess12() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 1.pt<1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|2.pt<1.pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess13() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 2.pt<1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|0.pt=1.pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals11() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 0.pt=1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|1.pt=1.pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals12() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 1.pt=1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|2.pt=1.pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals13() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 2.pt=1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|0.pt>1.pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater11() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 0.pt>1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|1.pt>1.pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater12() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 1.pt>1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|2.pt>1.pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater13() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim 2.pt>1.pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.0pt<.1pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess21() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .0pt<.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.1pt<.1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess22() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .1pt<.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.2pt<.1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess23() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .2pt<.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.0pt=.1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals21() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .0pt=.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.1pt=.1pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals22() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .1pt=.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.2pt=.1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals23() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .2pt=.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.0pt>.1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater21() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .0pt>.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.1pt>.1pt| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater22() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .1pt>.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifdim"> Test case checking that {@code \ifdim}
   * on \verb|.2pt>.1pt| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater23() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifdim .2pt>.1pt a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

}
