/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format;

/**
 * Class for a fixed point format (32 bit).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Fixed32 {

  /**
   * the value
   */
  private final int fp;

  /**
   * Create a new object.
   *
   * @param value the fixed point value
   */
  public Fixed32( int value ) {

    fp = value;
  }

  /**
   * Create a new object.
   *
   * @param value the value as double
   */
  public Fixed32( double value ) {

    fp = convert( value );
  }

  /**
   * Convert a double in a fixed point format
   *
   * @param dvalue the double value
   * @return Returns the fixed point format
   */
  private int convert( double dvalue ) {

    return (int) (dvalue * FRACTION + ROUND);

  }

  /**
   * round
   */
  private static final double ROUND = .5;

  /**
   * FRACTION
   */
  private static final double FRACTION = 65536.0;

  /**
   * Convert a fixed point value into a double
   *
   * @param fpvalue the fixed point value
   * @return Returns the double value
   */
  private double convert( int fpvalue ) {

    return fpvalue / FRACTION;
  }

  /**
   * Returns the fixed point value.
   *
   * @return Returns the fixed point value.
   */
  public long getFixedPoint() {

    return fp;
  }

  /**
   * Returns the double vlaue.
   *
   * @return Returns the double vlaue.
   */
  public double getDoubleValue() {

    return convert( fp );
  }

  /**
   * {@inheritDoc}
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder();
    buf.append( "0x" ).append( Integer.toHexString( fp ) ).append( " = " );
    buf.append( convert( fp ) );
    return buf.toString();
  }
}