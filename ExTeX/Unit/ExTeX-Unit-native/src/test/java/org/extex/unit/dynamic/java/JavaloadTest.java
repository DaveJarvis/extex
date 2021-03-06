/*
 * Copyright (C) 2005-2006 The ExTeX Group and individual authors listed below
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

package org.extex.unit.dynamic.java;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * This is a test suite for the primitive {@code \javaload}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class JavaloadTest extends NoFlagsPrimitiveTester {

  /**
   * The constant {@code SENSOR} contains the name of the sensor class.
   */
  private static final String SENSOR = JavaloadSensor.class.getName();


  public JavaloadTest() {

    setPrimitive( "javaload" );
    setArguments( "{" + SENSOR + "}" );
    setPrepare( "" );
    setConfig( "native-test" );
  }

  /**
   * <testcase primitive="\javaload"> Test case checking that
   * {@code \javaload} needs an argument.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(
        // --- input code ---
        DEFINE_BRACES + "\\javaload",
        // --- log message ---
        "File ended while scanning text of \\javaload" );
  }

  /**
   * <testcase primitive="\javaload"> Test case checking that
   * {@code \javaload} invokes the sensor class.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError2() throws Exception {

    assertFailure(
        // --- input code ---
        DEFINE_BRACES + "\\javaload" + "{" + getClass().getName() + "}"
            + " \\end",
        // --- log message ---
        "The class org.extex.unit.dynamic.java.JavaloadTest does not " +
            "implement the\n"
            +
            "required interface org.extex.unit.dynamic.java.Loadable." );
  }

  /**
   * <testcase primitive="\javaload"> Test case checking that
   * {@code \javaload} invokes the sensor class.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void test1() throws Exception {

    assertSuccess(
        // --- input code ---
        DEFINE_BRACES + "\\javaload" + "{" + SENSOR + "}" + " \\end",
        // --- log message ---
        "" );
    assertTrue( JavaloadSensor.isSensed() );
  }

}
