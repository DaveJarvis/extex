/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.macro;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;

/**
 * This is a test suite for def primitives.
 * <p>
 * In a derived class the actual name of the def primitive to be tested is
 * passed in via the constructor. It can be used with the getter
 * {@link #getDef() getDef()}.
 * </p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractDefTester extends ExTeXLauncher {

  /**
   * The field {@code primitive} contains the name of the def.
   */
  private final String def;

  /**
   * Creates a new object.
   *
   * @param def the name of the primitive
   */
  public AbstractDefTester( String def ) {

    this.def = def;
  }

  /**
   * Test case checking that \\immediate leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testImmediate1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\immediate\\" + def + "\\aaa{}",
                  // --- log message ---
                  "You can't use the prefix `\\immediate' with the control " +
                      "sequence \\"
                      + def );
  }

  /**
   * Test case checking that a simple definition of a control sequence
   * without parameters works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testBasic1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\" + def + "\\aaa{AAA}" + "--\\aaa--\\end",
                  // --- output message ---
                  "--AAA--" + TERM );
  }

  /**
   * Test case checking that a simple definition of an active character
   * without parameters works
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testBasic2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\catcode`\\~=13 "
                      + "\\" + def + "~{AAA}" + "--~--\\end",
                  // --- output message ---
                  "--AAA--" + TERM );
  }

  /**
   * Test case checking that long is an accepted prefix.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLong1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\long\\" + def + "\\aaa{AAA}" + "--\\aaa" +
                      "--\\end",
                  // --- output message ---
                  "--AAA--" + TERM );
  }

  /**
   * Test case checking that along definition accepts a \par in the
   * replacement text
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testLong2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\long\\" + def + "\\aaa{AAA\\par BBB}"
                      + "--\\aaa--\\end",
                  // --- output message ---
                  "--AAA\n\nBBB--" + TERM );
  }

  /**
   * Test case checking that a single argument works for a single token
   * parameter
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testArguments1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#1{A#1A}"
                      + "--\\aaa 1--\\end",
                  // --- output message ---
                  "--A1A--" + TERM );
  }

  /**
   * Test case checking that a single argument works for a parameter block
   * with one token
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testArguments2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#1{A#1A}"
                      + "--\\aaa {1}--\\end",
                  // --- output message ---
                  "--A1A--" + TERM );
  }

  /**
   * Test case checking that a single argument works for a parameter block
   * with two tokens
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testArguments3() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#1{A#1A}"
                      + "--\\aaa {12}--\\end",
                  // --- output message ---
                  "--A12A--" + TERM );
  }

  /**
   * Test case checking that two arguments are parsed.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTwoArguments1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\a#1#2{--#1" +
                      "--#2--}"
                      + "\\a 2." + "\\end ",
                  // --- output channel ---
                  "--2--.--" + TERM );
  }

  /**
   * Test case checking that a single argument with delimiter works for a
   * parameter block with one token
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPattern1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#1.{--#1--}"
                      + "\\aaa 2." + "\\end ",
                  // --- output channel ---
                  "--2--" + TERM );
  }

  /**
   * Test case checking that a single argument with delimiter works for a
   * parameter block which contains the delimiter
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testPattern2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#1.{--#1--}"
                      + "\\aaa {2.1}." + "\\end ",
                  // --- output channel ---
                  "--2.1--" + TERM );
  }

  /**
   * Test case checking that an additional closing brace is reported as error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testBrace1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#1.{--#1--}"
                      + "\\aaa }.",
                  // --- output channel ---
                  "Argument of \\aaa has an extra }" );
  }

  /**
   * Test case checking that a double hash is consumed in the replacement text
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHashArgument1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\a{--##--}\\end",
                  // --- output channel ---
                  "" );
  }

  /**
   * Test case checking that macro param character in the replacement text
   * leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHashArgument2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\a{--##--}\\a ",
                  // --- output channel ---
                  "You can't use `macro parameter character #' in horizontal " +
                      "mode" );
  }

  /**
   * Test case checking that a final hash requires a left brace upon
   * invocation: failure case
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHash10() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + getDef() + "\\a#{xxx}"
                      + "\\a \\end",
                  // --- output channel ---
                  "Use of \\a doesn't match its definition" );
  }

  /**
   * Test case checking that a final hash requires a left brace upon
   * invocation: success case
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHash11() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + getDef() + "\\a#{xxx}"
                      + "\\a{} \\end",
                  // --- output channel ---
                  "xxx" + TERM );
  }

  /**
   * Test case checking that the macro parameters must be numbered consecutively
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHashError1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#2{--#1--}",
                  // --- output channel ---
                  "Parameters must be numbered consecutively" );
  }

  /**
   * Test case checking that a macro parameter character must be followed by
   * a number
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHashError2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\aaa#a{--#1--}",
                  // --- output channel ---
                  "Parameters must be numbered consecutively" );
  }

  /**
   * Test case checking that a closing brace after the control sequence token
   * leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testRight1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\" + def + "\\a}",
                  // --- output channel ---
                  "Missing { inserted" );
  }

  /**
   * Test case checking that an outer control sequence leads to an error in
   * the pattern of a macro
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOuter1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + DEFINE_HASH + "\\outer\\def\\x{x}\\" + def
                      + "\\a\\x{}",
                  // --- output channel ---
                  "Forbidden control sequence found while scanning definition" +
                      " of \\a" );
  }

  /**
   * Test case checking that an eof in the pattern of the definition of a
   * macro is recognized
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\" + def + "\\a",
                  // --- output channel ---
                  "File ended while scanning definition of \\a" );
  }

  /**
   * Test case checking that an eof in the replacement text of the definition
   * of a macro is recognized
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_BRACES + "\\" + def + "\\a{",
                  // --- output channel ---
                  "File ended while scanning text of \\a" );
  }

  /**
   * Getter for def.
   *
   * @return the def
   */
  public String getDef() {

    return this.def;
  }

}
