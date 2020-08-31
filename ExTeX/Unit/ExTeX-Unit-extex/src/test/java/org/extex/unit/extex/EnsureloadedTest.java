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

package org.extex.unit.extex;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \ensureloaded}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class EnsureloadedTest extends NoFlagsPrimitiveTester {


  public EnsureloadedTest() {

    setPrimitive( "ensureloaded" );
    setArguments( "{tex}" );
    setPrepare( "" );
    setConfig( "extex-test" );
  }

  /**
   * <testcase primitive="\ensureloaded"> Test case checking that
   * {@code \ensureloaded} needs an argument; i.e. the end of file is
   * reported as error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(
        // --- input code ---
        DEFINE_BRACES + "\\ensureloaded",
        // --- log message ---
        "Unexpected end of file while processing \\ensureloaded" );
  }

  /**
   * <testcase primitive="\ensureloaded"> Test case checking that
   * {@code \ensureloaded} can report the named unit as missing when it
   * does not exist.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure(
        // --- input code ---
        DEFINE_BRACES + "\\ensureloaded{xxx}",
        // --- log message ---
        "I don't know the unit `xxx'" );
  }

  /**
   * <testcase primitive="\ensureloaded"> Test case checking that
   * {@code \ensureloaded} can load a named unit if it exists.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(
        // --- input code ---
        DEFINE_BRACES + "\\ensureloaded{extex}" + "\\the\\ignorevoid"
            + " \\end",
        // --- log message ---
        "0" + TERM );
  }

  /**
   * <testcase primitive="\ensureloaded"> Test case checking that
   * {@code \ensureloaded} can load a named unit if it exists.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(
        // --- input code ---
        DEFINE_BRACES + "\\ensureloaded{extex}"
            + "\\ifx\\relax\\everymathend\\else ok\\fi" + " \\end",
        // --- log message ---
        "ok" + TERM );
  }

}
