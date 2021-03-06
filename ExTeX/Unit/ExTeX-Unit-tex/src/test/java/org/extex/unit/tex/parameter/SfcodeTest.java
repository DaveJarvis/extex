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

package org.extex.unit.tex.parameter;

import org.extex.unit.tex.string.AbstractCharMapTester;
import org.junit.runner.JUnitCore;

/**
 * This is a test suite for the primitive {@code \sfcode}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class SfcodeTest extends AbstractCharMapTester {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( SfcodeTest.class );
  }


  public SfcodeTest() {

    super( "sfcode", "65", "999", "32767" );
  }

  /**
   * Get he message for a bad character code.
   *
   * @return the message
   */
  @Override
  protected String badCodeMessage() {

    return "Invalid code (-123), should be in the range 0..32767";
  }

}
