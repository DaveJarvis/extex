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
 * T2: cntrmask: cntrmask (20 + mask).
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class T2CntrMask extends T2AbstractHintMask {

  /**
   * The cntrmask.
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
  public T2CntrMask( List<T2CharString> stack, CharString ch,
                     RandomAccessR rar )
      throws IOException {

    super( stack, new short[]{T2CNTRMASK}, ch );

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

    return TYPE_CNTRMASK;
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

    return T2HintMask.toBin( mask );
  }

  @Override
  public String getName() {

    return "cntrmask";
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
      buf.append( val[ i ].toString() ).append( ' ' );
    }
    buf.append( getName() ).append( ' ' ).append( T2HintMask.toBin( mask ) );
    return buf.toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    writer.writeAttribute( "mask", T2HintMask.toBin( mask ) );
    StringBuilder buf = new StringBuilder();
    for( int i = 0; i < val.length; i++ ) {
      buf.append( ' ' ).append( val[ i ] );
    }
    writer.writeAttribute( "values", buf.toString().trim() );
    writer.writeEndElement();
  }

}
