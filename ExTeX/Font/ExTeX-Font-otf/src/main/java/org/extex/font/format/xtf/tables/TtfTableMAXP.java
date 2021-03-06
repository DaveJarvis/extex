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
 * The 'maxp' table establishes the memory requirements for a font.
 *
 *  <table> <caption>TBD</caption> <tbody>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * </tbody>
 * <tr>
 * <td>Fixed</td>
 * <td>Table version number</td>
 * <td> 0x00010000 for version 1.0.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>numGlyphs</td>
 * <td> The number of glyphs in the font.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxPoints</td>
 * <td> Maximum points in a non-composite glyph.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxContours</td>
 * <td> Maximum contours in a non-composite glyph.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxCompositePoints</td>
 * <td> Maximum points in a composite glyph.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxCompositeContours</td>
 * <td> Maximum contours in a composite glyph.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxZones</td>
 * <td> 1 if instructions do not use the twilight zone (Z0), or 2 if
 * instructions do use Z0; should be set to 2 in most cases.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxTwilightPoints</td>
 * <td> Maximum points used in Z0.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxStorage</td>
 * <td> Number of Storage Area locations.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxFunctionDefs</td>
 * <td> Number of FDEFs.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxInstructionDefs</td>
 * <td>Number of IDEFs.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxStackElements</td>
 * <td> Maximum stack depth 2.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxSizeOfInstructions</td>
 * <td> Maximum byte count for glyph instructions.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxComponentElements</td>
 * <td> Maximum number of components referenced at &ldquo;top level&rdquo; for
 * any composite glyph.</td>
 * </tr>
 * <tr>
 * <td>USHORT</td>
 * <td>maxComponentDepth</td>
 * <td> Maximum levels of recursion; 1 for simple components.</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class TtfTableMAXP extends AbstractXtfTable
    implements
    XtfTable,
    XMLWriterConvertible {

  /**
   * maxComponentDepth
   */
  private final int maxComponentDepth;

  /**
   * maxComponentElements
   */
  private final int maxComponentElements;

  /**
   * maxCompositeContours
   */
  private final int maxCompositeContours;

  /**
   * maxCompositePoints
   */
  private final int maxCompositePoints;

  /**
   * maxContours
   */
  private final int maxContours;

  /**
   * maxFunctionDefs
   */
  private final int maxFunctionDefs;

  /**
   * maxInstructionDefs
   */
  private final int maxInstructionDefs;

  /**
   * maxPoints
   */
  private final int maxPoints;

  /**
   * maxSizeOfInstructions
   */
  private final int maxSizeOfInstructions;

  /**
   * maxStackElements
   */
  private final int maxStackElements;

  /**
   * maxStorage
   */
  private final int maxStorage;

  /**
   * maxTwilightPoints
   */
  private final int maxTwilightPoints;

  /**
   * maxZones
   */
  private final int maxZones;

  /**
   * numGlyphs
   */
  private final int numGlyphs;

  /**
   * version
   */
  private final int version;

  /**
   * Create a new object.
   *
   * @param tablemap the table map
   * @param de       directory entry
   * @param rar      input
   * @throws IOException if an IO-error occurs
   */
  public TtfTableMAXP( XtfTableMap tablemap, XtfTableDirectory.Entry de,
                       RandomAccessR rar ) throws IOException {

    super( tablemap );
    rar.seek( de.getOffset() );
    version = rar.readInt();
    numGlyphs = rar.readUnsignedShort();
    maxPoints = rar.readUnsignedShort();
    maxContours = rar.readUnsignedShort();
    maxCompositePoints = rar.readUnsignedShort();
    maxCompositeContours = rar.readUnsignedShort();
    maxZones = rar.readUnsignedShort();
    maxTwilightPoints = rar.readUnsignedShort();
    maxStorage = rar.readUnsignedShort();
    maxFunctionDefs = rar.readUnsignedShort();
    maxInstructionDefs = rar.readUnsignedShort();
    maxStackElements = rar.readUnsignedShort();
    maxSizeOfInstructions = rar.readUnsignedShort();
    maxComponentElements = rar.readUnsignedShort();
    maxComponentDepth = rar.readUnsignedShort();
  }

  /**
   * Returns the max component depth
   *
   * @return Returns the max component depth
   */
  public int getMaxComponentDepth() {

    return maxComponentDepth;
  }

  /**
   * Returns the max component elements
   *
   * @return Returns the max component elements
   */
  public int getMaxComponentElements() {

    return maxComponentElements;
  }

  /**
   * Returns the max composite contours
   *
   * @return Returns the max composite contours
   */
  public int getMaxCompositeContours() {

    return maxCompositeContours;
  }

  /**
   * Returns the max composite points
   *
   * @return Returns the max composite points
   */
  public int getMaxCompositePoints() {

    return maxCompositePoints;
  }

  /**
   * Returns the max contours
   *
   * @return Returns the max contours
   */
  public int getMaxContours() {

    return maxContours;
  }

  /**
   * Returns the max function defs
   *
   * @return Returns the max function defs
   */
  public int getMaxFunctionDefs() {

    return maxFunctionDefs;
  }

  /**
   * Returns the max instruction defs
   *
   * @return Returns the max instruction defs
   */
  public int getMaxInstructionDefs() {

    return maxInstructionDefs;
  }

  /**
   * Returns the max points
   *
   * @return Returns the max points
   */
  public int getMaxPoints() {

    return maxPoints;
  }

  /**
   * Returns the max size of instructions
   *
   * @return Returns the max size of instructions
   */
  public int getMaxSizeOfInstructions() {

    return maxSizeOfInstructions;
  }

  /**
   * Returns the max stack elements
   *
   * @return Returns the max stack elements
   */
  public int getMaxStackElements() {

    return maxStackElements;
  }

  /**
   * Returns the max storage
   *
   * @return Returns the max storage
   */
  public int getMaxStorage() {

    return maxStorage;
  }

  /**
   * Returns the max twilight points
   *
   * @return Returns the max twilight points
   */
  public int getMaxTwilightPoints() {

    return maxTwilightPoints;
  }

  /**
   * Returns the max zones
   *
   * @return Returns the max zones
   */
  public int getMaxZones() {

    return maxZones;
  }

  /**
   * Returns the number of glyphs
   *
   * @return Returns the number of glyphs
   */
  public int getNumGlyphs() {

    return numGlyphs;
  }

  public String getShortcut() {

    return "maxp";
  }

  /**
   * Get the table type, as a table directory value.
   *
   * @return Returns the table type
   */
  public int getType() {

    return XtfReader.MAXP;
  }

  /**
   * Returns the versionNumber.
   *
   * @return Returns the versionNumber.
   */
  public int getVersion() {

    return version;
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writeStartElement( writer );
    writer.writeAttribute( "version", XtfReader
        .convertIntToHexString( version ) );
    writer.writeAttribute( "numberofglyphs", String.valueOf( numGlyphs ) );
    writer.writeAttribute( "maxpoints", String.valueOf( maxPoints ) );
    writer.writeAttribute( "maxcontours", String.valueOf( maxContours ) );
    writer.writeAttribute( "maxcompositepoints", String
        .valueOf( maxCompositePoints ) );
    writer.writeAttribute( "maxcompositecontours", String
        .valueOf( maxCompositeContours ) );
    writer.writeAttribute( "maxzones", String.valueOf( maxZones ) );
    writer.writeAttribute( "maxtwilightpoints", String
        .valueOf( maxTwilightPoints ) );
    writer.writeAttribute( "maxstorage", String.valueOf( maxStorage ) );
    writer.writeAttribute( "maxfunctiondefs", String
        .valueOf( maxFunctionDefs ) );
    writer.writeAttribute( "maxinstructionsdefs", String
        .valueOf( maxInstructionDefs ) );
    writer.writeAttribute( "maxstackelements", String
        .valueOf( maxStackElements ) );
    writer.writeAttribute( "maxsizeofinstructions", String
        .valueOf( maxSizeOfInstructions ) );
    writer.writeAttribute( "maxcomponentelements", String
        .valueOf( maxComponentElements ) );
    writer.writeAttribute( "maxcomponentdepth", String
        .valueOf( maxComponentDepth ) );
    writer.writeEndElement();
  }

}
