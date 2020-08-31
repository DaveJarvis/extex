/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
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

package org.extex.core.muskip;

import org.extex.core.dimen.Dimen;

import java.io.Serializable;

/**
 * This class provides a dimen value with a length which is a multiple of math
 * units (mu).
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Mudimen implements Serializable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code length} contains the the natural length.
   */
  private final Dimen length = new Dimen( 0 );

  /**
   * Creates a new object. All components are 0.
   */
  public Mudimen() {

  }

  /**
   * Creates a new object.
   *
   * @param len the length
   */
  public Mudimen( long len ) {

    length.set( len );
  }

  /**
   * Add some other length to the current value.
   *
   * @param value the value to add
   */
  public void add( long value ) {

    this.length.add( value );
  }

  /**
   * Getter for length.
   *
   * @return the length
   */
  public Dimen getLength() {

    return this.length;
  }

  /**
   * Check for a zero value.
   *
   * @return {@code true} iff the length is zero
   */
  public boolean isZero() {

    return length.eq( Dimen.ZERO );
  }

  /**
   * Multiply the value by an integer fraction.
   * <p>
   * <i>length</i> = <i>length</i> * <i>nom</i> / <i>denom</i>
   * </p>
   *
   * @param nom   nominator
   * @param denom denominator
   */
  public void multiply( long nom, long denom ) {

    length.multiply( nom, denom );
  }

  /**
   * Return the string representation of the instance.
   *
   * @return the string representation of this glue
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString( sb );
    return sb.toString();
  }

  /**
   * Append the string representation of the instance to a string buffer.
   *
   * @param sb the target string buffer
   */
  public void toString( StringBuilder sb ) {

    length.toString( sb, 'm', 'u' );
  }

}
