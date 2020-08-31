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

package org.extex.unit.tex.typesetter.paragraph;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \parshape}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ParshapeTest extends NoFlagsPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( ParshapeTest.class );
  }


  public ParshapeTest() {

    setPrimitive( "parshape" );
    setArguments( "0" );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="\parshape"> Test case showing that
   * {@code \parshape} needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\parshape",
                  // --- output channel ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\parshape"> Test case showing that
   * {@code \parshape} is theable and returns the number of entries if they
   * are 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape0 \\the\\parshape \\end",
                  // --- output channel ---
                  "0" + TERM );
  }

  /**
   * <testcase primitive="\parshape"> Test case showing that
   * {@code \parshape} on a length of 1 returns 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 1pt2pt \\the\\parshape \\end",
                  // --- output channel ---
                  "1" + TERM );
  }

  /**
   * <testcase primitive="\parshape"> Test case showing that
   * {@code \parshape} is a count value.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape0 \\count1=\\parshape \\the\\count1 \\end",
                  // --- output channel ---
                  "0" + TERM );
  }

  /**
   * <testcase primitive="\parshape"> Test case showing that
   * {@code \parshape} returns the count value of the length.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\parshape1 2pt3pt \\count1=\\parshape \\the\\count1 \\end",
                  // --- output channel ---
                  "1" + TERM );
  }

  // TODO implement more primitive specific test cases
}
