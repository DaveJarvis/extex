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

import org.extex.font.format.xtf.tables.XtfGlyphName;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

import java.io.IOException;

/**
 * Class for a PairValueRecord.
 *
 * <p>
 * A PairValueRecord specifies the second glyph in a pair (SecondGlyph) and
 * defines a ValueRecord for each glyph (Value1 and Value2). If ValueFormat1 is
 * set to zero (0) in the PairPos subtable, ValueRecord1 will be empty;
 * similarly, if ValueFormat2 is 0, Value2 will be empty.
 * </p>
 *
 * <table>
 * <caption>TBD</caption>
 * <tr>
 * <td><b>Value</b></td>
 * <td><b>Type</b></td>
 * <td><b>Description</b></td>
 * </tr>
 * <tr>
 * <td>GlyphID</td>
 * <td>SecondGlyph</td>
 * <td>GlyphID of second glyph in the pair-first glyph is listed in the
 * Coverage table</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value1</td>
 * <td>Positioning data for the first glyph in the pair</td>
 * </tr>
 * <tr>
 * <td>ValueRecord</td>
 * <td>Value2</td>
 * <td>Positioning data for the second glyph in the pair</td>
 * </tr>
 * </table>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public class PairValueRecord implements XMLWriterConvertible {

  /**
   * The index.
   */
  private final int idx;

  /**
   * Positioning data for the first and second glyph.
   */
  private final PairValue pairValue;

  /**
   * GlyphID of second glyph in the pair-first glyph is listed in the Coverage
   * table.
   */
  private final int secondGlyph;

  /**
   * The glyph name.
   */
  private final XtfGlyphName xtfGlyph;

  /**
   * Creates a new object.
   *
   * @param rar          The input.
   * @param posOffset    The offset of the pos table.
   * @param xtfGlyph     The glyph name.
   * @param idx          The index.
   * @param valueFormat2 The valueformat2.
   * @param valueFormat1 The valueformat1.
   * @throws IOException if an io-error occurred.
   */
  public PairValueRecord( RandomAccessR rar, int posOffset,
                          XtfGlyphName xtfGlyph, int idx, int valueFormat1,
                          int valueFormat2 )
      throws IOException {

    this.idx = idx;
    this.xtfGlyph = xtfGlyph;
    secondGlyph = rar.readUnsignedShort();
    ValueRecord value1 =
        new ValueRecord( rar, posOffset, xtfGlyph, valueFormat1 );
    ValueRecord value2 =
        new ValueRecord( rar, posOffset, xtfGlyph, valueFormat2 );
    pairValue = new PairValue( value1, value2 );
  }

  /**
   * Getter for pairValue.
   *
   * @return the pairValue
   */
  public PairValue getPairValue() {

    return pairValue;
  }

  /**
   * Getter for secondGlyph.
   *
   * @return the secondGlyph
   */
  public int getSecondGlyph() {

    return secondGlyph;
  }

  /**
   * Initialize the {@link ValueRecord}.
   *
   * @param rar       The input.
   * @param posOffset The ofset of the pos table.
   * @throws IOException if a IO-error occurred.
   */
  public void init( RandomAccessR rar, int posOffset ) throws IOException {

    pairValue.getValue1().init( rar, posOffset );
    pairValue.getValue2().init( rar, posOffset );

  }

  public void writeXML( XMLStreamWriter writer ) throws IOException {

    writer.writeStartElement( "pairvaluerecord" );
    writer.writeAttribute( "ID", idx );
    writer.writeAttribute( "secondGlyph", secondGlyph );
    writer.writeAttribute( "name", xtfGlyph.getGlyphName( secondGlyph ) );
    if( pairValue.getValue1() != null
        && pairValue.getValue1().getValueFormat() != 0 ) {
      pairValue.getValue1().writeXML( writer );
    }
    if( pairValue.getValue2() != null
        && pairValue.getValue2().getValueFormat() != 0 ) {
      pairValue.getValue2().writeXML( writer );
    }
    writer.writeEndElement();
  }

}
