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

package org.extex.unit.tex.info;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \show}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ShowTest extends NoFlagsPrimitiveTester {


  public ShowTest() {

    setPrimitive( "show" );
    setArguments( "\\count" );
    setPrepare( "" );
    setOut( "> \\count=\\count.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a letter.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testL1() throws Exception {

    assertFailure(//--- input code ---
                  "\\show a" + "\\end",
                  //--- output channel ---
                  "> the letter a.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a letter.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testL2() throws Exception {

    assertFailure(//--- input code ---
                  "\\show A" + "\\end",
                  //--- output channel ---
                  "> the letter A.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a digit.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testD1() throws Exception {

    assertFailure(//--- input code ---
                  "\\show 1" + "\\end",
                  //--- output channel ---
                  "> the character 1.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a comma.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testX1() throws Exception {

    assertFailure(//--- input code ---
                  "\\show ," + "\\end",
                  //--- output channel ---
                  "> the character ,.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testB1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\show {" + "\\end",
                  //--- output channel ---
                  "> begin-group character {.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a brace.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testB2() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\show }" + "\\end",
                  //--- output channel ---
                  "> end-group character }.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a hash.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testH1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_HASH + "\\show #" + "\\end",
                  //--- output channel ---
                  "> macro parameter character #.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a dollar.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testM1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_MATH + "\\show $" + "\\end",
                  //--- output channel ---
                  "> math shift character $.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a underscore.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testM2() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_CATCODES + "\\show _" + "\\end",
                  //--- output channel ---
                  "> subscript character _.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a ampercent.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testA1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_CATCODES + "\\show &" + "\\end",
                  //--- output channel ---
                  "> alignment tab character &.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a caret-defined character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testTab2() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_CATCODES + "\\show ^^10" + "\\end",
                  //--- output channel ---
                  "> the character ^^P.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a caret.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testM3() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_CATCODES + "\\show ^" + "\\end",
                  //--- output channel ---
                  "> superscript character ^.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * an undefined control sequence.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testUndef() throws Exception {

    assertFailure(//--- input code ---
                  "\\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=undefined.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * {@code \relax}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertFailure(//--- input code ---
                  "\\show\\relax" + "\\end",
                  //--- output channel ---
                  "> \\relax=\\relax.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * {@code \relax}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertFailure(//--- input code ---
                  "\\catcode`\\~=13 \\let~\\def" + "\\show~" + "\\end",
                  //--- output channel ---
                  "> ~=\\def.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro1() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\def\\x{abc}\\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=macro:\n->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro2() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\def\\x#1{abc}\\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=macro:\n#1->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro3() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\def\\x#1-#2{abc}\\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=macro:\n#1-#2->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro4() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\long\\def\\x{abc}\\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=\\long macro:\n->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro5() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\outer\\def\\x{abc}\\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=\\outer macro:\n->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro6() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\long\\outer\\def\\x{abc}\\show\\x" + 
                      "\\end",
                  //--- output channel ---
                  "> \\x=\\long\\outer macro:\n->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a macro.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMacro7() throws Exception {

    assertFailure(//--- input code ---
                  DEFINE_BRACES + "\\outer\\long\\def\\x{abc}\\show\\x" + 
                      "\\end",
                  //--- output channel ---
                  "> \\x=\\long\\outer macro:\n->abc.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a defined character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testChardef1() throws Exception {

    assertFailure(//--- input code ---
                  "\\chardef\\x=123 \\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=\\char\"7B.\n" );
  }

  /**
   * <testcase primitive="\show">
   * Test case checking that {@code \show} works with
   * a defined math character.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMathchardef1() throws Exception {

    assertFailure(//--- input code ---
                  "\\mathchardef\\x=123 \\show\\x" + "\\end",
                  //--- output channel ---
                  "> \\x=\\mathchar\"7B.\n" );
  }

  //TODO implement more primitive specific test cases

}
