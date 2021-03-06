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

package org.extex.unit.tex;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \ignorespaces}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class IgnorespacesTest extends NoFlagsPrimitiveTester {

  /**
   * Method for running the tests standalone.
   *
   * @param args command line parameter
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( IgnorespacesTest.class );
  }


  public IgnorespacesTest() {

    setPrimitive( "ignorespaces" );
    setArguments( "" );
    setPrepare( "" );
  }

  /**
   * Test that {@code \ignorespaces} absorbs following spaces in
   * a group. The open group character does not consume following spaces.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess( DEFINE_BRACES + "x\\expandafter\\ignorespaces{    }x",
                   "xx" + TERM );
  }

  /**
   * Test that {@code \ignorespaces} absorbs following spaces if
   * let to an active character. The active character does not consume
   * following spaces.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess( "\\catcode`\\~=13 \\let~\\ignorespaces x~    x",
                   "xx" + TERM );
  }

  /**
   * Test that {@code \ignorespaces} absorbs following spaces if
   * used as last primitive in a macro (with arguments). The argument avoids
   * that the following spaces are consumed by the scanner.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test3() throws Exception {

    assertSuccess( DEFINE_BRACES + DEFINE_HASH
                       + "\\def\\x#1{x\\ignorespaces}\\x.    x",
                   "xx" + TERM );
  }

}
