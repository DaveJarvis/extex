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

package org.extex.unit.etex.info;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \showtokens}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ShowtokensTest extends NoFlagsPrimitiveTester {


  public ShowtokensTest() {

    setPrimitive( "showtokens" );
    setArguments( "{abc}" );
    setPrepare( "" );
    setOut( "> abc.\n" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError0() throws Exception {

    assertFailure(// --- input code ---
                  "\\showtokens",
                  // --- output channel ---
                  "Unexpected end of file while processing tokens" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} needs a complete argument group.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\showtokens{xxx",
                  // --- output channel ---
                  "File ended while scanning text of \\showtokens" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} does not expand \relax.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\showtokens{\\relax}" + "\\end",
                 // --- output channel ---
                 "> \\relax.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} does not expand \jobname.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\showtokens{\\jobname}" + "\\end",
                 // --- output channel ---
                 "> \\jobname.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} shows an undefined active character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\catcode`\\~=13 " + "\\showtokens{x~x}" + 
                     "\\end",
                 // --- output channel ---
                 "> x~x.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} doubles the macro param character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_CATCODES + "\\showtokens{x#x}" + "\\end",
                 // --- output channel ---
                 "> x##x.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} shows an subscript character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_CATCODES + "\\showtokens{x_x}" + "\\end",
                 // --- output channel ---
                 "> x_x.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} shows an superscript character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test6() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_CATCODES + "\\showtokens{x^x}" + "\\end",
                 // --- output channel ---
                 "> x^x.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} lets \expandafter do it's work before reading the
   * token list.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test12() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\showtokens\\expandafter{\\jobname}" + 
                     "\\end",
                 // --- output channel ---
                 "> texput.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} complains if aargument group is missing.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test21() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\showtokens\\relax",
                  // --- output channel ---
                  "Missing `{' inserted" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} can take the tokens from a tokens register.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test22() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\toks0={abc}" + "\\showtokens\\toks0" + 
                     "\\end",
                 // --- output channel ---
                 "> abc.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} shows embedded groups with braces.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test23() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\showtokens{x{y}z}" + "\\end",
                 // --- output channel ---
                 "> x{y}z.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} can be used in a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test31() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_CATCODES + "\\def\\a#1{\\showtokens{#1}}" + "\\a{0}"
                     + "\\end",
                 // --- output channel ---
                 "> 0.\n", "" );
  }

  /**
   * <testcase primitive="\showtokens"> Test case checking that
   * {@code \showtokens} preserves braces in an argument of the macr
   * invocation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test32() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_CATCODES + "\\def\\a#1{\\showtokens{#1}}" + "\\a{{0}}"
                     + "\\end",
                 // --- output channel ---
                 "> {0}.\n", "" );
  }

  // TODO implement more primitive specific test cases
}
