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

package org.extex.unit.tex.typesetter.spacing;

import org.extex.test.NoFlagsPrimitiveTester;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \kern}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class KernTest extends NoFlagsPrimitiveTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( KernTest.class );
  }


  public KernTest() {

    setPrimitive( "kern" );
    setArguments( "1pt" );
    setPrepare( "" );
  }

  /**
   * Test case checking that a missing dimen leads to an error.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testEof1() throws Exception {

    assertFailure(// --- input code ---
                  "x\\kern ",
                  // --- log message ---
                  "Missing number, treated as zero" );
  }

  /**
   * Test case checking that a correct value is produced.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testKern1() throws Exception {

    assertSuccess( showNodesProperties(),
                   // --- input code ---
                   "x\\kern 123ptx" + "\\end ",
                   // --- output channel ---
                   "\\vbox(8.0pt+0.0pt)x3000.0pt\n"
                       + ".\\hbox(8.0pt+0.0pt)x3000.0pt\n" + "..x\n"
                       + "..\\kern123.0pt\n" + "..x\n" );
  }

  // TODO implement primitive specific test cases

}
