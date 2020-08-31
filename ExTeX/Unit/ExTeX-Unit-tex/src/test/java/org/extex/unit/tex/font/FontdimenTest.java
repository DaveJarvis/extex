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

package org.extex.unit.tex.font;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \fontdimen}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class FontdimenTest extends NoFlagsPrimitiveTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( FontdimenTest.class );
  }


  public FontdimenTest() {

    setPrimitive( "fontdimen" );
    setArguments( "0\\nullfont=123pt " );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen
   * needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\fontdimen",
                  // --- output channel ---
                  "Unexpected end of file while processing \\fontdimen" );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen
   * needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "\\the\\fontdimen",
                  // --- output channel ---
                  "Unexpected end of file while processing \\fontdimen" );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen on
   * unset keys returns 0 pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnset1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\fontdimen0\\nullfont " + "\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen on
   * unset keys returns 0 pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUnset2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\fontdimen65000\\nullfont " + "\\end",
                  // --- output channel ---
                  "0.0pt" + TERM );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen can
   * be set and read back for \nullfont.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSet1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\fontdimen65000\\nullfont=42pt "
                      + "\\the\\fontdimen65000\\nullfont " + "\\end",
                  // --- output channel ---
                  "42.0pt" + TERM );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen can
   * be set and read back for cmtt12.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testSet2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\fnt=cmtt12\\relax " + "\\fontdimen65000\\fnt=42pt "
                      + "\\the\\fontdimen65000\\fnt " + "\\end",
                  // --- output channel ---
                  "42.0pt" + TERM );
  }

  /**
   * <testcase primitive="\fontdimen"> Test case checking that \fontdimen is
   * an assignment.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testAssign1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\afterassignment abc" + "\\fontdimen0\\nullfont=42pt"
                      + "\\the\\fontdimen0\\nullfont " + "\\end",
                  // --- output channel ---
                  "bca42.0pt" + TERM );
  }

}
