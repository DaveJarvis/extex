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

package org.extex.unit.tex.file;

import org.extex.test.NoFlagsButImmediatePrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \write}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class WriteTest extends NoFlagsButImmediatePrimitiveTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( WriteTest.class );
  }


  public WriteTest() {

    super( "write", "1 {abc}" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that a lonely
   * {@code \write} leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\write ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that a lonely
   * {@code \write} with an index leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "\\write 2",
                  // --- log message ---
                  "File ended while scanning text of \\write" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that a lonely
   * {@code \write} leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof3() throws Exception {

    assertFailure(// --- input code ---
                  "\\immediate\\write ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that a lonely
   * {@code \write} with an index leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof4() throws Exception {

    assertFailure(// --- input code ---
                  "\\immediate\\write 2",
                  // --- log message ---
                  "Unexpected end of file while processing \\write" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that {@code \write}
   * takes {@code \immediate}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testImmediate1() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\immediate\\write 2{abc} \\end",
                 // --- log message ---
                 "abc\n", "" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that {@code \write}
   * complains about undefined active characters in the argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\catcode`\\~=13" + "x\\write 2{a~c} \\end",
                  // --- log message ---
                  "Undefined control sequence ~\n" );
  }

  /**
   * <testcase primitive="\write"> Test case checking that {@code \write}
   * expands active characters in the argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\catcode`\\~=13 \\def~{xyz} "
                     + "x\\write 2{a~c} \\end",
                 // --- log message ---
                 "axyzc", "x" + TERM );
  }

  /**
   * <testcase primitive="\write"> Test case checking that {@code \write}
   * writes active characters mapped to primitives in the argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\catcode`\\~=13 \\let~\\penalty "
                     + "x\\write 2{a~c} \\end",
                 // --- log message ---
                 "a~c", "x" + TERM );
  }

}
