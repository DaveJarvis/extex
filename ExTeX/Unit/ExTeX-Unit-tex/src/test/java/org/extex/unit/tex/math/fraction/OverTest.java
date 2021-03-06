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

package org.extex.unit.tex.math.fraction;

import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \over}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class OverTest extends AbstractMathTester {


  public OverTest() {

    setPrimitive( "over" );
    setArguments( " b$" );
    setPrepare( "a " );
  }

  /**
   * <testcase primitive="\over">
   * ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testError1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_MATH_FONTS + DEFINE_MATH + "$a\\over b \\over c$ " +
                      "\\end",
                  //--- error stream ---
                  "Ambiguous; you need another { and }" );
  }

  /**
   * <testcase primitive="\over">
   * ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testMathMode1() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_MATH_FONTS + DEFINE_MATH
                      + "$a\\over b$ \\end",
                  //--- output stream ---
                  "???" ); //TODO gene: check
  }

  /**
   * Test case checking that \mathaccent needs the math mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testMath2() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_MATH_FONTS + DEFINE_MATH + DEFINE_BRACES
                      + "${a \\over b}$ \\end",
                  //--- output channel ---
                  "???" ); //TODO gene: check
  }

  //TODO: write more primitive specific test cases
}
