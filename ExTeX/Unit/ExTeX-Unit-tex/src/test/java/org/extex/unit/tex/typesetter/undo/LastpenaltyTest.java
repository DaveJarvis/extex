/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.typesetter.undo;

import org.extex.test.count.AbstractReadonlyCountRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \lastpenalty}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LastpenaltyTest extends AbstractReadonlyCountRegisterTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( LastpenaltyTest.class );
  }


  public LastpenaltyTest() {

    super( "lastpenalty", "", "0" );
  }

  /**
   * Test case checking that {@code \lastpenalty} is showable.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    assertOutput(
        // --- input code ---
        "a\\penalty123 \\showthe\\lastpenalty\\end",
        // --- output channel ---
        "> 123.\n",

        null );
  }

  /**
   * Test case checking that {@code \lastpenalty} on an empty list returns 0
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertOutput(
        // --- input code ---
        "\\showthe\\lastpenalty\\end",
        // --- output channel ---
        "> 0.\n",

        null );
  }

  /**
   * Test case checking that \relax does not produce a node.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertOutput(
        // --- input code ---
        "a\\penalty123\\relax\\showthe\\lastpenalty\\end",
        // --- output channel ---
        "> 123.\n",

        null );
  }

  /**
   * Test case checking that <tt\lastpenalty</tt> returns 0 if the last
   * node is a character node
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertOutput(
        // --- input code ---
        "a\\showthe\\lastpenalty\\end",
        // --- output channel ---
        "> 0.\n",

        null );
  }

  /**
   * Test case checking that <tt\lastpenalty</tt> returns 0 if the last
   * node is a kern node
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertOutput(
        // --- input code ---
        "a\\kern 12pt\\showthe\\lastpenalty\\end",
        // --- output channel ---
        "> 0.\n",

        null );
  }

  /**
   * Test case checking that {@code \lastpenalty} returns 0 if the last
   * node is a glue node
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertOutput(
        // --- input code ---
        "a\\hfill \\showthe\\lastpenalty\\end",
        // --- output channel ---
        "> 0.\n",

        null );
  }

  /**
   * Test case checking that {@code \lastpenalty} is count convertible
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test10() throws Exception {

    assertOutput(
        // --- input code ---
        "a\\penalty123 \\count0=\\lastpenalty x\\showthe\\count0\\end",
        // --- output channel ---
        "> 123.\n",

        null );
  }

}
