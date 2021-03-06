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
 * This is a test suite for the primitive {@code \ifnum}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IfnumTest extends ConditionalTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfnumTest.class );
  }


  public IfnumTest() {

    super( "ifnum", " 1=1" );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "x\\ifnum 0",
                  // --- output channel ---
                  "Unexpected end of file while processing \\ifnum" );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * needs an operator after the first argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "x\\ifnum 0",
                  // --- output channel ---
                  "Unexpected end of file while processing \\ifnum" );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * complains if no valid operator is given.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErr1() throws Exception {

    assertFailure(// --- input code ---
                  "x\\ifnum 0!1",
                  // --- output channel ---
                  "Missing = inserted for \\ifnum" );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|0<1| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess1() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 0<1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|1<1| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 1<1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|2<1| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLess3() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 2<1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|0=1| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals1() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 0=1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|1=1| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 1=1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|2=1| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEquals3() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 2=1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|0>1| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater1() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 0>1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|1>1| selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 1>1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xbx" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * on \verb|2>1| selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGreater3() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifnum 2>1 a\\else b\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * works in an expand context
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count0=\\ifnum 2>1 123 \\else 456 \\fi \\the\\count0\\end",
                  // --- output channel ---
                  "123" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * works in an expand context
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count0=\\ifnum 2<1 123 \\else 456 \\fi \\the\\count0\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * works in an expand context
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand3() throws Exception {

    assertFailure(// --- input code ---
                  "\\count0=\\fi \\the\\count0\\end",
                  // --- output channel ---
                  "Extra \\fi" );
  }

  /**
   * <testcase primitive="\ifnum"> Test case checking that {@code \ifnum}
   * complains about an outer macro when skipping tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOuter1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\outer\\def\\x{}\\ifnum1=2 \\x\\fi\\end",
                  // --- output channel ---
                  "Incomplete \\ifnum; all text was ignored after line 1" );
  }

}
