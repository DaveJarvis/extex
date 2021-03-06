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
 * This is a test suite for the primitive {@code \ifcat}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IfcatTest extends ConditionalTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfcatTest.class );
  }


  public IfcatTest() {

    super( "ifcat", " xx" );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * needs an argument
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcat",
                  // --- output channel ---
                  "Unexpected end of file while processing \\ifcat" );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * needs two arguments
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcat \\relax",
                  // --- output channel ---
                  "Unexpected end of file while processing \\ifcat" );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * detects two identical letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcat AA a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * detects two different letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcat AB a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies a letter and an other character as different.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcat A1 a\\else b\\fi \\end",
                  // --- output channel ---
                  "b" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies a letter and a control sequence as different.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcat A\\relax a\\else b\\fi \\end",
                  // --- output channel ---
                  "b" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two identical control sequences as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCs1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcat \\relax\\relax a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two different control sequences as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCs2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcat \\abc\\relax a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two identical other characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOther1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat 11 a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two different other characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOther2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat 12 a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two math shift characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMath1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat $$ a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two subscript characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSub1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat __ a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two tab mark characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSuper1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat && a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two open group characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOpen1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat {{ a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two close group characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testClose1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat }} a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcat"> Test case checking that {@code \ifcat}
   * classifies two macro parameter characters as equal.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\ifcat ## a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  // TODO implement more primitive specific test cases
}
