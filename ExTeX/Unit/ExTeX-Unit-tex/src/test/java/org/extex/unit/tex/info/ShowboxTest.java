/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.info;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \showbox}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ShowboxTest extends NoFlagsPrimitiveTester {


  public ShowboxTest() {

    setPrimitive( "showbox" );
    setArguments( "1 " );
    setPrepare( "" );
    setOut( "\\box1=void\nOK\n" );
  }

  /**
   * <testcase primitive="\showbox"> Test case checking that {@code \showbox}
   * needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError0() throws Exception {

    assertFailure(// --- input code ---
                  "\\showbox ",
                  // --- error channel ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\showbox"> Test case checking that {@code \showbox}
   * complains about a negative register code.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\showbox -1",
                  // --- error channel ---
                  "Bad register code (-1)" );
  }

  /**
   * <testcase primitive="\showbox"> Test case checking that {@code \showbox}
   * of a hbox works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHbox1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\setbox1=\\hbox{}\\showbox 1 \\end",
                  // --- error channel ---
                  "\\box1=\n\\hbox(0.0pt+0.0pt)x0.0pt\nOK\n" );
  }

  /**
   * <testcase primitive="\showbox"> Test case checking that {@code \showbox}
   * of a vbox works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVbox1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\setbox1=\\vbox{}\\showbox 1 \\end",
                  // --- error channel ---
                  "\\box1=\n\\vbox(0.0pt+0.0pt)x0.0pt\nOK\n" );
  }

  /**
   * <testcase primitive="\showbox"> Test case checking that {@code \showbox}
   * of a void register works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVoid1() throws Exception {

    assertFailure(// --- input code ---
                  "\\showbox 1 \\end",
                  // --- error channel ---
                  "\\box1=void\nOK\n" );
  }

  // TODO implement more primitive specific test cases

}
