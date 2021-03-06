/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

package org.extex.scanner.type;

import org.extex.scanner.api.exception.CatcodeException;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.*;

/**
 * Test cases for Catcodes.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class CatcodeTest
    implements
    CatcodeVisitor<String, String, String, String> {

  /**
   * The field {@code CAT_INVALID} contains the numerical value for invalid
   * characters.
   */
  private static final int CAT_INVALID = 15;

  /**
   * The field {@code CAT_COMMENT} contains the numerical value for comment
   * characters.
   */
  private static final int CAT_COMMENT = 14;

  /**
   * The field {@code CAT_ACTIVE} contains the numerical value for active
   * characters.
   */
  private static final int CAT_ACTIVE = 13;

  /**
   * The field {@code CAT_OTHER} contains the numerical value for other
   * characters.
   */
  private static final int CAT_OTHER = 12;

  /**
   * The field {@code CAT_LETTER} contains the numerical value for letter
   * characters.
   */
  private static final int CAT_LETTER = 11;

  /**
   * The field {@code CAT_SPACE} contains the numerical value for space
   * characters.
   */
  private static final int CAT_SPACE = 10;

  /**
   * The field {@code CAT_IGNORE} contains the numerical value for ignored
   * characters.
   */
  private static final int CAT_IGNORE = 9;

  /**
   * The field {@code CAT_SUB} contains the numerical value for subscript
   * mark characters.
   */
  private static final int CAT_SUB = 8;

  /**
   * The field {@code CAT_SUP} contains the numerical value for superscript
   * mark characters.
   */
  private static final int CAT_SUP = 7;

  /**
   * The field {@code CAT_HASH} contains the numerical value for hash mark
   * characters &ndash; used for macro parameters.
   */
  private static final int CAT_HASH = 6;

  /**
   * The field {@code CAT_CR} contains the numerical value for carridge
   * return characters.
   */
  private static final int CAT_CR = 5;

  /**
   * The field {@code CAT_TAB} contains the numerical value for TAB mark
   * characters.
   */
  private static final int CAT_TAB = 4;

  /**
   * The field {@code CAT_MATH} contains the numerical value for math shift
   * characters.
   */
  private static final int CAT_MATH = 3;

  /**
   * The field {@code CAT_RIGHT} contains the numerical value for right brace
   * characters.
   */
  private static final int CAT_RIGHT = 2;

  /**
   * The field {@code CAT_LEFT} contains the numerical value for left brace
   * characters.
   */
  private static final int CAT_LEFT = 1;

  /**
   * The field {@code CAT_ESC} contains the numerical value for escape
   * characters.
   */
  private static final int CAT_ESC = 0;

  /**
   * The field {@code visited} contains the indicator that a visitor has been
   * invoked. It will be set by the visit* methods to the catcode encountered.
   */
  private static int visited = -1;

  /**
   * Command line interface.
   *
   * @param args the arguments
   */
  public static void main( String[] args ) {

    JUnitCore.runClasses( CatcodeTest.class );
  }

  /**
   * Test that escape tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode0() throws Exception {

    assertEquals( Catcode.ESCAPE, Catcode.valueOf( CAT_ESC ) );
    assertEquals( CAT_ESC, Catcode.ESCAPE.getCode() );
    assertEquals( "escape", Catcode.ESCAPE.toString() );
  }

  /**
   * Test that left brace tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode1() throws Exception {

    assertEquals( Catcode.LEFTBRACE, Catcode.valueOf( CAT_LEFT ) );
    assertEquals( CAT_LEFT, Catcode.LEFTBRACE.getCode() );
    assertEquals( "leftbrace", Catcode.LEFTBRACE.toString() );
  }

  /**
   * Test that space tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode10() throws Exception {

    assertEquals( Catcode.SPACE, Catcode.valueOf( CAT_SPACE ) );
    assertEquals( CAT_SPACE, Catcode.SPACE.getCode() );
    assertEquals( "space", Catcode.SPACE.toString() );
  }

  /**
   * Test that letter tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode11() throws Exception {

    assertEquals( Catcode.LETTER, Catcode.valueOf( CAT_LETTER ) );
    assertEquals( CAT_LETTER, Catcode.LETTER.getCode() );
    assertEquals( "letter", Catcode.LETTER.toString() );
  }

  /**
   * Test that other tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode12() throws Exception {

    assertEquals( Catcode.OTHER, Catcode.valueOf( CAT_OTHER ) );
    assertEquals( CAT_OTHER, Catcode.OTHER.getCode() );
    assertEquals( "other", Catcode.OTHER.toString() );
  }

  /**
   * Test that active tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode13() throws Exception {

    assertEquals( Catcode.ACTIVE, Catcode.valueOf( CAT_ACTIVE ) );
    assertEquals( CAT_ACTIVE, Catcode.ACTIVE.getCode() );
    assertEquals( "active", Catcode.ACTIVE.toString() );
  }

  /**
   * Test that comment tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode14() throws Exception {

    assertEquals( Catcode.COMMENT, Catcode.valueOf( CAT_COMMENT ) );
    assertEquals( CAT_COMMENT, Catcode.COMMENT.getCode() );
    assertEquals( "comment", Catcode.COMMENT.toString() );
  }

  /**
   * Test that invalid tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode15() throws Exception {

    assertEquals( Catcode.INVALID, Catcode.valueOf( CAT_INVALID ) );
    assertEquals( CAT_INVALID, Catcode.INVALID.getCode() );
    assertEquals( "invalid", Catcode.INVALID.toString() );
  }

  /**
   * Test that right brace tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode2() throws Exception {

    assertEquals( Catcode.RIGHTBRACE, Catcode.valueOf( CAT_RIGHT ) );
    assertEquals( CAT_RIGHT, Catcode.RIGHTBRACE.getCode() );
    assertEquals( "rightbrace", Catcode.RIGHTBRACE.toString() );
  }

  /**
   * Test that math shift tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode3() throws Exception {

    assertEquals( Catcode.MATHSHIFT, Catcode.valueOf( CAT_MATH ) );
    assertEquals( CAT_MATH, Catcode.MATHSHIFT.getCode() );
    assertEquals( "mathshift", Catcode.MATHSHIFT.toString() );
  }

  /**
   * Test that tab mark tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode4() throws Exception {

    assertEquals( Catcode.TABMARK, Catcode.valueOf( CAT_TAB ) );
    assertEquals( CAT_TAB, Catcode.TABMARK.getCode() );
    assertEquals( "tabmark", Catcode.TABMARK.toString() );
  }

  /**
   * Test that CR tokens have correct code, name, and string representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode5() throws Exception {

    assertEquals( Catcode.CR, Catcode.valueOf( CAT_CR ) );
    assertEquals( CAT_CR, Catcode.CR.getCode() );
    assertEquals( "cr", Catcode.CR.toString() );
  }

  /**
   * Test that macro parameter tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode6() throws Exception {

    assertEquals( Catcode.MACROPARAM, Catcode.valueOf( CAT_HASH ) );
    assertEquals( CAT_HASH, Catcode.MACROPARAM.getCode() );
    assertEquals( "macroparam", Catcode.MACROPARAM.toString() );
  }

  /**
   * Test that superscript mark tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode7() throws Exception {

    assertEquals( Catcode.SUPMARK, Catcode.valueOf( CAT_SUP ) );
    assertEquals( CAT_SUP, Catcode.SUPMARK.getCode() );
    assertEquals( "supmark", Catcode.SUPMARK.toString() );
  }

  /**
   * Test that subscript mark tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode8() throws Exception {

    assertEquals( Catcode.SUBMARK, Catcode.valueOf( CAT_SUB ) );
    assertEquals( CAT_SUB, Catcode.SUBMARK.getCode() );
    assertEquals( "submark", Catcode.SUBMARK.toString() );
  }

  /**
   * Test that ignore tokens have correct code, name, and string
   * representation.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testCatcode9() throws Exception {

    assertEquals( Catcode.IGNORE, Catcode.valueOf( CAT_IGNORE ) );
    assertEquals( CAT_IGNORE, Catcode.IGNORE.getCode() );
    assertEquals( "ignore", Catcode.IGNORE.toString() );
  }

  /**
   * Test that the static method toCatcode() throws an exception when invoked
   * with a negative number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testToCatcodeFail1() throws Exception {

    try {
      Catcode.valueOf( -1 );
      assertFalse( "Test succeeded unexpectedly", true );
    } catch( CatcodeException e ) {
      assertTrue( true );
    }
  }

  /**
   * Test that the static method toCatcode() throws an exception when invoked
   * with a to large number.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testToCatcodeFail2() throws Exception {

    try {
      Catcode.valueOf( 16 );
      assertFalse( "Test succeeded unexpectedly", true );
    } catch( CatcodeException e ) {
      assertTrue( true );
    }
  }

  /**
   * Test that the catcode visitor works for escape tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit0() throws Exception {

    assertEquals( "esc", Catcode.ESCAPE.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_ESC, visited );
  }

  /**
   * Test that the catcode visitor works for left brace tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit1() throws Exception {

    assertEquals( "{", Catcode.LEFTBRACE.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_LEFT, visited );
  }

  /**
   * Test that the catcode visitor works for space tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit10() throws Exception {

    assertEquals( " ", Catcode.SPACE.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_SPACE, visited );
  }

  /**
   * Test that the catcode visitor works for letter tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit11() throws Exception {

    assertEquals( "letter", Catcode.LETTER.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_LETTER, visited );
  }

  /**
   * Test that the catcode visitor works for other tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit12() throws Exception {

    assertEquals( ".", Catcode.OTHER.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_OTHER, visited );
  }

  /**
   * Test that the catcode visitor works for active tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit13() throws Exception {

    assertEquals( "active", Catcode.ACTIVE.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_ACTIVE, visited );
  }

  /**
   * Test that the catcode visitor works for comment tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit14() throws Exception {

    assertEquals( "%", Catcode.COMMENT.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_COMMENT, visited );
  }

  /**
   * Test that the catcode visitor works for invalid tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit15() throws Exception {

    assertEquals( "invalid", Catcode.INVALID.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_INVALID, visited );
  }

  /**
   * Test that the catcode visitor works for right brace tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit2() throws Exception {

    assertEquals( "}", Catcode.RIGHTBRACE.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_RIGHT, visited );
  }

  /**
   * Test that the catcode visitor works for math shift tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit3() throws Exception {

    assertEquals( "$", Catcode.MATHSHIFT.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_MATH, visited );
  }

  /**
   * Test that the catcode visitor works for tab mark tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit4() throws Exception {

    assertEquals( "&", Catcode.TABMARK.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_TAB, visited );
  }

  /**
   * Test that the catcode visitor works for CR tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit5() throws Exception {

    assertEquals( "cr", Catcode.CR.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_CR, visited );
  }

  /**
   * Test that the catcode visitor works for macro parameter tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit6() throws Exception {

    assertEquals( "#", Catcode.MACROPARAM.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_HASH, visited );
  }

  /**
   * Test that the catcode visitor works for superscript mark tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit7() throws Exception {

    assertEquals( "^", Catcode.SUPMARK.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_SUP, visited );
  }

  /**
   * Test that the catcode visitor works for subscript mark tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit8() throws Exception {

    assertEquals( "_", Catcode.SUBMARK.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_SUB, visited );
  }

  /**
   * Test that the catcode visitor works for ignore tokens.
   *
   * @throws Exception in case of an error
   */
  @Test
  public void testVisit9() throws Exception {

    assertEquals( "ignore", Catcode.IGNORE.visit( this, "1", "2", "3" ) );
    assertEquals( CAT_IGNORE, visited );
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public String visitActive( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_ACTIVE;
    return "active";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitComment( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_COMMENT;
    return "%";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitCr( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_CR;
    return "cr";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitEscape( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_ESC;
    return "esc";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitIgnore( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_IGNORE;
    return "ignore";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitInvalid( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_INVALID;
    return "invalid";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitLeftBrace( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_LEFT;
    return "{";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitLetter( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_LETTER;
    return "letter";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitMacroParam( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_HASH;
    return "#";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitMathShift( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_MATH;
    return "$";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitOther( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_OTHER;
    return ".";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitRightBrace( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_RIGHT;
    return "}";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitSpace( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_SPACE;
    return " ";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitSubMark( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_SUB;
    return "_";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitSupMark( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_SUP;
    return "^";
  }

  /**
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public final String visitTabMark( String arg1, String arg2, String arg3 ) {

    assertEquals( "1", arg1 );
    assertEquals( "2", arg2 );
    assertEquals( "3", arg3 );
    visited = CAT_TAB;
    return "&";
  }
}
