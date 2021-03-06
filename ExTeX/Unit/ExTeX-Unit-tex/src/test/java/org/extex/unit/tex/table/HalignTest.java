/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.table;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;

/**
 * This is a test suite for the primitive {@code \halign}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class HalignTest extends NoFlagsPrimitiveTester {

  public HalignTest() {

    setPrimitive( "halign" );
    setArguments( "{#\\cr}" );
    setPrepare( DEFINE_HASH );
  }

  /**
   * Test case checking that an outer macro in the preamble leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\halign",
                  // --- log message ---
                  "Unexpected end of file while processing \\halign" );
  }

  /**
   * Test case checking that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore("gene: expected result is missing")
  public void testHalign1() throws Exception {

    Properties properties = (Properties) System.getProperties().clone();
    properties.setProperty( "extex.output", "dump" );

    assertSuccess( properties,
                   // --- input code ---
                   DEFINE_CATCODES + "\\halign{a#b&c#d\\cr1&2\\cr}" + "\\end ",
                   // --- output channel ---
                   "???" ); // TODO gene: check
  }

  /**
   * Test case checking that an outer macro in the preamble leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMissingBrace1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\halign a" + "\\end ",
                  // --- log message ---
                  "Missing `{' inserted" );
  }

  /**
   * Test case checking that a missing sharp in the preamble leads to an
   * error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMissingSharp1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\halign{a&#\\cr}" + "\\end ",
                  // --- log message ---
                  "Missing # inserted in alignment preamble" );
  }

  /**
   * Test case checking that a missing sharp in the preamble leads to an
   * error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testMissingSharp2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\halign{#a&abc\\cr}" + "\\end ",
                  // --- log message ---
                  "Missing # inserted in alignment preamble" );
  }

  /**
   * Test case checking that an outer macro in the preamble leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOuter1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\outer\\def\\x{}" + "" + "\\halign{\\x" +
                      "#&\\cr}"
                      + "\\end ",
                  // --- log message ---
                  "Forbidden control sequence found while scanning preamble " +
                      "of " +
                      "\\halign" );
  }

  /**
   * Test case checking that an outer macro in the preamble leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOuter2() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\outer\\def\\x{}" + "" + "\\halign{a" +
                      "#\\x&\\cr}"
                      + "\\end ",
                  // --- log message ---
                  "Forbidden control sequence found while scanning preamble " +
                      "of " +
                      "\\halign" );
  }

  /**
   * Test case checking that an outer macro in the preamble leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testOuter3() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\outer\\def\\x{}" + ""
                      + "\\halign{a#b&a#\\x&\\cr}" + "\\end ",
                  // --- log message ---
                  "Forbidden control sequence found while scanning preamble " +
                      "of " +
                      "\\halign" );
  }

  /**
   * Test case checking that several sharps in the preamble leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testSharp1() throws Exception {

    assertFailure(// --- input code ---
                  DEFINE_CATCODES + "\\halign{#a#\\cr}" + "\\end ",
                  // --- log message ---
                  "Only one # is allowed per tab" );
  }

}
