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
 * This is a test suite for the primitive {@code \parshapeindent}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ParshapeindentTest extends NonExecuteTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( ParshapeindentTest.class );
  }


  public ParshapeindentTest() {

    super( "parshapeindent", "" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * {@code \parshapeindent} is applicable to {@code \the}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapeindent0 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * {@code \parshapeindent} on a negative index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapeindent-1 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent outside any parshape returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapeindent2 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 0 on one pair returns the first value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 12pt 24pt \\the\\parshapeindent0 \\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 0 on two pairs returns the first value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapeindent0 " +
                      "\\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 1 on two pairs returns the third value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test6() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapeindent1 " +
                      "\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test7() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapeindent2 " +
                      "\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test8() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapeindent222 " +
                      "\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * {@code \parshapeindent} is applicable to {@code \the}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test111() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapeindent0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * {@code \parshapeindent} on a negative index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test112() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapeindent-1 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 0 on no pair is assignable to a dimen register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test113() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapeindent2 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 0 on one pair is assignable to a dimen register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test114() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 12pt 24pt "
                      + "\\dimen0=\\parshapeindent0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 0 on two pairs is assignable to a dimen
   * register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test115() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapeindent0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * \parshapeindent at index 1 on two pairs is assignable to a dimen
   * register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test116() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapeindent1 \\the\\dimen0\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test117() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapeindent2 \\the\\dimen0\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test118() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapeindent222 \\the\\dimen0\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapeindent"> Test case showing that
   * {@code \parshapeindent} is count-convertible.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConvertible1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count1=\\parshapeindent0 \\the\\count1\\end",
                  // --- output channel ---
                  "0" + TERM );
  }

  // TODO implement more primitive specific test cases
}
