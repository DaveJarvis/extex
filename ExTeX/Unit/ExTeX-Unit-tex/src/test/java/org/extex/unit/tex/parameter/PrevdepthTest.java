/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.parameter;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \prevdepth}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PrevdepthTest extends NoFlagsPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( PrevdepthTest.class );
  }


  public PrevdepthTest() {

    setPrimitive( "prevdepth" );
    setArguments( "2pt" );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} in horizontal mode leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHorizontalMode1() throws Exception {

    assertFailure(// --- input code ---
                  "a\\prevdepth=12pt ",
                  // --- error channel ---
                  "You can't use `\\prevdepth' in horizontal mode" );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} in horizontal mode leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHorizontalMode2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\hbox{\\prevdepth=12pt} ",
                  // --- error channel ---
                  "You can't use `\\prevdepth' in restricted horizontal mode" );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} in math mode leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMathMode1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "$\\prevdepth=12pt $",
                  // --- error channel ---
                  "You can't use `\\prevdepth' in math mode" );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} in display math mode leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMathMode2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "$$\\prevdepth=12pt $$",
                  // --- error channel ---
                  "You can't use `\\prevdepth' in displaymath mode" );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} has a default value of -1000pt.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\prevdepth\\end",
                  // --- error channel ---
                  "-1000.0pt" + TERM );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\prevdepth=12pt \\the\\prevdepth\\end",
                  // --- error channel ---
                  "12.0pt" + TERM );
  }

  /**
   * <testcase primitive="\prevdepth"> Test case checking that
   * {@code \prevdepth} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\prevdepth=12pt \\dimen1=\\prevdepth \\the\\dimen1\\end",
                  // --- error channel ---
                  "12.0pt" + TERM );
  }

}
