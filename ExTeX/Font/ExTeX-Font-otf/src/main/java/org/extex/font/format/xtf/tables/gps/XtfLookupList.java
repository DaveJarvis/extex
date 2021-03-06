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

package org.extex.font.format.xtf.tables.gps;

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * List for all LookupTables.
 *
 * <p>
 * lookup list table
 * </p>
 * <p>
 * The headers of the GSUB and GPOS tables contain offsets to Lookup List tables
 * (LookupList) for glyph substitution (GSUB table) and glyph positioning (GPOS
 * table). The LookupList table contains an array of offsets to Lookup tables
 * (Lookup). The font developer defines the Lookup sequence in the Lookup array
 * to control the order in which a text-processing client applies lookup data to
 * glyph substitution and positioning operations. LookupCount specifies the
 * total number of Lookup table offsets in the array.
 * </p>
 *
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Type</b></td>
 * <td><b>Name</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>uint16</td>
 * <td>LookupCount</td>
 * <td>Number of lookups in this table</td>
 * </tr>
 * <tr>
 * <td>Offset</td>
 * <td>Lookup[LookupCount]</td>
 * <td>Array of offsets to Lookup tables-from beginning of LookupList -zero
 * based (first lookup is Lookup index = 0)</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class XtfLookupList implements XMLWriterConvertible {

  /**
   * lookup count
   */
  private final int lookupCount;

  /**
   * lookupFactory
   */
  private final LookupTableFactory lookupFactory;

  /**
   * lookup offsets
   */
  private final int[] lookupOffsets;

  /**
   * lookups
   */
  private final XtfLookup[] lookups;

  /**
   * Create a new object
   *
   * @param rar           The input.
   * @param offset        The offset.
   * @param posOffset     The offset of the pos table (GPOS, GSUB).
   * @param lookupFactory The factory for the lookup table.
   * @param xtfGlyph      The glyph name.
   * @throws IOException if an IO-error occurs
   */
  public XtfLookupList( RandomAccessR rar, int posOffset, int offset,
                        LookupTableFactory lookupFactory,
                        XtfGlyphName xtfGlyph )
      throws IOException {

    this.lookupFactory = lookupFactory;
    rar.seek( offset );
    lookupCount = rar.readUnsignedShort();
    lookupOffsets = new int[ lookupCount ];
    lookups = new XtfLookup[ lookupCount ];
    for( int i = 0; i < lookupCount; i++ ) {
      lookupOffsets[ i ] = rar.readUnsignedShort();
    }
    for( int i = 0; i < lookupCount; i++ ) {
      lookups[ i ] =
          new XtfLookup( rar, posOffset, offset + lookupOffsets[ i ],
                         lookupFactory, i, xtfGlyph );
    }
  }

  /**
   * Returns the lookup od {@code null}, if not exists.
   *
   * @param index The index.
   * @return Returns the lookup.
   */
  public XtfLookup getLookup( int index ) {

    if( index >= 0 && index < lookups.length ) {
      return lookups[ index ];
    }
    return null;
  }

  /**
   * Returns the lookup
   *
   * @param feature feature
   * @param index   index
   * @return Returns the lookup
   */
  public XtfLookup getLookup( XtfFeatureList.Feature feature, int index ) {

    if( feature.getLookupCount() > index ) {
      int i = feature.getLookupListIndex( index );
      if( i >= 0 && i < lookups.length ) {
        return lookups[ i ];
      }
    }
    return null;
  }

  /**
   * Returns the lookupCount.
   *
   * @return Returns the lookupCount.
   */
  public int getLookupCount() {

    return lookupCount;
  }

  /**
   * Returns the lookupOffsets.
   *
   * @return Returns the lookupOffsets.
   */
  public int[] getLookupOffsets() {

    return lookupOffsets;
  }

  /**
   * Returns the lookups.
   *
   * @return Returns the lookups.
   */
  public XtfLookup[] getLookups() {

    return lookups;
  }

  /**
   * Returns the info for this class
   *
   * @return Returns the info for this class
   */
  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder( "LookupList\n" );
    buf.append( "   lookup count  : " ).append( lookupCount ).append( '\n' );
    return buf.toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "lookuplist" );
    writer.writeAttribute( "count", lookupCount );

    for( int i = 0; i < lookupCount; i++ ) {
      XtfLookup lu = lookups[ i ];
      lu.writeXML( writer );
    }
    writer.writeEndElement();
  }
}
