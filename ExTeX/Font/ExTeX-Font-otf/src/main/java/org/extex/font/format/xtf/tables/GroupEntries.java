/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.xtf.tables;

import java.util.ArrayList;
import java.util.List;

/**
 * This class group entries in a list.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class GroupEntries {

  /**
   * The list for the entries.
   */
  private final List<Object> list;

  /**
   * Create a new object.
   */
  public GroupEntries() {

    list = new ArrayList<Object>();
  }

  /**
   * Create a new object.
   *
   * @param size The initialize size of the list.
   */
  public GroupEntries( int size ) {

    list = new ArrayList<Object>( size );
  }

  /**
   * Add the entry if it not exists in the list.
   *
   * @param val The entry.
   */
  public void add( int val ) {

    Integer v = new Integer( val );
    if( !list.contains( v ) ) {
      list.add( v );
    }
  }

  /**
   * Add the entry if it not exists in the list.
   *
   * @param val The entry.
   */
  public void add( Object val ) {

    if( !list.contains( val ) ) {
      list.add( val );
    }
  }

  /**
   * Returns the entry at position 'idx'.
   *
   * @param idx The index.
   * @return Returns the entry at position 'idx'.
   */
  public Object get( int idx ) {

    return list.get( idx );
  }

  /**
   * Returns the size of the list.
   *
   * @return Returns the size of the list.
   */
  public int size() {

    return list.size();
  }

  /**
   * Returns the list as array.
   *
   * @return Returns the list as array.
   */
  public Object[] toArray() {

    Object[] obj = new Object[ list.size() ];
    obj = list.toArray( obj );
    return obj;
  }

  /**
   * Returns the list as a int-array. If a value can not be convert, the value
   * set to -1.
   *
   * @return Returns the list as a int-array.
   */
  public int[] toIntArray() {

    int[] obj = new int[ list.size() ];
    for( int i = 0, n = list.size(); i < n; i++ ) {
      try {
        obj[ i ] = Integer.parseInt( get( i ).toString() );
      } catch( Exception e ) {
        obj[ i ] = -1;
      }
    }
    return obj;
  }

  /**
   * Returns the entries as a string.
   *
   * @return Returns the entries as a string.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder();
    for( int i = 0, n = list.size(); i < n; i++ ) {
      buf.append( get( i ) );
      if( i < n - 1 ) {
        buf.append( ", " );
      }
    }
    return buf.toString();
  }

}
