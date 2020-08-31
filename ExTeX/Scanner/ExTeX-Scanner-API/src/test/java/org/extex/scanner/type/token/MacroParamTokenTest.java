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

package org.extex.scanner.type.token;

import org.extex.core.UnicodeChar;
import org.extex.scanner.type.Catcode;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.*;

/**
 * Tests for the macro parameter tokens.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class MacroParamTokenTest extends TokenTester {

  /**
   * The field {@code t} contains the reference token.
   */
  private static final Token t = new MacroParamToken( UnicodeChar.get( 'x' ) );

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    (new JUnitCore()).run( LeftBraceTokenTest.class );
  }


  public MacroParamTokenTest() {

    setToken( t );
    setCatcode( Catcode.MACROPARAM );
    setText( "x" );
    setStr( "macro parameter character x" );
  }

  @Test
  @Override
  public void testEq10() {

    assertTrue( t.eq( Catcode.MACROPARAM, 'x' ) );
  }

  @Test
  @Override
  public void testEq11() {

    assertFalse( t.eq( Catcode.LETTER, 'x' ) );
  }

  @Test
  public void testEqChar0() {

    assertTrue( t.eq( 'x' ) );
  }

  @Test
  public void testEqualschar1() {

    assertFalse( t.eq( '.' ) );
  }

  @Test
  public void testEqualsToken1() {

    Token t1 = new MacroParamToken( UnicodeChar.get( ' ' ) );
    Token t2 = new SpaceToken( " " );
    assertFalse( t1.equals( t2 ) );
  }

  @Test
  @Override
  public void testToText0() {

    assertEquals( "xx", t.toText() );
  }


}
