/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.box;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractBoxDimenTester
    extends NoFlagsButGlobalPrimitiveTester {

  /**
   * The field {@code primitive} contains the name of the primitive.
   */
  private String primitive;

  public AbstractBoxDimenTester() {
    super.setArguments( "1=0pt" );
    super.setPrepare( "0" );
  }

  @Override
  public void setPrimitive( final String primitive ) {
    super.setPrimitive( primitive );
    this.primitive = primitive;
  }

  /**
   * Test case checking that the primitive needs a key.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure( "\\dimen0=\\" + primitive,
                   "Missing number, treated as zero" );
  }

  /**
   * Test case checking that the primitive needs a key.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof3() throws Exception {

    assertFailure( "\\the\\" + primitive,
                   "Missing number, treated as zero" );
  }

  /**
   * Test case checking that the primitive respects \afterassignment.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAfterassignment1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\afterassignment x-\\" + primitive
            + "1=2pt\\end ",
        "-x" + TERM );
  }

  /**
   * Test case checking that the primitive respects \global.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGlobal1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\global\\" + primitive + "1=2pt\\end ",
        "" );
  }

  /**
   * Test case checking that the primitive is applicable on a hbox.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHbox1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\setbox1=\\hbox{x}\\" + primitive
            + "1=2pt \\the\\" + primitive + "1\\end ",
        "2.0pt" + TERM );
  }

  /**
   * Test case checking that the primitive is count convertible on a void box.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\count0=\\" + primitive + "1 \\the\\count0\\end ",
        "0" + TERM );
  }

  /**
   * Test case checking that the primitive is dimen convertible on a void box.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDimen1() throws Exception {

    assertSuccess(
        DEFINE_BRACES + "\\dimen0=\\" + primitive + "1 \\the\\dimen0\\end ",
        "0.0pt" + TERM );
  }

  //TODO implement more primitive specific test cases
}
