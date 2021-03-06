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

package org.extex.unit.tex.math.delimiter;

import org.extex.unit.tex.math.AbstractMathTester;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This is a test suite for the primitive {@code \delimiter}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class DelimiterTest extends AbstractMathTester {

  public DelimiterTest() {
    setPrimitive( "delimiter" );
    setArguments( ". " );
    setPrepare( "" );
  }

  /**
   * <testcase primitive="delimiter"> Test case checking that \delimiter
   * produces an adequate node construction.
   *
   * @throws Exception in case of an error
   */
  @Test
  @Ignore
  public void test1() throws Exception {

    assertSuccess( showNodesProperties(),
                   // --- input code ---
                   DEFINE_BRACES + DEFINE_MATH + DEFINE_MATH_FONTS
                       + "$\\delimiter\"0161161 $\\end",
                   // --- output channel ---
                   "\\vbox(4.30554pt+0.0pt)x5.28589pt\n"
                       + ".\\hbox(4.30554pt+0.0pt)x5.28589pt\n" + "..\\mathon\n"
                       + "..a\n" + "..\\mathoff\n" );
  }

  // TODO implement more primitive specific test cases

}
