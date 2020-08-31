/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

import java.io.IOException;
import java.util.List;

/**
 * Abstract class for all SID-values.
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */

public abstract class T2TDOSID extends T2TopDICTOperator {

  /**
   * bytes
   */
  private final short[] bytes;

  /**
   * The string for the SID.
   */
  private String sidstring;

  /**
   * value
   */
  private final int value;

  /**
   * Create a new object.
   *
   * @param stack the stack
   * @param id    the operator-id for the value
   * @throws IOException if an IO-error occurs.
   */
  protected T2TDOSID( List<T2CharString> stack, short[] id )
      throws IOException {

    if( stack.size() < 1 ) {
      throw new T2MissingNumberException();
    }
    value = ((T2Number) stack.get( 0 )).getInteger();
    bytes = convertStackaddID( stack, id );
  }

  @Override
  public short[] getBytes() {

    return bytes;
  }

  /**
   * Returns the SID.
   *
   * @return Returns the SID.
   */
  public int getSID() {

    return value;
  }

  @Override
  public Object getValue() {

    return sidstring;
  }

  /**
   * org.extex.font.format.xtf.tables.OtfTableCFF, int,
   * org.extex.font.format.xtf.tables.cff.CffFont)
   */
  @Override
  public void init( RandomAccessR rar, OtfTableCFF cff, int baseoffset,
                    CffFont cffFont ) throws IOException {

    sidstring = cff.getStringIndex( getSID() );
  }

  @Override
  public String toString() {

    return String.valueOf( value );
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( getName() );
    writer.writeAttribute( "value", getValue() );
    writer.writeEndElement();

  }
}
