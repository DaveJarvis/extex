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
 * This is a test suite for the primitive {@code \fontcharwd}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FontcharwdTest extends ExTeXLauncher {


  public FontcharwdTest() {

    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\fontcharwd"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVertical1() throws Exception {

    assertFailure(// --- input code ---
                  "\\fontcharwd ",
                  // --- log message ---
                  "You can't use `\\fontcharwd' in vertical mode" );
  }

  /**
   * <testcase primitive="\fontcharwd"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\dimen0=\\fontcharwd ",
                  // --- log message ---
                  "Unexpected end of file while processing \\fontcharwd" );
  }

  /**
   * <testcase primitive="\fontcharwd"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "\\dimen0=\\fontcharwd\\nullfont ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\fontcharwd" checked="etex"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPoint() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 " + "\\the\\fontcharwd\\cmr `.\\end",
                  // --- output message ---
                  "2.77779pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharwd" checked="etex"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrG() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 " + "\\the\\fontcharwd\\cmr `g\\end",
                  // --- output message ---
                  "5.00002pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharwd" checked="etex"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 " + "\\the\\fontcharwd\\cmr `+\\end",
                  // --- output message ---
                  "7.7778pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharwd" checked="etex"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\dimen0=\\fontcharwd\\cmr `+\\the\\dimen0\\end",
                  // --- output message ---
                  "7.7778pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharwd"> Test case checking that
   * {@code \fontcharwd} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\count0=\\fontcharwd\\cmr `+\\the\\count0\\end",
                  // --- output message ---
                  "509726" + TERM );
  }

}
