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

package org.extex.unit.etex.group;

import org.extex.test.count.AbstractReadonlyCountRegisterTester;
import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \currentgrouptype}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CurrentgrouptypeTest extends AbstractReadonlyCountRegisterTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( CurrentgrouptypeTest.class );
  }


  public CurrentgrouptypeTest() {

    super( "currentgrouptype", "0" );
    setConfig( "etex-test" );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} outside any group
   * returns 0.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    assertSuccess(//--- input code ---
                  "\\the\\currentgrouptype \\end",
                  //--- log message ---
                  "0" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a group
   * returns 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES + "{\\the\\currentgrouptype}\\end",
                  //--- log message ---
                  "1" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a group
   * returns 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES + "\\hbox{\\the\\currentgrouptype}\\end",
                  //--- log message ---
                  "2" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a group
   * returns 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES + "\\hbox to 12pt{\\the\\currentgrouptype" +
                      "}\\end",
                  //--- log message ---
                  "3" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a vbox
   * in a group returns 4.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES
                      + "\\setbox0=\\vbox{\\global\\count0=\\currentgrouptype" +
                      "}\\the\\count0\\end",
                  //--- log message ---
                  "4" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a group
   * returns 1.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test5() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES + "\\vtop{\\the\\currentgrouptype}\\end",
                  //--- log message ---
                  "5\n\n" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a vcenter
   * in a group returns 12.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test12() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES
                      + DEFINE_MATH
                      + AbstractMathTester.DEFINE_MATH_FONTS
                      + "\\hbox{$\\vcenter{\\global\\count0" +
                      "=\\currentgrouptype}$}"
                      + "\\the\\count0\\end",
                  //--- log message ---
                  "\n12" + TERM );
  }

  /**
   * <testcase primitive="\currentgrouptype">
   * Test case checking that {@code \currentgrouptype} inside a group
   * in a group returns 2.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test14() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES
                      + "\\begingroup\\the\\currentgrouptype\\endgroup\\end",
                  //--- log message ---
                  "14" + TERM );
  }

  //TODO implement more primitive specific test cases
}
