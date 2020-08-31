/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.core.count;

/**
 * This class represents a long integer value. It is used for instance as count
 * register.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class Count extends CountConstant {

  /**
   * The constant {@code ONE} contains the count register with the value 1.
   * This count register is in fact immutable.
   */
  public static final FixedCount ONE = new CountConstant( 1 );

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The constant {@code THOUSAND} contains the count register with the value
   * 1000. This count register is in fact immutable.
   */
  public static final FixedCount THOUSAND = new CountConstant( 1000 );

  /**
   * The constant {@code ZERO} contains the count register with the value 0.
   * This count register is in fact immutable.
   */
  public static final FixedCount ZERO = new CountConstant( 0 );

  /**
   * Creates a new object.
   *
   * @param count the reference to be copied
   */
  public Count( FixedCount count ) {

    this.value = count.getValue();
  }

  /**
   * Creates a new object.
   *
   * @param value the value
   */
  public Count( long value ) {

    this.value = value;
  }


  public Count() {

  }

  /**
   * Add a long to the value. This operation modifies the value.
   *
   * @param val the value to add to
   */
  public void add( long val ) {

    value += val;
  }

  /**
   * Add a Count to the value. This operation modifies the value.
   *
   * @param val the value to add to
   */
  public void add( FixedCount val ) {

    value += val.getValue();
  }

  /**
   * Divide the value by a long. This operation modifies the value.
   *
   * @param denom the denominator to divide by
   * @throws ArithmeticException in case of a division by zero
   */
  public void divide( long denom ) throws ArithmeticException {

    if( denom == 0 ) {
      throw new ArithmeticException();
    }

    value /= denom;
  }

  /**
   * Divide the value by a denominator. This operation modifies the value.
   *
   * @param denom the denominator to divide by
   * @throws ArithmeticException in case of a division by zero
   */
  public void divide( FixedCount denom ) throws ArithmeticException {

    long d = denom.getValue();
    if( d == 0 ) {
      throw new ArithmeticException( null );
    }

    value /= d;
  }

  @Override
  public boolean eq( FixedCount count ) {

    return count.getValue() == value;
  }

  @Override
  public boolean ge( FixedCount count ) {

    return value >= count.getValue();
  }

  /**
   * Getter for the value
   *
   * @return the value
   */
  @Override
  public long getValue() {

    return value;
  }

  @Override
  public boolean gt( FixedCount count ) {

    return value > count.getValue();
  }

  @Override
  public boolean le( FixedCount count ) {

    return value <= count.getValue();
  }

  @Override
  public boolean lt( FixedCount count ) {

    return value < count.getValue();
  }

  /**
   * Multiply the value with a factor. This operation modifies the value.
   *
   * @param factor the factor to multiply with
   */
  public void multiply( long factor ) {

    value *= factor;
  }

  /**
   * Multiply the value with a factor. This operation modifies the value.
   *
   * @param factor the factor to multiply with
   */
  public void multiply( FixedCount factor ) {

    value *= factor.getValue();
  }

  @Override
  public boolean ne( FixedCount count ) {

    return value != count.getValue();
  }

  /**
   * Setter for the value.
   *
   * @param l the new value
   * @see #set(FixedCount)
   */
  public void set( long l ) {

    value = l;
  }

  /**
   * Setter for the value.
   *
   * @param c the new value
   * @see #set(long)
   */
  public void set( FixedCount c ) {

    value = c.getValue();
  }

  /**
   * Determine the printable representation of the object. The value returned
   * is exactly the string which would be produced by TeX to print the Count.
   *
   * @return the printable representation
   * @see #toString(StringBuilder)
   * @see org.extex.core.count.CountConstant#toString()
   */
  @Override
  public String toString() {

    return Long.toString( value );
  }

  /**
   * Determine the printable representation of the object. The value returned
   * is exactly the string which would be produced by TeX to print the Count.
   *
   * @param sb the target string buffer
   * @see #toString()
   * @see org.extex.core.count.CountConstant#toString(StringBuilder)
   */
  @Override
  public void toString( StringBuilder sb ) {

    sb.append( value );
  }

}
