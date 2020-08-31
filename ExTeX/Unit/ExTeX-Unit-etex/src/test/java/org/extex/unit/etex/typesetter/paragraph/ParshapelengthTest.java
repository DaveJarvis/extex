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

package org.extex.unit.etex.typesetter.paragraph;

import org.extex.test.NonExecuteTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \parshapelength}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ParshapelengthTest extends NonExecuteTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( ParshapelengthTest.class );
  }


  public ParshapelengthTest() {

    super( "parshapelength", "" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} is applicable to {@code \the}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapelength0 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} on a negative index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapelength-1 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} on a large index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapelength2 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that the length
   * of the first and only existing pair is read.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 12pt 24pt \\the\\parshapelength0 \\end",
                  // --- output channel ---
                  "24.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that the length
   * of the first existing pair of two is read.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapelength0 " +
                      "\\end",
                  // --- output channel ---
                  "24.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that length of
   * the second pair is read.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test6() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapelength1 " +
                      "\\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test7() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapelength2 " +
                      "\\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test8() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapelength222 " +
                      "\\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} is applicable to {@code \the}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test111() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapelength0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} on a negative index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test112() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapelength-1 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} is applicable to \the.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test113() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapelength2 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} can be used as a dimen value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test114() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 12pt 24pt "
                      + "\\dimen0=\\parshapelength0 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "24.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} can be used as a dimen value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test115() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapelength0 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "24.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} can be used as a dimen value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test116() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapelength1 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test117() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapelength2 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test118() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapelength222 \\the\\dimen0 \\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapelength"> Test case showing that
   * {@code \parshapelength} is count-convertible.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConvertible1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count1=\\parshapelength0 \\the\\count1\\end",
                  // --- output channel ---
                  "0" + TERM );
  }

  // TODO implement primitive specific test cases
}
