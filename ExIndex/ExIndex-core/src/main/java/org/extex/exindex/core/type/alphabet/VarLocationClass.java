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

package org.extex.exindex.core.type.alphabet;

import org.extex.exindex.core.type.page.PageReference;
import org.extex.exindex.core.type.page.SomePage;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a composed location class.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class VarLocationClass implements LocationClass {

  /**
   * This inner class represents a separator.
   */
  private class Seperator implements LocationClass {

    /**
     * The field {@code sep} contains the separator.
     */
    private String sep;

    /**
     * Creates a new object.
     *
     * @param sep the separator
     */
    public Seperator( String sep ) {

      this.sep = sep;
    }

    /**
     * Adder for a separator.
     *
     * @param separator the separator to set
     */
    public void addSep( String separator ) {

      this.sep = this.sep + separator;
    }

    /**
     * java.lang.String, String)
     */
    public PageReference match( String encap, String s ) {

      return null;
    }

    /**
     * java.lang.StringBuilder)
     */
    public boolean match( StringBuilder s ) {

      int j = 0;
      for( int i = 0; i < sep.length(); i++ ) {
        if( s.length() == 0 || s.charAt( j ) != sep.charAt( i ) ) {
          return false;
        }
        j++;
      }
      s.delete( 0, j );
      return true;
    }

  }

  /**
   * The field {@code list} contains the constituents.
   */
  private final List<LocationClass> list = new ArrayList<LocationClass>();


  public VarLocationClass() {

  }

  /**
   * Add some location class to the ones already stored.
   *
   * @param loc the location class to add
   */
  public void add( LocationClass loc ) {

    list.add( loc );
  }

  /**
   * Add a separator to the internal list.
   *
   * @param sep the separator
   */
  public void add( String sep ) {

    if( "".equals( sep ) ) {
      return;
    }
    int size = list.size();
    if( size > 0 ) {
      LocationClass last = list.get( size - 1 );
      if( last instanceof Seperator ) {
        ((Seperator) last).addSep( sep );
        return;
      }
    }
    list.add( new Seperator( sep ) );
  }

  /**
   * String)
   */
  public PageReference match( String encap, String text ) {

    StringBuilder sb = new StringBuilder( text );
    if( match( sb ) && sb.length() == 0 ) {

      return new SomePage( encap, text );
    }
    return null;
  }

  public boolean match( StringBuilder s ) {

    int size = list.size();
    if( size == 0 ) {
      return true;
    }

    for( LocationClass lc : list ) {
      if( !lc.match( s ) ) {
        return false;
      }
    }
    return true;
  }
}
