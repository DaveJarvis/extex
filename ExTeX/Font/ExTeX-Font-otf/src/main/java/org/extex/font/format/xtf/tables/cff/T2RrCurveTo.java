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

import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;
import java.util.List;

/**
 * T2 rrcurveto: {dxa dya dxb dyb dxc dyc}+ rrcurveto (8).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2RrCurveTo extends T2PathConstruction {

  /**
   * The six values array.
   */
  private final T2SixNumber[] six;

  /**
   * Create a new object.
   *
   * @param ch    The char string.
   * @param stack The stack.
   * @throws IOException in case of an error
   */
  public T2RrCurveTo( List<T2CharString> stack, CharString ch )
      throws IOException {

    super( stack, new short[]{T2RRCURVETO}, ch );

    int n = stack.size();

    if( n % 6 != 0 ) {
      throw new T2MissingNumberException();
    }

    six = new T2SixNumber[ n / 6 ];

    for( int i = 0; i < n; i += 6 ) {
      T2Number v1 = (T2Number) stack.get( i );
      T2Number v2 = (T2Number) stack.get( i + 1 );
      T2Number v3 = (T2Number) stack.get( i + 2 );
      T2Number v4 = (T2Number) stack.get( i + 3 );
      T2Number v5 = (T2Number) stack.get( i + 4 );
      T2Number v6 = (T2Number) stack.get( i + 5 );
      T2SixNumber si = new T2SixNumber( v1, v2, v3, v4, v5, v6 );
      six[ i / 6 ] = si;
    }

  }

  @Override
  public int getID() {

    return TYPE_RRCURVETO;
  }

  @Override
  public String getName() {

    return "rrcurveto";
  }

  /**
   * Getter for six.
   *
   * @return the six
   */
  public T2SixNumber[] getSix() {

    return six;
  }

  @Override
  public Object getValue() {

    return six;
  }

  @Override
  public String toText() {

    StringBuilder buf = new StringBuilder();

    for( int i = 0; i < six.length; i++ ) {
      buf.append( six[ i ].toString() ).append( ' ' );
    }
    return buf.append( getName() ).toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    for( int i = 0; i < six.length; i++ ) {
      writer.writeStartElement( "pair" );
      writer.writeAttribute( "id", i );
      writer.writeAttribute( "value", six[ i ].toString() );
      writer.writeEndElement();
    }
    writer.writeEndElement();
  }

}
