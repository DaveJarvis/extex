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
 * This is a test suite for the primitive {@code \fontchardp}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FontchardpTest extends ExTeXLauncher {


  public FontchardpTest() {

    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\fontchardp"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVertical1() throws Exception {

    assertFailure(// --- input code ---
                  "\\fontchardp ",
                  // --- log message ---
                  "You can't use `\\fontchardp' in vertical mode" );
  }

  /**
   * <testcase primitive="\fontchardp"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\dimen0=\\fontchardp ",
                  // --- log message ---
                  "Unexpected end of file while processing \\fontchardp" );
  }

  /**
   * <testcase primitive="\fontchardp"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "\\dimen0=\\fontchardp\\nullfont ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\fontchardp"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrA() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 " + "\\the\\fontchardp\\cmr `a\\end",
                  // --- output message ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\fontchardp" checked="etex"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrG() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 " + "\\the\\fontchardp\\cmr `g\\end",
                  // --- output message ---
                  "1.94444pt" + TERM );
  }

  /**
   * <testcase primitive="\fontchardp" checked="etex"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 " + "\\the\\fontchardp\\cmr `+\\end",
                  // --- output message ---
                  "0.83333pt" + TERM );
  }

  /**
   * <testcase primitive="\fontchardp" checked="etex"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\dimen0\\fontchardp\\cmr `+\\the\\dimen0\\end",
                  // --- output message ---
                  "0.83333pt" + TERM );
  }

  /**
   * <testcase primitive="\fontchardp"> Test case checking that
   * {@code \fontchardp} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmrPlus3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmr cmr10 "
                      + "\\count0=\\fontchardp\\cmr `+\\the\\count0\\end",
                  // --- output message ---
                  "54613" + TERM );
  }

}
