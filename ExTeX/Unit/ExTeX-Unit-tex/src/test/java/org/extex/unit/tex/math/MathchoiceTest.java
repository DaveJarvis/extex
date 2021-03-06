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

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \mathchoice}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MathchoiceTest extends AbstractMathTester {


  public MathchoiceTest() {

    setPrimitive( "mathchoice" );
    setArguments( "{}{}{}{}" );
    setPrepare( "" );
  }

  /**
   * Test case checking that {@code \mathchoice} selects the correct branch
   * in display mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testDisplay1() throws Exception {

    assertSuccess(//--- input code ---
                  AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                      + "$$\\mathchoice{a}{b}{c}{d}$$ \\end",
                  //--- output channel ---
                  "a" + TERM );
  }

  /**
   * Test case checking that {@code \mathchoice} selects the correct branch
   * in text mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testText1() throws Exception {

    assertSuccess(//--- input code ---
                  AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                      + "$\\mathchoice{a}{b}{c}{d}$ \\end",
                  //--- output channel ---
                  "b" + TERM );
  }

  /**
   * Test case checking that {@code \mathchoice} selects the correct branch
   * in explicit display mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testDisplay2() throws Exception {

    assertSuccess(//--- input code ---
                  AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                      + "$\\displaystyle\\mathchoice{a}{b}{c}{d}$ \\end",
                  //--- output channel ---
                  "a" + TERM );
  }

  /**
   * Test case checking that {@code \mathchoice} selects the correct branch
   * in explicit script mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testScript1() throws Exception {

    assertSuccess(//--- input code ---
                  AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                      + "$\\scriptstyle\\mathchoice{a}{b}{c}{d}$ \\end",
                  //--- output channel ---
                  "c" + TERM );
  }

  /**
   * Test case checking that {@code \mathchoice} selects the correct branch
   * in explicit scriptscript mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testScriptScript1() throws Exception {

    assertSuccess(//--- input code ---
                  AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
                      + "$\\scriptscriptstyle\\mathchoice{a}{b}{c}{d}$ \\end",
                  //--- output channel ---
                  "d" + TERM );
  }

  //TODO implement more primitive specific test cases

}
