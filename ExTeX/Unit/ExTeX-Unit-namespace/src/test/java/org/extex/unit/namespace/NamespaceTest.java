/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.namespace;

import org.extex.test.NoFlagsButGlobalPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \namespace}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class NamespaceTest extends NoFlagsButGlobalPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( NamespaceTest.class );
  }


  public NamespaceTest() {

    setPrimitive( "namespace" );
    setArguments( "{a}" );
    setPrepare( DEFINE_BRACES );
    setConfig( "namespace-test" );
  }

  /**
   * <testcase primitive="\namespace"> Test case checking that
   * {@code \namespace} is initially empty.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(// --- input code ---
                  ":\\the\\namespace:" + "\\end ",
                  // --- output channel ---
                  "::" + TERM );
  }

  /**
   * <testcase primitive="\namespace"> Test case checking that
   * {@code \namespace} can be set and read.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\namespace{TeX}" + ":\\the\\namespace:"
                      + "\\end ",
                  // --- output channel ---
                  ":TeX:" + TERM );
  }

  /**
   * <testcase primitive="\namespace"> Test case checking that
   * {@code \namespace} let assignment in different name spaces are
   * independent.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test10() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\let\\x a" + "\\namespace{TeX}" + 
                      "\\let\\x b"
                      + ".\\x." + "\\namespace{abc}" + "\\the\\namespace:"
                      + ".\\x." + "\\end ",
                  // --- output channel ---
                  ".b.abc:.a." + TERM );
  }

  /**
   * <testcase primitive="\namespace"> Test case checking that
   * {@code \namespace} interacts with groups.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test20() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\namespace{A}"
                      + "\\begingroup\\global\\namespace{TeX}\\endgroup"
                      + ":\\the\\namespace:" + "\\end ",
                  // --- output channel ---
                  ":TeX:" + TERM );
  }

  // TODO implement the primitive specific test cases
}
