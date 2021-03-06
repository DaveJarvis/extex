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

package org.extex.font.format.tfm;

import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * TFM-FixWord.
 * <p>
 * The dimensions are represented in the same way as in tfm files. Higher 12
 * bits is the whole part and lower 20 bits is the fractional part.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TfmFixWord implements Serializable {

  /**
   * conf.
   */
  public static final int CONV = 0x10000;

  /**
   * fixdominator.
   */
  public static final int FIXWORDDENOMINATOR = 0x100000;

  /**
   * FRACTIONDIGITS.
   */
  public static final int FRACTIONDIGITS = 6;

  /**
   * the maximal value for fix word to calculate the dimen value.
   */
  private static final int MAX_FIXWORD_VALUE = 8388608;

  /**
   * NULL.
   */
  public static final TfmFixWord NULL = null;

  /**
   * POINT-SHIFT.
   */
  public static final int POINTSHIFT = 20;

  /**
   * The field {@code serialVersionUID}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * TEN.
   */
  public static final TfmFixWord TEN = new TfmFixWord( TfmConstants.CONST_10 );

  /**
   * tfmconf.
   */
  public static final int TFMCONV = 0x10;

  /**
   * UNITY.
   */
  public static final TfmFixWord UNITY = new TfmFixWord( 1 );

  /**
   * ZERO.
   */
  public static final TfmFixWord ZERO = new TfmFixWord( 0 );

  /**
   * Convert the fixword value to a dimen value.
   * <p>
   * see toDimen(FixedDimen size)
   * </p>
   *
   * @param desingsize The design size.
   * @param val        The value.
   * @return Return the {@link Dimen} of the value.
   */
  public static Dimen toDimen( FixedDimen desingsize, long val ) {

    if( desingsize == null ) {
      throw new IllegalArgumentException( "designsize" );
    }
    int shift = POINTSHIFT;
    long z = desingsize.getValue();
    while( z >= MAX_FIXWORD_VALUE ) {
      z >>= 1;
      shift -= 1;
    }
    return new Dimen( z * val >> shift );
  }

  /**
   * Convert the fixword to a dimen value.
   * <p>
   * see toDimen(FixedDimen size)
   * </p>
   *
   * @param desingsize The design size.
   * @param fw         The fix word.
   * @return Return the {@link Dimen} of the value.
   */
  public static Dimen toDimen( FixedDimen desingsize, TfmFixWord fw ) {

    return toDimen( desingsize, fw.getValue() );
  }

  /**
   * Convert a dimen to a fix word value.
   *
   * @param desingsize The design size.
   * @param dimen      The dimen value.
   * @return Returns the fix word value of the dimen.
   */
  public static long toValue( FixedDimen desingsize, FixedDimen dimen ) {

    if( desingsize == null ) {
      throw new IllegalArgumentException( "designsize" );
    }
    if( dimen == null ) {
      throw new IllegalArgumentException( "dimen" );
    }
    int shift = POINTSHIFT;
    long z = desingsize.getValue();
    while( z >= MAX_FIXWORD_VALUE ) {
      z >>= 1;
      shift -= 1;
    }
    return (dimen.getValue() << shift) / z;
  }

  /**
   * the internal value.
   */
  private long value;

  /**
   * Create a new object.
   */
  public TfmFixWord() {

    value = 0;
  }

  /**
   * Create a new object.
   *
   * @param val the values as int. This int value represents the length in
   *            points.
   */
  public TfmFixWord( int val ) {

    value = val << POINTSHIFT;
  }

  /**
   * Create new object.
   * <p>
   * The value represented by this instance is num / den in points.
   *
   * @param num the num
   * @param den the den
   */
  public TfmFixWord( int num, int den ) {

    value = ((long) num << POINTSHIFT) / den;
  }

  /**
   * Create a new object.
   *
   * @param val the values as String
   */
  public TfmFixWord( String val ) {

    try {
      value = Integer.parseInt( val );
    } catch( NumberFormatException e ) {
      // use default
      value = 0;
    }
  }

  /**
   * Return the internal value.
   *
   * @return the internal value
   */
  public long getValue() {

    return value;
  }

  /**
   * less than.
   *
   * @param num the value to compare
   * @return {@code true}, if the internal values is lesser, otherwise
   * {@code false}
   */
  public boolean lessThan( int num ) {

    return (value < (num << POINTSHIFT));
  }

  /**
   * more than.
   *
   * @param num the value to compare
   * @return {@code true}, if the internal values are more, otherwise
   * {@code false}
   */
  public boolean moreThan( int num ) {

    return (value > (num << POINTSHIFT));
  }

  /**
   * Set the value.
   *
   * @param v The value to set.
   */
  public void setValue( long v ) {

    value = v;
  }

  /**
   * Convert the fixword value to a dimen value.
   *
   * <p>
   * the simple calculation fw.getValue() * actualsize.getValue() /
   * TFMFixWord.FIXWORDDENOMINATOR leads to different rounding than in TeX due
   * to a limitation to 4 byte integer precission. Note that fw.getValue() *
   * actualsize.getValue() might exceed the 4 byte range. Hence TTP 571
   * cancels actualsize.getValue() and TFMFixWord.FIXWORDDENOMINATOR to allow
   * for a bytewise calculation. We do not need this bytewise calculation
   * since we have long integers, but we need to be precise in performing the
   * same rounding.
   * </p>
   *
   * @param size The font size (design size for font dimen, actual size for
   *             all others)
   * @return Returns the Dimen value.
   */
  public Dimen toDimen( FixedDimen size ) {

    if( size == null ) {
      throw new IllegalArgumentException( "size" );
    }
    int shift = POINTSHIFT;
    long z = size.getValue();
    while( z >= MAX_FIXWORD_VALUE ) {
      z >>= 1;
      shift -= 1;
    }
    return new Dimen( z * value >> shift );
  }

  /**
   * Returns the value as double in untis.
   *
   * @return Returns the value as double in untis.
   */
  public double toDouble() {

    // mgn check!
    return (double) value /* * TFMConstants.CONST_1000 */
        / FIXWORDDENOMINATOR;
  }

  /**
   * Return the values as String.
   *
   * @return the values as String
   */
  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder();
    long v = value;
    int unity = 1 << POINTSHIFT;
    int mask = unity - 1;
    if( v < 0 ) {
      buf.append( '-' );
      v = -v;
    }
    buf.append( v >>> POINTSHIFT );
    buf.append( '.' );
    v = TfmConstants.CONST_10 * (v & mask) + TfmConstants.CONST_5;
    int delta = TfmConstants.CONST_10;
    do {
      if( delta > unity ) {
        v += unity / 2 - delta / 2;
      }
      buf.append( Character.forDigit( (int) (v >>> POINTSHIFT),
                                      TfmConstants.CONST_10 ) );
      v = TfmConstants.CONST_10 * (v & mask);
    } while( v > (delta *= TfmConstants.CONST_10) );
    return buf.toString();
  }

  /**
   * Returns the value as Sting in untis with comma (0.00000..).
   *
   * @return Returns the value as Sting in untis with comma.
   */
  public String toStringComma() {

    NumberFormat nf = NumberFormat.getInstance( Locale.US );
    nf.setMaximumFractionDigits( FRACTIONDIGITS );
    nf.setGroupingUsed( false );
    return nf.format( (double) value * TfmConstants.CONST_1000
                          / FIXWORDDENOMINATOR );
  }

  /**
   * Return the value as String in units.
   * <p>
   * It devide the value by 1000.
   * </p>
   *
   * @return the value as String in units
   */
  public String toStringUnits() {

    if( value > 0 ) {
      return String
          .valueOf( (value * TfmConstants.CONST_1000) >>> POINTSHIFT );
    }
    return String
        .valueOf( -((-value * TfmConstants.CONST_1000) >>> POINTSHIFT) );
  }

}
