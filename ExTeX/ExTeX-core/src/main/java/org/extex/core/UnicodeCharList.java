/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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
 */

package org.extex.core;

import java.util.ArrayList;

/**
 * This class provides a list of {@code UnicodeChar}s.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class UnicodeCharList extends ArrayList<UnicodeChar> {

  /**
   * The field {@code serialVersionUID} contains the version number for
   * serialization.
   */
  private static final long serialVersionUID = 20060814L;

  /**
   * Create a new object.
   * This list is initially empty.
   */
  public UnicodeCharList() {

  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param obj the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj
   * argument; {@code false} otherwise.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals( Object obj ) {

    if( !(obj instanceof UnicodeCharList) ) {
      return false;
    }

    UnicodeCharList ucl = (UnicodeCharList) obj;
    if( size() != ucl.size() ) {
      return false;
    }

    int i = 0;
    for( UnicodeChar uc : this ) {
      if( !uc.equals( ucl.get( i++ ) ) ) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    int hash = 0;

    for( UnicodeChar uc : this ) {
      hash += uc.hashCode();
    }
    return hash;
  }

  /**
   * Return the {@code UnicodeCharList} as {@code String}.
   *
   * @return the string of the list
   */
  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder( size() );
    for( UnicodeChar uc : this ) {
      buffer.append( uc.toString() );
    }
    return buffer.toString();
  }

}
