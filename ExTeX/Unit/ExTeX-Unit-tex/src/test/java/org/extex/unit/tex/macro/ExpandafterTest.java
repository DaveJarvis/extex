/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \expandafter}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ExpandafterTest extends ExTeXLauncher {


  public ExpandafterTest() {

  }

  /**
   * <testcase primitive="\expandafter">
   * Test case checking that {@code \expandafter} exchanges two letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(//--- input code ---
                  "\\expandafter",
                  //--- output message ---
                  "Unexpected end of file while processing \\expandafter" );
  }

  /**
   * <testcase primitive="\expandafter">
   * Test case checking that {@code \expandafter} exchanges two letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpandafterLetterLetter1() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_CATCODES + "\\expandafter ab" + "\\end",
                  //--- output message ---
                  "ba" + TERM );
  }

  /**
   * <testcase primitive="\expandafter">
   * Test case checking that {@code \expandafter} passes on {@code \global}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGlobal1() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_CATCODES + "\\global\\expandafter A\\count0=123 B"
                      + "\\end",
                  //--- output message ---
                  "AB" + TERM );
  }

  /**
   * <testcase primitive="\expandafter">
   * Test case checking that {@code \expandafter} can expand the second
   * token.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_CATCODES + "\\let\\x=X \\let\\y=Y"
                      + "\\expandafter\\x\\y A" + "\\end",
                  //--- output message ---
                  "YXA" + TERM );
  }

}
