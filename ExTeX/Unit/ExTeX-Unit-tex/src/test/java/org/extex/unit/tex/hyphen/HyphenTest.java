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

package org.extex.unit.tex.hyphen;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \-}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class HyphenTest extends NoFlagsPrimitiveTester {


  public HyphenTest() {

    setPrimitive( "-" );
    setArguments( "" );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="\-">
   * Test case checking that {@code \-} ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void testDisc1() throws Exception {

    assertSuccess(//--- input code ---
                  "\\font\\f cmr10 \\f "
                      + "\\hsize=123pt "
                      + "abc\\-def"
                      + "\\end ",
                  //--- output channel ---
                  "abc--def" + TERM );
  }

}
