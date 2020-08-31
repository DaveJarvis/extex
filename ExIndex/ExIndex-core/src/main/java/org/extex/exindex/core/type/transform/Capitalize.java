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

package org.extex.exindex.core.type.transform;

import java.io.PrintStream;

/**
 * This transformer translates all first characters of a word to their uppercase
 * counterpart and the other characters to theri lowercase counterpart.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public class Capitalize implements Transform {


  public Capitalize() {

  }

  public void print( PrintStream stream ) {

    stream.print( "capitalize" );
  }

  /**
   * java.lang.String)
   */
  public String transform( String in ) {

    boolean up = true;
    StringBuilder sb = new StringBuilder();
    for( int i = 0; i < in.length(); i++ ) {
      char c = in.charAt( i );
      if( up ) {
        sb.append( Character.toUpperCase( c ) );
        up = false;
      }
      else {
        sb.append( Character.toLowerCase( c ) );
      }
      if( Character.isWhitespace( c ) ) {
        up = true;
      }
    }
    return sb.toString();
  }

}
