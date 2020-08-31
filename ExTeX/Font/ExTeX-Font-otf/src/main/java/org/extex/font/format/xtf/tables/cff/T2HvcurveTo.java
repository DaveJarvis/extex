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
 * hvcurveto: dx1 dx2 dy2 dy3 {dya dxb dyb dxc dxd dxe dye dyf}* dxf? hvcurveto
 * (31) : {dxa dxb dyb dyc dyd dxe dye dxf}+ dyf? hvcurveto (31).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2HvcurveTo extends T2PathConstruction {

  /**
   * The dx1.
   */
  private T2Number dx1;

  /**
   * The dx2.
   */
  private T2Number dx2;

  /**
   * The dxf.
   */
  private T2Number dxf;

  /**
   * The dy2.
   */
  private T2Number dy2;

  /**
   * The dy3.
   */
  private T2Number dy3;

  /**
   * The eight values array.
   */
  private T2EightNumber[] eight;

  /**
   * Type 1 command.
   */
  private boolean type1;

  /**
   * Create a new object.
   *
   * @param ch    The char string.
   * @param stack The stack.
   * @throws IOException in case of an error
   */
  public T2HvcurveTo( List<T2CharString> stack, CharString ch )
      throws IOException {

    super( stack, new short[]{T2HVCURVETO}, ch );

    int n = stack.size();

    try {
      type1 = n % 8 != 0 && n % 8 != 1;

      if( type1 ) {

        dx1 = (T2Number) stack.remove( 0 );
        dx2 = (T2Number) stack.remove( 0 );
        dy2 = (T2Number) stack.remove( 0 );
        dy3 = (T2Number) stack.remove( 0 );

      }

      n = stack.size();
      eight = new T2EightNumber[ n / 8 ];

      for( int i = 0; i < n; i += 8 ) {
        T2Number v1 = (T2Number) stack.remove( 0 );
        T2Number v2 = (T2Number) stack.remove( 0 );
        T2Number v3 = (T2Number) stack.remove( 0 );
        T2Number v4 = (T2Number) stack.remove( 0 );
        T2Number v5 = (T2Number) stack.remove( 0 );
        T2Number v6 = (T2Number) stack.remove( 0 );
        T2Number v7 = (T2Number) stack.remove( 0 );
        T2Number v8 = (T2Number) stack.remove( 0 );
        T2EightNumber si =
            new T2EightNumber( v1, v2, v3, v4, v5, v6, v7, v8 );
        eight[ i / 8 ] = si;

        if( stack.size() == 1 ) {
          dxf = (T2Number) stack.remove( 0 );
        }
      }
    } catch( IndexOutOfBoundsException e ) {
      // TODO mgn remove!!!
      System.out.println();
    }
  }

  /**
   * Getter for dx1.
   *
   * @return the dx1
   */
  public T2Number getDx1() {

    return dx1;
  }

  /**
   * Getter for dx2.
   *
   * @return the dx2
   */
  public T2Number getDx2() {

    return dx2;
  }

  /**
   * Getter for dxf.
   *
   * @return the dxf
   */
  public T2Number getDxf() {

    return dxf;
  }

  /**
   * Getter for dy2.
   *
   * @return the dy2
   */
  public T2Number getDy2() {

    return dy2;
  }

  /**
   * Getter for dy3.
   *
   * @return the dy3
   */
  public T2Number getDy3() {

    return dy3;
  }

  /**
   * Getter for eight.
   *
   * @return the eight
   */
  public T2EightNumber[] getEight() {

    return eight;
  }

  @Override
  public int getID() {

    return TYPE_HVCURVETO;
  }

  @Override
  public String getName() {

    return "hvcurveto";
  }

  @Override
  public Object getValue() {

    return eight;
  }

  @Override
  public String toText() {

    StringBuilder buf = new StringBuilder();

    if( type1 ) {
      buf.append( dx1.toString() ).append( ' ' );
      buf.append( dx2.toString() ).append( ' ' );
      buf.append( dy2.toString() ).append( ' ' );
      buf.append( dy3.toString() ).append( ' ' );
    }

    for( int i = 0; i < eight.length; i++ ) {
      buf.append( eight[ i ].toString() ).append( ' ' );
    }

    if( dxf != null ) {
      buf.append( dxf.toString() ).append( ' ' );
    }

    return buf.append( getName() ).toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    writer.writeAttribute( "dx1", dx1 );
    writer.writeAttribute( "dx2", dx2 );
    writer.writeAttribute( "dy2", dy2 );
    writer.writeAttribute( "dy3", dy3 );
    writer.writeAttribute( "dxf", dxf );
    if( eight != null ) {
      for( int i = 0; i < eight.length; i++ ) {
        writer.writeStartElement( "pair" );
        writer.writeAttribute( "id", i );
        writer.writeAttribute( "value", eight[ i ].toString() );
        writer.writeEndElement();
      }
    }
    writer.writeEndElement();
  }

}
