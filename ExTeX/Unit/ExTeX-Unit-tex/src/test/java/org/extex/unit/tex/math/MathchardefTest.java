/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.math;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \mathchardef}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MathchardefTest extends NoFlagsButGlobalPrimitiveTester {


  public MathchardefTest() {

    setPrimitive( "mathchardef" );
    setArguments( "\\a=\"32 " );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that a character
   * defined with {@code \mathchardef} is applicable in math mode only.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErr1() throws Exception {

    assertFailure(
        // --- input code ---
        "\\mathchardef\\alpha=\"010B "
            + "\\alpha\\end",
        // --- output message ---
        "Missing $ inserted" );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that a math char
   * of -1 is not accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErr2() throws Exception {

    assertFailure(
        // --- input code ---
        "\\mathchardef\\alpha=-1 ",
        // --- output message ---
        "Bad mathchar (-1)" );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that a math char
   * of 32769 is not accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErr3() throws Exception {

    assertFailure(
        // --- input code ---
        "\\mathchardef\\alpha=32769 ",
        // --- output message ---
        "Bad mathchar (32769)" );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that a correct
   * value is accepted.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(
        // --- input code ---
        AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
            + "\\mathchardef\\alpha=\"010B"
            + "$a\\alpha b$\\end",
        // --- output message ---
        "a\013b" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} can be used to define an alias.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\mathchardef\\alpha=\"010B "
            + "\\mathchardef\\beta=\\alpha "
            + "\\the\\beta\\end",
        // --- output message ---
        "267" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} defines a control sequence which is convertible
   * into a count register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount1() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\mathchardef\\alpha=\"010B "
            + "\\count0=\\alpha \\the\\count0\\end",
        // --- output message ---
        "267" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} can define an active character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount2() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\catcode`~=13 " + "\\mathchardef~=\"010B "
            + "\\count0=~ \\the\\count0\\end",
        // --- output message ---
        "267" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} defines a control sequence which is applicable to
   * \the.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testThe1() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\mathchardef\\alpha=\"010B "
            + "\\the\\alpha\\end",
        // --- output message ---
        "267" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} respects groups.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGroup1() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\begingroup\\global\\mathchardef\\alpha=\"010B \\endgroup"
            + "\\the\\alpha\\end",
        // --- output message ---
        "267" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} respects groups.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGroup2() throws Exception {

    assertFailure(
        // --- input code ---
        "\\begingroup\\mathchardef\\alpha=\"010B \\endgroup"
            + "\\the\\alpha\\end",
        // --- output message ---
        "Undefined control sequence \\alpha" );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} accepts an extended syntax with a symbolic name.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExt1() throws Exception {

    assertSuccess(
        // --- input code ---
        DEFINE_BRACES + "\\mathchardef\\alpha={ord 1 `A} "
            + "\\the\\alpha\\end",
        // --- output message ---
        "321" + TERM );
  }

  /**
   * <testcase primitive="\mathchardef"> Test case checking that
   * {@code \mathchardef} accepts an extended syntax with a numeric class.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExt2() throws Exception {

    assertSuccess(
        // --- input code ---
        DEFINE_BRACES + "\\mathchardef\\alpha={0 1 `A} "
            + "\\the\\alpha\\end",
        // --- output message ---
        "321" + TERM );
  }

  // TODO implement more primitive specific test cases

}
