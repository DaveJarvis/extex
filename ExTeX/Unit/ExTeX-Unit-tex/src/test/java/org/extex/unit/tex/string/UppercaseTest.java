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

package org.extex.unit.tex.string;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code &#x5c;uppercase}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class UppercaseTest extends NoFlagsPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( UppercaseTest.class );
  }


  public UppercaseTest() {

    setPrimitive( "uppercase" );
    setArguments( "{abc}" );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="&#x5c;uppercase"> Test case checking that
   * {@code &#x5c;uppercase} throws an error on eof.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEOF1() throws Exception {

    assertFailure(// --- input code ---
                  "\\uppercase",
                  // --- log message ---
                  "File ended while scanning text of \\uppercase" );
  }

  /**
   * <testcase primitive="&#x5c;uppercase"> Test case checking that
   * {@code &#x5c;uppercase} is invariant on uppercase letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\uppercase{ABC}\\end",
                  // --- output channel ---
                  "ABC" + TERM );
  }

  /**
   * <testcase primitive="&#x5c;uppercase"> Test case checking that
   * {@code &#x5c;uppercase} translates lowercase letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\uppercase{abc}\\end",
                  // --- output channel ---
                  "ABC" + TERM );
  }

  /**
   * <testcase primitive="&#x5c;uppercase"> Test case checking that
   * {@code &#x5c;uppercase} translates mixed letters.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\uppercase{aBc}\\end",
                  // --- output channel ---
                  "ABC" + TERM );
  }

  /**
   * <testcase primitive="&#x5c;uppercase"> Test case checking that
   * {@code &#x5c;uppercase} respects uccode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test10() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\uccode`b=`1 " + "\\uppercase{abc}\\end",
                  // --- output channel ---
                  "A1C" + TERM );
  }

}
