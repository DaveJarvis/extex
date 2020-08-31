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

package org.extex.font.format.xtf.tables.gps;

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * Class for a Device Tables.
 *
 * <p>
 * The DeltaValue array lists the number of pixels to adjust specified points on
 * the glyph, or the entire glyph, at each ppem size in the targeted range. In
 * the array, the first index position specifies the number of pixels to add or
 * subtract from the coordinate at the smallest ppem size that needs correction,
 * the second index position specifies the number of pixels to add or subtract
 * from the coordinate at the next ppem size, and so on for each ppem size in
 * the range.
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>StartSize</td>
 * <td>Smallest size to correct-in ppem</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>EndSize</td>
 * <td>Largest size to correct-in ppem</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>DeltaFormat</td>
 * <td>Format of DeltaValue array data: 1, 2, or 3</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>DeltaValue[ ]</td>
 * <td>Array of compressed data</td>
 * </tr>
 * </table>
 *
 * <p>
 * <b>DeltaFormat</b>
 * </p>
 *
 * <p>
 * The 2-, 4-, or 8-bit signed values are packed into uint16's most significant
 * bits first. For example, using a DeltaFormat of 2 (4-bit values), an array of
 * values equal to {1, 2, 3, -1} would be represented by the DeltaValue 0x123F.
 * </p>
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>2</td>
 * <td>Signed 2-bit value, 8 values per uint16</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>4</td>
 * <td>Signed 4-bit value, 4 values per uint16</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>8</td>
 * <td>Signed 8-bit value, 2 values per uint16</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class DeviceTable implements XMLWriterConvertible {

  /**
   * Format of DeltaValue array data: 1, 2, or 3.
   */
  private final int deltaFormat;

  /**
   * Array of compressed data.
   */
  private final int[] deltaValues;

  /**
   * Largest size to correct-in ppem.
   */
  private final int endSize;

  /**
   * Smallest size to correct-in ppem.
   */
  private final int startSize;

  /**
   * Creates a new object.
   *
   * @param rar    The input.
   * @param offset The offset.
   * @throws IOException if a IO-error occurred.
   */
  public DeviceTable( RandomAccessR rar, int offset ) throws IOException {

    rar.seek( offset );
    startSize = rar.readUnsignedShort();
    endSize = rar.readUnsignedShort();
    deltaFormat = rar.readUnsignedShort();
    int count = endSize - startSize + 1;
    deltaValues = new int[ count ];
    for( int i = 0; i < count; i++ ) {
      deltaValues[ i ] = rar.readUnsignedShort();
    }

  }

  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "device" );
    writer.writeAttribute( "startSize", startSize );
    writer.writeAttribute( "endSize", endSize );
    writer.writeAttribute( "deltaFormat", deltaFormat );
    for( int i = 0; i < deltaValues.length; i++ ) {
      writer.writeStartElement( "value" );
      writer.writeAttribute( "id", i );
      writer.writeAttribute( "value", deltaValues[ i ] );
      writer.writeEndElement();
    }
    writer.writeEndElement();
  }

}
