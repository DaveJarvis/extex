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

package org.extex.unit.etex.conditional;

import org.extex.unit.tex.conditional.ConditionalTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \ifcsname}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IfcsnameTest extends ConditionalTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfcsnameTest.class );
  }


  public IfcsnameTest() {

    super( "ifcsname", " relax\\endcsname" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} needs some more tokens to follow.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcsname ",
                  // --- output channel ---
                  "Unexpected end of file" );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} needs a matching \endcsname.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcsname abc ",
                  // --- output channel ---
                  "Unexpected end of file" );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} needs a matching \endcsname.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError3() throws Exception {

    assertFailure(// --- input code ---
                  "\\ifcsname abc \\par",
                  // --- output channel ---
                  "Missing \\endcsname inserted" );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} on relax expands the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcsname relax\\endcsname a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} on par expands the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcsname par\\endcsname a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} expands macros on the fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\x{par}"
                      + "\\ifcsname \\x\\endcsname a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} expands macros on the fly.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\x{ar}"
                      + "\\ifcsname p\\x\\endcsname a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} ignores embedded spaces.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\x{ar}"
                      + "\\ifcsname p \\x\\endcsname a\\else b\\fi \\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifcsname"> Test case checking that
   * {@code \ifcsname} on an undefined control sequence expands the else
   * branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLetter2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifcsname abc\\endcsname a\\else b\\fi \\end",
                  // --- output channel ---
                  "b" + TERM );
  }

  // TODO implement more primitive specific test cases
}
