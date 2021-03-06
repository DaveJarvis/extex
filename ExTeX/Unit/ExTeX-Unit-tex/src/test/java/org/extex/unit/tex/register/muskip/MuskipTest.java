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

package org.extex.unit.tex.register.muskip;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \muskip}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MuskipTest extends AbstractMuskipRegisterTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( MuskipTest.class );
  }


  public MuskipTest() {

    super( "muskip", "42", "0.0mu" );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * needs mu as unit.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\muskip0=1pt",
                  // --- log message ---
                  "Illegal unit of measure (mu inserted)" );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can parse a constant with integer number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip0=1mu" + "\\the\\muskip0\\end",
                  // --- log message ---
                  "1.0mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can parse a constant with fraction number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip0=1.23mu" + "\\the\\muskip0\\end",
                  // --- log message ---
                  "1.23mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can parse a constant with negative fraction number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip0=-1.23mu" + "\\the\\muskip0\\end",
                  // --- log message ---
                  "-1.23mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can parse a constant with negative fraction number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertOutput(// --- input code ---
                 "\\muskip0=1mu plus 1fil" + "\\showthe\\muskip0\\end",
                 // --- log message ---
                 "> 1.0mu plus 1.0fil.\n", "" );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can parse a variable.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test10() throws Exception {

    assertOutput(// --- input code ---
                 "\\muskip0=1.23mu" + "\\muskip1=\\muskip0"
                     + "\\showthe\\muskip1\\end",
                 // --- log message ---
                 "> 1.23mu.\n", "" );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can parse a ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test11() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count0=2 " + "\\muskip0=\\count0mu" + "\\the\\muskip0" +
                      "\\end",
                  // --- log message ---
                  "2.0mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can advance its value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAdvance1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip1=2mu" + "\\advance\\muskip1 1mu" + "\\the" +
                      "\\muskip1\\end",
                  // --- log message ---
                  "3.0mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can advance its value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAdvance2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip0=1mu" + "\\muskip1=2mu" + "\\advance\\muskip1" +
                      "\\muskip0 "
                      + "\\the\\muskip1\\end",
                  // --- log message ---
                  "3.0mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can divide its value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDivide1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip1=2mu" + "\\divide\\muskip1 2 " + "\\the\\muskip1" +
                      "\\end",
                  // --- log message ---
                  "1.0mu" + TERM );
  }

  /**
   * <testcase primitive="\muskip"> Test case checking that {@code \muskip}
   * can multiply its value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMultiply1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\muskip1=2mu" + "\\multiply\\muskip1 2 " + "\\the" +
                      "\\muskip1\\end",
                  // --- log message ---
                  "4.0mu" + TERM );
  }

  // TODO implement more primitive specific test cases
}
