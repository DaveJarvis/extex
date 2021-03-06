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
 * This is a test suite for the primitive {@code \parshapedimen}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ParshapedimenTest extends NonExecuteTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( ParshapedimenTest.class );
  }


  public ParshapedimenTest() {

    super( "parshapedimen", "0" );
    setConfig( "etex-test" );
  }

  /**
   * Test case showing that the primitive can not be used in inner vertical
   * mode
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testInnerVerticalMode11() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\vbox{\\parshapedimen0}\\end",
                  // --- error channel ---
                  "You can't use `\\parshapedimen' in inner vertical mode" );
  }

  /**
   * Test case showing that the primitive can not be used in restricted 
   * horizontal mode
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRestrictedHorizontalMode11() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\hbox{\\parshapedimen0}\\end",
                  // --- error channel ---
                  "You can't use `\\parshapedimen"
                      + "' in restricted horizontal mode" );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * {@code \parshapedimen} is applicable to {@code \the}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapedimen0 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * {@code \parshapedimen} on a negative index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapedimen-1 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen outside the defined values returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\parshapedimen2 \\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen at index 0 returns the first value if just one is defined.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 12pt 24pt \\the\\parshapedimen0 \\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen at index 0 returns the first value if two are defined.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen0 \\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen at index 1 returns the second value if just pairs are
   * defined.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test6() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen1 \\end",
                  // --- output channel ---
                  "24.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test7() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen2 \\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test8() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen3 \\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test9() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen4 \\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test10() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen5 \\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test20() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\parshape2 12pt 24pt 36pt 48pt \\the\\parshapedimen222 \\end",
        // --- output channel ---
        "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * {@code \parshapedimen} is applicable to {@code \the}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapedimen0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * {@code \parshapedimen} on a negative index returns 0pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapedimen-1 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen can be assigned to a dimen register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen0=\\parshapedimen2 \\the\\dimen0\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen can be assigned to a dimen register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 12pt 24pt "
                      + "\\dimen0=\\parshapedimen0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen can be assigned to a dimen register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign5() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapedimen0 \\the\\dimen0\\end",
                  // --- output channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * \parshapedimen can be assigned to a dimen register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign6() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapedimen1 \\the\\dimen0\\end",
                  // --- output channel ---
                  "24.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign7() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapedimen2 \\the\\dimen0\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign8() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapedimen3 \\the\\dimen0\\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign9() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapedimen4 \\the\\dimen0\\end",
                  // --- output channel ---
                  "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign10() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape2 12pt 24pt 36pt 48pt "
                      + "\\dimen0=\\parshapedimen5 \\the\\dimen0\\end",
                  // --- output channel ---
                  "48.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that the last
   * value is repeated.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign20() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\parshape2 12pt 24pt 36pt 48pt "
            + "\\dimen0=\\parshapedimen222 \\the\\dimen0\\end",
        // --- output channel ---
        "36.0pt" + TERM );
  }

  /**
   * <testcase primitive="\parshapedimen"> Test case showing that
   * {@code \parshapedimen} is count-convertible.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConvertible1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count1=\\parshapedimen0 \\the\\count1\\end",
                  // --- output channel ---
                  "0" + TERM );
  }

  // TODO implement primitive specific test cases
}
