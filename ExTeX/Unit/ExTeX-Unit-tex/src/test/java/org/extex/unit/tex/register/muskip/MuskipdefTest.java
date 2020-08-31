/*
 * Copyright (C) 2005 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.register.muskip;

import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \muskipdef}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MuskipdefTest extends AbstractMuskipRegisterTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( MuskipdefTest.class );
  }


  public MuskipdefTest() {

    super( "x", "", "0.0mu", "\\muskipdef\\x=42 " );
  }

  /**
   * <testcase primitive="\muskipdef"> Test case checking that
   * {@code \muskipdef} creates a muskip assignable control sequence which
   * is equivalent to the {@code \muskip}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(
        // --- input code ---
        "\\muskipdef\\x=42 " + "\\muskip42=123mu "
            + "\\the\\muskip42 \\end",
        // --- output channel ---
        "123.0mu" + TERM );
  }

  /**
   * <testcase primitive="\muskipdef"> Test case checking that
   * {@code \muskipdef} respects a group.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGlobal1() throws Exception {

    assertFailure(// --- input code ---
                  "\\begingroup\\muskipdef\\x=42 \\endgroup" + "\\the\\x \\end",
                  // --- error channel ---
                  "Undefined control sequence \\x" );
  }

  /**
   * <testcase primitive="\muskipdef"> Test case checking that
   * {@code \muskipdef} respects a group.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testGlobal2() throws Exception {

    assertSuccess(// --- input code ---
                  "\\begingroup\\global\\muskipdef\\x=42 \\endgroup"
                      + "\\the\\x \\end",
                  // --- output channel ---
                  "0.0mu" + TERM );
  }

}
