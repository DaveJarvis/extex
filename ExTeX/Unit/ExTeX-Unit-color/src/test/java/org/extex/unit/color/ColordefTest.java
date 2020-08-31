/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.color;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \colordef}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ColordefTest extends NoFlagsButGlobalPrimitiveTester {


  public ColordefTest() {

    setPrimitive( "colordef" );
    setArguments( "\\x{.1 .2 .3}" );
    setPrepare( "" );
    setConfig( "colorextex-test" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} produces a control sequence which is showable.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x {.1 .2 .3} " + "\\showthe\\x" +
                      " \\end",
                  // --- log message ---
                  "> rgb {0.09999237 0.19998474 0.29999238}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} can take as value a color variable.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test20() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x {.1 .2 .3} " + "\\colordef" +
                      "\\y\\x"
                      + "\\showthe\\y \\end",
                  // --- log message ---
                  "> rgb {0.09999237 0.19998474 0.29999238}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} can take a value as color constant with an implicit
   * RGB color model.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test30() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x {1 1 1} " + "\\showthe\\x " +
                      "\\end",
                  // --- log message ---
                  "> rgb {1.0 1.0 1.0}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} can take a value as color constant with an explicit
   * RGB color model.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test31() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x rgb {1 1 1} " + "\\showthe" +
                      "\\x \\end",
                  // --- log message ---
                  "> rgb {1.0 1.0 1.0}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} can take a value as color constant with an explicit
   * gray scale color model.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test32() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x gray {1} " + "\\showthe\\x " +
                      "\\end",
                  // --- log message ---
                  "> gray {1.0}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} can take a value as color constant with an explicit
   * HSV color model.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test33() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x hsv {1 1 1} " + "\\showthe" +
                      "\\x \\end",
                  // --- log message ---
                  "> hsv {1.0 1.0 1.0}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} can take a value as color constant with an explicit
   * CMYK color model.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test34() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x cmyk {1 1 1 1} "
                      + "\\showthe\\x \\end",
                  // --- log message ---
                  "> cmyk {1.0 1.0 1.0 1.0}.\n" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} neds an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError0() throws Exception {

    assertFailure(// --- input code ---
                  "\\colordef",
                  // --- log message ---
                  "Missing control sequence inserted" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} needs a control sequence as first argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\colordef rgb",
                  // --- log message ---
                  "Missing control sequence inserted" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} needs a left brace after the control seqeunce.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure(// --- input code ---
                  "\\colordef\\x undef ",
                  // --- log message ---
                  "Missing left brace for color value" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} needs enough numbers in the braces.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError3() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x {1} ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="colordef"> Test case checking that
   * {@code \colordef} complains about too large values for the color
   * components.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError4() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\colordef\\x {1 2 3} ",
                  // --- log message ---
                  "Illegal color value" );
  }

}
