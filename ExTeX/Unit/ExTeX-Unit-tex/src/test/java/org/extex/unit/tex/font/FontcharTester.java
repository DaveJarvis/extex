/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.font;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is an abstract test suite for the primitives {@code \hyphenchar} and
 * {@code \skewchar}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class FontcharTester extends NoFlagsPrimitiveTester {

  /**
   * The field {@code def} contains the default value.
   */
  private String def = "";

  public FontcharTester() {
    setArguments( "\\nullfont=123 " );
  }

  public void setDef( final String def ) {
    this.def = def;
  }

  /**
   * Test case checking that an end of file leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "\\" + getPrimitive() + " ",
                  // --- log message ---
                  "Unexpected end of file while processing \\" + getPrimitive() + "" );
  }

  /**
   * Test case checking that an end of file leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof2() throws Exception {

    assertFailure(// --- input code ---
                  "\\" + getPrimitive() + "\\nullfont ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * Test case checking that the primitive needs a second argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testEof3() throws Exception {

    assertFailure(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x",
                  // --- output channel ---
                  "Missing number, treated as zero" );
  }

  /**
   * Test case checking that the primitive needs a second argument after an =
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testEof4() throws Exception {

    assertFailure(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x=",
                  // --- output channel ---
                  "Missing number, treated as zero" );
  }

  /**
   * Test case checking that an end of file leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof5() throws Exception {

    assertFailure(// --- input code ---
                  "\\count0=\\" + getPrimitive(),
                  // --- log message ---
                  "Unexpected end of file while processing \\" + getPrimitive() );
  }

  /**
   * Test case checking that a missing font identifier leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMissing1() throws Exception {

    assertFailure(// --- input code ---
                  "\\" + getPrimitive() + " x",
                  // --- log message ---
                  "Missing font identifier" );
  }

  /**
   * Test case checking that a missing font identifier leads to an error
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMissing2() throws Exception {

    assertFailure(// --- input code ---
                  "\\" + getPrimitive() + " \\x",
                  // --- log message ---
                  "Undefined control sequence \\x" );
  }

  /**
   * Test case checking that a correct value is produced when the hyphen 
   * char is not preset for \nullfont
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHyphencharNullfont0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\the\\" + getPrimitive() + "\\nullfont" + "\\end ",
                  // --- output channel ---
                  def + TERM );
  }

  /**
   * Test case checking that a correct value is produced when the hyphen 
   * char is set to 123 for \nullfont
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHyphencharNullfont1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\" + getPrimitive() + "\\nullfont =123 \\relax" + "\\the\\"
                      + getPrimitive() + "\\nullfont" + "\\end ",
                  // --- output channel ---
                  "123" + TERM );
  }

  /**
   * Test case checking that a correct value is produced when the hyphen char
   * is set to undefined for \nullfont
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHyphencharNullfont2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\" + getPrimitive() + "\\nullfont =-1 \\relax" + "\\the\\"
                      + getPrimitive() + "\\nullfont" + "\\end ",
                  // --- output channel ---
                  "-1" + TERM );
  }

  /**
   * Test case checking that \hyphenchar is countconvertible for \nullfont
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testHyphencharNullfont3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\" + getPrimitive() + "\\nullfont =123 \\relax" + 
                      "\\count1=\\"
                      + getPrimitive() + "\\nullfont" + "\\the\\count 1"
                      + "\\end ",
                  // --- output channel ---
                  "123" + TERM );
  }

  /**
   * Test case checking that a correct value is produced when the hyphen char
   * is not preset for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphenchar0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\x=cmtt12" + "\\the\\" + getPrimitive() + "\\x" + 
                      "\\end ",
                  // --- output channel ---
                  def + TERM );
  }

  /**
   * Test case checking that a correct value is produced when the hyphen char
   * is set to 123 for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphenchar1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x =123 " +
                      "\\relax"
                      + "\\the\\" + getPrimitive() + "\\x" + "\\end ",
                  // --- output channel ---
                  "123" + TERM );
  }

  /**
   * Test case checking that a correct value is produced when the hyphen char
   * is set to undefined for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphenchar2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x =-1 \\relax"
                      + "\\the\\" + getPrimitive() + "\\x" + "\\end ",
                  // --- output channel ---
                  "-1" + TERM );
  }

  /**
   * Test case checking that \hyphenchar is countconvertible for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphenchar3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x =123 " +
                      "\\relax"
                      + "\\count1=\\" + getPrimitive() + "\\x" + "\\the" +
                      "\\count 1"
                      + "\\end ",
                  // --- output channel ---
                  "123" + TERM );
  }

  /**
   * Test case checking that \hyphenchar is countconvertible for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphenchar4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x =-123 " +
                      "\\relax"
                      + "\\count1=\\" + getPrimitive() + "\\x" + "\\the" +
                      "\\count 1"
                      + "\\end ",
                  // --- output channel ---
                  "-1" + TERM );
  }

  /**
   * Test case checking that \hyphenchar is theable for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphencharThe0() throws Exception {

    assertFailure(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x =-123 " +
                      "\\relax"
                      + "\\the\\" + getPrimitive() + "",
                  // --- output channel ---
                  "Unexpected end of file while processing \\" + getPrimitive() + "" );
  }

  /**
   * Test case checking that \hyphenchar is theable for a loaded \font
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testHyphencharThe1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\font\\x=cmtt12" + "\\" + getPrimitive() + "\\x =-123 " +
                      "\\relax"
                      + "\\the\\" + getPrimitive() + "\\x" + "\\end ",
                  // --- output channel ---
                  "-1" + TERM );
  }

}
