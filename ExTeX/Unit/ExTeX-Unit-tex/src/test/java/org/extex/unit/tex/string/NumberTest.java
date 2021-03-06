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

package org.extex.unit.tex.string;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \number}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class NumberTest extends NoFlagsPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( NumberTest.class );
  }


  public NumberTest() {

    setPrimitive( "number" );
    setArguments( "\\count42" );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="\number"> Test case checking that {@code \number}
   * on a count with the value 2 gives {@code 2}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount1() throws Exception {

    assertSuccess( "\\count0=2 \\number\\count0 \\end",

                   "2" + TERM );
  }

  /**
   * <testcase primitive="\number"> Test case checking that {@code \number}
   * on a count with the value 32 gives {@code 32}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount2() throws Exception {

    assertSuccess( "\\count0=32 \\number\\count0 \\end",

                   "32" + TERM );
  }

  /**
   * <testcase primitive="\number"> Test case checking that {@code \number}
   * on a count with the value -2 gives {@code -2}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCount3() throws Exception {

    assertSuccess( "\\count0=-2 \\number\\count0 \\end",

                   "-2" + TERM );
  }

  /**
   * <testcase primitive="\number"> Test case checking that {@code \number}
   * is expandable.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testConvert1() throws Exception {

    assertSuccess(
        "\\count0=-2 \\count1=\\number\\count0\\relax\\the\\count1\\end",

        "-2" + TERM );
  }

}
