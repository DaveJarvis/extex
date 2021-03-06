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

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;
import java.util.List;

/**
 * T2: hintmask: hintmask (19 + mask).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2HintMask extends T2AbstractHintMask {

  /**
   * Returns the binary value with zeros.
   *
   * @param valueMask The value.
   * @return Returns the binary string.
   */
  static String toBin( long valueMask ) {

    StringBuilder buf = new StringBuilder( 128 );
    String bin = Long.toBinaryString( valueMask );
    buf.append(
        "0000000000000000000000000000000000000000000000000000000000000000" );
    buf.append(
        "0000000000000000000000000000000000000000000000000000000000000000" );
    buf.append( bin );
    int le = 8;
    int bl = bin.length();
    if( bl <= 8 ) {
      le = 8;
    }
    else if( bl <= 16 ) {
      le = 16;
    }
    else if( bl <= 24 ) {
      le = 24;
    }
    else if( bl <= 32 ) {
      le = 32;
    }
    else if( bl <= 40 ) {
      le = 40;
    }
    else {
      le = 48;
    }

    return buf.substring( buf.length() - le );
  }

  /**
   * The hintmask.
   */
  private long mask;

  /**
   * The values.
   */
  private final T2Number[] val;

  /**
   * Create a new object.
   *
   * @param stack the stack
   * @param ch    The char string.
   * @param rar   The input.
   * @throws IOException if an IO-error occurs.
   */
  public T2HintMask( List<T2CharString> stack, CharString ch,
                     RandomAccessR rar )
      throws IOException {

    super( stack, new short[]{T2HINTMASK}, ch );

    int n = stack.size();
    val = new T2Number[ n ];
    ch.addHints( n );

    for( int i = 0; i < n; i++ ) {
      val[ i ] = (T2Number) stack.get( i );
    }

    readMask( ch, rar );
  }

  @Override
  public int getID() {

    return TYPE_HINTMASK;
  }

  /**
   * Getter for mask.
   *
   * @return the mask
   */
  public long getMask() {

    return mask;
  }

  /**
   * Return the mask as binary string.
   *
   * @return Return the mask as binary string.
   */
  public String getMaskBin() {

    return toBin( mask );
  }

  @Override
  public String getName() {

    return "hintmask";
  }

  /**
   * Getter for val.
   *
   * @return the val
   */
  public T2Number[] getVal() {

    return val;
  }

  @Override
  public Object getValue() {

    return val;
  }

  /**
   * Read the mask.
   *
   * @param ch  The charstring.
   * @param rar The input.
   * @throws IOException if a IO-error occurred.
   */
  private void readMask( CharString ch, RandomAccessR rar ) throws IOException {

    int cnt = (ch.getActualHints() / 2 - 1) / 8;
    mask = rar.readUnsignedByte();
    for( int i = 0; i < cnt; i++ ) {
      mask = mask << 8;
      mask += rar.readUnsignedByte();
    }
  }

  @Override
  public String toText() {

    StringBuilder buf = new StringBuilder();
    for( int i = 0; i < val.length; i++ ) {
      buf.append( val[ i ].toString() ).append( " " );
    }
    buf.append( getName() ).append( " " );
    buf.append( toBin( mask ) );
    return buf.toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    writer.writeAttribute( "mask", toBin( mask ) );
    StringBuilder buf = new StringBuilder();
    for( int i = 0; i < val.length; i++ ) {
      buf.append( " " ).append( val[ i ] );
    }
    writer.writeAttribute( "values", buf.toString().trim() );
    writer.writeEndElement();
  }

}
