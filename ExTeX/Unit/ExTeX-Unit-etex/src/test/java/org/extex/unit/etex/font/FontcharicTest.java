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
 * This is a test suite for the primitive {@code \fontcharic}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FontcharicTest extends ExTeXLauncher {


  public FontcharicTest() {

    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\fontcharic"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVertical1() throws Exception {

    assertFailure(// --- input code ---
                  "\\fontcharic ",
                  // --- log message ---
                  "You can't use `\\fontcharic' in vertical mode" );
  }

  /**
   * <testcase primitive="\fontcharic"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\dimen0=\\fontcharic ",
                  // --- log message ---
                  "Unexpected end of file while processing \\fontcharic" );
  }

  /**
   * <testcase primitive="\fontcharic"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "\\dimen0=\\fontcharic\\nullfont ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\fontcharic" checked="etex"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmslPoint() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmsl cmsl10 " + "\\the\\fontcharic\\cmsl `.\\end",
                  // --- output message ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharic" checked="etex"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmslL() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmsl cmsl10 " + "\\the\\fontcharic\\cmsl `l\\end",
                  // --- output message ---
                  "0.39354pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharic" checked="etex"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmslX() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmsl cmsl10 " + "\\the\\fontcharic\\cmsl `x\\end",
                  // --- output message ---
                  "0.68982pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharic" checked="etex"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmslPlus2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmsl cmsl10 "
                      + "\\dimen0=\\fontcharic\\cmsl `+\\the\\dimen0\\end",
                  // --- output message ---
                  "0.1389pt" + TERM );
  }

  /**
   * <testcase primitive="\fontcharic"> Test case checking that
   * {@code \fontcharic} can not be used in vertical mode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCmslPlus3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\cmsl cmsl10 "
                      + "\\count0=\\fontcharic\\cmsl `+\\the\\count0\\end",
                  // --- output message ---
                  "9103" + TERM );
  }

}
