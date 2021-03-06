/*
 * Copyright (C) 2004-2005 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf.tables;

import org.extex.font.format.xtf.XtfReader;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * The 'prep' table stores the instructions that make up the control value
 * program, a set of TrueType instructions that will be executed once when the
 * font is first accessed and again whenever the font, point size or
 * transformation matrix change. It consists of an ordered list of instructions
 * opcodes. Each opcode is a byte. The tag 'prep', referring to the preProgram,
 * is anachronistic but some people still call the control value program the
 * preProgram.
 *
 *  <table> <caption>TBD</caption> <tbody>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * </tbody>
 * <tr>
 * <td>BYTE[ ]</td>
 * <td> Set of instructions executed whenever point size or font or
 * transformation change</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TtfTablePREP extends AbstractXtfTable
    implements
    XtfTable,
    XMLWriterConvertible {

  /**
   * instructions
   */
  private final byte[] instructions;

  /**
   * Create a new object.
   *
   * @param tablemap the table map
   * @param de       directory entry
   * @param rar      input
   * @throws IOException if an IO-error occurs
   */
  public TtfTablePREP( XtfTableMap tablemap, XtfTableDirectory.Entry de,
                       RandomAccessR rar ) throws IOException {

    super( tablemap );
    rar.seek( de.getOffset() );
    instructions = new byte[ de.getLength() ];
    for( int i = 0; i < de.getLength(); i++ ) {
      instructions[ i ] = (byte) rar.readUnsignedByte();
    }
  }

  /**
   * Returns the instructions
   *
   * @return Returns the instructions
   */
  public byte[] getInstructions() {

    return instructions;
  }

  public String getShortcut() {

    return "prep";
  }

  /**
   * Get the table type, as a table directory value.
   *
   * @return Returns the table type
   */
  public int getType() {

    return XtfReader.PREP;
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writeStartElement( writer );
    writer.writeByteArray( instructions );
    writer.writeEndElement();
  }

}
