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

package org.extex.core.count;

import java.io.Serializable;

/**
 * This class provides an implementation of a Count where no methods modifying
 * the contents are available.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:$
 */
public class CountConstant implements FixedCount, Serializable {

  /**
   * The constant {@code serialVersionUID} contains the id for serialization.
   */
  protected static final long serialVersionUID = 2007L;

  /**
   * The field {@code value} contains the value of the count register. Note:
   * This variable is protected to avoid that the derived class Count has to
   * excessively use getters and setters.
   */
  protected long value;

  /**
   * Creates a new object.
   *
   * @param value the value to be stored
   */
  public CountConstant( FixedCount value ) {

    this.value = value.getValue();
  }

  /**
   * Creates a new object.
   *
   * @param value the value to be stored
   */
  public CountConstant( long value ) {

    this.value = value;
  }


  public CountConstant() {

    this.value = 0;
  }

  public boolean eq( FixedCount count ) {

    return count.getValue() == value;
  }

  public boolean ge( FixedCount count ) {

    return value >= count.getValue();
  }

  /**
   * Getter for the value
   *
   * @return the value
   */
  public long getValue() {

    return value;
  }

  public boolean gt( FixedCount count ) {

    return value > count.getValue();
  }

  public boolean le( FixedCount count ) {

    return value <= count.getValue();
  }

  public boolean lt( FixedCount count ) {

    return value < count.getValue();
  }

  public boolean ne( FixedCount count ) {

    return value != count.getValue();
  }

  /**
   * Determine the printable representation of the object. The value returned
   * is exactly the string which would be produced by TeX to print the Count.
   *
   * @return the printable representation
   * @see #toString(StringBuilder)
   * @see java.lang.Object#toString()
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
   * @see org.extex.core.count.FixedCount#toString(StringBuilder)
   */
  public void toString( StringBuilder sb ) {

    sb.append( value );
  }

}
