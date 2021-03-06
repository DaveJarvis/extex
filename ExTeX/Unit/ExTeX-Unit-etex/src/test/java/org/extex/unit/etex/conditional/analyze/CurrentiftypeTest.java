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

package org.extex.unit.etex.conditional.analyze;

import org.extex.test.count.AbstractReadonlyCountRegisterTester;
import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \currentiftype}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CurrentiftypeTest extends AbstractReadonlyCountRegisterTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( CurrentiftypeTest.class );
  }


  public CurrentiftypeTest() {

    super( "currentiftype", "0" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 1 in an \if.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(//--- input code ---
                  "\\if AA\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "1" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 2 in an \ifcat.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifcat AA\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "2" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 3 in an \ifnum.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifnum 1=1 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "3" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 4 in an \ifdimen.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifdim 1sp=1sp \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "4" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 5 in an \ifodd.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifodd3 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "5" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 6 in an \ifvmode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test6() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifvmode \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "6" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 8 in an \ifmmode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test8() throws Exception {

    assertOutput(
        //--- input code ---
        AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_CATCODES
            + "$\\ifmmode \\showthe\\currentiftype \\fi$\\end",
        "> 8.\n",
        //--- log message ---
        "" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 10 in an \ifvoid.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test10() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifvoid0 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "10" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 11 in an \ifhbox.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test11() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifhbox1 \\else \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "11" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 12 in an \ifvbox.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test12() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifvbox1 \\else \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "12" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 13 in an \ifx.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test13() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifx AA \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "13" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 14 in an &5c;
   * unless\ifeof.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test14() throws Exception {

    assertSuccess(//--- input code ---
                  "\\nonstopmode"
                      + "\\ifeof1 \\the\\currentiftype \\else\\the" +
                      "\\currentiftype \\fi"
                      + "\\end",
                  //--- log message ---
                  "14" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 15 in an \iftrue.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test15() throws Exception {

    assertSuccess(//--- input code ---
                  "\\iftrue\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "15" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 16 in an \iffalse.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test16() throws Exception {

    assertSuccess(//--- input code ---
                  "\\iffalse\\else\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "16" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 17 in an \ifcase.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test17() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifcase0 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "17" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 19 in an \ifcsname.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test19() throws Exception {

    assertSuccess(//--- input code ---
                  "\\ifcsname relax\\endcsname \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "19" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is 20 in an \iffontchar.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test20() throws Exception {

    assertSuccess(
        //--- input code ---
        "\\iffontchar\\nullfont`\\a \\else\\the\\currentiftype \\fi\\end",
        //--- log message ---
        "20" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -1 in an &5c;unless\if.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg1() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\if AA\\else\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-1" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -2 in an &5c;
   * unless\ifcat.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg2() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifcat AA\\else\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-2" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -3 in an &5c;
   * unless\ifnum.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg3() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifnum 1=2 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-3" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -4 in an &5c;
   * unless\ifdim.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg4() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifdim 1sp=2sp \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-4" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -5 in an &5c;
   * unless\ifodd.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg5() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifodd2 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-5" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -6 in an &5c;
   * unless\ifvmode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg6() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifvmode \\else\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-6" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -8 in an &5c;
   * unless\ifmmode.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg8() throws Exception {

    assertOutput(
        //--- input code ---
        AbstractMathTester.DEFINE_MATH_FONTS
            + DEFINE_CATCODES
            + "$\\unless\\ifmmode \\else\\showthe\\currentiftype " +
            "\\fi$\\end",
        //--- log message ---
        "> -8.\n", "" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -10 in an &5c;
   * unless\ifvoid.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg10() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifvoid0 \\else\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-10" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -11 in an &5c;unless
   * \ifhbox.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg11() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifhbox1 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-11" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -12 in an &5c;
   * unless\ifvbox.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg12() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifvbox1 \\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-12" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -13 in an &5c;
   * unless\ifx.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg13() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\ifx AA \\else\\the\\currentiftype \\fi\\end",
                  //--- log message ---
                  "-13" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -14 in an &5c;
   * unless\ifeof.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg14() throws Exception {

    assertSuccess(//--- input code ---
                  "\\nonstopmode"
                      + "\\unless\\ifeof1 \\the\\currentiftype " +
                      "\\else\\the\\currentiftype \\fi"
                      + "\\end",
                  //--- log message ---
                  "-14" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -15 in an &5c;
   * unless\iftrue.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg15() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\iftrue\\else\\the\\currentiftype \\fi" + "\\end",
                  //--- log message ---
                  "-15" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -16 in an &5c;
   * unless\iffalse.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg16() throws Exception {

    assertSuccess(//--- input code ---
                  "\\unless\\iffalse\\the\\currentiftype \\fi" + "\\end",
                  //--- log message ---
                  "-16" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -19 in an &5c;
   * unless\ifcsname.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg19() throws Exception {

    assertSuccess(
        //--- input code ---
        "\\unless\\ifcsname aaa\\endcsname \\the\\currentiftype \\fi"
            + "\\end",
        //--- log message ---
        "-19" + TERM );
  }

  /**
   * <testcase primitive="\currentiftype">
   * Test case checking that {@code \currentiftype} is -20 in an &5c;
   * unless\iffontchar.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNeg20() throws Exception {

    assertSuccess(
        //--- input code ---
        "\\unless\\iffontchar\\nullfont`\\a \\the\\currentiftype \\fi"
            + "\\end",
        //--- log message ---
        "-20" + TERM );
  }

}
