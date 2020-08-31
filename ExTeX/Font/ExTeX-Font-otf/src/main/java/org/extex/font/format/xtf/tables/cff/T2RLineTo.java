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
 * rlineto: {dxa dya}+ rlineto (5).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2RLineTo extends T2PathConstruction implements CharStringCalc {

  /**
   * The pair array.
   */
  private final T2PairNumber[] pairs;

  /**
   * Create a new object.
   *
   * @param ch    The char string.
   * @param stack The stack.
   * @throws IOException in case of an error
   */
  public T2RLineTo( List<T2CharString> stack, CharString ch )
      throws IOException {

    super( stack, new short[]{T2RLINETO}, ch );

    int n = stack.size();

    if( n < 2 || n % 2 != 0 ) {
      throw new T2MissingNumberException();
    }
    pairs = new T2PairNumber[ n / 2 ];

    for( int i = 0; i < n; i += 2 ) {
      T2Number v1 = (T2Number) stack.get( i );
      T2Number v2 = (T2Number) stack.get( i + 1 );
      T2PairNumber pn = new T2PairNumber( v1, v2 );
      pairs[ i / 2 ] = pn;
    }

  }

  @Override
  public void calculate( CharString ch ) {

    for( int i = 0; i < pairs.length; i++ ) {
      int x = pairs[ i ].getA().getInteger();
      int y = pairs[ i ].getB().getInteger();
      ch.setMX( x );
      ch.setMY( y );
    }
  }

  @Override
  public int getID() {

    return TYPE_RLINETO;
  }

  @Override
  public String getName() {

    return "rlineto";
  }

  /**
   * Getter for pairs.
   *
   * @return the pairs
   */
  public T2PairNumber[] getPairs() {

    return pairs;
  }

  @Override
  public Object getValue() {

    return pairs;
  }

  @Override
  public String toText() {

    StringBuilder buf = new StringBuilder();
    for( int i = 0; i < pairs.length; i++ ) {
      buf.append( pairs[ i ].toString() ).append( ' ' );
    }
    return buf.append( getName() ).toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    for( int i = 0; i < pairs.length; i++ ) {
      writer.writeStartElement( "pair" );
      writer.writeAttribute( "id", i );
      writer.writeAttribute( "value", pairs[ i ].toString() );
      writer.writeEndElement();
    }
    writer.writeEndElement();
  }

}
