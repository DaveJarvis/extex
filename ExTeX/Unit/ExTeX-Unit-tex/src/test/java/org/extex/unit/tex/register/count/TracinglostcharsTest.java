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

package org.extex.unit.tex.register.count;

import org.extex.test.count.AbstractCountRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \tracinglostchars}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class TracinglostcharsTest extends AbstractCountRegisterTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( TracinglostcharsTest.class );
  }


  public TracinglostcharsTest() {

    super( "tracinglostchars", "", "0" );
  }

  /**
   * <testcase primitive="\tracinglostchars"> Test case checking that without
   * {@code \tracinglostchars} a lost character is silently dropped.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\nullfont a \\end",
                  // --- output stream ---
                  "" );
  }

  /**
   * <testcase primitive="\tracinglostchars"> Test case checking that
   * {@code \tracinglostchars} on an non-existing character does produce an
   * error message.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test01() throws Exception {

    assertOutput(// --- input code ---
                 "\\nullfont\\tracinglostchars=1 a \\end",
                 // --- log message ---
                 "Missing character: There is no a in font nullfont!\n",
                 // --- output stream ---
                 "" );
  }

  /**
   * <testcase primitive="\tracinglostchars"> Test case checking that
   * {@code \tracinglostchars} on a non-existing character appearing later
   * does produce a message.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test02() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\nullfont\\tracinglostchars=1\\hbox{a}\\end",
                 // --- log message ---
                 "Missing character: There is no a in font nullfont!\n",
                 // --- output stream ---
                 TERM );
  }

  /**
   * <testcase primitive="\tracinglostchars"> Test case checking that
   * {@code \tracinglostchars} on an existing character does not produce a
   * message.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertOutput(// --- input code ---
                 "\\tracinglostchars=1 a \\end",
                 // --- log message ---
                 "",
                 // --- output stream ---
                 "a" + TERM );
  }

  /**
   * <testcase primitive="\tracinglostchars"> Test case checking that
   * {@code \tracinglostchars} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\tracinglostchars=1\\hbox{a}\\end",
                 // --- log message ---
                 "",
                 // --- output stream ---
                 "a" + TERM );
  }

  // TODO implement more primitive specific test cases (lost chars in math
  // mode, etc)
}
