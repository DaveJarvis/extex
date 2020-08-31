/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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
 * The GPOS table <b>MarkToBase</b>.
 *
 * <p>
 * MarkToBase Attachment Positioning Subtable
 * </p>
 *
 * <p>
 * The MarkToBase attachment (MarkBasePos) subtable is used to position
 * combining mark glyphs with respect to base glyphs. For example, the Arabic,
 * Hebrew, and Thai scripts combine vowels, diacritical marks, and tone marks
 * with base glyphs.
 * </p>
 *
 * <p>
 * In the MarkBasePos subtable, every mark glyph has an anchor point and is
 * associated with a class of marks. Each base glyph then defines an anchor
 * point for each class of marks it uses.
 * </p>
 *
 * <p>
 * For example, assume two mark classes: all marks positioned above base glyphs
 * (Class 0), and all marks positioned below base glyphs (Class 1). In this
 * case, each base glyph that uses these marks would define two anchor points,
 * one for attaching the mark glyphs listed in Class 0, and one for attaching
 * the mark glyphs listed in Class 1.
 * </p>
 *
 * <p>
 * To identify the base glyph that combines with a mark, the text-processing
 * client must look backward in the glyph string from the mark to the preceding
 * base glyph. To combine the mark and base glyph, the client aligns their
 * attachment points, positioning the mark with respect to the final pen point
 * (advance) position of the base glyph.
 * </p>
 *
 * <p>
 * The MarkToBase Attachment subtable has one format: MarkBasePosFormat1. The
 * subtable begins with a format identifier (PosFormat) and offsets to two
 * Coverage tables: one that lists all the mark glyphs referenced in the
 * subtable (MarkCoverage), and one that lists all the base glyphs referenced in
 * the subtable (BaseCoverage).
 * </p>
 *
 * <p>
 * For each mark glyph in the MarkCoverage table, a record specifies its class
 * and an offset to the Anchor table that describes the mark's attachment point
 * (MarkRecord). A mark class is identified by a specific integer, called a
 * class value. ClassCount specifies the total number of distinct mark classes
 * defined in all the MarkRecords.
 * </p>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 */
public abstract class XtfGPOSMarkToBaseTable extends XtfLookupTable {

