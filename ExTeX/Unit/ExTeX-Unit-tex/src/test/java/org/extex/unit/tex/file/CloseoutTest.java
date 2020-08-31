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

package org.extex.unit.tex.file;

import org.extex.test.NoFlagsButImmediatePrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \closeout}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CloseoutTest extends NoFlagsButImmediatePrimitiveTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( CloseoutTest.class );
  }


  public CloseoutTest() {

    super( "closeout", "1" );
  }

  /**
   * <testcase primitive="\closeout"> Test case checking that a
   * {@code \closeout} works on unopened file handles.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test0() throws Exception {

    assertSuccess(// --- input code ---
                  "\\closeout1\\end",
                  // --- output channel ---
                  "\n" );
  }

  /**
   * <testcase primitive="\closeout"> Test case checking that a
   * {@code \closeout} works on unopened file handles.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\immediate\\closeout1\\end",
                  // --- output channel ---
                  "" );
  }

}
