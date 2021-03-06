/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables.cff;

/**
 * Eight {@link T2Number},
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2EightNumber {

  /**
   * The value array.
   */
  private final T2Number[] val = new T2Number[ 8 ];

  /**
   * Creates a new object.
   *
   * @param a The value a.
   * @param b The value b.
   * @param c The value c.
   * @param d The value d.
   * @param e The value e.
   * @param f The value f.
   * @param g The value g.
   * @param h The value h.
   */
  public T2EightNumber( T2Number a, T2Number b, T2Number c, T2Number d,
                        T2Number e, T2Number f, T2Number g, T2Number h ) {

    val[ 0 ] = a;
    val[ 1 ] = b;
    val[ 2 ] = c;
    val[ 3 ] = d;
    val[ 4 ] = e;
    val[ 5 ] = f;
    val[ 6 ] = g;
    val[ 7 ] = h;
  }

  /**
   * Returns the number.
   *
   * @param idx The index.
   * @return Returns the number.
   */
  public T2Number getNumber( int idx ) {

    return val[ idx ];
  }

  /**
   * Getter for val.
   *
   * @return the val
   */
  public T2Number[] getVal() {

    return val;
  }

  /**
   * Set the value.
   *
   * @param t   The number.
   * @param idx The index.
   */
  public void setValue( T2Number t, int idx ) {

    val[ idx ] = t;
  }

  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder();
    for( int i = 0; i < val.length; i++ ) {
      buf.append( ' ' ).append( val[ i ].toString() );
    }

    return buf.toString().trim();
  }

}
