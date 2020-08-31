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

package org.extex.font.format.xtf.tables;

import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * Class for a range record.
 *
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>Start</td>
 * <td>First GlyphID in the range</td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>End</td>
 * <td>Last GlyphID in the range</td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>StartCoverageIndex</td>
 * <td>Coverage Index of first GlyphID in range</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class RangeRecord implements XMLWriterConvertible {

  /**
   * Last GlyphID in the range.
   */
  private final int end;

  /**
   * First GlyphID in the range.
   */
  private final int start;

  /**
   * Coverage Index of first GlyphID in range.
   */
  private final int startCoverageIndex;

  /**
   * Creates a new object.
   *
   * @param rar input
   * @throws IOException if a io-error occurred.
   */
  public RangeRecord( RandomAccessR rar ) throws IOException {

    start = rar.readUnsignedShort();
    end = rar.readUnsignedShort();
    startCoverageIndex = rar.readUnsignedShort();
  }

  /**
   * Getter for end.
   *
   * @return the end
   */
  public int getEnd() {

    return end;
  }

  /**
   * Getter for start.
   *
   * @return the start
   */
  public int getStart() {

    return start;
  }

  /**
   * Getter for startCoverageIndex.
   *
   * @return the startCoverageIndex
   */
  public int getStartCoverageIndex() {

    return startCoverageIndex;
  }

  /**
   * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
   *org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "rangerecord" );
    writer.writeAttribute( "start", start );
    writer.writeAttribute( "end", end );
    writer.writeAttribute( "startCoverageIndex", startCoverageIndex );
    writer.writeEndElement();
  }

}
