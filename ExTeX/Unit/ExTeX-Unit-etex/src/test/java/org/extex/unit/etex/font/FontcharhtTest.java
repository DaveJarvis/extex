/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.etex.font;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \fontcharht}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FontcharhtTest extends ExTeXLauncher {


  public FontcharhtTest() {

    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\fontcharht">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVertical1() throws Exception {

    assertFailure(//--- input code ---
                  "\\fontcharht ",
                  //--- log message ---
                  "You can't use `\\fontcharht' in vertical mode" );
  }

  /**
   * <testcase primitive="\fontcharht">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(//--- input code ---
                  "\\dimen0=\\fontcharht ",
                  //--- log message ---
                  "Unexpected end of file while processing \\fontcharht" );
  }

  /**
   * <testcase primitive="\fontcharht">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(//--- input code ---
                  "\\dimen0=\\fontcharht\\nullfont ",
                  //--- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\fontcharht" checked="etex">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPoint() throws Exception {

    assertSuccess(//--- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\the\\fontcharht\\cmr `.\\end",
                  //--- output message ---
                  "1.05554pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharht" checked="etex">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrG() throws Exception {

    assertSuccess(//--- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\the\\fontcharht\\cmr `g\\end",
                  //--- output message ---
                  "4.30554pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharht" checked="etex">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus() throws Exception {

    assertSuccess(//--- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\the\\fontcharht\\cmr `+\\end",
                  //--- output message ---
                  "5.83333pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharht" checked="etex">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus2() throws Exception {

    assertSuccess(//--- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\dimen0=\\fontcharht\\cmr `+\\the\\dimen0\\end",
                  //--- output message ---
                  "5.83333pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharht">
   * Test case checking that {@code \fontcharht} can not be used in vertical
   * mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus3() throws Exception {

    assertSuccess(//--- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\count0=\\fontcharht\\cmr `+\\the\\count0\\end",
                  //--- output message ---
                  "382293" + TERM );
  }

}
