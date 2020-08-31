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

package org.extex.unit.tex.math.spacing;

import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \mkern}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MkernTest extends AbstractMathTester {


  public MkernTest() {

    setPrimitive( "mkern" );
    setArguments( "1mu" );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="\mkern">
   * Test case checking that ...
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void test1() throws Exception {

    assertSuccess( showNodesProperties(),
                   //--- input code ---
                   AbstractMathTester.DEFINE_MATH_FONTS + DEFINE_MATH
                       + "$a\\mkern12mu b$\\end",
                   //--- output message ---
                   "\\vbox(6.94444pt+0.0pt)x3000.0pt\n"
                       + ".\\hbox(6.94444pt+0.0pt)x3000.0pt\n"
                       + "..a\n"
                       + "..\\kern6.66667pt\n"
                       + "..b\n" );
  }

  //TODO implement more primitive specific test cases
}
