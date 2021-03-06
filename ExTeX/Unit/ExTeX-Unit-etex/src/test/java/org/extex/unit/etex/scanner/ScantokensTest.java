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

package org.extex.unit.etex.scanner;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \scantokens}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ScantokensTest extends NoFlagsPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( ScantokensTest.class );
  }


  public ScantokensTest() {

    setPrimitive( "scantokens" );
    setArguments( "{}" );
    setPrepare( "" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\scantokens"> Test case checking that
   * {@code \scantokens} needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\scantokens",
                  // --- output channel ---
                  "File ended while scanning text of \\scantokens" );
  }

  /**
   * <testcase primitive="\scantokens"> Test case checking that
   * {@code \scantokens} consumes a letter.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\scantokens{a}\\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\scantokens"> Test case checking that
   * {@code \scantokens} respects \escapechar.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEscapechar1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\escapechar=65 \\scantokens{\\xxx}\\end",
                  // --- output channel ---
                  "Axxx" + TERM );
  }

  /**
   * <testcase primitive="\scantokens"> Test case checking that
   * {@code \scantokens} inserts the tokens from {@code \everyeof}.
   * <p>
   * <p>
   * Note: The white-space inserted by \scantokens is correct (checked with
   * e-TeX)
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEveryeof1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\everyeof{x}\\scantokens{a}b\\end",
                  // --- output channel ---
                  "a xb" + TERM );
  }

  // TODO implement more primitive specific test cases
}
