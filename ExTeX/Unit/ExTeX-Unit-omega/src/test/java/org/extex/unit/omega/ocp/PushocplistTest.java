/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp;

import org.extex.test.ExTeXLauncher;
import org.junit.Test;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \pushocplist}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class PushocplistTest extends ExTeXLauncher {

  /**
   * The command line interface.
   *
   * @param args the command line arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( PushocplistTest.class );
  }


  public PushocplistTest() {

    setConfig( "omega-test" );
  }

  /**
   * <testcase primitive="\pushocp"> Test case checking that {@code \pushocp}
   * needs an active ocp list.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testError1() throws Exception {

    assertFailure(// --- input code ---
                  "\\pushocplist \\relax",
                  // --- output channel ---
                  "Missing ocp list identifier" );
  }

  // TODO implement more primitive specific test cases
}
