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
 * This is a test suite for the primitive {@code \ifcase}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IfcaseTest extends ConditionalTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfcaseTest.class );
  }


  public IfcaseTest() {

    super( "ifcase", "0 " );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcase 0 ",
                  // --- output channel ---
                  "(\\end occurred when \\ifcase was incomplete)\n" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcase 0 a\\or ",
                  // --- output channel ---
                  "Incomplete \\or; all text was ignored after line 1" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError3() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcase 1 a\\or ",
                  // --- output channel ---
                  "(\\end occurred when \\ifcase was incomplete)\n" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError4() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcase 2 a\\or ",
                  // --- output channel ---
                  "Incomplete \\ifcase; all text was ignored after line 1" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError10() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcase 0 a\\or \\else e",
                  // --- output channel ---
                  "Incomplete \\or; all text was ignored after line 1" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError11() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcase 0 a\\else \\else e",
                  // --- output channel ---
                  "Extra \\else" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 0 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConst0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase 0 a\\or b\\or c\\or d\\else e\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 2 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConst1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase 1 a\\or b\\or c\\or d\\else e\\fi \\end",
                  // --- output channel ---
                  "b" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 2 is hit
   * if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConst2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase 2 a\\or b\\or c\\or d\\else e\\fi \\end",
                  // --- output channel ---
                  "c" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 12 hits
   * the else case if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConst12() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase -1 a\\or b\\or c\\or d\\else e\\fi \\end",
                  // --- output channel ---
                  "e" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case 12 hits
   * nothing.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConst12b() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase -1 a\\or b\\or c\\or d\\fi \\end",
                  // --- output channel ---
                  "" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case -1 hits
   * the else case if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConstMinus1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase -1 a\\or b\\or c\\or d\\else e\\fi \\end",
                  // --- output channel ---
                  "e" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that the case -1 hits
   * the else case if present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConstMinus1b() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase -1 a\\or b\\or c\\or d\\fi \\end",
                  // --- output channel ---
                  "" );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that a negative value
   * falls through to the default case.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test11() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase -1 a\\else e\\fi \\end",
                  // --- output channel ---
                  "e" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that a large value
   * falls through to the default case.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test12() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcase 2 a\\else e\\fi \\end",
                  // --- output channel ---
                  "e" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that a large value
   * reduces to nothing if no default case is present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test13() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifcase 2 a\\fi x\\end",
                  // --- output channel ---
                  "xx" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that a large value
   * reduces to nothing if no default case is present.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test20() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifcase 2 \\ifcase 2 a\\fi\\fi x\\end",
                  // --- output channel ---
                  "xx" + TERM );
  }

  /**
   * <testcase primitive="\ifcase"> Test case checking that {@code \ifcase}
   * is expandable.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count0=\\ifcase 2 123 \\else 456 \\fi \\the\\count0\\end",
                  // --- output channel ---
                  "456" + TERM );
  }

}
