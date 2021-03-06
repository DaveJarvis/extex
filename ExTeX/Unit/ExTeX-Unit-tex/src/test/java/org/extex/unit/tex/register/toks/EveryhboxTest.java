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

package org.extex.unit.tex.register.toks;

import org.extex.test.toks.AbstractToksRegisterTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \everyhbox}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class EveryhboxTest extends AbstractToksRegisterTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( EveryhboxTest.class );
  }


  public EveryhboxTest() {

    super( "everyhbox", "", "" );
  }

  /**
   * <testcase primitive="\everyhbox"> Test case checking that a hbox
   * containing "abc" in font cmtt12 has the width 37.05002pt where "123" is
   * added to the box by an {@code \everyhbox}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testErr1() throws Exception {

    assertFailure(// --- input code ---
                  "\\catcode`{=1 " + "\\catcode`}=2 " + "\\everyhbox123" + 
                      "\\end",
                  // --- log message ---
                  "Missing `{' inserted" );
  }

  /**
   * <testcase primitive="\everyhbox"> Test case checking that a hbox
   * containing "abc" in font cmtt12 has the width 37.05002pt where "123" is
   * added to the box by an {@code \everyhbox}.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEveryHbox1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\catcode`{=1" + "\\catcode`}=2" + "\\relax"
                      + "\\font\\fnt cmtt12 \\fnt" + "\\everyhbox{123}"
                      + "\\setbox1=\\hbox{abc} " + "\\the\\wd1 " + "\\end",
                  // --- output channel ---
                  "37.05002pt" + TERM ); // checked with TeX
  }

  /**
   * <testcase primitive="\everyhbox"> Test case checking that a hbox
   * containing "abc" in font cmtt12 has the width 55.57503pt where "123" is
   * added to the box by an \everyhbox and "..." is prepended by
   * \afterassignment.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEveryHboxAfterassignment1() throws Exception {

    assertSuccess(// --- input code ---
                  "\\catcode`{=1" + "\\catcode`}=2" + "\\relax"
                      + "\\font\\fnt cmtt12 \\fnt" + "\\everyhbox{123}"
                      + "\\def\\x{...}" + "\\afterassignment\\x"
                      + "\\setbox1=\\hbox{abc} " + "\\the\\wd1 " + "\\end",
                  // --- output channel ---
                  "55.57503pt" + TERM ); // checked with TeX
  }

  // TODO implement the primitive specific test cases
}
