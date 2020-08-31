/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.lisp.type.value;

import java.io.PrintStream;

/**
 * This class is a node containing a floating point number. The class is
 * immutable; i.e. it does not have any setters.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class LDouble implements LValue {

  /**
   * The field {@code value} contains the value.
   */
  private final double value;

  /**
   * Creates a new object.
   *
   * @param value the value
   */
  public LDouble( double value ) {

    this.value = value;
  }

  /**
   * Getter for value.
   *
   * @return the value
   */
  public double getValue() {

    return value;
  }

  public void print( PrintStream stream ) {

    stream.print( value );
  }

  @Override
  public String toString() {

    return Double.toString( value );
  }

}
