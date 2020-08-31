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

package org.extex.core;

import com.ibm.icu.lang.UCharacter;

/**
 * This class provides a Unicode character with some combing characters attached
 * to it.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class ComposedUnicodeChar extends UnicodeChar {

  /**
   * The constant {@code serialVersionUID} contains the id for
   * serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * This is a factory method for combining Unicode characters.
   *
   * @param uc   he base Unicode character
   * @param comb the combining mark
   * @return the new combining Unicode character
   */
  public static ComposedUnicodeChar get( UnicodeChar uc, int comb ) {

    if( uc == null ) {
      throw new IllegalArgumentException( "null argument" );
    }
    UCharacter.getCombiningClass( comb ); // TODO gene: check
    ComposedUnicodeChar cuc = new ComposedUnicodeChar( uc.getCodePoint() );
    if( uc instanceof ComposedUnicodeChar ) {

      int[] a = ((ComposedUnicodeChar) uc).combiningCodePoints;
      cuc.combiningCodePoints = new int[ a.length + 1 ];
      int i = 0;
      for( int c : a ) {
        cuc.combiningCodePoints[ i++ ] = c;
      }
      cuc.combiningCodePoints[ i ] = comb;
    }
    else {
      cuc.combiningCodePoints = new int[]{comb};
    }
    return cuc;
  }

  /**
   * The field {@code combiningCodePoints} contains the combining
   * characters.
   */
  private int[] combiningCodePoints;

  /**
   * Creates a new object from an integer code point and some combining
   * characters.
   *
   * @param codePoint the 32-bit code point
   */
  protected ComposedUnicodeChar( int codePoint ) {

    super( codePoint );
  }

  /**
   * Creates a new object from an integer code point and some combining
   * characters.
   *
   * @param codePoint the 32-bit code point
   * @param combining the array of code points for the combining characters
   */
  protected ComposedUnicodeChar( int codePoint, int[] combining ) {

    super( codePoint );
    for( int i = 0; i < combining.length; i++ ) {
      if( combining[ i ] < MIN_VALUE || combining[ i ] > MAX_VALUE ) {
        throw new IllegalArgumentException( "Code point out of bounds" );
      }
    }
    combiningCodePoints = combining;
  }

  /**
   * Compares a {@code UnicodeChar} character with the value of this
   * object. They are considered equal if the are both
   * {@link ComposedUnicodeChar ComposedUnicodeChar}s and have the same
   * codes.
   * <p>
   * The general signature for comparison to an arbitrary object is required
   * for the implementation of {@link java.util.HashMap HashMap} and friends.
   * </p>
   *
   * @param unicodeChar the character to compare
   * @return {@code true} if the characters are equal, otherwise
   * {@code false}
   */
  @Override
  public boolean equals( Object unicodeChar ) {

    if( !(unicodeChar instanceof ComposedUnicodeChar)
        || this.getCodePoint() != ((UnicodeChar) unicodeChar)
        .getCodePoint() ) {
      return false;
    }
    ComposedUnicodeChar uc = (ComposedUnicodeChar) unicodeChar;
    if( uc.combiningCodePoints.length != combiningCodePoints.length ) {
      return false;
    }

    for( int i = combiningCodePoints.length; i > 0; i-- ) {
      if( combiningCodePoints[ i ] != uc.combiningCodePoints[ i ] ) {
        return false;
      }
    }
    return true;
  }

  /**
   * Getter for combining character's code points.
   *
   * @return the combining character's code points
   */
  protected int[] getCombiningCodePoints() {

    return this.combiningCodePoints;
  }

  /**
   * Getter for the hash code.
   *
   * @return the hash code
   * @see org.extex.core.UnicodeChar#hashCode()
   */
  @Override
  public int hashCode() {

    return getCodePoint();
  }

}