  /**
   * MarkToBaseTable format 1.
   *
   * <p>
   * The MarkBasePosFormat1 subtable also contains an offset to a MarkArray
   * table, which contains all the MarkRecords stored in an array (MarkRecord)
   * by MarkCoverage Index. A MarkArray table also contains a count of the
   * defined MarkRecords (MarkCount).
   * </p>
   *
   * <p>
   * The MarkBasePosFormat1 subtable also contains an offset to a BaseArray
   * table (BaseArray).
   * </p>
   *
   * <p>
   * MarkBasePosFormat1 subtable: MarkToBase attachment point
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
   * <td>uint16</td>
   * <td>PosFormat</td>
   * <td>Format identifier-format = 1</td>
   * </tr>
   * <tr>
   * <td>Offset</td>
   * <td>MarkCoverage</td>
   * <td>Offset to MarkCoverage table-from beginning of MarkBasePos 
   * subtable</td>
   * </tr>
   * <tr>
   * <td>Offset</td>
   * <td>BaseCoverage</td>
   * <td>Offset to BaseCoverage table-from beginning of MarkBasePos 
   * subtable</td>
   * </tr>
   * <tr>
   * <td>uint16</td>
   * <td>ClassCount</td>
   * <td>Number of classes defined for marks</td>
   * </tr>
   * <tr>
   * <td>Offset</td>
   * <td>MarkArray</td>
   * <td>Offset to MarkArray table-from beginning of MarkBasePos subtable</td>
   * </tr>
   * <tr>
   * <td>Offset</td>
   * <td>BaseArray</td>
   * <td>Offset to BaseArray table-from beginning of MarkBasePos subtable</td>
   * </tr>
   * </table>
   */
  public static class MarkToBaseTableFormat1 extends XtfGPOSMarkToBaseTable
      implements
      XMLWriterConvertible {

    /**
     * Offset to MarkCoverage table-from beginning of MarkBasePos subtable.
     */
    private final int markCoverageOffset;

    /**
     * Offset to BaseCoverage table-from beginning of MarkBasePos subtable,
     */
    private final int baseCoverageOffset;

    /**
     * Number of classes defined for marks.
     */
    private final int classCount;

    /**
     * Offset to MarkArray table-from beginning of MarkBasePos subtable.
     */
    private final int markArrayOffset;

    /**
     * Offset to BaseArray table-from beginning of MarkBasePos subtable.
     */
    private final int baseArrayOffset;

    /**
     * BaseArray table.
     */
    private final BaseArrayTable baseArray;

    /**
     * Create a new object.
     *
     * @param rar     the input
     * @param offset  the offset
     * @param xtfGlyp The glyph name.
     * @throws IOException if an IO_error occurs
     */
    MarkToBaseTableFormat1( RandomAccessR rar, int offset,
                            XtfGlyphName xtfGlyp ) throws IOException {

      super( FORMAT1, xtfGlyp );

      markCoverageOffset = rar.readUnsignedShort();
      baseCoverageOffset = rar.readUnsignedShort();
      classCount = rar.readUnsignedShort();
      markArrayOffset = rar.readUnsignedShort();
      baseArrayOffset = rar.readUnsignedShort();

      baseArray = new BaseArrayTable( rar, offset + baseArrayOffset );
    }

    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "marktobase" );
      writer.writeAttribute( "format", getFormat() );
      baseArray.writeXML( writer );
      writer.writeEndElement();
    }

  }

  /**
   * <p>
   * BaseArray table
   * </p>
   * <p>
   * The BaseArray table consists of an array (BaseRecord) and count
   * (BaseCount) of BaseRecords. The array stores the BaseRecords in the same
   * order as the BaseCoverage Index. Each base glyph in the BaseCoverage
   * table has a BaseRecord.
   *
   * <table>
   * <caption>TBD</caption>
   * <tr>
   * <td><b>Value</b></td>
   * <td><b>Type</b></td>
   * <td><b>Description</b></td>
   * </tr>
   * <tr>
   * <td>uint16</td>
   * <td>BaseCount</td>
   * <td>Number of BaseRecords</td>
   * </tr>
   * <tr>
   * <td>struct</td>
   * <td>BaseRecord[BaseCount]</td>
   * <td>Array of BaseRecords-in order of BaseCoverage Index</td>
   * </tr>
   * </table>
   */
  public static class BaseArrayTable implements XMLWriterConvertible {

    /**
     * Array of BaseRecords-in order of BaseCoverage Index.
     */
    private final BaseRecord[] baseRecord;

    /**
     * Creates a new object.
     *
     * @param rar    The input
     * @param offset The offset.
     * @throws IOException if a io-error occurred.
     */
    public BaseArrayTable( RandomAccessR rar, int offset ) throws IOException {

      rar.seek( offset );
      int baseCount = rar.readUnsignedShort();
      baseRecord = new BaseRecord[ baseCount ];
      for( int i = 0; i < baseCount; i++ ) {
        baseRecord[ i ] = new BaseRecord( rar );
      }

    }

    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "basearray" );
      for( int i = 0; i < baseRecord.length; i++ ) {
        baseRecord[ i ].writeXML( writer );
      }
      writer.writeEndElement();

    }

  }

  /**
   * BaseRecord.
   *
   * <p>
   * A BaseRecord declares one Anchor table for each mark class (including
   * Class 0) identified in the MarkRecords of the MarkArray. Each Anchor
   * table specifies one attachment point used to attach all the marks in a
   * particular class to the base glyph. A BaseRecord contains an array of
   * offsets to Anchor tables (BaseAnchor). The zero-based array of offsets
   * defines the entire set of attachment points each base glyph uses to
   * attach marks. The offsets to Anchor tables are ordered by mark class.
   * </p>
   *
   * <p>
   * Note: Anchor tables are not tagged with class value identifiers. Instead,
   * the index value of an Anchor table in the array defines the class value
   * represented by the Anchor table.
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
   * <td>Offset</td>
   * <td>BaseAnchor[ClassCount]</td>
   * <td>Array of offsets (one per class) to Anchor tables-from beginning of
   * BaseArray table-ordered by class-zero-based</td>
   * </tr>
   * </table>
   */
  public static class BaseRecord implements XMLWriterConvertible {

    /**
     * Creates a new object.
     *
     * @param rar The input
     * @throws IOException if a io-error occurred.
     */
    public BaseRecord( RandomAccessR rar ) throws IOException {

      // TODO mgn: incomplete

    }

    public void writeXML( XMLStreamWriter writer ) throws IOException {

      writer.writeStartElement( "baserecord" );
      writer.writeEndElement();

    }

  }

  /**
   * format 1
   */
  private static final int FORMAT1 = 1;

  /**
   * Create a new Instance.
   *
   * @param rar     the input
   * @param offset  the offset
   * @param xtfGlyp The glyph name.
   * @return Returns the new instance.
   * @throws IOException if an IO-error occurs
   */
  public static XtfGPOSMarkToBaseTable newInstance( RandomAccessR rar,
                                                    int offset,
                                                    XtfGlyphName xtfGlyp )
      throws IOException {

    XtfGPOSMarkToBaseTable s = null;
    rar.seek( offset );
    int format = rar.readUnsignedShort();

    if( format == FORMAT1 ) {
      s = new MarkToBaseTableFormat1( rar, offset, xtfGlyp );
    }
    return s;
  }

  /**
   * Create a new object.
   *
   * @param format  the format
   * @param xtfGlyp The glyph name.
   */
  XtfGPOSMarkToBaseTable( int format, XtfGlyphName xtfGlyp ) {

    super( format, xtfGlyp );

  }

}
