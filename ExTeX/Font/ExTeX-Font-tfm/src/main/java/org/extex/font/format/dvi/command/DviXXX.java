/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.font.format.dvi.command;

import org.extex.core.UnicodeChar;

/**
 * DVI: xxx: Undefined command e.g. for specials.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public class DviXXX extends DviCommand {

  /**
   * the value
   */
  private final int[] values;

  /**
   * Create a new object.
   *
   * @param opc the opcode
   * @param sp  the start pointer
   * @param v   the values
   */
  public DviXXX( final int opc, final int sp, final int[] v ) {

    super( opc, sp );
    values = v;
  }

  @Override
  public String getName() {

    return "xxx";
  }

  /**
   * Returns the values.
   *
   * @return Returns the values.
   */
  public int[] getValues() {

    return values;
  }

  /**
   * Return the values as String. If xxx has only printed letters, the the
   * String is returned, otherwise the hexvalues.
   *
   * @return Return a String for the values.
   */
  public String getXXXString() {

    boolean print = true;
    for( int i = 0; i < values.length; i++ ) {
      UnicodeChar uc = UnicodeChar.get( values[ i ] );
      if( !uc.isPrintable() ) {
        print = false;
        break;
      }
    }
    StringBuilder buf = new StringBuilder( values.length );
    if( print ) {
      for( int i = 0; i < values.length; i++ ) {
        buf.append( (char) values[ i ] );
      }
    }
    else {
      for( int i = 0; i < values.length; i++ ) {
        buf.append( "0x" ).append( Integer.toHexString( values[ i ] ) )
           .append( ' ' );
      }
    }
    return buf.toString();
  }
}
