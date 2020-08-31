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
 * rlinecurve: {dxa dya}+ dxb dyb dxc dyc dxd dyd rlinecurve (25).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2RlineCurve extends T2PathConstruction {

  /**
   * The dxb.
   */
  private final T2Number dxb;

  /**
   * The dxc.
   */
  private final T2Number dxc;

  /**
   * The dxd.
   */
  private final T2Number dxd;

  /**
   * The dyb.
   */
  private final T2Number dyb;

  /**
   * The dyc.
   */
  private final T2Number dyc;

  /**
   * The dyd.
   */
  private final T2Number dyd;

  /**
   * The pair values array.
   */
  private final T2PairNumber[] pair;

  /**
   * Create a new object.
   *
   * @param ch    The char string.
   * @param stack The stack.
   * @throws IOException in case of an error
   */
  public T2RlineCurve( List<T2CharString> stack, CharString ch )
      throws IOException {

    super( stack, new short[]{T2RLINECURVE}, ch );

    int n = stack.size();
    int p = n - 6;

    if( p < 0 ) {
      throw new T2MissingNumberException();
    }

    pair = new T2PairNumber[ p / 2 ];

    for( int i = 0; i < p; i += 2 ) {
      T2Number v1 = (T2Number) stack.remove( 0 );
      T2Number v2 = (T2Number) stack.remove( 0 );
      T2PairNumber si = new T2PairNumber( v1, v2 );
      pair[ i / 2 ] = si;
    }

    if( stack.size() < 6 ) {
      throw new T2MissingNumberException();
    }

    dxb = (T2Number) stack.remove( 0 );
    dyb = (T2Number) stack.remove( 0 );
    dxc = (T2Number) stack.remove( 0 );
    dyc = (T2Number) stack.remove( 0 );
    dxd = (T2Number) stack.remove( 0 );
    dyd = (T2Number) stack.remove( 0 );

  }

  /**
   * Getter for dxb.
   *
   * @return the dxb
   */
  public T2Number getDxb() {

    return dxb;
  }

  /**
   * Getter for dxc.
   *
   * @return the dxc
   */
  public T2Number getDxc() {

    return dxc;
  }

  /**
   * Getter for dxd.
   *
   * @return the dxd
   */
  public T2Number getDxd() {

    return dxd;
  }

  /**
   * Getter for dyb.
   *
   * @return the dyb
   */
  public T2Number getDyb() {

    return dyb;
  }

  /**
   * Getter for dyc.
   *
   * @return the dyc
   */
  public T2Number getDyc() {

    return dyc;
  }

  /**
   * Getter for dyd.
   *
   * @return the dyd
   */
  public T2Number getDyd() {

    return dyd;
  }

  @Override
  public int getID() {

    return TYPE_RLINECURVE;
  }

  @Override
  public String getName() {

    return "rlinecurve";
  }

  /**
   * Getter for pair.
   *
   * @return the pair
   */
  public T2PairNumber[] getPair() {

    return pair;
  }

  @Override
  public Object getValue() {

    return pair;
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    writer.writeAttribute( "dxb", dxb );
    writer.writeAttribute( "dyb", dyb );
    writer.writeAttribute( "dxc", dxc );
    writer.writeAttribute( "dyc", dyc );
    writer.writeAttribute( "dxd", dxd );
    writer.writeAttribute( "dyd", dyd );
    for( int i = 0; i < pair.length; i++ ) {
      writer.writeStartElement( "pair" );
      writer.writeAttribute( "id", i );
      writer.writeAttribute( "value", pair[ i ].toString() );
      writer.writeEndElement();
    }
    writer.writeEndElement();
  }

}
