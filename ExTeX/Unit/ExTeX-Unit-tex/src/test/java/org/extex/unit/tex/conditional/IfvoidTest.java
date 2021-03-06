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

package org.extex.unit.tex.conditional;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \ifvoid}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IfvoidTest extends ConditionalTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IfvoidTest.class );
  }


  public IfvoidTest() {

    super( "ifvoid", "0" );
  }

  /**
   * <testcase primitive="\ifvoid"> Test case checking that {@code \ifvoid}
   * on an undefined box selects the then branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\ifvoid42 a\\else b\\fi\\end",
                  // --- output channel ---
                  "a" + TERM );
  }

  /**
   * <testcase primitive="\ifvoid"> Test case checking that {@code \ifvoid}
   * on an undefined box selects nothing if the else branch is missing.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  "x\\ifvoid42 a\\fi x\\end",
                  // --- output channel ---
                  "xax" + TERM );
  }

  /**
   * <testcase primitive="\ifvoid"> Test case checking that {@code \ifvoid}
   * on an empty hbox selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess(// --- input code ---
                  "\\catcode`{=1 " + "\\catcode`}=2 " + "\\setbox42\\hbox{}"
                      + "\\ifvoid42 a\\else b\\fi\\end",
                  // --- output channel ---
                  "b" + TERM );
  }

  /**
   * <testcase primitive="\ifvoid"> Test case checking that {@code \ifvoid}
   * on an empty vbox selects the else branch.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test4() throws Exception {

    assertSuccess(// --- input code ---
                  "\\catcode`{=1 " + "\\catcode`}=2 " + "\\setbox42\\vbox{}"
                      + "\\ifvoid42 a\\else b\\fi\\end",
                  // --- output channel ---
                  "b" + TERM );
  }

}
