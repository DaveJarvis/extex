/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.tex.hyphen;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \hyphenation}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class HyphenationTest extends NoFlagsPrimitiveTester {


  public HyphenationTest() {

    setPrimitive( "hyphenation" );
    setArguments( "{}" );
    setPrepare( DEFINE_BRACES );
  }

  /**
   * <testcase primitive="\hyphenation">
   * Test case checking that {@code \hyphenation} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void test1() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES
                      + "\\hsize=15pt "
                      + "\\font\\f cmr10 \\f"
                      + "\\hyphenation{abc-def ghi-jkl}"
                      + "x abcdef\\end ",
                  //--- output channel ---
                  "x\n abc-\n--def" + TERM );
  }

  /**
   * <testcase primitive="\hyphenation">
   * Test case checking that {@code \hyphenation} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void test2() throws Exception {

    assertSuccess(//--- input code ---
                  DEFINE_BRACES
                      + "\\hsize=15pt "
                      + "\\font\\f cmr10 \\f"
                      + "\\hyphenation{abc-def auf-flug}"
                      + "x aufflug\\end ",
                  //--- output channel ---
                  "x\n auf-\n--flug" + TERM );
  }
}
