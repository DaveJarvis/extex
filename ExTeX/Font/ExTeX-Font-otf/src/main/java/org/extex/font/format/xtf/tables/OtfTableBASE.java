/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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
 * The Baseline table (BASE) provides information used to align glyphs of
 * different scripts and sizes in a line of text, whether the glyphs are in the
 * same font or in different fonts. To improve text layout, the Baseline table
 * also provides minimum (min) and maximum (max) glyph extent values for each
 * script, language system, or feature in a font.
 *
 * <p>
 * <img src="TTFTableBASE.png" alt="table base">
 * </p>
 *
 * <table> <caption>TBD</caption> <thead>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * </thead>
 * <tr>
 * <td>fixed32</td>
 * <td>Version</td>
 * <td> Version of the BASE table-initially 0x00010000</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>HorizAxis</td>
 * <td> Offset to horizontal Axis table-from beginning of BASE table-may be
 * NULL</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>VertAxis</td>
 * <td> Offset to vertical Axis table-from beginning of BASE table-may be
 * NULL</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class OtfTableBASE extends AbstractXtfTable
    implements
    XtfTable,
    XMLWriterConvertible {

  /**
   * Axis (vertical or horizontal)
   *
   * <table> <caption>TBD</caption> <thead>
   * <tr>
   * <td><b>Type</b></td>
   * <td><b>Name</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * </thead>
   * <tr>
   * <td>Offset</td>
   * <td>BaseTagList</td>
   * <td> Offset to BaseTagList table-from beginning of Axis table-may be
   * NULL</td>
   * </tr>
   * <tr>
   * <td>Offset</td>
   * <td>BaseScriptList</td>
   * <td> Offset to BaseScriptList table-from beginning of Axis table</td>
   * </tr>
   * </table>
   */
  public static class Axis implements XMLWriterConvertible {

    /**
     * basescript offset
     */
    private final int baseScriptListOffset;

    /**
     * the basetaglist
     */
    private final BaseTagList basetaglist;

    /**
     * basetaglist offset
     */
    private final int baseTagListOffset;

    /**
     * the name
     */
    private final String name;

    /**
     * Create a new object.
     *
     * @param rar    the input
     * @param offset the offset
     * @param n      the name of the table
     * @throws IOException if an IO-error occurs.
     */
    public Axis( RandomAccessR rar, int offset, String n ) throws IOException {

      name = n;
      rar.seek( offset );

      baseTagListOffset = rar.readUnsignedShort();
      baseScriptListOffset = rar.readUnsignedShort();

      basetaglist =
          new BaseTagList( rar, offset + baseTagListOffset, name );
    }

    /**
     * Returns the baseScriptListOffset.
     *
     * @return Returns the baseScriptListOffset.
     */
    public int getBaseScriptListOffset() {

      return baseScriptListOffset;
    }

    /**
     * Getter for basetaglist.
     *
     * @return the basetaglist
     */
    public BaseTagList getBasetaglist() {

      return basetaglist;
    }

    /**
     * Returns the baseTagListOffset.
     *
     * @return Returns the baseTagListOffset.
     */
    public int getBaseTagListOffset() {

      return baseTagListOffset;
    }

    /**
     * Returns the name.
     *
     * @return Returns the name.
     */
    public String getName() {

      return name;
    }

    /**
     * org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "axis" );
      writer.writeAttribute( "name", name );
      writer.writeComment( "incomplete" );
      writer.writeEndElement();
    }
  }

  /**
   * The BaseTagList table identifies the baselines for all scripts in the
   * font that are rendered in the same text direction. Each baseline is
   * identified with a 4-byte baseline tag.
   */
  public static class BaseTagList implements XMLWriterConvertible {

    /**
     * the name
     */
    private final String name;

    /**
     * Create a new object.
     *
     * @param rar    the input
     * @param offset the offset
     * @param n      the name
     * @throws IOException if an IO-error occurs.
     */
    public BaseTagList( RandomAccessR rar, int offset, String n )
        throws IOException {

      name = n;
      rar.seek( offset );

    }

    /**
     * org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "basetaglist" );
      writer.writeAttribute( "name", name );
      writer.writeComment( "incomplete" );
      writer.writeEndElement();
    }

  }

  /**
   * horiz. Axis offset
   */
  private final int horizAxisOffset;

  /**
   * horizontal Axis
   */
  private Axis horizontal;

  /**
   * version
   */
  private final int version;

  /**
   * vert. Axis offset
   */
  private final int vertAxisOffset;

  /**
   * vertical Axis
   */
  private Axis vertical;

  /**
   * Create a new object
   *
   * @param tablemap the table map
   * @param de       entry
   * @param rar      input
   * @throws IOException if an IO-error occurs
   */
  public OtfTableBASE( XtfTableMap tablemap, XtfTableDirectory.Entry de,
                       RandomAccessR rar ) throws IOException {

    super( tablemap );
    rar.seek( de.getOffset() );

    version = rar.readUnsignedShort();
    horizAxisOffset = rar.readUnsignedShort();
    vertAxisOffset = rar.readUnsignedShort();

    // vertical = new Axis(rar, de.getOffset() + vertAxisOffset,
    // "vertical");
    // horizontal = new Axis(rar, de.getOffset() + horizAxisOffset,
    // "horizontal");

    // incomplete
  }

  /**
   * Returns the horizAxisOffset.
   *
   * @return Returns the horizAxisOffset.
   */
  public int getHorizAxisOffset() {

    return horizAxisOffset;
  }

  /**
   * Getter for horizontal.
   *
   * @return the horizontal
   */
  public Axis getHorizontal() {

    return horizontal;
  }

  public String getShortcut() {

    return "base";
  }

  /**
   * Get the table type, as a table directory value.
   *
   * @return Returns the table type
   */
  public int getType() {

    return XtfReader.BASE;
  }

  /**
   * Returns the version.
   *
   * @return Returns the version.
   */
  public int getVersion() {

    return version;
  }

  /**
   * Returns the vertAxisOffset.
   *
   * @return Returns the vertAxisOffset.
   */
  public int getVertAxisOffset() {

    return vertAxisOffset;
  }

  /**
   * Getter for vertical.
   *
   * @return the vertical
   */
  public Axis getVertical() {

    return vertical;
  }

  /**
   * org.extex.util.xml.XMLStreamWriter)
   */
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writeStartElement( writer );
    writer.writeComment( "incomplete" );
    writer.writeEndElement();
  }

}
