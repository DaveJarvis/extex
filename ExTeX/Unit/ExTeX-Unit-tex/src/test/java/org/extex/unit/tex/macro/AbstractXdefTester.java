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

package org.extex.unit.tex.macro;

import org.junit.Test;

/**
 * This is a test suite for \xdef-like primitives.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public abstract class AbstractXdefTester extends AbstractDefTester {

  /**
   * Creates a new object.
   *
   * @param def the name of the primitive
   */
  public AbstractXdefTester( String def ) {

    super( def );
  }

  /**
   * Test case checking that macros (without arguments) are expanded at
   * definition time
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand1() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_BRACES + "\\def\\a{A}" + "\\" + getDef() + "\\aaa{X" +
                      "\\a X}"
                      + "\\def\\a{B}" + "--\\aaa--\\end",
                  // --- output message ---
                  "--XAX--" + TERM );
  }

  /**
   * Test case checking that macros (with arguments) are expanded at
   * definition time
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand2() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\def\\a#1{-#1-}" + "\\" + getDef()
                      + "\\aaa{X\\a9X}" + "\\def\\a{B}" + "--\\aaa--\\end",
                  // --- output message ---
                  "--X-9-X--" + TERM );
  }

  /**
   * Test case checking that macros are deeply expanded at definition time
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testExpand3() throws Exception {

    assertSuccess(// --- input code ---
                  DEFINE_CATCODES + "\\def\\a#1{-#1+\\b{#1}-}" + "\\def\\b#1" +
                      "{>#1<}"
                      + "\\" + getDef() + "\\aaa{X\\a9X}" + "\\def\\a{B}"
                      + "--\\aaa--\\end",
                  // --- output message ---
                  "--X-9+>9<-X--" + TERM );
  }

  /**
   * Test case checking that \noexpand prevent expansion of the
   * following token
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testNoexpand1() throws Exception {

    assertOutput(// --- input code ---
                 DEFINE_BRACES + "\\def\\a{A}" + "\\" + getDef()
                     + "\\aaa{X\\noexpand\\a X}" + "\\show\\aaa\\end",
                 // --- error message ---
                 "> \\aaa=macro:\n" + "->X\\aX.\n",
                 // --- output message ---
                 "" );
  }

}
