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

package org.extex.unit.tex.arithmetic;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \multiply}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MultiplyTest extends NoFlagsButGlobalPrimitiveTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( MultiplyTest.class );
  }


  public MultiplyTest() {

    setPrimitive( "multiply" );
    setArguments( "\\count1 1 " );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \advance} needs one arguments.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\multiply ",
                  // --- log message ---
                  "Unexpected end of file while processing \\multiply" );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} needs a defined control sequence as first argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUndef1() throws Exception {

    assertFailure(// --- input code ---
                  "\\multiply \\x ",
                  // --- log message ---
                  "Undefined control sequence \\x" );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a letter leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter1() throws Exception {

    assertFailure(// --- input code ---
                  "\\multiply a",
                  // --- log message ---
                  "You can't use `the letter a' after \\multiply" );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a other token leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOther1() throws Exception {

    assertFailure(// --- input code ---
                  "\\multiply 12 ",
                  // --- log message ---
                  "You can't use `the character 1' after \\multiply" );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a macro parameter token leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro1() throws Exception {

    assertFailure(// --- input code ---
                  "\\catcode`#=6 " + "\\multiply #2 ",
                  // --- log message ---
                  "You can't use `macro parameter character #' after " +
                      "\\multiply" );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a non-multipliable primitive (\\relax) leads to
   * an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRelax1() throws Exception {

    assertFailure(// --- input code ---
                  "\\multiply \\relax ",
                  // --- log message ---
                  "You can't use `the control sequence \\relax' after " +
                      "\\multiply" );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a count register name works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count1 8 " + "\\multiply \\count1 16 " + "\\the\\count1 " +
                      "\\end",
                  // --- output channel ---
                  "128" + TERM );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a count register name works with the global flag.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\count1 8 "
                      + "\\begingroup\\global\\multiply \\count1 16 \\endgroup "
                      + "\\the\\count1 \\end",
                  // --- output channel ---
                  "128" + TERM );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a dimen register name works.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDimen1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen1 8pt " + "\\multiply \\dimen1 16 " + "\\the" +
                      "\\dimen1 \\end",
                  // --- output channel ---
                  "128.0pt" + TERM );
  }

  /**
   * <testcase primitive="\multiply"> Test case checking that
   * {@code \multiply} on a dimen register name works with the global flag.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testDimen2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\dimen1 8pt "
                      + "\\begingroup\\global\\multiply \\dimen1 16 \\endgroup "
                      + "\\the\\dimen1 \\end",
                  // --- output channel ---
                  "128.0pt" + TERM );
  }

}
