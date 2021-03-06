/*
 * Copyright (C) 2007-2010 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.api.exception;

import org.extex.core.UnicodeChar;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the exception.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class InvalidCharacterScannerExceptionTest {

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    JUnitCore.runClasses( InvalidCharacterScannerExceptionTest.class );
  }

  /**
   * Test method for
   * {@link org.extex.scanner.api.exception.CatcodeWrongLengthException#getLocalizedMessage()}
   * .
   */
  @Test
  public final void testGetLocalizedMessage1() {

    Locale.setDefault( Locale.ENGLISH );
    assertEquals( "Invalid character x recognized",
                  new InvalidCharacterScannerException( UnicodeChar.get( 'x' ) )
                      .getLocalizedMessage() );
  }

  /**
   * Test method for
   * {@link org.extex.scanner.api.exception.CatcodeWrongLengthException#getLocalizedMessage()}
   * .
   */
  @Test
  public final void testGetLocalizedMessage2() {

    Locale.setDefault( Locale.GERMAN );
    assertEquals( "Ung\u00fcltiges Zeichen x erkannt",
                  new InvalidCharacterScannerException( UnicodeChar.get( 'x' ) )
                      .getLocalizedMessage() );
  }

}
