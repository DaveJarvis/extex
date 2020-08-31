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
 * Lookup tables provide a way of looking up information about a glyph index.
 *
 * <p>
 * Some lookup tables do simple array-type lookup. Others involve groupings,
 * allowing you to treat many different glyph indexes in the same way (that is,
 * to look up the same information about them). The top-level description of a
 * lookup table contains a format number and a format-specific header.
 * </p>
 * <p>
 * The format of the Lookup Table header is as follows:
 * </p>
 *
 * <table>
 * <caption>TBD</caption>
 * <tbody>
 * <tr>
 * <th>Type</th>
 * <th>Name</th>
 * <th>Description</th>
 * </tr>
 * </tbody>
 * <tr>
 * <td>uint16</td>
 * <td>format</td>
 * <td>Format of this lookup table. There are five lookup table formats, each
 * with a format number.</td>
 * </tr>
 * <tr>
 * <td>variable</td>
 * <td>fsHeader</td>
 * <td>Format-specific header (each of these is described in the following
 * sections), followed by the actual lookup data. The details of the fsHeader
 * structure are given with the different formats.</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class XtfLookup implements XMLWriterConvertible {

  /**
   * If set, skips over base glyphs.
   */
  public static final int FLAG_IGNORE_BASE_GLYPHS = 0x0002;

  /**
   * If set, skips over ligatures.
   */
  public static final int FLAG_IGNORE_BASE_LIGATURES = 0x0004;

  /**
   * If set, skips over combining marks.
   */
  public static final int FLAG_IGNORE_BASE_MARKS = 0x0008;

  /**
   * If not zero, skips over all marks of attachment type different from
   * specified.
   */
  public static final int FLAG_MARK_ATTACHMENT_TYPE = 0xFF00;

  /**
   * For future use.
   */
  public static final int FLAG_RESERVED = 0x00f0;

  /**
   * This bit relates only to the correct processing of the cursive attachment
   * lookup type (GPOS lookup type 3). When this bit is set, the last glyph in
   * a given sequence to which the cursive attachment lookup is applied, will
   * be positioned on the baseline. Note: Setting of this bit is not intended
   * to be used by operating systems or applications to determine text
   * direction.
   */
  public static final int FLAG_RIGHT_TO_LEFT = 0x0001;

  /**
   * 1 - Single adjustment -Adjust position of a single glyph
   */
  public static final int GPOS_1_SINGLE = 1;

  /**
   * 2 - Pair adjustment - Adjust position of a pair of glyphs
   */
  public static final int GPOS_2_PAIR = 2;

  /**
   * 3 - Cursive attachment - Attach cursive glyphs
   */
  public static final int GPOS_3_CURSIVE_ATTACHMENT = 3;

  /**
   * 4 - MarkToBase attachment - Attach a combining mark to a base glyph
   */
  public static final int GPOS_4_MARKTOBASE_ATTACHMENT = 4;

  /**
   * 5 - MarkToLigature attachment - Attach a combining mark to a ligature
   */
  public static final int GPOS_5_MARKTOLIGATURE_ATTACHMENT = 5;

  /**
   * 6 - MarkToMark attachment - Attach a combining mark to another mark
   */
  public static final int GPOS_6_MARKTOMARK_ATTACHMENT = 6;

  /**
   * 7 - Context positioning - Position one or more glyphs in context
   */
  public static final int GPOS_7_CONTEXT_POSITIONING = 7;

  /**
   * 8 - Chained Context positioning - Position one or more glyphs in chained
   * context
   */
  public static final int GPOS_8_CHAINED_CONTEXT_POSITIONING = 8;

  /**
   * 9 - Extension positioning - Extension mechanism for other positionings
   */
  public static final int GPOS_9_EXTENSION_POSITIONING = 9;

  /**
   * 1 - Single - Replace one glyph with one glyph
   */
  public static final int GSUB_1_SINGLE = 1;

  /**
   * 2 - Multiple - Replace one glyph with more than one glyph
   */
  public static final int GSUB_2_MULTIPLE = 2;

  /**
   * 3 - Alternate - Replace one glyph with one of many glyphs
   */
  public static final int GSUB_3_ALTERNATE = 3;

  /**
   * 4 - Ligature - Replace multiple glyphs with one glyph
   */
  public static final int GSUB_4_LIGATURE = 4;

  /**
   * 5 - Context - Replace one or more glyphs in context
   */
  public static final int GSUB_5_CONTEXT = 5;

  /**
   * 6 - Chaining - Context Replace one or more glyphs in chained context
   */
  public static final int GSUB_6_CHAINING_CONTEXTUAL = 6;

  /**
   * 7 - Extension Substitution - Extension mechanism for other substitutions
   * (i.e. this excludes the Extension type substitution itself)
   */
  public static final int GSUB_7_EXTENSION = 7;

  /**
   * 8 - Reverse chaining context single - Applied in reverse order, replace
   * single glyph in chaining context
   */
  public static final int GSUB_8_REVERSE_CHAINING_CONTEXT_SINGLE = 8;

  /**
   * The name of the lookup types (GPOS).
   */
  public static final String[] LOOKUP_TYPE_NAMES_GPOS = {"Single", "Pair",
      "Cursive", "MarkToBase", "MarkToLigature", "MarkToMark", "Context",
      "Chained", "Extension"};

  /**
   * The name of the lookup types (GSUB).
   */
  public static final String[] LOOKUP_TYPE_NAMES_GSUB = {"Single",
      "Multiple", "Alternate", "Ligature", "Context", "Chaining Context",
      "Extension Substitution", "Reverse chaining context single"};

  /**
   * flag
   */
  private final int flag;

  /**
   * The index.
   */
  private final int index;

  /**
   * The lookup factory.
   */
  private final LookupTableFactory lookupFactory;

  /**
   * subtable count
   */
  private final int subTableCount;

  /**
   * subtable offsets
   */
  private final int[] subTableOffsets;

  /**
   * subtables
   */
  private final XtfLookupTable[] subTables;

  /**
   * type
   */
  private final int type;

  /**
   * Create a new object
   *
   * @param rar           input
   * @param offset        offset
   * @param posOffset     The offset of the pos table (GSUB, GPOS).
   * @param lookupFactory The factory for the lookup table.
   * @param index         The index.
   * @param xtfGlyph      The glyph name.
   * @throws IOException if an IO-error occurs
   */
  public XtfLookup( RandomAccessR rar, int posOffset, int offset,
                    LookupTableFactory lookupFactory, int index,
                    XtfGlyphName xtfGlyph )
      throws IOException {

    this.index = index;
    this.lookupFactory = lookupFactory;
    rar.seek( offset );

    type = rar.readUnsignedShort();
    flag = rar.readUnsignedShort();
    subTableCount = rar.readUnsignedShort();
    subTableOffsets = new int[ subTableCount ];
    subTables = new XtfLookupTable[ subTableCount ];
    for( int i = 0; i < subTableCount; i++ ) {
      subTableOffsets[ i ] = rar.readUnsignedShort();
    }
    for( int i = 0; i < subTableCount; i++ ) {
      subTables[ i ] =
          lookupFactory.read( rar, posOffset, type, offset
              + subTableOffsets[ i ], xtfGlyph );
    }
  }

  /**
   * Check, if the flag is set.
   *
   * @param value The value.
   * @param flag  The flag.
   * @return Return {@code true}, if the flag is set.
   */
  private boolean checkFlag( int value, int flag ) {

    int erg = value & flag;
    return erg > 0;
  }

  /**
   * Getter for index.
   *
   * @return the index
   */
  public int getIndex() {

    return index;
  }

  /**
   * Returns the subtable
   *
   * @param i index
   * @return Returns the subtable
   */
  public XtfLookupTable getSubtable( int i ) {

    return subTables[ i ];
  }

  /**
   * Returns the subtable count
   *
   * @return Returns the subtablecount
   */
  public int getSubtableCount() {

    return subTableCount;
  }

  /**
   * Returns the type
   *
   * @return Returns the type
   */
  public int getType() {

    return type;
  }

  /**
   * Returns the type as string.
   *
   * @return Returns the type as string.
   */
  public String getTypeAsString() {

    return lookupFactory.lookupType( type );
  }

  /**
   * Returns the info for this class
   *
   * @return Returns the info for this class
   */
  @Override
  public String toString() {

    StringBuilder buf = new StringBuilder( "Lookup\n" );
    buf.append( "   type   : " ).append( type ).append( '\n' );
    return buf.toString();
  }

  @Override
  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "lookup" );
    writer.writeAttribute( "index", index );
    writer.writeAttribute( "count", subTables.length );
    writer.writeAttribute( "typeid", type );
    writer.writeAttribute( "type", getTypeAsString() );

    writer.writeAttribute( "flag", flag, 4 );
    writer.writeAttribute( "RightToLeft",
                           checkFlag( flag, FLAG_RIGHT_TO_LEFT ) );
    writer.writeAttribute( "IgnoreBaseGlyphs",
                           checkFlag( flag, FLAG_IGNORE_BASE_GLYPHS ) );
    writer.writeAttribute( "IgnoreBaseLigature",
                           checkFlag( flag, FLAG_IGNORE_BASE_LIGATURES ) );
    writer.writeAttribute( "IgnoreMarks",
                           checkFlag( flag, FLAG_IGNORE_BASE_MARKS ) );

    for( int i = 0; i < subTables.length; i++ ) {
      XtfLookupTable st = subTables[ i ];
      if( st != null ) {
        st.writeXML( writer );
      }
    }
    writer.writeEndElement();

  }

}
